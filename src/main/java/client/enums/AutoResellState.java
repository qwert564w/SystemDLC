package client.enums;

public enum AutoResellState {
   WAITING,
   OPENING_MAIN_AH,
   CLICKING_STORAGE,
   OPENING_STORAGE,
   CLICKING_CLOCK,
   WAITING_RESULT,
   COOLDOWN_WAIT;

   private static final AutoResellState[] autoResellStateArray = getAutoResellStateArray();

   private static AutoResellState[] getAutoResellStateArray() {
      return new AutoResellState[]{WAITING, OPENING_MAIN_AH, CLICKING_STORAGE, OPENING_STORAGE, CLICKING_CLOCK, WAITING_RESULT, COOLDOWN_WAIT};
   }

   public static AutoResellState getAutoResellStateByString(String text) {
      return Enum.valueOf(AutoResellState.class, text);
   }
}
