package recovered.fabric.offline;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import recovered.fabric.diagnostic.SystemDlcLog;

public final class OfflineConfigStore {
   private OfflineConfigStore() {
   }

   public static synchronized String call(int action, String payload) {
      try {
         return switch (action) {
            case 30 -> ConfigMutations.create(payload);
            case 31 -> ConfigMutations.save(payload);
            case 32 -> load(payload);
            case 33 -> ConfigResponses.list();
            case 34 -> ConfigMutations.delete(payload);
            case 35 -> ConfigMutations.rename(payload);
            case 36 -> ConfigMutations.describe(payload);
            case 37 -> ConfigMutations.activate(payload);
            case 38 -> ConfigResponses.active();
            case 39, 46 -> key(payload);
            case 40 -> importKey(payload);
            case 91, 92 -> ConfigResponses.ok();
            case 93 -> saveData(payload);
            default -> ConfigResponses.failure("unsupported config action");
            case 45 -> ConfigMutations.privacy(payload);
            case 47 -> keys(payload);
            case 48 -> ConfigResponses.ok();
         };
      } catch (Throwable throwable) {
         SystemDlcLog.error("offline config storage action=" + action, throwable);
         return ConfigResponses.failure("config storage failed");
      }
   }

   private static String saveData(String payload) throws Exception {
      JsonObject root = JsonParser.parseString(payload).getAsJsonObject();
      String name = root.has("configName") ? root.get("configName").getAsString() : "";
      String data = root.has("payload") ? root.get("payload").getAsString() : "";
      ConfigRecord config = ConfigFiles.find(name);
      if (config == null) {
         return ConfigResponses.failure("config not found");
      }
      ConfigFiles.write(config.withData(data));
      return ConfigResponses.ok();
   }

   private static String load(String payload) throws Exception {
      ConfigRecord config = named(payload);
      return config == null ? ConfigResponses.failure("config not found") : ConfigResponses.loaded(config);
   }

   private static String key(String payload) throws Exception {
      ConfigRecord config = named(payload);
      return config == null ? ConfigResponses.failure("config not found") : ConfigResponses.key(config);
   }

   private static String keys(String payload) throws Exception {
      ConfigRecord config = named(payload);
      return config == null ? ConfigResponses.failure("config not found") : ConfigResponses.keys(config);
   }

   private static String importKey(String payload) throws Exception {
      ConfigRecord config = ConfigFiles.findByKey(JsonField.text(payload, "configKey"));
      return config == null ? ConfigResponses.failure("config key not found") : ConfigResponses.config(config);
   }

   private static ConfigRecord named(String payload) throws Exception {
      return ConfigFiles.find(JsonField.text(payload, "configName"));
   }
}
