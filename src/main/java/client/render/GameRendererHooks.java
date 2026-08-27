package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.gui.screen.ScreenHelper;
import client.module.Feature;
import client.module.client.StreamBypass;
import client.module.player.SafeLeave;
import client.module.render.ShulkerPreview;
import client.module.visual.AspectRatio;
import client.module.visual.FrameSync;
import client.module.visual.HandGlow;
import client.module.visual.NoRender;
import client.module.visual.ViewModel;
import client.module.visual.Zoom;
import client.util.ModuleDispatcher;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import client.util.ViewModelController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

@HookClass(GameRenderer.class)
public class GameRendererHooks {
   private static RenderTickCounter renderTickCounter;
   private static boolean flag = false;
   private static float value = 110.0F;
   private static boolean flag2 = false;
   private static final UnsafeAccess<NoRender> unsafeAccess = new UnsafeAccess<>(NoRender.class);
   private static final UnsafeAccess<AspectRatio> unsafeAccess2 = new UnsafeAccess<>(AspectRatio.class);
   private static final UnsafeAccess<ShulkerPreview> unsafeAccess3 = new UnsafeAccess<>(ShulkerPreview.class);
   private static final UnsafeAccess<FrameSync> unsafeAccess4 = new UnsafeAccess<>(FrameSync.class);
   private static final UnsafeAccess<HandGlow> unsafeAccess5 = new UnsafeAccess<>(HandGlow::getAnimation);
   private static final UnsafeAccess<ViewModel> unsafeAccess6 = new UnsafeAccess<>(ViewModel.class);
   private static boolean flag3;
   private static boolean flag4;
   private static final long time = ReflectionCache.getLongByClassClassInt(GameRenderer.class, float.class, 5);
   private static final long time2 = ReflectionCache.getLongByClassClassInt(GameRenderer.class, float.class, 6);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(GameRenderer.class, float.class, 7);

   @Hook(
      method = "method_3186",
      desc = "(Lnet/minecraft/class_4587;F)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isGameRendererMatrixStackFloat(GameRenderer gameRenderer, MatrixStack matrixStack, float value) {
      if (flag2) {
         return false;
      } else {
         NoRender norender = (NoRender)unsafeAccess.getModule2();
         return norender == null || !norender.check9();
      }
   }

   public static void setFlag2(boolean flag) {
      flag2 = flag;
   }

   @Hook(
      method = "method_3172",
      desc = "(Lnet/minecraft/class_4184;FLorg/joml/Matrix4f;)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onGameRendererCameraFloatMatrix4f(GameRenderer gameRenderer, Camera camera, float value, Matrix4f matrix4f) {
      if (flag3) {
         flag3 = false;
         ViewModelController viewmodelcontroller = getViewModelController();
         if (viewmodelcontroller != null) {
            try {
               viewmodelcontroller.update2();
            } catch (Throwable throwable) {
            }
         }

         if (flag4) {
            flag4 = false;

            try {
               if (viewmodelcontroller != null && viewmodelcontroller.check5()) {
                  PlayerOutlineEffect.onMinecraftClientFloat(Feature.mc, viewmodelcontroller.getFloat2());
               } else {
                  HandGlow handglow = (HandGlow)unsafeAccess5.getModule2();
                  if (handglow != null) {
                     MinecraftClient minecraftclient = Feature.mc;
                     PlayerOutlineEffect.onHandGlowMinecraftClient(handglow, minecraftclient);
                  }
               }
            } catch (Throwable throwable1) {
            } finally {
               try {
                  if (Feature.mc.getFramebuffer() != null) {
                     Feature.mc.getFramebuffer().beginWrite(true);
                  }
               } catch (Throwable throwable2) {
               }
            }
         }
      }
   }

   private static ViewModelController getViewModelController() {
      ViewModel viewmodel = (ViewModel)unsafeAccess6.getModule2();
      return viewmodel != null ? viewmodel.getViewModelController() : null;
   }

   @Hook(
      target = InGameOverlayRenderer.class,
      method = "method_23070",
      desc = "(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isMatrixStackVertexConsumerProvider(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check11();
   }

   @Hook(
      method = "method_3172",
      desc = "(Lnet/minecraft/class_4184;FLorg/joml/Matrix4f;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onGameRendererCameraFloatMatrix4f2(GameRenderer gameRenderer, Camera camera, float value, Matrix4f matrix4f) {
      flag3 = false;
      flag4 = false;
      ViewModelController viewmodelcontroller = getViewModelController();
      if (viewmodelcontroller != null) {
         viewmodelcontroller.update4();
      }

      if (unsafeAccess5.getModule2() != null || viewmodelcontroller != null && viewmodelcontroller.check3()) {
         if (PlayerOutlineEffect.isFramebuffer(Feature.mc.getFramebuffer())) {
            flag3 = true;
         }
      }
   }

   @Hook(
      method = "method_57796",
      desc = "()V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isGameRenderer(GameRenderer gameRenderer) {
      return !StreamBypass.isFlag();
   }

   public static void setValue(float value2) {
      value = value2;
   }

   public static void setFlag(boolean flag2) {
      flag = flag2;
   }

   @Hook(
      target = HandledScreen.class,
      method = "method_2380",
      desc = "(Lnet/minecraft/class_332;II)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isHandledScreenDrawContextIntInt(HandledScreen handledScreen, DrawContext drawContext, int count, int count2) {
      try {
         ShulkerPreview shulkerpreview = (ShulkerPreview)unsafeAccess3.getModule2();
         if (shulkerpreview != null && shulkerpreview.isIntDrawContextHandledScreenInt(count, drawContext, handledScreen, count2)) {
            return false;
         }
      } catch (Exception exception) {
      }

      return true;
   }

   @Hook(
      method = "method_22973",
      desc = "(F)Lorg/joml/Matrix4f;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Matrix4f getMatrix4fByGameRendererFloat(GameRenderer gameRenderer, float value) {
      try {
         AspectRatio aspectratio = (AspectRatio)unsafeAccess2.getModule2();
         if (aspectratio != null && !AspectRatio.check3()) {
            Matrix4f matrix4f = new Matrix4f();
            float f = UnsafeAccess.unsafe.getFloat(gameRenderer, time);
            if (f != 1.0F) {
               float f1 = UnsafeAccess.unsafe.getFloat(gameRenderer, time2);
               float f2 = UnsafeAccess.unsafe.getFloat(gameRenderer, time3);
               matrix4f.translate(f1, -f2, 0.0F);
               matrix4f.scale(f, f, 1.0F);
            }

            return matrix4f.perspective(value * (float) (Math.PI / 180.0), AspectRatio.getFloat(), 0.05F, gameRenderer.getFarPlaneDistance());
         }
      } catch (Exception exception) {
      }

      return (Matrix4f)HandleInvoker.getObjectByObjectArray2(gameRenderer, value);
   }

   @Hook(
      method = "method_3198",
      desc = "(Lnet/minecraft/class_4587;F)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isGameRendererMatrixStackFloat2(GameRenderer gameRenderer, MatrixStack matrixStack, float value) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null || !norender.check7();
   }

   @Hook(
      method = "method_3196",
      desc = "(Lnet/minecraft/class_4184;FZ)F",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static float getFloatByGameRendererCameraFloatBoolean(GameRenderer gameRenderer, Camera camera, float value2, boolean flag2) {
      float f;
      if (flag) {
         f = flag2 ? value : 70.0F;
      } else {
         Float f1 = (Float)HandleInvoker.getObjectByObjectArray2(gameRenderer, camera, value2, flag2);
         f = f1 != null ? f1 : 70.0F;
      }

      Zoom zoom = Zoom.getInstance();
      if (zoom != null) {
         f *= zoom.getFloat();
      }

      return f;
   }

   public static void setFlag4() {
      flag4 = true;
   }

   public static boolean isFlag3() {
      return flag3;
   }

   @Hook(
      target = InGameOverlayRenderer.class,
      method = "method_23068",
      desc = "(Lnet/minecraft/class_1058;Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSpriteMatrixStackVertexConsumerProvider(Sprite sprite, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
      NoRender norender = (NoRender)unsafeAccess.getModule2();
      return norender == null;
   }

   public static RenderTickCounter getRenderTickCounter() {
      return renderTickCounter;
   }

   @Hook(
      target = Screen.class,
      method = "method_47413",
      desc = "(Lnet/minecraft/class_332;IIF)V",
      getInjectPoint = InjectPoint.TAIL
   )
   public static void onScreenDrawContextIntIntFloat(Screen screen2, DrawContext drawContext, int count, int count2, float value) {
      try {
         if (Feature.mc.currentScreen == screen2
            && Feature.mc.getOverlay() == null
            && Feature.mc.isFinishedLoading()
            && renderTickCounter != null
            && !StreamBypass.check7()) {
            ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
            if (moduledispatcher != null) {
               moduledispatcher.render(drawContext, renderTickCounter);
            }
         }

         if (screen2 instanceof GameMenuScreen gamemenuscreen && SafeLeave.isFlag() && ScreenHelper.time != -1L) {
            ButtonWidget buttonwidget = (ButtonWidget)ReflectionCache.getObjectByObjectLong(gamemenuscreen, ScreenHelper.time);
            if (buttonwidget != null && Feature.mc.textRenderer != null) {
               int i = buttonwidget.getX() + buttonwidget.getWidth() / 2;
               int j = buttonwidget.getY() + buttonwidget.getHeight() + 4;
               String s = SafeLeave.isFlag2() ? "§e⚠ Нажмите ещё раз для выхода" : "§c⚠ Вы в PvP!";
               drawContext.drawCenteredTextWithShadow(Feature.mc.textRenderer, s, i, j, -1);
            }
         }
      } catch (Exception exception) {
      }
   }

   @Hook(
      method = "method_3192",
      desc = "(Lnet/minecraft/class_9779;Z)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isGameRendererRenderTickCounterBoolean(GameRenderer gameRenderer, RenderTickCounter renderTickCounter2, boolean flag) {
      FrameSync framesync = (FrameSync)unsafeAccess4.getModule2();
      if (framesync != null && framesync.check3()) {
         return false;
      } else {
         renderTickCounter = renderTickCounter2;
         MipmapCapture.update2();
         return true;
      }
   }
}
