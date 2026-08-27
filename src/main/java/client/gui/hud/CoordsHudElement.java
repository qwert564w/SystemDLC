package client.gui.hud;

import client.api.Theme;
import client.data.IconMetrics;
import client.gui.widget.RenderElement;
import client.gui.widget.ScissorStack;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.Interpolation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import org.joml.Matrix4f;

public class CoordsHudElement extends RenderElement {
   private static final float value271 = 32.0F;
   private static final float value272 = 14.0F;
   private static final float value273 = 20.0F;
   private static final float value274 = 6.0F;
   private static final float value275 = 8.0F;
   private static final float value276 = 6.0F;
   private static final float value277 = 6.0F;
   private static final float value278 = 3.0F;
   private static final float value279 = 0.06F;
   private static final float value280 = 0.001F;
   private final Interpolation interpolation2 = new Interpolation();
   private long time = -1L;
   private float value281 = -1.0F;
   private float value282 = -1.0F;
   private long time2 = -1L;
   private int value283 = Integer.MIN_VALUE;
   private int value284 = Integer.MIN_VALUE;
   private int value285 = Integer.MIN_VALUE;
   private String text = "—";
   private String text2 = "—";
   private String text3 = "—";

   public CoordsHudElement() {
      this.getShowIkonku().setVisibleWhen(() -> true);
      this.getShowBackgroundYIkonki().setVisibleWhen(() -> this.getShowIkonku().isFlag3());
   }

   @Override
   public String getString() {
      return "Соординатез";
   }

   private boolean check24() {
      return this.check25() && this.getShowBackgroundYIkonki().isFlag3();
   }

   private void update4() {
      long i = UiContext.getTime();
      if (i != this.time2) {
         this.time2 = i;
         ClientPlayerEntity clientplayerentity = Feature.mc.player;
         if (clientplayerentity == null) {
            String s = this.check4() ? "0" : "—";
            String s1 = this.check4() ? "64" : "—";
            this.text = s;
            this.text2 = s1;
            this.text3 = s;
         } else {
            int j = (int)Math.floor(clientplayerentity.getX());
            int k = (int)Math.floor(clientplayerentity.getY());
            int l = (int)Math.floor(clientplayerentity.getZ());
            if (j != this.value283) {
               this.value283 = j;
               this.text = Integer.toString(j);
            }

            if (k != this.value284) {
               this.value284 = k;
               this.text2 = Integer.toString(k);
            }

            if (l != this.value285) {
               this.value285 = l;
               this.text3 = Integer.toString(l);
            }
         }
      }
   }

   private String getString4() {
      this.update4();
      return this.text2;
   }

   private float getFloat28() {
      return this.check25() ? 26.0F : 0.0F;
   }

   @Override
   public CategoryType getCategoryType() {
      return CategoryType.COORDS;
   }

   private String getString5() {
      this.update4();
      return this.text3;
   }

   private float getFloat29() {
      this.update5();
      return this.getFloatByFloat2(this.getFloat31());
   }

   @Override
   public float getFloat9() {
      return 32.0F;
   }

   @Override
   public String getString3() {
      return "cs";
   }

   private float getFloat30() {
      return this.getFloatByFloat2(this.getFloat28());
   }

   private void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      if (this.value282 > 0.001F) {
         int l = Theme.elevated();
         float f7 = this.getFloatByFloat(value * this.value282);
         int i = l;
         float f6 = 6.0F;
         float f5 = 20.0F;
         float f4 = 20.0F;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f6, value3, i, matrix4f, f5, f4, f7, value2);
      }

      float f = IconMetrics.getFloatByCategoryType2(CategoryType.COORDS);
      float f1 = IconMetrics.getFloatByCategoryType(CategoryType.COORDS);
      float f2 = value3 + (20.0F - f) / 2.0F;
      float f3 = value2 + (20.0F - f1) / 2.0F;
      CategoryType categorytype1 = CategoryType.COORDS;
      int k = Theme.mutedFg();
      float f8 = value * this.value281;
      int j = k;
      CategoryType categorytype = categorytype1;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f8, j, matrix4f, f3, categorytype, f1, f2, f);
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getKoordinaty().setBoolean(flag);
   }

   private void update5() {
      long i = UiContext.getTime();
      if (this.time != i) {
         this.time = i;
         boolean flag = this.check25();
         boolean flag1 = this.check24();
         if (this.value281 < 0.0F) {
            this.value281 = flag ? 1.0F : 0.0F;
            this.value282 = flag1 ? 1.0F : 0.0F;
         } else {
            float f = this.interpolation2.getFloat2();
            float f7 = flag ? 1.0F : 0.0F;
            float f3 = 0.06F;
            float f2 = f7;
            float f1 = this.value281;
            this.value281 = Interpolation.getFloatByFloatFloatFloatFloat2(f2, f1, f, f3);
            f7 = flag1 ? 1.0F : 0.0F;
            float f6 = 0.06F;
            float f5 = f7;
            float f4 = this.value282;
            this.value282 = Interpolation.getFloatByFloatFloatFloatFloat2(f5, f4, f, f6);
         }
      }
   }

   private static float getFloatByMatrix4fStringStringFloatFloatFloat(Matrix4f matrix4f, String text, String text2, float value, float value2, float value3) {
      int i = Theme.mutedFg();
      float f2 = 14.0F;
      TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value3, text2, f2, value2, i, value, matrix4f);
      float f3 = 14.0F;
      float f = TextShader.getFloatByFloatString(f3, text2);
      float f1 = value2 + f + 3.0F;
      int j = Theme.foreground();
      float f4 = 14.0F;
      TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value3, text, f4, f1, j, value, matrix4f);
      float f5 = 14.0F;
      return f1 + TextShader.getFloatByFloatString(f5, text);
   }

   private boolean check25() {
      return this.getShowIkonku().isFlag3();
   }

   private float getFloatByFloat2(float value) {
      float f = 8.0F + value;
      f += getFloatByStringString("X", this.getString6());
      f += 6.0F;
      f += getFloatByStringString("Y", this.getString4());
      f += 6.0F;
      f += getFloatByStringString("Z", this.getString5());
      return f + 8.0F;
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getKoordinaty().isFlag3();
   }

   private static float getFloatByStringString(String text, String text2) {
      float f = 14.0F;
      float f2 = TextShader.getFloatByFloatString(f, text) + 3.0F;
      float f1 = 14.0F;
      return f2 + TextShader.getFloatByFloatString(f1, text2);
   }

   private float getFloat31() {
      return 26.0F * Math.max(0.0F, this.value281 < 0.0F ? (this.check25() ? 1.0F : 0.0F) : this.value281);
   }

   private String getString6() {
      this.update4();
      return this.text;
   }

   @Override
   public float getFloat10() {
      return 32.0F;
   }

   @Override
   public float getFloat11() {
      return this.getFloat30();
   }

   @Override
   public float getFloat14() {
      return this.getFloat30();
   }

   @Override
   protected boolean check20() {
      return false;
   }

   @Override
   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      this.update5();
      float f = value * value2;
      if (!(f <= 0.001F)) {
         float f1 = this.getFloatByFloat2(this.getFloat31());
         if (!(f1 <= 0.5F)) {
            float f2 = this.getValue260();
            float f3 = this.getValue261();
            int k = Theme.background();
            float f16 = this.getFloatByFloat(f);
            float f15 = 1.0F;
            float f14 = 1.0F;
            float f13 = 0.0F;
            int j = 436207616;
            float f12 = 0.0F;
            byte b0 = 0;
            int i = k;
            float f11 = 12.0F;
            float f10 = 12.0F;
            float f9 = 12.0F;
            float f8 = 12.0F;
            float f7 = 32.0F;
            ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
               f1, j, i, f2, f3, f15, f8, f7, f14, matrix4f, f16, b0, f10, f12, f9, f11, f13
            );
            float f4 = f3 + 6.0F;
            float f5 = f3 + 9.0F;
            float f17 = 32.0F;
            ScissorStack.onFloatFloatFloatFloat(f1, f17, f3, f2);

            try {
               float f6 = f2 + 8.0F;
               if (this.value281 > 0.001F) {
                  this.onFloatMatrix4fFloatFloat(f, matrix4f, f4, f6);
                  f6 += 26.0F * this.value281;
               }

               String s1 = this.getString6();
               String s = "X";
               f6 = getFloatByMatrix4fStringStringFloatFloatFloat(matrix4f, s1, s, f5, f6, f) + 6.0F;
               String s3 = this.getString4();
               String s2 = "Y";
               f6 = getFloatByMatrix4fStringStringFloatFloatFloat(matrix4f, s3, s2, f5, f6, f) + 6.0F;
               String s5 = this.getString5();
               String s4 = "Z";
               getFloatByMatrix4fStringStringFloatFloatFloat(matrix4f, s5, s4, f5, f6, f);
            } finally {
               ScissorStack.update();
            }
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
      return this.getFloat29();
   }
}
