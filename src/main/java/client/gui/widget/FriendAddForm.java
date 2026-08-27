package client.gui.widget;

import client.api.Theme;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class FriendAddForm extends FormWidget {
   private static final float value239 = 220.0F;
   private static final float value240 = 36.0F;
   private static final float value241 = 6.0F;
   private final TextField textField;
   private Consumer<String> consumer;

   public FriendAddForm() {
      this.value237 = 300.0F;
      this.value238 = 78.0F;
      this.textField = new TextField(220.0F, 36.0F, "Введите никнейм...", 14.0F, 12.0F, 36.0F);
      this.textField.onRunnable(this::update4);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      return this.textField.isIntDoubleDouble(count, value, value2);
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      return this.textField.isIntIntInt2(count, count2, count3);
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      return this.textField.isIntChar(count, symbol);
   }

   public TextField getTextField() {
      return this.textField;
   }

   public void setConsumer(Consumer<String> consumer2) {
      this.consumer = consumer2;
   }

   private void update4() {
      String s = this.textField.getString().trim();
      if (!s.isEmpty()) {
         if (this.consumer != null) {
            this.consumer.accept(s);
         }

         this.textField.update3();
         this.textField.setBoolean(false);
      }
   }

   @Override
   protected float getFloat() {
      return 14.0F;
   }

   @Override
   protected void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      float f = 246.0F;
      float f1 = this.value235 + (this.value237 - f) / 2.0F;
      float f2 = this.value236 + (this.value238 - 36.0F) / 2.0F;
      float f3 = f2 + 8.0F;
      float f11 = 13.0F;
      float f10 = 14.0F;
      float f9 = 20.0F;
      CategoryType categorytype = CategoryType.FRIENDS;
      Spacer.onCategoryTypeMatrix4fFloatFloatFloatFloatFloatFloat(categorytype, matrix4f, f1, f11, f9, f3, value3, f10);
      float f4 = f1 + 20.0F + 6.0F;
      this.textField.setValue237(220.0F);
      this.textField.onFloatFloat2(f2, f4);
      this.textField.onFloatFloatFloatMatrix4f(value3, value2, value, matrix4f);
      float f5 = f4 + 220.0F - 12.0F - 24.0F;
      float f6 = f2 + 6.0F;
      int i = Theme.elevated();
      float f14 = 4.0F;
      float f13 = 24.0F;
      float f12 = 24.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f14, f5, i, matrix4f, f13, f12, value3, f6);
      float f7 = f5 + 5.335F;
      float f8 = f6 + 5.335F;
      CategoryType categorytype2 = CategoryType.INFO;
      int j = Theme.mutedFg();
      float f16 = 13.33F;
      float f15 = 13.33F;
      CategoryType categorytype1 = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, j, matrix4f, f8, categorytype1, f16, f7, f15);
      double d3 = value;
      double d2 = value2;
      float f18 = 24.0F;
      float f17 = 24.0F;
      double d1 = d2;
      double d0 = d3;
      if (isFloatFloatDoubleFloatFloatDouble(f5, f6, d1, f18, f17, d0)) {
         float f20 = 24.0F;
         float f19 = 24.0F;
         String s = "Введите ник игрока для добавления в друзья";
         HeaderPainter.onFloatStringFloatFloatFloat(f5, s, f6, f19, f20);
      }
   }

   public void setFloat(float value) {
      this.value237 = value;
   }
}
