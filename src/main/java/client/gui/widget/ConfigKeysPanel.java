package client.gui.widget;

import client.api.ColorSupplier;
import client.api.Theme;
import client.data.AnimatedInt;
import client.data.ConfigSync;
import client.data.LabelData;
import client.data.ScrollAnimator;
import client.data.TextTrimmer;
import client.data.Tween;
import client.module.CategoryType;
import client.module.Feature;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.EasingPresets;
import client.util.Easings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class ConfigKeysPanel extends Widget {
   private static final Map<String, List<LabelData>> map = new HashMap<>();
   private static final float value239 = 14.0F;
   private static final float value240 = 12.0F;
   private static final float value241 = 12.0F;
   private static final float value242 = 12.0F;
   private static final float value243 = 8.0F;
   private static final float value244 = 14.0F;
   private static final float value245 = 12.0F;
   private static final float value246 = 28.0F;
   private static final float value247 = 8.0F;
   private static final String text = "Приватные ключи";
   private static final String text2 = "Управляйте своими приватными ключами.";
   private static final float value248 = 32.0F;
   private static final float value249 = 4.0F;
   private static final float value250 = 3.0F;
   private static final float value251 = 120.0F;
   private static final float value252 = 26.0F;
   private static final float value253 = 4.0F;
   private static final float value254 = 6.0F;
   private static final float value255 = 6.0F;
   private static final float value256 = 6.0F;
   private static final float value257 = 4.0F;
   private static final float value258 = 8.0F;
   private static final float value259 = 20.0F;
   private static final float value260 = 4.0F;
   private static final float value261 = 6.0F;
   private static final int value262 = 6;
   private static final float value263 = 6.0F;
   private static final float value264 = 40.0F;
   private static final float value265 = 4.0F;
   private static final float value266 = 4.5F;
   private static final float value267 = 0.85F;
   private final String text3;
   private final List<LabelData> list = new ArrayList<>();
   private boolean flag4;
   private boolean flag5;
   private ColorSupplier colorSupplier;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.22F);
   private final Tween tween5 = EasingPresets.getTween();
   private final Tween tween6 = EasingPresets.getTween();
   private final Tween tween7 = new Tween(0.0F, 0.3F).getTweenByFunction(Easings::getFloatByFloat7);
   private static final float value268 = 1.5F;
   private static final float value269 = 0.45F;
   private final Map<String, Tween> map2 = new HashMap<>();
   private final Map<String, Tween> map3 = new HashMap<>();
   private final ScrollAnimator<LabelEntry> scrollAnimator = new ScrollAnimator<>(4.0F);
   private final Tween tween8 = new Tween(0.0F, 0.32F).getTweenByFunction(Easings::getFloatByFloat6);
   private float value270;
   private float value271 = Float.NaN;
   private boolean flag6;
   private float value272;

   public ConfigKeysPanel(String text) {
      this.text3 = text;
      this.value237 = 276.0F;
      this.value238 = 288.0F;
   }

   private float getFloat() {
      return 120.0F;
   }

   private float getFloat2() {
      float f2 = this.getFloat5();
      float f1 = 12.0F;
      String s = "Управляйте своими приватными ключами.";
      float f = TextShader.getFloatByFloatFloatString(f2, f1, s);
      return 30.0F + f;
   }

   private float getFloatByFloat(float value) {
      return value + this.getFloat2() + 8.0F;
   }

   public void setFlag5() {
      this.flag5 = false;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.flag5 && count == 0) {
         this.update5();
         float f = this.value235 + 276.0F - 12.0F - 8.0F;
         float f1 = this.value236 + 14.0F;
         float f14 = f - 4.0F;
         float f15 = f1 - 4.0F;
         float f11 = 16.0F;
         float f10 = 16.0F;
         float f9 = f15;
         float f8 = f14;
         if (isFloatFloatDoubleFloatFloatDouble(f8, f9, value2, f11, f10, value)) {
            this.setFlag5();
            return true;
         } else {
            float f2 = this.getFloatByFloat(this.value236);
            float f3 = this.getFloat3();
            float f4 = this.getFloat();
            float f5 = f2 + 3.0F;
            float f6 = f3 + 4.0F;
            float f7 = f6 + f4 + 4.0F;
            float f12 = 26.0F;
            if (isFloatFloatDoubleFloatFloatDouble(f6, f5, value2, f12, f4, value)) {
               String s = this.getString();
               this.onString2(s);
               if (s != null && !s.isEmpty()) {
                  this.tween7.setFloat(0.0F);
                  this.tween7.setFloat2(1.0F);
               }

               return true;
            } else {
               float f13 = 26.0F;
               if (isFloatFloatDoubleFloatFloatDouble(f7, f5, value2, f13, f4, value)) {
                  ConfigSync.getInstance().onStringConsumer(this.text3, var1x -> {
                     if (var1x != null) {
                        this.onString2((String)var1x);
                     }

                     this.update3();
                  });
                  return true;
               } else {
                  return this.isDoubleDouble2(value, value2) ? true : this.isDoubleDouble(value, value2);
               }
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f2) {
      this.tween4.setFloat2(this.flag5 ? 1.0F : 0.0F);
      float f = this.tween4.getFloat();
      if (f <= 0.001F) {
         if (!this.flag5) {
            this.colorSupplier = null;
         }
      } else {
         this.update5();
         this.update4();
         float f1 = value * f;
         float f2 = this.getFloatByFloat4(f);
         Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, f, this.value235 + 138.0F, f2 + 144.0F);
         float f8 = this.value235;
         int k = Theme.background();
         int l = Theme.border();
         float f7 = 1.0F;
         int j = l;
         int i = k;
         float f6 = 14.0F;
         float f5 = 288.0F;
         float f4 = 276.0F;
         float f3 = f8;
         ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f7, f4, f1, matrix4f, f2, i, f6, f3, j, f5);
         this.onMatrix4fFloatFloat(matrix4f, f2, f1);
         this.onFloatFloatMatrix4fFloatFloat(f1, f2, matrix4f, value2, value3);
         this.onFloatMatrix4fFloatFloatFloat(f1, matrix4f, value2, f2, value3);
      }
   }

   private float getFloatByFloat2(float value) {
      return this.getFloatByFloat(value) + 32.0F + 6.0F;
   }

   private float getFloat3() {
      return this.value235 + 12.0F;
   }

   private void update3() {
      ConfigSync configsync = ConfigSync.getInstance();
      String s1 = this.text3;
      Consumer<Object> consumer = var1x -> {
         this.list.clear();
         if (var1x != null) {
            this.list.addAll((java.util.Collection<? extends LabelData>)var1x);
         }

         map.put(this.text3, new ArrayList<>(this.list));
      };
      String s = s1;
      configsync.onConsumerString(consumer, s);
   }

   private float getFloat4() {
      return 252.0F;
   }

   private void update4() {
      if (this.scrollAnimator.isLong(UiContext.getTime())) {
         List<LabelData> listx = this.getList();
         ArrayList arraylist = new ArrayList(listx.size());

         for (LabelData labeldata : listx) {
            arraylist.add(labeldata.getText());
         }

         this.scrollAnimator.onListFunction(arraylist, var1x -> new LabelEntry(this.getLabelDataByString((String)var1x)));

         for (LabelEntry labelentry : (Iterable<LabelEntry>)(this.scrollAnimator.getCollection())) {
            LabelData labeldata1 = this.getLabelDataByString(labelentry.labelData.getText());
            if (labeldata1 != null) {
               labelentry.labelData = labeldata1;
            }
         }

         this.scrollAnimator.onList(arraylist);
         this.scrollAnimator.update();
      }
   }

   private float getFloatByFloat3(float value) {
      return value + 288.0F - this.getFloatByFloat2(value) - 12.0F;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (this.flag5 && count3 == 256) {
         this.setFlag5();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (!this.flag5) {
         return false;
      } else {
         float f = this.getFloat3();
         float f1 = this.getFloatByFloat2(this.value236);
         float f2 = this.getFloat4();
         float f3 = this.getFloatByFloat3(this.value236);
         if (!(value2 < f) && !(value2 > f + f2) && !(value3 < f1) && !(value3 > f1 + f3)) {
            int i = this.scrollAnimator.getCollection().size();
            if (!this.isInt(i)) {
               return false;
            } else {
               float f4 = this.getFloatByIntFloat(i, f3);
               this.value270 = Math.clamp(this.value270 - (float)(value * 24.0), 0.0F, f4);
               return true;
            }
         } else {
            return false;
         }
      }
   }

   private String getString() {
      StringBuilder stringbuilder = new StringBuilder();

      for (LabelData labeldata : this.list) {
         if (!labeldata.isFlag()) {
            String s = labeldata.getText();
            if (s != null && !s.isEmpty()) {
               if (!stringbuilder.isEmpty()) {
                  stringbuilder.append('\n');
               }

               stringbuilder.append(s);
            }
         }
      }

      return stringbuilder.toString();
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.flag6 && count == 0) {
         int i = this.scrollAnimator.getCollection().size();
         float f = this.getFloatByFloat2(this.value236);
         float f1 = this.getFloatByFloat3(this.value236);
         float f2 = f + 4.0F;
         float f3 = f + f1 - 4.0F;
         float f4 = f3 - f2 - 40.0F;
         float f5 = this.getFloatByIntFloat(i, f1);
         float f6 = (float)value - this.value272;
         float f7 = (f6 - f2) / f4;
         this.value270 = Math.clamp(f7 * f5, 0.0F, f5);
         return true;
      } else {
         return false;
      }
   }

   private boolean isDoubleDouble2(double value, double value2) {
      float f = this.getFloat3();
      float f1 = this.getFloatByFloat2(this.value236);
      float f2 = this.getFloat4();
      float f3 = this.getFloatByFloat3(this.value236);
      int i = this.scrollAnimator.getCollection().size();
      boolean flag = this.isInt(i);
      float f4 = f + 4.0F;
      float f5 = flag ? f + f2 - 4.0F - 6.0F - 4.0F : f + f2 - 4.0F;
      if (flag) {
         float f6 = f + f2 - 4.0F - 6.0F;
         float f7 = f1 + 4.0F;
         float f8 = f1 + f3 - 4.0F;
         float f9 = f8 - f7 - 40.0F;
         float f10 = this.getFloatByIntFloat(i, f3);
         float f11 = f7 + (f10 > 0.0F ? f9 * (this.tween8.getValue3() / f10) : 0.0F);
         if (value >= f6 && value <= f6 + 6.0F && value2 >= f11 && value2 <= f11 + 40.0F) {
            this.flag6 = true;
            this.value272 = (float)value2 - f11;
            return true;
         }
      }

      if (!(value2 < f1) && !(value2 > f1 + f3)) {
         for (LabelEntry labelentry : (Iterable<LabelEntry>)(this.scrollAnimator.getCollection())) {
            if (!(labelentry.animation.getFloat() <= 0.5F)) {
               LabelData labeldata = labelentry.labelData;
               float f14 = labelentry.animation.getValue7();
               if (!(f14 < f1) && !(f14 + 20.0F > f1 + f3)) {
                  if (!labeldata.isFlag()) {
                     float f15 = f5 - 6.0F - 13.0F;
                     float f16 = f14 + 3.0F;
                     float f13 = 14.0F;
                     float f12 = 13.0F;
                     if (isFloatFloatDoubleFloatFloatDouble(f15, f16, value2, f13, f12, value)) {
                        ConfigSync configsync = ConfigSync.getInstance();
                        String s2 = this.text3;
                        String s3 = labeldata.getText();
                        Runnable runnable = this::update3;
                        String s1 = s3;
                        String s = s2;
                        configsync.onStringRunnableString(s, runnable, s1);
                        return true;
                     }
                  }

                  if (value >= f4 && value <= f5 && value2 >= f14 && value2 <= f14 + 20.0F) {
                     this.onString2(labeldata.getText());
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
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

   private void onTweenFloatFloatFloatFloatStringFloatMatrix4fFloatTween(
      Tween tween, float value, float value2, float value3, float value4, String text, float value5, Matrix4f matrix4f, float value6, Tween tween2
   ) {
      boolean flag = value2 >= value && value2 <= value + value6 && value5 >= value3 && value5 <= value3 + 26.0F;
      tween2.setFloat2(flag ? 1.0F : 0.0F);
      float f = tween2.getFloat();
      int i2 = Theme.background();
      int j2 = Theme.elevated();
      float f2 = 0.85F;
      int l = j2;
      int k = i2;
      int i = AnimatedInt.getIntByIntFloatInt(l, f2, k);
      int i1 = Theme.background();
      int j = AnimatedInt.getIntByIntFloatInt(i, f, i1);
      if (tween != null) {
         float f1 = tween.getFloat();
         if (tween.getValue4() > 0.5F && f1 > 0.999F) {
            float f4 = 1.5F;
            float f3 = 0.0F;
            tween.onFloatFloat(f4, f3);
         }

         if (f1 > 0.001F) {
            j2 = Theme.success();
            float f5 = f1 * 0.45F;
            int j1 = j2;
            j = AnimatedInt.getIntByIntFloatInt(j1, f5, j);
         }
      }

      float f14 = 3.0F;
      float f13 = 1.0F;
      float f12 = 0.0F;
      int k1 = 436207616;
      float f11 = 0.0F;
      byte b0 = 0;
      float f10 = 6.0F;
      float f9 = 6.0F;
      float f8 = 6.0F;
      float f7 = 6.0F;
      float f6 = 26.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value6, k1, j, value, value3, f14, f7, f6, f13, matrix4f, value4, b0, f9, f11, f8, f10, f12
      );
      float f18 = TextShader.getFloatByStringFloat(text, 12.0F);
      float f19 = value + (value6 - f18) / 2.0F;
      float f20 = value3 + 7.0F;
      int l1 = Theme.foreground();
      float f17 = 12.0F;
      float f16 = f20;
      float f15 = f19;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f16, f15, l1, f17, value4, text, matrix4f);
   }

   public boolean isFlag5() {
      return this.flag5;
   }

   public void setFlag4(boolean flag) {
      this.flag4 = flag;
   }

   private void onString2(String text) {
      if (text != null && !text.isEmpty()) {
         if (Feature.mc != null && Feature.mc.keyboard != null) {
            Feature.mc.keyboard.setClipboard(text);
         }
      }
   }

   private float getFloat5() {
      return 224.0F;
   }

   private float getFloatByInt2(int count) {
      return count <= 0 ? 0.0F : 8.0F + count * 20.0F + (count - 1) * 4.0F;
   }

   private boolean isInt(int count) {
      return count > 6;
   }

   private float getFloatByIntFloat(int count, float value) {
      return Math.max(0.0F, this.getFloatByInt2(count) - value);
   }

   private float getFloatByFloat4(float value) {
      return this.value236 + EasingPresets.getFloatByFloat(value);
   }

   private List getList() {
      ArrayList arraylist = new ArrayList(this.list.size());
      ArrayList arraylist1 = this.flag4 ? null : new ArrayList(this.list.size());

      for (LabelData labeldata : this.list) {
         if (labeldata.isFlag()) {
            if (arraylist1 != null) {
               arraylist1.add(labeldata);
            }
         } else {
            arraylist.add(labeldata);
         }
      }

      if (arraylist1 != null && !arraylist1.isEmpty()) {
         arraylist.addAll(arraylist1);
         return arraylist;
      } else {
         return arraylist;
      }
   }

   private void update5() {
      if (this.colorSupplier != null) {
         float[] afloat = this.colorSupplier.get();
         if (afloat != null && afloat.length >= 2) {
            this.value235 = afloat[0];
            this.value236 = afloat[1];
         }
      }
   }

   private void onFloatMatrix4fFloatFloatFloat(float value, Matrix4f matrix4f, float value2, float value3, float value4) {
      float f = this.getFloat3();
      float f1 = this.getFloatByFloat2(value3);
      float f2 = this.getFloat4();
      float f3 = this.getFloatByFloat3(value3);
      int j = Theme.elevated();
      float f11 = 8.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f11, f, j, matrix4f, f3, f2, value, f1);
      int i = this.scrollAnimator.getCollection().size();
      boolean flag = this.isInt(i);
      float f4 = f + 4.0F;
      float f5 = flag ? f + f2 - 4.0F - 6.0F - 4.0F : f + f2 - 4.0F;
      float f6 = f5 - f4;
      float f7 = this.getFloatByIntFloat(i, f3);
      this.value270 = Math.clamp(this.value270, 0.0F, f7);
      if (this.value270 != this.value271) {
         this.tween8.setFloat2(this.value270);
         this.value271 = this.value270;
      }

      float f8 = this.tween8.getFloat();
      this.scrollAnimator.setFloat(f1 + 4.0F - f8);
      ScissorStack.onFloatFloatFloatFloat(f2, f3, f1, f);

      for (LabelEntry labelentry : (Iterable<LabelEntry>)(this.scrollAnimator.getCollection())) {
         float f9 = labelentry.animation.getFloat();
         if (!(f9 <= 0.001F)) {
            float f10 = labelentry.animation.getValue7();
            if (!(f10 + 20.0F < f1) && !(f10 > f1 + f3)) {
               float f12 = value * f9;
               LabelData labeldata = labelentry.labelData;
               this.onFloatLabelDataMatrix4fFloatFloatFloatFloatFloat(f12, labeldata, matrix4f, f6, f4, f10, value2, value4);
            }
         }
      }

      ScissorStack.update();
      if (flag) {
         this.onFloatFloatFloatFloatMatrix4fFloatFloatFloat(f7, f, f1, value, matrix4f, f8, f3, f2);
      }
   }

   private void onFloatLabelDataMatrix4fFloatFloatFloatFloatFloat(
      float value, LabelData labelData, Matrix4f matrix4f, float value2, float value3, float value4, float value5, float value6
   ) {
      Tween tween = this.map2.computeIfAbsent(labelData.getText(), var0 -> EasingPresets.getTween());
      boolean flag = value6 >= value3 && value6 <= value3 + value2 && value5 >= value4 && value5 <= value4 + 20.0F;
      tween.setFloat2(flag && !labelData.isFlag() ? 1.0F : 0.0F);
      float f = tween.getFloat();
      int i3 = Theme.surface();
      int j3 = Theme.elevated();
      float f12 = 0.85F;
      int j1 = j3;
      int i1 = i3;
      int i = AnimatedInt.getIntByIntFloatInt(j1, f12, i1);
      if (labelData.isFlag()) {
         i3 = Theme.surface();
      } else {
         int k1 = Theme.surface();
         i3 = AnimatedInt.getIntByIntFloatInt(i, f, k1);
      }

      int j = i3;
      float f21 = 3.0F;
      float f20 = 1.0F;
      float f19 = 0.0F;
      int l1 = 436207616;
      float f18 = 0.0F;
      byte b0 = 0;
      float f17 = 6.0F;
      float f16 = 6.0F;
      float f15 = 6.0F;
      float f14 = 6.0F;
      float f13 = 20.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value2, l1, j, value3, value4, f21, f14, f13, f20, matrix4f, value, b0, f16, f18, f15, f17, f19
      );
      float f1 = 14.0F;
      float f2 = value3 + 6.0F;
      float f3 = value4 + (20.0F - f1) / 2.0F;
      CategoryType categorytype3 = CategoryType.KEY;
      int i2 = Theme.mutedFg();
      CategoryType categorytype = categorytype3;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i2, matrix4f, f3, categorytype, f1, f2, f1);
      float f4 = 13.0F;
      float f5 = value3 + value2 - 6.0F - f4;
      float f6 = value4 + 3.0F;
      float f7 = f2 + f1 + 6.0F;
      float f8 = f5 - 6.0F;
      float f9 = value4 + 4.0F;
      String s2 = labelData.getText();
      float f29 = Math.max(0.0F, f8 - f7);
      float f23 = 12.0F;
      float f22 = f29;
      String s1 = s2;
      String s = TextTrimmer.getStringByFloatStringFloat2(f22, s1, f23);
      int k = labelData.isFlag() ? Theme.mutedFg() : Theme.foreground();
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s, f7, f9, 12.0F, k, value);
      if (labelData.isFlag()) {
         float f10 = value3 + value2 - 6.0F - 11.0F;
         float f11 = value4 + 6.0F;
         CategoryType categorytype4 = CategoryType.SUCCESS_CHECKBOX;
         int j2 = Theme.mutedFg();
         float f25 = 8.0F;
         float f24 = 11.0F;
         CategoryType categorytype1 = categorytype4;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j2, matrix4f, f11, categorytype1, f25, f10, f24);
      } else {
         Tween tween1 = this.map3.computeIfAbsent(labelData.getText(), var0 -> EasingPresets.getTween());
         boolean flag1 = value6 >= f5 && value6 <= f5 + f4 && value5 >= f6 && value5 <= f6 + 14.0F;
         tween1.setFloat2(flag1 ? 1.0F : 0.0F);
         i3 = Theme.mutedFg();
         j3 = Theme.foreground();
         float f26 = tween1.getFloat();
         int l2 = j3;
         int k2 = i3;
         int l = AnimatedInt.getIntByIntFloatInt(l2, f26, k2);
         float f28 = 14.0F;
         float f27 = 13.0F;
         CategoryType categorytype2 = CategoryType.TRASH;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, l, matrix4f, f6, categorytype2, f28, f5, f27);
      }
   }

   private void onFloatFloatFloatFloatMatrix4fFloatFloatFloat(float value, float value2, float value3, float value4, Matrix4f matrix4f, float value5, float value6, float value7) {
      float f = value2 + value7 - 4.0F - 6.0F;
      float f1 = value3 + 4.0F;
      float f2 = value3 + value6 - 4.0F;
      float f3 = f2 - f1 - 40.0F;
      float f4 = f1 + (value > 0.0F ? f3 * (value5 / value) : 0.0F);
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
         f6, j, i, f, f4, f11, f5, f7, f10, matrix4f, value4, b0, f5, f8, f5, f5, f9
      );
   }

   private LabelData getLabelDataByString(String text) {
      for (LabelData labeldata : this.list) {
         if (text.equals(labeldata.getText())) {
            return labeldata;
         }
      }

      return null;
   }

   public void setColorSupplier(ColorSupplier colorSupplier2) {
      this.colorSupplier = colorSupplier2;
      this.update5();
      this.flag5 = true;
      List listx = map.get(this.text3);
      this.list.clear();
      if (listx != null) {
         this.list.addAll(listx);
      }

      this.update3();
   }

   private void onFloatFloatMatrix4fFloatFloat(float value, float value2, Matrix4f matrix4f, float value3, float value4) {
      float f = this.getFloat3();
      float f1 = this.getFloatByFloat(value2);
      float f2 = this.getFloat4();
      int i = Theme.elevated();
      float f8 = 8.0F;
      float f7 = 32.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f8, f, i, matrix4f, f7, f2, value, f1);
      float f3 = this.getFloat();
      float f4 = f1 + 3.0F;
      float f5 = f + 4.0F;
      float f6 = f5 + f3 + 4.0F;
      Tween tween1 = this.tween7;
      Tween tween = this.tween5;
      String s = "Скопировать";
      this.onTweenFloatFloatFloatFloatStringFloatMatrix4fFloatTween(tween1, f5, value4, f4, value, s, value3, matrix4f, f3, tween);
      Object object = null;
      Tween tween2 = this.tween6;
      String s1 = "Создать";
      this.onTweenFloatFloatFloatFloatStringFloatMatrix4fFloatTween((Tween)object, f6, value4, f4, value, s1, value3, matrix4f, f3, tween2);
   }

   private void onMatrix4fFloatFloat(Matrix4f matrix4f, float value, float value2) {
      float f = this.value235 + 12.0F;
      float f1 = value + 12.0F;
      int i = Theme.foreground();
      float f2 = 14.0F;
      String s = "Приватные ключи";
      TextShader.onIntFloatFloatMatrix4fFloatFloatString(i, value2, f1, matrix4f, f, f2, s);
      CategoryType categorytype1 = CategoryType.CLOSE;
      float f10 = this.value235 + 276.0F - 12.0F - 8.0F;
      float f11 = value + 14.0F;
      int j = Theme.mutedFg();
      float f6 = 8.0F;
      float f5 = 8.0F;
      float f4 = f11;
      float f3 = f10;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, j, matrix4f, f4, categorytype, f6, f3, f5);
      f11 = f1 + 14.0F + 4.0F;
      float f12 = this.getFloat5();
      int k = Theme.mutedFg();
      float f9 = f12;
      float f8 = 12.0F;
      float f7 = f11;
      String s1 = "Управляйте своими приватными ключами.";
      TextShader.onFloatStringFloatFloatMatrix4fFloatFloatInt(value2, s1, f, f8, matrix4f, f9, f7, k);
   }
}
