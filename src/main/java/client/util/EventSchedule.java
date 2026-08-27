package client.util;

import java.util.List;

public record EventSchedule(List<EventInfo> active, List<EventInfo> upcoming, List<EventInfo> done, Integer nextDelaySec) {
   public List<EventInfo> getUpcoming() {
      return this.upcoming;
   }

   public List<EventInfo> getDone() {
      return this.done;
   }

   public Integer getNextDelaySec() {
      return this.nextDelaySec;
   }

   public List<EventInfo> getActive() {
      return this.active;
   }
}
