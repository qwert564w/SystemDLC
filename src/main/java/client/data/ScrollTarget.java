package client.data;

import client.gui.widget.IconLabel;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public final class ScrollTarget {
   public final IconLabel iconLabel;
   public final Supplier<String> supplier;
   private final BooleanSupplier booleanSupplier;
   private float value = 1.0F;

   public ScrollTarget(IconLabel iconLabel2, Supplier supplier2, BooleanSupplier booleanSupplier2) {
      this.iconLabel = iconLabel2;
      this.supplier = supplier2;
      this.booleanSupplier = booleanSupplier2;
   }

   public float getValue(float value2) {
      return value2 * this.value;
   }

   public boolean check() {
      return this.booleanSupplier == null || this.booleanSupplier.getAsBoolean();
   }

   public void setValue(float value2) {
      this.value = value2;
   }

   public IconLabel getIconLabel() {
      return this.iconLabel;
   }
}
