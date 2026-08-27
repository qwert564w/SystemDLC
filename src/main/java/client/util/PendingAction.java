package client.util;

import client.module.Module;

public record PendingAction(Module owner, Runnable abort) {
   public Runnable getAbort() {
      return this.abort;
   }

   public Module getOwner() {
      return this.owner;
   }
}
