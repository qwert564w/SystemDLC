package client.gui.widget;

public abstract class ListRow extends ListWidget {
   @Override
   public final boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.isFlag4()) {
         return false;
      } else {
         SmallButton smallbutton = this.getSmallButton();
         if (smallbutton != null && smallbutton.isIntDoubleDouble(count, value, value2)) {
            this.update4();
            return true;
         } else {
            ScrollState scrollstate = this.getScrollState();
            if (scrollstate != null && scrollstate.isDoubleIntDouble(value2, count, value)) {
               this.update4();
               return true;
            } else if (this.isIntDoubleDouble2(count, value2, value)) {
               return true;
            } else {
               this.update4();
               return false;
            }
         }
      }
   }

   protected abstract ScrollState getScrollState();

   protected boolean isIntDoubleDouble2(int count, double value, double value2) {
      return false;
   }

   protected SmallButton getSmallButton() {
      return null;
   }
}
