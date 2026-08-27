package client.enums;

public enum SwapJumpState {
   IDLE,
   LOCK_IN,
   SWAP_IN,
   PRESS_JUMP_PREP,
   PRESS_JUMP,
   RELEASE_JUMP;

   private static final SwapJumpState[] swapJumpStateArray = getSwapJumpStateArray();

   private static SwapJumpState[] getSwapJumpStateArray() {
      return new SwapJumpState[]{IDLE, LOCK_IN, SWAP_IN, PRESS_JUMP_PREP, PRESS_JUMP, RELEASE_JUMP};
   }

   public static SwapJumpState getSwapJumpStateByString(String text) {
      return Enum.valueOf(SwapJumpState.class, text);
   }
}
