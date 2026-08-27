package client.concurrent;

import client.audio.SoundEngine;
import client.concurrent.MainThread;
import client.data.ClientConfigData;
import client.data.ConfigBlob;
import client.data.ConfigData;
import client.data.ConfigEntry;
import client.data.HudConfig;
import client.data.ModuleState;
import client.data.SystemFriend;
import client.data.ThemeConfig;
import client.data.Waypoint;
import client.gui.widget.RenderElement;
import client.gui.widget.UiContext;
import client.module.Module;
import client.network.ConfigApi;
import client.setting.KeybindSetting;
import client.setting.Setting;
import client.util.DateUtil;
import client.util.GzipUtil;
import client.util.HashUtil;
import client.util.TextHash;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ConfigManager {
   private final Gson gson = new GsonBuilder().create();
   private final ModuleRegistry moduleRegistry;
   private String text;
   private String text2;
   private String text3;
   private final Map<String, float[]> map = new ConcurrentHashMap<>();
   private final Map<String, Map<String, JsonObject>> map2 = new ConcurrentHashMap<>();
   private final List<String> list = Collections.synchronizedList(new ArrayList<>());
   private final List<String> list2 = Collections.synchronizedList(new ArrayList<>());
   private final List<Integer> list3 = Collections.synchronizedList(new ArrayList<>());
   private ConfigData configData = null;
   private Runnable runnable;
   private Supplier<ConfigData> supplier;

   public ConfigManager(ModuleRegistry moduleRegistry2) {
      this.moduleRegistry = moduleRegistry2;
   }

   public List<String> getList() {
      return new ArrayList<>(this.list);
   }

   public String getStringByString(String text) {
      try {
         return ConfigApi.getStringByString2(text);
      } catch (Exception exception) {
         return null;
      }
   }

   public void onStringString(String text, String text2) {
      try {
         ConfigApi.isStringString3(text, text2);
      } catch (Exception exception) {
      }
   }

   public boolean check() {
      for (int i = 0; i < 3; i++) {
         try {
            String s = ConfigApi.getString();
            if (s == null) {
               return false;
            }

            boolean flag = this.isString3(s);
            if (flag) {
               this.text = s;

               for (ConfigEntry configentry : (Iterable<ConfigEntry>)(this.getList4())) {
                  if (s.equals(configentry.getText()) && configentry.getText3() != null) {
                     this.text2 = configentry.getText3();
                     this.text3 = DateUtil.getStringByString3(configentry.getText4());
                     break;
                  }
               }

               return true;
            }
         } catch (Exception exception) {
            if (i < 2) {
               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException interruptedexception) {
                  Thread.currentThread().interrupt();
                  return false;
               }
            }
         }
      }

      return false;
   }

   public String getText() {
      return this.text;
   }

   public void onString(String text) {
      if (text != null) {
         SystemClient systemclient = SystemClient.getInstance();
         HashUtil hashutil = systemclient != null ? systemclient.getHashUtil() : null;
         if (hashutil == null || !hashutil.isFlag3()) {
            try {
               if ("__global__".equals(text)) {
                  this.map2.put(text, new HashMap<>(HudConfig.getHudConfig().getMap()));
               } else {
                  UiContext uicontext = UiContext.getInstance();
                  RenderElement renderelement = uicontext.getRenderElementByString(text);
                  if (renderelement != null) {
                     this.map2.put(text, new HashMap<>(renderelement.getMap()));
                  }
               }
            } catch (Exception exception) {
            }

            if (hashutil != null) {
               hashutil.update5();
            }
         }
      }
   }

   public void update() {
      this.map.clear();
      SystemClient systemclient = SystemClient.getInstance();
      if (systemclient != null && systemclient.getHashUtil() != null) {
         systemclient.getHashUtil().update5();
      }
   }

   public void onList(List list) {
      this.list2.clear();
      if (list != null) {
         this.list2.addAll(list);
      }
   }

   private boolean isString(String text) {
      if (text == null) {
         return false;
      } else {
         try {
            for (ConfigEntry configentry : this.getList4()) {
               if (text.equals(configentry.getText())) {
                  return configentry.isFlag3();
               }
            }
         } catch (Exception exception) {
         }

         return false;
      }
   }

   public List<String> getList2() {
      return new ArrayList<>(this.list2);
   }

   public void onList2(List list) {
      this.list3.clear();
      if (list != null) {
         this.list3.addAll(list);
      }

      SystemClient systemclient = SystemClient.getInstance();
      if (systemclient != null && systemclient.getHashUtil() != null) {
         systemclient.getHashUtil().update5();
      }
   }

   public boolean isString2(String text2) {
      try {
         boolean flag = ConfigApi.isString(text2);
         if (flag) {
            this.text = text2;
         }

         return flag;
      } catch (Exception exception) {
         return false;
      }
   }

   public void onStringString2(String text, String text2) {
      try {
         ConfigApi.isStringString4(text2, text);
      } catch (Exception exception) {
      }
   }

   public List<Integer> getList3() {
      return new ArrayList<>(this.list3);
   }

   public void onClientConfigData(ClientConfigData clientConfigData) {
      this.onClientConfigDataBoolean(clientConfigData, false);
   }

   public String getString() {
      ClientConfigData clientconfigdata = new ClientConfigData();
      if (this.moduleRegistry != null) {
         int i = 0;
         int j = 0;

         for (Module module : this.moduleRegistry.getList32()) {
            if (!"Панис".equals(module.getName())) {
               ModuleState modulestate = new ModuleState();
               modulestate.setFlag(module.isEnabled());
               if (module.isEnabled()) {
                  j++;
               }

               i++;
               if (module.getKeybindSetting() instanceof KeybindSetting keybindsetting) {
                  modulestate.setValue(keybindsetting.getValue());
               }

               HashMap hashmap3 = new HashMap();
               KeybindSetting keybindsetting1 = module.getKeybindSetting();

               for (Setting setting : module.getSettings()) {
                  if (setting != keybindsetting1) {
                     try {
                        JsonObject jsonobject = setting.toJson();
                        if (jsonobject != null) {
                           hashmap3.put(setting.getNameHash(), jsonobject);
                        }
                     } catch (Exception exception1) {
                     }
                  }
               }

               modulestate.setMap(hashmap3);
               clientconfigdata.getMap().put(module.getName(), modulestate);
            }
         }
      }

      clientconfigdata.setMap2(new HashMap<>(this.map));
      HashMap hashmap = new HashMap();

      try {
         for (RenderElement renderelement : UiContext.getInstance().getList2()) {
            Map map1 = renderelement.getMap();
            if (map1 != null && !map1.isEmpty()) {
               HashMap hashmap2 = new HashMap(map1);
               hashmap.put(renderelement.getString3(), hashmap2);
               this.map2.put(renderelement.getString3(), new HashMap<>(map1));
            }
         }

         Map mapx = HudConfig.getHudConfig().getMap();
         if (mapx != null && !mapx.isEmpty()) {
            hashmap.put("__global__", new HashMap(mapx));
            this.map2.put("__global__", new HashMap<>(mapx));
         }
      } catch (Exception exception2) {
      }

      for (Entry entry : this.map2.entrySet()) {
         if (!hashmap.containsKey(entry.getKey())) {
            HashMap hashmap1 = new HashMap((Map)entry.getValue());
            hashmap.put((String)entry.getKey(), hashmap1);
         }
      }

      clientconfigdata.setMap3(hashmap);
      clientconfigdata.setText(this.text3);
      clientconfigdata.setList2(new ArrayList<>(this.list));
      clientconfigdata.setList3(new ArrayList<>(this.list2));
      clientconfigdata.setList4(new ArrayList<>(this.list3));
      if (this.supplier != null) {
         try {
            ConfigData configdata = this.supplier.get();
            if (configdata != null) {
               this.configData = configdata;
            }
         } catch (Exception exception) {
         }
      }

      if (this.configData != null) {
         clientconfigdata.setConfigData(this.configData);
      }

      return this.gson.toJson(clientconfigdata);
   }

   public void onFloatFloatString(float value, float value2, String text) {
      this.map.put(text, new float[]{value2, value});
      SystemClient systemclient = SystemClient.getInstance();
      if (systemclient != null && systemclient.getHashUtil() != null) {
         systemclient.getHashUtil().update5();
      }
   }

   public boolean check2() {
      try {
         SystemFriend.getInstance().check();
      } catch (Exception exception2) {
      }

      if (this.text == null) {
         return false;
      } else {
         try {
            String s = this.getString();
            String s1 = GzipUtil.getStringByString2(s);
            return ConfigApi.isStringString5(this.text, s1);
         } catch (Exception exception1) {
            return false;
         }
      }
   }

   public Map getMapByString(String text) {
      Map mapx = this.map2.get(text);
      return mapx == null ? null : new HashMap(mapx);
   }

   public ConfigEntry getConfigEntryByStringString(String text, String text2) {
      String s = this.text3;

      try {
         String s1 = this.getString();
         String s2 = GzipUtil.getStringByString2(s1);
         ConfigEntry configentry = ConfigApi.getConfigEntryByStringStringString(s2, text, text2);
         if (configentry != null && configentry.getText4() != null) {
            this.text3 = configentry.getText4();
         }

         return configentry;
      } catch (Exception exception) {
         this.text3 = s;
         return null;
      }
   }

   public ConfigData getConfigData() {
      return this.configData;
   }

   public void setConfigData(ConfigData configData2) {
      this.configData = configData2;
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   public void setSupplier(Supplier<ConfigData> supplier2) {
      this.supplier = supplier2;
   }

   public void update2() {
      SoundEngine.getInstance().setFlag3(true);

      try {
         for (Module module : this.moduleRegistry.getList32()) {
            if (!"Панис".equals(module.getName())) {
               if (module.isEnabled()) {
                  module.setEnabled(false);
               }

               for (Setting setting : (Iterable<Setting>)(module.getVisibleSettings())) {
                  setting.reset();
               }
            }
         }

         SystemFriend systemfriend = SystemFriend.getInstance();
         systemfriend.update3();
         WaypointStore.getInstance().update();
         ClientConfigData clientconfigdata = new ClientConfigData();
         clientconfigdata.getMap().clear();
         this.text = null;
         this.text3 = null;
      } finally {
         SoundEngine.getInstance().setFlag3(false);
      }
   }

   private void onBooleanClientConfigData(boolean flag, ClientConfigData clientConfigData) {
      this.text3 = DateUtil.getStringByString3(clientConfigData.getText());
      if (this.moduleRegistry != null && clientConfigData.getMap() != null) {
         int i = 0;
         int j = 0;

         for (Entry entry : clientConfigData.getMap().entrySet()) {
            if (!"Панис".equals(entry.getKey())) {
               Module module = this.moduleRegistry.getModuleByString((String)entry.getKey());
               if (module != null) {
                  ModuleState modulestate = (ModuleState)entry.getValue();
                  if (module.getKeybindSetting() instanceof KeybindSetting keybindsetting) {
                     keybindsetting.setInt(modulestate.getValue());
                  }

                  if (modulestate.getMap() != null) {
                     for (Entry entry1 : modulestate.getMap().entrySet()) {
                        Setting setting = module.getSettingByHash((String)entry1.getKey());
                        if (setting == null) {
                           setting = module.getSettingByName((String)entry1.getKey());
                           if (setting != null) {
                              TextHash.setFlag();
                           }
                        }

                        if (setting != null && ((JsonElement)entry1.getValue()).isJsonObject()) {
                           try {
                              setting.fromJson(((JsonElement)entry1.getValue()).getAsJsonObject());
                           } catch (Exception exception3) {
                           }
                        }
                     }
                  }

                  if (modulestate.isFlag() != module.isEnabled()) {
                     module.setEnabled(modulestate.isFlag());
                  }

                  if (modulestate.isFlag()) {
                     j++;
                  }

                  i++;
               }
            }
         }
      }

      if (clientConfigData.getMap2() != null) {
         this.map.clear();
         this.map.putAll(clientConfigData.getMap2());
      }

      this.map2.clear();
      if (clientConfigData.getMap3() != null) {
         for (Entry entry2 : clientConfigData.getMap3().entrySet()) {
            HashMap hashmap = new HashMap();

            for (Entry entry5 : (Iterable<Entry>)(((Map)entry2.getValue()).entrySet())) {
               if (entry5.getValue() != null && ((JsonElement)entry5.getValue()).isJsonObject()) {
                  hashmap.put((String)entry5.getKey(), ((JsonElement)entry5.getValue()).getAsJsonObject());
               }
            }

            this.map2.put((String)entry2.getKey(), hashmap);
         }
      }

      try {
         UiContext.getInstance().update5();
         UiContext.getInstance().update2();
      } catch (Exception exception2) {
      }

      SystemFriend systemfriend = SystemFriend.getInstance();
      systemfriend.update2();
      if (!flag && systemfriend.getSet().isEmpty() && clientConfigData.getList() != null && !clientConfigData.getList().isEmpty()) {
         for (String s : clientConfigData.getList()) {
            if (s != null && !s.isBlank()) {
               systemfriend.addString(s);
               if (clientConfigData.getMap6() != null) {
                  String s1 = (String)clientConfigData.getMap6().get(s);
                  if (s1 != null) {
                     systemfriend.onStringString4(s, s1);
                  }
               }
            }
         }

         if (clientConfigData.getMap4() != null) {
            for (Entry entry3 : clientConfigData.getMap4().entrySet()) {
               systemfriend.onStringString2((String)entry3.getKey(), (String)entry3.getValue());
            }
         }

         if (clientConfigData.getMap5() != null) {
            for (Entry entry4 : clientConfigData.getMap5().entrySet()) {
               systemfriend.onStringString((String)entry4.getKey(), (String)entry4.getValue());
            }
         }

         try {
            systemfriend.check();
         } catch (Exception exception1) {
         }
      }

      if (clientConfigData.getList2() != null) {
         this.list.clear();
         this.list.addAll(clientConfigData.getList2());
      }

      if (clientConfigData.getList3() != null) {
         this.list2.clear();
         this.list2.addAll(clientConfigData.getList3());
      }

      if (clientConfigData.getList4() != null) {
         this.list3.clear();
         this.list3.addAll(clientConfigData.getList4());
      }

      WaypointStore waypointstore = WaypointStore.getInstance();
      waypointstore.update();
      if (this.text != null) {
         waypointstore.onString(this.text);
         if (!flag && waypointstore.getList().isEmpty() && clientConfigData.getList5() != null && !clientConfigData.getList5().isEmpty()) {
            for (Waypoint waypoint : clientConfigData.getList5()) {
               if (waypoint != null) {
                  waypointstore.onWaypoint(waypoint);
               }
            }

            try {
               waypointstore.isString(this.text);
            } catch (Exception exception) {
            }
         }
      }

      if (clientConfigData.getConfigData() != null) {
         this.configData = clientConfigData.getConfigData();
         if (this.configData.getText2() != null) {
            ThemeConfig.setString(this.configData.getText2());
         }
      }
   }

   public void onClientConfigDataBoolean(ClientConfigData clientConfigData, boolean flag) {
      if (clientConfigData != null) {
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient != null && systemclient.getHashUtil() != null) {
            systemclient.getHashUtil().setFlag3(true);
         }

         try {
            this.onBooleanClientConfigData(flag, clientConfigData);
         } finally {
            if (systemclient != null && systemclient.getHashUtil() != null) {
               systemclient.getHashUtil().setFlag3(false);
            }

            try {
               if (this.runnable != null) {
                  this.runnable.run();
               }
            } catch (Exception exception) {
            }
         }
      }
   }

   public boolean isString3(String text2) {
      try {
         String s = ConfigApi.getStringByString3(text2);
         if (s == null) {
            return false;
         } else {
            String s1 = GzipUtil.getStringByString(s);
            if (s1 == null) {
               return false;
            } else {
               ClientConfigData clientconfigdata = (ClientConfigData)this.gson.fromJson(s1, ClientConfigData.class);
               if (clientconfigdata == null) {
                  return false;
               } else {
                  this.text = text2;
                  TextHash.check();
                  this.onClientConfigDataBoolean(clientconfigdata, this.isString(text2));
                  if (TextHash.check()) {
                     MainThread.onRunnable(() -> this.check2());
                  }

                  return true;
               }
            }
         }
      } catch (Exception exception) {
         return false;
      }
   }

   public void onStringBoolean(String text, boolean flag) {
      try {
         ConfigApi.isBooleanString(flag, text);
      } catch (Exception exception) {
      }
   }

   public ConfigEntry getConfigEntryByString(String text) {
      try {
         return ConfigApi.getConfigEntryByString(text);
      } catch (Exception exception) {
         return null;
      }
   }

   public String getStringByString2(String text) {
      try {
         return ConfigApi.getStringByString(text);
      } catch (Exception exception) {
         return null;
      }
   }

   public void onStringString3(String text2, String text3) {
      try {
         boolean flag = ConfigApi.isStringString2(text2, text3);
         if (flag && text3.equals(this.text)) {
            this.text = text2;
         }
      } catch (Exception exception) {
      }
   }

   public List<ConfigEntry> getList4() {
      try {
         return ConfigApi.getList3();
      } catch (Exception exception) {
         return new ArrayList();
      }
   }

   public float[] getFloatArrayByString(String text) {
      return this.map.get(text);
   }

   public void setString(String text2) {
      try {
         boolean flag = ConfigApi.isString3(text2);
         if (flag && text2.equals(this.text)) {
            this.text = null;
         }
      } catch (Exception exception) {
      }
   }

   public ConfigBlob getConfigBlob() {
      if (this.text == null) {
         return null;
      } else {
         try {
            String s = this.getString();
            String s1 = GzipUtil.getStringByString2(s);
            return new ConfigBlob(this.text, s1);
         } catch (Exception exception) {
            return null;
         }
      }
   }

   public void onList3(List list2) {
      this.list.clear();
      if (list2 != null) {
         this.list.addAll(list2);
      }
   }

   public boolean isConfigBlob(ConfigBlob configBlob) {
      if (configBlob == null) {
         return false;
      } else {
         try {
            return ConfigApi.isStringString5(configBlob.getConfigName(), configBlob.getCompressed());
         } catch (Exception exception) {
            return false;
         }
      }
   }

   public List getListByString(String text) {
      try {
         return ConfigApi.getListByString(text);
      } catch (Exception exception) {
         return new ArrayList();
      }
   }
}
