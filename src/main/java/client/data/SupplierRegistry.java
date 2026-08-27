package client.data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class SupplierRegistry {
   private SupplierRegistry() {
   }

   public static void onSupplierFunctionMapList(Supplier supplier, Function function2, Map map, List list2) {
      List list = list2;
      if (list2 == null) {
         try {
            list = (List)supplier.get();
         } catch (Throwable throwable) {
            return;
         }
      }

      if (list != null) {
         for (Object object : list) {
            if (object != null) {
               String s = (String)function2.apply(object);
               if (s != null) {
                  map.put(s, object);
               }
            }
         }
      }
   }
}
