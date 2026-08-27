package client.module.player;

import client.data.SystemFriend;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.InputSetting;
import client.util.StringParts;

public class Protect extends Module {
   private BooleanSetting hideNick;
   private InputSetting svoyNick;
   private BooleanSetting hideDruzey;
   private BooleanSetting hideParol;
   private BooleanSetting podmenaBorda;
   private InputSetting anarhiya;
   private InputSetting tokeny;
   private InputSetting rang;
   private ColorSetting colorRanga;
   private static String[] stringArray = new String[]{"/login ", "/l ", "/register ", "/reg ", "/auth ", "/changepassword ", "/changepass ", "/cp ", "/2fa "};

   public Protect() {
      super("Protect", Category.PLAYER);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Скрывать ник");
      booleansetting.setDescription("Скрыть свой никнейм");
      this.hideNick = booleansetting;
      InputSetting inputsetting = new InputSetting(
         "",
         "",
         StringParts.join(new String[]{"S", "у", "s", "t", "e", "m", "P", "l", "a", "y", "e", "r"}),
         StringParts.join(new String[]{"В", "в", "e", "д", "и", "т", "е", " ", "н", "и", "к", ".", ".", "."})
      );
      inputsetting.setName("Свой ник");
      inputsetting.setDescription("Ваш скрытый никнейм");
      this.svoyNick = inputsetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Скрывать друзей");
      booleansetting.setDescription("Заменять ники друзей на алиас");
      this.hideDruzey = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Скрывать пароль");
      booleansetting.setDescription("Маскировать пароли в чате");
      this.hideParol = booleansetting;
      booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Подмена борда");
      booleansetting.setDescription("Подменять значения в scoreboard (FunTime)");
      this.podmenaBorda = booleansetting;
      InputSetting inputsetting1 = new InputSetting("", "", "", StringParts.join(new String[]{"Н", "a", "п", "р", ".", " ", "1"}));
      inputsetting1.setName("Анархия");
      inputsetting1.setDescription("Подмена номера анархии");
      this.anarhiya = inputsetting1;
      InputSetting inputsetting2 = new InputSetting("", "", "", StringParts.join(new String[]{"Н", "a", "п", "р", ".", " ", "0"}));
      inputsetting2.setName("Токены");
      inputsetting2.setDescription("Подмена количества токенов");
      this.tokeny = inputsetting2;
      InputSetting inputsetting3 = new InputSetting("", "", "", StringParts.join(new String[]{"Н", "a", "п", "р", ".", " ", "И", "г", "р", "o", "к"}));
      inputsetting3.setName("Ранг");
      inputsetting3.setDescription("Подмена ранга");
      this.rang = inputsetting3;
      ColorSetting colorsetting = new ColorSetting("", "", -10682526);
      colorsetting.setName("Цвет ранга");
      colorsetting.setDescription("Цвет подменённого ранга");
      this.colorRanga = colorsetting;
      this.addSetting(this.hideNick);
      this.addSetting(this.svoyNick);
      this.addSetting(this.hideDruzey);
      this.addSetting(this.hideParol);
      this.addSetting(this.podmenaBorda);
      this.addSetting(this.anarhiya);
      this.addSetting(this.tokeny);
      this.addSetting(this.rang);
      this.addSetting(this.colorRanga);
      this.svoyNick.setVisibleWhen(this.hideNick::isFlag3);
      this.anarhiya.setVisibleWhen(this.podmenaBorda::isFlag3);
      this.tokeny.setVisibleWhen(this.podmenaBorda::isFlag3);
      this.rang.setVisibleWhen(this.podmenaBorda::isFlag3);
      this.colorRanga.setVisibleWhen(this::getBoolean);
   }

   public String getString() {
      return this.rang.getText();
   }

   public String getStringByString(String text) {
      int[] aint = this.getIntArrayByString(text);
      if (aint == null) {
         return text;
      } else {
         String s = text.substring(0, aint[0]);
         String s1 = text.substring(aint[0], aint[1]);
         String s2 = text.substring(aint[1]);
         return s + s1.replaceAll("\\S", "*") + s2;
      }
   }

   public boolean check3() {
      return this.isEnabled() && this.hideParol.isFlag3();
   }

   public BooleanSetting getHideDruzey() {
      return this.hideDruzey;
   }

   public String getString2() {
      return this.anarhiya.getText();
   }

   @Override
   public void onDisable() {
   }

   public String getString3() {
      return this.tokeny.getText();
   }

   public BooleanSetting getHideNick() {
      return this.hideNick;
   }

   public InputSetting getSvoyNick() {
      return this.svoyNick;
   }

   public String getStringByString2(String text) {
      if (!this.isEnabled()) {
         return text;
      } else if (this.hideNick.isFlag3() && this.client().player != null && text.equals(this.client().player.getGameProfile().getName())) {
         String s1 = this.svoyNick.getText();
         return s1 != null && !s1.isEmpty() ? s1 : "SystemPlayer";
      } else if (this.hideDruzey.isFlag3() && this.isFriend(text)) {
         String s = SystemFriend.getInstance().getStringByString(text);
         return s != null && !s.isEmpty() ? s : "SystemFriend";
      } else {
         return text;
      }
   }

   private Boolean getBoolean() {
      return this.podmenaBorda.isFlag3() && this.rang.getText() != null && !this.rang.getText().isEmpty();
   }

   public boolean check4() {
      return this.isEnabled() && this.podmenaBorda.isFlag3();
   }

   public int getInt() {
      return this.colorRanga.getInt();
   }

   public int[] getIntArrayByString(String text) {
      String s = text.toLowerCase();
      int i = -1;
      int j = -1;

      for (String s1 : stringArray) {
         int k = 0;

         while ((k = s.indexOf(s1, k)) != -1) {
            boolean flag = k == 0 || !Character.isLetterOrDigit(text.charAt(k - 1));
            if (flag) {
               if (i == -1 || k < i) {
                  i = k;
                  j = k + s1.length();
               }
               break;
            }

            k += s1.length();
         }
      }

      return i == -1 ? null : new int[]{j, text.length()};
   }

   @Override
   public void onEnable() {
   }
}
