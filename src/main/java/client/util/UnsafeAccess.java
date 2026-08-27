package client.util;

import client.concurrent.ModuleRegistry;
import client.concurrent.SystemClient;
import client.module.Module;
import java.lang.reflect.Field;
import java.util.function.Supplier;
import sun.misc.Unsafe;

public class UnsafeAccess<T extends Module> {
   public static final Unsafe unsafe;
   private final Supplier<T> supplier;
   private final Class<T> classValue;
   private T module;
   private long time = -1L;
   private static ModuleDispatcher moduleDispatcher;
   private static long time2;

   public UnsafeAccess(Class value) {
      this.classValue = value;
      this.supplier = null;
   }

   public UnsafeAccess(Supplier supplier2) {
      this.classValue = null;
      this.supplier = supplier2;
   }

   static {
      try {
         Field field = Unsafe.class.getDeclaredField("theUnsafe");
         field.setAccessible(true);
         unsafe = (Unsafe)field.get(null);
      } catch (Exception exception) {
         throw new RuntimeException(exception);
      }
   }

   public Module getModule() {
      if (this.supplier != null) {
         return this.supplier.get();
      } else {
         ModuleRegistry moduleregistry = getModuleRegistry();
         if (moduleregistry == null) {
            return this.module;
         } else {
            long i = moduleregistry.getTime();
            Module modulex = this.module;
            if (modulex != null && i == this.time) {
               return modulex;
            } else {
               try {
                  Module module1 = moduleregistry.getModuleByClass(this.classValue);
                  this.module = (T)module1;
                  this.time = i;
                  return module1;
               } catch (Exception exception) {
                  return modulex;
               }
            }
         }
      }
   }

   public Module getModule2() {
      Module modulex = this.getModule();
      return modulex != null && modulex.isEnabled() ? modulex : null;
   }

   public static ModuleDispatcher getModuleDispatcher() {
      long i = System.nanoTime();
      ModuleDispatcher moduledispatcher = moduleDispatcher;
      if (moduledispatcher != null && i - time2 < 500000000L) {
         return moduledispatcher;
      } else {
         try {
            SystemClient systemclient = SystemClient.getInstance();
            moduledispatcher = systemclient != null ? systemclient.getModuleDispatcher() : null;
         } catch (Exception exception) {
         }

         moduleDispatcher = moduledispatcher;
         time2 = i;
         return moduledispatcher;
      }
   }

   private static ModuleRegistry getModuleRegistry() {
      try {
         SystemClient systemclient = SystemClient.getInstance();
         return systemclient != null ? systemclient.getModuleRegistry() : null;
      } catch (Exception exception) {
         return null;
      }
   }
}
