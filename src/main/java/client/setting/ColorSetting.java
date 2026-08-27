package client.setting;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class ColorSetting extends Setting {
   @Expose
   private int value;
   @Expose
   private int value2;
   @Expose
   private int value3;
   @Expose
   private int value4;
   @Expose
   private int value5;
   @Expose
   private int value6;
   @Expose
   private int value7;
   @Expose
   private int value8;
   @Expose
   private boolean flag;

   public ColorSetting(String text, String text2, int count) {
      this(text, text2, count, false);
   }

   public ColorSetting(String text, String text2, int count, boolean flag2) {
      super(text, text2);
      this.value5 = count >> 16 & 0xFF;
      this.value6 = count >> 8 & 0xFF;
      this.value7 = count & 0xFF;
      this.value8 = flag2 ? count >> 24 & 0xFF : 255;
      this.value = this.value5;
      this.value2 = this.value6;
      this.value3 = this.value7;
      this.value4 = this.value8;
      this.flag = flag2;
   }

   public int getValue2() {
      return this.value2;
   }

   public void setValue4(int count) {
      this.value4 = count;
   }

   public int getValue6() {
      return this.value6;
   }

   public void setValue8(int count) {
      this.value8 = count;
   }

   @Override
   public String getTypeId() {
      return "color";
   }

   public void setValue(int count) {
      this.value = count;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public int getInt() {
      return 0xFF000000 | this.value << 16 | this.value2 << 8 | this.value3;
   }

   public int getInt2() {
      return this.value4 << 24 | this.value << 16 | this.value2 << 8 | this.value3;
   }

   @Override
   public void reset() {
      int l = this.value8;
      int k = this.value7;
      int j = this.value6;
      int i = this.value5;
      this.onIntIntIntInt(i, j, l, k);
   }

   public void setValue2(int count) {
      this.value2 = count;
   }

   public int getValue5() {
      return this.value5;
   }

   public void setValue7(int count) {
      this.value7 = count;
   }

   public int getValue3() {
      return this.value3;
   }

   public void setValue5(int count) {
      this.value5 = count;
   }

   public void setValue3(int count) {
      this.value3 = count;
   }

   public int getValue() {
      return this.value;
   }

   public int getInt3() {
      return this.value4 << 24 | this.value << 16 | this.value2 << 8 | this.value3;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public void onIntIntIntInt(int count, int count2, int count3, int count4) {
      int i = Math.max(0, Math.min(255, count));
      int j = Math.max(0, Math.min(255, count2));
      int k = Math.max(0, Math.min(255, count4));
      int l = this.flag ? Math.max(0, Math.min(255, count3)) : 255;
      this.setInt(l << 24 | i << 16 | j << 8 | k);
   }

   public void onIntIntInt(int count, int count2, int count3) {
      int i = Math.max(0, Math.min(255, count3));
      int j = Math.max(0, Math.min(255, count2));
      int k = Math.max(0, Math.min(255, count));
      this.setInt(this.value4 << 24 | i << 16 | j << 8 | k);
   }

   public void setInt(int count) {
      int i = this.value;
      int j = this.value2;
      int k = this.value3;
      int l = this.value4;
      this.value = count >> 16 & 0xFF;
      this.value2 = count >> 8 & 0xFF;
      this.value3 = count & 0xFF;
      if (this.flag) {
         this.value4 = count >> 24 & 0xFF;
      }

      if (i != this.value || j != this.value2 || k != this.value3 || l != this.value4) {
         this.fireOnChange();
      }
   }

   public boolean check() {
      return this.value == this.value5 && this.value2 == this.value6 && this.value3 == this.value7 && this.value4 == this.value8;
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("red") && jsonObject.has("green") && jsonObject.has("blue")) {
         int i = jsonObject.get("red").getAsInt();
         int j = jsonObject.get("green").getAsInt();
         int k = jsonObject.get("blue").getAsInt();
         int l = jsonObject.has("alpha") ? jsonObject.get("alpha").getAsInt() : 255;
         this.onIntIntIntInt(i, j, l, k);
      }

      if (jsonObject.has("visible")) {
         this.visible = jsonObject.get("visible").getAsBoolean();
      }
   }

   public String getString() {
      return this.flag
         ? String.format("#%02X%02X%02X%02X", this.value, this.value2, this.value3, this.value4)
         : String.format("#%02X%02X%02X", this.value, this.value2, this.value3);
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("red", this.value);
      jsonobject.addProperty("green", this.value2);
      jsonobject.addProperty("blue", this.value3);
      jsonobject.addProperty("alpha", this.value4);
      jsonobject.addProperty("hasAlpha", this.flag);
      jsonobject.addProperty("hex", this.getString());
      jsonobject.addProperty("visible", this.visible);
      return jsonobject;
   }

   public int getValue8() {
      return this.value8;
   }

   public int getValue4() {
      return this.value4;
   }

   public void setValue6(int count) {
      this.value6 = count;
   }

   public int getValue7() {
      return this.value7;
   }
}
