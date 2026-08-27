package client.gui.widget;

import client.data.Tween;
import client.enums.ScriptType;
import client.render.TextShader;
import client.util.Easings;
import org.joml.Matrix4f;

public final class TabPainter {
   private static final float value = 0.35F;
   private static final float value2 = 0.14285715F;
   private final Tween tween;
   private final ScriptType scriptType;
   private String text = "";
   private String text2 = "";
   private String text3 = "";
   private String text4 = "";
   private String text5 = "";
   private boolean flag;

   public TabPainter(ScriptType scriptType2, float value) {
      this.scriptType = scriptType2;
      this.tween = new Tween(1.0F, value).getTweenByFunction(Easings::getFloatByFloat3);
   }

   public TabPainter(ScriptType scriptType) {
      this(scriptType, 0.35F);
   }

   public TabPainter() {
      this(ScriptType.LATIN, 0.35F);
   }

   public void setString(String text6) {
      String s = text6 == null ? "" : text6;
      if (!s.equals(this.text)) {
         this.text2 = this.text;
         this.text = s;
         String s2 = this.text;
         String s1 = this.text2;
         int i = getIntByStringString(s2, s1);
         this.text3 = this.text.substring(0, i);
         this.text4 = this.text2.substring(i);
         this.text5 = this.text.substring(i);
         this.flag = !this.text4.isEmpty() || !this.text5.isEmpty();
         this.tween.setFloat(0.0F);
         this.tween.setFloat2(1.0F);
      }
   }

   private void onStringFloatIntFloatFloatFloatMatrix4f(String text, float value, int count, float value2, float value3, float value4, Matrix4f matrix4f) {
      if (!(value <= 0.001F)) {
         switch (this.scriptType) {
            case LATIN:
               TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, text, value3, value2, value4, count, value);
               break;
            case CYRILLIC:
               TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, text, value3, value2, value4, count, value);
         }
      }
   }

   public void onMatrix4fFloatIntFloatFloatFloat(Matrix4f matrix4f, float value, int count, float value2, float value3, float value4) {
      float f = this.tween.getFloat();
      if (!(value4 <= 0.001F)) {
         float f1 = 0.0F;
         if (!this.text3.isEmpty()) {
            String s = this.text3;
            this.onStringFloatIntFloatFloatFloatMatrix4f(s, value4, count, value2, value, value3, matrix4f);
            String s1 = this.text3;
            f1 = this.getFloatByFloatString(value3, s1);
         }

         float f2 = value + f1;
         if (this.flag && f < 0.14285715F) {
            float f6 = f / 0.14285715F;
            float f7 = value4 * (1.0F - f6);
            String s2 = this.text4;
            this.onFloatFloatFloatFloatFloatStringIntMatrix4f(value2, f7, value3, f2, f6, s2, count, matrix4f);
         } else if (!this.text5.isEmpty()) {
            float f3 = this.flag ? (f - 0.14285715F) / 0.85714287F : f;
            float f4 = value4 * f3;
            float f5 = 1.0F - f3;
            String s3 = this.text5;
            this.onFloatFloatFloatFloatFloatStringIntMatrix4f(value2, f4, value3, f2, f5, s3, count, matrix4f);
         }

         if (f >= 0.999F) {
            this.flag = false;
            this.text2 = "";
            this.text3 = this.text;
            this.text4 = "";
            this.text5 = "";
         }
      }
   }

   private void onFloatFloatFloatFloatFloatStringIntMatrix4f(float value, float value2, float value3, float value4, float value5, String text, int count, Matrix4f matrix4f) {
      if (!(value2 <= 0.001F)) {
         switch (this.scriptType) {
            case LATIN:
               TextShader.onMatrix4fFloatFloatIntFloatStringFloatFloat(matrix4f, value, value2, count, value5, text, value3, value4);
               break;
            case CYRILLIC:
               TextShader.onIntStringFloatMatrix4fFloatFloatFloatFloat(count, text, value5, matrix4f, value, value2, value3, value4);
         }
      }
   }

   private float getFloatByFloatString(float value, String text) {
      return this.scriptType == ScriptType.LATIN ? TextShader.getFloatByFloatString(value, text) : TextShader.getFloatByStringFloat(text, value);
   }

   private static int getIntByStringString(String text, String text2) {
      int i = Math.min(text2.length(), text.length());
      int j = 0;

      while (j < i && text2.charAt(j) == text.charAt(j)) {
         j++;
      }

      return j;
   }

   public void setString2(String text6) {
      this.text = text6 == null ? "" : text6;
      this.text2 = "";
      this.text3 = this.text;
      this.text4 = "";
      this.text5 = "";
      this.flag = false;
      this.tween.setFloat(1.0F);
   }

   public void update() {
      this.setFloat(0.0F);
   }

   public void setFloat(float value) {
      this.text2 = "";
      this.text3 = "";
      this.text4 = "";
      this.text5 = this.text;
      this.flag = false;
      this.tween.setFloat(0.0F);
      if (value > 0.0F) {
         float f = 1.0F;
         this.tween.onFloatFloat(value, f);
      } else {
         this.tween.setFloat2(1.0F);
      }
   }

   public String getText() {
      return this.text;
   }

   public float getFloat() {
      return this.tween.getValue3();
   }
}
