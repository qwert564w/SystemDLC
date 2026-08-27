package client.gui.widget;

import client.api.Theme;
import client.render.ShapeShader;
import org.joml.Matrix4f;

public abstract class FormWidget extends Widget {
   protected void update3() {
   }

   @Override
   public final void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.update();
      this.update3();
      float f6 = this.value235;
      float f7 = this.value236;
      float f8 = this.value237;
      float f9 = this.value238;
      float f10 = this.getFloat();
      int k = this.getInt();
      int l = Theme.border();
      float f5 = 1.0F;
      int j = l;
      int i = k;
      float f4 = f10;
      float f3 = f9;
      float f2 = f8;
      float f1 = f7;
      float f = f6;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f5, f2, value, matrix4f, f1, i, f4, f, j, f3);
      this.onFloatMatrix4fFloatFloat(value3, matrix4f, value2, value);
   }

   @Override
   public void onFloatFloat2(float value, float value2) {
      this.onFloatFloat3(value2, value);
   }

   protected abstract void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3);

   protected int getInt() {
      return Theme.surface();
   }

   protected abstract float getFloat();

   public void onFloatFloat4(float value, float value2) {
      this.onFloatFloat(value2, value);
   }
}
