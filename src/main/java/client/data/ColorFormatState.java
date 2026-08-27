package client.data;

import client.concurrent.ConfigManager;
import client.concurrent.SystemClient;
import client.enums.ColorFormat;

public final class ColorFormatState {
   private ColorFormatState() {
   }

   public static ColorFormat getColorFormat() {
      try {
         ConfigManager configmanager = ClientAccess.getConfigManager();
         if (configmanager != null && configmanager.getConfigData() != null) {
            String s = configmanager.getConfigData().getText4();
            if (s != null) {
               return ColorFormat.getColorFormatByString(s);
            }
         }
      } catch (Exception exception) {
      }

      return ColorFormat.RGB;
   }

   public static void onColorFormat(ColorFormat colorFormat) {
      try {
         ConfigManager configmanager = ClientAccess.getConfigManager();
         if (configmanager == null) {
            return;
         }

         ConfigData configdata = configmanager.getConfigData();
         if (configdata == null) {
            configdata = new ConfigData();
            configmanager.setConfigData(configdata);
         }

         configdata.setText4(colorFormat.name());
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient != null && systemclient.getHashUtil() != null) {
            systemclient.getHashUtil().update5();
         }
      } catch (Exception exception) {
      }
   }
}
