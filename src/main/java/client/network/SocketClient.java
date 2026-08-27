package client.network;

import b.Boot;
import client.transform.NativeBridgeUtil;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class SocketClient {
   public final Consumer<JsonObject> consumer;
   private WebSocket webSocket;
   private boolean flag;

   public SocketClient(Consumer consumer2) {
      this.consumer = consumer2;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public void onStringJsonObject(String text, JsonObject jsonObject) {
      WebSocket websocket = this.webSocket;
      if (websocket != null) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("op", "pub");
         jsonobject.addProperty("topic", text);
         jsonobject.add("data", jsonObject);
         websocket.sendText(jsonobject.toString(), true);
      }
   }

   public void update() {
      this.flag = true;
      WebSocket websocket = this.webSocket;
      if (websocket != null) {
         try {
            websocket.sendClose(1000, "bye");
         } catch (Exception exception) {
         }
      }

      this.webSocket = null;
   }

   public boolean check() {
      try {
         HttpClient httpclient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5L)).build();
         SocketListener socketlistener = new SocketListener(this);
         this.webSocket = httpclient.newWebSocketBuilder()
            .connectTimeout(Duration.ofSeconds(5L))
            .buildAsync(URI.create(Boot.wsUrl()), socketlistener)
            .get(Duration.ofSeconds(10L).toSeconds(), TimeUnit.SECONDS);
         String s = NativeBridgeUtil.getText();
         if (s == null) {
            return false;
         } else {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("op", "auth");
            jsonobject.addProperty("session_id", s);
            jsonobject.addProperty("source", "Client");
            this.webSocket.sendText(jsonobject.toString(), true).get();
            return true;
         }
      } catch (Exception exception) {
         return false;
      }
   }

   public void onString(String text) {
      WebSocket websocket = this.webSocket;
      if (websocket != null) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("op", "sub");
         jsonobject.addProperty("topic", text);
         websocket.sendText(jsonobject.toString(), true);
      }
   }
}
