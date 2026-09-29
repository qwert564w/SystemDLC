package pulse.cosmetic;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import ru.pulse.cosmetic.model.CosmeticModel;
import ru.pulse.cosmetic.render.CosmeticRenderer;

public class CosmeticFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    public CosmeticFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue commandQueue, int light,
                       PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        MinecraftClient client = MinecraftClient.getInstance();
        AbstractClientPlayerEntity player = client.player;
        if (player == null || commandQueue == null || state == null || state.spectator) return;
        if (state.id != player.getId()) return;

        List<Integer> selected = LocalCosmetics.selectedIndices();
        if (selected.isEmpty()) return;

        PlayerEntityModel model = this.getContextModel();
        for (Integer idx : selected) {
            if (idx == null || "cape".equals(LocalCosmetics.type(idx))) continue;
            CosmeticModel cosmetic = LocalCosmetics.modelFor(idx);
            if (cosmetic == null || cosmetic.getTextureId() == null) continue;
            Identifier tex = cosmetic.getTextureId();
            RenderLayer layer = RenderLayer.getEntityTranslucent(tex);
            try {
                commandQueue.submitCustom(matrices, layer, (entry, vertexConsumer) -> {
                    MatrixStack local = new MatrixStack();
                    local.multiplyPositionMatrix(entry.getPositionMatrix());
                    CosmeticRenderer.getInstance().renderCosmetic(
                        cosmetic, player, local, vertexConsumer, light, model, limbDistance);
                });
            } catch (Throwable t) {
                // fallback: try without submitCustom path
            }
        }
    }
}
