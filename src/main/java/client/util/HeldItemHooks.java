package client.util;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.data.UseActionSwitchMap;
import client.enums.InjectPoint;
import client.module.visual.SwingAnimation;
import client.module.visual.ViewModel;
import client.render.GameRendererHooks;
import client.render.ImmediateBufferSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

@HookClass(HeldItemRenderer.class)
public class HeldItemHooks {
   private static final UnsafeAccess<ViewModel> unsafeAccess = new UnsafeAccess<>(ViewModel.class);
   private static final UnsafeAccess<SwingAnimation> unsafeAccess2 = new UnsafeAccess<>(SwingAnimation.class);

   private static void render(MatrixStack matrixStack, Arm arm, float value) {
      int i = arm == Arm.RIGHT ? 1 : -1;
      float f = MathHelper.sin(value * value * (float) Math.PI);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * (45.0F + f * -20.0F)));
      float f1 = MathHelper.sin(MathHelper.sqrt(value) * (float) Math.PI);
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * f1 * -20.0F));
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f1 * -80.0F));
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * -45.0F));
   }

   private static void onAbstractClientPlayerEntityFloatHandFloatItemStackFloatMatrixStackArmIntBooleanSwingAnimation(
      AbstractClientPlayerEntity abstractClientPlayerEntity,
      float value,
      Hand hand,
      float value2,
      ItemStack itemStack,
      float value3,
      MatrixStack matrixStack,
      Arm arm,
      int count,
      boolean flag,
      SwingAnimation swingAnimation
   ) {
      if (abstractClientPlayerEntity.isUsingItem() && abstractClientPlayerEntity.getItemUseTimeLeft() > 0 && abstractClientPlayerEntity.getActiveHand() == hand) {
         switch (UseActionSwitchMap.intArray[itemStack.getUseAction().ordinal()]) {
            case 1:
               render2(matrixStack, arm, value3);
               break;
            case 2:
            case 3:
               render(matrixStack, value, arm, itemStack, abstractClientPlayerEntity);
               render2(matrixStack, arm, value3);
               break;
            case 4:
               render2(matrixStack, arm, value3);
               if (!(itemStack.getItem() instanceof ShieldItem)) {
                  matrixStack.translate(count * -0.14F, 0.08F, 0.14F);
                  matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-102.25F));
                  matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(count * 13.36F));
                  matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(count * 78.05F));
               }
               break;
            case 5:
               render2(matrixStack, arm, value3);
               matrixStack.translate(count * -0.28F, 0.18F, 0.16F);
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-13.93F));
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(count * 35.3F));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(count * -9.78F));
               float f4 = itemStack.getMaxUseTime(abstractClientPlayerEntity) - (abstractClientPlayerEntity.getItemUseTimeLeft() - value + 1.0F);
               float f5 = f4 / 20.0F;
               f5 = (f5 * f5 + f5 * 2.0F) / 3.0F;
               if (f5 > 1.0F) {
                  f5 = 1.0F;
               }

               if (f5 > 0.1F) {
                  float f2 = MathHelper.sin((f4 - 0.1F) * 1.3F);
                  float f3 = f2 * (f5 - 0.1F);
                  matrixStack.translate(f3 * 0.0F, f3 * 0.004F, f3 * 0.0F);
               }

               matrixStack.translate(f5 * 0.0F, f5 * 0.0F, f5 * 0.04F);
               matrixStack.scale(1.0F, 1.0F, 1.0F + f5 * 0.2F);
               matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(count * 45.0F));
               break;
            case 6:
               render2(matrixStack, arm, value3);
               matrixStack.translate(count * -0.5F, 0.7F, 0.1F);
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-55.0F));
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(count * 35.3F));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(count * -9.785F));
               float f = (itemStack.getMaxUseTime(abstractClientPlayerEntity) - (abstractClientPlayerEntity.getItemUseTimeLeft() - value + 1.0F)) / 10.0F;
               if (f > 1.0F) {
                  f = 1.0F;
               }

               if (f > 0.1F) {
                  float f1 = MathHelper.sin((f - 0.1F) * 1.3F) * (f - 0.1F);
                  matrixStack.translate(f1 * 0.0F, f1 * 0.004F, f1 * 0.0F);
               }

               matrixStack.translate(0.0F, 0.0F, f * 0.2F);
               matrixStack.scale(1.0F, 1.0F, 1.0F + f * 0.2F);
               matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(count * 45.0F));
               break;
            case 7:
               render(matrixStack, value, arm, itemStack, abstractClientPlayerEntity, value3);
               break;
            case 8:
               onSwingAnimationFloatFloatMatrixStackIntArmBoolean(swingAnimation, value2, value3, matrixStack, count, arm, flag);
         }
      } else if (abstractClientPlayerEntity.isUsingRiptide()) {
         render2(matrixStack, arm, value3);
         matrixStack.translate(count * -0.4F, 0.8F, 0.3F);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(count * 65.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(count * -85.0F));
      } else {
         onSwingAnimationFloatFloatMatrixStackIntArmBoolean(swingAnimation, value2, value3, matrixStack, count, arm, flag);
      }
   }

   private static void render(MatrixStack matrixStack, float value, Arm arm, ItemStack itemStack, PlayerEntity playerEntity) {
      float f = playerEntity.getItemUseTimeLeft() - value + 1.0F;
      float f1 = f / itemStack.getMaxUseTime(playerEntity);
      if (f1 < 0.8F) {
         float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);
         matrixStack.translate(0.0F, f2, 0.0F);
      }

      float f3 = 1.0F - (float)Math.pow(f1, 27.0);
      int i = arm == Arm.RIGHT ? 1 : -1;
      matrixStack.translate(f3 * 0.6F * i, f3 * -0.5F, f3 * 0.0F);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * f3 * 90.0F));
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f3 * 10.0F));
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * f3 * 30.0F));
   }

   private static void render2(MatrixStack matrixStack, Arm arm, float value) {
      int i = arm == Arm.RIGHT ? 1 : -1;
      matrixStack.translate(i * 0.56F, -0.52F + value * -0.6F, -0.72F);
   }

   private static void render(MatrixStack matrixStack, float value, Arm arm, ItemStack itemStack, PlayerEntity playerEntity, float value2) {
      render2(matrixStack, arm, value2);
      float f = playerEntity.getItemUseTimeLeft() % 10;
      float f1 = f - value + 1.0F;
      float f2 = 1.0F - f1 / 10.0F;
      float f3 = -15.0F + 75.0F * MathHelper.cos(f2 * 2.0F * (float) Math.PI);
      if (arm != Arm.RIGHT) {
         matrixStack.translate(0.1, 0.83, 0.35);
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f3));
         matrixStack.translate(-0.3, 0.22, 0.35);
      } else {
         matrixStack.translate(-0.25, 0.22, 0.35);
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f3));
      }
   }

   private static boolean isViewModelBoolean(ViewModel viewModel, boolean flag) {
      if (!GameRendererHooks.isFlag3() || FramebufferRedirect.isFlag()) {
         return false;
      } else if (viewModel == null) {
         return true;
      } else {
         ViewModelController viewmodelcontroller = viewModel.getViewModelController();
         return !viewmodelcontroller.check5() || viewmodelcontroller.isBoolean(flag);
      }
   }

   private static void onAbstractClientPlayerEntityFloatHandFloatItemStackFloatMatrixStackArmIntBooleanSwingAnimation2(
      AbstractClientPlayerEntity abstractClientPlayerEntity,
      float value,
      Hand hand,
      float value2,
      ItemStack itemStack,
      float value3,
      MatrixStack matrixStack,
      Arm arm,
      int count,
      boolean flag2,
      SwingAnimation swingAnimation
   ) {
      boolean flag = CrossbowItem.isCharged(itemStack);
      if (abstractClientPlayerEntity.isUsingItem() && abstractClientPlayerEntity.getItemUseTimeLeft() > 0 && abstractClientPlayerEntity.getActiveHand() == hand) {
         render2(matrixStack, arm, value3);
         matrixStack.translate(count * -0.48F, -0.094F, 0.057F);
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-12.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(count * 65.3F));
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(count * -9.78F));
         float f = itemStack.getMaxUseTime(abstractClientPlayerEntity) - (abstractClientPlayerEntity.getItemUseTimeLeft() - value + 1.0F);
         float f1 = f / CrossbowItem.getPullTime(itemStack, abstractClientPlayerEntity);
         if (f1 > 1.0F) {
            f1 = 1.0F;
         }

         if (f1 > 0.1F) {
            float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
            float f3 = f1 - 0.1F;
            float f4 = f2 * f3;
            matrixStack.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
         }

         matrixStack.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.04F);
         matrixStack.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
         matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(count * 45.0F));
      } else {
         onSwingAnimationFloatFloatMatrixStackIntArmBoolean(swingAnimation, value2, value3, matrixStack, count, arm, flag2);
         if (flag && value2 < 0.001F && flag2) {
            matrixStack.translate(count * -0.64F, 0.0F, 0.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(count * 10.0F));
         }
      }
   }

   private static void onSwingAnimationFloatFloatMatrixStackIntArmBoolean(
      SwingAnimation swingAnimation, float value, float value2, MatrixStack matrixStack, int count, Arm arm, boolean flag2
   ) {
      boolean flag = swingAnimation != null && swingAnimation.check3();
      if (flag && swingAnimation.onlyVGlavnoyRuke.isFlag3() && !flag2) {
         flag = false;
      }

      render2(matrixStack, arm, flag ? 0.0F : value2);

      if (flag) {
         float f = swingAnimation.getFloatByFloat(value);
         swingAnimation.onFloatArmMatrixStack(f, arm, matrixStack);
      } else {
         float f3 = -0.4F * MathHelper.sin(MathHelper.sqrt(value) * (float) Math.PI);
         float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(value) * (float) (Math.PI * 2));
         float f2 = -0.2F * MathHelper.sin(value * (float) Math.PI);
         matrixStack.translate(count * f3, f1, f2);
         render(matrixStack, arm, value);
      }
   }

   private static void onViewModelMatrixStackBoolean(ViewModel viewModel, MatrixStack matrixStack, boolean flag2) {
      ViewModelController viewmodelcontroller = viewModel.getViewModelController();
      boolean flag = !flag2 && viewModel.getZerkalo().isFlag3();
      boolean flag1 = flag || flag2;
      float f = flag ? -viewmodelcontroller.getFloatByBoolean(true) : viewmodelcontroller.getFloatByBoolean(flag1);
      float f1 = viewmodelcontroller.getFloatByBoolean2(flag1);
      float f2 = viewmodelcontroller.getFloatByBoolean3(flag1);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      viewmodelcontroller.onMatrix4fFloatFloatBoolean(matrix4f, f1, f, flag2);
      matrixStack.translate(f, f1, 0.0F);
      if (f2 != 1.0F) {
         float f3 = ViewModelController.getFloatByBoolean4(flag2);
         matrixStack.translate(f3, ViewModelController.getFloat3(), ViewModelController.getFloat());
         matrixStack.scale(f2, f2, f2);
         matrixStack.translate(-f3, -ViewModelController.getFloat3(), -ViewModelController.getFloat());
      }
   }

   @Hook(
      method = "method_3228",
      desc = "(Lnet/minecraft/class_742;FFLnet/minecraft/class_1268;FLnet/minecraft/class_1799;FLnet/minecraft/class_4587;Lnet/minecraft/class_4597;I)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onHeldItemRendererAbstractClientPlayerEntityFloatFloatHandFloatItemStackFloatMatrixStackVertexConsumerProviderInt(
      HeldItemRenderer heldItemRenderer,
      AbstractClientPlayerEntity abstractClientPlayerEntity,
      float value,
      float value2,
      Hand hand,
      float value3,
      ItemStack itemStack,
      float value4,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      int count
   ) {
      ViewModel viewmodel = (ViewModel)unsafeAccess.getModule2();
      SwingAnimation swinganimation = (SwingAnimation)unsafeAccess2.getModule2();
      boolean flag = viewmodel != null;
      boolean flag1 = swinganimation != null && swinganimation.check3();
      boolean flag2 = hand == Hand.MAIN_HAND;
      Arm arm = flag2 ? abstractClientPlayerEntity.getMainArm() : abstractClientPlayerEntity.getMainArm().getOpposite();
      boolean flag3 = arm == Arm.RIGHT;
      int i = flag3 ? 1 : -1;
      if (!flag && !flag1) {
         HandleInvoker.onObjectArray(heldItemRenderer, abstractClientPlayerEntity, value, value2, hand, value3, itemStack, value4, matrixStack, vertexConsumerProvider, count);
         if (isViewModelBoolean(null, flag3)) {

            try {
               client.render.PlayerOutlineEffect.onVertexConsumerProvider(vertexConsumerProvider);
               FramebufferRedirect.setFlag2();
               MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
               ImmediateBufferSource immediatebuffersource3 = ImmediateBufferSource.getInstance();
               HandleInvoker.onObjectArray(heldItemRenderer, abstractClientPlayerEntity, value, value2, hand, value3, itemStack, value4, matrixStack, immediatebuffersource3, count);
               immediatebuffersource3.update();
               GameRendererHooks.setFlag4();
            } finally {
               FramebufferRedirect.setFlag();
               client.render.PlayerOutlineEffect.update4();
            }
         }
      } else if (!abstractClientPlayerEntity.isUsingSpyglass()) {
         if (!flag1) {
            matrixStack.push();
            onViewModelMatrixStackBoolean(viewmodel, matrixStack, flag3);
            HandleInvoker.onObjectArray(heldItemRenderer, abstractClientPlayerEntity, value, value2, hand, value3, itemStack, value4, matrixStack, vertexConsumerProvider, count);
            matrixStack.pop();
            if (isViewModelBoolean(viewmodel, flag3)) {
               matrixStack.push();
               onViewModelMatrixStackBoolean(viewmodel, matrixStack, flag3);

               try {

                  client.render.PlayerOutlineEffect.onVertexConsumerProvider(vertexConsumerProvider);
                  FramebufferRedirect.setFlag2();
                  MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
                  ImmediateBufferSource immediatebuffersource2 = ImmediateBufferSource.getInstance();
                  HandleInvoker.onObjectArray(heldItemRenderer, abstractClientPlayerEntity, value, value2, hand, value3, itemStack, value4, matrixStack, immediatebuffersource2, count);
                  immediatebuffersource2.update();
                  GameRendererHooks.setFlag4();
               } finally {
                  FramebufferRedirect.setFlag();
                  client.render.PlayerOutlineEffect.update4();
                  matrixStack.pop();
               }
            }
         } else if (!itemStack.isEmpty() && !itemStack.contains(DataComponentTypes.MAP_ID)) {
            matrixStack.push();

            try {
               if (flag) {
                  onViewModelMatrixStackBoolean(viewmodel, matrixStack, flag3);
               }

               if (itemStack.isOf(Items.CROSSBOW)) {
                  onAbstractClientPlayerEntityFloatHandFloatItemStackFloatMatrixStackArmIntBooleanSwingAnimation2(
                     abstractClientPlayerEntity, value, hand, value3, itemStack, value4, matrixStack, arm, i, flag2, swinganimation
                  );
               } else {
                  onAbstractClientPlayerEntityFloatHandFloatItemStackFloatMatrixStackArmIntBooleanSwingAnimation(
                     abstractClientPlayerEntity, value, hand, value3, itemStack, value4, matrixStack, arm, i, flag2, swinganimation
                  );
               }

               ModelTransformationMode modeltransformationmode = flag3
                  ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND
                  : ModelTransformationMode.FIRST_PERSON_LEFT_HAND;
               heldItemRenderer.renderItem(abstractClientPlayerEntity, itemStack, modeltransformationmode, !flag3, matrixStack, vertexConsumerProvider, count);
               if (isViewModelBoolean(viewmodel, flag3)) {

                  try {
                     // Хук Framebuffer.beginWrite перенаправляет привязку в маску HandGlow, но внутри
                     // этого блока beginWrite не звал никто — рука рисовалась второй раз прямо в main,
                     // а маска оставалась пустой, и blur/blit давали нулевое свечение.
                     client.render.PlayerOutlineEffect.onVertexConsumerProvider(vertexConsumerProvider);
                     FramebufferRedirect.setFlag2();
                     MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
                     ImmediateBufferSource immediatebuffersource1 = ImmediateBufferSource.getInstance();
                     heldItemRenderer.renderItem(abstractClientPlayerEntity, itemStack, modeltransformationmode, !flag3, matrixStack, immediatebuffersource1, count);
                     immediatebuffersource1.update();
                     GameRendererHooks.setFlag4();
                  } finally {
                     FramebufferRedirect.setFlag();
                     client.render.PlayerOutlineEffect.update4();
                  }
               }
            } finally {
               matrixStack.pop();
            }
         } else {
            if (flag) {
               matrixStack.push();
            }

            if (flag) {
               onViewModelMatrixStackBoolean(viewmodel, matrixStack, flag3);
            }

            HandleInvoker.onObjectArray(heldItemRenderer, abstractClientPlayerEntity, value, value2, hand, value3, itemStack, value4, matrixStack, vertexConsumerProvider, count);
            if (flag) {
               matrixStack.pop();
            }

            if (isViewModelBoolean(viewmodel, flag3)) {
               if (flag) {
                  matrixStack.push();
               }

               if (flag) {
                  onViewModelMatrixStackBoolean(viewmodel, matrixStack, flag3);
               }


               try {
                  // Хук Framebuffer.beginWrite перенаправляет привязку в маску HandGlow, но внутри
                  // этого блока beginWrite не звал никто — рука рисовалась второй раз прямо в main,
                  // а маска оставалась пустой, и blur/blit давали нулевое свечение.
                  client.render.PlayerOutlineEffect.onVertexConsumerProvider(vertexConsumerProvider);
                  FramebufferRedirect.setFlag2();
                  MinecraftClient.getInstance().getFramebuffer().beginWrite(true);
                  ImmediateBufferSource immediatebuffersource = ImmediateBufferSource.getInstance();
                  HandleInvoker.onObjectArray(heldItemRenderer, abstractClientPlayerEntity, value, value2, hand, value3, itemStack, value4, matrixStack, immediatebuffersource, count);
                  immediatebuffersource.update();
                  GameRendererHooks.setFlag4();
               } finally {
                  FramebufferRedirect.setFlag();
                  client.render.PlayerOutlineEffect.update4();
                  if (flag) {
                     matrixStack.pop();
                  }
               }
            }
         }
      }
   }
}
