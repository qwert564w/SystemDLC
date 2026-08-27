package client.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public record JumpWave(Vec3d pos, long createdAt, float lifetimeMs, float strength, float maxRadius) {
   public float getStrength() {
      return this.strength;
   }

   public float getMaxRadius() {
      return this.maxRadius;
   }

   public float getLifetimeMs() {
      return this.lifetimeMs;
   }

   public boolean isLong(long time) {
      return (float)(time - this.createdAt) >= this.lifetimeMs;
   }

   public long getCreatedAt() {
      return this.createdAt;
   }

   public Vec3d getPos() {
      return this.pos;
   }

   public float getFloatByLong(long time) {
      return MathHelper.clamp((float)(time - this.createdAt) / this.lifetimeMs, 0.0F, 1.0F);
   }
}
