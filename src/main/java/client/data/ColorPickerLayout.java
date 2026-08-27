package client.data;

public record ColorPickerLayout(
   float boxX,
   float boxY,
   float slidersX,
   float hueY,
   float alphaY,
   float sliderBlockY,
   float sliderBlockH,
   float readoutY,
   float modeBlockX,
   float modeBlockY,
   float presetsX,
   float presetsY,
   float presetGap,
   float savedHeaderY,
   float descMaxW
) {
   public float getAlphaY() {
      return this.alphaY;
   }

   public float getModeBlockX() {
      return this.modeBlockX;
   }

   public float getBoxY() {
      return this.boxY;
   }

   public float getSlidersX() {
      return this.slidersX;
   }

   public float getSavedHeaderY() {
      return this.savedHeaderY;
   }

   public float getReadoutY() {
      return this.readoutY;
   }

   public float getSliderBlockY() {
      return this.sliderBlockY;
   }

   public float getPresetsY() {
      return this.presetsY;
   }

   public float getHueY() {
      return this.hueY;
   }

   public float getBoxX() {
      return this.boxX;
   }

   public float getPresetGap() {
      return this.presetGap;
   }

   public float getDescMaxW() {
      return this.descMaxW;
   }

   public float getPresetsX() {
      return this.presetsX;
   }

   public float getSliderBlockH() {
      return this.sliderBlockH;
   }

   public float getModeBlockY() {
      return this.modeBlockY;
   }
}
