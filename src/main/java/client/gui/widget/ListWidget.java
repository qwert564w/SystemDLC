package client.gui.widget;

import client.api.Theme;
import client.data.TextTrimmer;
import client.data.Tween;
import client.render.ShapeShader;
import client.render.TextShader;
import client.util.DateUtil;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public abstract class ListWidget extends Widget {
   protected final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.22F);
   private final List<TextInputController> list = new ArrayList<>();
   private boolean flag4;

   public void update3() {
      this.flag4 = true;
      this.tween4.setFloat2(0.0F);
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   @Override
   public final void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.update();
      float f = value * this.tween4.getFloat();
      if (!(f <= 0.0F)) {
         this.onFloatMatrix4fFloatFloat(f, matrix4f, value2, value3);
      }
   }

   @Override
   public void onFloatFloat2(float value, float value2) {
      if (this.tween4.getValue3() == 0.0F && this.tween4.getValue4() == 0.0F) {
         this.tween4.setFloat2(1.0F);
      }

      this.onFloatFloat3(value2, value);
   }

   public boolean check() {
      for (TextInputController textinputcontroller : this.list) {
         if (textinputcontroller.isFlag()) {
            return true;
         }
      }

      return false;
   }

   protected void update4() {
      for (TextInputController textinputcontroller : this.list) {
         textinputcontroller.update2();
      }
   }

   protected void onFloatMatrix4fFloat(float value, Matrix4f matrix4f, float value2) {
      float f5 = this.value235;
      float f6 = this.value236;
      float f7 = this.value237;
      float f8 = this.value238;
      int k = Theme.surface();
      int l = Theme.border();
      float f4 = 1.0F;
      int j = l;
      int i = k;
      float f3 = f8;
      float f2 = f7;
      float f1 = f6;
      float f = f5;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f4, f2, value2, matrix4f, f1, i, value, f, j, f3);
   }

   protected void onStringTextInputController(String text, TextInputController textInputController) {
      for (TextInputController textinputcontroller : this.list) {
         if (textinputcontroller != textInputController) {
            textinputcontroller.update2();
         }
      }

      textInputController.setString(text);
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      for (TextInputController textinputcontroller : this.list) {
         if (textinputcontroller.isFlag()) {
            return textinputcontroller.isIntInt(count2, count3);
         }
      }

      return false;
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      for (TextInputController textinputcontroller : this.list) {
         if (textinputcontroller.isFlag()) {
            return textinputcontroller.isChar(symbol);
         }
      }

      return false;
   }

   protected void onMatrix4fFloatFloatStringFloatFloat(Matrix4f matrix4f, float value, float value2, String text, float value3, float value4) {
      if (!(value2 <= 0.0F)) {
         String s = DateUtil.getStringByString2(text);
         if (s != null) {
            String s3 = "Created: " + s;
            float f = 12.0F;
            String s2 = s3;
            String s1 = TextTrimmer.getStringByFloatStringFloat2(value2, s2, f);
            TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s1, value3, value, 12.0F, Theme.mutedFg(), value4);
         }
      }
   }

   public void onFloatFloat4(float value, float value2) {
      if (this.tween4.getValue3() == 0.0F && this.tween4.getValue4() == 0.0F) {
         this.tween4.setFloat2(1.0F);
      }

      this.onFloatFloat(value, value2);
   }

   protected abstract void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3);

   protected final TextInputController getTextInputControllerByConsumer(Consumer consumer) {
      boolean flag = false;
      float f = 14.0F;
      return this.getTextInputControllerByBooleanFloatConsumer(flag, f, consumer);
   }

   public void setFloat(float value) {
      this.value237 = value;
   }

   protected final TextInputController getTextInputControllerByBooleanFloatConsumer(boolean flag, float value, Consumer consumer) {
      TextInputController textinputcontroller = new TextInputController(value).getTextInputControllerByConsumer(consumer);
      textinputcontroller.getTextInputState().getTextInputStateByBoolean(flag);
      this.list.add(textinputcontroller);
      return textinputcontroller;
   }

   public boolean check2() {
      return this.flag4 && this.tween4.getValue3() <= 0.001F;
   }
}
