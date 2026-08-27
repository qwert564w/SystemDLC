package client.network;

import b.Boot;
import client.data.ConfigEntry;
import client.data.IconSheetListToken;
import client.data.LabelData;
import client.data.ShaderAssetListToken;
import client.data.ShaderSourceListToken;
import client.transform.NativeBridgeUtil;
import client.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConfigApi {
   private static final Gson gson = new Gson();
   private static boolean flag = false;

   public static boolean isStringString(String text, String text2) {
      JsonObject jsonobject = getJsonObjectByString("93");
      jsonobject.addProperty("configName", text2);
      jsonobject.addProperty("payload", text);
      return isJsonObject(jsonobject);
   }

   public static String getStringByString(String text) {
      JsonObject jsonobject = getJsonObjectByString("39");
      jsonobject.addProperty("configName", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      if (isJsonObject2(jsonobject1) && jsonobject1.has("data")) {
         JsonObject jsonobject2 = jsonobject1.getAsJsonObject("data");
         return jsonobject2.has("config_key") ? jsonobject2.get("config_key").getAsString() : null;
      } else {
         return null;
      }
   }

   private static boolean isJsonObject(JsonObject jsonObject) {
      return isJsonObject2(getJsonObjectByJsonObject2(jsonObject));
   }

   public static void update() {
      ServerUtil.update();
      JsonObject jsonobject = getJsonObjectByString("4");
      jsonobject.addProperty("lastMinecraftName", ServerUtil.getText());
      String s = ServerUtil.getText2();
      if (s != null) {
         jsonobject.addProperty("currentServer", s);
      }

      getJsonObjectByJsonObject2(jsonobject);
   }

   public static JsonObject getJsonObjectByJsonObject(JsonObject jsonObject) {
      JsonObject jsonobject = getJsonObjectByString("56");
      jsonobject.add("assets", jsonObject);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      return isJsonObject2(jsonobject1) && jsonobject1.has("data") ? jsonobject1.getAsJsonObject("data") : null;
   }

   public static boolean isStringString2(String text, String text2) {
      JsonObject jsonobject = getJsonObjectByString("35");
      jsonobject.addProperty("configName", text2);
      jsonobject.addProperty("newConfigName", text);
      return isJsonObject(jsonobject);
   }

   public static boolean isString(String text) {
      JsonObject jsonobject = getJsonObjectByString("37");
      jsonobject.addProperty("configName", text);
      return isJsonObject(jsonobject);
   }

   public static List getList() {
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(getJsonObjectByString("13"));
      Type type = new ShaderAssetListToken().getType();
      String s = "fonts";
      JsonObject jsonobject = jsonobject1;
      return getListByStringJsonObjectType(s, jsonobject, type);
   }

   public static boolean isStringString3(String text, String text2) {
      JsonObject jsonobject = getJsonObjectByString("36");
      jsonobject.addProperty("configName", text);
      jsonobject.addProperty("configDescription", text2);
      return isJsonObject(jsonobject);
   }

   public static boolean isString2(String text) {
      JsonObject jsonobject = getJsonObjectByString("91");
      jsonobject.addProperty("payload", text);
      return isJsonObject(jsonobject);
   }

   public static List getList2() {
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(getJsonObjectByString("15"));
      Type type = new IconSheetListToken().getType();
      String s = "icons";
      JsonObject jsonobject = jsonobject1;
      return getListByStringJsonObjectType(s, jsonobject, type);
   }

   public static String getStringByString2(String text) {
      JsonObject jsonobject = getJsonObjectByString("46");
      jsonobject.addProperty("configName", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      return isJsonObject2(jsonobject1) && jsonobject1.has("data") ? getStringByJsonObjectString(jsonobject1.getAsJsonObject("data"), "config_key") : null;
   }

   public static void update2() {
      JsonObject jsonobject = getJsonObjectByString("5");
      jsonobject.addProperty("lastMinecraftName", ServerUtil.getText());
      getJsonObjectByJsonObject2(jsonobject);
   }

   public static boolean isStringString4(String text, String text2) {
      JsonObject jsonobject = getJsonObjectByString("48");
      jsonobject.addProperty("configName", text);
      jsonobject.addProperty("configKey", text2);
      return isJsonObject(jsonobject);
   }

   public static List getList3() {
      JsonObject jsonobject = getJsonObjectByJsonObject2(getJsonObjectByString("33"));
      ArrayList arraylist = new ArrayList();
      if (isJsonObject2(jsonobject) && jsonobject.has("data")) {
         JsonObject jsonobject1 = jsonobject.getAsJsonObject("data");
         if (!jsonobject1.has("configs")) {
            return arraylist;
         } else {
            for (JsonElement jsonelement : jsonobject1.getAsJsonArray("configs")) {
               JsonObject jsonobject2 = jsonelement.getAsJsonObject();
               ConfigEntry configentry = new ConfigEntry();
               configentry.setText(getStringByJsonObjectString(jsonobject2, "config_name"));
               configentry.setText2(getStringByJsonObjectString(jsonobject2, "description"));
               configentry.setText3(getStringByJsonObjectString(jsonobject2, "config_key"));
               String s = "is_active";
               configentry.setFlag(isStringJsonObject(s, jsonobject2));
               String s1 = "is_private";
               configentry.setFlag2(isStringJsonObject(s1, jsonobject2));
               String s2 = "is_imported";
               configentry.setFlag3(isStringJsonObject(s2, jsonobject2));
               configentry.setText4(DateUtil.getStringByString3(getStringByJsonObjectString(jsonobject2, "created_at")));
               configentry.setText5(getStringByJsonObjectString(jsonobject2, "updated_at"));
               arraylist.add(configentry);
            }

            return arraylist;
         }
      } else {
         return arraylist;
      }
   }

   public static void update3() {
      if (!flag) {
         update2();
      }

      flag = true;

      try {
         Boot.shutdown();
      } catch (UnsatisfiedLinkError unsatisfiedlinkerror) {
      }
   }

   private static AuthResult getAuthResultByJsonObject(JsonObject jsonObject) {
      boolean flagx = jsonObject.has("success") && jsonObject.get("success").getAsBoolean();
      String s = jsonObject.has("message") ? jsonObject.get("message").getAsString() : "";
      String s1 = jsonObject.has("status") ? jsonObject.get("status").getAsString() : "UNKNOWN";
      HashMap hashmap = new HashMap();
      if (jsonObject.has("data") && jsonObject.get("data").isJsonObject()) {
         for (Entry entry : jsonObject.getAsJsonObject("data").entrySet()) {
            if (!((JsonElement)entry.getValue()).isJsonNull()) {
               hashmap.put((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
            }
         }
      }

      return new AuthResult(flagx, s, s1, hashmap);
   }

   public static boolean isBooleanString(boolean flag, String text) {
      JsonObject jsonobject = getJsonObjectByString("45");
      jsonobject.addProperty("configName", text);
      jsonobject.addProperty("isPrivateConfig", flag);
      return isJsonObject(jsonobject);
   }

   public static ConfigEntry getConfigEntryByString(String text) {
      JsonObject jsonobject = getJsonObjectByString("40");
      jsonobject.addProperty("configKey", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      if (isJsonObject2(jsonobject1) && jsonobject1.has("data")) {
         JsonObject jsonobject2 = jsonobject1.getAsJsonObject("data");
         ConfigEntry configentry = new ConfigEntry();
         configentry.setText(getStringByJsonObjectString(jsonobject2, "config_name"));
         configentry.setText3(getStringByJsonObjectString(jsonobject2, "config_key"));
         configentry.setFlag(false);
         String s = "is_imported";
         configentry.setFlag3(isStringJsonObject(s, jsonobject2));
         configentry.setText4(DateUtil.getStringByString3(getStringByJsonObjectString(jsonobject2, "created_at")));
         return configentry;
      } else {
         return null;
      }
   }

   private static String getStringByJsonObjectString(JsonObject jsonObject, String text) {
      return jsonObject.has(text) && !jsonObject.get(text).isJsonNull() ? jsonObject.get(text).getAsString() : null;
   }

   public static JsonArray getJsonArrayByString(String text) {
      JsonObject jsonobject = getJsonObjectByString("92");
      jsonobject.addProperty("configName", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      if (isJsonObject2(jsonobject1) && jsonobject1.has("data")) {
         JsonObject jsonobject2 = jsonobject1.getAsJsonObject("data");
         return jsonobject2.has("waypoints") && jsonobject2.get("waypoints").isJsonArray() ? jsonobject2.getAsJsonArray("waypoints") : null;
      } else {
         return null;
      }
   }

   private static List getListByStringJsonObjectType(String text, JsonObject jsonObject, Type type) {
      if (isJsonObject2(jsonObject) && jsonObject.has("data")) {
         JsonObject jsonobject = jsonObject.getAsJsonObject("data");
         if (!jsonobject.has(text)) {
            return new ArrayList();
         } else {
            String s = jsonobject.get(text).getAsString();
            return (List)gson.fromJson(s, type);
         }
      } else {
         return new ArrayList();
      }
   }

   private static Map getMapByStringJsonObject(String text, JsonObject jsonObject) {
      HashMap hashmap = new HashMap();
      if (isJsonObject2(jsonObject) && jsonObject.has("data")) {
         JsonObject jsonobject = jsonObject.getAsJsonObject("data");
         if (jsonobject.has(text) && jsonobject.get(text).isJsonObject()) {
            for (Entry entry : jsonobject.getAsJsonObject(text).entrySet()) {
               if (!((JsonElement)entry.getValue()).isJsonNull()) {
                  hashmap.put((String)entry.getKey(), Base64.getDecoder().decode(((JsonElement)entry.getValue()).getAsString()));
               }
            }

            return hashmap;
         } else {
            return hashmap;
         }
      } else {
         return hashmap;
      }
   }

   private static boolean isStringJsonObject(String text, JsonObject jsonObject) {
      if (jsonObject.has(text) && !jsonObject.get(text).isJsonNull()) {
         JsonElement jsonelement = jsonObject.get(text);
         if (jsonelement.isJsonPrimitive()) {
            JsonPrimitive jsonprimitive = jsonelement.getAsJsonPrimitive();
            if (jsonprimitive.isBoolean()) {
               return jsonprimitive.getAsBoolean();
            }

            if (jsonprimitive.isNumber()) {
               return jsonprimitive.getAsInt() != 0;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static JsonArray getJsonArray() {
      JsonObject jsonobject = getJsonObjectByJsonObject2(getJsonObjectByString("90"));
      if (isJsonObject2(jsonobject) && jsonobject.has("data")) {
         JsonObject jsonobject1 = jsonobject.getAsJsonObject("data");
         return jsonobject1.has("friends") && jsonobject1.get("friends").isJsonArray() ? jsonobject1.getAsJsonArray("friends") : null;
      } else {
         return null;
      }
   }

   public static List getListByString(String text) {
      JsonObject jsonobject = getJsonObjectByString("47");
      jsonobject.addProperty("configName", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      ArrayList arraylist = new ArrayList();
      if (isJsonObject2(jsonobject1) && jsonobject1.has("data")) {
         JsonObject jsonobject2 = jsonobject1.getAsJsonObject("data");
         if (jsonobject2.has("keys") && jsonobject2.get("keys").isJsonArray()) {
            for (JsonElement jsonelement : jsonobject2.getAsJsonArray("keys")) {
               JsonObject jsonobject3 = jsonelement.getAsJsonObject();
               LabelData labeldata = new LabelData();
               labeldata.setText(getStringByJsonObjectString(jsonobject3, "key_value"));
               String s1 = "used";
               labeldata.setFlag(isStringJsonObject(s1, jsonobject3));
               labeldata.setText2(DateUtil.getStringByString3(getStringByJsonObjectString(jsonobject3, "created_at")));
               String s = getStringByJsonObjectString(jsonobject3, "used_at");
               labeldata.setText3(s == null ? null : DateUtil.getStringByString3(s));
               arraylist.add(labeldata);
            }

            return arraylist;
         } else {
            return arraylist;
         }
      } else {
         return arraylist;
      }
   }

   private static boolean isJsonObject2(JsonObject jsonObject) {
      return jsonObject != null && jsonObject.has("success") && !jsonObject.get("success").isJsonNull() && jsonObject.get("success").getAsBoolean();
   }

   public static AuthResult getAuthResult() {
      JsonObject jsonobject = getJsonObjectByString("3");
      jsonobject.addProperty("lastMinecraftName", ServerUtil.getText());
      String s = ServerUtil.getText2();
      if (s != null) {
         jsonobject.addProperty("currentServer", s);
      }

      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      return jsonobject1 == null ? new AuthResult(false, "error", "UNAUTHORIZED") : getAuthResultByJsonObject(jsonobject1);
   }

   public static Map getMapByStringArray(String[] textArray) {
      JsonObject jsonobject = getJsonObjectByString("10");
      JsonArray jsonarray = new JsonArray();

      for (String s : textArray) {
         jsonarray.add(s);
      }

      jsonobject.add("imageNames", jsonarray);
      JsonObject jsonobject2 = getJsonObjectByJsonObject2(jsonobject);
      String s1 = "images";
      JsonObject jsonobject1 = jsonobject2;
      return getMapByStringJsonObject(s1, jsonobject1);
   }

   public static Map getMap() {
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(getJsonObjectByString("11"));
      String s = "sounds";
      JsonObject jsonobject = jsonobject1;
      return getMapByStringJsonObject(s, jsonobject);
   }

   private static JsonObject getJsonObjectByJsonObject2(JsonObject jsonObject) {
      if (flag) {
         return null;
      } else {
         String s = jsonObject.has("action") ? jsonObject.get("action").getAsString() : null;
         if (s == null) {
            return null;
         } else {
            String s1 = NativeBridgeUtil.getStringByStringString(s, jsonObject.toString());
            if (s1 != null && !s1.isEmpty()) {
               try {
                  JsonObject jsonobject = JsonParser.parseString(s1).getAsJsonObject();
                  onJsonObject(jsonobject);
                  onJsonObjectString(jsonobject, s);
                  return jsonobject;
               } catch (Exception exception) {
                  return null;
               }
            } else {
               return null;
            }
         }
      }
   }

   private static void onJsonObject(JsonObject jsonObject) {
      if (jsonObject != null && jsonObject.has("status") && !jsonObject.get("status").isJsonNull()) {
         if ("SESSION_INVALID".equals(jsonObject.get("status").getAsString())) {
            NativeBridgeUtil.setText(null);
         }
      }
   }

   private static void onJsonObjectString(JsonObject jsonObject, String text) {
      if ("1".equals(text) || "3".equals(text)) {
         if (jsonObject.has("data") && jsonObject.get("data").isJsonObject()) {
            JsonObject jsonobject = jsonObject.getAsJsonObject("data");
            if (jsonobject.has("session_id") && !jsonobject.get("session_id").isJsonNull()) {
               NativeBridgeUtil.setText(jsonobject.get("session_id").getAsString());
            }
         }
      }
   }

   private static JsonObject getJsonObjectByString(String text) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("action", text);
      jsonobject.addProperty("source", "Client");
      jsonobject.addProperty("ts", System.currentTimeMillis() / 1000L);
      return jsonobject;
   }

   public static String getStringByString3(String text) {
      JsonObject jsonobject = getJsonObjectByString("32");
      jsonobject.addProperty("configName", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      if (!isJsonObject2(jsonobject1)) {
         return null;
      } else {
         return jsonobject1.has("configData") ? jsonobject1.get("configData").getAsString() : null;
      }
   }

   public static void onString(String text) {
      NativeBridgeUtil.setText(text);
   }

   public static boolean isString3(String text) {
      JsonObject jsonobject = getJsonObjectByString("34");
      jsonobject.addProperty("configName", text);
      return isJsonObject(jsonobject);
   }

   public static String getString() {
      JsonObject jsonobject = getJsonObjectByJsonObject2(getJsonObjectByString("38"));
      if (isJsonObject2(jsonobject) && jsonobject.has("data")) {
         JsonObject jsonobject1 = jsonobject.getAsJsonObject("data");
         return jsonobject1.has("config_name") && !jsonobject1.get("config_name").isJsonNull() ? jsonobject1.get("config_name").getAsString() : null;
      } else {
         return null;
      }
   }

   public static List getList4() {
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(getJsonObjectByString("12"));
      Type type = new ShaderSourceListToken().getType();
      String s = "shaders";
      JsonObject jsonobject = jsonobject1;
      return getListByStringJsonObjectType(s, jsonobject, type);
   }

   public static Map getMapByString(String text) {
      JsonObject jsonobject = getJsonObjectByString("14");
      jsonobject.addProperty("lang", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      if (!isJsonObject2(jsonobject1)) {
         return null;
      } else {
         JsonObject jsonobject2 = jsonobject1.getAsJsonObject("data");
         if (jsonobject2 != null && jsonobject2.has("translations")) {
            String s = jsonobject2.get("translations").getAsString();
            JsonObject jsonobject3 = JsonParser.parseString(s).getAsJsonObject();
            HashMap hashmap = new HashMap();

            for (Entry entry : jsonobject3.entrySet()) {
               hashmap.put((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
            }

            return hashmap;
         } else {
            return null;
         }
      }
   }

   public static ConfigEntry getConfigEntryByStringStringString(String text, String text2, String text3) {
      JsonObject jsonobject = getJsonObjectByString("30");
      jsonobject.addProperty("configName", text3);
      jsonobject.addProperty("configDescription", text2);
      jsonobject.addProperty("configData", text);
      JsonObject jsonobject1 = getJsonObjectByJsonObject2(jsonobject);
      if (!isJsonObject2(jsonobject1)) {
         return null;
      } else {
         JsonObject jsonobject2 = jsonobject1.has("data") ? jsonobject1.getAsJsonObject("data") : null;
         ConfigEntry configentry = new ConfigEntry();
         configentry.setText(jsonobject2 != null && jsonobject2.has("config_name") ? jsonobject2.get("config_name").getAsString() : text3);
         configentry.setText3(jsonobject2 != null && jsonobject2.has("config_key") ? jsonobject2.get("config_key").getAsString() : null);
         configentry.setText2(text2);
         configentry.setFlag(false);
         configentry.setText4(DateUtil.getStringByString3(jsonobject2 != null ? getStringByJsonObjectString(jsonobject2, "created_at") : null));
         return configentry;
      }
   }

   public static boolean isStringString5(String text, String text2) {
      JsonObject jsonobject = getJsonObjectByString("31");
      jsonobject.addProperty("configName", text);
      jsonobject.addProperty("configData", text2);
      return isJsonObject(jsonobject);
   }
}
