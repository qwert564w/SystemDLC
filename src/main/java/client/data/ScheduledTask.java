package client.data;

public record ScheduledTask(int delay, boolean atTickStart, Runnable action) {
   public boolean isAtTickStart() {
      return this.atTickStart;
   }

   public Runnable getAction() {
      return this.action;
   }

   public int getDelay() {
      return this.delay;
   }
}
