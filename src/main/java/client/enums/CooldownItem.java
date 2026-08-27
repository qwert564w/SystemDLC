package client.enums;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;

public enum CooldownItem {
   ENCHANTED_GOLDEN_APPLE("Чарка", 150, Items.ENCHANTED_GOLDEN_APPLE),
   NAUSEA("Дезка", 60, Items.ENDER_EYE),
   TOTEM("Тотем", 60, Items.TOTEM_OF_UNDYING),
   GOLDEN_APPLE("Гэпл", 30, Items.GOLDEN_APPLE),
   DRIED_KELP("Пласт", 30, Items.DRIED_KELP),
   HEALING_POTION("Исцеление", 20, Items.POTION),
   CHORUS_FRUIT("Хорус", 20, Items.CHORUS_FRUIT),
   NETHERITE_SCRAP("Трапка", 10, Items.NETHERITE_SCRAP);

   public final String text;
   public final long time;
   public final ItemStack itemStack;
   private static final CooldownItem[] cooldownItemArray = getCooldownItemArray();

   private CooldownItem(String text2, int count, Item item2) {
      this.text = text2;
      this.time = count * 1000L;
      this.itemStack = new ItemStack(item2);
      if (item2 == Items.POTION) {
         this.itemStack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.HEALING));
      }
   }

   private static CooldownItem[] getCooldownItemArray() {
      return new CooldownItem[]{ENCHANTED_GOLDEN_APPLE, NAUSEA, TOTEM, GOLDEN_APPLE, DRIED_KELP, HEALING_POTION, CHORUS_FRUIT, NETHERITE_SCRAP};
   }

   public static CooldownItem getCooldownItemByString(String text) {
      return Enum.valueOf(CooldownItem.class, text);
   }
}
