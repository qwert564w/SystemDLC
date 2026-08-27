package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.util.InventoryActions;
import client.util.SneakState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import org.lwjgl.glfw.GLFW;

public class AutoTool extends Module {
   public BooleanSetting neBreakInstrument;
   private int value235;
   private int value236;
   private int value237;
   private boolean flag;

   public AutoTool() {
      super("AutoTool", Category.PLAYER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Не ломать инструмент");
      booleansetting.setDescription("Не использовать сломанные инструменты");
      this.neBreakInstrument = booleansetting;
      this.value235 = -1;
      this.value236 = -1;
      this.value237 = -1;
      this.flag = false;
      this.addSettings(new Setting[]{this.neBreakInstrument});
   }

   @Override
   public void onTick() {
      SneakState.update4();
   }

   private void setFlag() {
      this.flag = false;
   }

   private boolean check3() {
      return this.client().crosshairTarget instanceof BlockHitResult;
   }

   private boolean check4() {
      if (this.notInGame()) {
         return false;
      } else if (this.client().crosshairTarget instanceof BlockHitResult blockhitresult) {
         BlockState blockstate = this.world().getBlockState(blockhitresult.getBlockPos());
         int i = this.clientPlayer().getInventory().selectedSlot;
         ItemStack itemstack = this.inventory().getStack(i);
         return this.isBlockStateItemStack(blockstate, itemstack);
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.update11();
      this.flag = false;
      this.value236 = -1;
      this.value237 = -1;
      InventoryActions.setModule(this);
   }

   private int getInt() {
      if (this.notInGame()) {
         return -1;
      } else {
         BlockHitResult blockhitresult = (BlockHitResult)this.client().crosshairTarget;
         if (blockhitresult != null) {
            BlockState blockstate = this.world().getBlockState(blockhitresult.getBlockPos());
            return this.getIntByBlockState(blockstate);
         } else {
            return -1;
         }
      }
   }

   private int getIntByBlockState(BlockState blockState) {
      int i = -1;
      float f = 1.0F;
      boolean flagx = false;

      for (int j = 0; j < 36; j++) {
         ItemStack itemstack = this.inventory().getStack(j);
         if (!itemstack.isEmpty()) {
            if (this.neBreakInstrument.isFlag3() && itemstack.isDamageable()) {
               int k = itemstack.getMaxDamage() - itemstack.getDamage();
               if (k <= 1) {
                  continue;
               }
            }

            boolean flag1 = this.isBlockStateItemStack(blockState, itemstack);
            float f1 = itemstack.getMiningSpeedMultiplier(blockState);
            if (flag1 && !flagx) {
               flagx = true;
               f = f1;
               i = j;
            } else if (flag1 == flagx && f1 > f) {
               f = f1;
               i = j;
            }
         }
      }

      return i;
   }

   private boolean isBlockStateItemStack(BlockState blockState, ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         Item item = itemStack.getItem();
         if (item instanceof AxeItem) {
            return blockState.isIn(BlockTags.AXE_MINEABLE);
         } else if (item instanceof PickaxeItem) {
            return blockState.isIn(BlockTags.PICKAXE_MINEABLE);
         } else if (item instanceof ShovelItem) {
            return blockState.isIn(BlockTags.SHOVEL_MINEABLE);
         } else {
            return item instanceof HoeItem ? blockState.isIn(BlockTags.HOE_MINEABLE) : false;
         }
      } else {
         return false;
      }
   }

   private float getFloatByBlockInt(Block block2, int count) {
      if (this.notInGame()) {
         return 0.0F;
      } else {
         ItemStack itemstack = this.inventory().getStack(count);
         return itemstack.getMiningSpeedMultiplier(block2.getDefaultState());
      }
   }

   private void onIntInt(int count, int count2) {
      this.flag = true;
      Runnable runnable = this::setFlag;
      InventoryActions.onIntRunnableModuleInt(count, runnable, this, count2);
   }

   private void update11() {
      if (!this.flag) {
         if (this.inGame() && this.value236 != -1 && this.value237 != -1) {
            this.onIntInt(this.value236, this.value237);
         }

         this.value236 = -1;
         this.value237 = -1;
         if (this.inGame() && this.value235 != -1) {
            this.clientPlayer().getInventory().selectedSlot = this.value235;
         }

         this.value235 = -1;
      }
   }

   private boolean check5() {
      try {
         long i = this.client().getWindow().getHandle();
         Key key = this.options().attackKey.getDefaultKey();
         int j = key.getCode();
         if (j < 0) {
            return false;
         } else {
            return key.getCategory() == Type.MOUSE ? GLFW.glfwGetMouseButton(i, j) == 1 : InputUtil.isKeyPressed(i, j);
         }
      } catch (Throwable throwable) {
         return false;
      }
   }

   @Override
   public void onEnable() {
      this.value235 = -1;
   }

   @Override
   public void update8() {
      SneakState.update4();
      if (!this.notInGame() && !this.player().isCreative()) {
         boolean flagx = this.client().currentScreen == null && this.client().mouse.isCursorLocked();
         boolean flag1 = flagx && this.check5() && this.check3();
         if (flag1) {
            if (this.value235 == -1) {
               this.value235 = this.clientPlayer().getInventory().selectedSlot;
            }

            if (!this.check4()) {
               int i = this.getInt();
               if (i != -1) {
                  if (i < 9) {
                     if (i != this.clientPlayer().getInventory().selectedSlot) {
                        this.clientPlayer().getInventory().selectedSlot = i;
                     }
                  } else if (this.value236 == -1 && !this.flag) {
                     int j = this.clientPlayer().getInventory().selectedSlot;
                     this.onIntInt(i, j);
                     this.value236 = i;
                     this.value237 = j;
                  }
               }
            }
         } else {
            this.update11();
         }
      } else {
         this.update11();
      }
   }
}
