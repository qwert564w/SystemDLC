package client.transform;

import client.data.HookRegistry;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class BytecodeTransformer extends ClassWriter {
   public final ClassLoader classLoader;
   public final ClassLoader classLoader2;

   public BytecodeTransformer(ClassReader classReader, int count, ClassLoader classLoader3, ClassLoader classLoader4) {
      super(classReader, count);
      this.classLoader = classLoader3;
      this.classLoader2 = classLoader4;
   }

   protected String getCommonSuperClass(String text, String text2) {
      Class oclass = HookRegistry.getClassByStringClassLoaderClassLoader(text, this.classLoader, this.classLoader2);
      Class oclass1 = HookRegistry.getClassByStringClassLoaderClassLoader(text2, this.classLoader, this.classLoader2);
      if (oclass != null && oclass1 != null) {
         try {
            if (oclass.isAssignableFrom(oclass1)) {
               return text;
            } else if (oclass1.isAssignableFrom(oclass)) {
               return text2;
            } else if (!oclass.isInterface() && !oclass1.isInterface()) {
               do {
                  oclass = oclass.getSuperclass();
               } while (oclass != null && !oclass.isAssignableFrom(oclass1));

               return oclass == null ? "java/lang/Object" : oclass.getName().replace('.', '/');
            } else {
               return "java/lang/Object";
            }
         } catch (Throwable throwable) {
            return "java/lang/Object";
         }
      } else {
         return "java/lang/Object";
      }
   }
}
