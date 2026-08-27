package client.enums;

public enum ServerMode {
   OFF,
   FUNTIME,
   MINEBLAZE;

   private static final ServerMode[] serverModeArray = getServerModeArray();

   private static ServerMode[] getServerModeArray() {
      return new ServerMode[]{OFF, FUNTIME, MINEBLAZE};
   }

   public static ServerMode getServerModeByString(String text) {
      return Enum.valueOf(ServerMode.class, text);
   }
}
