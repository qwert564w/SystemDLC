package client.gui.widget;

import client.api.Theme;
import client.data.HudConfig;
import client.data.Tween;
import client.module.CategoryType;
import client.render.MatrixUtil;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.EasingPresets;
import client.util.ScrollOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class ProfileBar extends Widget {
   private static final float value239 = 16.0F;
   private static final float value240 = 20.0F;
   private static final float value241 = 14.0F;
   private static final float value242 = 6.0F;
   private static final float value243 = 0.001F;
   private static final float value244 = 0.5F;
   private static final float value245 = 0.22F;
   private static final String text = "Global";
   private final HudConfig hudConfig = HudConfig.getHudConfig();
   private final List<PanelWidget> list = new ArrayList<>();
   private final Map<PanelWidget, Tween> map = new HashMap<>();
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.22F);
   private final ToggleButton toggleButton;
   private boolean flag4;

   public ProfileBar() {
      this.value237 = 301.0F;
      this.toggleButton = new ToggleButton(this.hudConfig.check());
      this.toggleButton.setRunnable(() -> this.hudConfig.getSinhronizaciya().setBoolean(this.toggleButton.isFlag4()));
      this.update4();
      this.value238 = this.getFloat2();
      this.tween4.setFloat2(1.0F);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      return this.toggleButton.isIntDoubleDouble(count, value, value2) ? true : this.isPredicate(var5 -> var5.isIntDoubleDouble(count, value, value2));
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      return this.isPredicate(var3x -> var3x.isIntIntInt(count, count2, count3));
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   private void update4() {
      this.list.clear();
      float f = this.getFloat();

      for (Setting setting : this.hudConfig.getList()) {
         if (setting != this.hudConfig.getSinhronizaciya()) {
            PanelWidget panelwidget = this.getPanelWidgetBySetting(setting);
            if (panelwidget != null) {
               panelwidget.setFloat(f);
               this.list.add(panelwidget);
            }
         }
      }

      this.map.keySet().retainAll(this.list);

      for (PanelWidget panelwidget1 : this.list) {
         this.map.computeIfAbsent(panelwidget1, this::getTweenByPanelWidget);
      }
   }

   private float getFloat() {
      return this.value237 - 32.0F;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.update5();
      this.value238 = this.getFloat2();
      float f = this.value235 + 16.0F;
      float f22 = this.value235;
      float f23 = this.value236;
      float f24 = this.value237;
      float f25 = this.value238;
      int j1 = Theme.background();
      int k1 = Theme.border();
      float f18 = 6.0F;
      float f17 = 2.0F;
      float f16 = 0.0F;
      int k = 436207616;
      float f15 = 1.0F;
      int j = k1;
      int i = j1;
      float f14 = 14.0F;
      float f13 = 14.0F;
      float f12 = 14.0F;
      float f11 = 14.0F;
      float f10 = f25;
      float f9 = f24;
      float f8 = f23;
      float f7 = f22;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f9, k, i, f7, f8, f18, f11, f10, f17, matrix4f, value, j, f13, f15, f12, f14, f16
      );
      float f1 = this.value236 + 16.0F;
      float f2 = this.getFloatByFloatFloatMatrix4f(value, f, matrix4f);
      float f3 = f1 + 4.0F;
      int l = Theme.foreground();
      float f19 = 16.0F;
      String s = "Global";
      TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value, s, f19, f2, l, f3, matrix4f);
      this.toggleButton.setBoolean2(this.hudConfig.check());
      float f4 = f + this.getFloat() - 28.0F;
      float f5 = f1 + 4.0F;
      this.toggleButton.onFloatFloat2(f5, f4);
      this.toggleButton.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      float f6 = f1 + 24.0F + 16.0F;
      f24 = this.getFloat();
      int i1 = Theme.border();
      float f21 = 1.0F;
      float f20 = f24;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f21, f, f6, f20, matrix4f, i1);
      this.onFloatFloat4(f, f6 + 1.0F + 16.0F);
      this.onFloatMatrix4fFloatFloat(value3, matrix4f, value, value2);
   }

   private void update5() {
      for (PanelWidget panelwidget : this.list) {
         Tween tween = this.map.get(panelwidget);
         if (tween != null) {
            tween.setFloat2(panelwidget.getSetting().isVisible() ? 1.0F : 0.0F);
            tween.getFloat();
         }
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f2(float value, float value2, float value3, Matrix4f matrix4f) {
      ScrollOffset.onFloatFloat(0.0F, UiContext.getInstance().getValue238());

      try {
         for (PanelWidget panelwidget : this.list) {
            float f = this.getFloatByPanelWidget(panelwidget);
            if (!(f <= 0.001F)) {
               float f1 = value3 * f;
               panelwidget.onFloatFloatFloatMatrix4f2(value, value2, f1, matrix4f);
            }
         }
      } finally {
         ScrollOffset.setFlag();
      }
   }

   public void update6() {
      for (PanelWidget panelwidget : this.list) {
         if (panelwidget.check2()) {
            panelwidget.update3();
         }
      }
   }

   public float getFloat2() {
      float f = 73.0F;
      float f1 = 0.0F;

      for (PanelWidget panelwidget : this.list) {
         float f2 = this.getFloatByPanelWidget(panelwidget);
         if (!(f2 <= 0.001F)) {
            float f3 = panelwidget.getFloat7() * f2;
            float f4 = 16.0F * f2;
            f += f3 + (f1 > 0.0F ? f4 : 0.0F);
            f1 += f3 + f4;
         }
      }

      return f + 16.0F;
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      return this.isPredicate(var2x -> var2x.isIntChar(count, symbol));
   }

   private boolean isPredicate(Predicate<PanelWidget> predicate) {
      for (PanelWidget panelwidget : this.list) {
         if (!(this.getFloatByPanelWidget(panelwidget) <= 0.5F) && predicate.test(panelwidget)) {
            return true;
         }
      }

      return false;
   }

   private PanelWidget getPanelWidgetBySetting(Setting setting2) {
      if (setting2 instanceof BooleanSetting booleansetting) {
         return new BooleanRow(booleansetting);
      } else if (setting2 instanceof SliderSetting slidersetting) {
         return new SliderRow(slidersetting);
      } else if (setting2 instanceof MultilistSetting multilistsetting) {
         return new FilterDropdown(multilistsetting);
      } else {
         return setting2 instanceof ListSetting listsetting ? new FilterDropdown(listsetting) : null;
      }
   }

   private void onFloatFloat4(float value, float value2) {
      float f = value2;
      float f1 = 0.0F;

      for (PanelWidget panelwidget : this.list) {
         float f2 = this.getFloatByPanelWidget(panelwidget);
         if (!(f2 <= 0.001F)) {
            float f3 = 16.0F * f2;
            if (f1 > 0.0F) {
               f += f3;
            }

            panelwidget.onFloatFloat2(f, value);
            float f4 = panelwidget.getFloat7() * f2;
            f += f4;
            f1 += f4 + f3;
         }
      }
   }

   private Tween getTweenByPanelWidget(PanelWidget panelWidget) {
      float f = panelWidget.getSetting().isVisible() ? 1.0F : 0.0F;
      Tween tween = EasingPresets.getTweenByFloatFloat2(f, 0.22F);
      tween.setFloat2(f);
      return tween;
   }

   public boolean check() {
      for (PanelWidget panelwidget : this.list) {
         if (panelwidget.check2()) {
            return true;
         }
      }

      return false;
   }

   public void onMatrix4fDrawContextFloatFloatFloat(Matrix4f matrix4f2, DrawContext drawContext, float value, float value2, float value3) {
      float f = this.tween4.getFloat();
      if (!(f <= 0.001F)) {
         float f1 = EasingPresets.getFloatByFloat2(f);
         float f2 = (1.0F - f) * -6.0F;
         float f3 = this.value235 + this.value237 / 2.0F;
         float f4 = this.value236 + this.value238 / 2.0F;
         MatrixStack matrixstack = drawContext.getMatrices();
         Matrix4f matrix4f = MatrixUtil.getMatrix4fByFloatFloatFloatFloatMatrixStack(f1, f3, f2, f4, matrixstack);
         ScrollOffset.onFloatFloat(0.0F, UiContext.getInstance().getValue238());

         try {
            float f5 = value * f;
            this.onFloatFloatFloatMatrix4f(f5, value3, value2, matrix4f);
         } finally {
            ScrollOffset.setFlag();
            matrixstack.pop();
         }
      }
   }

   private float getFloatByFloatFloatMatrix4f(float value, float value2, Matrix4f matrix4f) {
      float f = this.value236 + 16.0F + 2.0F;
      int i = Theme.elevated();
      float f8 = 4.0F;
      float f7 = 20.0F;
      float f6 = 20.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f8, value2, i, matrix4f, f7, f6, value, f);
      CategoryType categorytype = CategoryType.SETTINGS;
      float f1 = Math.min(14.0F / categorytype.getWidth(), 14.0F / categorytype.getHeight());
      float f2 = categorytype.getWidth() * f1;
      float f3 = categorytype.getHeight() * f1;
      float f4 = value2 + (20.0F - f2) / 2.0F;
      float f5 = f + (20.0F - f3) / 2.0F;
      int j = Theme.mutedFg();
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j, matrix4f, f5, categorytype, f3, f4, f2);
      return value2 + 20.0F + 6.0F;
   }

   private void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      for (PanelWidget panelwidget : this.list) {
         float f = this.getFloatByPanelWidget(panelwidget);
         if (!(f <= 0.001F)) {
            float f1 = value2 * f;
            panelwidget.onFloatFloatFloatMatrix4f(f1, value3, value, matrix4f);
         }
      }
   }

   public boolean isDoubleDouble2(double value, double value2) {
      for (PanelWidget panelwidget : this.list) {
         if (!(this.getFloatByPanelWidget(panelwidget) <= 0.5F) && panelwidget.check2() && panelwidget.isDoubleDouble2(value, value2)) {
            return true;
         }
      }

      return false;
   }

   public boolean isFloatFloat(float value, float value2) {
      return value >= this.value235 && value <= this.value235 + this.value237 && value2 >= this.value236 && value2 <= this.value236 + this.value238;
   }

   private float getFloatByPanelWidget(PanelWidget panelWidget) {
      Tween tween = this.map.get(panelWidget);
      if (tween == null) {
         return panelWidget.getSetting().isVisible() ? 1.0F : 0.0F;
      } else {
         return tween.getValue3();
      }
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      return this.isPredicate(var3x -> var3x.isIntIntInt2(count, count2, count3));
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      return this.isPredicate(var6 -> var6.isDoubleDoubleDouble(value, value2, value3));
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      return this.toggleButton.isDoubleDoubleInt(value, value2, count) ? true : this.isPredicate(var5x -> var5x.isDoubleDoubleInt(value, value2, count));
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      return this.toggleButton.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)
         ? true
         : this.isPredicate(var9 -> var9.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4));
   }

   public void update7() {
      if (!this.flag4) {
         this.flag4 = true;
         this.tween4.setFloat2(0.0F);
      }
   }

   @Override
   public void update2() {
      for (PanelWidget panelwidget : this.list) {
         panelwidget.update2();
      }
   }
}
