package client.module.render;

import client.data.ScreenSlots;
import client.module.Category;
import client.module.Module;
import client.render.HudRenderContext;
import client.setting.BooleanSetting;
import client.setting.Setting;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;

public class CompassCooldown extends Module {
   private final BooleanSetting pryatatGotovyy;

   public CompassCooldown() {
      super("CompassCooldown", Category.RENDER);
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Прятать готовый");
      booleansetting.setDescription("Не рисовать отметку, когда компас уже готов");
      this.pryatatGotovyy = booleansetting;
      this.addSettings(new Setting[]{this.pryatatGotovyy});
   }

   @Override
   public void onDisable() {
   }

   private static String getStringByLong(long time) {
      long i = time / 1000L;
      long j = i / 3600L;
      long k = i % 3600L / 60L;
      long l = i % 60L;
      if (j > 0L) {
         return j + "ч" + k + "м";
      } else {
         return k > 0L ? k + "м" + l + "с" : l + "с";
      }
   }

   private static long getLongByItemStack(ItemStack itemStack) {
      NbtComponent nbtcomponent = (NbtComponent)itemStack.get(DataComponentTypes.CUSTOM_DATA);
      if (nbtcomponent == null) {
         return -1L;
      } else {
         NbtCompound nbtcompound = nbtcomponent.copyNbt();
         if (nbtcompound != null && nbtcompound.contains("kringeItems") && nbtcompound.contains("region-radar")) {
            NbtCompound nbtcompound1 = nbtcompound.getCompound("kringeItems");
            if (nbtcompound1 != null && "RegionRadar".equals(nbtcompound1.getString("type"))) {
               NbtCompound nbtcompound2 = nbtcompound.getCompound("region-radar");
               return nbtcompound2 != null && nbtcompound2.contains("delay") ? nbtcompound2.getLong("delay") : -1L;
            } else {
               return -1L;
            }
         } else {
            return -1L;
         }
      }
   }

   private void onIntDrawContextIntStringInt(int count, DrawContext drawContext, int count2, String text, int count3) {
      TextRenderer textrenderer = this.client().textRenderer;
      int i = count + 16 - textrenderer.getWidth(text);
      int j = count2 + 16 - 9 + 1;
      drawContext.drawText(textrenderer, text, i, j, count3, true);
   }

   @Override
   public void onHudRenderContext(HudRenderContext hudRenderContext) {
      if (!this.notInGame()) {
         if (this.currentScreen() instanceof HandledScreen handledscreen) {
            int[] aint = ScreenSlots.getIntArrayByHandledScreen(handledscreen);
            DrawContext drawcontext = hudRenderContext.getDrawContext();

            for (Slot slot : handledscreen.getScreenHandler().slots) {
               if (slot != null && slot.isEnabled() && slot.hasStack()) {
                  long i = getLongByItemStack(slot.getStack());
                  if (i >= 0L) {
                     long j = i - System.currentTimeMillis();
                     if (j > 0L || !this.pryatatGotovyy.isFlag3()) {
                        String s = j <= 0L ? "✔" : getStringByLong(j);
                        int k = j <= 0L ? -11141291 : -171;
                        int j1 = aint[0] + slot.x;
                        int i1 = aint[1] + slot.y;
                        int l = j1;
                        this.onIntDrawContextIntStringInt(l, drawcontext, i1, s, k);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
   }
}
