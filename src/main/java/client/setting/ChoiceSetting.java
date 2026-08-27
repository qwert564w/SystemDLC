package client.setting;

import client.data.ChoiceOption;

public class ChoiceSetting extends BooleanSetting {
   private final transient ChoiceOption choiceOption;
   private final transient ChoiceOption choiceOption2;

   public ChoiceSetting(String text, String text2, ChoiceOption choiceOption3, ChoiceOption choiceOption4, boolean flag) {
      super(text, text2, flag);
      this.choiceOption = choiceOption3;
      this.choiceOption2 = choiceOption4;
   }

   @Override
   public String getTypeId() {
      return "choice";
   }

   public ChoiceOption getChoiceOption() {
      return this.choiceOption;
   }

   public ChoiceOption getChoiceOption2() {
      return this.choiceOption2;
   }

   public ChoiceOption getChoiceOption3() {
      return this.isFlag3() ? this.choiceOption2 : this.choiceOption;
   }
}
