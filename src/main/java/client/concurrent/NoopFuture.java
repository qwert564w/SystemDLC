package client.concurrent;

import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class NoopFuture implements ScheduledFuture<Object> {
   public static final NoopFuture INSTANCE = new NoopFuture();

   private NoopFuture() {
   }

   @Override
   public Object get(long time, TimeUnit timeUnit) {
      return null;
   }

   @Override
   public Object get() {
      return null;
   }

   @Override
   public int compareTo(Delayed value) {
      return this.getIntByDelayed(value);
   }

   public static ScheduledFuture getInstanceAsScheduledFuture() {
      return INSTANCE;
   }

   public int getIntByDelayed(Delayed delayed) {
      return 0;
   }

   @Override
   public boolean isDone() {
      return true;
   }

   @Override
   public boolean cancel(boolean flag) {
      return false;
   }

   @Override
   public boolean isCancelled() {
      return false;
   }

   @Override
   public long getDelay(TimeUnit timeUnit) {
      return 0L;
   }
}
