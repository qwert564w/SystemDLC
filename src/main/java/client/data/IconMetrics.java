package client.data;

import client.module.CategoryType;
import java.util.EnumMap;
import java.util.Map;

public final class IconMetrics {
   private static final float value = 14.0F;
   private static final Map<CategoryType, IconSize> map = new EnumMap<>(CategoryType.class);

   private IconMetrics() {
   }

   static {
      map.put(CategoryType.POTION, new IconSize(9.0F, 14.0F));
      map.put(CategoryType.WAYPOINT, new IconSize(11.0F, 14.0F));
      map.put(CategoryType.FRIENDS, new IconSize(14.0F, 10.0F));
      map.put(CategoryType.KEYBOARD, new IconSize(14.0F, 9.0F));
      map.put(CategoryType.COORDS, new IconSize(12.0F, 12.0F));
      map.put(CategoryType.LOGO, new IconSize(14.0F, 14.0F));
      map.put(CategoryType.BOLT, new IconSize(13.0F, 14.0F));
      map.put(CategoryType.GLOBE, new IconSize(14.0F, 14.0F));
      map.put(CategoryType.RACK, new IconSize(14.0F, 14.0F));
      map.put(CategoryType.CUBE, new IconSize(12.0F, 14.0F));
   }

   public static float getFloatByCategoryType(CategoryType categoryType) {
      IconSize iconsize = map.get(categoryType);
      return iconsize != null ? iconsize.getH() : 14.0F;
   }

   public static float getFloatByCategoryType2(CategoryType categoryType) {
      IconSize iconsize = map.get(categoryType);
      return iconsize != null ? iconsize.getW() : 14.0F;
   }
}
