package client.gui.widget;

import client.api.ColorSupplier;
import client.module.CategoryType;
import client.setting.StafflistSetting;

public class NameListPanel extends ModuleWidget<NickListInput> {
   public NameListPanel(StafflistSetting stafflistSetting) {
      super(stafflistSetting, new NickListInput(stafflistSetting));
      this.buttonWidget.setFlag5(false);
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.FRIENDS;
   }

   @Override
   protected boolean check4() {
      return true;
   }

   @Override
   protected void onColorSupplier(ColorSupplier colorSupplier) {
      this.buttonWidget.setColorSupplier(colorSupplier);
   }

   @Override
   protected String getString() {
      return "Свои ники";
   }
}
