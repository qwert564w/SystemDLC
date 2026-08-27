package client.data;

public record SvgPath(float[] segments, int count) {
   public int getCount() {
      return this.count;
   }

   public float[] getSegments() {
      return this.segments;
   }
}
