package recovered.fabric.mixin.profile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import recovered.fabric.identity.ProfileIdentity;

@Mixin(
   targets = {"client.network.ServerUtil"},
   remap = false
)
public abstract class SessionProfileMixin {
   @Inject(
      method = {"getText()Ljava/lang/String;"},
      at = {@At("HEAD")},
      cancellable = true,
      remap = false
   )
   private static void replaceUsername(CallbackInfoReturnable<String> callback) {
      callback.setReturnValue(ProfileIdentity.username());
   }
}
