package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class HitboxChecks extends Module {
   private final BooleanSetting hideMusor;

   public HitboxChecks() {
      super("HitboxChecks", Category.VISUAL);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Скрывать мусор");
      booleansetting.setDescription("Скрывать красную полоску глаз и синюю линию взгляда");
      this.hideMusor = booleansetting;
      this.addSettings(new Setting[]{this.hideMusor});
   }

   @Override
   public void onDisable() {
   }

   public void onEntityMatrixStackFloatVertexConsumerProvider(Entity entity2, MatrixStack matrixStack, float value, VertexConsumerProvider vertexConsumerProvider) {
      VertexConsumer vertexconsumer = vertexConsumerProvider.getBuffer(RenderLayer.getLines());
      Box box = entity2.getBoundingBox().offset(-entity2.getX(), -entity2.getY(), -entity2.getZ());
      VertexRendering.drawBox(matrixStack, vertexconsumer, box, 1.0F, 1.0F, 1.0F, 1.0F);
      if (entity2 instanceof EnderDragonEntity enderdragonentity) {
         double d0 = -MathHelper.lerp(value, entity2.lastRenderX, entity2.getX());
         double d1 = -MathHelper.lerp(value, entity2.lastRenderY, entity2.getY());
         double d2 = -MathHelper.lerp(value, entity2.lastRenderZ, entity2.getZ());

         for (EnderDragonPart enderdragonpart : enderdragonentity.getBodyParts()) {
            matrixStack.push();
            double d3 = d0 + MathHelper.lerp(value, enderdragonpart.lastRenderX, enderdragonpart.getX());
            double d4 = d1 + MathHelper.lerp(value, enderdragonpart.lastRenderY, enderdragonpart.getY());
            double d5 = d2 + MathHelper.lerp(value, enderdragonpart.lastRenderZ, enderdragonpart.getZ());
            matrixStack.translate(d3, d4, d5);
            VertexRendering.drawBox(
               matrixStack,
               vertexconsumer,
               enderdragonpart.getBoundingBox().offset(-enderdragonpart.getX(), -enderdragonpart.getY(), -enderdragonpart.getZ()),
               0.25F,
               1.0F,
               0.0F,
               1.0F
            );
            matrixStack.pop();
         }
      }

      if (!this.hideMusor.isFlag3()) {
         if (entity2 instanceof LivingEntity) {
            VertexRendering.drawBox(
               matrixStack,
               vertexconsumer,
               box.minX,
               entity2.getStandingEyeHeight() - 0.01F,
               box.minZ,
               box.maxX,
               entity2.getStandingEyeHeight() + 0.01F,
               box.maxZ,
               1.0F,
               0.0F,
               0.0F,
               1.0F
            );
         }

         VertexRendering.drawVector(
            matrixStack, vertexconsumer, new Vector3f(0.0F, entity2.getStandingEyeHeight(), 0.0F), entity2.getRotationVec(value).multiply(2.0), -16776961
         );
      }

      Entity entity = entity2.getVehicle();
      if (entity != null) {
         float f = Math.min(entity.getWidth(), entity2.getWidth()) / 2.0F;
         Vec3d vec3d = entity.getPassengerRidingPos(entity2).subtract(entity2.getPos());
         VertexRendering.drawBox(matrixStack, vertexconsumer, vec3d.x - f, vec3d.y, vec3d.z - f, vec3d.x + f, vec3d.y + 0.0625, vec3d.z + f, 1.0F, 0.0F, 0.0F, 1.0F);
      }
   }

   @Override
   public void onEnable() {
   }
}
