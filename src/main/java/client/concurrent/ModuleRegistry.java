package client.concurrent;

import client.data.ModuleIndex;
import client.module.Category;
import client.module.Module;
import client.util.KeyboardState;
import client.util.StaffChecks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleRegistry {
   private final Map<String, Module> map = new ConcurrentHashMap<>();
   private final Map<Category, List<Module>> map2 = new ConcurrentHashMap<>();
   private final Map<Class<? extends Module>, Module> map3 = new ConcurrentHashMap<>();
   private final List<Module> list = new ArrayList<>();
   private Set<Integer> set = new HashSet<>();
   private List<Module> list2 = Collections.emptyList();
   private List<Module> list3 = Collections.emptyList();
   private long time = 0L;

   public List<Module> getList2() {
      return this.list2;
   }

   public void update() {
      this.map.values().forEach(var0 -> var0.setEnabled(false));
   }

   public List<Module> getList22() {
      return this.list2;
   }

   public void update2() {
      ArrayList arraylist = new ArrayList();

      for (Module module : this.map.values()) {
         if (module.isEnabled()) {
            arraylist.add(module);
         }
      }

      this.list2 = Collections.unmodifiableList(arraylist);
      this.time++;
      KeyboardState.getKeyboardState().setFlag();
   }

   public Map<Category, List<Module>> getMap2() {
      return this.map2;
   }

   public int getInt() {
      return this.list2.size();
   }

   public List getList() {
      return new ArrayList<>(this.map.keySet());
   }

   public Map<Class<? extends Module>, Module> getMap3() {
      return this.map3;
   }

   private void update3() {
      this.list3 = List.copyOf(this.list);
      this.update2();
   }

   public void update4() {
      this.map.values().forEach(var0 -> {
         if (var0.isEnabled()) {
            var0.setEnabled(false);
         }
      });
      this.map.clear();
      this.map2.clear();
      this.map3.clear();
      this.list.clear();
      this.update3();
   }

   public List<Module> getList3() {
      return this.list3;
   }

   public void update5() {
      this.map.values().forEach(Module::forceDisabled);
      this.map.clear();
      this.map2.clear();
      this.map3.clear();
      this.list.clear();
      this.update3();
   }

   public List<Module> getList4() {
      return this.list;
   }

   public void update6() {
      this.map.values().forEach(var0 -> var0.setEnabled(true));
   }

   public long getTime() {
      return this.time;
   }

   public Set<Integer> getSet() {
      return this.set;
   }

   public Map<Category, List<Module>> getMap() {
      HashMap hashmap = new HashMap();
      this.map2.forEach((item, item2) -> {
         synchronized (item2) {
            hashmap.put(item, new ArrayList<>(item2));
         }
      });
      return hashmap;
   }

   public List getListByCategory2(Category category) {
      List<Module> listx = this.map2.get(category);
      if (listx == null) {
         return new ArrayList();
      } else {
         synchronized (listx) {
            return new ArrayList(listx);
         }
      }
   }

   public List<Module> getList32() {
      return this.list3;
   }

   public Module getModuleByClass(Class value) {
      Module module = this.map3.get(value);
      if (module != null) {
         return module;
      } else {
         for (Module module1 : this.map.values()) {
            if (value.isInstance(module1)) {
               this.map3.put(value, module1);
               return (Module)value.cast(module1);
            }
         }

         return null;
      }
   }

   public Module getModuleByString(String text) {
      return this.map.get(text);
   }

   public void removeString(String text) {
      Module module = this.map.remove(text);
      if (module != null) {
         this.list.remove(module);
         this.map3.remove(module.getClass());
         this.map2.computeIfPresent(module.getCategory(), (item, item2) -> {
            synchronized (item2) {
               item2.remove(module);
               return item2.isEmpty() ? null : item2;
            }
         });
         this.update3();
      }
   }

   public void setSet(Set<Integer> set2) {
      this.set = set2;
   }

   private void addModule(Module module2) {
      if (module2 != null) {
         String s = module2.getName();
         if (!this.map.containsKey(s)) {
            this.map.put(s, module2);
            this.map3.put((Class<? extends Module>)module2.getClass(), module2);
            this.map2.computeIfAbsent(module2.getCategory(), var0 -> new ArrayList<>()).add(module2);
            this.list.add(module2);
            this.update3();
         }
      }
   }

   public void update7() {
      Set setx = StaffChecks.getSet();
      if (!setx.isEmpty()) {
         this.setSet(setx);
      }

      List<Module> listx;
      if (this.set.isEmpty()) {
         listx = ModuleIndex.getList();
      } else {
         listx = ModuleIndex.getListBySet(this.set);
      }

      for (Module module : listx) {
         this.addModule(module);
      }

      try {
         SystemClient.getInstance().getConfigManager().check();
      } catch (Exception exception) {
      }
   }

   public boolean isString(String text) {
      return this.map.containsKey(text);
   }

   public int getInt2() {
      return this.map.size();
   }

   public void update8() {
      this.map.values().forEach(Module::toggle);
   }
}
