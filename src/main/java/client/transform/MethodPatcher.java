package client.transform;

import client.data.HookInfo;
import client.data.HookPointSwitchMap;
import client.data.HookRegistry;
import java.util.List;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

public final class MethodPatcher extends MethodVisitor {
   private final MethodNode methodNode;
   private final List<HookInfo> list;
   private final MethodVisitor methodVisitor;

   MethodPatcher(MethodNode methodNode2, List list2, MethodVisitor methodVisitor2) {
      super(589824, methodNode2);
      this.methodNode = methodNode2;
      this.list = list2;
      this.methodVisitor = methodVisitor2;
   }

   public void visitEnd() {
      super.visitEnd();

      for (HookInfo hookinfo : this.list) {
         switch (HookPointSwitchMap.intArray[hookinfo.hookPoint.ordinal()]) {
            case 1:
               HookRegistry.onMethodNodeHookInfo4(this.methodNode, hookinfo);
               break;
            case 2:
               HookRegistry.onMethodNodeHookInfo(this.methodNode, hookinfo);
               break;
            case 3:
               HookRegistry.onMethodNodeHookInfo2(this.methodNode, hookinfo);
               break;
            case 4:
               HookRegistry.onMethodNodeHookInfo3(this.methodNode, hookinfo);
         }
      }

      this.methodNode.accept(this.methodVisitor);
   }
}
