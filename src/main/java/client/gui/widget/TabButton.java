package client.gui.widget;

import client.api.TextDrawCall;
import client.api.Theme;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public abstract class TabButton extends SliderWidget {
   private static final float value240 = 0.82F;
   protected final CategoryType categoryType;
   private final Tween tween4 = EasingPresets.getTween();

   protected TabButton(CategoryType categoryType2, String text) {
      super(text, 208.0F, 32.0F);
      this.categoryType = categoryType2;
   }

   protected boolean check() {
      return false;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.flag = this.isDoubleDouble(value3, value2) && !WidgetState.check();
      this.tween4.setFloat2(this.check() ? 1.0F : (this.flag ? 0.82F : 0.0F));
      float f = this.tween4.getFloat();
      if (f > 0.001F) {
         float f18 = this.value235;
         float f19 = this.value236;
         float f20 = this.value237;
         float f21 = this.value238;
         int l = Theme.elevated();
         float f12 = value * f;
         int i = l;
         float f11 = 8.0F;
         float f10 = f21;
         float f9 = f20;
         float f8 = f19;
         float f7 = f18;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f11, f7, i, matrix4f, f10, f9, f12, f8);
      }

      float f1 = this.value235 + 16.0F - 8.0F;
      float f2 = this.value236 + (this.value238 - 16.0F) / 2.0F;
      CategoryType categorytype1 = this.categoryType;
      int j = Theme.foreground();
      float f13 = 16.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatCategoryTypeIntMatrix4fFloatFloatFloat(f2, categorytype, j, matrix4f, value, f13, f1);
      float f3 = f1 + 16.0F + 8.0F;
      float f4 = Math.max(0.0F, this.value235 + this.value237 - 8.0F - f3);
      float f5 = this.value236 + (this.value238 - 14.0F) / 2.0F;
      float f6 = value * (1.0F - this.value239);
      float f22 = this.value236;
      float f17 = this.value238;
      int i1 = Theme.foreground();
      TextDrawCall textdrawcall = TextShader::onMatrix4fStringFloatFloatFloatIntFloat;
      int k = i1;
      float f16 = 14.0F;
      float f15 = f17;
      float f14 = f22;
      this.onFloatMatrix4fFloatIntFloatTextDrawCallFloatFloatFloatFloat(f4, matrix4f, f6, k, f3, textdrawcall, f5, f16, f15, f14);
   }
}
