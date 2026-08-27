package recovered.fabric.offline;

import java.util.List;

public final class ConfigResponses {
   private static final String prefix = "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":";

   private ConfigResponses() {
   }

   public static String ok() {
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{}}";
   }

   public static String config(ConfigRecord config) throws Exception {
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":" + config.json(config.name().equals(ConfigFiles.active())) + "}";
   }

   public static String list() throws Exception {
      List<ConfigRecord> configs = ConfigFiles.all();
      String active = ConfigFiles.active();
      StringBuilder data = new StringBuilder("{\"configs\":[");

      for (int index = 0; index < configs.size(); index++) {
         if (index > 0) {
            data.append(',');
         }

         ConfigRecord config = configs.get(index);
         data.append(config.json(config.name().equals(active)));
      }

      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":" + data.append("]}}").toString();
   }

   public static String loaded(ConfigRecord config) {
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{},\"configData\":" + JsonField.quote(config.data()) + "}";
   }

   public static String active() throws Exception {
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{\"config_name\":" + JsonField.quote(ConfigFiles.active()) + "}}";
   }

   public static String key(ConfigRecord config) {
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{\"config_key\":" + JsonField.quote(config.key()) + "}}";
   }

   public static String keys(ConfigRecord config) {
      String item = "{\"key_value\":"
         + JsonField.quote(config.key())
         + ",\"used\":false,\"created_at\":"
         + JsonField.quote(config.createdAt())
         + ",\"used_at\":null}";
      return "{\"success\":true,\"message\":\"offline\",\"status\":\"AUTHORIZED\",\"data\":{\"keys\":[" + item + "]}}";
   }

   public static String failure(String message) {
      return "{\"success\":false,\"message\":" + JsonField.quote(message) + ",\"status\":\"OFFLINE_ERROR\",\"data\":{}}";
   }
}
