package client.module.visual;

import client.module.Category;
import client.module.Module;
import client.module.movement.FreeCam;
import client.render.HudRenderContext;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.MultilistSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.StringParts;
import client.util.UnsafeAccess;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Enhancer extends Module {
   private static final UnsafeAccess<FreeCam> unsafeAccess = new UnsafeAccess<>(FreeCam.class);
   private static String text = "Бесконечный чат";
   private static String text2 = "Не очищать чат";
   private static String text3 = "Группировать дубликаты";
   private MultilistSetting chat;
   private static String text4 = "Подсвечивать друга";
   private static String text5 = "Оптимизация";
   private static String text6 = "Показывать пинг";
   private MultilistSetting tab;
   public static String text7 = "Масштаб";
   public static String text8 = "Снизу";
   public static String text9 = "Сверху";
   public static String text10 = "Сжатие";
   public static String text11 = "Исчезание";
   private static String text12 = "FPS";
   private static String text13 = "Энтити";
   private static String text14 = "Направление";
   private static String text15 = "Координаты";
   private static String text16 = "Портал-конвертер";
   private static String text17 = "Биом";
   private static String text18 = "Чанк";
   private static final Pattern pattern = Pattern.compile("(\\d+/\\d+)");
   private BooleanSetting chistyyF3;
   private MultilistSetting polyaF3;
   private BooleanSetting animationVvoda;
   private SliderSetting dlitelnostVvoda;
   private ListSetting typeAnimacii;

   public Enhancer() {
      super("Enhancer", Category.VISUAL);
      MultilistSetting multilistsetting = new MultilistSetting("", "", List.of(text, text2, text3), List.of(text, text2, text3));
      multilistsetting.setName("Чат");
      multilistsetting.setDescription("Настройки чата");
      this.chat = multilistsetting;
      multilistsetting = new MultilistSetting("", "", List.of(text4, text5, text6), List.of(text4));
      multilistsetting.setName("Таб");
      multilistsetting.setDescription("Настройки списка игроков");
      this.tab = multilistsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Чистый F3");
      booleansetting.setDescription("Отключает дефолтный экран отладки и рисует компактный оверлей при открытом F3");
      this.chistyyF3 = booleansetting;
      multilistsetting = new MultilistSetting("", "", List.of(text12, text13, text14, text15, text16, text17, text18), List.of(text12, text15));
      multilistsetting.setName("Поля F3");
      multilistsetting.setDescription("Какие строки показывать в чистом F3");
      this.polyaF3 = multilistsetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Анимация ввода");
      booleansetting1.setDescription("Анимация символов при печати в текстовых полях");
      this.animationVvoda = booleansetting1;
      SliderSetting slidersetting = new SliderSetting("", "", 180.0, 30.0, 300.0, 10.0, StringParts.join(new String[]{"м", "c"}), 0);
      slidersetting.setName("Длительность ввода");
      slidersetting.setDescription("Время анимации появления/исчезновения символа");
      this.dlitelnostVvoda = slidersetting;
      ListSetting listsetting = new ListSetting("", "", List.of(text7, text8, text9, text10, text11), List.of(text7), false);
      listsetting.setName("Тип анимации");
      listsetting.setDescription("Стиль появления и исчезновения символов");
      this.typeAnimacii = listsetting;
      this.dlitelnostVvoda.setVisibleWhen(this.animationVvoda::isFlag3);
      this.typeAnimacii.setVisibleWhen(this.animationVvoda::isFlag3);
      this.polyaF3.setVisibleWhen(this.chistyyF3::isFlag3);
      this.addSettings(new Setting[]{this.chat, this.tab, this.chistyyF3, this.polyaF3, this.animationVvoda, this.dlitelnostVvoda, this.typeAnimacii});
   }

   public boolean check3() {
      return this.tab.isString(text5);
   }

   public boolean check4() {
      return this.chat.isString(text2);
   }

   public boolean check5() {
      return this.chat.isString(text3);
   }

   public String getString() {
      return this.typeAnimacii.getString2();
   }

   public boolean check6() {
      return this.tab.isString(text4);
   }

   @Override
   public void onDisable() {
   }

   public boolean check7() {
      return this.animationVvoda.isFlag3();
   }

   private static Vec3d getVec3d() {
      FreeCam freecam = (FreeCam)unsafeAccess.getModule2();
      return freecam != null ? freecam.getVec3d() : null;
   }

   private List getListByMinecraftClient(MinecraftClient minecraftClient) {
      ArrayList arraylist = new ArrayList();
      ClientPlayerEntity clientplayerentity = minecraftClient.player;
      ClientWorld clientworld = minecraftClient.world;
      if (clientplayerentity != null && clientworld != null) {
         BlockPos blockpos = clientplayerentity.getBlockPos();
         if (this.polyaF3.isString(text12)) {
            arraylist.add(minecraftClient.getCurrentFps() + " fps");
         }

         if (this.polyaF3.isString(text13)) {
            String s = minecraftClient.worldRenderer.getEntitiesDebugString();
            Matcher matcher = pattern.matcher(s);
            arraylist.add("E: " + (matcher.find() ? matcher.group(1) : s));
         }

         if (this.polyaF3.isString(text14)) {
            Direction direction = clientplayerentity.getHorizontalFacing();
            float f1 = MathHelper.wrapDegrees(clientplayerentity.getYaw());
            float f = MathHelper.wrapDegrees(clientplayerentity.getPitch());
            arraylist.add(String.format(Locale.ROOT, "%s (%.1f / %.1f)", direction.asString().toLowerCase(Locale.ROOT), f1, f));
         }

         if (this.polyaF3.isString(text15)) {
            Vec3d vec3d = getVec3d();
            double d2 = vec3d != null ? vec3d.x : clientplayerentity.getX();
            double d0 = vec3d != null ? vec3d.y : clientplayerentity.getY();
            double d1 = vec3d != null ? vec3d.z : clientplayerentity.getZ();
            arraylist.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", d2, d0, d1));
            if (this.polyaF3.isString(text16)) {
               if (clientworld.getRegistryKey() == World.NETHER) {
                  arraylist.add(String.format(Locale.ROOT, " -> OW: %.0f, %.0f", d2 * 8.0, d1 * 8.0));
               } else if (clientworld.getRegistryKey() == World.OVERWORLD) {
                  arraylist.add(String.format(Locale.ROOT, " -> N: %.0f, %.0f", d2 / 8.0, d1 / 8.0));
               }
            }
         }

         if (this.polyaF3.isString(text17)) {
            String s1 = clientworld.getBiome(blockpos).getKey().map(Enhancer::getStringByRegistryKey).orElse("unknown");
            arraylist.add("Biome: " + getStringByString(s1));
         }

         if (this.polyaF3.isString(text18)) {
            int j = blockpos.getX() & 15;
            int k = blockpos.getY() & 15;
            int l = blockpos.getZ() & 15;
            int i1 = blockpos.getX() >> 4;
            int i = blockpos.getZ() >> 4;
            arraylist.add(String.format(Locale.ROOT, "Chunk: %d %d %d (%d %d)", j, k, l, i1, i));
         }

         return arraylist;
      } else {
         return arraylist;
      }
   }

   public float getFloat() {
      return this.dlitelnostVvoda.getValueAsFloat();
   }

   private static String getStringByRegistryKey(RegistryKey registryKey) {
      return registryKey.getValue().getPath();
   }

   @Override
   public void onHudRenderContext(HudRenderContext hudRenderContext) {
      if (this.chistyyF3.isFlag3()) {
         MinecraftClient minecraftclient = this.client();
         if (minecraftclient != null && minecraftclient.player != null && minecraftclient.world != null) {
            if (!minecraftclient.options.hudHidden) {
               if (minecraftclient.getDebugHud().shouldShowDebugHud()) {
                  List<String> list = this.getListByMinecraftClient(minecraftclient);
                  if (!list.isEmpty()) {
                     DrawContext drawcontext = hudRenderContext.getDrawContext();
                     TextRenderer textrenderer = minecraftclient.textRenderer;
                     byte b0 = 2;
                     int i = 2;
                     int j = 9 + 1;

                     for (String s : list) {
                        int k = textrenderer.getWidth(s);
                        drawcontext.fill(b0 - 1, i - 1, b0 + k + 1, i + 9 - 1, -1873784752);
                        drawcontext.drawText(textrenderer, s, b0, i, 14737632, false);
                        i += j;
                     }
                  }
               }
            }
         }
      }
   }

   public boolean check8() {
      return this.chat.isString(text);
   }

   private static String getStringByString(String text) {
      String[] astring = text.split("_");
      StringBuilder stringbuilder = new StringBuilder(text.length());

      for (int i = 0; i < astring.length; i++) {
         String s = astring[i];
         if (!s.isEmpty()) {
            if (i > 0) {
               stringbuilder.append(' ');
            }

            stringbuilder.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
         }
      }

      return stringbuilder.toString();
   }

   public boolean check9() {
      return this.chistyyF3.isFlag3();
   }

   @Override
   public void onEnable() {
   }

   public boolean check10() {
      return this.tab.isString(text6);
   }
}
