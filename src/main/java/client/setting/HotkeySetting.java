package client.setting;

import client.util.KeyboardState;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import org.lwjgl.glfw.GLFW;

public class HotkeySetting extends Setting {
   @Expose
   private int value;
   @Expose
   private int value2;
   @Expose
   private int value3;
   @Expose
   private String text;
   private Runnable runnable;

   public HotkeySetting(String text2, String text3, int count) {
      super(text2, text3);
      this.value3 = count;
      this.value = count;
      this.value2 = 0;
      this.text = KeybindSetting.getStringByIntInt(count, 0);
   }

   public HotkeySetting(String text, String text2, int count, Runnable runnable2) {
      this(text, text2, count);
      this.runnable = runnable2;
   }

   @Override
   public String getTypeId() {
      return "hotkey";
   }

   public String getText() {
      return this.text;
   }

   public int getValue2() {
      return this.value2;
   }

   public void setValue2(int count) {
      this.value2 = count;
   }

   public int getValue3() {
      return this.value3;
   }

   @Override
   public void reset() {
      this.setInt(this.value3);
   }

   public void setValue3(int count) {
      this.value3 = count;
   }

   public int getValue() {
      return this.value;
   }

   public Runnable getRunnable() {
      return this.runnable;
   }

   public void setText(String text2) {
      this.text = text2;
   }

   public void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
   }

   public String getText2() {
      return this.text;
   }

   public void update() {
      if (this.runnable != null) {
         this.runnable.run();
      }
   }

   public void onIntInt(int count, int count2) {
      this.value = count2;
      this.value2 = count;
      this.text = KeybindSetting.getStringByIntInt(count2, count);
      KeyboardState.getKeyboardState().setFlag();
   }

   public void setInt(int count) {
      this.value = count;
      this.value2 = 0;
      this.text = this.getStringByInt(count);
      KeyboardState.getKeyboardState().setFlag();
   }

   @Override
   public void fromJson(JsonObject jsonObject) {
      if (jsonObject.has("keyCode")) {
         int i = jsonObject.get("keyCode").getAsInt();
         int j = jsonObject.has("modifierFlags") ? jsonObject.get("modifierFlags").getAsInt() : 0;
         this.onIntInt(j, i);
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", this.getTypeId());
      jsonobject.addProperty("keyCode", this.value);
      jsonobject.addProperty("modifierFlags", this.value2);
      jsonobject.addProperty("defaultKeyCode", this.value3);
      jsonobject.addProperty("keyName", this.text);
      jsonobject.addProperty("fullKeyName", this.getText2());
      return jsonobject;
   }

   public boolean check() {
      if (this.value != -1 && this.value != -1) {
         long i = GLFW.glfwGetCurrentContext();
         if (i == 0L) {
            return false;
         } else {
            int j = this.value2;
            if (!KeybindSetting.isIntLong(j, i)) {
               return false;
            } else if (this.value >= 0 && this.value <= 7) {
               return GLFW.glfwGetMouseButton(i, this.value) == 1;
            } else {
               return this.value < 32 ? false : GLFW.glfwGetKey(i, this.value) == 1;
            }
         }
      } else {
         return false;
      }
   }

   public String getStringByInt(int count) {
      if (count == -1) {
         return "None";
      } else {
         switch (count) {
            case 0:
               return "M1";
            case 1:
               return "M2";
            case 2:
               return "M3";
            case 3:
               return "M4";
            case 4:
               return "M5";
            case 5:
               return "M6";
            case 6:
               return "M7";
            case 7:
               return "M8";
            default:
               switch (count) {
                  case 32:
                     return "SPACE";
                  case 39:
                     return "'";
                  case 44:
                     return ",";
                  case 45:
                     return "-";
                  case 46:
                     return ".";
                  case 47:
                     return "/";
                  case 59:
                     return ";";
                  case 61:
                     return "=";
                  case 91:
                     return "[";
                  case 92:
                     return "\\";
                  case 93:
                     return "]";
                  case 96:
                     return "`";
                  case 256:
                     return "ESC";
                  case 257:
                     return "ENTER";
                  case 258:
                     return "TAB";
                  case 259:
                     return "BACKSPACE";
                  case 260:
                     return "INSERT";
                  case 261:
                     return "DELETE";
                  case 262:
                     return "RIGHT";
                  case 263:
                     return "LEFT";
                  case 264:
                     return "DOWN";
                  case 265:
                     return "UP";
                  case 266:
                     return "PAGE_UP";
                  case 267:
                     return "PAGE_DOWN";
                  case 268:
                     return "HOME";
                  case 269:
                     return "END";
                  case 280:
                     return "CAPS";
                  case 281:
                     return "SCROLL_LOCK";
                  case 282:
                     return "NUM_LOCK";
                  case 340:
                     return "LSHIFT";
                  case 341:
                     return "LCTRL";
                  case 342:
                     return "LALT";
                  case 344:
                     return "RSHIFT";
                  case 345:
                     return "RCTRL";
                  case 346:
                     return "RALT";
                  default:
                     if (count >= 290 && count <= 314) {
                        return "F" + (count - 290 + 1);
                     } else if (count >= 320 && count <= 329) {
                        return "KP_" + (count - 320);
                     } else if (count >= 48 && count <= 57) {
                        return String.valueOf((char)count);
                     } else {
                        return count >= 65 && count <= 90 ? String.valueOf((char)count) : "KEY_" + count;
                     }
               }
         }
      }
   }
}
