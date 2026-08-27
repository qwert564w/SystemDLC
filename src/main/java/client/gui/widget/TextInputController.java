package client.gui.widget;

import java.util.function.Consumer;
import java.util.function.IntPredicate;

public final class TextInputController {
   private boolean flag;
   private long time;
   private final TextInputState textInputState;
   private Consumer<String> consumer;
   private Runnable runnable;
   private IntPredicate intPredicate;

   public TextInputController() {
      this(14.0F);
   }

   public TextInputController(float value) {
      this.textInputState = new TextInputState(value);
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void update() {
      if (this.flag) {
         this.flag = false;
         this.textInputState.setString2("");
         if (this.runnable != null) {
            this.runnable.run();
         }
      }
   }

   public TextInputController getTextInputControllerByConsumer(Consumer<String> consumer2) {
      this.consumer = consumer2;
      return this;
   }

   public boolean check() {
      long i = System.currentTimeMillis() - this.time;
      return i / 500L % 2L == 0L;
   }

   public boolean isIntInt(int count, int count2) {
      if (!this.flag) {
         return false;
      } else {
         this.time = System.currentTimeMillis();
         if (count2 == 257 || count2 == 335) {
            this.update2();
            return true;
         } else if (count2 == 256) {
            this.update();
            return true;
         } else {
            return this.textInputState.isIntInt(count, count2);
         }
      }
   }

   public boolean isChar(char symbol) {
      if (!this.flag) {
         return false;
      } else if (this.intPredicate != null && !this.intPredicate.test(symbol)) {
         return false;
      } else {
         this.time = System.currentTimeMillis();
         return this.textInputState.isChar(symbol);
      }
   }

   public void setString(String text) {
      this.flag = true;
      this.textInputState.setString2(text == null ? "" : text);
      this.time = System.currentTimeMillis();
   }

   public TextInputController getTextInputControllerByIntPredicate(IntPredicate intPredicate2) {
      this.intPredicate = intPredicate2;
      return this;
   }

   public String getString() {
      return this.textInputState.getString2();
   }

   public TextInputState getTextInputState() {
      return this.textInputState;
   }

   public TextInputController getTextInputControllerByRunnable(Runnable runnable2) {
      this.runnable = runnable2;
      return this;
   }

   public void update2() {
      if (this.flag) {
         String s = this.textInputState.getString2();
         this.flag = false;
         this.textInputState.setString2("");
         if (this.consumer != null) {
            this.consumer.accept(s);
         }
      }
   }
}
