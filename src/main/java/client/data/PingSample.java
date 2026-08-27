package client.data;

public class PingSample {
   public int value;
   public int value2;
   public long time;
   public long time2;

   public PingSample(int count) {
      this.value2 = count;
      this.value = count;
   }

   public void onIntLong(int count, long time3) {
      if (count != this.value) {
         long i = count < this.value ? 20L : 10L;
         this.time2 = time3 + i;
         this.value = count;
         this.time = time3;
      }

      if (time3 - this.time > 20L) {
         this.value2 = count;
      }
   }

   public boolean isLong(long time) {
      return this.time2 > time && (this.time2 - time) % 6L >= 3L;
   }
}
