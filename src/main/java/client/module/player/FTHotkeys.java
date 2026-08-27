package client.module.player;

import client.module.Category;
import client.module.Module;
import client.setting.KeybindSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.StringParts;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class FTHotkeys extends Module {
   private static final Pattern pattern = Pattern.compile("Монет:\\s*([\\d,.\\s]+)");
   private SliderSetting slotLiva;
   private SliderSetting delayKlika;
   private SliderSetting taymaut;
   private KeybindSetting keyLivaAreny;
   private KeybindSetting keyInvesta;
   private KeybindSetting keySklada;
   private boolean flag;
   private int value235;
   private int value236;

   public FTHotkeys() {
      super("FTHotkeys", Category.PLAYER);
      SliderSetting slidersetting = new SliderSetting("", "", 25.0, 1.0, 54.0, 1.0, "", 0);
      slidersetting.setName("Слот лива");
      slidersetting.setDescription("Номер слота меню /darena для клика (1-54)");
      this.slotLiva = slidersetting;
      slidersetting = new SliderSetting("", "", 1.0, 0.0, 20.0, 1.0, StringParts.join(new String[]{" ", "т", "и", "к", "o", "в"}), 0);
      slidersetting.setName("Задержка клика");
      slidersetting.setDescription("Сколько тиков ждать после открытия меню перед кликом");
      this.delayKlika = slidersetting;
      slidersetting = new SliderSetting("", "", 60.0, 10.0, 2000.0, 5.0, StringParts.join(new String[]{" ", "т", "и", "к", "o", "в"}), 0);
      slidersetting.setName("Таймаут");
      slidersetting.setDescription("Сколько тиков ждать открытия меню");
      this.taymaut = slidersetting;
      KeybindSetting keybindsetting = new KeybindSetting("", "", -1, this::update14);
      keybindsetting.setName("Клавиша лива арены");
      keybindsetting.setDescription("Прожать чтобы выполнить /darena и кликнуть по слоту");
      this.keyLivaAreny = keybindsetting;
      KeybindSetting keybindsetting1 = new KeybindSetting("", "", -1, this::update11);
      keybindsetting1.setName("Клавиша инвеста");
      keybindsetting1.setDescription("Прожать чтобы инвестировать весь баланс в клан");
      this.keyInvesta = keybindsetting1;
      KeybindSetting keybindsetting2 = new KeybindSetting("", "", -1, this::update12);
      keybindsetting2.setName("Клавиша склада");
      keybindsetting2.setDescription("Прожать чтобы открыть /clan storage");
      this.keySklada = keybindsetting2;
      this.flag = false;
      this.value235 = 0;
      this.value236 = -1;
      this.addSettings(new Setting[]{this.slotLiva, this.delayKlika, this.taymaut, this.keyLivaAreny, this.keyInvesta, this.keySklada});
   }

   @Override
   public void onTick() {
      if (this.flag) {
         if (!this.notInGame() && this.interactionManager() != null) {
            this.value235++;
            if (this.value235 > this.taymaut.getValue()) {
               this.update13();
            } else if (this.currentScreen() instanceof HandledScreen handledscreen) {
               if (!(handledscreen instanceof InventoryScreen)) {
                  ScreenHandler screenhandler = handledscreen.getScreenHandler();
                  int i = (int)this.slotLiva.getValue() - 1;
                  if (i >= 0 && i < screenhandler.slots.size()) {
                     if (this.value236 < 0) {
                        this.value236 = 0;
                     }

                     if (this.value236 < this.delayKlika.getValue()) {
                        this.value236++;
                     } else {
                        this.interactionManager().clickSlot(screenhandler.syncId, i, 0, SlotActionType.PICKUP, this.player());
                        handledscreen.close();
                        this.client().setScreen(null);
                        this.update13();
                     }
                  } else {
                     this.update13();
                  }
               }
            }
         } else {
            this.update13();
         }
      }
   }

   private void update11() {
      if (this.isEnabled()) {
         if (!this.notInGame() && this.networkHandler() != null) {
            long i = this.getLong();
            if (i > 0L) {
               this.networkHandler().sendChatCommand("clan invest " + i);
            }
         }
      }
   }

   private void update12() {
      if (this.isEnabled()) {
         if (!this.notInGame() && this.networkHandler() != null) {
            this.networkHandler().sendChatCommand("clan storage");
         }
      }
   }

   @Override
   public void onDisable() {
      this.update13();
   }

   private void update13() {
      this.flag = false;
      this.value235 = 0;
      this.value236 = -1;
   }

   private long getLong() {
      if (this.world() == null) {
         return -1L;
      } else {
         Scoreboard scoreboard = this.world().getScoreboard();
         if (scoreboard == null) {
            return -1L;
         } else {
            ScoreboardObjective scoreboardobjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
            if (scoreboardobjective == null) {
               return -1L;
            } else {
               for (ScoreboardEntry scoreboardentry : scoreboard.getScoreboardEntries(scoreboardobjective)) {
                  Team team = scoreboard.getScoreHolderTeam(scoreboardentry.owner());
                  String s = this.getStringByTeamString(team, scoreboardentry.owner());
                  long i = this.getLongByString(s);
                  if (i >= 0L) {
                     return i;
                  }
               }

               return -1L;
            }
         }
      }
   }

   private long getLongByString(String text2) {
      if (text2 != null && !text2.isEmpty()) {
         Matcher matcher = pattern.matcher(text2);
         if (!matcher.find()) {
            return -1L;
         } else {
            String s = matcher.group(1).replaceAll("[^0-9]", "");
            if (s.isEmpty()) {
               return -1L;
            } else {
               try {
                  return Long.parseLong(s);
               } catch (NumberFormatException numberformatexception) {
                  return -1L;
               }
            }
         }
      } else {
         return -1L;
      }
   }

   private String getStringByTeamString(Team team, String text2) {
      StringBuilder stringbuilder = new StringBuilder();
      if (team != null) {
         Text text = team.getPrefix();
         if (text != null) {
            stringbuilder.append(text.getString());
         }
      }

      if (text2 != null) {
         stringbuilder.append(text2);
      }

      if (team != null) {
         Text text1 = team.getSuffix();
         if (text1 != null) {
            stringbuilder.append(text1.getString());
         }
      }

      return stringbuilder.toString();
   }

   private void update14() {
      if (this.isEnabled()) {
         if (!this.notInGame() && this.networkHandler() != null) {
            if (!this.flag) {
               this.networkHandler().sendChatCommand("darena");
               this.flag = true;
               this.value235 = 0;
               this.value236 = -1;
            }
         }
      }
   }

   @Override
   public void onEnable() {
      this.update13();
   }
}
