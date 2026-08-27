package recovered.fabric.offline;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

public record ConfigRecord(String name, String description, String data, String key, String createdAt, String updatedAt, boolean privateConfig) {
   public static ConfigRecord create(String name, String description, String data) {
      String now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
      String key = UUID.randomUUID().toString().replace("-", "");
      return new ConfigRecord(name, description, data, key, now, now, false);
   }

   public ConfigRecord withData(String value) {
      return this.changed(this.name, this.description, value, this.privateConfig);
   }

   public ConfigRecord withDescription(String value) {
      return this.changed(this.name, value, this.data, this.privateConfig);
   }

   public ConfigRecord withName(String value) {
      return this.changed(value, this.description, this.data, this.privateConfig);
   }

   public ConfigRecord withPrivacy(boolean value) {
      return this.changed(this.name, this.description, this.data, value);
   }

   public String serialize() {
      return String.join(
         "\n",
         encode(this.name),
         encode(this.description),
         encode(this.data),
         encode(this.key),
         encode(this.createdAt),
         encode(this.updatedAt),
         Boolean.toString(this.privateConfig)
      );
   }

   public static ConfigRecord deserialize(String value) {
      String[] fields = value.split("\\R", -1);
      if (fields.length != 7) {
         throw new IllegalArgumentException("invalid config record");
      } else {
         return new ConfigRecord(
            decode(fields[0]), decode(fields[1]), decode(fields[2]), decode(fields[3]), decode(fields[4]), decode(fields[5]), Boolean.parseBoolean(fields[6])
         );
      }
   }

   public String json(boolean active) {
      return "{\"config_name\":"
         + JsonField.quote(this.name)
         + ",\"description\":"
         + JsonField.quote(this.description)
         + ",\"config_key\":"
         + JsonField.quote(this.key)
         + ",\"is_active\":"
         + active
         + ",\"is_private\":"
         + this.privateConfig
         + ",\"is_imported\":false,\"created_at\":"
         + JsonField.quote(this.createdAt)
         + ",\"updated_at\":"
         + JsonField.quote(this.updatedAt)
         + "}";
   }

   private ConfigRecord changed(String nextName, String nextDescription, String nextData, boolean nextPrivacy) {
      String now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
      return new ConfigRecord(nextName, nextDescription, nextData, this.key, this.createdAt, now, nextPrivacy);
   }

   private static String encode(String value) {
      return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
   }

   private static String decode(String value) {
      return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
   }
}
