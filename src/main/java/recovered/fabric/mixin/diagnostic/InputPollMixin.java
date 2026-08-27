package recovered.fabric.mixin.diagnostic;

import client.data.FactoryRegistry;
import client.util.KeyboardState;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import recovered.fabric.diagnostic.SystemDlcLog;
import recovered.fabric.nativebridge.HookStatus;

@Mixin(MinecraftClient.class)
public abstract class InputPollMixin {
   private static boolean iconsRequested;

   @Inject(
      method = {"tick()V"},
      at = {@At("HEAD")}
   )
   private void pollKeybinds(CallbackInfo callback) {
      if (!iconsRequested) {
         iconsRequested = true;

         try {
            FactoryRegistry.update3();
            SystemDlcLog.info("icon registry initialized");
            HookStatus.report();
         } catch (Throwable throwable) {
            SystemDlcLog.error("icon registry initialization failed", throwable);
         }
      }

      try {
         KeyboardState.getKeyboardState().update5();
      } catch (Throwable throwable) {
      }
   }
}
