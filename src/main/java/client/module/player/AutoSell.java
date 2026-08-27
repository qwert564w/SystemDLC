package client.module.player;

import client.enums.AutoSellState;
import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.InputSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.PriceParser;
import client.util.StringParts;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class AutoSell extends Module {
   private ListSetting server;
   private ListSetting modeProdazhi;
   private SliderSetting nacenka;
   private HotkeySetting bindProdazhi;
   private InputSetting cenaOdnoyProdazhi;
   private ListSetting vykladka;
   private BooleanSetting autoInventar;
   private ListSetting chtoProdavat;
   private AutoSellState autoSellState;
   private boolean flag;
   private long time;
   private String text;
   private int value235;
   private boolean flag2;
   private String text2;
   private String text3;
   private Item item;
   private int value236;
   private boolean flag3;
   private int value237;
   private int value238;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private boolean flag7;
   private int value239;
   private int value240;
   private int value241;
   private boolean flag8;
   private boolean flag9;
   private int value242;
   private int value243;
   private long time2;
   private boolean flag10;
   private boolean flag11;
   private String text4;
   private final Set<String> set;
   private String text5;
   private long time3;
   private long time4;
   private long time5;
   private boolean flag12;
   private boolean flag13;
   private int value244;
   private long time6;
   private long time7;
   private long time8;
   private long time9;
   private long time10;
   private static final long time11 = ThreadLocalRandom.current().nextLong();

   public AutoSell() {
      super("AutoSell", Category.PLAYER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(StringParts.join(new String[]{"Ф", "Т"}), StringParts.join(new String[]{"Х", "В"})),
         List.of(StringParts.join(new String[]{"Ф", "Т"})),
         false
      );
      listsetting.setName("Сервер");
      listsetting.setDescription("Под какой сервер работает продажа");
      this.server = listsetting;
      listsetting = new ListSetting(
         "",
         "",
         List.of(
            StringParts.join(new String[]{"П", "о", " ", "о", "д", "н", "о", "й", " ", "ш", "т", "у", "к", "е"}),
            StringParts.join(new String[]{"В", "с", "е", " ", "с", "р", "а", "з", "у"}),
            StringParts.join(new String[]{"О", "д", "н", "а", " ", "ц", "е", "н", "а"})
         ),
         List.of(StringParts.join(new String[]{"П", "о", " ", "о", "д", "н", "о", "й", " ", "ш", "т", "у", "к", "е"})),
         false
      );
      listsetting.setName("Режим продажи");
      listsetting.setDescription("Как выставлять предметы");
      this.modeProdazhi = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 10.0, 0.0, 100.0, 1.0, "%", 0);
      slidersetting.setName("Наценка");
      slidersetting.setDescription("Наценка к цене самого дешёвого конкурента");
      this.nacenka = slidersetting;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", -1, this::update20);
      hotkeysetting.setName("Бинд продажи");
      hotkeysetting.setDescription("Кнопка запуска продажи предмета в руке");
      this.bindProdazhi = hotkeysetting;
      InputSetting inputsetting = new InputSetting("", "", "1000", "1000");
      inputsetting.setName("Цена одной продажи");
      inputsetting.setDescription("Цена для режима одной цены (можно 10к, 1м, 50тыс)");
      this.cenaOdnoyProdazhi = inputsetting;
      listsetting = new ListSetting(
         "",
         "",
         List.of(
            StringParts.join(new String[]{"П", "о", " ", "о", "д", "н", "о", "й", " ", "ш", "т", "у", "к", "е"}),
            StringParts.join(new String[]{"В", "с", "е", " ", "с", "р", "а", "з", "у"})
         ),
         List.of(StringParts.join(new String[]{"П", "о", " ", "о", "д", "н", "о", "й", " ", "ш", "т", "у", "к", "е"})),
         false
      );
      listsetting.setName("Выкладка");
      listsetting.setDescription("Как раскладывать предметы в sellgui");
      this.vykladka = listsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Авто инвентарь");
      booleansetting.setDescription("По бинду распродать предметы один за другим без ручного выбора");
      this.autoInventar = booleansetting;
      listsetting = new ListSetting(
         "",
         "",
         List.of(
            StringParts.join(new String[]{"Т", "о", "л", "ь", "к", "о", " ", "х", "о", "т", "б", "а", "р"}),
            StringParts.join(new String[]{"Ф", "у", "л", "л", " ", "и", "н", "в", "е", "н", "т", "а", "р", "ь"})
         ),
         List.of(StringParts.join(new String[]{"Т", "о", "л", "ь", "к", "о", " ", "х", "о", "т", "б", "а", "р"})),
         false
      );
      listsetting.setName("Что продавать");
      listsetting.setDescription("Откуда брать предметы при авто-продаже");
      this.chtoProdavat = listsetting;
      this.autoSellState = AutoSellState.IDLE;
      this.flag = false;
      this.time = 0L;
      this.text = "";
      this.value235 = -1;
      this.flag2 = false;
      this.text2 = "";
      this.text3 = "";
      this.item = Items.AIR;
      this.value236 = 0;
      this.flag3 = false;
      this.value237 = 0;
      this.value238 = 0;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.flag7 = false;
      this.value239 = 0;
      this.value240 = 0;
      this.value241 = 9;
      this.flag8 = false;
      this.flag9 = false;
      this.value242 = 0;
      this.value243 = 0;
      this.time2 = 0L;
      this.flag10 = false;
      this.flag11 = false;
      this.text4 = "";
      this.set = new HashSet<>();
      this.text5 = null;
      this.time3 = 0L;
      this.time4 = 0L;
      this.time5 = 0L;
      this.flag12 = false;
      this.flag13 = false;
      this.value244 = 0;
      this.time6 = 0L;
      this.time7 = 0L;
      this.time8 = 0L;
      this.time9 = 0L;
      this.time10 = 0L;
      this.nacenka.setVisibleWhen(this::getBoolean2);
      this.cenaOdnoyProdazhi.setVisibleWhen(this::check8);
      this.vykladka.setVisibleWhen(this::check8);
      this.autoInventar.setVisibleWhen(this::getBoolean);
      this.chtoProdavat.setVisibleWhen(this::getBoolean3);
      this.addSettings(
         new Setting[]{
            this.server, this.modeProdazhi, this.nacenka, this.bindProdazhi, this.cenaOdnoyProdazhi, this.vykladka, this.autoInventar, this.chtoProdavat
         }
      );
   }

   private int getIntByGenericContainerScreen(GenericContainerScreen genericContainerScreen) {
      int i = this.getIntByGenericContainerScreen4(genericContainerScreen);

      for (int j = 0; j < i; j++) {
         Slot slot = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(j);
         if (slot.hasStack() && this.isItemStack(slot.getStack())) {
            return j;
         }
      }

      return -1;
   }

   private static int getIntByString(String text2) {
      if (text2 != null && !text2.isBlank()) {
         try {
            return Math.max(0, Integer.parseInt(text2));
         } catch (NumberFormatException numberformatexception) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private static int getIntByStringString(String text2, String text3) {
      int i = text2.indexOf(text3);
      if (i <= 0) {
         return 0;
      } else {
         String s = text2.substring(0, i).trim();
         String s1 = s.replaceFirst("^.*?([0-9]+)\\s*$", "$1");
         return s1.equals(s) && !s1.matches("[0-9]+") ? 0 : getIntByString(s1);
      }
   }

   private static boolean isString(String text2) {
      return !text2.contains("у вас купили") || !text2.contains("на /ah") && !text2.contains(" за ")
         ? text2.contains("купил у вас") && text2.contains(" за ") && (text2.contains("¤") || text2.contains("$"))
         : true;
   }

   private void setBoolean(boolean flag) {
      if (this.flag5) {
         this.update36();
      } else if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen && this.isGenericContainerScreen(genericcontainerscreen)) {
         this.onGenericContainerScreenBoolean(genericcontainerscreen, flag);
      } else if (this.flag4) {
         this.flag4 = false;
         this.update14();
         this.autoSellState = this.getAutoSellStateByBoolean(flag);
         this.time6 = getLong();
      } else {
         if (isLongLong2(this.time6, 12000L)) {
            this.update33();
            this.value238 = 0;
            this.autoSellState = this.getAutoSellStateByBoolean(flag);
            if (this.autoSellState == AutoSellState.IDLE && this.value239 <= 0) {
               this.update19();
            } else {
               this.time6 = getLong();
            }
         }
      }
   }

   private void update11() {
      long j = 700L;
      long i = this.time6;
      if (isLongLong(j, i)) {
         if (this.time > 0L) {
            this.update31();
         }

         this.autoSellState = AutoSellState.FINISHING;
         this.time6 = getLong();
         this.time7 = getLong();
      }
   }

   private Boolean getBoolean() {
      return !this.check8();
   }

   private static boolean isLongLong(long time, long time2) {
      double d0 = getDoubleByLong(time2 ^ time11);
      double d1 = d0 < 0.15 ? 3.0 + 4.0 * getDoubleByLong((time2 ^ time11) * 31L) : 1.0 + 1.5 * d0;
      return getLong() - time2 >= (long)(time * d1);
   }

   private static long getLong() {
      return System.currentTimeMillis();
   }

   private void onString2(String text2) {
      this.sendPrefixedMessage(text2);
   }

   private static String getStringByString(String text2) {
      return text2 == null ? "" : text2.replaceAll("§.", "").replace(' ', ' ').trim();
   }

   private boolean isGenericContainerScreen(GenericContainerScreen genericContainerScreen) {
      String[] astring = new String[]{"продажа", "sellgui", "sell gui"};
      return PriceParser.isStringArrayGenericContainerScreen(astring, genericContainerScreen);
   }

   private boolean check3() {
      if (this.flag2) {
         return true;
      } else {
         ItemStack itemstack = this.getItemStack();
         if (itemstack.isEmpty()) {
            return false;
         } else {
            this.flag2 = true;
            this.text2 = this.getStringByItemStack(itemstack);
            this.text3 = itemstack.getName().getString();
            this.item = itemstack.getItem();
            return true;
         }
      }
   }

   private void setString(String text2) {
      long i = getLong();
      if (i - this.time9 >= 3000L) {
         this.time9 = i;
         this.onString2(text2);
      }
   }

   private boolean check4() {
      if (this.player() == null) {
         return false;
      } else if (this.isItemStack(this.getItemStack())) {
         return true;
      } else {
         for (int i = 0; i < 36; i++) {
            if (this.isItemStack(this.inventory().getStack(i))) {
               return true;
            }
         }

         return false;
      }
   }

   private int getIntByGenericContainerScreen2(GenericContainerScreen genericContainerScreen) {
      int i = Math.min(9, this.getIntByGenericContainerScreen4(genericContainerScreen));

      for (int j = 0; j < i; j++) {
         if (!((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(j).hasStack()) {
            return j;
         }
      }

      return -1;
   }

   private void update12() {
      if (isLongLong2(this.time6, this.check10() ? 16000L : 10000L)) {
         this.onString2("§e[П] §fHet ответа от аукциона.");
         if (this.flag10) {
            this.set.add(this.text4);
         }

         this.update26();
      }
   }

   private boolean isGenericContainerScreen2(GenericContainerScreen genericContainerScreen) {
      if (this.player() == null) {
         return false;
      } else {
         String s = this.player().getName().getString().toLowerCase(Locale.ROOT);
         String[] astring = new String[]{s, "мои товары", "мои предметы", "поиск:"};
         return PriceParser.isStringArrayGenericContainerScreen(astring, genericContainerScreen);
      }
   }

   private Boolean getBoolean2() {
      return !this.check8();
   }

   private int getIntByGenericContainerScreen3(GenericContainerScreen genericContainerScreen) {
      int i = this.getIntByGenericContainerScreen4(genericContainerScreen);
      int j = -1;

      for (int k = i - 1; k >= 0; k--) {
         Slot slot = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getSlot(k);
         if (slot.hasStack()) {
            ItemStack itemstack = slot.getStack();
            if (itemstack.isOf(Items.LIME_DYE)
               || itemstack.isOf(Items.GREEN_DYE)
               || itemstack.isOf(Items.LIME_STAINED_GLASS_PANE)
               || itemstack.isOf(Items.GREEN_STAINED_GLASS_PANE)) {
               return k;
            }

            String s = getStringByString(itemstack.getName().getString()).toLowerCase(Locale.ROOT);
            if (s.contains("выстав") || s.contains("продать") || s.contains("подтверд")) {
               j = k;
            }
         }
      }

      return j;
   }

   private boolean isGenericContainerScreen3(GenericContainerScreen genericContainerScreen) {
      String[] astring = new String[]{"аукцион", "auction"};
      return PriceParser.isStringArrayGenericContainerScreen(astring, genericContainerScreen);
   }

   private boolean check5() {
      if (this.interactionManager() != null && this.player() != null) {
         if (this.isItemStack(this.getItemStack())) {
            return true;
         } else {
            for (int i = 0; i < 9; i++) {
               if (this.isItemStack(this.inventory().getStack(i))) {
                  this.inventory().selectedSlot = i;
                  this.time6 = getLong();
                  return true;
               }
            }

            int k = this.inventory().selectedSlot;

            for (int j = 9; j < 36; j++) {
               if (this.isItemStack(this.inventory().getStack(j))) {
                  this.interactionManager().clickSlot(this.player().playerScreenHandler.syncId, j, k, SlotActionType.SWAP, this.player());
                  this.time6 = getLong();
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private void update13() {
      if (this.flag5) {
         this.update36();
      } else {
         boolean flagx = this.check4();
         if (this.value240 > 0 && flagx) {
            if (this.value239 < this.value241) {
               this.update28();
            } else if (this.check9()) {
               this.update36();
            }
         } else if (this.value239 > 0) {
            if (flagx && this.value239 < this.value241) {
               this.update28();
            } else if (this.check9()) {
               this.update36();
            }
         } else if (flagx) {
            this.update28();
         } else {
            this.update19();
         }
      }
   }

   private void update14() {
      this.flag3 = false;
      this.value237 = 0;
      this.value238 = 0;
      this.flag6 = false;
      this.flag7 = false;
   }

   private boolean check6() {
      return "ХВ".equals(this.server.getString2());
   }

   private void update15() {
      if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen && this.isGenericContainerScreen2(genericcontainerscreen)) {
         this.autoSellState = AutoSellState.RESALE_TAKE_ITEM;
         this.time6 = getLong();
      } else if (isLongLong2(this.time7, 18000L)) {
         this.onString2("§c[П] §fTaйmayt поиска своих товаров.");
         this.update19();
      } else {
         if (this.currentScreen() != null) {
            long j = 900L;
            long i = this.time6;
            if (isLongLong(j, i)) {
               this.update33();
               this.autoSellState = AutoSellState.RESALE_SEARCH_OWN_AH;
               this.time2 = getLong() + getLongByLong(400L);
               this.time6 = getLong();
               return;
            }
         }

         long l = 2500L;
         long k = this.time6;
         if (isLongLong(l, k)) {
            this.autoSellState = AutoSellState.RESALE_SEARCH_OWN_AH;
            this.time2 = getLong() + getLongByLong(400L);
            this.time6 = getLong();
         }
      }
   }

   private void update16() {
      long j = 450L;
      long i = this.time6;
      if (isLongLong(j, i)) {
         if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
            if (!this.isGenericContainerScreen2(genericcontainerscreen)) {
               if (isLongLong2(this.time6, 10000L)) {
                  this.update33();
                  this.autoSellState = AutoSellState.RESALE_SEARCH_OWN_AH;
                  this.time6 = getLong();
               }
            } else {
               int k = this.getIntByGenericContainerScreen(genericcontainerscreen);
               if (k != -1) {
                  ScreenHandler screenhandler1 = genericcontainerscreen.getScreenHandler();
                  SlotActionType slotactiontype = SlotActionType.QUICK_MOVE;
                  byte b0 = 0;
                  ScreenHandler screenhandler = screenhandler1;
                  this.onSlotActionTypeIntIntScreenHandler(slotactiontype, b0, k, screenhandler);
                  this.value242 = 0;
                  this.time6 = getLong();
               } else if (this.value242++ < 2) {
                  this.time6 = getLong();
               } else {
                  this.update33();
                  this.flag5 = false;
                  if (!this.check4()) {
                     this.onString2("§e[П] §fCbou лоты не найдены, предметов в инвентаре нет.");
                     this.update19();
                  } else {
                     this.flag9 = false;
                     this.autoSellState = AutoSellState.RESALE_SELLING;
                     this.time6 = getLong();
                  }
               }
            }
         } else {
            this.autoSellState = AutoSellState.RESALE_SEARCH_OWN_AH;
            this.time6 = getLong();
         }
      }
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.interactionManager() != null && this.flag) {
         this.update30();
         if (this.check8() && this.autoSellState == AutoSellState.IDLE) {
            this.update13();
            if (!this.flag || this.autoSellState == AutoSellState.IDLE) {
               return;
            }
         }

         switch (this.autoSellState) {
            case SWEEP_NEXT:
               this.update25();
               break;
            case PREPARING:
               this.update18();
               break;
            case SPLITTING:
               this.update27();
               break;
            case SEARCHING:
               this.update24();
               break;
            case SCANNING:
               this.update29();
               break;
            case SELLING:
               this.update11();
               break;
            case FINISHING:
               this.update12();
               break;
            case SELLGUI_SELLING:
               this.setBoolean2(false);
               break;
            case SELLGUI_WAITING_RESULT:
               this.setBoolean(false);
               break;
            case RESALE_SEARCH_OWN_AH:
               this.update34();
               break;
            case RESALE_WAITING_OWN_AH:
               this.update15();
               break;
            case RESALE_TAKE_ITEM:
               this.update16();
               break;
            case RESALE_SELLING:
               this.setBoolean2(true);
               break;
            case RESALE_WAIT_SELL_RESULT:
               this.setBoolean(true);
               break;
            case HOLY_SELLING:
               this.update35();
               break;
            case HOLY_OPENING_AUCTION:
               this.update32();
         }
      }
   }

   private void update17() {
      this.time = 0L;
      this.text = "";
      this.value235 = -1;
      this.flag2 = false;
      this.text2 = "";
      this.text3 = "";
      this.item = Items.AIR;
      this.value236 = 0;
      this.flag12 = false;
      this.flag13 = false;
      this.value244 = 0;
   }

   private void update18() {
      if (this.check8()) {
         if (this.check6()) {
            this.onString2("§c[П] §fPeжum одной цены доступен только для ФТ.");
            this.update19();
            return;
         }

         if (this.currentScreen() != null) {
            long j = 450L;
            long i = this.time6;
            if (!isLongLong(j, i)) {
               return;
            }

            this.update33();
            this.time6 = getLong();
            return;
         }

         if (!this.check3() || !this.check4()) {
            this.update19();
            return;
         }

         this.autoSellState = AutoSellState.SELLGUI_SELLING;
         this.time6 = getLong();
      } else if (this.check6()) {
         if (this.currentScreen() != null) {
            long l = 450L;
            long k = this.time6;
            if (!isLongLong(l, k)) {
               return;
            }

            this.update33();
            this.time6 = getLong();
            return;
         }

         this.check3();
         if ((this.getItemStack().isEmpty() || !this.isItemStack(this.getItemStack())) && !this.check5()) {
            if (isLongLong2(this.time7, 8000L)) {
               this.update19();
            }

            return;
         }

         this.value236 = 0;
         this.autoSellState = AutoSellState.HOLY_SELLING;
         this.time6 = getLong();
         this.time7 = getLong();
      } else {
         if (this.getItemStack().isEmpty()) {
            this.update26();
            return;
         }

         this.autoSellState = "По одной штуке".equals(this.modeProdazhi.getString2()) ? AutoSellState.SPLITTING : AutoSellState.SEARCHING;
         this.time6 = getLong();
      }
   }

   private void update19() {
      this.update21();
   }

   @Override
   public void onDisable() {
      this.update21();
   }

   private void update20() {
      if (this.isEnabled() && !this.notInGame()) {
         if (this.flag) {
            if (this.flag10) {
               this.onString2("§3[П] §fAbto-продажа остановлена.");
               this.update19();
            }
         } else if (this.autoInventar.isFlag3() && !this.check8()) {
            this.update22();
         } else if (this.getItemStack().isEmpty()) {
            this.onString2("§3[П] §fВозьмите предмет в руку");
         } else {
            this.update23();
         }
      }
   }

   private boolean check7() {
      return "Фулл инвентарь".equals(this.chtoProdavat.getString2());
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof GameMessageS2CPacket gamemessages2cpacket) {
            GameMessageS2CPacket gamemessages2cpacket1 = gamemessages2cpacket;

            Text textx = gamemessages2cpacket1.content();
            gamemessages2cpacket1 = gamemessages2cpacket;

            boolean flagx = gamemessages2cpacket1.overlay();
            if (true && !flagx) {
               String s = getStringByString(textx.getString()).toLowerCase(Locale.ROOT);
               if (!isString(s)) {
                  if (isString2(s)) {
                     if (!this.flag) {
                        return;
                     }

                     if (this.check8()) {
                        int j = Math.max(1, this.value238);
                        this.value239 += j;
                        if (this.flag7) {
                           this.value241 = Math.max(1, Math.min(this.value241, this.value239));
                        }

                        this.value240 = Math.max(0, this.value240 - j);
                        this.value238 = 0;
                        this.flag7 = false;
                        this.flag5 = false;
                        this.flag4 = true;
                        this.time10 = getLong();
                     } else if (this.check6()) {
                        this.setString2("ah");
                        this.autoSellState = AutoSellState.HOLY_OPENING_AUCTION;
                        this.time6 = getLong();
                        this.time7 = getLong();
                     } else {
                        this.update26();
                     }

                     return;
                  }

                  if (s.contains("не удалось выставить") && s.contains("освободите хранилище")) {
                     if (!this.flag) {
                        return;
                     }

                     if (this.check8()) {
                        this.flag5 = true;
                        this.value238 = 0;
                        this.flag7 = false;
                     } else {
                        this.onString2("§c[П] Хранилище заполнено. Продажа приостановлена.");
                        this.update19();
                     }
                  }

                  return;
               }

               if (!this.check8() || !this.flag && this.value239 <= 0) {
                  if (this.flag && !this.flag10) {
                     this.update19();
                  }

                  return;
               }

               int i = getIntByString2(s);
               this.value239 = Math.max(0, this.value239 - i);
               this.value240 += i;
               this.time10 = getLong();
               if (this.flag) {
                  this.autoSellState = AutoSellState.IDLE;
                  this.time6 = getLong();
               }

               return;
            }
         }
      }
   }

   private int getInt() {
      int i = this.check7() ? 36 : 9;

      for (int j = 0; j < i; j++) {
         ItemStack itemstack = this.inventory().getStack(j);
         if (!itemstack.isEmpty() && !this.set.contains(getStringByString2(this.getStringByItemStack(itemstack)))) {
            return j;
         }
      }

      return -1;
   }

   private void update21() {
      this.autoSellState = AutoSellState.IDLE;
      this.flag = false;
      this.time = 0L;
      this.text = "";
      this.value235 = -1;
      this.flag2 = false;
      this.text2 = "";
      this.text3 = "";
      this.item = Items.AIR;
      this.value236 = 0;
      this.flag3 = false;
      this.value237 = 0;
      this.value238 = 0;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.flag7 = false;
      this.value239 = 0;
      this.value240 = 0;
      this.value241 = 9;
      this.flag8 = false;
      this.flag9 = false;
      this.value242 = 0;
      this.value243 = 0;
      this.time2 = 0L;
      this.time10 = 0L;
      this.flag10 = false;
      this.flag11 = false;
      this.text4 = "";
      this.set.clear();
      this.text5 = null;
      this.time3 = 0L;
      this.time4 = 0L;
      this.time5 = 0L;
      this.flag12 = false;
      this.flag13 = false;
      this.value244 = 0;
      this.time6 = getLong();
      this.time7 = getLong();
   }

   private void update22() {
      this.flag10 = true;
      this.set.clear();
      this.flag = true;
      this.autoSellState = AutoSellState.SWEEP_NEXT;
      this.time6 = 0L;
      this.time7 = getLong();
      this.onString2("§3[П] §fПродаю " + (this.check7() ? "инвентарь" : "хотбар") + "...");
   }

   @Override
   public void onEnable() {
      this.update21();
   }

   private void update23() {
      if (this.check8()) {
         if (this.check6()) {
            this.onString2("§c[П] §fPeжum одной цены доступен только для ФТ.");
            return;
         }

         if (this.getLong2() <= 0L) {
            this.onString2("§c[П] §fЦeha одной продажи не задана.");
            return;
         }

         if (!this.check3()) {
            return;
         }

         this.value239 = 0;
         this.value240 = 0;
         this.value241 = 9;
         this.time10 = 0L;
         this.value238 = 0;
         this.flag7 = false;
      } else if (this.check6() && !this.check3()) {
         return;
      }

      this.flag = true;
      this.autoSellState = AutoSellState.PREPARING;
      this.time6 = getLong();
      this.time7 = getLong();
   }

   private void update24() {
      long j = 400L;
      long i = this.time6;
      if (isLongLong(j, i)) {
         ItemStack itemstack = this.getItemStack();
         if (itemstack.isEmpty()) {
            this.autoSellState = AutoSellState.PREPARING;
         } else if ("По одной штуке".equals(this.modeProdazhi.getString2()) && itemstack.getCount() > 1) {
            this.autoSellState = AutoSellState.SPLITTING;
         } else {
            String s = this.getStringByItemStack(itemstack);
            if (s.isEmpty()) {
               this.onString2("§3[П] Не удалось определить имя предмета");
               if (this.flag10) {
                  this.set.add(this.text4);
               }

               this.update26();
            } else {
               this.text = s;
               if (this.currentScreen() != null) {
                  if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen) {
                     this.value235 = ((GenericContainerScreenHandler)genericcontainerscreen.getScreenHandler()).syncId;
                  }

                  this.update33();
                  this.time6 = getLong();
               } else {
                  this.setString2("ah search " + this.text);
                  this.autoSellState = AutoSellState.SCANNING;
                  this.time6 = getLong();
                  this.time7 = getLong();
               }
            }
         }
      }
   }

   private void update25() {
      long k = 1500L;
      long j = this.time6;
      if (isLongLong(k, j)) {
         if (this.currentScreen() != null) {
            this.update33();
            this.time6 = getLong();
         } else {
            int i = this.getInt();
            if (i == -1) {
               if (!this.flag11) {
                  this.flag11 = true;
                  this.time6 = getLong();
               } else if (isLongLong2(this.time6, 600L)) {
                  this.onString2("§3[П] §fГotobo: продавать больше нечего.");
                  this.update19();
               }
            } else {
               this.flag11 = false;
               if (this.interactionManager() != null && this.player() != null) {
                  ItemStack itemstack = this.inventory().getStack(i);
                  this.text4 = getStringByString2(this.getStringByItemStack(itemstack));
                  if (i < 9) {
                     this.inventory().selectedSlot = i;
                  } else {
                     this.interactionManager()
                        .clickSlot(this.player().playerScreenHandler.syncId, i, this.inventory().selectedSlot, SlotActionType.SWAP, this.player());
                  }

                  this.update17();
                  this.autoSellState = AutoSellState.PREPARING;
                  this.time6 = getLong();
                  this.time7 = getLong();
               } else {
                  this.update19();
               }
            }
         }
      }
   }

   private void update26() {
      if (this.flag10) {
         this.autoSellState = AutoSellState.SWEEP_NEXT;
         this.time6 = getLong();
      } else {
         this.update19();
      }
   }

   private void update27() {
      long i1 = 260L;
      long l = this.time6;
      if (isLongLong(i1, l)) {
         if (this.player() != null && this.interactionManager() != null) {
            ItemStack itemstack = this.getItemStack();
            int i = this.player().playerScreenHandler.syncId;
            int j = this.inventory().selectedSlot + 36;
            boolean flagx = this.player().playerScreenHandler.getCursorStack().isEmpty();
            switch (this.value244) {
               case 0:
                  if (itemstack.isEmpty()) {
                     this.update19();
                     return;
                  }

                  if (itemstack.getCount() == 1) {
                     this.autoSellState = AutoSellState.SEARCHING;
                  } else {
                     if (this.getInt2() == -1) {
                        this.onString2("§3[П] Нет места для стака");
                        this.update19();
                        return;
                     }

                     this.interactionManager().clickSlot(i, j, 0, SlotActionType.PICKUP, this.player());
                     this.value244 = 1;
                  }
                  break;
               case 1:
                  if (flagx) {
                     this.value244 = 0;
                  } else {
                     this.interactionManager().clickSlot(i, j, 1, SlotActionType.PICKUP, this.player());
                     this.value244 = 2;
                  }
                  break;
               case 2:
                  if (flagx) {
                     this.value244 = 0;
                     this.autoSellState = AutoSellState.SEARCHING;
                  } else {
                     int k = this.getInt2();
                     if (k == -1) {
                        this.onString2("§3[П] Нет места для стака");
                        this.update19();
                        return;
                     }

                     this.interactionManager().clickSlot(i, k, 0, SlotActionType.PICKUP, this.player());
                     this.value244 = 0;
                     this.autoSellState = AutoSellState.SEARCHING;
                  }
                  break;
               default:
                  this.value244 = 0;
            }

            this.time6 = getLong();
         } else {
            this.update19();
         }
      }
   }

   private boolean check8() {
      return "Одна цена".equals(this.modeProdazhi.getString2());
   }

   private void update28() {
      this.autoSellState = AutoSellState.SELLGUI_SELLING;
      this.time6 = getLong();
   }

   private void setString2(String text2) {
      if (text2 != null && !text2.isEmpty()) {
         if (!text2.equals(this.text5)) {
            this.text5 = text2;
            this.time3 = getLong();
            this.time4 = getLongByString(text2);
         }
      }
   }

   private static String getStringByString2(String text2) {
      return getStringByString(text2).toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", "");
   }

   private long getLong2() {
      String s = this.cenaOdnoyProdazhi.getText();
      if (s == null) {
         return 0L;
      } else {
         String s1 = s.toLowerCase(Locale.ROOT).replace(" ", "").replace("_", "").replace(",", "").replace(".", "");
         long i = 1L;
         if (s1.endsWith("тысяч")) {
            i = 1000L;
            s1 = s1.substring(0, s1.length() - 5);
         } else if (s1.endsWith("тысячи") || s1.endsWith("тысяча")) {
            i = 1000L;
            s1 = s1.substring(0, s1.length() - 6);
         } else if (s1.endsWith("тыс")) {
            i = 1000L;
            s1 = s1.substring(0, s1.length() - 3);
         } else if (s1.endsWith("k") || s1.endsWith("к")) {
            i = 1000L;
            s1 = s1.substring(0, s1.length() - 1);
         } else if (s1.endsWith("m") || s1.endsWith("м")) {
            i = 1000000L;
            s1 = s1.substring(0, s1.length() - 1);
         }

         String s2 = s1.replaceAll("[^0-9]", "");
         if (s2.isEmpty()) {
            return 0L;
         } else {
            try {
               return Math.multiplyExact(Long.parseLong(s2), i);
            } catch (ArithmeticException | NumberFormatException numberformatexception) {
               return 0L;
            }
         }
      }
   }

   private ItemStack getItemStack() {
      return this.player() != null ? this.player().getMainHandStack() : ItemStack.EMPTY;
   }

   private void update29() {
      if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen && this.isGenericContainerScreen4(genericcontainerscreen)) {
         long j = 900L;
         long i = this.time6;
         if (isLongLong(j, i)) {
            try {
               this.addGenericContainerScreen(genericcontainerscreen);
            } catch (Exception exception) {
               this.update19();
            }
         }
      } else {
         if (this.check10() && !this.flag12) {
            long l = 2500L;
            long k = this.time7;
            if (isLongLong(l, k)) {
               this.flag12 = true;
               if (this.currentScreen() != null) {
                  this.update33();
               } else {
                  this.setString2("ah search " + this.text);
               }

               this.time7 = getLong();
            }
         }

         if (isLongLong2(this.time6, this.check10() ? 14000L : 4000L)) {
            this.onString2("§e[П] §fAykцuoh не ответил на поиск.");
            if (this.flag10) {
               this.set.add(this.text4);
            }

            this.update26();
         }
      }
   }

   private static boolean isLongLong2(long time, long time2) {
      return getLong() - time >= time2;
   }

   private static double getDoubleByLong(long time) {
      long i = time * -7046029254386353131L;
      i ^= i >>> 33;
      return (i & 16777215L) / 1.6777216E7;
   }

   private Boolean getBoolean3() {
      return this.autoInventar.isFlag3() && !this.check8();
   }

   private static long getLongByLong(long time) {
      return (long)(time * (0.8 + 0.9 * getDouble()));
   }

   private static long getLongByString(String text2) {
      long i = 550L + text2.length() * 55L;
      return (long)(i * (0.8 + 0.5 * getDouble()));
   }

   private static double getDouble() {
      return ThreadLocalRandom.current().nextDouble();
   }

   private void addGenericContainerScreen(GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericcontainerscreenhandler = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      ArrayList<Double> arraylist = new ArrayList();
      Item itemx = this.getItemStack().getItem();
      String s = getStringByString2(this.text);
      int i = Math.min(45, genericcontainerscreenhandler.slots.size());

      for (int j = 0; j < i; j++) {
         Slot slot = genericcontainerscreenhandler.getSlot(j);
         if (slot.hasStack()) {
            ItemStack itemstack = slot.getStack();
            if (itemstack.getItem() == itemx) {
               String s1 = getStringByString2(this.getStringByItemStack(itemstack));
               if (s.isEmpty() || s1.isEmpty() || s1.contains(s) || s.contains(s1)) {
                  long k = PriceParser.getLongByItemStack(itemstack);
                  if (k > 0L) {
                     arraylist.add((double)k / Math.max(1, itemstack.getCount()));
                  }
               }
            }
         }
      }

      int i1 = Math.max(1, this.getItemStack().getCount());
      this.update33();
      if (arraylist.isEmpty()) {
         if (this.flag10) {
            this.onString2("§3[П] §fKohkypehtob нет — пропускаю предмет.");
            this.set.add(this.text4);
         } else {
            this.onString2("§3[П] Конкурентов нет, ставьте вручную");
         }

         this.update26();
      } else {
         arraylist.sort(null);
         double d1 = (Double)arraylist.getFirst();
         double d2 = d1;
         boolean flagx = false;

         for (double d0 : arraylist) {
            if (!(d0 <= d1 * 1.001)) {
               if (d0 >= d1 * 2.0) {
                  d2 = (d1 + d0) / 2.0;
                  flagx = true;
               }
               break;
            }
         }

         long j1 = "Все сразу".equals(this.modeProdazhi.getString2()) ? (long)(d2 * i1) : (long)d2;
         long l = (long)(j1 * (1.0 + this.nacenka.getValue() / 100.0));
         this.time = Math.max(1L, l);
         this.onString2(
            "§3[П] §fKohkypeht: §b"
               + PriceParser.getStringByLong((long)d2)
               + "$§f/шт"
               + (flagx ? " §7(срез демпинга: мин " + PriceParser.getStringByLong((long)d1) + "$)" : "")
               + "§f, выставляю за §b"
               + PriceParser.getStringByLong(this.time)
               + "$"
         );
         this.autoSellState = AutoSellState.SELLING;
         this.time6 = getLong();
      }
   }

   private boolean isItemStack(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && this.flag2) {
         if (this.item != Items.AIR && itemStack.getItem() != this.item) {
            return false;
         } else {
            String s = getStringByString2(this.getStringByItemStack(itemStack));
            String s1 = getStringByString2(this.text2);
            String s2 = getStringByString2(this.text3);
            if (s1.isEmpty() && s2.isEmpty()) {
               return true;
            } else {
               return s.isEmpty()
                  ? false
                  : s.equals(s1) || s.equals(s2) || !s1.isEmpty() && (s.contains(s1) || s1.contains(s)) || !s2.isEmpty() && (s.contains(s2) || s2.contains(s));
            }
         }
      } else {
         return false;
      }
   }

   private int getIntByBoolean(boolean flag) {
      if (flag) {
         return Integer.MAX_VALUE;
      } else {
         int i = Math.max(1, this.value241 - this.value239);
         if (this.value240 > 0) {
            return Math.max(1, Math.min(this.value240, i));
         } else {
            return this.value239 > 0 ? i : Integer.MAX_VALUE;
         }
      }
   }

   private int getInt2() {
      for (int i = 9; i < 36; i++) {
         if (this.inventory().getStack(i).isEmpty()) {
            return i;
         }
      }

      int k = this.inventory().selectedSlot + 36;

      for (int j = 36; j < 45; j++) {
         if (j != k && this.inventory().getStack(j - 36).isEmpty()) {
            return j;
         }
      }

      return -1;
   }

   private int getIntByGenericContainerScreen4(GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericcontainerscreenhandler = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      return Math.max(0, Math.min(genericcontainerscreenhandler.getRows() * 9, genericcontainerscreenhandler.slots.size()));
   }

   private void onGenericContainerScreenBoolean(GenericContainerScreen genericContainerScreen, boolean flag) {
      ScreenHandler screenhandler = genericContainerScreen.getScreenHandler();
      int i = this.getIntByGenericContainerScreen4(genericContainerScreen);
      if (this.flag3) {
         long k1 = 900L;
         long j1 = this.time6;
         if (isLongLong(k1, j1)) {
            this.update33();
         }
      } else {
         int j = 0;

         for (int k = this.getIntByBoolean(flag); j < 1 && this.value237 < k; j++) {
            int l = this.getIntByGenericContainerScreen2(genericContainerScreen);
            int i1 = this.getIntByScreenHandlerInt(screenhandler, i);
            if (l == -1) {
               this.flag6 = true;
               break;
            }

            if (i1 == -1) {
               break;
            }

            if ("По одной штуке".equals(this.vykladka.getString2())) {
               if (!this.isScreenHandlerIntInt(screenhandler, l, i1)) {
                  break;
               }
            } else {
               SlotActionType slotactiontype = SlotActionType.PICKUP;
               byte b0 = 0;
               this.onSlotActionTypeIntIntScreenHandler(slotactiontype, b0, i1, screenhandler);
               SlotActionType slotactiontype1 = SlotActionType.PICKUP;
               byte b1 = 0;
               this.onSlotActionTypeIntIntScreenHandler(slotactiontype1, b1, l, screenhandler);
            }

            this.value237++;
         }

         if (j > 0) {
            this.time6 = getLong();
         } else if (this.value237 > 0) {
            int l2 = this.getIntByGenericContainerScreen3(genericContainerScreen);
            if (l2 != -1) {
               SlotActionType slotactiontype2 = SlotActionType.PICKUP;
               byte b2 = 0;
               this.onSlotActionTypeIntIntScreenHandler(slotactiontype2, b2, l2, screenhandler);
               this.flag3 = true;
               this.value238 = this.value237;
               this.flag7 = this.flag6;
               if (this.flag6) {
                  this.value241 = Math.max(1, this.value237);
               }

               this.time6 = getLong();
            } else {
               long i2 = 1500L;
               long l1 = this.time6;
               if (isLongLong(i2, l1)) {
                  this.update33();
                  this.autoSellState = flag ? AutoSellState.RESALE_SELLING : AutoSellState.SELLGUI_SELLING;
                  this.time6 = getLong();
               }
            }
         } else {
            long k2 = 450L;
            long j2 = this.time6;
            if (!isLongLong(k2, j2)) {
               return;
            }

            this.update33();
            if (this.flag6 && this.value239 > 0) {
               this.value241 = Math.max(1, Math.min(this.value241, this.value239));
               this.setString("§e[П] §fB sellgui нет свободных слотов, жду перевыставление.");
            }

            this.autoSellState = flag ? AutoSellState.RESALE_SEARCH_OWN_AH : AutoSellState.IDLE;
            if (this.autoSellState == AutoSellState.IDLE && this.value239 <= 0) {
               this.update19();
            } else {
               this.time6 = getLong();
            }
         }
      }
   }

   private String getStringByItemStack(ItemStack itemStack) {
      String s = itemStack.getName().getString();
      if (s.contains("TIER WHITE")) {
         return "вайт";
      } else if (s.contains("TIER BLACK")) {
         return "блэк";
      } else if (s.contains("Рассадник монстров")) {
         return "Спавнер";
      } else if (s.contains("Прогрузчик чанков [1x1]")) {
         return "Прогрузчик чанков";
      } else if (s.contains("Яйцо призыва зомби-крестьянина")) {
         return "Яйцо зомби-крестьянина";
      } else {
         String s1 = s.replaceAll("(?i)§.", "")
            .replaceAll("(?i)&.", "")
            .replace(' ', ' ')
            .replaceAll("\\[[^\\]]*]", " ")
            .replaceAll("[★✦✧✪✫✬✭✮✯✰❄☃⚒☠❤❣♕♛♜♞♟\ud83c\udf79]", " ")
            .replace("xxx", " ")
            .replaceAll("\\s+", " ")
            .trim();
         if (s1.isEmpty()) {
            s1 = getStringByString(itemStack.getItem().getName().getString());
         }

         return s1;
      }
   }

   private static boolean isString2(String text2) {
      return text2.contains("выстав") && text2.contains("продаж");
   }

   private static int getIntByString2(String text2) {
      String s = text2;
      int i = text2.indexOf(" за ");
      if (i > 0) {
         s = text2.substring(0, i);
      }

      String s1 = "x";
      int j = getIntByStringString2(s1, s);
      if (j > 0) {
         return j;
      } else {
         String s2 = "х";
         j = getIntByStringString2(s2, s);
         if (j > 0) {
            return j;
         } else {
            j = getIntByStringString(s, "предмет");
            if (j > 0) {
               return j;
            } else {
               j = getIntByStringString(s, "шт");
               return j > 0 ? j : 1;
            }
         }
      }
   }

   private static int getIntByStringString2(String text2, String text3) {
      int i = text3.indexOf(text2);
      if (i < 0) {
         return 0;
      } else {
         String s = text3.substring(i + text2.length()).replaceFirst("[^0-9]*", "").replaceFirst("[^0-9].*$", "");
         return getIntByString(s);
      }
   }

   private boolean check9() {
      return getLong() - this.time10 >= 5000L;
   }

   private void setBoolean2(boolean flag) {
      long k = 350L;
      long j = this.time6;
      if (isLongLong(k, j)) {
         if (this.flag5) {
            this.update36();
         } else {
            long i = this.getLong2();
            if (i <= 0L) {
               this.onString2("§c[П] §fЦeha одной продажи не задана.");
               this.update19();
            } else {
               if (this.check3() && this.check4()) {
                  if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen && this.isGenericContainerScreen(genericcontainerscreen)) {
                     this.onGenericContainerScreenBoolean(genericcontainerscreen, flag);
                  } else if (this.currentScreen() != null) {
                     this.update33();
                     this.time6 = getLong();
                  } else if (getLong() - this.time8 >= 5000L) {
                     this.update14();
                     this.flag4 = false;
                     this.setString2("ah sellgui " + i);
                     this.time8 = getLong();
                     this.autoSellState = flag ? AutoSellState.RESALE_WAIT_SELL_RESULT : AutoSellState.SELLGUI_WAITING_RESULT;
                     this.time6 = getLong();
                     this.time7 = getLong();
                  }
               } else {
                  this.autoSellState = AutoSellState.IDLE;
                  this.time6 = getLong();
               }
            }
         }
      }
   }

   private boolean isScreenHandlerIntInt(ScreenHandler screenHandler, int count, int count2) {
      if (this.interactionManager() != null && this.player() != null) {
         this.interactionManager().clickSlot(screenHandler.syncId, count2, 0, SlotActionType.PICKUP, this.player());
         if (screenHandler.getCursorStack().isEmpty()) {
            return false;
         } else {
            this.interactionManager().clickSlot(screenHandler.syncId, count, 1, SlotActionType.PICKUP, this.player());
            if (!screenHandler.getCursorStack().isEmpty()) {
               this.interactionManager().clickSlot(screenHandler.syncId, count2, 0, SlotActionType.PICKUP, this.player());
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean isGenericContainerScreen4(GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericcontainerscreenhandler = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      if (genericcontainerscreenhandler.syncId == this.value235) {
         return false;
      } else {
         String[] astring = new String[]{"аукцион", "auction", "поиск"};
         if (PriceParser.isStringArrayGenericContainerScreen(astring, genericContainerScreen)) {
            return true;
         } else {
            int i = Math.min(45, genericcontainerscreenhandler.slots.size());

            for (int j = 0; j < i; j++) {
               Slot slot = genericcontainerscreenhandler.getSlot(j);
               if (slot.hasStack() && PriceParser.getLongByItemStack(slot.getStack()) > 0L) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private void onSlotActionTypeIntIntScreenHandler(SlotActionType slotActionType, int count, int count2, ScreenHandler screenHandler) {
      if (this.interactionManager() != null && this.player() != null) {
         this.interactionManager().clickSlot(screenHandler.syncId, count2, count, slotActionType, this.player());
      }
   }

   private AutoSellState getAutoSellStateByBoolean(boolean flag) {
      if (this.value239 > 0) {
         return AutoSellState.IDLE;
      } else if (this.check4()) {
         return flag ? AutoSellState.RESALE_SELLING : AutoSellState.SELLGUI_SELLING;
      } else {
         return AutoSellState.IDLE;
      }
   }

   private int getIntByScreenHandlerInt(ScreenHandler screenHandler, int count) {
      for (int i = count; i < screenHandler.slots.size(); i++) {
         Slot slot = screenHandler.getSlot(i);
         if (slot.hasStack() && this.isItemStack(slot.getStack())) {
            return i;
         }
      }

      return -1;
   }

   private void update30() {
      if (this.text5 != null) {
         long i = getLong();
         if (i >= this.time5) {
            if (i - this.time3 >= this.time4) {
               if (this.networkHandler() == null) {
                  this.text5 = null;
               } else {
                  this.networkHandler().sendChatCommand(this.text5);
                  this.text5 = null;
                  this.time5 = i + (long)(1400.0 * (0.8 + 0.9 * getDouble()));
               }
            }
         }
      }
   }

   private void update31() {
      if (this.time > 0L) {
         this.setString2("ah sell " + this.time);
      }
   }

   private void update32() {
      if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen && this.isGenericContainerScreen3(genericcontainerscreen)) {
         long j = 450L;
         long i = this.time6;
         if (!isLongLong(j, i)) {
            return;
         }

         if (this.flag10) {
            this.update33();
         }

         this.update26();
      } else {
         if (!this.flag13) {
            long l = 2500L;
            long k = this.time6;
            if (isLongLong(l, k)) {
               this.flag13 = true;
               this.setString2("ah");
               this.time6 = getLong();
               return;
            }
         }

         if (isLongLong2(this.time6, 10000L)) {
            this.update26();
         }
      }
   }

   private void update33() {
      if (this.clientPlayer() != null) {
         this.clientPlayer().closeHandledScreen();
      }
   }

   private void update34() {
      long i = getLong();
      if (i >= this.time2) {
         if (this.currentScreen() != null) {
            this.update33();
            this.time2 = i + getLongByLong(400L);
         } else if (!this.flag9 && !this.flag8 && this.check4()) {
            this.autoSellState = AutoSellState.RESALE_SELLING;
            this.time6 = getLong();
         } else {
            this.flag8 = false;
            if (this.player() == null) {
               this.update19();
            } else if (this.value243 >= 3) {
               this.onString2("§e[П] §fCbou лоты не открываются, останавливаюсь.");
               this.update19();
            } else {
               this.value243++;
               this.setString2("ah " + this.player().getName().getString().trim());
               this.value242 = 0;
               this.autoSellState = AutoSellState.RESALE_WAITING_OWN_AH;
               this.time6 = getLong();
               this.time7 = getLong();
            }
         }
      }
   }

   private void update35() {
      if (isLongLong2(this.time7, 9000L)) {
         this.update19();
      } else {
         ItemStack itemstack = this.getItemStack();
         if (!itemstack.isEmpty() && this.isItemStack(itemstack)) {
            if (this.value236 == 0) {
               if (this.currentScreen() != null) {
                  long j = 450L;
                  long i = this.time6;
                  if (!isLongLong(j, i)) {
                     return;
                  }

                  this.update33();
                  this.time6 = getLong();
                  return;
               }

               this.setString2("ah sell auto");
               this.value236 = 1;
               this.time6 = getLong();
            } else if (this.value236 == 1) {
               long l = 1000L;
               long k = this.time6;
               if (isLongLong(l, k)) {
                  this.setString2("ah sell auto confirm");
                  this.value236 = 2;
                  this.time6 = getLong();
               }
            }
         } else {
            this.value236 = 0;
            this.autoSellState = AutoSellState.PREPARING;
            this.time6 = getLong();
         }
      }
   }

   private void update36() {
      this.flag5 = false;
      this.flag4 = false;
      this.flag8 = true;
      this.flag9 = true;
      this.value242 = 0;
      this.value239 = 0;
      this.value240 = 0;
      this.value243 = 0;
      this.time2 = getLong() + getLongByLong(400L);
      this.update14();
      this.autoSellState = AutoSellState.RESALE_SEARCH_OWN_AH;
      this.time6 = getLong();
      this.time7 = getLong();
      this.update33();
   }

   private boolean check10() {
      return !this.check6();
   }
}
