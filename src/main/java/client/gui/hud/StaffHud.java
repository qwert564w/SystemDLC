package client.gui.hud;

import client.api.Theme;
import client.data.ChoiceOption;
import client.data.StaffEntry;
import client.data.ThemeConfig;
import client.enums.ThemePalette;
import client.gui.widget.KeybindEntry;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.render.ShapeShader;
import client.setting.ChoiceSetting;
import client.setting.StafflistSetting;
import client.util.MathUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.joml.Matrix4f;

public class StaffHud extends HudPanel {
   private static final Pattern pattern = Pattern.compile("^\\w{3,16}$");
   private static final float value277 = 20.0F;
   private static final float value278 = 0.22F;
   private static final float value279 = 0.9F;
   private static final float value280 = 0.55F;
   private static final float value281 = 6.0F;
   private static final float value282 = 4.0F;
   private static final int value283 = -22016;
   private static final int value284 = -43691;
   private static final int value285 = -11162881;
   private static final int value286 = -11141291;
   private static final int value287 = -5614081;
   private static final int value288 = -3618616;
   private static final Set<String> set2 = Set.of(
      "0kotee4ka0",
      "qyousuke",
      "mrpikachu69",
      "beakytuhcotte",
      "0xotnik",
      "leichtenshtein",
      "snikers_top4k",
      "ameba02",
      "killedbyakasa",
      "hapyc0t",
      "itstik66",
      "jockerdudka777",
      "gagtfyy",
      "ivanche44",
      "pupochek0012",
      "zxczahar4ik",
      "reym",
      "salattop",
      "8g",
      "feiis_02",
      "sterrist",
      "kakoitoegor",
      "fa_nat_om",
      "zamzung",
      "prepo_tar",
      "vventus",
      "jarusers",
      "mrkryaka",
      "iamyuffi",
      "sedirca",
      "itslameno228",
      "lun4iks",
      "sniper_pubgms",
      "iisixopat",
      "arxitektorr",
      "radino229",
      "i_l",
      "d1reevo",
      "echosymbol",
      "masik766",
      "get0ut",
      "wilans",
      "kxrzroio",
      "cashup",
      "da_malako",
      "izpaktwink",
      "will0w18",
      "furelanezure",
      "ystal_",
      "aboba6884",
      "cene4ka_4eka43",
      "alextheales",
      "pokorltel",
      "ts11yatu",
      "btnoy1",
      "lopertoper",
      "2polya22hollis",
      "howcantodie",
      "fortinaaau",
      "inspectrgavrilov",
      "karto4kaaa4a",
      "priforik_000",
      "davidik98015",
      "kamehb_b_bode",
      "geoguessr",
      "_suxarb_",
      "diotop4elik",
      "eferavlep",
      "command8913",
      "bipolyrkaa",
      "akity",
      "muxanski",
      "zwertagiuss",
      "palka_pilka",
      "pablisss",
      "ws1_",
      "kobra1337",
      "apelsinchik32",
      "maksim_mirnov",
      "sexyflamix0013",
      "repetyz09",
      "nefolmi",
      "aeris",
      "whynotlazarus",
      "olegtopgo9991qq",
      "aloofs_46",
      "lepsrihgob",
      "ne_medvedb",
      "notopero4ek",
      "ptichkiprob",
      "aigerok",
      "sipwize",
      "im_be3yn4ik",
      "quanto",
      "s11n4y",
      "dar1uss",
      "f1xminiplay",
      "apologize",
      "1valhalla",
      "noghtalit",
      "1finsi",
      "neiseha",
      "mulafka79",
      "siinta",
      "ryodzaki"
   );
   private final ChoiceSetting mode;
   private final StafflistSetting ownNiki;
   private final Map<String, StaffEntry> map2;
   private final HashSet<String> hashSet;
   private final ArrayList<KeybindEntry> list5;

   public StaffHud() {
      ChoiceSetting choicesetting = new ChoiceSetting("", "", new ChoiceOption("Обычный"), new ChoiceOption("FT"), false);
      choicesetting.setName("Режим");
      choicesetting.setDescription("Обычный: определение стафа по префиксам команд. FT: показывать онлайн-игроков из FT-состава как Moder.");
      this.mode = choicesetting;
      StafflistSetting stafflistsetting = new StafflistSetting("", "");
      stafflistsetting.setName("Свои ники");
      stafflistsetting.setDescription("Онлайн-игроки из этого списка показываются как Модератор.");
      this.ownNiki = stafflistsetting;
      this.map2 = new HashMap<>();
      this.hashSet = new HashSet<>();
      this.list5 = new ArrayList<>();
      this.addSetting(this.mode);
      this.addSetting(this.ownNiki);
   }

   @Override
   public String getString() {
      return "ЗтаффЛизт";
   }

   @Override
   protected List getList2() {
      this.map2.put("AdminExample", new StaffEntry("Админ", false));
      this.map2.put("ModerExample [V]", new StaffEntry("Модер", true));
      this.map2.put("HelperExample", new StaffEntry("Хелпер", false));
      return List.of(
         new KeybindEntry("AdminExample", "Админ", true),
         new KeybindEntry("ModerExample [V]", "Модер", true),
         new KeybindEntry("HelperExample", "Хелпер", true)
      );
   }

   private void addArrayList(ArrayList arrayList) {
      for (PlayerListEntry playerlistentry : Feature.mc.getNetworkHandler().getPlayerList()) {
         String s = playerlistentry.getProfile().getName();
         if (pattern.matcher(s).matches()) {
            this.hashSet.add(s);
            Team team = playerlistentry.getScoreboardTeam();
            String s1 = team == null ? "" : Formatting.strip(team.getPrefix().getString()).toLowerCase();
            boolean flag = team != null && isString(s1);
            boolean flag1 = this.ownNiki.isString2(s);
            if (flag || flag1) {
               String s2 = flag ? getStringByString(s1) : "Модер";
               boolean flag2 = playerlistentry.getGameMode() == GameMode.SPECTATOR;
               this.onArrayListBooleanStringString(arrayList, flag2, s2, s);
            }
         }
      }

      for (Team team1 : Feature.mc.world.getScoreboard().getTeams()) {
         String s3 = team1.getPrefix().getString();
         if (!s3.isEmpty()) {
            String s4 = Formatting.strip(s3).toLowerCase();
            if (isString(s4)) {
               String s5 = getStringByString(s4);

               for (String s6 : team1.getPlayerList()) {
                  if (s6 != null && !s6.isEmpty() && !this.hashSet.contains(s6) && pattern.matcher(s6).matches()) {
                     boolean flag3 = true;
                     this.onArrayListBooleanStringString(arrayList, flag3, s5, s6);
                  }
               }
            }
         }
      }
   }

   @Override
   protected int getIntByKeybindEntry(KeybindEntry keybindEntry) {
      StaffEntry staffentry = this.map2.get(keybindEntry.getText());
      return staffentry == null ? Theme.foreground() : getIntByString(staffentry.role());
   }

   @Override
   protected String getString2() {
      return "Staff";
   }

   @Override
   protected float getFloatByKeybindEntry3(KeybindEntry keybindEntry) {
      return 6.0F;
   }

   @Override
   public String getString3() {
      return "sl";
   }

   private static int getIntByString(String text) {
      String s = text.toLowerCase();

      return switch (s) {
         case "владелец" -> -22016;
         case "админ" -> -43691;
         case "модер" -> -11162881;
         case "хелпер" -> -11141291;
         case "куратор" -> -5614081;
         default -> -3618616;
      };
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getSpisokStafa().isFlag3();
   }

   private void onArrayListBooleanStringString(ArrayList arrayList, boolean flag, String text, String text2) {
      String s = flag ? text2 + " [V]" : text2;
      this.map2.put(s, new StaffEntry(text, flag));
      arrayList.add(new KeybindEntry(s, text, true));
   }

   @Override
   protected float getFloat30() {
      return 20.0F;
   }

   private int getIntByKeybindEntry2(KeybindEntry keybindEntry) {
      int i = this.getIntByKeybindEntry(keybindEntry);
      boolean flag = ThemeConfig.getThemePalette() == ThemePalette.INSTANCE2;
      float[] afloat = MathUtil.getFloatArrayByInt(i);
      float f3 = afloat[0];
      float f4 = afloat[1] * 0.55F;
      float f2 = flag ? 0.22F : 0.9F;
      float f1 = f4;
      float f = f3;
      return MathUtil.getIntByFloatFloatFloat(f2, f, f1);
   }

   @Override
   protected void onFloatMatrix4fFloatFloatKeybindEntryFloatFloat(float value, Matrix4f matrix4f, float value2, float value3, KeybindEntry keybindEntry, float value4, float value5) {
      StaffEntry staffentry = this.map2.get(keybindEntry.getText());
      float f = staffentry != null && staffentry.vanished() ? value4 * 0.55F : value4;
      float f1 = value2 - 6.0F;
      float f2 = value5 + 12.0F;
      float f3 = value + -4.0F;
      int i = this.getIntByKeybindEntry2(keybindEntry);
      float f5 = 4.0F;
      float f4 = 20.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f5, f1, i, matrix4f, f4, f2, f, f3);
   }

   @Override
   protected CategoryType getCategoryType2() {
      return CategoryType.FRIENDS;
   }

   @Override
   protected List getList3() {
      ArrayList arraylist = this.list5;
      arraylist.clear();
      this.map2.clear();
      this.hashSet.clear();
      if (Feature.mc != null
         && Feature.mc.player != null
         && Feature.mc.getNetworkHandler() != null
         && Feature.mc.world != null
         && !Feature.mc.isInSingleplayer()) {
         if (this.mode.isFlag3()) {
            this.onArrayList(arraylist);
         } else {
            this.addArrayList(arraylist);
         }

         return arraylist;
      } else {
         return arraylist;
      }
   }

   private void onArrayList(ArrayList arrayList) {
      for (PlayerListEntry playerlistentry : Feature.mc.getNetworkHandler().getPlayerList()) {
         String s = playerlistentry.getProfile().getName();
         if (pattern.matcher(s).matches()) {
            boolean flag = set2.contains(s.toLowerCase());
            boolean flag1 = this.ownNiki.isString2(s);
            if (flag || flag1) {
               boolean flag2 = playerlistentry.getGameMode() == GameMode.SPECTATOR;
               String s1 = "Модер";
               this.onArrayListBooleanStringString(arrayList, flag2, s1, s);
            }
         }
      }
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getSpisokStafa().setBoolean(flag);
   }

   private static boolean isString(String text) {
      boolean flag = Feature.mc.getCurrentServerEntry() != null && Feature.mc.getCurrentServerEntry().address.contains("mcfunny");
      return flag
         ? text.contains("helper") || text.contains("хелпер") || text.contains("moder") || text.contains("модер")
         : text.contains("helper")
            || text.contains("хелпер")
            || text.contains("moder")
            || text.contains("модер")
            || text.contains("admin")
            || text.contains("админ")
            || text.contains("owner")
            || text.contains("curator")
            || text.contains("куратор")
            || text.contains("поддержка")
            || text.contains("сотрудник")
            || text.contains("зам")
            || text.contains("стажёр");
   }

   private static String getStringByString(String text) {
      if (text.contains("owner")) {
         return "Владелец";
      } else if (text.contains("admin") || text.contains("админ")) {
         return "Админ";
      } else if (text.contains("moder") || text.contains("модер")) {
         return "Модер";
      } else if (text.contains("helper") || text.contains("хелпер")) {
         return "Хелпер";
      } else if (text.contains("curator") || text.contains("куратор")) {
         return "Куратор";
      } else if (text.contains("поддержка")) {
         return "Поддержка";
      } else if (text.contains("сотрудник")) {
         return "Сотрудник";
      } else if (text.contains("зам")) {
         return "Зам";
      } else {
         return text.contains("стажёр") ? "Стажёр" : "Персонал";
      }
   }
}
