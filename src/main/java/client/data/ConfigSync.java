package client.data;

import client.concurrent.ConfigManager;
import client.concurrent.MainThread;
import client.concurrent.SystemClient;
import client.util.HashUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ConfigSync {
   private static final ConfigSync INSTANCE = new ConfigSync();
   private final List<ConfigEntry> list = new ArrayList<>();
   private boolean flag;

   public void onStringStringRunnable(String text, String text2, Runnable runnable) {
      Consumer<ConfigManager> consumer = var2x -> var2x.onStringString(text2, text);
      onRunnableConsumer(runnable, consumer);
   }

   public void onRunnableString(Runnable runnable, String text) {
      onConsumerRunnable3(var1x -> {
         boolean flagx = text != null && text.equals(var1x.getText());
         var1x.setString(text);
         if (flagx) {
            var1x.update2();
         }
      }, runnable);
   }

   public void onConsumerString(Consumer<Object> consumer, String text) {
      onFunctionConsumer2(var1x -> var1x.getListByString(text), var1x -> {
         if (consumer != null) {
            consumer.accept(var1x == null ? new ArrayList() : var1x);
         }
      });
   }

   public void onRunnable(Runnable runnable) {
      Consumer<ConfigManager> consumer = var0 -> {
         HashUtil hashutil = getHashUtil();
         if (hashutil != null) {
            hashutil.update4();
         } else {
            var0.check2();
         }
      };
      onRunnableConsumer(runnable, consumer);
   }

   public void onConsumerString2(Consumer<Object> consumer, String text) {
      onFunctionConsumer2(var1x -> var1x.getConfigEntryByString(text), consumer);
   }

   public synchronized List<ConfigEntry> getList() {
      return new ArrayList<>(this.list);
   }

   public void onStringBooleanRunnable(String text, boolean flag, Runnable runnable) {
      Consumer<ConfigManager> consumer = var2x -> var2x.onStringBoolean(text, flag);
      onRunnableConsumer(runnable, consumer);
   }

   public void onStringConsumer(String text, Consumer<Object> consumer) {
      onFunctionConsumer2(var1x -> var1x.getStringByString(text), consumer);
   }

   public void onStringRunnableString(String text, Runnable runnable, String text2) {
      Consumer<ConfigManager> consumer = var2x -> var2x.onStringString2(text2, text);
      onRunnableConsumer(runnable, consumer);
   }

   public void onStringStringConsumer2(String text, String text2, Consumer consumer) {
      MainThread.onRunnable2(() -> {
         ConfigManager configmanager = getConfigManager();
         if (configmanager != null) {
            HashUtil hashutil = getHashUtil();
            if (hashutil != null && configmanager.getText() != null) {
               hashutil.update4();
            }

            ConfigEntry[] aconfigentry = new ConfigEntry[1];
            onHashUtilRunnable(hashutil, () -> {
               aconfigentry[0] = configmanager.getConfigEntryByStringString(text, text2);
               if (aconfigentry[0] != null && aconfigentry[0].getText() != null) {
                  configmanager.isString2(aconfigentry[0].getText());
               }
            });
            MainThread.onRunnable(() -> {
               if (consumer != null) {
                  consumer.accept(aconfigentry[0]);
               }
            });
         }
      });
   }

   private static void onRunnableConsumer(Runnable runnable, Consumer<ConfigManager> consumer) {
      MainThread.onRunnable2(() -> {
         ConfigManager configmanager = getConfigManager();
         if (configmanager != null) {
            consumer.accept(configmanager);
            MainThread.onRunnable(runnable);
         }
      });
   }

   public void setRunnable2(Runnable runnable) {
      this.flag = true;
      MainThread.onRunnable2(() -> {
         ConfigManager configmanager = getConfigManager();
         if (configmanager == null) {
            this.flag = false;
         } else {
            List listx = configmanager.getList4();
            synchronized (this) {
               this.list.clear();
               if (listx != null) {
                  listx.sort(Comparator.comparing(ConfigEntry::getText4, Comparator.nullsLast(Comparator.naturalOrder())));
                  this.list.addAll(listx);
               }
            }

            this.flag = false;
            MainThread.onRunnable(runnable);
         }
      });
   }

   public void onRunnableString2(Runnable runnable, String text) {
      MainThread.onRunnable2(() -> {
         ConfigManager configmanager = getConfigManager();
         if (configmanager != null) {
            HashUtil hashutil = getHashUtil();
            if (hashutil != null && text != null && !text.equals(configmanager.getText())) {
               hashutil.update4();
            }

            onHashUtilRunnable(hashutil, () -> {
               if (configmanager.isString2(text)) {
                  configmanager.isString3(text);
               }
            });
            MainThread.onRunnable(runnable);
         }
      });
   }

   public void onStringStringRunnable2(String text, String text2, Runnable runnable) {
      Consumer<ConfigManager> consumer = var2x -> var2x.onStringString3(text2, text);
      onRunnableConsumer(runnable, consumer);
   }

   public void onConsumerString3(Consumer<Object> consumer, String text) {
      onFunctionConsumer2(var1x -> var1x.getStringByString2(text), consumer);
   }

   private static HashUtil getHashUtil() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient == null ? null : systemclient.getHashUtil();
   }

   public boolean isFlag() {
      return this.flag;
   }

   public static ConfigSync getInstance() {
      return INSTANCE;
   }

   private static void onFunctionConsumer2(Function<ConfigManager, Object> function2, Consumer<Object> consumer) {
      MainThread.onRunnable2(() -> {
         ConfigManager configmanager = getConfigManager();
         if (configmanager != null) {
            Object object = function2.apply(configmanager);
            MainThread.onRunnable(() -> {
               if (consumer != null) {
                  consumer.accept(object);
               }
            });
         }
      });
   }

   private static void onConsumerRunnable3(Consumer<ConfigManager> consumer, Runnable runnable) {
      MainThread.onRunnable2(() -> {
         ConfigManager configmanager = getConfigManager();
         if (configmanager != null) {
            onHashUtilRunnable(getHashUtil(), () -> consumer.accept(configmanager));
            MainThread.onRunnable(runnable);
         }
      });
   }

   private static void onHashUtilRunnable(HashUtil hashUtil, Runnable runnable) {
      if (hashUtil != null) {
         hashUtil.update();
      }

      try {
         runnable.run();
      } finally {
         if (hashUtil != null) {
            hashUtil.setText();
            hashUtil.setFlag2();
         }
      }
   }

   private static ConfigManager getConfigManager() {
      SystemClient systemclient = SystemClient.getInstance();
      return systemclient == null ? null : systemclient.getConfigManager();
   }
}
