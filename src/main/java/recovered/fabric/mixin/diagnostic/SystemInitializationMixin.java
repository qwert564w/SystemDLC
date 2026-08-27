package recovered.fabric.mixin.diagnostic;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import recovered.fabric.diagnostic.SystemDlcLog;
import recovered.fabric.nativebridge.HookStatus;
import recovered.fabric.offline.OfflineStateBridge;

@Mixin(
   targets = {"client.concurrent.SystemClient"},
   remap = false
)
public abstract class SystemInitializationMixin {
   @Inject(
      method = {"update3()V"},
      at = {@At("HEAD")},
      remap = false
   )
   private void beforeAuthorization(CallbackInfo callback) {
      SystemDlcLog.info("authorization started");
   }

   @Inject(
      method = {"update3()V"},
      at = {@At("RETURN")},
      remap = false
   )
   private void afterAuthorization(CallbackInfo callback) {
      SystemDlcLog.info("authorization completed");
   }

   @Inject(
      method = {"update()V"},
      at = {@At("HEAD")},
      remap = false
   )
   private void beforeSystems(CallbackInfo callback) {
      SystemDlcLog.info("system initialization started");
   }

   @Inject(
      method = {"update()V"},
      at = {@At("RETURN")},
      remap = false
   )
   private void afterSystems(CallbackInfo callback) {
      HookStatus.report();
      OfflineStateBridge.initialize();
      SystemDlcLog.info("system initialization completed");
   }
}
