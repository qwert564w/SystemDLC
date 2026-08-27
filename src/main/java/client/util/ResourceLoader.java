package client.util;

import client.module.Feature;
import java.io.ByteArrayInputStream;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class ResourceLoader {
   private final Identifier identifier;
   private final byte[] byteArray;

   public ResourceLoader(String text, byte[] valueArray) {
      this.identifier = Identifier.ofVanilla(text);
      this.byteArray = valueArray;
   }

   public Identifier getIdentifier() {
      return this.identifier;
   }

   public static ResourceLoader getResourceLoaderByByteArrayString(byte[] valueArray, String text) {
      return new ResourceLoader(text, valueArray);
   }

   public Resource getResource() {
      return new Resource(Feature.mc.getDefaultResourcePack(), () -> new ByteArrayInputStream(this.byteArray));
   }
}
