package client.util;

import client.module.Feature;
import client.module.visual.ViewModel;
import client.render.PixelReader;
import client.render.PlayerOutlineEffect;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.ChatScreen;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public final class ViewModelController {
   private static boolean flag;
   private final ViewModel viewModel;
   private final ViewModelTransform viewModelTransform;
   private final ViewModelTransform viewModelTransform2;
   private final Interpolation interpolation = new Interpolation();
   private final SmoothFloat smoothFloat = new SmoothFloat(0.0F);
   private final Vector4f vector4f = new Vector4f();
   private ViewModelTransform viewModelTransform3;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4 = true;
   private boolean flag5;
   private int value = -1;
   private boolean flag6;
   private boolean flag7 = true;
   private double value2;
   private double value3;
   private float value4;
   private float value5;
   private long time;

   public ViewModelController(ViewModel viewModel2) {
      this.viewModel = viewModel2;
      this.viewModelTransform = new ViewModelTransform(viewModel2.getX(), viewModel2.getY(), viewModel2.getSize());
      this.viewModelTransform2 = new ViewModelTransform(viewModel2.getLX(), viewModel2.getLY(), viewModel2.getLSize());
   }

   private boolean check() {
      double d0 = Feature.mc.mouse.getX();
      double d1 = Feature.mc.mouse.getY();
      boolean flagx = check4();
      if (flagx && !this.flag5) {
         ViewModelTransform viewmodeltransform = this.getViewModelTransformByDoubleDouble(d1, d0);
         if (viewmodeltransform != null) {
            this.viewModelTransform3 = viewmodeltransform;
            this.value2 = d0;
            this.value3 = d1;
            this.value4 = viewmodeltransform.value;
            this.value5 = viewmodeltransform.value2;
         }
      } else if (!flagx && this.flag5) {
         this.update3();
      }

      boolean flag1;
      label62: {
         this.flag5 = flagx;
         if (this.viewModelTransform3 == null) {
            ViewModelTransform viewmodeltransform1 = this.viewModelTransform;
            label45:
            if (!this.isDoubleDoubleViewModelTransform(d0, d1, viewmodeltransform1)) {
               if (!this.viewModel.getZerkalo().isFlag3()) {
                  ViewModelTransform viewmodeltransform2 = this.viewModelTransform2;
                  if (this.isDoubleDoubleViewModelTransform(d0, d1, viewmodeltransform2)) {
                     break label45;
                  }
               }

               flag1 = false;
               break label62;
            }
         }

         flag1 = true;
      }

      this.flag3 = flag1;
      if (this.viewModelTransform3 != null) {
         ViewModelTransform viewmodeltransform4 = this.viewModelTransform3;
         float f6 = this.value4 + (float)(d0 - this.value2) / this.viewModelTransform3.value6;
         float f2 = (float)this.viewModelTransform3.sliderSetting.getValue42();
         float f1 = (float)this.viewModelTransform3.sliderSetting.getValue3();
         float f = f6;
         viewmodeltransform4.value = getFloatByFloatFloatFloat(f, f2, f1);
         viewmodeltransform4 = this.viewModelTransform3;
         float f7 = this.value5 + (float)(d1 - this.value3) / this.viewModelTransform3.value7;
         float f5 = (float)this.viewModelTransform3.sliderSetting2.getValue42();
         float f4 = (float)this.viewModelTransform3.sliderSetting2.getValue3();
         float f3 = f7;
         viewmodeltransform4.value2 = getFloatByFloatFloatFloat(f3, f5, f4);
      }

      ViewModelTransform viewmodeltransform3 = this.viewModelTransform3 != null ? this.viewModelTransform3 : this.getViewModelTransformByDoubleDouble(d1, d0);
      if (viewmodeltransform3 == null) {
         return false;
      } else {
         this.flag4 = viewmodeltransform3 == this.viewModelTransform;
         return true;
      }
   }

   public float getFloatByBoolean(boolean flag) {
      return (flag ? this.viewModelTransform : this.viewModelTransform2).smoothFloat.getValue2();
   }

   public boolean check2() {
      return Feature.mc != null && Feature.mc.currentScreen instanceof ChatScreen;
   }

   public void update() {
      this.update3();
      this.smoothFloat.setFloat(0.0F);
      this.value = -1;
      this.flag3 = false;
      this.interpolation.setTime();
      this.viewModelTransform.update2();
      this.viewModelTransform2.update2();
   }

   public static float getFloat() {
      return -0.72F;
   }

   public float getFloat2() {
      return this.smoothFloat.getValue2();
   }

   public void update2() {
      if (this.viewModelTransform3 == null) {
         this.value = -1;
         if (this.flag3 && Feature.mc != null && Feature.mc.getWindow() != null) {
            Framebuffer framebuffer = PlayerOutlineEffect.getSimpleFramebufferAsFramebuffer();
            if (framebuffer != null) {
               int i = (int)Feature.mc.mouse.getX();
               int j = framebuffer.textureHeight - 1 - (int)Feature.mc.mouse.getY();
               if (i >= 0 && j >= 0 && i < framebuffer.textureWidth && j < framebuffer.textureHeight) {
                  this.value = PixelReader.getIntByIntIntInt(framebuffer.fbo, i, j);
                  this.flag6 = this.check5();
                  this.flag7 = this.flag4;
               }
            }
         }
      }
   }

   public float getFloatByBoolean2(boolean flag) {
      return (flag ? this.viewModelTransform : this.viewModelTransform2).smoothFloat2.getValue2();
   }

   public boolean check3() {
      return this.flag3 || this.check5();
   }

   private static boolean check4() {
      return Feature.mc != null && Feature.mc.getWindow() != null ? GLFW.glfwGetMouseButton(Feature.mc.getWindow().getHandle(), 0) == 1 : false;
   }

   public boolean check5() {
      return this.smoothFloat.getValue2() > 0.004F;
   }

   private void update3() {
      if (this.viewModelTransform3 != null) {
         this.viewModelTransform3.update();
         this.viewModelTransform3 = null;
      }

      this.flag5 = false;
      flag = false;
   }

   public float getFloatByBoolean3(boolean flag) {
      return (flag ? this.viewModelTransform : this.viewModelTransform2).smoothFloat3.getValue2();
   }

   private ViewModelTransform getViewModelTransformByDoubleDouble(double value2, double value3) {
      if (this.value < 10) {
         return null;
      } else if (this.flag6) {
         return this.flag7 ? this.viewModelTransform : this.viewModelTransform2;
      } else {
         boolean flagx = this.viewModelTransform.time >= this.time - 1L;
         boolean flag1 = this.viewModelTransform2.time >= this.time - 1L && !this.viewModel.getZerkalo().isFlag3();
         if (flagx && flag1) {
            ViewModelTransform viewmodeltransform = this.viewModelTransform;
            double d0 = getDoubleByDoubleDoubleViewModelTransform(value2, value3, viewmodeltransform);
            ViewModelTransform viewmodeltransform1 = this.viewModelTransform2;
            return d0 <= getDoubleByDoubleDoubleViewModelTransform(value2, value3, viewmodeltransform1) ? this.viewModelTransform : this.viewModelTransform2;
         } else if (flagx) {
            return this.viewModelTransform;
         } else {
            return flag1 ? this.viewModelTransform2 : null;
         }
      }
   }

   private static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      return value < value3 ? value3 : Math.min(value, value2);
   }

   private boolean isDoubleDoubleViewModelTransform(double value, double value2, ViewModelTransform viewModelTransform) {
      if (viewModelTransform.time < this.time - 1L) {
         return false;
      } else {
         float f = viewModelTransform.smoothFloat3.getValue2();
         return Math.abs(value - viewModelTransform.value4) <= Math.abs(viewModelTransform.value6) * 0.42F * f && Math.abs(value2 - viewModelTransform.value5) <= Math.abs(viewModelTransform.value7) * 0.32F * f;
      }
   }

   private boolean isFloatMatrix4fFloatFloatArrayFloat(float value, Matrix4f matrix4f, float value2, float[] valueArray, float value3) {
      Vector4f vector4fx = this.vector4f.set(value3, value2, value, 1.0F);
      matrix4f.transform(vector4fx);
      if (vector4fx.w <= 1.0E-4F) {
         return false;
      } else {
         float f = 1.0F / vector4fx.w;
         valueArray[0] = (vector4fx.x * f * 0.5F + 0.5F) * Feature.mc.getWindow().getWidth();
         valueArray[1] = (0.5F - vector4fx.y * f * 0.5F) * Feature.mc.getWindow().getHeight();
         return true;
      }
   }

   private static double getDoubleByDoubleDoubleViewModelTransform(double value, double value2, ViewModelTransform viewModelTransform) {
      double d0 = value2 - viewModelTransform.value4;
      double d1 = value - viewModelTransform.value5;
      return d0 * d0 + d1 * d1;
   }

   public static float getFloat3() {
      return -0.52F;
   }

   public void update4() {
      this.time++;
      float f = this.interpolation.getFloat2();
      this.flag2 = this.check2();
      boolean flagx = this.flag2 && this.check();
      if (!this.flag2) {
         this.update3();
         this.flag3 = false;
      }

      flag = this.viewModelTransform3 != null;
      float f3 = flagx ? 1.0F : 0.0F;
      float f2 = 0.05F;
      float f1 = f3;
      this.smoothFloat.getFloatByFloatFloatFloat(f2, f1, f);
      this.viewModelTransform.onBooleanFloat(this.viewModelTransform3 == this.viewModelTransform, f);
      this.viewModelTransform2.onBooleanFloat(this.viewModelTransform3 == this.viewModelTransform2, f);
   }

   public void onMatrix4fFloatFloatBoolean(Matrix4f matrix4f2, float value, float value2, boolean flag) {
      if (this.flag2) {
         ViewModelTransform viewmodeltransform = flag ? this.viewModelTransform : this.viewModelTransform2;
         if (viewmodeltransform.time != this.time && Feature.mc != null && Feature.mc.getWindow() != null) {
            Matrix4f matrix4f = viewmodeltransform.matrix4f.set(RenderSystem.getProjectionMatrix()).mul(RenderSystem.getModelViewMatrix()).mul(matrix4f2);
            float f = viewmodeltransform.smoothFloat3.getValue2();
            float f1 = getFloatByBoolean4(flag);
            float f2 = value2 + f1 + f * ((flag ? 0.5F : -0.5F) - f1);
            float f3 = value + -0.52F + f * 0.21999997F;
            float[] afloat = viewmodeltransform.floatArray;
            float f6 = -0.72F;
            if (this.isFloatMatrix4fFloatFloatArrayFloat(f6, matrix4f, f3, afloat, f2)) {
               float f11 = f2 + 1.0F;
               float[] afloat1 = viewmodeltransform.floatArray2;
               float f8 = -0.72F;
               float f7 = f11;
               if (this.isFloatMatrix4fFloatFloatArrayFloat(f8, matrix4f, f3, afloat1, f7)) {
                  float f4 = viewmodeltransform.floatArray2[0] - viewmodeltransform.floatArray[0];
                  float f12 = f3 + 1.0F;
                  float[] afloat2 = viewmodeltransform.floatArray2;
                  float f10 = -0.72F;
                  float f9 = f12;
                  if (this.isFloatMatrix4fFloatFloatArrayFloat(f10, matrix4f, f9, afloat2, f2)) {
                     float f5 = viewmodeltransform.floatArray2[1] - viewmodeltransform.floatArray[1];
                     if (!(Math.abs(f4) < 0.001F) && !(Math.abs(f5) < 0.001F)) {
                        viewmodeltransform.value4 = viewmodeltransform.floatArray[0];
                        viewmodeltransform.value5 = viewmodeltransform.floatArray[1];
                        viewmodeltransform.value6 = f4;
                        viewmodeltransform.value7 = f5;
                        viewmodeltransform.time = this.time;
                     }
                  }
               }
            }
         }
      }
   }

   public boolean isDouble(double value) {
      if (this.viewModelTransform3 == null) {
         return false;
      } else {
         ViewModelTransform viewmodeltransform = this.viewModelTransform3;
         float f3 = this.viewModelTransform3.value3 + (float)value * 0.06F;
         float f2 = (float)this.viewModelTransform3.sliderSetting3.getValue42();
         float f1 = (float)this.viewModelTransform3.sliderSetting3.getValue3();
         float f = f3;
         viewmodeltransform.value3 = getFloatByFloatFloatFloat(f, f2, f1);
         return true;
      }
   }

   public static float getFloatByBoolean4(boolean flag) {
      return flag ? 0.56F : -0.56F;
   }

   public boolean isBoolean(boolean flag) {
      return this.flag4 == flag;
   }

   public static boolean isFlag() {
      return flag;
   }
}
