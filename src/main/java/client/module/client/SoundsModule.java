package client.module.client;

import client.audio.SoundEngine;
import client.module.Category;
import client.module.Module;
import client.setting.Setting;
import client.setting.SliderSetting;

public class SoundsModule extends Module {
   private final SliderSetting gromkost;

   public SoundsModule() {
      super("SoundsModule", Category.CLIENT);
      SliderSetting slidersetting = new SliderSetting("", "", 20.0, 0.0, 100.0, 1.0, "%", 0);
      slidersetting.setName("Громкость");
      slidersetting.setDescription("Общая громкость звуков");
      this.gromkost = slidersetting;
      this.addSettings(new Setting[]{this.gromkost});
      this.update11();
   }

   @Override
   protected void onSettingChanged(Setting setting2) {
      if (setting2 == this.gromkost) {
         SoundEngine.getInstance().setFloat((float)(this.gromkost.getValue() / 100.0));
      }
   }

   @Override
   public void onDisable() {
      SoundEngine.getInstance().setFlag2(false);
   }

   public SliderSetting getGromkost() {
      return this.gromkost;
   }

   private void update11() {
      SoundEngine soundengine = SoundEngine.getInstance();
      soundengine.setFlag2(this.isEnabled());
      soundengine.setFloat((float)(this.gromkost.getValue() / 100.0));
   }

   @Override
   public void onEnable() {
      SoundEngine.getInstance().setFlag2(true);
   }
}
