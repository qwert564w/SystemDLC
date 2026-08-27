package client.enums;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum TrackedItem {
   ENDER_EYE(Items.ENDER_EYE, "Дезориентация", "дезориентация", "звуковая волна", ServerFlag.FT),
   FIRE_CHARGE(Items.FIRE_CHARGE, "Огненный смерч", "огненный", null, ServerFlag.ANY),
   SUGAR(Items.SUGAR, "Явная пыль", "явная пыль", "световая вспышка", ServerFlag.FT),
   TOTEM_OF_UNDYING(Items.TOTEM_OF_UNDYING, "Тотем бессмертия", null),
   EXPERIENCE_BOTTLE(Items.EXPERIENCE_BOTTLE, "Бутылка опыта", null),
   NETHERITE_SCRAP(Items.NETHERITE_SCRAP, "Трапка", "трапка", "нерушимая клетка", ServerFlag.FT),
   DRIED_KELP(Items.DRIED_KELP, "Пласт", "пласт", "нерушимая стена", ServerFlag.FT),
   GOLDEN_APPLE(Items.GOLDEN_APPLE, "Золотое яблоко", null),
   ENCHANTED_GOLDEN_APPLE(Items.ENCHANTED_GOLDEN_APPLE, "Зач. золотое яблоко", null),
   CHORUS_FRUIT(Items.CHORUS_FRUIT, "Хорус", null),
   ENDER_PEARL(Items.ENDER_PEARL, "Эндер пёрл", null),
   SNOWBALL(Items.SNOWBALL, "Снежок", null),
   SNOWBALL_FREEZE(Items.SNOWBALL, "Снежок заморозка", "заморозка", "ледяная сфера"),
   POPPED_CHORUS_FRUIT(Items.POPPED_CHORUS_FRUIT, "Трапка 2", null, null, ServerFlag.HW),
   NETHER_STAR(Items.NETHER_STAR, "Стан", null, null, ServerFlag.HW),
   JACK_O_LANTERN(Items.JACK_O_LANTERN, "Светильник Джека", null),
   PRISMARINE_SHARD(Items.PRISMARINE_SHARD, "Взрывная трапка", null, null, ServerFlag.HW),
   WIND_CHARGE(Items.WIND_CHARGE, "Заряд вихря", null),
   BOZHA_AURA(Items.PHANTOM_MEMBRANE, "Божья аура", "божья аура", "божественная аура", ServerFlag.FT),
   CROSSBOW(Items.CROSSBOW, "Арбалет", "арбалет"),
   SHULKER(Items.SHULKER_BOX, "Шалкер", "ящик"),
   POTION_POPPER(Items.SPLASH_POTION, "Хлопушка", null, 16738740, -1),
   SVYATAYA_VODA(Items.SPLASH_POTION, "Святая вода", "святая вода", 16777215, -1),
   POTION_GNEVA(Items.SPLASH_POTION, "Зелье Гнева", "зелье гнева", 10040115, -1),
   POTION_PALADINA(Items.SPLASH_POTION, "Зелье Палладина", "зелье палладина", 65535, -1),
   POTION_ASSASINA(Items.SPLASH_POTION, "Зелье Ассасина", "зелье ассасина", 3355443, -1),
   POTION_RADIACII(Items.SPLASH_POTION, "Зелье Радиации", "зелье радиации", 3329330, -1),
   SNOTVORNOE(Items.SPLASH_POTION, "Снотворное", "снотворное", 255, -1),
   TALIC_MRAKA(Items.TOTEM_OF_UNDYING, "Талисман Мрака", null, -1, 1, true),
   TALIC_VIHRYA(Items.TOTEM_OF_UNDYING, "Талисман Вихря", null, -1, 2, true),
   TALIC_DEMONA(Items.TOTEM_OF_UNDYING, "Талисман Демона", null, -1, 3, true),
   TALIC_RAZDORA(Items.TOTEM_OF_UNDYING, "Талисман Раздора", null, -1, 4, true),
   TALIC_YAROSTI(Items.TOTEM_OF_UNDYING, "Талисман Ярости", null, -1, 5, false),
   TALIC_KRUSHITELYA(Items.TOTEM_OF_UNDYING, "Талисман Крушителя", null, -1, 6, false),
   TALIC_KARATELYA(Items.TOTEM_OF_UNDYING, "Талисман Карателя", null, -1, 7, false),
   TALIC_TIRANA(Items.TOTEM_OF_UNDYING, "Талисман Тирана", null, -1, 8, true);

   public final Item item;
   public final String text;
   public final String text2;
   public final String text3;
   public final String text4;
   public final String text5;
   public final boolean flag;
   public final int value;
   public final int value2;
   public final boolean flag2;
   public final ServerFlag serverFlag;
   private static final TrackedItem[] trackedItemArray = getTrackedItemArray();

   private TrackedItem(Item item2, String text, String text2, int count, int count2) {
      this(item2, text, text2, null, false, count, count2, false, ServerFlag.NONE);
   }

   private TrackedItem(Item item2, String text, String text2, String text3, ServerFlag serverFlag) {
      this(item2, text, text2, text3, false, -1, -1, false, serverFlag);
   }

   private TrackedItem(Item item2, String text, String text2, String text3) {
      this(item2, text, text2, text3, false, -1, -1, false, ServerFlag.NONE);
   }

   private TrackedItem(Item item2, String text, String text2, int count, int count2, boolean flag) {
      this(item2, text, text2, null, false, count, count2, flag, ServerFlag.NONE);
   }

   private TrackedItem(Item item2, String text6, String text7, String text8, boolean flag3, int count, int count2, boolean flag4, ServerFlag serverFlag2) {
      this.item = item2;
      this.text = text6;
      this.text2 = text7;
      this.text3 = text8;
      this.text4 = text7 == null ? null : text7.toLowerCase();
      this.text5 = text8 == null ? null : text8.toLowerCase();
      this.flag = flag3;
      this.value = count;
      this.value2 = count2;
      this.flag2 = flag4;
      this.serverFlag = serverFlag2;
   }

   private TrackedItem(Item item2, String text, String text2) {
      this(item2, text, text2, null, false, -1, -1, false, ServerFlag.NONE);
   }

   public static TrackedItem getTrackedItemByInt(int count) {
      for (TrackedItem trackeditem : values()) {
         if (trackeditem.value2 == count) {
            return trackeditem;
         }
      }

      return null;
   }

   public static TrackedItem getTrackedItemByInt2(int count) {
      for (TrackedItem trackeditem : values()) {
         if (trackeditem.value == count) {
            return trackeditem;
         }
      }

      return null;
   }

   private static TrackedItem[] getTrackedItemArray() {
      return new TrackedItem[]{
         ENDER_EYE,
         FIRE_CHARGE,
         SUGAR,
         TOTEM_OF_UNDYING,
         EXPERIENCE_BOTTLE,
         NETHERITE_SCRAP,
         DRIED_KELP,
         GOLDEN_APPLE,
         ENCHANTED_GOLDEN_APPLE,
         CHORUS_FRUIT,
         ENDER_PEARL,
         SNOWBALL,
         SNOWBALL_FREEZE,
         POPPED_CHORUS_FRUIT,
         NETHER_STAR,
         JACK_O_LANTERN,
         PRISMARINE_SHARD,
         WIND_CHARGE,
         BOZHA_AURA,
         CROSSBOW,
         SHULKER,
         POTION_POPPER,
         SVYATAYA_VODA,
         POTION_GNEVA,
         POTION_PALADINA,
         POTION_ASSASINA,
         POTION_RADIACII,
         SNOTVORNOE,
         TALIC_MRAKA,
         TALIC_VIHRYA,
         TALIC_DEMONA,
         TALIC_RAZDORA,
         TALIC_YAROSTI,
         TALIC_KRUSHITELYA,
         TALIC_KARATELYA,
         TALIC_TIRANA
      };
   }

   public static TrackedItem getTrackedItemByString(String text) {
      return Enum.valueOf(TrackedItem.class, text);
   }
}
