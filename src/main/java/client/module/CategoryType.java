package client.module;

public enum CategoryType {
   LOGO("logo"),
   ALL("all"),
   CLIENT("client"),
   COMBAT("combat"),
   MOVEMENT("movement"),
   PANEL("panel"),
   PARTICLES("particles"),
   PLAYER("player"),
   RENDER("render"),
   SEARCH("search"),
   DROPDOWN_ARROWS("dropdown_arrows"),
   CHEVRON_RIGHT("chevron_right"),
   DROPDOWN_SUCCESS("dropdown_success"),
   STAR("star"),
   STAR_FILLED("star_filled"),
   EXPAND_ALL("expand_all"),
   COLLAPSE_ALL("collapse_all"),
   SUN("sun"),
   MOON("moon"),
   LANGUAGE("language"),
   SUCCESS_CHECKBOX("success_checkbox"),
   KEYBOARD("keyboard"),
   CLOSE("close"),
   POTION("potion"),
   HEART("heart"),
   COOLDOWN("cooldown"),
   EDIT("edit"),
   SETTINGS("settings"),
   VISUAL("visual"),
   CLOUDS("clouds"),
   SAVE("save"),
   SHARE("share"),
   TRASH("trash"),
   INFO("info"),
   FRIENDS("friends"),
   WAYPOINT("waypoint"),
   WAYPOINT_ARROW("waypoint_arrow"),
   COPY("copy"),
   TOOLTIP_ARROW("tooltip_arrow"),
   TOOLTIP_ARROW_UP("tooltip_arrow_up"),
   TOOLTIP_ARROW_LEFT("tooltip_arrow_left"),
   TOOLTIP_ARROW_RIGHT("tooltip_arrow_right"),
   ALIGN_LEFT("align_left"),
   ALIGN_CENTER("align_center"),
   ALIGN_RIGHT("align_right"),
   ARMOR_HUD("armor_hud"),
   TARGET_HUD("target_hud"),
   SWAP_HUD("swap_hud"),
   BOLT("bolt"),
   GLOBE("globe"),
   RACK("rack"),
   CUBE("cube"),
   WATERMARK("watermark"),
   PLUS("plus"),
   KEY("key"),
   LOCK_OPEN("lock_open"),
   LOCK_CLOSED("lock_closed"),
   ORIENT_VERTICAL("orient_vertical"),
   ORIENT_HORIZONTAL("orient_horizontal"),
   ARROW_LEFT("arrow_left"),
   ARROW_RIGHT("arrow_right"),
   COORDS("coords"),
   SETTING_DROPDOWN("setting_dropdown"),
   SETTING_COLOR("setting_color"),
   SETTING_SLIDER("setting_slider"),
   SETTING_CHECKBOX("setting_checkbox"),
   SETTING_ALIGNMENT("setting_alignment"),
   BRUSH("brush"),
   RESET("reset"),
   EYEDROPPER("eyedropper"),
   TIMER_RING("timer_ring"),
   USE_TRACKER("use_tracker");

   private final String key;
   private float width = 16.0F;
   private float height = 16.0F;
   private float strokeWidth = 1.0F;
   private boolean filled;
   private String[] paths = new String[0];
   private static final CategoryType[] $VALUES = $values();

   private CategoryType(String key) {
      this.key = key;
   }

   public String getKey() {
      return this.key;
   }

   public void set(float width, float height, float strokeWidth, boolean filled, String[] paths) {
      if (width > 0.0F) {
         this.width = width;
      }

      if (height > 0.0F) {
         this.height = height;
      }

      if (strokeWidth > 0.0F) {
         this.strokeWidth = strokeWidth;
      }

      this.filled = filled;
      if (paths != null && paths.length > 0) {
         this.paths = paths;
      }
   }

   private static CategoryType[] $values() {
      return new CategoryType[]{
         LOGO,
         ALL,
         CLIENT,
         COMBAT,
         MOVEMENT,
         PANEL,
         PARTICLES,
         PLAYER,
         RENDER,
         SEARCH,
         DROPDOWN_ARROWS,
         CHEVRON_RIGHT,
         DROPDOWN_SUCCESS,
         STAR,
         STAR_FILLED,
         EXPAND_ALL,
         COLLAPSE_ALL,
         SUN,
         MOON,
         LANGUAGE,
         SUCCESS_CHECKBOX,
         KEYBOARD,
         CLOSE,
         POTION,
         HEART,
         COOLDOWN,
         EDIT,
         SETTINGS,
         VISUAL,
         CLOUDS,
         SAVE,
         SHARE,
         TRASH,
         INFO,
         FRIENDS,
         WAYPOINT,
         WAYPOINT_ARROW,
         COPY,
         TOOLTIP_ARROW,
         TOOLTIP_ARROW_UP,
         TOOLTIP_ARROW_LEFT,
         TOOLTIP_ARROW_RIGHT,
         ALIGN_LEFT,
         ALIGN_CENTER,
         ALIGN_RIGHT,
         ARMOR_HUD,
         TARGET_HUD,
         SWAP_HUD,
         BOLT,
         GLOBE,
         RACK,
         CUBE,
         WATERMARK,
         PLUS,
         KEY,
         LOCK_OPEN,
         LOCK_CLOSED,
         ORIENT_VERTICAL,
         ORIENT_HORIZONTAL,
         ARROW_LEFT,
         ARROW_RIGHT,
         COORDS,
         SETTING_DROPDOWN,
         SETTING_COLOR,
         SETTING_SLIDER,
         SETTING_CHECKBOX,
         SETTING_ALIGNMENT,
         BRUSH,
         RESET,
         EYEDROPPER,
         TIMER_RING,
         USE_TRACKER
      };
   }

   public String[] getPaths() {
      return this.paths;
   }

   public float getHeight() {
      return this.height;
   }

   public float getWidth() {
      return this.width;
   }

   public boolean isFilled() {
      return this.filled;
   }

   public float getStrokeWidth() {
      return this.strokeWidth;
   }
}
