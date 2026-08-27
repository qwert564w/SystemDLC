package client.enums;

public enum AutoSellState {
   IDLE,
   SWEEP_NEXT,
   PREPARING,
   SPLITTING,
   SEARCHING,
   SCANNING,
   SELLING,
   FINISHING,
   SELLGUI_SELLING,
   SELLGUI_WAITING_RESULT,
   RESALE_SEARCH_OWN_AH,
   RESALE_WAITING_OWN_AH,
   RESALE_TAKE_ITEM,
   RESALE_SELLING,
   RESALE_WAIT_SELL_RESULT,
   HOLY_SELLING,
   HOLY_OPENING_AUCTION;

   private static final AutoSellState[] autoSellStateArray = getAutoSellStateArray();

   private static AutoSellState[] getAutoSellStateArray() {
      return new AutoSellState[]{
         IDLE,
         SWEEP_NEXT,
         PREPARING,
         SPLITTING,
         SEARCHING,
         SCANNING,
         SELLING,
         FINISHING,
         SELLGUI_SELLING,
         SELLGUI_WAITING_RESULT,
         RESALE_SEARCH_OWN_AH,
         RESALE_WAITING_OWN_AH,
         RESALE_TAKE_ITEM,
         RESALE_SELLING,
         RESALE_WAIT_SELL_RESULT,
         HOLY_SELLING,
         HOLY_OPENING_AUCTION
      };
   }

   public static AutoSellState getAutoSellStateByString(String text) {
      return Enum.valueOf(AutoSellState.class, text);
   }
}
