package client.gui.widget;

public abstract class LabelWidget extends Widget {
   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      return count == 0 && this.isDoubleDouble(value, value2) ? this.isDoubleDouble2(value2, value) : false;
   }

   protected abstract boolean isDoubleDouble2(double value, double value2);
}
