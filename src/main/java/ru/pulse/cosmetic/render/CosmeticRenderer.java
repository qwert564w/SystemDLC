package ru.pulse.cosmetic.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import ru.pulse.cosmetic.geckolib.GeckolibCosmeticRenderer;
import ru.pulse.cosmetic.model.CosmeticModel;
import ru.pulse.cosmetic.model.ModelPosition;

public class CosmeticRenderer {
   private static CosmeticRenderer instance;
   private final GeckolibCosmeticRenderer geckolibRenderer = GeckolibCosmeticRenderer.getInstance();
   private final RenderStack stack = new RenderStack();
   private static final float RAD_TO_DEG = 180.0F / (float)Math.PI;

   public static CosmeticRenderer getInstance() {
      if (instance == null) {
         instance = new CosmeticRenderer();
      }

      return instance;
   }

   public void renderCosmetic(CosmeticModel var1, AbstractClientPlayerEntity var2, MatrixStack var3, VertexConsumerProvider var4, int var5, PlayerEntityModel var6, float var7) {
      if (var1 != null && var1.getTextureId() != null) {
         this.stack.update(var3);
         this.stack.push();
         float var8 = this.transformToPosition(var1, var6);
         this.stack.rotateZDegrees(180.0F);
         this.stack.translate(var1.getX(), var1.getY() + var8, var1.getZ());
         this.stack.rotateYDegrees(var1.getYaw());
         this.stack.rotateXDegrees(var1.getPitch());
         this.stack.rotateZDegrees(var1.getRoll());
         this.stack.scale(var1.getScale(), var1.getScale(), var1.getScale());
         this.geckolibRenderer.renderCosmetic(var1, var3, var4, var5);
         this.stack.pop();
      }
   }

   public void renderCosmetic(CosmeticModel cosmetic, AbstractClientPlayerEntity player, MatrixStack matrices, VertexConsumer vertexConsumer, int light, PlayerEntityModel playerModel, float tickDelta) {
      if (cosmetic != null && cosmetic.getTextureId() != null && vertexConsumer != null) {
         this.stack.update(matrices);
         this.stack.push();
         float yOffset = this.transformToPosition(cosmetic, playerModel);
         this.stack.rotateZDegrees(180.0F);
         this.stack.translate(cosmetic.getX(), cosmetic.getY() + yOffset, cosmetic.getZ());
         this.stack.rotateYDegrees(cosmetic.getYaw());
         this.stack.rotateXDegrees(cosmetic.getPitch());
         this.stack.rotateZDegrees(cosmetic.getRoll());
         this.stack.scale(cosmetic.getScale(), cosmetic.getScale(), cosmetic.getScale());
         this.geckolibRenderer.renderCosmetic(cosmetic, matrices, vertexConsumer, light);
         this.stack.pop();
      }
   }

   private float transformToPosition(CosmeticModel var1, PlayerEntityModel var2) {
      float var3 = 0.0F;
      ModelPosition var4 = var1.getPosition();
      if (var2 == null) {
         return var3;
      }

      switch (var4) {
         case HEAD:
            this.transformToModelPart(var2.head);
            var3 = 0.5F;
            break;
         case ABOVE_HEAD:
            var3 = 0.75F;
            break;
         case BODY:
            this.transformToModelPart(var2.body);
            var3 = -0.3F;
            break;
         case RIGHT_ARM:
            this.transformToModelPart(var2.rightArm);
            var3 = -0.25F;
            break;
         case LEFT_ARM:
            this.transformToModelPart(var2.leftArm);
            var3 = -0.25F;
            break;
         case RIGHT_LEG:
            this.transformToModelPart(var2.rightLeg);
            var3 = -0.35F;
            break;
         case LEFT_LEG:
            this.transformToModelPart(var2.leftLeg);
            var3 = -0.35F;
         case FREE:
      }

      return var3;
   }

   private void transformToModelPart(ModelPart var1) {
      this.stack.translate(var1.pivotX * 0.0625F, var1.pivotY * 0.0625F, var1.pivotZ * 0.0625F);
      this.stack
         .rotateDegrees(var1.pitch * (180.0F / (float)Math.PI), var1.yaw * (180.0F / (float)Math.PI), var1.roll * (180.0F / (float)Math.PI));
   }
}
