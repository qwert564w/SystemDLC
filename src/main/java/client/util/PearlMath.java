package client.util;

import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PearlMath {
   public static boolean isWorldPlayerEntityDouble(World world2, PlayerEntity playerEntity, double value) {
      if (world2 != null && playerEntity != null) {
         for (TridentEntity tridententity : world2.getEntitiesByClass(
            TridentEntity.class, getBoxByPlayerEntityDouble(playerEntity, value), p0 -> PearlMath.isPlayerEntityTridentEntity(playerEntity, p0)
         )) {
            if (!(playerEntity.distanceTo(tridententity) > value)) {
               Vec3d vec3d = tridententity.getVelocity();
               if (!(vec3d.lengthSquared() < 0.01)) {
                  Vec3d vec3d1 = playerEntity.getPos().subtract(tridententity.getPos());
                  if (vec3d1.lengthSquared() < 1.0E-4) {
                     return true;
                  }

                  if (vec3d.normalize().dotProduct(vec3d1.normalize()) > 0.5) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean isIntTntEntity(int count, TntEntity tntEntity) {
      return tntEntity.getFuse() <= count;
   }

   private static boolean isPlayerEntityTridentEntity(PlayerEntity playerEntity, TridentEntity tridentEntity) {
      return tridentEntity.getOwner() != playerEntity;
   }

   private static boolean isEndCrystalEntity(EndCrystalEntity endCrystalEntity) {
      return true;
   }

   public static boolean isIntWorldDoublePlayerEntity(int count, World world2, double value, PlayerEntity playerEntity) {
      if (world2 != null && playerEntity != null) {
         for (TntEntity tntentity : world2.getEntitiesByClass(TntEntity.class, getBoxByPlayerEntityDouble(playerEntity, value), p0 -> PearlMath.isIntTntEntity(count, p0))) {
            if (playerEntity.distanceTo(tntentity) <= value) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean isWorldPlayerEntityDouble2(World world2, PlayerEntity playerEntity, double value) {
      if (world2 != null && playerEntity != null) {
         for (EndCrystalEntity endcrystalentity : world2.getEntitiesByClass(
            EndCrystalEntity.class, getBoxByPlayerEntityDouble(playerEntity, value), PearlMath::isEndCrystalEntity
         )) {
            if (playerEntity.distanceTo(endcrystalentity) <= value) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static Box getBoxByPlayerEntityDouble(PlayerEntity playerEntity, double value) {
      return playerEntity.getBoundingBox().expand(Math.min(value, 128.0));
   }
}
