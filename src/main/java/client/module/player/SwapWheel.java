package client.module.player;

import client.data.BundleSlot;
import client.data.SlotSelection;
import client.data.SwapWheelData;
import client.module.Category;
import client.module.Module;
import client.render.HudRenderContext;
import client.render.ItemIconCache;
import client.setting.HotkeySetting;
import client.util.InventoryActions;
import client.util.SphereItems;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.joml.Matrix4f;

public class SwapWheel extends Module {
   private final SwapWheelData swapWheelData = new SwapWheelData(this, "свапа", "Клавиша колеса", "Ячейки", "wheelSlots", "cellBind", "Кол-во ячеек");
   private final SwapWheelData swapWheelData2 = new SwapWheelData(
      this, "использования", "Клавиша колеса использования", "Ячейки использования", "useWheelSlots", "useCellBind", "Кол-во ячеек использования"
   );
   private final SwapWheelData[] swapWheelDataArray = new SwapWheelData[]{this.swapWheelData, this.swapWheelData2};
   private World world = null;
   private SwapWheelData swapWheelData3 = null;
   private boolean flag = false;
   private boolean flag2 = false;

   public SwapWheel() {
      super("SwapWheel", Category.PLAYER);
      this.swapWheelData.update4();
      this.swapWheelData2.update4();
   }

   private void onInt(int count) {
      Runnable runnable = this.getRunnable();
      int i = this.inventory().selectedSlot;
      if (SphereItems.isInt2(count)) {
         int j = count - 36;
         InventoryActions.onIntIntRunnableModule(j, i, runnable, this);
      } else {
         InventoryActions.onRunnableModuleInt(runnable, this, count);
      }
   }

   private void setSwapWheelData(SwapWheelData swapWheelData) {
      this.swapWheelData3 = swapWheelData;
      if (this.client().mouse != null && !this.flag) {
         this.client().mouse.unlockCursor();
         this.flag = true;
      }
   }

   private void update11() {
      this.swapWheelData3 = null;
      if (this.flag && this.client().mouse != null) {
         if (this.currentScreen() == null) {
            this.client().mouse.lockCursor();
         }

         this.flag = false;
      }
   }

   private boolean isItemStackItemStack(ItemStack itemStack, ItemStack itemStack2) {
      ProfileComponent profilecomponent = (ProfileComponent)itemStack2.get(DataComponentTypes.PROFILE);
      ProfileComponent profilecomponent1 = (ProfileComponent)itemStack.get(DataComponentTypes.PROFILE);
      if (profilecomponent == null && profilecomponent1 == null) {
         return true;
      } else if (profilecomponent != null && profilecomponent1 != null) {
         String s = (String)profilecomponent.name().orElse(null);
         String s1 = (String)profilecomponent1.name().orElse(null);
         if (s != null && s.equalsIgnoreCase(s1)) {
            return true;
         } else {
            UUID uuid = (UUID)profilecomponent.id().orElse(null);
            UUID uuid1 = (UUID)profilecomponent1.id().orElse(null);
            return uuid != null && uuid.equals(uuid1);
         }
      } else {
         return false;
      }
   }

   private void update12() {
      this.flag2 = false;
      InventoryActions.setModule(this);
   }

   private boolean isItemStackItemStack2(ItemStack itemStack, ItemStack itemStack2) {
      return this.isItemStackItemStack3(itemStack, itemStack2);
   }

   @Override
   public void onDisable() {
      this.update11();
      this.update12();
   }

   private void setFlag2() {
      this.flag2 = false;
   }

   public static void onIntFloatIntIntFloatIntFloatFloatBufferBuilderFloatFloatMatrix4f(
      int count, float value, int count2, int count3, float value2, int count4, float value3, float value4, BufferBuilder bufferBuilder, float value5, float value6, Matrix4f matrix4f
   ) {
      int i = Math.max(6, (int)(48.0F * (Math.abs(value3 - value4) / (float) (Math.PI * 2))));
      float f = (value3 - value4) / i;

      for (int j = 0; j < i; j++) {
         float f1 = value4 + f * j;
         float f2 = value4 + f * (j + 1);
         float f3 = value5 + (float)Math.cos(f1) * value2;
         float f4 = value6 + (float)Math.sin(f1) * value2;
         float f5 = value5 + (float)Math.cos(f2) * value2;
         float f6 = value6 + (float)Math.sin(f2) * value2;
         float f7 = value5 + (float)Math.cos(f1) * value;
         float f8 = value6 + (float)Math.sin(f1) * value;
         float f9 = value5 + (float)Math.cos(f2) * value;
         float f10 = value6 + (float)Math.sin(f2) * value;
         bufferBuilder.vertex(matrix4f, f7, f8, 0.0F).color(count3, count4, count, count2);
         bufferBuilder.vertex(matrix4f, f3, f4, 0.0F).color(count3, count4, count, count2);
         bufferBuilder.vertex(matrix4f, f5, f6, 0.0F).color(count3, count4, count, count2);
         bufferBuilder.vertex(matrix4f, f7, f8, 0.0F).color(count3, count4, count, count2);
         bufferBuilder.vertex(matrix4f, f5, f6, 0.0F).color(count3, count4, count, count2);
         bufferBuilder.vertex(matrix4f, f9, f10, 0.0F).color(count3, count4, count, count2);
      }
   }

   private void render(DrawContext drawContext, SwapWheelData swapWheelData) {
      int i = swapWheelData.slotCapacity();
      float f = this.client().getWindow().getScaledWidth() / 2.0F;
      float f1 = this.client().getWindow().getScaledHeight() / 2.0F;
      float f2 = 92.0F;
      float f3 = Math.max(20.0F, f2 - 38.0F);
      float f4 = (float)(this.client().mouse.getX() * this.client().getWindow().getScaledWidth() / this.client().getWindow().getWidth());
      float f5 = (float)(this.client().mouse.getY() * this.client().getWindow().getScaledHeight() / this.client().getWindow().getHeight());
      float f9 = Float.POSITIVE_INFINITY;
      float f8 = 12.0F;
      int j = getIntByIntFloatFloatFloatFloatFloatFloat(i, f1, f, f4, f9, f5, f8);
      RenderSystem.enableBlend();
      RenderSystem.disableCull();
      RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

      for (int k = 0; k < i; k++) {
         int l = k == j ? -587214545 : -1441195751;
         int i1 = l >>> 24 & 0xFF;
         int j1 = l >> 16 & 0xFF;
         int k1 = l >> 8 & 0xFF;
         int l1 = l & 0xFF;
         float f6 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((double)k / i));
         float f7 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((k + 1.0) / i));
         onIntFloatIntIntFloatIntFloatFloatBufferBuilderFloatFloatMatrix4f(l1, f3, i1, j1, f2, k1, f7, f6, bufferbuilder, f, f1, matrix4f);
      }

      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      RenderSystem.enableCull();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();

      for (int i2 = 0; i2 < i; i2++) {
         ItemStack itemstack = swapWheelData.itemStackArray[i2];
         if (itemstack != null && !itemstack.isEmpty()) {
            float f14 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((double)i2 / i));
            float f15 = (float)((-Math.PI / 2) + (Math.PI * 2) * ((i2 + 1.0) / i));
            float f16 = (f14 + f15) / 2.0F;
            float f17 = (f3 + f2) / 2.0F;
            float f18 = f + (float)Math.cos(f16) * f17;
            float f19 = f1 + (float)Math.sin(f16) * f17;
            float f20 = f18 - 8.0F;
            float f21 = f19 - 8.0F;
            float f13 = 1.0F;
            float f12 = 16.0F;
            float f11 = f21;
            float f10 = f20;
            ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(f12, itemstack, f13, f11, f10, drawContext);
         }
      }
   }

   private static int getIntByIntFloatFloatFloatFloatFloatFloat(int count, float value, float value2, float value3, float value4, float value5, float value6) {
      float f = value3 - value2;
      float f1 = value5 - value;
      float f2 = (float)Math.sqrt(f * f + f1 * f1);
      if (!(f2 < value6) && !(f2 > value4)) {
         double d0 = Math.atan2(f1, f) + (Math.PI / 2);
         if (d0 < 0.0) {
            d0 += Math.PI * 2;
         }

         int i = (int)Math.floor(d0 / (Math.PI * 2) * count);
         return i >= 0 && i < count ? i : -1;
      } else {
         return -1;
      }
   }

   private int getIntBySwapWheelData(SwapWheelData swapWheelData) {
      int i = swapWheelData.slotCapacity();
      float f = this.client().getWindow().getScaledWidth() / 2.0F;
      float f1 = this.client().getWindow().getScaledHeight() / 2.0F;
      float f2 = (float)(this.client().mouse.getX() * this.client().getWindow().getScaledWidth() / this.client().getWindow().getWidth());
      float f3 = (float)(this.client().mouse.getY() * this.client().getWindow().getScaledHeight() / this.client().getWindow().getHeight());
      float f5 = Float.POSITIVE_INFINITY;
      float f4 = 12.0F;
      return getIntByIntFloatFloatFloatFloatFloatFloat(i, f1, f, f2, f5, f3, f4);
   }

   private boolean isItemStackItemStack3(ItemStack itemStack, ItemStack itemStack2) {
      if (itemStack != null && itemStack2 != null && !itemStack.isEmpty() && !itemStack2.isEmpty()) {
         if (itemStack.getItem() != itemStack2.getItem()) {
            return false;
         } else {
            return itemStack.getItem() != Items.PLAYER_HEAD
               ? itemStack.getComponentChanges().equals(itemStack2.getComponentChanges())
               : itemStack.getName().getString().equalsIgnoreCase(itemStack2.getName().getString()) && this.isItemStackItemStack(itemStack2, itemStack);
         }
      } else {
         return false;
      }
   }

   private void onSwapWheelDataBoolean(SwapWheelData swapWheelData, boolean flag) {
      int i = swapWheelData.slotCapacity();

      for (int j = 0; j < 6; j++) {
         HotkeySetting hotkeysetting = swapWheelData.hotkeySettingArray[j];
         boolean flagx = hotkeysetting.getValue() != -1 && hotkeysetting.check();
         if (flagx && !swapWheelData.booleanArray[j] && flag && j < i) {
            this.onIntSwapWheelData(j, swapWheelData);
         }

         swapWheelData.booleanArray[j] = flagx;
      }
   }

   private void onIntSwapWheelData(int count, SwapWheelData swapWheelData) {
      if (count >= 0 && count < 6) {
         ItemStack itemstack = swapWheelData.itemStackArray[count];
         if (itemstack != null && !itemstack.isEmpty()) {
            if (!this.flag2) {
               Slot slot = this.getSlotByItemStack(itemstack);
               if (slot != null) {
                  if (swapWheelData == this.swapWheelData2) {
                     this.onInt(slot.id);
                  } else {
                     this.onInt2(slot.id);
                  }
               } else {
                  if (swapWheelData == this.swapWheelData2) {
                     this.onItemStack(itemstack);
                  }
               }
            }
         }
      }
   }

   public void onItemStackNbtCompound(ItemStack itemStack, NbtCompound nbtCompound) {
      DynamicRegistryManager dynamicregistrymanager = this.world() != null ? this.world().getRegistryManager() : null;
      if (dynamicregistrymanager != null && nbtCompound != null) {
         try {
            Codec codec = ComponentChanges.CODEC;
            DataResult dataresult = codec.parse(dynamicregistrymanager.getOps(NbtOps.INSTANCE), nbtCompound);
            dataresult.result().ifPresent(var0 -> itemStack.applyChanges((ComponentChanges)var0));
         } catch (Exception exception) {
         }
      }
   }

   public NbtCompound getNbtCompoundByItemStack(ItemStack itemStack) {
      DynamicRegistryManager dynamicregistrymanager = this.world() != null ? this.world().getRegistryManager() : null;
      if (dynamicregistrymanager == null) {
         return null;
      } else {
         try {
            Codec codec = ComponentChanges.CODEC;
            DataResult dataresult = codec.encodeStart(dynamicregistrymanager.getOps(NbtOps.INSTANCE), itemStack.getComponentChanges());
            Optional optional = dataresult.result();
            if (optional.isEmpty()) {
               return null;
            } else {
               NbtElement nbtelement = (NbtElement)optional.get();
               return nbtelement instanceof NbtCompound nbtcompound ? nbtcompound : null;
            }
         } catch (Exception exception) {
            return null;
         }
      }
   }

   private void update13() {
      if (this.swapWheelData3 != null) {
         int i = this.getIntBySwapWheelData(this.swapWheelData3);
         if (i >= 0 && i < this.swapWheelData3.slotCapacity()) {
            SwapWheelData swapwheeldata = this.swapWheelData3;
            this.onIntSwapWheelData(i, swapwheeldata);
         }
      }
   }

   @Override
   public void onSlotSelection(SlotSelection slotSelection) {
      if (this.swapWheelData3 != null) {
         if (slotSelection.getValue2() == 1) {
            if (slotSelection.getValue() == 0) {
               this.update13();
               this.update11();
               slotSelection.setFlag(true);
            } else if (slotSelection.getValue() == 1) {
               this.update11();
               slotSelection.setFlag(true);
            }
         }
      }
   }

   @Override
   public void onHudRenderContext(HudRenderContext hudRenderContext) {
      if (this.swapWheelData3 != null) {
         this.render(hudRenderContext.getDrawContext(), this.swapWheelData3);
      }
   }

   private void onItemStack(ItemStack itemStack) {
      BundleSlot bundleslot = SphereItems.getBundleSlotByPredicate(p0 -> this.isItemStackItemStack2(itemStack, p0));
      if (bundleslot != null) {
         int l = bundleslot.getBundleSlot().id;
         int i1 = bundleslot.getIndexInBundle();
         int j1 = this.inventory().selectedSlot;
         Runnable runnable = this.getRunnable();
         int k = j1;
         int j = i1;
         int i = l;
         InventoryActions.onModuleIntIntRunnableInt(this, k, i, runnable, j);
      }
   }

   private void onSwapWheelData(SwapWheelData swapWheelData) {
      boolean flagx = swapWheelData.hotkeySetting.getValue() != -1 && swapWheelData.hotkeySetting.check();
      if (flagx && !swapWheelData.flag) {
         if (this.currentScreen() == null && this.swapWheelData3 == null) {
            this.setSwapWheelData(swapWheelData);
         }
      } else if (!flagx && swapWheelData.flag && this.swapWheelData3 == swapWheelData) {
         this.update13();
         this.update11();
      }

      swapWheelData.flag = flagx;
   }

   private Slot getSlotByItemStack(ItemStack itemStack) {
      if (this.player() != null && this.player().currentScreenHandler != null) {
         for (Slot slot : this.player().currentScreenHandler.slots) {
            if (slot.id >= 9 && slot.id <= 35 && this.isItemStackItemStack3(itemStack, slot.getStack())) {
               return slot;
            }
         }

         for (Slot slot1 : this.player().currentScreenHandler.slots) {
            if (slot1.id >= 36 && slot1.id <= 44 && this.isItemStackItemStack3(itemStack, slot1.getStack())) {
               return slot1;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private void onInt2(int count) {
      InventoryActions.onModuleIntRunnable(this, count, this.getRunnable());
   }

   private Runnable getRunnable() {
      this.flag2 = true;
      return this::setFlag2;
   }

   @Override
   public void onEnable() {
      for (SwapWheelData swapwheeldata : this.swapWheelDataArray) {
         swapwheeldata.setFlag2();
         swapwheeldata.update();
         swapwheeldata.update2();
      }

      this.swapWheelData3 = null;
      this.update12();
   }

   @Override
   public void update8() {
      World worldx = this.world();
      boolean flagx = worldx != this.world;
      this.world = worldx;

      for (SwapWheelData swapwheeldata : this.swapWheelDataArray) {
         if (flagx) {
            swapwheeldata.setFlag2();
         }

         if (worldx != null && swapwheeldata.flag2) {
            swapwheeldata.update();
         }

         this.onSwapWheelData(swapwheeldata);
      }

      if (this.swapWheelData3 != null && this.currentScreen() != null) {
         this.update11();
      }

      boolean flag1 = this.currentScreen() == null;

      for (SwapWheelData swapwheeldata1 : this.swapWheelDataArray) {
         this.onSwapWheelDataBoolean(swapwheeldata1, flag1);
      }
   }
}
