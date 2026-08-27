package client.transform;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

public final class MixinTransformerAccess {
   private Object value;
   private Object value2;
   private Method method;
   private boolean flag;
   private Object value3;
   private Method method2;
   private boolean flag2;

   private byte[] getByteArrayByStringClassLoader(String text, ClassLoader classLoader) {
      Method methodx = this.getMethodByClassLoader(classLoader);
      if (methodx == null) {
         return null;
      } else {
         try {
            return (byte[])methodx.invoke(this.value2, text, true);
         } catch (Throwable throwable) {
            return null;
         }
      }
   }

   private Object getObjectByClassLoader(ClassLoader classLoader) {
      if (this.flag2) {
         return this.value3;
      } else {
         synchronized (this) {
            if (this.flag2) {
               return this.value3;
            }

            this.flag2 = true;
            this.value3 = this.getObjectByClassLoader2(classLoader);
         }

         return this.value3;
      }
   }

   private Method getMethodByObject(Object value) {
      if (this.method2 != null) {
         return this.method2;
      } else {
         synchronized (this) {
            if (this.method2 != null) {
               return this.method2;
            } else {
               this.method2 = getMethodByClassStringClassArray(value.getClass(), "transformClassBytes", String.class, String.class, byte[].class);
               if (this.method2 == null) {
                  this.method2 = getMethodByClassClassClassArray2(value.getClass(), byte[].class, String.class, String.class, byte[].class);
               }

               if (this.method2 != null) {
                  this.method2.setAccessible(true);
               }

               return this.method2;
            }
         }
      }
   }

   private static Method getMethodByClassClassClassArray(Class value, Class value2, Class... value3) {
      for (Method methodx : value.getDeclaredMethods()) {
         if (methodx.getReturnType() == value2) {
            Class[] aclass = methodx.getParameterTypes();
            if (aclass.length == value3.length) {
               boolean flagx = true;

               for (int i = 0; i < aclass.length; i++) {
                  if (aclass[i] != value3[i]) {
                     flagx = false;
                     break;
                  }
               }

               if (flagx) {
                  return methodx;
               }
            }
         }
      }

      return null;
   }

   private Object getObjectByClassLoader2(ClassLoader classLoader) {
      Predicate<String> predicate = var0 -> var0.contains("Transformer") || var0.contains("Processor");
      Object object = this.getObjectByClassLoader3(classLoader);
      if (object != null) {
         Object object1 = getObjectByObjectString(object, "getActiveTransformer");
         if (object1 != null) {
            return object1;
         }

         Object object2 = getObjectByObjectPredicate(object, predicate);
         if (object2 != null) {
            return object2;
         }
      }

      Object object3 = this.getObjectByClassLoader4(classLoader);
      if (object3 != null) {
         Object object4 = getObjectByObjectPredicate(object3, predicate);
         if (object4 != null) {
            return object4;
         }
      }

      return null;
   }

   private Object getObjectByClassLoader3(ClassLoader classLoader) {
      for (ClassLoader classloader : (Iterable<ClassLoader>)(this.getSetByClassLoader(classLoader))) {
         try {
            Class oclass = Class.forName("org.spongepowered.asm.mixin.MixinEnvironment", true, classloader);
            Object object = oclass.getMethod("getCurrentEnvironment").invoke(null);
            if (object != null) {
               return object;
            }
         } catch (Throwable throwable) {
         }
      }

      return null;
   }

   private Object getObjectByClassLoader4(ClassLoader classLoader) {
      if (this.value != null) {
         return this.value;
      } else {
         for (ClassLoader classloader : (Iterable<ClassLoader>)(this.getSetByClassLoader(classLoader))) {
            try {
               Class oclass = Class.forName("org.spongepowered.asm.service.MixinService", true, classloader);
               Object object = oclass.getMethod("getService").invoke(null);
               if (object != null) {
                  this.value = object;
                  return object;
               }
            } catch (Throwable throwable) {
            }
         }

         return null;
      }
   }

   private static Method getMethodByClassStringClassArray(Class value, String text, Class... value2) {
      for (Class oclass = value; oclass != null; oclass = oclass.getSuperclass()) {
         try {
            return oclass.getDeclaredMethod(text, value2);
         } catch (NoSuchMethodException nosuchmethodexception1) {
            for (Class oclass1 : oclass.getInterfaces()) {
               try {
                  return oclass1.getDeclaredMethod(text, value2);
               } catch (NoSuchMethodException nosuchmethodexception) {
               }
            }
         }
      }

      return null;
   }

   private static Object getObjectByObjectPredicate(Object value, Predicate predicate) {
      for (Class oclass = value.getClass(); oclass != null; oclass = oclass.getSuperclass()) {
         for (Field field : oclass.getDeclaredFields()) {
            if (predicate.test(field.getType().getSimpleName())) {
               try {
                  field.setAccessible(true);
                  Object object = field.get(value);
                  if (object != null) {
                     return object;
                  }
               } catch (Throwable throwable) {
               }
            }
         }
      }

      return null;
   }

   private static Object getObjectByObjectString(Object value, String text) {
      try {
         Method methodx = value.getClass().getMethod(text);
         methodx.setAccessible(true);
         return methodx.invoke(value);
      } catch (Throwable throwable) {
         return null;
      }
   }

   private Set getSetByClassLoader(ClassLoader classLoader) {
      LinkedHashSet linkedhashset = new LinkedHashSet();
      if (classLoader != null) {
         linkedhashset.add(classLoader);
      }

      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      if (classloader != null) {
         linkedhashset.add(classloader);
      }

      linkedhashset.add(this.getClass().getClassLoader());
      ClassLoader classloader1 = ClassLoader.getSystemClassLoader();
      if (classloader1 != null) {
         linkedhashset.add(classloader1);
      }

      return linkedhashset;
   }

   private static Object getObjectByFieldObject(Field field, Object value) {
      try {
         field.setAccessible(true);
         Object object = field.get(value);
         return isObject(object) ? object : null;
      } catch (Throwable throwable) {
         return null;
      }
   }

   private static Object getObjectByMethodObject(Method method, Object value) {
      try {
         method.setAccessible(true);
         Object object = method.invoke(value);
         return isObject(object) ? object : null;
      } catch (Throwable throwable) {
         return null;
      }
   }

   private Object getObjectByObject(Object value) {
      for (Class oclass = value.getClass(); oclass != null && oclass != Object.class; oclass = oclass.getSuperclass()) {
         for (Method methodx : oclass.getDeclaredMethods()) {
            if (methodx.getParameterCount() == 0 && !Modifier.isStatic(methodx.getModifiers()) && isClass(methodx.getReturnType())) {
               Object object = getObjectByMethodObject(methodx, value);
               if (object != null && getMethodByObject2(object) != null) {
                  return object;
               }
            }
         }
      }

      for (Class oclass1 = value.getClass(); oclass1 != null && oclass1 != Object.class; oclass1 = oclass1.getSuperclass()) {
         for (Field field : oclass1.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && isClass(field.getType())) {
               Object object1 = getObjectByFieldObject(field, value);
               if (object1 != null && getMethodByObject2(object1) != null) {
                  return object1;
               }
            }
         }
      }

      return isObject(value) && getMethodByObject2(value) != null ? value : null;
   }

   private Method getMethodByClassLoader(ClassLoader classLoader) {
      if (this.flag) {
         return this.method;
      } else {
         synchronized (this) {
            if (this.flag) {
               return this.method;
            } else {
               this.flag = true;
               Object object = this.getObjectByClassLoader4(classLoader);
               if (object == null) {
                  return null;
               } else {
                  this.value2 = this.getObjectByObject(object);
                  if (this.value2 == null) {
                     return null;
                  } else {
                     this.method = getMethodByObject2(this.value2);
                     if (this.method != null) {
                        this.method.setAccessible(true);
                     }

                     return this.method;
                  }
               }
            }
         }
      }
   }

   public byte[] getByteArrayByStringClassLoader2(String text, ClassLoader classLoader) {
      String s = text.replace('/', '.');
      byte[] abyte = this.getByteArrayByStringClassLoader(s, classLoader);
      if (abyte == null) {
         return null;
      } else {
         Object object = this.getObjectByClassLoader(classLoader);
         if (object == null) {
            return abyte;
         } else {
            Method methodx = this.getMethodByObject(object);
            if (methodx == null) {
               return abyte;
            } else {
               try {
                  byte[] abyte1 = (byte[])methodx.invoke(object, s, s, abyte);
                  return abyte1 == null ? abyte : abyte1;
               } catch (Throwable throwable) {
                  return abyte;
               }
            }
         }
      }
   }

   public byte[] getByteArrayByString(String text) {
      return this.getByteArrayByStringClassLoader2(text, null);
   }

   private static void onMethod(Method method) {
      try {
         method.setAccessible(true);
      } catch (Throwable throwable) {
      }
   }

   private static Method getMethodByClassClassClassArray2(Class value, Class value2, Class... value3) {
      for (Class oclass = value; oclass != null && oclass != Object.class; oclass = oclass.getSuperclass()) {
         Method methodx = getMethodByClassClassClassArray(oclass, value2, value3);
         if (methodx != null) {
            onMethod(methodx);
            return methodx;
         }

         for (Class oclass1 : oclass.getInterfaces()) {
            methodx = getMethodByClassClassClassArray(oclass1, value2, value3);
            if (methodx != null) {
               try {
                  Method method1 = value.getMethod(methodx.getName(), value3);
                  onMethod(method1);
                  return method1;
               } catch (NoSuchMethodException nosuchmethodexception) {
                  onMethod(methodx);
                  return methodx;
               }
            }
         }
      }

      return null;
   }

   private static Method getMethodByObject2(Object value) {
      return getMethodByClassClassClassArray2(value.getClass(), byte[].class, String.class, boolean.class);
   }

   private static boolean isObject(Object value) {
      return value != null && isClass(value.getClass());
   }

   private static boolean isClass(Class value) {
      if (value == null || value == void.class || value == Object.class) {
         return false;
      } else if (!value.isPrimitive() && !value.isArray()) {
         String s = value.getName();
         return !s.startsWith("java.lang.")
            && !s.startsWith("java.util.")
            && !s.startsWith("java.io.")
            && !s.startsWith("java.net.")
            && !s.startsWith("java.nio.")
            && !s.startsWith("java.time.")
            && !s.startsWith("java.security.");
      } else {
         return false;
      }
   }
}
