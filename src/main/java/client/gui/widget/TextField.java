package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.data.AnimatedInt;
import client.data.Tween;
import client.render.ShapeShader;
import client.render.TextShader;
import client.util.EasingPresets;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class TextField extends Widget {
   private final TextInputState textInputState;
   private final String text;
   private final float value239;
   private final float value240;
   private final float value241;
   private boolean flag4;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private Consumer<String> consumer;

   public TextField(float value, float value2, String text2, float value3, float value4, float value5) {
      this.value237 = value;
      this.value238 = value2;
      this.text = text2;
      this.value240 = value3;
      this.value239 = value4;
      this.value241 = value5;
      this.textInputState = new TextInputState(value3);
      this.textInputState.setConsumer(var1x -> {
         if (this.consumer != null) {
            this.consumer.accept(var1x);
         }
      });
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else {
         boolean flag = this.isDoubleDouble(value, value2);
         this.setBoolean(flag);
         return flag;
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.tween4.getFloat();
      int k1 = Theme.border();
      int k = Theme.primary();
      int j = k1;
      int i = AnimatedInt.getIntByIntFloatInt(k, f, j);
      float f20 = this.value235;
      float f21 = this.value236;
      float f22 = this.value237;
      float f23 = this.value238;
      int i2 = Theme.background();
      float f14 = 1.0F;
      float f13 = 1.0F;
      float f12 = 0.0F;
      int i1 = 436207616;
      float f11 = 1.0F;
      int l = i2;
      float f10 = 8.0F;
      float f9 = 8.0F;
      float f8 = 8.0F;
      float f7 = 8.0F;
      float f6 = f23;
      float f5 = f22;
      float f4 = f21;
      float f3 = f20;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f5, i1, l, f3, f4, f14, f7, f6, f13, matrix4f, value, i, f9, f11, f8, f10, f12
      );
      float f1 = this.value236 + (this.value238 - this.value240) / 2.0F;
      float f2 = this.value237 - this.value239 * 2.0F - this.value241;
      float f19 = this.value235 + this.value239;
      float f17 = this.value238;
      float f16 = this.value236;
      float f15 = f19;
      ScissorStack.onFloatFloatFloatFloat(f2, f17, f16, f15);
      if (this.textInputState.check2() && !this.flag4) {
         String s = Translations.getInstance().getStringByString2(this.text);
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s, this.value235 + this.value239, f1, this.value240, Theme.mutedFg(), value);
      } else {
         TextInputState textinputstate = this.textInputState;
         f21 = this.value235 + this.value239;
         int l1 = Theme.foreground();
         boolean flag = this.flag4;
         int j1 = l1;
         float f18 = f21;
         textinputstate.onFloatIntMatrix4fFloatBooleanFloatFloat(f1, j1, matrix4f, value, flag, f18, f2);
      }

      ScissorStack.update();
   }

   public String getString() {
      return this.textInputState.getString2();
   }

   public float getValue240() {
      return this.value240;
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      return !this.flag4 ? false : this.textInputState.isChar(symbol);
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   public void setConsumer(Consumer<String> consumer2) {
      this.consumer = consumer2;
   }

   public void update3() {
      this.textInputState.update3();
   }

   public void onRunnable(Runnable runnable) {
      this.textInputState.setRunnable(runnable);
   }

   public void setBoolean(boolean flag) {
      if (this.flag4 != flag) {
         this.flag4 = flag;
         this.tween4.setFloat2(flag ? 1.0F : 0.0F);
      }
   }

   public void onString2(String text) {
      this.textInputState.setString2(text);
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (!this.flag4) {
         return false;
      } else if (count3 == 256) {
         this.setBoolean(false);
         return true;
      } else {
         return this.textInputState.isIntInt(count2, count3);
      }
   }
}
