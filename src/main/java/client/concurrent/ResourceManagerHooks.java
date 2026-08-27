package client.concurrent;

import client.api.Hook;
import client.api.HookClass;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.client.PanicModule;
import client.render.ShaderLoader;
import client.transform.ClassRedefiner;
import client.util.ReflectionCache;
import client.util.ResourceLoader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourceReload;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.SimpleResourceReload;
import net.minecraft.util.Identifier;

@HookClass(ReloadableResourceManagerImpl.class)
public class ResourceManagerHooks {
   private static final HashMap<String, ResourceLoader> map = new HashMap<>();
   private static boolean flag = false;
   private static boolean flag2 = false;
   private static boolean flag3 = false;
   private static boolean flag4 = false;
   private static final AtomicInteger atomicInteger = new AtomicInteger(0);
   private static final Pattern pattern = Pattern.compile("[a-z0-9/._-]+");
   private static final long time = ReflectionCache.getLongByClassInt(ReloadableResourceManagerImpl.class, 0);
   private static final long time2 = ReflectionCache.getLongByClassInt(ReloadableResourceManagerImpl.class, 1);
   private static final long time3 = ReflectionCache.getLongByClassInt(ReloadableResourceManagerImpl.class, 2);

   public static void update() {
      if (!flag2) {
         if (Feature.mc != null && Feature.mc.getResourceManager() != null) {
            update2();
            flag2 = true;

            try {
               CompletableFuture completablefuture = Feature.mc.reloadResources();
               if (completablefuture != null) {
                  completablefuture.whenComplete((item, item2) -> flag3 = true);
               } else {
                  flag3 = true;
               }
            } catch (Throwable throwable) {
               flag3 = true;
            }
         }
      }
   }

   public static boolean isFlag4() {
      return flag4;
   }

   @Hook(
      method = "method_14486",
      desc = "(Lnet/minecraft/class_2960;)Ljava/util/Optional;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Optional getOptionalByReloadableResourceManagerImplIdentifier(ReloadableResourceManagerImpl reloadableResourceManagerImpl, Identifier identifier) {
      if (flag && !PanicModule.isFlag() && map.containsKey(identifier.getPath())) {
         return Optional.of(map.get(identifier.getPath()).getResource());
      } else {
         LifecycledResourceManager lifecycledresourcemanager = (LifecycledResourceManager)ReflectionCache.getObjectByObjectLong(reloadableResourceManagerImpl, time);
         return lifecycledresourcemanager.getResource(identifier);
      }
   }

   @Hook(
      method = "method_14488",
      desc = "(Ljava/lang/String;Ljava/util/function/Predicate;)Ljava/util/Map;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static Map getMapByReloadableResourceManagerImplStringPredicate(ReloadableResourceManagerImpl reloadableResourceManagerImpl, String text, Predicate predicate) {
      LifecycledResourceManager lifecycledresourcemanager = (LifecycledResourceManager)ReflectionCache.getObjectByObjectLong(reloadableResourceManagerImpl, time);
      LinkedHashMap linkedhashmap = new LinkedHashMap(lifecycledresourcemanager.findResources(text, predicate));
      if (flag && !PanicModule.isFlag()) {
         for (Entry entry : map.entrySet()) {
            String s = (String)entry.getKey();
            if (s != null && s.startsWith(text) && pattern.matcher(s).matches()) {
               try {
                  linkedhashmap.put(Identifier.ofVanilla(s), ((ResourceLoader)entry.getValue()).getResource());
               } catch (Throwable throwable) {
               }
            }
         }
      }

      return linkedhashmap;
   }

   public static void onResourceLoader(ResourceLoader resourceLoader) {
      map.put(resourceLoader.getIdentifier().getPath(), resourceLoader);
   }

   public static int getInt() {
      return atomicInteger.get();
   }

   private static synchronized void update2() {
      if (!flag) {
         try {
            new ShaderLoader(ResourceManagerHooks::onResourceLoader);
            flag = true;
         } catch (Throwable throwable) {
         }
      }
   }

   public static boolean isFlag3() {
      return flag3;
   }

   @Hook(
      method = "method_18232",
      desc = "(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/class_4011;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static ResourceReload getResourceReloadByReloadableResourceManagerImplExecutorExecutorCompletableFutureList(
      ReloadableResourceManagerImpl reloadableResourceManagerImpl, Executor executor, Executor executor2, CompletableFuture completableFuture, List list2
   ) {
      LifecycledResourceManager lifecycledresourcemanager = (LifecycledResourceManager)ReflectionCache.getObjectByObjectLong(reloadableResourceManagerImpl, time);
      List list = (List)ReflectionCache.getObjectByObjectLong(reloadableResourceManagerImpl, time2);
      ResourceType resourcetype = (ResourceType)ReflectionCache.getObjectByObjectLong(reloadableResourceManagerImpl, time3);
      if (lifecycledresourcemanager != null && list != null && resourcetype != null) {
         try {
            lifecycledresourcemanager.close();
         } catch (Throwable throwable) {
         }

         LifecycledResourceManagerImpl lifecycledresourcemanagerimpl = new LifecycledResourceManagerImpl(resourcetype, list2);
         ReflectionCache.onObjectLongObject(reloadableResourceManagerImpl, time, lifecycledresourcemanagerimpl);
         flag4 = true;
         atomicInteger.incrementAndGet();
         ResourceReload resourcereload = SimpleResourceReload.start(reloadableResourceManagerImpl, list, executor, executor2, completableFuture, false);
         resourcereload.whenComplete().whenComplete((item, item2) -> flag4 = false);
         return resourcereload;
      } else {
         throw new IllegalStateException("ReloadableResourceManagerImpl shadow read returned null");
      }
   }
}
