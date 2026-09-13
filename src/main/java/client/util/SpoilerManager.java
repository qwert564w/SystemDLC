package client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.Optional;

public class SpoilerManager {
    
    // Обход Polar: Смена сессии без закрытия клиента (Runtime Session Spoofing)
    public static void spoofSession(String newUsername, String java.util.UUID.fromString(newUUID), String token) {
        try {
            MinecraftClient mc = MinecraftClient.getInstance();
            Field sessionField = MinecraftClient.class.getDeclaredField("session"); 
            sessionField.setAccessible(true);
            
            // Создаем новую сессию (обход проверки Microsoft/Mojang на уровне клиента)
            Session newSession = new Session(newUsername, java.util.UUID.fromString(newUUID), token, 
                Optional.empty(), Optional.empty(), Session.AccountType.MOJANG);
            sessionField.set(mc, newSession);
            
            // Чистим кэш скинов и пропертей, чтобы античит не спалил старый UUID
            // mc.getSessionProperties().clear(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Подмена прокси перед реконнектом (байпасс IP бана)
    public static void setProxy(String ip, int port) {
        try {
            // Устанавливаем SOCKS5 прокси для всех Java сокетов
            System.setProperty("socksProxyHost", ip);
            System.setProperty("socksProxyPort", String.valueOf(port));
            // Для HTTP прокси
            System.setProperty("http.proxyHost", ip);
            System.setProperty("http.proxyPort", String.valueOf(port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Форсим реконнект без закрытия клиента
    public static void reconnect() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world != null) {
            mc.world.disconnect();
            mc.disconnect();
            // Возвращаем на экран мультиплеера
            mc.setScreen(null); 
        }
    }
}
