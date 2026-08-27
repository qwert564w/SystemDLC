package client.module.client;

import client.data.SystemFriend;
import client.module.Category;
import client.module.Module;
import client.setting.HotkeySetting;
import client.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class MSF extends Module {
   private final HotkeySetting knopka;

   public MSF() {
      super("MSF", Category.CLIENT);
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 2, this::update11);
      hotkeysetting.setName("Кнопка");
      hotkeysetting.setDescription("Кнопка для добавления/удаления из друзей");
      this.knopka = hotkeysetting;
      this.addSettings(new Setting[]{this.knopka});
   }

   @Override
   public void onDisable() {
   }

   private void update11() {
      if (!this.notInGame()) {
         Entity entity = this.client().targetedEntity;
         if (entity != null) {
            if (entity instanceof PlayerEntity playerentity) {
               if (playerentity != this.player()) {
                  String s = playerentity.getName().getString();
                  SystemFriend systemfriend = SystemFriend.getInstance();
                  systemfriend.onString(s);
               }
            }
         }
      }
   }

   @Override
   public void onEnable() {
   }
}
