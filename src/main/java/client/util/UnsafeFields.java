package client.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import sun.misc.Unsafe;

public class UnsafeFields<T> {
   private static final Unsafe unsafe = UnsafeAccess.unsafe;
   private final Object value;
   private final Field field;
   private final boolean flag;
   private final Object value2;
   private final long time;

   public UnsafeFields(Object value, Class value2, Class value3) {
      this(value, value2, value3, 0);
   }

   public UnsafeFields(Object value, Class value2, Class value3, int count) {
      this(value, getFieldByClassClassInt(value2, value3, count));
   }

   public UnsafeFields(Object value, Class value2, int count) {
      this(value, value2.getDeclaredFields()[count]);
   }

   public UnsafeFields(Object value3, Field field2) {
      this.value = value3;
      this.field = field2;

      try {
         field2.setAccessible(true);
      } catch (Exception exception) {
      }

      boolean flagx = Modifier.isStatic(field2.getModifiers());
      Object object = null;
      long i = 0L;

      try {
         if (flagx) {
            object = unsafe.staticFieldBase(field2);
            i = unsafe.staticFieldOffset(field2);
         } else {
            i = unsafe.objectFieldOffset(field2);
         }
      } catch (Throwable throwable) {
      }

      this.flag = flagx;
      this.value2 = object;
      this.time = i;
   }

   public Object getObjectByObject(Object value) {
      try {
         return unsafe.getObject(this.getObjectByObject2(value), this.time);
      } catch (Exception exception) {
         return null;
      }
   }

   public void onObject(Object value2) {
      this.onObjectObject(this.value, value2);
   }

   public void onObjectInt(Object value, int count) {
      try {
         unsafe.putInt(this.getObjectByObject2(value), this.time, count);
      } catch (Exception exception) {
      }
   }

   public void onDouble(double value2) {
      this.onObjectDouble(this.value, value2);
   }

   public void onObjectBoolean(Object value, boolean flag) {
      try {
         unsafe.putBoolean(this.getObjectByObject2(value), this.time, flag);
      } catch (Exception exception) {
      }
   }

   public void onObjectDouble(Object value, double value2) {
      try {
         unsafe.putDouble(this.getObjectByObject2(value), this.time, value2);
      } catch (Exception exception) {
      }
   }

   public void onObjectFloat(Object value, Float value2) {
      try {
         unsafe.putFloat(this.getObjectByObject2(value), this.time, value2);
      } catch (Exception exception) {
      }
   }

   public void onObjectObject(Object value, Object value2) {
      try {
         unsafe.putObject(this.getObjectByObject2(value), this.time, value2);
      } catch (Exception exception) {
      }
   }

   public boolean check() {
      return this.isObject(this.value);
   }

   private static Field getFieldByClassClassInt(Class value, Class value2, int count) {
      for (Field fieldx : value.getDeclaredFields()) {
         if (fieldx.getType().equals(value2)) {
            if (count == 0) {
               return fieldx;
            }

            count--;
         }
      }

      return null;
   }

   public void onBoolean(boolean flag) {
      this.onObjectBoolean(this.value, flag);
   }

   public void onInt(int count) {
      this.onObjectInt(this.value, count);
   }

   public void onFloat(Float value2) {
      this.onObjectFloat(this.value, value2);
   }

   public float getFloatByObject(Object value) {
      try {
         return unsafe.getFloat(this.getObjectByObject2(value), this.time);
      } catch (Exception exception) {
         return 0.0F;
      }
   }

   public int getIntByObject(Object value) {
      try {
         return unsafe.getInt(this.getObjectByObject2(value), this.time);
      } catch (Exception exception) {
         return 0;
      }
   }

   public boolean isObject(Object value) {
      try {
         return unsafe.getBoolean(this.getObjectByObject2(value), this.time);
      } catch (Exception exception) {
         return false;
      }
   }

   public long getLongByObject(Object value) {
      try {
         return unsafe.getLong(this.getObjectByObject2(value), this.time);
      } catch (Exception exception) {
         return 0L;
      }
   }

   public Object getObject() {
      return this.getObjectByObject(this.value);
   }

   private Object getObjectByObject2(Object value) {
      return this.flag ? this.value2 : value;
   }

   public void onObject2(Object value) {
      if (this.getObject() != value) {
         this.onObject(value);
      }
   }

   public float getFloat() {
      return this.getFloatByObject(this.value);
   }

   public UnsafeFields getUnsafeFields() {
      try {
         Field fieldx = Field.class.getDeclaredField("modifiers");
         fieldx.setAccessible(true);
         int i = this.field.getModifiers();
         fieldx.setInt(this.field, i & -17 & -3);
      } catch (Exception exception) {
      }

      return this;
   }

   public int getInt() {
      return this.getIntByObject(this.value);
   }

   public long getLong() {
      return this.getLongByObject(this.value);
   }
}
