package client.data;

public class RectI {
   private final int value;
   private final int value2;
   private final int value3;
   private final int value4;

   public RectI(int count, int count2, int count3, int count4) {
      this.value = count;
      this.value2 = count2;
      this.value3 = count3;
      this.value4 = count4;
   }

   public int getValue2() {
      return this.value2;
   }

   public int getValue3() {
      return this.value3;
   }

   public int getValue4() {
      return this.value4;
   }

   public int getValue() {
      return this.value;
   }
}
