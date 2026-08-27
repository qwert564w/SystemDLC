package client.gui.widget;

import client.api.ListEntry;
import client.data.ResourceItem;
import client.data.Tween;
import client.util.Animation;
import client.util.EasingPresets;
import client.util.Easings;
import net.minecraft.item.ItemStack;

public final class ItemSlotEntry implements ListEntry {
   public final ResourceItem resourceItem;
   public final Animation animation;
   public final Tween tween;
   public ItemStack itemStack;
   public String text;

   ItemSlotEntry(ResourceItem resourceItem2, boolean flag) {
      this.resourceItem = resourceItem2;
      this.animation = new Animation(4.5F).getAnimationByFunction(Easings::getFloatByFloat7).getAnimation();
      this.tween = EasingPresets.getTweenByFloatFloat2(flag ? 1.0F : 0.0F, 0.22F);
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
