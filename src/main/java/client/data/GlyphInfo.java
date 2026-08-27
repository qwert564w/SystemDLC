package client.data;

import com.google.gson.annotations.SerializedName;

public final class GlyphInfo {
   @SerializedName("unicode")
   private int value;
   @SerializedName("advance")
   private float value2;
   @SerializedName("planeBounds")
   private RectF rectF;
   @SerializedName("atlasBounds")
   private RectF rectF2;

   public RectF getRectF2() {
      return this.rectF2;
   }

   public int getValue() {
      return this.value;
   }

   public RectF getRectF() {
      return this.rectF;
   }

   public float getValue2() {
      return this.value2;
   }
}
