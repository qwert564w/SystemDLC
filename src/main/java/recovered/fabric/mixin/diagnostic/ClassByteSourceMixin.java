package recovered.fabric.mixin.diagnostic;

import b.Boot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import recovered.fabric.diagnostic.SystemDlcLog;

@Mixin(
   targets = {"client.transform.ClassRedefiner"},
   remap = false
)
public abstract class ClassByteSourceMixin {
   @Redirect(
      method = {"getByteArrayByString(Ljava/lang/String;)[B"},
      at = @At(
         value = "INVOKE",
         target = "Lb/Boot;nativeDumpClassBytes(Ljava/lang/Class;)[B"
      ),
      remap = false
   )
   private static byte[] validateNativeBytes(Class<?> type) {
      try {
         byte[] bytes = Boot.nativeDumpClassBytes(type);
         if (valid(bytes)) {
            return bytes;
         } else {
            SystemDlcLog.once("nativeClassBytes", "native class dump rejected; original fallback enabled");
            return null;
         }
      } catch (Throwable throwable) {
         SystemDlcLog.once("nativeClassBytes", "native class dump unavailable; using fallback");
         return null;
      }
   }

   private static boolean valid(byte[] bytes) {
      return bytes != null && bytes.length >= 4 && bytes[0] == -54 && bytes[1] == -2 && bytes[2] == -70 && bytes[3] == -66;
   }
}
