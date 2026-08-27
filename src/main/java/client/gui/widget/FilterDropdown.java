package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.data.CharMap;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.util.EasingPresets;
import client.util.Easings;
import client.util.StringParts;
import client.util.TextHash;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;

public class FilterDropdown extends PanelWidget {
   private static final float value252 = 0.32F;
   private static final float value253 = 0.3F;
   private static final float value254 = 10.0F;
   private static final float value255 = 26.0F;
   private static final float value256 = 4.0F;
   private static final float value257 = 4.0F;
   private static final float value258 = 12.0F;
   private static final float value259 = 8.0F;
   private static final int value260 = 5;
   private static final float value261 = 154.0F;
   private static final float value262 = 6.0F;
   private static final float value263 = 59.0F;
   private static final float value264 = 4.0F;
   private static final float value265 = 1.0F;
   private static final float value266 = 0.4F;
   private static final float value267 = 0.7F;
   private final ListSetting listSetting;
   private final MultilistSetting multilistSetting;
   private boolean flag4;
   private final Tween tween4 = new Tween(0.0F, 0.32F).getTweenByFunction(Easings::getFloatByFloat7);
   private boolean flag5;
   private final Map<Integer, Tween> map = new HashMap<>();
   private final Tween tween5 = new Tween(0.0F, 0.32F).getTweenByFunction(Easings::getFloatByFloat6);
   private float value268;
   private float value269 = Float.NaN;
   private boolean flag6;
   private float value270;

   public FilterDropdown(ListSetting listSetting2) {
      super(listSetting2);
      this.listSetting = listSetting2;
      this.multilistSetting = null;
   }

   public FilterDropdown(MultilistSetting multilistSetting2) {
      super(multilistSetting2);
      this.listSetting = null;
      this.multilistSetting = multilistSetting2;
   }

   @Override
   public float getFloat2() {
      float f = this.getFloatByFloatFloat3(this.getFloatByFloat2(this.value237), this.getFloatByFloat(this.value237)) + 8.0F + 32.0F;
      if (!this.flag4) {
         return f;
      } else {
         int i = this.getInt3();
         return i == 0 ? f : f + 10.0F + this.getFloatByInt(i);
      }
   }

   private int getInt() {
      return this.listSetting != null ? this.listSetting.getInt() : this.multilistSetting.getInt();
   }

   private float getFloatByInt(int count) {
      return Math.min(154.0F, this.getFloatByInt2(count));
   }

   private String getStringByInt(int count) {
      if (this.multilistSetting != null && this.multilistSetting.isFlag()) {
         String[] astring = this.getStringArrayByInt(count);
         return astring.length == 0 ? null : CharMap.getStringByString(StringParts.join(astring));
      } else {
         return null;
      }
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else {
         float f = this.getFloat8();
         if (value >= this.value235 && value <= this.value235 + this.value237 && value2 >= f && value2 <= f + 32.0F) {
            this.flag4 = !this.flag4;
            if (this.flag4) {
               this.value268 = 0.0F;
               this.tween5.setFloat(0.0F);
               this.value269 = 0.0F;
            }

            return true;
         } else if (!this.flag4) {
            return false;
         } else {
            int i = this.getInt3();
            if (i == 0) {
               return false;
            } else {
               float f1 = f + 32.0F + 10.0F;
               float f2 = this.getFloatByInt(i);
               if (value >= this.value235 && value <= this.value235 + this.value237 && value2 >= f1 && value2 <= f1 + f2) {
                  boolean flag = this.isInt(i);
                  if (flag) {
                     float f3 = this.value235 + this.value237 - 4.0F - 6.0F;
                     float f4 = f1 + 4.0F;
                     float f5 = f1 + f2 - 4.0F;
                     float f6 = f5 - f4 - 59.0F;
                     float f7 = this.getFloatByInt5(i);
                     float f8 = f4 + (f7 > 0.0F ? f6 * (this.tween5.getValue3() / f7) : 0.0F);
                     if (value >= f3 && value <= f3 + 6.0F && value2 >= f8 && value2 <= f8 + 59.0F) {
                        this.flag6 = true;
                        this.value270 = (float)value2 - f8;
                        return true;
                     }
                  }

                  float f9 = this.value235 + 4.0F;
                  float f10 = flag ? this.value235 + this.value237 - 4.0F - 6.0F - 4.0F : this.value235 + this.value237 - 4.0F;
                  float f11 = this.tween5.getValue3();

                  for (int j = 0; j < i; j++) {
                     float f12 = f1 + 4.0F - f11 + j * 30.0F;
                     if (!(f12 + 26.0F < f1) && !(f12 > f1 + f2) && value >= f9 && value <= f10 && value2 >= f12 && value2 <= f12 + 26.0F) {
                        this.onInt(j);
                        return true;
                     }
                  }

                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }

   private void onFloatFloatFloatMatrix4fFloatFloat(float value, float value2, float value3, Matrix4f matrix4f, float value4, float value5) {
      float f = this.value235 + this.value237 - 4.0F - 6.0F;
      float f1 = value3 + 4.0F;
      float f2 = value3 + value - 4.0F;
      float f3 = f2 - f1 - 59.0F;
      float f4 = f1 + (value2 > 0.0F ? f3 * (value4 / value2) : 0.0F);
      float f5 = 3.0F;
      int k = Theme.surface();
      float f11 = 3.0F;
      float f10 = 1.0F;
      float f9 = 0.0F;
      int j = 436207616;
      float f8 = 0.0F;
      byte b0 = 0;
      int i = k;
      float f7 = 59.0F;
      float f6 = 6.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f6, j, i, f, f4, f11, f5, f7, f10, matrix4f, value5, b0, f5, f8, f5, f5, f9
      );
   }

   private boolean isInt(int count) {
      return count > 5;
   }

   @Override
   public void update3() {
      this.flag4 = false;
      this.flag6 = false;
   }

   private float getFloatByInt5(int count) {
      return Math.max(0.0F, this.getFloatByInt2(count) - this.getFloatByInt(count));
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f10 = this.value237;
      float f = this.getFloatByFloatMatrix4fFloat(f10, matrix4f, value);
      float f11 = this.value237;
      float f1 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f, f11);
      float f2 = this.getFloatByFloatFloat2(f1, f);
      boolean flag = this.flag4;
      if (flag != this.flag5) {
         this.tween4.setValue5(flag ? 0.32F : 0.3F);
         this.tween4.getTweenByFunction(Easings::getFloatByFloat7);
         this.flag5 = flag;
      }

      this.tween4.setFloat2(flag ? 1.0F : 0.0F);
      float f3 = this.tween4.getFloat();
      float f24 = this.value235;
      float f26 = this.value237;
      int j1 = Theme.background();
      int k1 = Theme.border();
      float f22 = 1.0F;
      float f21 = 1.0F;
      float f20 = 0.0F;
      int k = 436207616;
      float f19 = 1.0F;
      int j = k1;
      int i = j1;
      float f18 = 8.0F;
      float f17 = 8.0F;
      float f16 = 8.0F;
      float f15 = 8.0F;
      float f14 = 32.0F;
      float f13 = f26;
      float f12 = f24;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f13, k, i, f12, f2, f22, f15, f14, f21, matrix4f, value, j, f17, f19, f16, f18, f20
      );
      float f4 = CategoryType.DROPDOWN_ARROWS.getWidth();
      float f5 = CategoryType.DROPDOWN_ARROWS.getHeight();
      float f6 = this.value235 + this.value237 - 12.0F - f4;
      float f7 = f2 + (32.0F - f5) / 2.0F + 1.0F;
      CategoryType categorytype1 = CategoryType.DROPDOWN_ARROWS;
      int l = Theme.foreground();
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, l, matrix4f, f7, categorytype, f5, f6, f4);
      float f8 = this.value237 - 24.0F - f4 - 4.0F;
      float f9 = f2 + 9.0F;
      float f25 = this.value235 + 12.0F;
      int i1 = Theme.foreground();
      float f23 = f25;
      this.onFloatIntFloatFloatMatrix4fFloat(value, i1, f9, f23, matrix4f, f8);
      this.value238 = f2 + 32.0F - this.value236;
      if (f3 > 0.001F) {
         this.onFloatFloatFloatMatrix4fFloatFloat2(f2, value, value2, matrix4f, value3, f3);
         this.value238 = f2 + 32.0F - this.value236 + (10.0F + this.getFloatByInt(this.getInt3())) * f3;
      }
   }

   @Override
   public boolean check2() {
      return false;
   }

   @Override
   protected float getFloat3() {
      return 12.0F;
   }

   private int getInt2() {
      if (this.listSetting != null) {
         return this.listSetting.getInt2();
      } else {
         for (int i = 0; i < this.multilistSetting.getStringArrayArrayAsInt(); i++) {
            if (this.multilistSetting.isInt(i)) {
               return i;
            }
         }

         return -1;
      }
   }

   @Override
   public float getFloat5() {
      float f = this.getFloatByFloatFloat3(this.getFloatByFloat2(this.value237), this.getFloatByFloat(this.value237)) + 8.0F + 32.0F;
      float f1 = this.tween4.getValue3();
      if (f1 <= 0.001F) {
         return f;
      } else {
         int i = this.getInt3();
         return i == 0 ? f : f + (10.0F + this.getFloatByInt(i)) * f1;
      }
   }

   private boolean check4() {
      return this.multilistSetting != null || this.listSetting != null && this.listSetting.isFlag();
   }

   @Override
   protected float getFloat6() {
      return 12.0F;
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.flag6 && count == 0) {
         int i = this.getInt3();
         if (i == 0) {
            return false;
         } else {
            float f = this.getFloat8();
            float f1 = f + 32.0F + 10.0F;
            float f2 = this.getFloatByInt(i);
            float f3 = f1 + 4.0F;
            float f4 = f1 + f2 - 4.0F;
            float f5 = f4 - f3 - 59.0F;
            float f6 = this.getFloatByInt5(i);
            float f7 = (float)value - this.value270;
            float f8 = (f7 - f3) / f5;
            this.value268 = Math.clamp(f8 * f6, 0.0F, f6);
            return true;
         }
      } else {
         return false;
      }
   }

   private void onFloatFloatFloatMatrix4fFloatFloat2(float value, float value2, float value3, Matrix4f matrix4f, float value4, float value5) {
      int i = this.getInt3();
      if (i != 0) {
         float f = value + 32.0F + 10.0F;
         float f1 = this.getFloatByInt(i);
         float f2 = f1 * value5;
         float f3 = value2 * value5;
         float f15 = this.value237;
         float f14 = this.value235;
         ScissorStack.onFloatFloatFloatFloat(f15, f2, f, f14);
         float f32 = this.value235;
         float f33 = this.value237;
         int k = Theme.elevated();
         float f18 = 12.0F;
         float f17 = f33;
         float f16 = f32;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f18, f16, k, matrix4f, f1, f17, f3, f);
         boolean flag = this.isInt(i);
         float f4 = this.value235 + 4.0F;
         float f5 = flag ? this.value235 + this.value237 - 4.0F - 6.0F - 4.0F : this.value235 + this.value237 - 4.0F;
         float f6 = f5 - f4;
         float f7 = this.getFloatByInt5(i);
         float f8 = Math.clamp(this.value268, 0.0F, f7);
         this.value268 = f8;
         if (f8 != this.value269) {
            this.tween5.setFloat2(f8);
            this.value269 = f8;
         }

         float f9 = this.tween5.getFloat();
         boolean flag1 = value4 >= this.value235 && value4 <= this.value235 + this.value237 && value3 >= f && value3 <= f + f1;

         for (int j = 0; j < i; j++) {
            float f10 = f + 4.0F - f9 + j * 30.0F;
            if (!(f10 + 26.0F < f) && !(f10 > f + f1)) {
               boolean flag2 = this.isInt2(j);
               boolean flag3 = flag1 && value4 >= f4 && value4 <= f4 + f6 && value3 >= f10 && value3 <= f10 + 26.0F;
               float f11 = flag2 ? 1.0F : (flag3 ? 0.7F : 0.4F);
               Tween tween = this.map.computeIfAbsent(j, var1x -> EasingPresets.getTweenByFloatFloat2(this.isInt2(var1x) ? 1.0F : 0.4F, 0.22F));
               tween.setFloat2(f11);
               float f12 = tween.getFloat();
               int l1 = Theme.surface();
               float f28 = f3 * f12;
               float f27 = 3.0F;
               float f26 = 1.0F;
               float f25 = 0.0F;
               int i1 = 436207616;
               float f24 = 0.0F;
               byte b0 = 0;
               int l = l1;
               float f23 = 8.0F;
               float f22 = 8.0F;
               float f21 = 8.0F;
               float f20 = 8.0F;
               float f19 = 26.0F;
               ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
                  f6, i1, l, f4, f10, f27, f20, f19, f26, matrix4f, f28, b0, f22, f24, f21, f23, f25
               );
               float f13 = f10 + 6.0F - 1.0F;
               f33 = f4 + 12.0F - 4.0F;
               int k1 = Theme.foreground();
               float f34 = f3 * f12;
               float f31 = f6 - 16.0F;
               float f30 = f34;
               int j1 = k1;
               float f29 = f33;
               this.getFloatByMatrix4fFloatIntIntFloatFloatFloat(matrix4f, f30, j1, j, f29, f13, f31);
            }
         }

         if (flag) {
            this.onFloatFloatFloatMatrix4fFloatFloat(f1, f7, f, matrix4f, f9, f3);
         }

         ScissorStack.update();
      }
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.flag6 && count == 0) {
         this.flag6 = false;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (!this.flag4) {
         return false;
      } else {
         int i = this.getInt3();
         if (i == 0) {
            return false;
         } else if (!this.isInt(i)) {
            return false;
         } else {
            float f = this.getFloat8();
            float f1 = f + 32.0F + 10.0F;
            float f2 = this.getFloatByInt(i);
            if (value2 >= this.value235 && value2 <= this.value235 + this.value237 && value3 >= f1 && value3 <= f1 + f2) {
               float f3 = this.getFloatByInt5(i);
               this.value268 = Math.clamp(this.value268 - (float)(value * 30.0), 0.0F, f3);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private boolean isInt2(int count) {
      return this.listSetting != null ? this.listSetting.isInt(count) : this.multilistSetting.isInt(count);
   }

   private void onInt(int count) {
      if (this.listSetting != null) {
         if (this.listSetting.isFlag()) {
            this.listSetting.onInt(count);
         } else {
            this.listSetting.onInt2(count);
         }
      } else {
         this.multilistSetting.addInt(count);
      }
   }

   private String getStringByInt2(int count) {
      String[] astring = this.getStringArrayByInt(count);
      return astring.length == 0 ? null : Translations.getInstance().getStringByString(Long.toHexString(TextHash.getLongByStringArray(astring)));
   }

   private float getFloatByStringBoolean(String text, boolean flag) {
      float f1;
      if (flag) {
         float f = 14.0F;
         f1 = TextShader.getFloatByFloatString(f, text);
      } else {
         f1 = TextShader.getFloatByStringFloat(text, 14.0F);
      }

      return f1;
   }

   private String[] getStringArrayByInt(int count) {
      return this.listSetting != null ? this.listSetting.getStringArrayByInt(count) : this.multilistSetting.getStringArrayByInt(count);
   }

   private int getInt3() {
      return this.listSetting != null
         ? this.listSetting.getStringArrayArrayAsInt()
         : (this.multilistSetting != null ? this.multilistSetting.getStringArrayArrayAsInt() : 0);
   }

   @Override
   protected CategoryType getCategoryType() {
      return CategoryType.SETTING_DROPDOWN;
   }

   private void onFloatIntFloatFloatMatrix4fFloat(float value, int count, float value2, float value3, Matrix4f matrix4f, float value4) {
      int i = this.getInt();
      if (i == 0) {
         String s4 = this.getStringByString("None");
         boolean flag2 = false;
         String s1 = s4;
         String s3 = this.getStringByBooleanStringFloat(flag2, s1, value4);
         float f2 = 14.0F;
         String s2 = s3;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value2, value3, count, f2, value, s2, matrix4f);
      } else {
         int j = this.getInt2();
         boolean flag = this.getStringByInt(j) != null;
         boolean flag1 = this.check4() && i > 1;
         String s = "…";
         float f = flag1 ? this.getFloatByStringBoolean(s, flag) : 0.0F;
         float f3 = value4 - f;
         float f1 = this.getFloatByMatrix4fFloatIntIntFloatFloatFloat(matrix4f, value, count, j, value3, value2, f3);
         if (flag1) {
            float f4 = value3 + f1;
            this.onFloatStringBooleanMatrix4fFloatIntFloat(value2, s, flag, matrix4f, f4, count, value);
         }
      }
   }

   private String getStringByString(String text) {
      return Translations.getInstance().getStringByString2(text);
   }

   private float getFloat8() {
      float f1 = this.getFloatByFloat(this.value237);
      float f = this.getFloatByFloat2(this.value237);
      return this.getFloatByFloatFloat2(f1, f);
   }

   private float getFloatByInt2(int count) {
      return count <= 0 ? 0.0F : 8.0F + count * 26.0F + (count - 1) * 4.0F;
   }

   private float getFloatByMatrix4fFloatIntIntFloatFloatFloat(Matrix4f matrix4f, float value, int count, int count2, float value2, float value3, float value4) {
      String s = this.getStringByInt(count2);
      String s1 = s != null ? s : this.getStringByInt2(count2);
      if (s1 != null) {
         boolean flag = s != null;
         String s3 = this.getStringByBooleanStringFloat(flag, s1, value4);
         this.onFloatStringBooleanMatrix4fFloatIntFloat(value3, s3, flag, matrix4f, value2, count, value);
         return this.getFloatByStringBoolean(s3, flag);
      } else {
         String[] astring = this.getStringArrayByInt(count2);
         float f3 = 14.0F;
         float f = TextShader.getFloatByFloatStringArray(f3, astring);
         if (f <= value4) {
            int k = astring.length;
            float f4 = 14.0F;
            int j = k;
            byte b0 = 0;
            TextShader.onIntFloatStringArrayFloatMatrix4fIntIntFloatFloat(b0, value, astring, value2, matrix4f, j, count, f4, value3);
            return f;
         } else {
            float f1 = this.getFloatByStringBoolean("...", false);
            float f11 = value4 - f1;
            float f6 = 14.0F;
            float f5 = f11;
            int i = TextShader.getIntByFloatFloatStringArray(f6, f5, astring);
            float f7 = 14.0F;
            byte b1 = 0;
            float f2 = TextShader.getFloatByIntStringArrayFloatInt(b1, astring, f7, i);
            float f8 = 14.0F;
            byte b2 = 0;
            TextShader.onIntFloatStringArrayFloatMatrix4fIntIntFloatFloat(b2, value, astring, value2, matrix4f, i, count, f8, value3);
            float f12 = value2 + f2;
            float f10 = 14.0F;
            float f9 = f12;
            String s2 = "...";
            TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value3, f9, count, f10, value, s2, matrix4f);
            return f2 + f1;
         }
      }
   }

   private void onFloatStringBooleanMatrix4fFloatIntFloat(float value, String text, boolean flag, Matrix4f matrix4f, float value2, int count, float value3) {
      if (flag) {
         float f = 14.0F;
         TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value3, text, f, value2, count, value, matrix4f);
      } else {
         float f1 = 14.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value, value2, count, f1, value3, text, matrix4f);
      }
   }

   private String getStringByBooleanStringFloat(boolean flag, String text, float value) {
      if (this.getFloatByStringBoolean(text, flag) <= value) {
         return text;
      } else {
         Object object = "...";
         float f = this.getFloatByStringBoolean((String)object, flag);
         StringBuilder stringbuilder = new StringBuilder(text);

         while (!stringbuilder.isEmpty() && this.getFloatByStringBoolean(stringbuilder.toString(), flag) + f > value) {
            stringbuilder.deleteCharAt(stringbuilder.length() - 1);
         }

         return stringbuilder + String.valueOf(object);
      }
   }
}
