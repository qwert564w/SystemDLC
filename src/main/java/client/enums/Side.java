package client.enums;

public enum Side {
   ABOVE,
   BELOW,
   LEFT,
   RIGHT;

   private static final Side[] sideArray = getSideArray();

   private static Side[] getSideArray() {
      return new Side[]{ABOVE, BELOW, LEFT, RIGHT};
   }

   public static Side getSideByString(String text) {
      return Enum.valueOf(Side.class, text);
   }
}
