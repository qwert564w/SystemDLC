package client.transform;

import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public final class MethodIndex {
   private static final Map<Class<?>, ClassNode> map = new HashMap<>();
   private static final MixinTransformerAccess mixinTransformerAccess = new MixinTransformerAccess();

   private MethodIndex() {
   }

   private static boolean isString(String text) {
      if (text.startsWith("method_")) {
         return true;
      } else {
         return switch (text) {
            case "<init>", "<clinit>", "toString", "hashCode", "equals", "close", "run", "call", "tick", "render" -> true;
            default -> false;
         };
      }
   }

   private static ClassNode getClassNodeByClassString(Class value, String text) {
      try {
         ClassNode classnode = new ClassNode();
         classnode.version = 65;
         classnode.access = value.getModifiers() & 65535;
         classnode.name = text;
         Class oclass = value.getSuperclass();
         classnode.superName = oclass == null ? null : Type.getInternalName(oclass);

         for (Method method : value.getDeclaredMethods()) {
            if (isString(method.getName())) {
               MethodNode methodnode = new MethodNode();
               methodnode.access = method.getModifiers() & 65535;
               methodnode.name = method.getName();
               methodnode.desc = Type.getMethodDescriptor(method);
               methodnode.exceptions = new ArrayList();

               for (Class oclass1 : method.getExceptionTypes()) {
                  methodnode.exceptions.add(Type.getInternalName(oclass1));
               }

               classnode.methods.add(methodnode);
            }
         }

         for (Constructor constructor : value.getDeclaredConstructors()) {
            MethodNode methodnode1 = new MethodNode();
            methodnode1.access = constructor.getModifiers() & 65535;
            methodnode1.name = "<init>";
            methodnode1.desc = Type.getConstructorDescriptor(constructor);
            classnode.methods.add(methodnode1);
         }

         return classnode;
      } catch (Throwable throwable) {
         return null;
      }
   }

   private static Class[] getClassArrayByClassLoaderTypeArray(ClassLoader classLoader, Type[] typeArray) {
      Class[] aclass = new Class[typeArray.length];

      for (int i = 0; i < typeArray.length; i++) {
         try {
            aclass[i] = getClassByTypeClassLoader(typeArray[i], classLoader);
         } catch (ClassNotFoundException classnotfoundexception) {
            return null;
         }
      }

      return aclass;
   }

   private static Class getClassByTypeClassLoader(Type type, ClassLoader classLoader) throws ClassNotFoundException {
      return switch (type.getSort()) {
         case 1 -> boolean.class;
         case 2 -> char.class;
         case 3 -> byte.class;
         case 4 -> short.class;
         case 5 -> int.class;
         case 6 -> float.class;
         case 7 -> long.class;
         case 8 -> double.class;
         case 9 -> Class.forName(type.getDescriptor().replace('/', '.'), false, classLoader);
         default -> Class.forName(type.getClassName(), false, classLoader);
      };
   }

   private static byte[] getByteArrayByClassString(Class value, String text) {
      try {
         byte[] abyte = mixinTransformerAccess.getByteArrayByStringClassLoader2(text, value.getClassLoader());
         if (abyte != null) {
            return abyte;
         }
      } catch (Throwable throwable) {
      }

      ClassLoader classloader = value.getClassLoader();
      if (classloader == null) {
         classloader = ClassLoader.getSystemClassLoader();
      }

      try {
         byte[] abyte1;
         try (InputStream inputstream = classloader.getResourceAsStream(text + ".class")) {
            abyte1 = inputstream == null ? null : inputstream.readAllBytes();
         }

         return abyte1;
      } catch (Throwable throwable2) {
         return null;
      }
   }

   public static Method getMethodByClassInt(Class value, int count) {
      MethodNode methodnode = getMethodNodeByClassInt(value, count);
      if (methodnode == null) {
         return null;
      } else {
         Class[] aclass = getClassArrayByClassLoaderTypeArray(value.getClassLoader(), Type.getArgumentTypes(methodnode.desc));
         if (aclass == null) {
            return null;
         } else {
            try {
               Method method = value.getDeclaredMethod(methodnode.name, aclass);
               method.setAccessible(true);
               return method;
            } catch (NoSuchMethodException nosuchmethodexception) {
               return null;
            }
         }
      }
   }

   public static MethodHandle getMethodHandleByClassInt(Class value, int count) {
      Method method = getMethodByClassInt(value, count);
      if (method == null) {
         return null;
      } else {
         try {
            return MethodHandles.lookup().unreflect(method);
         } catch (IllegalAccessException illegalaccessexception) {
            return null;
         }
      }
   }

   private static synchronized ClassNode getClassNodeByClass(Class value) {
      ClassNode classnode = map.get(value);
      if (classnode != null) {
         return classnode;
      } else {
         String s = Type.getInternalName(value);
         byte[] abyte = getByteArrayByClassString(value, s);
         if (abyte != null) {
            try {
               ClassReader classreader = new ClassReader(abyte);
               ClassNode classnode2 = new ClassNode();
               classreader.accept(classnode2, 7);
               map.put(value, classnode2);
               return classnode2;
            } catch (Throwable throwable) {
            }
         }

         ClassNode classnode1 = getClassNodeByClassString(value, s);
         if (classnode1 != null) {
            map.put(value, classnode1);
         }

         return classnode1;
      }
   }

   public static MethodNode getMethodNodeByClassInt(Class value, int count) {
      ClassNode classnode = getClassNodeByClass(value);
      return classnode != null && count >= 0 && count < classnode.methods.size() ? (MethodNode)classnode.methods.get(count) : null;
   }
}
