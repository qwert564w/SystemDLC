package client.data;

import com.google.gson.annotations.SerializedName;

public class Waypoint {
   @SerializedName("id")
   private String text;
   @SerializedName("name")
   private String text2;
   @SerializedName("x")
   private int value;
   @SerializedName("y")
   private int value2;
   @SerializedName("z")
   private int value3;
   @SerializedName("server")
   private String text3;
   @SerializedName("enabled")
   private boolean flag;
   @SerializedName("createdAt")
   private String text4;
   private transient boolean flag2;
   private transient long time;
   private transient boolean flag3;

   public Waypoint() {
   }

   public Waypoint(String text5, String text6, int count, int count2, int count3, String text7, boolean flag2, String text8) {
      this.text = text5;
      this.text2 = text6;
      this.value = count;
      this.value2 = count2;
      this.value3 = count3;
      this.text3 = text7;
      this.flag = flag2;
      this.text4 = text8;
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   public void setText2(String text) {
      this.text2 = text;
   }

   public int getValue2() {
      return this.value2;
   }

   public void setValue2(int count) {
      this.value2 = count;
   }

   public void setFlag3(boolean flag) {
      this.flag3 = flag;
   }

   public String getText2() {
      return this.text2;
   }

   public String getText3() {
      return this.text3;
   }

   public void setText3(String text) {
      this.text3 = text;
   }

   public boolean isFlag3() {
      return this.flag3;
   }

   public void setFlag2(boolean flag) {
      this.flag2 = flag;
   }

   public void setValue3(int count) {
      this.value3 = count;
   }

   public int getValue3() {
      return this.value3;
   }

   public void setText4(String text) {
      this.text4 = text;
   }

   public String getText4() {
      return this.text4;
   }

   public void setTime(long time2) {
      this.time = time2;
   }

   public String getText() {
      return this.text;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setText(String text2) {
      this.text = text2;
   }

   public void setValue(int count) {
      this.value = count;
   }

   public int getValue() {
      return this.value;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public long getTime() {
      return this.time;
   }
}
