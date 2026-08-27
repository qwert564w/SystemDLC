package client.enums;

public enum EventState {
   ACTIVE,
   PENDING,
   DONE,
   UNKNOWN;

   private static final EventState[] eventStateArray = getEventStateArray();

   private static EventState[] getEventStateArray() {
      return new EventState[]{ACTIVE, PENDING, DONE, UNKNOWN};
   }

   public static EventState getEventStateByString(String text) {
      return Enum.valueOf(EventState.class, text);
   }
}
