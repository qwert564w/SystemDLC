package client.util;

public class PlaceCooldown {
   private boolean flag;
   private int value;
   private long time;

   public void update() {
      this.value = 0;
      this.time = 0L;
   }

   public void setLong(long time2) {
      this.time = System.currentTimeMillis() + time2;
   }

   public void update2() {
      if (!this.flag && this.value > 0) {
         this.value--;
      }
   }

   public boolean check() {
      return this.flag ? System.currentTimeMillis() >= this.time : this.value <= 0;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public void setInt(int count) {
      this.value = Math.max(count, 1);
   }
}
