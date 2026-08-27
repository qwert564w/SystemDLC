package client.gui.widget;

import client.api.ListEntry;
import client.util.Animation;
import client.util.Easings;

public final class StringListEntry implements ListEntry {
   public final String text;
   public final Animation animation;

   StringListEntry(String text2) {
      this.text = text2;
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
