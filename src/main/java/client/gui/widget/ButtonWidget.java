package client.gui.widget;

import client.api.ColorSupplier;
import client.data.Tween;
import client.util.EasingPresets;
import client.util.Easings;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;

public abstract class ButtonWidget extends Widget {
   private static final float value239 = 0.38F;
   private static final float value240 = 0.26F;
   protected final Tween tween4 = new Tween(0.0F, 0.38F).getTweenByFunction(Easings::getFloatByFloat9);
   private boolean flag4;
   private boolean flag5 = true;
   private boolean flag6 = true;
   private boolean flag7;
   private ColorSupplier colorSupplier;

   protected abstract void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f);

   public float getFloat() {
      return 8.0F;
   }

   public void update3() {
      this.flag4 = false;
      this.flag7 = true;
      this.update10();
      this.tween4.setFloat2(1.0F);
   }

   public boolean isFlag7() {
      return this.flag7;
   }

   public void setFlag5(boolean flag) {
      this.flag5 = flag;
   }

   public void setColorSupplier(ColorSupplier colorSupplier2) {
      this.colorSupplier = colorSupplier2;
      this.update9();
      this.update3();
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.flag4) {
         return true;
      } else {
         this.update9();
         if (count == 0 && this.flag5 && !this.isDoubleDouble(value, value2)) {
            this.update4();
            return true;
         } else {
            return this.isIntDoubleDouble2(count, value, value2);
         }
      }
   }

   protected boolean isIntIntInt3(int count, int count2, int count3) {
      return false;
   }

   public boolean check() {
      return this.tween4.getValue3() > 0.001F || this.tween4.getValue4() > 0.001F;
   }

   @Override
   public final void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
   }

   public float getFloat2() {
      return this.tween4.getValue3();
   }

   public void update4() {
      if (!this.flag4) {
         this.flag4 = true;
         this.update6();
         this.tween4.setFloat2(0.0F);
         this.update7();
      }
   }

   public void update5() {
      this.flag4 = false;
      this.flag7 = false;
      this.update10();
      this.tween4.setFloat2(1.0F);
      WidgetState.setWidget2(this);
   }

   public float getFloat3() {
      return EasingPresets.getFloatByFloat(this.tween4.getValue3());
   }

   protected final boolean isFlag4() {
      return this.flag4;
   }

   public float getFloat4() {
      return this.tween4.getValue3();
   }

   @Override
   public final void onFloatFloatFloatMatrix4f2(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.tween4.getFloat();
      if (f <= 0.001F) {
         if (this.flag4) {
            this.update8();
            this.colorSupplier = null;
            if (!this.flag7) {
               WidgetState.setWidget(this);
            }
         }
      } else {
         this.update9();
         RenderSystem.disableDepthTest();

         try {
            float f1 = value3 * f;
            this.onFloatFloatFloatMatrix4f3(value, f1, value2, matrix4f);
         } finally {
            RenderSystem.enableDepthTest();
         }
      }
   }

   protected void setFlag6(boolean flag) {
      this.flag6 = flag;
   }

   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      return false;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (this.flag6 && count3 == 256) {
         this.update4();
         return true;
      } else {
         return this.isIntIntInt3(count2, count, count3);
      }
   }

   public void setColorSupplier2(ColorSupplier colorSupplier2) {
      this.colorSupplier = colorSupplier2;
      this.update9();
      this.update5();
   }

   protected void setFlag52(boolean flag) {
      this.flag5 = flag;
   }

   private void update6() {
      this.tween4.setValue5(0.26F);
      this.tween4.getTweenByFunction(Easings::getFloatByFloat3);
   }

   protected void update7() {
   }

   protected void update8() {
   }

   @Override
   public void update2() {
      this.flag4 = false;
      this.tween4.setFloat(0.0F);
   }

   protected final void update9() {
      if (this.colorSupplier != null) {
         float[] afloat = this.colorSupplier.get();
         if (afloat != null && afloat.length >= 2) {
            this.value235 = afloat[0];
            this.value236 = afloat[1];
         }
      }
   }

   public float getFloat5() {
      return EasingPresets.getFloatByFloat2(this.tween4.getValue3());
   }

   private void update10() {
      this.tween4.setValue5(0.38F);
      this.tween4.getTweenByFunction(Easings::getFloatByFloat9);
   }
}
