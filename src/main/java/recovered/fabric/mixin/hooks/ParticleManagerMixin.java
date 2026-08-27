package recovered.fabric.mixin.hooks;

import client.concurrent.HandleInvoker;
import client.render.ParticleHooks;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Inject(method = "method_3056", at = @At("HEAD"), cancellable = true)
    private void systemdlcAddParticle(
        ParticleEffect parameters,
        double x,
        double y,
        double z,
        double velocityX,
        double velocityY,
        double velocityZ,
        CallbackInfoReturnable<Particle> cir
    ) {
        if (HandleInvoker.isMixinGuard()) {
            return;
        }
        HandleInvoker.setMixinGuard(true);
        try {
            Particle result = ParticleHooks.getParticleByParticleManagerParticleEffectDoubleDoubleDoubleDoubleDoubleDouble(
                (ParticleManager)(Object)this, parameters, x, y, z, velocityX, velocityY, velocityZ
            );
            cir.setReturnValue(result);
        } finally {
            HandleInvoker.setMixinGuard(false);
        }
    }

    @Inject(method = "method_3054", at = @At("HEAD"), cancellable = true)
    private void systemdlcAddBlockBreakParticles(BlockPos pos, Direction direction, CallbackInfo ci) {
        if (!ParticleHooks.isParticleManagerBlockPosDirection((ParticleManager)(Object)this, pos, direction)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_3046", at = @At("HEAD"), cancellable = true)
    private void systemdlcAddBlockDustParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!ParticleHooks.isParticleManagerBlockPosBlockState((ParticleManager)(Object)this, pos, state)) {
            ci.cancel();
        }
    }
}
