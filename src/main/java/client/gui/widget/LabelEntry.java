package client.gui.widget;

import client.api.ListEntry;
import client.data.LabelData;
import client.util.Animation;
import client.util.Easings;

public final class LabelEntry implements ListEntry {
   public LabelData labelData;
   public final Animation animation;

   LabelEntry(LabelData labelData2) {
      this.labelData = labelData2;
      this.animation = new Animation(4.5F).getAnimationByFunction(Easings::getFloatByFloat7).getAnimation();
   }

   @Override
   public float itemHeight() {
      return 20.0F;
   }

   @Override
   public Animation animation() {
      return this.animation;
   }
}
