package client.module.combat;

import client.module.Category;
import client.module.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Minecraft 1.21.4-compatible Aura module shell.
 *
 * The previous Aura.java belonged to a different aethereal.* codebase and
 * could not compile inside the current client.module architecture.
 * This implementation keeps the module registration/API stable so the
 * project can build and launch on the current source tree.
 */
public final class Aura extends Module {
    private LivingEntity target;

    public Aura() {
        super("Aura", Category.COMBAT);
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    public void onTick() {
        if (!isEnabled() || mc.player == null || mc.world == null) {
            target = null;
            return;
        }

        target = mc.world.getPlayers().stream()
                .filter(this::isValidTarget)
                .min((a, b) -> Double.compare(
                        mc.player.squaredDistanceTo(a),
                        mc.player.squaredDistanceTo(b)))
                .orElse(null);
    }

    private boolean isValidTarget(PlayerEntity player) {
        return player != mc.player
                && !player.isSpectator()
                && player.isAlive()
                && AuraUtil.inRange(mc.player.getEyePos(), player, 3.0D);
    }
}
