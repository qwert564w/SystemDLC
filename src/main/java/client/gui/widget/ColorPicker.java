package client.gui.widget;

import client.api.ColorSupplier;
import client.api.Theme;
import client.data.AnimatedInt;
import client.data.ColorFormatState;
import client.data.ColorPickerLayout;
import client.data.Tween;
import client.enums.ColorFormat;
import client.gui.screen.ClickGuiScreen;
import client.module.CategoryType;
import client.module.Feature;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.ColorSetting;
import client.util.EasingPresets;
import client.util.Easings;
import client.util.Interpolation;
import client.util.MathUtil;
import client.util.ThemeColors;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class ColorPicker extends ButtonWidget {
   private static final float value241 = 293.0F;
   private static final float value242 = 383.0F;
   private static final float value243 = 8.0F;
   private static final float value244 = 12.0F;
   private static final float value245 = 12.0F;
   private static final float value246 = 28.0F;
   private static final float value247 = 14.0F;
   private static final float value248 = 12.0F;
   private static final float value249 = 12.0F;
   private static final float value250 = 4.0F;
   private static final float value251 = 6.0F;
   private static final float value252 = 8.0F;
   private static final float value253 = 14.0F;
   private static final float value254 = 16.0F;
   private static final float value255 = 269.0F;
   private static final float value256 = 150.0F;
   private static final float value257 = 8.0F;
   private static final float value258 = 240.0F;
   private static final float value259 = 8.0F;
   private static final float value260 = 7.0F;
   private static final float value261 = 10.0F;
   private static final float value262 = 16.0F;
   private static final float value263 = 16.0F;
   private static final float value264 = 12.0F;
   private static final float value265 = 13.0F;
   private static final float value266 = 14.0F;
   private static final float value267 = 1.5F;
   private static final int value268 = -1;
   private static final float value269 = 269.0F;
   private static final float value270 = 34.0F;
   private static final float value271 = 8.0F;
   private static final float value272 = 4.0F;
   private static final float value273 = 26.0F;
   private static final float value274 = 8.0F;
   private static final float value275 = 60.0F;
   private static final float value276 = 133.0F;
   private static final float value277 = 60.0F;
   private static final float value278 = 12.0F;
   private static final float value279 = 8.0F;
   private static final float value280 = 6.0F;
   private static final float value281 = 269.0F;
   private static final float value282 = 32.0F;
   private static final float value283 = 8.0F;
   private static final float value284 = 16.0F;
   private static final float value285 = 10.0F;
   private static final float value286 = 29.0F;
   private static final float value287 = 26.0F;
   private static final float value288 = 8.0F;
   private static final float value289 = 1.5F;
   private static final float value290 = 26.0F;
   private static final float value291 = 4.0F;
   private static final float value292 = 4.0F;
   private static final float value293 = 6.0F;
   private static final float value294 = 0.4F;
   private static final float value295 = 0.7F;
   private static final float value296 = 0.1F;
   private static final float value297 = 0.18F;
   private static final float value298 = 6.0F;
   private static final float value299 = 0.05F;
   private static final float value300 = 0.03F;
   private static final float value301 = 28.0F;
   private static final float value302 = 18.0F;
   private static final String text = "Выбор цвета";
   private static final String text2 = "Выберите цвет, который будет использоваться.";
   private static final String text3 = "Сохранённые цвета";
   private static final String text4 = "Можете выбрать сохранённый цвет.";
   private static final int value303 = -1;
   private static final int value304 = 3;
   private static final int value305 = 4;
   private final ColorSetting colorSetting;
   private float value306;
   private float value307;
   private float value308;
   private float value309 = 1.0F;
   private boolean flag8;
   private boolean flag9;
   private boolean flag10;
   private float value310;
   private float value311;
   private float value312;
   private float value313;
   private final Interpolation interpolation = new Interpolation();
   private boolean flag11;
   private int value314 = -1;
   private final Tween tween5 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private final Tween tween6 = EasingPresets.getTweenByFloatFloat(0.0F, 0.15F);
   private final AnimatedInt animatedInt;
   private boolean flag12;
   private int value315 = -16777216;
   private float value316;
   private float value317;
   private final Interpolation interpolation2 = new Interpolation();
   private boolean flag13;
   private static final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
   private static ColorFormat colorFormat = ColorFormatState.getColorFormat();
   private ColorFormat colorFormat2 = colorFormat;
   private boolean flag14;
   private final Tween tween7 = EasingPresets.getTweenByFloatFloat2(0.0F, 0.15F);
   private final Tween tween8 = new Tween(1.0F, 0.18F).getTweenByFunction(Easings::getFloatByFloat6);
   private int value318 = 1;
   private int value319 = -1;
   private ColorFormat colorFormat3;
   private final TextInputController textInputController = new TextInputController(12.0F);

   public ColorPicker(ColorSetting colorSetting2) {
      this.colorSetting = colorSetting2;
      this.value237 = 293.0F;
      this.value238 = 383.0F;
      this.animatedInt = new AnimatedInt(0xFF000000 | colorSetting2.getInt3() & 16777215, 0.22F);
      this.textInputController.getTextInputControllerByConsumer(this::setString);
      this.textInputController.getTextInputControllerByRunnable(() -> this.value319 = -1);
      this.textInputController.getTextInputControllerByIntPredicate(this::isInt);
      this.textInputController.getTextInputState().getTextInputStateByBoolean(true);
      this.update14();
   }

   @Override
   protected void onFloatFloatFloatMatrix4f3(float value, float value2, float value3, Matrix4f matrix4f2) {
      this.update12();
      float f = this.value236 + EasingPresets.getFloatByFloat(value2);
      Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, value2, this.value235 + 146.5F, f + 191.5F);
      float f26 = this.value235;
      int l = Theme.background();
      int i1 = Theme.border();
      float f11 = 6.0F;
      float f10 = 2.0F;
      float f9 = 0.0F;
      int k = 436207616;
      float f8 = 1.0F;
      int j = i1;
      int i = l;
      float f7 = 8.0F;
      float f6 = 8.0F;
      float f5 = 8.0F;
      float f4 = 8.0F;
      float f3 = 383.0F;
      float f2 = 293.0F;
      float f1 = f26;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f2, k, i, f1, f, f11, f4, f3, f10, matrix4f, value2, j, f6, f8, f5, f7, f9
      );
      ColorPickerLayout colorpickerlayout = this.getColorPickerLayoutByFloat(f);
      float f12 = colorpickerlayout.descMaxW();
      this.onFloatMatrix4fFloatFloat3(value2, matrix4f, f, f12);
      float f14 = colorpickerlayout.boxY();
      float f13 = colorpickerlayout.boxX();
      this.onFloatFloatFloatMatrix4f5(value2, f13, f14, matrix4f);
      float f16 = colorpickerlayout.sliderBlockH();
      float f15 = colorpickerlayout.sliderBlockY();
      this.onMatrix4fFloatFloatFloat3(matrix4f, f16, value2, f15);
      float f18 = colorpickerlayout.readoutY();
      float f17 = colorpickerlayout.boxX();
      this.onFloatMatrix4fFloatFloat(f18, matrix4f, f17, value2);
      this.onMatrix4fFloatFloatFloat(matrix4f, colorpickerlayout.boxX(), colorpickerlayout.savedHeaderY(), value2);
      float f21 = colorpickerlayout.presetGap();
      float f20 = colorpickerlayout.presetsY();
      float f19 = colorpickerlayout.boxX();
      this.onFloatFloatFloatFloatMatrix4f(value2, f21, f20, f19, matrix4f);
      if (this.flag12) {
         this.onFloatFloat5(value, value3);
         this.onFloatFloat4(value3, value);
         float f23 = this.value317;
         float f22 = this.value316;
         this.onMatrix4fFloatFloatFloat2(matrix4f2, f22, value2, f23);
      } else if (this.flag13) {
         this.flag13 = false;
         this.interpolation2.setTime();
      }

      float f25 = colorpickerlayout.modeBlockY() + 26.0F;
      float f24 = colorpickerlayout.modeBlockX();
      this.onFloatMatrix4fFloatFloatFloatFloat(f24, matrix4f, value, value2, value3, f25);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.flag12) {
         if (count == 1) {
            this.flag12 = false;
         } else if (count == 0) {
            this.update13();
         }

         return true;
      } else if (this.flag14 && count == 0 && !this.isDoubleDouble2(value, value2) && !this.isDoubleDouble3(value, value2)) {
         this.flag14 = false;
         return true;
      } else {
         return super.isIntDoubleDouble(count, value, value2);
      }
   }

   private void onMatrix4fFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3) {
      CategoryType categorytype1 = CategoryType.EYEDROPPER;
      float f9 = value2 + 1.0F;
      int i = Theme.foreground();
      float f2 = 12.0F;
      float f1 = 12.0F;
      float f = f9;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, f, categorytype, f2, value, f1);
      float f8 = value + 12.0F + 4.0F;
      int j = Theme.foreground();
      float f4 = 14.0F;
      float f3 = f8;
      String s = "Сохранённые цвета";
      TextShader.onIntFloatFloatMatrix4fFloatFloatString(j, value3, value2, matrix4f, f3, f4, s);
      f9 = value2 + 14.0F + 4.0F;
      int k = Theme.mutedFg();
      float f7 = 269.0F;
      float f6 = 12.0F;
      float f5 = f9;
      String s1 = "Можете выбрать сохранённый цвет.";
      TextShader.onFloatStringFloatFloatMatrix4fFloatFloatInt(value3, s1, value, f6, matrix4f, f7, f5, k);
   }

   private static float getFloat6() {
      int i = ColorFormat.values().length;
      return 8.0F + i * 26.0F + (i - 1) * 4.0F;
   }

   private void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      int i = Theme.elevated();
      float f7 = 8.0F;
      float f6 = 34.0F;
      float f5 = 269.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, value2, i, matrix4f, f6, f5, value3, value);
      float f = value + 4.0F;
      float f1 = getFloat7();
      float f2 = value2 + 4.0F;
      float f3 = f2 + 60.0F + f1;
      float f4 = f3 + 133.0F + f1;
      this.onFloatFloatMatrix4fFloat(f, value3, matrix4f, f2);
      this.onFloatFloatFloatMatrix4f4(f3, value3, f, matrix4f);
      this.onFloatMatrix4fFloatFloat2(value3, matrix4f, f4, f);
   }

   private void onMatrix4fFloatFloatFloatFloat(Matrix4f matrix4f, float value, float value2, float value3, float value4) {
      int k = Theme.surface();
      float f8 = 3.0F;
      float f7 = 1.0F;
      float f6 = 0.0F;
      int j = 436207616;
      float f5 = 0.0F;
      byte b0 = 0;
      int i = k;
      float f4 = 8.0F;
      float f3 = 8.0F;
      float f2 = 8.0F;
      float f1 = 8.0F;
      float f = 26.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         value2, j, i, value3, value4, f8, f1, f, f7, matrix4f, value, b0, f3, f5, f2, f4, f6
      );
   }

   private void onFloatFloatFloatMatrix4f4(float value, float value2, float value3, Matrix4f matrix4f) {
      float f7 = 133.0F;
      this.onMatrix4fFloatFloatFloatFloat(matrix4f, value2, f7, value, value3);
      float f = this.tween8.getFloat();
      if (f < 0.001F && this.colorFormat2 != colorFormat) {
         this.colorFormat2 = colorFormat;
         this.tween8.setFloat2(1.0F);
         f = this.tween8.getFloat();
      }

      float f1 = value2 * f;
      boolean flag = this.tween8.getValue4() < 0.5F;
      float f2 = (flag ? this.value318 : -this.value318) * (1.0F - f) * 4.0F;
      if (this.colorFormat2 == ColorFormat.HEX) {
         this.onMatrix4fFloatFloatFloatFloat2(matrix4f, value3, f2, value, f1);
      } else {
         String[] astring = this.getStringArrayByColorFormat(this.colorFormat2);
         float f3 = this.colorFormat2 != ColorFormat.HEX && colorFormat != ColorFormat.HEX ? value2 : f1;
         float f4 = 43.666668F;

         for (int i = 0; i < 3; i++) {
            float f5 = value + i * (f4 + 1.0F);
            float f6 = value3 + 7.0F + f2;
            String s = astring[i];
            this.onStringFloatFloatIntMatrix4fFloatFloat(s, f4, f1, i, matrix4f, f5, f6);
            if (i < 2) {
               float f11 = f5 + f4;
               int j = Theme.border();
               float f10 = 26.0F;
               float f9 = 1.0F;
               float f8 = f11;
               ShapeShader.onFloatFloatFloatFloatFloatMatrix4fInt(f3, f10, f8, value3, f9, matrix4f, j);
            }
         }
      }
   }

   private void update11() {
      int i = this.colorSetting.isFlag() ? Math.round(this.value309 * 255.0F) : 255;
      float f2 = this.value308;
      float f1 = this.value307;
      float f = this.value306;
      int j = MathUtil.getIntByFloatIntFloatFloat(f2, i, f1, f);
      this.colorSetting.setInt(j);
      if (this.value314 >= 0) {
         int k = this.value314;
         ThemeColors.onIntInt(j, k);
      }
   }

   private void onFloatFloat4(float value, float value2) {
      float f = this.interpolation2.getFloat2();
      if (!this.flag13) {
         this.value316 = value;
         this.value317 = value2;
         this.flag13 = true;
      } else {
         float f2 = 0.03F;
         float f1 = this.value316;
         this.value316 = Interpolation.getFloatByFloatFloatFloatFloat2(value, f1, f, f2);
         float f4 = 0.03F;
         float f3 = this.value317;
         this.value317 = Interpolation.getFloatByFloatFloatFloatFloat2(value2, f3, f, f4);
      }
   }

   private boolean isDoubleDouble2(double value, double value2) {
      if (!this.flag14) {
         return false;
      } else {
         ColorPickerLayout colorpickerlayout = this.getColorPickerLayout();
         float f4 = colorpickerlayout.modeBlockX();
         float f5 = colorpickerlayout.modeBlockY() + 26.0F + 6.0F;
         float f3 = getFloat6();
         float f2 = 60.0F;
         float f1 = f5;
         float f = f4;
         return isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value);
      }
   }

   private int getInt() {
      return Color.HSBtoRGB(this.value306, this.value307, this.value308);
   }

   private void onString(String text) {
      Integer integer = MathUtil.getIntegerByString(text);
      if (integer != null) {
         int l = integer >> 16 & 0xFF;
         int i1 = integer >> 8 & 0xFF;
         int k = integer & 0xFF;
         int j = i1;
         int i = l;
         this.onIntIntInt(k, i, j);
      }
   }

   private void update12() {
      float f = this.interpolation.getFloat2();
      if (!this.flag11) {
         this.value310 = this.value307;
         this.value311 = this.value308;
         this.value312 = this.value306;
         this.value313 = this.value309;
         this.flag11 = true;
      } else {
         float f3 = 0.05F;
         float f2 = this.value307;
         float f1 = this.value310;
         this.value310 = Interpolation.getFloatByFloatFloatFloatFloat2(f2, f1, f, f3);
         float f6 = 0.05F;
         float f5 = this.value308;
         float f4 = this.value311;
         this.value311 = Interpolation.getFloatByFloatFloatFloatFloat2(f5, f4, f, f6);
         float f9 = 0.05F;
         float f8 = this.value306;
         float f7 = this.value312;
         this.value312 = Interpolation.getFloatByFloatFloatFloatFloat2(f8, f7, f, f9);
         float f12 = 0.05F;
         float f11 = this.value309;
         float f10 = this.value313;
         this.value313 = Interpolation.getFloatByFloatFloatFloatFloat2(f11, f10, f, f12);
      }
   }

   private void onFloatMatrix4fFloatFloat2(float value, Matrix4f matrix4f, float value2, float value3) {
      float f2 = 60.0F;
      this.onMatrix4fFloatFloatFloatFloat(matrix4f, value, f2, value2, value3);
      float f = value3 + 7.0F;
      if (this.value319 == 3) {
         String s1 = "%";
         float f3 = 60.0F;
         this.onMatrix4fFloatFloatFloatStringFloat(matrix4f, value, value2, f, s1, f3);
      } else {
         String s = Math.round(this.value309 * 100.0F) + "%";
         float f1 = TextShader.getFloatByStringFloat(s, 12.0F);
         float f6 = value2 + (60.0F - f1) / 2.0F;
         int i = Theme.foreground();
         float f5 = 12.0F;
         float f4 = f6;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f, f4, i, f5, value, s, matrix4f);
      }
   }

   private void onMatrix4fFloatFloatFloat2(Matrix4f matrix4f, float value, float value2, float value3) {
      float f = 14.0F;
      float f1 = Feature.mc.getWindow().getScaledWidth();
      float f2 = Feature.mc.getWindow().getScaledHeight();
      float f3 = value + 18.0F;
      float f4 = value3 + 18.0F;
      if (f3 + 28.0F > f1) {
         f3 = value - 18.0F - 28.0F;
      }

      if (f4 + 28.0F > f2) {
         f4 = value3 - 18.0F - 28.0F;
      }

      float f11 = 3.0F;
      float f10 = 1.0F;
      float f9 = 0.0F;
      int j = 436207616;
      float f8 = 1.5F;
      byte b0 = -1;
      int i = this.value315;
      float f7 = 28.0F;
      float f6 = 28.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f6, j, i, f3, f4, f11, f, f7, f10, matrix4f, value2, b0, f, f8, f, f, f9
      );
      String s = String.format("#%06X", this.value315 & 16777215);
      float f5 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f15 = f3 + (28.0F - f5) / 2.0F;
      float f16 = f4 + 28.0F + 4.0F;
      byte b1 = -1;
      float f14 = 12.0F;
      float f13 = f16;
      float f12 = f15;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f13, f12, b1, f14, value2, s, matrix4f);
   }

   private void update13() {
      int i = this.value315;
      if (this.colorSetting.isFlag()) {
         ColorSetting colorsetting = this.colorSetting;
         int j3 = i >> 16 & 0xFF;
         int k3 = i >> 8 & 0xFF;
         int l3 = i & 0xFF;
         int i1 = Math.round(this.value309 * 255.0F);
         int l = l3;
         int k = k3;
         int j = j3;
         colorsetting.onIntIntIntInt(j, k, i1, l);
      } else {
         int l2 = i >> 16 & 0xFF;
         int i3 = i >> 8 & 0xFF;
         int l1 = i & 0xFF;
         int k1 = i3;
         int j1 = l2;
         this.colorSetting.onIntIntInt(l1, k1, j1);
      }

      this.update14();
      if (this.value314 >= 0) {
         int k2 = this.value314;
         int j2 = this.colorSetting.getInt3();
         int i2 = k2;
         ThemeColors.onIntInt(j2, i2);
      }

      this.flag12 = false;
   }

   private boolean isDoubleDouble3(double value, double value2) {
      ColorPickerLayout colorpickerlayout = this.getColorPickerLayout();
      float f3 = 26.0F;
      float f2 = 60.0F;
      float f1 = colorpickerlayout.modeBlockY();
      float f = colorpickerlayout.modeBlockX();
      return isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value);
   }

   private void setString(String text) {
      int i = this.value319;
      this.value319 = -1;
      if (text != null) {
         text = text.trim();
         if (!text.isEmpty()) {
            try {
               switch (i) {
                  case 3:
                     this.value309 = Math.clamp(Float.parseFloat(text), 0.0F, 100.0F) / 100.0F;
                     this.update11();
                     break;
                  case 4:
                     this.onString(text);
                     break;
                  default:
                     this.onIntString(i, text);
               }
            } catch (NumberFormatException numberformatexception) {
            }
         }
      }
   }

   private void onIntString(int count, String text) {
      int i = this.getInt();
      int j = i >> 16 & 0xFF;
      int k = i >> 8 & 0xFF;
      int l = i & 0xFF;
      if (this.colorFormat3 == ColorFormat.RGB) {
         int i1 = Math.clamp((long)Integer.parseInt(text), 0, 255);
         if (count == 0) {
            j = i1;
         } else if (count == 1) {
            k = i1;
         } else {
            l = i1;
         }

         this.onIntIntInt(l, j, k);
      } else if (this.colorFormat3 == ColorFormat.HSL) {
         float[] afloat = MathUtil.getFloatArrayByInt(this.getInt2());
         float f = Float.parseFloat(text);
         if (count == 0) {
            afloat[0] = Math.clamp(f, 0.0F, 360.0F) / 360.0F;
         } else {
            afloat[count] = Math.clamp(f, 0.0F, 100.0F) / 100.0F;
         }

         float f4 = afloat[0];
         float f5 = afloat[1];
         float f3 = afloat[2];
         float f2 = f5;
         float f1 = f4;
         int j1 = MathUtil.getIntByFloatFloatFloat(f3, f1, f2);
         int j2 = j1 >> 16 & 0xFF;
         int k2 = j1 >> 8 & 0xFF;
         int i2 = j1 & 0xFF;
         int l1 = k2;
         int k1 = j2;
         this.onIntIntInt(i2, k1, l1);
      }
   }

   private void onIntIntInt(int count, int count2, int count3) {
      float[] afloat = MathUtil.getFloatArrayByInt2(0xFF000000 | count2 << 16 | count3 << 8 | count);
      this.value306 = afloat[0];
      this.value307 = afloat[1];
      this.value308 = afloat[2];
      this.update11();
   }

   @Override
   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      if (count != 0) {
         return true;
      } else {
         ColorPickerLayout colorpickerlayout = this.getColorPickerLayout();
         float f = this.value235 + 293.0F - 16.0F - 8.0F;
         float f39 = f - 4.0F;
         float f40 = this.value236 + 14.0F - 4.0F;
         float f8 = 16.0F;
         float f7 = 16.0F;
         float f6 = f40;
         float f5 = f39;
         if (isFloatFloatDoubleFloatFloatDouble(f5, f6, value2, f8, f7, value)) {
            this.update4();
            return true;
         } else {
            if (this.flag14) {
               float f1 = colorpickerlayout.modeBlockY() + 26.0F + 6.0F;
               ColorFormat[] acolorformat = ColorFormat.values();

               for (int i = 0; i < acolorformat.length; i++) {
                  float f2 = f1 + 4.0F + i * 30.0F;
                  float f3 = colorpickerlayout.modeBlockX() + 4.0F;
                  float f4 = 52.0F;
                  float f9 = 26.0F;
                  if (isFloatFloatDoubleFloatFloatDouble(f3, f2, value2, f9, f4, value)) {
                     this.setColorFormat(acolorformat[i]);
                     this.flag14 = false;
                     return true;
                  }
               }
            }

            float f13 = 26.0F;
            float f12 = 60.0F;
            float f11 = colorpickerlayout.modeBlockY();
            float f10 = colorpickerlayout.modeBlockX();
            if (isFloatFloatDoubleFloatFloatDouble(f10, f11, value2, f13, f12, value)) {
               this.flag14 = !this.flag14;
               return true;
            } else {
               float f14 = colorpickerlayout.modeBlockY();
               if (this.isFloatDoubleDouble(f14, value2, value)) {
                  return true;
               } else {
                  if (this.value319 >= 0) {
                     this.textInputController.update2();
                  }

                  float f36 = colorpickerlayout.sliderBlockY() + (colorpickerlayout.sliderBlockH() - 16.0F) / 2.0F;
                  f39 = this.value235 + 12.0F - 4.0F;
                  f40 = f36 - 4.0F;
                  float f18 = 24.0F;
                  float f17 = 24.0F;
                  float f16 = f40;
                  float f15 = f39;
                  if (isFloatFloatDoubleFloatFloatDouble(f15, f16, value2, f18, f17, value)) {
                     this.flag12 = true;
                     return true;
                  } else {
                     float f22 = 150.0F;
                     float f21 = 269.0F;
                     float f20 = colorpickerlayout.boxY();
                     float f19 = colorpickerlayout.boxX();
                     if (isFloatFloatDoubleFloatFloatDouble(f19, f20, value2, f22, f21, value)) {
                        this.flag8 = true;
                        float f24 = colorpickerlayout.boxY();
                        float f23 = colorpickerlayout.boxX();
                        this.onFloatFloatDoubleDouble(f23, f24, value, value2);
                        return true;
                     } else {
                        f40 = colorpickerlayout.hueY() - 2.0F;
                        float f28 = 12.0F;
                        float f27 = 240.0F;
                        float f26 = f40;
                        float f25 = colorpickerlayout.slidersX();
                        if (isFloatFloatDoubleFloatFloatDouble(f25, f26, value2, f28, f27, value)) {
                           this.flag9 = true;
                           float f29 = colorpickerlayout.slidersX();
                           this.onFloatDouble(f29, value);
                           return true;
                        } else {
                           f40 = colorpickerlayout.alphaY() - 2.0F;
                           float f33 = 12.0F;
                           float f32 = 240.0F;
                           float f31 = f40;
                           float f30 = colorpickerlayout.slidersX();
                           if (isFloatFloatDoubleFloatFloatDouble(f30, f31, value2, f33, f32, value)) {
                              this.flag10 = true;
                              this.onDoubleFloat(value, colorpickerlayout.slidersX());
                              return true;
                           } else {
                              for (int j = 0; j < 7; j++) {
                                 float f37 = colorpickerlayout.presetsX() + 10.0F + j * (16.0F + colorpickerlayout.presetGap());
                                 float f38 = colorpickerlayout.presetsY() + 8.0F;
                                 float f35 = 16.0F;
                                 float f34 = 16.0F;
                                 if (isFloatFloatDoubleFloatFloatDouble(f37, f38, value2, f35, f34, value)) {
                                    this.value314 = j;
                                    this.colorSetting.setInt(ThemeColors.getIntByInt(j));
                                    this.update14();
                                    return true;
                                 }
                              }

                              return true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean isFloatDoubleDouble(float value, double value2, double value3) {
      if (!(value2 < value) && !(value2 > value + 26.0F)) {
         float f = this.value235 + 12.0F + 4.0F;
         float f1 = getFloat7();
         float f2 = f + 60.0F + f1;
         float f3 = f2 + 133.0F + f1;
         if (value3 >= f2 && value3 <= f2 + 133.0F) {
            if (this.colorFormat2 == ColorFormat.HEX) {
               this.setInt(4);
            } else {
               float f4 = 43.666668F;

               for (int i = 0; i < 3; i++) {
                  float f5 = f2 + i * (f4 + 1.0F);
                  if (value3 >= f5 && value3 <= f5 + f4) {
                     this.setInt(i);
                     return true;
                  }
               }
            }

            return true;
         } else if (value3 >= f3 && value3 <= f3 + 60.0F) {
            this.setInt(3);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void onFloatDouble(float value, double value2) {
      this.value306 = getFloatByFloat((float)(value2 - value) / 240.0F);
      this.update11();
   }

   private void onDoubleFloat(double value, float value2) {
      this.value309 = getFloatByFloat((float)(value - value2) / 240.0F);
      this.update11();
   }

   private static float getFloatByFloat(float value) {
      return Math.clamp(value, 0.0F, 1.0F);
   }

   private ColorPickerLayout getColorPickerLayout() {
      return this.getColorPickerLayoutByFloat(this.value236);
   }

   private ColorPickerLayout getColorPickerLayoutByFloat(float value) {
      float f = 241.0F;
      float f15 = 12.0F;
      String s = "Выберите цвет, который будет использоваться.";
      float f1 = TextShader.getFloatByFloatFloatString(f, f15, s);
      float f2 = this.value235 + 12.0F;
      float f3 = value + 12.0F + 14.0F + 4.0F + f1 + 6.0F;
      float f4 = f3 + 150.0F + 10.0F;
      float f5 = 23.0F;
      float f6 = this.value235 + 12.0F + 16.0F + 13.0F;
      float f7 = f4 + 8.0F + 7.0F;
      float f8 = f4 + f5 + 10.0F;
      float f9 = f8 + 4.0F;
      float f10 = f2 + 4.0F;
      float f11 = f8 + 34.0F + 8.0F;
      float f17 = 269.0F;
      float f16 = 12.0F;
      String s1 = "Можете выбрать сохранённый цвет.";
      float f12 = TextShader.getFloatByFloatFloatString(f17, f16, s1);
      float f13 = f11 + 14.0F + 4.0F + f12 + 6.0F;
      float f14 = 22.833334F;
      return new ColorPickerLayout(f2, f3, f6, f4, f7, f4, f5, f8, f10, f9, f2, f13, f14, f11, f);
   }

   private void onFloatFloat5(float value, float value2) {
      try {
         double d0 = Feature.mc.getWindow().getScaleFactor() * ClickGuiScreen.getValue235();
         int i = Feature.mc.getWindow().getFramebufferWidth();
         int j = Feature.mc.getWindow().getFramebufferHeight();
         int k = (int)Math.round(value2 * d0);
         int l = (int)Math.round(j - value * d0);
         if (k < 0 || l < 0 || k >= i || l >= j) {
            return;
         }

         byteBuffer.clear();
         RenderSystem.assertOnRenderThread();
         GL11.glReadPixels(k, l, 1, 1, 6408, 5121, byteBuffer);
         this.value315 = 0xFF000000 | (byteBuffer.get(0) & 255) << 16 | (byteBuffer.get(1) & 255) << 8 | byteBuffer.get(2) & 255;
      } catch (Throwable throwable) {
      }
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      ColorPickerLayout colorpickerlayout = this.getColorPickerLayout();
      if (this.flag8) {
         float f1 = colorpickerlayout.boxY();
         float f = colorpickerlayout.boxX();
         this.onFloatFloatDoubleDouble(f, f1, value2, value);
         return true;
      } else if (this.flag9) {
         float f2 = colorpickerlayout.slidersX();
         this.onFloatDouble(f2, value2);
         return true;
      } else if (this.flag10) {
         this.onDoubleFloat(value2, colorpickerlayout.slidersX());
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      if (!this.flag8 && !this.flag10 && !this.flag9) {
         return false;
      } else {
         this.flag8 = this.flag10 = this.flag9 = false;
         return true;
      }
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      if (this.flag12 && count3 == 256) {
         this.flag12 = false;
         return true;
      } else {
         return this.textInputController.isFlag() ? this.textInputController.isIntInt(count2, count3) : super.isIntIntInt2(count, count2, count3);
      }
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      if (this.textInputController.isFlag()) {
         this.textInputController.isChar(symbol);
         return true;
      } else {
         return false;
      }
   }

   private void onFloatFloatDoubleDouble(float value, float value2, double value3, double value4) {
      this.value307 = getFloatByFloat((float)(value3 - value) / 269.0F);
      this.value308 = 1.0F - getFloatByFloat((float)(value4 - value2) / 150.0F);
      this.update11();
   }

   private static float getFloat7() {
      float f = 261.0F;
      return Math.max(0.0F, (f - 60.0F - 133.0F - 60.0F) / 2.0F);
   }

   private void onFloatFloatMatrix4fFloat(float value, float value2, Matrix4f matrix4f, float value3) {
      float f5 = 60.0F;
      this.onMatrix4fFloatFloatFloatFloat(matrix4f, value2, f5, value3, value);
      String s = colorFormat.text;
      float f = TextShader.getFloatByStringFloat(s, 12.0F);
      float f1 = CategoryType.DROPDOWN_ARROWS.getWidth();
      float f2 = CategoryType.DROPDOWN_ARROWS.getHeight();
      float f3 = value3 + (60.0F - (f + 6.0F + f1)) / 2.0F;
      float f4 = value + 7.0F;
      int i = Theme.foreground();
      float f6 = 12.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f3, i, f6, value2, s, matrix4f);
      CategoryType categorytype1 = CategoryType.DROPDOWN_ARROWS;
      float f9 = f3 + f + 6.0F;
      float f10 = value + (26.0F - f2) / 2.0F;
      int j = Theme.foreground();
      float f8 = f10;
      float f7 = f9;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, j, matrix4f, f8, categorytype, f2, f7, f1);
   }

   private void onMatrix4fFloatFloatFloatFloat2(Matrix4f matrix4f, float value, float value2, float value3, float value4) {
      float f = value + 7.0F + value2;
      if (this.value319 == 4) {
         String s1 = "#";
         float f2 = 133.0F;
         this.onFloatMatrix4fFloatStringFloatFloat(value3, matrix4f, value4, s1, f2, f);
      } else {
         String s = String.format("#%06X", this.getInt2() & 16777215);
         float f1 = TextShader.getFloatByStringFloat(s, 12.0F);
         float f5 = value3 + (133.0F - f1) / 2.0F;
         int i = Theme.foreground();
         float f4 = 12.0F;
         float f3 = f5;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f, f3, i, f4, value4, s, matrix4f);
      }
   }

   private void onStringFloatFloatIntMatrix4fFloatFloat(String text, float value, float value2, int count, Matrix4f matrix4f, float value3, float value4) {
      if (this.value319 == count) {
         String s = this.textInputController.getString();
         float f = TextShader.getFloatByStringFloat(s, 12.0F);
         TextInputState textinputstate = this.textInputController.getTextInputState();
         float f6 = value3 + (value - f) / 2.0F;
         int k = Theme.foreground();
         boolean flag = this.textInputController.check();
         int i = k;
         float f1 = f6;
         textinputstate.onFloatIntMatrix4fFloatBooleanFloatFloat(value4, i, matrix4f, value2, flag, f1, value);
      } else {
         float f4 = TextShader.getFloatByStringFloat(text, 12.0F);
         float f5 = value3 + (value - f4) / 2.0F;
         int j = Theme.foreground();
         float f3 = 12.0F;
         float f2 = f5;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value4, f2, j, f3, value2, text, matrix4f);
      }
   }

   private void onFloatMatrix4fFloatStringFloatFloat(float value, Matrix4f matrix4f, float value2, String text, float value3, float value4) {
      String s = this.textInputController.getString();
      float f = TextShader.getFloatByStringFloat(text, 12.0F);
      float f1 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f2 = value + (value3 - (f + f1)) / 2.0F;
      int i = Theme.foreground();
      float f3 = 12.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value4, f2, i, f3, value2, text, matrix4f);
      TextInputState textinputstate = this.textInputController.getTextInputState();
      float f5 = f2 + f;
      int k = Theme.foreground();
      boolean flag = this.textInputController.check();
      int j = k;
      float f4 = f5;
      textinputstate.onFloatIntMatrix4fFloatBooleanFloatFloat(value4, j, matrix4f, value2, flag, f4, value3);
   }

   private int getInt2() {
      return 0xFF000000 | Color.HSBtoRGB(this.value306, this.value307, this.value308) & 16777215;
   }

   private void onFloatMatrix4fFloatFloat3(float value, Matrix4f matrix4f, float value2, float value3) {
      float f = this.value235 + 12.0F;
      float f1 = value2 + 12.0F;
      float f2 = f1 + 1.0F;
      CategoryType categorytype2 = CategoryType.SETTING_COLOR;
      int i = Theme.foreground();
      float f4 = 12.0F;
      float f3 = 12.0F;
      CategoryType categorytype = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, i, matrix4f, f2, categorytype, f4, f, f3);
      float f13 = f + 12.0F + 4.0F;
      int j = Theme.foreground();
      float f6 = 14.0F;
      float f5 = f13;
      String s = "Выбор цвета";
      TextShader.onIntFloatFloatMatrix4fFloatFloatString(j, value, f1, matrix4f, f5, f6, s);
      categorytype2 = CategoryType.CLOSE;
      f13 = this.value235 + 293.0F - 16.0F - 8.0F;
      float f14 = value2 + 14.0F;
      int k = Theme.mutedFg();
      float f10 = 8.0F;
      float f9 = 8.0F;
      float f8 = f14;
      float f7 = f13;
      CategoryType categorytype1 = categorytype2;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value, k, matrix4f, f8, categorytype1, f10, f7, f9);
      f14 = f1 + 14.0F + 4.0F;
      int l = Theme.mutedFg();
      float f12 = 12.0F;
      float f11 = f14;
      String s1 = "Выберите цвет, который будет использоваться.";
      TextShader.onFloatStringFloatFloatMatrix4fFloatFloatInt(value, s1, f, f12, matrix4f, value3, f11, l);
   }

   private void onFloatFloatFloatMatrix4f5(float value, float value2, float value3, Matrix4f matrix4f) {
      int i = Color.HSBtoRGB(this.value306, 1.0F, 1.0F) | 0xFF000000;
      float f4 = 8.0F;
      float f3 = 150.0F;
      float f2 = 269.0F;
      ShapeShader.onFloatMatrix4fFloatFloatFloatFloatFloatInt(value, matrix4f, f3, f4, value2, f2, value3, i);
      float f = this.flag11 ? this.value310 : this.value307;
      float f1 = this.flag11 ? this.value311 : this.value308;
      float f7 = value2 + f * 269.0F - 7.0F;
      float f8 = value3 + (1.0F - f1) * 150.0F - 7.0F;
      int j = this.getInt2();
      float f6 = f8;
      float f5 = f7;
      this.onIntFloatFloatFloatMatrix4f(j, value, f6, f5, matrix4f);
   }

   private void onMatrix4fFloatFloatFloat3(Matrix4f matrix4f, float value, float value2, float value3) {
      CategoryType categorytype1 = CategoryType.EYEDROPPER;
      float f18 = this.value235 + 12.0F;
      float f19 = value3 + (value - 16.0F) / 2.0F;
      int k = Theme.foreground();
      float f7 = 16.0F;
      float f6 = 16.0F;
      float f5 = f19;
      float f4 = f18;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value2, k, matrix4f, f5, categorytype, f7, f4, f6);
      float f = this.value235 + 12.0F + 16.0F + 13.0F;
      float f1 = this.flag11 ? this.value312 : this.value306;
      float f2 = this.flag11 ? this.value313 : this.value309;
      float f10 = 4.0F;
      float f9 = 8.0F;
      float f8 = 240.0F;
      ShapeShader.onFloatFloatMatrix4fFloatFloatFloatFloat(f9, value3, matrix4f, f10, f, f8, value2);
      f18 = f + f1 * 240.0F - 7.0F;
      f19 = value3 + -3.0F;
      int l = this.getInt2();
      float f12 = f19;
      float f11 = f18;
      this.onIntFloatFloatFloatMatrix4f(l, value2, f12, f11, matrix4f);
      float f3 = value3 + 8.0F + 7.0F;
      int i = this.getInt() & 16777215;
      int i1 = i | 0xFF000000;
      float f15 = 4.0F;
      float f14 = 8.0F;
      float f13 = 240.0F;
      ShapeShader.onIntFloatFloatIntFloatFloatMatrix4fFloatFloat(i1, f14, f15, i, f3, f, matrix4f, f13, value2);
      int j = Math.round(f2 * 255.0F) << 24 | i;
      f18 = f + f2 * 240.0F - 7.0F;
      float f17 = f3 + -3.0F;
      float f16 = f18;
      this.onIntFloatFloatFloatMatrix4f(j, value2, f17, f16, matrix4f);
   }

   private void onIntFloatFloatFloatMatrix4f(int count, float value, float value2, float value3, Matrix4f matrix4f) {
      float f = 7.0F;
      float f6 = 0.0F;
      float f5 = 0.0F;
      float f4 = 0.0F;
      byte b1 = 0;
      float f3 = 1.5F;
      byte b0 = -1;
      float f2 = 14.0F;
      float f1 = 14.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f1, b1, count, value3, value2, f6, f, f2, f5, matrix4f, value, b0, f, f3, f, f, f4
      );
   }

   private void onFloatFloatFloatFloatMatrix4f(float value, float value2, float value3, float value4, Matrix4f matrix4f) {
      int l = Theme.elevated();
      float f7 = 8.0F;
      float f6 = 32.0F;
      float f5 = 269.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f7, value4, l, matrix4f, f6, f5, value, value3);
      this.tween5.setFloat2(this.value314 >= 0 ? 1.0F : 0.0F);
      if (this.value314 >= 0 && this.tween5.getValue4() < 0.5F) {
         this.tween6.setFloat(this.value314);
      }

      if (this.value314 >= 0) {
         this.tween6.setFloat2(this.value314);
      }

      this.animatedInt.setInt(this.getInt2());
      float f = this.tween5.getFloat();
      float f1 = this.tween6.getFloat();
      int i = this.animatedInt.getInt2();
      if (f > 0.001F) {
         float f2 = value4 + 10.0F + f1 * (16.0F + value2);
         float f29 = f2 + -6.5F;
         float f30 = value3 + 3.0F;
         int l1 = Theme.background();
         float f20 = value * f;
         float f19 = 2.0F;
         float f18 = 1.0F;
         float f17 = 0.0F;
         int j1 = 436207616;
         float f16 = 1.5F;
         int i1 = l1;
         float f15 = 8.0F;
         float f14 = 8.0F;
         float f13 = 8.0F;
         float f12 = 8.0F;
         float f11 = 26.0F;
         float f10 = 29.0F;
         float f9 = f30;
         float f8 = f29;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f10, j1, i1, f8, f9, f19, f12, f11, f18, matrix4f, f20, i, f14, f16, f13, f15, f17
         );
      }

      List list = ThemeColors.getList();
      float f3 = 8.0F;
      float f4 = value3 + 8.0F;

      for (int j = 0; j < 7; j++) {
         int k = j < list.size() ? (Integer)list.get(j) : -1;
         float f28 = value4 + 10.0F + j * (16.0F + value2);
         float f27 = 2.0F;
         float f26 = 1.0F;
         float f25 = 0.0F;
         int k1 = 436207616;
         float f24 = 0.0F;
         byte b0 = 0;
         float f23 = 16.0F;
         float f22 = 16.0F;
         float f21 = f28;
         ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
            f22, k1, k, f21, f4, f27, f3, f23, f26, matrix4f, value, b0, f3, f24, f3, f3, f25
         );
      }
   }

   @Override
   public void setColorSupplier2(ColorSupplier colorSupplier) {
      this.update14();
      this.value314 = -1;
      this.flag11 = false;
      this.flag12 = false;
      this.flag13 = false;
      this.flag14 = false;
      this.value319 = -1;
      this.flag8 = this.flag9 = this.flag10 = false;
      this.tween5.setFloat(0.0F);
      this.animatedInt.setInt2(this.getInt2());
      super.setColorSupplier2(colorSupplier);
   }

   private void update14() {
      int i = this.colorSetting.getInt3();
      float[] afloat = MathUtil.getFloatArrayByInt2(i);
      this.value306 = afloat[0];
      this.value307 = afloat[1];
      this.value308 = afloat[2];
      this.value309 = (i >> 24 & 0xFF) / 255.0F;
   }

   private boolean isInt(int count) {
      return this.value319 == 4 ? count >= 48 && count <= 57 || count >= 97 && count <= 102 || count >= 65 && count <= 70 || count == 35 : count >= 48 && count <= 57;
   }

   private void setInt(int count) {
      if (this.value319 != count) {
         if (this.value319 >= 0) {
            this.textInputController.update2();
         }

         this.value319 = count;
         this.colorFormat3 = this.colorFormat2;
         this.textInputController.setString(this.getStringByInt(count));
      }
   }

   private String getStringByInt(int count) {
      if (count == 4) {
         return String.format("%06X", this.getInt2() & 16777215);
      } else {
         return count == 3
            ? String.valueOf(Math.round(this.value309 * 100.0F))
            : this.getStringArrayByColorFormat(this.colorFormat2)[count].replace("°", "").replace("%", "");
      }
   }

   private String[] getStringArrayByColorFormat(ColorFormat colorFormat) {
      int i = this.getInt();
      int j = i >> 16 & 0xFF;
      int k = i >> 8 & 0xFF;
      int l = i & 0xFF;

      return switch (colorFormat) {
         case RGB -> new String[]{String.valueOf(j), String.valueOf(k), String.valueOf(l)};
         case HSL -> {
            float[] afloat = MathUtil.getFloatArrayByInt(this.getInt2());
            yield new String[]{Math.round(afloat[0] * 360.0F) + "°", Math.round(afloat[1] * 100.0F) + "%", Math.round(afloat[2] * 100.0F) + "%"};
         }
         default -> new String[]{"", "", ""};
      };
   }

   private void onMatrix4fFloatFloatFloatStringFloat(Matrix4f matrix4f, float value, float value2, float value3, String text, float value4) {
      String s = this.textInputController.getString();
      float f = TextShader.getFloatByStringFloat(s, 12.0F);
      float f1 = TextShader.getFloatByStringFloat(text, 12.0F);
      float f2 = value2 + (value4 - (f + f1)) / 2.0F;
      TextInputState textinputstate = this.textInputController.getTextInputState();
      int k = Theme.foreground();
      boolean flag = this.textInputController.check();
      int i = k;
      textinputstate.onFloatIntMatrix4fFloatBooleanFloatFloat(value3, i, matrix4f, value, flag, f2, value4);
      float f5 = f2 + f;
      int j = Theme.foreground();
      float f4 = 12.0F;
      float f3 = f5;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value3, f3, j, f4, value, text, matrix4f);
   }

   private void onFloatMatrix4fFloatFloatFloatFloat(float value, Matrix4f matrix4f2, float value2, float value3, float value4, float value5) {
      this.tween7.setFloat2(this.flag14 ? 1.0F : 0.0F);
      float f = this.tween7.getFloat();
      if (!(f <= 0.001F)) {
         float f1 = value5 + 6.0F;
         float f2 = getFloat6();
         float f3 = value3 * EasingPresets.getFloatByFloat3(f);
         Matrix4f matrix4f = EasingPresets.getMatrix4fByMatrix4fFloatFloatFloat(matrix4f2, f, value + 30.0F, f1);
         matrix4f = new Matrix4f(matrix4f).translate(0.0F, -(1.0F - f) * 6.0F, 0.0F);
         int j = Theme.elevated();
         float f10 = 8.0F;
         float f9 = 60.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f10, value, j, matrix4f, f2, f9, f3, f1);
         ColorFormat[] acolorformat = ColorFormat.values();

         for (int i = 0; i < acolorformat.length; i++) {
            float f4 = f1 + 4.0F + i * 30.0F;
            float f5 = value + 4.0F;
            float f6 = 52.0F;
            boolean flag = acolorformat[i] == colorFormat;
            boolean flag1 = value4 >= f5 && value4 <= f5 + f6 && value2 >= f4 && value2 <= f4 + 26.0F;
            float f7 = flag ? 1.0F : (flag1 ? 0.7F : 0.4F);
            int k1 = Theme.surface();
            float f20 = f3 * f7;
            float f19 = 3.0F;
            float f18 = 1.0F;
            float f17 = 0.0F;
            int l = 436207616;
            float f16 = 0.0F;
            byte b0 = 0;
            int k = k1;
            float f15 = 8.0F;
            float f14 = 8.0F;
            float f13 = 8.0F;
            float f12 = 8.0F;
            float f11 = 26.0F;
            ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
               f6, l, k, f5, f4, f19, f12, f11, f18, matrix4f, f20, b0, f14, f16, f13, f15, f17
            );
            String s = acolorformat[i].text;
            float f8 = TextShader.getFloatByStringFloat(s, 12.0F);
            float f25 = f5 + (f6 - f8) / 2.0F;
            float f26 = f4 + 7.0F;
            int j1 = Theme.foreground();
            float f24 = f3 * f7;
            int i1 = j1;
            float f23 = 12.0F;
            float f22 = f26;
            float f21 = f25;
            TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f22, f21, i1, f23, f24, s, matrix4f);
         }
      }
   }

   private void setColorFormat(ColorFormat colorFormat2) {
      if (colorFormat2 != colorFormat) {
         this.value318 = colorFormat2.ordinal() > colorFormat.ordinal() ? -1 : 1;
         colorFormat = colorFormat2;
         ColorFormatState.onColorFormat(colorFormat2);
         this.tween8.setFloat(1.0F);
         this.tween8.setFloat2(0.0F);
      }
   }

   @Override
   protected void update7() {
      this.flag8 = this.flag10 = this.flag9 = false;
      if (this.textInputController.isFlag()) {
         this.textInputController.update2();
      }
   }
}
