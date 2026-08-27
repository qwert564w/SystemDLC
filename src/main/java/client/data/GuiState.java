package client.data;

import client.concurrent.ConfigManager;
import client.enums.GuiTab;
import client.gui.widget.ModuleRow;
import client.gui.widget.NavBar;
import client.gui.widget.SearchBar;
import client.gui.widget.SideBar;
import client.module.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public final class GuiState {
   private GuiState() {
   }

   public static void onNavBar(NavBar navBar) {
      ConfigManager configmanager = ClientAccess.getConfigManager();
      if (configmanager != null && navBar != null) {
         List list = configmanager.getList2();
         if (list != null) {
            navBar.getContentArea().onSet(new HashSet(list));
         }
      }
   }

   public static ConfigData getConfigData() {
      ConfigManager configmanager = ClientAccess.getConfigManager();
      return configmanager != null ? configmanager.getConfigData() : null;
   }

   public static void onConfigData(ConfigData configData) {
      if (configData.getText2() != null) {
         ThemeConfig.setString(configData.getText2());
      }
   }

   public static void onSearchBarConfigData(SearchBar searchBar, ConfigData configData) {
      if (searchBar != null && configData.getList() != null) {
         searchBar.addList(configData.getList());
      }
   }

   public static void onConfigDataSideBarTween(ConfigData configData, SideBar sideBar, Tween tween) {
      boolean flag = configData.isFlag();
      tween.setFloat(flag ? 1.0F : 0.0F);
      sideBar.setFloat(flag ? 1.0F : 0.0F);
   }

   public static void onMapConfigData(Map map, ConfigData configData) {
      map.clear();
      if (configData.getMap2() != null) {
         map.putAll(configData.getMap2());
      }
   }

   public static ConfigData getConfigDataByFloatNavBarMapSearchBarFloatBoolean(float value, NavBar navBar, Map map, SearchBar searchBar, float value2, boolean flag) {
      ConfigData configdata = new ConfigData();
      if (navBar != null) {
         Category category = navBar.getCategory();
         configdata.setText(category != null ? category.name() : null);
         configdata.setText3(navBar.getGuiTab() != null ? navBar.getGuiTab().name() : GuiTab.MODULES.name());
      }

      configdata.setValue(value);
      configdata.setValue2(value2);
      configdata.setFlag(flag);
      configdata.setText2(ThemeConfig.getThemePalette() != null ? ThemeConfig.getThemePalette().name() : null);
      HashMap hashmap = new HashMap(map);
      if (navBar != null && navBar.getGuiTab() == GuiTab.MODULES) {
         hashmap.put(getStringByCategory(navBar.getCategory()), navBar.getContentArea().getValue249());
      }

      configdata.setMap2(hashmap);
      if (searchBar != null) {
         configdata.setList(searchBar.getList2());
      }

      addNavBar(navBar);
      return configdata;
   }

   public static void addNavBar(NavBar navBar) {
      ConfigManager configmanager = ClientAccess.getConfigManager();
      if (configmanager != null && navBar != null) {
         LinkedHashSet linkedhashset = new LinkedHashSet(configmanager.getList2());

         for (ModuleRow modulerow : navBar.getContentArea().getList()) {
            String s = modulerow.getModule().getName();
            if (modulerow.isFlag5()) {
               linkedhashset.add(s);
            } else {
               linkedhashset.remove(s);
            }
         }

         configmanager.onList(new ArrayList(linkedhashset));
      }
   }

   public static String getStringByCategory(Category category) {
      return category != null ? category.name() : "_ALL_";
   }

   public static Category getCategoryByString(String text) {
      if (text == null) {
         return null;
      } else {
         try {
            return Category.getCategoryByString(text);
         } catch (IllegalArgumentException illegalargumentexception) {
            return null;
         }
      }
   }
}
