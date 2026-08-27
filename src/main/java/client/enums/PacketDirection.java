package client.enums;

public enum PacketDirection {
   SEND,
   RECEIVE;

   private static final PacketDirection[] packetDirectionArray = getPacketDirectionArray();

   private static PacketDirection[] getPacketDirectionArray() {
      return new PacketDirection[]{SEND, RECEIVE};
   }

   public static PacketDirection getPacketDirectionByString(String text) {
      return Enum.valueOf(PacketDirection.class, text);
   }
}
