package client.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class FriendNames {
   private static final FriendNames INSTANCE = new FriendNames();

   public void onStringString(String text, String text2) {
      SystemFriend.getInstance().onStringString3(text2, text);
   }

   public void onString(String text) {
      SystemFriend.getInstance().removeString(text);
   }

   public String getStringByString(String text) {
      return SystemFriend.getInstance().getStringByString(text);
   }

   public String getStringByString2(String text) {
      return SystemFriend.getInstance().getStringByString2(text);
   }

   public void onStringString2(String text, String text2) {
      SystemFriend.getInstance().onStringString5(text2, text);
   }

   public String getStringByString3(String text) {
      String s = SystemFriend.getInstance().getStringByString4(text);
      return s == null ? text : s;
   }

   public void onString2(String text) {
      SystemFriend.getInstance().addString2(text);
   }

   public static FriendNames getInstance() {
      return INSTANCE;
   }

   public List getList() {
      SystemFriend systemfriend = SystemFriend.getInstance();
      ArrayList arraylist = new ArrayList(systemfriend.getSet());
      arraylist.sort(Comparator.<String, String>comparing(var1x -> {
         String s = systemfriend.getStringByString2(var1x);
         return s == null ? "" : s;
      }).reversed().thenComparing(Comparator.naturalOrder()));
      return arraylist;
   }
}
