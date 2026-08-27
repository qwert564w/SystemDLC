package client.gui.widget;

import client.api.Theme;
import client.data.TextTrimmer;
import client.enums.FontWeight;
import client.module.CategoryType;
import client.render.TextShader;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class FriendRow extends ListRow {
   private static final float value239 = 16.0F;
   private static final float value240 = 20.0F;
   private static final float value241 = 16.0F;
   private String text;
   private String text2;
   private String text3;
   private final ScrollState scrollState;
   private final TextInputController textInputController;
   private float value242;
   private float value243;
   private float value244;
   private float value245;
   private Consumer<String> consumer;
   private Consumer<String> consumer2;
   private BiConsumer<String, String> biConsumer;
   private BiConsumer<String, String> biConsumer2;

   public FriendRow(String text4, String text5, String text6) {
      Consumer<String> consumerx = this::onString;
      boolean flag = false;
      float f = 12.0F;
      this.textInputController = this.getTextInputControllerByBooleanFloatConsumer(flag, f, consumerx);
      this.text = text4;
      this.text2 = text5;
      this.text3 = text6;
      this.value237 = 300.0F;
      this.value238 = 78.0F;
      ScrollState scrollstate = new ScrollState();
      CategoryType categorytype2 = CategoryType.TRASH;
      Runnable runnable = () -> {
         if (this.consumer != null) {
            this.consumer.accept(this.text);
         }
      };
      String s = "Удалить друга";
      float f2 = 14.0F;
      float f1 = 13.0F;
      CategoryType categorytype = categorytype2;
      scrollstate = scrollstate.getScrollStateByStringRunnableFloatFloatCategoryType(s, runnable, f1, f2, categorytype);
      categorytype2 = CategoryType.COPY;
      Runnable runnable1 = () -> {
         if (this.consumer2 != null) {
            this.consumer2.accept(this.text);
         }
      };
      String s1 = "Скопировать ник";
      float f4 = 14.0F;
      float f3 = 14.0F;
      CategoryType categorytype1 = categorytype2;
      this.scrollState = scrollstate.getScrollStateByStringRunnableFloatFloatCategoryType(s1, runnable1, f3, f4, categorytype1);
   }

   public void setConsumer2(Consumer<String> consumer) {
      this.consumer2 = consumer;
   }

   public void setBiConsumer2(BiConsumer<String, String> biConsumer) {
      this.biConsumer2 = biConsumer;
   }

   public void setConsumer(Consumer<String> consumer2) {
      this.consumer = consumer2;
   }

   @Override
   protected void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      float f = 14.0F;
      this.onFloatMatrix4fFloat(f, matrix4f, value);
      this.onMatrix4fFloat(matrix4f, value);
      this.onFloatFloatFloatMatrix4f3(value, value2, value3, matrix4f);
   }

   public void setBiConsumer(BiConsumer<String, String> biConsumer2) {
      this.biConsumer = biConsumer2;
   }

   public void onStringStringStringString(String text4, String text5, String text6, String text7) {
      this.text = text4;
      this.text2 = text6;
      this.text3 = text7;
   }

   private void onString(String text3) {
      text3 = text3.trim();
      if (this.text != null) {
         String s = "SystemFriend";
         String s1 = !text3.isEmpty() && !text3.equals(s) ? text3 : "";
         String s2 = this.text2 != null && !this.text2.equals(s) ? this.text2 : "";
         if (!s1.equals(s2)) {
            if (this.biConsumer2 != null) {
               this.biConsumer2.accept(this.text, s1);
            }
         }
      }
   }

   private void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.value236 + this.value238 - 16.0F - this.scrollState.getFloat();
      float f1 = this.value235 + this.value237 - 18.0F;
      this.scrollState.getFloatByFloatFloatFloatMatrix4fFloatFloat(value2, value3, value, matrix4f, f, f1);
   }

   @Override
   protected ScrollState getScrollState() {
      return this.scrollState;
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (count == 0 && this.value244 > 0.0F) {
         float f3 = this.value245;
         float f2 = this.value244;
         float f1 = this.value243;
         float f = this.value242;
         if (isFloatFloatDoubleFloatFloatDouble(f, f1, value, f3, f2, value2)) {
            String s = this.text2 == null ? "" : this.text2;
            TextInputController textinputcontroller = this.textInputController;
            this.onStringTextInputController(s, textinputcontroller);
            return true;
         }
      }

      return false;
   }

   private void onMatrix4fFloat(Matrix4f matrix4f, float value) {
      float f = this.value235 + 16.0F;
      float f1 = this.value236 + 18.0F;
      float f13 = 13.0F;
      float f12 = 14.0F;
      float f11 = 20.0F;
      CategoryType categorytype = CategoryType.FRIENDS;
      Spacer.onCategoryTypeMatrix4fFloatFloatFloatFloatFloatFloat(categorytype, matrix4f, f, f13, f11, f1, value, f12);
      float f2 = f + 20.0F + 8.0F;
      float f3 = this.value236 + 16.0F;
      float f4 = this.value235 + this.value237 - 16.0F - f2;
      String s4 = this.text == null ? "" : this.text;
      float f21 = Math.min(f4, 140.0F);
      float f15 = 16.0F;
      float f14 = f21;
      String s2 = s4;
      String s = TextTrimmer.getStringByFloatStringFloat2(f14, s2, f15);
      float f5 = TextShader.getFloatByStringFloat(s, 16.0F);
      int i = Theme.foreground();
      float f16 = 16.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f3, f2, i, f16, value, s, matrix4f);
      float f6 = f2 + f5;
      String s1 = this.text2 != null && !this.text2.isEmpty() ? this.text2 : "SystemFriend";
      float f7 = f6 + 6.0F;
      float f8 = this.value235 + this.value237 - 16.0F - f7;
      if (f8 > 0.0F) {
         float f9 = this.value236 + 20.0F;
         TextInputController textinputcontroller1 = this.textInputController;
         int k = Theme.mutedFg();
         FontWeight fontweight = FontWeight.REGULAR;
         int j = k;
         float f17 = 12.0F;
         TextInputController textinputcontroller = textinputcontroller1;
         float f10 = EmptyRow.getFloatByIntFloatFloatFloatFloatMatrix4fFontWeightFloatStringTextInputController(
            j, f17, f7, f9, f8, matrix4f, fontweight, value, s1, textinputcontroller
         );
         this.value242 = f7 - 2.0F;
         this.value243 = this.value236 + 20.0F - 2.0F;
         this.value244 = Math.max(f10 + 4.0F, 30.0F);
         this.value245 = 16.0F;
      } else {
         this.value244 = 0.0F;
         this.value245 = 0.0F;
      }

      float f19 = this.value235 + 16.0F;
      float f20 = this.value236 + this.value238 - 16.0F - 12.0F;
      float f22 = this.value235 + this.value237 - 16.0F - f19;
      String s3 = this.text3;
      float f18 = f22;
      this.onMatrix4fFloatFloatStringFloatFloat(matrix4f, f20, f18, s3, f19, value);
   }

   public String getText() {
      return this.text;
   }
}
