package client.data;

import client.concurrent.ConfigManager;
import client.concurrent.SystemClient;
import client.gui.widget.RenderElement;
import client.gui.widget.UiContext;
import client.setting.ActionSetting;
import client.setting.BooleanSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.TextHash;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class HudConfig {
   public static final String text = "__global__";
   private static HudConfig INSTANCE;
   private final SliderSetting sizeModulya;
   private final SliderSetting neprozrachnostModulya;
   private final BooleanSetting showHeder;
   private final BooleanSetting showIkonku;
   private final BooleanSetting showBackgroundYIkonki;
   private final BooleanSetting staknut;
   private final BooleanSetting sinhronizaciya;
   private final ActionSetting sbrosVsehModuley;
   private MultilistSetting isklyucheniya;
   private final List<Setting> list;
   private boolean flag;

   private HudConfig() {
      SliderSetting slidersetting = new SliderSetting("", "", 100.0, 50.0, 200.0, 5.0, "%", 0);
      slidersetting.setName("Размер модуля");
      slidersetting.setDescription("Глобальный размер для всех модулей.");
      this.sizeModulya = slidersetting;
      slidersetting = new SliderSetting("", "", 100.0, 0.0, 100.0, 5.0, "%", 0);
      slidersetting.setName("Непрозрачность модуля");
      slidersetting.setDescription("Глобальная непрозрачность для всех модулей.");
      this.neprozrachnostModulya = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать хедер");
      booleansetting.setDescription("Отображать хедер (плашку c названием) y каждого модуля.");
      this.showHeder = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Показывать иконку");
      booleansetting1.setDescription("Отображать иконку в хедере каждого модуля.");
      this.showIkonku = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("Показывать фон y иконки");
      booleansetting2.setDescription("Отображать подложку под иконкой хедера.");
      this.showBackgroundYIkonki = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", false);
      booleansetting3.setName("Стакнуть");
      booleansetting3.setDescription("Слить хедер c контент-плашкой в единую форму c плавными переходами.");
      this.staknut = booleansetting3;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", false);
      booleansetting4.setName("Синхронизация");
      booleansetting4.setDescription("Применять эти настройки ко всем модулям.");
      this.sinhronizaciya = booleansetting4;
      ActionSetting actionsetting = new ActionSetting("", "");
      actionsetting.setName("Сброс всех модулей");
      actionsetting.setDescription("Сбросить настройки всех HUD-модулей до значений по умолчанию.");
      this.sbrosVsehModuley = actionsetting;
      this.list = new ArrayList<>();
      this.showIkonku.setVisibleWhen(this.showHeder::isFlag3);
      this.showBackgroundYIkonki.setVisibleWhen(() -> this.showHeder.isFlag3() && this.showIkonku.isFlag3());
      this.staknut.setVisibleWhen(this.showHeder::isFlag3);

      for (Setting setting : List.of(
         this.sizeModulya, this.neprozrachnostModulya, this.showHeder, this.showIkonku, this.showBackgroundYIkonki, this.staknut, this.sinhronizaciya
      )) {
         setting.setOnChange(this::update);
         this.list.add(setting);
      }

      this.sbrosVsehModuley.setRunnable(this::update2);
      this.list.add(this.sbrosVsehModuley);
   }

   public BooleanSetting getSinhronizaciya() {
      return this.sinhronizaciya;
   }

   public BooleanSetting getShowIkonku() {
      return this.showIkonku;
   }

   public boolean isRenderElement(RenderElement renderElement) {
      return this.check() && !this.isRenderElement2(renderElement);
   }

   public SliderSetting getNeprozrachnostModulya() {
      return this.neprozrachnostModulya;
   }

   private void update() {
      ConfigManager configmanager = getConfigManager();
      if (configmanager != null) {
         configmanager.onString("__global__");
      }
   }

   public BooleanSetting getShowBackgroundYIkonki() {
      return this.showBackgroundYIkonki;
   }

   public BooleanSetting getStaknut() {
      return this.staknut;
   }

   public SliderSetting getSizeModulya() {
      return this.sizeModulya;
   }

   public BooleanSetting getShowHeder() {
      return this.showHeder;
   }

   public MultilistSetting getIsklyucheniya() {
      return this.isklyucheniya;
   }

   public boolean isRenderElement2(RenderElement renderElement) {
      return this.isklyucheniya == null ? false : this.isklyucheniya.getList4().contains(renderElement.getString3());
   }

   public boolean check() {
      return this.sinhronizaciya.isFlag3();
   }

   public List<Setting> getList() {
      return Collections.unmodifiableList(this.list);
   }

   public void addList(List<RenderElement> list2) {
      ArrayList arraylist = new ArrayList();
      ArrayList arraylist1 = new ArrayList();

      for (RenderElement renderelement : list2) {
         arraylist.add(renderelement.getString3());
         arraylist1.add(renderelement.getString());
      }

      if (arraylist.isEmpty()) {
         arraylist.add("placeholder");
         arraylist1.add("placeholder");
      }

      if (!this.flag) {
         MultilistSetting multilistsetting = new MultilistSetting("", "", arraylist, new ArrayList(), arraylist1, true);
         multilistsetting.setName("Исключения");
         multilistsetting.setDescription("Эти модули будут игнорировать синхронизацию и использовать свои настройки.");
         this.isklyucheniya = multilistsetting;
         this.isklyucheniya.setOnChange(this::update);
         int i = this.list.indexOf(this.sbrosVsehModuley);
         if (i >= 0) {
            this.list.add(i, this.isklyucheniya);
         } else {
            this.list.add(this.isklyucheniya);
         }

         this.flag = true;
      } else {
         this.isklyucheniya.onList(arraylist);
         this.isklyucheniya.onList2(arraylist1);
      }
   }

   private void update2() {
      for (Setting setting : this.list) {
         if (setting != this.sbrosVsehModuley) {
            setting.reset();
         }
      }

      UiContext uicontext = UiContext.getInstance();
      if (uicontext != null) {
         for (RenderElement renderelement : uicontext.getList2()) {
            for (Setting setting1 : renderelement.getList()) {
               setting1.reset();
            }

            ConfigManager configmanager = getConfigManager();
            if (configmanager != null) {
               configmanager.onString(renderelement.getString3());
            }
         }
      }

      this.update();
   }

   private static ConfigManager getConfigManager() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient != null ? systemclient.getConfigManager() : null;
   }

   public static HudConfig getHudConfig() {
      if (INSTANCE == null) {
         INSTANCE = new HudConfig();
      }

      return INSTANCE;
   }

   public void onMap(Map map) {
      if (map != null && !map.isEmpty()) {
         for (Setting setting : this.list) {
            JsonObject jsonobject = (JsonObject)map.get(setting.getNameHash());
            if (jsonobject == null) {
               String s = setting.getName();
               jsonobject = (JsonObject)TextHash.getObjectByStringMap(s, map);
               if (jsonobject != null) {
                  TextHash.setFlag();
               }
            }

            if (jsonobject != null) {
               try {
                  setting.fromJson(jsonobject);
               } catch (Exception exception) {
               }
            }
         }
      }
   }

   public Map getMap() {
      LinkedHashMap linkedhashmap = new LinkedHashMap();

      for (Setting setting : this.list) {
         try {
            JsonObject jsonobject = setting.toJson();
            if (jsonobject != null) {
               linkedhashmap.put(setting.getNameHash(), jsonobject);
            }
         } catch (Exception exception) {
         }
      }

      return linkedhashmap;
   }
}
