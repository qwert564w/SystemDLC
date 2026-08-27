package client.audio;

import org.lwjgl.openal.AL10;

public final class SoundSource {
   private final int value;
   private final int[] intArray;
   private int value2;

   public SoundSource(int count, int[] countArray) {
      this.value = count;
      this.intArray = countArray;
   }

   public int getValue() {
      return this.value;
   }

   public synchronized int getInt() {
      for (int i : this.intArray) {
         if (i != 0 && AL10.alGetSourcei(i, 4112) != 4114) {
            return i;
         }
      }

      int j = this.intArray[this.value2];
      this.value2 = (this.value2 + 1) % this.intArray.length;
      return j;
   }

   public void update() {
      for (int i : this.intArray) {
         if (i != 0) {
            try {
               AL10.alSourceStop(i);
               AL10.alDeleteSources(i);
            } catch (Exception exception1) {
            }
         }
      }

      try {
         if (this.value != 0) {
            AL10.alDeleteBuffers(this.value);
         }
      } catch (Exception exception) {
      }
   }
}
