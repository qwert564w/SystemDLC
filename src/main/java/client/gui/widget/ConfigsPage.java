package client.gui.widget;

import client.data.ConfigEntry;
import client.data.ConfigSync;
import client.module.Feature;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class ConfigsPage extends PageWidget<ConfigRow, ConfigImportForm> {
   private final ConfigImportForm configImportForm = new ConfigImportForm();
   private ConfigPrivacyButton configPrivacyButton;

   public ConfigsPage() {
      this.configImportForm.setConsumer(this::onString2);
      this.configImportForm.setRunnable(this::update6);
      this.update3();
   }

   private void onConfigEntry(ConfigEntry configEntry) {
      if (configEntry != null) {
         ConfigSync configsync = ConfigSync.getInstance();
         String s1 = configEntry.getText();
         Runnable runnable = this::update3;
         String s = s1;
         configsync.onRunnableString(runnable, s);
      }
   }

   private String getString() {
      String[] astring = new String[]{"описание", "описалово", "дескрипшн"};
      Random random = new Random();
      return astring[random.nextInt(astring.length)] + " " + (random.nextInt(9000) + 1000);
   }

   public void update3() {
      ConfigSync.getInstance().setRunnable2(this::update4);
   }

   private void onConfigEntryString(ConfigEntry configEntry, String text) {
      if (configEntry != null) {
         ConfigSync configsync = ConfigSync.getInstance();
         String s2 = configEntry.getText();
         String s3 = text == null ? "" : text;
         Runnable runnable = this::update3;
         String s1 = s3;
         String s = s2;
         configsync.onStringStringRunnable(s1, s, runnable);
      }
   }

   private void onConfigEntry3(ConfigEntry configEntry) {
      this.update3();
   }

   private void update4() {
      List<ConfigEntry> list = ConfigSync.getInstance().getList();
      LinkedHashMap linkedhashmap = new LinkedHashMap();

      for (ConfigEntry configentry : (Iterable<ConfigEntry>)(list)) {
         if (configentry != null && configentry.getText() != null) {
            linkedhashmap.put(configentry.getText(), configentry);
         }
      }

      ArrayList arraylist = new ArrayList();
      HashSet hashset = new HashSet();

      for (ConfigRow configrow : this.list) {
         ConfigEntry configentry1 = configrow.getConfigEntry();
         if (configrow.isFlag4()) {
            arraylist.add(configrow);
         } else if (configentry1 != null && configentry1.getText() != null) {
            ConfigEntry configentry2 = (ConfigEntry)linkedhashmap.get(configentry1.getText());
            if (configentry2 != null) {
               configrow.setConfigEntry(configentry2);
               arraylist.add(configrow);
               hashset.add(configentry1.getText());
            } else {
               configrow.update3();
               arraylist.add(configrow);
            }
         }
      }

      for (Entry entry : (Iterable<Entry>)(linkedhashmap.entrySet())) {
         if (!hashset.contains(entry.getKey())) {
            arraylist.add(this.getConfigRowByConfigEntry((ConfigEntry)entry.getValue()));
         }
      }

      this.list.clear();
      this.list.addAll(arraylist);
      if (this.configPrivacyButton != null && this.configPrivacyButton.getConfigEntry() != null) {
         String s = this.configPrivacyButton.getConfigEntry().getText();

         for (ConfigEntry configentry3 : list) {
            if (configentry3 != null && Objects.equals(configentry3.getText(), s)) {
               this.configPrivacyButton.setConfigEntry(configentry3);
               break;
            }
         }
      }
   }

   private void onConfigEntry4(ConfigEntry configEntry) {
      ConfigSync.getInstance().onRunnable(null);
   }

   private void onConfigEntry5(ConfigEntry configEntry) {
      if (configEntry != null) {
         boolean flag = !configEntry.isFlag2();
         configEntry.setFlag2(flag);
         ConfigSync.getInstance().onStringBooleanRunnable(configEntry.getText(), flag, null);
      }
   }

   private void onConfigEntry6(ConfigEntry configEntry) {
      if (configEntry != null) {
         ConfigSync configsync = ConfigSync.getInstance();
         String s1 = configEntry.getText();
         Consumer<Object> consumer = var0 -> {
            if (var0 != null) {
               if (Feature.mc != null && Feature.mc.keyboard != null) {
                  Feature.mc.keyboard.setClipboard((String)var0);
               }
            }
         };
         String s = s1;
         configsync.onConsumerString3(consumer, s);
      }
   }

   private void update5() {
      if (this.configPrivacyButton != null) {
         this.configPrivacyButton.update14();
      }

      this.configPrivacyButton = null;
   }

   @Override
   protected void onFloat(float value) {
      this.configImportForm.setFloat(value);
   }

   private void onString2(String text) {
      ConfigSync configsync = ConfigSync.getInstance();
      Consumer consumer = var1x -> {
         if (var1x == null) {
            this.configImportForm.update4();
         } else {
            this.update3();
         }
      };
      configsync.onConsumerString2(consumer, text);
   }

   @Override
   protected void onWidgetBooleanFloatFloat(Widget widget2, boolean flag, float value, float value2) {
      ConfigImportForm configimportform = (ConfigImportForm)widget2;
      this.onFloatBooleanConfigImportFormFloat(value2, flag, configimportform, value);
   }

   @Override
   protected Widget getWidget() {
      return this.getConfigImportForm();
   }

   @Override
   protected boolean check() {
      return this.configImportForm.getTextField().isFlag4();
   }

   protected ConfigImportForm getConfigImportForm() {
      return this.configImportForm;
   }

   private void onConfigEntryString2(ConfigEntry configEntry, String text) {
      if (configEntry != null && text != null) {
         String s = configEntry.getText();
         if (s != null && !s.equals(text) && !text.isBlank()) {
            ConfigSync.getInstance().onStringStringRunnable2(s, text, this::update3);
         }
      }
   }

   private void onConfigRowConfigEntry2(ConfigRow configRow, ConfigEntry configEntry) {
      if (configEntry != null) {
         this.update5();
         ConfigPrivacyButton configprivacybutton = new ConfigPrivacyButton(configEntry);
         configprivacybutton.setConsumer(this::onConfigEntry7);
         configprivacybutton.setConsumer2(this::onConfigEntry3);
         configprivacybutton.setConsumer4(this::onConfigEntry5);
         configprivacybutton.setConsumer3(var1x -> this.configPrivacyButton = null);
         this.configPrivacyButton = configprivacybutton;
         configprivacybutton.setColorSupplier2(() -> {
            float f = configRow.getValue235() + configRow.getValue237() + 16.0F;
            if (f + 300.0F > this.value235 + this.value237) {
               f = configRow.getValue235() - 16.0F - 300.0F;
            }

            return new float[]{f, configRow.getValue236()};
         });
      }
   }

   private ConfigRow getConfigRowByConfigEntry(ConfigEntry configEntry) {
      ConfigRow configrow = new ConfigRow(configEntry);
      configrow.setFloat(this.value244);
      configrow.setConsumer(this::onConfigEntry7);
      configrow.setConsumer2(this::onConfigEntry3);
      configrow.setConsumer3(this::onConfigEntry4);
      configrow.setConsumer4(this::onConfigEntry6);
      configrow.setConsumer5(this::onConfigEntry);
      configrow.setBiConsumer(this::onConfigEntryString2);
      configrow.setBiConsumer2(this::onConfigEntryString);
      configrow.setConsumer7(this::onConfigEntry5);
      configrow.setConsumer6(var2x -> this.onConfigRowConfigEntry2(configrow, var2x));
      return configrow;
   }

   private void update6() {
      ConfigSync configsync = ConfigSync.getInstance();
      String s2 = this.getString2();
      String s3 = this.getString();
      Consumer consumer = var1x -> this.update3();
      String s1 = s3;
      String s = s2;
      configsync.onStringStringConsumer2(s1, s, consumer);
   }

   private String getString2() {
      String[] astring = new String[]{"кфг", "конфиг"};
      HashSet hashset = new HashSet();

      for (ConfigEntry configentry : ConfigSync.getInstance().getList()) {
         if (configentry.getText() != null) {
            hashset.add(configentry.getText());
         }
      }

      Random random = new Random();

      for (int i = 0; i < 32; i++) {
         String s = astring[random.nextInt(astring.length)] + " " + (random.nextInt(9000) + 1000);
         if (!hashset.contains(s)) {
            return s;
         }
      }

      return "кфг " + Long.toString(System.currentTimeMillis(), 36);
   }

   @Override
   protected float getFloat3() {
      return 300.0F;
   }

   private void onConfigEntry7(ConfigEntry configEntry) {
      if (configEntry != null) {
         ConfigSync configsync = ConfigSync.getInstance();
         String s1 = configEntry.getText();
         Runnable runnable = this::update3;
         String s = s1;
         configsync.onRunnableString2(runnable, s);
      }
   }

   protected void onFloatBooleanConfigImportFormFloat(float value, boolean flag, ConfigImportForm configImportForm, float value2) {
      if (flag) {
         configImportForm.onFloatFloat4(value, value2);
      } else {
         configImportForm.onFloatFloat2(value, value2);
      }
   }
}
