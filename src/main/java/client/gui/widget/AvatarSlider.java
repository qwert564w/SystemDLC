package client.gui.widget;

import client.api.TextDrawCall;
import client.api.Theme;
import client.render.TextShader;
import org.joml.Matrix4f;

public class AvatarSlider extends SliderWidget {
   public AvatarSlider(String text) {
      super(text, 208.0F, 32.0F);
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.value235 + 8.0F - 1.0F;
      float f1 = Math.max(0.0F, this.value235 + this.value237 - 8.0F - f);
      float f2 = this.value236 + (this.value238 - 12.0F) / 2.0F;
      float f3 = value * (1.0F - this.value239);
      float f7 = this.value236;
      float f8 = this.value238;
      int j = Theme.foreground();
      TextDrawCall textdrawcall = TextShader::onMatrix4fStringFloatFloatFloatIntFloat3;
      int i = j;
      float f6 = 12.0F;
      float f5 = f8;
      float f4 = f7;
      this.onFloatMatrix4fFloatIntFloatTextDrawCallFloatFloatFloatFloat(f1, matrix4f, f3, i, f, textdrawcall, f2, f6, f5, f4);
   }
}
