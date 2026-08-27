package client.data;

public record CharTiming(char ch, long timestamp, int x) {
   public long getTimestamp() {
      return this.timestamp;
   }

   public int getX() {
      return this.x;
   }

   public char getCh() {
      return this.ch;
   }
}
