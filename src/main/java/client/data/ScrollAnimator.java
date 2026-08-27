package client.data;

import client.api.ListEntry;
import client.util.Animation;
import client.util.Interpolation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class ScrollAnimator<T extends ListEntry> {
   private static final float value = 0.001F;
   private final LinkedHashMap<Object, T> linkedHashMap = new LinkedHashMap<>();
   private final HashSet<Object> hashSet = new HashSet<>();
   private final float value2;
   private final Interpolation interpolation = new Interpolation();
   private long time = -1L;
   private boolean flag;

   public ScrollAnimator(float value) {
      this.value2 = value;
   }

   private float getFloatByToDoubleFunction(ToDoubleFunction<? super T> toDoubleFunction) {
      float f = 0.0F;
      boolean flagx = true;

      for (T listentry : this.linkedHashMap.values()) {
         float f1 = listentry.animation().getFloat();
         if (!flagx) {
            f += this.value2 * f1;
         }

         flagx = false;
         f += (float)toDoubleFunction.applyAsDouble(listentry) * f1;
      }

      return f;
   }

   public void setFlag() {
      this.flag = true;
   }

   private void onFloatToDoubleFunction(float value, ToDoubleFunction<? super T> toDoubleFunction) {
      float f = value;
      boolean flagx = true;

      for (T listentry : this.linkedHashMap.values()) {
         float f1 = listentry.animation().getFloat();
         if (!flagx) {
            f += this.value2 * f1;
         }

         flagx = false;
         listentry.animation().setFloat4(f);
         f += (float)toDoubleFunction.applyAsDouble(listentry) * f1;
      }
   }

   public boolean check() {
      for (T listentry : this.linkedHashMap.values()) {
         if (listentry.animation().getValue5() > 0.001F) {
            return true;
         }
      }

      return false;
   }

   public void onFloatToDoubleFunction2(float value, ToDoubleFunction<? super T> toDoubleFunction) {
      this.onFloatToDoubleFunction(value, toDoubleFunction);
   }

   public void setFloat(float value) {
      this.onFloatToDoubleFunction(value, ListEntry::itemHeight);
      if (this.flag) {
         for (T listentry : this.linkedHashMap.values()) {
            Animation animation = listentry.animation();
            animation.setFloat(animation.getValue8());
         }

         this.flag = false;
      }
   }

   public boolean check2() {
      return this.linkedHashMap.isEmpty();
   }

   private Set getSetByList(List list) {
      this.hashSet.clear();
      this.hashSet.addAll(list);
      return this.hashSet;
   }

   public float getFloatByToDoubleFunction2(ToDoubleFunction<? super T> toDoubleFunction) {
      return this.getFloatByToDoubleFunction(toDoubleFunction);
   }

   public float getFloat() {
      return this.getFloatByToDoubleFunction(ListEntry::itemHeight);
   }

   public Collection<T> getCollection() {
      return this.linkedHashMap.values();
   }

   private static void onBooleanAnimationIntFloat(boolean flag, Animation animation, int count, float value) {
      if (value > 0.0F) {
         float f = count * value;
         animation.onFloatBoolean(f, flag);
      } else {
         animation.setBoolean(flag);
      }
   }

   public void onFloatListFunction(float value, List list, Function function2) {
      Set set = this.getSetByList(list);
      int i = 0;

      for (Object object : list) {
         ListEntry listentry = this.linkedHashMap.get(object);
         if (listentry != null) {
            listentry.animation().setBoolean(true);
         } else {
            ListEntry listentry1 = (ListEntry)function2.apply(object);
            if (listentry1 != null) {
               Animation animation2 = listentry1.animation();
               boolean flagx = true;
               Animation animation = animation2;
               onBooleanAnimationIntFloat(flagx, animation, i, value);
               this.linkedHashMap.put(object, (T)listentry1);
               i++;
            }
         }
      }

      int j = 0;

      for (Entry entry : this.linkedHashMap.entrySet()) {
         if (!set.contains(entry.getKey())) {
            Animation animation1 = ((ListEntry)entry.getValue()).animation();
            if (animation1.isFlag()) {
               boolean flag1 = false;
               onBooleanAnimationIntFloat(flag1, animation1, j, value);
               j++;
            }
         }
      }
   }

   public void onListFunction(List list, Function function2) {
      float f = 0.0F;
      this.onFloatListFunction(f, list, function2);
   }

   public boolean isLong(long time2) {
      if (this.time == time2) {
         return false;
      } else {
         this.time = time2;
         return true;
      }
   }

   public void onList(List list) {
      Set set = this.getSetByList(list);
      if (!this.isSetList(set, list)) {
         LinkedHashMap linkedhashmap = new LinkedHashMap(this.linkedHashMap.size());
         Iterator iterator = list.iterator();

         for (Entry entry : this.linkedHashMap.entrySet()) {
            Object object = entry.getKey();
            if (!set.contains(object)) {
               linkedhashmap.put(object, (ListEntry)entry.getValue());
            } else {
               while (iterator.hasNext()) {
                  Object object1 = iterator.next();
                  ListEntry listentry = this.linkedHashMap.get(object1);
                  if (listentry != null) {
                     linkedhashmap.put(object1, listentry);
                     if (object1.equals(object)) {
                        break;
                     }
                  }
               }
            }
         }

         while (iterator.hasNext()) {
            Object object2 = iterator.next();
            ListEntry listentry1 = this.linkedHashMap.get(object2);
            if (listentry1 != null) {
               linkedhashmap.putIfAbsent(object2, listentry1);
            }
         }

         this.linkedHashMap.clear();
         this.linkedHashMap.putAll(linkedhashmap);
      }
   }

   private boolean isSetList(Set set, List list) {
      int i = 0;

      for (Object object : this.linkedHashMap.keySet()) {
         if (set.contains(object)) {
            if (i >= list.size() || !object.equals(list.get(i))) {
               return false;
            }

            i++;
         }
      }

      return i == list.size();
   }

   public void update() {
      float f = this.interpolation.getFloat2();
      Iterator iterator = this.linkedHashMap.entrySet().iterator();

      while (iterator.hasNext()) {
         Animation animation = ((ListEntry)((Entry)iterator.next()).getValue()).animation();
         animation.setFloat2(f);
         if (!animation.isFlag() && animation.check()) {
            iterator.remove();
         }
      }
   }
}
