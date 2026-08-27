package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.enums.Language;
import client.module.CategoryType;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.Setting;
import client.util.StringParts;
import org.joml.Matrix4f;

public abstract class PanelWidget extends Widget {
   protected static final float value239 = 16.0F;
   protected static final float value240 = 4.0F;
   protected static final float value241 = 16.0F;
   protected final Setting setting;
   private Object value242;
   private float value243 = -1.0F;
   private float value244;
   private Object value245;
   private float value246 = -1.0F;
   private float value247;
   private long time = -1L;
   private float value248 = Float.NaN;
   private float value249;
   private long time2 = -1L;
   private float value250 = Float.NaN;
   private float value251;

   protected PanelWidget(Setting setting2) {
      this.setting = setting2;
      this.value237 = 269.0F;
   }

   private float getFloat() {
      return this.getCategoryType() != null ? 20.0F : 0.0F;
   }

   public float getFloat2() {
      return this.getFloat5();
   }

   protected float getFloatByFloatFloat2(float value, float value2) {
      return this.value236 + this.getFloatByFloatFloat3(value2, value) + 8.0F;
   }

   protected float getFloatByFloat(float value) {
      if (!this.check()) {
         return 0.0F;
      } else {
         Object object = this.getObject();
         if (value == this.value246 && object == this.value245) {
            return this.value247;
         } else {
            this.value245 = object;
            this.value246 = value;
            float f2;
            if (object instanceof String s) {
               float f = 12.0F;
               f2 = TextShader.getFloatByFloatFloatString(value, f, s);
            } else {
               String[] astring1 = (String[])object;
               float f1 = 12.0F;
               String[] astring = astring1;
               f2 = TextShader.getFloatByFloatFloatStringArray(value, f1, astring);
            }

            this.value247 = f2;
            return this.value247;
         }
      }
   }

   public void update3() {
   }

   private Object getObject() {
      if (check3()) {
         String s = Translations.getInstance().getStringByString(this.setting.getDescriptionHash());
         if (s != null) {
            return s;
         }
      }

      return this.setting.getDescriptionParts();
   }

   protected boolean check() {
      return !StringParts.isBlank(this.setting.getDescriptionParts());
   }

   public boolean check2() {
      return false;
   }

   public boolean isDoubleDouble2(double value, double value2) {
      return false;
   }

   protected float getFloat3() {
      return 16.0F;
   }

   public final float getFloat4() {
      long i = UiContext.getTime();
      if (i == this.time2 && this.value237 == this.value250) {
         return this.value251;
      } else {
         this.value251 = this.getFloat2();
         this.time2 = i;
         this.value250 = this.value237;
         return this.value251;
      }
   }

   public abstract float getFloat5();

   public float getValue246() {
      return this.value246;
   }

   protected float getFloat6() {
      return 16.0F;
   }

   public Setting getSetting() {
      return this.setting;
   }

   protected static boolean check3() {
      return Translations.getInstance().getLanguage() != Language.RU;
   }

   public final void update4() {
      this.time = -1L;
      this.time2 = -1L;
   }

   protected float getFloatByMatrix4fFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3) {
      if (!this.check()) {
         return 0.0F;
      } else {
         float f = this.value236 + value2 + 0.0F;
         float f1 = this.getFloatByFloat(value3);
         Object object = this.getObject();
         if (object instanceof String s) {
            String s2 = s.trim();
            float f7 = this.value235;
            int i = Theme.mutedFg();
            float f3 = 12.0F;
            float f2 = f7;
            String s1 = s2;
            TextShader.onFloatStringFloatFloatMatrix4fFloatFloatInt(value, s1, f2, f3, matrix4f, value3, f, i);
         } else {
            String[] astring1 = (String[])object;
            float f6 = this.value235;
            int j = Theme.mutedFg();
            float f5 = 12.0F;
            float f4 = f6;
            String[] astring = astring1;
            TextShader.onFloatFloatFloatStringArrayFloatIntMatrix4fFloat(f, value, value3, astring, f5, j, matrix4f, f4);
         }

         return f1;
      }
   }

   protected float getFloatByFloatMatrix4fFloat(float value, Matrix4f matrix4f, float value2) {
      float f = this.getFloatByFloat2(value);
      CategoryType categorytype = this.getCategoryType();
      float f1 = this.getFloat();
      float f2 = Math.max(0.0F, value - f1);
      if (categorytype != null) {
         float f3 = this.getFloat3();
         float f4 = this.getFloat6();
         float f5 = this.value235 + (16.0F - f3) / 2.0F;
         float f6 = this.value236 + (14.0F - f4) / 2.0F;
         int i = Theme.foreground();
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, i, matrix4f, f6, categorytype, f4, f5, f3);
      }

      Object object = this.getObject2();
      if (object instanceof String s) {
         float f15 = this.value235 + f1;
         float f16 = this.value236;
         int j = Theme.foreground();
         float f9 = 14.0F;
         float f8 = f16;
         float f7 = f15;
         TextShader.onFloatFloatIntFloatMatrix4fStringFloatFloat(f7, f8, j, f2, matrix4f, s, value2, f9);
      } else {
         String[] astring1 = (String[])object;
         float f13 = this.value235 + f1;
         float f14 = this.value236;
         int k = Theme.foreground();
         float f12 = 14.0F;
         float f11 = f14;
         float f10 = f13;
         String[] astring = astring1;
         TextShader.onIntFloatStringArrayMatrix4fFloatFloatFloatFloat(k, value2, astring, matrix4f, f10, f2, f11, f12);
      }

      return f;
   }

   protected float getFloatByFloat2(float value) {
      float f = Math.max(0.0F, value - this.getFloat());
      Object object = this.getObject2();
      if (f == this.value243 && object == this.value242) {
         return this.value244;
      } else {
         this.value242 = object;
         this.value243 = f;
         float f4;
         if (object instanceof String s) {
            float f2 = 14.0F;
            f4 = TextShader.getFloatByFloatFloatString(f, f2, s);
         } else {
            String[] astring1 = (String[])object;
            float f3 = 14.0F;
            String[] astring = astring1;
            f4 = TextShader.getFloatByFloatFloatStringArray(f, f3, astring);
         }

         float f1 = f4;
         if (this.getCategoryType() != null) {
            f1 = Math.max(f1, this.getFloat6());
         }

         this.value244 = f1;
         return this.value244;
      }
   }

   protected CategoryType getCategoryType() {
      return null;
   }

   protected float getFloatByFloatFloat3(float value, float value2) {
      return value + (value2 > 0.0F ? 0.0F + value2 : 0.0F);
   }

   private Object getObject2() {
      if (check3()) {
         String s = Translations.getInstance().getStringByString(this.setting.getNameHash());
         if (s != null) {
            return s;
         }
      }

      return this.setting.getNameParts();
   }

   public void setFloat(float value) {
      if (this.value237 != value) {
         this.value237 = value;
         this.update4();
      }
   }

   public float getValue247() {
      return this.value247;
   }

   public float getValue244() {
      return this.value244;
   }

   public final float getFloat7() {
      long i = UiContext.getTime();
      if (i == this.time && this.value237 == this.value248) {
         return this.value249;
      } else {
         this.value249 = this.getFloat5();
         this.time = i;
         this.value248 = this.value237;
         return this.value249;
      }
   }

   public float getValue243() {
      return this.value243;
   }
}
