package client.render;

import com.google.gson.annotations.SerializedName;

public class ShaderAsset {
   @SerializedName("name")
   private String text;
   @SerializedName("jsonData")
   private String text2;

   public String getText2() {
      return this.text2;
   }

   public String getText() {
      return this.text;
   }

   public byte[] getByteArray() {
      return this.text2.getBytes();
   }
}
