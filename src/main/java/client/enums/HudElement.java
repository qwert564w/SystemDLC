package client.enums;

public enum HudElement {
   LOGO,
   FPS,
   PING,
   TPS,
   BPS;

   private static final HudElement[] hudElementArray = getHudElementArray();

   private static HudElement[] getHudElementArray() {
      return new HudElement[]{LOGO, FPS, PING, TPS, BPS};
   }

   public static HudElement getHudElementByString(String text) {
      return Enum.valueOf(HudElement.class, text);
   }
}
