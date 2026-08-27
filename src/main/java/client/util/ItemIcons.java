package client.util;

import client.data.GlyphBuffer;
import client.data.IconTexture;
import client.module.Feature;
import client.render.RoundedTextureShader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public final class ItemIcons {
   private static final Identifier identifier = Identifier.ofVanilla("__missing__");
   private static final Map<Item, Identifier> map = new HashMap<>();
   private static int value;
   private static final LinkedHashMap<IconTexture, GlyphBuffer> linkedHashMap = new LinkedHashMap<>();

   private ItemIcons() {
   }

   public static void update() {
      value++;
   }

   private static Identifier getIdentifierByItem(Item item2) {
      if (item2 instanceof BlockItem) {
         return null;
      } else {
         Identifier identifierx = Registries.ITEM.getId(item2);
         if (identifierx == null) {
            return null;
         } else {
            Identifier identifier1 = Identifier.of(identifierx.getNamespace(), "textures/item/" + identifierx.getPath() + ".png");
            if (!isIdentifier(identifier1)) {
               return null;
            } else {
               return isItem(item2) ? null : identifier1;
            }
         }
      }
   }

   public static void update2() {
      if (value > 0 && --value == 0) {
         update3();
      }
   }

   private static void update3() {
      for (Entry entry : linkedHashMap.entrySet()) {
         GlyphBuffer glyphbuffer = (GlyphBuffer)entry.getValue();
         if (glyphbuffer.value != 0) {
            Identifier identifier1 = ((IconTexture)entry.getKey()).getTexture();
            float f2 = ((IconTexture)entry.getKey()).getSize();
            float f3 = ((IconTexture)entry.getKey()).getSize();
            int i = glyphbuffer.value;
            float[] afloat = glyphbuffer.floatArray;
            float f1 = f3;
            float f = f2;
            Identifier identifierx = identifier1;
            RoundedTextureShader.onIntIdentifierFloatArrayFloatFloat(i, identifierx, afloat, f1, f);
            glyphbuffer.value = 0;
         }
      }
   }

   private static void onMatrix4fIntIdentifierFloatFloatFloat(Matrix4f matrix4f, int count, Identifier identifier, float value2, float value3, float value4) {
      GlyphBuffer glyphbuffer = linkedHashMap.computeIfAbsent(new IconTexture(identifier, value3), var0x -> new GlyphBuffer());
      if (glyphbuffer.value + 24 > glyphbuffer.floatArray.length) {
         glyphbuffer.floatArray = Arrays.copyOf(glyphbuffer.floatArray, glyphbuffer.floatArray.length * 2);
      }

      float f1 = 0.0F;
      float f = 0.0F;
      onGlyphBufferFloatFloatFloatIntFloatMatrix4f(glyphbuffer, value4, value2, f1, count, f, matrix4f);
      float f13 = value2 + value3;
      float f4 = 1.0F;
      float f3 = 0.0F;
      float f2 = f13;
      onGlyphBufferFloatFloatFloatIntFloatMatrix4f(glyphbuffer, value4, f2, f4, count, f3, matrix4f);
      float f12 = value4 + value3;
      f13 = value2 + value3;
      float f8 = 1.0F;
      float f7 = 1.0F;
      float f6 = f13;
      float f5 = f12;
      onGlyphBufferFloatFloatFloatIntFloatMatrix4f(glyphbuffer, f5, f6, f8, count, f7, matrix4f);
      f12 = value4 + value3;
      float f11 = 0.0F;
      float f10 = 1.0F;
      float f9 = f12;
      onGlyphBufferFloatFloatFloatIntFloatMatrix4f(glyphbuffer, f9, value2, f11, count, f10, matrix4f);
   }

   private static int getIntByFloatInt(float value, int count) {
      int i = Math.round((count >>> 24 & 0xFF) * Math.clamp(value, 0.0F, 1.0F));
      return count & 16777215 | i << 24;
   }

   private static void onGlyphBufferFloatFloatFloatIntFloatMatrix4f(GlyphBuffer glyphBuffer, float value2, float value3, float value4, int count, float value5, Matrix4f matrix4f) {
      int i = glyphBuffer.value;
      float[] afloat = glyphBuffer.floatArray;
      afloat[i] = matrix4f.m00() * value2 + matrix4f.m10() * value3 + matrix4f.m30();
      afloat[i + 1] = matrix4f.m01() * value2 + matrix4f.m11() * value3 + matrix4f.m31();
      afloat[i + 2] = matrix4f.m02() * value2 + matrix4f.m12() * value3 + matrix4f.m32();
      afloat[i + 3] = value5;
      afloat[i + 4] = value4;
      afloat[i + 5] = Float.intBitsToFloat(count);
      glyphBuffer.value = i + 6;
   }

   private static boolean isItem(Item item2) {
      Identifier identifierx = Registries.ITEM.getId(item2);
      if (identifierx == null) {
         return false;
      } else {
         String s = identifierx.getPath();
         return s.equals("totem_of_undying")
            || s.equals("elytra")
            || s.equals("trident")
            || s.equals("mace")
            || s.equals("crossbow")
            || s.equals("bow")
            || s.equals("fishing_rod")
            || s.equals("compass")
            || s.equals("clock")
            || s.equals("shield")
            || s.equals("potion")
            || s.equals("splash_potion")
            || s.equals("lingering_potion")
            || s.equals("tipped_arrow");
      }
   }

   public static void update4() {
      map.clear();
   }

   public static boolean isFloatMatrix4fFloatFloatFloatItemStack(float value2, Matrix4f matrix4f, float value3, float value4, float value5, ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && !(value3 <= 0.001F) && !(value4 <= 0.0F)) {
         Identifier identifierx = getIdentifierByItem2(itemStack.getItem());
         if (identifierx == null) {
            return false;
         } else {
            byte b0 = -1;
            int i = getIntByFloatInt(value3, b0);
            if (value > 0) {
               onMatrix4fIntIdentifierFloatFloatFloat(matrix4f, i, identifierx, value5, value4, value2);
               return true;
            } else {
               float f5 = 1.0F;
               float f4 = 0.0F;
               float f3 = 1.0F;
               float f2 = 1.0F;
               float f1 = 0.0F;
               float f = 0.0F;
               RoundedTextureShader.onFloatMatrix4fFloatFloatFloatFloatFloatIntFloatIdentifierFloatFloatFloat(
                  f4, matrix4f, f5, f1, f, value4, value4, i, f2, identifierx, value5, value2, f3
               );
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public static Identifier getIdentifierByItem2(Item item2) {
      if (item2 == null) {
         return null;
      } else {
         Identifier identifierx = map.get(item2);
         if (identifierx != null) {
            return identifierx == identifier ? null : identifierx;
         } else {
            Identifier identifier1 = getIdentifierByItem(item2);
            map.put(item2, identifier1 == null ? identifier : identifier1);
            return identifier1;
         }
      }
   }

   private static boolean isIdentifier(Identifier identifier) {
      try {
         ResourceManager resourcemanager = Feature.mc != null ? Feature.mc.getResourceManager() : null;
         return resourcemanager == null ? false : resourcemanager.getResource(identifier).isPresent();
      } catch (Throwable throwable) {
         return false;
      }
   }
}
