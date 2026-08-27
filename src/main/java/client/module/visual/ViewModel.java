package client.module.visual;

import client.data.Rotation;
import client.module.Category;
import client.module.Module;
import client.setting.ActionSetting;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.ViewModelController;

public class ViewModel extends Module {
   private SliderSetting x;
   private SliderSetting y;
   private SliderSetting size;
   private SliderSetting lX;
   private SliderSetting lY;
   private SliderSetting lSize;
   private BooleanSetting zerkalo;
   private ActionSetting sbros;
   private final ViewModelController viewModelController;

   public ViewModel() {
      super("ViewModel", Category.VISUAL);
      SliderSetting slidersetting1 = new SliderSetting("", "", 0.0, -2.0, 2.0, 0.01);
      slidersetting1.setName("X");
      slidersetting1.setDescription("Позиция руки по X");
      this.x = slidersetting1;
      slidersetting1 = new SliderSetting("", "", 0.0, -2.0, 2.0, 0.01);
      slidersetting1.setName("Y");
      slidersetting1.setDescription("Позиция руки по Y");
      this.y = slidersetting1;
      slidersetting1 = new SliderSetting("", "", 1.0, 0.1, 3.0, 0.01);
      slidersetting1.setName("Размер");
      slidersetting1.setDescription("Размер руки");
      this.size = slidersetting1;
      slidersetting1 = new SliderSetting("", "", 0.0, -2.0, 2.0, 0.01);
      slidersetting1.setName("Л. X");
      slidersetting1.setDescription("Позиция левой руки по X");
      this.lX = slidersetting1;
      slidersetting1 = new SliderSetting("", "", 0.0, -2.0, 2.0, 0.01);
      slidersetting1.setName("Л. Y");
      slidersetting1.setDescription("Позиция левой руки по Y");
      this.lY = slidersetting1;
      slidersetting1 = new SliderSetting("", "", 1.0, 0.1, 3.0, 0.01);
      slidersetting1.setName("Л. Размер");
      slidersetting1.setDescription("Размер левой руки");
      this.lSize = slidersetting1;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Зеркало");
      booleansetting.setDescription("Левая рука зеркалит правую");
      this.zerkalo = booleansetting;
      ActionSetting actionsetting = new ActionSetting("", "");
      actionsetting.setName("Сброс");
      actionsetting.setDescription("Вернуть все настройки по умолчанию");
      this.sbros = actionsetting;
      this.sbros.setRunnable(this::update11);
      this.addSettings(new Setting[]{this.zerkalo, this.sbros, this.x, this.y, this.size, this.lX, this.lY, this.lSize});

      for (SliderSetting slidersetting : new SliderSetting[]{this.lX, this.lY, this.lSize}) {
         slidersetting.setVisibleWhen(() -> !this.zerkalo.isFlag3());
      }

      this.viewModelController = new ViewModelController(this);
   }

   public SliderSetting getLY() {
      return this.lY;
   }

   public SliderSetting getY() {
      return this.y;
   }

   public SliderSetting getSize() {
      return this.size;
   }

   @Override
   public void onDisable() {
      this.viewModelController.update();
   }

   public SliderSetting getLSize() {
      return this.lSize;
   }

   public SliderSetting getLX() {
      return this.lX;
   }

   private void update11() {
      this.x.setDouble2(0.0);
      this.y.setDouble2(0.0);
      this.size.setDouble2(1.0);
      this.lX.setDouble2(0.0);
      this.lY.setDouble2(0.0);
      this.lSize.setDouble2(1.0);
   }

   public BooleanSetting getZerkalo() {
      return this.zerkalo;
   }

   public ActionSetting getSbros() {
      return this.sbros;
   }

   @Override
   public void onRotation(Rotation rotation) {
      if (this.viewModelController.isDouble(rotation.getValue2())) {
         rotation.setFlag(true);
      }
   }

   public SliderSetting getX() {
      return this.x;
   }

   public ViewModelController getViewModelController() {
      return this.viewModelController;
   }

   @Override
   public void onEnable() {
      this.viewModelController.update();
   }
}
