package client.data;

import client.audio.SoundEngine;
import client.concurrent.SystemClient;
import client.enums.SoundEvent;
import client.network.ConfigApi;
import client.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class SystemFriend {
   private static final SystemFriend INSTANCE = new SystemFriend();
   private final Set<String> set = ConcurrentHashMap.newKeySet();
   private final Map<String, String> map = new ConcurrentHashMap<>();
   private final Map<String, String> map2 = new ConcurrentHashMap<>();
   private final Map<String, String> map3 = new ConcurrentHashMap<>();

   public void onStringString(String text, String text2) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         if (text2 != null && !text2.trim().isEmpty() && !text2.trim().equals("SystemFriend")) {
            this.map2.put(s, text2.trim());
         } else {
            this.map2.remove(s);
         }
      }
   }

   public void onString(String text) {
      if (this.isString(text)) {
         this.removeString(text);
      } else {
         this.addString2(text);
      }
   }

   public void onStringString2(String text, String text2) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         if (text2 != null && !text2.trim().isEmpty()) {
            this.map.put(s, text2.trim());
         } else {
            this.map.remove(s);
         }
      }
   }

   public Map<String, String> getMap() {
      return new HashMap<>(this.map2);
   }

   public String getStringByString(String text) {
      if (text == null) {
         return "SystemFriend";
      } else {
         String s = this.map2.get(text.trim().toLowerCase());
         return s == null ? "SystemFriend" : s;
      }
   }

   private void update() {
      try {
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient != null && systemclient.getHashUtil() != null) {
            systemclient.getHashUtil().update5();
         }
      } catch (Exception exception) {
      }
   }

   public void addString(String text) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         this.set.add(s);
         this.map3.putIfAbsent(s, DateUtil.getString());
      }
   }

   public String getStringByString2(String text) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         String s1 = this.map3.get(s);
         String s2 = getStringByString3(s1);
         if (!s2.equals(s1)) {
            this.map3.put(s, s2);
            this.update();
         }

         return s2;
      } else {
         return DateUtil.getString();
      }
   }

   public void onStringString3(String text, String text2) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         if (text2 != null && !text2.trim().isEmpty() && !text2.trim().equals("SystemFriend")) {
            this.map2.put(s, text2.trim());
         } else {
            this.map2.remove(s);
         }

         this.update();
      }
   }

   public Map<String, String> getMap2() {
      return new HashMap<>(this.map3);
   }

   public void removeString(String text) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         boolean flag = this.set.remove(s);
         this.map.remove(s);
         this.map2.remove(s);
         this.map3.remove(s);
         if (flag) {
            this.update();
            SoundEngine.getInstance().onSoundEvent(SoundEvent.FRIEND_REMOVE);
         }
      }
   }

   public void update2() {
      try {
         JsonArray jsonarray = ConfigApi.getJsonArray();
         if (jsonarray == null) {
            return;
         }

         this.update3();

         for (JsonElement jsonelement : jsonarray) {
            if (jsonelement.isJsonObject()) {
               JsonObject jsonobject = jsonelement.getAsJsonObject();
               String s = getStringByJsonObjectString(jsonobject, "name");
               if (s != null && !s.isBlank()) {
                  String s1 = s.trim().toLowerCase();
                  this.set.add(s1);
                  String s2 = getStringByJsonObjectString(jsonobject, "nickname");
                  if (s2 != null && !s2.isBlank()) {
                     this.map.put(s1, s2);
                  }

                  String s3 = getStringByJsonObjectString(jsonobject, "alias");
                  if (s3 != null && !s3.isBlank() && !s3.equals("SystemFriend")) {
                     this.map2.put(s1, s3);
                  }

                  String s4 = getStringByJsonObjectString(jsonobject, "created_at");
                  this.map3.put(s1, getStringByString3(s4));
               }
            }
         }
      } catch (Exception exception) {
      }
   }

   private static String getStringByString3(String text) {
      String s = DateUtil.getStringByString(text);
      return s == null ? DateUtil.getString() : s;
   }

   public void removeString2(String text) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         boolean flag = this.set.remove(s);
         this.map.remove(s);
         this.map2.remove(s);
         this.map3.remove(s);
         if (flag) {
            this.update();
         }
      }
   }

   public void onStringString4(String text, String text2) {
      if (text != null && !text.trim().isEmpty()) {
         this.map3.put(text.trim().toLowerCase(), getStringByString3(text2));
      }
   }

   public boolean check() {
      try {
         return ConfigApi.isString2(this.getString());
      } catch (Exception exception) {
         return false;
      }
   }

   public String getString() {
      ArrayList arraylist = new ArrayList();

      for (String s : this.set) {
         HashMap hashmap = new HashMap();
         hashmap.put("name", s);
         String s1 = this.map.get(s);
         if (s1 != null) {
            hashmap.put("nickname", s1);
         }

         String s2 = this.map2.get(s);
         if (s2 != null) {
            hashmap.put("alias", s2);
         }

         String s3 = this.map3.get(s);
         if (s3 != null) {
            hashmap.put("created_at", s3);
         }

         arraylist.add(hashmap);
      }

      return new Gson().toJson(arraylist);
   }

   public Set<String> getSet() {
      return new HashSet<>(this.set);
   }

   public void onStringString5(String text, String text2) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         if (text2 != null && !text2.trim().isEmpty()) {
            this.map.put(s, text2.trim());
         } else {
            this.map.remove(s);
         }

         this.update();
      }
   }

   private static String getStringByJsonObjectString(JsonObject jsonObject, String text) {
      return jsonObject.has(text) && !jsonObject.get(text).isJsonNull() ? jsonObject.get(text).getAsString() : null;
   }

   public static SystemFriend getInstance() {
      return INSTANCE;
   }

   public boolean isLivingEntity(LivingEntity livingEntity) {
      if (livingEntity == null) {
         return false;
      } else {
         return livingEntity instanceof PlayerEntity playerentity ? this.isPlayerEntity(playerentity) : false;
      }
   }

   public boolean isString(String text) {
      return text != null && !text.trim().isEmpty() ? this.set.contains(text.trim().toLowerCase()) : false;
   }

   public void addString2(String text) {
      if (text != null && !text.trim().isEmpty()) {
         String s = text.trim().toLowerCase();
         boolean flag = this.set.add(s);
         if (flag) {
            this.map3.put(s, DateUtil.getString());
            this.update();
            SoundEngine.getInstance().onSoundEvent(SoundEvent.FRIEND_ADD);
         }
      }
   }

   public boolean isPlayerEntity(PlayerEntity playerEntity) {
      return playerEntity == null ? false : this.isString(playerEntity.getName().getString());
   }

   public Map<String, String> getMap3() {
      return new HashMap<>(this.map);
   }

   public String getStringByString4(String text) {
      return text == null ? null : this.map.get(text.trim().toLowerCase());
   }

   public void update3() {
      this.set.clear();
      this.map.clear();
      this.map2.clear();
      this.map3.clear();
   }
}
