package client.module.player;

import client.data.ContainerBounds;
import client.data.ContainerButton;
import client.data.SlotSelection;
import client.enums.ContainerAction;
import client.module.Category;
import client.module.Module;
import client.render.HudRenderContext;
import client.setting.MultilistSetting;
import client.setting.Setting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;

public class ContainerHelper extends Module {
   private static final Identifier identifier = Identifier.ofVanilla("widget/button");
   private static final Identifier identifier2 = Identifier.ofVanilla("widget/button_highlighted");
   private final List<ContainerButton> list = new ArrayList<>();
   private boolean flag;
   private int value235 = -1;
   private boolean flag2;
   private final MultilistSetting knopki;
   private static final Comparator<ItemStack> comparator = Comparator.comparingInt(ContainerHelper::getIntByItemStack2)
      .thenComparing(ContainerHelper::getStringByItemStack)
      .thenComparingInt(ItemStack::getDamage)
      .thenComparingInt(ContainerHelper::getIntByItemStack);

   public ContainerHelper() {
      super("ContainerHelper", Category.PLAYER);
      MultilistSetting multilistsetting = new MultilistSetting(
         "",
         "",
         Arrays.stream(ContainerAction.values()).map(ContainerHelper::getStringByContainerAction).toList(),
         Arrays.stream(ContainerAction.values()).map(ContainerHelper::getStringByContainerAction2).toList()
      );
      multilistsetting.setName("Кнопки");
      multilistsetting.setDescription("Какие кнопки показывать");
      this.knopki = multilistsetting;
      this.addSettings(new Setting[]{this.knopki});
   }

   @Override
   public void onTick() {
      if (this.flag) {
         if (!this.notInGame()
            && this.interactionManager() != null
            && this.currentScreen() instanceof HandledScreen handledscreen
            && handledscreen.getScreenHandler().syncId == this.value235) {
            for (int i = 0; i < 2; i++) {
               if (!this.isHandledScreen(handledscreen)) {
                  this.flag = false;
                  return;
               }
            }
         } else {
            this.flag = false;
         }
      }
   }

   private static int getIntByItemStack(ItemStack itemStack) {
      return -itemStack.getCount();
   }

   private static String getStringByContainerAction(ContainerAction containerAction) {
      return containerAction.text;
   }

   private float getFloat() {
      return (float)(this.client().mouse.getY() * this.client().getWindow().getScaledHeight() / this.client().getWindow().getHeight());
   }

   private void onHandledScreenBoolean(HandledScreen handledScreen, boolean flag3) {
      this.flag = true;
      this.value235 = handledScreen.getScreenHandler().syncId;
      this.flag2 = flag3;
   }

   @Override
   public void onDisable() {
      this.list.clear();
      this.flag = false;
   }

   private float getFloat2() {
      return (float)(this.client().mouse.getX() * this.client().getWindow().getScaledWidth() / this.client().getWindow().getWidth());
   }

   private ContainerBounds getContainerBoundsByHandledScreen(HandledScreen handledScreen) {
      PlayerInventory playerinventory = this.inventory();
      boolean flagx = handledScreen instanceof InventoryScreen;
      int i = Integer.MAX_VALUE;
      int j = Integer.MIN_VALUE;
      int k = 0;

      for (Slot slot : handledScreen.getScreenHandler().slots) {
         if (slot != null) {
            if (slot.y + 18 > k) {
               k = slot.y + 18;
            }

            boolean flag1 = playerinventory != null && slot.inventory == playerinventory;
            if (flagx || !flag1) {
               if (slot.x < i) {
                  i = slot.x;
               }

               if (slot.x + 16 > j) {
                  j = slot.x + 16;
               }
            }
         }
      }

      if (i == Integer.MAX_VALUE) {
         return null;
      } else {
         int l = Math.max(166, k + 8);
         int i1 = (handledScreen.width - 176) / 2;
         int j1 = (handledScreen.height - l) / 2;
         return new ContainerBounds(i1 + i, i1 + j, j1);
      }
   }

   private static int getIntByItemStack2(ItemStack itemStack) {
      if (itemStack.contains(DataComponentTypes.EQUIPPABLE)) {
         return 0;
      } else if (itemStack.contains(DataComponentTypes.TOOL) || itemStack.isDamageable()) {
         return 1;
      } else {
         return itemStack.getItem() instanceof BlockItem ? 2 : 3;
      }
   }

   private List getListByBooleanHandledScreenBoolean(boolean flag, HandledScreen handledScreen, boolean flag2) {
      PlayerInventory playerinventory = this.inventory();
      ArrayList arraylist = new ArrayList();

      for (Slot slot : handledScreen.getScreenHandler().slots) {
         if (slot != null) {
            boolean flagx = playerinventory != null && slot.inventory == playerinventory;
            if (flag == flagx && (!flag2 || !flagx || slot.getIndex() >= 9 && slot.getIndex() <= 35)) {
               arraylist.add(slot);
            }
         }
      }

      return arraylist;
   }

   private static String getStringByItemStack(ItemStack itemStack) {
      return Registries.ITEM.getId(itemStack.getItem()).toString();
   }

   private boolean isContainerAction(ContainerAction containerAction) {
      return this.knopki.isString(containerAction.text);
   }

   private static String getStringByContainerAction2(ContainerAction containerAction) {
      return containerAction.text;
   }

   private void onIntIntHandledScreen(int count, int count2, HandledScreen handledScreen) {
      ScreenHandler screenhandler = handledScreen.getScreenHandler();
      this.onIntScreenHandler(count2, screenhandler);
      this.onIntScreenHandler(count, screenhandler);
      if (!screenhandler.getCursorStack().isEmpty()) {
         this.onIntScreenHandler(count2, screenhandler);
      }
   }

   private void onBooleanHandledScreen(boolean flag, HandledScreen handledScreen) {
      ScreenHandler screenhandler = handledScreen.getScreenHandler();
      PlayerInventory playerinventory = this.inventory();
      if (playerinventory != null) {
         for (Slot slot : screenhandler.slots) {
            if (slot != null && slot.hasStack() && slot.inventory == playerinventory == flag) {
               this.interactionManager().clickSlot(screenhandler.syncId, slot.id, 0, SlotActionType.QUICK_MOVE, this.player());
            }
         }
      }
   }

   private void onHandledScreenBoolean2(HandledScreen handledScreen, boolean flag) {
      ScreenHandler screenhandler = handledScreen.getScreenHandler();
      boolean flagx = false;

      for (Slot slot : (Iterable<Slot>)(this.getListByBooleanHandledScreenBoolean(flag, handledScreen, flagx))) {
         if (slot.hasStack()) {
            this.interactionManager().clickSlot(screenhandler.syncId, slot.id, 1, SlotActionType.THROW, this.player());
         }
      }
   }

   @Override
   public void onSlotSelection(SlotSelection slotSelection) {
      if (!this.list.isEmpty()) {
         if (slotSelection.getValue2() == 1) {
            if (slotSelection.getValue() == 0) {
               if (!this.notInGame() && this.interactionManager() != null) {
                  if (this.currentScreen() instanceof HandledScreen handledscreen) {
                     float f1 = this.getFloat2();
                     float f = this.getFloat();

                     for (ContainerButton containerbutton : this.list) {
                        if (containerbutton.isFloatFloat(f1, f)) {
                           slotSelection.setFlag(true);
                           boolean flagx = handledscreen instanceof InventoryScreen;
                           switch (containerbutton.getAction()) {
                              case DROP:
                                 this.onHandledScreenBoolean2(handledscreen, flagx);
                                 break;
                              case STASH:
                                 boolean flag1 = true;
                                 this.onBooleanHandledScreen(flag1, handledscreen);
                                 break;
                              case TAKE:
                                 boolean flag2x = false;
                                 this.onBooleanHandledScreen(flag2x, handledscreen);
                                 break;
                              case SORT:
                                 this.onHandledScreenBoolean(handledscreen, flagx);
                           }

                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private List getListByHandledScreen(HandledScreen handledScreen) {
      List<ContainerAction> listx = handledScreen instanceof InventoryScreen
         ? List.of(ContainerAction.DROP, ContainerAction.SORT)
         : List.of(ContainerAction.DROP, ContainerAction.STASH, ContainerAction.TAKE, ContainerAction.SORT);
      return listx.stream().filter(this::isContainerAction).toList();
   }

   @Override
   public void onHudRenderContext(HudRenderContext hudRenderContext) {
      this.list.clear();
      if (!this.notInGame()) {
         if (this.currentScreen() instanceof HandledScreen handledscreen) {
            if (!(handledscreen instanceof CreativeInventoryScreen)) {
               ContainerBounds containerbounds = this.getContainerBoundsByHandledScreen(handledscreen);
               if (containerbounds != null) {
                  List<ContainerAction> listx = this.getListByHandledScreen(handledscreen);
                  if (!listx.isEmpty()) {
                     TextRenderer textrenderer = this.client().textRenderer;
                     int i = (listx.size() - 1) * 4;

                     for (ContainerAction containeraction : listx) {
                        i += textrenderer.getWidth(containeraction.text) + 16;
                     }

                     int k = containerbounds.bgTop() - 20 - 4;
                     int l = Math.round(containerbounds.left() + (containerbounds.right() - containerbounds.left() - i) / 2.0F);
                     DrawContext drawcontext = hudRenderContext.getDrawContext();
                     float f = this.getFloat2();
                     float f1 = this.getFloat();

                     for (ContainerAction containeraction1 : listx) {
                        int j = textrenderer.getWidth(containeraction1.text) + 16;
                        ContainerButton containerbutton = new ContainerButton(l, k, j, containeraction1);
                        boolean flagx = containerbutton.isFloatFloat(f, f1);
                        drawcontext.drawGuiTexture(RenderLayer::getGuiTextured, flagx ? identifier2 : identifier, l, k, j, 20);
                        drawcontext.drawCenteredTextWithShadow(textrenderer, containeraction1.text, l + j / 2, k + 6, flagx ? 16777120 : 16777215);
                        this.list.add(containerbutton);
                        l += j + 4;
                     }
                  }
               }
            }
         }
      }
   }

   private void onIntScreenHandler(int count, ScreenHandler screenHandler) {
      this.interactionManager().clickSlot(screenHandler.syncId, count, 0, SlotActionType.PICKUP, this.player());
   }

   private boolean isListHandledScreen(List<Slot> list, HandledScreen handledScreen) {
      ArrayList arraylist = new ArrayList();

      for (Slot slot : list) {
         if (slot.hasStack()) {
            arraylist.add(slot.getStack());
         }
      }

      arraylist.sort(comparator);

      for (int l = 0; l < arraylist.size(); l++) {
         Slot slot1 = (Slot)list.get(l);
         ItemStack itemstack = (ItemStack)arraylist.get(l);
         if (slot1.getStack() != itemstack) {
            for (int i = l + 1; i < list.size(); i++) {
               if (((Slot)list.get(i)).getStack() == itemstack) {
                  int k = slot1.id;
                  int j = ((Slot)list.get(i)).id;
                  this.onIntIntHandledScreen(k, j, handledScreen);
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean isHandledScreenList(HandledScreen handledScreen, List list) {
      for (int i = 0; i < list.size(); i++) {
         ItemStack itemstack = ((Slot)list.get(i)).getStack();
         if (!itemstack.isEmpty() && itemstack.getCount() < itemstack.getMaxCount()) {
            for (int j = i + 1; j < list.size(); j++) {
               ItemStack itemstack1 = ((Slot)list.get(j)).getStack();
               if (!itemstack1.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemstack, itemstack1)) {
                  int i1 = ((Slot)list.get(j)).id;
                  int l = ((Slot)list.get(i)).id;
                  int k = i1;
                  this.onIntIntHandledScreen(l, k, handledScreen);
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean isHandledScreen(HandledScreen handledScreen) {
      boolean flag1 = true;
      boolean flagx = this.flag2;
      List listx = this.getListByBooleanHandledScreenBoolean(flagx, handledScreen, flag1);
      if (listx.isEmpty()) {
         return false;
      } else {
         return !handledScreen.getScreenHandler().getCursorStack().isEmpty() ? false : this.isHandledScreenList(handledScreen, listx) || this.isListHandledScreen(listx, handledScreen);
      }
   }

   @Override
   public void onEnable() {
   }
}
