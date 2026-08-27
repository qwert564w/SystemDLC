package client.concurrent;

import client.enums.CooldownItem;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.PlayerEntity;

public final class CooldownTracker {
   private static final long time = 1000L;
   private static final CooldownItem[] cooldownItemArray = CooldownItem.values();
   private static final Map<UUID, Map<CooldownItem, Long>> map = new ConcurrentHashMap<>();
   private static final Map<CooldownItem, Long> map2 = new ConcurrentHashMap<>();
   private static UUID uUID;
   private static String text;

   private CooldownTracker() {
   }

   public static String getText() {
      return text;
   }

   public static void update() {
      map2.clear();
      map.clear();
      uUID = null;
      text = null;
   }

   private static void onMap(Map<?, Long> map) {
      long i = System.currentTimeMillis();
      map.entrySet().removeIf(var2 -> (Long)var2.getValue() <= i);
   }

   public static UUID getUUID() {
      return uUID;
   }

   public static void removePlayerEntity(PlayerEntity playerEntity) {
      if (playerEntity != null) {
         UUID uuid = playerEntity.getUuid();
         if (!uuid.equals(uUID)) {
            UUID uuid1 = uUID;
            if (uuid1 != null) {
               ConcurrentHashMap concurrenthashmap = new ConcurrentHashMap<>(map2);
               onMap(concurrenthashmap);
               if (concurrenthashmap.isEmpty()) {
                  map.remove(uuid1);
               } else {
                  map.put(uuid1, concurrenthashmap);
               }
            }

            uUID = uuid;
            text = playerEntity.getGameProfile().getName();
            map2.clear();
            Map mapx = map.remove(uuid);
            if (mapx != null) {
               onMap(mapx);
               map2.putAll(mapx);
            }
         }
      }
   }

   public static void onCooldownItem(CooldownItem cooldownItem) {
      if (uUID != null) {
         long i = System.currentTimeMillis() + cooldownItem.time;
         Long olong = map2.get(cooldownItem);
         if (olong == null || Math.abs(olong - i) >= 1000L) {
            map2.put(cooldownItem, i);
         }
      }
   }

   public static long getLongByCooldownItem(CooldownItem cooldownItem) {
      Long olong = map2.get(cooldownItem);
      if (olong == null) {
         return 0L;
      } else {
         long i = olong - System.currentTimeMillis();
         if (i > 0L) {
            return i;
         } else {
            map2.remove(cooldownItem);
            return 0L;
         }
      }
   }

   public static CooldownItem[] getCooldownItemArray() {
      return cooldownItemArray;
   }
}
