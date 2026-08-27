package client.data;

import com.google.gson.annotations.SerializedName;

public class ModuleLayoutData {
   @SerializedName("moduleName")
   private String text;
   @SerializedName("groupName")
   private String text2;
   @SerializedName("scrollPosition")
   private float value;

   public ModuleLayoutData() {
   }

   public ModuleLayoutData(String text3, String text4, float value2) {
      this.text = text3;
      this.text2 = text4;
      this.value = value2;
   }

   public String getText2() {
      return this.text2;
   }

   public void setText2(String text) {
      this.text2 = text;
   }

   public void setValue(float value2) {
      this.value = value2;
   }

   public void setText(String text2) {
      this.text = text2;
   }

   public float getValue() {
      return this.value;
   }

   public String getText() {
      return this.text;
   }
}
