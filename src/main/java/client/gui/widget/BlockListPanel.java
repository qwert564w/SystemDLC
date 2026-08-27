package client.gui.widget;

import client.api.ColorSupplier;
import client.module.CategoryType;
import client.setting.BlocklistSetting;

public class BlockListPanel extends ModuleWidget<BlockEspPanel> {
   public BlockListPanel(BlocklistSetting blocklistSetting) {
      super(blocklistSetting, new BlockEspPanel(blocklistSetting));
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.SETTING_DROPDOWN;
   }

   @Override
   protected void onColorSupplier(ColorSupplier colorSupplier) {
      this.buttonWidget.setColorSupplier2(colorSupplier);
   }

   @Override
   protected String getString() {
      return "Список блоков";
   }
}
