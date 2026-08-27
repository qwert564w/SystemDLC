package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.MultilistSetting;
import client.setting.Setting;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.Identifier;

public class NoSounds extends Module {
   private static List<String> list = Arrays.asList("Опыт", "Колокол", "Иссушение", "Варден", "Колокольчик");
   private static List<String> list2 = Arrays.asList("Опыт", "Колокол", "Иссушение", "Варден", "Колокольчик");
   public MultilistSetting otklyuchit;

   public NoSounds() {
      super("NoSounds", Category.PLAYER);
      MultilistSetting multilistsetting = new MultilistSetting("", "", list, list2);
      multilistsetting.setName("Отключить");
      multilistsetting.setDescription("Какие звуки заглушить");
      this.otklyuchit = multilistsetting;
      this.addSettings(new Setting[]{this.otklyuchit});
   }

   @Override
   public void onDisable() {
   }

   public boolean isSoundInstance(SoundInstance soundInstance) {
      if (this.isEnabled() && soundInstance != null) {
         Identifier identifier = soundInstance.getId();
         if (identifier == null) {
            return false;
         } else {
            String s = identifier.getPath();
            if (!this.otklyuchit.isString("Опыт")
               || !s.startsWith("entity.experience_orb") && !s.startsWith("entity.experience_bottle") && !s.equals("entity.splash_potion.break")) {
               if (this.otklyuchit.isString("Колокол") && s.startsWith("block.bell")) {
                  return true;
               } else if (this.otklyuchit.isString("Иссушение") && s.startsWith("entity.wither")) {
                  return true;
               } else {
                  return this.otklyuchit.isString("Варден") && s.startsWith("entity.warden")
                     ? true
                     : this.otklyuchit.isString("Колокольчик") && s.equals("block.note_block.bell");
               }
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void onEnable() {
   }
}
