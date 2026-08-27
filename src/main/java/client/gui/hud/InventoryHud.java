package client.gui.hud;

import client.api.Theme;
import client.gui.widget.RenderElement;
import client.gui.widget.ScissorStack;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.render.ItemIconCache;
import client.render.ShapeShader;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.util.ItemIcons;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.joml.Matrix4f;

public class InventoryHud extends RenderElement {
   private static final int value271 = 9;
   private static final int value272 = 3;
   private static final float value273 = 16.0F;
   private static final float value274 = 2.0F;
   private static final float value275 = 20.0F;
   private static final float value276 = 2.0F;
   private static final float value277 = 8.0F;
   private static final float value278 = 4.0F;
   private static final float value279 = 212.0F;
   private static final float value280 = 80.0F;
   private static final ItemStack[] itemStackArray = new ItemStack[]{
      new ItemStack(Items.DIAMOND_SWORD),
      new ItemStack(Items.ENDER_PEARL, 16),
      new ItemStack(Items.GOLDEN_APPLE, 8),
      new ItemStack(Items.OBSIDIAN, 64),
      new ItemStack(Items.TOTEM_OF_UNDYING),
      new ItemStack(Items.EXPERIENCE_BOTTLE, 32),
      new ItemStack(Items.CROSSBOW),
      new ItemStack(Items.ARROW, 64),
      new ItemStack(Items.COBWEB, 12)
   };
   private final BooleanSetting showBackground;
   private final BooleanSetting ubratKolliziyu;

   public InventoryHud() {
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать фон");
      booleansetting.setDescription("Отображать подложку и рамки слотов. Если выключено — рисуются только предметы.");
      this.showBackground = booleansetting;
      booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Убрать коллизию");
      booleansetting.setDescription("Инвентарь не будет двигаться другими HUD-элементами (например, уведомлениями) и сам не будет их толкать.");
      this.ubratKolliziyu = booleansetting;
      this.onSettingArray(new Setting[]{this.showBackground, this.ubratKolliziyu});
   }

   @Override
   public boolean check2() {
      return !this.ubratKolliziyu.isFlag3();
   }

   @Override
   public String getString() {
      return "Инвенторй";
   }

   @Override
   public CategoryType getCategoryType() {
      return CategoryType.CUBE;
   }

   @Override
   public float getFloat9() {
      return 80.0F;
   }

   @Override
   public String getString3() {
      return "inventory";
   }

   private boolean check24() {
      ClientPlayerEntity clientplayerentity = Feature.mc.player;
      if (clientplayerentity == null) {
         return true;
      } else {
         PlayerInventory playerinventory = clientplayerentity.getInventory();
         if (playerinventory == null) {
            return true;
         } else {
            int i = Math.min(playerinventory.main.size(), 36);

            for (int j = 9; j < i; j++) {
               ItemStack itemstack = (ItemStack)playerinventory.main.get(j);
               if (itemstack != null && !itemstack.isEmpty()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private ItemStack getItemStackByIntInt(int count, int count2) {
      int i = 9 + count2 * 9 + count;
      ClientPlayerEntity clientplayerentity = Feature.mc.player;
      if (clientplayerentity == null) {
         return ItemStack.EMPTY;
      } else {
         PlayerInventory playerinventory = clientplayerentity.getInventory();
         if (playerinventory != null && i < playerinventory.main.size()) {
            ItemStack itemstack = (ItemStack)playerinventory.main.get(i);
            return itemstack != null ? itemstack : ItemStack.EMPTY;
         } else {
            return ItemStack.EMPTY;
         }
      }
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getInventar().setBoolean(flag);
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getInventar().isFlag3();
   }

   @Override
   public float getFloat10() {
      return 80.0F;
   }

   @Override
   public float getFloat11() {
      return 212.0F;
   }

   @Override
   protected boolean check11() {
      return false;
   }

   @Override
   public float getFloat14() {
      return 212.0F;
   }

   @Override
   protected boolean check20() {
      return false;
   }

   @Override
   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      float f = value * value2;
      if (!(f <= 0.001F)) {
         float f1 = this.getValue260();
         float f2 = this.getValue261();
         boolean flag = this.showBackground.isFlag3();
         float f3 = this.getFloatByFloat(f);
         if (flag) {
            int j1 = Theme.background();
            float f17 = 1.0F;
            float f16 = 1.0F;
            float f15 = 0.0F;
            int l = 436207616;
            float f14 = 0.0F;
            byte b0 = 0;
            int k = j1;
            float f13 = 12.0F;
            float f12 = 12.0F;
            float f11 = 12.0F;
            float f10 = 12.0F;
            float f9 = 80.0F;
            float f8 = 212.0F;
            ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
               f8, l, k, f1, f2, f17, f10, f9, f16, matrix4f, f3, b0, f12, f14, f11, f13, f15
            );
         }

         boolean flag1 = this.check4() && this.check24();
         float f19 = 80.0F;
         float f18 = 212.0F;
         ScissorStack.onFloatFloatFloatFloat(f18, f19, f2, f1);

         try {
            for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 9; j++) {
                  float f4 = f1 + 8.0F + j * 22.0F;
                  float f5 = f2 + 8.0F + i * 22.0F;
                  if (flag) {
                     int i1 = Theme.elevated();
                     float f22 = 4.0F;
                     float f21 = 20.0F;
                     float f20 = 20.0F;
                     ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f22, f4, i1, matrix4f, f21, f20, f3, f5);
                  }

                  ItemStack itemstack = flag1 ? (i == 0 ? itemStackArray[j % itemStackArray.length] : ItemStack.EMPTY) : this.getItemStackByIntInt(j, i);
                  if (itemstack != null && !itemstack.isEmpty()) {
                     float f6 = f4 + 2.0F;
                     float f7 = f5 + 2.0F;
                     float f23 = 16.0F;
                     if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f6, matrix4f, f, f23, f7, itemstack)) {
                        float f24 = 16.0F;
                        ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(f24, itemstack, f, f7, f6, drawContext);
                     }

                     drawContext.drawStackOverlay(Feature.mc.textRenderer, itemstack, (int)f6, (int)f7);
                  }
               }
            }
         } finally {
            ScissorStack.update();
         }
      }
   }

   @Override
   public float[] getFloatArray() {
      return new float[]{20.0F, 200.0F};
   }

   @Override
   public float getFloat22() {
      return this.getValue260();
   }

   @Override
   public float getFloat23() {
      return 80.0F;
   }

   @Override
   public float getFloat24() {
      return 212.0F;
   }
}
