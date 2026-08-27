package client.setting;

import client.gui.widget.SwapWheelEditor;

public class SwapWheelSetting extends ActionSetting {
   private final SwapWheelEditor swapWheelEditor;

   public SwapWheelSetting(String text, String text2, SwapWheelEditor swapWheelEditor2) {
      super(text, text2);
      this.swapWheelEditor = swapWheelEditor2;
   }

   public SwapWheelEditor getSwapWheelEditor() {
      return this.swapWheelEditor;
   }
}
