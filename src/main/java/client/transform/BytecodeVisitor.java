package client.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

public class BytecodeVisitor extends ClassVisitor {
   public final Map map;
   public final Map map2;

   public BytecodeVisitor(int count, ClassVisitor classVisitor, Map map3, Map map4) {
      super(count, classVisitor);
      this.map = map3;
      this.map2 = map4;
   }

   public MethodVisitor visitMethod(int count, String text, String text2, String text3, String[] textArray) {
      MethodVisitor methodvisitor = super.visitMethod(count, text, text2, text3, textArray);
      ArrayList arraylist = new ArrayList();
      List list = (List)this.map.get(text + text2);
      List list1 = (List)this.map2.get(text);
      if (list != null) {
         arraylist.addAll(list);
      }

      if (list1 != null) {
         arraylist.addAll(list1);
      }

      if (arraylist.isEmpty()) {
         return methodvisitor;
      } else {
         MethodNode methodnode = new MethodNode(589824, count, text, text2, text3, textArray);
         return new MethodPatcher(methodnode, arraylist, methodvisitor);
      }
   }
}
