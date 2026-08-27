package client.module.render;

import client.data.ThemeConfig;
import client.module.Category;
import client.module.Module;
import client.render.ShapeShader;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.Setting;
import client.util.ItemIcons;
import client.util.UnsafeFields;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.joml.Matrix4f;

public class ShulkerPreview extends Module {
   private final BooleanSetting onlyPoBindu;
   private final HotkeySetting bindPrevyu;
   private UnsafeFields<Slot> unsafeFields;
   private boolean flag;

   public ShulkerPreview() {
      super("ShulkerPreview", Category.RENDER);
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только по бинду");
      booleansetting.setDescription("Показывать превью только пока зажат бинд");
      this.onlyPoBindu = booleansetting;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 342);
      hotkeysetting.setName("Бинд превью");
      hotkeysetting.setDescription("Клавиша для показа превью");
      this.bindPrevyu = hotkeysetting;
      this.flag = false;
      this.bindPrevyu.setVisibleWhen(this.onlyPoBindu::isFlag3);
      this.addSettings(new Setting[]{this.onlyPoBindu, this.bindPrevyu});
   }

   @Override
   public void onDisable() {
      this.flag = false;
      this.unsafeFields = null;
   }

   private boolean isItemStack(ItemStack itemStack) {
      return itemStack != null && !itemStack.isEmpty() ? Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock : false;
   }

   private List getListByItemStack(ItemStack itemStack) {
      try {
         ContainerComponent containercomponent = (ContainerComponent)itemStack.get(DataComponentTypes.CONTAINER);
         if (containercomponent != null) {
            DefaultedList<ItemStack> defaultedlist = DefaultedList.ofSize(27, ItemStack.EMPTY);
            containercomponent.copyTo(defaultedlist);

            for (ItemStack itemstack : defaultedlist) {
               if (!itemstack.isEmpty()) {
                  return defaultedlist;
               }
            }
         }
      } catch (Exception exception2) {
      }

      try {
         NbtComponent nbtcomponent = (NbtComponent)itemStack.get(DataComponentTypes.CUSTOM_DATA);
         if (nbtcomponent != null) {
            NbtCompound nbtcompound = nbtcomponent.copyNbt();
            if (nbtcompound != null) {
               List list = this.getListByNbtCompound(nbtcompound);
               if (list != null) {
                  return list;
               }
            }
         }
      } catch (Exception exception1) {
      }

      try {
         NbtComponent nbtcomponent1 = (NbtComponent)itemStack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
         if (nbtcomponent1 != null) {
            NbtCompound nbtcompound1 = nbtcomponent1.copyNbt();
            if (nbtcompound1 != null) {
               return this.getListByNbtCompound(nbtcompound1);
            }
         }
      } catch (Exception exception) {
      }

      return null;
   }

   private List getListByNbtCompound(NbtCompound nbtCompound) {
      NbtList nbtlist = null;
      if (nbtCompound.contains("Items")) {
         nbtlist = nbtCompound.getList("Items", 10);
      } else if (nbtCompound.contains("BlockEntityTag")) {
         NbtCompound nbtcompound = nbtCompound.getCompound("BlockEntityTag");
         if (nbtcompound.contains("Items")) {
            nbtlist = nbtcompound.getList("Items", 10);
         }
      }

      if (nbtlist != null && !nbtlist.isEmpty()) {
         DefaultedList<ItemStack> defaultedlist = DefaultedList.ofSize(27, ItemStack.EMPTY);

         for (int i = 0; i < nbtlist.size(); i++) {
            try {
               NbtCompound nbtcompound1 = nbtlist.getCompound(i);
               int j = nbtcompound1.getByte("Slot") & 255;
               if (j < 27) {
                  ItemStack itemstack = ItemStack.fromNbt(this.world().getRegistryManager(), nbtcompound1).orElse(ItemStack.EMPTY);
                  if (!itemstack.isEmpty()) {
                     defaultedlist.set(j, itemstack);
                  }
               }
            } catch (Exception exception) {
            }
         }

         for (ItemStack itemstack1 : defaultedlist) {
            if (!itemstack1.isEmpty()) {
               return defaultedlist;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private void onIntDrawContextIntList(int count, DrawContext drawContext, int count2, List list) {
      float f = 162.0F;
      float f1 = 54.0F;
      float f2 = f + 20.0F;
      float f3 = f1 + 14.0F;
      int i = this.client().getWindow().getScaledWidth();
      int j = this.client().getWindow().getScaledHeight();
      float f4 = count2 - f2 - 12.0F;
      float f5 = count + 12;
      if (f4 < 2.0F) {
         f4 = count2 + 12;
      }

      if (f4 + f2 > i - 2) {
         f4 = i - f2 - 2.0F;
      }

      if (f5 + f3 > j - 2) {
         f5 = count - f3 - 12.0F;
      }

      MatrixStack matrixstack = drawContext.getMatrices();
      matrixstack.push();
      matrixstack.translate(0.0F, 0.0F, 800.0F);
      Matrix4f matrix4f = matrixstack.peek().getPositionMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      int i2 = ThemeConfig.getPalette().getValue();
      float f9 = 1.0F;
      int l1 = i2;
      float f8 = 8.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f8, f4, l1, matrix4f, f3, f2, f9, f5);
      float f6 = f4 + 10.0F;
      float f7 = f5 + 7.0F;

      for (int k = 0; k < Math.min(list.size(), 27); k++) {
         ItemStack itemstack = (ItemStack)list.get(k);
         if (!itemstack.isEmpty()) {
            int l = k / 9;
            int i1 = k % 9;
            int j1 = (int)(f6 + i1 * 18 + 1.0F);
            int k1 = (int)(f7 + l * 18 + 1.0F);
            Matrix4f matrix4f1 = matrixstack.peek().getPositionMatrix();
            float f14 = j1;
            float f15 = k1;
            float f13 = 1.0F;
            float f12 = 16.0F;
            float f11 = f15;
            float f10 = f14;
            if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f10, matrix4f1, f13, f12, f11, itemstack)) {
               drawContext.drawItem(itemstack, j1, k1);
            }

            drawContext.drawStackOverlay(this.client().textRenderer, itemstack, j1, k1);
         }
      }

      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
      matrixstack.pop();
   }

   private Slot getSlotByHandledScreen(HandledScreen handledScreen) {
      if (!this.flag) {
         try {
            this.unsafeFields = new UnsafeFields<>(handledScreen, HandledScreen.class, Slot.class);
            this.flag = true;
         } catch (Exception exception) {
            this.flag = true;
         }
      }

      return this.unsafeFields == null ? null : (Slot)this.unsafeFields.getObjectByObject(handledScreen);
   }

   public boolean isIntDrawContextHandledScreenInt(int count, DrawContext drawContext, HandledScreen handledScreen, int count2) {
      if (this.onlyPoBindu.isFlag3() && !this.bindPrevyu.check()) {
         return false;
      } else {
         try {
            Slot slot = this.getSlotByHandledScreen(handledScreen);
            if (slot != null && slot.hasStack()) {
               ItemStack itemstack = slot.getStack();
               if (!this.isItemStack(itemstack)) {
                  return false;
               } else {
                  List list = this.getListByItemStack(itemstack);
                  if (list == null) {
                     return false;
                  } else {
                     this.onIntDrawContextIntList(count2, drawContext, count, list);
                     return false;
                  }
               }
            } else {
               return false;
            }
         } catch (Exception exception) {
            return false;
         }
      }
   }

   @Override
   public void onEnable() {
   }
}
