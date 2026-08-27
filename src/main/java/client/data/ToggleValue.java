package client.data;

public class ToggleValue {
   private boolean flag;
   private int value;

   public ToggleValue(boolean flag2, int count) {
      this.flag = flag2;
      this.value = count;
   }

   public int getValue() {
      return this.value;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setValue(int count) {
      this.value = count;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }
}
