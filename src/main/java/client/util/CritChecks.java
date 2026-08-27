package client.util;

import client.module.Feature;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class CritChecks {
   public static boolean check() {
      return Feature.mc.player == null
         ? false
         : Feature.mc.player.isTouchingWater()
            || Feature.mc.player.isSwimming()
            || Feature.mc.player.isGliding()
            || Feature.mc.player.isClimbing()
            || Feature.mc.player.isInsideWall()
            || Feature.mc.player.isInLava()
            || Feature.mc.player.hasVehicle();
   }

   public static boolean check2() {
      if (Feature.mc.player == null) {
         return false;
      } else {
         Item item = Feature.mc.player.getMainHandStack().getItem();
         return item instanceof AxeItem || item instanceof TridentItem || item instanceof MaceItem;
      }
   }

   public static boolean check3() {
      return isFloat(-0.01F);
   }

   public static boolean check4() {
      if (Feature.mc.player == null || Feature.mc.options == null) {
         return false;
      } else if (!Feature.mc.player.isTouchingWater() || Feature.mc.player.isSubmergedInWater() || Feature.mc.player.isSwimming()) {
         return false;
      } else {
         return !Feature.mc.options.jumpKey.isPressed() ? false : Feature.mc.player.isOnGround() || Feature.mc.player.getVelocity().y > 0.0;
      }
   }

   public static boolean check5() {
      if (Feature.mc.player != null && Feature.mc.world != null) {
         BlockPos blockpos = Feature.mc.player.getBlockPos().up(2);
         BlockState blockstate = Feature.mc.world.getBlockState(blockpos);
         return !blockstate.isAir() && blockstate.isFullCube(Feature.mc.world, blockpos);
      } else {
         return false;
      }
   }

   public static boolean isFloat(float value) {
      if (Feature.mc.player == null) {
         return false;
      } else if (!Feature.mc.player.isOnGround() && !Feature.mc.player.isInLava() && !Feature.mc.player.isTouchingWater() && !Feature.mc.player.hasVehicle()) {
         double d0 = Feature.mc.player.getVelocity().y;
         if (check5() && d0 < -0.01F) {
            double d1 = Feature.mc.player.getY() - (Feature.mc.player.getBlockPos().down().getY() + 1.0);
            if (d1 > 0.4) {
               return true;
            }
         }

         return d0 < value;
      } else {
         return false;
      }
   }

   public static boolean isLivingEntity(LivingEntity livingEntity) {
      return !livingEntity.isBlocking() ? false : livingEntity.getActiveItem().getItem() == Items.SHIELD;
   }

   public static int getInt() {
      if (Feature.mc.player == null) {
         return -1;
      } else {
         for (int i = 0; i < 9; i++) {
            ItemStack itemstack = Feature.mc.player.getInventory().getStack(i);
            if (!itemstack.isEmpty() && itemstack.getItem() instanceof AxeItem) {
               return i;
            }
         }

         return -1;
      }
   }

   public static boolean isItemStack(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else {
         Item item = itemStack.getItem();
         return item instanceof SwordItem || item instanceof AxeItem || item instanceof TridentItem || item instanceof MaceItem;
      }
   }

   public static boolean isClass(Class value) {
      return Feature.mc.player == null
         ? false
         : value.isInstance(Feature.mc.player.getMainHandStack().getItem()) || value.isInstance(Feature.mc.player.getOffHandStack().getItem());
   }

   public static boolean check6() {
      return Feature.mc.player == null ? false : isItemStack(Feature.mc.player.getMainHandStack()) || isItemStack(Feature.mc.player.getOffHandStack());
   }

   public static void onEntity(Entity entity2) {
      if (Feature.mc.interactionManager != null && Feature.mc.player != null) {
         Feature.mc.interactionManager.attackEntity(Feature.mc.player, entity2);
         Feature.mc.player.swingHand(Hand.MAIN_HAND);
         Feature.mc.player.resetLastAttackedTicks();
      }
   }

   public static float getFloat() {
      if (Feature.mc.player == null) {
         return 0.8F;
      } else {
         boolean flag = check2();
         if (!Feature.mc.player.isOnGround() && !check()) {
            return flag ? 0.85F : 0.75F;
         } else {
            return flag ? 0.95F : 0.8F;
         }
      }
   }

   public static boolean isStringBoolean(String text, boolean flag) {
      float f3 = getFloat();
      float f2 = -0.01F;
      float f1 = f3;
      float f = 0.0F;
      return isStringFloatBooleanFloatFloat(text, f1, flag, f2, f);
   }

   public static boolean isStringFloatBooleanFloatFloat(String text, float value, boolean flag2, float value2, float value3) {
      if (Feature.mc.player != null && flag2) {
         float f = Feature.mc.player.getAttackCooldownProgress(value3);
         if (f < value) {
            return false;
         } else {
            boolean flag = isFloat(value2);
            if ("Только криты".equals(text)) {
               return check() || flag;
            } else if (!check() && Feature.mc.player.getVelocity().y > 0.0) {
               return false;
            } else {
               return Feature.mc.player.isOnGround() ? f >= value : check() || flag;
            }
         }
      } else {
         return false;
      }
   }
}
