package client.gui.widget;

import client.api.ListEntry;
import client.gui.hud.HudPanel;
import client.util.Animation;
import client.util.Easings;

public final class KeybindRow implements ListEntry {
   public KeybindEntry keybindEntry;
   public final Animation animation;
   public final HudPanel hudPanel;

   public KeybindRow(HudPanel hudPanel2, KeybindEntry keybindEntry2) {
      this.hudPanel = hudPanel2;
      this.keybindEntry = keybindEntry2;
      this.animation = new Animation(4.5F).getAnimationByFunction(Easings::getFloatByFloat7).getAnimation();
   }

   @Override
   public float itemHeight() {
      return this.hudPanel.getFloatByKeybindEntry(this.keybindEntry);
   }

   @Override
   public Animation animation() {
      return this.animation;
   }
}
