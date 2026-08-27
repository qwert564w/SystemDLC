package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.data.AnimatedInt;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class ConfigImportForm extends FormWidget {
   private static final long time = 2500000000L;
   private final TextField textField;
   private final AnimatedInt animatedInt = new AnimatedInt(Theme.background(), 0.22F);
   private long time2;
   private Consumer<String> consumer;
   private Runnable runnable;

   public ConfigImportForm() {
      this.value237 = 300.0F;
      this.value238 = 144.0F;
      this.textField = new TextField(220.0F, 36.0F, "Или вставьте ключ...", 14.0F, 12.0F, 36.0F);
      this.textField.onRunnable(this::update5);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.textField.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (count == 0 && this.isDoubleDouble(value, value2)) {
         if (!this.textField.getString().trim().isEmpty()) {
            this.update5();
         } else if (this.runnable != null) {
            this.runnable.run();
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void update3() {
      this.textField.setValue237(this.getFloat2());
   }

   private float getFloat2() {
      return Math.max(0.0F, this.value237 - 80.0F);
   }

   public void update4() {
      this.time2 = System.nanoTime() + 2500000000L;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      return this.textField.isIntIntInt2(count, count2, count3);
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      return this.textField.isIntChar(count, symbol);
   }

   public void setConsumer(Consumer<String> consumer2) {
      this.consumer = consumer2;
   }

   public TextField getTextField() {
      return this.textField;
   }

   public void setFloat(float value) {
      this.value237 = value;
      this.textField.setValue237(this.getFloat2());
   }

   private void update5() {
      String s = this.textField.getString().trim();
      if (!s.isEmpty() && this.consumer != null) {
         this.consumer.accept(s);
         this.textField.update3();
         this.textField.setBoolean(false);
      }
   }

   private boolean check() {
      return System.nanoTime() < this.time2;
   }

   @Override
   protected float getFloat() {
      return 14.0F;
   }

   @Override
   protected void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      float f = this.value235 + 40.0F;
      float f1 = this.value236 + 41.0F;
      float f11 = 13.0F;
      float f10 = 14.0F;
      float f9 = 20.0F;
      CategoryType categorytype = CategoryType.CLOUDS;
      Spacer.onCategoryTypeMatrix4fFloatFloatFloatFloatFloatFloat(categorytype, matrix4f, f, f11, f9, f1, value3, f10);
      String s = Translations.getInstance().getStringByString2("Создать новый конфиг");
      float f2 = f + 20.0F + 6.0F;
      float f3 = f1 + 2.0F;
      int j = Theme.foreground();
      float f12 = 16.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f3, f2, j, f12, value3, s, matrix4f);
      float f4 = f1 + 20.0F + 8.0F;
      this.textField.onFloatFloat2(f4, f);
      this.textField.onFloatFloatFloatMatrix4f(value3, value2, value, matrix4f);
      float f5 = f + this.getFloat2() - 12.0F - 24.0F;
      float f6 = f4 + 6.0F;
      int k = Theme.elevated();
      float f15 = 4.0F;
      float f14 = 24.0F;
      float f13 = 24.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f15, f5, k, matrix4f, f14, f13, value3, f6);
      float f7 = f5 + 5.335F;
      float f8 = f6 + 5.335F;
      boolean flag = this.check();
      this.animatedInt.setInt(flag ? Theme.danger() : Theme.mutedFg());
      int i = this.animatedInt.getInt2();
      float f17 = 13.33F;
      float f16 = 13.33F;
      CategoryType categorytype1 = CategoryType.INFO;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, f8, categorytype1, f17, f7, f16);
      if (flag) {
         float f19 = 24.0F;
         float f18 = 24.0F;
         String s1 = "Такого ключа не существует";
         HeaderPainter.onFloatFloatStringFloatFloat(f18, f19, s1, f5, f6);
      } else {
         double d2 = value;
         double d3 = value2;
         float f21 = 24.0F;
         float f20 = 24.0F;
         double d1 = d3;
         double d0 = d2;
         if (isFloatFloatDoubleFloatFloatDouble(f5, f6, d1, f21, f20, d0)) {
            float f23 = 24.0F;
            float f22 = 24.0F;
            String s2 = "Вставьте ключ конфигурации для импорта";
            HeaderPainter.onFloatStringFloatFloatFloat(f5, s2, f6, f22, f23);
         }
      }
   }
}
