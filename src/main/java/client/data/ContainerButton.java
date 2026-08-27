package client.data;

import client.enums.ContainerAction;

public record ContainerButton(int x, int y, int width, ContainerAction action) {
   public int getY() {
      return this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public ContainerAction getAction() {
      return this.action;
   }

   public int getX() {
      return this.x;
   }

   public boolean isFloatFloat(float value, float value2) {
      return value >= this.x && value <= this.x + this.width && value2 >= this.y && value2 <= this.y + 20;
   }
}
