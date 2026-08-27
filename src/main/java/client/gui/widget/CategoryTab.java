package client.gui.widget;

import client.module.Category;
import client.module.CategoryType;

public class CategoryTab extends TabButton {
   private final Category category;
   private boolean flag4;

   public CategoryTab(Category category2) {
      super(category2 != null ? category2.getCategoryType() : CategoryType.ALL, category2 != null ? category2.getString() : "All");
      this.category = category2;
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      return count == 0 && this.isDoubleDouble(value, value2);
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

   public Category getCategory() {
      return this.category;
   }
}
