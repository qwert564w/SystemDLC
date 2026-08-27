package client.enums;

public enum AutoAnchorState {
   IDLE,
   SWAP_TO_ANCHOR,
   PLACE_ANCHOR,
   VERIFY_ANCHOR,
   SWAP_TO_GLOWSTONE,
   CHARGE_ANCHOR,
   SWAP_BACK_FROM_GLOW,
   EXPLODE_ANCHOR,
   SWAP_BACK,
   DONE;

   private static final AutoAnchorState[] autoAnchorStateArray = getAutoAnchorStateArray();

   private static AutoAnchorState[] getAutoAnchorStateArray() {
      return new AutoAnchorState[]{
         IDLE, SWAP_TO_ANCHOR, PLACE_ANCHOR, VERIFY_ANCHOR, SWAP_TO_GLOWSTONE, CHARGE_ANCHOR, SWAP_BACK_FROM_GLOW, EXPLODE_ANCHOR, SWAP_BACK, DONE
      };
   }

   public static AutoAnchorState getAutoAnchorStateByString(String text) {
      return Enum.valueOf(AutoAnchorState.class, text);
   }
}
