package client.module;

import client.enums.PacketDirection;
import client.network.PacketEvent;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.SliderSetting;
import client.util.FakePlayerCopy;
import client.util.InteractEvent;
import client.util.StringParts;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.RecipeBookDataC2SPacket;
import net.minecraft.network.packet.c2s.play.RecipeCategoryOptionsC2SPacket;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.network.packet.c2s.play.SpectatorTeleportC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;

public abstract class CategoryModule extends Module {
   protected final SliderSetting delay;
   protected final BooleanSetting pokazatServernuyuPoziciyu;
   private final Queue<Packet<?>> queue;
   private long time;
   private long time2;
   private boolean flag;
   protected final FakePlayerCopy fakePlayerCopy;

   protected CategoryModule(String text, Category category) {
      super(text, category);
      SliderSetting slidersetting = new SliderSetting("", "", 300.0, 50.0, 1000.0, 10.0, StringParts.join(new String[]{"м", "c"}), 0);
      slidersetting.setName("Задержка");
      slidersetting.setDescription("Заморозка пакетов (мс, рандом 0..2x)");
      this.delay = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показать серверную позицию");
      booleansetting.setDescription("Отображает вашу модельку на сервере");
      this.pokazatServernuyuPoziciyu = booleansetting;
      this.queue = new ConcurrentLinkedQueue<>();
      this.time = 0L;
      this.time2 = 300L;
      this.flag = false;
      this.fakePlayerCopy = new FakePlayerCopy();
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         boolean flagx = this.check3();
         if (this.flag && !flagx) {
            this.update11();
            this.update12();
         }

         this.flag = flagx;
         if (!flagx) {
            this.fakePlayerCopy.update();
         } else {
            long i = System.currentTimeMillis();
            if (i - this.time >= this.time2) {
               this.update11();
               this.time = i;
               this.time2 = this.getLong();
            }
         }
      }
   }

// This client has been infected LOL by collapse

   protected void update11() {
      while (!this.queue.isEmpty()) {
         Packet packet = this.queue.poll();
         if (packet != null) {
            PacketEvent.onPacket2(packet);
         }
      }

      this.fakePlayerCopy.update();
   }

   @Override
   public void onDisable() {
      this.update11();
      this.fakePlayerCopy.update();
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()
         && this.pokazatServernuyuPoziciyu.isFlag3()
         && this.fakePlayerCopy.check()
         && !this.client().options.getPerspective().isFirstPerson()) {
         this.fakePlayerCopy.render(worldRenderContext);
      }
   }

   private boolean isPacket(Packet packet2) {
      if (packet2 instanceof PlayerInteractEntityC2SPacket
         || packet2 instanceof PlayerActionC2SPacket
         || packet2 instanceof PlayerInteractBlockC2SPacket
         || packet2 instanceof PlayerInteractItemC2SPacket
         || packet2 instanceof HandSwingC2SPacket) {
         this.update11();
         return true;
      } else if (packet2 instanceof ClickSlotC2SPacket
         || packet2 instanceof CloseHandledScreenC2SPacket
         || packet2 instanceof ButtonClickC2SPacket
         || packet2 instanceof CreativeInventoryActionC2SPacket
         || packet2 instanceof SelectMerchantTradeC2SPacket
         || packet2 instanceof RenameItemC2SPacket) {
         this.update11();
         return true;
      } else if (packet2 instanceof ChatMessageC2SPacket || packet2 instanceof CommandExecutionC2SPacket) {
         this.update11();
         return true;
      } else if (packet2 instanceof BookUpdateC2SPacket || packet2 instanceof UpdateSignC2SPacket) {
         this.update11();
         return true;
      } else if (packet2 instanceof ClientStatusC2SPacket || packet2 instanceof UpdatePlayerAbilitiesC2SPacket) {
         this.update11();
         return true;
      } else if (packet2 instanceof SpectatorTeleportC2SPacket || packet2 instanceof TeleportConfirmC2SPacket) {
         this.update11();
         return true;
      } else if (!(packet2 instanceof CraftRequestC2SPacket) && !(packet2 instanceof RecipeBookDataC2SPacket) && !(packet2 instanceof RecipeCategoryOptionsC2SPacket)) {
         return false;
      } else {
         this.update11();
         return true;
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (!this.notInGame() && this.check3()) {
         if (packetEvent.getPacketDirection() == PacketDirection.SEND) {
            Packet packet = packetEvent.getPacket();
            if (this.isPacket(packet)) {
               return;
            }

            if (!this.fakePlayerCopy.check() && packet instanceof PlayerMoveC2SPacket) {
               this.fakePlayerCopy.setAbstractClientPlayerEntity(this.clientPlayer());
            }

            this.queue.add(packet);
            packetEvent.setFlag(true);
         }
      }
   }

   @Override
   public void onInteractEvent(InteractEvent interactEvent) {
      this.update11();
   }

   private long getLong() {
      long i = this.delay.getValueAsLong() * 2L;
      return i <= 0L ? 0L : ThreadLocalRandom.current().nextLong(i + 1L);
   }

   protected void update12() {
   }

   protected abstract boolean check3();

   @Override
   public void update4() {
      this.queue.clear();
      this.fakePlayerCopy.update();
   }

   @Override
   public void onEnable() {
      this.queue.clear();
      this.time = System.currentTimeMillis();
      this.time2 = this.getLong();
      this.flag = false;
      this.fakePlayerCopy.update();
   }
}
