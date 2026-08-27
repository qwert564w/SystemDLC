package client.gui.widget;

import client.module.CategoryType;
import client.setting.BooleanSetting;
import org.joml.Matrix4f;

public class BooleanRow extends PanelWidget {
   private final BooleanSetting booleanSetting;
   private final SmallButton smallButton;

   public BooleanRow(BooleanSetting booleanSetting2) {
      super(booleanSetting2);
      this.booleanSetting = booleanSetting2;
      this.smallButton = new SmallButton(booleanSetting2.isFlag3());
      this.smallButton.setRunnable(() -> this.booleanSetting.setBoolean(this.smallButton.isFlag4()));
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      return this.smallButton.isIntDoubleDouble(count, value, value2);
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.smallButton.setBoolean(this.booleanSetting.isFlag3());
      float f = this.value237 - 16.0F - 8.0F;
      float f1 = this.getFloatByFloatMatrix4fFloat(f, matrix4f, value);
      float f2 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f1, f);
      float f3 = this.value236 + -1.0F;
      float f4 = this.value235 + this.value237 - 16.0F;
      this.smallButton.onFloatFloat2(f3, f4);
      this.smallButton.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      this.value238 = Math.max(this.getFloatByFloatFloat3(f1, f2), 16.0F);
   }

   @Override
   protected float getFloat3() {
      return 11.0F;
   }

   @Override
   public float getFloat5() {
      float f = this.value237 - 16.0F - 8.0F;
      return Math.max(this.getFloatByFloatFloat3(this.getFloatByFloat2(f), this.getFloatByFloat(f)), 16.0F);
   }

   @Override
   protected float getFloat6() {
      return 12.0F;
   }

   @Override
   protected CategoryType getCategoryType() {
      return CategoryType.SETTING_CHECKBOX;
   }
}
