package client.data;

public final class StringCursor {
   private final String text;
   private int value;

   public StringCursor(String text2) {
      this.text = text2;
   }

   public boolean check() {
      this.update();
      return this.value < this.text.length() && Character.isLetter(this.text.charAt(this.value));
   }

   public boolean check2() {
      this.update();
      if (this.value >= this.text.length()) {
         return false;
      } else {
         char c0 = this.text.charAt(this.value);
         return c0 == '-' || c0 == '+' || c0 == '.' || Character.isDigit(c0);
      }
   }

   public float getFloat() {
      this.update();
      int i = this.value;
      boolean flag = false;

      while (this.value < this.text.length()) {
         char c0 = this.text.charAt(this.value);
         if (!Character.isDigit(c0) && c0 != '.' && (c0 != '-' && c0 != '+' || this.value != i) && (c0 != '-' && c0 != '+' || !flag)) {
            if (c0 != 'e' && c0 != 'E') {
               break;
            }

            flag = true;
            this.value++;
         } else {
            flag = false;
            this.value++;
         }
      }

      return Float.parseFloat(this.text.substring(i, this.value));
   }

   private void update() {
      while (this.value < this.text.length()) {
         char c0 = this.text.charAt(this.value);
         if (Character.isWhitespace(c0) || c0 == ',') {
            this.value++;
            continue;
         }
         break;
      }
   }

   public char getChar() {
      return this.text.charAt(this.value++);
   }

   public boolean check3() {
      this.update();
      return this.value < this.text.length();
   }
}
