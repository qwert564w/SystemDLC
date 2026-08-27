package client.gui.hud;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.ChoiceOption;
import client.data.ThemeConfig;
import client.enums.ServerMode;
import client.enums.ThemePalette;
import client.gui.widget.RenderElement;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.module.player.Protect;
import client.render.ItemIconCache;
import client.render.RoundedTextureShader;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.Setting;
import client.util.AttackRecord;
import client.util.HealthTracker;
import client.util.Interpolation;
import client.util.ItemIcons;
import client.util.MathUtil;
import client.util.UnsafeAccess;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class TargetHudElement extends RenderElement {
   private static final float value271 = 175.0F;
   private static final float value272 = 54.0F;
   private static final float value273 = 8.0F;
   private static final float value274 = 12.0F;
   private static final float value275 = 54.0F;
   private static final float value276 = 45.0F;
   private static final float value277 = 6.0F;
   private static final float value278 = 6.0F;
   private static final float value279 = 18.0F;
   private static final float value280 = 19.0F;
   private static final float value281 = 4.0F;
   private static final float value282 = 4.0F;
   private static final float value283 = 7.0F;
   private static final float value284 = 8.0F;
   private static final float value285 = 9.0F;
   private static final float value286 = 8.0F;
   private static final float value287 = 4.0F;
   private static final float value288 = 3.0F;
   private static final float value289 = 8.0F;
   private static final float value290 = 4.0F;
   private static final float value291 = 10.0F;
   private static final float value292 = 9.0F;
   private static final String text = "HP";
   private static final float value293 = 0.5F;
   private static final float value294 = 0.66F;
   private static final float value295 = 0.38F;
   private static final float value296 = 0.22F;
   private static final float value297 = 0.9F;
   private static final float value298 = 0.55F;
   private static final float value299 = 8.0F;
   private static final float value300 = 235.0F;
   private static final float value301 = 0.1F;
   private static final float value302 = 0.05F;
   private static final float value303 = 0.001F;
   private static final String text2 = "...";
   private final BooleanSetting showBronyu;
   private final BooleanSetting showGolovu;
   private final ChoiceSetting polozhenieGolovy;
   private final ChoiceSetting indikatorZdorovya;
   private static final UnsafeAccess<Protect> unsafeAccess2 = new UnsafeAccess<>(Protect.class);
   private final Interpolation interpolation2;
   private long time;
   private float value304;
   private float value305;
   private float value306;
   private float value307;
   private float value308;
   private boolean flag3;
   private PlayerEntity playerEntity;
   private final ItemStack[] itemStackArray;
   private final ItemStack[] itemStackArray2;

   public TargetHudElement() {
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать броню");
      booleansetting.setDescription("Отображать броню и предметы в руках цели.");
      this.showBronyu = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Показывать голову");
      booleansetting.setDescription("Отображать блок c головой цели.");
      this.showGolovu = booleansetting;
      ChoiceSetting choicesetting = new ChoiceSetting(
         "", "", new ChoiceOption("Слева", CategoryType.ALIGN_LEFT), new ChoiceOption("Справа", CategoryType.ALIGN_RIGHT), false
      );
      choicesetting.setName("Положение головы");
      choicesetting.setDescription("Расположение блока c головой относительно никнейма.");
      this.polozhenieGolovy = choicesetting;
      ChoiceSetting choicesetting1 = new ChoiceSetting("", "", new ChoiceOption("Иконка"), new ChoiceOption("HP"), false);
      choicesetting1.setName("Индикатор здоровья");
      choicesetting1.setDescription("Что показывать рядом c числом здоровья: иконку сердца или подпись HP.");
      this.indikatorZdorovya = choicesetting1;
      this.interpolation2 = new Interpolation();
      this.time = -1L;
      this.value306 = 1.0F;
      this.value307 = 1.0F;
      this.value308 = 235.0F;
      this.itemStackArray = new ItemStack[4];
      this.itemStackArray2 = new ItemStack[2];
      this.onSettingArray(new Setting[]{this.showBronyu, this.showGolovu, this.polozhenieGolovy, this.indikatorZdorovya});
   }

   @Override
   public String getString() {
      return "ТаргетХуд";
   }

   private static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      float f = value2 < value ? 0.05F : 0.1F;
      return Interpolation.getFloatByFloatFloatFloatFloat2(value2, value, value3, f);
   }

   private PlayerEntity getPlayerEntity() {
      PlayerEntity playerentity = AttackRecord.getPlayerEntity();
      if (playerentity != null) {
         this.playerEntity = playerentity;
      } else if (this.check4()) {
         this.playerEntity = this.getPlayerEntity2();
      }

      return this.playerEntity;
   }

   public BooleanSetting getShowGolovu() {
      return this.showGolovu;
   }

   public ChoiceSetting getIndikatorZdorovya() {
      return this.indikatorZdorovya;
   }

   @Override
   public CategoryType getCategoryType() {
      return CategoryType.TARGET_HUD;
   }

   @Override
   public float getFloat9() {
      return 54.0F;
   }

   @Override
   public String getString3() {
      return "tt";
   }

   private void render(DrawContext drawContext, PlayerEntity playerEntity, float value, float value2, Matrix4f matrix4f, float value3) {
      this.itemStackArray[0] = playerEntity.getEquippedStack(EquipmentSlot.HEAD);
      this.itemStackArray[1] = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
      this.itemStackArray[2] = playerEntity.getEquippedStack(EquipmentSlot.LEGS);
      this.itemStackArray[3] = playerEntity.getEquippedStack(EquipmentSlot.FEET);
      this.itemStackArray2[0] = playerEntity.getOffHandStack();
      this.itemStackArray2[1] = playerEntity.getMainHandStack();
      float f = Math.max(19.0F, 18.0F);
      float f1 = value3 + (f - 19.0F) / 2.0F;
      float f2 = value3 + (f - 18.0F) / 2.0F;
      float f9 = value2 + 7.0F;
      float f5 = 19.0F;
      float f4 = f9;
      ItemStack[] aitemstack = this.itemStackArray;
      this.getFloatByDrawContextFloatMatrix4fItemStackArrayFloatFloatFloat(drawContext, f5, matrix4f, aitemstack, value, f1, f4);
      int j = this.itemStackArray2.length;
      float f6 = 18.0F;
      int i = j;
      float f3 = getFloatByFloatInt(f6, i);
      f9 = value2 + 175.0F - 8.0F - f3;
      float f8 = 18.0F;
      float f7 = f9;
      ItemStack[] aitemstack1 = this.itemStackArray2;
      this.getFloatByDrawContextFloatMatrix4fItemStackArrayFloatFloatFloat(drawContext, f8, matrix4f, aitemstack1, value, f2, f7);
   }

   private static float getFloatByFloatInt(float value, int count) {
      return count * value + (count - 1) * 4.0F;
   }

   private static int getIntByFloatIntFloat(float value, int count, float value2) {
      float[] afloat = MathUtil.getFloatArrayByInt(count);
      float f2 = afloat[0];
      float f1 = afloat[1] * value;
      float f = f2;
      return MathUtil.getIntByFloatFloatFloat(value2, f, f1);
   }

   private void onPlayerEntityMatrix4fFloatFloatFloat(PlayerEntity playerEntity, Matrix4f matrix4f, float value, float value2, float value3) {
      int k = Theme.background();
      float f12 = this.getFloatByFloat(value2);
      float f11 = 1.0F;
      float f10 = 1.0F;
      float f9 = 0.0F;
      int j = 436207616;
      float f8 = 0.0F;
      byte b0 = 0;
      int i = k;
      float f7 = 12.0F;
      float f6 = 12.0F;
      float f5 = 12.0F;
      float f4 = 12.0F;
      float f3 = 54.0F;
      float f2 = 54.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f2, j, i, value3, value, f11, f4, f3, f10, matrix4f, f12, b0, f6, f8, f5, f7, f9
      );
      Identifier identifier = this.getIdentifierByPlayerEntity(playerEntity);
      if (identifier != null) {
         float f = value3 + 4.5F;
         float f1 = value + 4.5F;
         float f14 = 6.0F;
         float f13 = 45.0F;
         RoundedTextureShader.onFloatIdentifierFloatFloatFloatMatrix4fFloat(f13, identifier, f1, f14, f, matrix4f, value2);
      }
   }

   private void onFloatDrawContextFloatFloatMatrix4fPlayerEntity(float value, DrawContext drawContext, float value2, float value3, Matrix4f matrix4f, PlayerEntity playerEntity) {
      float f = this.getFloatByFloat(value);
      int l = Theme.background();
      float f18 = 1.0F;
      float f17 = 1.0F;
      float f16 = 0.0F;
      int j = 436207616;
      float f15 = 0.0F;
      byte b0 = 0;
      int i = l;
      float f14 = 12.0F;
      float f13 = 12.0F;
      float f12 = 12.0F;
      float f11 = 12.0F;
      float f10 = 54.0F;
      float f9 = 175.0F;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f9, j, i, value2, value3, f18, f11, f10, f17, matrix4f, f, b0, f13, f15, f12, f14, f16
      );
      ServerMode servermode = ServerMode.FUNTIME;
      float f1 = HealthTracker.getFloatByServerModePlayerEntity(servermode, playerEntity);
      float f2 = HealthTracker.getFloatByPlayerEntityServerMode(playerEntity, ServerMode.FUNTIME);
      float f3 = value3 + 8.0F;
      float f4 = this.getFloatByFloatMatrix4fFloatFloatFloatFloatFloat(f1, matrix4f, f, f2, value, f3, value2);
      String s = playerEntity.getGameProfile().getName();
      Protect protect = (Protect)unsafeAccess2.getModule2();
      if (protect != null) {
         s = protect.getStringByString2(s);
      }

      float f5 = value2 + 8.0F;
      float f6 = Math.max(0.0F, f4 - 4.0F - f5);
      String s2 = getStringByFloatString(f6, s);
      int k = Theme.foreground();
      float f19 = 12.0F;
      String s1 = s2;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f3, f5, k, f19, value, s1, matrix4f);
      float f7 = value * this.value307;
      if (f7 > 0.001F) {
         float f8 = f3 + 12.0F + 3.0F;
         float f20 = f8 + 4.0F;
         this.render(drawContext, playerEntity, f7, value2, matrix4f, f20);
      }
   }

   private float getFloatByFloatMatrix4fFloatFloatFloatFloatFloat(float value, Matrix4f matrix4f, float value2, float value3, float value4, float value5, float value6) {
      String s = HealthTracker.getStringByFloat(value);
      float f = TextShader.getFloatByStringFloat(s, 12.0F);
      boolean flag = this.indikatorZdorovya.isFlag3();
      float f19;
      if (flag) {
         float f8 = 12.0F;
         String s1 = "HP";
         f19 = TextShader.getFloatByFloatString(f8, s1);
      } else {
         f19 = 10.0F;
      }

      float f1 = f19;
      float f2 = 4.0F + f + 4.0F + f1 + 4.0F;
      float f3 = 18.0F;
      float f4 = value6 + 175.0F - 8.0F - f2;
      float f5 = value5 - 3.0F;
      int i = getIntByFloatFloat(value3, value);
      boolean flag1 = ThemeConfig.getThemePalette() == ThemePalette.INSTANCE2;
      float f22 = flag1 ? 0.22F : 0.9F;
      float f10 = 0.55F;
      float f9 = f22;
      int k = getIntByFloatIntFloat(f10, i, f9);
      float f11 = 4.0F;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f11, f4, k, matrix4f, f3, f2, value2, f5);
      float f20 = flag1 ? 0.66F : 0.38F;
      float f13 = 1.0F;
      float f12 = f20;
      int j = getIntByFloatIntFloat(f13, i, f12);
      float f21 = f4 + 4.0F;
      float f15 = 12.0F;
      float f14 = f21;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(value5, f14, j, f15, value4, s, matrix4f);
      float f6 = f4 + 4.0F + f + 4.0F;
      if (flag) {
         float f16 = 12.0F;
         String s2 = "HP";
         TextShader.onFloatStringFloatFloatIntFloatMatrix4f(value4, s2, f16, f6, j, value5, matrix4f);
      } else {
         float f7 = value5 + 1.5F;
         float f18 = 9.0F;
         float f17 = 10.0F;
         CategoryType categorytype = CategoryType.HEART;
         SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(value4, j, matrix4f, f7, categorytype, f18, f6, f17);
      }

      return f4;
   }

   private static int getIntByFloatFloat(float value, float value2) {
      float f = Math.clamp(HealthTracker.getFloatByFloatFloat(value, value2), 0.0F, 1.0F);
      if (f >= 0.5F) {
         float f2 = (f - 0.5F) / 0.5F;
         int i1 = Theme.caution();
         int j = Theme.success();
         int i = i1;
         return AnimatedInt.getIntByIntFloatInt(j, f2, i);
      } else {
         float f1 = f / 0.5F;
         int j1 = Theme.danger();
         int l = Theme.caution();
         int k = j1;
         return AnimatedInt.getIntByIntFloatInt(l, f1, k);
      }
   }

   public BooleanSetting getShowBronyu() {
      return this.showBronyu;
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getTargethud().setBoolean(flag);
   }

   public ChoiceSetting getPolozhenieGolovy() {
      return this.polozhenieGolovy;
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getTargethud().isFlag3();
   }

   private float getFloatByDrawContextFloatMatrix4fItemStackArrayFloatFloatFloat(
      DrawContext drawContext, float value, Matrix4f matrix4f, ItemStack[] itemStackArray, float value2, float value3, float value4
   ) {
      float f = value4;

      for (int i = 0; i < itemStackArray.length; i++) {
         ItemStack itemstack = itemStackArray[i];
         if (itemstack != null && !itemstack.isEmpty()) {
            if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f, matrix4f, value2, value, value3, itemstack)) {
               ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(value, itemstack, value2, value3, f, drawContext);
            }

            onFloatItemStackFloatFloatFloatMatrix4f(f, itemstack, value3, value2, value, matrix4f);
         } else {
            float f1 = f + (value - 9.0F) / 2.0F;
            float f2 = value3 + (value - 9.0F) / 2.0F;
            CategoryType categorytype1 = CategoryType.CLOSE;
            int j = Theme.border();
            float f3 = 9.0F;
            CategoryType categorytype = categorytype1;
            SvgShader.onFloatCategoryTypeIntMatrix4fFloatFloatFloat(f2, categorytype, j, matrix4f, value2, f3, f1);
         }

         f += value;
         if (i < itemStackArray.length - 1) {
            f += 4.0F;
         }
      }

      return f;
   }

   private static void onFloatItemStackFloatFloatFloatMatrix4f(float value, ItemStack itemStack, float value2, float value3, float value4, Matrix4f matrix4f) {
      if (itemStack.getCount() > 1) {
         String s = Integer.toString(itemStack.getCount());
         float f3 = 8.0F;
         float f = TextShader.getFloatByFloatString(f3, s);
         float f1 = value + value4 - f;
         float f2 = value2 + value4 - 8.0F;
         TextShader.onMatrix4fStringFloatFloatFloatIntFloat2(matrix4f, s, f1, f2, 8.0F, Theme.foreground(), value3);
      }
   }

   private Identifier getIdentifierByPlayerEntity(PlayerEntity playerEntity) {
      ClientPlayNetworkHandler clientplaynetworkhandler = Feature.mc.getNetworkHandler();
      if (clientplaynetworkhandler == null) {
         return null;
      } else {
         PlayerListEntry playerlistentry = clientplaynetworkhandler.getPlayerListEntry(playerEntity.getUuid());
         return playerlistentry == null ? null : playerlistentry.getSkinTextures().texture();
      }
   }

   private static String getStringByFloatString(float value, String text) {
      if (text == null || text.isEmpty()) {
         return "";
      } else if (value <= 0.0F) {
         return "";
      } else if (TextShader.getFloatByStringFloat(text, 12.0F) <= value) {
         return text;
      } else {
         float f = TextShader.getFloatByStringFloat("...", 12.0F);
         if (f > value) {
            return "...";
         } else {
            StringBuilder stringbuilder = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {
               stringbuilder.append(text.charAt(i));
               if (TextShader.getFloatByStringFloat(stringbuilder.toString(), 12.0F) + f > value) {
                  stringbuilder.setLength(stringbuilder.length() - 1);
                  break;
               }
            }

            return stringbuilder.append("...").toString();
         }
      }
   }

   private PlayerEntity getPlayerEntity2() {
      return Feature.mc != null ? Feature.mc.player : null;
   }

   private float getFloat28() {
      return (this.showGolovu.isFlag3() ? 60.0F : 0.0F) + 175.0F;
   }

   private void update4() {
      long i = UiContext.getTime();
      if (this.time != i) {
         this.time = i;
         boolean flag = this.showBronyu.isFlag3();
         boolean flag1 = this.showGolovu.isFlag3();
         boolean flag2 = this.polozhenieGolovy.isFlag3();
         float f = (flag1 ? 60.0F : 0.0F) + 175.0F;
         float f1;
         float f2;
         if (flag2) {
            f2 = 0.0F;
            f1 = 181.0F;
         } else {
            f1 = 0.0F;
            f2 = flag1 ? 60.0F : 0.0F;
         }

         float f3 = flag1 ? 1.0F : 0.0F;
         float f4 = flag ? 1.0F : 0.0F;
         if (!this.flag3) {
            this.value304 = f1;
            this.value305 = f2;
            this.value306 = f3;
            this.value307 = f4;
            this.value308 = f;
            this.flag3 = true;
         } else {
            float f5 = this.interpolation2.getFloat2();
            this.value304 = getFloatByFloatFloatFloat2(this.value304, f1, f5);
            this.value305 = getFloatByFloatFloatFloat2(this.value305, f2, f5);
            this.value306 = getFloatByFloatFloatFloat(this.value306, f3, f5);
            this.value307 = getFloatByFloatFloatFloat(this.value307, f4, f5);
            this.value308 = getFloatByFloatFloatFloat2(this.value308, f, f5);
         }
      }
   }

   private static float getFloatByFloatFloatFloat2(float value, float value2, float value3) {
      float f = 0.1F;
      return Interpolation.getFloatByFloatFloatFloatFloat2(value2, value, value3, f);
   }

   @Override
   public float getFloat10() {
      return 54.0F;
   }

   @Override
   public float getFloat11() {
      return this.getFloat28();
   }

   @Override
   protected boolean check11() {
      return false;
   }

   @Override
   public float getFloat14() {
      return 235.0F;
   }

   @Override
   protected boolean check19() {
      boolean flag = super.check19() && (this.check4() || this.check23());
      if (!flag && this.getFloat16() <= 0.001F) {
         this.playerEntity = null;
         this.interpolation2.setTime();
         this.time = -1L;
      }

      return flag;
   }

   @Override
   protected boolean check20() {
      return false;
   }

   @Override
   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      PlayerEntity playerentity = this.getPlayerEntity();
      if (playerentity != null) {
         this.update4();
         float f = this.getValue260();
         float f1 = this.getValue261();
         float f2 = value * this.value306;
         if (f2 > 0.001F) {
            float f3 = f + this.value304;
            this.onPlayerEntityMatrix4fFloatFloatFloat(playerentity, matrix4f, f1, f2, f3);
         }

         float f4 = f + this.value305;
         this.onFloatDrawContextFloatFloatMatrix4fPlayerEntity(value, drawContext, f4, f1, matrix4f, playerentity);
      }
   }

   @Override
   protected boolean check23() {
      return AttackRecord.getPlayerEntity() != null;
   }

   @Override
   public float getFloat22() {
      return this.getValue260();
   }

   @Override
   public float getFloat23() {
      return 54.0F;
   }

   @Override
   public float getFloat24() {
      return this.getFloat28();
   }
}
