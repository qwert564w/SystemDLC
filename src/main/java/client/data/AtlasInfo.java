package client.data;

import com.google.gson.annotations.SerializedName;

public final class AtlasInfo {
   @SerializedName("unicode1")
   private int value;
   @SerializedName("unicode2")
   private int value2;
   @SerializedName("advance")
   private float value3;

   public int getValue2() {
      return this.value2;
   }

   public int getValue() {
      return this.value;
   }

   public float getValue3() {
      return this.value3;
   }
}
