package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.data.AnimatedInt;
import client.data.ScrollAnimator;
import client.data.SettingRowBounds;
import client.module.CategoryType;
import client.render.ItemIconCache;
import client.render.RoundedTextureShader;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.ColorToggleSetting;
import client.setting.CompactGroupSetting;
import client.setting.HotkeySetting;
import client.setting.KeybindSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.Interpolation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class SettingRow extends PanelWidget {
   private static final float value252 = 8.0F;
   private static final float value253 = 4.0F;
   private static final float value254 = 26.0F;
   private static final float value255 = 4.0F;
   private static final float value256 = 8.0F;
   private static final float value257 = 8.0F;
   private static final float value258 = 8.0F;
   private static final float value259 = 16.0F;
   private static final float value260 = 6.0F;
   private static final float value261 = 12.0F;
   private static final float value262 = 70.0F;
   private static final float value263 = 6.0F;
   private static final float value264 = 10.0F;
   private static final float value265 = 141.0F;
   private static final float value266 = 0.05F;
   private static final float value267 = 56.0F;
   private static final float value268 = 18.0F;
   private static final float value269 = 4.0F;
   private static final float value270 = 4.0F;
   private static final float value271 = 14.0F;
   private static final float value272 = 13.0F;
   private static final float value273 = 8.0F;
   private static final float value274 = 20.0F;
   private static final float value275 = 70.0F;
   private static final float value276 = 4.0F;
   private static final float value277 = 6.0F;
   private static final float value278 = 18.0F;
   private static final float value279 = 10.0F;
   private static final float value280 = 0.85F;
   private static final float value281 = 0.55F;
   private static final float value282 = 4.5F;
   private static final float value283 = 0.04F;
   private static final float value284 = 0.1F;
   private final CompactGroupSetting compactGroupSetting;
   private final Map<Setting, SettingField> map = new HashMap<>();
   private final ScrollAnimator<SettingField> scrollAnimator = new ScrollAnimator<>(4.0F);
   private final Interpolation interpolation = new Interpolation();
   private float value285;
   private boolean flag4;
   public SliderSetting sliderSetting;
   private float value286 = Float.NaN;
   private boolean flag5 = true;

   public SettingRow(CompactGroupSetting compactGroupSetting2) {
      super(compactGroupSetting2);
      this.compactGroupSetting = compactGroupSetting2;
   }

   public static float getFloatBySettingRow(SettingRow settingRow) {
      return settingRow.value237;
   }

   @Override
   public float getFloat2() {
      return this.getFloat9() + this.getFloat10();
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      for (SettingRowBounds settingrowbounds : (Iterable<SettingRowBounds>)(this.getList())) {
         float f2 = settingrowbounds.rw();
         float f1 = settingrowbounds.ry();
         float f = settingrowbounds.rx();
         if (settingrowbounds.row().isDoubleDoubleIntFloatFloatFloat(value, value2, count, f2, f1, f)) {
            return true;
         }
      }

      return false;
   }

   private List<SettingRowBounds> getList() {
      ArrayList arraylist = new ArrayList();
      float f = this.value235 + 4.0F;
      float f1 = this.value237 - 8.0F;

      for (SettingField settingfield : (Iterable<SettingField>)(this.scrollAnimator.getCollection())) {
         if (settingfield.animation.getFloat() > 0.5F) {
            arraylist.add(new SettingRowBounds(settingfield, f, settingfield.animation.getValue7(), f1));
         }
      }

      return arraylist;
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      for (SettingField settingfield : (Iterable<SettingField>)(this.scrollAnimator.getCollection())) {
         if (settingfield instanceof ChoiceField choicefield && choicefield.isInt(count)) {
            return true;
         }
      }

      return false;
   }

   public static float getFloatBySettingRow2(SettingRow settingRow) {
      return settingRow.value237;
   }

   private float getFloat8() {
      float f = this.scrollAnimator.getFloat();
      return f <= 0.001F ? 0.0F : 8.0F + f;
   }

   private void update5() {
      if (this.scrollAnimator.isLong(UiContext.getTime())) {
         List<SettingField> list = this.getList2();
         ArrayList arraylist = new ArrayList(list.size());

         for (SettingField settingfield : list) {
            arraylist.add(settingfield.setting);
         }

         float f = this.flag5 ? 0.0F : 0.04F;
         ScrollAnimator scrollanimator = this.scrollAnimator;
         Function function = var1x -> this.map.get((Setting)var1x);
         scrollanimator.onFloatListFunction(f, arraylist, function);
         this.scrollAnimator.onList(arraylist);
         if (this.flag5) {
            for (SettingField settingfield1 : (Iterable<SettingField>)(this.scrollAnimator.getCollection())) {
               settingfield1.animation.update2();
            }

            this.flag4 = false;
            this.flag5 = false;
         }

         this.scrollAnimator.update();
      }
   }

   public static float getFloatBySettingRow3(SettingRow settingRow) {
      return settingRow.value236;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.update5();
      float f7 = this.value237;
      float f = this.getFloatByFloatMatrix4fFloat(f7, matrix4f, value);
      float f8 = this.value237;
      float f1 = this.getFloatByMatrix4fFloatFloatFloat(matrix4f, value, f, f8);
      float f2 = this.getFloatByFloatFloat2(f1, f);
      if (!this.scrollAnimator.check2() && this.scrollAnimator.check()) {
         if (!Float.isNaN(this.value286) && this.value286 != f2) {
            this.scrollAnimator.setFlag();
         }

         this.value286 = f2;
         this.getFloat5();
         float f3 = this.value285;
         float f14 = this.value235;
         float f15 = this.value237;
         int i = Theme.elevated();
         float f11 = 8.0F;
         float f10 = f15;
         float f9 = f14;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f11, f9, i, matrix4f, f3, f10, value, f2);
         float f4 = this.value235 + 4.0F;
         float f5 = this.value237 - 8.0F;
         this.scrollAnimator.setFloat(f2 + 4.0F);

         for (SettingField settingfield : (Iterable<SettingField>)(this.scrollAnimator.getCollection())) {
            float f6 = settingfield.animation.getFloat();
            if (!(f6 <= 0.001F)) {
               float f16 = settingfield.animation.getValue7();
               float f13 = value * f6;
               float f12 = f16;
               this.onFloatFloatMatrix4fFloatSettingFieldFloatFloatFloat(f12, f4, matrix4f, f13, settingfield, value3, f5, value2);
            }
         }

         this.value238 = f2 - this.value236 + f3;
      } else {
         this.value238 = f2 - this.value236;
      }
   }

   private float getFloat9() {
      float f = this.getFloatByFloat2(this.value237);
      float f1 = this.getFloatByFloat(this.value237);
      return this.getFloatByFloatFloat3(f, f1) + 8.0F;
   }

   @Override
   public float getFloat5() {
      this.update5();
      float f = this.interpolation.getFloat2();
      float f1 = this.getFloat8();
      if (!this.flag4) {
         this.value285 = f1;
         this.flag4 = true;
      } else {
         float f3 = 0.1F;
         float f2 = this.value285;
         this.value285 = Interpolation.getFloatByFloatFloatFloatFloat2(f1, f2, f, f3);
      }

      return this.getFloat9() + this.value285;
   }

   public static float getFloatBySettingRow4(SettingRow settingRow) {
      return settingRow.value236;
   }

   public static float getFloatBySettingRow5(SettingRow settingRow) {
      return settingRow.value235;
   }

   private SettingField getSettingFieldBySetting(Setting setting2) {
      if (setting2 instanceof KeybindSetting keybindsetting) {
         return new ChoiceField(keybindsetting);
      } else if (setting2 instanceof HotkeySetting hotkeysetting) {
         return new ChoiceField(hotkeysetting);
      } else if (setting2 instanceof BooleanSetting booleansetting) {
         return new BooleanField(booleansetting);
      } else if (setting2 instanceof SliderSetting slidersetting) {
         return new SliderField(this, slidersetting);
      } else if (setting2 instanceof ColorSetting colorsetting) {
         return new ColorField(this, colorsetting);
      } else {
         return setting2 instanceof ColorToggleSetting colortogglesetting ? new ColorToggleField(this, colortogglesetting) : null;
      }
   }

   public static float getFloatBySettingRow6(SettingRow settingRow) {
      return settingRow.value235;
   }

   public static int getIntByFloat(float value) {
      int i1 = Theme.background();
      int j1 = Theme.elevated();
      float f = 0.85F;
      int k = j1;
      int j = i1;
      int i = AnimatedInt.getIntByIntFloatInt(k, f, j);
      int l = Theme.background();
      return AnimatedInt.getIntByIntFloatInt(i, value, l);
   }

   private void onFloatFloatFloatMatrix4fSettingFieldFloatFloat(float value, float value2, float value3, Matrix4f matrix4f, SettingField settingField, float value4, float value5) {
      float f = value + 8.0F;
      float f1 = value3 + 5.0F;
      ItemStack itemstack = this.compactGroupSetting.getFunction() != null ? (ItemStack)this.compactGroupSetting.getFunction().apply(settingField.setting) : null;
      if (itemstack != null && !itemstack.isEmpty()) {
         float f2 = 16.0F;
         ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f2, value5, f, itemstack, matrix4f, f1);
      } else {
         Sprite sprite = this.compactGroupSetting.getFunction2() != null ? (Sprite)this.compactGroupSetting.getFunction2().apply(settingField.setting) : null;
         if (sprite != null) {
            Identifier identifier1 = sprite.getAtlasId();
            float f16 = sprite.getMinU();
            float f17 = sprite.getMinV();
            float f18 = sprite.getMaxU();
            float f19 = sprite.getMaxV();
            byte b0 = -1;
            float f12 = 0.0F;
            float f11 = 0.0F;
            float f10 = 0.0F;
            float f9 = 0.0F;
            float f8 = f19;
            float f7 = f18;
            float f6 = f17;
            float f5 = f16;
            float f4 = 16.0F;
            float f3 = 16.0F;
            Identifier identifier = identifier1;
            RoundedTextureShader.onFloatFloatFloatFloatFloatFloatIntFloatFloatMatrix4fIdentifierFloatFloatFloatFloatFloat(
               f, f3, f7, f4, f8, f12, b0, f1, f11, matrix4f, identifier, f9, f5, f6, value5, f10
            );
         } else {
            CategoryType categorytype1 = CategoryType.INFO;
            int i = Theme.mutedFg();
            float f14 = 16.0F;
            float f13 = 16.0F;
            CategoryType categorytype = categorytype1;
            SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value5, i, matrix4f, f1, categorytype, f14, f, f13);
            if (value4 >= f && value4 <= f + 16.0F && value2 >= f1 && value2 <= f1 + 16.0F) {
               String s = settingField.setting.getDisplayDescription();
               if (s != null && !s.isEmpty()) {
                  float f15 = 16.0F;
                  HeaderPainter.onFloatStringFloatFloat(f15, s, f, f1);
               }
            }
         }
      }
   }

   private void onFloatFloatMatrix4fFloatSettingFieldFloatFloatFloat(
      float value, float value2, Matrix4f matrix4f, float value3, SettingField settingField, float value4, float value5, float value6
   ) {
      int k2 = Theme.surface();
      float f13 = 3.0F;
      float f12 = 1.0F;
      float f11 = 0.0F;
      int k = 436207616;
      float f10 = 0.0F;
      byte b0 = 0;
      int j = k2;
      float f9 = 8.0F;
      float f8 = 8.0F;
      float f7 = 8.0F;
      float f6 = 8.0F;
      float f5 = 26.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value5, k, j, value2, value, f13, f6, f5, f12, matrix4f, value3, b0, f8, f10, f7, f9, f11
      );
      float f = settingField instanceof ColorToggleField colortogglefield ? value3 * colortogglefield.getFloat() : value3;
      this.onFloatFloatFloatMatrix4fSettingFieldFloatFloat(value2, value6, value, matrix4f, settingField, value4, f);
      float f24 = value2 + 8.0F + 16.0F + 6.0F;
      float f1 = value + 7.0F;
      float f2;
      float f3;
      if (settingField instanceof ColorToggleField colortogglefield1) {
         f2 = colortogglefield1.getFloatByFloatFloat(value5, value2);
         f3 = 3.0F;
      } else if (settingField instanceof ColorField colorfield) {
         f2 = colorfield.getFloatByFloatFloat(value2, value5);
         f3 = 3.0F;
      } else {
         f2 = value2 + 141.0F;
         f3 = 4.0F;
      }

      float f25 = f2 - f24 - f3;
      int l1;
      if (settingField instanceof ColorToggleField colortogglefield2) {
         l1 = Theme.mutedFg();
         int i2 = Theme.foreground();
         float f14 = colortogglefield2.tween.getValue3();
         int i1 = i2;
         int l = l1;
         l1 = AnimatedInt.getIntByIntFloatInt(i1, f14, l);
      } else {
         l1 = Theme.foreground();
      }

      int k1 = l1;
      String s1 = check3() ? Translations.getInstance().getStringByString(settingField.setting.getNameHash()) : null;
      if (s1 != null) {
         if (TextShader.getFloatByStringFloat(s1, 12.0F) > f25) {
            s1 = getStringByFloatString(f25, s1);
         }

         float f15 = 12.0F;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f1, f24, k1, f15, value3, s1, matrix4f);
      } else {
         String[] astring = settingField.setting.getNameParts();
         float f16 = 12.0F;
         if (TextShader.getFloatByFloatStringArray(f16, astring) > f25) {
            float f4 = TextShader.getFloatByStringFloat("...", 12.0F);
            float f26 = f25 - f4;
            float f18 = 12.0F;
            float f17 = f26;
            int i = TextShader.getIntByFloatFloatStringArray(f18, f17, astring);
            float f19 = 12.0F;
            byte b1 = 0;
            TextShader.onIntFloatStringArrayFloatMatrix4fIntIntFloatFloat(b1, value3, astring, f24, matrix4f, i, k1, f19, f1);
            float f20 = 12.0F;
            byte b2 = 0;
            float f27 = f24 + TextShader.getFloatByIntStringArrayFloatInt(b2, astring, f20, i);
            float f22 = 12.0F;
            float f21 = f27;
            String s = "...";
            TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f1, f21, k1, f22, value3, s, matrix4f);
         } else {
            int j2 = astring.length;
            float f23 = 12.0F;
            int j1 = j2;
            byte b3 = 0;
            TextShader.onIntFloatStringArrayFloatMatrix4fIntIntFloatFloat(b3, value3, astring, f24, matrix4f, j1, k1, f23, f1);
         }
      }

      settingField.onFloatFloatFloatFloatFloatMatrix4fFloat(value3, value6, value4, value2, value5, matrix4f, value);
   }

   private float getFloat10() {
      int i = 0;

      for (Setting setting : this.compactGroupSetting.getList()) {
         if (setting.isVisible()) {
            i++;
         }
      }

      return i == 0 ? 0.0F : 8.0F + i * 26.0F + (i - 1) * 4.0F;
   }

   private List getList2() {
      ArrayList arraylist = new ArrayList(this.compactGroupSetting.getList().size());

      for (Setting setting : this.compactGroupSetting.getList()) {
         if (setting.isVisible()) {
            SettingField settingfield = this.map.computeIfAbsent(setting, this::getSettingFieldBySetting);
            if (settingfield != null) {
               arraylist.add(settingfield);
            }
         }
      }

      return arraylist;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      for (SettingField settingfield : (Iterable<SettingField>)(this.scrollAnimator.getCollection())) {
         if (settingfield instanceof ChoiceField choicefield && choicefield.isIntInt(count3, count2)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (count != 0) {
         return false;
      } else if (this.sliderSetting != null) {
         this.sliderSetting = null;
         return true;
      } else {
         boolean flag = false;

         for (SettingField settingfield : (Iterable<SettingField>)(this.scrollAnimator.getCollection())) {
            if (settingfield instanceof BooleanField booleanfield && booleanfield.toggleButton.isDoubleDoubleInt(value, value2, count)) {
               flag = true;
            } else if (settingfield instanceof ColorToggleField colortogglefield && colortogglefield.toggleButton.isDoubleDoubleInt(value, value2, count)) {
               flag = true;
            }
         }

         return flag;
      }
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (count != 0) {
         return false;
      } else if (this.sliderSetting != null) {
         for (SettingRowBounds settingrowbounds : this.getList()) {
            if (settingrowbounds.row() instanceof SliderField sliderfield && sliderfield.sliderSetting == this.sliderSetting) {
               sliderfield.onDoubleFloat(value2, settingrowbounds.rx());
               return true;
            }
         }

         return false;
      } else {
         for (SettingField settingfield : (Iterable<SettingField>)(this.scrollAnimator.getCollection())) {
            if (settingfield instanceof BooleanField booleanfield && booleanfield.toggleButton.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)) {
               return true;
            }

            if (settingfield instanceof ColorToggleField colortogglefield
               && colortogglefield.toggleButton.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)) {
               return true;
            }
         }

         return false;
      }
   }

   private static String getStringByFloatString(float value, String text) {
      Object object = "...";
      float f = TextShader.getFloatByStringFloat((String)object, 12.0F);
      StringBuilder stringbuilder = new StringBuilder(text);

      while (!stringbuilder.isEmpty() && TextShader.getFloatByStringFloat(stringbuilder.toString(), 12.0F) + f > value) {
         stringbuilder.deleteCharAt(stringbuilder.length() - 1);
      }

      return stringbuilder + String.valueOf(object);
   }
}
