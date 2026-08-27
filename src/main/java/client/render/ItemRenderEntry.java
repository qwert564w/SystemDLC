package client.render;

import net.minecraft.client.render.item.ItemRenderState;

public final class ItemRenderEntry {
   public final ItemRenderState itemRenderState;
   public long time;

   ItemRenderEntry(ItemRenderState itemRenderState2, long time2) {
      this.itemRenderState = itemRenderState2;
      this.time = time2;
   }
}
