package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.ListSetting;
import client.setting.SliderSetting;
import java.util.List;

public class AspectRatio extends Module {
   public static ListSetting preset;
   public static SliderSetting strengthRastyaga;

   public AspectRatio() {
      super("AspectRatio", Category.VISUAL);
      this.addSetting(preset);
      this.addSetting(strengthRastyaga);
      strengthRastyaga.setVisibleWhen(AspectRatio::getBoolean);
   }

   static {
      ListSetting listsetting = new ListSetting("", "", List.of("16:9", "16:10", "4:3", "Custom"), List.of("4:3"), false);
      listsetting.setName("Пресет");
      listsetting.setDescription("Соотношение сторон");
      preset = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.1, 10.0, 0.1);
      slidersetting.setName("Сила растяга");
      slidersetting.setDescription("Степень горизонтального растяжения экрана");
      strengthRastyaga = slidersetting;
   }

   @Override
   public void onDisable() {
   }

   public static SliderSetting getStrengthRastyaga() {
      return strengthRastyaga;
   }

   private static Boolean getBoolean() {
      return "Custom".equals(preset.getString2());
   }

   public static float getFloat() {
      String s = preset.getString2();

      return switch (s) {
         case "16:10" -> 2.2F;
         case "4:3" -> 1.6F;
         case "Custom" -> strengthRastyaga.getValueAsFloat();
         default -> 1.0F;
      };
   }

   public static boolean check3() {
      return "16:9".equals(preset.getString2());
   }

   @Override
   public void onEnable() {
   }
}
