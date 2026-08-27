package client.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class ReflectionCache {
   private ReflectionCache() {
   }

   public static long getLongByClassClass(Class value, Class value2) {
      for (Field field : value.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers()) && value2.isAssignableFrom(field.getType())) {
            return UnsafeAccess.unsafe.objectFieldOffset(field);
         }
      }

      return 0L;
   }

   public static void onObjectLongInt(Object value, long time, int count) {
      UnsafeAccess.unsafe.putInt(value, time, count);
   }

   public static void onObjectLongObject(Object value, long time, Object value2) {
      UnsafeAccess.unsafe.putObject(value, time, value2);
   }

   public static boolean isObjectLong(Object value, long time) {
      return UnsafeAccess.unsafe.getBoolean(value, time);
   }

   public static double getDoubleByObjectLong(Object value, long time) {
      return UnsafeAccess.unsafe.getDouble(value, time);
   }

   public static void onObjectLongBoolean(Object value, long time, boolean flag) {
      UnsafeAccess.unsafe.putBoolean(value, time, flag);
   }

   public static void onObjectLongDouble(Object value, long time, double value2) {
      UnsafeAccess.unsafe.putDouble(value, time, value2);
   }

   public static void onObjectLongFloat(Object value, long time, float value2) {
      UnsafeAccess.unsafe.putFloat(value, time, value2);
   }

   public static void onObjectLongLong(Object value, long time, long time2) {
      UnsafeAccess.unsafe.putLong(value, time, time2);
   }

   public static Object getObjectByObjectLong(Object value, long time) {
      return UnsafeAccess.unsafe.getObject(value, time);
   }

   public static long getLongByClassInt(Class value, int count) {
      int i = 0;

      for (Field field : value.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers())) {
            if (i == count) {
               return UnsafeAccess.unsafe.objectFieldOffset(field);
            }

            i++;
         }
      }

      return 0L;
   }

   public static long getLongByClassClassInt(Class value, Class value2, int count) {
      int i = 0;

      for (Field field : value.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers()) && field.getType() == value2) {
            if (i == count) {
               return UnsafeAccess.unsafe.objectFieldOffset(field);
            }

            i++;
         }
      }

      return 0L;
   }

   public static long getLongByClassClass2(Class value, Class value2) {
      return getLongByClassClassInt(value, value2, 0);
   }

   public static int getIntByObjectLong(Object value, long time) {
      return UnsafeAccess.unsafe.getInt(value, time);
   }

   public static long getLongByObjectLong(Object value, long time) {
      return UnsafeAccess.unsafe.getLong(value, time);
   }

   public static float getFloatByObjectLong(Object value, long time) {
      return UnsafeAccess.unsafe.getFloat(value, time);
   }
}
