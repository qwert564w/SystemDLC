package client.gui.widget;

import client.api.Theme;
import client.data.Tween;
import client.module.CategoryType;
import client.module.client.ThemeModule;
import client.render.MatrixUtil;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.ActionSetting;
import client.setting.AlignmentSetting;
import client.setting.BlocklistSetting;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.ColorSetting;
import client.setting.CompactGroupSetting;
import client.setting.HotkeySetting;
import client.setting.InputSetting;
import client.setting.KeybindSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.setting.StafflistSetting;
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

public final class HudEditorOverlay extends Widget {
   private static final float value239 = 16.0F;
   private static final float value240 = 20.0F;
   private static final float value241 = 14.0F;
   private static final float value242 = 6.0F;
   private static final float value243 = 0.001F;
   private static final float value244 = 0.5F;
   private static final float value245 = 0.22F;
   private static final float value246 = 40.0F;
   private static final float value247 = 400.0F;
   private static final float value248 = 3.0F;
   private static final float value249 = 4.0F;
   private static final float value250 = 24.0F;
   private static final float value251 = 2.0F;
   private static final float value252 = 24.0F;
   private final RenderElement renderElement;
   private final List<PanelWidget> list = new ArrayList<>();
   private final Map<PanelWidget, Tween> map = new HashMap<>();
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.22F);
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat(0.0F, 0.28F);
   private float value253;
   private float value254;
   private float value255;
   private float value256;
   private float value257;
   private float value258;
   private float value259;
   private boolean flag4;
   private float value260;
   private final ToggleButton toggleButton;
   private boolean flag5;

   public HudEditorOverlay(RenderElement renderElement2) {
      this.renderElement = renderElement2;
      this.value237 = 301.0F;
      this.toggleButton = new ToggleButton(renderElement2.check13());
      this.toggleButton.setRunnable(() -> renderElement2.onBoolean(this.toggleButton.isFlag4()));
      this.update4();
      this.value238 = this.getFloat6();
      this.tween4.setFloat2(1.0F);
   }

   public void update3() {
      for (PanelWidget panelwidget : this.list) {
         if (panelwidget.check2()) {
            panelwidget.update3();
         }
      }
   }

   private float getFloat() {
      return Math.max(0.0F, this.getFloat3() - this.getFloat5());
   }

   private boolean isDoubleDouble2(double value, double value2) {
      float f = this.value235 + this.value237 - 4.0F - 3.0F;
      return value2 >= f - 3.0F && value2 <= f + 3.0F + 3.0F && value >= this.value259 && value <= this.value259 + this.value258;
   }

   private float getFloat2() {
      return 73.0F;
   }

   public boolean check() {
      for (PanelWidget panelwidget : this.list) {
         if (panelwidget.check2()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      return this.isPredicate(var3x -> var3x.isIntIntInt(count, count2, count3));
   }

   private void update4() {
      this.list.clear();
      float f = this.getFloat4();

      for (Setting setting : this.renderElement.getList()) {
         this.onSettingFloat(setting, f);
      }

      this.map.keySet().retainAll(this.list);

      for (PanelWidget panelwidget : this.list) {
         this.map.computeIfAbsent(panelwidget, this::getTweenByPanelWidget);
      }
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count == 0 && this.getFloat() > 0.001F && this.isDoubleDouble2(value2, value)) {
         this.flag4 = true;
         this.value260 = (float)value2 - this.value259;
         return true;
      } else {
         return this.toggleButton.isIntDoubleDouble(count, value, value2) ? true : this.isPredicate(var5 -> var5.isIntDoubleDouble(count, value, value2));
      }
   }

   public boolean isFlag5() {
      return this.flag5;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.update6();
      this.value238 = this.getFloat6();
      float f = this.value235 + 16.0F;
      float f30 = this.value235;
      float f31 = this.value236;
      float f32 = this.value237;
      float f33 = this.value238;
      int j1 = Theme.background();
      int k1 = Theme.border();
      float f22 = 6.0F;
      float f21 = 2.0F;
      float f20 = 0.0F;
      int k = 436207616;
      float f19 = 1.0F;
      int j = k1;
      int i = j1;
      float f18 = 14.0F;
      float f17 = 14.0F;
      float f16 = 14.0F;
      float f15 = 14.0F;
      float f14 = f33;
      float f13 = f32;
      float f12 = f31;
      float f11 = f30;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f13, k, i, f11, f12, f22, f15, f14, f21, matrix4f, value, j, f17, f19, f16, f18, f20
      );
      float f1 = this.value236 + 16.0F;
      float f2 = this.getFloatByMatrix4fFloatFloat(matrix4f, f, value);
      float f3 = f1 + 4.0F;
      String s1 = this.renderElement.getString();
      int l = Theme.foreground();
      float f23 = 16.0F;
      String s = s1;
      TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value, s, f23, f2, l, f3, matrix4f);
      this.toggleButton.setBoolean2(this.renderElement.check13());
      float f4 = f + this.getFloat4() - 28.0F;
      float f5 = f1 + 4.0F;
      this.toggleButton.onFloatFloat2(f5, f4);
      this.toggleButton.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      float f6 = f1 + 24.0F + 16.0F;
      f32 = this.getFloat4();
      int i1 = Theme.border();
      float f25 = 1.0F;
      float f24 = f32;
      ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(value, f25, f, f6, f24, matrix4f, i1);
      this.update5();
      float f7 = this.tween5.getFloat();
      float f8 = f6 + 1.0F + 16.0F;
      this.value254 = f8;
      this.value255 = Math.max(0.0F, this.value236 + this.value238 - 16.0F - f8);
      float f26 = f8 - f7;
      this.onFloatFloat4(f26, f);
      float f9 = f6 + 1.0F;
      float f10 = Math.max(0.0F, this.value236 + this.value238 - 2.0F - f9);
      float f28 = this.value237;
      float f27 = this.value235;
      ScissorStack.onFloatFloatFloatFloat(f28, f10, f9, f27);

      try {
         this.onFloatFloatMatrix4fFloat(value, value3, matrix4f, value2);
      } finally {
         ScissorStack.update();
      }

      this.onFloatMatrix4fFloatFloatFloat(f10, matrix4f, value, f7, f9);
      if (this.getFloat() > 0.001F) {
         float f29 = this.value255;
         this.onMatrix4fFloatFloatFloatFloat(matrix4f, f8, value, f29, f7);
      }
   }

   private void update5() {
      this.value253 = Math.clamp(this.value253, 0.0F, this.getFloat());
      this.tween5.setFloat2(this.value253);
   }

   private float getFloat3() {
      float f = 0.0F;
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

      return f;
   }

   private float getFloat4() {
      return this.value237 - 32.0F;
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

   private float getFloat5() {
      return Math.max(0.0F, 400.0F - this.getFloat2() - 16.0F);
   }

   private void update6() {
      for (PanelWidget panelwidget : this.list) {
         Tween tween = this.map.get(panelwidget);
         if (tween != null) {
            tween.setFloat2(panelwidget.getSetting().isVisible() ? 1.0F : 0.0F);
            tween.getFloat();
         }
      }
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.flag4 && count == 0) {
         this.flag4 = false;
         return true;
      } else {
         return this.toggleButton.isDoubleDoubleInt(value, value2, count) ? true : this.isPredicate(var5x -> var5x.isDoubleDoubleInt(value, value2, count));
      }
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      return this.isPredicate(var2x -> var2x.isIntChar(count, symbol));
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.flag4 && count == 0) {
         float f = Math.max(0.0F, this.value257 - this.value258);
         float f1 = this.getFloat();
         if (f > 0.0F && f1 > 0.0F) {
            float f2 = (float)value - this.value260;
            float f3 = (f2 - this.value256) / f;
            this.value253 = Math.clamp(f3 * f1, 0.0F, f1);
            this.tween5.setFloat2(this.value253);
            this.tween5.setFloat(this.value253);
         }

         return true;
      } else {
         return this.toggleButton.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)
            ? true
            : this.isPredicate(var9 -> var9.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4));
      }
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (this.isPredicate(var6 -> var6.isDoubleDoubleDouble(value, value2, value3))) {
         return true;
      }
      float f = this.getFloat();
      if (f <= 0.001F) {
         return false;
      }
      this.value253 = Math.clamp(this.value253 - (float)(value * 40.0), 0.0F, f);
      this.tween5.setFloat2(this.value253);
      return true;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      return this.isPredicate(var3x -> var3x.isIntIntInt2(count, count2, count3));
   }

   private boolean isPredicate(Predicate<PanelWidget> predicate) {
      for (PanelWidget panelwidget : this.list) {
         if (!(this.getFloatByPanelWidget(panelwidget) <= 0.5F) && predicate.test(panelwidget)) {
            return true;
         }
      }

      return false;
   }

   public RenderElement getRenderElement() {
      return this.renderElement;
   }

   private PanelWidget getPanelWidgetBySetting(Setting setting2) {
      if (setting2 instanceof ActionSetting actionsetting) {
         return new ActionRow(actionsetting);
      } else if (setting2 instanceof ChoiceSetting choicesetting) {
         return new ChoiceRow(choicesetting);
      } else if (setting2 instanceof BooleanSetting booleansetting) {
         return new BooleanRow(booleansetting);
      } else if (setting2 instanceof SliderSetting slidersetting) {
         return new SliderRow(slidersetting);
      } else if (setting2 instanceof AlignmentSetting alignmentsetting) {
         return new AlignmentRow(alignmentsetting);
      } else if (setting2 instanceof MultilistSetting multilistsetting) {
         return new FilterDropdown(multilistsetting);
      } else if (setting2 instanceof ListSetting listsetting) {
         return new FilterDropdown(listsetting);
      } else if (setting2 instanceof ColorSetting colorsetting) {
         return new ColorPickerRow(colorsetting);
      } else if (setting2 instanceof InputSetting inputsetting) {
         return new TextInputRow(inputsetting);
      } else if (setting2 instanceof KeybindSetting keybindsetting) {
         return new DropdownRow(keybindsetting);
      } else if (setting2 instanceof HotkeySetting hotkeysetting) {
         return new DropdownRow(hotkeysetting);
      } else if (setting2 instanceof BlocklistSetting blocklistsetting) {
         return new BlockListPanel(blocklistsetting);
      } else if (setting2 instanceof StafflistSetting stafflistsetting) {
         return new NameListPanel(stafflistsetting);
      } else {
         return setting2 instanceof CompactGroupSetting compactgroupsetting ? new SettingRow(compactgroupsetting) : null;
      }
   }

   public float getFloat6() {
      return 400.0F;
   }

   private float getFloatByPanelWidget(PanelWidget panelWidget) {
      Tween tween = this.map.get(panelWidget);
      if (tween == null) {
         return panelWidget.getSetting().isVisible() ? 1.0F : 0.0F;
      } else {
         return tween.getValue3();
      }
   }

   public boolean isFloatFloat(float value, float value2) {
      return value >= this.value235 && value <= this.value235 + this.value237 && value2 >= this.value236 && value2 <= this.value236 + this.value238;
   }

   public boolean isDoubleDouble3(double value, double value2) {
      for (PanelWidget panelwidget : this.list) {
         if (!(this.getFloatByPanelWidget(panelwidget) <= 0.5F) && panelwidget.check2() && panelwidget.isDoubleDouble2(value2, value)) {
            return true;
         }
      }

      return false;
   }

   public void update7() {
      if (!this.flag5) {
         this.flag5 = true;
         this.tween4.setFloat2(0.0F);
      }
   }

   public boolean check2() {
      return this.flag5 && this.tween4.getValue3() < 0.001F;
   }

   private Tween getTweenByPanelWidget(PanelWidget panelWidget) {
      float f = panelWidget.getSetting().isVisible() ? 1.0F : 0.0F;
      Tween tween = EasingPresets.getTweenByFloatFloat2(f, 0.22F);
      tween.setFloat2(f);
      return tween;
   }

   private void onSettingFloat(Setting setting2, float value) {
      PanelWidget panelwidget = this.getPanelWidgetBySetting(setting2);
      if (panelwidget != null) {
         panelwidget.setFloat(value);
         this.list.add(panelwidget);
      }
   }

   private void onMatrix4fFloatFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3, float value4) {
      float f = this.getFloat3();
      if (!(f <= value3)) {
         float f1 = this.value235 + this.value237 - 4.0F - 3.0F;
         float f2 = value + 4.0F;
         float f3 = value + value3 - 4.0F;
         float f4 = Math.max(0.0F, f3 - f2);
         if (!(f4 <= 0.0F)) {
            float f5 = Math.max(24.0F, f4 * (value3 / f));
            f5 = Math.min(f5, f4);
            float f6 = f4 - f5;
            float f7 = this.getFloat();
            float f8 = f2 + (f7 > 0.0F ? f6 * (value4 / f7) : 0.0F);
            this.value256 = f2;
            this.value257 = f4;
            this.value258 = f5;
            this.value259 = f8;
            float f9 = 1.5F;
            int k = Theme.surface();
            float f14 = 3.0F;
            float f13 = 1.0F;
            float f12 = 0.0F;
            int j = 436207616;
            float f11 = 0.0F;
            byte b0 = 0;
            int i = k;
            float f10 = 3.0F;
            ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
               f10, j, i, f1, f8, f14, f9, f5, f13, matrix4f, value2, b0, f9, f11, f9, f9, f12
            );
         }
      }
   }

   private float getFloatByMatrix4fFloatFloat(Matrix4f matrix4f, float value, float value2) {
      CategoryType categorytype = this.renderElement.getCategoryType();
      if (categorytype == null) {
         return value;
      } else {
         float f = this.value236 + 16.0F + 2.0F;
         int i = Theme.elevated();
         float f8 = 4.0F;
         float f7 = 20.0F;
         float f6 = 20.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f8, value, i, matrix4f, f7, f6, value2, f);
         float f1 = Math.min(14.0F / categorytype.getWidth(), 14.0F / categorytype.getHeight());
         float f2 = categorytype.getWidth() * f1;
         float f3 = categorytype.getHeight() * f1;
         float f4 = value + (20.0F - f2) / 2.0F;
         float f5 = f + (20.0F - f3) / 2.0F;
         int j = Theme.mutedFg();
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, j, matrix4f, f5, categorytype, f3, f4, f2);
         return value + 20.0F + 6.0F;
      }
   }

   private void onFloatFloatMatrix4fFloat(float value, float value2, Matrix4f matrix4f, float value3) {
      for (PanelWidget panelwidget : this.list) {
         float f = this.getFloatByPanelWidget(panelwidget);
         if (!(f <= 0.001F)) {
            float f1 = value * f;
            panelwidget.onFloatFloatFloatMatrix4f(f1, value3, value2, matrix4f);
         }
      }
   }

   private void onFloatFloat4(float value, float value2) {
      float f = value;
      float f1 = 0.0F;

      for (PanelWidget panelwidget : this.list) {
         float f2 = this.getFloatByPanelWidget(panelwidget);
         if (!(f2 <= 0.001F)) {
            float f3 = 16.0F * f2;
            if (f1 > 0.0F) {
               f += f3;
            }

            panelwidget.onFloatFloat2(f, value2);
            float f4 = panelwidget.getFloat7() * f2;
            f += f4;
            f1 += f4 + f3;
         }
      }
   }

   public void render(DrawContext drawContext, float value, float value2, float value3, Matrix4f matrix4f2) {
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
            float f5 = value3 * f;
            this.onFloatFloatFloatMatrix4f(f5, value2, value, matrix4f);
         } finally {
            ScrollOffset.setFlag();
            matrixstack.pop();
         }
      }
   }

   private void onFloatMatrix4fFloatFloatFloat(float value, Matrix4f matrix4f, float value2, float value3, float value4) {
      ThemeModule thememodule = ThemeModule.getThemeModule();
      if (thememodule == null || !thememodule.check4()) {
         float f = this.getFloat();
         float f1 = value2 * Math.clamp(value3 / 24.0F, 0.0F, 1.0F);
         float f2 = value2 * Math.clamp((f - value3) / 24.0F, 0.0F, 1.0F);
         int i = Theme.background();
         int j = 0xFF000000 | i & 16777215;
         int k = i & 16777215;
         float f3 = this.value237 - 8.0F - 3.0F;
         if (f1 > 0.001F) {
            float f13 = this.value235 + 14.0F;
            float f15 = f3 - 14.0F;
            int i2 = getIntByIntFloat(j, f1);
            int i1 = getIntByIntFloat(k, f1);
            int l = i2;
            float f6 = 24.0F;
            float f5 = f15;
            float f4 = f13;
            ShapeShader.onIntMatrix4fFloatIntFloatFloatFloat(l, matrix4f, f5, i1, f6, f4, value4);
         }

         if (f2 > 0.001F) {
            float f11 = this.value235 + 14.0F;
            float f14 = value4 + value - 24.0F + 1.0F;
            float f12 = f3 - 14.0F;
            int l1 = getIntByIntFloat(k, f2);
            int k1 = getIntByIntFloat(j, f2);
            int j1 = l1;
            float f10 = 24.0F;
            float f9 = f12;
            float f8 = f14;
            float f7 = f11;
            ShapeShader.onIntMatrix4fFloatIntFloatFloatFloat(j1, matrix4f, f9, k1, f10, f7, f8);
         }
      }
   }

   private static int getIntByIntFloat(int count, float value) {
      int i = Math.clamp((long)((int)((count >>> 24 & 0xFF) * value)), 0, 255);
      return i << 24 | count & 16777215;
   }

   @Override
   public void update2() {
      for (PanelWidget panelwidget : this.list) {
         panelwidget.update2();
      }
   }
}
