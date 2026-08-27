package client.util;

import client.setting.BooleanSetting;
import client.setting.SliderSetting;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class LegitAim extends AimMode {
   private SliderSetting speed;
   private SliderSetting sglazhivanie;
   private SliderSetting maxLerp;
   private SliderSetting minLerp;
   private BooleanSetting randomizaciya;
   private final SecureRandom secureRandom;
   private int value;
   private double value2;
   private long time;
   private final double[] doubleArray;
   private long time2;
   private final float[] floatArray;
   private int value3;
   private float value4;
   private long time3;

   public LegitAim() {
      super("Легит");
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.1, 3.0, 0.05);
      slidersetting.setName("Скорость");
      slidersetting.setDescription("Базовая скорость наведения (множитель лерпа)");
      this.speed = slidersetting;
      slidersetting = new SliderSetting("", "", 1.0, 0.2, 3.0, 0.05);
      slidersetting.setName("Сглаживание");
      slidersetting.setDescription("Чем выше, тем плавнее доводится прицел");
      this.sglazhivanie = slidersetting;
      slidersetting = new SliderSetting("", "", 1.5, 0.2, 2.5, 0.05);
      slidersetting.setName("Макс. лерп");
      slidersetting.setDescription("Верхний предел доли угла, проходимой за тик");
      this.maxLerp = slidersetting;
      slidersetting = new SliderSetting("", "", 0.05, 0.01, 0.5, 0.01);
      slidersetting.setName("Мин. лерп");
      slidersetting.setDescription("Нижний предел доли угла, проходимой за тик");
      this.minLerp = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Рандомизация");
      booleansetting.setDescription("Добавлять фазовые случайные колебания скорости");
      this.randomizaciya = booleansetting;
      this.secureRandom = new SecureRandom();
      this.doubleArray = new double[15];
      this.floatArray = new float[5];
   }

   private float getFloatByFloat(float value) {
      if (!this.randomizaciya.isFlag3()) {
         return value < 20.0F ? 1.0F : (value > 90.0F ? 0.95F : 0.97F);
      } else {
         return value < 20.0F
            ? 0.95F + this.secureRandom.nextFloat() * 0.1F
            : (value > 90.0F ? 0.8F + this.secureRandom.nextFloat() * 0.3F : 0.85F + this.secureRandom.nextFloat() * 0.25F);
      }
   }

   private void update3() {
      for (int i = 0; i < 5; i++) {
         int j = i * 3;
         long k = this.time2 + i * 7919L;
         this.doubleArray[j] = this.getDoubleByLongDoubleDouble(k, 0.3, 0.7);
         this.doubleArray[j + 1] = this.getDoubleByLongDoubleDouble(k + 1L, 0.2, 0.9);
         this.doubleArray[j + 2] = this.getDoubleByLongDoubleDouble(k + 2L, 0.1, 0.8);
      }
   }

   @Override
   public double getDouble() {
      return this.speed.getValue();
   }

   @Override
   public void update() {
      this.update4();
   }

   @Override
   public void update2() {
      this.value = 0;
      this.value2 = 0.0;
      this.time = System.currentTimeMillis();
      this.time2 = System.nanoTime();
      this.value4 = 0.0F;
      this.time3 = System.currentTimeMillis();
      this.value3 = 0;
      Arrays.fill(this.floatArray, 0.0F);
      this.update3();
   }

   private float getFloatByFloat2(float value2) {
      boolean flag = this.randomizaciya.isFlag3();
      long i = System.currentTimeMillis() - this.time3;
      float f1;
      if (flag) {
         f1 = switch (this.value) {
            case 0 -> 0.6F + this.secureRandom.nextFloat() * 0.4F;
            case 1 -> 0.8F + this.secureRandom.nextFloat() * 0.3F;
            case 2 -> 0.5F + this.secureRandom.nextFloat() * 0.3F;
            case 3 -> 0.7F + this.secureRandom.nextFloat() * 0.5F;
            case 4 -> 0.4F + this.secureRandom.nextFloat() * 0.4F;
            case 5 -> 0.9F + this.secureRandom.nextFloat() * 0.2F;
            default -> 0.7F + this.secureRandom.nextFloat() * 0.4F;
         } * (0.9F + this.secureRandom.nextFloat() * 0.3F);
         if (i < 45L) {
            f1 *= 0.7F + this.secureRandom.nextFloat() * 0.4F;
         } else if (i > 300L) {
            f1 *= 1.1F + this.secureRandom.nextFloat() * 0.4F;
         }

         if (value2 > 120.0F) {
            f1 *= 1.3F;
         } else if (value2 < 15.0F) {
            f1 *= 0.5F + this.secureRandom.nextFloat() * 0.4F;
         }

         if (this.value4 > 0.0F) {
            float f = f1 - this.value4;
            if (Math.abs(f) > 0.35F) {
               f1 = this.value4 + (f > 0.0F ? 0.35F : -0.35F);
            }
         }
      } else {
         f1 = 1.0F;
         if (value2 > 120.0F) {
            f1 *= 1.3F;
         } else if (value2 < 15.0F) {
            f1 *= 0.7F;
         }
      }

      float f2 = MathHelper.clamp(f1, 0.15F, 1.8F);
      this.value4 = f2;
      this.time3 = System.currentTimeMillis();
      return f2;
   }

   private float getFloat() {
      float f = 0.0F;
      int i = 0;

      for (float f1 : this.floatArray) {
         if (f1 > 0.0F) {
            f += f1;
            i++;
         }
      }

      return i > 0 ? f / i : (this.value4 > 0.0F ? this.value4 : 1.0F);
   }

   private double getDoubleByLongDoubleDouble(long time, double value, double value2) {
      double d0 = Math.abs(Math.sin(time * 0.001)) % 1.0;
      return value + d0 * (value2 - value);
   }

   private void setLong(long time2) {
      long i = time2 - this.time;
      double d0 = 3000.0 + this.doubleArray[0] * 2000.0;
      this.value2 = i / d0;
      if (this.value2 >= 1.0) {
         this.value = (this.value + 1) % 5;
         this.value2 = 0.0;
         this.time = time2;
      }
   }

   @Override
   public List getList() {
      return Arrays.asList(this.speed, this.sglazhivanie, this.maxLerp, this.minLerp, this.randomizaciya);
   }

   private void update4() {
      this.time2 = System.nanoTime();
      this.value = 0;
      this.value2 = 0.0;
      this.time = System.currentTimeMillis();
      this.value4 = 0.0F;
      this.time3 = System.currentTimeMillis();
      this.value3 = 0;
      Arrays.fill(this.floatArray, 0.0F);
      this.update3();
   }

   @Override
   public void onDoubleLongEntity(double value, long time, Entity entity2) {
      this.setLong(time);
   }

   @Override
   public double getDouble2() {
      return 0.0;
   }

   @Override
   public double[] getDoubleArrayByEntity(Entity entity2) {
      return new double[]{0.0, 0.0, 0.0};
   }

   @Override
   public double[] getDoubleArrayByDoubleLongDoubleDoubleDoubleDoubleDoubleLongPlayerEntityDouble(
      double value, long time, double value2, double value4, double value5, double value6, double value7, long time2, PlayerEntity playerEntity, double value8
   ) {
      float f = (float)value5;
      float f1 = this.getFloatByFloat2(f);
      this.floatArray[this.value3] = f1;
      this.value3 = (this.value3 + 1) % this.floatArray.length;
      float f2 = this.getFloat();
      float f3 = this.getFloatByFloat(f);
      double d0 = this.sglazhivanie.getValue();
      double d1 = value8 / 3.0 * value7 / d0;
      double d2 = this.minLerp.getValue();
      double d3 = this.maxLerp.getValue();
      if (d2 > d3) {
         d2 = d3;
      }

      double d4 = MathHelper.clamp(d1 * f2 * f3, d2, d3);
      double d5 = value * d4;
      double d6 = 0.0;
      double d7 = RandomUtil.getDouble();
      d5 = RandomUtil.getDoubleByDoubleDouble(d7, d5);
      d6 = RandomUtil.getDoubleByDoubleDouble(d7, d6);
      return new double[]{d5, d6, d5, d6};
   }
}
