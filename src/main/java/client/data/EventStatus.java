package client.data;

import client.enums.EventState;

public record EventStatus(String text, EventState state, Integer remainingSec) {
   public EventState getState() {
      return this.state;
   }

   public Integer getRemainingSec() {
      return this.remainingSec;
   }

   public String getText() {
      return this.text;
   }
}
