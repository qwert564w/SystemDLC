package client.data;

public record ContainerBounds(int left, int right, int bgTop) {
   public int getRight() {
      return this.right;
   }

   public int getBgTop() {
      return this.bgTop;
   }

   public int getLeft() {
      return this.left;
   }
}
