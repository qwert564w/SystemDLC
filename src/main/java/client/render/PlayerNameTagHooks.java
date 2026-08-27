package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.data.SystemFriend;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.client.StreamBypass;
import client.module.player.Protect;
import client.module.render.NameTags;
import client.module.render.PlayerScaler;
import client.util.ReflectionCache;
import client.util.UnsafeAccess;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckStingersFeatureRenderer;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

@HookClass(PlayerEntityRenderer.class)
public class PlayerNameTagHooks {
   private static final UnsafeAccess<Protect> unsafeAccess = new UnsafeAccess<>(Protect.class);
   private static final UnsafeAccess<NameTags> unsafeAccess2 = new UnsafeAccess<>(NameTags.class);
   private static final UnsafeAccess<PlayerScaler> unsafeAccess3 = new UnsafeAccess<>(PlayerScaler.class);
   private static String text;
   private static Set<String> set;
   private static final Map<String, Pattern> map = new ConcurrentHashMap<>();
   private static long time;
   private static final long time2 = ReflectionCache.getLongByClassClass2(LivingEntityRenderer.class, List.class);

   @Hook(
      method = "method_4217",
      desc = "(Lnet/minecraft/class_10055;Lnet/minecraft/class_4587;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onPlayerEntityRendererPlayerEntityRenderStateMatrixStack(PlayerEntityRenderer playerEntityRenderer, PlayerEntityRenderState playerEntityRenderState, MatrixStack matrixStack) {
      PlayerScaler playerscaler = (PlayerScaler)unsafeAccess3.getModule2();
      boolean flag = Feature.mc.player != null && Feature.mc.player.getId() == playerEntityRenderState.id;
      if (playerscaler == null || flag) {
         matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
      } else if (playerscaler.getProporcionalno().isFlag3()) {
         float f = playerscaler.getSize().getValueAsFloat();
         matrixStack.scale(f, f, f);
      } else {
         float f3 = playerscaler.getSizeX().getValueAsFloat();
         float f1 = playerscaler.getSizeY().getValueAsFloat();
         float f2 = playerscaler.getSizeZ().getValueAsFloat();
         matrixStack.scale(f3, f1, f2);
      }
   }

   private static Text getTextByTextProtect(Text text3, Protect protect) {
      String s = text3.getLiteralString();
      boolean flag = false;
      String s1 = s;
      if (s != null && !s.isEmpty()) {
         if (protect.getHideNick().isFlag3() && text != null && s.contains(text)) {
            String s2 = protect.getSvoyNick().getText();
            String s3 = s2 != null && !s2.isEmpty() ? s2 : "SystemPlayer";
            s1 = s.replace(text, s3);
            flag = true;
         }

         if (protect.getHideDruzey().isFlag3() && set != null) {
            for (Entry entry : map.entrySet()) {
               if (((Pattern)entry.getValue()).matcher(s1).find()) {
                  String s4 = SystemFriend.getInstance().getStringByString((String)entry.getKey());
                  String s5 = s4 != null && !s4.isEmpty() ? s4 : "SystemFriend";
                  s1 = ((Pattern)entry.getValue()).matcher(s1).replaceAll(Matcher.quoteReplacement(s5));
                  flag = true;
               }
            }
         }
      }

      List list = text3.getSiblings();
      if (!flag && list.isEmpty()) {
         return text3;
      } else {
         Text[] atext = null;
         boolean flag1 = false;
         if (!list.isEmpty()) {
            atext = new Text[list.size()];

            for (int i = 0; i < list.size(); i++) {
               Text textx = (Text)list.get(i);
               Text text1 = getTextByTextProtect(textx, protect);
               atext[i] = text1;
               if (text1 != textx) {
                  flag1 = true;
               }
            }
         }

         if (!flag && !flag1) {
            return text3;
         } else {
            MutableText mutabletext = flag ? Text.literal(s1).setStyle(text3.getStyle()) : text3.copyContentOnly().setStyle(text3.getStyle());
            if (atext != null) {
               for (Text text2 : atext) {
                  mutabletext.append(text2);
               }
            }

            return mutabletext;
         }
      }
   }

   @Hook(
      method = "method_4213",
      desc = "(Lnet/minecraft/class_10055;Lnet/minecraft/class_2561;Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;I)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onPlayerEntityRendererPlayerEntityRenderStateTextMatrixStackVertexConsumerProviderInt(
      PlayerEntityRenderer playerEntityRenderer, PlayerEntityRenderState playerEntityRenderState, Text text2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int count
   ) {
      NameTags nametags = (NameTags)unsafeAccess2.getModule2();
      boolean flag = StreamBypass.check6();
      if (nametags == null
         || nametags.check3()
         || flag
         || Feature.mc.world == null
         || !(Feature.mc.world.getEntityById(playerEntityRenderState.id) instanceof PlayerEntity playerentity && nametags.isPlayerEntity(playerentity))) {
         Protect protect = Feature.mc.player != null ? (Protect)unsafeAccess.getModule2() : null;
         if (protect == null) {
            HandleInvoker.onObjectArray(playerEntityRenderer, playerEntityRenderState, text2, matrixStack, vertexConsumerProvider, count);
         } else {
            update();
            Text textx = playerEntityRenderState.playerName;
            if (playerEntityRenderState.playerName != null) {
               playerEntityRenderState.playerName = getTextByTextProtect(playerEntityRenderState.playerName, protect);
            }

            text2 = getTextByTextProtect(text2, protect);
            HandleInvoker.onObjectArray(playerEntityRenderer, playerEntityRenderState, text2, matrixStack, vertexConsumerProvider, count);
            playerEntityRenderState.playerName = textx;
         }
      }
   }

   public static void onPlayerEntityRendererContextBoolean(PlayerEntityRenderer playerEntityRenderer, Context context, boolean flag) {
      List list = (List)ReflectionCache.getObjectByObjectLong(playerEntityRenderer, time2);
      list.clear();
      list.add(
         new ArmorFeatureRenderer(
            playerEntityRenderer,
            new ArmorEntityModel(context.getPart(flag ? EntityModelLayers.PLAYER_SLIM_INNER_ARMOR : EntityModelLayers.PLAYER_INNER_ARMOR)),
            new ArmorEntityModel(context.getPart(flag ? EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR : EntityModelLayers.PLAYER_OUTER_ARMOR)),
            context.getEquipmentRenderer()
         )
      );
      list.add(new PlayerHeldItemFeatureRenderer(playerEntityRenderer));
      list.add(new StuckArrowsFeatureRenderer(playerEntityRenderer, context));
      list.add(new HeadFeatureRenderer(playerEntityRenderer, context.getEntityModels()));
      list.add(new ElytraFeatureRenderer(playerEntityRenderer, context.getEntityModels(), context.getEquipmentRenderer()));
      list.add(new TridentRiptideFeatureRenderer(playerEntityRenderer, context.getEntityModels()));
      list.add(new StuckStingersFeatureRenderer(playerEntityRenderer, context));
   }

   private static void update() {
      long i = System.currentTimeMillis();
      if (i - time >= 500L) {
         time = i;

         try {
            text = Feature.mc.player != null ? Feature.mc.player.getGameProfile().getName() : null;
            Set setx = SystemFriend.getInstance().getSet();
            if (set == null || !set.equals(setx)) {
               set = new HashSet<>(setx);
               map.clear();

               for (String s : set) {
                  map.put(s, Pattern.compile("(?i)" + Pattern.quote(s)));
               }
            }
         } catch (Exception exception) {
         }
      }
   }
}
