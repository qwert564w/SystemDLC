package recovered.fabric.mixin.diagnostic;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import recovered.fabric.diagnostic.SystemDlcLog;

@Mixin(
   targets = {"client.module.client.SliskGui"},
   remap = false
)
public abstract class GuiModuleMixin {
   @Inject(
      method = {"toggle()V"},
      at = {@At("HEAD")},
      remap = false
   )
   private void beforeToggle(CallbackInfo callback) {
      SystemDlcLog.info("RightShift GUI toggle received");
   }

   @Inject(
      method = {"toggle()V"},
      at = {@At("RETURN")},
      remap = false
   )
   private void afterToggle(CallbackInfo callback) {
      SystemDlcLog.info("RightShift GUI toggle completed");
   }
}
