package client.data;

public record ConfigBlob(String configName, String compressed) {
   public String getCompressed() {
      return this.compressed;
   }

   public String getConfigName() {
      return this.configName;
   }
}
