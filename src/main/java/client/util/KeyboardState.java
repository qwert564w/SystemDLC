package client.util;

import client.concurrent.SystemClient;
import client.module.Feature;
import client.module.Module;
import client.setting.CompactGroupSetting;
import client.setting.HotkeySetting;
import client.setting.KeybindSetting;
import client.setting.Setting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lwjgl.glfw.GLFW;

public class KeyboardState {
   private static final Map<Integer, Boolean> map = new HashMap<>();
   private static KeyboardState INSTANCE;
   private final List<KeybindSetting> list = new ArrayList<>();
   private final List<HotkeySetting> list2 = new ArrayList<>();
   private final List<KeybindSetting> list3 = new ArrayList<>();
   private final List<HotkeySetting> list4 = new ArrayList<>();
   private final Set<Integer> set = new HashSet<>();
   private final Map<Integer, List<KeybindSetting>> map2 = new HashMap<>();
   private final Map<Integer, List<HotkeySetting>> map3 = new HashMap<>();
   private boolean flag = true;

   private void update() {
      this.list3.clear();
      this.list4.clear();
      this.set.clear();
      this.map2.values().forEach(List::clear);
      this.map2.clear();
      this.map3.values().forEach(List::clear);
      this.map3.clear();
      this.list3.addAll(this.list);
      this.list4.addAll(this.list2);

      for (Module module : SystemClient.getInstance().getModuleRegistry().getList32()) {
         List list3x = module.getSettings();
         List<HotkeySetting> list2x = this.list4;
         List<KeybindSetting> list1 = this.list3;
         List<Setting> listx = list3x;
         this.onListListListModule(list2x, list1, listx, module);
      }

      for (KeybindSetting keybindsetting : this.list3) {
         int i = keybindsetting.getValue();
         if (i != -1 && i != -1) {
            this.map2.computeIfAbsent(i, KeyboardState::getListByInteger).add(keybindsetting);
            this.set.add(i);
         }
      }

      for (HotkeySetting hotkeysetting : this.list4) {
         int j = hotkeysetting.getValue();
         if (j != -1 && j != -1) {
            this.map3.computeIfAbsent(j, KeyboardState::getListByInteger2).add(hotkeysetting);
            this.set.add(j);
         }
      }
   }

   public void removeHotkeySetting(HotkeySetting hotkeySetting) {
      if (this.list2.remove(hotkeySetting)) {
         this.flag = true;
      }
   }

   public void update2() {
      try {
         map.clear();
      } catch (Exception exception) {
         System.err.println(exception.getMessage());
      }
   }

   private static List getListByInteger(Integer value) {
      return new ArrayList<>();
   }

   public void removeKeybindSetting(KeybindSetting keybindSetting) {
      if (this.list.remove(keybindSetting)) {
         this.flag = true;
      }
   }

   private boolean isIntLong(int count, long time) {
      if (count >= 0 && count <= 7) {
         return GLFW.glfwGetMouseButton(time, count) == 1;
      } else {
         return count < 32 ? false : GLFW.glfwGetKey(time, count) == 1;
      }
   }

   public void update3() {
   }

   public void update4() {
      map.clear();
   }

   public void update5() {
      if (Feature.mc != null && Feature.mc.player != null && Feature.mc.currentScreen == null) {
         if (Feature.mc.getWindow() != null) {
            long i = Feature.mc.getWindow().getHandle();
            if (i != 0L) {
               this.setLong(i);
            }
         }
      }
   }

   private void onListListListModule(List<HotkeySetting> list, List<KeybindSetting> list2, List<Setting> list3, Module module2) {
      for (Setting setting : list3) {
         if (setting instanceof KeybindSetting keybindsetting) {
            list2.add(keybindsetting);
         } else if (setting instanceof HotkeySetting hotkeysetting) {
            if (module2.isEnabled()) {
               list.add(hotkeysetting);
            }
         } else if (setting instanceof CompactGroupSetting compactgroupsetting) {
            List<Setting> listx = compactgroupsetting.getList();
            this.onListListListModule(list, list2, listx, module2);
         }
      }
   }

   private static List getListByInteger2(Integer value) {
      return new ArrayList<>();
   }

   public void addKeybindSetting(KeybindSetting keybindSetting) {
      if (!this.list.contains(keybindSetting)) {
         this.list.add(keybindSetting);
         this.flag = true;
      }
   }

   public void addHotkeySetting(HotkeySetting hotkeySetting) {
      if (!this.list2.contains(hotkeySetting)) {
         this.list2.add(hotkeySetting);
         this.flag = true;
      }
   }

   public void setFlag() {
      this.flag = true;
   }

   public static KeyboardState getKeyboardState() {
      if (INSTANCE == null) {
         INSTANCE = new KeyboardState();
      }

      return INSTANCE;
   }

   private void setLong(long time) {
      if (Feature.mc.currentScreen == null) {
         if (this.flag) {
            this.update();
            this.flag = false;
         }

         for (int i : this.set) {
            try {
               boolean flagx = this.isIntLong(i, time);
               Boolean obool = map.get(i);
               boolean flag1 = obool != null && obool;
               if (flagx && !flag1) {
                  List<KeybindSetting> listx = this.map2.get(i);
                  if (listx != null) {
                     for (KeybindSetting keybindsetting : listx) {
                        int j = keybindsetting.getValue2();
                        if (this.isIntLong2(j, time)) {
                           keybindsetting.update();
                        }
                     }
                  }

                  List<HotkeySetting> list1 = this.map3.get(i);
                  if (list1 != null) {
                     for (HotkeySetting hotkeysetting : list1) {
                        int k = hotkeysetting.getValue2();
                        if (this.isIntLong2(k, time)) {
                           hotkeysetting.update();
                        }
                     }
                  }
               }

               map.put(i, flagx);
            } catch (Exception exception) {
            }
         }
      }
   }

   private boolean isIntLong2(int count, long time) {
      if (count == 0) {
         return true;
      } else {
         boolean flagx = (count & 2) != 0;
         boolean flag1 = (count & 1) != 0;
         boolean flag2 = (count & 4) != 0;
         boolean flag3 = GLFW.glfwGetKey(time, 341) == 1 || GLFW.glfwGetKey(time, 345) == 1;
         boolean flag4 = GLFW.glfwGetKey(time, 340) == 1 || GLFW.glfwGetKey(time, 344) == 1;
         boolean flag5 = GLFW.glfwGetKey(time, 342) == 1 || GLFW.glfwGetKey(time, 346) == 1;
         return flagx == flag3 && flag1 == flag4 && flag2 == flag5;
      }
   }
}
