package client.util;

import net.minecraft.util.math.Vec3d;

public record AttackEffectEntry(Vec3d pos, long startMs, long durationMs, float radius, float temperature, float brightness, float seed, int color, int effect) {
   public int getEffect() {
      return this.effect;
   }

   public float getTemperature() {
      return this.temperature;
   }

   public long getDurationMs() {
      return this.durationMs;
   }

   public float getBrightness() {
      return this.brightness;
   }

   public float getSeed() {
      return this.seed;
   }

   public boolean isLong(long time) {
      return time - this.startMs >= this.durationMs;
   }

   public int getColor() {
      return this.color;
   }

   public long getStartMs() {
      return this.startMs;
   }

   public Vec3d getPos() {
      return this.pos;
   }

   public float getRadius() {
      return this.radius;
   }
}
