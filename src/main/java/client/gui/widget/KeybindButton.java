package client.gui.widget;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.KeybindSetting;
import client.util.EasingPresets;
import org.joml.Matrix4f;

public class KeybindButton extends LabelWidget {
   private static final float value239 = 16.0F;
   private static final float value240 = 10.0F;
   private KeybindSetting keybindSetting;
   private boolean flag4;
   private Runnable runnable;
   private int value241 = -1;
   private final Tween tween4 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);

   public KeybindButton(KeybindSetting keybindSetting2) {
      this.keybindSetting = keybindSetting2;
      this.value238 = 20.0F;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.flag4) {
         if (count == 0 && this.isDoubleDouble(value, value2)) {
            this.flag4 = false;
            this.value241 = -1;
            return true;
         } else {
            this.keybindSetting.setInt(count);
            this.flag4 = false;
            this.value241 = -1;
            if (this.runnable != null) {
               this.runnable.run();
            }

            return true;
         }
      } else {
         return super.isIntDoubleDouble(count, value, value2);
      }
   }

   private boolean check() {
      if (this.flag4) {
         return false;
      } else {
         String s = this.keybindSetting.getText2();
         return s != null && !s.isEmpty() && !s.equals("None");
      }
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      if (!this.flag4) {
         return false;
      } else if (this.value241 == -1) {
         return false;
      } else if (count == this.value241) {
         this.keybindSetting.onIntInt(this.value241, 0);
         this.flag4 = false;
         this.value241 = -1;
         if (this.runnable != null) {
            this.runnable.run();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      String s = this.getString();
      this.value237 = this.getFloat();
      this.getFloatByFloatFloat(value3, value2);
      this.tween4.setFloat2(this.flag4 ? 1.0F : 0.0F);
      float f = this.tween4.getFloat();
      int j2 = Theme.elevated();
      int i1 = Theme.primary();
      int l = j2;
      int i = AnimatedInt.getIntByIntFloatInt(i1, f, l);
      j2 = Theme.foreground();
      int k1 = Theme.background();
      int j1 = j2;
      int j = AnimatedInt.getIntByIntFloatInt(k1, f, j1);
      j2 = Theme.mutedFg();
      int i2 = Theme.background();
      int l1 = j2;
      int k = AnimatedInt.getIntByIntFloatInt(i2, f, l1);
      float f9 = 4.0F;
      float f8 = this.value238;
      float f7 = this.value237;
      float f6 = this.value236;
      float f5 = this.value235;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f9, f5, i, matrix4f, f8, f7, value, f6);
      float f1 = this.value235 + 4.0F;
      float f2 = this.value236 + (this.value238 - 10.0F) / 2.0F;
      float f11 = 10.0F;
      float f10 = 16.0F;
      CategoryType categorytype = CategoryType.KEYBOARD;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, j, matrix4f, f2, categorytype, f11, f1, f10);
      float f3 = f1 + 16.0F + 4.0F;
      float f4 = this.value236 + (this.value238 - 12.0F) / 2.0F;
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, s, f3, f4, 12.0F, k, value);
   }

   private String getString() {
      if (this.flag4) {
         return "...";
      } else {
         String s = this.keybindSetting.getText2();
         return s != null && !s.isEmpty() ? s : "None";
      }
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   public void setKeybindSetting(KeybindSetting keybindSetting2) {
      this.keybindSetting = keybindSetting2;
   }

   @Override
   protected boolean isDoubleDouble2(double value, double value2) {
      this.flag4 = !this.flag4;
      this.value241 = -1;
      return true;
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (!this.flag4) {
         if (count3 == 261 && this.flag && this.check2()) {
            this.keybindSetting.setInt(-1);
            if (this.runnable != null) {
               this.runnable.run();
            }

            return true;
         } else {
            return false;
         }
      } else if (count3 == 256 || count3 == 261) {
         this.keybindSetting.setInt(-1);
         this.flag4 = false;
         this.value241 = -1;
         if (this.runnable != null) {
            this.runnable.run();
         }

         return true;
      } else if (KeybindSetting.isInt(count3)) {
         this.value241 = count3;
         return true;
      } else {
         this.keybindSetting.onIntInt(count3, count2);
         this.flag4 = false;
         this.value241 = -1;
         if (this.runnable != null) {
            this.runnable.run();
         }

         return true;
      }
   }

   private boolean check2() {
      String s = this.keybindSetting.getText2();
      return s != null && !s.isEmpty() && !s.equals("None");
   }

   public float getFloat() {
      float f = 20.0F + TextShader.getFloatByStringFloat(this.getString(), 12.0F);
      return this.check() ? 4.0F + f + 4.0F : 8.0F + Math.max(f, 51.0F);
   }
}
