package client.enums;

public enum ServerFlag {
   NONE,
   FT,
   HW,
   ANY;

   private static final ServerFlag[] serverFlagArray = getServerFlagArray();

   private static ServerFlag[] getServerFlagArray() {
      return new ServerFlag[]{NONE, FT, HW, ANY};
   }

   public static ServerFlag getServerFlagByString(String text) {
      return Enum.valueOf(ServerFlag.class, text);
   }
}
