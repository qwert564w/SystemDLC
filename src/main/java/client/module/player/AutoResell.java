package client.module.player;

import client.enums.AutoResellState;
import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.PriceParser;
import client.util.StringParts;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class AutoResell extends Module {
   private static final Pattern pattern = Pattern.compile("Подождите (\\d+) сек");
   private SliderSetting interval;
   private AutoResellState autoResellState;
   private long time;
   private long time2;
   private long time3;
   private long time4;

   public AutoResell() {
      super("AutoResell", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 60.0, 15.0, 300.0, 5.0, StringParts.join(new String[]{" ", "c", "е", "к"}), 0);
      slidersetting.setName("Интервал");
      slidersetting.setDescription("Пауза между циклами перевыставления");
      this.interval = slidersetting;
      this.autoResellState = AutoResellState.WAITING;
      this.time = 0L;
      this.time2 = 0L;
      this.time3 = 0L;
      this.time4 = 0L;
      this.addSettings(new Setting[]{this.interval});
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.interactionManager() != null) {
         switch (this.autoResellState) {
            case WAITING:
               long i1 = this.time;
               long j = (long)this.interval.getValue() * 1000L;
               long i = i1;
               if (isLongLong(j, i)) {
                  this.update11();
               }
               break;
            case OPENING_MAIN_AH:
               this.update12();
               break;
            case CLICKING_STORAGE:
               this.update13();
               break;
            case OPENING_STORAGE:
               this.update15();
               break;
            case CLICKING_CLOCK:
               this.update18();
               break;
            case WAITING_RESULT:
               this.update16();
               break;
            case COOLDOWN_WAIT:
               long l = this.time4;
               long k = this.time;
               if (isLongLong(l, k)) {
                  this.onString2("§d[Р] §fПовторная попытка после ожидания...");
                  this.update11();
               }
         }
      }
   }

   private void update11() {
      if (this.check3()) {
         this.setAutoResellState(AutoResellState.CLICKING_CLOCK);
      } else if (this.check4()) {
         this.setAutoResellState(AutoResellState.CLICKING_STORAGE);
      } else {
         this.update17();
         this.setAutoResellState(AutoResellState.OPENING_MAIN_AH);
      }
   }

   private boolean check3() {
      if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
         String[] astring = new String[]{"хранилище"};
         if (PriceParser.isStringArrayGenericContainerScreen(astring, genericcontainerscreen)) {
            return true;
         }
      }

      return false;
   }

   private void update12() {
      if (this.check4()) {
         long j = 200L;
         long i = this.time2;
         if (isLongLong(j, i)) {
            this.setAutoResellState(AutoResellState.CLICKING_STORAGE);
         }
      } else {
         long l = 900L;
         long k = this.time3;
         if (isLongLong(l, k)) {
            this.update17();
            this.time3 = getLong();
         }

         long j1 = 9000L;
         long i1 = this.time2;
         if (isLongLong(j1, i1)) {
            this.onString2("§c[Р] §fMehю аукциона не открылось.");
            this.update14();
         }
      }
   }

   @Override
   public void onDisable() {
      this.update14();
   }

   private void update13() {
      if (this.check4() && this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
         Item item = Items.ENDER_CHEST;
         if (this.isItemGenericContainerScreen(item, genericcontainerscreen)) {
            this.setAutoResellState(AutoResellState.OPENING_STORAGE);
         } else {
            long j = 3000L;
            long i = this.time2;
            if (isLongLong(j, i)) {
               this.onString2("§c[Р] §fKhoпka 'Хранилище' не найдена.");
               this.update14();
            }
         }
      } else {
         this.update14();
      }
   }

   private boolean check4() {
      if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
         String[] astring = new String[]{"аукцион", "auction"};
         if (PriceParser.isStringArrayGenericContainerScreen(astring, genericcontainerscreen)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isLongLong(long time, long time2) {
      return getLong() - time2 >= time;
   }

   private boolean isItemGenericContainerScreen(Item item2, GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericcontainerscreenhandler = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      int i = genericcontainerscreenhandler.slots.size() - 36;

      for (Slot slot : genericcontainerscreenhandler.slots) {
         if (slot.id < i && slot.hasStack() && slot.getStack().getItem() == item2) {
            this.interactionManager().clickSlot(genericcontainerscreenhandler.syncId, slot.id, 0, SlotActionType.PICKUP, this.player());
            return true;
         }
      }

      return false;
   }

   private void onString2(String text2) {
      this.sendPrefixedMessage(text2);
   }

   private static long getLong() {
      return System.currentTimeMillis();
   }

   private void setAutoResellState(AutoResellState autoResellState2) {
      this.autoResellState = autoResellState2;
      this.time2 = getLong();
      this.time3 = getLong();
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gamemessages2cpacket) {
            GameMessageS2CPacket gamemessages2cpacket1 = gamemessages2cpacket;

            Text text = gamemessages2cpacket1.content();
            gamemessages2cpacket1 = gamemessages2cpacket;

            boolean flag = gamemessages2cpacket1.overlay();
            if (true && !flag) {
               String s = text.getString();
               if (!s.contains("Предметы успешно перевыставлены") && (!s.contains("[✔]") || !s.contains("перевыставлены"))) {
                  if (s.contains("В хранилище отсутствуют предметы для перевыставления")) {
                     this.onString2("§e[Р] §fХранилище пустое.");
                     this.update14();
                     return;
                  }

                  if (this.autoResellState != AutoResellState.WAITING && s.contains("Подождите") && s.contains("сек")) {
                     Matcher matcher = pattern.matcher(s);
                     if (!matcher.find()) {
                        return;
                     }

                     try {
                        int i = Integer.parseInt(matcher.group(1));
                        this.onString2("§e[Р] §fЖдем " + i + " сек (кулдаун)...");
                        this.time4 = (i + 1) * 1000L;
                        this.autoResellState = AutoResellState.COOLDOWN_WAIT;
                        this.time = getLong();
                     } catch (NumberFormatException numberformatexception) {
                     }
                  }

                  return;
               }

               this.onString2("§a[Р] §fГotobo.");
               this.update14();
               return;
            }
         }
      }
   }

   private void update14() {
      this.autoResellState = AutoResellState.WAITING;
      this.time = getLong();
      this.time2 = getLong();
      this.time3 = getLong();
      this.time4 = 0L;
   }

   private void update15() {
      if (this.check3()) {
         long j = 200L;
         long i = this.time2;
         if (isLongLong(j, i)) {
            this.setAutoResellState(AutoResellState.CLICKING_CLOCK);
         }
      } else {
         if (this.check4() && this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
            long l = 900L;
            long k = this.time3;
            if (isLongLong(l, k)) {
               Item item = Items.ENDER_CHEST;
               this.isItemGenericContainerScreen(item, genericcontainerscreen);
               this.time3 = getLong();
            }
         }

         long j1 = 9000L;
         long i1 = this.time2;
         if (isLongLong(j1, i1)) {
            this.onString2("§c[Р] §fХранилище не открылось.");
            this.update14();
         }
      }
   }

   @Override
   public void onEnable() {
      this.update14();
   }

   private void update16() {
      if (this.check3() && this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
         long j = 900L;
         long i = this.time3;
         if (isLongLong(j, i)) {
            Item item = Items.CLOCK;
            this.isItemGenericContainerScreen(item, genericcontainerscreen);
            this.time3 = getLong();
         }
      }

      long l = 12000L;
      long k = this.time2;
      if (isLongLong(l, k)) {
         this.onString2("§e[Р] §fHet ответа от аукциона.");
         this.update14();
      }
   }

   private void update17() {
      if (this.networkHandler() != null) {
         this.networkHandler().sendChatCommand("ah");
      }
   }

   private void update18() {
      if (this.check3() && this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
         Item item = Items.CLOCK;
         if (this.isItemGenericContainerScreen(item, genericcontainerscreen)) {
            this.onString2("§d[Р] §fПеревыставляем предметы...");
            this.setAutoResellState(AutoResellState.WAITING_RESULT);
         } else {
            long j = 3500L;
            long i = this.time2;
            if (isLongLong(j, i)) {
               this.update14();
            }
         }
      } else {
         this.update14();
      }
   }
}
