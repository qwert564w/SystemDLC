package client.gui.widget;

import client.api.ColorSupplier;
import client.api.Theme;
import client.concurrent.Translations;
import client.data.ThemeConfig;
import client.enums.Language;
import client.render.ShapeShader;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public class QuickMenu extends ButtonWidget {
   private final List<TabbedPanel> list = new ArrayList<>();
   private Runnable runnable;

   public QuickMenu() {
      this.value237 = 192.0F;
      this.list.add(new TabbedPanel("Сханге Тхеме", Theme::foreground, this::update14));
      this.list.add(new TabbedPanel("Сханге Лангуаге", Theme::foreground, this::update11));
      this.list.add(new TabbedPanel("Панис & Слеан", Theme::foreground, this::update12));
      this.list.add(new TabbedPanel("Слозе", Theme::danger, this::update13));
      this.value238 = this.getFloat6();
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      float f = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + this.value237 / 2.0F, f + this.value238 / 2.0F);
      float f10 = this.value235;
      float f11 = this.value237;
      float f12 = this.value238;
      int i1 = Theme.background();
      int j1 = Theme.border();
      float f7 = 1.0F;
      int k = j1;
      int j = i1;
      float f6 = 8.0F;
      float f5 = f12;
      float f4 = f11;
      float f3 = f10;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f7, f4, value2, matrix4f, f, j, f6, f3, k, f5);
      float f1 = this.value235 + 4.0F;
      float f2 = f + 4.0F;

      for (int i = 0; i < this.list.size(); i++) {
         TabbedPanel tabbedpanel = this.list.get(i);
         tabbedpanel.setValue237(184.0F);
         tabbedpanel.onFloatFloat2(f2, f1);
         tabbedpanel.onFloatFloatFloatMatrix4f(value2, value, value3, matrix4f);
         f2 += 32.0F;
         if (i < this.list.size() - 1) {
            f2 += 4.0F;
            int l = Theme.border();
            float f9 = 1.0F;
            float f8 = 184.0F;
            ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value2, f9, f1, f2, f8, matrix4f, l);
            f2 += 5.0F;
         }
      }
   }

   private void update11() {
      Translations translations = Translations.getInstance();
      if (translations.getLanguage() == Language.RU) {
         translations.update3();
      }

      translations.update();
      this.update4();
   }

   private void update12() {
      if (this.runnable != null) {
         this.runnable.run();
      }

      this.update4();
   }

   private void update13() {
      this.update4();
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      for (TabbedPanel tabbedpanel : this.list) {
         if (tabbedpanel.isIntDoubleDouble(count, value, value2)) {
            return true;
         }
      }

      return true;
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   private float getFloat6() {
      int i = this.list.size();
      float f = i * 32.0F;
      float f1 = Math.max(0, i - 1) * 9.0F;
      return 8.0F + f + f1;
   }

   @Override
   public void setColorSupplier2(ColorSupplier colorSupplier) {
      super.setColorSupplier2(() -> {
         float[] afloat = colorSupplier.get();
         return new float[]{afloat[0], afloat[1] - this.value238 / 2.0F};
      });
   }

   private void update14() {
      ThemeConfig.update();
      this.update4();
   }
}
