package client.render;

import client.api.Hook;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.util.ChunkAnimatorAccess;
import net.minecraft.util.Identifier;

public class ShaderSourceProvider {
   @Hook(
      targetName = "net.caffeinemc.mods.sodium.client.gl.shader.ShaderLoader",
      method = "getShaderSource",
      desc = "(Lnet/minecraft/class_2960;)Ljava/lang/String;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static String getStringByIdentifier(Identifier identifier) {
      String s = (String)HandleInvoker.getObjectByObjectArray2(identifier);
      String s1 = String.valueOf(identifier);
      return ChunkAnimatorAccess.getStringByStringString(s, s1);
   }

   @Hook(
      targetName = "net.caffeinemc.mods.sodium.client.render.chunk.DefaultChunkRenderer",
      method = "setModelMatrixUniforms",
      desc = "(Lnet/caffeinemc/mods/sodium/client/render/chunk/shader/ChunkShaderInterface;Lnet/caffeinemc/mods/sodium/client/render/chunk/region/RenderRegion;Lnet/caffeinemc/mods/sodium/client/render/viewport/CameraTransform;)V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onObjectObjectObject(Object value, Object value2, Object value3) {
      ChunkAnimatorAccess.onObject(value2);
   }

   @Hook(
      targetName = "net.caffeinemc.mods.sodium.client.render.chunk.RenderSection",
      method = "delete",
      desc = "()V",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onObject(Object value) {
      boolean flag = false;
      ChunkAnimatorAccess.onBooleanObject(flag, value);
   }

   @Hook(
      targetName = "net.caffeinemc.mods.sodium.client.render.chunk.RenderSection",
      method = "setInfo",
      desc = "(Lnet/caffeinemc/mods/sodium/client/render/chunk/data/BuiltSectionInfo;)Z",
      getInjectPoint = InjectPoint.HEAD
   )
   public static void onObjectObject(Object value, Object value2) {
      boolean flag = value2 != null;
      ChunkAnimatorAccess.onBooleanObject(flag, value);
   }
}
