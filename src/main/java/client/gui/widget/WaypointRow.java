package client.gui.widget;

import client.api.ConfigChangeCallback;
import client.api.Theme;
import client.concurrent.WaypointStore;
import client.data.TextTrimmer;
import client.data.Tween;
import client.data.Waypoint;
import client.enums.FontWeight;
import client.module.CategoryType;
import client.module.Feature;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.EasingPresets;
import client.util.IntPredicateUtil;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import org.joml.Matrix4f;

public class WaypointRow extends ListRow {
   private static final float value239 = 14.0F;
   private Waypoint waypoint;
   private final ToggleButton toggleButton;
   private final ScrollState scrollState;
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat2(1.0F, 0.22F);
   private static final IntPredicate intPredicate = IntPredicateUtil.intPredicate;
   private final TextInputController textInputController;
   private final TextInputController textInputController2;
   private final TextInputController textInputController3;
   private final TextInputController textInputController4;
   private float value240;
   private float value241;
   private float value242;
   private float value243;
   private float value244;
   private float value245;
   private float value246;
   private float value247;
   private float value248;
   private float value249;
   private Consumer<Waypoint> consumer;
   private Consumer<Waypoint> consumer2;
   private Consumer<Waypoint> consumer3;
   private BiConsumer<Waypoint, String> biConsumer;
   private ConfigChangeCallback configChangeCallback;

   public WaypointRow(Waypoint waypoint2) {
      Consumer<String> consumerx = this::onString4;
      boolean flag = true;
      float f = 16.0F;
      this.textInputController = this.getTextInputControllerByBooleanFloatConsumer(flag, f, consumerx);
      Consumer<String> consumer1 = var1x -> this.onStringInt(var1x, 0);
      boolean flag1 = true;
      float f1 = 14.0F;
      this.textInputController2 = this.getTextInputControllerByBooleanFloatConsumer(flag1, f1, consumer1).getTextInputControllerByIntPredicate(intPredicate);
      Consumer<String> consumer2x = var1x -> this.onStringInt(var1x, 1);
      boolean flag2 = true;
      float f2 = 14.0F;
      this.textInputController3 = this.getTextInputControllerByBooleanFloatConsumer(flag2, f2, consumer2x).getTextInputControllerByIntPredicate(intPredicate);
      Consumer<String> consumer3x = var1x -> this.onStringInt(var1x, 2);
      boolean flag3 = true;
      float f3 = 14.0F;
      this.textInputController4 = this.getTextInputControllerByBooleanFloatConsumer(flag3, f3, consumer3x).getTextInputControllerByIntPredicate(intPredicate);
      this.waypoint = waypoint2;
      this.value237 = 300.0F;
      this.value238 = 116.0F;
      this.toggleButton = new ToggleButton(waypoint2 != null && waypoint2.isFlag());
      ScrollState scrollstate = new ScrollState();
      CategoryType categorytype2 = CategoryType.TRASH;
      Runnable runnable = () -> this.onConsumer(this.consumer);
      String s = "Удалить вейпоинт";
      float f5 = 14.0F;
      float f4 = 13.0F;
      CategoryType categorytype = categorytype2;
      scrollstate = scrollstate.getScrollStateByStringRunnableFloatFloatCategoryType(s, runnable, f4, f5, categorytype);
      categorytype2 = CategoryType.COPY;
      Runnable runnable1 = () -> this.onConsumer(this.consumer2);
      String s1 = "Скопировать координаты";
      float f7 = 14.0F;
      float f6 = 14.0F;
      CategoryType categorytype1 = categorytype2;
      this.scrollState = scrollstate.getScrollStateByStringRunnableFloatFloatCategoryType(s1, runnable1, f6, f7, categorytype1);
      this.tween5.setFloat(waypoint2 != null && waypoint2.isFlag() ? 1.0F : 0.5F);
   }

   public void setConsumer(Consumer<Waypoint> consumer2) {
      this.consumer = consumer2;
   }

   private void onMatrix4fFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3) {
      float f = this.value236 + this.value238 - 12.0F - this.scrollState.getFloat();
      float f1 = this.value235 + this.value237 - 16.0F;
      float f2 = this.scrollState.getFloatByFloatFloatFloatMatrix4fFloatFloat(value2, value, value3, matrix4f, f, f1);
      String s = this.waypoint != null && this.waypoint.getText3() != null ? this.waypoint.getText3() : "";
      if (!s.isEmpty()) {
         float f3 = this.value235 + 16.0F;
         float f4 = Math.max(0.0F, f2 - 8.0F - f3);
         float f5 = f + (this.scrollState.getFloat() - 12.0F) / 2.0F + 1.0F;
         float f6 = 12.0F;
         String s1 = TextTrimmer.getStringByFloatStringFloat2(f4, s, f6);
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s1, f3, f5, 12.0F, Theme.mutedFg(), value3);
      }
   }

   public void setConsumer2(Consumer<Waypoint> consumer) {
      this.consumer2 = consumer;
   }

   public void setConsumer3(Consumer<Waypoint> consumer) {
      this.consumer3 = consumer;
   }

   private void onStringInt(String text, int count) {
      if (this.waypoint != null) {
         int i = this.waypoint.getValue();
         int j = this.waypoint.getValue2();
         int k = this.waypoint.getValue3();
         int[] aint = IntPredicateUtil.getIntArrayByString(text);
         if (aint != null) {
            i = aint[0];
            j = aint[1];
            k = aint[2];
         } else {
            Integer integer = IntPredicateUtil.getIntegerByString(text);
            if (integer == null) {
               return;
            }

            switch (count) {
               case 0:
                  i = integer;
                  break;
               case 1:
                  j = integer;
                  break;
               case 2:
                  k = integer;
            }
         }

         if (i != this.waypoint.getValue() || j != this.waypoint.getValue2() || k != this.waypoint.getValue3()) {
            if (this.configChangeCallback != null) {
               this.configChangeCallback.onChange(this.waypoint, i, j, k);
            }
         }
      }
   }

   public void setBiConsumer(BiConsumer<Waypoint, String> biConsumer2) {
      this.biConsumer = biConsumer2;
   }

   public void setConfigChangeCallback(ConfigChangeCallback configChangeCallback2) {
      this.configChangeCallback = configChangeCallback2;
   }

   private void onFloatMatrix4f(float value, Matrix4f matrix4f) {
      float f = this.value236 + 44.0F;
      float f1 = this.value235 + (this.value237 - 269.0F) / 2.0F;
      int i = Theme.elevated();
      float f9 = 8.0F;
      float f8 = 34.0F;
      float f7 = 269.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f9, f1, i, matrix4f, f8, f7, value, f);
      float f2 = f1 + 4.0F;
      float f3 = f + 4.0F;
      int j2 = Theme.background();
      float f19 = 3.0F;
      float f18 = 1.0F;
      float f17 = 0.0F;
      int k = 436207616;
      float f16 = 0.0F;
      byte b0 = 0;
      int j = j2;
      float f15 = 6.0F;
      float f14 = 6.0F;
      float f13 = 6.0F;
      float f12 = 6.0F;
      float f11 = 26.0F;
      float f10 = 261.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f10, k, j, f2, f3, f19, f12, f11, f18, matrix4f, value, b0, f14, f16, f13, f15, f17
      );
      this.value244 = f2;
      this.value245 = f3;
      this.value246 = this.value244 + 87.0F + 0.0F;
      this.value247 = f3;
      this.value248 = this.value246 + 87.0F + 0.0F;
      this.value249 = f3;
      float f4 = 5.0F;
      float f5 = 26.0F - f4 * 2.0F;
      float f6 = f3 + f4;
      float f27 = this.value246;
      int l = Theme.border();
      float f21 = 1.0F;
      float f20 = f27;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f5, f20, f6, f21, matrix4f, l);
      f27 = this.value248;
      int i1 = Theme.border();
      float f23 = 1.0F;
      float f22 = f27;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f5, f22, f6, f23, matrix4f, i1);
      int i2 = this.waypoint == null ? 0 : this.waypoint.getValue();
      TextInputController textinputcontroller = this.textInputController2;
      float f24 = this.value244;
      int j1 = i2;
      String s = "X";
      Divider.onMatrix4fFloatTextInputControllerStringFloatFloatInt(matrix4f, f3, textinputcontroller, s, value, f24, j1);
      i2 = this.waypoint == null ? 0 : this.waypoint.getValue2();
      TextInputController textinputcontroller1 = this.textInputController3;
      float f25 = this.value246;
      int k1 = i2;
      String s1 = "Y";
      Divider.onMatrix4fFloatTextInputControllerStringFloatFloatInt(matrix4f, f3, textinputcontroller1, s1, value, f25, k1);
      i2 = this.waypoint == null ? 0 : this.waypoint.getValue3();
      TextInputController textinputcontroller2 = this.textInputController4;
      float f26 = this.value248;
      int l1 = i2;
      String s2 = "Z";
      Divider.onMatrix4fFloatTextInputControllerStringFloatFloatInt(matrix4f, f3, textinputcontroller2, s2, value, f26, l1);
   }

   private static String getStringByDouble(double value) {
      return value < 1000.0 ? (int)Math.round(value) + "m" : String.format("%.1fkm", value / 1000.0);
   }

   private String getString() {
      if (this.waypoint == null) {
         return "";
      } else {
         String s = WaypointStore.getString2();
         if (s == null) {
            return "";
         } else if (!s.equals(this.waypoint.getText3())) {
            return "";
         } else if (Feature.mc != null && Feature.mc.player != null) {
            double d0 = Feature.mc.player.getX() - this.waypoint.getValue();
            double d1 = Feature.mc.player.getY() - this.waypoint.getValue2();
            double d2 = Feature.mc.player.getZ() - this.waypoint.getValue3();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            return getStringByDouble(d3);
         } else {
            return "";
         }
      }
   }

   private void onMatrix4fFloatFloatFloat2(Matrix4f matrix4f, float value, float value2, float value3) {
      float f = this.value235 + 16.0F;
      float f1 = this.value236 + 16.0F;
      CategoryType categorytype1 = CategoryType.WAYPOINT;
      float f16 = f1 + 1.0F;
      int i = Theme.foreground();
      float f14 = 18.0F;
      float f13 = 18.0F;
      float f12 = f16;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, i, matrix4f, f12, categorytype, f14, f, f13);
      float f2 = f + 18.0F + 8.0F;
      float f3 = this.value235 + this.value237 - 16.0F - this.toggleButton.getValue237();
      float f4 = f1 + (18.0F - this.toggleButton.getValue238()) / 2.0F - 1.0F;
      float f5 = f1 + 1.0F - 1.0F;
      String s = this.waypoint != null && this.waypoint.getText2() != null ? this.waypoint.getText2() : "";
      String s1 = this.getString();
      float f6 = s1.isEmpty() ? 0.0F : TextShader.getFloatByStringFloat(s1, 14.0F);
      float f7 = s1.isEmpty() ? 0.0F : 8.0F;
      float f8 = Math.max(0.0F, f3 - 8.0F - f2 - f7 - f6);
      this.value240 = f2 - 2.0F;
      this.value241 = f1;
      this.value242 = f8 + 4.0F;
      this.value243 = 18.0F;
      TextInputController textinputcontroller1 = this.textInputController;
      int k = Theme.foreground();
      FontWeight fontweight = FontWeight.MEDIUM;
      int j = k;
      float f15 = 16.0F;
      TextInputController textinputcontroller = textinputcontroller1;
      float f9 = EmptyRow.getFloatByIntFloatFloatFloatFloatMatrix4fFontWeightFloatStringTextInputController(
         j, f15, f2, f5, f8, matrix4f, fontweight, value2, s, textinputcontroller
      );
      if (!s1.isEmpty()) {
         float f10 = f2 + f9 + 8.0F;
         float f11 = f1 + 2.0F - 1.0F;
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s1, f10, f11, 14.0F, Theme.mutedFg(), value2);
      }

      this.toggleButton.onFloatFloat2(f4, f3);
      if (this.waypoint != null) {
         this.toggleButton.setBoolean2(this.waypoint.isFlag());
      }

      this.toggleButton.onFloatFloatFloatMatrix4f(value2, value, value3, matrix4f);
   }

   @Override
   protected void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      this.tween5.setFloat2(this.waypoint != null && this.waypoint.isFlag() ? 1.0F : 0.5F);
      float f = value * this.tween5.getFloat();
      float f1 = 14.0F;
      this.onFloatMatrix4fFloat(f1, matrix4f, f);
      this.onMatrix4fFloatFloatFloat2(matrix4f, value2, f, value3);
      this.onFloatMatrix4f(f, matrix4f);
      this.onMatrix4fFloatFloatFloat(matrix4f, value3, value2, f);
   }

   private void onConsumer(Consumer consumer) {
      if (consumer != null && this.waypoint != null) {
         consumer.accept(this.waypoint);
      }
   }

   public Waypoint getWaypoint() {
      return this.waypoint;
   }

   public void setWaypoint(Waypoint waypoint2) {
      this.waypoint = waypoint2;
      if (waypoint2 != null) {
         this.toggleButton.setBoolean2(waypoint2.isFlag());
      }
   }

   private void onString4(String text) {
      if (this.waypoint != null) {
         String s = text == null ? "" : text.trim();
         if (!s.isEmpty()) {
            if (!s.equals(this.waypoint.getText2())) {
               if (this.biConsumer != null) {
                  this.biConsumer.accept(this.waypoint, s);
               }
            }
         }
      }
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (count == 0) {
         float f20 = this.toggleButton.getValue235();
         float f21 = this.toggleButton.getValue236();
         float f3 = this.toggleButton.getValue238();
         float f2 = this.toggleButton.getValue237();
         float f1 = f21;
         float f = f20;
         if (isFloatFloatDoubleFloatFloatDouble(f, f1, value, f3, f2, value2)) {
            boolean flag = !this.toggleButton.isFlag4();
            this.toggleButton.setBoolean2(flag);
            if (this.waypoint != null) {
               this.waypoint.setFlag(flag);
               if (this.consumer3 != null) {
                  this.consumer3.accept(this.waypoint);
               }
            }

            return true;
         }
      }

      if (count != 0) {
         return false;
      } else {
         float f7 = this.value243;
         float f6 = this.value242;
         float f5 = this.value241;
         float f4 = this.value240;
         if (isFloatFloatDoubleFloatFloatDouble(f4, f5, value, f7, f6, value2)) {
            String s = this.waypoint != null ? this.waypoint.getText2() : "";
            TextInputController textinputcontroller = this.textInputController;
            this.onStringTextInputController(s, textinputcontroller);
            return true;
         } else {
            float f11 = 26.0F;
            float f10 = 87.0F;
            float f9 = this.value245;
            float f8 = this.value244;
            if (isFloatFloatDoubleFloatFloatDouble(f8, f9, value, f11, f10, value2)) {
               String s1 = this.waypoint == null ? "0" : Integer.toString(this.waypoint.getValue());
               TextInputController textinputcontroller1 = this.textInputController2;
               this.onStringTextInputController(s1, textinputcontroller1);
               return true;
            } else {
               float f15 = 26.0F;
               float f14 = 87.0F;
               float f13 = this.value247;
               float f12 = this.value246;
               if (isFloatFloatDoubleFloatFloatDouble(f12, f13, value, f15, f14, value2)) {
                  String s2 = this.waypoint == null ? "0" : Integer.toString(this.waypoint.getValue2());
                  TextInputController textinputcontroller2 = this.textInputController3;
                  this.onStringTextInputController(s2, textinputcontroller2);
                  return true;
               } else {
                  float f19 = 26.0F;
                  float f18 = 87.0F;
                  float f17 = this.value249;
                  float f16 = this.value248;
                  if (isFloatFloatDoubleFloatFloatDouble(f16, f17, value, f19, f18, value2)) {
                     String s3 = this.waypoint == null ? "0" : Integer.toString(this.waypoint.getValue3());
                     TextInputController textinputcontroller3 = this.textInputController4;
                     this.onStringTextInputController(s3, textinputcontroller3);
                     return true;
                  } else {
                     return false;
                  }
               }
            }
         }
      }
   }

   @Override
   protected ScrollState getScrollState() {
      return this.scrollState;
   }
}
