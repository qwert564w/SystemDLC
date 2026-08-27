package client.gui.hud;

import client.api.Theme;
import client.data.AnimatedInt;
import client.data.ChoiceOption;
import client.gui.screen.ClickGuiScreen;
import client.gui.widget.RenderElement;
import client.gui.widget.ScissorStack;
import client.gui.widget.UiContext;
import client.module.CategoryType;
import client.module.Feature;
import client.module.client.HudModule;
import client.render.ItemIconCache;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.BooleanSetting;
import client.setting.ChoiceSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.util.Interpolation;
import client.util.ItemDisplayEntry;
import client.util.ItemIcons;
import client.util.StringParts;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class HotbarHud extends RenderElement {
   private static final float value271 = 24.0F;
   private static final float value272 = 8.0F;
   private static final float value273 = 8.0F;
   private static final float value274 = 8.0F;
   private static final float value275 = 8.0F;
   private static final float value276 = 4.0F;
   private static final float value277 = 6.0F;
   private static final float value278 = 14.0F;
   private static final float value279 = 40.0F;
   private static final float value280 = 0.06F;
   private static final float value281 = 0.05F;
   private static final float value282 = 0.07F;
   private static final float value283 = 0.06F;
   private static final float value284 = 0.05F;
   private static final float value285 = 12.0F;
   private static final float value286 = 0.002F;
   private static final float value287 = 68.0F;
   private static final float value288 = 8.0F;
   private static final float value289 = 8.0F;
   private static final float value290 = 8.0F;
   private static final float value291 = 10.0F;
   private static final float value292 = 0.001F;
   private static final int value293 = 4;
   private static final Identifier identifier = Identifier.ofVanilla("hud/hotbar");
   private static final int value294 = 20;
   private static final int value295 = 22;
   private static final int value296 = 22;
   private static final int value297 = 16;
   private static final int value298 = 3;
   private static final int value299 = 182;
   private static final int value300 = 22;
   private static final int value301 = 3;
   private static final int value302 = 98;
   private static final int value303 = 29;
   private static final int value304 = 23;
   private static final int value305 = 5;
   private static final float value306 = 0.05F;
   private static final float value307 = 1.2F;
   private static final int value308 = 16724016;
   private static final int value309 = 112;
   private static final Identifier[] identifierArray = new Identifier[]{
      Identifier.ofVanilla("container/slot/boots"),
      Identifier.ofVanilla("container/slot/leggings"),
      Identifier.ofVanilla("container/slot/chestplate"),
      Identifier.ofVanilla("container/slot/helmet")
   };
   private static final float value310 = 16.0F;
   private static final float value311 = 96.0F;
   private static final float value312 = 36.0F;
   private static final float value313 = 18.0F;
   private static final float value314 = 24.0F;
   private static final int value315 = 17;
   private static final int value316 = 4;
   private static final ItemStack[] itemStackArray = new ItemStack[]{
      new ItemStack(Items.DIAMOND_BOOTS), new ItemStack(Items.DIAMOND_LEGGINGS), new ItemStack(Items.DIAMOND_CHESTPLATE), new ItemStack(Items.DIAMOND_HELMET)
   };
   private final ItemDisplayEntry[] itemDisplayEntryArray = new ItemDisplayEntry[4];
   private final Interpolation interpolation2 = new Interpolation();
   private static final String text = "Плашка";
   private static final String text2 = "У инвентаря";
   private static final String text3 = "Ванильный";
   private final ListSetting mode;
   private final ChoiceSetting orientaciya;
   private final ChoiceSetting storonaProchnosti;
   private final ChoiceSetting storonaHotbara;
   private final BooleanSetting colorPoProchnosti;
   private final BooleanSetting preduprezhdenieOProchnosti;
   private float value317;
   private float value318;
   private float value319;
   private long time;

   public HotbarHud() {
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"П", "л", "a", "ш", "к", "а"}),
            StringParts.join(new String[]{"У", " ", "и", "н", "в", "e", "н", "т", "а", "р", "я"}),
            StringParts.join(new String[]{"B", "a", "н", "и", "л", "ь", "н", "ы", "й"})
         ),
         List.of(StringParts.join(new String[]{"П", "л", "a", "ш", "к", "а"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Плашка — своя перетаскиваемая панель. У инвентаря — иконки по краям хотбара. Ванильный — полоса как хотбар, вплотную к нему.");
      this.mode = listsetting;
      ChoiceSetting choicesetting = new ChoiceSetting(
         "", "", new ChoiceOption("Горизонталь", CategoryType.ORIENT_HORIZONTAL), new ChoiceOption("Вертикаль", CategoryType.ORIENT_VERTICAL), false
      );
      choicesetting.setName("Ориентация");
      choicesetting.setDescription("Расположение элементов брони: горизонтально или вертикально.");
      this.orientaciya = choicesetting;
      ChoiceSetting choicesetting1 = new ChoiceSetting(
         "", "", new ChoiceOption("Слева", CategoryType.ARROW_LEFT), new ChoiceOption("Справа", CategoryType.ARROW_RIGHT), true
      );
      choicesetting1.setName("Сторона прочности");
      choicesetting1.setDescription("C какой стороны от иконки отображать текст прочности.");
      this.storonaProchnosti = choicesetting1;
      ChoiceSetting choicesetting2 = new ChoiceSetting(
         "", "", new ChoiceOption("Слева", CategoryType.ARROW_LEFT), new ChoiceOption("Справа", CategoryType.ARROW_RIGHT), false
      );
      choicesetting2.setName("Сторона хотбара");
      choicesetting2.setDescription("C какой стороны от хотбара ставить полосу.");
      this.storonaHotbara = choicesetting2;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Цвет по прочности");
      booleansetting.setDescription("Окрашивать число прочности от белого (100%) к тёмно-красному (низкая).");
      this.colorPoProchnosti = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Предупреждение o прочности");
      booleansetting1.setDescription("Подсвечивать слот, когда предмет почти сломан.");
      this.preduprezhdenieOProchnosti = booleansetting1;
      this.value317 = -1.0F;
      this.value318 = -1.0F;
      this.value319 = -1.0F;
      this.time = -1L;

      for (int i = 0; i < 4; i++) {
         this.itemDisplayEntryArray[i] = new ItemDisplayEntry(i);
      }

      this.orientaciya.setVisibleWhen(this::check28);
      this.storonaProchnosti.setVisibleWhen(this::check28);
      this.getSizeModulya().setVisibleWhen(this::check28);
      this.colorPoProchnosti.setVisibleWhen(() -> !this.check24());
      this.storonaHotbara.setVisibleWhen(this::check24);
      this.preduprezhdenieOProchnosti.setVisibleWhen(this::check24);
      this.onSettingArray(
         new Setting[]{this.mode, this.orientaciya, this.storonaProchnosti, this.colorPoProchnosti, this.storonaHotbara, this.preduprezhdenieOProchnosti}
      );
   }

   @Override
   public boolean check2() {
      return !this.check26() && !this.check24();
   }

   private boolean check24() {
      return this.mode.isString("Ванильный");
   }

   private float getFloat28() {
      return this.getFloat40() + this.getFloat33() - this.getFloat29();
   }

   @Override
   public String getString() {
      return "Армор";
   }

   private float getFloat29() {
      return this.getFloat32() - this.getFloatByFloat2(36.0F);
   }

   @Override
   public CategoryType getCategoryType() {
      return CategoryType.ARMOR_HUD;
   }

   private float getFloat30() {
      float f = this.getFloat36();
      return f < 0.001F ? 0.0F : 16.0F * f + this.getFloatByBoolean(false);
   }

   private void render(DrawContext drawContext, float value) {
      float f = ClickGuiScreen.getValue235();
      if (f <= 0.0F) {
         f = 1.0F;
      }

      drawContext.getMatrices().push();
      drawContext.getMatrices().scale(1.0F / f, 1.0F / f, 1.0F);

      try {
         int i = Feature.mc.getWindow().getScaledWidth() / 2;
         int j = Feature.mc.getWindow().getScaledHeight();
         int k = i - 96 - 16;
         int l = i + 96;
         int i1 = j - 36;
         int j1 = j - 18;
         ItemDisplayEntry itemdisplayentry4 = this.itemDisplayEntryArray[3];
         boolean flag = false;
         ItemDisplayEntry itemdisplayentry = itemdisplayentry4;
         this.onItemDisplayEntryDrawContextIntBooleanFloatInt(itemdisplayentry, drawContext, k, flag, value, i1);
         itemdisplayentry4 = this.itemDisplayEntryArray[2];
         boolean flag1 = false;
         ItemDisplayEntry itemdisplayentry1 = itemdisplayentry4;
         this.onItemDisplayEntryDrawContextIntBooleanFloatInt(itemdisplayentry1, drawContext, k, flag1, value, j1);
         itemdisplayentry4 = this.itemDisplayEntryArray[1];
         boolean flag2 = true;
         ItemDisplayEntry itemdisplayentry2 = itemdisplayentry4;
         this.onItemDisplayEntryDrawContextIntBooleanFloatInt(itemdisplayentry2, drawContext, l, flag2, value, i1);
         itemdisplayentry4 = this.itemDisplayEntryArray[0];
         boolean flag3 = true;
         ItemDisplayEntry itemdisplayentry3 = itemdisplayentry4;
         this.onItemDisplayEntryDrawContextIntBooleanFloatInt(itemdisplayentry3, drawContext, l, flag3, value, j1);
      } finally {
         drawContext.getMatrices().pop();
      }
   }

   private int getInt() {
      int i = this.getInt4();
      return i <= 0 ? 0 : 22 + (i - 1) * 20;
   }

   private boolean check25() {
      return this.orientaciya.isFlag3();
   }

   @Override
   public float getFloat8() {
      return !this.check26() && !this.check24() ? super.getFloat8() : 1.0F;
   }

   private int getInt2() {
      int i = Feature.mc.getWindow().getScaledWidth() / 2;
      boolean flag = this.check27();
      int j = 98 + this.getIntByBoolean(flag);
      return flag ? i - j - this.getInt() : i + j;
   }

   private float getFloat31() {
      return this.getFloat36() < 0.001F ? 0.0F : 16.0F + this.getFloatByBoolean(true);
   }

   private boolean check26() {
      return this.mode.isString("У инвентаря");
   }

   @Override
   public float getFloat9() {
      this.update4();
      float f2 = this.value319;
      float f1 = this.getFloat31();
      float f = 40.0F;
      return getFloatByFloatFloatFloat(f2, f, f1);
   }

   private float getFloat32() {
      UiContext uicontext = UiContext.getInstance();
      return uicontext != null ? uicontext.getValue238() : 1080.0F;
   }

   private float getFloat33() {
      return this.getFloatByFloat2(16.0F);
   }

   private boolean check27() {
      return !this.storonaHotbara.isFlag3();
   }

   private float getFloat34() {
      return this.getFloat39() + this.getFloatByFloat2(96.0F);
   }

   @Override
   public String getString3() {
      return "armor";
   }

   private int getInt3() {
      return Feature.mc.getWindow().getScaledHeight() - 22;
   }

   private boolean check28() {
      return this.mode.isString("Плашка");
   }

   private float getFloat35() {
      return this.getFloat34() + this.getFloat33() - this.getFloat38();
   }

   private void onItemDisplayEntryDrawContextIntBooleanFloatInt(ItemDisplayEntry itemDisplayEntry, DrawContext drawContext, int count, boolean flag, float value, int count2) {
      float f = value * itemDisplayEntry.value4 * itemDisplayEntry.value5;
      if (!(f <= 0.001F)) {
         if (itemDisplayEntry.itemStack != null && !itemDisplayEntry.itemStack.isEmpty()) {
            float f4 = count;
            float f5 = count2;
            float f3 = 16.0F;
            float f2 = f5;
            float f1 = f4;
            ItemStack itemstack = itemDisplayEntry.itemStack;
            ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(f3, itemstack, f, f2, f1, drawContext);
         }

         if (!itemDisplayEntry.text.isEmpty()) {
            int i = Math.round(Math.clamp(f, 0.0F, 1.0F) * 255.0F);
            if (i > 3) {
               int j = this.colorPoProchnosti.isFlag3() ? getIntByItemStack(itemDisplayEntry.itemStack) : -1;
               int k = j & 16777215 | i << 24;
               int l = count2 + 4;
               int i1 = flag ? count + 17 : count - 1 - Feature.mc.textRenderer.getWidth(itemDisplayEntry.text);
               drawContext.drawText(Feature.mc.textRenderer, itemDisplayEntry.text, i1, l, k, true);
            }
         }
      }
   }

   private float getFloatByFloat2(float value) {
      int i = Feature.mc.getWindow().getScaledWidth();
      if (i <= 0) {
         return value;
      } else {
         UiContext uicontext = UiContext.getInstance();
         float f = uicontext != null ? uicontext.getFloat3() : 1920.0F;
         return value * f / i;
      }
   }

   @Override
   public void onFloatFloat(float value, float value2) {
      if (this.check24()) {
         super.onFloatFloat(this.getFloatByFloat2(this.getInt2()), this.getFloatByFloat2(this.getInt3()));
      } else if (this.check26()) {
         super.onFloatFloat(this.getFloat38(), this.getFloat29());
      } else {
         super.onFloatFloat(value, value2);
      }
   }

   private static boolean isItemStack(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty() && itemStack.isDamageable() && itemStack.getMaxDamage() > 0) {
         int i = itemStack.getMaxDamage() - itemStack.getDamage();
         return i <= 5 || i <= itemStack.getMaxDamage() * 0.05F;
      } else {
         return false;
      }
   }

   private void render2(DrawContext drawContext, float value) {
      int i = this.getInt4();
      if (i > 0) {
         float f = ClickGuiScreen.getValue235();
         if (f <= 0.0F) {
            f = 1.0F;
         }

         drawContext.getMatrices().push();
         drawContext.getMatrices().scale(1.0F / f, 1.0F / f, 1.0F);

         try {
            int j = 22 + (i - 1) * 20;
            int k = this.getInt2();
            int l = this.getInt3();
            drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier, 182, 22, 0, 0, k, l, j - 3, 22);
            drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifier, 182, 22, 179, 0, k + j - 3, l, 3, 22);
            float f1 = getFloat37();
            int i1 = 0;

            for (int j1 = 3; j1 >= 0; j1--) {
               ItemDisplayEntry itemdisplayentry = this.itemDisplayEntryArray[j1];
               if (itemdisplayentry.flag) {
                  int k1 = k + i1 * 20;
                  int l1 = k1 + 3;
                  int i2 = l + 3;
                  ItemStack itemstack = itemdisplayentry.itemStack;
                  if (itemstack != null && !itemstack.isEmpty()) {
                     if (this.preduprezhdenieOProchnosti.isFlag3() && isItemStack(itemstack)) {
                        int j2 = Math.round(112.0F * f1 * Math.clamp(value, 0.0F, 1.0F));
                        if (j2 > 3) {
                           drawContext.fill(k1 + 1, l + 1, k1 + 20 + 1, l + 22 - 1, j2 << 24 | 16724016);
                        }
                     }

                     float f5 = l1;
                     float f6 = i2;
                     float f4 = 16.0F;
                     float f3 = f6;
                     float f2 = f5;
                     ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(f4, itemstack, value, f3, f2, drawContext);
                     drawContext.drawStackOverlay(Feature.mc.textRenderer, itemstack, l1, i2);
                  } else {
                     drawContext.drawGuiTexture(RenderLayer::getGuiTextured, identifierArray[j1], l1, i2, 16, 16);
                  }

                  i1++;
               }
            }
         } finally {
            drawContext.getMatrices().pop();
         }
      }
   }

   private boolean check29() {
      return this.storonaProchnosti.isFlag3();
   }

   @Override
   protected void onHudModuleBoolean(HudModule hudModule, boolean flag) {
      hudModule.getArmor().setBoolean(flag);
   }

   private static int getIntByItemStack(ItemStack itemStack) {
      int i = Theme.foreground();
      if (itemStack != null && !itemStack.isEmpty() && itemStack.isDamageable() && itemStack.getMaxDamage() > 0) {
         float f = 1.0F - (float)itemStack.getDamage() / itemStack.getMaxDamage();
         if (f < 0.0F) {
            f = 0.0F;
         } else if (f > 1.0F) {
            f = 1.0F;
         }

         int j = Theme.success();
         int k = Theme.warning();
         int l = Theme.caution();
         int i1 = Theme.danger();
         if (f >= 0.75F) {
            float f1 = (f - 0.75F) / 0.25F;
            return AnimatedInt.getIntByIntFloatInt(i, f1, j);
         } else if (f >= 0.5F) {
            float f2 = (f - 0.5F) / 0.25F;
            return AnimatedInt.getIntByIntFloatInt(j, f2, k);
         } else if (f >= 0.2F) {
            float f3 = (f - 0.2F) / 0.3F;
            return AnimatedInt.getIntByIntFloatInt(k, f3, l);
         } else if (f >= 0.08F) {
            return l;
         } else {
            float f4 = f / 0.08F;
            return AnimatedInt.getIntByIntFloatInt(l, f4, i1);
         }
      } else {
         return i;
      }
   }

   private void onFloatFloatFloatFloatFloatDrawContextFloatMatrix4fItemDisplayEntry(
      float value3, float value6, float value7, float value8, float value9, DrawContext drawContext, float value10, Matrix4f matrix4f, ItemDisplayEntry itemDisplayEntry
   ) {
      float f = value10 * itemDisplayEntry.value4;
      if (!(f <= 0.001F)) {
         TextShader.update2();
         float f1 = itemDisplayEntry.value5;
         float f2 = getFloatByInt2(itemDisplayEntry.value);
         float f3 = itemDisplayEntry.value2 * f1;
         float f12 = value9 + f3 + 4.0F + f2;
         float f4 = getFloatByFloatFloatFloat(value6, f12, value9);
         float f13 = value9 + 24.0F + 4.0F + f2;
         float f5 = getFloatByFloatFloatFloat(value6, value9, f13);
         float f30 = this.getValue260() + value7 - 8.0F - 24.0F;
         float f15 = this.getValue260() + 8.0F;
         float f14 = f30;
         float f6 = getFloatByFloatFloatFloat(value6, f14, f15);
         f30 = this.getValue260() + 8.0F;
         float f17 = this.getValue260() + value7 - 8.0F - itemDisplayEntry.value2;
         float f16 = f30;
         float f7 = getFloatByFloatFloatFloat(value6, f16, f17);
         float f8 = getFloatByFloatFloatFloat(value3, f4, f6);
         float f18 = this.getValue261() + 8.0F + 1.0F;
         float f9 = getFloatByFloatFloatFloat(value3, f18, value8);
         if (f1 > 0.001F && itemDisplayEntry.itemStack != null && !itemDisplayEntry.itemStack.isEmpty()) {
            float f10 = f * f1;
            float f19 = 24.0F;
            ItemStack itemstack = itemDisplayEntry.itemStack;
            if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f8, matrix4f, f10, f19, f9, itemstack)) {
               float f20 = 24.0F;
               ItemStack itemstack1 = itemDisplayEntry.itemStack;
               ItemIconCache.onFloatItemStackFloatFloatFloatDrawContext(f20, itemstack1, f10, f9, f8, drawContext);
            }
         }

         if (f1 < 0.999F) {
            float f27 = f8 + 6.0F;
            float f11 = f9 + 6.0F;
            CategoryType categorytype1 = CategoryType.CLOSE;
            int k = Theme.border();
            float f22 = f * (1.0F - f1);
            int j = k;
            float f21 = 12.0F;
            CategoryType categorytype = categorytype1;
            SvgShader.onFloatCategoryTypeIntMatrix4fFloatFloatFloat(f11, categorytype, j, matrix4f, f22, f21, f27);
         }

         if (f1 > 0.001F && !itemDisplayEntry.text.isEmpty()) {
            float f28 = getFloatByFloatFloatFloat(value3, f5, f7);
            f30 = this.getValue261() + 13.0F;
            float f24 = value8 + 5.0F;
            float f23 = f30;
            float f29 = getFloatByFloatFloatFloat(value3, f23, f24);
            int i = this.colorPoProchnosti.isFlag3() ? getIntByItemStack(itemDisplayEntry.itemStack) : Theme.foreground();
            float f26 = f * f1;
            float f25 = 14.0F;
            String s = itemDisplayEntry.text;
            TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f29, f28, i, f25, f26, s, matrix4f);
         }
      }
   }

   private ItemStack getItemStackByInt(int count) {
      ClientPlayerEntity clientplayerentity = Feature.mc.player;
      ItemStack itemstack = clientplayerentity != null ? clientplayerentity.getInventory().getArmorStack(count) : null;
      if ((itemstack == null || itemstack.isEmpty()) && this.check4()) {
         return itemStackArray[count].copy();
      } else {
         return itemstack != null ? itemstack : ItemStack.EMPTY;
      }
   }

   private static float getFloatByInt2(int count) {
      return count == 3 ? -4.0F : -2.0F;
   }

   private int getInt4() {
      this.update4();
      int i = 0;

      for (ItemDisplayEntry itemdisplayentry : this.itemDisplayEntryArray) {
         if (itemdisplayentry.flag) {
            i++;
         }
      }

      return i;
   }

   private int getIntByBoolean(boolean flag2) {
      ClientPlayerEntity clientplayerentity = Feature.mc.player;
      if (clientplayerentity == null) {
         return 0;
      } else {
         Arm arm = clientplayerentity.getMainArm().getOpposite();
         boolean flag = arm == Arm.LEFT;
         if (!clientplayerentity.getOffHandStack().isEmpty() && flag == flag2) {
            return 29;
         } else {
            boolean flag1 = !flag;
            return Feature.mc.options != null && Feature.mc.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR && flag1 == flag2 ? 23 : 0;
         }
      }
   }

   @Override
   protected boolean isHudModule(HudModule hudModule) {
      return hudModule.getArmor().isFlag3();
   }

   public static float getFloatByFloatFloatFloat(float value, float value2, float value3) {
      return value2 + (value3 - value2) * value;
   }

   private float getFloat36() {
      float f = 0.0F;

      for (ItemDisplayEntry itemdisplayentry : this.itemDisplayEntryArray) {
         if (itemdisplayentry.value4 > f) {
            f = itemdisplayentry.value4;
         }
      }

      return f;
   }

   private void onFloatDrawContextFloatMatrix4f(float value, DrawContext drawContext, float value2, Matrix4f matrix4f) {
      float f = this.value319;
      float f1 = this.value318;
      float f2 = this.value317;
      float f3 = this.getFloat30();
      float f4 = this.getFloat31();
      float f5 = getFloatByFloatFloatFloat(f, f3, f2);
      float f10 = 40.0F;
      float f6 = getFloatByFloatFloatFloat(f, f10, f4);
      float f7 = this.getFloatByFloat(value2) * value;
      if (!(f5 <= 0.5F) && !(f6 <= 0.5F)) {
         if (f7 > 0.001F) {
            float f23 = this.getValue260();
            float f24 = this.getValue261();
            int l = Theme.background();
            float f20 = 1.0F;
            float f19 = 1.0F;
            float f18 = 0.0F;
            int k = 436207616;
            float f17 = 0.0F;
            byte b0 = 0;
            int j = l;
            float f16 = 12.0F;
            float f15 = 12.0F;
            float f14 = 12.0F;
            float f13 = 12.0F;
            float f12 = f24;
            float f11 = f23;
            ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
               f5, k, j, f11, f12, f20, f13, f6, f19, matrix4f, f7, b0, f15, f17, f14, f16, f18
            );
         }

         float f22 = this.getValue261();
         float f21 = this.getValue260();
         ScissorStack.onFloatFloatFloatFloat(f5, f6, f22, f21);

         try {
            float f8 = this.getValue260() + 8.0F * value;
            float f9 = this.getValue261() + 8.0F;
            boolean flag = true;
            boolean flag1 = true;

            for (int i = 3; i >= 0; i--) {
               ItemDisplayEntry itemdisplayentry = this.itemDisplayEntryArray[i];
               if (!(itemdisplayentry.value4 < 0.001F)) {
                  if (!flag) {
                     f8 += 6.0F * itemdisplayentry.value4;
                  }

                  if (!flag1) {
                     f9 += 10.0F * itemdisplayentry.value4;
                  }

                  flag = false;
                  flag1 = false;
                  this.onFloatFloatFloatFloatFloatDrawContextFloatMatrix4fItemDisplayEntry(f, f1, f2, f9, f8, drawContext, value2, matrix4f, itemdisplayentry);
                  f8 += itemdisplayentry.getFloat() * itemdisplayentry.value4;
                  f9 += 24.0F * itemdisplayentry.value4;
               }
            }
         } finally {
            ScissorStack.update();
         }
      }
   }

   private float getFloatByBoolean(boolean flag2) {
      float f = 0.0F;
      boolean flag = true;

      for (int i = 3; i >= 0; i--) {
         ItemDisplayEntry itemdisplayentry = this.itemDisplayEntryArray[i];
         if (!(itemdisplayentry.value4 < 0.001F)) {
            float f1 = flag2 ? 24.0F : itemdisplayentry.getFloat();
            float f2 = flag ? 0.0F : (flag2 ? 10.0F : 6.0F);
            f += (f1 + f2) * itemdisplayentry.value4;
            flag = false;
         }
      }

      return f;
   }

   private static String getStringByItemStack(ItemStack itemStack) {
      return itemStack.isDamageable() ? Integer.toString(itemStack.getMaxDamage() - itemStack.getDamage()) : Integer.toString(itemStack.getCount());
   }

   private void update4() {
      long i = UiContext.getTime();
      if (this.time != i) {
         this.time = i;
         float f = this.interpolation2.getFloat2();
         boolean flag = this.check5();
         boolean flag1 = !this.check21() || this.check4();

         for (ItemDisplayEntry itemdisplayentry : this.itemDisplayEntryArray) {
            ItemStack itemstack = this.getItemStackByInt(itemdisplayentry.value);
            if (itemstack == null) {
               itemstack = ItemStack.EMPTY;
            }

            boolean flag2 = !itemstack.isEmpty();
            itemdisplayentry.flag = flag && (flag2 || flag1);
            if (flag2) {
               itemdisplayentry.itemStack = itemstack.copy();
               itemdisplayentry.text = getStringByItemStack(itemdisplayentry.itemStack);
               itemdisplayentry.value2 = TextShader.getFloatByStringFloat(itemdisplayentry.text, 14.0F);
               itemdisplayentry.value3 = 28.0F + itemdisplayentry.value2;
            }

            float f1 = itemdisplayentry.flag ? 1.0F : 0.0F;
            float f4 = 0.06F;
            float f3 = itemdisplayentry.value4;
            itemdisplayentry.value4 = Interpolation.getFloatByFloatFloatFloatFloat2(f1, f3, f, f4);
            float f2 = flag2 ? 1.0F : 0.0F;
            float f6 = 0.05F;
            float f5 = itemdisplayentry.value5;
            itemdisplayentry.value5 = Interpolation.getFloatByFloatFloatFloatFloat2(f2, f5, f, f6);
            if (!itemdisplayentry.flag && itemdisplayentry.value4 < 0.002F) {
               itemdisplayentry.value4 = 0.0F;
               itemdisplayentry.value5 = 0.0F;
               itemdisplayentry.itemStack = ItemStack.EMPTY;
               itemdisplayentry.text = "";
               itemdisplayentry.value2 = 0.0F;
               itemdisplayentry.value3 = 24.0F;
            }
         }

         float f16 = this.value318;
         float f17 = this.check29() ? 1.0F : 0.0F;
         float f9 = 0.06F;
         float f8 = f17;
         float f7 = f16;
         this.value318 = Interpolation.getFloatByFloatFloatFloatFloat(f8, f, f7, f9);
         f16 = this.value319;
         f17 = this.check25() ? 1.0F : 0.0F;
         float f12 = 0.07F;
         float f11 = f17;
         float f10 = f16;
         this.value319 = Interpolation.getFloatByFloatFloatFloatFloat(f11, f, f10, f12);
         float f15 = this.check30() ? 68.0F : 40.0F;
         float f14 = 0.05F;
         float f13 = this.value317;
         this.value317 = Interpolation.getFloatByFloatFloatFloatFloat(f15, f, f13, f14);
      }
   }

   private static float getFloat37() {
      long i = 1200000000L;
      float f = (float)(System.nanoTime() % i) / (float)i;
      return 0.5F - 0.5F * (float)Math.cos(f * Math.PI * 2.0);
   }

   @Override
   public float getFloat10() {
      return this.getFloat9();
   }

   @Override
   public float getFloat11() {
      this.update4();
      if (this.getFloat36() < 0.001F) {
         return 0.0F;
      } else {
         float f3 = this.getFloat30();
         float f2 = this.value319;
         float f1 = this.value317;
         float f = f3;
         return getFloatByFloatFloatFloat(f2, f, f1);
      }
   }

   private float getFloat38() {
      return this.getFloat39() - this.getFloatByFloat2(96.0F) - this.getFloat33();
   }

   private float getFloat39() {
      UiContext uicontext = UiContext.getInstance();
      return (uicontext != null ? uicontext.getFloat3() : 1920.0F) / 2.0F;
   }

   private boolean check30() {
      for (ItemDisplayEntry itemdisplayentry : this.itemDisplayEntryArray) {
         if (itemdisplayentry.value4 * itemdisplayentry.value5 > 0.05F) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected boolean check14() {
      return this.getFloat36() > 0.001F;
   }

   private float getFloat40() {
      return this.getFloat32() - this.getFloatByFloat2(18.0F);
   }

   @Override
   protected boolean check16() {
      return true;
   }

   @Override
   public float getFloat14() {
      return this.getFloat11();
   }

   @Override
   protected boolean check20() {
      return false;
   }

   @Override
   public boolean check22() {
      return !this.check26() && !this.check24();
   }

   @Override
   protected boolean check23() {
      if (this.getFloat36() > 0.001F) {
         return true;
      } else if (!this.check21()) {
         return true;
      } else {
         for (ItemDisplayEntry itemdisplayentry : this.itemDisplayEntryArray) {
            if (itemdisplayentry.flag) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isFloatFloat(float value, float value2) {
      if (this.check24()) {
         float f7 = this.getFloatByFloat2(this.getInt2());
         float f8 = this.getFloatByFloat2(this.getInt3());
         float f9 = this.getFloatByFloat2(this.getInt());
         float f10 = this.getFloatByFloat2(22.0F);
         return value >= f7 && value <= f7 + f9 && value2 >= f8 && value2 <= f8 + f10;
      } else if (!this.check26()) {
         return super.isFloatFloat(value, value2);
      } else {
         float f = this.getFloat33();
         float f1 = this.getFloatByFloat2(24.0F);
         float f2 = this.getFloat29();
         float f3 = this.getFloat40() + f;
         float f4 = this.getFloat38() - f1;
         float f5 = this.getFloat34();
         float f6 = f + f1;
         boolean flag = value >= f4 && value <= f4 + f6 && value2 >= f2 && value2 <= f3;
         boolean flag1 = value >= f5 && value <= f5 + f6 && value2 >= f2 && value2 <= f3;
         return flag || flag1;
      }
   }

   @Override
   protected void onFloatDrawContextMatrix4fFloat(float value, DrawContext drawContext, Matrix4f matrix4f, float value2) {
      this.update4();
      float f = this.getFloat36();
      if (!(f < 0.001F)) {
         float f1 = value * value2;
         if (!(f1 <= 0.001F)) {
            if (this.check24()) {
               this.render2(drawContext, f1);
            } else if (this.check26()) {
               this.render(drawContext, f1);
            } else {
               this.onFloatDrawContextFloatMatrix4f(f, drawContext, f1, matrix4f);
            }
         }
      }
   }

   @Override
   public float getFloat22() {
      if (this.check24()) {
         return this.getFloatByFloat2(this.getInt2());
      } else {
         return this.check26() ? this.getFloat38() : this.getValue260();
      }
   }

   @Override
   public float getFloat23() {
      if (this.check24()) {
         return this.getFloatByFloat2(22.0F);
      } else {
         return this.check26() ? this.getFloat28() : this.getFloat9();
      }
   }

   @Override
   public float getFloat24() {
      if (this.check24()) {
         return this.getFloatByFloat2(this.getInt());
      } else {
         return this.check26() ? this.getFloat35() : this.getFloat11();
      }
   }
}
