package client.data;

public class NoSlowState {
   private float value = 1.0F;
   private boolean flag;

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setValue(float value2) {
      this.value = value2;
   }

   public float getValue() {
      return this.value;
   }
}
