package client.gui.widget;

import client.api.SubmitCallback;
import client.api.Theme;
import client.concurrent.Translations;
import client.concurrent.WaypointStore;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.module.Feature;
import client.module.movement.FreeCam;
import client.render.SvgShader;
import client.render.TextShader;
import client.util.EasingPresets;
import client.util.UnsafeAccess;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class WaypointCreateForm extends FormWidget {
   private static final float value239 = 14.0F;
   private static final float value240 = 8.0F;
   private static final float value241 = 14.0F;
   private final Tween tween4 = EasingPresets.getTween();
   private SubmitCallback submitCallback;
   private static final UnsafeAccess<FreeCam> unsafeAccess = new UnsafeAccess<>(FreeCam.class);

   public WaypointCreateForm() {
      this.value237 = 300.0F;
      this.value238 = 116.0F;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count != 0) {
         return false;
      } else {
         float f3 = this.value238;
         float f2 = this.value237;
         float f1 = this.value236;
         float f = this.value235;
         if (!isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value)) {
            return false;
         } else {
            this.update4();
            return true;
         }
      }
   }

   private void update4() {
      if (this.check() && this.submitCallback != null) {
         FreeCam freecam = (FreeCam)unsafeAccess.getModule2();
         Vec3d vec3d = freecam != null ? freecam.getVec3d() : null;
         int i = (int)Math.round(vec3d != null ? vec3d.x : Feature.mc.player.getX());
         int j = (int)Math.round(vec3d != null ? vec3d.y : Feature.mc.player.getY());
         int k = (int)Math.round(vec3d != null ? vec3d.z : Feature.mc.player.getZ());
         String s = i + " " + j + " " + k;
         this.submitCallback.onSubmit(s, i, j, k);
      }
   }

   private boolean check() {
      return WaypointStore.check() && Feature.mc != null && Feature.mc.player != null;
   }

   public void setSubmitCallback(SubmitCallback submitCallback2) {
      this.submitCallback = submitCallback2;
   }

   @Override
   protected float getFloat() {
      return 14.0F;
   }

   @Override
   protected int getInt() {
      int i = Theme.surface();
      int k = Theme.elevated();
      float f = this.tween4.getValue3();
      int j = k;
      return AnimatedInt.getIntByIntFloatInt(j, f, i);
   }

   @Override
   protected void onFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, float value3) {
      double d2 = value;
      double d3 = value2;
      float f8 = this.value238;
      float f7 = this.value237;
      float f6 = this.value236;
      float f5 = this.value235;
      double d1 = d3;
      double d0 = d2;
      boolean flag = isFloatFloatDoubleFloatFloatDouble(f5, f6, d1, f8, f7, d0);
      this.tween4.setFloat2(flag ? 1.0F : 0.0F);
      this.tween4.getFloat();
      String s = Translations.getInstance().getStringByString2("Создать");
      float f = TextShader.getFloatByStringFloat(s, 14.0F);
      float f1 = 22.0F + f;
      float f2 = this.value235 + (this.value237 - f1) / 2.0F;
      float f3 = this.value236 + (this.value238 - 14.0F) / 2.0F;
      float f4 = this.value236 + (this.value238 - 14.0F) / 2.0F - 1.0F;
      int i = this.check() ? Theme.foreground() : Theme.mutedFg();
      float f10 = 14.0F;
      float f9 = 14.0F;
      CategoryType categorytype = CategoryType.WAYPOINT;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value3, i, matrix4f, f3, categorytype, f10, f2, f9);
      float f17 = f2 + 14.0F + 8.0F;
      float f12 = 14.0F;
      float f11 = f17;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f11, i, f12, value3, s, matrix4f);
      if (flag && !this.check()) {
         float f16 = this.value238;
         float f15 = this.value237;
         float f14 = this.value236;
         float f13 = this.value235;
         String s1 = "Зайдите на сервер чтобы создать вейпоинт";
         HeaderPainter.onFloatStringFloatFloatFloat(f13, s1, f14, f15, f16);
      }
   }

   public void setFloat(float value) {
      this.value237 = value;
   }
}
