package client.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Small Minecraft 1.21.4/Yarn utility used by Aura and other combat modules.
 */
public final class AuraUtil {
    private AuraUtil() {
    }

    public static double distanceSquared(Vec3d from, Entity entity) {
        if (from == null || entity == null) {
            return Double.POSITIVE_INFINITY;
        }

        Box box = entity.getBoundingBox();
        double x = MathHelper.clamp(from.x, box.minX, box.maxX);
        double y = MathHelper.clamp(from.y, box.minY, box.maxY);
        double z = MathHelper.clamp(from.z, box.minZ, box.maxZ);

        return from.squaredDistanceTo(x, y, z);
    }

    public static boolean inRange(Vec3d from, LivingEntity entity, double range) {
        if (from == null || entity == null || !entity.isAlive() || range < 0.0D) {
            return false;
        }
        return distanceSquared(from, entity) <= range * range;
    }
}
