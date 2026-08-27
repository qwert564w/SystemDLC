package client.gui.widget;

import client.api.Theme;
import client.data.Tween;
import client.render.ShapeShader;
import client.util.EasingPresets;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import org.joml.Matrix4f;

public class TabbedPanel extends Widget {
   private static final float value239 = 0.82F;
   private final Supplier<String> supplier;
   private final IntSupplier intSupplier;
   private final Runnable runnable;
   private final Tween tween4 = EasingPresets.getTween();
   private final TabPainter tabPainter = new TabPainter();

   public TabbedPanel(String text, IntSupplier intSupplier, Runnable runnable) {
      this(() -> text, intSupplier, runnable);
   }

   public TabbedPanel(Supplier supplier2, IntSupplier intSupplier2, Runnable runnable2) {
      this.supplier = supplier2;
      this.intSupplier = intSupplier2;
      this.runnable = runnable2;
      this.value237 = 184.0F;
      this.value238 = 32.0F;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count == 0 && this.isDoubleDouble(value, value2) && this.runnable != null) {
         this.runnable.run();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.flag = this.isDoubleDouble(value3, value2);
      this.tween4.setFloat2(this.flag ? 0.82F : 0.0F);
      float f = this.tween4.getFloat();
      if (f > 0.001F) {
         float f10 = this.value235;
         float f11 = this.value236;
         float f12 = this.value237;
         float f13 = this.value238;
         int k = Theme.elevated();
         float f8 = value * f;
         int i = k;
         float f7 = 8.0F;
         float f6 = f13;
         float f5 = f12;
         float f4 = f11;
         float f3 = f10;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, f3, i, matrix4f, f6, f5, f8, f4);
      }

      float f1 = this.value235 + 8.0F;
      float f2 = this.value236 + (this.value238 - 14.0F) / 2.0F;
      this.tabPainter.setString(this.supplier.get());
      TabPainter tabpainter = this.tabPainter;
      int j = this.intSupplier.getAsInt();
      float f9 = 14.0F;
      tabpainter.onMatrix4fFloatIntFloatFloatFloat(matrix4f, f1, j, f2, f9, value);
   }

   public Runnable getRunnable() {
      return this.runnable;
   }

   public void onFloat(float value) {
      this.tabPainter.setFloat(value);
   }
}
