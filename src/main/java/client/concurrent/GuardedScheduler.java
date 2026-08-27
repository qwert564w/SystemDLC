package client.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class GuardedScheduler implements ScheduledExecutorService {
   private final ScheduledExecutorService scheduledExecutorService;

   public GuardedScheduler(ScheduledExecutorService scheduledExecutorService2) {
      this.scheduledExecutorService = scheduledExecutorService2;
   }

   @Override
   public void shutdown() {
      this.scheduledExecutorService.shutdown();
   }

   private boolean check() {
      return this.scheduledExecutorService.isShutdown() || this.scheduledExecutorService.isTerminated();
   }

   @Override
   public void execute(Runnable runnable) {
      if (!this.check()) {
         try {
            this.scheduledExecutorService.execute(runnable);
         } catch (RejectedExecutionException rejectedexecutionexception) {
         }
      }
   }

   @Override
   public boolean isTerminated() {
      return this.scheduledExecutorService.isTerminated();
   }

   @Override
   public ScheduledFuture schedule(Runnable runnable, long time, TimeUnit timeUnit) {
      if (this.check()) {
         return NoopFuture.INSTANCE;
      } else {
         try {
            return this.scheduledExecutorService.schedule(runnable, time, timeUnit);
         } catch (RejectedExecutionException rejectedexecutionexception) {
            return NoopFuture.INSTANCE;
         }
      }
   }

   @Override
   public ScheduledFuture schedule(Callable callable, long time, TimeUnit timeUnit) {
      if (this.check()) {
         return NoopFuture.getInstanceAsScheduledFuture();
      } else {
         try {
            return this.scheduledExecutorService.schedule(callable, time, timeUnit);
         } catch (RejectedExecutionException rejectedexecutionexception) {
            return NoopFuture.getInstanceAsScheduledFuture();
         }
      }
   }

   @Override
   public boolean isShutdown() {
      return this.scheduledExecutorService.isShutdown();
   }

   @Override
   public List invokeAll(Collection collection, long time, TimeUnit timeUnit) throws InterruptedException {
      return this.scheduledExecutorService.invokeAll(collection, time, timeUnit);
   }

   @Override
   public List invokeAll(Collection collection) throws InterruptedException {
      return this.scheduledExecutorService.invokeAll(collection);
   }

   @Override
   public Object invokeAny(Collection collection) throws InterruptedException, ExecutionException {
      return this.scheduledExecutorService.invokeAny(collection);
   }

   @Override
   public Object invokeAny(Collection collection, long time, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
      return this.scheduledExecutorService.invokeAny(collection, time, timeUnit);
   }

   @Override
   public boolean awaitTermination(long time, TimeUnit timeUnit) throws InterruptedException {
      return this.scheduledExecutorService.awaitTermination(time, timeUnit);
   }

   @Override
   public Future submit(Callable callable) {
      if (this.check()) {
         return CompletableFuture.completedFuture(null);
      } else {
         try {
            return this.scheduledExecutorService.submit(callable);
         } catch (RejectedExecutionException rejectedexecutionexception) {
            return CompletableFuture.completedFuture(null);
         }
      }
   }

   @Override
   public Future submit(Runnable runnable, Object value) {
      if (this.check()) {
         return CompletableFuture.completedFuture(value);
      } else {
         try {
            return this.scheduledExecutorService.submit(runnable, value);
         } catch (RejectedExecutionException rejectedexecutionexception) {
            return CompletableFuture.completedFuture(value);
         }
      }
   }

   @Override
   public Future submit(Runnable runnable) {
      if (this.check()) {
         return CompletableFuture.completedFuture(null);
      } else {
         try {
            return this.scheduledExecutorService.submit(runnable);
         } catch (RejectedExecutionException rejectedexecutionexception) {
            return CompletableFuture.completedFuture(null);
         }
      }
   }

   @Override
   public List shutdownNow() {
      return this.scheduledExecutorService.shutdownNow();
   }

   @Override
   public ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long time, long time2, TimeUnit timeUnit) {
      if (this.check()) {
         return NoopFuture.INSTANCE;
      } else {
         try {
            return this.scheduledExecutorService.scheduleWithFixedDelay(runnable, time, time2, timeUnit);
         } catch (RejectedExecutionException rejectedexecutionexception) {
            return NoopFuture.INSTANCE;
         }
      }
   }

   @Override
   public ScheduledFuture scheduleAtFixedRate(Runnable runnable, long time, long time2, TimeUnit timeUnit) {
      if (this.check()) {
         return NoopFuture.INSTANCE;
      } else {
         try {
            return this.scheduledExecutorService.scheduleAtFixedRate(runnable, time, time2, timeUnit);
         } catch (RejectedExecutionException rejectedexecutionexception) {
            return NoopFuture.INSTANCE;
         }
      }
   }
}
