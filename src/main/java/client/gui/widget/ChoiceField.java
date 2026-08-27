package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.ChoiceAnim;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.HotkeySetting;
import client.setting.KeybindSetting;
import client.setting.Setting;
import client.util.EasingPresets;
import client.util.Easings;
import org.joml.Matrix4f;

public final class ChoiceField extends SettingField {
   private final Setting setting2;
   private boolean flag;
   private int value = -1;
   private final Tween tween = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private final Tween tween2 = EasingPresets.getTween();
   private final Tween tween3 = EasingPresets.getTween();
   private final Tween tween4 = new Tween(0.0F, 0.55F).getTweenByFunction(Easings::getFloatByFloat3);

   ChoiceField(Setting setting3) {
      super(setting3);
      this.setting2 = setting3;
   }

   @Override
   public boolean isDoubleDoubleIntFloatFloatFloat(double value4, double value5, int count, float value6, float value7, float value8) {
      ChoiceAnim choiceanim = this.getChoiceAnimByFloatFloatFloat(value7, value8, value6);
      boolean flagx = value4 >= choiceanim.value && value4 <= choiceanim.value + 70.0F && value5 >= choiceanim.value3 && value5 <= choiceanim.value3 + 18.0F;
      boolean flag1 = value4 >= choiceanim.value2 && value4 <= choiceanim.value2 + 20.0F && value5 >= choiceanim.value3 && value5 <= choiceanim.value3 + 18.0F;
      if (this.flag) {
         if (count == 0 && flagx) {
            this.update();
            return true;
         } else {
            this.onIntInt(count, 0);
            this.update();
            return true;
         }
      } else if (count != 0) {
         return false;
      } else if (flag1) {
         this.onIntInt(-1, 0);
         this.tween4.setFloat(0.0F);
         this.tween4.setFloat2(1.0F);
         return true;
      } else if (flagx) {
         this.flag = true;
         this.value = -1;
         return true;
      } else {
         return false;
      }
   }

   private void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f2, float value2, float value3) {
      float f = this.tween4.getFloat();
      Matrix4f matrix4f = matrix4f2;
      if (f > 1.0E-4F && f < 0.9999F) {
         float f1 = f * (float) (Math.PI * 2);
         float f2 = value + 5.0F;
         float f3 = value2 + 5.0F;
         matrix4f = new Matrix4f(matrix4f2).translate(f2, f3, 0.0F).rotateZ(f1).translate(-f2, -f3, 0.0F);
      }

      CategoryType categorytype1 = CategoryType.RESET;
      int i = Theme.foreground();
      float f5 = 10.0F;
      float f4 = 10.0F;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, value2, categorytype, f5, value, f4);
   }

   private void onFloatFloatFloatIntFloatMatrix4f(float value, float value2, float value3, int count, float value4, Matrix4f matrix4f) {
      float f8 = 3.0F;
      float f7 = 1.0F;
      float f6 = 0.0F;
      int i = 436207616;
      float f5 = 0.0F;
      byte b0 = 0;
      float f4 = 6.0F;
      float f3 = 6.0F;
      float f2 = 6.0F;
      float f1 = 6.0F;
      float f = 18.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value, i, count, value3, value4, f8, f1, f, f7, matrix4f, value2, b0, f3, f5, f2, f4, f6
      );
   }

   public boolean isIntInt(int count, int count2) {
      if (!this.flag) {
         return false;
      } else if (count == 256) {
         this.onIntInt(-1, 0);
         this.update();
         return true;
      } else if (KeybindSetting.isInt(count)) {
         this.value = count;
         return true;
      } else {
         this.onIntInt(count, count2);
         this.update();
         return true;
      }
   }

   public boolean isInt(int count) {
      if (this.flag && this.value != -1 && count == this.value) {
         this.onIntInt(this.value, 0);
         this.update();
         return true;
      } else {
         return false;
      }
   }

   private ChoiceAnim getChoiceAnimByFloatFloatFloat(float value, float value2, float value3) {
      float f = value2 + value3 - 8.0F - 20.0F;
      float f1 = f - 4.0F - 70.0F;
      float f2 = value + 5.0F - 2.0F;
      return new ChoiceAnim(f1, f, f2);
   }

   private String getString() {
      if (this.flag) {
         return "...";
      } else if (this.setting2 instanceof KeybindSetting keybindsetting) {
         return keybindsetting.getText2();
      } else {
         return this.setting2 instanceof HotkeySetting hotkeysetting ? hotkeysetting.getText2() : "None";
      }
   }

   private void onIntInt(int count, int count2) {
      if (this.setting2 instanceof KeybindSetting keybindsetting) {
         keybindsetting.onIntInt(count, count2);
      } else if (this.setting2 instanceof HotkeySetting hotkeysetting) {
         hotkeysetting.onIntInt(count2, count);
      }
   }

   private void update() {
      this.flag = false;
      this.value = -1;
   }

   @Override
   public void onFloatFloatFloatFloatFloatMatrix4fFloat(float value4, float value5, float value6, float value7, float value8, Matrix4f matrix4f, float value9) {
      ChoiceAnim choiceanim = this.getChoiceAnimByFloatFloatFloat(value9, value7, value8);
      this.tween.setFloat2(this.flag ? 1.0F : 0.0F);
      float f = this.tween.getFloat();
      boolean flagx = !this.flag
         && value6 >= choiceanim.value
         && value6 <= choiceanim.value + 70.0F
         && value5 >= choiceanim.value3
         && value5 <= choiceanim.value3 + 18.0F;
      this.tween2.setFloat2(flagx ? 1.0F : 0.0F);
      int l1 = SettingRow.getIntByFloat(this.tween2.getFloat());
      int l = Theme.primary();
      int k = l1;
      int i = AnimatedInt.getIntByIntFloatInt(l, f, k);
      l1 = Theme.foreground();
      int j1 = Theme.background();
      int i1 = l1;
      int j = AnimatedInt.getIntByIntFloatInt(j1, f, i1);
      float f5 = 70.0F;
      float f4 = choiceanim.value3;
      float f3 = choiceanim.value;
      this.onFloatFloatFloatIntFloatMatrix4f(f5, value4, f3, i, f4, matrix4f);
      String s = this.getString();
      float f12 = choiceanim.value + (70.0F - TextShader.getFloatByStringFloat(s, 12.0F)) / 2.0F;
      float f13 = choiceanim.value3 + 3.0F;
      float f8 = 12.0F;
      float f7 = f13;
      float f6 = f12;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f7, f6, j, f8, value4, s, matrix4f);
      boolean flag1 = value6 >= choiceanim.value2 && value6 <= choiceanim.value2 + 20.0F && value5 >= choiceanim.value3 && value5 <= choiceanim.value3 + 18.0F;
      this.tween3.setFloat2(flag1 ? 1.0F : 0.0F);
      f12 = choiceanim.value2;
      f13 = choiceanim.value3;
      int k1 = SettingRow.getIntByFloat(this.tween3.getFloat());
      float f11 = 20.0F;
      float f10 = f13;
      float f9 = f12;
      this.onFloatFloatFloatIntFloatMatrix4f(f11, value4, f9, k1, f10, matrix4f);
      float f1 = choiceanim.value2 + 5.0F;
      float f2 = choiceanim.value3 + 4.0F;
      this.onFloatMatrix4fFloatFloat(f1, matrix4f, f2, value4);
   }
}
