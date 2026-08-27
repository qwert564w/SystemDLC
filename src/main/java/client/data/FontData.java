package client.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FontData {
   @SerializedName("atlas")
   private GlyphMetrics glyphMetrics;
   @SerializedName("metrics")
   private FontMetrics fontMetrics;
   @SerializedName("glyphs")
   private List<GlyphInfo> list;
   @SerializedName("kerning")
   private List<AtlasInfo> list2;

   public List<AtlasInfo> getList2() {
      return this.list2;
   }

   public GlyphMetrics getGlyphMetrics() {
      return this.glyphMetrics;
   }

   public List<GlyphInfo> getList() {
      return this.list;
   }

   public FontMetrics getFontMetrics() {
      return this.fontMetrics;
   }
}
