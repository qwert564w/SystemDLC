package client.transform;

import b.Boot;
import client.concurrent.HandleInvoker;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class BytecodeCache {
   private static final String text = Type.getInternalName(HandleInvoker.class);
   private static final Map<String, String> map = new HashMap<>();
   private static final Map<String, String> map2 = new HashMap<>();

   private BytecodeCache() {
   }

   static {
      update();
   }

   private static byte[] getByteArrayByClass(Class value) {
      String s = value.getName().replace('.', '/') + ".class";
      ClassLoader classloader = value.getClassLoader();
      if (classloader == null) {
         classloader = ClassLoader.getSystemClassLoader();
      }

      try {
         byte[] abyte;
         try (InputStream inputstream = classloader.getResourceAsStream(s)) {
            abyte = inputstream == null ? null : inputstream.readAllBytes();
         }

         return abyte;
      } catch (Exception exception) {
         return null;
      }
   }

   private static boolean isMethodNodeInt(MethodNode methodNode, int count) {
      if (methodNode.instructions != null && methodNode.instructions.size() != 0) {
         boolean flag = false;

         for (AbstractInsnNode abstractinsnnode = methodNode.instructions.getFirst(); abstractinsnnode != null; abstractinsnnode = abstractinsnnode.getNext()) {
            if (abstractinsnnode instanceof MethodInsnNode methodinsnnode && methodinsnnode.getOpcode() == 184 && text.equals(methodinsnnode.owner)) {
               String s = getStringByStringString(methodinsnnode.name, methodinsnnode.desc);
               String s1 = map.get(s);
               String s2 = map2.get(s);
               if (s1 != null && s2 != null) {
                  methodNode.instructions.insertBefore(methodinsnnode, new LdcInsnNode(count));
                  methodNode.instructions.insertBefore(methodinsnnode, new InsnNode(95));
                  methodinsnnode.name = s1;
                  methodinsnnode.desc = s2;
                  flag = true;
               }
            }
         }

         return flag;
      } else {
         return false;
      }
   }

   private static byte[] getByteArrayByByteArrayStringMap(byte[] valueArray, String text, Map map) {
      ClassReader classreader = new ClassReader(valueArray);
      ClassNode classnode = new ClassNode();
      classreader.accept(classnode, 0);
      boolean flag = false;

      for (MethodNode methodnode : classnode.methods) {
         Integer integer = (Integer)map.get(text + "." + methodnode.name + methodnode.desc);
         if (integer != null && integer >= 0) {
            flag |= isMethodNodeInt(methodnode, integer);
         }
      }

      if (!flag) {
         return null;
      } else {
         ClassWriter classwriter = new ClassWriter(3);
         classnode.accept(classwriter);
         return classwriter.toByteArray();
      }
   }

   private static String getStringByStringString(String text, String text2) {
      return text + text2;
   }

   public static void onClassMap(Class value, Map map2) {
      if (value != null && !map.isEmpty()) {
         byte[] abyte = getByteArrayByClass(value);
         if (abyte != null) {
            String s = Type.getInternalName(value);
            byte[] abyte1 = getByteArrayByByteArrayStringMap(abyte, s, map2);
            if (abyte1 != null) {
               try {
                  Boot.nativeRedefineClass(value, abyte1);
               } catch (Throwable throwable) {
               }
            }
         }
      }
   }

   private static void update() {
      HashMap hashmap = new HashMap();
      HashMap hashmap1 = new HashMap();

      for (Method method : HandleInvoker.class.getDeclaredMethods()) {
         if (Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers())) {
            Class[] aclass = method.getParameterTypes();
            Class oclass = method.getReturnType();
            if (aclass.length == 1 && aclass[0] == Object[].class) {
               hashmap.put(oclass, method);
            } else if (aclass.length == 2 && aclass[0] == int.class && aclass[1] == Object[].class) {
               hashmap1.put(oclass, method);
            }
         }
      }

      for (Entry entry : (Iterable<Entry>)(hashmap.entrySet())) {
         Method method1 = (Method)hashmap1.get(entry.getKey());
         if (method1 != null) {
            Method method2 = (Method)entry.getValue();
            String s = getStringByStringString(method2.getName(), Type.getMethodDescriptor(method2));
            map.put(s, method1.getName());
            map2.put(s, Type.getMethodDescriptor(method1));
         }
      }
   }
}
