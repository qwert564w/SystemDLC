package recovered.fabric.offline;

import java.util.function.UnaryOperator;

public final class ConfigMutations {
   private ConfigMutations() {
   }

   public static String create(String payload) throws Exception {
      String name = required(payload, "configName");
      if (ConfigFiles.find(name) != null) {
         return ConfigResponses.failure("config already exists");
      } else {
         ConfigRecord config = ConfigRecord.create(name, text(payload, "configDescription"), required(payload, "configData"));
         ConfigFiles.write(config);
         return ConfigResponses.config(config);
      }
   }

   public static String save(String payload) throws Exception {
      return update(payload, config -> config.withData(required(payload, "configData")));
   }

   public static String delete(String payload) throws Exception {
      ConfigFiles.delete(required(payload, "configName"));
      return ConfigResponses.ok();
   }

   public static String rename(String payload) throws Exception {
      String oldName = required(payload, "configName");
      String newName = required(payload, "newConfigName");
      ConfigRecord config = ConfigFiles.find(oldName);
      if (config == null) {
         return ConfigResponses.failure("config not found");
      } else if (ConfigFiles.find(newName) != null) {
         return ConfigResponses.failure("config already exists");
      } else {
         ConfigFiles.rename(oldName, config.withName(newName));
         return ConfigResponses.ok();
      }
   }

   public static String describe(String payload) throws Exception {
      return update(payload, config -> config.withDescription(text(payload, "configDescription")));
   }

   public static String activate(String payload) throws Exception {
      String name = required(payload, "configName");
      if (ConfigFiles.find(name) == null) {
         return ConfigResponses.failure("config not found");
      } else {
         ConfigFiles.active(name);
         return ConfigResponses.ok();
      }
   }

   public static String privacy(String payload) throws Exception {
      boolean value = JsonField.bool(payload, "isPrivateConfig");
      return update(payload, config -> config.withPrivacy(value));
   }

   private static String update(String payload, UnaryOperator<ConfigRecord> change) throws Exception {
      String name = required(payload, "configName");
      ConfigRecord config = ConfigFiles.find(name);
      if (config == null) {
         return ConfigResponses.failure("config not found");
      } else {
         ConfigFiles.write(change.apply(config));
         return ConfigResponses.ok();
      }
   }

   private static String required(String payload, String field) {
      String value = JsonField.text(payload, field);
      if (value != null && !value.isBlank()) {
         return value;
      } else {
         throw new IllegalArgumentException(field + " empty");
      }
   }

   private static String text(String payload, String field) {
      String value = JsonField.text(payload, field);
      return value == null ? "" : value;
   }
}
