package client.gui.widget;

import java.util.function.Consumer;
import org.joml.Matrix4f;

public final class WidgetState {
   private static Widget widget;
   private static boolean flag;

   private WidgetState() {
   }

   public static void setWidget(Widget widget2) {
      if (widget == widget2) {
         widget = null;
      }
   }

   public static boolean isIntIntInt(int count, int count2, int count3) {
      return isConsumer(var3 -> var3.isIntIntInt(count, count2, count3));
   }

   public static boolean check() {
      return widget != null && !flag;
   }

   public static Widget getWidget() {
      return widget;
   }

   private static boolean isConsumer(Consumer<Widget> consumer) {
      if (widget == null) {
         return false;
      } else {
         consumer.accept(widget);
         return true;
      }
   }

   public static boolean isCharInt(char symbol, int count) {
      return isConsumer(var2 -> var2.isIntChar(count, symbol));
   }

   public static boolean isIntDoubleDouble(int count, double value, double value2) {
      return widget != null && widget.isIntDoubleDouble(count, value, value2);
   }

   public static void onMatrix4fFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3) {
      if (widget != null) {
         flag = true;

         try {
            widget.onFloatFloatFloatMatrix4f2(value2, value, value3, matrix4f);
         } finally {
            flag = false;
         }
      }
   }

   public static void setWidget2(Widget widget2) {
      if (widget != null && widget != widget2) {
         widget.update2();
      }

      widget = widget2;
   }

   public static boolean isWidgetAsBoolean() {
      return widget != null;
   }

   public static boolean isIntIntInt2(int count, int count2, int count3) {
      return isConsumer(var3 -> var3.isIntIntInt2(count3, count, count2));
   }

   public static boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      return isConsumer(var6 -> var6.isDoubleDoubleDouble(value3, value, value2));
   }

   public static boolean isDoubleIntDoubleDoubleDouble(double value, int count, double value2, double value3, double value4) {
      return isConsumer(var9 -> var9.isDoubleDoubleIntDoubleDouble(value, value4, count, value2, value3));
   }

   public static boolean isDoubleDoubleInt(double value, double value2, int count) {
      return isConsumer(var5 -> var5.isDoubleDoubleInt(value, value2, count));
   }
}
