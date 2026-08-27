package client.transform;

import b.Boot;
import client.api.Hook;
import client.api.HookClass;
import client.audio.SoundManagerHooks;
import client.concurrent.HandleInvoker;
import client.concurrent.ResourceManagerHooks;
import client.concurrent.SystemClient;
import client.data.HookInfo;
import client.data.HookRegistry;
import client.data.InjectPointSwitchMap;
import client.enums.HookPoint;
import client.enums.InjectPoint;
import client.gui.hud.DebugHudHelper;
import client.gui.widget.TextFieldAccess;
import client.network.ConnectionHooks;
import client.network.NetworkHandlerHooks;
import client.render.ArmorLayerHook;
import client.render.CameraHooks;
import client.render.ChamsRenderHooks;
import client.render.CrosshairTextures;
import client.render.EndCrystalRenderHook;
import client.render.EntityDispatcherHooks;
import client.render.FogHooks;
import client.render.FramebufferBindHook;
import client.render.GameMenuHooks;
import client.render.GameRendererHooks;
import client.render.LightmapHooks;
import client.render.NameTagRenderer;
import client.render.NameTagVisibilityHook;
import client.render.ParticleHooks;
import client.render.PlayerNameTagHooks;
import client.render.ShaderSourceProvider;
import client.render.SpriteRenderLayers;
import client.render.TabListHooks;
import client.render.WorldRenderHooks;
import client.render.WorldTickHook;
import client.util.ChatSpamGuard;
import client.util.EntityChecks;
import client.util.EntityDamageHelper;
import client.util.EntityHooks;
import client.util.HeldItemHooks;
import client.util.InputHooks;
import client.util.ServerInfo;
import client.util.TargetAnimation;
import client.util.TextFieldHooks;
import client.util.WindowUtil;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;
import recovered.fabric.diagnostic.SystemDlcLog;

public class ClassRedefiner {
   private static final Class<?>[] classArray = new Class[]{
      ResourceManagerHooks.class,
      ConnectionHooks.class,
      WorldRenderHooks.class,
      FogHooks.class,
      NameTagRenderer.class,
      HeldItemHooks.class,
      TabListHooks.class,
      PlayerNameTagHooks.class,
      EntityDispatcherHooks.class,
      GameRendererHooks.class,
      CameraHooks.class,
      CrosshairTextures.class,
      ChatSpamGuard.class,
      TextFieldHooks.class,
      InputHooks.class,
      TextFieldAccess.class,
      DebugHudHelper.class,
      WorldTickHook.class,
      GameMenuHooks.class,
      ParticleHooks.class,
      SoundManagerHooks.class,
      EndCrystalRenderHook.class,
      ChamsRenderHooks.class,
      SpriteRenderLayers.class,
      ArmorLayerHook.class,
      TargetAnimation.class,
      EntityDamageHelper.class,
      LightmapHooks.class,
      NetworkHandlerHooks.class,
      EntityChecks.class,
      FramebufferBindHook.class,
      NameTagVisibilityHook.class,
      ShaderSourceProvider.class,
      WindowUtil.class,
      EntityHooks.class
   };
   private static final Map<String, List<HookInfo>> map = new ConcurrentHashMap<>();
   private static final Map<String, byte[]> map2 = new ConcurrentHashMap<>();
   private static final Map<String, MethodHandle> map3 = new ConcurrentHashMap<>();
   private static final Map<String, Integer> map4 = new ConcurrentHashMap<>();
   private static final MixinTransformerAccess mixinTransformerAccess = new MixinTransformerAccess();
   private static final ThreadLocal<Boolean> threadLocal = ThreadLocal.withInitial(() -> Boolean.FALSE);
   private static boolean flag;
   private static boolean flag2;
   private static final Map<String, Class<?>> map5 = new ConcurrentHashMap<>();

   private static void update() {
      java.util.Iterator<?> iterator = map5.entrySet().iterator();
      while (iterator.hasNext()) {
         java.util.Map.Entry<?, ?> entry = (java.util.Map.Entry<?, ?>) iterator.next();
         try {
            onClassString((Class)entry.getValue(), (String)entry.getKey());
            iterator.remove();
         } catch (Throwable throwable) {
         }
      }
   }

   public static boolean check() {
      boolean flagx = threadLocal.get();
      if (flagx) {
         threadLocal.set(false);
      }

      return flagx;
   }

   public static void update2() {
      Thread.currentThread().setContextClassLoader(SystemClient.class.getClassLoader());

      for (Class oclass : classArray) {
         try {
            onClass(oclass);
         } catch (Throwable throwable1) {
         }
      }

      if (flag2) {
         update();

         for (Class oclass1 : classArray) {
            try {
               BytecodeCache.onClassMap(oclass1, map4);
            } catch (Throwable throwable) {
            }
         }

         flag = true;
      } else {
         System.err.println("[SystemDLC] hook engine unavailable; mixin hooks handle injection");
      }
   }

   public static void update3() {
      map.clear();
   }

   public static void update4() {
      threadLocal.set(true);
   }

   public static void retryPendingHooks() {
      if (map5.isEmpty()) {
         return;
      }
      if (!flag2) {
         try {
            flag2 = Boot.nativePatchMethodReady();
         } catch (Throwable t) {
            flag2 = false;
         }
      }
      if (!flag2) {
         try {
            flag2 = JavaInstrumentation.isAvailable();
         } catch (Throwable t) {
            flag2 = false;
         }
      }
      if (flag2 && !flag) {
         update();
         for (Class oclass : classArray) {
            try {
               BytecodeCache.onClassMap(oclass, map4);
            } catch (Throwable t) {
            }
         }
         flag = true;
         System.err.println("[SystemDLC] hook engine activated on retry; " + map5.size() + " hooks pending");
      } else if (flag2) {
         update();
      }
   }

   private static byte[] getByteArrayByClassLoaderString(ClassLoader classLoader, String text) {
      if (classLoader == null) {
         classLoader = ClassLoader.getSystemClassLoader();
      }

      try {
         byte[] abyte;
         try (InputStream inputstream = classLoader.getResourceAsStream(text)) {
            abyte = inputstream == null ? null : inputstream.readAllBytes();
         }

         return abyte;
      } catch (Throwable throwable1) {
         return null;
      }
   }

   private static byte[] getByteArrayByString(String text) {
      Class oclass = null;
      ClassLoader classloader = null;

      try {
         oclass = Class.forName(text.replace('/', '.'), false, Thread.currentThread().getContextClassLoader());
         classloader = oclass.getClassLoader();
      } catch (Throwable throwable1) {
      }

      if (oclass != null) {
         try {
            byte[] abyte = Boot.nativeDumpClassBytes(oclass);
            if (abyte != null && abyte.length > 0) {
               return abyte;
            }
         } catch (Throwable throwable) {
         }
      }

      byte[] abyte2 = mixinTransformerAccess.getByteArrayByStringClassLoader2(text, classloader);
      if (abyte2 != null) {
         return abyte2;
      } else {
         String s = text + ".class";
         byte[] abyte1 = getByteArrayByClassLoaderString(SystemClient.class.getClassLoader(), s);
         if (abyte1 == null && classloader != null) {
            abyte1 = getByteArrayByClassLoaderString(classloader, s);
         }

         return abyte1;
      }
   }

   private static void onClassString(Class value, String text) {
      List list = map.get(text);
      if (list != null && !list.isEmpty()) {
         byte[] abyte = map2.computeIfAbsent(text, ClassRedefiner::getByteArrayByString);
         if (abyte != null) {
            byte[] abyte1;
            try {
               abyte1 = HookRegistry.getByteArrayByByteArrayListClassLoader(abyte, list, value.getClassLoader());
            } catch (Throwable throwable1) {
               return;
            }

            if (abyte1 != null) {
               try {
                  Boot.nativeRedefineClass(value, abyte1);
               } catch (Throwable throwable) {
                  if (!JavaInstrumentation.redefineClass(value, abyte1)) {
                     SystemDlcLog.once("redefine:" + text, "class redefinition failed for " + text);
                  }
               }
            }
         }
      }
   }

   private static HookPoint getHookPointByInjectPoint(InjectPoint injectPoint) {
      return switch (InjectPointSwitchMap.intArray[injectPoint.ordinal()]) {
         case 1 -> HookPoint.HEAD;
         case 2 -> HookPoint.TAIL;
         case 3 -> HookPoint.REPLACE;
         case 4 -> HookPoint.HEAD_CANCELLABLE;
         default -> throw new MatchException(null, null);
      };
   }

   public static void update5() {
      if (!flag2) {
         try {
            flag2 = Boot.nativePatchMethodReady();
            if (flag2) {
               System.err.println("[SystemDLC] hook engine: native ready");
            }
         } catch (Throwable throwable1) {
            flag2 = false;
         }

         int attempts = 0;
         while (!flag2 && attempts < 3) {
            try {
               flag2 = JavaInstrumentation.isAvailable();
               if (flag2) {
                  System.err.println("[SystemDLC] hook engine: JavaInstrumentation ready (attempt " + (attempts + 1) + ")");
               } else if (attempts < 2) {
                  Thread.sleep(2000L);
               }
            } catch (Throwable throwable2) {
               flag2 = false;
            }
            attempts++;
         }

         if (!flag2) {
            System.err.println("[SystemDLC] WARNING: No hook engine available. Features requiring class hooks will not work.");
            System.err.println("[SystemDLC] To fix: add --add-opens jdk.attach/com.sun.tools.attach=ALL-UNNAMED to JVM args");
         }

         try {
            ServerInfo.check();
         } catch (Throwable throwable) {
         }
      }
   }

   private static void onClassHookClassMethod(Class value2, Hook hook, Class value3, Method method2) {
      String s = Type.getInternalName(value2);
      String s1 = Type.getInternalName(value3);
      HookPoint hookpoint = getHookPointByInjectPoint(hook.getInjectPoint());
      String s2 = Type.getMethodDescriptor(method2);
      String s5 = hook.method();
      String s6 = hook.desc();
      boolean flagx = s5 != null && !s5.isEmpty() && s6 != null && !s6.isEmpty();
      String s3;
      String s4;
      if (flagx) {
         String s7 = s5;
         String s8 = s6;
         if (getMethodByClassStringString(value2, s5, s6) == null) {
            String s9 = getStringByClassStringString(value2, s5, s6);
            String s10 = ServerInfo.getStringByString3(s6);
            if (getMethodByClassStringString(value2, s9, s10) != null) {
               s7 = s9;
               s8 = s10;
            }
         }

         if (getMethodByClassStringString(value2, s7, s8) == null) {
            throw new IllegalArgumentException();
         }

         s3 = s7;
         s4 = s8;
      } else {
         int i = hook.methodIndex();
         if (i < 0) {
            throw new IllegalArgumentException();
         }

         MethodNode methodnode = MethodIndex.getMethodNodeByClassInt(value2, i);
         if (methodnode == null) {
            throw new IllegalArgumentException();
         }

         s3 = methodnode.name;
         s4 = methodnode.desc;
      }

      Method method = getMethodByClassStringString(value2, s3, s4);
      HookInfo hookinfo = new HookInfo(s, s3, s4, s1, method2.getName(), s2, hookpoint);
      String s11 = s1 + "." + method2.getName() + s2;
      onClassStringStringStringMethod(value2, s3, s4, s11, method);
      MethodHandle methodhandle = map3.get(s11);
      if (methodhandle != null) {
         hookinfo.value = HandleInvoker.getIntByMethodHandle(methodhandle);
      }

      map.computeIfAbsent(s, var0x -> new ArrayList<>()).add(hookinfo);
      map4.put(s11, hookinfo.value);
      map5.put(s, value2);
   }

   private static void onClass(Class value2) {
      HookClass hookclass = (HookClass)value2.getAnnotation(HookClass.class);
      Class oclass = hookclass != null && hookclass.value() != void.class ? hookclass.value() : null;

      for (Method method : value2.getDeclaredMethods()) {
         for (Hook hook : method.getAnnotationsByType(Hook.class)) {
            Class oclass1;
            if (!hook.targetName().isEmpty()) {
               try {
                  oclass1 = Class.forName(hook.targetName(), false, SystemClient.class.getClassLoader());
               } catch (Throwable throwable1) {
                  continue;
               }
            } else {
               oclass1 = hook.target() != void.class ? hook.target() : oclass;
            }

            if (oclass1 == null) {
               throw new IllegalStateException();
            }

            try {
               onClassHookClassMethod(oclass1, hook, value2, method);
            } catch (Throwable throwable) {
            }
         }
      }
   }

   public static MethodHandle getMethodHandleByStringStringString(String text, String text2, String text3) {
      return map3.get(text + "." + text2 + text3);
   }

   public static boolean isFlag() {
      return flag;
   }

   private static void onClassStringStringStringMethod(Class value, String text, String text2, String text3, Method method2) {
      map3.computeIfAbsent(text3, var4x -> {
         Method method = method2;
         if (method2 == null) {
            method = getMethodByClassStringString(value, text, text2);
         }

         if (method == null) {
            for (Method method1 : value.getDeclaredMethods()) {
               if (method1.getName().equals(text)) {
                  method = method1;
                  break;
               }
            }
         }

         if (method == null) {
            return null;
         } else {
            try {
               method.setAccessible(true);
               return MethodHandles.lookup().unreflect(method);
            } catch (Throwable throwable) {
               return null;
            }
         }
      });
   }

   private static Method getMethodByClassStringString(Class value, String text, String text2) {
      for (Method method : value.getDeclaredMethods()) {
         if (method.getName().equals(text) && Type.getMethodDescriptor(method).equals(text2)) {
            return method;
         }
      }

      return null;
   }

   private static String getStringByClassStringString(Class value, String text, String text2) {
      ArrayDeque arraydeque = new ArrayDeque();
      HashSet hashset = new HashSet();
      arraydeque.add(value);

      while (!arraydeque.isEmpty()) {
         Class oclass = (Class)arraydeque.poll();
         if (hashset.add(oclass)) {
            String s = ServerInfo.getStringByString2(Type.getInternalName(oclass));
            String s1 = ServerInfo.methodNamed(s, text, text2);
            if (!s1.equals(text)) {
               return s1;
            }

            if (oclass.getSuperclass() != null) {
               arraydeque.add(oclass.getSuperclass());
            }

            for (Class oclass1 : oclass.getInterfaces()) {
               arraydeque.add(oclass1);
            }
         }
      }

      return text;
   }

   public static byte[] getByteArrayByStringByteArray(String text, byte[] valueArray) {
      return null;
   }
}
