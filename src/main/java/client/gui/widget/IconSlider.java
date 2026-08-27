package client.gui.widget;

import client.api.TextDrawCall;
import client.api.Theme;
import client.module.CategoryType;
import client.render.SvgShader;
import client.render.TextShader;
import org.joml.Matrix4f;

public class IconSlider extends SliderWidget {
   private final CategoryType categoryType;

   public IconSlider(CategoryType categoryType2, String text) {
      super(text, 224.0F, 49.0F);
      this.categoryType = categoryType2;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.value235 + 16.0F - 2.0F;
      float f1 = this.value236 + this.value238 - 15.0F - 20.0F;
      CategoryType categorytype1 = this.categoryType;
      int i = Theme.foreground();
      float f6 = 20.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatCategoryTypeIntMatrix4fFloatFloatFloat(f1, categorytype, i, matrix4f, value, f6, f);
      float f2 = f + 20.0F + 8.0F;
      float f3 = Math.max(0.0F, this.value235 + this.value237 - 8.0F - f2);
      float f4 = f1 + 2.0F;
      float f5 = value * (1.0F - this.value239);
      float f10 = this.value236;
      float f11 = this.value238;
      int k = Theme.primary();
      TextDrawCall textdrawcall = TextShader::onMatrix4fStringFloatFloatFloatIntFloat2;
      int j = k;
      float f9 = 16.0F;
      float f8 = f11;
      float f7 = f10;
      this.onFloatMatrix4fFloatIntFloatTextDrawCallFloatFloatFloatFloat(f3, matrix4f, f5, j, f2, textdrawcall, f4, f9, f8, f7);
   }
}
