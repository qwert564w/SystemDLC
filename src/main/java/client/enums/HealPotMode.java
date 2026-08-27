package client.enums;

public enum HealPotMode {
   NONE,
   USE_FROM_HOTBAR,
   USE_FROM_INVENTORY;

   private static final HealPotMode[] healPotModeArray = getHealPotModeArray();

   private static HealPotMode[] getHealPotModeArray() {
      return new HealPotMode[]{NONE, USE_FROM_HOTBAR, USE_FROM_INVENTORY};
   }

   public static HealPotMode getHealPotModeByString(String text) {
      return Enum.valueOf(HealPotMode.class, text);
   }
}
