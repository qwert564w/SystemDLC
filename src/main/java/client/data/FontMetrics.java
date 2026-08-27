package client.data;

import com.google.gson.annotations.SerializedName;

public final class FontMetrics {
   @SerializedName("lineHeight")
   private float value;
   @SerializedName("ascender")
   private float value2;
   @SerializedName("descender")
   private float value3;

   public float getValue2() {
      return this.value2;
   }

   public float getValue3() {
      return this.value3;
   }

   public float getFloat() {
      return this.value + this.value3;
   }

   public float getValue() {
      return this.value;
   }
}
