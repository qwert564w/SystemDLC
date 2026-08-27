package client.data;

import client.gui.widget.ActionRow;
import client.gui.widget.AlignmentRow;
import client.gui.widget.BlockListPanel;
import client.gui.widget.BooleanRow;
import client.gui.widget.ChoiceRow;
import client.gui.widget.ColorPickerRow;
import client.gui.widget.DropdownRow;
import client.gui.widget.FilterDropdown;
import client.gui.widget.FilterMenuRow;
import client.gui.widget.NameListPanel;
import client.gui.widget.PanelWidget;
import client.gui.widget.ResourceSearchPanel;
import client.gui.widget.SettingRow;
import client.gui.widget.SliderRow;
import client.gui.widget.SwapWheelRow;
import client.gui.widget.TextInputRow;
import client.setting.ActionSetting;
import client.setting.AlignmentSetting;
import client.setting.BlocklistSetting;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.ColorSetting;
import client.setting.CompactGroupSetting;
import client.setting.FilterMenuSetting;
import client.setting.HotkeySetting;
import client.setting.InputSetting;
import client.setting.KeybindSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.ResourceIndexSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.setting.StafflistSetting;
import client.setting.SwapWheelSetting;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public class SettingWidgetFactory {
   private static final Map<Class<? extends Setting>, Function<Setting, PanelWidget>> map = new LinkedHashMap<>();

   private SettingWidgetFactory() {
   }

   static {
      onClassFunction(ChoiceSetting.class, var0 -> new ChoiceRow((ChoiceSetting)var0));
      onClassFunction(FilterMenuSetting.class, var0 -> new FilterMenuRow((FilterMenuSetting)var0));
      onClassFunction(ResourceIndexSetting.class, var0 -> new ResourceSearchPanel((ResourceIndexSetting)var0));
      onClassFunction(BooleanSetting.class, var0 -> new BooleanRow((BooleanSetting)var0));
      onClassFunction(SliderSetting.class, var0 -> new SliderRow((SliderSetting)var0));
      onClassFunction(MultilistSetting.class, var0 -> new FilterDropdown((MultilistSetting)var0));
      onClassFunction(ListSetting.class, var0 -> new FilterDropdown((ListSetting)var0));
      onClassFunction(ColorSetting.class, var0 -> new ColorPickerRow((ColorSetting)var0));
      onClassFunction(BlocklistSetting.class, var0 -> new BlockListPanel((BlocklistSetting)var0));
      onClassFunction(StafflistSetting.class, var0 -> new NameListPanel((StafflistSetting)var0));
      onClassFunction(InputSetting.class, var0 -> new TextInputRow((InputSetting)var0));
      onClassFunction(KeybindSetting.class, var0 -> new DropdownRow((Setting)var0));
      onClassFunction(HotkeySetting.class, var0 -> new DropdownRow((Setting)var0));
      onClassFunction(AlignmentSetting.class, var0 -> new AlignmentRow((AlignmentSetting)var0));
      onClassFunction(SwapWheelSetting.class, var0 -> new SwapWheelRow((SwapWheelSetting)var0));
      onClassFunction(ActionSetting.class, var0 -> new ActionRow((ActionSetting)var0));
      onClassFunction(CompactGroupSetting.class, var0 -> new SettingRow((CompactGroupSetting)var0));
   }

   public static PanelWidget getPanelWidgetBySetting10(Setting setting2) {
      if (setting2 == null) {
         return null;
      } else {
         for (Entry entry : map.entrySet()) {
            if (((Class)entry.getKey()).isInstance(setting2)) {
               return (PanelWidget)((Function)entry.getValue()).apply(setting2);
            }
         }

         return null;
      }
   }

   public static void onClassFunction(Class value, Function function2) {
      map.put(value, function2);
   }
}
