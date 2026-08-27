package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.concurrent.HandleInvoker;
import client.concurrent.ResourceManagerHooks;
import client.enums.InjectPoint;
import client.gui.widget.UiContext;
import client.module.Feature;
import client.module.client.CleanHud;
import client.module.client.HudModule;
import client.module.client.PanicModule;
import client.module.client.StreamBypass;
import client.module.combat.Hitbox;
import client.module.movement.FreeCam;
import client.module.player.Protect;
import client.module.render.Tracers;
import client.module.visual.Bloom;
import client.module.visual.NoRender;
import client.module.visual.Saturation;
import client.util.ModuleDispatcher;
import client.util.UnsafeAccess;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.PlainTextContent.Literal;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.joml.Matrix4fStack;

@HookClass(InGameHud.class)
public class CrosshairTextures {
   private static final Identifier identifier = Identifier.ofVanilla("hud/crosshair");
   private static final Identifier identifier2 = Identifier.ofVanilla("hud/crosshair_attack_indicator_full");
   private static final Identifier identifier3 = Identifier.ofVanilla("hud/crosshair_attack_indicator_background");
   private static final Identifier identifier4 = Identifier.ofVanilla("hud/crosshair_attack_indicator_progress");
   private static final Identifier identifier5 = Identifier.ofVanilla("hud/food_empty");
   private static final Identifier identifier6 = Identifier.ofVanilla("hud/food_half");
   private static final Identifier identifier7 = Identifier.ofVanilla("hud/food_full");
   private static final UnsafeAccess<CleanHud> unsafeAccess = new UnsafeAccess<>(CleanHud.class);
   private static final UnsafeAccess<NoRender> unsafeAccess2 = new UnsafeAccess<>(NoRender.class);
   private static final UnsafeAccess<HudModule> unsafeAccess3 = new UnsafeAccess<>(HudModule.class);
   private static final UnsafeAccess<Tracers> unsafeAccess4 = new UnsafeAccess<>(Tracers.class);
   private static final UnsafeAccess<Hitbox> unsafeAccess5 = new UnsafeAccess<>(Hitbox.class);
   private static final UnsafeAccess<Protect> unsafeAccess6 = new UnsafeAccess<>(Protect.class);
   private static final UnsafeAccess<FreeCam> unsafeAccess7 = new UnsafeAccess<>(FreeCam.class);
   private static final Map<RegistryEntry<StatusEffect>, StatusEffectInstance> map = new LinkedHashMap<>();

   @Hook(
      method = "method_1760",
      desc = "(Lnet/minecraft/class_332;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContext(InGameHud inGameHud, DrawContext drawContext) {
      CleanHud cleanhud = (CleanHud)unsafeAccess.getModule2();
      if (cleanhud == null) {
         NoRender norender = (NoRender)unsafeAccess2.getModule2();
         boolean flag = norender != null && norender.check17() && Feature.mc.player != null && Feature.mc.player.hasStatusEffect(StatusEffects.WITHER);
         StatusEffectInstance statuseffectinstance = null;
         if (flag) {
            statuseffectinstance = Feature.mc.player.getStatusEffect(StatusEffects.WITHER);
            Feature.mc.player.removeStatusEffect(StatusEffects.WITHER);
         }

         HandleInvoker.onObjectArray(inGameHud, drawContext);
         if (statuseffectinstance != null) {
            Feature.mc.player.addStatusEffect(statuseffectinstance);
         }
      }
   }

   @Hook(
      method = "method_55802",
      desc = "(Lnet/minecraft/class_332;Lnet/minecraft/class_9779;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextRenderTickCounter(InGameHud inGameHud, DrawContext drawContext, RenderTickCounter renderTickCounter) {
      CleanHud cleanhud = (CleanHud)unsafeAccess.getModule2();
      if (cleanhud == null) {
         HandleInvoker.onObjectArray(inGameHud, drawContext, renderTickCounter);
      }
   }

   private static String getStringByStringString(String text2, String text3) {
      return !text2.isEmpty() && !text2.endsWith(" ") ? " " + text3 : text3;
   }

   @Hook(
      method = "method_1736",
      desc = "(Lnet/minecraft/class_332;Lnet/minecraft/class_9779;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextRenderTickCounter2(InGameHud inGameHud, DrawContext drawContext, RenderTickCounter renderTickCounter) {
      GameOptions gameoptions = Feature.mc.options;
      if (gameoptions.getPerspective().isFirstPerson()) {
         if (Feature.mc.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || check()) {
            if (inGameHud.getDebugHud().shouldShowDebugHud() && !Feature.mc.player.hasReducedDebugInfo() && !(Boolean)gameoptions.getReducedDebugInfo().getValue()) {
               Camera camera1 = Feature.mc.gameRenderer.getCamera();
               Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
               matrix4fstack.pushMatrix();
               matrix4fstack.mul(drawContext.getMatrices().peek().getPositionMatrix());
               matrix4fstack.translate(drawContext.getScaledWindowWidth() / 2, drawContext.getScaledWindowHeight() / 2, 0.0F);
               matrix4fstack.rotateX(-camera1.getPitch() * (float) (Math.PI / 180.0));
               matrix4fstack.rotateY(camera1.getYaw() * (float) (Math.PI / 180.0));
               matrix4fstack.scale(-1.0F, -1.0F, -1.0F);
               RenderSystem.renderCrosshair(10);
               matrix4fstack.popMatrix();
            } else {
               drawContext.drawGuiTexture(
                  RenderLayer::getCrosshair, identifier, (drawContext.getScaledWindowWidth() - 15) / 2, (drawContext.getScaledWindowHeight() - 15) / 2, 15, 15
               );
               if (Feature.mc.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR) {
                  NoRender norender = (NoRender)unsafeAccess2.getModule2();
                  if (norender != null && norender.check15()) {
                     return;
                  }

                  Hitbox hitbox = (Hitbox)unsafeAccess5.getModule2();
                  if (hitbox != null
                     && hitbox.getSkrytIndikator().isFlag3()
                     && Feature.mc.targetedEntity != null
                     && Feature.mc.targetedEntity instanceof LivingEntity livingentity) {
                     Box box = livingentity.getType().getDimensions().getBoxAt(livingentity.getPos());
                     Camera camera = Feature.mc.gameRenderer.getCamera();
                     Vec3d vec3d = camera.getPos();
                     Vec3d vec3d1 = Vec3d.fromPolar(camera.getPitch(), camera.getYaw());
                     double d0 = Feature.mc.interactionManager.getCurrentGameMode().isCreative() ? 5.0 : 4.5;
                     Vec3d vec3d2 = vec3d.add(vec3d1.multiply(d0));
                     Optional optional = box.raycast(vec3d, vec3d2);
                     if (optional.isEmpty()) {
                        return;
                     }
                  }

                  float f = Feature.mc.player.getAttackCooldownProgress(0.0F);
                  boolean flag = false;
                  if (Feature.mc.targetedEntity instanceof LivingEntity && f >= 1.0F) {
                     flag = Feature.mc.player.getAttackCooldownProgressPerTick() > 5.0F;
                     flag &= Feature.mc.targetedEntity.isAlive();
                  }

                  int i = drawContext.getScaledWindowHeight() / 2 - 7 + 16;
                  int j = drawContext.getScaledWindowWidth() / 2 - 8;
                  if (flag) {
                     drawContext.drawGuiTexture(RenderLayer::getCrosshair, identifier2, j, i, 16, 16);
                  } else if (f < 1.0F) {
                     int k = (int)(f * 17.0F);
                     drawContext.drawGuiTexture(RenderLayer::getCrosshair, identifier3, j, i, 16, 4);
                     drawContext.drawGuiTexture(RenderLayer::getCrosshair, identifier4, 16, 4, 0, 0, j, i, k, 4);
                  }
               }
            }
         }
      }
   }

   @Hook(
      method = "method_1765",
      desc = "(Lnet/minecraft/class_332;Lnet/minecraft/class_9779;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextRenderTickCounter3(InGameHud inGameHud, DrawContext drawContext, RenderTickCounter renderTickCounter) {
      NoRender norender = (NoRender)unsafeAccess2.getModule2();
      boolean flag = norender != null && norender.check10();
      if (flag && Feature.mc.player != null) {
         Map mapx = Feature.mc.player.getActiveStatusEffects();
         map.putAll(mapx);
         mapx.clear();

         try {
            HandleInvoker.onObjectArray(inGameHud, drawContext, renderTickCounter);
         } finally {
            mapx.putAll(map);
            map.clear();
         }
      } else {
         HandleInvoker.onObjectArray(inGameHud, drawContext, renderTickCounter);
      }
   }

   @Hook(
      method = "method_65022",
      desc = "(Lnet/minecraft/class_332;Lnet/minecraft/class_1657;III)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextPlayerEntityIntIntInt(InGameHud inGameHud, DrawContext drawContext, PlayerEntity playerEntity, int count, int count2, int count3) {
      NoRender norender = (NoRender)unsafeAccess2.getModule2();
      if (norender == null || !norender.check8()) {
         HandleInvoker.onObjectArray(inGameHud, drawContext, playerEntity, count, count2, count3);
      }
   }

   @Hook(
      method = "method_58477",
      desc = "(Lnet/minecraft/class_332;Lnet/minecraft/class_1657;II)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextPlayerEntityIntInt(InGameHud inGameHud, DrawContext drawContext, PlayerEntity playerEntity, int count, int count2) {
      HungerManager hungermanager = playerEntity.getHungerManager();
      int i = (int)Math.ceil(hungermanager.getSaturationLevel());
      int j = hungermanager.getFoodLevel();
      HudModule hudmodule = (HudModule)unsafeAccess3.getModule2();
      if (i > 0 && hudmodule != null && hudmodule.getNasyschenie().isFlag3()) {
         int k = count - 10;

         for (int l = 0; l < 10; l++) {
            int i1 = count2 - l * 8 - 9;
            if (l * 2 + 1 <= j) {
               drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier5, i1, k, 9, 9);
            }

            if (l * 2 + 1 < i) {
               drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier7, i1, k, 9, 9);
            } else if (l * 2 + 1 == i) {
               drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier6, i1, k, 9, 9);
            }
         }
      }

      HandleInvoker.onObjectArray(inGameHud, drawContext, playerEntity, count, count2);
   }

   private static MutableText getMutableTextByStringStyleInt(String text2, Style style2, int count) {
      Style style = style2 == null ? Style.EMPTY : style2;
      if (count != 0) {
         style = style.withColor(count & 16777215);
      }

      return Text.literal(text2).setStyle(style);
   }

   private static MutableText getMutableTextByTextStyle(Text text2, Style style) {
      String s = getStringByText(text2);
      MutableText mutabletext = s != null && !s.isEmpty() ? Text.literal(s) : Text.empty();
      mutabletext.setStyle(style);
      return mutabletext;
   }

   private static Text getTextByTextStringIntBooleanArray(Text text4, String text5, int count, boolean[] flagArray) {
      if (flagArray[0]) {
         return text4;
      } else {
         String s = getStringByText(text4);
         Style style = text4.getStyle();
         if (s != null && !s.isBlank()) {
            flagArray[0] = true;
            String s1 = s.startsWith(" ") ? " " : "";
            MutableText mutabletext1 = getMutableTextByStringStyleInt(s1 + text5, style, count);

            for (Text text3 : text4.getSiblings()) {
               mutabletext1.append(text3);
            }

            return mutabletext1;
         } else {
            MutableText mutabletext = null;
            int i = 0;

            for (Text text : text4.getSiblings()) {
               Text text1 = getTextByTextStringIntBooleanArray(text, text5, count, flagArray);
               if (mutabletext == null && text1 != text) {
                  mutabletext = getMutableTextByTextStyle(text4, style);
                  int j = 0;

                  for (Text text2 : text4.getSiblings()) {
                     if (j == i) {
                        break;
                     }

                     mutabletext.append(text2);
                     j++;
                  }

                  mutabletext.append(text1);
               } else if (mutabletext != null) {
                  mutabletext.append(text1);
               }

               i++;
            }

            return (Text)(mutabletext != null ? mutabletext : text4);
         }
      }
   }

   private static boolean check() {
      HitResult hitresult = Feature.mc.crosshairTarget;
      if (hitresult == null) {
         return false;
      } else if (hitresult.getType() == Type.ENTITY) {
         return ((EntityHitResult)hitresult).getEntity() instanceof NamedScreenHandlerFactory;
      } else if (hitresult.getType() == Type.BLOCK) {
         BlockPos blockpos = ((BlockHitResult)hitresult).getBlockPos();
         ClientWorld clientworld = Feature.mc.world;
         return clientworld.getBlockState(blockpos).createScreenHandlerFactory(clientworld, blockpos) != null;
      } else {
         return false;
      }
   }

   @Hook(
      target = BossBarHud.class,
      method = "method_1796",
      desc = "(Lnet/minecraft/class_332;)V",
      getInjectPoint = InjectPoint.CANCELLABLE
   )
   public static boolean isBossBarHudDrawContext(BossBarHud bossBarHud, DrawContext drawContext) {
      NoRender norender = (NoRender)unsafeAccess2.getModule2();
      return norender == null || !norender.check12();
   }

   @Hook(
      method = "method_61980",
      desc = "(Lnet/minecraft/class_332;F)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextFloat(InGameHud inGameHud, DrawContext drawContext, float value) {
      NoRender norender = (NoRender)unsafeAccess2.getModule2();
      if (norender == null || !norender.check18()) {
         HandleInvoker.onObjectArray(inGameHud, drawContext, value);
      }
   }

   @Hook(
      method = "method_1731",
      desc = "(Lnet/minecraft/class_1297;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudEntity(InGameHud inGameHud, Entity entity2) {
      CleanHud cleanhud = (CleanHud)unsafeAccess.getModule2();
      if (cleanhud == null) {
         NoRender norender = (NoRender)unsafeAccess2.getModule2();
         if (norender == null || !norender.check6() && !norender.check3()) {
            HandleInvoker.onObjectArray(inGameHud, entity2);
         } else {
            inGameHud.vignetteDarkness = 0.0F;
         }
      }
   }

   private static Text getTextByTextStringBooleanArray(Text text3, String text4, boolean[] flagArray) {
      String s = getStringByText(text3);
      Style style = text3.getStyle();
      boolean flag = s != null && s.matches(".*\\d+.*");
      if (!flagArray[0] && flag) {
         flagArray[0] = true;
         MutableText mutabletext1 = Text.literal(getStringByStringString2(s, text4)).setStyle(style);

         for (Text text2 : text3.getSiblings()) {
            mutabletext1.append(text2);
         }

         return mutabletext1;
      } else {
         MutableText mutabletext = getMutableTextByTextStyle(text3, style);

         for (Text text : text3.getSiblings()) {
            if (!flagArray[0]) {
               Text text1 = getTextByTextStringBooleanArray(text, text4, flagArray);
               mutabletext.append(text1);
            } else {
               mutabletext.append(text);
            }
         }

         return mutabletext;
      }
   }

   private static Text getTextByTextString(Text text2, String text3) {
      if (text2 == null) {
         return Text.literal(text3);
      } else {
         boolean[] aboolean = new boolean[]{false};
         Text text = getTextByTextStringBooleanArray(text2, text3, aboolean);
         if (!aboolean[0] && text instanceof MutableText mutabletext) {
            mutabletext.append(Text.literal(" " + text3));
         }

         return text;
      }
   }

   @Hook(
      method = "method_1757",
      desc = "(Lnet/minecraft/class_332;Lnet/minecraft/class_266;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextScoreboardObjective(InGameHud inGameHud, DrawContext drawContext, ScoreboardObjective scoreboardObjective) {
      CleanHud cleanhud = (CleanHud)unsafeAccess.getModule2();
      if (cleanhud == null) {
         NoRender norender = (NoRender)unsafeAccess2.getModule2();
         if (norender == null || !norender.check4()) {
            Protect protect = (Protect)unsafeAccess6.getModule2();
            if (protect != null && protect.check4() && Feature.mc.world != null && scoreboardObjective != null) {
               Scoreboard scoreboard = Feature.mc.world.getScoreboard();
               Text text = scoreboardObjective.getDisplayName();
               HashMap hashmap = new HashMap();

               try {
                  String s = protect.getString2();
                  if (s != null && !s.isEmpty()) {
                     scoreboardObjective.setDisplayName(getTextByTextString(text, s));
                  }

                  String s1 = protect.getString();
                  String s2 = protect.getString3();
                  if (s1 != null && !s1.isEmpty() || s2 != null && !s2.isEmpty()) {
                     for (ScoreboardEntry scoreboardentry : scoreboard.getScoreboardEntries(scoreboardObjective)) {
                        Team team = scoreboard.getScoreHolderTeam(scoreboardentry.owner());
                        if (team != null) {
                           Text text1 = team.getPrefix();
                           if (text1 != null) {
                              String s3 = text1.getString();
                              Text text2 = null;
                              if (s1 != null && !s1.isEmpty() && s3.contains("Ранг:")) {
                                 String s4 = s1.startsWith(" ") ? s1 : " " + s1;
                                 text2 = getTextByTextStringStringInt(text1, "Ранг:", s4, protect.getInt());
                              } else if (s2 != null && !s2.isEmpty() && s3.contains("Токенов:")) {
                                 text2 = getTextByTextStringStringInt(text1, "Токенов:", s2, 0);
                              }

                              if (text2 != null) {
                                 hashmap.put(team, text1);
                                 team.setPrefix(text2);
                              }
                           }
                        }
                     }
                  }

                  HandleInvoker.onObjectArray(inGameHud, drawContext, scoreboardObjective);
               } finally {
                  scoreboardObjective.setDisplayName(text);

                  for (Entry entry : (Iterable<Entry>)(hashmap.entrySet())) {
                     ((Team)entry.getKey()).setPrefix((Text)entry.getValue());
                  }
               }
            } else {
               HandleInvoker.onObjectArray(inGameHud, drawContext, scoreboardObjective);
            }
         }
      }
   }

   @Hook(
      method = "method_1749",
      desc = "(Lnet/minecraft/class_332;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContext2(InGameHud inGameHud, DrawContext drawContext) {
      CleanHud cleanhud = (CleanHud)unsafeAccess.getModule2();
      if (cleanhud == null) {
         HandleInvoker.onObjectArray(inGameHud, drawContext);
      }
   }

   @Hook(
      method = "method_1753",
      desc = "(Lnet/minecraft/class_332;Lnet/minecraft/class_9779;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onInGameHudDrawContextRenderTickCounter4(InGameHud inGameHud, DrawContext drawContext, RenderTickCounter renderTickCounter) {
      HandleInvoker.onObjectArray(inGameHud, drawContext, renderTickCounter);
      renderModuleHud(drawContext, renderTickCounter);
   }

   public static void renderModuleHud(DrawContext drawContext, RenderTickCounter renderTickCounter) {
      if (!PanicModule.isFlag()) {
         ModuleDispatcher moduledispatcher = UnsafeAccess.getModuleDispatcher();
         if (moduledispatcher != null) {
            if (ResourceManagerHooks.isFlag3()) {
               if (!StreamBypass.check7()) {
                  moduledispatcher.render(drawContext, renderTickCounter);
               }

               Tracers tracers = (Tracers)unsafeAccess4.getModule2();
               if (tracers != null && !StreamBypass.check7()) {
                  tracers.render2(drawContext);
               }

               HudModule hudmodule = (HudModule)unsafeAccess3.getModule2();
               if (hudmodule != null && !(Feature.mc.currentScreen instanceof ChatScreen) && !StreamBypass.check4()) {
                  UiContext.getInstance().render(drawContext, 1.0F);
               }

               if (!Bloom.isFlag2()) {
                  try {
                     RenderSystem.disableBlend();
                     RenderSystem.disableDepthTest();
                     RenderSystem.resetTextureMatrix();
                     Bloom.setFramebuffer(Feature.mc.getFramebuffer());
                     Feature.mc.getFramebuffer().beginWrite(true);
                  } catch (Throwable throwable1) {
                  }
               }

               if (!Saturation.isFlag2()) {
                  try {
                     RenderSystem.disableBlend();
                     RenderSystem.disableDepthTest();
                     RenderSystem.resetTextureMatrix();
                     Saturation.setFramebuffer(Feature.mc.getFramebuffer());
                     Feature.mc.getFramebuffer().beginWrite(true);
                  } catch (Throwable throwable) {
                  }
               }
            }
         }
      }
   }

   private static String getStringByText(Text text2) {
      if (text2 == null) {
         return null;
      } else {
         return text2.getContent() instanceof Literal literal ? literal.string() : null;
      }
   }

   @Hook(
      method = "method_1737",
      desc = "()Lnet/minecraft/class_1657;",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static PlayerEntity getPlayerEntityByInGameHud(InGameHud inGameHud) {
      FreeCam freecam = (FreeCam)unsafeAccess7.getModule2();
      return (PlayerEntity)(freecam != null && freecam.check3() ? Feature.mc.player : (PlayerEntity)HandleInvoker.getObjectByObjectArray2(inGameHud));
   }

   private static Text getTextByTextStringStringIntBooleanArrayBooleanArray(Text text4, String text5, String text6, int count, boolean[] flagArray, boolean[] flagArray2) {
      String s = getStringByText(text4);
      Style style = text4.getStyle();
      if (!flagArray[0] && s != null) {
         int i = s.indexOf(text5);
         if (i >= 0) {
            flagArray[0] = true;
            String s1 = s.substring(0, i + text5.length());
            String s2 = s.substring(i + text5.length());
            MutableText mutabletext1 = Text.literal(s1).setStyle(style);
            if (!s2.isBlank()) {
               mutabletext1.append(getMutableTextByStringStyleInt(getStringByStringString(s1, text6), style, count));
               flagArray2[0] = true;
            }

            for (Text text3 : text4.getSiblings()) {
               mutabletext1.append(getTextByTextStringIntBooleanArray(text3, text6, count, flagArray2));
            }

            return mutabletext1;
         }
      }

      if (flagArray[0]) {
         return !flagArray2[0] ? getTextByTextStringIntBooleanArray(text4, text6, count, flagArray2) : text4;
      } else {
         MutableText mutabletext = null;
         int j = 0;

         for (Text text : text4.getSiblings()) {
            Text text1 = getTextByTextStringStringIntBooleanArrayBooleanArray(text, text5, text6, count, flagArray, flagArray2);
            if (mutabletext == null && text1 != text) {
               mutabletext = getMutableTextByTextStyle(text4, style);
               int k = 0;

               for (Text text2 : text4.getSiblings()) {
                  if (k == j) {
                     break;
                  }

                  mutabletext.append(text2);
                  k++;
               }

               mutabletext.append(text1);
            } else if (mutabletext != null) {
               if (flagArray[0] && !flagArray2[0]) {
                  mutabletext.append(getTextByTextStringIntBooleanArray(text, text6, count, flagArray2));
               } else {
                  mutabletext.append(text1);
               }
            }

            j++;
         }

         return (Text)(mutabletext != null ? mutabletext : text4);
      }
   }

   private static Text getTextByTextStringStringInt(Text text2, String text3, String text4, int count) {
      if (text2 == null) {
         return null;
      } else {
         boolean[] aboolean = new boolean[]{false};
         boolean[] aboolean1 = new boolean[]{false};
         Text text = getTextByTextStringStringIntBooleanArrayBooleanArray(text2, text3, text4, count, aboolean, aboolean1);
         return !aboolean[0] ? null : text;
      }
   }

   private static String getStringByStringString2(String text2, String text3) {
      return text2.replaceFirst("\\d+(?:[.,]\\d+)*", Matcher.quoteReplacement(text3));
   }
}
