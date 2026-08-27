package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.setting.MultilistSetting;
import client.setting.Setting;
import java.util.Arrays;
import java.util.List;

public class NoRender extends Module {
   private static final List<String> list = Arrays.asList(
      "Огонь в худе",
      "Таблица очков",
      "Тряска при уроне",
      "Тряска при беге",
      "Тьма вардена",
      "Тошнота",
      "Индикатор атаки",
      "Эффекты",
      "Полоса босса",
      "Пузыри",
      "Бутыли опыта",
      "Свечение",
      "Погода",
      "Барьер",
      "Виньетка",
      "Партиклы взрывов",
      "Партиклы блоков",
      "Визер сердца",
      "Туман",
      "Тени"
   );
   private static final List<String> list2 = Arrays.asList(
      "Огонь в худе",
      "Таблица очков",
      "Тряска при уроне",
      "Тряска при беге",
      "Тьма вардена",
      "Тошнота",
      "Индикатор атаки",
      "Эффекты",
      "Полоса босса",
      "Пузыри",
      "Бутыли опыта",
      "Свечение",
      "Погода",
      "Барьер",
      "Виньетка",
      "Партиклы взрывов",
      "Партиклы блоков",
      "Визер сердца",
      "Туман",
      "Тени"
   );
   public final MultilistSetting otklyuchit;
   private boolean flag;

   public NoRender() {
      super("NoRender", Category.VISUAL);
      MultilistSetting multilistsetting = new MultilistSetting("", "", list, list2);
      multilistsetting.setName("Отключить");
      multilistsetting.setDescription("Что отключить");
      this.otklyuchit = multilistsetting;
      this.addSettings(new Setting[]{this.otklyuchit});
      this.flag = this.otklyuchit.isString("Тени");
   }

   public boolean check3() {
      return this.isEnabled() && this.otklyuchit.isString("Тьма вардена");
   }

   public boolean check4() {
      return this.isEnabled() && this.otklyuchit.isString("Таблица очков");
   }

   public boolean check5() {
      return this.isEnabled() && this.flag;
   }

   public boolean check6() {
      return this.isEnabled() && this.otklyuchit.isString("Виньетка");
   }

   public boolean check7() {
      return this.isEnabled() && this.otklyuchit.isString("Тряска при уроне");
   }

   @Override
   protected void onSettingChanged(Setting setting2) {
      this.flag = this.otklyuchit.isString("Тени");
   }

   public boolean check8() {
      return this.isEnabled() && this.otklyuchit.isString("Пузыри");
   }

   public boolean check9() {
      return this.isEnabled() && this.otklyuchit.isString("Тряска при беге");
   }

   @Override
   public void onDisable() {
   }

   public boolean check10() {
      return this.isEnabled() && this.otklyuchit.isString("Эффекты");
   }

   public boolean check11() {
      return this.isEnabled() && this.otklyuchit.isString("Огонь в худе");
   }

   public boolean check12() {
      return this.isEnabled() && this.otklyuchit.isString("Полоса босса");
   }

   public boolean check13() {
      return this.isEnabled() && this.otklyuchit.isString("Бутыли опыта");
   }

   public boolean check14() {
      return this.isEnabled() && this.otklyuchit.isString("Свечение");
   }

   public boolean check15() {
      return this.isEnabled() && this.otklyuchit.isString("Индикатор атаки");
   }

   public boolean check16() {
      return this.isEnabled() && this.otklyuchit.isString("Партиклы взрывов");
   }

   @Override
   public void onEnable() {
   }

   public boolean check17() {
      return this.isEnabled() && this.otklyuchit.isString("Визер сердца");
   }

   public boolean check18() {
      return this.isEnabled() && this.otklyuchit.isString("Тошнота");
   }

   public boolean check19() {
      return this.isEnabled() && this.otklyuchit.isString("Погода");
   }

   public boolean check20() {
      return this.isEnabled() && this.otklyuchit.isString("Туман");
   }

   public boolean check21() {
      return this.isEnabled() && this.otklyuchit.isString("Партиклы блоков");
   }

   public boolean check22() {
      return this.isEnabled() && this.otklyuchit.isString("Барьер");
   }
}
