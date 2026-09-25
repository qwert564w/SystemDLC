package client.enums;

public enum GuiTab {
   MODULES,
   CONFIGS,
   FRIENDS,
   FAVORITES,
   WAYPOINTS,
   ACCOUNTS;

   private static final GuiTab[] guiTabArray = getGuiTabArray();

   private static GuiTab[] getGuiTabArray() {
      return new GuiTab[]{MODULES, CONFIGS, FRIENDS, FAVORITES, WAYPOINTS, ACCOUNTS};
   }

   public static GuiTab getGuiTabByString(String text) {
      return Enum.valueOf(GuiTab.class, text);
   }
}
