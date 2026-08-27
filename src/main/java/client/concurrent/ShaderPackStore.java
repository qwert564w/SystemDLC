package client.concurrent;

import client.data.SupplierRegistry;
import client.network.ConfigApi;
import client.render.ShaderAsset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ShaderPackStore {
   private static final Map<String, ShaderAsset> map = new HashMap<>();
   private static final Map<String, byte[]> map2 = new ConcurrentHashMap<>();
   private static boolean flag = false;

   public static void update() {
      map.clear();
      map2.clear();
      flag = false;
   }

   public static byte[] getByteArrayByString(String text) {
      byte[] abyte = map2.get(text);
      if (abyte != null) {
         return abyte;
      } else {
         byte[] abyte1 = AssetLoader.getByteArrayByString(text);
         if (abyte1 != null) {
            map2.put(text, abyte1);
         }

         return abyte1;
      }
   }

   public static ShaderAsset getShaderAssetByString(String text) {
      if (!flag) {
         update2();
      }

      return map.get(text);
   }

   public static synchronized void update2() {
      if (!flag) {
         flag = true;
         Map map1 = map;
         List list1 = AssetIndex.getList2();
         Supplier supplier1 = ConfigApi::getList;
         Function<ShaderAsset, String> function = ShaderAsset::getText;
         Supplier supplier = supplier1;
         List list = list1;
         Map mapx = map1;
         SupplierRegistry.onSupplierFunctionMapList(supplier, function, mapx, list);
      }
   }
}
