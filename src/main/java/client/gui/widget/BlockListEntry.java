package client.gui.widget;

import client.api.ListEntry;
import client.data.AnimatedColor;
import client.data.AnimatedInt;
import client.data.Tween;
import client.util.Animation;
import client.util.EasingPresets;
import client.util.Easings;
import net.minecraft.block.Block;

public final class BlockListEntry implements ListEntry {
   public final Block block;
   public final Animation animation;
   public final Tween tween;
   public final AnimatedInt animatedInt;
   public AnimatedColor animatedColor;

   BlockListEntry(Block block2, boolean flag, int count) {
      this.block = block2;
      this.animation = new Animation(4.5F).getAnimationByFunction(Easings::getFloatByFloat7).getAnimation();
      this.tween = EasingPresets.getTweenByFloatFloat2(flag ? 1.0F : 0.0F, 0.22F);
      this.animatedInt = new AnimatedInt(count, 0.22F);
   }

   @Override
   public float itemHeight() {
      return 26.0F;
   }

   @Override
   public Animation animation() {
      return this.animation;
   }
}
