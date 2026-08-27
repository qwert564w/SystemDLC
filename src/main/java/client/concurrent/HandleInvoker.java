package client.concurrent;

import client.transform.ClassRedefiner;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class HandleInvoker {
   private static final StackWalker stackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
   private static final ClassValue<ConcurrentHashMap<String, MethodHandle>> classValue = new ClassMapCache();
   private static final MethodHandle methodHandle = MethodHandles.identity(Object.class);
   private static MethodHandle[] methodHandleArray = new MethodHandle[64];
   private static final AtomicInteger atomicInteger = new AtomicInteger(0);
   private static final ThreadLocal<Boolean> mixinGuard = ThreadLocal.withInitial(() -> Boolean.FALSE);

   public static boolean isMixinGuard() {
      return mixinGuard.get();
   }

   public static void setMixinGuard(boolean value) {
      mixinGuard.set(value);
   }

   private HandleInvoker() {
   }

   private static Object getObjectByIntObjectArray(int count, Object[] valueArray) {
      MethodHandle[] amethodhandle = methodHandleArray;
      if (count >= 0 && count < amethodhandle.length) {
         MethodHandle methodhandle = amethodhandle[count];
         if (methodhandle == null) {
            return null;
         } else {
            ClassRedefiner.update4();
            return getObjectByMethodHandleObjectArray(methodhandle, valueArray);
         }
      } else {
         return null;
      }
   }

   private static Object getObjectByObjectArray(Object[] valueArray) {
      StackFrame stackframe = stackWalker.walk(var0x -> var0x.skip(2L).findFirst().orElse(null));
      if (stackframe == null) {
         return null;
      } else {
         Class oclass = stackframe.getDeclaringClass();
         String s = stackframe.getMethodName();
         String s1 = stackframe.getDescriptor();
         String s2 = s + s1;
         ConcurrentHashMap concurrenthashmap = classValue.get(oclass);
         MethodHandle methodhandle = (MethodHandle)concurrenthashmap.get(s2);
         if (methodhandle == null) {
            String s3 = oclass.getName().replace('.', '/');
            MethodHandle methodhandle1 = ClassRedefiner.getMethodHandleByStringStringString(s3, s, s1);
            methodhandle = methodhandle1 != null ? methodhandle1 : methodHandle;
            concurrenthashmap.put(s2, methodhandle);
         }

         if (methodhandle == methodHandle) {
            return null;
         } else {
            ClassRedefiner.update4();
            return getObjectByMethodHandleObjectArray(methodhandle, valueArray);
         }
      }
   }

   public static long getLongByObjectArray(Object... value) {
      return getObjectByObjectArray(value) instanceof Number number ? number.longValue() : 0L;
   }

   public static float getFloatByObjectArray(Object... value) {
      return getObjectByObjectArray(value) instanceof Number number ? number.floatValue() : 0.0F;
   }

   public static double getDoubleByObjectArray(Object... value) {
      return getObjectByObjectArray(value) instanceof Number number ? number.doubleValue() : 0.0;
   }

   public static boolean isObjectArray(Object... value) {
      return getObjectByObjectArray(value) instanceof Boolean obool && obool;
   }

   public static int getIntByObjectArray(Object... value) {
      return getObjectByObjectArray(value) instanceof Number number ? number.intValue() : 0;
   }

   public static Object getObjectByObjectArray2(Object... value) {
      return getObjectByObjectArray(value);
   }

   private static Object getObjectByMethodHandleObjectArray(MethodHandle methodHandle, Object[] valueArray) {
      try {
         int i = valueArray == null ? 0 : valueArray.length;

         return switch (i) {
            case 0 -> (Object)methodHandle.invoke();
            case 1 -> (Object)methodHandle.invoke((Object)valueArray[0]);
            case 2 -> (Object)methodHandle.invoke((Object)valueArray[0], (Object)valueArray[1]);
            case 3 -> (Object)methodHandle.invoke((Object)valueArray[0], (Object)valueArray[1], (Object)valueArray[2]);
            case 4 -> (Object)methodHandle.invoke((Object)valueArray[0], (Object)valueArray[1], (Object)valueArray[2], (Object)valueArray[3]);
            case 5 -> (Object)methodHandle.invoke((Object)valueArray[0], (Object)valueArray[1], (Object)valueArray[2], (Object)valueArray[3], (Object)valueArray[4]);
            case 6 -> (Object)methodHandle.invoke((Object)valueArray[0], (Object)valueArray[1], (Object)valueArray[2], (Object)valueArray[3], (Object)valueArray[4], (Object)valueArray[5]);
            case 7 -> (Object)methodHandle.invoke((Object)valueArray[0], (Object)valueArray[1], (Object)valueArray[2], (Object)valueArray[3], (Object)valueArray[4], (Object)valueArray[5], (Object)valueArray[6]);
            case 8 -> (Object)methodHandle.invoke(
               (Object)valueArray[0], (Object)valueArray[1], (Object)valueArray[2], (Object)valueArray[3], (Object)valueArray[4], (Object)valueArray[5], (Object)valueArray[6], (Object)valueArray[7]
            );
            default -> methodHandle.invokeWithArguments(valueArray);
         };
      } catch (Throwable throwable) {
         return null;
      }
   }

   public static void update() {
      ClassRedefiner.update4();
   }

   public static void onObjectArray(Object... value) {
      getObjectByObjectArray(value);
   }

   public static int getIntByIntObjectArray(int count, Object[] valueArray) {
      return getObjectByIntObjectArray(count, valueArray) instanceof Number number ? number.intValue() : 0;
   }

   public static boolean isIntObjectArray(int count, Object[] valueArray) {
      return getObjectByIntObjectArray(count, valueArray) instanceof Boolean obool && obool;
   }

   public static Object getObjectByIntObjectArray2(int count, Object[] valueArray) {
      return getObjectByIntObjectArray(count, valueArray);
   }

   private static void setInt(int count) {
      MethodHandle[] amethodhandle = methodHandleArray;
      if (count > amethodhandle.length) {
         int i = amethodhandle.length;

         while (i < count) {
            i *= 2;
         }

         MethodHandle[] amethodhandle1 = new MethodHandle[i];
         System.arraycopy(amethodhandle, 0, amethodhandle1, 0, amethodhandle.length);
         methodHandleArray = amethodhandle1;
      }
   }

   private static MethodHandle getMethodHandleByMethodHandle(MethodHandle methodHandle) {
      try {
         int i = methodHandle.type().parameterCount();
         Class[] aclass = new Class[i];
         Arrays.fill(aclass, Object.class);
         return methodHandle.asType(MethodType.methodType(Object.class, aclass));
      } catch (Throwable throwable) {
         return methodHandle;
      }
   }

   public static synchronized int getIntByMethodHandle(MethodHandle methodHandle) {
      if (methodHandle == null) {
         return -1;
      } else {
         int i = atomicInteger.getAndIncrement();
         setInt(i + 1);
         methodHandleArray[i] = getMethodHandleByMethodHandle(methodHandle);
         return i;
      }
   }

   public static void onIntObjectArray(int count, Object[] valueArray) {
      getObjectByIntObjectArray(count, valueArray);
   }

   public static double getDoubleByIntObjectArray(int count, Object[] valueArray) {
      return getObjectByIntObjectArray(count, valueArray) instanceof Number number ? number.doubleValue() : 0.0;
   }

   public static float getFloatByIntObjectArray(int count, Object[] valueArray) {
      return getObjectByIntObjectArray(count, valueArray) instanceof Number number ? number.floatValue() : 0.0F;
   }

   public static long getLongByIntObjectArray(int count, Object[] valueArray) {
      return getObjectByIntObjectArray(count, valueArray) instanceof Number number ? number.longValue() : 0L;
   }
}
