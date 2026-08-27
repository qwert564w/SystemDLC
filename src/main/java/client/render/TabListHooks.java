package client.render;

import client.api.Hook;
import client.api.HookClass;
import client.data.PingSample;
import client.data.SystemFriend;
import client.enums.InjectPoint;
import client.module.Feature;
import client.module.visual.Enhancer;
import client.util.ReflectionCache;
import client.util.ScoreboardRow;
import client.util.UnsafeAccess;
import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.ScoreboardCriterion.RenderType;
import net.minecraft.scoreboard.number.NumberFormat;
import net.minecraft.scoreboard.number.StyledNumberFormat;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

@HookClass(PlayerListHud.class)
public class TabListHooks {
   private static final Map<UUID, PingSample> map = new Object2ObjectOpenHashMap();
   private static final HashSet<UUID> hashSet = new HashSet<>();
   private static final ArrayList<PlayerListEntry> list = new ArrayList<>();
   private static final long time = ReflectionCache.getLongByClassClass2(PlayerListHud.class, boolean.class);
   private static final long time2 = ReflectionCache.getLongByClassClassInt(PlayerListHud.class, Text.class, 0);
   private static final long time3 = ReflectionCache.getLongByClassClassInt(PlayerListHud.class, Text.class, 1);
   private static final UnsafeAccess<Enhancer> unsafeAccess = new UnsafeAccess<>(Enhancer.class);
   private static final Identifier identifier = Identifier.ofVanilla("icon/ping_unknown");
   private static final Identifier identifier2 = Identifier.ofVanilla("icon/ping_1");
   private static final Identifier identifier3 = Identifier.ofVanilla("icon/ping_2");
   private static final Identifier identifier4 = Identifier.ofVanilla("icon/ping_3");
   private static final Identifier identifier5 = Identifier.ofVanilla("icon/ping_4");
   private static final Identifier identifier6 = Identifier.ofVanilla("icon/ping_5");
   private static final Identifier identifier7 = Identifier.ofVanilla("hud/heart/container_blinking");
   private static final Identifier identifier8 = Identifier.ofVanilla("hud/heart/container");
   private static final Identifier identifier9 = Identifier.ofVanilla("hud/heart/full_blinking");
   private static final Identifier identifier10 = Identifier.ofVanilla("hud/heart/half_blinking");
   private static final Identifier identifier11 = Identifier.ofVanilla("hud/heart/absorbing_full_blinking");
   private static final Identifier identifier12 = Identifier.ofVanilla("hud/heart/full");
   private static final Identifier identifier13 = Identifier.ofVanilla("hud/heart/absorbing_half_blinking");
   private static final Identifier identifier14 = Identifier.ofVanilla("hud/heart/half");
   private static final Comparator<PlayerListEntry> comparator = (var0, var1) -> {
      int i = Integer.compare(-var0.getListOrder(), -var1.getListOrder());
      if (i != 0) {
         return i;
      } else {
         int j = var0.getGameMode() == GameMode.SPECTATOR ? 1 : 0;
         int k = var1.getGameMode() == GameMode.SPECTATOR ? 1 : 0;
         i = Integer.compare(j, k);
         if (i != 0) {
            return i;
         } else {
            Team team = var0.getScoreboardTeam();
            String s = team == null ? "" : team.getName();
            Team team1 = var1.getScoreboardTeam();
            String s1 = team1 == null ? "" : team1.getName();
            i = s.compareTo(s1);
            return i != 0 ? i : var0.getProfile().getName().compareToIgnoreCase(var1.getProfile().getName());
         }
      }
   };

   private static Text getTextByPlayerListHud(PlayerListHud playerListHud) {
      return (Text)ReflectionCache.getObjectByObjectLong(playerListHud, time2);
   }

   private static List getListByMinecraftClient(MinecraftClient minecraftClient) {
      list.clear();
      list.addAll(minecraftClient.player.networkHandler.getListedPlayerListEntries());
      list.sort(comparator);
      int i = list.size();
      if (i > 80) {
         list.subList(80, i).clear();
      }

      return list;
   }

   private static void onMinecraftClientIntIntIntUUIDDrawContextIntInt(
      MinecraftClient minecraftClient, int count, int count2, int count3, UUID uUID, DrawContext drawContext, int count4, int count5
   ) {
      PingSample pingsample = map.computeIfAbsent(uUID, var1x -> new PingSample(count4));
      pingsample.onIntLong(count4, (long)count5);
      int i = MathHelper.ceilDiv(Math.max(count4, pingsample.value2), 2);
      int j = Math.max(count4, Math.max(pingsample.value2, 20)) / 2;
      boolean flag = pingsample.isLong((long)count5);
      if (i > 0) {
         int k = MathHelper.floor(Math.min((float)(count3 - count2 - 4) / j, 9.0F));
         if (k <= 3) {
            float f = MathHelper.clamp(count4 / 20.0F, 0.0F, 1.0F);
            int l = (int)((1.0F - f) * 255.0F) << 16 | (int)(f * 255.0F) << 8;
            float f1 = count4 / 2.0F;
            MutableText mutabletext = Text.translatable("multiplayer.player.list.hp", new Object[]{f1});
            MutableText mutabletext1;
            if (count3 - minecraftClient.textRenderer.getWidth(mutabletext) >= count2) {
               mutabletext1 = mutabletext;
            } else {
               mutabletext1 = Text.literal(Float.toString(f1));
            }

            drawContext.drawTextWithShadow(minecraftClient.textRenderer, mutabletext1, (count3 + count2 - minecraftClient.textRenderer.getWidth(mutabletext1)) / 2, count, l);
         } else {
            Identifier identifierx = flag ? identifier7 : identifier8;

            for (int i1 = i; i1 < j; i1++) {
               drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifierx, count2 + i1 * k, count, 9, 9);
            }

            for (int j1 = 0; j1 < i; j1++) {
               drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifierx, count2 + j1 * k, count, 9, 9);
               if (flag) {
                  if (j1 * 2 + 1 < pingsample.value2) {
                     drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier9, count2 + j1 * k, count, 9, 9);
                  }

                  if (j1 * 2 + 1 == pingsample.value2) {
                     drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier10, count2 + j1 * k, count, 9, 9);
                  }
               }

               if (j1 * 2 + 1 < count4) {
                  drawContext.drawGuiTexture(RenderLayer::getGuiTextured, j1 >= 10 ? identifier11 : identifier12, count2 + j1 * k, count, 9, 9);
               }

               if (j1 * 2 + 1 == count4) {
                  drawContext.drawGuiTexture(RenderLayer::getGuiTextured, j1 >= 10 ? identifier13 : identifier14, count2 + j1 * k, count, 9, 9);
               }
            }
         }
      }
   }

   private static void onMinecraftClientScoreboardObjectiveIntScoreboardRowIntIntUUIDDrawContextInt(
      MinecraftClient minecraftClient, ScoreboardObjective scoreboardObjective, int count, ScoreboardRow scoreboardRow, int count2, int count3, UUID uUID, DrawContext drawContext, int count4
   ) {
      if (scoreboardObjective.getRenderType() == RenderType.HEARTS) {
         onMinecraftClientIntIntIntUUIDDrawContextIntInt(minecraftClient, count, count2, count3, uUID, drawContext, scoreboardRow.score(), count4);
      } else if (scoreboardRow.formattedScore() != null) {
         drawContext.drawTextWithShadow(minecraftClient.textRenderer, scoreboardRow.formattedScore(), count3 - scoreboardRow.scoreWidth(), count, 16777215);
      }
   }

   private static Text getTextByPlayerListHud2(PlayerListHud playerListHud) {
      return (Text)ReflectionCache.getObjectByObjectLong(playerListHud, time3);
   }

   @Hook(
      method = "method_1921",
      desc = "(Z)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onPlayerListHudBoolean(PlayerListHud playerListHud, boolean flag) {
      UnsafeAccess.unsafe.putBoolean(playerListHud, time, flag);
      if (flag && Feature.mc.player != null) {
         List<PlayerListEntry> listx = getListByMinecraftClient(Feature.mc);
         MutableText mutabletext = Texts.join(listx, Text.literal(", "), playerListHud::getPlayerName);
         Feature.mc.getNarratorManager().narrate(Text.translatable("multiplayer.player.list.narration", new Object[]{mutabletext}));
      }

      if (!flag) {
         map.clear();
      }
   }

   @Hook(
      method = "method_1919",
      desc = "(Lnet/minecraft/class_332;ILnet/minecraft/class_269;Lnet/minecraft/class_266;)V",
      getInjectPoint = InjectPoint.REPLACE
   )
   public static void onPlayerListHudDrawContextIntScoreboardScoreboardObjective(
      PlayerListHud playerListHud, DrawContext drawContext, int count, Scoreboard scoreboard2, ScoreboardObjective scoreboardObjective
   ) {
      if (isPlayerListHud(playerListHud)) {
         Enhancer enhancer = (Enhancer)unsafeAccess.getModule2();
         List<PlayerListEntry> listx = getListByMinecraftClient(Feature.mc);
         ArrayList arraylist = new ArrayList(listx.size());
         int i = Feature.mc.textRenderer.getWidth(" ");
         int j = 0;
         int k = 0;

         for (PlayerListEntry playerlistentry : (Iterable<PlayerListEntry>)(listx)) {
            Text text = playerListHud.getPlayerName(playerlistentry);
            j = Math.max(j, Feature.mc.textRenderer.getWidth(text));
            int l = 0;
            MutableText mutabletext = null;
            int i1 = 0;
            if (scoreboardObjective != null) {
               ScoreHolder scoreholder = ScoreHolder.fromProfile(playerlistentry.getProfile());
               ReadableScoreboardScore readablescoreboardscore = scoreboard2.getScore(scoreholder, scoreboardObjective);
               if (readablescoreboardscore != null) {
                  l = readablescoreboardscore.getScore();
               }

               if (scoreboardObjective.getRenderType() != RenderType.HEARTS) {
                  NumberFormat numberformat = scoreboardObjective.getNumberFormatOr(StyledNumberFormat.YELLOW);
                  mutabletext = ReadableScoreboardScore.getFormattedScore(readablescoreboardscore, numberformat);
                  i1 = Feature.mc.textRenderer.getWidth(mutabletext);
                  k = Math.max(k, i1 > 0 ? i + i1 : 0);
               }
            }

            arraylist.add(new ScoreboardRow(text, l, mutabletext, i1));
         }

         if (!map.isEmpty()) {
            HashSet hashset = hashSet;
            hashset.clear();

            for (PlayerListEntry playerlistentry2 : listx) {
               hashset.add(playerlistentry2.getProfile().getId());
            }

            map.keySet().removeIf(var1x -> !hashset.contains(var1x));
         }

         int j3 = listx.size();
         int k3 = j3;

         int l3;
         for (l3 = 1; k3 > 20; k3 = (j3 + l3 - 1) / l3) {
            l3++;
         }

         boolean flag4 = Feature.mc.isInSingleplayer() || Feature.mc.getNetworkHandler().getConnection().isEncrypted();
         boolean flag5 = enhancer != null && enhancer.check10();
         int i4 = 13;
         if (flag5) {
            for (PlayerListEntry playerlistentry3 : listx) {
               int l4 = playerlistentry3.getLatency();
               String s = l4 == 0 ? "?" : String.valueOf(l4);
               i4 = Math.max(i4, Feature.mc.textRenderer.getWidth(s) + 4);
            }
         }

         int j4;
         if (scoreboardObjective != null) {
            j4 = scoreboardObjective.getRenderType() == RenderType.HEARTS ? 90 : k;
         } else {
            j4 = 0;
         }

         int k4 = Math.min(l3 * ((flag4 ? 9 : 0) + j + j4 + i4), count - 50) / l3;
         int i5 = count / 2 - (k4 * l3 + (l3 - 1) * 5) / 2;
         int j5 = 10;
         int j1 = k4 * l3 + (l3 - 1) * 5;
         boolean flag = enhancer != null && enhancer.check3();
         Text text1 = getTextByPlayerListHud2(playerListHud);
         Text text2 = getTextByPlayerListHud(playerListHud);
         List<OrderedText> list1 = null;
         if (text1 != null && !flag) {
            list1 = Feature.mc.textRenderer.wrapLines(text1, count - 50);

            for (OrderedText orderedtext : (Iterable<OrderedText>)(list1)) {
               j1 = Math.max(j1, Feature.mc.textRenderer.getWidth(orderedtext));
            }
         }

         List<OrderedText> list2 = null;
         if (text2 != null && !flag) {
            list2 = Feature.mc.textRenderer.wrapLines(text2, count - 50);

            for (OrderedText orderedtext1 : (Iterable<OrderedText>)(list2)) {
               j1 = Math.max(j1, Feature.mc.textRenderer.getWidth(orderedtext1));
            }
         }

         if (list1 != null) {
            drawContext.fill(count / 2 - j1 / 2 - 1, j5 - 1, count / 2 + j1 / 2 + 1, j5 + list1.size() * 9, Integer.MIN_VALUE);

            for (OrderedText orderedtext2 : list1) {
               int k1 = Feature.mc.textRenderer.getWidth(orderedtext2);
               drawContext.drawTextWithShadow(Feature.mc.textRenderer, orderedtext2, count / 2 - k1 / 2, j5, -1);
               j5 += 9;
            }

            j5++;
         }

         drawContext.fill(count / 2 - j1 / 2 - 1, j5 - 1, count / 2 + j1 / 2 + 1, j5 + k3 * 9, Integer.MIN_VALUE);
         int k5 = Feature.mc.options.getTextBackgroundColor(553648127);
         SystemFriend systemfriend = SystemFriend.getInstance();
         int l5 = Feature.mc.inGameHud.getTicks();

         for (int l1 = 0; l1 < j3; l1++) {
            int i2 = l1 / k3;
            int j2 = l1 % k3;
            int k2 = i5 + i2 * k4 + i2 * 5;
            int l2 = j5 + j2 * 9;
            PlayerListEntry playerlistentry1 = (PlayerListEntry)listx.get(l1);
            boolean flag1 = playerlistentry1 != null && systemfriend.isString(playerlistentry1.getProfile().getName());
            boolean flag2 = flag1 && enhancer != null && enhancer.check6();
            int i3 = flag2 ? -2147483393 : k5;
            drawContext.fill(k2, l2, k2 + k4, l2 + 8, i3);
            ScoreboardRow scoreboardrow = (ScoreboardRow)arraylist.get(l1);
            GameProfile gameprofile = playerlistentry1.getProfile();
            if (flag4) {
               PlayerEntity playerentity = Feature.mc.world.getPlayerByUuid(gameprofile.getId());
               boolean flag3 = playerentity != null && LivingEntityRenderer.shouldFlipUpsideDown(playerentity);
               PlayerSkinDrawer.draw(drawContext, playerlistentry1.getSkinTextures().texture(), k2, l2, 8, playerlistentry1.shouldShowHat(), flag3, -1);
               k2 += 9;
            }

            drawContext.drawTextWithShadow(
               Feature.mc.textRenderer, scoreboardrow.name(), k2, l2, playerlistentry1.getGameMode() == GameMode.SPECTATOR ? -1862270977 : -1
            );
            if (scoreboardObjective != null && playerlistentry1.getGameMode() != GameMode.SPECTATOR) {
               int j6 = k2 + j + 1;
               int k6 = j6 + j4;
               if (k6 - j6 > 5) {
                  onMinecraftClientScoreboardObjectiveIntScoreboardRowIntIntUUIDDrawContextInt(
                     Feature.mc, scoreboardObjective, l2, scoreboardrow, j6, k6, gameprofile.getId(), drawContext, l5
                  );
               }
            }

            onMinecraftClientDrawContextIntIntIntPlayerListEntryEnhancer(Feature.mc, drawContext, k4, k2 - (flag4 ? 9 : 0), l2, playerlistentry1, enhancer);
         }

         if (list2 != null) {
            j5 += k3 * 9 + 1;
            drawContext.fill(count / 2 - j1 / 2 - 1, j5 - 1, count / 2 + j1 / 2 + 1, j5 + list2.size() * 9, Integer.MIN_VALUE);

            for (OrderedText orderedtext3 : list2) {
               int i6 = Feature.mc.textRenderer.getWidth(orderedtext3);
               drawContext.drawTextWithShadow(Feature.mc.textRenderer, orderedtext3, count / 2 - i6 / 2, j5, -1);
               j5 += 9;
            }
         }
      }
   }

   private static void onMinecraftClientDrawContextIntIntIntPlayerListEntryEnhancer(
      MinecraftClient minecraftClient, DrawContext drawContext, int count, int count2, int count3, PlayerListEntry playerListEntry, Enhancer enhancer
   ) {
      boolean flag = enhancer != null && enhancer.check10();
      if (flag) {
         int i = playerListEntry.getLatency();
         String s = i == 0 ? "?" : String.valueOf(i);
         int j;
         if (i <= 0) {
            j = -5592406;
         } else if (i < 75) {
            j = -16711936;
         } else if (i < 150) {
            j = -11141291;
         } else if (i < 300) {
            j = -256;
         } else if (i < 600) {
            j = -43691;
         } else {
            j = -65536;
         }

         int k = minecraftClient.textRenderer.getWidth(s);
         drawContext.getMatrices().push();
         drawContext.getMatrices().translate(0.0F, 0.0F, 100.0F);
         drawContext.drawTextWithShadow(minecraftClient.textRenderer, s, count2 + count - k - 1, count3, j);
         drawContext.getMatrices().pop();
      } else {
         Identifier identifierx;
         if (playerListEntry.getLatency() < 0) {
            identifierx = identifier;
         } else if (playerListEntry.getLatency() < 150) {
            identifierx = identifier6;
         } else if (playerListEntry.getLatency() < 300) {
            identifierx = identifier5;
         } else if (playerListEntry.getLatency() < 600) {
            identifierx = identifier4;
         } else if (playerListEntry.getLatency() < 1000) {
            identifierx = identifier3;
         } else {
            identifierx = identifier2;
         }

         drawContext.getMatrices().push();
         drawContext.getMatrices().translate(0.0F, 0.0F, 100.0F);
         drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifierx, count2 + count - 11, count3, 10, 8);
         drawContext.getMatrices().pop();
      }
   }

   private static boolean isPlayerListHud(PlayerListHud playerListHud) {
      return UnsafeAccess.unsafe.getBoolean(playerListHud, time);
   }
}
