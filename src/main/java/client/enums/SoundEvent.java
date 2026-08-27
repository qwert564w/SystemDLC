package client.enums;

public enum SoundEvent {
   GUI_OPEN("4.wav"),
   GUI_CLOSE("4F.wav"),
   MODULE_ENABLE("3.wav"),
   MODULE_DISABLE("3F.wav"),
   FRIEND_ADD("3.wav"),
   FRIEND_REMOVE("3F.wav"),
   GROUP_OPEN("5.wav"),
   FAVOURITE_ADD("6.wav"),
   CATEGORY_SWITCH("1.wav"),
   TOGGLE_ON("2.wav"),
   TOGGLE_OFF("2F.wav");

   private final String text;
   private static final SoundEvent[] soundEventArray = getSoundEventArray();

   private SoundEvent(String text2) {
      this.text = text2;
   }

   public static SoundEvent getSoundEventByString(String text) {
      return Enum.valueOf(SoundEvent.class, text);
   }

   private static SoundEvent[] getSoundEventArray() {
      return new SoundEvent[]{
         GUI_OPEN, GUI_CLOSE, MODULE_ENABLE, MODULE_DISABLE, FRIEND_ADD, FRIEND_REMOVE, GROUP_OPEN, FAVOURITE_ADD, CATEGORY_SWITCH, TOGGLE_ON, TOGGLE_OFF
      };
   }

   public String getText() {
      return this.text;
   }
}
