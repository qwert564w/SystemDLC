package client.gui.widget;

import client.data.FriendNames;
import client.module.Feature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FriendsPage extends PageWidget<FriendRow, FriendAddForm> {
   private final FriendAddForm friendAddForm = new FriendAddForm();

   public FriendsPage() {
      this.friendAddForm.setConsumer(this::onString2);
      this.update3();
   }

   private void onStringString(String text, String text2) {
      if (text != null) {
         FriendNames friendnames = FriendNames.getInstance();
         String s = text2 != null && !text2.isEmpty() ? text2 : null;
         friendnames.onStringString(s, text);
         this.update3();
      }
   }

   private void onString(String text) {
      if (text != null) {
         if (Feature.mc != null && Feature.mc.keyboard != null) {
            Feature.mc.keyboard.setClipboard(text);
         }
      }
   }

   private void update3() {
      List<String> list = FriendNames.getInstance().getList();
      HashMap hashmap = new HashMap();

      for (FriendRow friendrow : this.list) {
         if (!friendrow.isFlag4() && friendrow.getText() != null) {
            hashmap.put(friendrow.getText(), friendrow);
         }
      }

      HashSet hashset = new HashSet();
      ArrayList arraylist = new ArrayList(list.size());

      for (String s : list) {
         if (s != null) {
            hashset.add(s);
            String s1 = FriendNames.getInstance().getStringByString3(s);
            String s2 = s1 != null && !s1.equals(s) ? s1 : "";
            String s3 = FriendNames.getInstance().getStringByString(s);
            String s4 = FriendNames.getInstance().getStringByString2(s);
            FriendRow friendrow1 = (FriendRow)hashmap.get(s);
            if (friendrow1 != null) {
               friendrow1.onStringStringStringString(s, s2, s3, s4);
            } else {
               friendrow1 = this.getFriendRowByStringStringString(s3, s, s4);
            }

            arraylist.add(friendrow1);
         }
      }

      for (FriendRow friendrow2 : this.list) {
         if (!friendrow2.isFlag4() && (friendrow2.getText() == null || !hashset.contains(friendrow2.getText()))) {
            friendrow2.update3();
            arraylist.add(friendrow2);
         }
      }

      this.list.clear();
      this.list.addAll(arraylist);
   }

   private void onString2(String text) {
      if (text != null && !text.isBlank()) {
         FriendNames.getInstance().onString2(text.trim());
         this.update3();
      }
   }

   private void onString3(String text) {
      if (text != null) {
         FriendNames.getInstance().onString(text);
         this.update3();
      }
   }

   @Override
   protected Widget getWidget() {
      return this.getFriendAddForm();
   }

   @Override
   protected void onWidgetBooleanFloatFloat(Widget widget2, boolean flag, float value, float value2) {
      FriendAddForm friendaddform = (FriendAddForm)widget2;
      this.onFriendAddFormBooleanFloatFloat(friendaddform, flag, value2, value);
   }

   private void onStringString2(String text, String text2) {
      if (text != null) {
         FriendNames friendnames = FriendNames.getInstance();
         String s = text2 != null && !text2.isEmpty() ? text2 : null;
         friendnames.onStringString2(s, text);
         this.update3();
      }
   }

   @Override
   protected boolean check() {
      return this.friendAddForm.getTextField().isFlag4();
   }

   @Override
   protected void onFloat(float value) {
      this.friendAddForm.setFloat(value);
   }

   protected FriendAddForm getFriendAddForm() {
      return this.friendAddForm;
   }

   @Override
   protected float getFloat3() {
      return 300.0F;
   }

   protected void onFriendAddFormBooleanFloatFloat(FriendAddForm friendAddForm, boolean flag, float value, float value2) {
      if (flag) {
         friendAddForm.onFloatFloat4(value, value2);
      } else {
         friendAddForm.onFloatFloat2(value, value2);
      }
   }

   public void update4() {
      this.update3();
   }

   private FriendRow getFriendRowByStringStringString(String text, String text2, String text3) {
      FriendRow friendrow = new FriendRow(text2, text, text3);
      friendrow.setFloat(this.value244);
      friendrow.setConsumer(this::onString3);
      friendrow.setConsumer2(this::onString);
      friendrow.setBiConsumer(this::onStringString2);
      friendrow.setBiConsumer2(this::onStringString);
      return friendrow;
   }
}
