package client.data;

import client.util.NamedBlockPos;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class ResourceItem {
   public final String text;
   public final String text2;
   public String text3;
   public String text4;
   public String text5;
   public final Map<NamedBlockPos, Integer> map = new ConcurrentHashMap<>();

   public ResourceItem(String text6, String text7, String text8, String text9, String text10) {
      this.text = text6;
      this.text2 = text7;
      this.text3 = text8;
      this.text4 = text9;
      this.text5 = text10;
   }

   public Item getItem() {
      return (Item)Registries.ITEM.get(Identifier.of(this.text2));
   }

   public int getInt() {
      int i = 0;

      for (int j : this.map.values()) {
         i += j;
      }

      return i;
   }
}
