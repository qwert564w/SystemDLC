package client.gui.hud;

import client.api.Theme;
import client.data.IconMetrics;
import client.enums.HudElement;
import client.gui.widget.RenderElement;
import client.gui.widget.ScissorStack;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.util.Easings;
import client.util.Interpolation;
import client.util.PingTracker;
import client.util.TextFormatUtil;
import client.util.TpsTracker;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import org.joml.Matrix4f;

public class StatsHud extends RenderElement {
   private static final float value271 = 32.0F;
   private static final float value272 = 20.0F;
   private static final float value273 = 14.0F;
   private static final float value274 = 6.0F;
   private static final float value275 = 8.0F;
   private static final float value276 = 6.0F;
   private static final float value277 = 3.0F;
   private static final float value278 = 6.0F;
   private static final float value279 = 0.001F;
   private static final String text = "System";
   private static final float value280 = 0.06F;
   private static final float value281 = 0.1F;
   private static final float value282 = 0.25F;
   private static final float value283 = 5.0F;
   private static final float value284 = 12.0F;
   private static final float value285 = 0.4F;
   private static final float value286 = 0.32F;
   private static final HudElement[] hudElementArray = HudElement.values();
   private static final int value287 = hudElementArray.length;
   private final BooleanSetting showFps;
   private final BooleanSetting showPing;
   private final BooleanSetting showTps;
   private final BooleanSetting showBps;
   private final BooleanSetting razdelno;
   private final Interpolation interpolation2;
   private static final CategoryType[] categoryTypeArray = new CategoryType[]{
      CategoryType.LOGO, CategoryType.BOLT, CategoryType.GLOBE, CategoryType.RACK, CategoryType.CUBE
   };
   private static final String[] stringArray = new String[]{"", " Fps", " MS", " TPS", " BPS"};
   private final BooleanSetting[] booleanSettingArray;
   private final float[] floatArray5;
   private final String[] stringArray2;
   private final String[] stringArray3;
   private final float[] floatArray6;
   private final float[] floatArray7;
   private final float[] floatArray8;
   private long time;
   private boolean flag3;
   private float value288;
   private float value289;
   private float value290;
   private float value291;
   private float value292;
   private double value293;
   private double value294;
   private long time2;
   private float value295;
   private long time3;
   private int value296;
   private int value297;
   private float value298;
   private float value299;

   public StatsHud() {
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать FPS");
      booleansetting.setDescription("Отображать счётчик FPS.");
      this.showFps = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать пинг");
      booleansetting.setDescription("Отображать пинг до сервера.");
      this.showPing = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать TPS");
      booleansetting.setDescription("Отображать TPS сервера.");
      this.showTps = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать BPS");
      booleansetting.setDescription("Отображать скорость в блоках в секунду.");
      this.showBps = booleansetting;
      booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Раздельно");
      booleansetting.setDescription("Разделить блоки ватермарка на отдельные плашки.");
      this.razdelno = booleansetting;
      this.interpolation2 = new Interpolation();
      this.booleanSettingArray = new BooleanSetting[]{null, this.showFps, this.showPing, this.showTps, this.showBps};
      this.floatArray5 = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
      this.stringArray2 = new String[]{"System", "0 Fps", "0 MS", "0.0 TPS", "0.0 BPS"};
      this.stringArray3 = new String[]{"", "0", "0", "0.0", "0.0"};
      this.floatArray6 = new float[value287];
      this.floatArray7 = new float[value287];
      this.floatArray8 = new float[value287];
      this.time = -1L;
      this.value288 = 1.0F;
      this.value289 = 1.0F;
      this.value293 = Double.NaN;
      this.time3 = -1L;
      this.value296 = -1;
      this.value297 = -1;
      this.value298 = Float.NaN;
      this.value299 = Float.NaN;
      this.getShowIkonku().setVisibleWhen(() -> true);
      this.getShowBackgroundYIkonki().setVisibleWhen(() -> this.getShowIkonku().isFlag3());
      this.onSettingArray(new Setting[]{this.showFps, this.showPing, this.showTps, this.showBps, this.razdelno});
   }

   @Override
   protected boolean check() {
      return false;
   }

   @Override
   public String getString() {
      return "Шатермарк";
   }

   private float getFloat28() {
      return this.getFloatByInt2(0) + this.getFloat29() + TextShader.getFloatByStringFloat("System", 14.0F) + this.getFloatByInt(0);
   }

   private float getFloatByInt(int count) {
      return count == this.getInt() ? 8.0F : this.getValue291();
   }

   private void update4() {
      long i = UiContext.getTime();
      if (i != this.time3) {
         this.time3 = i;
         int j = Feature.mc != null ? Feature.mc.getCurrentFps() : 0;
         if (j != this.value296) {
            this.value296 = j;
            HudElement hudelement7 = HudElement.FPS;
            String s = Integer.toString(j);
            HudElement hudelement = hudelement7;
            this.onStringHudElement(s, hudelement);
         }

         int k = PingTracker.getInstance().getInt();
         if (k != this.value297) {
            this.value297 = k;
            HudElement hudelement4 = HudElement.PING;
            String s1 = Integer.toString(k);
            HudElement hudelement1 = hudelement4;
            this.onStringHudElement(s1, hudelement1);
         }

         float f = TpsTracker.getInstance().getFloat();
         if (f != this.value298) {
            this.value298 = f;
            HudElement hudelement5 = HudElement.TPS;
            String s2 = TextFormatUtil.getStringByDouble(f);
            HudElement hudelement2 = hudelement5;
            this.onStringHudElement(s2, hudelement2);
         }

         if (this.value295 != this.value299) {
            this.value299 = this.value295;
            HudElement hudelement6 = HudElement.BPS;
            String s3 = TextFormatUtil.getStringByDouble(this.value295);
            HudElement hudelement3 = hudelement6;
            this.onStringHudElement(s3, hudelement3);
         }
      }
   }

   private float getValue288() {
      return 26.0F * this.value288;
   }

   private float getFloat29() {
      return 26.0F;
   }

   private void update5() {
      ClientPlayerEntity clientplayerentity = Feature.mc != null ? Feature.mc.player : null;
      if (clientplayerentity == null) {
         this.value293 = Double.NaN;
         this.value295 = 0.0F;
      } else {
         double d0 = clientplayerentity.getX();
         double d1 = clientplayerentity.getZ();
         long i = System.nanoTime();
         if (Double.isNaN(this.value293)) {
            this.value293 = d0;
            this.value294 = d1;
            this.time2 = i;
         } else {
            float f = (float)(i - this.time2) / 1.0E9F;
            if (f >= 0.1F) {
               double d2 = d0 - this.value293;
               double d3 = d1 - this.value294;
               float f1 = (float)(Math.sqrt(d2 * d2 + d3 * d3) / f);
               float f3 = 0.25F;
               float f2 = this.value295;
               this.value295 = Interpolation.getFloatByFloatFloatFloatFloat2(f1, f2, f, f3);
               this.value293 = d0;
               this.value294 = d1;
               this.time2 = i;
            }
         }
      }
   }

   @Override
   public float getFloat9() {
      return 32.0F;
   }

   private float getFloat30() {
      float f = this.getFloat28();
      float f1 = 0.0F;

      for (int i = 1; i < value287; i++) {
         f += this.getFloatByIntString(i, this.getStringByHudElement(hudElementArray[i])) * this.floatArray5[i];
         f1 += this.floatArray5[i];
      }

      return f + f1 * 5.0F * this.value291;
   }

   private float getValue291() {
      return 3.0F + 5.0F * this.value291;
   }

   @Override
   public String getString3() {
      return "wk";
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.WATERMARK;
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getVatermarka().setBoolean(flag);
   }

   private void onStringHudElement(String text, HudElement hudElement) {
      int i = hudElement.ordinal();
      this.stringArray3[i] = text;
      this.stringArray2[i] = text + stringArray[i];
   }

   private float getFloatByHudElement(HudElement hudElement) {
      if (hudElement == HudElement.LOGO) {
         return this.getFloat28();
      } else {
         int i = hudElement.ordinal();
         return this.getFloatByIntString(i, this.getStringByHudElement(hudElement)) * this.floatArray5[i];
      }
   }

   private float getFloat31() {
      return this.value291 >= 0.4F ? 0.0F : 1.0F - this.value291 / 0.4F;
   }

   private float getFloatByIntString(int count, String text) {
      return this.getFloatByInt2(count) + this.getValue288() + TextShader.getFloatByStringFloat(text, 14.0F) + this.getFloatByInt(count);
   }

   private void onFloatFloatFloatFloatCategoryTypeFloatFloatMatrix4f(
      float value, float value2, float value3, float value4, CategoryType categoryType, float value5, float value6, Matrix4f matrix4f
   ) {
      if (value3 > 0.001F) {
         int k = Theme.elevated();
         float f5 = this.getFloatByFloat(value4 * value3);
         int i = k;
         float f4 = 6.0F;
         float f3 = 20.0F;
         float f2 = 20.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f4, value2, i, matrix4f, f3, f2, f5, value6);
      }

      float f = value2 + (20.0F - value5) / 2.0F;
      float f1 = value6 + (20.0F - value) / 2.0F;
      int j = Theme.mutedFg();
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value4, j, matrix4f, f1, categoryType, value, f, value5);
   }

   private static void onMatrix4fStringFloatStringFloatFloat(Matrix4f matrix4f, String text, float value, String text2, float value2, float value3) {
      int i = Theme.foreground();
      float f = 14.0F;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value2, value, i, f, value3, text2, matrix4f);
      float f3 = value + TextShader.getFloatByStringFloat(text2, 14.0F);
      int j = Theme.mutedFg();
      float f2 = 14.0F;
      float f1 = f3;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value2, f1, j, f2, value3, text, matrix4f);
   }

   private static float getFloatByBooleanFloatFloat(boolean flag, float value, float value2) {
      float f2 = flag ? 1.0F : 0.0F;
      float f1 = 0.06F;
      float f = f2;
      return Interpolation.getFloatByFloatFloatFloatFloat2(f, value2, value, f1);
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getVatermarka().isFlag3();
   }

   private String getStringByHudElement(HudElement hudElement) {
      this.update4();
      return this.stringArray2[hudElement.ordinal()];
   }

   public void update6() {
      long i = UiContext.getTime();
      if (this.time != i) {
         this.time = i;
         boolean flag = this.getShowIkonku().isFlag3();
         boolean flag1 = flag && this.getShowBackgroundYIkonki().isFlag3();
         float f = this.razdelno.isFlag3() ? 1.0F : 0.0F;
         if (!this.flag3) {
            for (int k = 1; k < value287; k++) {
               this.floatArray5[k] = this.booleanSettingArray[k].isFlag3() ? 1.0F : 0.0F;
            }

            this.value288 = flag ? 1.0F : 0.0F;
            this.value289 = flag1 ? 1.0F : 0.0F;
            this.value290 = f;
            this.value291 = f;
            this.value292 = this.getFloat30();
            this.flag3 = true;
         } else {
            float f1 = this.interpolation2.getFloat2();
            this.update5();

            for (int j = 1; j < value287; j++) {
               float[] afloat = this.floatArray5;
               float f6 = this.floatArray5[j];
               boolean flag2 = this.booleanSettingArray[j].isFlag3();
               float f2 = f6;
               afloat[j] = getFloatByBooleanFloatFloat(flag2, f1, f2);
            }

            float f3 = this.value288;
            this.value288 = getFloatByBooleanFloatFloat(flag, f1, f3);
            float f4 = this.value289;
            this.value289 = getFloatByBooleanFloatFloat(flag1, f1, f4);
            float f5 = f1 / 0.32F;
            if (this.value290 < f) {
               this.value290 = Math.min(f, this.value290 + f5);
            } else if (this.value290 > f) {
               this.value290 = Math.max(f, this.value290 - f5);
            }

            this.value291 = Easings.getFloatByFloat7(this.value290);
            this.value292 = this.getFloat30();
         }
      }
   }

   private int getInt() {
      for (int i = value287 - 1; i > 0; i--) {
         if (this.floatArray5[i] > 0.001F) {
            return i;
         }
      }

      return 0;
   }

   private float getFloatByInt2(int count) {
      return count == 0 ? 8.0F : this.getValue291();
   }

   @Override
   public float getFloat10() {
      return 32.0F;
   }

   @Override
   protected boolean check11() {
      return false;
   }

   @Override
   public float getFloat11() {
      return this.getFloat24();
   }

   @Override
   public float getFloat14() {
      return this.getFloat24();
   }

   @Override
   protected boolean check20() {
      return false;
   }

   @Override
   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      this.update6();
      float f = value * value2;
      if (!(f <= 0.001F)) {
         float f1 = this.getValue260();
         float f2 = this.getValue261();
         float f3 = this.value292;
         float f4 = this.getFloat31();
         float f5 = this.value291;
         int i = 0;
         float f6 = 0.0F;
         boolean flag = false;

         for (int j = 0; j < value287; j++) {
            float f7 = this.floatArray5[j];
            if (f7 <= 0.001F) {
               this.floatArray6[j] = f1 + f6;
            } else {
               if (flag) {
                  f6 += 5.0F * f5 * f7;
               }

               this.floatArray6[j] = f1 + f6;
               float f8 = this.getFloatByHudElement(hudElementArray[j]);
               this.floatArray7[i] = f6 + f8 * 0.5F;
               this.floatArray8[i] = f8 * 0.5F + 12.0F * f4;
               i++;
               f6 += f8;
               flag = true;
            }
         }

         if (i > 0) {
            float f35 = 12.0F * f4;
            int j1 = i - 1;
            if (j1 == 0) {
               this.floatArray8[0] = this.floatArray8[0] - f35;
            } else {
               this.floatArray7[0] = this.floatArray7[0] + f35 * 0.5F;
               this.floatArray8[0] = this.floatArray8[0] - f35 * 0.5F;
               this.floatArray7[j1] = this.floatArray7[j1] - f35 * 0.5F;
               this.floatArray8[j1] = this.floatArray8[j1] - f35 * 0.5F;
            }
         }

         int l1 = Theme.background();
         float f44 = this.getFloatByFloat(f);
         float[] afloat2 = i > 0 ? this.floatArray7 : null;
         float[] afloat3 = i > 0 ? this.floatArray8 : null;
         float f23 = 12.0F;
         float[] afloat1 = afloat3;
         float[] afloat = afloat2;
         float f22 = f44;
         float f21 = 1.0F;
         float f20 = 1.0F;
         float f19 = 0.0F;
         int l = 436207616;
         float f18 = 0.0F;
         byte b0 = 0;
         int k = l1;
         float f17 = 12.0F;
         float f16 = 12.0F;
         float f15 = 12.0F;
         float f14 = 12.0F;
         float f13 = 32.0F;
         ShapeShader.onFloatFloatFloatIntFloatArrayFloatFloatArrayFloatMatrix4fFloatFloatFloatFloatFloatFloatIntFloatFloatIntFloat(
            f18, f1, f2, l, afloat1, f14, afloat, f17, matrix4f, f16, f20, f19, f21, f15, f3, k, f22, f13, b0, f23
         );
         float f36 = f2 + 6.0F;
         float f37 = f2 + 9.0F;
         float f24 = 32.0F;
         ScissorStack.onFloatFloatFloatFloat(f3, f24, f2, f1);

         try {
            for (int k1 = 0; k1 < value287; k1++) {
               float f9 = this.floatArray5[k1];
               if (!(f9 <= 0.001F)) {
                  TextShader.update2();
                  float f10 = this.floatArray6[k1];
                  float f11 = f * f9;
                  float f12 = f10 + this.getFloatByInt2(k1);
                  if (hudElementArray[k1] == HudElement.LOGO) {
                     CategoryType categorytype2 = CategoryType.LOGO;
                     float f41 = IconMetrics.getFloatByCategoryType2(CategoryType.LOGO);
                     float f42 = IconMetrics.getFloatByCategoryType(CategoryType.LOGO);
                     float f27 = 1.0F;
                     float f26 = f42;
                     float f25 = f41;
                     CategoryType categorytype1 = categorytype2;
                     this.onFloatFloatFloatFloatCategoryTypeFloatFloatMatrix4f(f26, f12, f27, f11, categorytype1, f25, f36, matrix4f);
                     float f40 = f12 + this.getFloat29();
                     int i1 = Theme.foreground();
                     float f29 = 14.0F;
                     float f28 = f40;
                     String s = "System";
                     TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f37, f28, i1, f29, f11, s, matrix4f);
                  } else {
                     if (this.value288 > 0.001F) {
                        CategoryType categorytype = categoryTypeArray[k1];
                        float f38 = IconMetrics.getFloatByCategoryType2(categorytype);
                        float f39 = IconMetrics.getFloatByCategoryType(categorytype);
                        float f43 = f11 * this.value288;
                        float f33 = this.value289;
                        float f32 = f43;
                        float f31 = f39;
                        float f30 = f38;
                        this.onFloatFloatFloatFloatCategoryTypeFloatFloatMatrix4f(f31, f12, f33, f32, categorytype, f30, f36, matrix4f);
                     }

                     String s4 = this.stringArray3[k1];
                     String s3 = stringArray[k1];
                     float f34 = f12 + this.getValue288();
                     String s2 = s3;
                     String s1 = s4;
                     onMatrix4fStringFloatStringFloatFloat(matrix4f, s2, f34, s1, f37, f11);
                  }
               }
            }
         } finally {
            ScissorStack.update();
         }
      }
   }

   @Override
   public float getFloat22() {
      return this.getValue260();
   }

   @Override
   public float getFloat23() {
      return 32.0F;
   }

   @Override
   public float getFloat24() {
      this.update6();
      return this.value292;
   }
}
