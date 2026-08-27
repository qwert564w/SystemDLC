package recovered.fabric.mixin.diagnostic;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import recovered.fabric.diagnostic.SystemDlcLog;

@Mixin(
   targets = {"client.util.KeyboardState"},
   remap = false
)
public abstract class InputSystemMixin {
   @Inject(
      method = {"update3()V"},
      at = {@At("HEAD")},
      remap = false
   )
   private void beforeInput(CallbackInfo callback) {
      SystemDlcLog.info("input system start requested");
   }

   @Inject(
      method = {"update3()V"},
      at = {@At("RETURN")},
      remap = false
   )
   private void afterInput(CallbackInfo callback) {
      SystemDlcLog.info("input system returned; KeyboardState.update3 contains no polling code");
   }
}
