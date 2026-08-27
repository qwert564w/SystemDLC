package client.render;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class ShaderCache {
   private static final ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();
   private static final Gson gson = new Gson();

   public static ShaderProgramKey getShaderProgramKeyByStringVertexFormatDefines(String text, VertexFormat vertexFormat, Defines defines) {
      return new ShaderProgramKey(Identifier.ofVanilla("core/" + text), vertexFormat, defines);
   }

   public static String getStringByStringIdentifier(String text, Identifier identifier) {
      try {
         String s;
         try (
            InputStream inputstream = resourceManager.open(identifier);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
         ) {
            s = bufferedreader.lines().collect(Collectors.joining(text));
         }

         return s;
      } catch (IOException ioexception) {
         throw new RuntimeException(ioexception);
      }
   }

   public static Object getObjectByStringClass(String text, Class value) {
      try {
         return gson.fromJson(text, value);
      } catch (Exception exception) {
         return null;
      }
   }
}
