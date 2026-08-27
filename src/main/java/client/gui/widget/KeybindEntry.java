package client.gui.widget;

import client.module.CategoryType;

public final class KeybindEntry {
   private String text;
   private String text2;
   private boolean flag;
   private CategoryType categoryType;

   public KeybindEntry(String text, String text2, boolean flag) {
      this(text, text2, flag, null);
   }

   public KeybindEntry(String text, String text2) {
      this(text, text2, false, null);
   }

   public KeybindEntry(String text3, String text4, boolean flag2, CategoryType categoryType2) {
      this.text = text3;
      this.text2 = text4;
      this.flag = flag2;
      this.categoryType = categoryType2;
   }

   public String getText2() {
      return this.text2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public CategoryType getCategoryType() {
      return this.categoryType;
   }

   public String getText() {
      return this.text;
   }

   public KeybindEntry getKeybindEntryByBooleanCategoryTypeStringString(boolean flag2, CategoryType categoryType2, String text3, String text4) {
      this.text = text3;
      this.text2 = text4;
      this.flag = flag2;
      this.categoryType = categoryType2;
      return this;
   }
}
