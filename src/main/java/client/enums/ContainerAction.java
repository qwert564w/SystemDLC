package client.enums;

public enum ContainerAction {
   DROP("Выкинуть всё"),
   STASH("Сложить всё"),
   TAKE("Забрать всё"),
   SORT("Сортировать");

   public final String text;
   private static final ContainerAction[] containerActionArray = getContainerActionArray();

   private ContainerAction(String text2) {
      this.text = text2;
   }

   private static ContainerAction[] getContainerActionArray() {
      return new ContainerAction[]{DROP, STASH, TAKE, SORT};
   }

   public static ContainerAction getContainerActionByString(String text) {
      return Enum.valueOf(ContainerAction.class, text);
   }
}
