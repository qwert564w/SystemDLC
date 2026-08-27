package client.gui.widget;

import client.setting.BooleanSetting;
import org.joml.Matrix4f;

public final class BooleanField extends SettingField {
   private final BooleanSetting booleanSetting;
   public final ToggleButton toggleButton;

   BooleanField(BooleanSetting booleanSetting2) {
      super(booleanSetting2);
      this.booleanSetting = booleanSetting2;
      this.toggleButton = new ToggleButton(booleanSetting2.isFlag3());
      this.toggleButton.setRunnable(() -> this.booleanSetting.setBoolean(this.toggleButton.isFlag4()));
   }

   @Override
   public boolean isDoubleDoubleIntFloatFloatFloat(double value, double value2, int count, float value3, float value4, float value5) {
      if (count != 0) {
         return false;
      } else {
         byte b0 = 0;
         return this.toggleButton.isIntDoubleDouble(b0, value, value2);
      }
   }

   @Override
   public void onFloatFloatFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f, float value6) {
      this.toggleButton.setBoolean2(this.booleanSetting.isFlag3());
      float f2 = value4 + value5 - 8.0F - 28.0F;
      float f1 = value6 + 5.0F;
      float f = f2;
      this.toggleButton.onFloatFloat2(f1, f);
      this.toggleButton.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
   }
}
