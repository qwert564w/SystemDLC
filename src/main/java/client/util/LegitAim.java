package client.util;

import client.setting.BooleanSetting;
import client.setting.SliderSetting;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class LegitAim extends AimMode {
   private final SliderSetting speed;
   private final SliderSetting sglazhivanie;
   private final SliderSetting maxLerp;
   private final SliderSetting minLerp;
   private final BooleanSetting randomizaciya;
   private int phase;
   private double phaseProgress;
   private long phaseStarted;
   private long seedTime;
   private final float[] history;
   private int historyIndex;
   private float lastFactor;
   private long factorTime;

   public LegitAim() {
      super("Легит");
      SliderSetting setting = new SliderSetting("", "", 1.0, 0.1, 3.0, 0.05);
      setting.setName("Скорость");
      setting.setDescription("Базовая скорость наведения (множитель лерпа)");
      this.speed = setting;

      setting = new SliderSetting("", "", 1.0, 0.2, 3.0, 0.05);
      setting.setName("Сглаживание");
      setting.setDescription("Чем выше, тем плавнее доводится прицел");
      this.sglazhivanie = setting;

      setting = new SliderSetting("", "", 1.5, 0.2, 2.5, 0.05);
      setting.setName("Макс. лерп");
      setting.setDescription("Верхний предел доли угла, проходимой за тик");
      this.maxLerp = setting;

      setting = new SliderSetting("", "", 0.05, 0.01, 0.5, 0.01);
      setting.setName("Мин. лерп");
      setting.setDescription("Нижний предел доли угла, проходимой за тик");
      this.minLerp = setting;

      BooleanSetting random = new BooleanSetting("", "", true);
      random.setName("Рандомизация");
      random.setDescription("Добавлять фазовые случайные колебания скорости");
      this.randomizaciya = random;

      this.history = new float[5];
      this.lastFactor = 1.0F;
   }

   private float randomFactor(float angle) {
      if (!this.randomizaciya.isFlag3()) {
         return angle < 20.0F ? 1.0F : angle > 90.0F ? 0.95F : 0.97F;
      }

      ThreadLocalRandom random = ThreadLocalRandom.current();
      float factor = angle < 20.0F
         ? 0.95F + random.nextFloat() * 0.10F
         : angle > 90.0F
            ? 0.80F + random.nextFloat() * 0.30F
            : 0.85F + random.nextFloat() * 0.25F;
      return MathHelper.clamp(factor, 0.75F, 1.30F);
   }

   private float phaseFactor(float angle) {
      if (!this.randomizaciya.isFlag3()) {
         return angle > 120.0F ? 1.30F : angle < 15.0F ? 0.70F : 1.0F;
      }

      long elapsed = System.currentTimeMillis() - this.factorTime;
      ThreadLocalRandom random = ThreadLocalRandom.current();
      float base = switch (this.phase) {
         case 0 -> 0.75F;
         case 1 -> 0.95F;
         case 2 -> 0.65F;
         case 3 -> 1.00F;
         case 4 -> 0.55F;
         default -> 0.80F;
      };
      float jitter = 0.85F + random.nextFloat() * 0.30F;
      float factor = base * jitter;

      if (elapsed < 45L) {
         factor *= 0.75F + random.nextFloat() * 0.25F;
      } else if (elapsed > 300L) {
         factor *= 1.05F + random.nextFloat() * 0.25F;
      }

      if (angle > 120.0F) {
         factor *= 1.25F;
      } else if (angle < 15.0F) {
         factor *= 0.65F + random.nextFloat() * 0.35F;
      }

      float delta = factor - this.lastFactor;
      if (Math.abs(delta) > 0.30F) {
         factor = this.lastFactor + Math.copySign(0.30F, delta);
      }

      return MathHelper.clamp(factor, 0.15F, 1.8F);
   }

   private void resetState() {
      this.phase = 0;
      this.phaseProgress = 0.0;
      this.seedTime = System.nanoTime();
      this.phaseStarted = System.currentTimeMillis();
      this.factorTime = this.phaseStarted;
      this.historyIndex = 0;
      this.lastFactor = 1.0F;
      Arrays.fill(this.history, 0.0F);
   }

   @Override
   public double getDouble() {
      return this.speed.getValue();
   }

   @Override
   public void update() {
      this.resetState();
   }

   @Override
   public void update2() {
      this.resetState();
   }

   private void advancePhase(long time) {
      long elapsed = time - this.phaseStarted;
      double duration = 3000.0 + phaseNoise(this.seedTime + this.phase * 7919L) * 2000.0;
      this.phaseProgress = elapsed / duration;
      if (this.phaseProgress >= 1.0) {
         this.phase = (this.phase + 1) % 5;
         this.phaseProgress = 0.0;
         this.phaseStarted = time;
      }
   }

   private double phaseNoise(long time) {
      double value = Math.abs(Math.sin(time * 0.001));
      return 0.25 + value * 0.5;
   }

   @Override
   public List getList() {
      return Arrays.asList(this.speed, this.sglazhivanie, this.maxLerp, this.minLerp, this.randomizaciya);
   }

   @Override
   public void onDoubleLongEntity(double value, long time, Entity entity) {
      this.advancePhase(time);
   }

   @Override
   public double getDouble2() {
      return 0.0;
   }

   @Override
   public double[] getDoubleArrayByEntity(Entity entity) {
      return new double[]{0.0, 0.0, 0.0};
   }

   @Override
   public double[] getDoubleArrayByDoubleLongDoubleDoubleDoubleDoubleDoubleLongPlayerEntityDouble(
      double value, long time, double value2, double value4, double value5, double value6,
      double value7, long time2, PlayerEntity playerEntity, double value8
   ) {
      float angle = (float)value5;
      float phaseFactor = this.phaseFactor(angle);
      this.history[this.historyIndex] = phaseFactor;
      this.historyIndex = (this.historyIndex + 1) % this.history.length;

      float average = 0.0F;
      int count = 0;
      for (float sample : this.history) {
         if (sample > 0.0F) {
            average += sample;
            count++;
         }
      }
      average = count > 0 ? average / count : this.lastFactor;

      float angleFactor = randomFactor(angle);
      double smoothing = this.sglazhivanie.getValue();
      double targetLerp = value8 / 3.0 * value7 / smoothing;
      double min = Math.min(this.minLerp.getValue(), this.maxLerp.getValue());
      double max = Math.max(this.minLerp.getValue(), this.maxLerp.getValue());

      double lerp = MathHelper.clamp(targetLerp * average * angleFactor, min, max);
      double yaw = value * lerp;
      double pitch = 0.0;

      double sharedNoise = RandomUtil.getDouble();
      yaw = RandomUtil.getDoubleByDoubleDouble(sharedNoise, yaw);
      pitch = RandomUtil.getDoubleByDoubleDouble(sharedNoise, pitch);

      this.lastFactor = MathHelper.clamp((float)lerp, 0.15F, 1.8F);
      this.factorTime = System.currentTimeMillis();

      return new double[]{yaw, pitch, yaw, pitch};
   }
}
