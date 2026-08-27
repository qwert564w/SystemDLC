package client.util;

import net.minecraft.item.Item;

public record ItemKey(Item item, int componentsHash, int count, int damage) {
   public int getCount() {
      return this.count;
   }

   public int getDamage() {
      return this.damage;
   }

   public int getComponentsHash() {
      return this.componentsHash;
   }

   public Item getItem() {
      return this.item;
   }
}
