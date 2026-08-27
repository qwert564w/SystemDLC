package client.gui.widget;

import client.api.Theme;
import client.audio.SoundEngine;
import client.data.AnimatedInt;
import client.data.Tween;
import client.enums.SoundEvent;
import client.module.CategoryType;
import client.render.SvgShader;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class FavoriteButton extends LabelWidget {
   private boolean flag4;
   private Runnable runnable;
   private final Tween tween4;

   public FavoriteButton(boolean flag) {
      this.flag4 = flag;
      this.value237 = 16.0F;
      this.value238 = 16.0F;
      this.tween4 = EasingPresets.getTweenByFloatFloat2(flag ? 1.0F : 0.0F, 0.15F);
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.tween4.setFloat2(this.flag4 ? 1.0F : 0.0F);
      float f = this.tween4.getFloat();
      int l = Theme.mutedFg();
      int k = Theme.primary();
      int j = l;
      int i = AnimatedInt.getIntByIntFloatInt(k, f, j);
      if (f < 0.999F) {
         float f5 = value * (1.0F - f);
         float f4 = this.value238;
         float f3 = this.value237;
         float f2 = this.value236;
         float f1 = this.value235;
         CategoryType categorytype = CategoryType.STAR;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f5, i, matrix4f, f2, categorytype, f4, f1, f3);
      }

      if (f > 0.001F) {
         float f10 = value * f;
         float f9 = this.value238;
         float f8 = this.value237;
         float f7 = this.value236;
         float f6 = this.value235;
         CategoryType categorytype1 = CategoryType.STAR_FILLED;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f10, i, matrix4f, f7, categorytype1, f9, f6, f8);
      }
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   @Override
   protected boolean isDoubleDouble2(double value, double value2) {
      this.flag4 = !this.flag4;
      if (this.flag4) {
         SoundEngine.getInstance().onSoundEvent(SoundEvent.FAVOURITE_ADD);
      }

      if (this.runnable != null) {
         this.runnable.run();
      }

      return true;
   }
}
