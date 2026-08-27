package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.Setting;
import client.setting.SliderSetting;

public class CustomTime extends Module {
   private SliderSetting timeSutok;

   public CustomTime() {
      super("CustomTime", Category.VISUAL);
      SliderSetting slidersetting = new SliderSetting("", "", 6000.0, 0.0, 24000.0, 100.0);
      slidersetting.setName("Время суток");
      slidersetting.setDescription("0 — рассвет, 6000 — полдень, 12000 — закат, 18000 — полночь");
      this.timeSutok = slidersetting;
      this.addSettings(new Setting[]{this.timeSutok});
   }

   @Override
   public void onDisable() {
   }

   public long getLong() {
      return this.timeSutok.getValueAsLong();
   }

   @Override
   public void onEnable() {
   }
}
