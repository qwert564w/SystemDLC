package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.util.DisplayInfo;

public class FrameSync extends Module {
   private static FrameSync INSTANCE;
   private long time;
   private long time2;
   private long time3;

   public FrameSync() {
      super("FrameSync", Category.VISUAL);
      INSTANCE = this;
      this.update11();
   }

   @Override
   public void onDisable() {
   }

   private void update11() {
      this.time = 1000000000L / DisplayInfo.getInt();
   }

   public static FrameSync getInstance() {
      return INSTANCE;
   }

   public boolean check3() {
      if (!this.isEnabled()) {
         return false;
      } else {
         long i = System.nanoTime();
         long j = i - this.time2;
         this.time3 += j;
         if (this.time3 < this.time) {
            if (j > this.time * 3L) {
               this.time2 = i;
               this.time3 = 0L;
               return false;
            } else {
               return true;
            }
         } else {
            this.time3 = this.time3 - this.time;
            if (this.time3 > this.time * 2L) {
               this.time3 = this.time;
            }

            this.time2 = i;
            return false;
         }
      }
   }

   @Override
   public void onEnable() {
      this.update11();
      this.time2 = System.nanoTime();
      this.time3 = 0L;
   }
}
