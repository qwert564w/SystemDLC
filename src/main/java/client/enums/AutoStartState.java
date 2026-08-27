package client.enums;

public enum AutoStartState {
   IDLE,
   ROTATE_TO_BLOCK,
   PLACE_RAIL,
   WAIT_RAIL,
   PLACE_CART,
   WAIT_CART,
   SWAP_TO_BOW,
   ROTATE_TO_CART,
   START_BOW_CHARGE,
   BOW_CHARGING,
   RELEASE_BOW,
   SF_SWAP_TO_BOW,
   SF_ROTATE_TO_TARGET,
   SF_START_BOW_CHARGE,
   SF_BOW_CHARGING,
   SF_RELEASE_BOW,
   SF_WAIT_AFTER_SHOT,
   SF_ROTATE_TO_BLOCK,
   SF_PLACE_RAIL,
   SF_WAIT_RAIL,
   SF_PLACE_CART,
   RESTORE_SLOT,
   DONE;

   private static final AutoStartState[] autoStartStateArray = getAutoStartStateArray();

   private static AutoStartState[] getAutoStartStateArray() {
      return new AutoStartState[]{
         IDLE,
         ROTATE_TO_BLOCK,
         PLACE_RAIL,
         WAIT_RAIL,
         PLACE_CART,
         WAIT_CART,
         SWAP_TO_BOW,
         ROTATE_TO_CART,
         START_BOW_CHARGE,
         BOW_CHARGING,
         RELEASE_BOW,
         SF_SWAP_TO_BOW,
         SF_ROTATE_TO_TARGET,
         SF_START_BOW_CHARGE,
         SF_BOW_CHARGING,
         SF_RELEASE_BOW,
         SF_WAIT_AFTER_SHOT,
         SF_ROTATE_TO_BLOCK,
         SF_PLACE_RAIL,
         SF_WAIT_RAIL,
         SF_PLACE_CART,
         RESTORE_SLOT,
         DONE
      };
   }

   public static AutoStartState getAutoStartStateByString(String text) {
      return Enum.valueOf(AutoStartState.class, text);
   }
}
