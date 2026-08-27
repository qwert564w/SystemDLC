package client.gui.hud;

import client.concurrent.ModuleRegistry;
import client.concurrent.SystemClient;
import client.gui.widget.KeybindEntry;
import client.module.Category;
import client.module.CategoryType;
import client.module.Module;
import client.module.client.HudModule;
import client.module.client.SliskGui;
import client.setting.BooleanSetting;
import client.setting.KeybindSetting;
import java.util.ArrayList;
import java.util.List;

public class KeybindsHud extends HudPanel {
   private final BooleanSetting vizualyVBindah;
   private final ArrayList<KeybindEntry> list5;

   public KeybindsHud() {
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Визуалы в биндах");
      booleansetting.setDescription("Показывать бинды на визуал-модули");
      this.vizualyVBindah = booleansetting;
      this.list5 = new ArrayList<>();
      this.addSetting(this.vizualyVBindah);
   }

   @Override
   public String getString() {
      return "Кейбиндз";
   }

   @Override
   protected List getList2() {
      return List.of(
         new KeybindEntry("СлискГуи", "Y", true, Category.CLIENT.getCategoryType()),
         new KeybindEntry("ТриггерБот", "R", true, Category.COMBAT.getCategoryType()),
         new KeybindEntry("АимАззизт", "V", true, Category.COMBAT.getCategoryType())
      );
   }

   @Override
   protected String getString2() {
      return "Keybinds";
   }

   @Override
   public String getString3() {
      return "ks";
   }

   @Override
   protected boolean check25() {
      return true;
   }

   @Override
   protected float getFloat30() {
      return 20.0F;
   }

   @Override
   protected List getList3() {
      ArrayList arraylist = this.list5;
      arraylist.clear();
      SystemClient systemclient = SystemClient.getInstance();
      if (systemclient == null) {
         return arraylist;
      } else {
         ModuleRegistry moduleregistry = systemclient.getModuleRegistry();
         if (moduleregistry == null) {
            return arraylist;
         } else {
            boolean flag = this.vizualyVBindah.isFlag3();

            for (Module module : moduleregistry.getList22()) {
               if (!(module instanceof SliskGui) && (flag || module.getCategory() != Category.VISUAL)) {
                  KeybindSetting keybindsetting = module.getKeybindSetting();
                  if (keybindsetting != null) {
                     int i = keybindsetting.getValue();
                     if (i != -1) {
                        Category category = module.getCategory();
                        arraylist.add(new KeybindEntry(module.getName(), keybindsetting.getText2(), true, category != null ? category.getCategoryType() : null));
                     }
                  }
               }
            }

            return arraylist;
         }
      }
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.KEYBOARD;
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getKeybindy().setBoolean(flag);
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getKeybindy().isFlag3();
   }
}
