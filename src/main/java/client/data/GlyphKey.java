package client.data;

import client.render.IconAtlas;

public record GlyphKey(IconAtlas font, String text, int sizeBits) {
   @Override
   public boolean equals(Object value) {
      if (value instanceof GlyphKey glyphkey) {
         GlyphKey glyphkey1 = glyphkey;

         IconAtlas iconatlas = glyphkey1.getFont();
         glyphkey1 = glyphkey;

         String s = glyphkey1.getText();
         glyphkey1 = glyphkey;

         int i = glyphkey1.getSizeBits();
         if (true) {
            return iconatlas == this.font && i == this.sizeBits && s.equals(this.text);
         }
      }

      return false;
   }

   @Override
   public int hashCode() {
      return (System.identityHashCode(this.font) * 31 + this.text.hashCode()) * 31 + this.sizeBits;
   }

   public int getSizeBits() {
      return this.sizeBits;
   }

   public String getText() {
      return this.text;
   }

   public IconAtlas getFont() {
      return this.font;
   }
}
