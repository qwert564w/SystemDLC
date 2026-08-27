package client.data;

import client.concurrent.ShaderPackStore;
import client.render.ShaderAsset;
import client.util.NameParts;
import client.util.ResourceLoader;
import java.util.HashMap;
import java.util.Map;

public class StringPool {
   private final Map<String, ResourceLoader> map = new HashMap<>();
   private static StringPool INSTANCE;

   public StringPool() {
      ShaderPackStore.update2();

      for (String s : NameParts.getStringArray()) {
         this.onString(s);
      }
   }

   public Map<String, ResourceLoader> getMap() {
      return this.map;
   }

   public static StringPool getStringPool() {
      StringPool stringpool = INSTANCE;
      if (stringpool == null) {
         synchronized (StringPool.class) {
            stringpool = INSTANCE;
            if (stringpool == null) {
               stringpool = new StringPool();
               INSTANCE = stringpool;
            }
         }
      }

      return stringpool;
   }

   private void onString(String text) {
      ShaderAsset shaderasset = ShaderPackStore.getShaderAssetByString(text);
      if (shaderasset != null) {
         Map mapx = this.map;
         String s1 = text + ".json";
         String s2 = "fonts/" + text + ".json";
         byte[] abyte = shaderasset.getByteArray();
         String s = s2;
         mapx.put(s1, ResourceLoader.getResourceLoaderByByteArrayString(abyte, s));
      }
   }
}
