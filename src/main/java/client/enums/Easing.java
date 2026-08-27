package client.enums;

public enum Easing {
   CUBIC_IN {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value /= value3;
      return value4 * value * value * value + value2;
   }
   },
   CUBIC_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value = value / value3 - 1.0F;
      return value4 * (value * value * value + 1.0F) + value2;
   }
   },
   CUBIC_IN_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value /= value3 / 2.0F;
      if (value < 1.0F) {
         return value4 / 2.0F * value * value * value + value2;
      } else {
         value -= 2.0F;
         return value4 / 2.0F * (value * value * value + 2.0F) + value2;
      }
   }
   },
   LINEAR {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      return value4 * value / value3 + value2;
   }
   },
   QUINT_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value = value / value3 - 1.0F;
      return value4 * (value * value * value * value * value + 1.0F) + value2;
   }
   },
   QUINT_IN {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value /= value3;
      return value4 * value * value * value * value * value + value2;
   }
   },
   QUINT_IN_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value /= value3 / 2.0F;
      if (value < 1.0F) {
         return value4 / 2.0F * value * value * value * value * value + value2;
      } else {
         value -= 2.0F;
         return value4 / 2.0F * (value * value * value * value * value + 2.0F) + value2;
      }
   }
   },
   ELASTIC_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      if (value == 0.0F) {
         return value2;
      } else if ((value = value / value3) == 1.0F) {
         return value2 + value4;
      } else {
         float f = value3 * 0.3F;
         float f1 = f / 4.0F;
         return value4 * (float)Math.pow(2.0, -10.0F * value) * (float)Math.sin((value * value3 - f1) * (Math.PI * 2) / f) + value4 + value2;
      }
   }
   },
   ELASTIC_IN {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      if (value == 0.0F) {
         return value2;
      } else if ((value = value / value3) == 1.0F) {
         return value2 + value4;
      } else {
         float f = value3 * 0.3F;
         float f1 = f / 4.0F;
         return -(value4 * (float)Math.pow(2.0, 10.0F * --value) * (float)Math.sin((value * value3 - f1) * (Math.PI * 2) / f)) + value2;
      }
   }
   },
   BACK_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      float f = 1.70158F;
      float f1;
      return value4 * ((f1 = value / value3 - 1.0F) * f1 * ((f + 1.0F) * f1 + f) + 1.0F) + value2;
   }
   },
   BACK_IN {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      float f = 1.70158F;
      float f1;
      return value4 * (f1 = value / value3) * f1 * ((f + 1.0F) * f1 - f) + value2;
   }
   },
   QUAD_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value /= value3;
      return -value4 * value * (value - 2.0F) + value2;
   }
   },
   QUART_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value = value / value3 - 1.0F;
      return -value4 * (value * value * value * value - 1.0F) + value2;
   }
   },
   EXPO_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      return value == value3 ? value2 + value4 : value4 * (-((float)Math.pow(2.0, -10.0F * value / value3)) + 1.0F) + value2;
   }
   },
   SINE_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      return value4 * (float)Math.sin(value / value3 * (Math.PI / 2)) + value2;
   }
   },
   CIRC_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      value = value / value3 - 1.0F;
      return value4 * (float)Math.sqrt(1.0F - value * value) + value2;
   }
   },
   BOUNCE_OUT {
   public float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4) {
      if ((value = value / value3) < 0.36363637F) {
         return value4 * (7.5625F * value * value) + value2;
      } else if (value < 0.72727275F) {
         float f2;
         return value4 * (7.5625F * (f2 = value - 0.54545456F) * f2 + 0.75F) + value2;
      } else {
         float f;
         float f1;
         return value < 0.9090909090909091
            ? value4 * (7.5625F * (f = value - 0.8181818F) * f + 0.9375F) + value2
            : value4 * (7.5625F * (f1 = value - 0.95454544F) * f1 + 0.984375F) + value2;
      }
   }
   };

   private static final Easing[] easingArray = getEasingArray();

   public static Easing getEasingByString(String text) {
      return Enum.valueOf(Easing.class, text);
   }

   private static Easing[] getEasingArray() {
      return new Easing[]{
         CUBIC_IN,
         CUBIC_OUT,
         CUBIC_IN_OUT,
         LINEAR,
         QUINT_OUT,
         QUINT_IN,
         QUINT_IN_OUT,
         ELASTIC_OUT,
         ELASTIC_IN,
         BACK_OUT,
         BACK_IN,
         QUAD_OUT,
         QUART_OUT,
         EXPO_OUT,
         SINE_OUT,
         CIRC_OUT,
         BOUNCE_OUT
      };
   }

   public abstract float getFloatByFloatFloatFloatFloat(float value, float value2, float value3, float value4);
}
