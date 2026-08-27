package client.data;

import client.api.SwapWheelView;
import client.gui.widget.SwapWheelEditor;
import client.module.player.SwapWheel;
import client.setting.HotkeySetting;
import client.setting.InputSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.setting.SwapWheelSetting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Arrays;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class SwapWheelData implements SwapWheelView {
   public final ItemStack[] itemStackArray;
   public final HotkeySetting[] hotkeySettingArray;
   public final boolean[] booleanArray;
   public final HotkeySetting hotkeySetting;
   public final InputSetting inputSetting;
   public final SliderSetting sliderSetting;
   public final SwapWheelEditor swapWheelEditor;
   public final SwapWheelSetting swapWheelSetting;
   public boolean flag;
   public boolean flag2;
   public final SwapWheel swapWheel;

   public SwapWheelData(SwapWheel swapWheel2, String text, String text2, String text3, String text4, String text5, String text6) {
      this.swapWheel = swapWheel2;
      this.itemStackArray = new ItemStack[6];
      this.hotkeySettingArray = new HotkeySetting[6];
      this.booleanArray = new boolean[6];
      this.flag = false;
      this.flag2 = true;
      this.hotkeySetting = new HotkeySetting(text2, "Удерживай — открывает колесо " + text + ", отпусти над сегментом для действия.", -1);
      this.inputSetting = new InputSetting(text4, "", "[]", "");
      this.inputSetting.setVisible(false);
      this.sliderSetting = new SliderSetting(text6, "Количество сегментов в колесе " + text, 6.0, 3.0, 6.0, 1.0);
      this.swapWheelEditor = new SwapWheelEditor(this);
      this.swapWheelSetting = new SwapWheelSetting(text3, "Открыть редактор колеса " + text, this.swapWheelEditor);

      for (int i = 0; i < 6; i++) {
         this.hotkeySettingArray[i] = new HotkeySetting(text5 + i, "", -1);
         this.hotkeySettingArray[i].setVisible(false);
      }
   }

   public void update() {
      Arrays.fill(this.itemStackArray, null);
      String s = this.inputSetting.getText();
      if (s == null || s.isEmpty() || "[]".equals(s)) {
         this.flag2 = false;
      } else if (this.swapWheel.client().world == null) {
         this.flag2 = true;
      } else {
         try {
            JsonElement jsonelement = JsonParser.parseString(s);
            if (!jsonelement.isJsonArray()) {
               return;
            }

            JsonArray jsonarray = jsonelement.getAsJsonArray();

            for (int i = 0; i < Math.min(jsonarray.size(), 6); i++) {
               JsonElement jsonelement1 = jsonarray.get(i);
               if (jsonelement1 == null || jsonelement1.isJsonNull()) {
                  this.itemStackArray[i] = null;
               } else if (jsonelement1.isJsonObject()) {
                  JsonObject jsonobject = jsonelement1.getAsJsonObject();
                  if (jsonobject.has("id")) {
                     Identifier identifier = Identifier.tryParse(jsonobject.get("id").getAsString());
                     if (identifier != null) {
                        Item item = (Item)Registries.ITEM.get(identifier);
                        if (item != null) {
                           ItemStack itemstack = new ItemStack(item);
                           if (!itemstack.isEmpty()) {
                              if (jsonobject.has("components")) {
                                 try {
                                    NbtCompound nbtcompound = StringNbtReader.parse(jsonobject.get("components").getAsString());
                                    this.swapWheel.onItemStackNbtCompound(itemstack, nbtcompound);
                                 } catch (Exception exception) {
                                 }
                              }

                              this.itemStackArray[i] = itemstack;
                           }
                        }
                     }
                  }
               }
            }

            this.flag2 = false;
         } catch (Exception exception1) {
         }
      }
   }

   public void setFlag2() {
      this.flag2 = true;
   }

   public void update2() {
      this.flag = false;
      Arrays.fill(this.booleanArray, false);
   }

   public void update3() {
      JsonArray jsonarray = new JsonArray();

      for (ItemStack itemstack : this.itemStackArray) {
         if (itemstack != null && !itemstack.isEmpty()) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("id", Registries.ITEM.getId(itemstack.getItem()).toString());
            NbtCompound nbtcompound = this.swapWheel.getNbtCompoundByItemStack(itemstack);
            if (nbtcompound != null && !nbtcompound.isEmpty()) {
               jsonobject.addProperty("components", nbtcompound.toString());
            }

            jsonarray.add(jsonobject);
         } else {
            jsonarray.add(JsonNull.INSTANCE);
         }
      }

      this.inputSetting.setString(jsonarray.toString());
   }

   public void update4() {
      this.swapWheel.addSettings(new Setting[]{this.hotkeySetting, this.swapWheelSetting, this.sliderSetting, this.inputSetting});

      for (HotkeySetting hotkeysetting : this.hotkeySettingArray) {
         this.swapWheel.addSetting(hotkeysetting);
      }
   }

   @Override
   public void clearSlot(int count) {
      if (count >= 0 && count < 6) {
         this.itemStackArray[count] = null;
         this.update3();
      }
   }

   @Override
   public void setSlot(ItemStack itemStack, int count) {
      if (count >= 0 && count < 6) {
         if (itemStack != null && !itemStack.isEmpty()) {
            this.itemStackArray[count] = itemStack.copyWithCount(1);
         } else {
            this.itemStackArray[count] = null;
         }

         this.update3();
      }
   }

   @Override
   public ItemStack[] getWheelSlots() {
      return this.itemStackArray;
   }

   @Override
   public HotkeySetting getCellBind(int count) {
      return count >= 0 && count < 6 ? this.hotkeySettingArray[count] : null;
   }

   @Override
   public int slotCapacity() {
      int i = (int)this.sliderSetting.getValue();
      if (i < 3) {
         i = 3;
      }

      if (i > 6) {
         i = 6;
      }

      return i;
   }
}
