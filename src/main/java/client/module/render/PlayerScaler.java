package client.module.render;

import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;

public class PlayerScaler extends Module {
   private BooleanSetting proporcionalno;
   private SliderSetting size;
   private SliderSetting sizeX;
   private SliderSetting sizeY;
   private SliderSetting sizeZ;

   public PlayerScaler() {
      super("PlayerScaler", Category.RENDER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Пропорционально");
      booleansetting.setDescription("Масштабировать по всем осям одинаково");
      this.proporcionalno = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.5, 0.1, 3.0, 0.05);
      slidersetting.setName("Размер");
      slidersetting.setDescription("Пропорциональный размер игрока");
      this.size = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 0.5, 0.1, 3.0, 0.05);
      slidersetting1.setName("Размер X");
      slidersetting1.setDescription("Масштаб по оси X");
      this.sizeX = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.5, 0.1, 3.0, 0.05);
      slidersetting2.setName("Размер Y");
      slidersetting2.setDescription("Масштаб по оси Y");
      this.sizeY = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 0.5, 0.1, 3.0, 0.05);
      slidersetting3.setName("Размер Z");
      slidersetting3.setDescription("Масштаб по оси Z");
      this.sizeZ = slidersetting3;
      this.size.setVisibleWhen(this.proporcionalno::isFlag3);
      this.sizeX.setVisibleWhen(this::getBoolean2);
      this.sizeY.setVisibleWhen(this::getBoolean);
      this.sizeZ.setVisibleWhen(this::getBoolean3);
      this.addSettings(new Setting[]{this.proporcionalno, this.size, this.sizeX, this.sizeY, this.sizeZ});
   }

   private Boolean getBoolean() {
      return !this.proporcionalno.isFlag3();
   }

   public SliderSetting getSizeX() {
      return this.sizeX;
   }

   public SliderSetting getSizeY() {
      return this.sizeY;
   }

   private Boolean getBoolean2() {
      return !this.proporcionalno.isFlag3();
   }

   @Override
   public void onDisable() {
   }

   public SliderSetting getSizeZ() {
      return this.sizeZ;
   }

   public BooleanSetting getProporcionalno() {
      return this.proporcionalno;
   }

   private Boolean getBoolean3() {
      return !this.proporcionalno.isFlag3();
   }

   public SliderSetting getSize() {
      return this.size;
   }

   @Override
   public void onEnable() {
   }
}
