package client.concurrent;

import client.data.Waypoint;
import client.module.Feature;
import client.network.ConfigApi;
import client.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.client.network.ServerInfo;

public final class WaypointStore {
   private static final WaypointStore INSTANCE = new WaypointStore();
   private final ConcurrentHashMap<String, Waypoint> concurrentHashMap = new ConcurrentHashMap<>();
   private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

   private WaypointStore() {
   }

   public String getString() {
      ArrayList arraylist = new ArrayList();
      this.reentrantReadWriteLock.readLock().lock();

      try {
         for (Waypoint waypoint : this.concurrentHashMap.values()) {
            if (!waypoint.isFlag2()) {
               HashMap hashmap = new HashMap();
               hashmap.put("wp_id", waypoint.getText());
               hashmap.put("wp_name", waypoint.getText2());
               hashmap.put("x", waypoint.getValue());
               hashmap.put("y", waypoint.getValue2());
               hashmap.put("z", waypoint.getValue3());
               hashmap.put("server", waypoint.getText3() == null ? "" : waypoint.getText3());
               hashmap.put("enabled", waypoint.isFlag());
               hashmap.put("created_at", waypoint.getText4());
               arraylist.add(hashmap);
            }
         }
      } finally {
         this.reentrantReadWriteLock.readLock().unlock();
      }

      return new Gson().toJson(arraylist);
   }

   public void onString(String text) {
      if (text != null) {
         try {
            JsonArray jsonarray = ConfigApi.getJsonArrayByString(text);
            if (jsonarray == null) {
               return;
            }

            this.update();

            for (JsonElement jsonelement : jsonarray) {
               if (jsonelement.isJsonObject()) {
                  JsonObject jsonobject = jsonelement.getAsJsonObject();
                  String s = getStringByJsonObjectString(jsonobject, "wp_id");
                  String s1 = getStringByJsonObjectString(jsonobject, "wp_name");
                  if (s != null && s1 != null) {
                     Waypoint waypoint = new Waypoint(
                        s,
                        s1,
                        getIntByJsonObjectString(jsonobject, "x"),
                        getIntByJsonObjectString(jsonobject, "y"),
                        getIntByJsonObjectString(jsonobject, "z"),
                        getStringByJsonObjectString(jsonobject, "server"),
                        !Boolean.FALSE.equals(getBooleanByJsonObjectString(jsonobject, "enabled")),
                        DateUtil.getStringByString3(getStringByJsonObjectString(jsonobject, "created_at"))
                     );
                     this.onWaypoint(waypoint);
                  }
               }
            }
         } catch (Exception exception) {
         }
      }
   }

   public void update() {
      this.reentrantReadWriteLock.writeLock().lock();

      try {
         this.concurrentHashMap.clear();
      } finally {
         this.reentrantReadWriteLock.writeLock().unlock();
      }
   }

   public Waypoint getWaypointByStringIntIntIntString(String text, int count, int count2, int count3, String text2) {
      return this.getWaypointByStringIntIntIntStringBoolean(text, count, count2, count3, text2, true);
   }

   private void update2() {
      try {
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient != null && systemclient.getHashUtil() != null) {
            systemclient.getHashUtil().update5();
         }
      } catch (Exception exception) {
      }
   }

   public boolean isString(String text) {
      if (text == null) {
         return false;
      } else {
         try {
            String s = this.getString();
            return ConfigApi.isStringString(s, text);
         } catch (Exception exception) {
            return false;
         }
      }
   }

   public void update3() {
      this.reentrantReadWriteLock.writeLock().lock();

      try {
         this.concurrentHashMap.values().removeIf(Waypoint::isFlag2);
      } finally {
         this.reentrantReadWriteLock.writeLock().unlock();
      }
   }

   public static String getStringByString(String text) {
      if (text == null) {
         return "";
      } else {
         String s = text.trim().toLowerCase();
         int i = s.lastIndexOf(58);
         int j = s.lastIndexOf(93);
         if (i > j && i > 0) {
            s = s.substring(0, i);
         }

         int k = s.indexOf(46);
         int l = s.lastIndexOf(46);
         if (k < 0) {
            return s;
         } else if (k == l) {
            return s.substring(0, k);
         } else {
            String s1 = s.substring(k + 1, l);
            return s1.isEmpty() ? s : s1;
         }
      }
   }

   public static String getString2() {
      try {
         ServerInfo serverinfo = Feature.mc.getCurrentServerEntry();
         if (serverinfo != null && serverinfo.address != null && !serverinfo.address.isEmpty()) {
            return getStringByString(serverinfo.address);
         }

         if (Feature.mc.isInSingleplayer()) {
            return "__singleplayer__";
         }
      } catch (Exception exception) {
      }

      return null;
   }

   public static WaypointStore getInstance() {
      return INSTANCE;
   }

   private static Boolean getBooleanByJsonObjectString(JsonObject jsonObject, String text) {
      if (jsonObject.has(text) && !jsonObject.get(text).isJsonNull()) {
         try {
            return jsonObject.get(text).getAsBoolean();
         } catch (Exception exception) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static int getIntByJsonObjectString(JsonObject jsonObject, String text) {
      if (jsonObject.has(text) && !jsonObject.get(text).isJsonNull()) {
         try {
            return jsonObject.get(text).getAsInt();
         } catch (Exception exception) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private static String getStringByJsonObjectString(JsonObject jsonObject, String text) {
      return jsonObject.has(text) && !jsonObject.get(text).isJsonNull() ? jsonObject.get(text).getAsString() : null;
   }

   public void onStringIntIntInt(String text, int count, int count2, int count3) {
      Waypoint waypoint = this.concurrentHashMap.get(text);
      if (waypoint != null) {
         if (waypoint.getValue() != count || waypoint.getValue2() != count2 || waypoint.getValue3() != count3) {
            waypoint.setValue(count);
            waypoint.setValue2(count2);
            waypoint.setValue3(count3);
            this.update2();
         }
      }
   }

   public void onStringString(String text, String text2) {
      if (text2 != null && !text2.trim().isEmpty()) {
         Waypoint waypoint = this.concurrentHashMap.get(text);
         if (waypoint != null) {
            String s = text2.trim();
            if (!s.equals(waypoint.getText2())) {
               waypoint.setText2(s);
               this.update2();
            }
         }
      }
   }

   public void onStringBoolean(String text, boolean flag) {
      Waypoint waypoint = this.concurrentHashMap.get(text);
      if (waypoint != null && waypoint.isFlag() != flag) {
         waypoint.setFlag(flag);
         this.update2();
      }
   }

   public void removeString(String text) {
      if (text != null) {
         this.reentrantReadWriteLock.writeLock().lock();

         Waypoint waypoint;
         try {
            waypoint = this.concurrentHashMap.remove(text);
         } finally {
            this.reentrantReadWriteLock.writeLock().unlock();
         }

         if (waypoint != null) {
            this.update2();
         }
      }
   }

   public void onWaypoint(Waypoint waypoint) {
      if (waypoint != null && waypoint.getText() != null) {
         if (waypoint.getText4() == null) {
            waypoint.setText4(DateUtil.getString2());
         }

         this.reentrantReadWriteLock.writeLock().lock();

         try {
            this.concurrentHashMap.put(waypoint.getText(), waypoint);
         } finally {
            this.reentrantReadWriteLock.writeLock().unlock();
         }
      }
   }

   public static boolean check() {
      String s = getString2();
      return s != null && !"__singleplayer__".equals(s);
   }

   private Waypoint getWaypointByStringIntIntIntStringBoolean(String text, int count, int count2, int count3, String text2, boolean flag) {
      if (text == null || text.trim().isEmpty()) {
         return null;
      } else if (text2 != null && !text2.trim().isEmpty()) {
         Waypoint waypoint = new Waypoint(UUID.randomUUID().toString(), text.trim(), count, count2, count3, getStringByString(text2), true, DateUtil.getString2());
         waypoint.setFlag2(flag);
         this.reentrantReadWriteLock.writeLock().lock();

         try {
            this.concurrentHashMap.put(waypoint.getText(), waypoint);
         } finally {
            this.reentrantReadWriteLock.writeLock().unlock();
         }

         return waypoint;
      } else {
         return null;
      }
   }

   public Waypoint getWaypointByStringIntIntIntString2(String text, int count, int count2, int count3, String text2) {
      Waypoint waypoint = this.getWaypointByStringIntIntIntStringBoolean(text, count, count2, count3, text2, false);
      if (waypoint != null) {
         this.update2();
      }

      return waypoint;
   }

   public List getListByString(String text) {
      if (text == null) {
         return Collections.emptyList();
      } else {
         String s = getStringByString(text);
         ArrayList arraylist = new ArrayList();
         this.reentrantReadWriteLock.readLock().lock();

         try {
            for (Waypoint waypoint : this.concurrentHashMap.values()) {
               if (s.equals(waypoint.getText3())) {
                  arraylist.add(waypoint);
               }
            }
         } finally {
            this.reentrantReadWriteLock.readLock().unlock();
         }

         return arraylist;
      }
   }

   public List getList() {
      this.reentrantReadWriteLock.readLock().lock();

      ArrayList arraylist;
      try {
         arraylist = new ArrayList<>(this.concurrentHashMap.values());
      } finally {
         this.reentrantReadWriteLock.readLock().unlock();
      }

      return arraylist;
   }
}
