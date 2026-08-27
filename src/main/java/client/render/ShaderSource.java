package client.render;

import com.google.gson.annotations.SerializedName;

public class ShaderSource {
   @SerializedName("name")
   private String text;
   @SerializedName("jsonData")
   private String text2;
   @SerializedName("fshData")
   private String text3;
   @SerializedName("vshData")
   private String text4;
   @SerializedName("type")
   private String text5;

   public String getString() {
      return this.text5 == null ? "core" : this.text5;
   }

   public String getText2() {
      return this.text2;
   }

   public byte[] getByteArray() {
      return this.text3.getBytes();
   }

   public byte[] getByteArray2() {
      return this.text4.getBytes();
   }

   public String getText3() {
      return this.text3;
   }

   public String getText4() {
      return this.text4;
   }

   public byte[] getByteArray3() {
      return this.text2.getBytes();
   }

   public String getText() {
      return this.text;
   }
}
