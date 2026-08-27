package client.data;

import client.concurrent.ConfigManager;
import client.concurrent.ModuleRegistry;
import client.concurrent.SystemClient;
import client.module.Category;
import client.module.Module;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public final class ClientAccess {
   private ClientAccess() {
   }

   public static ConfigManager getConfigManager() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient != null ? systemclient.getConfigManager() : null;
   }

   public static List getListByCategory(Category category2) {
      ModuleRegistry moduleregistry = getModuleRegistry();
      if (moduleregistry == null) {
         return List.of();
      } else if (category2 == null) {
         return moduleregistry.getList32();
      } else {
         List list = moduleregistry.getListByCategory2(category2);
         if (list == null) {
            return List.of();
         } else {
            for (Category category : Category.values()) {
               if (category.check() && category.getCategory() == category2) {
                  List list1 = moduleregistry.getListByCategory2(category);
                  if (list1 != null) {
                     list.addAll(list1);
                  }
               }
            }

            return list;
         }
      }
   }

   public static List getList() {
      ModuleRegistry moduleregistry = getModuleRegistry();
      ConfigManager configmanager = getConfigManager();
      if (moduleregistry != null && configmanager != null) {
         HashSet hashset = new HashSet(configmanager.getList());
         if (hashset.isEmpty()) {
            return List.of();
         } else {
            ArrayList arraylist = new ArrayList();

            for (Module module : moduleregistry.getList32()) {
               if (hashset.contains(module.getName())) {
                  arraylist.add(module);
               }
            }

            return arraylist;
         }
      } else {
         return List.of();
      }
   }

   public static ModuleRegistry getModuleRegistry() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient != null ? systemclient.getModuleRegistry() : null;
   }

   public static void onStringBoolean(String text, boolean flag) {
      ConfigManager configmanager = getConfigManager();
      if (configmanager != null && text != null) {
         LinkedHashSet linkedhashset = new LinkedHashSet(configmanager.getList());
         if (flag) {
            linkedhashset.add(text);
         } else {
            linkedhashset.remove(text);
         }

         configmanager.onList3(new ArrayList(linkedhashset));
      }
   }

   public static boolean isString(String text) {
      ConfigManager configmanager = getConfigManager();
      return configmanager != null && text != null ? configmanager.getList().contains(text) : false;
   }
}
