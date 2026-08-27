package client.concurrent;

import client.enums.Language;
import client.network.ConfigApi;
import client.util.TextHash;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Translations {
   private static final Translations INSTANCE = new Translations();
   private Language language = Language.RU;
   private final Map<String, String> map = new ConcurrentHashMap<>();
   private final List<Runnable> list = new CopyOnWriteArrayList<>();
   private boolean flag = false;

   private Translations() {
   }

   public String getStringByString(String text) {
      return text != null && this.language != Language.RU && this.flag ? this.map.get(text) : null;
   }

   public void update() {
      this.language = this.language.getLanguage();
      if (this.language == Language.RU) {
         this.map.clear();
         this.flag = false;
      }

      this.update2();
   }

   private void update2() {
      for (Runnable runnable : this.list) {
         try {
            runnable.run();
         } catch (Exception exception) {
         }
      }
   }

   public void update3() {
      try {
         Map mapx = AssetIndex.getMap3();
         if (mapx == null) {
            mapx = ConfigApi.getMapByString("en");
         }

         if (mapx != null && !mapx.isEmpty()) {
            this.map.clear();
            this.map.putAll(mapx);
            this.flag = true;
         }
      } catch (Exception exception) {
      }
   }

   public Language getLanguage() {
      return this.language;
   }

   public void addRunnable(Runnable runnable) {
      this.list.add(runnable);
   }

   public static Translations getInstance() {
      return INSTANCE;
   }

   public String getStringByString2(String text) {
      if (text == null || text.isEmpty()) {
         return text;
      } else {
         return this.language != Language.RU && this.flag ? this.map.getOrDefault(Long.toHexString(TextHash.getLongByString(text)), text) : text;
      }
   }
}
