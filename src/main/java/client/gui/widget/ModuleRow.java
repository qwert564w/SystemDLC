package client.gui.widget;

import client.api.Theme;
import client.audio.SoundEngine;
import client.data.ClientAccess;
import client.data.PanelLayout;
import client.data.SettingWidgetFactory;
import client.data.TextTrimmer;
import client.data.Tween;
import client.enums.SoundEvent;
import client.module.CategoryType;
import client.module.Module;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.concurrent.SystemClient;
import client.setting.Setting;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public class ModuleRow extends Widget {
   private final Module module;
   private final ToggleButton toggleButton;
   private final FavoriteButton favoriteButton;
   private final KeybindButton keybindButton;
   private final List<PanelWidget> list = new ArrayList<>();
   private final PanelLayout panelLayout = new PanelLayout();
   private boolean flag4 = true;
   private boolean flag5 = false;
   private boolean flag6 = false;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.32F);
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat2(1.0F, 0.22F);
   private final Tween tween6 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.5F);
   private float value239 = Float.NaN;
   private long time = -1L;
   private float value240;
   private long time2 = -1L;
   private float value241;

   public ModuleRow(Module module2) {
      this.module = module2;
      this.value237 = 301.0F;
      this.toggleButton = new ToggleButton(module2.isEnabled());
      this.toggleButton.setRunnable(() -> module2.setEnabled(this.toggleButton.isFlag4()));
      this.favoriteButton = new FavoriteButton(ClientAccess.isString(module2.getName()));
      this.favoriteButton.setRunnable(() -> ClientAccess.onStringBoolean(module2.getName(), this.favoriteButton.isFlag4()));
      this.keybindButton = new KeybindButton(module2.getKeybindSetting());
      this.keybindButton.setRunnable(() -> {
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient != null && systemclient.getHashUtil() != null) {
            systemclient.getHashUtil().update5();
         }
      });
      this.update7();
      this.update3();
      this.tween4.setFloat(this.check() ? 0.0F : 1.0F);
      this.tween5.setFloat(module2.isEnabled() ? 1.0F : 0.5F);
   }

   private float getFloat() {
      return this.value237 - 32.0F;
   }

   private void update3() {
      float f = this.getFloat();
      if (!(Math.abs(f - this.value239) <= 0.001F)) {
         this.value239 = f;

         for (PanelWidget panelwidget : this.list) {
            panelwidget.setFloat(f);
         }
      }
   }

   public void update4() {
      if (this.flag4) {
         this.update7();
      }

      this.update3();
      this.panelLayout.onList(this.list);
      float f = this.value235 + 16.0F;
      this.getFloatByFloatFloat2(f, this.value236 + 16.0F);
      if (this.check2()) {
         float f1 = this.value236 + 16.0F + 24.0F + 16.0F;
         float f2 = f1 + 1.0F + 16.0F;
         this.onFloatFloat4(f, f2);
      }
   }

   public void setBoolean(boolean flag) {
      this.flag5 = flag;
      float f = this.check() ? 0.0F : 1.0F;
      this.tween4.setFloat(f);
      this.tween4.setFloat2(f);
   }

   private boolean check() {
      return !this.check2() || this.flag5;
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      if (this.keybindButton.isIntIntInt(count, count2, count3)) {
         return true;
      } else {
         for (PanelWidget panelwidget : (Iterable<PanelWidget>)(this.getList())) {
            if (panelwidget.isIntIntInt(count, count2, count3)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.tween4.getValue3() >= 0.5F) {
         for (PanelWidget panelwidget : (Iterable<PanelWidget>)(this.getList())) {
            if (panelwidget.check2() && panelwidget.isIntDoubleDouble(count, value, value2)) {
               return true;
            }
         }
      }

      if (this.keybindButton.isFlag4() && this.keybindButton.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (count == 1 && this.isDoubleDouble2(value2, value) && this.check2()) {
         this.flag5 = !this.flag5;
         SoundEngine.getInstance().onSoundEvent(SoundEvent.GROUP_OPEN);
         return true;
      } else if (this.toggleButton.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.favoriteButton.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.keybindButton.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.tween4.getValue3() < 0.5F) {
         return false;
      } else {
         for (PanelWidget panelwidget1 : (Iterable<PanelWidget>)(this.getList())) {
            if (panelwidget1.isIntDoubleDouble(count, value, value2)) {
               return true;
            }
         }

         return false;
      }
   }

   public float getFloat2() {
      long i = UiContext.getTime();
      if (i == this.time) {
         return this.value240;
      } else {
         this.value240 = this.check() ? 56.0F : this.getFloat4();
         this.time = i;
         return this.value240;
      }
   }

   public void update5() {
      this.tween4.setFloat2(this.check() ? 0.0F : 1.0F);
      this.tween4.getFloat();
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      this.toggleButton.setBoolean2(this.module.isEnabled());
      if (this.flag4) {
         this.update7();
      }

      this.update3();
      this.panelLayout.onList(this.list);
      this.tween5.setFloat2(this.module.isEnabled() ? 1.0F : 0.5F);
      float f = value * this.tween5.getFloat();
      boolean flag = this.check();
      if (flag && !this.flag6) {
         for (PanelWidget panelwidget : this.list) {
            panelwidget.update2();
         }
      }

      this.flag6 = flag;
      this.tween4.setFloat2(flag ? 0.0F : 1.0F);
      float f24 = this.tween4.getFloat();
      float f25 = this.getFloat5();
      float f26 = this.value235;
      float f27 = this.value236;
      float f28 = this.value237;
      int j1 = Theme.background();
      int k1 = Theme.border();
      float f12 = 1.0F;
      int j = k1;
      int i = j1;
      float f11 = 14.0F;
      float f10 = f28;
      float f9 = f27;
      float f8 = f26;
      ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f12, f10, f, matrix4f, f9, i, f11, f8, j, f25);
      float f1 = this.tween6.getFloat();
      if (f1 > 0.001F) {
         f26 = this.value235;
         f27 = this.value236;
         f28 = this.value237;
         k1 = Theme.primary();
         float f18 = value * f1;
         float f17 = 2.0F;
         int k = k1;
         byte b0 = 0;
         float f16 = 14.0F;
         float f15 = f28;
         float f14 = f27;
         float f13 = f26;
         ShapeShader.onFloatFloatFloatMatrix4fFloatIntFloatFloatIntFloat(f17, f15, f18, matrix4f, f14, b0, f16, f13, k, f25);
      }

      float f2 = this.value235 + 16.0F;
      float f3 = this.value236 + 16.0F;
      this.onFloatFloatFloatFloatFloatMatrix4f(f2, f3, f, value3, value2, matrix4f);
      if (!(f24 <= 0.001F)) {
         float f4 = f3 + 24.0F + 16.0F;
         f28 = this.getFloat();
         int i1 = Theme.border();
         float f21 = f * f24;
         int l = i1;
         float f20 = 1.0F;
         float f19 = f28;
         ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(f21, f20, f2, f4, f19, matrix4f, l);
         float f5 = f4 + 1.0F + 16.0F;
         this.onFloatFloat4(f2, f5);
         boolean flag1 = f24 < 0.999F;
         if (flag1) {
            float f6 = f4 + 1.0F;
            float f7 = Math.max(0.0F, this.value236 + f25 - 16.0F - f6);
            float f22 = this.getFloat();
            ScissorStack.onFloatFloatFloatFloat(f22, f7, f6, f2);
         }

         float f23 = f * f24;
         this.onFloatMatrix4fFloatFloat(f23, matrix4f, value3, value2);
         if (flag1) {
            ScissorStack.update();
         }
      }
   }

   private boolean check2() {
      for (PanelWidget panelwidget : this.list) {
         if (panelwidget.getSetting().isVisible()) {
            return true;
         }

         if (this.panelLayout.getFloatByPanelWidget(panelwidget) > 0.001F) {
            return true;
         }
      }

      return false;
   }

   private float getFloat3() {
      this.update3();
      float f = 73.0F;
      float f1 = 0.0F;

      for (PanelWidget panelwidget : this.list) {
         float f2 = this.panelLayout.getFloatByPanelWidget(panelwidget);
         if (!(f2 <= 0.001F)) {
            float f3 = panelwidget.getFloat7() * f2;
            float f4 = 16.0F * f2;
            f += f3 + (f1 > 0.0F ? f4 : 0.0F);
            f1 += f3 + f4;
         }
      }

      return f + 16.0F;
   }

   private void setValue239() {
      this.value239 = Float.NaN;
   }

   @Override
   public void onFloatFloatFloatMatrix4f2(float value, float value2, float value3, Matrix4f matrix4f) {
      if (!(this.tween4.getValue3() <= 0.001F)) {
         float f = value3 * this.tween5.getValue3();

         for (PanelWidget panelwidget : this.list) {
            float f1 = this.panelLayout.getFloatByPanelWidget(panelwidget);
            if (!(f1 <= 0.001F)) {
               float f2 = f * f1;
               panelwidget.onFloatFloatFloatMatrix4f2(value, value2, f2, matrix4f);
            }
         }
      }
   }

   public void update6() {
      this.tween6.setFloat(1.0F);
      float f1 = 1.1F;
      float f = 0.0F;
      this.tween6.onFloatFloat(f1, f);
   }

   public boolean check3() {
      if (this.tween4.getValue3() < 0.5F) {
         return false;
      } else {
         for (PanelWidget panelwidget : (Iterable<PanelWidget>)(this.getList())) {
            if (panelwidget.check2()) {
               return true;
            }
         }

         return false;
      }
   }

   private float getFloat4() {
      this.update3();
      float f = 73.0F;
      boolean flag = true;

      for (PanelWidget panelwidget : this.list) {
         if (panelwidget.getSetting().isVisible()) {
            if (!flag) {
               f += 16.0F;
            }

            f += panelwidget.getFloat4();
            flag = false;
         }
      }

      return f + 16.0F;
   }

   public void setFloat(float value) {
      if (!(Math.abs(this.value237 - value) <= 0.001F)) {
         this.value237 = value;
         this.setValue239();
      }
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (this.keybindButton.isIntIntInt2(count, count2, count3)) {
         return true;
      } else {
         for (PanelWidget panelwidget : (Iterable<PanelWidget>)(this.getList())) {
            if (panelwidget.isIntIntInt2(count, count2, count3)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (this.tween4.getValue3() < 0.5F) {
         return false;
      } else {
         for (PanelWidget panelwidget : (Iterable<PanelWidget>)(this.getList())) {
            if (panelwidget.isDoubleDoubleDouble(value, value2, value3)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      if (this.toggleButton.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)) {
         return true;
      } else {
         for (PanelWidget panelwidget : (Iterable<PanelWidget>)(this.getList())) {
            if (panelwidget.isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4)) {
               return true;
            }
         }

         return false;
      }
   }

   public Module getModule() {
      return this.module;
   }

   private void addSetting(Setting setting2) {
      PanelWidget panelwidget = SettingWidgetFactory.getPanelWidgetBySetting10(setting2);
      if (panelwidget != null) {
         this.list.add(panelwidget);
      }
   }

   private List<PanelWidget> getList() {
      ArrayList arraylist = new ArrayList(this.list.size());

      for (PanelWidget panelwidget : this.list) {
         if (this.panelLayout.getFloatByPanelWidget(panelwidget) > 0.5F) {
            arraylist.add(panelwidget);
         }
      }

      return arraylist;
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      for (PanelWidget panelwidget : (Iterable<PanelWidget>)(this.getList())) {
         if (panelwidget.isIntChar(count, symbol)) {
            return true;
         }
      }

      return false;
   }

   private boolean isDoubleDouble2(double value, double value2) {
      float f = this.value236 + 16.0F;
      return value2 >= this.value235 && value2 <= this.value235 + this.value237 && value >= f && value <= f + 24.0F;
   }

   private void onFloatFloatFloatFloatFloatMatrix4f(float value, float value2, float value3, float value4, float value5, Matrix4f matrix4f) {
      float f = value2 + 4.0F;
      CategoryType categorytype1 = this.module.getCategory().getCategoryType();
      int i = Theme.foreground();
      float f5 = 16.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatCategoryTypeIntMatrix4fFloatFloatFloat(f, categorytype, i, matrix4f, value3, f5, value);
      float f1 = this.getFloatByFloatFloat2(value, value2);
      this.toggleButton.onFloatFloatFloatMatrix4f(value3, value5, value4, matrix4f);
      this.favoriteButton.onFloatFloatFloatMatrix4f(value3, value5, value4, matrix4f);
      this.keybindButton.onFloatFloatFloatMatrix4f(value3, value5, value4, matrix4f);
      float f2 = value + 16.0F + 6.0F;
      float f3 = value2 + 4.0F;
      float f4 = Math.max(0.0F, f1 - 8.0F - f2);
      String s2 = this.module.getName();
      float f6 = 16.0F;
      String s = s2;
      String s3 = TextTrimmer.getStringByFloatStringFloat2(f4, s, f6);
      int j = Theme.foreground();
      float f7 = 16.0F;
      String s1 = s3;
      TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value3, s1, f7, f2, j, f3, matrix4f);
   }

   public boolean isFlag5() {
      return this.flag5;
   }

   public float getFloat5() {
      long i = UiContext.getTime();
      if (i == this.time2) {
         this.value238 = this.value241;
         return this.value241;
      } else {
         float f = this.tween4.getValue3();
         float f1 = 56.0F;
         float f2;
         if (!this.check2()) {
            f2 = f1;
         } else {
            float f3 = this.getFloat3();
            f2 = f1 + (f3 - f1) * f;
         }

         this.value241 = f2;
         this.time2 = i;
         this.value238 = f2;
         return f2;
      }
   }

   public void setBoolean2(boolean flag) {
      if (this.check2()) {
         this.flag5 = flag;
      }
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (this.toggleButton.isDoubleDoubleInt(value, value2, count)) {
         return true;
      } else {
         for (PanelWidget panelwidget : this.getList()) {
            if (panelwidget.isDoubleDoubleInt(value, value2, count)) {
               return true;
            }
         }

         return false;
      }
   }

   private void update7() {
      this.list.clear();

      for (Setting setting : (Iterable<Setting>)(this.module.getSettingsCopy())) {
         if (setting != this.module.getKeybindSetting()) {
            this.addSetting(setting);
         }
      }

      this.panelLayout.onList2(this.list);
      this.flag4 = false;
      this.setValue239();
   }

   private float getFloatByFloatFloat2(float value, float value2) {
      float f = value + this.getFloat() - 28.0F;
      float f4 = value2 + 4.0F;
      this.toggleButton.onFloatFloat2(f4, f);
      float f1 = f - 4.0F - 16.0F;
      float f5 = value2 + 4.0F;
      this.favoriteButton.onFloatFloat2(f5, f1);
      this.keybindButton.setKeybindSetting(this.module.getKeybindSetting());
      float f2 = this.keybindButton.getFloat();
      float f3 = f1 - 4.0F - f2;
      this.keybindButton.setValue237(f2);
      float f6 = value2 + 2.0F;
      this.keybindButton.onFloatFloat2(f6, f3);
      return f3;
   }

   private void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      for (PanelWidget panelwidget : this.list) {
         float f = this.panelLayout.getFloatByPanelWidget(panelwidget);
         if (!(f <= 0.001F)) {
            float f1 = value * f;
            panelwidget.onFloatFloatFloatMatrix4f(f1, value3, value2, matrix4f);
         }
      }
   }

   private void onFloatFloat4(float value, float value2) {
      float f = value2;
      float f1 = 0.0F;

      for (PanelWidget panelwidget : this.list) {
         float f2 = this.panelLayout.getFloatByPanelWidget(panelwidget);
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

   public void update8() {
      for (PanelWidget panelwidget : this.list) {
         if (panelwidget.check2()) {
            panelwidget.update3();
         }
      }
   }
}
