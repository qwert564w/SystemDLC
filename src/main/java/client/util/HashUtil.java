package client.util;

import client.concurrent.ConfigManager;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class HashUtil {
   private static long time = 10L;
   private final ConfigManager configManager;
   private final ScheduledExecutorService scheduledExecutorService;
   private final AtomicReference<ScheduledFuture<?>> atomicReference = new AtomicReference<>();
   private boolean flag = false;
   private boolean flag2 = false;
   private boolean flag3 = false;
   private String text = null;

   public HashUtil(ConfigManager configManager2, ScheduledExecutorService scheduledExecutorService2) {
      this.configManager = configManager2;
      this.scheduledExecutorService = scheduledExecutorService2;
   }

   public void update() {
      this.flag2 = true;
      ScheduledFuture scheduledfuture = this.atomicReference.getAndSet(null);
      if (scheduledfuture != null && !scheduledfuture.isDone()) {
         scheduledfuture.cancel(false);
      }
   }

   private void update2() {
      ScheduledFuture scheduledfuture = this.atomicReference.get();
      if (scheduledfuture != null && !scheduledfuture.isDone()) {
         scheduledfuture.cancel(false);
      }

      ScheduledFuture scheduledfuture1 = this.scheduledExecutorService.schedule(this::update3, time, TimeUnit.SECONDS);
      this.atomicReference.set(scheduledfuture1);
   }

   private void update3() {
      if (!this.flag2 && this.flag) {
         try {
            String s = this.configManager.getString();
            String s1 = this.getStringByString(s);
            if (s1 != null && s1.equals(this.text)) {
               this.flag = false;
               return;
            }

            boolean flagx = this.configManager.check2();
            if (flagx) {
               this.text = s1;
               this.flag = false;
            }
         } catch (Exception exception) {
         }
      }
   }

   public void setFlag2() {
      this.flag2 = false;
   }

   public void update4() {
      if (!this.flag2) {
         ScheduledFuture scheduledfuture = this.atomicReference.getAndSet(null);
         if (scheduledfuture != null && !scheduledfuture.isDone()) {
            scheduledfuture.cancel(false);
         }

         if (this.configManager.getText() != null) {
            try {
               String s = this.configManager.getString();
               String s1 = this.getStringByString(s);
               if (s1 == null || !s1.equals(this.text)) {
                  boolean flagx = this.configManager.check2();
                  if (flagx) {
                     this.text = s1;
                     this.flag = false;
                  }
               }
            } catch (Exception exception) {
            }
         }
      }
   }

   public void setFlag3(boolean flag) {
      this.flag3 = flag;
   }

   public boolean isFlag3() {
      return this.flag3;
   }

   private String getStringByString(String text) {
      try {
         MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
         byte[] abyte = messagedigest.digest(text.getBytes(StandardCharsets.UTF_8));
         StringBuilder stringbuilder = new StringBuilder(abyte.length * 2);

         for (byte b0 : abyte) {
            stringbuilder.append(String.format("%02x", b0));
         }

         return stringbuilder.toString();
      } catch (Exception exception) {
         return null;
      }
   }

   public void update5() {
      if (!this.flag2 && !this.flag3) {
         this.flag = true;
         this.update2();
      }
   }

   public void setText() {
      this.text = null;
   }
}
