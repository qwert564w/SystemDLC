package client.util;

import client.concurrent.ConfigManager;
import client.data.ClientAccess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ThemeColors {
   public static final int value = 7;
   private static final int[] intArray = new int[]{-1, -16777216, -1096636, -429290, -340971, -14498466, -12877066};

   private ThemeColors() {
   }

   public static void onIntInt(int count, int count2) {
      if (count2 >= 0 && count2 < 7) {
         ConfigManager configmanager = ClientAccess.getConfigManager();
         if (configmanager != null) {
            List list = getList();
            list.set(count2, count);
            configmanager.onList2(list);
         }
      }
   }

   public static int[] getIntArray() {
      return Arrays.copyOf(intArray, intArray.length);
   }

   public static List getList() {
      ConfigManager configmanager = ClientAccess.getConfigManager();
      List object = configmanager != null ? configmanager.getList3() : new ArrayList();
      ArrayList arraylist = new ArrayList(7);

      for (int i = 0; i < 7; i++) {
         if (i < object.size() && object.get(i) != null) {
            arraylist.add((Integer)object.get(i));
         } else {
            arraylist.add(intArray[i]);
         }
      }

      return arraylist;
   }

   public static int getIntByInt(int count) {
      if (count >= 0 && count < 7) {
         List list = getList();
         return (Integer)list.get(count);
      } else {
         return 0;
      }
   }
}
