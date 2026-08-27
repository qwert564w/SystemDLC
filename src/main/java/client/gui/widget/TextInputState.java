package client.gui.widget;

import client.api.Theme;
import client.module.Feature;
import client.render.ShapeShader;
import client.render.TextShader;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class TextInputState {
   private final StringBuilder stringBuilder = new StringBuilder();
   private final float value;
   private boolean flag;
   private boolean flag2;
   private int value2;
   private int value3;
   private Consumer<String> consumer;
   private Runnable runnable;

   public TextInputState(float value2) {
      this.value = value2;
   }

   private String getString() {
      int i = Math.min(this.value2, this.value3);
      int j = Math.max(this.value2, this.value3);
      return i == j ? "" : this.stringBuilder.substring(i, j);
   }

   public int getInt() {
      return this.stringBuilder.length();
   }

   private int getIntByInt(int count) {
      int i = count;
      int j = this.stringBuilder.length();

      while (i < j && !Character.isWhitespace(this.stringBuilder.charAt(i))) {
         i++;
      }

      while (i < j && Character.isWhitespace(this.stringBuilder.charAt(i))) {
         i++;
      }

      return i;
   }

   public boolean check() {
      return this.value2 != this.value3;
   }

   public TextInputState getTextInputStateByBoolean(boolean flag) {
      this.flag2 = flag;
      return this;
   }

   private void update() {
      int i = Math.min(this.value2, this.value3);
      int j = Math.max(this.value2, this.value3);
      if (i != j) {
         this.stringBuilder.delete(i, j);
         this.value2 = i;
         this.value3 = i;
      }
   }

   public void setString(String text) {
      if (this.value3 != this.value2) {
         this.update();
      }

      this.stringBuilder.insert(this.value2, text);
      this.value2 = this.value2 + text.length();
      this.value3 = this.value2;
      this.update2();
   }

   private void update2() {
      if (this.value2 > this.stringBuilder.length()) {
         this.value2 = this.stringBuilder.length();
      }

      if (this.value3 > this.stringBuilder.length()) {
         this.value3 = this.stringBuilder.length();
      }

      if (this.consumer != null) {
         this.consumer.accept(this.stringBuilder.toString());
      }
   }

   public int getValue2() {
      return this.value2;
   }

   public boolean isChar(char symbol) {
      if (symbol >= ' ' && symbol != 127) {
         this.setString(String.valueOf(symbol));
         return true;
      } else {
         return false;
      }
   }

   public static int getInt2() {
      return Theme.primary() & 16777215 | 1426063360;
   }

   public void onFloatIntMatrix4fFloatBooleanFloatFloat(float value4, int count, Matrix4f matrix4f, float value5, boolean flag3, float value6, float value7) {
      String s = this.stringBuilder.toString();
      int i = this.getIntByFloat(value7);
      String s1 = s.substring(i);
      if (this.flag) {
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, s1, value6, value4, this.value, count, value5);
      } else if (this.flag2) {
         float f2 = this.value;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value4, value6, count, f2, value5, s1, matrix4f);
      } else {
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s1, value6, value4, this.value, count, value5);
      }

      int j = Math.min(this.value2, this.value3);
      int k = Math.max(this.value2, this.value3);
      if (j != k) {
         int l = Math.max(j, i);
         int i1 = Math.max(k, i);
         if (i1 > l) {
            float f = value6 + TextShader.getFloatByStringFloat(s.substring(i, l), this.value);
            float f1 = TextShader.getFloatByStringFloat(s.substring(l, i1), this.value);
            float f10 = value4 - 1.0F;
            float f11 = this.value + 2.0F;
            int j1 = getInt2();
            float f5 = 0.0F;
            float f4 = f11;
            float f3 = f10;
            ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f5, f, j1, matrix4f, f4, f1, value5, f3);
         }
      }

      if (flag3) {
         float f8 = value6 + TextShader.getFloatByStringFloat(s.substring(i, Math.max(this.value2, i)), this.value);
         float f9 = this.value;
         int k1 = Theme.foreground();
         float f7 = f9;
         float f6 = 1.0F;
         ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value5, f7, f8, value4, f6, matrix4f, k1);
      }
   }

   private int getIntByInt2(int count) {
      int i = count;

      while (i > 0 && Character.isWhitespace(this.stringBuilder.charAt(i - 1))) {
         i--;
      }

      while (i > 0 && !Character.isWhitespace(this.stringBuilder.charAt(i - 1))) {
         i--;
      }

      return i;
   }

   public int getIntByFloat(float value3) {
      String s = this.stringBuilder.toString();
      int i = 0;

      while (i < this.value2 && TextShader.getFloatByStringFloat(s.substring(i, this.value2), this.value) > value3) {
         i++;
      }

      return i;
   }

   public void setString2(String text) {
      this.stringBuilder.setLength(0);
      if (text != null) {
         this.stringBuilder.append(text);
      }

      this.value2 = this.stringBuilder.length();
      this.value3 = this.value2;
      this.update2();
   }

   public boolean check2() {
      return this.stringBuilder.length() == 0;
   }

   public String getString2() {
      return this.stringBuilder.toString();
   }

   public TextInputState getTextInputStateByBoolean2(boolean flag2) {
      this.flag = flag2;
      return this;
   }

   public boolean isIntInt(int count, int count2) {
      boolean flagx = (count & 2) != 0;
      boolean flag1 = (count & 1) != 0;
      if (count2 != 257 && count2 != 335) {
         if (flagx && count2 == 65) {
            this.value3 = 0;
            this.value2 = this.stringBuilder.length();
            return true;
         } else if (flagx && count2 == 67) {
            String s2 = this.getString();
            Feature.mc.keyboard.setClipboard(s2.isEmpty() ? this.stringBuilder.toString() : s2);
            return true;
         } else if (flagx && count2 == 88) {
            String s1 = this.getString();
            if (!s1.isEmpty()) {
               Feature.mc.keyboard.setClipboard(s1);
               this.update();
               this.update2();
            }

            return true;
         } else if (flagx && count2 == 86) {
            String s = Feature.mc.keyboard.getClipboard();
            if (s != null && !s.isEmpty()) {
               StringBuilder stringbuilder = new StringBuilder();

               for (int j = 0; j < s.length(); j++) {
                  char c0 = s.charAt(j);
                  if (c0 >= ' ' && c0 != 127) {
                     stringbuilder.append(c0);
                  }
               }

               if (!stringbuilder.isEmpty()) {
                  this.setString(stringbuilder.toString());
               }
            }

            return true;
         } else if (count2 == 263) {
            int i1 = flagx ? this.getIntByInt2(this.value2) : Math.max(0, this.value2 - 1);
            if (flag1) {
               this.value2 = i1;
            } else if (this.value3 != this.value2) {
               this.value2 = Math.min(this.value2, this.value3);
            } else {
               this.value2 = i1;
            }

            if (!flag1) {
               this.value3 = this.value2;
            }

            return true;
         } else if (count2 == 262) {
            int l = flagx ? this.getIntByInt(this.value2) : Math.min(this.stringBuilder.length(), this.value2 + 1);
            if (flag1) {
               this.value2 = l;
            } else if (this.value3 != this.value2) {
               this.value2 = Math.max(this.value2, this.value3);
            } else {
               this.value2 = l;
            }

            if (!flag1) {
               this.value3 = this.value2;
            }

            return true;
         } else if (count2 == 268) {
            this.value2 = 0;
            if (!flag1) {
               this.value3 = this.value2;
            }

            return true;
         } else if (count2 == 269) {
            this.value2 = this.stringBuilder.length();
            if (!flag1) {
               this.value3 = this.value2;
            }

            return true;
         } else if (count2 == 259) {
            if (this.value3 != this.value2) {
               this.update();
            } else {
               if (this.value2 <= 0) {
                  return true;
               }

               int k = flagx ? this.getIntByInt2(this.value2) : this.value2 - 1;
               this.stringBuilder.delete(k, this.value2);
               this.value2 = k;
               this.value3 = this.value2;
            }

            this.update2();
            return true;
         } else if (count2 == 261) {
            if (this.value3 != this.value2) {
               this.update();
            } else {
               if (this.value2 >= this.stringBuilder.length()) {
                  return true;
               }

               int i = flagx ? this.getIntByInt(this.value2) : this.value2 + 1;
               this.stringBuilder.delete(this.value2, i);
            }

            this.update2();
            return true;
         } else {
            return false;
         }
      } else {
         if (this.runnable != null) {
            this.runnable.run();
         }

         return true;
      }
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   public void setConsumer(Consumer<String> consumer2) {
      this.consumer = consumer2;
   }

   public void update3() {
      this.stringBuilder.setLength(0);
      this.value2 = 0;
      this.value3 = 0;
      this.update2();
   }
}
