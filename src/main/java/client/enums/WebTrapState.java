package client.enums;

public enum WebTrapState {
   IDLE,
   SWAP_SLOT,
   PLACE,
   SWAP_BACK;

   private static final WebTrapState[] webTrapStateArray = getWebTrapStateArray();

   private static WebTrapState[] getWebTrapStateArray() {
      return new WebTrapState[]{IDLE, SWAP_SLOT, PLACE, SWAP_BACK};
   }

   public static WebTrapState getWebTrapStateByString(String text) {
      return Enum.valueOf(WebTrapState.class, text);
   }
}
