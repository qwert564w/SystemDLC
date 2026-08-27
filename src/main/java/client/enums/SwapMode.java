package client.enums;

public enum SwapMode {
   TOTEM_TO_SPHERE,
   SPHERE_TO_SPHERE,
   TOTEM_TO_TOTEM;

   private static final SwapMode[] swapModeArray = getSwapModeArray();

   private static SwapMode[] getSwapModeArray() {
      return new SwapMode[]{TOTEM_TO_SPHERE, SPHERE_TO_SPHERE, TOTEM_TO_TOTEM};
   }

   public static SwapMode getSwapModeByString(String text) {
      return Enum.valueOf(SwapMode.class, text);
   }
}
