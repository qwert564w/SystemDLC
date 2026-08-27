package client.enums;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum SwapProjectile {
   SNOWBALL(Items.SNOWBALL, "Ком снега"),
   NETHER_STAR(Items.NETHER_STAR, "Стан"),
   SHULKER(null, "Рюкзак (любой шалкер)"),
   PRISMARINE_SHARD(Items.PRISMARINE_SHARD, "Взрывная трапка"),
   POPPED_CHORUS_FRUIT(Items.POPPED_CHORUS_FRUIT, "Трапка"),
   FIRE_CHARGE(Items.FIRE_CHARGE, "Взрывная штучка");

   public final Item item;
   public final String text;
   private static final Item[] itemArray = new Item[]{
      Items.SHULKER_BOX,
      Items.WHITE_SHULKER_BOX,
      Items.ORANGE_SHULKER_BOX,
      Items.MAGENTA_SHULKER_BOX,
      Items.LIGHT_BLUE_SHULKER_BOX,
      Items.YELLOW_SHULKER_BOX,
      Items.LIME_SHULKER_BOX,
      Items.PINK_SHULKER_BOX,
      Items.GRAY_SHULKER_BOX,
      Items.LIGHT_GRAY_SHULKER_BOX,
      Items.CYAN_SHULKER_BOX,
      Items.PURPLE_SHULKER_BOX,
      Items.BLUE_SHULKER_BOX,
      Items.BROWN_SHULKER_BOX,
      Items.GREEN_SHULKER_BOX,
      Items.RED_SHULKER_BOX,
      Items.BLACK_SHULKER_BOX
   };
   private static final SwapProjectile[] swapProjectileArray = getSwapProjectileArray();

   private SwapProjectile(Item item2, String text2) {
      this.item = item2;
      this.text = text2;
   }

   public static Item[] getItemArray() {
      return itemArray;
   }

   private static SwapProjectile[] getSwapProjectileArray() {
      return new SwapProjectile[]{SNOWBALL, NETHER_STAR, SHULKER, PRISMARINE_SHARD, POPPED_CHORUS_FRUIT, FIRE_CHARGE};
   }

   public boolean check() {
      return this == SHULKER;
   }

   public static SwapProjectile getSwapProjectileByString(String text) {
      return Enum.valueOf(SwapProjectile.class, text);
   }
}
