package recovered.fabric.mixin.hooks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void intelClientRenderTitle(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        final Text title = Text.literal("intel client");
        final int width = context.getScaledWindowWidth();
        final int titleWidth = MinecraftClient.getInstance().textRenderer.getWidth(title);
        final int x = width / 2 - titleWidth / 2;
        final int y = 56;

        // Cover the vanilla logo area and keep the main menu background black.
        context.fill(Math.max(0, x - 34), 34, Math.min(width, x + titleWidth + 34), 92, 0xFF000000);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, title, x, y, 0xFFFFFFFF);

        // Blue dot above the first "i".
        context.fill(x + 1, y - 5, x + 4, y - 2, 0xFF4DA3FF);
    }
}
