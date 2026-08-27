package recovered.fabric.mixin.profile;

import client.util.ModuleDispatcher;
import client.util.UnsafeAccess;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Drives the client tick loop.
 *
 * Normally GameMenuHooks feeds MinecraftClient.tick into ModuleDispatcher, but those
 * hooks are installed by ClassRedefiner through the native library. With the native
 * path disabled nothing ticks the client, so keybinds are never polled.
 */
@Mixin(MinecraftClient.class)
public abstract class ClientTickMixin {
   @Inject(method = "tick()V", at = @At("TAIL"))
   private void systemdlcTick(CallbackInfo callback) {
      ModuleDispatcher dispatcher = UnsafeAccess.getModuleDispatcher();
      if (dispatcher != null) {
         try {
            dispatcher.update2();
         } catch (Throwable throwable) {
         }
      }
   }
}
