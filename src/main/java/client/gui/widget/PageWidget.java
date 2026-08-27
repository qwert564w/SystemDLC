package client.gui.widget;

import client.gui.screen.ClickGuiScreen;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public abstract class PageWidget<C extends ListWidget, A extends Widget> extends ScrollableWidget {
   protected static final int value242 = 3;
   private static final float value243 = 2.0F;
   protected final List<C> list = new ArrayList<>();
   protected float value244;
   protected boolean flag4;
   private float value245;
   private float value246;

   protected PageWidget() {
      this.value237 = 963.0F;
      this.value244 = this.getFloat3();
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (!(value < this.value235) && !(value > this.value235 + this.value237) && !(value2 < this.value236) && !(value2 > this.value246)) {
         for (ListWidget listwidget : List.copyOf(this.list)) {
            if (listwidget.isIntDoubleDouble(count, value, value2)) {
               return true;
            }
         }

         Widget widget = this.getWidget();
         return widget != null && widget.isIntDoubleDouble(count, value, value2);
      } else {
         return false;
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.list.removeIf(ListWidget::check2);
      float f = this.getFloat();
      float f1 = this.value235 + 16.0F;
      float f2 = this.value236 + 16.0F - f;
      float f3 = this.value244 + 16.0F;
      float[] afloat = new float[3];
      float f4 = Math.max(0.0F, this.value238 - 2.0F);
      this.value246 = this.value236 + f4;
      float f9 = this.value237;
      float f8 = this.value236;
      float f7 = this.value235;
      ScissorStack.onFloatFloatFloatFloat(f9, f4, f8, f7);

      for (ListWidget listwidget : this.list) {
         if (listwidget.isFlag4()) {
            listwidget.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
         }
      }

      boolean flag = Math.abs(f - this.value245) > 0.001F;
      boolean flag1 = this.flag4 || flag || ClickGuiScreen.isFlag();
      this.value245 = f;
      Widget widget = this.getWidget();
      this.onWidgetBooleanFloatFloat(widget, flag1, f1, f2);
      widget.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      afloat[0] += widget.value238 + 16.0F;
      int i = 1;

      for (ListWidget listwidget1 : this.list) {
         if (!listwidget1.isFlag4()) {
            int j = i++ % 3;
            float f5 = f1 + j * f3;
            float f6 = f2 + afloat[j];
            if (flag1) {
               listwidget1.onFloatFloat4(f5, f6);
            } else {
               listwidget1.onFloatFloat2(f6, f5);
            }

            listwidget1.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
            afloat[j] += listwidget1.value238 + 16.0F;
         }
      }

      this.flag4 = false;
      ScissorStack.update();
      float f10 = 0.0F;

      for (float f11 : afloat) {
         if (f11 > f10) {
            f10 = f11;
         }
      }

      this.setFloat(f10);
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      for (ListWidget listwidget : this.list) {
         if (listwidget.check()) {
            return listwidget.isIntIntInt2(count, count2, count3);
         }
      }

      return this.check() ? this.getWidget().isIntIntInt2(count, count2, count3) : false;
   }

   protected abstract void onWidgetBooleanFloatFloat(Widget widget2, boolean flag, float value, float value2);

   @Override
   public boolean isIntChar(int count, char symbol) {
      for (ListWidget listwidget : this.list) {
         if (listwidget.check()) {
            return listwidget.isIntChar(count, symbol);
         }
      }

      return this.check() ? this.getWidget().isIntChar(count, symbol) : false;
   }

   protected abstract float getFloat3();

   protected abstract Widget getWidget();

   protected abstract void onFloat(float value);

   protected abstract boolean check();

   public void onFloatFloat4(float value, float value2) {
      this.value237 = value2;
      float f3 = this.getFloat3();
      float f2 = 358.0F;
      float f1 = f3;
      float f = EasingPresets.getFloatByFloatFloatFloat(f2, value, f1);
      if (!(Math.abs(f - this.value244) <= 0.001F)) {
         this.value244 = f;
         this.flag4 = true;
         this.onFloat(this.value244);

         for (ListWidget listwidget : this.list) {
            listwidget.setFloat(this.value244);
         }

         this.setFloat(this.value241);
      }
   }
}
