package b;

import java.lang.reflect.Method;

public final class DescriptorWriter {
   private DescriptorWriter() {
   }

   public static String of(Method method) {
      StringBuilder builder = new StringBuilder(64).append('(');

      for (Class<?> type : method.getParameterTypes()) {
         append(builder, type);
      }

      builder.append(')');
      append(builder, method.getReturnType());
      return builder.toString();
   }

   public static void append(StringBuilder builder, Class<?> type) {
      builder.append(type.descriptorString());
   }
}
