package client.gui.widget;

import client.data.Tween;
import client.enums.GuiTab;
import client.module.Category;
import client.module.Module;
import client.util.Easings;
import java.util.List;
import java.util.function.Consumer;
import org.joml.Matrix4f;

public class NavBar extends Widget {
   private static final float value239 = 0.18F;
   private static final float value240 = 0.32F;
   private static final float value241 = 10.0F;
   private final TopBar topBar;
   private final ContentArea contentArea;
   private final ConfigsPage configsPage;
   private final FriendsPage friendsPage;
   private final WaypointsPage waypointsPage;
   private final Tween tween4 = new Tween(1.0F, 0.18F).getTweenByFunction(Easings::getFloatByFloat4);
   private boolean flag4 = false;
   private Runnable runnable;
   private GuiTab guiTab = GuiTab.MODULES;
   private Category category;

   public NavBar() {
      this.value237 = 963.0F;
      this.value238 = 700.0F;
      this.topBar = new TopBar();
      this.contentArea = new ContentArea();
      this.configsPage = new ConfigsPage();
      this.friendsPage = new FriendsPage();
      this.waypointsPage = new WaypointsPage();
      this.topBar.setConsumer(this.contentArea::onBoolean);
   }

   private void update3() {
      this.tween4.setFloat(1.0F);
      this.flag4 = false;
      this.runnable = null;
   }

   private void setRunnable(Runnable runnable2) {
      this.runnable = runnable2;
      this.flag4 = true;
      this.tween4.setValue5(0.18F);
      this.tween4.getTweenByFunction(Easings::getFloatByFloat4);
      this.tween4.setFloat2(0.0F);
   }

   @Override
   public boolean isIntDoubleDouble(int count, double value, double value2) {
      if (this.topBar.isIntDoubleDouble(count, value, value2)) {
         return true;
      } else if (this.isDouble(value2)) {
         return true;
      } else {
         return this.flag4 ? true : this.getWidget().isIntDoubleDouble(count, value, value2);
      }
   }

   public void onList(List list) {
      this.setRunnable(() -> {
         this.guiTab = GuiTab.FAVORITES;
         this.category = null;
         this.topBar.setString("Favorites");
         this.contentArea.addList(list);
         this.contentArea.setFloat2(0.0F);
         this.topBar.onBoolean(true);
      });
   }

   @Override
   public boolean isIntIntInt(int count, int count2, int count3) {
      return this.getWidget().isIntIntInt(count, count2, count3);
   }

   public float getFloat() {
      return this.value236 + 48.0F + 1.0F;
   }

   public void update5() {
      this.guiTab = GuiTab.FRIENDS;
      this.topBar.setString2("Friends");
      this.friendsPage.update4();
      this.topBar.onBoolean(false);
      this.update3();
   }

   public void update6() {
      this.guiTab = GuiTab.WAYPOINTS;
      this.topBar.setString2("Waypoints");
      this.waypointsPage.update4();
      this.topBar.onBoolean(false);
      this.update3();
   }

   @Override
   public void onFloatFloatFloatMatrix4f(float value, float value2, float value3, Matrix4f matrix4f) {
      float f = this.tween4.getFloat();
      if (this.flag4 && f <= 0.001F) {
         if (this.runnable != null) {
            this.runnable.run();
         }

         this.runnable = null;
         this.flag4 = false;
         this.tween4.setValue5(0.32F);
         this.tween4.getTweenByFunction(Easings::getFloatByFloat9);
         this.tween4.setFloat2(1.0F);
         f = this.tween4.getFloat();
      }

      float f5 = this.value236;
      float f4 = this.value235;
      this.topBar.onFloatFloat2(f5, f4);
      this.topBar.onFloatFloatFloatMatrix4f(value, value2, value3, matrix4f);
      float f1 = this.value236 + 48.0F + 1.0F;
      float f2 = this.value238 - 48.0F - 1.0F;
      Widget widget = this.getWidget();
      float f6 = this.value235;
      widget.onFloatFloat2(f1, f6);
      widget.setValue237(this.value237);
      widget.setValue238(f2);
      float f3 = (1.0F - f) * 10.0F;
      float f7 = value * f;
      this.onFloatWidgetFloatMatrix4fFloatFloatFloatFloat(f7, widget, f1, matrix4f, f2, f3, value2, value3);
      if (widget == this.contentArea) {
         this.contentArea.onMatrix4fFloat(matrix4f, value * f);
      }
   }

   public float getFloat2() {
      return this.value238 - 48.0F - 1.0F;
   }

   public void update7() {
      this.setRunnable(() -> {
         this.guiTab = GuiTab.WAYPOINTS;
         this.topBar.setString("Waypoints");
         this.waypointsPage.update4();
         this.topBar.onBoolean(false);
      });
   }

   public void update8() {
      this.setRunnable(() -> {
         this.guiTab = GuiTab.CONFIGS;
         this.topBar.setString("Configs");
         this.configsPage.update3();
         this.topBar.onBoolean(false);
      });
   }

   public void update9() {
      switch (this.guiTab) {
         case CONFIGS:
            this.configsPage.update3();
            break;
         case FRIENDS:
            this.friendsPage.update4();
         case FAVORITES:
         default:
            break;
         case WAYPOINTS:
            this.waypointsPage.update4();
      }
   }

   public float getFloat3() {
      return this.tween4.getValue3();
   }

   @Override
   public boolean isDoubleDoubleDouble(double value, double value2, double value3) {
      if (this.isDouble(value3)) {
         return true;
      } else {
         return this.flag4 ? true : this.getWidget().isDoubleDoubleDouble(value, value2, value3);
      }
   }

   public void onRunnable(Runnable runnable) {
      this.topBar.setRunnable(runnable);
   }

   @Override
   public boolean isIntChar(int count, char symbol) {
      return this.getWidget().isIntChar(count, symbol);
   }

   public void onFloatFloatBoolean(float value, float value2, boolean flag) {
      this.value237 = value;
      this.topBar.setValue237(value);
      this.topBar.setFlag5(flag);
      this.contentArea.onFloatFloat4(value2, value);
      this.configsPage.onFloatFloat4(value2, value);
      this.friendsPage.onFloatFloat4(value2, value);
      this.waypointsPage.onFloatFloat4(value2, value);
   }

   @Override
   public boolean isIntIntInt2(int count, int count2, int count3) {
      return this.getWidget().isIntIntInt2(count, count2, count3);
   }

   private void onFloatWidgetFloatMatrix4fFloatFloatFloatFloat(
      float value, Widget widget2, float value2, Matrix4f matrix4f2, float value3, float value4, float value5, float value6
   ) {
      if (value4 <= 0.001F) {
         widget2.onFloatFloatFloatMatrix4f(value, value5, value6, matrix4f2);
      } else {
         Matrix4f matrix4f = new Matrix4f(matrix4f2).translate(0.0F, value4, 0.0F);
         float f1 = this.value237;
         float f = this.value235;
         ScissorStack.onFloatFloatFloatFloat(f1, value3, value2, f);

         try {
            widget2.onFloatFloatFloatMatrix4f(value, value5, value6, matrix4f);
         } finally {
            ScissorStack.update();
         }
      }
   }

   public void onConsumer(Consumer<Boolean> consumer) {
      this.topBar.setConsumer2(consumer);
   }

   public Category getCategory() {
      return this.category;
   }

   public GuiTab getGuiTab() {
      return this.guiTab;
   }

   public ContentArea getContentArea() {
      return this.contentArea;
   }

   public boolean isCategory(Category category2) {
      return this.guiTab == GuiTab.MODULES && this.category == category2;
   }

   public void onCategoryList(Category category2, List list) {
      this.guiTab = GuiTab.MODULES;
      this.category = category2;
      this.topBar.setCategory(category2);
      this.contentArea.addList(list);
      this.contentArea.setFloat2(0.0F);
      this.topBar.onBoolean(false);
      this.update3();
   }

   private void onListCategoryFloat(List list, Category category2, float value) {
      this.guiTab = GuiTab.MODULES;
      this.category = category2;
      this.topBar.setCategory2(category2);
      this.contentArea.addList(list);
      this.contentArea.setFloat2(value);
      this.topBar.onBoolean(false);
   }

   public void update10() {
      this.guiTab = GuiTab.CONFIGS;
      this.topBar.setString2("Configs");
      this.configsPage.update3();
      this.topBar.onBoolean(false);
      this.update3();
   }

   public void onCategoryListFloat2(Category category, List list, float value) {
      this.setRunnable(() -> this.onListCategoryFloat(list, category, value));
   }

   public void setList2(List list) {
      this.guiTab = GuiTab.FAVORITES;
      this.category = null;
      this.topBar.setString2("Favorites");
      this.contentArea.addList(list);
      this.contentArea.setFloat2(0.0F);
      this.topBar.onBoolean(true);
      this.update3();
   }

   @Override
   public boolean isDoubleDoubleInt(double value, double value2, int count) {
      return this.isDouble(value2) ? false : this.getWidget().isDoubleDoubleInt(value, value2, count);
   }

   @Override
   public boolean isDoubleDoubleIntDoubleDouble(double value, double value2, int count, double value3, double value4) {
      return this.getWidget().isDoubleDoubleIntDoubleDouble(value, value2, count, value3, value4);
   }

   public void onModule(Module module2) {
      this.contentArea.setModule(module2);
   }

   private Widget getWidget() {
      Object object;
      if (this.guiTab == GuiTab.CONFIGS) {
         object = this.configsPage;
      } else if (this.guiTab == GuiTab.FRIENDS) {
         object = this.friendsPage;
      } else if (this.guiTab == GuiTab.WAYPOINTS) {
         object = this.waypointsPage;
      } else {
         object = this.contentArea;
      }

      return (Widget)object;
   }

   public boolean isDouble(double value) {
      return value >= this.value236 && value < this.value236 + 48.0F;
   }

   public boolean isDoubleIntDouble(double value, int count, double value2) {
      return this.topBar.isIntDoubleDouble(count, value2, value);
   }

   public void update12() {
      this.setRunnable(() -> {
         this.guiTab = GuiTab.FRIENDS;
         this.topBar.setString("Friends");
         this.friendsPage.update4();
         this.topBar.onBoolean(false);
      });
   }
}
