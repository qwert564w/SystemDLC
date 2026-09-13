package client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import java.util.Random;

public class PolarBypass {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static float gcdStep() {
        float sens = mc.options.getMouseSensitivity().getValue().floatValue();
        float f = sens * 0.6F + 0.2F;
        float gcd = f * f * f * 8F;
        return gcd * 0.15F;
    }

    public static float quantize(float delta) {
        float step = gcdStep();
        if (step <= 0) return delta;
        return Math.round(delta / step) * step;
    }

    public static boolean hasLos(Entity from, Entity to) {
        if (mc.world == null) return false;
        Vec3d fromEye = from.getEyePos();
        Vec3d toCenter = to.getBoundingBox().getCenter();
        RaycastContext ctx = new RaycastContext(fromEye, toCenter, ShapeType.COLLIDER, FluidHandling.NONE, from);
        HitResult res = mc.world.raycast(ctx);
        if (res.getType() == HitResult.Type.MISS) return true;
        if (res instanceof EntityHitResult ehr && ehr.getEntity() == to) return true;
        return res.getPos().squaredDistanceTo(fromEye) > to.squaredDistanceTo(fromEye);
    }

    public static float angleTo(LivingEntity player, Vec3d point) {
        Vec3d look = player.getRotationVec(1.0F);
        Vec3d dir = point.subtract(player.getEyePos()).normalize();
        double dot = look.dotProduct(dir);
        dot = Math.max(-1.0, Math.min(1.0, dot));
        return (float) Math.toDegrees(Math.acos(dot));
    }

    public static float[] stepRotation(float curYaw, float curPitch, float tgtYaw, float tgtPitch, float maxSpeedDeg, float smooth, float jitterDeg, Random rnd) {
        float dYaw = MathHelper.wrapDegrees(tgtYaw - curYaw);
        float dPitch = tgtPitch - curPitch;
        
        float stepYaw = dYaw * Math.max(0.02F, Math.min(1.0F, smooth));
        float stepPitch = dPitch * Math.max(0.02F, Math.min(1.0F, smooth));
        
        float mag = (float) Math.sqrt(stepYaw * stepYaw + stepPitch * stepPitch);
        if (mag > maxSpeedDeg) {
            float scale = maxSpeedDeg / mag;
            stepYaw *= scale;
            stepPitch *= scale;
        }
        
        stepYaw += (rnd.nextFloat() - 0.5F) * jitterDeg;
        stepPitch += (rnd.nextFloat() - 0.5F) * jitterDeg * 0.6F;
        
        float newYaw = curYaw + quantize(stepYaw);
        float newPitch = curPitch + quantize(stepPitch);
        
        newPitch = Math.max(-90.0F, Math.min(90.0F, newPitch));
        
        return new float[]{newYaw, newPitch};
    }

    public static long humanReactionMs(long mean, long stddev, long min, long max, Random rnd) {
        long val = (long) (mean + rnd.nextGaussian() * stddev);
        return Math.max(min, Math.min(max, val));
    }

    public static Vec3d randomAimPoint(Entity target, double spread, Random rnd) {
        double minX = target.getX() - target.getWidth() / 2.0 + 0.05;
        double maxX = target.getX() + target.getWidth() / 2.0 - 0.05;
        double minZ = target.getZ() - target.getWidth() / 2.0 + 0.05;
        double maxZ = target.getZ() + target.getWidth() / 2.0 - 0.05;
        
        double minY = target.getY() + target.getHeight() * 0.72 - spread / 2.0;
        double maxY = target.getY() + target.getHeight() * 0.72 + spread / 2.0;
        
        minY = Math.max(target.getY() + 0.05, minY);
        maxY = Math.min(target.getY() + target.getHeight() - 0.05, maxY);
        
        double x = minX + (maxX - minX) * (0.5 + rnd.nextGaussian() * 0.5);
        double y = minY + (maxY - minY) * (0.5 + rnd.nextGaussian() * 0.5);
        double z = minZ + (maxZ - minZ) * (0.5 + rnd.nextGaussian() * 0.5);
        
        x = Math.max(minX, Math.min(maxX, x));
        y = Math.max(minY, Math.min(maxY, y));
        z = Math.max(minZ, Math.min(maxZ, z));
        
        return new Vec3d(x, y, z);
    }
}
