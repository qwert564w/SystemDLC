package client.util;

import client.data.SystemFriend;
import client.module.Feature;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class TargetSelector {
   private static double getDoubleByLivingEntity(LivingEntity livingEntity) {
      return Feature.mc.player.distanceTo(livingEntity);
   }

   private static double getDoubleByLivingEntity2(LivingEntity livingEntity) {
      return Feature.mc.player.distanceTo(livingEntity);
   }

   public static double getDoubleByLivingEntity3(LivingEntity livingEntity) {
      if (Feature.mc.player == null) {
         return Double.MAX_VALUE;
      } else {
         Vec3d vec3d = Feature.mc.player.getCameraPosVec(1.0F);
         Box box = livingEntity.getBoundingBox().expand(livingEntity.getTargetingMargin());
         double d0 = Math.max(0.0, Math.max(box.minX - vec3d.x, vec3d.x - box.maxX));
         double d1 = Math.max(0.0, Math.max(box.minY - vec3d.y, vec3d.y - box.maxY));
         double d2 = Math.max(0.0, Math.max(box.minZ - vec3d.z, vec3d.z - box.maxZ));
         return d0 * d0 + d1 * d1 + d2 * d2;
      }
   }

   public static Vec3d getVec3dByLivingEntity(LivingEntity livingEntity) {
      return new Vec3d(livingEntity.getX(), livingEntity.getY() + livingEntity.getHeight() * 0.5, livingEntity.getZ());
   }

   private static boolean isEntity(Entity entity2) {
      return entity2 instanceof LivingEntity;
   }

   private static LivingEntity getLivingEntityByEntity(Entity entity2) {
      return (LivingEntity)entity2;
   }

   private static boolean isDoubleBooleanDoubleLivingEntity(double value, boolean flag, double value2, LivingEntity livingEntity) {
      return isLivingEntityBooleanDoubleDouble(livingEntity, flag, value, value2);
   }

   public static boolean isLivingEntity(LivingEntity livingEntity) {
      for (ItemStack itemstack : livingEntity.getArmorItems()) {
         if (!itemstack.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   public static boolean isBooleanBooleanBooleanLivingEntityBoolean(boolean flag, boolean flag2, boolean flag3, LivingEntity livingEntity, boolean flag4) {
      if (Feature.mc.player == null
         || livingEntity == null
         || livingEntity == Feature.mc.player
         || livingEntity.isDead()
         || !livingEntity.isAlive()
         || livingEntity.isSpectator()
         || livingEntity.getHealth() <= 0.0F) {
         return false;
      } else if (!flag4 && livingEntity.isInvisible()) {
         return false;
      } else if (!flag2 && livingEntity.isBlocking()) {
         return false;
      } else if (flag && !isLivingEntity(livingEntity)) {
         return false;
      } else {
         return livingEntity instanceof PlayerEntity ? !SystemFriend.getInstance().isString(livingEntity.getName().getString()) : !flag3 && livingEntity instanceof MobEntity;
      }
   }

   public static boolean isLivingEntityBooleanDoubleDouble(LivingEntity livingEntity, boolean flag, double value, double value2) {
      if (Feature.mc.player == null) {
         return false;
      } else if (!flag) {
         return Feature.mc.player.distanceTo(livingEntity) <= value;
      } else {
         double d0 = livingEntity.getX() - Feature.mc.player.getX();
         double d1 = livingEntity.getY() - Feature.mc.player.getY();
         double d2 = livingEntity.getZ() - Feature.mc.player.getZ();
         double d3 = d0 * d0 + d2 * d2;
         double d4 = Math.sqrt(d3);
         return d4 <= value && Math.abs(d1) <= value2;
      }
   }

   public static Stream getStreamByDoubleBooleanDouble(double value, boolean flag, double value2) {
      return Feature.mc.world != null && Feature.mc.player != null
         ? StreamSupport.<Entity>stream(Feature.mc.world.getEntities().spliterator(), false)
            .filter(TargetSelector::isEntity)
            .map(TargetSelector::getLivingEntityByEntity)
            .filter(p0 -> TargetSelector.isDoubleBooleanDoubleLivingEntity(value, flag, value, p0))
         : Stream.empty();
   }

   public static boolean isLivingEntityFloat(LivingEntity livingEntity, float value) {
      if (Feature.mc.player == null) {
         return true;
      } else if (value >= 180.0F) {
         return true;
      } else {
         Vec3d vec3d = livingEntity.getPos().add(0.0, livingEntity.getHeight() / 2.0F, 0.0).subtract(Feature.mc.player.getEyePos()).normalize();
         double d0 = Math.max(-1.0, Math.min(1.0, Feature.mc.player.getRotationVec(1.0F).dotProduct(vec3d)));
         return Math.toDegrees(Math.acos(d0)) <= value / 2.0F;
      }
   }

   public static LivingEntity getLivingEntityByFloatLivingEntityIntListString(float value, LivingEntity livingEntity, int count, List list, String text) {
      if (list.isEmpty()) {
         return null;
      } else if (Feature.mc.player == null) {
         return null;
      } else {
         return switch (text) {
            case "Текущая цель" -> livingEntity != null && count < value && list.contains(livingEntity)
               ? livingEntity
               : (LivingEntity)list.stream().min(Comparator.comparingDouble(TargetSelector::getDoubleByLivingEntity2)).orElse(null);
            case "Меньше HP" -> (LivingEntity)list.stream().min(Comparator.comparingDouble(LivingEntity::getHealth)).orElse(null);
            default -> (LivingEntity)list.stream().min(Comparator.comparingDouble(TargetSelector::getDoubleByLivingEntity)).orElse(null);
         };
      }
   }

   public static boolean isDoubleVec3d(double value, Vec3d vec3d2) {
      if (Feature.mc.player == null) {
         return false;
      } else {
         Vec3d vec3d = vec3d2.subtract(Feature.mc.player.getEyePos()).normalize();
         double d0 = Math.max(-1.0, Math.min(1.0, Feature.mc.player.getRotationVec(1.0F).dotProduct(vec3d)));
         return Math.toDegrees(Math.acos(d0)) <= value;
      }
   }
}
