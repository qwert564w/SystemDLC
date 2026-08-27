package client.render;

import client.concurrent.ShaderPackStore;
import client.data.AtlasInfo;
import client.data.FontData;
import client.data.GlyphInfo;
import client.data.StringPool;
import client.module.Feature;
import client.util.ResourceLoader;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.NativeImage.Format;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class IconAtlasLoader {
   private String text;
   private Identifier identifier;
   private String text2;

   public IconAtlasLoader() {
   }

   public IconAtlasLoader getIconAtlasLoaderByString(String text) {
      this.text2 = text;
      this.identifier = null;
      return this;
   }

   public IconAtlas getIconAtlas() {
      if (this.text != null && !this.text.isEmpty()) {
         StringPool stringpool = StringPool.getStringPool();
         FontData fontdata = null;
         String s = this.text + ".json";

         try {
            Map map = stringpool.getMap();
            if (map != null) {
               ResourceLoader resourceloader = (ResourceLoader)map.get(s);
               if (resourceloader != null) {
                  Resource resource = resourceloader.getResource();
                  if (resource != null) {
                     try (InputStream inputstream = resource.getInputStream()) {
                        if (inputstream != null) {
                           String s1 = new String(inputstream.readAllBytes(), StandardCharsets.UTF_8);
                           fontdata = (FontData)ShaderCache.getObjectByStringClass(s1, FontData.class);
                        }
                     }
                  }
               }
            }
         } catch (Exception exception) {
            return null;
         }

         if (fontdata == null) {
            return null;
         } else if (fontdata.getGlyphMetrics() == null) {
            return null;
         } else {
            Object object;
            if (this.text2 != null) {
               try {
                  byte[] abyte = ShaderPackStore.getByteArrayByString(this.text);

                  NativeImage nativeimage;
                  try (ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte)) {
                     nativeimage = NativeImage.read(Format.RGBA, bytearrayinputstream);
                  }

                  object = new NativeImageBackedTexture(nativeimage);
               } catch (IOException ioexception) {
                  if (this.identifier == null) {
                     return null;
                  }

                  if (Feature.mc == null) {
                     return null;
                  }

                  object = Feature.mc.getTextureManager().getTexture(this.identifier);
                  if (object == null) {
                     return null;
                  }
               }
            } else {
               if (this.identifier == null) {
                  return null;
               }

               if (Feature.mc == null) {
                  return null;
               }

               object = Feature.mc.getTextureManager().getTexture(this.identifier);
               if (object == null) {
                  return null;
               }
            }

            AbstractTexture object1 = (AbstractTexture)object;
            RenderSystem.recordRenderCall(() -> object1.setFilter(true, false));
            float f = fontdata.getGlyphMetrics().getValue2();
            float f1 = fontdata.getGlyphMetrics().getValue3();
            HashMap hashmap1 = new HashMap();
            if (fontdata.getList() != null) {
               hashmap1.putAll(
                  fontdata.getList()
                     .stream()
                     .filter(Objects::nonNull)
                     .collect(Collectors.toMap(GlyphInfo::getValue, var2x -> new AtlasSprite(var2x, f, f1), (item, item2) -> item2))
               );
            }

            HashMap hashmap = new HashMap();
            if (fontdata.getList2() != null) {
               fontdata.getList2().forEach(item2 -> {
                  if (item2 != null) {
                     Float fx = item2.getValue3();
                     Map<Object, Float> mapx = (Map<Object, Float>)hashmap.computeIfAbsent(item2.getValue(), var0x -> new HashMap<>());
                     mapx.put(item2.getValue2(), fx);
                  }
               });
            }

            return new IconAtlas((AbstractTexture)object, fontdata.getGlyphMetrics(), fontdata.getFontMetrics(), hashmap1, hashmap);
         }
      } else {
         return null;
      }
   }

   public IconAtlasLoader getIconAtlasLoaderByString2(String text2) {
      this.text = text2;
      return this;
   }
}
