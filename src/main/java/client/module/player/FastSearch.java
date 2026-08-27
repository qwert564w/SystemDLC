package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.InputSetting;
import client.setting.Setting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

public class FastSearch extends Module {
   private InputSetting komanda;
   private BooleanSetting vtoroyRuki;
   private BooleanSetting ubiratSkobki;
   private HotkeySetting keyPoiska;
   private long time;

   public FastSearch() {
      super("FastSearch", Category.PLAYER);
      InputSetting inputsetting = new InputSetting("", "", "ah search {name}", "ah search {name}");
      inputsetting.setName("Команда");
      inputsetting.setDescription("Шаблон команды, {name} заменяется на название предмета");
      this.komanda = inputsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Второй руки");
      booleansetting.setDescription("Брать предмет из левой руки вместо правой");
      this.vtoroyRuki = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Убирать скобки");
      booleansetting1.setDescription("Вырезать префиксы в квадратных скобках вроде [Rare]");
      this.ubiratSkobki = booleansetting1;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", -1, this::update11);
      hotkeysetting.setName("Клавиша поиска");
      hotkeysetting.setDescription("Прожать чтобы отправить команду поиска предмета в руке");
      this.keyPoiska = hotkeysetting;
      this.time = 0L;
      this.addSettings(new Setting[]{this.komanda, this.vtoroyRuki, this.ubiratSkobki, this.keyPoiska});
   }

   @Override
   public void onDisable() {
   }

   private void update11() {
      if (this.isEnabled()) {
         if (!this.notInGame() && this.networkHandler() != null) {
            long i = System.currentTimeMillis();
            if (i - this.time >= 250L) {
               this.time = i;
               ItemStack itemstack = this.vtoroyRuki.isFlag3() ? this.player().getOffHandStack() : this.player().getMainHandStack();
               if (itemstack != null && !itemstack.isEmpty()) {
                  String s = this.getStringByItemStack(itemstack);
                  if (!s.isEmpty()) {
                     String s1 = this.komanda.getText();
                     if (s1 == null || s1.isEmpty()) {
                        s1 = "ah search {name}";
                     }

                     String s2 = s1.contains("{name}") ? s1.replace("{name}", s) : s1 + " " + s;
                     s2 = s2.trim();
                     if (s2.startsWith("/")) {
                        s2 = s2.substring(1);
                     }

                     this.networkHandler().sendChatCommand(s2);
                  }
               }
            }
         }
      }
   }

   private String getStringByItemStack(ItemStack itemStack) {
      String s = itemStack.getName().getString();
      String s1 = Formatting.strip(s);
      if (s1 == null) {
         s1 = s;
      }

      s1 = s1.trim();
      if (this.ubiratSkobki.isFlag3()) {
         while (s1.startsWith("[")) {
            int i = s1.indexOf(93);
            if (i < 0) {
               break;
            }

            s1 = s1.substring(i + 1).trim();
         }
      }

      return s1;
   }

   @Override
   public void onEnable() {
   }
}
