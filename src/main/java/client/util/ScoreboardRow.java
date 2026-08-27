package client.util;

import net.minecraft.text.Text;

public record ScoreboardRow(Text name, int score, Text formattedScore, int scoreWidth) {
   public Text getFormattedScore() {
      return this.formattedScore;
   }

   public int getScoreWidth() {
      return this.scoreWidth;
   }

   public int getScore() {
      return this.score;
   }

   public Text getName() {
      return this.name;
   }
}
