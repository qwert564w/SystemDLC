package client.data;

import client.transform.BytecodeTransformer;
import client.transform.BytecodeVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class HookRegistry {
   private HookRegistry() {
   }

   public static void onMethodNodeHookInfo(MethodNode methodNode, HookInfo hookInfo) {
      for (AbstractInsnNode abstractinsnnode : methodNode.instructions) {
         int i = abstractinsnnode.getOpcode();
         if (i >= 172 && i <= 177) {
            InsnList insnlist = getInsnListByMethodNode(methodNode);
            insnlist.add(getMethodInsnNodeByHookInfo(hookInfo));
            methodNode.instructions.insertBefore(abstractinsnnode, insnlist);
         }
      }
   }

   public static void onMethodNodeHookInfo2(MethodNode methodNode, HookInfo hookInfo) {
      LabelNode labelnode = new LabelNode();
      Type type = Type.getReturnType(methodNode.desc);
      InsnList insnlist = new InsnList();
      insnlist.add(new MethodInsnNode(184, "b/Boot", "isCallingOriginal", "()Z", false));
      insnlist.add(new JumpInsnNode(154, labelnode));
      insnlist.add(getInsnListByMethodNode(methodNode));
      insnlist.add(getMethodInsnNodeByHookInfo(hookInfo));
      insnlist.add(new InsnNode(type.getOpcode(172)));
      insnlist.add(labelnode);
      methodNode.instructions.insert(insnlist);
   }

   public static void onMethodNodeHookInfo3(MethodNode methodNode, HookInfo hookInfo) {
      InsnList insnlist = getInsnListByMethodNode(methodNode);
      insnlist.add(getMethodInsnNodeByHookInfo(hookInfo));
      LabelNode labelnode = new LabelNode();
      insnlist.add(new JumpInsnNode(154, labelnode));
      Type type = Type.getReturnType(methodNode.desc);
      switch (type.getSort()) {
         case 0:
            insnlist.add(new InsnNode(177));
            break;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            insnlist.add(new InsnNode(3));
            insnlist.add(new InsnNode(172));
            break;
         case 6:
            insnlist.add(new InsnNode(11));
            insnlist.add(new InsnNode(174));
            break;
         case 7:
            insnlist.add(new InsnNode(9));
            insnlist.add(new InsnNode(173));
            break;
         case 8:
            insnlist.add(new InsnNode(14));
            insnlist.add(new InsnNode(175));
            break;
         default:
            insnlist.add(new InsnNode(1));
            insnlist.add(new InsnNode(176));
      }

      insnlist.add(labelnode);
      methodNode.instructions.insert(insnlist);
   }

   public static byte[] getByteArrayByByteArrayList(byte[] valueArray, List<HookInfo> list) {
      return getByteArrayByByteArrayListClassLoader(valueArray, list, null);
   }

   private static MethodInsnNode getMethodInsnNodeByHookInfo(HookInfo hookInfo) {
      return new MethodInsnNode(184, hookInfo.text4, hookInfo.text5, hookInfo.text6, false);
   }

   private static byte[] getByteArrayByByteArrayListClassLoaderBoolean(byte[] valueArray, List<HookInfo> list, ClassLoader classLoader, boolean flag) {
      ClassReader classreader = new ClassReader(valueArray);
      HashMap<String, List<HookInfo>> hashmap = new HashMap<>();
      HashMap<String, List<HookInfo>> hashmap1 = new HashMap<>();

      for (HookInfo hookinfo : list) {
         if (hookinfo.text3 != null) {
            hashmap.computeIfAbsent(hookinfo.text2 + hookinfo.text3, var0x -> new ArrayList<>()).add(hookinfo);
         } else {
            hashmap1.computeIfAbsent(hookinfo.text2, var0x -> new ArrayList<>()).add(hookinfo);
         }
      }

      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      if (classloader == null) {
         classloader = HookRegistry.class.getClassLoader();
      }

      ClassLoader classloader1 = classLoader != null ? classLoader : classloader;
      int i = flag ? 3 : 1;
      BytecodeTransformer bytecodetransformer = new BytecodeTransformer(classreader, i, classloader1, classloader);
      BytecodeVisitor bytecodevisitor = new BytecodeVisitor(589824, bytecodetransformer, hashmap, hashmap1);
      classreader.accept(bytecodevisitor, 0);
      return bytecodetransformer.toByteArray();
   }

   public static Class getClassByStringClassLoaderClassLoader(String text, ClassLoader classLoader, ClassLoader classLoader2) {
      String s = text.replace('/', '.');

      try {
         return Class.forName(s, false, classLoader);
      } catch (Throwable throwable2) {
         if (classLoader2 != classLoader) {
            try {
               return Class.forName(s, false, classLoader2);
            } catch (Throwable throwable1) {
            }
         }

         try {
            return Class.forName(s, false, ClassLoader.getSystemClassLoader());
         } catch (Throwable throwable) {
            return null;
         }
      }
   }

   private static InsnList getInsnListByMethodNode(MethodNode methodNode) {
      InsnList insnlist = new InsnList();
      boolean flag = (methodNode.access & 8) != 0;
      int i = 0;
      if (!flag) {
         insnlist.add(new VarInsnNode(25, 0));
         i = 1;
      }

      for (Type type : Type.getArgumentTypes(methodNode.desc)) {
         insnlist.add(new VarInsnNode(type.getOpcode(21), i));
         i += type.getSize();
      }

      return insnlist;
   }

   public static byte[] getByteArrayByByteArrayListClassLoader(byte[] valueArray, List list, ClassLoader classLoader) {
      try {
         return getByteArrayByByteArrayListClassLoaderBoolean(valueArray, list, classLoader, true);
      } catch (ArrayIndexOutOfBoundsException | NegativeArraySizeException negativearraysizeexception) {
         return getByteArrayByByteArrayListClassLoaderBoolean(valueArray, list, classLoader, false);
      }
   }

   public static void onMethodNodeHookInfo4(MethodNode methodNode, HookInfo hookInfo) {
      InsnList insnlist = getInsnListByMethodNode(methodNode);
      insnlist.add(getMethodInsnNodeByHookInfo(hookInfo));
      methodNode.instructions.insert(insnlist);
   }
}
