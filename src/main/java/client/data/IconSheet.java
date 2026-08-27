package client.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IconSheet {
   @SerializedName("name")
   private String text;
   @SerializedName("width")
   private float value;
   @SerializedName("height")
   private float value2;
   @SerializedName("strokeWidth")
   private float value3;
   @SerializedName("filled")
   private boolean flag;
   @SerializedName("paths")
   private List<String> list;

   public float getValue2() {
      return this.value2;
   }

   public float getValue3() {
      return this.value3;
   }

   public List<String> getList() {
      return this.list;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public float getValue() {
      return this.value;
   }

   public String getText() {
      return this.text;
   }
}
