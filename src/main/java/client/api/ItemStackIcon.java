package client.api;

import client.render.ItemIconCache;
import client.util.ItemIcons;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;

public record ItemStackIcon(ItemStack stack) implements Icon {
   public ItemStack getStack() {
      return this.stack;
   }

   @Override
   public void draw(float value, float value2, float value3, Matrix4f matrix4f, float value4, DrawContext drawContext) {
      ItemStack itemstack = this.stack;
      if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(value2, matrix4f, value3, value4, value, itemstack)) {
         ItemStack itemstack1 = this.stack;
         ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(value4, itemstack1, value3, value, value2, drawContext);
      }
   }
}
