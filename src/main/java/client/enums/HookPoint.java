package client.enums;

public enum HookPoint {
   HEAD,
   TAIL,
   REPLACE,
   HEAD_CANCELLABLE;

   private static final HookPoint[] hookPointArray = getHookPointArray();

   private static HookPoint[] getHookPointArray() {
      return new HookPoint[]{HEAD, TAIL, REPLACE, HEAD_CANCELLABLE};
   }

   public static HookPoint getHookPointByString(String text) {
      return Enum.valueOf(HookPoint.class, text);
   }
}
