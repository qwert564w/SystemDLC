package client.concurrent;

import client.network.ConfigApi;
import client.render.ArrowTextureLoader;
import client.util.NameParts;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class AssetLoader {
   private static final Map<String, byte[]> map = new ConcurrentHashMap<>();
   private static final CompletableFuture<Void> completableFuture = new CompletableFuture<>();
   private static final String[] stringArray = NameParts.getStringArrayByStringArray("arrow", "arrow_3d");

   public AssetLoader() {
      new Thread(() -> {
         update3();
         completableFuture.complete(null);
         ArrowTextureLoader.update3();
      }, "i-t").start();
   }

   public void update() {
      for (byte[] abyte : map.values()) {
         if (abyte != null) {
            Arrays.fill(abyte, (byte)0);
         }
      }

      map.clear();
   }

   public static String[] getStringArray() {
      return (String[])stringArray.clone();
   }

   public static void update3() {
      try {
         Map mapx = AssetIndex.getMap();
         if (mapx == null) {
            mapx = ConfigApi.getMapByStringArray(stringArray);
         }

         for (Entry entry : (Iterable<Entry>)(mapx.entrySet())) {
            if (entry.getValue() != null && ((byte[])entry.getValue()).length > 0) {
               map.put((String)entry.getKey(), (byte[])entry.getValue());
            }
         }
      } catch (Exception exception) {
      }
   }

   public static byte[] getByteArrayByString(String text) {
      completableFuture.join();
      return map.get(text);
   }
}
