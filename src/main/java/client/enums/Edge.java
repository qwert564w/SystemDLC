package client.enums;

public enum Edge {
   NONE(0.0F, 0.0F),
   LEFT(-1.0F, 0.0F),
   RIGHT(1.0F, 0.0F),
   TOP(0.0F, -1.0F),
   BOTTOM(0.0F, 1.0F);

   private final float value;
   private final float value2;
   private static final Edge[] edgeArray = getEdgeArray();

   private Edge(float value3, float value4) {
      this.value = value3;
      this.value2 = value4;
   }

   public float getValue2() {
      return this.value2;
   }

   public float getValue() {
      return this.value;
   }

   private static Edge[] getEdgeArray() {
      return new Edge[]{NONE, LEFT, RIGHT, TOP, BOTTOM};
   }

   public static Edge getEdgeByFloatFloatFloatFloatFloat(float value, float value2, float value3, float value4, float value5) {
      float f = value2 - value5;
      float f1 = value - value3;
      float f2 = Math.min(Math.min(value5, f), Math.min(value3, f1));
      if (f2 > value4) {
         return NONE;
      } else if (f2 == value5) {
         return LEFT;
      } else if (f2 == f) {
         return RIGHT;
      } else {
         return f2 == value3 ? TOP : BOTTOM;
      }
   }

   public static Edge getEdgeByString(String text) {
      return Enum.valueOf(Edge.class, text);
   }
}
