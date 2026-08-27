package client.render;

import client.data.FactoryRegistry;
import client.util.ResourceLoader;
import java.util.function.Consumer;

public class ShaderLoader {
   public ShaderLoader(Consumer<ResourceLoader> consumer) {
      ShaderRegistry.update2();
      FactoryRegistry.update3();

      for (ShaderSource shadersource : (Iterable<ShaderSource>)(ShaderRegistry.getCollection())) {
         try {
            this.onConsumerShaderSource(consumer, shadersource);
         } catch (Throwable throwable) {
         }
      }
   }

   private void onConsumerShaderSource(Consumer<ResourceLoader> consumer, ShaderSource shaderSource) {
      String s = shaderSource.getText();
      String s1 = shaderSource.getString();
      if ("post_effect".equals(s1)) {
         String s7 = "post_effect/" + s + ".json";
         byte[] abyte = shaderSource.getByteArray3();
         String s3 = s7;
         consumer.accept(ResourceLoader.getResourceLoaderByByteArrayString(abyte, s3));
      } else {
         String s2 = "shaders/core/" + s;
         String s8 = s2 + ".json";
         byte[] abyte1 = shaderSource.getByteArray3();
         String s4 = s8;
         consumer.accept(ResourceLoader.getResourceLoaderByByteArrayString(abyte1, s4));
         if (shaderSource.getText3() != null && !shaderSource.getText3().isEmpty()) {
            s8 = s2 + ".fsh";
            byte[] abyte2 = shaderSource.getByteArray();
            String s5 = s8;
            consumer.accept(ResourceLoader.getResourceLoaderByByteArrayString(abyte2, s5));
         }

         if (shaderSource.getText4() != null && !shaderSource.getText4().isEmpty()) {
            s8 = s2 + ".vsh";
            byte[] abyte3 = shaderSource.getByteArray2();
            String s6 = s8;
            consumer.accept(ResourceLoader.getResourceLoaderByByteArrayString(abyte3, s6));
         }
      }
   }
}
