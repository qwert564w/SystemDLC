package client.data;

import com.google.gson.annotations.SerializedName;

public final class RectF {
   @SerializedName("left")
   private float value;
   @SerializedName("top")
   private float value2;
   @SerializedName("right")
   private float value3;
   @SerializedName("bottom")
   private float value4;

   public float getValue2() {
      return this.value2;
   }

   public float getValue3() {
      return this.value3;
   }

   public float getValue4() {
      return this.value4;
   }

   public float getValue() {
      return this.value;
   }
}
