package client.data;

import client.render.ItemRenderEntry;
import client.util.ItemKey;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruCache extends LinkedHashMap<ItemKey, ItemRenderEntry> {
   public LruCache(int count, float value, boolean flag) {
      super(count, value, flag);
   }

   @Override
   protected boolean removeEldestEntry(Entry entry) {
      return this.size() > 256;
   }
}
