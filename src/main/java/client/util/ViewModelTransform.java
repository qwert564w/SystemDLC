package client.util;

import client.setting.SliderSetting;
import org.joml.Matrix4f;

public final class ViewModelTransform {
   public final SliderSetting sliderSetting;
   public final SliderSetting sliderSetting2;
   public final SliderSetting sliderSetting3;
   public final Matrix4f matrix4f = new Matrix4f();
   public final float[] floatArray = new float[2];
   public final float[] floatArray2 = new float[2];
   public final SmoothFloat smoothFloat = new SmoothFloat(0.0F);
   public final SmoothFloat smoothFloat2 = new SmoothFloat(0.0F);
   public final SmoothFloat smoothFloat3 = new SmoothFloat(1.0F);
   public float value;
   public float value2;
   public float value3 = 1.0F;
   public float value4;
   public float value5;
   public float value6;
   public float value7;
   public long time = Long.MIN_VALUE;

   ViewModelTransform(SliderSetting sliderSetting4, SliderSetting sliderSetting5, SliderSetting sliderSetting6) {
      this.sliderSetting = sliderSetting4;
      this.sliderSetting2 = sliderSetting5;
      this.sliderSetting3 = sliderSetting6;
   }

   public void update() {
      this.sliderSetting.setDouble2(this.value);
      this.sliderSetting2.setDouble2(this.value2);
      this.sliderSetting3.setDouble2(this.value3);
   }

   public void update2() {
      this.value = this.sliderSetting.getValueAsFloat();
      this.value2 = this.sliderSetting2.getValueAsFloat();
      this.value3 = this.sliderSetting3.getValueAsFloat();
      this.smoothFloat.setFloat(this.value);
      this.smoothFloat2.setFloat(this.value2);
      this.smoothFloat3.setFloat(this.value3);
   }

   public void onBooleanFloat(boolean flag, float value4) {
      if (!flag) {
         this.value = this.sliderSetting.getValueAsFloat();
         this.value2 = this.sliderSetting2.getValueAsFloat();
         this.value3 = this.sliderSetting3.getValueAsFloat();
      }

      float f1 = 0.05F;
      float f = this.value;
      this.smoothFloat.getFloatByFloatFloatFloat(f1, f, value4);
      float f3 = 0.05F;
      float f2 = this.value2;
      this.smoothFloat2.getFloatByFloatFloatFloat(f3, f2, value4);
      float f5 = 0.05F;
      float f4 = this.value3;
      this.smoothFloat3.getFloatByFloatFloatFloat(f5, f4, value4);
   }
}
