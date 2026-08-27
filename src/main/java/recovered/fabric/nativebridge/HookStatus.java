package recovered.fabric.nativebridge;

import b.Boot;
import client.transform.ClassRedefiner;
import java.lang.reflect.Field;
import java.util.Map;
import recovered.fabric.diagnostic.SystemDlcLog;

/**
 * Reports whether the recovered ClassRedefiner managed to install its hooks. The original
 * client reported this through the obfuscated "li" class, which no longer exists.
 */
public final class HookStatus {
   private HookStatus() {
   }

   public static void report() {
      try {
         Class<?> type = ClassRedefiner.class;
         boolean ready;

         try {
            ready = Boot.nativePatchMethodReady();
         } catch (Throwable throwable) {
            ready = false;
         }

         SystemDlcLog.info(
            "hook status nativeReady="
               + ready
               + " installed="
               + booleanValue(type, "flag")
               + " nativeEnabled="
               + booleanValue(type, "flag2")
               + " hookedClasses="
               + mapSize(type, "map")
               + " cachedBytecode="
               + mapSize(type, "map2")
               + " handles="
               + mapSize(type, "map3")
         );
      } catch (Throwable throwable) {
         SystemDlcLog.error("hook status unavailable", throwable);
      }
   }

   private static boolean booleanValue(Class<?> type, String name) throws ReflectiveOperationException {
      return field(type, name).getBoolean(null);
   }

   private static int mapSize(Class<?> type, String name) throws ReflectiveOperationException {
      Map<?, ?> values = (Map<?, ?>)field(type, name).get(null);
      return values == null ? -1 : values.size();
   }

   private static Field field(Class<?> type, String name) throws NoSuchFieldException {
      Field field = type.getDeclaredField(name);
      field.setAccessible(true);
      return field;
   }
}
