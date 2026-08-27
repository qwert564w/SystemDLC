package client.data;

public class SlotSelection {
   private int value;
   private int value2;
   private int value3;
   private boolean flag;

   public SlotSelection(int count, int count2, int count3) {
      this.getSlotSelectionByIntIntInt(count, count2, count3);
   }

   public int getValue2() {
      return this.value2;
   }

   public boolean check() {
      return this.value2 == 0;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public int getValue3() {
      return this.value3;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public int getValue() {
      return this.value;
   }

   public boolean check2() {
      return this.value2 == 1;
   }

   public SlotSelection getSlotSelectionByIntIntInt(int count, int count2, int count3) {
      this.value = count;
      this.value2 = count2;
      this.value3 = count3;
      this.flag = false;
      return this;
   }
}
