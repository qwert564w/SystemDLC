package client.util;

import client.module.Feature;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class RotationUtil {
   private long time;

   public void update() {
      this.time = System.nanoTime();
   }

   public void onDoubleFloatFloat(double value, float targetPitch, float targetYaw) {
      if (Feature.mc.player == null) {
         return;
      }

      double tickDelta = this.getDouble();
      float yaw = Feature.mc.player.getYaw();
      float pitch = Feature.mc.player.getPitch();

      double yawDelta = RandomUtil.getDoubleByDouble(targetYaw - yaw);
      double pitchDelta = targetPitch - pitch;
      double distanceSq = yawDelta * yawDelta + pitchDelta * pitchDelta;
      if (distanceSq < 0.0025) {
         return;
      }

      double factor = MathHelper.clamp(value * 0.04 * tickDelta, 0.01, 0.95);
      double nextYaw = yawDelta * factor;
      double nextPitch = pitchDelta * factor;

      double noise = RandomUtil.getDouble();
      nextYaw = RandomUtil.getDoubleByDoubleDouble(noise, nextYaw);
      nextPitch = RandomUtil.getDoubleByDoubleDouble(noise, nextPitch);

      if (nextYaw == 0.0 && Math.abs(yawDelta) > noise) {
         nextYaw = Math.copySign(noise, yawDelta);
      }
      if (nextPitch == 0.0 && Math.abs(pitchDelta) > noise) {
         nextPitch = Math.copySign(noise, pitchDelta);
      }

      Feature.mc.player.setYaw(yaw + (float)nextYaw);
      Feature.mc.player.setPitch(MathHelper.clamp(pitch + (float)nextPitch, -90.0F, 90.0F));
   }

   public double getDouble() {
      long now = System.nanoTime();
      double delta = this.time > 0L ? (now - this.time) / 1.66666667E7 : 1.0;
      this.time = now;
      return MathHelper.clamp(delta, 0.05, 3.0);
   }

   public void onDoubleVec3d(double value, Vec3d vec3d) {
      if (Feature.mc.player != null) {
         float[] rotation = getFloatArrayByVec3d(vec3d);
         this.onDoubleFloatFloat(value, rotation[1], rotation[0]);
      }
   }

   public static float[] getFloatArrayByVec3d(Vec3d vec3d) {
      Vec3d eye = Feature.mc.player.getEyePos();
      double dx = vec3d.x - eye.x;
      double dy = vec3d.y - eye.y;
      double dz = vec3d.z - eye.z;
      double horizontal = Math.sqrt(dx * dx + dz * dz);

      float yaw = (float)(Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F;
      float pitch = (float)(-(Math.atan2(dy, horizontal) * 180.0 / Math.PI));
      return new float[]{yaw, pitch};
   }

   public void setTime() {
      this.time = 0L;
   }
}
