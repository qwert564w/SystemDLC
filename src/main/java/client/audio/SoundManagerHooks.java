package client.audio;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.module.player.NoSounds;
import client.util.UnsafeAccess;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.TickableSoundInstance;

@HookClass(SoundManager.class)
public class SoundManagerHooks {
   private static final UnsafeAccess<NoSounds> unsafeAccess = new UnsafeAccess<>(NoSounds.class);

   @Hook(
      method = "method_4872",
      desc = "(Lnet/minecraft/class_1113;I)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSoundManagerSoundInstanceInt(SoundManager soundManager, SoundInstance soundInstance, int count) {
      return isSoundInstance(soundInstance);
   }

   @Hook(
      method = "method_22140",
      desc = "(Lnet/minecraft/class_1117;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSoundManagerTickableSoundInstance(SoundManager soundManager, TickableSoundInstance tickableSoundInstance) {
      return isSoundInstance(tickableSoundInstance);
   }

   private static boolean isSoundInstance(SoundInstance soundInstance) {
      NoSounds nosounds = (NoSounds)unsafeAccess.getModule2();
      return nosounds == null || !nosounds.isSoundInstance(soundInstance);
   }

   @Hook(
      method = "method_4873",
      desc = "(Lnet/minecraft/class_1113;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isSoundManagerSoundInstance(SoundManager soundManager, SoundInstance soundInstance) {
      return isSoundInstance(soundInstance);
   }
}
