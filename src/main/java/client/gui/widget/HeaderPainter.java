package client.gui.widget;

import client.concurrent.Translations;
import client.data.Tween;
import client.enums.Side;
import client.util.EasingPresets;
import client.util.Easings;
import org.joml.Matrix4f;

public final class HeaderPainter {
   private static final float value = 0.2F;
   private static final float value2 = 1.0E9F;
   private static final Tween tween = new Tween(0.0F, 0.22F).getTweenByFunction(Easings::getFloatByFloat7);
   private static final Tween tween2 = EasingPresets.getTweenByFloatFloat(0.0F, 0.22F);
   private static final Tween tween3 = EasingPresets.getTweenByFloatFloat(0.0F, 0.22F);
   private static final Tween tween4 = EasingPresets.getTweenByFloatFloat(0.0F, 0.22F);
   private static final Tween tween5 = EasingPresets.getTweenByFloatFloat(0.0F, 0.22F);
   private static String text;
   private static float value3;
   private static float value4;
   private static float value5;
   private static float value6;
   private static Side side;
   private static boolean flag;
   private static String text2;
   private static float value7;
   private static float value8;
   private static float value9;
   private static float value10;
   private static Side side2;
   private static long time;
   private static boolean flag2;
   private static final float[] floatArray = new float[4];

   private HeaderPainter() {
   }

   public static boolean isFloatArray(float[] valueArray) {
      if (text2 != null && side2 != null && !(tween.getValue3() <= 0.001F)) {
         float f = tween2.getValue3();
         float f1 = tween3.getValue3();
         float f2 = tween4.getValue3();
         float f3 = tween5.getValue3();
         float f4 = LayoutMetrics.getFloatByString(text2);
         float f5 = 36.0F;
         float f6;
         float f7;
         switch (side2) {
            case BELOW:
               f6 = f + f2 / 2.0F - f4 / 2.0F;
               f7 = f1 + f3 + 6.0F + 5.0F - 1.0F;
               break;
            case LEFT:
               f6 = f - 6.0F - 5.0F + 1.0F - f4;
               f7 = f1 + f3 / 2.0F - f5 / 2.0F;
               break;
            case RIGHT:
               f6 = f + f2 + 6.0F + 5.0F - 1.0F;
               f7 = f1 + f3 / 2.0F - f5 / 2.0F;
               break;
            default:
               f6 = f + f2 / 2.0F - f4 / 2.0F;
               f7 = f1 - 6.0F - 5.0F + 1.0F - f5;
         }

         valueArray[0] = f6;
         valueArray[1] = f7;
         valueArray[2] = f4;
         valueArray[3] = f5;
         return true;
      } else {
         return false;
      }
   }

   public static float getFloat() {
      return tween.getValue3();
   }

   public static void onMatrix4fFloat(Matrix4f matrix4f2, float value) {
      if (text != null) {
         boolean flagx = !text.equals(text2) || value3 != value7 || value4 != value8 || value5 != value9 || value6 != value10 || side != side2;
         boolean flag1 = tween.getValue3() > 0.05F;
         boolean flag2x = false;
         if (flag) {
            time = 0L;
            flag2 = true;
            if (!flag1) {
               flag2x = true;
            }
         } else if (!flag2 || flagx) {
            if (flag1) {
               time = 0L;
               flag2 = true;
            } else {
               time = System.nanoTime();
               flag2 = true;
               tween.setFloat(0.0F);
               flag2x = true;
            }
         }

         text2 = text;
         value7 = value3;
         value8 = value4;
         value9 = value5;
         value10 = value6;
         side2 = side;
         if (flag2x) {
            tween2.setFloat(value3);
            tween3.setFloat(value4);
            tween4.setFloat(value5);
            tween5.setFloat(value6);
         } else {
            tween2.setFloat2(value3);
            tween3.setFloat2(value4);
            tween4.setFloat2(value5);
            tween5.setFloat2(value6);
         }

         float f = (float)(System.nanoTime() - time) / 1.0E9F;
         if (flag || f >= 0.2F) {
            tween.setFloat2(1.0F);
         }
      } else {
         flag2 = false;
         tween.setFloat2(0.0F);
      }

      text = null;
      flag = false;
      float f8 = tween.getFloat();
      float f9 = tween2.getFloat();
      float f10 = tween3.getFloat();
      float f11 = tween4.getFloat();
      float f1 = tween5.getFloat();
      if (!(f8 <= 0.001F) && text2 != null && side2 != null) {
         Matrix4f matrix4f = matrix4f2;
         if (isFloatArray(floatArray)) {
            float f2 = floatArray[0] + floatArray[2] * 0.5F;
            float f3 = floatArray[1] + floatArray[3] * 0.5F;
            matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, f8, f2, f3);
         }

         switch (side2) {
            case BELOW:
               float f4 = f8 * value;
               String s = text2;
               LayoutMetrics.onStringFloatMatrix4fFloatFloatFloatFloat(s, f9, matrix4f, f10, f11, f4, f1);
               break;
            case LEFT:
               float f6 = f8 * value;
               String s2 = text2;
               LayoutMetrics.onFloatFloatMatrix4fFloatFloatString(f9, f6, matrix4f, f1, f10, s2);
               break;
            case RIGHT:
               float f5 = f8 * value;
               String s1 = text2;
               LayoutMetrics.onStringFloatFloatFloatFloatMatrix4fFloat(s1, f11, f1, f5, f10, matrix4f, f9);
               break;
            default:
               float f7 = f8 * value;
               String s3 = text2;
               LayoutMetrics.onFloatFloatFloatStringFloatMatrix4f(f11, f10, f7, s3, f9, matrix4f);
         }
      }
   }

   public static void onFloatStringFloatFloat(float value, String text2, float value2, float value7) {
      if (text2 != null && !text2.isEmpty()) {
         text = Translations.getInstance().getStringByString2(text2);
         value3 = value2;
         value4 = value7;
         value5 = 0.0F;
         value6 = value;
         side = Side.LEFT;
      }
   }

   public static void onFloatStringFloatFloatFloat(float value, String text2, float value2, float value7, float value8) {
      if (text2 != null && !text2.isEmpty()) {
         text = Translations.getInstance().getStringByString2(text2);
         value3 = value;
         value4 = value2;
         value5 = value7;
         value6 = value8;
         side = Side.BELOW;
      }
   }

   public static void onFloatFloatStringFloatFloat(float value, float value2, String text2, float value7, float value8) {
      if (text2 != null && !text2.isEmpty()) {
         text = Translations.getInstance().getStringByString2(text2);
         value3 = value7;
         value4 = value8;
         value5 = value;
         value6 = value2;
         side = Side.BELOW;
         flag = true;
      }
   }

   public static void onStringFloatFloatFloatFloat(String text2, float value, float value2, float value7, float value8) {
      if (text2 != null && !text2.isEmpty()) {
         text = Translations.getInstance().getStringByString2(text2);
         value3 = value7;
         value4 = value;
         value5 = value2;
         value6 = value8;
         side = Side.RIGHT;
      }
   }

   public static void onFloatFloatStringFloat(float value, float value2, String text2, float value7) {
      if (text2 != null && !text2.isEmpty()) {
         text = Translations.getInstance().getStringByString2(text2);
         value3 = value;
         value4 = value2;
         value5 = value7;
         value6 = 0.0F;
         side = Side.ABOVE;
      }
   }
}
