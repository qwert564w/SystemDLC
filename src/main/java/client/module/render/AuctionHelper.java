package client.module.render;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.render.HudRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.FilterMenuSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.EnchantmentNames;
import client.util.PriceParser;
import client.util.StringParts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;

public class AuctionHelper extends Module {
   private ListSetting podsvetka;
   private BooleanSetting cenaZa1Item;
   private BooleanSetting onlyHilkiRega;
   private BooleanSetting onlyStrengthSpeed;
   private BooleanSetting onlyPickaxeCBuldozerom;
   private BooleanSetting onlyBulavaCCharami;
   private ColorSetting deshevyyItem;
   private ColorSetting vygodnyyItem;
   private FilterMenuSetting filtryBroni;
   private SliderSetting minProchnost;
   private FilterMenuSetting filtryMecha;
   private Slot slot;
   private Slot slot2;
   private boolean flag;
   private GenericContainerScreen genericContainerScreen;
   private final Map<Integer, Integer> map;
   private int value235;
   private int value236;

   public AuctionHelper() {
      super("AuctionHelper", Category.RENDER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(StringParts.join(new String[]{"C", "т", "а", "т", "и", "к"}), StringParts.join(new String[]{"М", "и", "г", "а", "т", "ь"})),
         List.of(StringParts.join(new String[]{"М", "и", "г", "а", "т", "ь"})),
         false
      );
      listsetting.setName("Подсветка");
      listsetting.setDescription("Способ отрисовки подсветки");
      this.podsvetka = listsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Цена за 1 предмет");
      booleansetting.setDescription("Показывать цену за 1 штуку в слотах c количеством > 1");
      this.cenaZa1Item = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Только хилки + рега");
      booleansetting1.setDescription("Среди питьевых зелий исцеления подсвечивать только связку Исцеление II + Регенерация");
      this.onlyHilkiRega = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("Только сила + скорость");
      booleansetting2.setDescription("Среди питьевых зелий силы подсвечивать только связку Сила III + Скорость III");
      this.onlyStrengthSpeed = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", false);
      booleansetting3.setName("Только кирка c Бульдозером");
      booleansetting3.setDescription("Подсвечивать только кирки c Бульдозером и Эффективностью V+");
      this.onlyPickaxeCBuldozerom = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", false);
      booleansetting4.setName("Только булава c чарами");
      booleansetting4.setDescription("Подсвечивать только булавы c Остротой VII и Пробитием III");
      this.onlyBulavaCCharami = booleansetting4;
      ColorSetting colorsetting = new ColorSetting("", "", -11796661);
      colorsetting.setName("Дешевый предмет");
      colorsetting.setDescription("Цвет подсветки для предмета c наименьшей общей ценой");
      this.deshevyyItem = colorsetting;
      ColorSetting colorsetting1 = new ColorSetting("", "", -11830273);
      colorsetting1.setName("Выгодный предмет");
      colorsetting1.setDescription("Цвет подсветки для предмета c наименьшей ценой за 1 шт");
      this.vygodnyyItem = colorsetting1;
      FilterMenuSetting filtermenusetting = new FilterMenuSetting(
         "",
         "",
         StringParts.join(new String[]{"O", "т", "к", "р", "ы", "т", "ь", " ", "н", "a", "с", "т", "р", "о", "й", "к", "и"}),
         EnchantmentNames.getList2(),
         List.of(
            StringParts.join(new String[]{"З", "a", "щ", "и", "т", "а", " ", "V"}),
            StringParts.join(new String[]{"П", "р", "o", "ч", "н", "о", "с", "т", "ь", " ", "V"}),
            StringParts.join(new String[]{"П", "o", "ч", "и", "н", "к", "а"})
         ),
         List.of(StringParts.join(new String[]{"Ш", "и", "п", "ы"}))
      );
      filtermenusetting.setName("Фильтры брони");
      filtermenusetting.setDescription("Подсвечивать только броню c нужными чарами без лишних");
      this.filtryBroni = filtermenusetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.0, 0.0, 100.0, 1.0);
      slidersetting.setName("Мин. прочность, %");
      slidersetting.setDescription("Фильтровать предметы c прочностью ниже указанного процента (0 — выкл.)");
      this.minProchnost = slidersetting;
      FilterMenuSetting filtermenusetting1 = new FilterMenuSetting(
         "",
         "",
         StringParts.join(new String[]{"O", "т", "к", "р", "ы", "т", "ь", " ", "н", "a", "с", "т", "р", "о", "й", "к", "и"}),
         EnchantmentNames.getList(),
         List.of(
            StringParts.join(new String[]{"O", "с", "т", "р", "о", "т", "а", " ", "V", "I", "I"}),
            StringParts.join(new String[]{"П", "р", "o", "ч", "н", "о", "с", "т", "ь", " ", "V"}),
            StringParts.join(new String[]{"З", "a", "г", "о", "в", "о", "р", " ", "o", "г", "н", "я", " ", "I", "I"})
         ),
         List.of(StringParts.join(new String[]{"O", "т", "д", "а", "ч", "а", " ", "I", "I"}))
      );
      filtermenusetting1.setName("Фильтры меча");
      filtermenusetting1.setDescription("Подсвечивать только мечи c нужными чарами без лишних");
      this.filtryMecha = filtermenusetting1;
      this.flag = false;
      this.genericContainerScreen = null;
      this.map = new HashMap<>();
      this.value235 = 0;
      this.value236 = 0;
      this.addSettings(
         new Setting[]{
            this.podsvetka,
            this.cenaZa1Item,
            this.onlyHilkiRega,
            this.onlyStrengthSpeed,
            this.onlyPickaxeCBuldozerom,
            this.onlyBulavaCCharami,
            this.deshevyyItem,
            this.vygodnyyItem,
            this.minProchnost,
            this.filtryBroni,
            this.filtryMecha
         }
      );
   }

   private static boolean isItemStack(ItemStack itemStack) {
      if (itemStack.getItem() != Items.POTION) {
         return false;
      } else {
         PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         if (potioncontentscomponent == null) {
            return false;
         } else {
            boolean flagx = false;
            boolean flag1 = false;
            if (potioncontentscomponent.potion().isPresent()) {
               for (StatusEffectInstance statuseffectinstance : ((Potion)((RegistryEntry)potioncontentscomponent.potion().get()).value()).getEffects()) {
                  if (statuseffectinstance.getEffectType() == StatusEffects.STRENGTH && statuseffectinstance.getAmplifier() >= 2) {
                     flagx = true;
                  }

                  if (statuseffectinstance.getEffectType() == StatusEffects.SPEED && statuseffectinstance.getAmplifier() >= 2) {
                     flag1 = true;
                  }
               }
            }

            for (StatusEffectInstance statuseffectinstance1 : potioncontentscomponent.customEffects()) {
               if (statuseffectinstance1.getEffectType() == StatusEffects.STRENGTH && statuseffectinstance1.getAmplifier() >= 2) {
                  flagx = true;
               }

               if (statuseffectinstance1.getEffectType() == StatusEffects.SPEED && statuseffectinstance1.getAmplifier() >= 2) {
                  flag1 = true;
               }
            }

            return flagx && flag1;
         }
      }
   }

   private boolean isItem(Item item2) {
      return item2 == Items.NETHERITE_HELMET
         || item2 == Items.DIAMOND_HELMET
         || item2 == Items.NETHERITE_CHESTPLATE
         || item2 == Items.DIAMOND_CHESTPLATE
         || item2 == Items.NETHERITE_LEGGINGS
         || item2 == Items.DIAMOND_LEGGINGS
         || item2 == Items.NETHERITE_BOOTS
         || item2 == Items.DIAMOND_BOOTS
         || item2 == Items.IRON_HELMET
         || item2 == Items.IRON_CHESTPLATE
         || item2 == Items.IRON_LEGGINGS
         || item2 == Items.IRON_BOOTS;
   }

   private boolean isItemStack2(ItemStack itemStack) {
      if (!isItemStack3(itemStack)) {
         return false;
      } else if (this.world() == null) {
         return false;
      } else {
         Registry registry = this.world().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
         RegistryKey registrykey = Enchantments.EFFICIENCY;
         return this.getIntByItemStackRegistryRegistryKey(itemStack, registry, registrykey) >= 5;
      }
   }

   private boolean isItem2(Item item2) {
      return item2 == Items.NETHERITE_SWORD
         || item2 == Items.DIAMOND_SWORD
         || item2 == Items.IRON_SWORD
         || item2 == Items.GOLDEN_SWORD
         || item2 == Items.STONE_SWORD
         || item2 == Items.WOODEN_SWORD;
   }

   private static boolean isItemStack3(ItemStack itemStack) {
      LoreComponent lorecomponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (lorecomponent == null) {
         return false;
      } else {
         for (Text text : lorecomponent.lines()) {
            if (text != null) {
               String s = Formatting.strip(text.getString());
               if (s != null && s.toLowerCase().contains("бульдозер")) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   private static boolean isItemStack4(ItemStack itemStack) {
      Item item = itemStack.getItem();
      return item == Items.GRAY_DYE || item == Items.LIGHT_GRAY_DYE || item == Items.BARRIER || item == Items.STRUCTURE_VOID;
   }

   private static boolean isItemStack5(ItemStack itemStack) {
      if (itemStack.getItem() != Items.POTION) {
         return false;
      } else {
         PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         if (potioncontentscomponent == null) {
            return false;
         } else {
            boolean flagx = false;
            boolean flag1 = false;
            if (potioncontentscomponent.potion().isPresent()) {
               for (StatusEffectInstance statuseffectinstance : ((Potion)((RegistryEntry)potioncontentscomponent.potion().get()).value()).getEffects()) {
                  if (statuseffectinstance.getEffectType() == StatusEffects.INSTANT_HEALTH && statuseffectinstance.getAmplifier() >= 1) {
                     flagx = true;
                  }

                  if (statuseffectinstance.getEffectType() == StatusEffects.REGENERATION) {
                     flag1 = true;
                  }
               }
            }

            for (StatusEffectInstance statuseffectinstance1 : potioncontentscomponent.customEffects()) {
               if (statuseffectinstance1.getEffectType() == StatusEffects.INSTANT_HEALTH && statuseffectinstance1.getAmplifier() >= 1) {
                  flagx = true;
               }

               if (statuseffectinstance1.getEffectType() == StatusEffects.REGENERATION) {
                  flag1 = true;
               }
            }

            return flagx && flag1;
         }
      }
   }

   private void addItemStack(ItemStack itemStack) {
      LoreComponent lorecomponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (lorecomponent != null) {
         for (Text text : lorecomponent.lines()) {
            if (text.getString().contains("Цена за 1 шт:")) {
               return;
            }
         }
      }

      int j = PriceParser.getIntByItemStack(itemStack);
      if (j > 0) {
         int k = itemStack.getCount();
         int i = j / k;
         String s = "§a$ §fЦeha за 1 шт: §a$" + PriceParser.getStringByLong(i);
         ArrayList arraylist = new ArrayList();
         boolean flagx = false;
         if (lorecomponent != null) {
            for (Text text1 : lorecomponent.lines()) {
               arraylist.add(text1);
               String s1 = text1.getString();
               if (!flagx && (s1.contains("Цена") || s1.contains("$") && s1.matches(".*\\$\\s*[\\d,.]+.*"))) {
                  arraylist.add(Text.literal(s));
                  flagx = true;
               }
            }
         }

         if (!flagx) {
            arraylist.add(Text.literal(s));
         }

         itemStack.set(DataComponentTypes.LORE, new LoreComponent(arraylist));
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (packetEvent.getPacket() instanceof ScreenHandlerSlotUpdateS2CPacket) {
            if (this.client().currentScreen instanceof GenericContainerScreen genericcontainerscreen) {
               if (this.isGenericContainerScreen(genericcontainerscreen)) {
                  this.flag = true;
               }
            }
         }
      }
   }

   private int getIntByItemStackRegistryRegistryKey(ItemStack itemStack, Registry registry2, RegistryKey registryKey) {
      RegistryEntry registryentry = (RegistryEntry)registry2.getEntry(registryKey.getValue()).orElse(null);
      return registryentry == null ? 0 : EnchantmentHelper.getLevel(registryentry, itemStack);
   }

   private int getIntByEnchantmentNames(EnchantmentNames enchantmentNames) {
      return Math.max(1, enchantmentNames.getValue());
   }

   private boolean isRegistryKeyItemStackRegistry(RegistryKey registryKey, ItemStack itemStack, Registry registry2) {
      RegistryEntry registryentry = (RegistryEntry)registry2.getEntry(registryKey.getValue()).orElse(null);
      if (registryentry == null) {
         return false;
      } else {
         try {
            return ((Enchantment)registryentry.value()).isAcceptableItem(itemStack);
         } catch (Throwable throwable) {
            return true;
         }
      }
   }

   private boolean isFilterMenuSettingItemStack(FilterMenuSetting filterMenuSetting, ItemStack itemStack) {
      if (this.world() == null) {
         return false;
      } else {
         Registry registry = this.world().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);

         for (String s : (Iterable<String>)(filterMenuSetting.getObyazatelnyeChary().getList4())) {
            EnchantmentNames enchantmentnames = EnchantmentNames.getEnchantmentNamesByString(s);
            if (enchantmentnames != null) {
               RegistryKey registrykey = enchantmentnames.getRegistryKey();
               if (this.isRegistryKeyItemStackRegistry(registrykey, itemStack, registry)) {
                  RegistryKey registrykey1 = enchantmentnames.getRegistryKey();
                  if (this.getIntByItemStackRegistryRegistryKey(itemStack, registry, registrykey1) < this.getIntByEnchantmentNames(enchantmentnames)) {
                     return false;
                  }
               }
            }
         }

         for (String s1 : (Iterable<String>)(filterMenuSetting.getIsklyuchennyeChary().getList4())) {
            EnchantmentNames enchantmentnames1 = EnchantmentNames.getEnchantmentNamesByString(s1);
            if (enchantmentnames1 != null) {
               RegistryKey registrykey2 = enchantmentnames1.getRegistryKey();
               if (this.isRegistryKeyItemStackRegistry(registrykey2, itemStack, registry)) {
                  RegistryKey registrykey3 = enchantmentnames1.getRegistryKey();
                  if (this.getIntByItemStackRegistryRegistryKey(itemStack, registry, registrykey3) >= this.getIntByEnchantmentNames(enchantmentnames1)) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private void update11() {
      this.slot = null;
      this.slot2 = null;
      this.flag = false;
      this.genericContainerScreen = null;
      this.map.clear();
      this.value235 = 0;
      this.value236 = 0;
   }

   private boolean isGenericContainerScreen(GenericContainerScreen genericContainerScreen) {
      String[] astring = new String[]{"аукцион", "auction", "маркет", "поиск", ":"};
      return PriceParser.isStringArrayGenericContainerScreen(astring, genericContainerScreen);
   }

   private int[] getIntArrayByGenericContainerScreen(GenericContainerScreen genericContainerScreen) {
      int i = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).getRows();
      int j = i * 18 + 17 + 96;
      return new int[]{(genericContainerScreen.width - 176) / 2, (genericContainerScreen.height - j) / 2};
   }

   private void onSlotIntDrawContextIntInt(Slot slot2, int count, DrawContext drawContext, int count2, int count3) {
      int i = count2 + slot2.x;
      int j = count3 + slot2.y;
      drawContext.fill(i, j, i + 16, j + 16, count);
   }

   private int getIntByInt(int count) {
      float f = (float)Math.abs(Math.sin(System.currentTimeMillis() / 3.0 * Math.PI / 180.0));
      int i = (int)((count >> 24 & 0xFF) * f);
      return i << 24 | count & 16777215;
   }

   private boolean isBooleanBooleanItemStack(boolean flag, boolean flag2, ItemStack itemStack) {
      int i = this.minProchnost.getInt2();
      if (i > 0 && itemStack.getMaxDamage() > 0 && getIntByItemStack(itemStack) < i) {
         return false;
      } else {
         if (flag && this.isItem(itemStack.getItem())) {
            FilterMenuSetting filtermenusetting = this.filtryBroni;
            if (!this.isFilterMenuSettingItemStack(filtermenusetting, itemStack)) {
               return false;
            }
         }

         if (flag2 && this.isItem2(itemStack.getItem())) {
            FilterMenuSetting filtermenusetting1 = this.filtryMecha;
            if (!this.isFilterMenuSettingItemStack(filtermenusetting1, itemStack)) {
               return false;
            }
         }

         if (this.onlyHilkiRega.isFlag3() && isItemStackRegistryEntry(itemStack, StatusEffects.INSTANT_HEALTH) && !isItemStack5(itemStack)) {
            return false;
         } else if (this.onlyStrengthSpeed.isFlag3() && isItemStackRegistryEntry(itemStack, StatusEffects.STRENGTH) && !isItemStack(itemStack)) {
            return false;
         } else {
            return this.onlyPickaxeCBuldozerom.isFlag3() && isItem3(itemStack.getItem()) && !this.isItemStack2(itemStack)
               ? false
               : !this.onlyBulavaCCharami.isFlag3() || itemStack.getItem() != Items.MACE || this.isItemStack6(itemStack);
         }
      }
   }

   private void setGenericContainerScreen(GenericContainerScreen genericContainerScreen) {
      if (this.player() == null) {
         this.update11();
      } else {
         DefaultedList<Slot> defaultedlist = ((GenericContainerScreenHandler)genericContainerScreen.getScreenHandler()).slots;
         this.map.clear();

         for (Slot slotx : defaultedlist) {
            if (this.isSlot(slotx)) {
               this.map.put(slotx.id, PriceParser.getIntByItemStack(slotx.getStack()));
            }
         }

         this.setList(defaultedlist);
         this.flag = false;
      }
   }

   private void setList(List<Slot> list) {
      boolean flagx = this.filtryBroni.getBooleanSetting().isFlag3();
      boolean flag1 = this.filtryMecha.getBooleanSetting().isFlag3();
      Slot slotx = null;
      int i = Integer.MAX_VALUE;
      Slot slot1 = null;
      double d0 = Double.MAX_VALUE;

      for (Slot slot2x : list) {
         if (this.isSlot(slot2x)) {
            int j = this.map.getOrDefault(slot2x.id, -1);
            if (j >= 0) {
               ItemStack itemstack = slot2x.getStack();
               if (this.isBooleanBooleanItemStack(flagx, flag1, itemstack)) {
                  if (j < i) {
                     i = j;
                     slotx = slot2x;
                  }

                  int k = slot2x.getStack().getCount();
                  double d1 = k > 0 ? (double)j / k : j;
                  if (d1 < d0) {
                     d0 = d1;
                     slot1 = slot2x;
                  }
               }
            }
         }
      }

      this.slot = slotx;
      this.slot2 = slot1;
   }

   private static boolean isItem3(Item item2) {
      return item2 == Items.NETHERITE_PICKAXE
         || item2 == Items.DIAMOND_PICKAXE
         || item2 == Items.IRON_PICKAXE
         || item2 == Items.GOLDEN_PICKAXE
         || item2 == Items.STONE_PICKAXE
         || item2 == Items.WOODEN_PICKAXE;
   }

   private boolean isItemStack6(ItemStack itemStack) {
      if (this.world() == null) {
         return false;
      } else {
         Registry registry = this.world().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
         RegistryKey registrykey = Enchantments.SHARPNESS;
         if (this.getIntByItemStackRegistryRegistryKey(itemStack, registry, registrykey) >= 7) {
            RegistryKey registrykey1 = Enchantments.BREACH;
            if (this.getIntByItemStackRegistryRegistryKey(itemStack, registry, registrykey1) >= 3) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isSlot(Slot slot2) {
      ItemStack itemstack = slot2.getStack();
      if (itemstack.isEmpty()) {
         return false;
      } else {
         return slot2.inventory == this.inventory() ? false : !isItemStack4(itemstack);
      }
   }

   private static int getIntByItemStack(ItemStack itemStack) {
      int i = itemStack.getMaxDamage();
      if (i <= 0) {
         return 100;
      } else {
         int j = i - itemStack.getDamage();
         return j <= 0 ? 0 : (int)Math.floor(j * 100.0 / i);
      }
   }

   private int getIntByList(List<Slot> list) {
      int i = 0;

      for (Slot slotx : list) {
         ItemStack itemstack = slotx.getStack();
         if (!itemstack.isEmpty() && slotx.inventory != this.inventory()) {
            i = i * 31 + itemstack.getItem().hashCode();
            i = i * 31 + itemstack.getCount();
            i = i * 31 + itemstack.getComponents().hashCode();
         }
      }

      return i;
   }

   private static boolean isItemStackRegistryEntry(ItemStack itemStack, RegistryEntry registryEntry) {
      if (itemStack.getItem() != Items.POTION) {
         return false;
      } else {
         PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         if (potioncontentscomponent == null) {
            return false;
         } else {
            if (potioncontentscomponent.potion().isPresent()) {
               for (StatusEffectInstance statuseffectinstance : ((Potion)((RegistryEntry)potioncontentscomponent.potion().get()).value()).getEffects()) {
                  if (statuseffectinstance.getEffectType() == registryEntry) {
                     return true;
                  }
               }
            }

            for (StatusEffectInstance statuseffectinstance1 : potioncontentscomponent.customEffects()) {
               if (statuseffectinstance1.getEffectType() == registryEntry) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   @Override
   public void onHudRenderContext(HudRenderContext hudRenderContext) {
      if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen && this.isGenericContainerScreen(genericcontainerscreen)) {
         if (this.genericContainerScreen != genericcontainerscreen) {
            this.genericContainerScreen = genericcontainerscreen;
            this.flag = true;
         }

         if (this.flag) {
            this.setGenericContainerScreen(genericcontainerscreen);
         }

         if (this.slot != null && !this.isSlot(this.slot)) {
            this.slot = null;
         }

         if (this.slot2 != null && !this.isSlot(this.slot2)) {
            this.slot2 = null;
         }

         if (this.slot != null || this.slot2 != null) {
            int[] aint = this.getIntArrayByGenericContainerScreen(genericcontainerscreen);
            boolean flagx = "Мигать".equals(this.podsvetka.getString2());
            if (this.slot != null) {
               int i = this.deshevyyItem.getInt();
               DrawContext drawcontext3 = hudRenderContext.getDrawContext();
               int k2 = aint[0];
               int l2 = aint[1];
               int l = flagx ? this.getIntByInt(i) : i;
               Slot slotx = this.slot;
               int k = l2;
               int j = k2;
               DrawContext drawcontext = drawcontext3;
               this.onSlotIntDrawContextIntInt(slotx, l, drawcontext, j, k);
            }

            if (this.slot2 != null && this.slot2 != this.slot) {
               int l1 = this.vygodnyyItem.getInt();
               DrawContext drawcontext2 = hudRenderContext.getDrawContext();
               int i2 = aint[0];
               int j2 = aint[1];
               int k1 = flagx ? this.getIntByInt(l1) : l1;
               Slot slot1 = this.slot2;
               int j1 = j2;
               int i1 = i2;
               DrawContext drawcontext1 = drawcontext2;
               this.onSlotIntDrawContextIntInt(slot1, k1, drawcontext1, i1, j1);
            }
         }

         if (this.cenaZa1Item.isFlag3()) {
            for (Slot slot2x : ((GenericContainerScreenHandler)genericcontainerscreen.getScreenHandler()).slots) {
               if (!slot2x.getStack().isEmpty() && slot2x.getStack().getCount() > 1) {
                  this.addItemStack(slot2x.getStack());
               }
            }
         }
      } else {
         this.genericContainerScreen = null;
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void update8() {
      if (this.currentScreen() instanceof GenericContainerScreen genericcontainerscreen && this.isGenericContainerScreen(genericcontainerscreen)) {
         if (++this.value236 >= 20) {
            this.value236 = 0;
            this.flag = true;
         }

         int i = this.getIntByList(((GenericContainerScreenHandler)genericcontainerscreen.getScreenHandler()).slots);
         if (i != this.value235) {
            this.value235 = i;
            this.flag = true;
         }

         if (this.flag) {
            this.setGenericContainerScreen(genericcontainerscreen);
         }
      } else {
         this.update11();
      }
   }
}
