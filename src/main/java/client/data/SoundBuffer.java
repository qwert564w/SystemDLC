package client.data;

public final class SoundBuffer {
   public final int value;
   public final int value2;
   public final byte[] byteArray;

   public SoundBuffer(int count, int count2, byte[] valueArray) {
      this.value = count;
      this.value2 = count2;
      this.byteArray = valueArray;
   }
}
