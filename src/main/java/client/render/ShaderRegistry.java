package client.render;

import client.concurrent.AssetIndex;
import client.data.SupplierRegistry;
import client.network.ConfigApi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ShaderRegistry {
   private static final Map<String, ShaderSource> map = new HashMap<>();
   private static boolean flag = false;

   public static void update() {
      map.clear();
      flag = false;
   }

   public static Collection getCollection() {
      if (!flag) {
         update2();
      }

      return new ArrayList<>(map.values());
   }

   public static synchronized void update2() {
      if (!flag) {
         flag = true;
         Map map1 = map;
         List list1 = AssetIndex.getList();
         Supplier supplier1 = ConfigApi::getList4;
         Function<ShaderSource, String> function = var0x -> var0x.getString() + "/" + var0x.getText();
         Supplier supplier = supplier1;
         List list = list1;
         Map mapx = map1;
         SupplierRegistry.onSupplierFunctionMapList(supplier, function, mapx, list);
      }
   }
}
