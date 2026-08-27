package client.concurrent;

import client.data.IconSheet;
import client.network.ConfigApi;
import client.render.ShaderAsset;
import client.render.ShaderSource;
import client.util.NameParts;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AssetIndex {
   private static final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
   private static final String[] stringArray = NameParts.getStringArrayByStringArray("arrow", "arrow_3d");
   private static Map<String, byte[]> map = null;
   private static Map<String, byte[]> map2 = null;
   private static List<ShaderSource> list = null;
   private static List<ShaderAsset> list2 = null;
   private static List<IconSheet> list3 = null;
   private static Map<String, String> map3 = null;

   private AssetIndex() {
   }

   public static Map<String, byte[]> getMap2() {
      return map2;
   }

   public static List<ShaderAsset> getList2() {
      return list2;
   }

   public static List<IconSheet> getList3() {
      return list3;
   }

   public static Map<String, String> getMap3() {
      return map3;
   }

   private static List getListByClassStringJsonObject(Class value, String text, JsonObject jsonObject) {
      if (jsonObject.has(text) && !jsonObject.get(text).isJsonNull()) {
         try {
            String s = jsonObject.get(text).getAsString();
            ArrayList arraylist = new ArrayList();
            JsonArray jsonarray = JsonParser.parseString(s).getAsJsonArray();
            Gson gson = new Gson();

            for (JsonElement jsonelement : jsonarray) {
               arraylist.add(gson.fromJson(jsonelement, value));
            }

            return arraylist;
         } catch (Exception exception) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static Map<String, byte[]> getMap() {
      return map;
   }

   private static Map getMapByJsonObject(JsonObject jsonObject) {
      if (jsonObject.has("translations") && !jsonObject.get("translations").isJsonNull()) {
         try {
            String s = jsonObject.get("translations").getAsString();
            JsonObject jsonobject = JsonParser.parseString(s).getAsJsonObject();
            HashMap hashmap = new HashMap();

            for (Entry entry : jsonobject.entrySet()) {
               if (!((JsonElement)entry.getValue()).isJsonNull()) {
                  hashmap.put((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
               }
            }

            return hashmap;
         } catch (Exception exception) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static Map getMapByStringJsonObject(String text, JsonObject jsonObject) {
      if (jsonObject.has(text) && jsonObject.get(text).isJsonObject()) {
         HashMap hashmap = new HashMap();

         for (Entry entry : jsonObject.getAsJsonObject(text).entrySet()) {
            if (!((JsonElement)entry.getValue()).isJsonNull()) {
               hashmap.put((String)entry.getKey(), Base64.getDecoder().decode(((JsonElement)entry.getValue()).getAsString()));
            }
         }

         return hashmap;
      } else {
         return null;
      }
   }

   public static void update() {
      if (atomicBoolean.compareAndSet(false, true)) {
         JsonObject jsonobject = new JsonObject();
         JsonArray jsonarray = new JsonArray();

         for (String s : stringArray) {
            jsonarray.add(s);
         }

         jsonobject.add("images", jsonarray);
         jsonobject.addProperty("sounds", true);
         jsonobject.addProperty("shaders", true);
         jsonobject.addProperty("fonts", true);
         jsonobject.addProperty("icons", true);
         JsonObject jsonobject1 = ConfigApi.getJsonObjectByJsonObject(jsonobject);
         if (jsonobject1 != null) {
            String s1 = "images";
            map = getMapByStringJsonObject(s1, jsonobject1);
            String s2 = "sounds";
            map2 = getMapByStringJsonObject(s2, jsonobject1);
            Class<ShaderSource> oclass = ShaderSource.class;
            String s3 = "shaders";
            list = getListByClassStringJsonObject(oclass, s3, jsonobject1);
            Class<ShaderAsset> oclass1 = ShaderAsset.class;
            String s4 = "fonts";
            list2 = getListByClassStringJsonObject(oclass1, s4, jsonobject1);
            Class<IconSheet> oclass2 = IconSheet.class;
            String s5 = "icons";
            list3 = getListByClassStringJsonObject(oclass2, s5, jsonobject1);
         }
      }
   }

   public static List<ShaderSource> getList() {
      return list;
   }
}
