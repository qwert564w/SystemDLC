package client.data;

import client.concurrent.AssetIndex;
import client.module.CategoryType;
import client.network.ConfigApi;
import client.render.SvgShader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class FactoryRegistry {
   private static final Map<String, IconSheet> map = new HashMap<>();
   private static boolean flag = false;

   private static void update() {
      for (CategoryType categorytype : CategoryType.values()) {
         IconSheet iconsheet = map.get(categorytype.getKey());
         if (iconsheet != null) {
            List<String> list = iconsheet.getList();
            String[] astring = list == null ? new String[0] : list.toArray(new String[0]);
            categorytype.set(iconsheet.getValue(), iconsheet.getValue2(), iconsheet.getValue3(), iconsheet.isFlag(), astring);
         }
      }
   }

   public static void update2() {
      map.clear();
      flag = false;
   }

   public static synchronized void update3() {
      if (!flag) {
         flag = true;
         Map map1 = map;
         List list1 = AssetIndex.getList3();
         Supplier supplier1 = ConfigApi::getList2;
         Function<IconSheet, String> function = IconSheet::getText;
         Supplier supplier = supplier1;
         List list = list1;
         Map mapx = map1;

         try {
            SupplierRegistry.onSupplierFunctionMapList(supplier, function, mapx, list);
         } catch (Throwable throwable) {
         }

         LocalIconLoader.load(map);
         update();
         SvgShader.update3();
      }
   }
}
