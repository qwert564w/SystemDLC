package client.gui.widget;

import client.api.Theme;
import client.audio.SoundEngine;
import client.data.AnimatedInt;
import client.data.Tween;
import client.enums.SoundEvent;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class SmallButton extends LabelWidget {
   private boolean flag4;
   private Runnable runnable;
   private final Tween tween4 = EasingPresets.getTweenByFloat(0.0F);
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.22F);

   public SmallButton(boolean flag) {
      this.flag4 = flag;
      this.value237 = 16.0F;
      this.value238 = 16.0F;
      this.tween4.setFloat(flag ? 1.0F : 0.0F);
      this.tween5.setFloat(flag ? 1.0F : 0.0F);
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.tween5.getFloat();
      int i2 = Theme.background();
      int l = Theme.primary();
      int k = i2;
      int i = AnimatedInt.getIntByIntFloatInt(l, f, k);
      i2 = Theme.border();
      int j1 = Theme.primary();
      int i1 = i2;
      int j = AnimatedInt.getIntByIntFloatInt(j1, f, i1);
      float f1 = this.tween4.getFloat();
      float f17 = 1.0F;
      float f16 = 1.0F;
      float f15 = 0.0F;
      int k1 = 436207616;
      float f14 = 1.0F;
      float f13 = 6.0F;
      float f12 = 6.0F;
      float f11 = 6.0F;
      float f10 = 6.0F;
      float f9 = this.value238;
      float f8 = this.value237;
      float f7 = this.value236;
      float f6 = this.value235;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f8, k1, i, f6, f7, f17, f10, f9, f16, matrix4f, value, j, f12, f14, f11, f13, f15
      );
      if (f1 > 0.001F) {
         float f2 = 7.0F;
         float f3 = 6.0F;
         float f4 = this.value235 + (this.value237 - f2) / 2.0F;
         float f5 = this.value236 + (this.value238 - f3) / 2.0F;
         CategoryType categorytype1 = CategoryType.SUCCESS_CHECKBOX;
         int j2 = Theme.surface();
         float f18 = value * f1;
         int l1 = j2;
         CategoryType categorytype = categorytype1;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f18, l1, matrix4f, f5, categorytype, f3, f4, f2);
      }
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   @Override
   protected boolean isDoubleDouble2(double value, double value2) {
      this.setBoolean(!this.flag4);
      SoundEngine.getInstance().onSoundEvent(this.flag4 ? SoundEvent.TOGGLE_ON : SoundEvent.TOGGLE_OFF);
      if (this.runnable != null) {
         this.runnable.run();
      }

      return true;
   }

   public void setBoolean(boolean flag) {
      if (this.flag4 != flag) {
         this.flag4 = flag;
         this.tween4.setFloat2(flag ? 1.0F : 0.0F);
         this.tween5.setFloat2(flag ? 1.0F : 0.0F);
      }
   }
}
