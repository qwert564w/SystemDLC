package recovered.fabric.mixin.hooks;

import client.audio.SoundManagerHooks;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.TickableSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin {
    @Inject(method = "method_4872", at = @At("HEAD"), cancellable = true)
    private void systemdlcPlayNextTick(SoundInstance sound, int delay, CallbackInfo ci) {
        if (!SoundManagerHooks.isSoundManagerSoundInstanceInt((SoundManager)(Object)this, sound, delay)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_22140", at = @At("HEAD"), cancellable = true)
    private void systemdlcTick(TickableSoundInstance sound, CallbackInfo ci) {
        if (!SoundManagerHooks.isSoundManagerTickableSoundInstance((SoundManager)(Object)this, sound)) {
            ci.cancel();
        }
    }

    @Inject(method = "method_4873", at = @At("HEAD"), cancellable = true)
    private void systemdlcPlay(SoundInstance sound, CallbackInfo ci) {
        if (!SoundManagerHooks.isSoundManagerSoundInstance((SoundManager)(Object)this, sound)) {
            ci.cancel();
        }
    }
}
