package client.gui.widget;

import client.api.ColorSupplier;
import client.api.ListEntry;
import client.api.SwapWheelMetrics;
import client.api.Theme;
import client.data.ScrollAnimator;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public abstract class OverlayPanel<K, R extends ListEntry> extends ButtonWidget implements SwapWheelMetrics {
   protected final TextField textField;
   protected final ScrollAnimator<R> scrollAnimator = new ScrollAnimator<>(4.0F);
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat(0.0F, 0.28F);
   protected float value275;
   private final Tween tween6 = EasingPresets.getTween();
   private final Tween tween7 = EasingPresets.getTween();
   private boolean flag8;
   private float value276;

   protected OverlayPanel() {
      this.value237 = 335.0F;
      float f = 32.0F;
      this.textField = new TextField(303.0F, 26.0F, this.getString3(), 12.0F, f, 0.0F);
      this.textField.setConsumer(var1x -> {
         this.update15();
         this.value275 = 0.0F;
         this.tween5.setFloat(0.0F);
      });
      this.value238 = this.getFloat10();
   }

   protected String getString() {
      return null;
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      float f = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + this.value237 / 2.0F, f + this.value238 / 2.0F);
      float f6 = this.value235;
      float f7 = this.value237;
      float f8 = this.value238;
      int k = Theme.background();
      int l = Theme.border();
      float f5 = 1.0F;
      int j = l;
      int i = k;
      float f4 = 12.0F;
      float f3 = f8;
      float f2 = f7;
      float f1 = f6;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f5, f2, value2, matrix4f, f, i, f4, f1, j, f3);
      this.onFloatFloatMatrix4f(f, value2, matrix4f);
      this.onFloatFloatMatrix4fFloatFloat(value3, f, matrix4f, value2, value);
      this.onFloatFloatFloatFloatMatrix4f(value2, value3, f, value, matrix4f);
      if (this.check4()) {
         this.onFloatFloatFloatMatrix4fFloat(value2, value3, f, matrix4f, value);
      }

      this.onFloatMatrix4fFloatMatrix4fFloatFloat(value3, matrix4f2, value, matrix4f, value2, f);
   }

   private void update11() {
      this.update15();
      this.value275 = 0.0F;
      this.tween5.setFloat(0.0F);
   }

   protected float getFloat6() {
      return this.value235 + (this.value237 - 303.0F) / 2.0F;
   }

   protected float getFloatByFloat(float value) {
      return this.getFloatByFloat3(value) + 26.0F + 8.0F;
   }

   @Override
   public void setColorSupplier(ColorSupplier colorSupplier) {
      this.update11();
      super.setColorSupplier(colorSupplier);
   }

   protected float getFloat7() {
      return this.value237 - 24.0F - 28.0F;
   }

   protected boolean isIntChar2(int count, char symbol) {
      return false;
   }

   protected boolean isDoubleDoubleDouble2(double value, double value2, double value3) {
      return false;
   }

   protected boolean isDoubleDoubleInt2(double value, double value2, int count) {
      return false;
   }

   private ListEntry getListEntryByObject(Object value) {
      return this.getListEntryByObject2(value);
   }

   protected void update12() {
   }

   protected abstract String getString2();

   protected abstract String getString3();

   protected boolean isIntDoubleDouble3(int count, double value, double value2) {
      return false;
   }

   protected void update13() {
   }

   @Override
   protected boolean isIntIntInt3(int count, int count2, int count3) {
      if (this.isIntIntInt4(count2, count, count3)) {
         return true;
      } else if (this.textField.isFlag4()) {
         this.textField.isIntIntInt2(count2, count, count3);
         return true;
      } else {
         return false;
      }
   }

   protected float getFloatByFloat2(float value) {
      return this.getFloatByFloat(value) + 148.0F + 8.0F;
   }

   private boolean check2() {
      return this.scrollAnimator.getFloat() > 140.0F;
   }

   protected float getFloat8() {
      String s1 = this.getString2();
      float f2 = this.getFloat7();
      float f1 = 12.0F;
      String s = s1;
      float f = TextShader.getFloatByFloatFloatString(f2, f1, s);
      return 30.0F + f;
   }

   protected void update14() {
      if (this.scrollAnimator.isLong(UiContext.getTime())) {
         List list = this.getList();
         ArrayList arraylist = new ArrayList(list);
         this.scrollAnimator.onListFunction(arraylist, this::getListEntryByObject);
         this.scrollAnimator.onList(arraylist);
         this.scrollAnimator.update();
      }
   }

   private float getFloat9() {
      return Math.max(0.0F, this.scrollAnimator.getFloat() - 140.0F);
   }

   protected void update15() {
   }

   protected boolean isIntIntInt4(int count, int count2, int count3) {
      return false;
   }

   protected String getString4() {
      return null;
   }

   private float getFloat10() {
      return this.getFloat8() + 10.0F + 26.0F + 8.0F + 148.0F + (this.check4() ? 36.0F : 0.0F) + 12.0F;
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.isDoubleDoubleInt2(value, value2, count)) {
         return true;
      } else if (this.flag8 && count == 0) {
         this.flag8 = false;
         return true;
      } else {
         return false;
      }
   }

   protected boolean isDoubleDoubleDoubleIntDouble(double value, double value2, double value3, int count, double value4) {
      return false;
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.isDoubleDoubleDoubleIntDouble(value2, value, value4, count, value3)) {
         return true;
      } else if (this.flag8 && count == 0) {
         float f = this.value236;
         float f1 = this.getFloatByFloat(f);
         float f2 = f1 + 4.0F;
         float f3 = f1 + 148.0F - 4.0F;
         float f4 = f3 - f2 - 40.0F;
         float f5 = this.getFloat9();
         float f6 = (float)value - this.value276;
         float f7 = (f6 - f2) / f4;
         this.value275 = Math.clamp(f7 * f5, 0.0F, f5);
         return true;
      } else {
         return false;
      }
   }

   private void onFloatFloatMatrix4f(float value, float value2, Matrix4f matrix4f) {
      float f = this.value235 + 12.0F;
      float f1 = value + 12.0F;
      String s2 = this.getString5();
      int i = Theme.foreground();
      float f2 = 14.0F;
      String s = s2;
      TextShader.onIntFloatFloatMatrix4fFloatFloatString(i, value2, f1, matrix4f, f, f2, s);
      CategoryType categorytype1 = CategoryType.CLOSE;
      float f10 = this.value235 + this.value237 - 12.0F - 8.0F;
      float f11 = value + 14.0F;
      int j = Theme.mutedFg();
      float f6 = 8.0F;
      float f5 = 8.0F;
      float f4 = f11;
      float f3 = f10;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, j, matrix4f, f4, categorytype, f6, f3, f5);
      s2 = this.getString2();
      f11 = f1 + 14.0F + 4.0F;
      float f12 = this.getFloat7();
      int k = Theme.mutedFg();
      float f9 = f12;
      float f8 = 12.0F;
      float f7 = f11;
      String s1 = s2;
      TextShader.onFloatStringFloatFloatMatrix4fFloatFloatInt(value2, s1, f, f8, matrix4f, f9, f7, k);
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (count != 0) {
         return true;
      } else if (this.isIntDoubleDouble3(count, value, value2)) {
         return true;
      } else {
         float f = this.value236;
         float f1 = this.value235 + this.value237 - 12.0F - 8.0F;
         float f2 = f + 14.0F;
         float f34 = f1 - 4.0F;
         float f35 = f2 - 4.0F;
         float f17 = 16.0F;
         float f16 = 16.0F;
         float f15 = f35;
         float f14 = f34;
         if (isFloatFloatDoubleFloatFloatDouble(f14, f15, value2, f17, f16, value)) {
            this.update4();
            return true;
         } else {
            float f3 = this.getFloat6();
            float f4 = this.getFloatByFloat3(f);
            float f19 = 26.0F;
            float f18 = 303.0F;
            if (isFloatFloatDoubleFloatFloatDouble(f3, f4, value2, f19, f18, value)) {
               this.textField.isIntDoubleDouble(count, value, value2);
               return true;
            } else {
               this.textField.setBoolean(false);
               if (this.check4()) {
                  float f5 = this.getFloat11();
                  float f6 = this.getFloatByFloat2(f);
                  float f7 = 155.5F;
                  if (this.getString4() != null) {
                     float f20 = 28.0F;
                     if (isFloatFloatDoubleFloatFloatDouble(f5, f6, value2, f20, f7, value)) {
                        this.update12();
                        this.value275 = 0.0F;
                        this.tween5.setFloat(0.0F);
                        return true;
                     }
                  }

                  if (this.getString() != null) {
                     f34 = f5 + f7;
                     float f22 = 28.0F;
                     float f21 = f34;
                     if (isFloatFloatDoubleFloatFloatDouble(f21, f6, value2, f22, f7, value)) {
                        this.update13();
                        return true;
                     }
                  }
               }

               float f28 = this.getFloat12();
               float f29 = this.getFloatByFloat(f);
               boolean flag = this.check2();
               if (flag) {
                  float f8 = f28 + 311.0F - 4.0F - 6.0F;
                  float f9 = f29 + 4.0F;
                  float f10 = f29 + 148.0F - 4.0F;
                  float f11 = f10 - f9 - 40.0F;
                  float f12 = this.getFloat9();
                  float f13 = f9 + (f12 > 0.0F ? f11 * (this.tween5.getValue3() / f12) : 0.0F);
                  float f24 = 40.0F;
                  float f23 = 6.0F;
                  if (isFloatFloatDoubleFloatFloatDouble(f8, f13, value2, f24, f23, value)) {
                     this.flag8 = true;
                     this.value276 = (float)value2 - f13;
                     return true;
                  }
               }

               float f26 = 148.0F;
               float f25 = 311.0F;
               if (!isFloatFloatDoubleFloatFloatDouble(f28, f29, value2, f26, f25, value)) {
                  return true;
               } else {
                  float f30 = f28 + 4.0F;
                  float f31 = flag ? f28 + 311.0F - 4.0F - 6.0F - 4.0F : f28 + 311.0F - 4.0F;
                  float f32 = f31 - f30;

                  for (ListEntry listentry : (Iterable<ListEntry>)(this.scrollAnimator.getCollection())) {
                     if (!(listentry.animation().getFloat() <= 0.001F)) {
                        float f33 = listentry.animation().getValue7();
                        float f27 = listentry.itemHeight();
                        if (isFloatFloatDoubleFloatFloatDouble(f30, f33, value2, f27, f32, value)) {
                           this.isListEntryFloatFloatDoubleFloatDouble(listentry, f33, f30, value, f32, value2);
                           return true;
                        }
                     }
                  }

                  return true;
               }
            }
         }
      }
   }

   protected abstract String getString5();

   @Override
   public boolean isIntChar(int count, char symbol) {
      if (this.isIntChar2(count, symbol)) {
         return true;
      } else if (this.textField.isFlag4()) {
         this.textField.isIntChar(count, symbol);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void setColorSupplier2(ColorSupplier colorSupplier) {
      this.update11();
      super.setColorSupplier2(colorSupplier);
   }

   protected abstract boolean isListEntryFloatFloatDoubleFloatDouble(ListEntry listEntry, float value, float value2, double value3, float value4, double value5);

   protected abstract void onMatrix4fFloatFloatFloatFloatFloatFloatListEntry(
      Matrix4f matrix4f, float value, float value2, float value3, float value4, float value5, float value6, ListEntry listEntry
   );

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (this.isDoubleDoubleDouble2(value2, value, value3)) {
         return true;
      } else {
         float f = this.value236;
         float f1 = this.getFloat12();
         float f2 = this.getFloatByFloat(f);
         float f5 = 148.0F;
         float f4 = 311.0F;
         if (!isFloatFloatDoubleFloatFloatDouble(f1, f2, value3, f5, f4, value2)) {
            return true;
         } else {
            float f3 = this.getFloat9();
            this.value275 = Math.clamp(this.value275 - (float)(value * 48.0), 0.0F, f3);
            this.tween5.setFloat2(this.value275);
            return true;
         }
      }
   }

   protected void onFloatMatrix4fFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, Matrix4f matrix4f2, float value3, float value4) {
   }

   protected abstract List getList();

   protected boolean check3() {
      return false;
   }

   protected float getFloatByFloat3(float value) {
      return value + this.getFloat8() + 10.0F;
   }

   protected abstract ListEntry getListEntryByObject2(Object value);

   private void onMatrix4fFloatFloatFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3, float value4, float value5) {
      float f = value + 311.0F - 4.0F - 6.0F;
      float f1 = value3 + 4.0F;
      float f2 = value3 + 148.0F - 4.0F;
      float f3 = f2 - f1 - 40.0F;
      float f4 = f1 + (value4 > 0.0F ? f3 * (value5 / value4) : 0.0F);
      float f5 = 3.0F;
      int k = Theme.surface();
      float f11 = 3.0F;
      float f10 = 1.0F;
      float f9 = 0.0F;
      int j = 436207616;
      float f8 = 0.0F;
      byte b0 = 0;
      int i = k;
      float f7 = 40.0F;
      float f6 = 6.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f6, j, i, f, f4, f11, f5, f7, f10, matrix4f, value2, b0, f5, f8, f5, f5, f9
      );
   }

   private void onFloatFloatFloatMatrix4fFloat(float value, float value2, float value3, Matrix4f matrix4f, float value4) {
      String s;
      String s1;
      float f;
      float f1;
      float f2;
      boolean flag2;
      label56: {
         s = this.getString4();
         s1 = this.getString();
         f = this.getFloat11();
         f1 = this.getFloatByFloat2(value3);
         f2 = 155.5F;
         if (s != null) {
            double d6 = value2;
            double d7 = value4;
            float f7 = 28.0F;
            double d1 = d7;
            double d0 = d6;
            if (isFloatFloatDoubleFloatFloatDouble(f, f1, d1, f7, f2, d0)) {
               flag2 = true;
               break label56;
            }
         }

         flag2 = false;
      }

      boolean flag;
      label51: {
         flag = flag2;
         if (s1 != null) {
            double d4 = value2;
            double d5 = value4;
            float f53 = f + f2;
            float f9 = 28.0F;
            float f8 = f53;
            double d3 = d5;
            double d2 = d4;
            if (isFloatFloatDoubleFloatFloatDouble(f8, f1, d3, f9, f2, d2)) {
               flag2 = true;
               break label51;
            }
         }

         flag2 = false;
      }

      boolean flag1 = flag2;
      this.tween6.setFloat2(flag ? 1.0F : 0.0F);
      this.tween7.setFloat2(flag1 ? 1.0F : 0.0F);
      float f3 = this.tween6.getFloat();
      float f4 = this.tween7.getFloat();
      int i2 = Theme.elevated();
      int j2 = Theme.border();
      float f19 = 1.0F;
      float f18 = 1.0F;
      float f17 = 0.0F;
      int l = 436207616;
      float f16 = 1.0F;
      int k = j2;
      int j = i2;
      float f15 = 8.0F;
      float f14 = 8.0F;
      float f13 = 8.0F;
      float f12 = 8.0F;
      float f11 = 28.0F;
      float f10 = 311.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f10, l, j, f, f1, f19, f12, f11, f18, matrix4f, value, k, f14, f16, f13, f15, f17
      );
      if (s != null && f3 > 0.001F) {
         float f29 = value * 0.12F * f3;
         float f28 = 1.0F;
         float f27 = 1.0F;
         float f26 = 0.0F;
         byte b1 = 0;
         float f25 = 0.0F;
         byte b0 = 0;
         int i1 = -16777216;
         float f24 = 8.0F;
         float f23 = 0.0F;
         float f22 = 0.0F;
         float f21 = 8.0F;
         float f20 = 28.0F;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f2, b1, i1, f, f1, f28, f21, f20, f27, matrix4f, f29, b0, f23, f25, f22, f24, f26
         );
      }

      if (s1 != null && f4 > 0.001F) {
         float f49 = f + f2;
         float f40 = value * 0.12F * f4;
         float f39 = 1.0F;
         float f38 = 1.0F;
         float f37 = 0.0F;
         byte b3 = 0;
         float f36 = 0.0F;
         byte b2 = 0;
         int j1 = -16777216;
         float f35 = 0.0F;
         float f34 = 8.0F;
         float f33 = 8.0F;
         float f32 = 0.0F;
         float f31 = 28.0F;
         float f30 = f49;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f2, b3, j1, f30, f1, f39, f32, f31, f38, matrix4f, f40, b2, f34, f36, f33, f35, f37
         );
      }

      float f50 = f + f2;
      int k1 = Theme.border();
      float f43 = 28.0F;
      float f42 = 1.0F;
      float f41 = f50;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f43, f41, f1, f42, matrix4f, k1);
      float f5 = f1 + 8.0F;
      if (s != null) {
         float f6 = TextShader.getFloatByStringFloat(s, 12.0F);
         int i = this.check3() ? Theme.primary() : Theme.foreground();
         float f51 = f + (f2 - f6) / 2.0F;
         float f45 = 12.0F;
         float f44 = f51;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f5, f44, i, f45, value, s, matrix4f);
      }

      if (s1 != null) {
         float f48 = TextShader.getFloatByStringFloat(s1, 12.0F);
         float f52 = f + f2 + (f2 - f48) / 2.0F;
         int l1 = Theme.foreground();
         float f47 = 12.0F;
         float f46 = f52;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f5, f46, l1, f47, value, s1, matrix4f);
      }
   }

   private void onFloatFloatFloatFloatMatrix4f(float value, float value2, float value3, float value4, Matrix4f matrix4f) {
      this.update14();
      float f = this.getFloat12();
      float f1 = this.getFloatByFloat(value3);
      int i = Theme.elevated();
      float f13 = 8.0F;
      float f12 = 148.0F;
      float f11 = 311.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f13, f, i, matrix4f, f12, f11, value, f1);
      boolean flag = this.check2();
      float f2 = f + 4.0F;
      float f3 = flag ? f + 311.0F - 4.0F - 6.0F - 4.0F : f + 311.0F - 4.0F;
      float f4 = f3 - f2;
      float f5 = this.getFloat9();
      if (this.value275 < 0.0F) {
         this.value275 = 0.0F;
      }

      if (this.value275 > f5) {
         this.value275 = f5;
      }

      this.tween5.setFloat2(this.value275);
      float f6 = this.tween5.getFloat();
      this.scrollAnimator.setFloat(f1 + 4.0F - f6);
      float f7 = f1 + 4.0F;
      float f8 = 140.0F;
      float f14 = 311.0F;
      ScissorStack.onFloatFloatFloatFloat(f14, f8, f7, f);

      for (ListEntry listentry : (Iterable<ListEntry>)(this.scrollAnimator.getCollection())) {
         float f9 = listentry.animation().getFloat();
         if (!(f9 <= 0.001F)) {
            float f10 = listentry.animation().getValue7();
            if (!(f10 + listentry.itemHeight() < f7) && !(f10 > f7 + f8)) {
               float f15 = value * f9;
               this.onMatrix4fFloatFloatFloatFloatFloatFloatListEntry(matrix4f, f4, f15, f2, value2, value4, f10, listentry);
            }
         }
      }

      this.onFloatMatrix4fFloatFloat(f1, matrix4f, value, f);
      ScissorStack.update();
      if (flag) {
         this.onMatrix4fFloatFloatFloatFloatFloat(matrix4f, f, value, f1, f5, f6);
      }
   }

   private void onFloatFloatMatrix4fFloatFloat(float value, float value2, Matrix4f matrix4f, float value3, float value4) {
      float f = this.getFloat6();
      float f1 = this.getFloatByFloat3(value2);
      int i1 = Theme.background();
      int j1 = Theme.border();
      float f13 = 1.0F;
      float f12 = 1.0F;
      float f11 = 0.0F;
      int k = 436207616;
      float f10 = 1.0F;
      int j = j1;
      int i = i1;
      float f9 = 8.0F;
      float f8 = 8.0F;
      float f7 = 8.0F;
      float f6 = 8.0F;
      float f5 = 26.0F;
      float f4 = 303.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f4, k, i, f, f1, f13, f6, f5, f12, matrix4f, value3, j, f8, f10, f7, f9, f11
      );
      this.textField.onFloatFloat2(f1, f);
      this.textField.onFloatFloatFloatMatrix4f(value3, value4, value, matrix4f);
      float f2 = f + 8.0F;
      float f3 = f1 + 5.0F;
      CategoryType categorytype1 = CategoryType.SEARCH;
      int l = Theme.mutedFg();
      float f15 = 16.0F;
      float f14 = 16.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, l, matrix4f, f3, categorytype, f15, f2, f14);
   }

   protected void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
   }

   @Override
   protected void update7() {
      this.textField.setBoolean(false);
      this.update16();
   }

   protected float getFloat11() {
      return this.value235 + (this.value237 - 311.0F) / 2.0F;
   }

   protected boolean check4() {
      return this.getString4() != null || this.getString() != null;
   }

   protected float getFloat12() {
      return this.value235 + (this.value237 - 311.0F) / 2.0F;
   }

   protected void update16() {
   }
}
