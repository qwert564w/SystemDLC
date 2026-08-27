package client.data;

import client.gui.widget.PanelWidget;
import client.util.EasingPresets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PanelLayout {
   private final Map<PanelWidget, Tween> map = new HashMap<>();

   public void onList(List<PanelWidget> list) {
      for (PanelWidget panelwidget : list) {
         Tween tween = this.map.get(panelwidget);
         if (tween != null) {
            tween.setFloat2(panelwidget.getSetting().isVisible() ? 1.0F : 0.0F);
            tween.getFloat();
         }
      }
   }

   public void onList2(List<PanelWidget> list) {
      this.map.keySet().retainAll(list);

      for (PanelWidget panelwidget : list) {
         this.map.computeIfAbsent(panelwidget, var0 -> {
            Tween tween = EasingPresets.getTweenByFloatFloat2(var0.getSetting().isVisible() ? 1.0F : 0.0F, 0.22F);
            tween.setFloat2(var0.getSetting().isVisible() ? 1.0F : 0.0F);
            return tween;
         });
      }
   }

   public float getFloatByPanelWidget(PanelWidget panelWidget) {
      Tween tween = this.map.get(panelWidget);
      if (tween == null) {
         return panelWidget.getSetting().isVisible() ? 1.0F : 0.0F;
      } else {
         return tween.getValue3();
      }
   }
}
