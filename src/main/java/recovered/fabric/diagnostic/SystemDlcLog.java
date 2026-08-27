package recovered.fabric.diagnostic;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class SystemDlcLog {
   private static final Set<String> reported = ConcurrentHashMap.newKeySet();
   private static final AtomicInteger modules = new AtomicInteger();

   private SystemDlcLog() {
   }

   public static void info(String message) {
      System.out.println("[SystemDLC] " + message);
   }

   public static void once(String key, String message) {
      if (reported.add(key)) {
         info(message);
      }
   }

   public static void error(String message, Throwable error) {
      System.err.println("[SystemDLC] " + message + ": " + error);
      error.printStackTrace(System.err);
   }

   public static void errorOnce(String operation, Throwable error) {
      if (reported.add("error:" + operation)) {
         error(operation + " failed", error);
      }
   }

   public static void moduleScanStarted() {
      modules.set(0);
      info("module registration started");
   }

   public static void moduleRegistered(Object module) {
      int count = modules.incrementAndGet();
      String name = module == null ? "null" : module.getClass().getName();
      String marker = "bQ".equals(name) ? " RightShift GUI" : "";
      info("module registered #" + count + " " + name + marker);
   }

   public static void moduleScanFinished() {
      info("module registration completed count=" + modules.get());
   }
}
