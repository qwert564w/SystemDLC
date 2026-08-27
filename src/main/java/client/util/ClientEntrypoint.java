package client.util;

import client.concurrent.SystemClient;
import net.fabricmc.api.ClientModInitializer;

public class ClientEntrypoint implements ClientModInitializer {
   public void onInitializeClient() {
      new SystemClient();
   }
}
