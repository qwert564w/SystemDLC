package client.gui.widget;

import client.api.ColorSupplier;
import client.module.CategoryType;
import client.setting.ResourceIndexSetting;

public class ResourceSearchPanel extends ModuleWidget<ResourceFinderPanel> {
   public ResourceSearchPanel(ResourceIndexSetting resourceIndexSetting) {
      super(resourceIndexSetting, new ResourceFinderPanel(resourceIndexSetting));
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.SEARCH;
   }

   @Override
   protected void onColorSupplier(ColorSupplier colorSupplier) {
      this.buttonWidget.setColorSupplier2(colorSupplier);
   }

   @Override
   protected String getString() {
      return "Поиск ресурсов";
   }
}
