package client.gui.widget;

import client.api.ColorSupplier;
import client.api.Theme;
import client.data.AnimatedInt;
import client.data.ScrollAnimator;
import client.data.TextTrimmer;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.StafflistSetting;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;

public class NickListInput extends ButtonWidget {
   private static final float value241 = 48.0F;
   private static final float value242 = 20.0F;
   private static final float value243 = 4.0F;
   private static final float value244 = 6.0F;
   private static final float value245 = 4.5F;
   private static final float value246 = 4.0F;
   private static final float value247 = 8.0F;
   private static final float value248 = 8.0F;
   private static final float value249 = 10.0F;
   private static final float value250 = 13.0F;
   private static final float value251 = 14.0F;
   private final StafflistSetting stafflistSetting;
   private final TextField textField;
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat(0.0F, 0.28F);
   private float value252;
   private final ScrollAnimator<StringListEntry> scrollAnimator = new ScrollAnimator<>(4.0F);
   private final Map<String, Tween> map = new HashMap<>();
   private final Map<String, Tween> map2 = new HashMap<>();

   public NickListInput(StafflistSetting stafflistSetting2) {
      this.stafflistSetting = stafflistSetting2;
      this.value237 = 310.0F;
      this.value238 = 232.0F;
      float f = this.value237 - 32.0F;
      this.textField = new TextField(f, 36.0F, "Вставьте ники через пробел, Enter", 14.0F, 12.0F, 0.0F);
      this.textField.onRunnable(this::update11);
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      float f = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + this.value237 / 2.0F, f + this.value238 / 2.0F);
      float f10 = this.value235;
      float f11 = this.value237;
      float f12 = this.value238;
      int k = Theme.background();
      int l = Theme.border();
      float f9 = 1.0F;
      int j = l;
      int i = k;
      float f8 = 12.0F;
      float f7 = f12;
      float f6 = f11;
      float f5 = f10;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f9, f6, value2, matrix4f, f, i, f8, f5, j, f7);
      float f1 = this.value235 + 16.0F;
      float f2 = f + 16.0F;
      this.textField.onFloatFloat2(f2, f1);
      this.textField.onFloatFloatFloatMatrix4f(value2, value, value3, matrix4f);
      float f3 = f2 + 36.0F + 8.0F;
      float f4 = f + this.value238 - 16.0F - f3;
      this.onFloatFloatFloatMatrix4fFloatFloat(f4, f3, value2, matrix4f, value, value3);
   }

   private void update11() {
      String s = this.textField.getString();
      if (s != null && !s.isBlank()) {
         ArrayList arraylist = new ArrayList();

         for (String s1 : s.split("\\s+")) {
            if (!s1.isBlank()) {
               arraylist.add(s1);
            }
         }

         int i = this.stafflistSetting.getIntByIterable(arraylist);
         if (i > 0) {
            this.textField.update3();
            this.value252 = 0.0F;
         }
      }
   }

   @Override
   public void setColorSupplier(ColorSupplier colorSupplier) {
      this.update13();
      super.setColorSupplier(colorSupplier);
   }

   @Override
   protected boolean isIntIntInt3(int count, int count2, int count3) {
      if (this.textField.isFlag4()) {
         this.textField.isIntIntInt2(count2, count, count3);
         return true;
      } else {
         return false;
      }
   }

   private void update12() {
      if (this.scrollAnimator.isLong(UiContext.getTime())) {
         List<String> list = this.stafflistSetting.getList();
         ArrayList arraylist = new ArrayList(list.size());

         for (String s : list) {
            arraylist.add(s);
         }

         this.scrollAnimator.onListFunction(arraylist, var0 -> new StringListEntry((String)var0));
         this.scrollAnimator.onList(arraylist);
         this.scrollAnimator.update();
      }
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      float f = this.value236 + 16.0F;
      float f1 = f + 36.0F + 8.0F;
      float f2 = this.value236 + this.value238 - 16.0F - f1;
      float f3 = this.value235 + 16.0F;
      float f4 = this.value237 - 32.0F;
      if (!isFloatFloatDoubleFloatFloatDouble(f3, f1, value3, f2, f4, value2)) {
         return false;
      } else {
         float f5 = this.scrollAnimator.getFloat();
         float f6 = Math.max(0.0F, f2 - 8.0F);
         float f7 = Math.max(0.0F, f5 - f6);
         this.value252 = Math.clamp(this.value252 - (float)(value * 48.0), 0.0F, f7);
         this.tween5.setFloat2(this.value252);
         return true;
      }
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      if (this.textField.isFlag4()) {
         this.textField.isIntChar(count, symbol);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void setColorSupplier2(ColorSupplier colorSupplier) {
      this.update13();
      super.setColorSupplier2(colorSupplier);
   }

   private void update13() {
      this.value252 = 0.0F;
      this.tween5.setFloat(0.0F);
   }

   private void onFloatFloatFloatMatrix4fFloatFloat(float value, float value2, float value3, Matrix4f matrix4f, float value4, float value5) {
      this.update12();
      float f = this.value235 + 16.0F;
      float f1 = this.value237 - 32.0F;
      int i = Theme.elevated();
      float f10 = 8.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f10, f, i, matrix4f, value, f1, value3, value2);
      float f2 = this.scrollAnimator.getFloat();
      float f3 = Math.max(0.0F, value - 8.0F);
      float f4 = Math.max(0.0F, f2 - f3);
      if (this.value252 < 0.0F) {
         this.value252 = 0.0F;
      }

      if (this.value252 > f4) {
         this.value252 = f4;
      }

      this.tween5.setFloat2(this.value252);
      float f5 = this.tween5.getFloat();
      float f6 = f + 4.0F;
      float f7 = f1 - 8.0F;
      this.scrollAnimator.setFloat(value2 + 4.0F - f5);
      ScissorStack.onFloatFloatFloatFloat(f1, value, value2, f);

      for (StringListEntry stringlistentry : (Iterable<StringListEntry>)(this.scrollAnimator.getCollection())) {
         float f8 = stringlistentry.animation.getFloat();
         if (!(f8 <= 0.001F)) {
            float f9 = stringlistentry.animation.getValue7();
            if (!(f9 + 20.0F < value2) && !(f9 > value2 + value)) {
               float f11 = value3 * f8;
               this.onFloatStringListEntryFloatMatrix4fFloatFloatFloatFloat(value5, stringlistentry, value4, matrix4f, f11, f9, f6, f7);
            }
         }
      }

      ScissorStack.update();
   }

   private void onFloatStringListEntryFloatMatrix4fFloatFloatFloatFloat(
      float value, StringListEntry stringListEntry, float value2, Matrix4f matrix4f, float value3, float value4, float value5, float value6
   ) {
      Tween tween = this.map.computeIfAbsent(stringListEntry.text, var0 -> EasingPresets.getTween());
      double d2 = value;
      double d3 = value2;
      float f7 = 20.0F;
      double d1 = d3;
      double d0 = d2;
      boolean flag = isFloatFloatDoubleFloatFloatDouble(value5, value4, d1, f7, value6, d0);
      float f9 = 6.0F;
      float f8 = 20.0F;
      PanelPainter.onFloatMatrix4fBooleanFloatFloatFloatTweenFloatFloat(value3, matrix4f, flag, f9, value4, f8, tween, value6, value5);
      float f = value5 + 6.0F;
      float f1 = value4 + 5.0F;
      CategoryType categorytype2 = CategoryType.FRIENDS;
      int j = Theme.mutedFg();
      float f11 = 10.0F;
      float f10 = 10.0F;
      CategoryType categorytype = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, j, matrix4f, f1, categorytype, f11, f, f10);
      float f2 = value5 + value6 - 6.0F - 13.0F;
      float f3 = value4 + 3.0F;
      float f4 = f + 10.0F + 6.0F;
      float f5 = f2 - 6.0F;
      float f6 = value4 + 4.0F;
      String s2 = stringListEntry.text;
      float f17 = Math.max(0.0F, f5 - f4);
      float f13 = 12.0F;
      float f12 = f17;
      String s1 = s2;
      String s = TextTrimmer.getStringByFloatStringFloat2(f12, s1, f13);
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat(matrix4f, s, f4, f6, 12.0F, Theme.foreground(), value3);
      Tween tween1 = this.map2.computeIfAbsent(stringListEntry.text, var0 -> EasingPresets.getTween());
      boolean flag1 = value >= f2 && value <= f2 + 13.0F && value2 >= f3 && value2 <= f3 + 14.0F;
      tween1.setFloat2(flag1 ? 1.0F : 0.0F);
      int i1 = Theme.mutedFg();
      int j1 = Theme.foreground();
      float f14 = tween1.getFloat();
      int l = j1;
      int k = i1;
      int i = AnimatedInt.getIntByIntFloatInt(l, f14, k);
      float f16 = 14.0F;
      float f15 = 13.0F;
      CategoryType categorytype1 = CategoryType.TRASH;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, f3, categorytype1, f16, f2, f15);
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (count != 0) {
         return true;
      } else {
         float f = this.value235 + 16.0F;
         float f1 = this.value236 + 16.0F;
         float f16 = this.value237 - 32.0F;
         float f12 = 36.0F;
         float f11 = f16;
         if (isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f12, f11, value)) {
            this.textField.isIntDoubleDouble(count, value, value2);
            return true;
         } else {
            this.textField.setBoolean(false);
            float f2 = f1 + 36.0F + 8.0F;
            float f3 = this.value236 + this.value238 - 16.0F - f2;
            float f4 = this.value235 + 16.0F;
            float f5 = this.value237 - 32.0F;
            if (!isFloatFloatDoubleFloatFloatDouble(f4, f2, value2, f3, f5, value)) {
               return true;
            } else {
               float f6 = f4 + 4.0F;
               float f7 = f5 - 8.0F;

               for (StringListEntry stringlistentry : (Iterable<StringListEntry>)(this.scrollAnimator.getCollection())) {
                  if (!(stringlistentry.animation.getFloat() <= 0.5F)) {
                     float f8 = stringlistentry.animation.getValue7();
                     float f13 = 20.0F;
                     if (isFloatFloatDoubleFloatFloatDouble(f6, f8, value2, f13, f7, value)) {
                        float f9 = f6 + f7 - 6.0F - 13.0F;
                        float f10 = f8 + 3.0F;
                        float f15 = 14.0F;
                        float f14 = 13.0F;
                        if (isFloatFloatDoubleFloatFloatDouble(f9, f10, value2, f15, f14, value)) {
                           this.stafflistSetting.isString(stringlistentry.text);
                        }

                        return true;
                     }
                  }
               }

               return true;
            }
         }
      }
   }

   @Override
   protected void update7() {
      this.textField.setBoolean(false);
   }
}
