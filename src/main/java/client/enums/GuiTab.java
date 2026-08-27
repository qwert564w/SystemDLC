package client.enums;

public enum GuiTab {
   MODULES,
   CONFIGS,
   FRIENDS,
   FAVORITES,
   WAYPOINTS;

   private static final GuiTab[] guiTabArray = getGuiTabArray();

   private static GuiTab[] getGuiTabArray() {
      return new GuiTab[]{MODULES, CONFIGS, FRIENDS, FAVORITES, WAYPOINTS};
   }

   public static GuiTab getGuiTabByString(String text) {
      return Enum.valueOf(GuiTab.class, text);
   }
}
