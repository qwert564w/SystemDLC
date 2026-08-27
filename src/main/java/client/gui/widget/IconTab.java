package client.gui.widget;

import client.module.CategoryType;

public class IconTab extends TabButton {
   private final Runnable runnable;
   private boolean flag4;

   public IconTab(CategoryType categoryType, String text, Runnable runnable2) {
      super(categoryType, text);
      this.runnable = runnable2;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (count == 0 && this.isDoubleDouble(value, value2) && this.runnable != null) {
         this.runnable.run();
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected boolean check() {
      return this.flag4;
   }

   public void setFlag4(boolean flag) {
      this.flag4 = flag;
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   public Runnable getRunnable() {
      return this.runnable;
   }
}
