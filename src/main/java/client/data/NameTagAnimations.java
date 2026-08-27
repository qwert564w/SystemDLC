package client.data;

import client.util.Animation;
import client.util.Easings;

public final class NameTagAnimations {
   public final Animation animation = new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7);
   public final Animation animation2 = new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7);
   public final Animation animation3 = new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7);
   public final Animation animation4 = new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7);
   public final Animation animation5 = new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7);
   public final Animation[] animationArray = new Animation[]{
      new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7),
      new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7),
      new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7),
      new Animation(5.0F).getAnimationByFunction(Easings::getFloatByFloat7)
   };
   public float value = Float.NaN;
   public boolean flag = true;
   public boolean flag2;

   public NameTagAnimations() {
   }
}
