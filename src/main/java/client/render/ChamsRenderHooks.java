package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.render.FriendChecks;
import client.module.render.ShowInvisible;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModelLoader;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel.Dyeable;
import net.minecraft.client.render.entity.equipment.EquipmentModel.Layer;
import net.minecraft.client.render.entity.equipment.EquipmentModel.LayerType;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@HookClass(EquipmentRenderer.class)
public class ChamsRenderHooks {
   private static final UnsafeAccess<FriendChecks> unsafeAccess = new UnsafeAccess<>(FriendChecks.class);
   private static final UnsafeAccess<ShowInvisible> unsafeAccess2 = new UnsafeAccess<>(ShowInvisible::getInstance);
   private static float value = 0.0F;
   private static final List<PlayerRenderEntry> list = new ArrayList<>();
   private static boolean flag = false;
   private static final long time = ReflectionCache.getLongByClassClass(EquipmentRenderer.class, EquipmentModelLoader.class);
   private static final long time2 = ReflectionCache.getLongByClassClassInt(EquipmentRenderer.class, Function.class, 0);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(EquipmentRenderer.class, Function.class, 1);
   private static Constructor<?> constructor;
   private static Constructor<?> constructor2;

   static {
      try {
         for (Class oclass : EquipmentRenderer.class.getDeclaredClasses()) {
            for (Constructor constructorx : oclass.getDeclaredConstructors()) {
               Class[] aclass = constructorx.getParameterTypes();
               if (aclass.length == 2 && aclass[0] == LayerType.class) {
                  constructorx.setAccessible(true);
                  constructor = constructorx;
               } else if (aclass.length == 3 && aclass[0] == ArmorTrim.class) {
                  constructorx.setAccessible(true);
                  constructor2 = constructorx;
               }
            }
         }
      } catch (Exception exception) {
      }
   }

   public static void render(MatrixStack matrixStack, Camera camera, float value3) {
      if (!list.isEmpty()) {
         if (!flag) {
            ArrayList<PlayerRenderEntry> arraylist = new ArrayList<>(list);
            list.clear();
            Immediate immediate = Feature.mc.getBufferBuilders().getEntityVertexConsumers();
            Vec3d vec3d = camera.getPos();
            flag = true;

            try {
               immediate.draw();
            } catch (Throwable throwable1) {
            }

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);

            try {
               for (PlayerRenderEntry playerrenderentry : arraylist) {
                  if (playerrenderentry.playerEntity != null && !playerrenderentry.playerEntity.isRemoved()) {
                     LivingEntityRenderer livingentityrenderer = playerrenderentry.livingEntityRenderer;

                     EntityRenderState entityrenderstate;
                     try {
                        entityrenderstate = livingentityrenderer.getAndUpdateRenderState(playerrenderentry.playerEntity, value3);
                     } catch (Throwable throwable2) {
                        continue;
                     }

                     if (entityrenderstate instanceof LivingEntityRenderState livingentityrenderstate) {
                        double d0 = MathHelper.lerp(value3, playerrenderentry.playerEntity.lastRenderX, playerrenderentry.playerEntity.getX());
                        double d1 = MathHelper.lerp(value3, playerrenderentry.playerEntity.lastRenderY, playerrenderentry.playerEntity.getY());
                        double d2 = MathHelper.lerp(value3, playerrenderentry.playerEntity.lastRenderZ, playerrenderentry.playerEntity.getZ());
                        matrixStack.push();

                        try {
                           matrixStack.translate(d0 - vec3d.x, d1 - vec3d.y, d2 - vec3d.z);
                           RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, playerrenderentry.value2);
                           value = playerrenderentry.value2;

                           try {
                              ((LivingEntityRenderer)playerrenderentry.livingEntityRenderer).render(livingentityrenderstate, matrixStack, immediate, playerrenderentry.value);

                              try {
                                 immediate.draw();
                              } catch (Throwable throwable) {
                              }
                           } finally {
                              value = 0.0F;
                              RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                           }
                        } finally {
                           matrixStack.pop();
                        }
                     }
                  }
               }
            } finally {
               RenderSystem.depthMask(true);
               RenderSystem.disableBlend();
               flag = false;
            }
         }
      }
   }

   public static void update() {
      list.clear();
   }

   private static PlayerEntity getPlayerEntityByLivingEntityRenderState(LivingEntityRenderState livingEntityRenderState) {
      if (Feature.mc.world == null) {
         return null;
      } else if (livingEntityRenderState instanceof PlayerEntityRenderState playerentityrenderstate) {
         try {
            if (Feature.mc.world.getEntityById(playerentityrenderstate.id) instanceof PlayerEntity playerentity) {
               return playerentity;
            }
         } catch (Throwable throwable) {
         }

         return null;
      } else {
         return null;
      }
   }

   @Hook(
      target = LivingEntityRenderer.class,
      method = "method_4054",
      desc = "(Lnet/minecraft/class_10042;Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;I)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onLivingEntityRendererLivingEntityRenderStateMatrixStackVertexConsumerProviderInt(
      LivingEntityRenderer livingEntityRenderer, LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int count
   ) {
      if (FramebufferSwap.isFlag()) {
         boolean flag1 = livingEntityRenderState.invisible;
         livingEntityRenderState.invisible = false;

         try {
            HandleInvoker.onObjectArray(livingEntityRenderer, livingEntityRenderState, matrixStack, vertexConsumerProvider, count);
         } finally {
            livingEntityRenderState.invisible = flag1;
         }
      } else if (flag) {
         HandleInvoker.onObjectArray(livingEntityRenderer, livingEntityRenderState, matrixStack, vertexConsumerProvider, count);
      } else {
         PlayerEntity playerentity = getPlayerEntityByLivingEntityRenderState(livingEntityRenderState);
         if (playerentity != null && (Feature.mc.player == null || playerentity != Feature.mc.player)) {
            FriendChecks friendchecks = (FriendChecks)unsafeAccess.getModule2();
            boolean flagx = friendchecks != null && friendchecks.isPlayerEntity(playerentity);
            if (flagx) {
               list.add(new PlayerRenderEntry(livingEntityRenderer, playerentity, count, friendchecks.getFloatByPlayerEntity(playerentity)));
            } else {
               HandleInvoker.onObjectArray(livingEntityRenderer, livingEntityRenderState, matrixStack, vertexConsumerProvider, count);
            }
         } else {
            HandleInvoker.onObjectArray(livingEntityRenderer, livingEntityRenderState, matrixStack, vertexConsumerProvider, count);
         }
      }
   }

   @Hook(
      method = "method_64078",
      desc = "(Lnet/minecraft/class_10186$class_10190;Lnet/minecraft/class_5321;Lnet/minecraft/class_3879;Lnet/minecraft/class_1799;Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;ILnet/minecraft/class_2960;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onEquipmentRendererLayerTypeRegistryKeyModelItemStackMatrixStackVertexConsumerProviderIntIdentifier(
      EquipmentRenderer equipmentRenderer,
      LayerType layerType,
      RegistryKey registryKey,
      Model model2,
      ItemStack itemStack,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      int count,
      Identifier identifier2
   ) {
      boolean flagx = value > 0.0F && value < 1.0F;
      if (flagx && time != 0L && constructor != null) {
         try {
            int i = OverlayTexture.DEFAULT_UV;
            EquipmentModelLoader equipmentmodelloader = (EquipmentModelLoader)ReflectionCache.getObjectByObjectLong(equipmentRenderer, time);
            Function function = (Function)ReflectionCache.getObjectByObjectLong(equipmentRenderer, time2);
            Function function1 = (Function)ReflectionCache.getObjectByObjectLong(equipmentRenderer, time3);
            List<Layer> listx = equipmentmodelloader.get(registryKey).getLayers(layerType);
            if (listx.isEmpty()) {
               return;
            }

            int j = itemStack.isIn(ItemTags.DYEABLE) ? DyedColorComponent.getColor(itemStack, 0) : 0;
            boolean flag1 = itemStack.hasGlint();

            for (Layer layer : listx) {
               int k = getIntByLayerInt(layer, j);
               if (k == 0) {
                  k = -1;
               }

               k = getIntByInt(k);
               Identifier identifier;
               if (layer.usePlayerTexture() && identifier2 != null) {
                  identifier = identifier2;
               } else {
                  Object object = constructor.newInstance(layerType, layer);
                  identifier = (Identifier)function.apply(object);
               }

               VertexConsumer vertexconsumer1 = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEntityTranslucent(identifier), flag1);
               model2.render(matrixStack, vertexconsumer1, count, i, k);
               flag1 = false;
            }

            ArmorTrim armortrim = (ArmorTrim)itemStack.get(DataComponentTypes.TRIM);
            if (armortrim != null && constructor2 != null) {
               Object object1 = constructor2.newInstance(armortrim, layerType, registryKey);
               Sprite sprite = (Sprite)function1.apply(object1);
               VertexConsumer vertexconsumer = sprite.getTextureSpecificVertexConsumer(
                  vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE))
               );
               model2.render(matrixStack, vertexconsumer, count, i, getIntByInt(-1));
            }
         } catch (Exception exception) {
            HandleInvoker.onObjectArray(equipmentRenderer, layerType, registryKey, model2, itemStack, matrixStack, vertexConsumerProvider, count, identifier2);
         }
      } else {
         HandleInvoker.onObjectArray(equipmentRenderer, layerType, registryKey, model2, itemStack, matrixStack, vertexConsumerProvider, count, identifier2);
      }
   }

   private static int getIntByLayerInt(Layer layer, int count) {
      Optional optional = layer.dyeable();
      if (optional.isPresent()) {
         int i = ((Dyeable)optional.get()).colorWhenUndyed().<Integer>map(ColorHelper::fullAlpha).orElse(0);
         return count != 0 ? count : i;
      } else {
         return -1;
      }
   }

   private static int getIntByInt(int count) {
      int i = count >>> 24 & 0xFF;
      int j = Math.round(i * value);
      if (j < 0) {
         j = 0;
      }

      if (j > 255) {
         j = 255;
      }

      return j << 24 | count & 16777215;
   }
}
