package client.util;

public class MathUtil {
   public static int getIntByFloatFloatFloat(float value, float value2, float value3) {
      float f;
      float f1;
      float f2;
      if (value3 == 0.0F) {
         f2 = value;
         f1 = value;
         f = value;
      } else {
         float f3 = value < 0.5F ? value * (1.0F + value3) : value + value3 - value * value3;
         float f4 = 2.0F * value - f3;
         f = getFloatByFloatFloatFloat(f4, f3, value2 + 0.33333334F);
         f1 = getFloatByFloatFloatFloat(f4, f3, value2);
         f2 = getFloatByFloatFloatFloat(f4, f3, value2 - 0.33333334F);
      }

      return 0xFF000000 | Math.round(f * 255.0F) << 16 | Math.round(f1 * 255.0F) << 8 | Math.round(f2 * 255.0F);
   }

   public static int getIntByFloatInt(float value, int count) {
      if (Math.abs(value - 1.0F) < 0.001F) {
         return count;
      } else {
         float[] afloat = getFloatArrayByInt(count);
         float f3 = afloat[0];
         float f4 = Math.min(1.0F, Math.max(0.0F, afloat[1] * value));
         float f2 = afloat[2];
         float f1 = f4;
         float f = f3;
         int i = getIntByFloatFloatFloat(f2, f, f1) & 16777215;
         return count & 0xFF000000 | i;
      }
   }

   public static float[] getFloatArrayByInt(int count) {
      float f = (count >> 16 & 0xFF) / 255.0F;
      float f1 = (count >> 8 & 0xFF) / 255.0F;
      float f2 = (count & 0xFF) / 255.0F;
      float f3 = Math.max(f, Math.max(f1, f2));
      float f4 = Math.min(f, Math.min(f1, f2));
      float f5 = (f3 + f4) / 2.0F;
      if (f3 == f4) {
         return new float[]{0.0F, 0.0F, f5};
      } else {
         float f6 = f3 - f4;
         float f7 = f5 > 0.5F ? f6 / (2.0F - f3 - f4) : f6 / (f3 + f4);
         float f8;
         if (f3 == f) {
            f8 = (f1 - f2) / f6 + (f1 < f2 ? 6.0F : 0.0F);
         } else if (f3 == f1) {
            f8 = (f2 - f) / f6 + 2.0F;
         } else {
            f8 = (f - f1) / f6 + 4.0F;
         }

         return new float[]{f8 / 6.0F, f7, f5};
      }
   }

   public static float getFloatByInt(int count) {
      return (count >> 8 & 0xFF) / 255.0F;
   }

   public static float getFloatByInt2(int count) {
      return (count & 0xFF) / 255.0F;
   }

   public static float getFloatByInt3(int count) {
      return (count >> 24 & 0xFF) / 255.0F;
   }

   private static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      if (value3 < 0.0F) {
         value3++;
      }

      if (value3 > 1.0F) {
         value3--;
      }

      if (value3 < 0.16666667F) {
         return value + (value2 - value) * 6.0F * value3;
      } else if (value3 < 0.5F) {
         return value2;
      } else {
         return value3 < 0.6666667F ? value + (value2 - value) * (0.6666667F - value3) * 6.0F : value;
      }
   }

   public static float getFloatByIntInt(int count, int count2) {
      float f = ((count2 >> 16 & 0xFF) - (count >> 16 & 0xFF)) / 255.0F;
      float f1 = ((count2 >> 8 & 0xFF) - (count >> 8 & 0xFF)) / 255.0F;
      float f2 = ((count2 & 0xFF) - (count & 0xFF)) / 255.0F;
      return (float)Math.sqrt((f * f + f1 * f1 + f2 * f2) / 3.0F);
   }

   public static boolean isIntInt(int count, int count2) {
      return (count & 16777215) == (count2 & 16777215);
   }

   public static boolean isInt(int count) {
      int i = count >> 16 & 0xFF;
      int j = count >> 8 & 0xFF;
      int k = count & 0xFF;
      return Math.max(i, Math.max(j, k)) < 40;
   }

   public static int getIntByFloatInt2(float value, int count) {
      int i = Math.round((count >>> 24 & 0xFF) * Math.clamp(value, 0.0F, 1.0F));
      return count & 16777215 | i << 24;
   }

   private static float[] getFloatArrayByIntIntInt(int count, int count2, int count3) {
      int i = Math.max(count3, Math.max(count2, count));
      int j = Math.min(count3, Math.min(count2, count));
      float f = i / 255.0F;
      float f1 = i != 0 ? (float)(i - j) / i : 0.0F;
      float f2;
      if (f1 == 0.0F) {
         f2 = 0.0F;
      } else {
         float f3 = i - j;
         float f4 = (i - count3) / f3;
         float f5 = (i - count2) / f3;
         float f6 = (i - count) / f3;
         if (count3 == i) {
            f2 = f6 - f5;
         } else if (count2 == i) {
            f2 = 2.0F + f4 - f6;
         } else {
            f2 = 4.0F + f5 - f4;
         }

         f2 /= 6.0F;
         if (f2 < 0.0F) {
            f2++;
         }
      }

      return new float[]{f2, f1, f};
   }

   public static int getIntByFloatIntFloatFloat(float value, int count, float value2, float value3) {
      int i = getIntByFloatFloatFloat2(value2, value3, value);
      return count << 24 | i & 16777215;
   }

   public static float[] getFloatArrayByInt2(int count) {
      int i = count >> 16 & 0xFF;
      int j = count >> 8 & 0xFF;
      int k = count & 0xFF;
      return getFloatArrayByIntIntInt(k, j, i);
   }

   public static float getFloatByInt4(int count) {
      return (count >> 16 & 0xFF) / 255.0F;
   }

   private static int getIntByFloatFloatFloat2(float value, float value2, float value3) {
      int i = 0;
      int j = 0;
      int k = 0;
      if (value == 0.0F) {
         i = j = k = (int)(value3 * 255.0F + 0.5F);
      } else {
         float f = (value2 - (float)Math.floor(value2)) * 6.0F;
         float f1 = f - (float)Math.floor(f);
         float f2 = value3 * (1.0F - value);
         float f3 = value3 * (1.0F - value * f1);
         float f4 = value3 * (1.0F - value * (1.0F - f1));
         switch ((int)f) {
            case 0:
               i = (int)(value3 * 255.0F + 0.5F);
               j = (int)(f4 * 255.0F + 0.5F);
               k = (int)(f2 * 255.0F + 0.5F);
               break;
            case 1:
               i = (int)(f3 * 255.0F + 0.5F);
               j = (int)(value3 * 255.0F + 0.5F);
               k = (int)(f2 * 255.0F + 0.5F);
               break;
            case 2:
               i = (int)(f2 * 255.0F + 0.5F);
               j = (int)(value3 * 255.0F + 0.5F);
               k = (int)(f4 * 255.0F + 0.5F);
               break;
            case 3:
               i = (int)(f2 * 255.0F + 0.5F);
               j = (int)(f3 * 255.0F + 0.5F);
               k = (int)(value3 * 255.0F + 0.5F);
               break;
            case 4:
               i = (int)(f4 * 255.0F + 0.5F);
               j = (int)(f2 * 255.0F + 0.5F);
               k = (int)(value3 * 255.0F + 0.5F);
               break;
            case 5:
               i = (int)(value3 * 255.0F + 0.5F);
               j = (int)(f2 * 255.0F + 0.5F);
               k = (int)(f3 * 255.0F + 0.5F);
         }
      }

      return 0xFF000000 | i << 16 | j << 8 | k;
   }

   public static Integer getIntegerByString(String text) {
      if (text == null) {
         return null;
      } else {
         text = text.trim();
         if (text.startsWith("#")) {
            text = text.substring(1);
         }

         try {
            if (text.length() == 3) {
               StringBuilder stringbuilder = new StringBuilder(6);

               for (int i = 0; i < 3; i++) {
                  char c0 = text.charAt(i);
                  stringbuilder.append(c0).append(c0);
               }

               text = stringbuilder.toString();
            }

            if (text.length() == 6) {
               int l = Integer.parseInt(text.substring(0, 2), 16);
               int j1 = Integer.parseInt(text.substring(2, 4), 16);
               int l1 = Integer.parseInt(text.substring(4, 6), 16);
               return 0xFF000000 | l << 16 | j1 << 8 | l1;
            }

            if (text.length() == 8) {
               int k = Integer.parseInt(text.substring(0, 2), 16);
               int i1 = Integer.parseInt(text.substring(2, 4), 16);
               int k1 = Integer.parseInt(text.substring(4, 6), 16);
               int j = Integer.parseInt(text.substring(6, 8), 16);
               return j << 24 | k << 16 | i1 << 8 | k1;
            }
         } catch (NumberFormatException numberformatexception) {
         }

         return null;
      }
   }

   public static int getIntByFloat(float value) {
      float f = (value - (float)Math.floor(value)) * 6.0F;
      int l = (int)f;
      float f1 = f - l;
      float f2 = 0.14999998F;
      float f3 = 1.0F - 0.85F * f1;
      float f4 = 1.0F - 0.85F * (1.0F - f1);
      int i;
      int j;
      int k;
      switch (l) {
         case 0:
            i = 255;
            j = (int)(f4 * 255.0F + 0.5F);
            k = (int)(f2 * 255.0F + 0.5F);
            break;
         case 1:
            i = (int)(f3 * 255.0F + 0.5F);
            j = 255;
            k = (int)(f2 * 255.0F + 0.5F);
            break;
         case 2:
            i = (int)(f2 * 255.0F + 0.5F);
            j = 255;
            k = (int)(f4 * 255.0F + 0.5F);
            break;
         case 3:
            i = (int)(f2 * 255.0F + 0.5F);
            j = (int)(f3 * 255.0F + 0.5F);
            k = 255;
            break;
         case 4:
            i = (int)(f4 * 255.0F + 0.5F);
            j = (int)(f2 * 255.0F + 0.5F);
            k = 255;
            break;
         default:
            i = 255;
            j = (int)(f2 * 255.0F + 0.5F);
            k = (int)(f3 * 255.0F + 0.5F);
      }

      return 2013265920 | i << 16 | j << 8 | k;
   }
}
