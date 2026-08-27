package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.AttackEvent;
import client.util.BlockPlacement;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlastFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.CrafterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SmokerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class GhostHand extends Module {
   private SliderSetting range;
   private BooleanSetting onlyKonteynery;
   private BooleanSetting attackThroughWalls;
   private BooleanSetting igrokov;
   private BooleanSetting mobov;
   private SliderSetting rangeAtaki;
   private static LivingEntity livingEntity = null;

   public GhostHand() {
      super("GhostHand", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 5.0, 1.0, 6.0, 0.5);
      slidersetting.setName("Дистанция");
      slidersetting.setDescription("Максимальная дистанция взаимодействия");
      this.range = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Только контейнеры");
      booleansetting.setDescription("Взаимодействовать только c сундуками/бочками/шалкерами и т.п.");
      this.onlyKonteynery = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Атака сквозь стены");
      booleansetting1.setDescription("Бить игроков/мобов сквозь блоки как в фрикаме");
      this.attackThroughWalls = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("Игроков");
      booleansetting2.setDescription("Бить игроков сквозь стены");
      this.igrokov = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", true);
      booleansetting3.setName("Мобов");
      booleansetting3.setDescription("Бить мобов сквозь стены");
      this.mobov = booleansetting3;
      slidersetting = new SliderSetting("", "", 3.0, 1.0, 6.0, 0.1);
      slidersetting.setName("Дистанция атаки");
      slidersetting.setDescription("Максимальная дистанция атаки сквозь стены");
      this.rangeAtaki = slidersetting;
      this.addSettings(new Setting[]{this.range, this.onlyKonteynery, this.attackThroughWalls, this.igrokov, this.mobov, this.rangeAtaki});
      this.igrokov.setVisibleWhen(this.attackThroughWalls::isFlag3);
      this.mobov.setVisibleWhen(this.attackThroughWalls::isFlag3);
      this.rangeAtaki.setVisibleWhen(this.attackThroughWalls::isFlag3);
   }

   private LivingEntity getLivingEntity() {
      PlayerEntity playerentity = this.player();
      if (playerentity == null) {
         return null;
      } else {
         double d0 = this.rangeAtaki.getValue();
         Vec3d vec3d = playerentity.getCameraPosVec(1.0F);
         Vec3d vec3d1 = playerentity.getRotationVec(1.0F);
         Vec3d vec3d2 = vec3d.add(vec3d1.multiply(d0));
         Box box = playerentity.getBoundingBox().stretch(vec3d1.multiply(d0)).expand(1.0);
         EntityHitResult entityhitresult = ProjectileUtil.raycast(playerentity, vec3d, vec3d2, box, p0 -> this.isPlayerEntityEntity(playerentity, p0), d0 * d0);
         if (!(entityhitresult != null && entityhitresult.getEntity() instanceof LivingEntity livingentity)) {
            return null;
         } else {
            return !livingentity.isAlive() ? null : livingentity;
         }
      }
   }

   @Override
   public void onDisable() {
      livingEntity = null;
   }

   private Direction getDirectionByVec3dBlockPos(Vec3d vec3d2, BlockPos blockPos) {
      Vec3d vec3d = Vec3d.ofCenter(blockPos);
      Vec3d vec3d1 = vec3d2.subtract(vec3d);
      double d0 = Math.abs(vec3d1.x);
      double d1 = Math.abs(vec3d1.y);
      double d2 = Math.abs(vec3d1.z);
      if (d0 >= d1 && d0 >= d2) {
         return vec3d1.x > 0.0 ? Direction.EAST : Direction.WEST;
      } else if (d1 >= d0 && d1 >= d2) {
         return vec3d1.y > 0.0 ? Direction.UP : Direction.DOWN;
      } else {
         return vec3d1.z > 0.0 ? Direction.SOUTH : Direction.NORTH;
      }
   }

   private boolean isBlockPosBlockState(BlockPos blockPos, BlockState blockState) {
      Block block = blockState.getBlock();
      if (this.onlyKonteynery.isFlag3()) {
         BlockEntity blockentity = this.world().getBlockEntity(blockPos);
         if (blockentity instanceof LockableContainerBlockEntity) {
            return true;
         } else if (block instanceof EnderChestBlock) {
            return true;
         } else if (block instanceof ChestBlock) {
            return true;
         } else if (block instanceof BarrelBlock) {
            return true;
         } else if (block instanceof ShulkerBoxBlock) {
            return true;
         } else if (block instanceof FurnaceBlock) {
            return true;
         } else if (block instanceof BlastFurnaceBlock) {
            return true;
         } else if (block instanceof SmokerBlock) {
            return true;
         } else if (block instanceof HopperBlock) {
            return true;
         } else if (block instanceof DispenserBlock) {
            return true;
         } else if (block instanceof BrewingStandBlock) {
            return true;
         } else {
            return block instanceof CrafterBlock ? true : block instanceof AbstractChestBlock;
         }
      } else {
         return blockState.getOutlineShape(this.world(), blockPos) != null && !blockState.getOutlineShape(this.world(), blockPos).isEmpty();
      }
   }

   private Vec3d getVec3dByDirectionBlockPos(Direction direction, BlockPos blockPos) {
      double d0 = blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5;
      double d1 = blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5;
      double d2 = blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5;
      return new Vec3d(d0, d1, d2);
   }

   private boolean isPlayerEntityEntity(PlayerEntity playerEntity, Entity entity2) {
      return !entity2.isSpectator() && entity2.canHit() && entity2 != playerEntity && this.isEntity(entity2);
   }

   public static LivingEntity getLivingEntity2() {
      LivingEntity livingentity = livingEntity;
      livingEntity = null;
      return livingentity;
   }

   @Override
   public void onAttackEvent(AttackEvent attackEvent) {
      if (!this.notInGame()) {
         BlockPlacement blockplacement = this.getBlockPlacement();
         if (blockplacement != null) {
            BlockHitResult blockhitresult = new BlockHitResult(blockplacement.aimPoint(), blockplacement.side(), blockplacement.pos(), false);
            this.client().crosshairTarget = blockhitresult;
         }
      }
   }

   private boolean isEntity(Entity entity2) {
      if (entity2 instanceof LivingEntity livingentity) {
         if (this.isFriend(livingentity)) {
            return false;
         } else if (livingentity instanceof PlayerEntity) {
            return this.igrokov.isFlag3();
         } else {
            return livingentity instanceof MobEntity ? this.mobov.isFlag3() : false;
         }
      } else {
         return false;
      }
   }

   private BlockPlacement getBlockPlacement() {
      if (this.world() != null && this.player() != null) {
         Vec3d vec3d = this.player().getEyePos();
         Vec3d vec3d1 = this.player().getRotationVec(1.0F);
         double d0 = this.range.getValue();
         double d1 = 0.2;
         BlockPos blockpos = null;

         for (double d2 = 0.0; d2 <= d0; d2 += d1) {
            Vec3d vec3d2 = vec3d.add(vec3d1.multiply(d2));
            BlockPos blockpos1 = BlockPos.ofFloored(vec3d2);
            if (!blockpos1.equals(blockpos)) {
               blockpos = blockpos1;
               BlockState blockstate = this.world().getBlockState(blockpos1);
               if (!blockstate.isAir() && this.isBlockPosBlockState(blockpos1, blockstate)) {
                  Direction direction = this.getDirectionByVec3dBlockPos(vec3d, blockpos1);
                  Vec3d vec3d3 = this.getVec3dByDirectionBlockPos(direction, blockpos1);
                  return new BlockPlacement(blockpos1, direction, vec3d3);
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         if (this.attackThroughWalls.isFlag3()) {
            if (!this.options().attackKey.isPressed()) {
               livingEntity = null;
            } else {
               LivingEntity livingentity = this.getLivingEntity();
               livingEntity = livingentity;
               if (livingentity != null) {
                  this.client().crosshairTarget = new EntityHitResult(livingentity, livingentity.getBoundingBox().getCenter());
               }
            }
         }
      }
   }
}
