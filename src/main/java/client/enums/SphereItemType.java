package client.enums;

public enum SphereItemType {
   VALUABLE,
   UNKNOWN,
   SACRIFICIAL,
   PLAIN;

   private static final SphereItemType[] sphereItemTypeArray = getSphereItemTypeArray();

   private static SphereItemType[] getSphereItemTypeArray() {
      return new SphereItemType[]{VALUABLE, UNKNOWN, SACRIFICIAL, PLAIN};
   }

   public static SphereItemType getSphereItemTypeByString(String text) {
      return Enum.valueOf(SphereItemType.class, text);
   }
}
