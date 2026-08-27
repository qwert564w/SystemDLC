package client.util;

import client.module.Feature;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.SliderSetting;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FreeAim extends AimMode {
   private static double value = 0.0;
   private SliderSetting speed;
   private SliderSetting strengthKorrekcii;
   private SliderSetting svobodaVniz;
   private MultilistSetting povedenie;
   private SliderSetting porogZamedl;
   private double value2;
   private double value3;
   private double value4;
   private double value5;
   private double value6;
   private double value7;
   private double value8;
   private double value9;
   private double value10;
   private boolean flag;
   private double value11;
   private double value12;
   private double value13;
   private double value14;

   public FreeAim() {
      super("Свободная");
      SliderSetting slidersetting = new SliderSetting("", "", 15.0, 5.0, 20.0, 0.1);
      slidersetting.setName("Скорость");
      slidersetting.setDescription("Базовая скорость наведения");
      this.speed = slidersetting;
      slidersetting = new SliderSetting("", "", 0.5, 0.1, 1.0, 0.05);
      slidersetting.setName("Сила коррекции");
      slidersetting.setDescription("Насколько сильно помогает прицеливание вне хитбокса");
      this.strengthKorrekcii = slidersetting;
      slidersetting = new SliderSetting("", "", 0.0, -1.0, 1.0, 0.05);
      slidersetting.setName("Свобода вниз");
      slidersetting.setDescription("Сужает (-) или расширяет (+) мёртвую зону по Y снизу хитбокса");
      this.svobodaVniz = slidersetting;
      MultilistSetting multilistsetting = new MultilistSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"П", "л", "a", "в", "н", "ы", "й", " ", "в", "х", "o", "д"}),
            StringParts.join(new String[]{"У", "ч", "и", "т", "ы", "в", "a", "т", "ь", " ", "c", "е", "н", "с", "у"}),
            StringParts.join(new String[]{"З", "a", "м", "е", "д", "л", "е", "н", "и", "е", " ", "в", "б", "л", "и", "з", "и"}),
            StringParts.join(new String[]{"Ш", "y", "м", " ", "п", "p", "и", " ", "н", "a", "в", "о", "д", "к", "е"})
         ),
         Arrays.asList(
            StringParts.join(new String[]{"П", "л", "a", "в", "н", "ы", "й", " ", "в", "х", "o", "д"}),
            StringParts.join(new String[]{"У", "ч", "и", "т", "ы", "в", "a", "т", "ь", " ", "c", "е", "н", "с", "у"}),
            StringParts.join(new String[]{"З", "a", "м", "е", "д", "л", "е", "н", "и", "е", " ", "в", "б", "л", "и", "з", "и"}),
            StringParts.join(new String[]{"Ш", "y", "м", " ", "п", "p", "и", " ", "н", "a", "в", "о", "д", "к", "е"})
         )
      );
      multilistsetting.setName("Поведение");
      multilistsetting.setDescription("Настройки поведения ротации");
      this.povedenie = multilistsetting;
      slidersetting = new SliderSetting("", "", 6.0, 1.0, 20.0, 0.5);
      slidersetting.setName("Порог замедл.");
      slidersetting.setDescription("Угол (градусы) при котором начинается замедление");
      this.porogZamedl = slidersetting;
   }

   private static Boolean getBooleanByStringListSetting2(String text, ListSetting listSetting) {
      return text.equals(listSetting.getString2());
   }

   @Override
   public double getDouble() {
      return this.speed.getValue();
   }

   private static Boolean getBooleanByStringListSetting3(String text, ListSetting listSetting) {
      return text.equals(listSetting.getString2());
   }

   private static Boolean getBooleanByStringListSetting4(String text, ListSetting listSetting) {
      return text.equals(listSetting.getString2());
   }

   @Override
   public void update() {
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      this.value2 = threadlocalrandom.nextDouble(Math.PI * 2);
      this.value3 = threadlocalrandom.nextDouble(Math.PI * 2);
      this.value4 = threadlocalrandom.nextDouble(0.06, 0.18);
      this.value5 = threadlocalrandom.nextDouble(0.04, 0.14);
      this.value6 = threadlocalrandom.nextDouble(-0.08, 0.08);
      this.value7 = 0.0;
      this.value8 = threadlocalrandom.nextDouble(0.85, 1.15);
      this.value9 = 0.0;
      this.value10 = 0.0;
      this.flag = false;
      this.value14 = 0.0;
   }

   private static Boolean getBooleanByStringListSetting5(String text, ListSetting listSetting) {
      return text.equals(listSetting.getString2());
   }

   @Override
   public void update2() {
      this.value2 = 0.0;
      this.value3 = 0.0;
      this.value4 = 0.0;
      this.value5 = 0.0;
      this.value6 = 0.0;
      this.value7 = 0.0;
      this.value8 = 1.0;
      this.value9 = 0.0;
      this.value10 = 0.0;
      this.flag = false;
      this.value14 = 0.0;
   }

   private boolean isString(String text) {
      return this.povedenie.isString(text);
   }

   private Boolean getBooleanByStringListSetting(String text, ListSetting listSetting) {
      return listSetting.isString(text) && this.isString("Замедление вблизи");
   }

   @Override
   public double[] getDoubleArrayByDoubleLongDoubleDoubleDoubleDoubleDoubleLongPlayerEntityDouble(
      double value, long time, double value4, double value5, double value6, double value15, double value16, long time2, PlayerEntity playerEntity, double value17
   ) {
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      boolean flagx = this.isDoublePlayerEntity(value6, playerEntity);
      double d0 = flagx ? 0.0 : 1.0;
      if (!flagx) {
         this.value10 = Math.min(this.value10 + 0.06 * value17, 3.0);
      } else {
         this.value10 = this.value10 * Math.max(0.0, 1.0 - 0.15 * value17);
      }

      double d1 = flagx ? 0.08 : 0.08 + this.value10 * 0.04;
      this.value9 = this.value9 + (d0 - this.value9) * d1 * value17;
      this.value9 = MathHelper.clamp(this.value9, 0.0, 1.0);
      double d2 = this.strengthKorrekcii.getValue() * this.value7 * this.value8;
      double d3 = 1.0;
      if (this.isString("Учитывать сенсу")) {
         double d4 = (Double)Feature.mc.options.getMouseSensitivity().getValue();
         d3 = MathHelper.clamp(d4 * 2.0, 0.3, 3.0);
      }

      double d21 = MathHelper.clamp((time2 - time) / (450.0 + threadlocalrandom.nextDouble(150.0)), 0.0, 1.0);
      double d5 = d21 * d21 * (3.0 - 2.0 * d21);
      double d6 = value16 * d2 * d3 * d5;
      if (this.isString("Замедление вблизи")) {
         double d7 = this.porogZamedl.getValue();
         if (value6 < d7) {
            double d8 = Math.max(0.2, value6 / d7);
            d6 *= d8;
         }
      }

      double d22 = this.getDoubleByDouble(value6);
      d6 *= d22;
      d6 *= 1.0 + this.value10 * 0.5;
      if (value6 > 80.0 && !this.flag) {
         this.flag = true;
         this.value11 = threadlocalrandom.nextDouble(2.0, 3.5);
         this.value12 = threadlocalrandom.nextDouble(5.0, 12.0);
         this.value13 = threadlocalrandom.nextDouble(0.15, 0.4);
         this.value14 = threadlocalrandom.nextDouble(Math.PI * 2);
      }

      if (value6 < 6.0 && this.flag) {
         this.flag = false;
      }

      if (this.flag) {
         double d23 = Math.sin(this.value14) * this.value13;
         d6 *= this.value11 * (1.0 + d23);
      }

      double d24 = value16 / 15.0;
      double d9 = threadlocalrandom.nextDouble(-0.002, 0.002);
      double d10 = MathHelper.clamp(d6 * 0.03 * d24 + d9, 0.003, 0.85);
      double d11 = value * d10;
      double d12 = value5 * d10 * (0.85 + threadlocalrandom.nextDouble(0.3));
      if (this.isString("Шум при наводке")) {
         double d13 = 0.05 + threadlocalrandom.nextDouble(0.07);
         d11 += Math.sin(this.value2) * d13 * (0.5 + threadlocalrandom.nextDouble(0.6));
         d12 += Math.cos(this.value3) * d13 * 0.45 * (0.4 + threadlocalrandom.nextDouble(0.5));
      }

      if (this.value10 > 0.3 && !flagx) {
         double d25 = MathHelper.clamp(this.value10 * 0.07, 0.02, 0.2);
         double d14 = value * d25;
         double d15 = value5 * d25 * 0.85;
         if (Math.abs(d11) < Math.abs(d14)) {
            d11 = d14;
         }

         if (Math.abs(d12) < Math.abs(d15)) {
            d12 = d15;
         }
      }

      double d26 = d11 * this.value9;
      double d27 = d12 * this.value9;
      double d28 = 1.0 + Math.min(value6 / 30.0, 4.0) * d24;
      if (this.flag) {
         d28 *= 2.0 + threadlocalrandom.nextDouble(1.5);
      }

      double d16 = (1.8 + threadlocalrandom.nextDouble(1.2)) * value17 * d28;
      double d17 = (1.3 + threadlocalrandom.nextDouble(0.8)) * value17 * d28;
      double d18 = d26 - value4;
      double d19 = d27 - value15;
      if (Math.abs(d18) > d16) {
         d26 = value4 + Math.signum(d18) * d16;
      }

      if (Math.abs(d19) > d17) {
         d27 = value15 + Math.signum(d19) * d17;
      }

      double d20 = RandomUtil.getDouble();
      d26 = RandomUtil.getDoubleByDoubleDouble(d20, d26);
      d27 = RandomUtil.getDoubleByDoubleDouble(d20, d27);
      return new double[]{d26, d27, d26, d27};
   }

   @Override
   public double[] getDoubleArrayByEntity(Entity entity2) {
      return new double[]{0.0, 0.0, 0.0};
   }

   @Override
   public double getDouble2() {
      return this.value6;
   }

   @Override
   public void onDoubleLongEntity(double value, long time, Entity entity2) {
      ThreadLocalRandom threadlocalrandom = ThreadLocalRandom.current();
      this.value2 = this.value2 + this.value4 * value;
      this.value3 = this.value3 + this.value5 * value;
      if (this.isString("Плавный вход")) {
         this.value7 = Math.min(1.0, this.value7 + 0.035 * value);
      } else {
         this.value7 = 1.0;
      }

      if (threadlocalrandom.nextDouble() < 0.02) {
         this.value8 = threadlocalrandom.nextDouble(0.8, 1.2);
      }

      if (threadlocalrandom.nextDouble() < 0.015) {
         this.value6 = this.value6 + threadlocalrandom.nextDouble(-0.04, 0.04);
         this.value6 = MathHelper.clamp(this.value6, -0.15, 0.1);
      }

      if (this.flag) {
         this.value14 = this.value14 + this.value12 * value * 0.02;
      }
   }

   @Override
   public void onStringListSetting(String text, ListSetting listSetting) {
      this.speed.setVisibleWhen(() -> FreeAim.getBooleanByStringListSetting2(text, listSetting));
      this.strengthKorrekcii.setVisibleWhen(() -> FreeAim.getBooleanByStringListSetting5(text, listSetting));
      this.svobodaVniz.setVisibleWhen(() -> FreeAim.getBooleanByStringListSetting4(text, listSetting));
      this.povedenie.setVisibleWhen(() -> FreeAim.getBooleanByStringListSetting3(text, listSetting));
      this.porogZamedl.setVisibleWhen(() -> this.getBooleanByStringListSetting(text, listSetting));
   }

   @Override
   public List getList() {
      return Arrays.asList(this.speed, this.strengthKorrekcii, this.svobodaVniz, this.povedenie, this.porogZamedl);
   }

   private boolean isDoublePlayerEntity(double value2, PlayerEntity playerEntity) {
      if (value2 > 45.0) {
         return false;
      } else if (Feature.mc.world == null) {
         return false;
      } else {
         Vec3d vec3d = playerEntity.getEyePos();
         Vec3d vec3d1 = playerEntity.getRotationVec(1.0F);
         double d0 = 6.0;
         Vec3d vec3d2 = vec3d.add(vec3d1.multiply(d0));

         for (Entity entity : Feature.mc.world.getEntities()) {
            if (entity != playerEntity && entity.isAlive() && !(playerEntity.squaredDistanceTo(entity) > d0 * d0)) {
               Box box = entity.getBoundingBox();
               if (value > 0.0) {
                  box = box.expand(value);
               }

               double d1 = this.svobodaVniz.getValue();
               if (d1 != 0.0) {
                  box = new Box(box.minX, box.minY - d1, box.minZ, box.maxX, box.maxY, box.maxZ);
               }

               if (box.raycast(vec3d, vec3d2).isPresent() || box.contains(vec3d2)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private double getDoubleByDouble(double value) {
      if (value < 1.5) {
         return 0.12 + value * 0.12;
      } else if (value < 8.0) {
         double d3 = (value - 1.5) / 6.5;
         return 0.3 + d3 * 0.4;
      } else if (value < 40.0) {
         double d2 = (value - 8.0) / 32.0;
         double d1 = d2 * d2 * (3.0 - 2.0 * d2);
         return 0.7 + d1 * 1.8;
      } else {
         double d0 = Math.min((value - 40.0) / 140.0, 1.0);
         return 2.5 + d0 * 2.5;
      }
   }
}
