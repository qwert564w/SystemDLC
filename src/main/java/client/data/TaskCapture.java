package client.data;

public final class TaskCapture {
   public int value;
   public final Runnable runnable;

   public TaskCapture(int count, Runnable runnable2) {
      this.value = count;
      this.runnable = runnable2;
   }
}
