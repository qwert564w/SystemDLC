package recovered.fabric.mixin.diagnostic;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import recovered.fabric.diagnostic.SystemDlcLog;

@Mixin(
   targets = {"client.concurrent.ModuleRegistry"},
   remap = false
)
public abstract class ModuleRegistryMixin {
   @Inject(
      method = {"update7()V"},
      at = {@At("HEAD")},
      remap = false
   )
   private void beforeModules(CallbackInfo callback) {
      SystemDlcLog.moduleScanStarted();
   }

   @Inject(
      method = {"addModule(Lclient/module/Module;)V"},
      at = {@At("TAIL")},
      remap = false
   )
   private void afterModule(@Coerce Object module, CallbackInfo callback) {
      SystemDlcLog.moduleRegistered(module);
   }

   @Inject(
      method = {"update7()V"},
      at = {@At("RETURN")},
      remap = false
   )
   private void afterModules(CallbackInfo callback) {
      SystemDlcLog.moduleScanFinished();
   }
}
