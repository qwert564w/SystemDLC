package client.util;

import client.enums.PacketDirection;
import client.network.PacketEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

public class TpsTracker {
   private static final TpsTracker INSTANCE = new TpsTracker();
   private final ArrayDeque<Float> arrayDeque = new ArrayDeque<>(20);
   private long time;
   private float value = 20.0F;

   public static float getFloatByDouble(double value) {
      BigDecimal bigdecimal = new BigDecimal(value);
      bigdecimal = bigdecimal.setScale(2, RoundingMode.HALF_UP);
      return bigdecimal.floatValue();
   }

   public void addPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE && packetEvent.getPacket() instanceof WorldTimeUpdateS2CPacket) {
         long i = System.currentTimeMillis();
         if (this.time != 0L) {
            long j = i - this.time;
            long k = Math.clamp(j, 250L, 10000L);
            if (this.arrayDeque.size() >= 20) {
               this.arrayDeque.poll();
            }

            this.arrayDeque.add(20.0F * (1000.0F / (float)k));
            float f = 0.0F;

            for (Float f1 : this.arrayDeque) {
               float f5 = f1;
               float f4 = 20.0F;
               float f3 = 0.0F;
               float f2 = f5;
               f += RandomUtil.getFloatByFloatFloatFloat(f3, f4, f2);
            }

            if (!this.arrayDeque.isEmpty()) {
               this.value = f / this.arrayDeque.size();
            }
         }

         this.time = i;
      }
   }

   public static TpsTracker getInstance() {
      return INSTANCE;
   }

   public float getFloat() {
      float f2 = 20.0F;
      float f1 = 0.0F;
      float f = this.value;
      return getFloatByDouble(RandomUtil.getFloatByFloatFloatFloat(f1, f2, f));
   }
}
