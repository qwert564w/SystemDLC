package client.util;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.NameTagAnimations;
import client.data.ThemeConfig;
import client.enums.ThemePalette;
import client.enums.VoiceIcon;
import client.module.CategoryType;
import client.module.Feature;
import client.render.GlyphQuad;
import client.render.ItemIconCache;
import client.render.RotationBuffer;
import client.render.RoundedTextureShader;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.render.WorldRenderContext;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class NameTagCache {
   private final Map<UUID, NameTagAnimations> map = new HashMap<>();
   private final Set<UUID> set = new HashSet<>();
   private final List<GlyphQuad> list = new ArrayList<>();
   private long time = System.currentTimeMillis();

   private static int getIntByFloatFloat(float value, float value2) {
      if (value <= 0.0F) {
         return Theme.success();
      } else {
         float f = Math.clamp(value2 / value, 0.0F, 1.0F);
         if (f >= 0.5F) {
            float f1 = (f - 0.5F) / 0.5F;
            int i1 = Theme.caution();
            int j = Theme.success();
            int i = i1;
            return AnimatedInt.getIntByIntFloatInt(j, f1, i);
         } else {
            int j1 = Theme.danger();
            int k1 = Theme.caution();
            float f2 = f / 0.5F;
            int l = k1;
            int k = j1;
            return AnimatedInt.getIntByIntFloatInt(l, f2, k);
         }
      }
   }

   private static void onMatrix4fIntFloatFloatItemStackFloat(Matrix4f matrix4f, int count, float value, float value2, ItemStack itemStack, float value3) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.getCount() > 1) {
         String s = Integer.toString(itemStack.getCount());
         float f3 = 8.0F;
         float f = TextShader.getFloatByFloatString(f3, s);
         float f1 = value3 + 16.0F - f;
         float f2 = value2 + 16.0F - 8.0F;
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat2(matrix4f, s, f1, f2, 8.0F, count, value);
      }
   }

   private void onNameTagEntryMatrix4fGlyphQuad(NameTagEntry nameTagEntry, Matrix4f matrix4f, GlyphQuad glyphQuad) {
      if (nameTagEntry.voiceIcon != null && nameTagEntry.voiceIcon.identifier != null) {
         float f = -glyphQuad.value16 / 2.0F;
         float f1 = -16.0F;
         float f2 = f + 6.0F + glyphQuad.value11;
         float f3 = f1 + 10.0F;
         float f7 = 1.0F;
         byte b0 = -1;
         float f6 = 0.0F;
         float f5 = 12.0F;
         float f4 = 12.0F;
         Identifier identifier = nameTagEntry.voiceIcon.identifier;
         RoundedTextureShader.onFloatFloatMatrix4fFloatIdentifierFloatFloatFloatInt(f4, f2, matrix4f, f5, identifier, f7, f3, f6, b0);
      }
   }

   private void onIntGlyphQuadMatrix4fNameTagEntry(int count, GlyphQuad glyphQuad, Matrix4f matrix4f, NameTagEntry nameTagEntry) {
      float f = -glyphQuad.value16 / 2.0F;
      float f1 = -16.0F;
      float f2 = f1 + 8.0F;
      float f3 = f + 6.0F;
      if (glyphQuad.value2 > 0.001F && glyphQuad.value5 > 0.001F) {
         float f7 = glyphQuad.value2 * glyphQuad.value5;
         ItemStack itemstack = nameTagEntry.itemStack;
         onMatrix4fIntFloatFloatItemStackFloat(matrix4f, count, f7, f2, itemstack, f3);
      }

      f3 += glyphQuad.value11 + glyphQuad.value14 + glyphQuad.value13 + (glyphQuad.flag ? glyphQuad.value8 + 6.0F : 0.0F) + glyphQuad.value9 + 6.0F + glyphQuad.value10 + 8.0F;
      if (glyphQuad.value4 > 0.001F && glyphQuad.value7 > 0.001F) {
         float f4 = f3 + 6.0F * glyphQuad.value4 * glyphQuad.value7;

         for (int i = 3; i >= 0; i--) {
            float f5 = glyphQuad.floatArray[i];
            if (f5 > 0.001F) {
               ItemStack itemstack3 = nameTagEntry.itemStackArray[i];
               float f8 = glyphQuad.value4 * f5;
               ItemStack itemstack1 = itemstack3;
               onMatrix4fIntFloatFloatItemStackFloat(matrix4f, count, f8, f2, itemstack1, f4);
            }

            f4 += 16.0F * f5 * glyphQuad.value4;
            if (i > 0) {
               float f6 = 6.0F * Math.max(f5, glyphQuad.floatArray[i - 1]);
               f4 += f6 * glyphQuad.value4;
            }
         }
      }

      f3 += glyphQuad.value15;
      if (glyphQuad.value3 > 0.001F && glyphQuad.value6 > 0.001F) {
         float f10 = f3 + 6.0F * glyphQuad.value3 * glyphQuad.value6;
         float f9 = glyphQuad.value3 * glyphQuad.value6;
         ItemStack itemstack2 = nameTagEntry.itemStack2;
         onMatrix4fIntFloatFloatItemStackFloat(matrix4f, count, f9, f2, itemstack2, f10);
      }
   }

   private static NameTagAnimations getNameTagAnimationsByUUID(UUID uUID) {
      return new NameTagAnimations();
   }

   private void onFloatMatrix4fItemStackBooleanFloatFloatInt(float value, Matrix4f matrix4f, ItemStack itemStack, boolean flag, float value2, float value3, int count) {
      if (itemStack != null && !itemStack.isEmpty()) {
         float f3 = 16.0F;
         if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(value, matrix4f, value2, f3, value3, itemStack)) {
            if (value2 >= 0.999F) {
               float f4 = 16.0F;
               ItemIconCache.onFloatFloatItemStackMatrix4fFloat(value, f4, itemStack, matrix4f, value3);
            } else {
               float f5 = 16.0F;
               ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f5, value2, value, itemStack, matrix4f, value3);
            }
         }
      } else if (!flag) {
         float f = value + 3.5F;
         float f1 = value3 + 3.5F;
         float f2 = 9.0F;
         CategoryType categorytype = CategoryType.CLOSE;
         SvgShader.onFloatCategoryTypeIntMatrix4fFloatFloatFloat(f1, categorytype, count, matrix4f, value2, f2, f);
      }
   }

   private static int getIntByIntFloat(int count, float value) {
      float f = value < 0.0F ? 0.0F : (value > 1.0F ? 1.0F : value);
      int i = count >> 24 & 0xFF;
      int j = Math.round(i * f);
      return j << 24 | count & 16777215;
   }

   private static int getIntByFloatFloatInt(float value, float value2, int count) {
      float[] afloat = MathUtil.getFloatArrayByInt(count);
      float f2 = afloat[0];
      float f1 = afloat[1] * value;
      float f = f2;
      return MathUtil.getIntByFloatFloatFloat(value2, f, f1);
   }

   private void onIntNameTagEntryGlyphQuadBooleanFloatNameTagAnimations(
      int count, NameTagEntry nameTagEntry, GlyphQuad glyphQuad, boolean flag3, float value22, NameTagAnimations nameTagAnimations
   ) {
      float f = nameTagAnimations.animation.getFloat();
      float f1 = nameTagAnimations.animation2.getFloat();
      float f2 = nameTagAnimations.animation3.getFloat();
      float f3 = nameTagAnimations.animation4.getFloat();
      float f4 = nameTagAnimations.animation5.getFloat();
      float f5 = nameTagAnimations.animationArray[0].getFloat();
      float f6 = nameTagAnimations.animationArray[1].getFloat();
      float f7 = nameTagAnimations.animationArray[2].getFloat();
      float f8 = nameTagAnimations.animationArray[3].getFloat();
      boolean flag = nameTagEntry.text != null && !nameTagEntry.text.isEmpty();
      float f9 = flag ? TextShader.getFloatByStringFloat(nameTagEntry.text, 14.0F) : 0.0F;
      float f10 = flag ? f9 + 6.0F : 0.0F;
      float f11 = TextShader.getFloatByStringFloat(nameTagEntry.text2, 14.0F);
      float f12 = TextShader.getFloatByStringFloat(nameTagEntry.text3, 14.0F);
      float f13 = 16.0F * f3;
      float f14 = (f13 + 6.0F * f3) * f;
      float f15 = 16.0F * f4;
      float f16 = (6.0F * f4 + f15) * f1;
      float f17 = 16.0F * (f5 + f6 + f7 + f8);
      float f18 = 6.0F * Math.max(f5, f6);
      float f19 = 6.0F * Math.max(f6, f7);
      float f20 = 6.0F * Math.max(f7, f8);
      float f21 = f17 + f18 + f19 + f20;
      float f22 = Math.max(Math.max(f5, f6), Math.max(f7, f8));
      float f23 = (6.0F * f22 + f21) * f2;
      float f24 = nameTagEntry.flag2 ? 21.0F : 0.0F;
      boolean flag1 = nameTagEntry.voiceIcon != null && nameTagEntry.voiceIcon != VoiceIcon.NONE && nameTagEntry.voiceIcon.identifier != null;
      float f25 = flag1 ? 18.0F : 0.0F;
      float f26 = f12 + 8.0F;
      float f27 = 6.0F + f14 + f25 + f24 + f10 + f11 + 6.0F + f26 + f23 + f16 + 6.0F;
      int i = Theme.success();
      int j = nameTagEntry.flag2 ? i : count;
      boolean flag2 = ThemeConfig.getThemePalette() == ThemePalette.INSTANCE2;
      int k;
      if (nameTagEntry.flag2) {
         k = i;
      } else if (!HealthTracker.isFloat(nameTagEntry.value) && flag3) {
         float f29 = nameTagEntry.value2;
         float f28 = nameTagEntry.value;
         k = getIntByFloatFloat(f29, f28);
      } else {
         k = 0;
      }

      int j1;
      if (k == 0) {
         j1 = count;
      } else {
         float f35 = flag2 ? 0.66F : 0.38F;
         float f31 = 1.0F;
         float f30 = f35;
         j1 = getIntByFloatFloatInt(f31, f30, k);
      }

      int l = j1;
      if (k == 0) {
         j1 = Theme.elevated();
      } else {
         float f34 = flag2 ? 0.22F : 0.9F;
         float f33 = 0.55F;
         float f32 = f34;
         j1 = getIntByFloatFloatInt(f33, f32, k);
      }

      int i1 = j1;
      glyphQuad.value = value22;
      glyphQuad.value2 = f;
      glyphQuad.value3 = f1;
      glyphQuad.value4 = f2;
      glyphQuad.value5 = f3;
      glyphQuad.value6 = f4;
      glyphQuad.floatArray[0] = f5;
      glyphQuad.floatArray[1] = f6;
      glyphQuad.floatArray[2] = f7;
      glyphQuad.floatArray[3] = f8;
      glyphQuad.value7 = f22;
      glyphQuad.flag = flag;
      glyphQuad.value8 = f9;
      glyphQuad.value9 = f11;
      glyphQuad.value10 = f12;
      glyphQuad.value11 = f14;
      glyphQuad.value12 = f16;
      glyphQuad.value13 = f24;
      glyphQuad.value14 = f25;
      glyphQuad.flag2 = flag1;
      glyphQuad.value15 = f23;
      glyphQuad.value16 = f27;
      glyphQuad.value17 = j;
      glyphQuad.value18 = l;
      glyphQuad.value19 = i1;
      glyphQuad.value21 = i;
      glyphQuad.value20 = -f27 / 2.0F + 6.0F + f14 + f25 + f24 + f10 + f11 + 6.0F;
   }

   private static void onFloatNameTagEntryCameraMatrixStack(float value, NameTagEntry nameTagEntry, Camera camera, MatrixStack matrixStack) {
      matrixStack.translate(nameTagEntry.vec3d.x, nameTagEntry.vec3d.y, nameTagEntry.vec3d.z);
      matrixStack.translate(0.0, nameTagEntry.playerEntity.getHeight() + 0.5, 0.0);
      RotationBuffer.render(matrixStack);
      matrixStack.scale(-value, -value, -value);
   }

   public void onBooleanBooleanFloatBooleanListWorldRenderContextBooleanBooleanFloatBooleanBooleanFloatBoolean(
      boolean flag9,
      boolean flag10,
      float value2,
      boolean flag11,
      List<NameTagEntry> list2,
      WorldRenderContext worldRenderContext,
      boolean flag12,
      boolean flag13,
      float value4,
      boolean flag14,
      boolean flag15,
      float value5,
      boolean flag16
   ) {
      if (!list2.isEmpty()) {
         MatrixStack matrixstack = worldRenderContext.getMatrixStack();
         Camera camera = worldRenderContext.getCamera();
         long i = System.currentTimeMillis();
         float f = Math.min((float)(i - this.time) / 1000.0F, 0.1F);
         this.time = i;
         int j = getIntByIntFloat(Theme.background(), value4);
         int k = Theme.border();
         int l = Theme.foreground();
         int i1 = list2.size();

         while (this.list.size() < i1) {
            this.list.add(new GlyphQuad());
         }

         for (int j1 = 0; j1 < i1; j1++) {
            NameTagEntry nametagentry = (NameTagEntry)list2.get(j1);
            UUID uuid = nametagentry.playerEntity.getUuid();
            NameTagAnimations nametaganimations = this.map.computeIfAbsent(uuid, NameTagCache::getNameTagAnimationsByUUID);
            if (!flag13) {
               nametaganimations.flag = true;
            } else if (nametaganimations.flag) {
               if (nametagentry.value3 > value2 + 4.0F) {
                  nametaganimations.flag = false;
               }
            } else if (nametagentry.value3 < value2) {
               nametaganimations.flag = true;
            }

            boolean flag = flag11 && nametaganimations.flag;
            boolean flag1 = flag10 && nametaganimations.flag;
            boolean flag2 = flag9 && nametaganimations.flag;
            boolean flag3 = nametagentry.itemStack != null && !nametagentry.itemStack.isEmpty();
            boolean flag4 = nametagentry.itemStack2 != null && !nametagentry.itemStack2.isEmpty();
            boolean flag5 = !flag14 || flag3;
            boolean flag6 = !flag14 || flag4;
            if (!nametaganimations.flag2) {
               if (flag) {
                  nametaganimations.animation.update2();
               }

               if (flag1) {
                  nametaganimations.animation2.update2();
               }

               if (flag2) {
                  nametaganimations.animation3.update2();
               }

               if (flag5) {
                  nametaganimations.animation4.update2();
               }

               if (flag6) {
                  nametaganimations.animation5.update2();
               }

               for (int k1 = 0; k1 < 4; k1++) {
                  ItemStack itemstack = nametagentry.itemStackArray[k1];
                  boolean flag7 = !flag14 || itemstack != null && !itemstack.isEmpty();
                  if (flag7) {
                     nametaganimations.animationArray[k1].update2();
                  }
               }

               nametaganimations.flag2 = true;
            }

            nametaganimations.animation.setBoolean(flag);
            nametaganimations.animation2.setBoolean(flag1);
            nametaganimations.animation3.setBoolean(flag2);
            nametaganimations.animation4.setBoolean(flag5);
            nametaganimations.animation5.setBoolean(flag6);

            for (int k2 = 0; k2 < 4; k2++) {
               ItemStack itemstack1 = nametagentry.itemStackArray[k2];
               boolean flag8 = !flag14 || itemstack1 != null && !itemstack1.isEmpty();
               nametaganimations.animationArray[k2].setBoolean(flag8);
            }

            nametaganimations.animation.setFloat2(f);
            nametaganimations.animation2.setFloat2(f);
            nametaganimations.animation3.setFloat2(f);
            nametaganimations.animation4.setFloat2(f);
            nametaganimations.animation5.setFloat2(f);

            for (int l2 = 0; l2 < 4; l2++) {
               nametaganimations.animationArray[l2].setFloat2(f);
            }

            float f3 = DistanceScale.getFloatByDoubleFloat(nametagentry.value3, value5);
            if (Float.isNaN(nametaganimations.value)) {
               nametaganimations.value = f3;
            } else {
               nametaganimations.value = nametaganimations.value + (f3 - nametaganimations.value) * Math.min(1.0F, f * 10.0F);
            }

            GlyphQuad glyphquad4 = this.list.get(j1);
            float f1 = nametaganimations.value;
            GlyphQuad glyphquad = glyphquad4;
            this.onIntNameTagEntryGlyphQuadBooleanFloatNameTagAnimations(l, nametagentry, glyphquad, flag16, f1, nametaganimations);
         }

         RotationBuffer.setMinecraftClient2(Feature.mc);
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         ShapeShader.update2();

         try {
            for (int l1 = 0; l1 < i1; l1++) {
               NameTagEntry nametagentry1 = (NameTagEntry)list2.get(l1);
               GlyphQuad glyphquad1 = this.list.get(l1);
               matrixstack.push();

               try {
                  float f2 = glyphquad1.value;
                  onFloatNameTagEntryCameraMatrixStack(f2, nametagentry1, camera, matrixstack);
                  Matrix4f matrix4f3 = matrixstack.peek().getPositionMatrix();
                  glyphquad1.matrix4f.set(matrix4f3);
                  if (flag12) {
                     this.onGlyphQuadMatrix4fInt(glyphquad1, matrix4f3, j);
                  }
               } finally {
                  matrixstack.pop();
               }
            }
         } finally {
            ShapeShader.update();
         }

         ItemIconCache.setFloat(1.0F);
         SvgShader.update4();
         ItemIcons.update();
         TextShader.update3();

         try {
            for (int i2 = 0; i2 < i1; i2++) {
               NameTagEntry nametagentry2 = (NameTagEntry)list2.get(i2);
               GlyphQuad glyphquad2 = this.list.get(i2);
               Matrix4f matrix4f = glyphquad2.matrix4f;
               this.onMatrix4fNameTagEntryIntIntGlyphQuadBoolean(matrix4f, nametagentry2, k, l, glyphquad2, flag14);
            }
         } finally {
            TextShader.update();
            ItemIcons.update2();
            SvgShader.update();
            ItemIconCache.update4();
         }

         TextShader.update3();

         try {
            for (int j2 = 0; j2 < i1; j2++) {
               NameTagEntry nametagentry3 = (NameTagEntry)list2.get(j2);
               GlyphQuad glyphquad3 = this.list.get(j2);
               if (glyphquad3.flag2) {
                  Matrix4f matrix4f1 = glyphquad3.matrix4f;
                  this.onNameTagEntryMatrix4fGlyphQuad(nametagentry3, matrix4f1, glyphquad3);
               }

               if (flag15) {
                  Matrix4f matrix4f2 = glyphquad3.matrix4f;
                  this.onIntGlyphQuadMatrix4fNameTagEntry(l, glyphquad3, matrix4f2, nametagentry3);
               }
            }
         } finally {
            TextShader.update();
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RotationBuffer.setMinecraftClient(Feature.mc);
         Set setx = this.set;
         setx.clear();

         for (NameTagEntry nametagentry4 : list2) {
            setx.add(nametagentry4.playerEntity.getUuid());
         }

         this.map.keySet().retainAll(setx);
      }
   }

   public void update() {
      this.map.clear();
      this.list.clear();
   }

   private void onGlyphQuadMatrix4fInt(GlyphQuad glyphQuad, Matrix4f matrix4f, int count) {
      float f = -glyphQuad.value16 / 2.0F;
      float f1 = -16.0F;
      float f12 = 1.0F;
      float f11 = 0.0F;
      float f10 = 0.0F;
      float f9 = 0.0F;
      byte b1 = 0;
      float f8 = 0.0F;
      byte b0 = 0;
      float f7 = 8.0F;
      float f6 = 8.0F;
      float f5 = 8.0F;
      float f4 = 8.0F;
      float f3 = 32.0F;
      float f2 = glyphQuad.value16;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f2, b1, count, f, f1, f11, f4, f3, f10, matrix4f, f12, b0, f6, f8, f5, f7, f9
      );
      this.onMatrix4fGlyphQuad(matrix4f, glyphQuad);
   }

   private void onMatrix4fGlyphQuad(Matrix4f matrix4f, GlyphQuad glyphQuad) {
      if (!(glyphQuad.value10 <= 0.001F)) {
         float f = 20.0F;
         float f1 = -f / 2.0F;
         float f6 = glyphQuad.value10 + 8.0F;
         float f5 = 1.0F;
         int i = glyphQuad.value19;
         float f4 = 4.0F;
         float f3 = f6;
         float f2 = glyphQuad.value20;
         ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f4, f2, i, matrix4f, f, f3, f5, f1);
      }
   }

   private void onMatrix4fNameTagEntryIntIntGlyphQuadBoolean(Matrix4f matrix4f, NameTagEntry nameTagEntry, int count, int count2, GlyphQuad glyphQuad, boolean flag3) {
      float f = -glyphQuad.value16 / 2.0F;
      float f1 = -16.0F;
      float f2 = f1 + 8.0F;
      float f3 = f1 + 9.0F;
      float f4 = f1 + 9.5F;
      float f5 = f + 6.0F;
      if (glyphQuad.value2 > 0.001F && glyphQuad.value5 > 0.001F) {
         float f9 = glyphQuad.value2 * glyphQuad.value5;
         ItemStack itemstack = nameTagEntry.itemStack;
         this.onFloatMatrix4fItemStackBooleanFloatFloatInt(f5, matrix4f, itemstack, flag3, f9, f2, count);
      }

      f5 += glyphQuad.value11;
      if (glyphQuad.flag2) {
         f5 += 18.0F;
      }

      if (nameTagEntry.flag2) {
         float f12 = 1.0F;
         int j = glyphQuad.value21;
         float f11 = 13.0F;
         float f10 = 14.0F;
         CategoryType categorytype = CategoryType.FRIENDS;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f12, j, matrix4f, f4, categorytype, f11, f5, f10);
         f5 += 21.0F;
      }

      if (glyphQuad.flag) {
         float f14 = 1.0F;
         float f13 = 14.0F;
         String s = nameTagEntry.text;
         TextShader.onIntFloatFloatMatrix4fFloatFloatString(count2, f14, f3, matrix4f, f5, f13, s);
         f5 += glyphQuad.value8 + 6.0F;
      }

      float f16 = 1.0F;
      int k = glyphQuad.value17;
      float f15 = 14.0F;
      String s1 = nameTagEntry.text2;
      TextShader.onIntFloatFloatMatrix4fFloatFloatString(k, f16, f3, matrix4f, f5, f15, s1);
      f5 += glyphQuad.value9 + 6.0F;
      float f23 = f5 + 4.0F;
      float f19 = 1.0F;
      int l = glyphQuad.value18;
      float f18 = 14.0F;
      float f17 = f23;
      String s2 = nameTagEntry.text3;
      TextShader.onIntFloatFloatMatrix4fFloatFloatString(l, f19, f3, matrix4f, f17, f18, s2);
      f5 += glyphQuad.value10 + 8.0F;
      if (glyphQuad.value4 > 0.001F && glyphQuad.value7 > 0.001F) {
         float f6 = f5 + 6.0F * glyphQuad.value4 * glyphQuad.value7;

         for (int i = 3; i >= 0; i--) {
            float f7 = glyphQuad.floatArray[i];
            if (f7 > 0.001F) {
               ItemStack itemstack3 = nameTagEntry.itemStackArray[i];
               float f20 = glyphQuad.value4 * f7;
               ItemStack itemstack1 = itemstack3;
               this.onFloatMatrix4fItemStackBooleanFloatFloatInt(f6, matrix4f, itemstack1, flag3, f20, f2, count);
            }

            f6 += 16.0F * f7 * glyphQuad.value4;
            if (i > 0) {
               float f8 = 6.0F * Math.max(f7, glyphQuad.floatArray[i - 1]);
               f6 += f8 * glyphQuad.value4;
            }
         }
      }

      f5 += glyphQuad.value15;
      if (glyphQuad.value3 > 0.001F && glyphQuad.value6 > 0.001F) {
         float f22 = f5 + 6.0F * glyphQuad.value3 * glyphQuad.value6;
         float f21 = glyphQuad.value3 * glyphQuad.value6;
         ItemStack itemstack2 = nameTagEntry.itemStack2;
         this.onFloatMatrix4fItemStackBooleanFloatFloatInt(f22, matrix4f, itemstack2, flag3, f21, f2, count);
      }
   }
}
