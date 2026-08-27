package client.render;

import client.data.FontRef;
import client.data.GlyphKey;
import client.data.VertexBatch;
import client.util.MathUtil;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public class TextShader {
   private static final float value = 0.02F;
   private static final float value2 = 0.03F;
   private static final float value3 = 0.04F;
   private static final float value4 = 12.0F;
   private static final float value5 = 18.0F;
   private static final float value6 = 0.0F;
   private static final ShaderProgramKey shaderProgramKey = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "text", VertexFormats.POSITION_TEXTURE_COLOR, Defines.EMPTY
   );
   private static final ShaderProgramKey shaderProgramKey2 = ShaderCache.getShaderProgramKeyByStringVertexFormatDefines(
      "text_blur", VertexFormats.POSITION_TEXTURE_COLOR, Defines.EMPTY
   );
   private static final Supplier<IconAtlas> supplier = getSupplierByString("a");
   private static final Supplier<IconAtlas> supplier2 = getSupplierByString("aa");
   private static final Supplier<IconAtlas> supplier3 = getSupplierByString("b");
   private static final Supplier<IconAtlas> supplier4 = getSupplierByString("bb");
   private static final Supplier<IconAtlas> supplier5 = getSupplierByString("c");
   private static final Supplier<IconAtlas> supplier6 = getSupplierByString("cc");
   private static final int value7 = 1024;
   private static final Map<GlyphKey, Float> map = new HashMap<>(256);
   private static IconAtlas iconAtlas;
   private static String text;
   private static int value8;
   private static float value9;
   private static int value10;
   private static final LinkedHashMap<FontRef, VertexBatch> linkedHashMap = new LinkedHashMap<>();
   private static final VertexBatch vertexBatch = new VertexBatch();
   private static final TextVertexConsumer textVertexConsumer = new TextVertexConsumer();

   public static void onMatrix4fStringFloatFloatFloatIntFloat(Matrix4f matrix4f, String text, float value, float value2, float value3, int count, float value4) {
      IconAtlas iconatlas1 = supplier2.get();
      float f = getFloatByFloat4(value3);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, value4, value, count, matrix4f, value2, text, value3, iconatlas);
   }

   private static float getFloatByFloat(float value) {
      float f = 0.03F;
      return getFloatByFloatFloat(value, f);
   }

   public static void update() {
      if (value10 > 0 && --value10 == 0) {
         update2();
      }
   }

   public static void update2() {
      for (Entry entry : linkedHashMap.entrySet()) {
         VertexBatch vertexbatch = (VertexBatch)entry.getValue();
         if (vertexbatch.value != 0) {
            ShaderProgramKey shaderprogramkey1 = shaderProgramKey;
            IconAtlas iconatlas1 = ((FontRef)entry.getKey()).getFont();
            float f2 = ((FontRef)entry.getKey()).getWeight();
            int i = vertexbatch.value;
            float[] afloat = vertexbatch.floatArray;
            float f1 = 0.0F;
            float f = f2;
            IconAtlas iconatlas = iconatlas1;
            ShaderProgramKey shaderprogramkey = shaderprogramkey1;
            onFloatIntFloatIconAtlasFloatArrayShaderProgramKey(f, i, f1, iconatlas, afloat, shaderprogramkey);
            vertexbatch.value = 0;
         }
      }
   }

   private static float getFloatByFloat2(float value) {
      float f = 0.04F;
      return getFloatByFloatFloat(value, f);
   }

   public static void onMatrix4fStringFloatFloatFloatIntFloat2(Matrix4f matrix4f, String text, float value, float value2, float value3, int count, float value4) {
      IconAtlas iconatlas1 = supplier6.get();
      float f = getFloatByFloat2(value3);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, value4, value, count, matrix4f, value2, text, value3, iconatlas);
   }

   public static float getFloatByFloat3(float value) {
      return value * 1.2F;
   }

   public static void onFloatFloatFloatStringArrayFloatIntMatrix4fFloat(
      float value, float value2, float value3, String[] textArray, float value4, int count, Matrix4f matrix4f, float value5
   ) {
      IconAtlas iconatlas1 = supplier.get();
      float f = getFloatByFloat4(value4);
      IconAtlas iconatlas = iconatlas1;
      onFloatIconAtlasMatrix4fFloatFloatStringArrayIntFloatFloatFloat(value3, iconatlas, matrix4f, value, value5, textArray, count, f, value4, value2);
   }

   private static boolean isStringIconAtlas(String text, IconAtlas iconAtlas) {
      return iconAtlas != null && text != null && !text.isEmpty() ? iconAtlas.getGlyphMetrics() != null && iconAtlas.getFontMetrics() != null && iconAtlas.getInt() != 0 : false;
   }

   public static void onIntFloatStringArrayMatrix4fFloatFloatFloatFloat(
      int count, float value, String[] textArray, Matrix4f matrix4f, float value2, float value3, float value4, float value5
   ) {
      IconAtlas iconatlas1 = supplier3.get();
      float f = getFloatByFloat(value5);
      IconAtlas iconatlas = iconatlas1;
      onFloatIconAtlasMatrix4fFloatFloatStringArrayIntFloatFloatFloat(value3, iconatlas, matrix4f, value4, value2, textArray, count, f, value5, value);
   }

   public static float getFloatByFloatFloatStringArray(float value, float value2, String[] textArray) {
      IconAtlas iconatlas = supplier.get();
      List list = getListByFloatStringArrayIconAtlasFloat(value2, textArray, iconatlas, value);
      return list.isEmpty() ? 0.0F : list.size() * getFloatByFloat3(value2);
   }

   public static float getFloatByFloatStringArray(float value, String[] textArray) {
      IconAtlas iconatlas = supplier.get();
      float f;
      if (iconatlas != null && textArray != null) {
         int i = textArray.length;
         byte b0 = 0;
         f = iconatlas.getFloatByStringArrayIntFloatInt(textArray, i, value, b0);
      } else {
         f = 0.0F;
      }

      return f;
   }

   private static void onIconAtlasFloatFloatFloatMatrix4fIntFloatStringFloatFloat(
      IconAtlas iconAtlas, float value, float value2, float value3, Matrix4f matrix4f, int count, float value4, String text, float value5, float value6
   ) {
      List list = getListByFloatIconAtlasStringFloat(value, iconAtlas, text, value4);

      for (int i = 0; i < list.size(); i++) {
         String s1 = (String)list.get(i);
         float f = value3 + i * getFloatByFloat3(value);
         String s = s1;
         onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(value2, value5, value6, count, matrix4f, f, s, value, iconAtlas);
      }
   }

   private static void onFloatIntFloatIconAtlasFloatArrayShaderProgramKey(float value, int count, float value2, IconAtlas iconAtlas, float[] valueArray, ShaderProgramKey shaderProgramKey) {
      if (count > 0) {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.disableCull();

         try {
            RenderSystem.setShaderTexture(0, iconAtlas.getInt());
            ShaderProgram shaderprogram = RenderSystem.setShader(shaderProgramKey);
            if (shaderprogram != null) {
               float f1 = iconAtlas.getGlyphMetrics().getValue3();
               float f = iconAtlas.getGlyphMetrics().getValue2();
               String s = "AtlasSize";
               ShaderUniforms.onStringFloatFloatShaderProgram(s, f1, f, shaderprogram);
               float f2 = iconAtlas.getGlyphMetrics().getValue();
               String s1 = "DistanceRange";
               ShaderUniforms.onFloatShaderProgramString(f2, shaderprogram, s1);
               String s2 = "Weight";
               ShaderUniforms.onFloatShaderProgramString(value, shaderprogram, s2);
               if (shaderProgramKey == shaderProgramKey2) {
                  String s3 = "Blur";
                  ShaderUniforms.onFloatShaderProgramString(value2, shaderprogram, s3);
               }

               BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

               for (int b0 = 0; b0 < count; b0 += 6) {
                  bufferbuilder.vertex(valueArray[b0], valueArray[b0 + 1], valueArray[b0 + 2]).texture(valueArray[b0 + 3], valueArray[b0 + 4]).color(Float.floatToRawIntBits(valueArray[b0 + 5]));
               }

               BuiltBuffer builtbuffer = bufferbuilder.endNullable();
               if (builtbuffer != null) {
                  BufferRenderer.drawWithGlobalProgram(builtbuffer);
               }

               return;
            }
         } finally {
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void onFloatStringVertexBatchFloatMatrix4fIconAtlasFloatIntFloat(
      float value2, String text, VertexBatch vertexBatch2, float value3, Matrix4f matrix4f, IconAtlas iconAtlas, float value4, int count, float value5
   ) {
      int i = MathUtil.getIntByFloatInt2(value5, count);
      double d0 = getDouble();
      float f = getFloatByFloatDouble(value3, d0);
      float f1 = getFloatByFloatDouble(value4 + iconAtlas.getFontMetrics().getFloat() * value2 - 1.0F, d0);
      int j = vertexBatch2.value + text.length() * 24;
      if (j > vertexBatch2.floatArray.length) {
         vertexBatch2.floatArray = Arrays.copyOf(vertexBatch2.floatArray, Math.max(j, vertexBatch2.floatArray.length * 2));
      }

      textVertexConsumer.vertexBatch = vertexBatch2;
      textVertexConsumer.value = i;

      try {
         float f3 = 0.0F;
         float f2 = 0.0F;
         TextVertexConsumer textvertexconsumer = textVertexConsumer;
         iconAtlas.onFloatVertexConsumerFloatMatrix4fFloatFloatStringFloat(f, textvertexconsumer, value2, matrix4f, f3, f2, text, f1);
      } finally {
         textVertexConsumer.vertexBatch = null;
      }
   }

   private static List getListByFloatIconAtlasStringFloat(float value, IconAtlas iconAtlas, String text, float value2) {
      ArrayList arraylist = new ArrayList();
      if (iconAtlas == null || text == null || text.isBlank()) {
         return arraylist;
      } else if (value2 <= 0.0F) {
         arraylist.add(text);
         return arraylist;
      } else {
         String s = " ";
         float f = getFloatByFloatStringIconAtlas(value, s, iconAtlas);
         StringBuilder stringbuilder = new StringBuilder();
         float f1 = 0.0F;
         int i = text.length();
         int j = 0;
         boolean flag = true;

         while (j <= i) {
            char c0 = j < i ? text.charAt(j) : 10;
            if (c0 == '\r') {
               j++;
            } else if (c0 != '\n' && j != i) {
               if (c0 != ' ' && c0 != '\t') {
                  int k = j;

                  while (true) {
                     if (j < i) {
                        char c1 = text.charAt(j);
                        if (c1 != ' ' && c1 != '\t' && c1 != '\n' && c1 != '\r') {
                           j++;
                           continue;
                        }
                     }

                     String s1 = text.substring(k, j);
                     float f2 = getFloatByFloatStringIconAtlas(value, s1, iconAtlas);
                     if (f2 > value2) {
                        if (!stringbuilder.isEmpty()) {
                           arraylist.add(stringbuilder.toString());
                           stringbuilder.setLength(0);
                           f1 = 0.0F;
                        }

                        onIconAtlasListFloatFloatString(iconAtlas, arraylist, value2, value, s1);
                        flag = false;
                     } else {
                        float f3 = stringbuilder.isEmpty() ? 0.0F : f;
                        if (f1 + f3 + f2 <= value2) {
                           if (f3 > 0.0F) {
                              stringbuilder.append(' ');
                           }

                           stringbuilder.append(s1);
                           f1 += f3 + f2;
                        } else {
                           if (!stringbuilder.isEmpty()) {
                              arraylist.add(stringbuilder.toString());
                           }

                           stringbuilder.setLength(0);
                           stringbuilder.append(s1);
                           f1 = f2;
                        }

                        flag = false;
                     }
                     break;
                  }
               } else {
                  j++;
               }
            } else {
               if (!stringbuilder.isEmpty() || !flag) {
                  arraylist.add(stringbuilder.isEmpty() ? "" : stringbuilder.toString());
               }

               stringbuilder.setLength(0);
               f1 = 0.0F;
               flag = false;
               if (j == i) {
                  break;
               }

               j++;
            }
         }

         return arraylist;
      }
   }

   private static void onIconAtlasListFloatFloatString(IconAtlas iconAtlas, List list, float value, float value2, String text) {
      StringBuilder stringbuilder = new StringBuilder();
      float f = 0.0F;

      for (int i = 0; i < text.length(); i++) {
         char c0 = text.charAt(i);
         String s = String.valueOf(c0);
         float f1 = getFloatByFloatStringIconAtlas(value2, s, iconAtlas);
         if (!stringbuilder.isEmpty() && f + f1 > value) {
            list.add(stringbuilder.toString());
            stringbuilder.setLength(0);
            f = 0.0F;
         }

         stringbuilder.append(c0);
         f += f1;
      }

      if (!stringbuilder.isEmpty()) {
         list.add(stringbuilder.toString());
      }
   }

   private static void onFloatFloatFloatIntFloatIntVertexBatchMatrix4fStringArrayIntIconAtlas(
      float value2, float value3, float value4, int count, float value5, int count2, VertexBatch vertexBatch2, Matrix4f matrix4f, String[] textArray, int count3, IconAtlas iconAtlas
   ) {
      int i = MathUtil.getIntByFloatInt2(value4, count);
      double d0 = getDouble();
      float f = getFloatByFloatDouble(value3, d0);
      float f1 = getFloatByFloatDouble(value2 + iconAtlas.getFontMetrics().getFloat() * value5 - 1.0F, d0);
      int j = vertexBatch2.value + (count2 - count3) * 24;
      if (j > vertexBatch2.floatArray.length) {
         vertexBatch2.floatArray = Arrays.copyOf(vertexBatch2.floatArray, Math.max(j, vertexBatch2.floatArray.length * 2));
      }

      textVertexConsumer.vertexBatch = vertexBatch2;
      textVertexConsumer.value = i;

      try {
         float f3 = 0.0F;
         float f2 = 0.0F;
         TextVertexConsumer textvertexconsumer = textVertexConsumer;
         iconAtlas.onVertexConsumerStringArrayFloatFloatMatrix4fIntFloatFloatIntFloat(textvertexconsumer, textArray, value5, f1, matrix4f, count2, f3, f, count3, f2);
      } finally {
         textVertexConsumer.vertexBatch = null;
      }
   }

   private static List getListByFloatStringArrayIconAtlasFloat(float value, String[] textArray, IconAtlas iconAtlas, float value2) {
      ArrayList arraylist = new ArrayList();
      if (iconAtlas != null && textArray != null && textArray.length != 0) {
         int i = textArray.length;
         if (value2 <= 0.0F) {
            arraylist.add(new int[]{0, i});
            return arraylist;
         } else {
            String s = " ";
            float f = getFloatByFloatStringIconAtlas(value, s, iconAtlas);
            int j = -1;
            int k = -1;
            float f1 = 0.0F;
            int l = 0;
            boolean flag = true;

            while (l <= i) {
               int i1 = l < i ? getIntByIntStringArray(l, textArray) : 10;
               if (i1 == 13) {
                  l++;
               } else if (i1 != 10 && l != i) {
                  if (i1 != 32 && i1 != 9) {
                     int j1 = l;

                     while (true) {
                        if (l < i) {
                           int k1 = getIntByIntStringArray(l, textArray);
                           if (k1 != 32 && k1 != 9 && k1 != 10 && k1 != 13) {
                              l++;
                              continue;
                           }
                        }

                        float f2 = iconAtlas.getFloatByStringArrayIntFloatInt(textArray, l, value, j1);
                        if (f2 > value2) {
                           if (j >= 0) {
                              arraylist.add(new int[]{j, k});
                              j = -1;
                              k = -1;
                              f1 = 0.0F;
                           }

                           onIconAtlasFloatIntFloatListIntStringArray(iconAtlas, value2, l, value, arraylist, j1, textArray);
                           flag = false;
                        } else {
                           float f3 = j < 0 ? 0.0F : f;
                           if (f1 + f3 + f2 <= value2) {
                              if (j < 0) {
                                 j = j1;
                              }

                              k = l;
                              f1 += f3 + f2;
                           } else {
                              if (j >= 0) {
                                 arraylist.add(new int[]{j, k});
                              }

                              j = j1;
                              k = l;
                              f1 = f2;
                           }

                           flag = false;
                        }
                        break;
                     }
                  } else {
                     l++;
                  }
               } else {
                  if (j >= 0 || !flag) {
                     arraylist.add(j >= 0 ? new int[]{j, k} : new int[]{l, l});
                  }

                  j = -1;
                  k = -1;
                  f1 = 0.0F;
                  flag = false;
                  if (l == i) {
                     break;
                  }

                  l++;
               }
            }

            return arraylist;
         }
      } else {
         return arraylist;
      }
   }

   private static void onIconAtlasFloatIntFloatListIntStringArray(IconAtlas iconAtlas, float value, int count, float value2, List list, int count2, String[] textArray) {
      int i = -1;
      float f = 0.0F;

      for (int j = count2; j < count; j++) {
         int k = j + 1;
         float f1 = iconAtlas.getFloatByStringArrayIntFloatInt(textArray, k, value2, j);
         if (i >= 0 && f + f1 > value) {
            list.add(new int[]{i, j});
            i = -1;
            f = 0.0F;
         }

         if (i < 0) {
            i = j;
         }

         f += f1;
      }

      if (i >= 0) {
         list.add(new int[]{i, count});
      }
   }

   public static void update3() {
      value10++;
   }

   public static float getFloatByIntStringArrayFloatInt(int count, String[] textArray, float value, int count2) {
      IconAtlas iconatlas = supplier.get();
      return iconatlas != null && textArray != null ? iconatlas.getFloatByStringArrayIntFloatInt(textArray, count2, value, count) : 0.0F;
   }

   public static void onIntFloatStringArrayFloatMatrix4fIntIntFloatFloat(
      int count, float value, String[] textArray, float value2, Matrix4f matrix4f, int count2, int count3, float value3, float value4
   ) {
      IconAtlas iconatlas1 = supplier3.get();
      float f = getFloatByFloat(value3);
      IconAtlas iconatlas = iconatlas1;
      onIconAtlasFloatFloatFloatFloatStringArrayFloatMatrix4fIntIntInt(iconatlas, value4, value2, value, value3, textArray, f, matrix4f, count3, count2, count);
   }

   public static int getIntByFloatFloatStringArray(float value, float value2, String[] textArray) {
      IconAtlas iconatlas = supplier.get();
      if (iconatlas != null && textArray != null) {
         for (int i = textArray.length; i > 0; i--) {
            byte b0 = 0;
            if (iconatlas.getFloatByStringArrayIntFloatInt(textArray, i, value, b0) <= value2) {
               return i;
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   private static int getIntByIntStringArray(int count, String[] textArray) {
      String s = textArray[count];
      return s != null && !s.isEmpty() ? s.charAt(0) : -1;
   }

   private static void onFloatIconAtlasMatrix4fFloatFloatStringArrayIntFloatFloatFloat(
      float value, IconAtlas iconAtlas, Matrix4f matrix4f, float value2, float value3, String[] textArray, int count, float value4, float value5, float value6
   ) {
      List list = getListByFloatStringArrayIconAtlasFloat(value5, textArray, iconAtlas, value);

      for (int i = 0; i < list.size(); i++) {
         int[] aint = (int[])list.get(i);
         int l = aint[0];
         int i1 = aint[1];
         float f = value2 + i * getFloatByFloat3(value5);
         int k = i1;
         int j = l;
         onIconAtlasFloatFloatFloatFloatStringArrayFloatMatrix4fIntIntInt(iconAtlas, f, value3, value6, value5, textArray, value4, matrix4f, count, k, j);
      }
   }

   private static void onIconAtlasFloatFloatFloatFloatStringArrayFloatMatrix4fIntIntInt(
      IconAtlas iconAtlas, float value2, float value3, float value4, float value5, String[] textArray, float value6, Matrix4f matrix4f, int count, int count2, int count3
   ) {
      if (iconAtlas != null && textArray != null && count3 < count2) {
         if (iconAtlas.getGlyphMetrics() != null && iconAtlas.getFontMetrics() != null && iconAtlas.getInt() != 0) {
            if (value10 > 0) {
               VertexBatch vertexbatch = linkedHashMap.computeIfAbsent(new FontRef(iconAtlas, value6), var0x -> new VertexBatch());
               onFloatFloatFloatIntFloatIntVertexBatchMatrix4fStringArrayIntIconAtlas(value2, value3, value4, count, value5, count2, vertexbatch, matrix4f, textArray, count3, iconAtlas);
            } else {
               VertexBatch vertexbatch1 = vertexBatch;
               onFloatFloatFloatIntFloatIntVertexBatchMatrix4fStringArrayIntIconAtlas(value2, value3, value4, count, value5, count2, vertexbatch1, matrix4f, textArray, count3, iconAtlas);
               int i = vertexBatch.value;
               float[] afloat = vertexBatch.floatArray;
               float f = 0.0F;
               ShaderProgramKey shaderprogramkey = shaderProgramKey;
               onFloatIntFloatIconAtlasFloatArrayShaderProgramKey(value6, i, f, iconAtlas, afloat, shaderprogramkey);
               vertexBatch.value = 0;
            }
         }
      }
   }

   public static void onFloatFloatIntFloatFloatStringMatrix4f(float value, float value2, int count, float value3, float value4, String text, Matrix4f matrix4f) {
      IconAtlas iconatlas1 = supplier3.get();
      float f = getFloatByFloat(value3);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, value4, value2, count, matrix4f, value, text, value3, iconatlas);
   }

   public static void onMatrix4fFloatFloatIntFloatStringFloatFloat(
      Matrix4f matrix4f, float value, float value2, int count, float value3, String text, float value4, float value5
   ) {
      IconAtlas iconatlas1 = supplier2.get();
      float f = getFloatByFloat4(value4);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatStringFloatFloatFloatFloatIntMatrix4fIconAtlas(value2, value, text, f, value3, value5, value4, count, matrix4f, iconatlas);
   }

   public static void onIntStringFloatMatrix4fFloatFloatFloatFloat(
      int count, String text, float value, Matrix4f matrix4f, float value2, float value3, float value4, float value5
   ) {
      IconAtlas iconatlas1 = supplier.get();
      float f = getFloatByFloat4(value4);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatStringFloatFloatFloatFloatIntMatrix4fIconAtlas(value3, value2, text, f, value, value5, value4, count, matrix4f, iconatlas);
   }

   public static void onFloatStringFloatFloatIntFloatMatrix4f(float value, String text, float value2, float value3, int count, float value4, Matrix4f matrix4f) {
      IconAtlas iconatlas1 = supplier4.get();
      float f = getFloatByFloat(value2);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, value, value3, count, matrix4f, value4, text, value2, iconatlas);
   }

   public static float getFloatByFloatString(float value, String text) {
      IconAtlas iconatlas = supplier2.get();
      return getFloatByFloatStringIconAtlas(value, text, iconatlas);
   }

   public static void onFloatStringFloatFloatMatrix4fFloatFloatInt(
      float value, String text, float value2, float value3, Matrix4f matrix4f, float value4, float value5, int count
   ) {
      IconAtlas iconatlas1 = supplier.get();
      float f = getFloatByFloat4(value3);
      IconAtlas iconatlas = iconatlas1;
      onIconAtlasFloatFloatFloatMatrix4fIntFloatStringFloatFloat(iconatlas, value3, f, value5, matrix4f, count, value4, text, value, value2);
   }

   private static double getDouble() {
      MinecraftClient minecraftclient = MinecraftClient.getInstance();
      if (minecraftclient != null && minecraftclient.getWindow() != null) {
         double d0 = minecraftclient.getWindow().getScaleFactor();
         return d0 > 0.0 ? d0 : 1.0;
      } else {
         return 1.0;
      }
   }

   private static float getFloatByFloatDouble(float value, double value2) {
      return (float)(Math.round(value * value2) / value2);
   }

   private static Supplier getSupplierByString(String text) {
      return Suppliers.memoize(() -> IconAtlas.getIconAtlasLoader().getIconAtlasLoaderByString2(text).getIconAtlasLoaderByString(text).getIconAtlas());
   }

   private static float getFloatByFloatStringIconAtlas(float value, String text2, IconAtlas iconAtlas2) {
      if (iconAtlas2 != null && text2 != null && !text2.isEmpty()) {
         int i = Float.floatToRawIntBits(value);
         if (iconAtlas2 == iconAtlas && i == value8 && text2 == text) {
            return value9;
         } else {
            GlyphKey glyphkey = new GlyphKey(iconAtlas2, text2, i);
            Float f = map.get(glyphkey);
            if (f != null) {
               iconAtlas = iconAtlas2;
               text = text2;
               value8 = i;
               value9 = f;
               return f;
            } else {
               if (map.size() >= 1024) {
                  map.clear();
               }

               float f1 = iconAtlas2.getFloatByStringFloat(text2, value);
               map.put(glyphkey, f1);
               iconAtlas = iconAtlas2;
               text = text2;
               value8 = i;
               value9 = f1;
               return f1;
            }
         }
      } else {
         return 0.0F;
      }
   }

   public static void onMatrix4fStringFloatFloatFloatIntFloat3(Matrix4f matrix4f, String text, float value, float value2, float value3, int count, float value4) {
      IconAtlas iconatlas1 = supplier.get();
      float f = getFloatByFloat4(value3);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, value4, value, count, matrix4f, value2, text, value3, iconatlas);
   }

   public static void onIntFloatFloatMatrix4fFloatFloatString(int count, float value, float value2, Matrix4f matrix4f, float value3, float value4, String text) {
      IconAtlas iconatlas1 = supplier5.get();
      float f = getFloatByFloat2(value4);
      IconAtlas iconatlas = iconatlas1;
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, value, value3, count, matrix4f, value2, text, value4, iconatlas);
   }

   public static float getFloatByFloatIconAtlasString(float value, IconAtlas iconAtlas, String text) {
      return getFloatByFloatStringIconAtlas(value, text, iconAtlas);
   }

   public static float getFloatByFloatFloatString(float value, float value2, String text) {
      IconAtlas iconatlas = supplier.get();
      List list = getListByFloatIconAtlasStringFloat(value2, iconatlas, text, value);
      return list.isEmpty() ? 0.0F : list.size() * getFloatByFloat3(value2);
   }

   private static float getFloatByFloatFloat(float value, float value2) {
      float f = value * (float)getDouble();
      if (f <= 12.0F) {
         return 0.0F;
      } else if (f >= 18.0F) {
         return value2;
      } else {
         float f1 = (f - 12.0F) / 6.0F;
         return value2 * f1;
      }
   }

   private static float getFloatByFloat4(float value) {
      float f = 0.02F;
      return getFloatByFloatFloat(value, f);
   }

   private static void onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(
      float value2, float value3, float value4, int count, Matrix4f matrix4f, float value5, String text, float value6, IconAtlas iconAtlas
   ) {
      if (isStringIconAtlas(text, iconAtlas)) {
         if (value10 > 0) {
            VertexBatch vertexbatch = linkedHashMap.computeIfAbsent(new FontRef(iconAtlas, value2), var0x -> new VertexBatch());
            onFloatStringVertexBatchFloatMatrix4fIconAtlasFloatIntFloat(value6, text, vertexbatch, value4, matrix4f, iconAtlas, value5, count, value3);
         } else {
            VertexBatch vertexbatch1 = vertexBatch;
            onFloatStringVertexBatchFloatMatrix4fIconAtlasFloatIntFloat(value6, text, vertexbatch1, value4, matrix4f, iconAtlas, value5, count, value3);
            int i = vertexBatch.value;
            float[] afloat = vertexBatch.floatArray;
            float f = 0.0F;
            ShaderProgramKey shaderprogramkey = shaderProgramKey;
            onFloatIntFloatIconAtlasFloatArrayShaderProgramKey(value2, i, f, iconAtlas, afloat, shaderprogramkey);
            vertexBatch.value = 0;
         }
      }
   }

   private static void onFloatFloatStringFloatFloatFloatFloatIntMatrix4fIconAtlas(
      float value2, float value3, String text, float value4, float value5, float value6, float value7, int count, Matrix4f matrix4f, IconAtlas iconAtlas
   ) {
      if (isStringIconAtlas(text, iconAtlas)) {
         if (value5 <= 0.001F) {
            onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(value4, value2, value6, count, matrix4f, value3, text, value7, iconAtlas);
         } else {
            update2();
            VertexBatch vertexbatch = vertexBatch;
            onFloatStringVertexBatchFloatMatrix4fIconAtlasFloatIntFloat(value7, text, vertexbatch, value6, matrix4f, iconAtlas, value3, count, value2);
            ShaderProgramKey shaderprogramkey1 = shaderProgramKey2;
            float f1 = Math.clamp(value5, 0.0F, 1.0F);
            int i = vertexBatch.value;
            float[] afloat = vertexBatch.floatArray;
            float f = f1;
            ShaderProgramKey shaderprogramkey = shaderprogramkey1;
            onFloatIntFloatIconAtlasFloatArrayShaderProgramKey(value4, i, f, iconAtlas, afloat, shaderprogramkey);
            vertexBatch.value = 0;
         }
      }
   }

   public static void onFloatFloatIntFloatMatrix4fStringFloatFloat(
      float value, float value2, int count, float value3, Matrix4f matrix4f, String text, float value4, float value5
   ) {
      IconAtlas iconatlas1 = supplier3.get();
      float f = getFloatByFloat(value5);
      IconAtlas iconatlas = iconatlas1;
      onIconAtlasFloatFloatFloatMatrix4fIntFloatStringFloatFloat(iconatlas, value5, f, value2, matrix4f, count, value3, text, value4, value);
   }

   public static void onMatrix4fFloatFloatStringFloatIntFloatFloat(
      Matrix4f matrix4f, float value, float value2, String text, float value3, int count, float value4, float value5
   ) {
      IconAtlas iconatlas1 = supplier5.get();
      float f = getFloatByFloat2(value3);
      IconAtlas iconatlas = iconatlas1;
      onIconAtlasFloatFloatFloatMatrix4fIntFloatStringFloatFloat(iconatlas, value3, f, value5, matrix4f, count, value4, text, value, value2);
   }

   public static void onStringFloatMatrix4fIntFloatIconAtlasFloat(String text, float value, Matrix4f matrix4f, int count, float value2, IconAtlas iconAtlas, float value3) {
      float f2 = getFloatByFloat4(value);
      float f1 = 1.0F;
      float f = f2;
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, f1, value2, count, matrix4f, value3, text, value, iconAtlas);
   }

   public static void onIconAtlasFloatStringIntMatrix4fFloatFloatFloat(
      IconAtlas iconAtlas, float value, String text, int count, Matrix4f matrix4f, float value2, float value3, float value4
   ) {
      float f = getFloatByFloat4(value2);
      onFloatFloatFloatIntMatrix4fFloatStringFloatIconAtlas(f, value4, value, count, matrix4f, value3, text, value2, iconAtlas);
   }

   public static float getFloatByStringFloat(String text, float value) {
      IconAtlas iconatlas = supplier.get();
      return getFloatByFloatStringIconAtlas(value, text, iconatlas);
   }
}
