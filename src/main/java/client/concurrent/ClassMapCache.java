package client.concurrent;

import java.lang.invoke.MethodHandle;
import java.util.concurrent.ConcurrentHashMap;

public class ClassMapCache extends ClassValue<ConcurrentHashMap<String, MethodHandle>> {
   protected ConcurrentHashMap<String, MethodHandle> getConcurrentHashMapByClass(Class<?> value) {
      return new ConcurrentHashMap<>();
   }

   @Override
   protected ConcurrentHashMap<String, MethodHandle> computeValue(Class<?> value) {
      return this.getConcurrentHashMapByClass(value);
   }
}
