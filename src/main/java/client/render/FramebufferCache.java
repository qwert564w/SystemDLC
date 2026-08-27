package client.render;

import client.util.ItemKey;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import net.minecraft.client.gl.SimpleFramebuffer;

public class FramebufferCache extends LinkedHashMap<ItemKey, SimpleFramebuffer> {
   FramebufferCache(int count, float value, boolean flag) {
      super(count, value, flag);
   }

   @Override
   protected boolean removeEldestEntry(Entry entry) {
      if (this.size() > 64) {
         try {
            ((SimpleFramebuffer)entry.getValue()).delete();
         } catch (Throwable throwable) {
         }

         return true;
      } else {
         return false;
      }
   }
}
