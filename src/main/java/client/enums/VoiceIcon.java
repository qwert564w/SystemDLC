package client.enums;

import net.minecraft.util.Identifier;

public enum VoiceIcon {
   NONE(null),
   SPEAKER(Identifier.of("voicechat", "textures/icons/speaker.png")),
   WHISPER(Identifier.of("voicechat", "textures/icons/speaker_whisper.png")),
   DISCONNECT(Identifier.of("voicechat", "textures/icons/disconnected.png")),
   GROUP(Identifier.of("voicechat", "textures/icons/group.png")),
   DISABLED(Identifier.of("voicechat", "textures/icons/speaker_off.png"));

   public final Identifier identifier;
   private static final VoiceIcon[] voiceIconArray = getVoiceIconArray();

   private VoiceIcon(Identifier identifier2) {
      this.identifier = identifier2;
   }

   private static VoiceIcon[] getVoiceIconArray() {
      return new VoiceIcon[]{NONE, SPEAKER, WHISPER, DISCONNECT, GROUP, DISABLED};
   }

   public static VoiceIcon getVoiceIconByString(String text) {
      return Enum.valueOf(VoiceIcon.class, text);
   }
}
