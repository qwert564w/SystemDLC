package client.data;

public record IconSize(float w, float h) {
   public float getH() {
      return this.h;
   }

   public float getW() {
      return this.w;
   }
}
