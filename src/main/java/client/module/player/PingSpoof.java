package client.module.player;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.Setting;
import client.setting.SliderSetting;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;

public class PingSpoof extends Module {
   private SliderSetting ping;
   private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(PingSpoof::getThreadByRunnable);

   public PingSpoof() {
      super("PingSpoof", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 0.0, 1.0, 1000.0, 10.0);
      slidersetting.setName("Пинг");
      slidersetting.setDescription("Желаемый пинг (мс)");
      this.ping = slidersetting;
      this.addSettings(new Setting[]{this.ping});
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof KeepAliveS2CPacket keepalives2cpacket) {
            if (this.networkHandler() != null) {
               packetEvent.setFlag(true);
               int j = this.ping.getInt2();
               long i = keepalives2cpacket.getId();
               scheduledExecutorService.schedule(() -> this.onLong(i), (long)j, TimeUnit.MILLISECONDS);
            }
         }
      }
   }

   private static Thread getThreadByRunnable(Runnable runnable) {
      Thread thread = new Thread(runnable, "l");
      thread.setDaemon(true);
      return thread;
   }

   private void onLong(long time) {
      try {
         if (!this.isEnabled()) {
            return;
         }

         ClientPlayNetworkHandler clientplaynetworkhandler = this.networkHandler();
         if (clientplaynetworkhandler == null) {
            return;
         }

         clientplaynetworkhandler.getConnection().send(new KeepAliveC2SPacket(time));
      } catch (Throwable throwable) {
      }
   }

   @Override
   public void onEnable() {
   }
}
