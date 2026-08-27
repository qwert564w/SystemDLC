package client.util;

import client.module.Feature;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class RaycastUtil {
   public static boolean isVec3dVec3d(Vec3d vec3d, Vec3d vec3d2) {
      if (Feature.mc.world == null) {
         return false;
      } else if (vec3d2.squaredDistanceTo(vec3d) < 1.0E-4) {
         return false;
      } else {
         RaycastContext raycastcontext = new RaycastContext(vec3d2, vec3d, ShapeType.COLLIDER, FluidHandling.NONE, Feature.mc.player);
         BlockHitResult blockhitresult = Feature.mc.world.raycast(raycastcontext);
         return blockhitresult.getType() != Type.MISS;
      }
   }
}
