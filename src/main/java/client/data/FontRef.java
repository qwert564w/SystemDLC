package client.data;

import client.render.IconAtlas;

public record FontRef(IconAtlas font, float weight) {
   public float getWeight() {
      return this.weight;
   }

   public IconAtlas getFont() {
      return this.font;
   }
}
