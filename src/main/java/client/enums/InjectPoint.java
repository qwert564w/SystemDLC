package client.enums;

public enum InjectPoint {
   HEAD,
   TAIL,
   REPLACE,
   CANCELLABLE;

   private static final InjectPoint[] injectPointArray = getInjectPointArray();

   private static InjectPoint[] getInjectPointArray() {
      return new InjectPoint[]{HEAD, TAIL, REPLACE, CANCELLABLE};
   }

   public static InjectPoint getInjectPointByString(String text) {
      return Enum.valueOf(InjectPoint.class, text);
   }
}
