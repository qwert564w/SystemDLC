package client.api;

import client.module.CategoryType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import org.joml.Matrix4f;

public sealed interface Icon permits ItemStackIcon, CategoryIcon, StatusEffectIcon {
   public static Icon getIconByCategoryType(CategoryType categoryType) {
      return new CategoryIcon(categoryType);
   }

   public static Icon getIconByRegistryEntry(RegistryEntry registryEntry) {
      return new StatusEffectIcon(registryEntry);
   }

   public static Icon getIconByItem(Item item2) {
      return new ItemStackIcon(new ItemStack(item2));
   }

   public static Icon getIconByItemStack(ItemStack itemStack) {
      return new ItemStackIcon(itemStack != null && !itemStack.isEmpty() ? itemStack : new ItemStack(Items.PAPER));
   }

   public void draw(float value, float value2, float value3, Matrix4f matrix4f, float value4, DrawContext drawContext);
}
