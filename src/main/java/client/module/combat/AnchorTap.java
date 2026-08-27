package client.module.combat;

import client.module.Category;
import client.module.Module;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.BlockChecks;
import client.util.KeyBindings;
import client.util.RotationUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AnchorTap extends Module {
   public SliderSetting delay;
   public SliderSetting speedNavodki;
   private int value235;
   private int value236;
   private int value237;
   private BlockPos blockPos;
   private final RotationUtil rotationUtil;

   public AnchorTap() {
      super("AnchorTap", Category.COMBAT);
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.0, 5.0, 1.0);
      slidersetting.setName("Задержка");
      slidersetting.setDescription("Задержка между стадиями (тики)");
      this.delay = slidersetting;
      slidersetting = new SliderSetting("", "", 5.0, 1.0, 10.0, 0.5);
      slidersetting.setName("Скорость наводки");
      slidersetting.setDescription("Скорость поворота камеры к якорю");
      this.speedNavodki = slidersetting;
      this.value235 = 0;
      this.value236 = -1;
      this.value237 = 0;
      this.rotationUtil = new RotationUtil();
      this.addSettings(new Setting[]{this.delay, this.speedNavodki});
   }

   private int getInt() {
      int i = this.inventory().selectedSlot;
      if (this.inventory().getStack(i).getItem() != Items.GLOWSTONE) {
         return i;
      } else {
         for (int j = 0; j < 9; j++) {
            if (this.inventory().getStack(j).getItem() != Items.GLOWSTONE) {
               return j;
            }
         }

         return -1;
      }
   }

   @Override
   public void onDisable() {
      this.update11();
      KeyBindings.update5();
   }

   private void update11() {
      this.value235 = 0;
      this.value236 = -1;
      this.blockPos = null;
      this.value237 = 0;
      this.rotationUtil.setTime();
   }

   private int getInt2() {
      for (int i = 0; i < 9; i++) {
         if (this.inventory().getStack(i).getItem() == Items.GLOWSTONE) {
            return i;
         }
      }

      return -1;
   }

   private boolean isBlockPos(BlockPos blockPos) {
      return BlockChecks.isBlockPos2(blockPos);
   }

   @Override
   public void update7() {
      if (!this.notInGame() && this.value235 != 0 && this.blockPos != null) {
         RotationUtil rotationutil = this.rotationUtil;
         Vec3d vec3d1 = Vec3d.ofCenter(this.blockPos);
         double d0 = this.speedNavodki.getValue();
         Vec3d vec3d = vec3d1;
         rotationutil.onDoubleVec3d(d0, vec3d);
      }
   }

   @Override
   public void onEnable() {
      this.update11();
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         if (!(this.client().crosshairTarget instanceof BlockHitResult blockhitresult)) {
            if (this.value235 != 0) {
               this.update11();
            }
         } else {
            while (this.value235 != 0) {
               if (this.value237 > 0) {
                  this.value237--;
                  return;
               }

               int j = this.value235;
               switch (this.value235) {
                  case 1:
                     int i = this.getInt2();
                     if (i == -1) {
                        this.update11();
                        return;
                     }

                     this.inventory().selectedSlot = i;
                     this.value235 = 2;
                     this.value237 = this.delay.getInt2();
                     break;
                  case 2:
                     if (!blockhitresult.getBlockPos().equals(this.blockPos)) {
                        this.update11();
                        return;
                     }

                     if (this.world().getBlockState(this.blockPos).getBlock() != Blocks.RESPAWN_ANCHOR) {
                        this.update11();
                        return;
                     }

                     KeyBindings.update3();
                     this.value235 = 3;
                     this.value237 = Math.max(1, this.delay.getInt2());
                     break;
                  case 3:
                     if (this.value236 != -1) {
                        this.inventory().selectedSlot = this.value236;
                     }

                     this.value235 = 4;
                     this.value237 = this.delay.getInt2();
                     break;
                  case 4:
                     if (!blockhitresult.getBlockPos().equals(this.blockPos)) {
                        this.update11();
                        return;
                     }

                     if (this.world().getBlockState(this.blockPos).getBlock() != Blocks.RESPAWN_ANCHOR) {
                        this.update11();
                        return;
                     }

                     KeyBindings.update3();
                     this.value235 = 5;
                     this.value237 = 1;
                     break;
                  case 5:
                     this.update11();
               }

               if (this.value235 == 0 || this.value237 > 0 || this.value235 == j) {
                  break;
               }
            }

            if (this.value235 == 0) {
               if (this.world().getBlockState(blockhitresult.getBlockPos()).getBlock() != Blocks.RESPAWN_ANCHOR) {
                  return;
               }

               this.blockPos = blockhitresult.getBlockPos();
               this.value236 = this.inventory().selectedSlot;
               if (this.isBlockPos(this.blockPos)) {
                  int k = this.getInt();
                  if (k == -1) {
                     return;
                  }

                  this.inventory().selectedSlot = k;
                  this.value235 = 4;
               } else {
                  if (this.getInt2() == -1) {
                     return;
                  }

                  this.value235 = 1;
               }
            }

            return;
         }
      }
   }
}
