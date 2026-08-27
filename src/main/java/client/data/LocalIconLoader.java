package client.data;

import client.module.CategoryType;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Reads the icon sheets shipped inside the mod jar. The remote asset package is still
 * preferred when it arrives, this only guarantees the interface has icons without it.
 */
public final class LocalIconLoader {
   private static final String directory = "/assets/systemdlc/icons/";
   private static final Gson gson = new Gson();

   private LocalIconLoader() {
   }

   public static void load(Map<String, IconSheet> map) {
      for (CategoryType categorytype : CategoryType.values()) {
         String s = categorytype.getKey();
         if (s != null && !s.isEmpty() && !map.containsKey(s)) {
            IconSheet iconsheet = read(s);
            if (iconsheet != null) {
               map.put(s, iconsheet);
            }
         }
      }
   }

   private static IconSheet read(String name) {
      try (InputStream inputstream = LocalIconLoader.class.getResourceAsStream(directory + name + ".json")) {
         if (inputstream == null) {
            return null;
         }

         IconSheet iconsheet = gson.fromJson(new InputStreamReader(inputstream, StandardCharsets.UTF_8), IconSheet.class);
         List<String> list = iconsheet == null ? null : iconsheet.getList();
         return list == null || list.isEmpty() ? null : iconsheet;
      } catch (Exception exception) {
         return null;
      }
   }
}
