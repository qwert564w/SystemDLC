package client.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;

public final class SocketListener implements Listener {
   private final StringBuilder stringBuilder;
   public final SocketClient socketClient;

   public SocketListener(SocketClient socketClient2) {
      this.socketClient = socketClient2;
      this.stringBuilder = new StringBuilder();
   }

   @Override
   public CompletionStage onText(WebSocket webSocket, CharSequence text, boolean flag) {
      this.stringBuilder.append(text);
      if (flag) {
         String s = this.stringBuilder.toString();
         this.stringBuilder.setLength(0);

         try {
            JsonObject jsonobject = JsonParser.parseString(s).getAsJsonObject();
            if (this.socketClient.consumer != null) {
               this.socketClient.consumer.accept(jsonobject);
            }
         } catch (Exception exception) {
         }
      }

      webSocket.request(1L);
      return null;
   }

   @Override
   public void onOpen(WebSocket webSocket) {
      webSocket.request(1L);
   }
}
