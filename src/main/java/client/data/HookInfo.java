package client.data;

import client.enums.HookPoint;

public final class HookInfo {
   public final String text;
   public final String text2;
   public final String text3;
   public final String text4;
   public final String text5;
   public final String text6;
   public final HookPoint hookPoint;
   public int value = -1;

   public HookInfo(String text7, String text8, String text9, String text10, String text11, String text12, HookPoint hookPoint2) {
      this.text = text7;
      this.text2 = text8;
      this.text3 = text9;
      this.text4 = text10;
      this.text5 = text11;
      this.text6 = text12;
      this.hookPoint = hookPoint2;
   }
}
