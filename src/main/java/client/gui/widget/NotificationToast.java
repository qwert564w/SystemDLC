package client.gui.widget;

import client.api.Icon;
import client.api.Theme;
import client.api.UiMetrics;
import client.enums.Edge;
import client.render.MatrixUtil;
import client.render.ShapeShader;
import client.render.TextShader;
import client.util.Animation;
import client.util.Easings;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class NotificationToast implements UiMetrics, Theme {
   public static final float value235 = 32.0F;
   public static final float value236 = 16.0F;
   public static final float value237 = 6.0F;
   public static final float value238 = 6.0F;
   public static final float value239 = 14.0F;
   public static final float value240 = 4.0F;
   private static final float value241 = 4.5F;
   private static final float value242 = 0.001F;
   private final Icon icon;
   private final String text;
   private final String text2;
   private final long time;
   private final long time2;
   private final boolean flag;
   private final Animation animation;
   private final Animation animation2;
   private boolean flag2;
   private float value243 = -1.0F;

   private NotificationToast(Icon icon2, String text3, String text4, long time3, boolean flag2) {
      this.icon = icon2;
      this.text = text3 == null ? "" : text3;
      this.text2 = text4 == null ? "" : text4;
      this.time = System.currentTimeMillis();
      this.time2 = time3;
      this.flag = flag2;
      this.animation = new Animation(4.5F).getAnimationByFunction(Easings::getFloatByFloat7);
      this.animation2 = new Animation(4.5F).getAnimation();
      this.animation.setBoolean(true);
   }

   public void setFlag2() {
      this.flag2 = true;
   }

   public boolean check() {
      return this.animation.getFloat() > 0.001F;
   }

   public void onFloat(float value) {
      this.animation2.setFloat4(value);
   }

   public String getText2() {
      return this.text2;
   }

   public static NotificationToast getNotificationToastByStringIconString(String text, Icon icon, String text2) {
      return new NotificationToast(icon, text2, text, Long.MAX_VALUE, true);
   }

   public void onFloatDrawContextEdgeFloatFloatFloat(float value, DrawContext drawContext, Edge edge, float value2, float value3, float value4) {
      float f = this.animation.getFloat();
      if (!(f <= 0.001F)) {
         float f1 = this.animation2.getValue7();
         float f2 = this.getFloat();
         float f3 = (1.0F - f) * value4;
         float f4 = edge.getValue() * f3;
         float f5 = edge.getValue2() * f3;
         float f6 = value2 - f2 / 2.0F + f4;
         float f7 = f1 + f5;
         float f8 = value3 * f;
         float f9 = 0.96F + 0.04F * f;
         float f10 = f6 + f2 / 2.0F;
         float f11 = f7 + 16.0F;
         MatrixStack matrixstack = drawContext.getMatrices();
         float f16 = 0.0F;
         Matrix4f matrix4f = MatrixUtil.getMatrix4fByFloatFloatFloatFloatMatrixStack(f9, f10, f16, f11, matrixstack);

         try {
            int i1 = Theme.background();
            float f26 = f8 * value;
            float f25 = 1.0F;
            float f24 = 1.0F;
            float f23 = 0.0F;
            int j = 436207616;
            float f22 = 0.0F;
            byte b0 = 0;
            int i = i1;
            float f21 = 12.0F;
            float f20 = 12.0F;
            float f19 = 12.0F;
            float f18 = 12.0F;
            float f17 = 32.0F;
            ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
               f2, j, i, f6, f7, f25, f18, f17, f24, matrix4f, f26, b0, f20, f22, f19, f21, f23
            );
            float f12 = f6 + 6.0F;
            float f13 = f7 + 8.0F;
            float f27 = 16.0F;
            this.icon.draw(f13, f12, f8, matrix4f, f27, drawContext);
            float f14 = f12 + 16.0F + 6.0F;
            float f15 = f7 + 9.0F;
            if (!this.text.isEmpty()) {
               String s3 = this.text;
               int k = Theme.mutedFg();
               float f28 = 14.0F;
               String s = s3;
               TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f15, f14, k, f28, f8, s, matrix4f);
               f14 += TextShader.getFloatByStringFloat(this.text, 14.0F) + (this.text2.isEmpty() ? 0.0F : 4.0F);
            }

            if (!this.text2.isEmpty()) {
               String s2 = this.text2;
               int l = Theme.foreground();
               float f29 = 14.0F;
               String s1 = s2;
               TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f15, f14, l, f29, f8, s1, matrix4f);
            }
         } finally {
            matrixstack.pop();
         }
      }
   }

   public Icon getIcon() {
      return this.icon;
   }

   public String getText() {
      return this.text;
   }

   public static NotificationToast getNotificationToastByIconStringStringLong(Icon icon, String text, String text2, long time) {
      return new NotificationToast(icon, text2, text, Math.max(50L, time), false);
   }

   public void setFloat(float value) {
      if (!this.flag && !this.flag2) {
         long i = System.currentTimeMillis() - this.time;
         if (i >= this.time2) {
            this.flag2 = true;
         }
      }

      this.animation.setBoolean(!this.flag2);
      this.animation.setFloat2(value);
      this.animation2.setFloat2(value);
   }

   public boolean check2() {
      return this.flag2 && this.animation.check();
   }

   public void update() {
      if (!this.flag) {
         this.flag2 = true;
      }
   }

   public float getFloat() {
      if (this.value243 < 0.0F) {
         float f = this.text.isEmpty() ? 0.0F : TextShader.getFloatByStringFloat(this.text, 14.0F);
         float f1 = this.text2.isEmpty() ? 0.0F : TextShader.getFloatByStringFloat(this.text2, 14.0F);
         float f2 = f > 0.0F && f1 > 0.0F ? 4.0F : 0.0F;
         this.value243 = 28.0F + f + f2 + f1 + 6.0F;
      }

      return this.value243;
   }
}
