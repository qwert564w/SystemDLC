package client.util;

import client.api.Icon;
import client.api.UiMetrics;
import client.concurrent.ModuleRegistry;
import client.concurrent.SystemClient;
import client.data.CharMap;
import client.enums.Edge;
import client.enums.TrackedItem;
import client.gui.widget.NotificationToast;
import client.module.Category;
import client.module.CategoryType;
import client.module.Module;
import client.module.client.HudModule;
import client.render.MatrixUtil;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class NotificationManager implements UiMetrics {
   public static final float value235 = 4.0F;
   private static final int value236 = 64;
   private static final NotificationManager INSTANCE = new NotificationManager();
   private final Deque<NotificationToast> deque = new ArrayDeque<>();
   private final Interpolation interpolation = new Interpolation();
   private final Object value237 = new Object();
   private int value238 = 5;
   private long time = 3000L;
   private float value239 = Float.NaN;

   private NotificationManager() {
   }

   public void onTrackedItem(TrackedItem trackedItem) {
      if (trackedItem != null) {
         Icon icon1 = this.getIconByTrackedItem(trackedItem);
         String s1 = trackedItem.text;
         String s = "Свапнул";
         Icon icon = icon1;
         this.onStringIconString(s, icon, s1);
      }
   }

   private void onNotificationToast(NotificationToast notificationToast) {
      synchronized (this.value237) {
         this.deque.addFirst(notificationToast);
         this.update();
         if (!Float.isNaN(this.value239)) {
            this.onFloat(this.value239);
         }
      }
   }

   public void removeFloat(float value) {
      float f = this.interpolation.getFloat2();
      synchronized (this.value237) {
         this.value239 = value;
         Iterator iterator = this.deque.iterator();

         while (iterator.hasNext()) {
            NotificationToast notificationtoast = (NotificationToast)iterator.next();
            notificationtoast.setFloat(f);
            if (notificationtoast.check2()) {
               iterator.remove();
            }
         }

         this.onFloat(value);
      }
   }

   public float getFloat() {
      synchronized (this.value237) {
         float f = 0.0F;

         for (NotificationToast notificationtoast : this.deque) {
            if (!notificationtoast.check2() && notificationtoast.getFloat() > f) {
               f = notificationtoast.getFloat();
            }
         }

         return f;
      }
   }

   public void onStringCategoryTypeString(String text, CategoryType categoryType, String text2) {
      Icon icon = Icon.getIconByCategoryType(categoryType);
      this.onStringIconString(text, icon, text2);
   }

   private Icon getIconByTrackedItem(TrackedItem trackedItem) {
      return trackedItem.item != null ? Icon.getIconByItemStack(new ItemStack(trackedItem.item)) : Icon.getIconByCategoryType(CategoryType.INFO);
   }

   public void onStringStringItem(String text, String text2, Item item2) {
      if (item2 != null) {
         Icon icon = Icon.getIconByItem(item2);
         this.onStringIconString(text, icon, text2);
      }
   }

   public static NotificationManager getInstance() {
      return INSTANCE;
   }

   public float getFloat2() {
      synchronized (this.value237) {
         float f = 0.0F;
         boolean flag = true;

         for (NotificationToast notificationtoast : this.deque) {
            if (!notificationtoast.check2()) {
               if (!flag) {
                  f += 4.0F;
               }

               f += 32.0F;
               flag = false;
            }
         }

         return f;
      }
   }

   public void onFloatFloatFloatEdgeFloatFloatDrawContextFloat(
      float value, float value2, float value3, Edge edge, float value4, float value5, DrawContext drawContext, float value6
   ) {
      synchronized (this.value237) {
         float f = value + 16.0F;
         MatrixStack matrixstack = drawContext.getMatrices();
         float f1 = 0.0F;
         MatrixUtil.getMatrix4fByFloatFloatFloatFloatMatrixStack(value2, value3, f1, f, matrixstack);

         try {
            for (NotificationToast notificationtoast : this.deque) {
               notificationtoast.onFloatDrawContextEdgeFloatFloatFloat(value4, drawContext, edge, value3, value6, value5);
            }
         } finally {
            matrixstack.pop();
         }
      }
   }

   private void update() {
      int i = 0;

      for (NotificationToast notificationtoast : this.deque) {
         if (!notificationtoast.check2()) {
            i++;
         }
      }

      Iterator iterator = this.deque.descendingIterator();

      while (i > this.value238 && iterator.hasNext()) {
         NotificationToast notificationtoast1 = (NotificationToast)iterator.next();
         if (!notificationtoast1.check2()) {
            notificationtoast1.update();
            i--;
         }
      }

      while (this.deque.size() > 64) {
         NotificationToast notificationtoast2 = this.deque.pollLast();
         if (notificationtoast2 == null) {
            break;
         }
      }
   }

   private void onFloat(float value) {
      int i = 0;

      for (NotificationToast notificationtoast : this.deque) {
         notificationtoast.onFloat(value + i * 36.0F);
         i++;
      }
   }

   private static boolean check() {
      try {
         SystemClient systemclient = SystemClient.getInstance();
         if (systemclient == null) {
            return true;
         } else {
            ModuleRegistry moduleregistry = systemclient.getModuleRegistry();
            if (moduleregistry == null) {
               return true;
            } else {
               HudModule hudmodule = (HudModule)moduleregistry.getModuleByClass(HudModule.class);
               return hudmodule == null || hudmodule.getUvedomleniya().isFlag3();
            }
         }
      } catch (Exception exception) {
         return true;
      }
   }

   private static boolean isString(String text) {
      return text == null || text.isEmpty();
   }

   public NotificationToast getNotificationToastByStringIconString(String text, Icon icon, String text2) {
      if (icon == null) {
         return null;
      } else if (isString(text2) && isString(text)) {
         return null;
      } else {
         NotificationToast notificationtoast = NotificationToast.getNotificationToastByStringIconString(text, icon, text2);
         this.onNotificationToast(notificationtoast);
         return notificationtoast;
      }
   }

   public void onStringIcon(String text, Icon icon) {
      String s = "";
      this.onStringIconString(s, icon, text);
   }

   public void onStringIconString(String text, Icon icon, String text2) {
      if (icon != null) {
         if (!isString(text) || !isString(text2)) {
            if (check()) {
               long i;
               synchronized (this.value237) {
                  i = this.time;
               }

               this.onNotificationToast(NotificationToast.getNotificationToastByIconStringStringLong(icon, text2, text, i));
            }
         }
      }
   }

   public void onIntLong(int count, long time2) {
      synchronized (this.value237) {
         this.value238 = Math.max(1, count);
         this.time = Math.max(50L, time2);
      }
   }

   private static String getStringByDouble(double value) {
      return value >= 10.0 ? String.format("%.0fc", value) : String.format("%.1fc", value);
   }

   public void onTrackedItemDouble(TrackedItem trackedItem, double value) {
      if (trackedItem != null) {
         String s = value > 0.0 ? trackedItem.text + " — " + getStringByDouble(value) : trackedItem.text;
         Icon icon1 = this.getIconByTrackedItem(trackedItem);
         String s1 = "Кд";
         Icon icon = icon1;
         this.onStringIconString(s1, icon, s);
      }
   }

   public void onTrackedItem2(TrackedItem trackedItem) {
      if (trackedItem != null) {
         Icon icon1 = this.getIconByTrackedItem(trackedItem);
         String s1 = trackedItem.text;
         String s = "Использовал";
         Icon icon = icon1;
         this.onStringIconString(s, icon, s1);
      }
   }

   public void onBooleanModule(boolean flag, Module module2) {
      if (module2 != null) {
         Category category = module2.getCategory();
         CategoryType categorytype = category != null ? category.getCategoryType() : CategoryType.INFO;
         String s = flag ? "Включён" : "Выключен";
         Icon icon1 = Icon.getIconByCategoryType(categorytype);
         String s1 = CharMap.getStringByString(module2.getName());
         Icon icon = icon1;
         this.onStringIconString(s, icon, s1);
      }
   }

   public void onNotificationToast2(NotificationToast notificationToast) {
      if (notificationToast != null) {
         notificationToast.setFlag2();
      }
   }
}
