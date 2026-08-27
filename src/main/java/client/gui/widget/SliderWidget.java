package client.gui.widget;

import client.api.TextDrawCall;
import org.joml.Matrix4f;

public abstract class SliderWidget extends Widget {
   protected final String text;
   protected float value239;

   protected SliderWidget(String text2, float value, float value2) {
      this.text = text2;
      this.value237 = value;
      this.value238 = value2;
   }

   public String getText() {
      return this.text;
   }

   protected final void onFloatMatrix4fFloatIntFloatTextDrawCallFloatFloatFloatFloat(
      float value, Matrix4f matrix4f, float value2, int count, float value3, TextDrawCall textDrawCall, float value4, float value5, float value6, float value7
   ) {
      if (!(value <= 0.0F) && !(value2 <= 0.001F)) {
         ScissorStack.onFloatFloatFloatFloat(value, value6, value7, value3);
         textDrawCall.draw(matrix4f, this.text, value3, value4, value5, count, value2);
         ScissorStack.update();
      }
   }

   public float getValue239() {
      return this.value239;
   }

   public void setValue239(float value) {
      this.value239 = value;
   }
}
