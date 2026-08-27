package client.data;

import com.google.gson.annotations.SerializedName;

public final class GlyphMetrics {
   @SerializedName("distanceRange")
   private float value;
   @SerializedName("width")
   private float value2;
   @SerializedName("height")
   private float value3;

   public float getValue2() {
      return this.value2;
   }

   public float getValue3() {
      return this.value3;
   }

   public float getValue() {
      return this.value;
   }
}
