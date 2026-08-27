package client.module.movement;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import net.minecraft.block.BlockState;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class BoatFly extends Module {
   public SliderSetting speedVverh;
   public SliderSetting speedVniz;
   public SliderSetting speedVStorony;
   public SliderSetting speedVBlokah;
   public SliderSetting rangeChecks;
   public BooleanSetting ispolzovaniePredmetov;
   private boolean flag;

   public BoatFly() {
      super("BoatFly", Category.MOVEMENT);
      SliderSetting slidersetting = new SliderSetting("", "", 0.4F, 0.05F, 5.0, 0.05F);
      slidersetting.setName("Скорость вверх");
      slidersetting.setDescription("Скорость вертикального полёта вверх");
      this.speedVverh = slidersetting;
      slidersetting = new SliderSetting("", "", 0.4F, 0.05F, 5.0, 0.05F);
      slidersetting.setName("Скорость вниз");
      slidersetting.setDescription("Скорость вертикального полёта вниз");
      this.speedVniz = slidersetting;
      slidersetting = new SliderSetting("", "", 0.6F, 0.05F, 5.0, 0.05F);
      slidersetting.setName("Скорость в стороны");
      slidersetting.setDescription("Горизонтальная скорость полёта лодки");
      this.speedVStorony = slidersetting;
      slidersetting = new SliderSetting("", "", 0.25, 0.05F, 2.0, 0.05F);
      slidersetting.setName("Скорость в блоках");
      slidersetting.setDescription("Скорость движения сквозь блоки");
      this.speedVBlokah = slidersetting;
      slidersetting = new SliderSetting("", "", 0.5, 0.1F, 2.0, 0.1F);
      slidersetting.setName("Дистанция проверки");
      slidersetting.setDescription("Дистанция обнаружения блоков впереди лодки");
      this.rangeChecks = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Использование предметов");
      booleansetting.setDescription("Позволяет использовать предметы в лодке");
      this.ispolzovaniePredmetov = booleansetting;
      this.flag = false;
      this.addSettings(new Setting[]{this.speedVverh, this.speedVniz, this.speedVStorony, this.speedVBlokah, this.rangeChecks, this.ispolzovaniePredmetov});
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         if (this.player().getVehicle() instanceof BoatEntity boatentity) {
            if (this.ispolzovaniePredmetov.isFlag3()) {
               this.update11();
            }

            boatentity.noClip = true;
            boatentity.setNoGravity(true);
            boolean flag1 = this.check3();
            boolean flagx = this.isBoatEntity(boatentity);
            double d0 = !flag1 && !flagx ? this.speedVStorony.getValue() : this.speedVBlokah.getValue();
            boatentity.setYaw(this.player().getYaw());
            boatentity.prevYaw = boatentity.getYaw();
            double d1 = 0.0;
            double d2 = 0.0;
            double d3 = 0.0;
            float f = boatentity.getYaw();
            double d4 = Math.toRadians(f);
            if (this.options().jumpKey.isPressed()) {
               d2 += this.speedVverh.getValue();
            }

            if (GLFW.glfwGetKey(this.client().getWindow().getHandle(), 88) == 1) {
               d2 -= this.speedVniz.getValue();
            }

            PlayerInput playerinput = this.clientPlayer().input.playerInput;
            if (playerinput.forward()) {
               d1 -= MathHelper.sin((float)d4) * d0;
               d3 += MathHelper.cos((float)d4) * d0;
            }

            if (playerinput.backward()) {
               d1 += MathHelper.sin((float)d4) * d0;
               d3 -= MathHelper.cos((float)d4) * d0;
            }

            if (playerinput.left()) {
               d1 += MathHelper.cos((float)d4) * d0;
               d3 += MathHelper.sin((float)d4) * d0;
            }

            if (playerinput.right()) {
               d1 -= MathHelper.cos((float)d4) * d0;
               d3 -= MathHelper.sin((float)d4) * d0;
            }

            Vec3d vec3d = new Vec3d(d1, d2, d3);
            boatentity.setVelocity(vec3d);
            if (!this.player().hasVehicle()) {
               this.player().startRiding(boatentity, true);
            }
         } else {
            this.flag = false;
         }
      }
   }

   @Override
   public void onDisable() {
      if (this.player() != null && this.player().getVehicle() instanceof BoatEntity boatentity) {
         boatentity.noClip = false;
         boatentity.setNoGravity(false);
      }

      this.flag = false;
   }

   private boolean check3() {
      if (this.notInGame()) {
         return false;
      } else {
         Box box = this.player().getBoundingBox().expand(0.001);
         int i = MathHelper.floor(box.minX);
         int j = MathHelper.floor(box.minY);
         int k = MathHelper.floor(box.minZ);
         int l = MathHelper.floor(box.maxX);
         int i1 = MathHelper.floor(box.maxY);
         int j1 = MathHelper.floor(box.maxZ);

         for (int k1 = i; k1 <= l; k1++) {
            for (int l1 = j; l1 <= i1; l1++) {
               for (int i2 = k; i2 <= j1; i2++) {
                  BlockPos blockpos = BlockPos.ofFloored(k1, l1, i2);
                  BlockState blockstate = this.world().getBlockState(blockpos);
                  if (blockstate.isSolid()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private boolean isBoatEntity(BoatEntity boatEntity) {
      if (this.notInGame()) {
         return false;
      } else {
         Vec3d vec3d = boatEntity.getPos();
         float f = boatEntity.getYaw();
         double d0 = Math.toRadians(f);
         double d1 = this.rangeChecks.getValue();
         PlayerInput playerinput = this.clientPlayer().input.playerInput;
         double d2 = vec3d.x;
         double d3 = vec3d.y;
         double d4 = vec3d.z;
         if (playerinput.forward()) {
            d2 -= MathHelper.sin((float)d0) * d1;
            d4 += MathHelper.cos((float)d0) * d1;
         }

         if (playerinput.backward()) {
            d2 += MathHelper.sin((float)d0) * d1;
            d4 -= MathHelper.cos((float)d0) * d1;
         }

         if (playerinput.left()) {
            d2 += MathHelper.cos((float)d0) * d1;
            d4 += MathHelper.sin((float)d0) * d1;
         }

         if (playerinput.right()) {
            d2 -= MathHelper.cos((float)d0) * d1;
            d4 -= MathHelper.sin((float)d0) * d1;
         }

         if (this.options().jumpKey.isPressed()) {
            d3 += d1;
         }

         if (GLFW.glfwGetKey(this.client().getWindow().getHandle(), 88) == 1) {
            d3 -= d1;
         }

         Box box = new Box(d2 - 0.5, d3 - 0.5, d4 - 0.5, d2 + 0.5, d3 + 0.5, d4 + 0.5);
         int i = MathHelper.floor(box.minX);
         int j = MathHelper.floor(box.minY);
         int k = MathHelper.floor(box.minZ);
         int l = MathHelper.floor(box.maxX);
         int i1 = MathHelper.floor(box.maxY);
         int j1 = MathHelper.floor(box.maxZ);

         for (int k1 = i; k1 <= l; k1++) {
            for (int l1 = j; l1 <= i1; l1++) {
               for (int i2 = k; i2 <= j1; i2++) {
                  BlockPos blockpos = BlockPos.ofFloored(k1, l1, i2);
                  BlockState blockstate = this.world().getBlockState(blockpos);
                  if (blockstate.isSolid()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private void update11() {
      boolean flagx = this.options().useKey.isPressed();
      if (flagx && !this.flag) {
         if (!this.player().getMainHandStack().isEmpty()) {
            this.interactItem(Hand.MAIN_HAND);
         } else if (!this.player().getOffHandStack().isEmpty()) {
            this.interactItem(Hand.OFF_HAND);
         }
      }

      if (!flagx && this.flag && this.player().isUsingItem()) {
         this.player().stopUsingItem();
      }

      this.flag = flagx;
   }

   @Override
   public void onEnable() {
      this.flag = false;
   }
}
