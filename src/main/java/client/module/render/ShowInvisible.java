package client.module.render;

import client.module.Category;
import client.module.Module;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ShowInvisible extends Module {
   private static ShowInvisible INSTANCE;
   private SliderSetting alfa;
   private BooleanSetting hitboks;

   public ShowInvisible() {
      super("ShowInvisible", Category.RENDER);
      SliderSetting slidersetting = new SliderSetting("", "", 0.5, 0.1, 1.0, 0.05);
      slidersetting.setName("Альфа");
      slidersetting.setDescription("Прозрачность модельки");
      this.alfa = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Хитбокс");
      booleansetting.setDescription("Показывать хитбокс невидимых игроков");
      this.hitboks = booleansetting;
      this.addSettings(new Setting[]{this.alfa, this.hitboks});
      INSTANCE = this;
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()) {
         try {
            this.render8(worldRenderContext);
         } catch (Exception exception) {
         }
      }
   }

   private void render8(WorldRenderContext worldRenderContext) {
      MinecraftClient minecraftclient = this.client();
      PlayerEntity playerentity = this.player();
      if (playerentity != null) {
         List<? extends PlayerEntity> list = this.world().getPlayers();
         MatrixStack matrixstack = worldRenderContext.getMatrixStack();
         float f = worldRenderContext.getRenderTickCounter().getTickDelta(true);
         Vec3d vec3d = worldRenderContext.getCamera().getPos();
         double d0 = vec3d.x;
         double d1 = vec3d.y;
         double d2 = vec3d.z;
         EntityRenderDispatcher entityrenderdispatcher = minecraftclient.getEntityRenderDispatcher();
         Immediate immediate = minecraftclient.getBufferBuilders().getEntityVertexConsumers();
         float f1 = this.alfa.getValueAsFloat();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f1);

         try {
            for (PlayerEntity playerentity1 : list) {
               if (playerentity1 != playerentity && playerentity1.isInvisible()) {
                  double d3 = playerentity1.prevX + (playerentity1.getX() - playerentity1.prevX) * f - d0;
                  double d4 = playerentity1.prevY + (playerentity1.getY() - playerentity1.prevY) * f - d1;
                  double d5 = playerentity1.prevZ + (playerentity1.getZ() - playerentity1.prevZ) * f - d2;
                  matrixstack.push();
                  matrixstack.translate(d3, d4, d5);
                  EntityRenderer entityrenderer = entityrenderdispatcher.getRenderer(playerentity1);
                  EntityRenderState entityrenderstate = entityrenderer.createRenderState();
                  entityrenderer.updateRenderState(playerentity1, entityrenderstate, f);
                  entityrenderstate.invisible = false;
                  entityrenderer.render(entityrenderstate, matrixstack, immediate, 15728880);
                  matrixstack.pop();
               }
            }

            immediate.draw();
         } finally {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
         }
      }
   }

   public static ShowInvisible getInstance() {
      return INSTANCE;
   }

   public boolean check3() {
      return this.hitboks.isFlag3();
   }

   @Override
   public void onEnable() {
   }
}
