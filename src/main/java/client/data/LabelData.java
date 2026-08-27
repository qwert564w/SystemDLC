package client.data;

public class LabelData {
   private String text;
   private boolean flag;
   private String text2;
   private String text3;

   public String getText2() {
      return this.text2;
   }

   public void setText2(String text) {
      this.text2 = text;
   }

   public void setText3(String text) {
      this.text3 = text;
   }

   public String getText3() {
      return this.text3;
   }

   public String getText() {
      return this.text;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void setFlag(boolean flag2) {
      this.flag = flag2;
   }

   public void setText(String text2) {
      this.text = text2;
   }
}
