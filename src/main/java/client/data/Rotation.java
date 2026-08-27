package client.data;

public class Rotation {
   private double value;
   private double value2;
   private boolean flag;

   public Rotation(double value, double value2) {
      this.getRotationByDoubleDouble(value, value2);
   }

   public double getValue2() {
      return this.value2;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public Rotation getRotationByDoubleDouble(double value3, double value4) {
      this.value = value3;
      this.value2 = value4;
      this.flag = false;
      return this;
   }

   public double getValue() {
      return this.value;
   }
}
