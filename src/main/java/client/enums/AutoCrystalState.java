package client.enums;

public enum AutoCrystalState {
   IDLE,
   SWAP_TO_OBSIDIAN,
   PLACE_OBSIDIAN,
   VERIFY_OBSIDIAN,
   SWAP_TO_CRYSTAL,
   PLACE_CRYSTAL,
   WAIT_CRYSTAL,
   ATTACK_CRYSTAL,
   SWAP_BACK,
   DONE;

   private static final AutoCrystalState[] autoCrystalStateArray = getAutoCrystalStateArray();

   private static AutoCrystalState[] getAutoCrystalStateArray() {
      return new AutoCrystalState[]{
         IDLE, SWAP_TO_OBSIDIAN, PLACE_OBSIDIAN, VERIFY_OBSIDIAN, SWAP_TO_CRYSTAL, PLACE_CRYSTAL, WAIT_CRYSTAL, ATTACK_CRYSTAL, SWAP_BACK, DONE
      };
   }

   public static AutoCrystalState getAutoCrystalStateByString(String text) {
      return Enum.valueOf(AutoCrystalState.class, text);
   }
}
