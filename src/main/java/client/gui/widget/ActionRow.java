package client.gui.widget;

import client.api.Theme;
import client.concurrent.Translations;
import client.data.AnimatedInt;
import client.data.Tween;
import client.render.ShapeShader;
import client.render.TextShader;
import client.setting.ActionSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class ActionRow extends PanelWidget {
   private static final float value252 = 32.0F;
   private static final float value253 = 8.0F;
   private static final float value254 = 0.85F;
   private final ActionSetting actionSetting;
   private final Tween tween4 = EasingPresets.getTween();

   public ActionRow(ActionSetting actionSetting2) {
      super(actionSetting2);
      this.actionSetting = actionSetting2;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else if (!(value < this.value235) && !(value > this.value235 + this.value237) && !(value2 < this.value236) && !(value2 > this.value236 + 32.0F)) {
         this.actionSetting.update();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      boolean flag = value3 >= this.value235 && value3 <= this.value235 + this.value237 && value2 >= this.value236 && value2 <= this.value236 + 32.0F;
      this.tween4.setFloat2(flag ? 1.0F : 0.0F);
      float f = this.tween4.getFloat();
      int l2 = Theme.background();
      int i3 = Theme.elevated();
      float f4 = 0.85F;
      int l = i3;
      int k = l2;
      int i = AnimatedInt.getIntByIntFloatInt(l, f4, k);
      int i1 = Theme.background();
      int j = AnimatedInt.getIntByIntFloatInt(i, f, i1);
      float f22 = this.value235;
      float f23 = this.value236;
      float f24 = this.value237;
      int j3 = Theme.border();
      float f16 = 1.0F;
      float f15 = 1.0F;
      float f14 = 0.0F;
      int k1 = 436207616;
      float f13 = 1.0F;
      int j1 = j3;
      float f12 = 8.0F;
      float f11 = 8.0F;
      float f10 = 8.0F;
      float f9 = 8.0F;
      float f8 = 32.0F;
      float f7 = f24;
      float f6 = f23;
      float f5 = f22;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f7, k1, j, f5, f6, f16, f9, f8, f15, matrix4f, value, j1, f11, f13, f10, f12, f14
      );
      float f1 = this.value236 + 9.0F;
      String s = check3() ? Translations.getInstance().getStringByString(this.actionSetting.getNameHash()) : null;
      if (s != null) {
         float f2 = TextShader.getFloatByStringFloat(s, 14.0F);
         f23 = this.value235 + (this.value237 - f2) / 2.0F;
         int l1 = Theme.foreground();
         float f18 = 14.0F;
         float f17 = f23;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f1, f17, l1, f18, value, s, matrix4f);
      } else {
         String[] astring = this.actionSetting.getNameParts();
         float f19 = 14.0F;
         float f3 = TextShader.getFloatByFloatStringArray(f19, astring);
         int k2 = astring.length;
         float f25 = this.value235 + (this.value237 - f3) / 2.0F;
         int j2 = Theme.foreground();
         float f21 = 14.0F;
         float f20 = f25;
         int i2 = k2;
         byte b0 = 0;
         TextShader.onIntFloatStringArrayFloatMatrix4fIntIntFloatFloat(b0, value, astring, f20, matrix4f, i2, j2, f21, f1);
      }

      this.value238 = 32.0F;
   }

   @Override
   public float getFloat5() {
      return 32.0F;
   }
}
