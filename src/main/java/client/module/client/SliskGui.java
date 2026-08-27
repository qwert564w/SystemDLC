package client.module.client;

import client.audio.SoundEngine;
import client.enums.SoundEvent;
import client.gui.screen.ClickGuiScreen;
import client.module.Category;
import client.module.Module;
import client.setting.Setting;

public class SliskGui extends Module {
   private ClickGuiScreen clickGuiScreen;

   public SliskGui() {
      super("SliskGui", Category.CLIENT);
      this.keybind.setValue3(344);
      this.keybind.setInt(344);
      this.addSettings(new Setting[0]);
   }

   @Override
   public void toggle() {
      if (!PanicModule.isFlag()) {
         if (this.currentScreen() == null) {
            if (this.clickGuiScreen == null) {
               this.clickGuiScreen = new ClickGuiScreen();
            }

            this.client().setScreen(this.clickGuiScreen);
            SoundEngine.getInstance().onSoundEvent(SoundEvent.GUI_OPEN);
         } else if (this.currentScreen() instanceof ClickGuiScreen) {
            this.client().setScreen(null);
         }
      }
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onEnable() {
   }
}
