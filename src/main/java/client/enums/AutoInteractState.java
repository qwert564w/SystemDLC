package client.enums;

public enum AutoInteractState {
   IDLE,
   SWAP_IN,
   SELECT,
   HOLD,
   RELEASE,
   POST_COOLDOWN;

   private static final AutoInteractState[] autoInteractStateArray = getAutoInteractStateArray();

   private static AutoInteractState[] getAutoInteractStateArray() {
      return new AutoInteractState[]{IDLE, SWAP_IN, SELECT, HOLD, RELEASE, POST_COOLDOWN};
   }

   public static AutoInteractState getAutoInteractStateByString(String text) {
      return Enum.valueOf(AutoInteractState.class, text);
   }
}
