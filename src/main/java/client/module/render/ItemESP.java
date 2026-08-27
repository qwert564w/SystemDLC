package client.module.render;

import client.api.Theme;
import client.enums.TrackedItem;
import client.module.Category;
import client.module.Module;
import client.module.client.StreamBypass;
import client.render.BoxEspRenderer;
import client.render.DepthState;
import client.render.ItemIconCache;
import client.render.RotationBuffer;
import client.render.ShapeShader;
import client.render.TextShader;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.CompactGroupSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.DistanceScale;
import client.util.ItemEspEntry;
import client.util.ItemIcons;
import client.util.ItemPickupMath;
import client.util.MathUtil;
import client.util.SphereItems;
import client.util.StringParts;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class ItemESP extends Module {
   private static final Pattern pattern = Pattern.compile("§.");
   private ListSetting mode;
   private SliderSetting size;
   private SliderSetting opacityPlashki;
   private BooleanSetting onlyCennyeItems;
   private BooleanSetting podsvechivatTaliki;
   private ColorSetting colorTalikov;
   private SliderSetting thicknessLines;
   private BooleanSetting scaleOtDistancii;
   private ColorSetting colorLiniy;
   private BooleanSetting outline;
   private ColorSetting colorObvodki;
   private BooleanSetting fill;
   private ColorSetting colorZalivki;
   private final List<ItemEspEntry> list;
   private final Map<Item, String> map;
   private final BoxEspRenderer boxEspRenderer;
   private final ArrayList<Entity> list2;

   public ItemESP() {
      super("ItemESP", Category.RENDER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"П", "л", "а", "ш", "к", "а"}),
            StringParts.join(new String[]{"Б", "о", "к", "с"}),
            StringParts.join(new String[]{"П", "л", "а", "ш", "к", "а", " ", "+", " ", "Б", "о", "к", "с"})
         ),
         List.of(StringParts.join(new String[]{"П", "л", "а", "ш", "к", "а"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Как отображать предметы");
      this.mode = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.35, 0.01, 1.0, 0.05);
      slidersetting.setName("Размер");
      slidersetting.setDescription("Масштаб плашки");
      this.size = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 100.0, 0.0, 100.0, 5.0, "%", 0);
      slidersetting1.setName("Прозрачность плашки");
      slidersetting1.setDescription("Непрозрачность фона и обводки");
      this.opacityPlashki = slidersetting1;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Только ценные предметы");
      booleansetting.setDescription("Показывать только ценные предметы");
      this.onlyCennyeItems = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Подсвечивать талики");
      booleansetting1.setDescription("Выделять талисманы ФТ отдельным цветом");
      this.podsvechivatTaliki = booleansetting1;
      ColorSetting colorsetting = new ColorSetting("", "", -48060, true);
      colorsetting.setName("Цвет таликов");
      colorsetting.setDescription("Цвет текста талисманов ФТ");
      this.colorTalikov = colorsetting;
      SliderSetting slidersetting2 = new SliderSetting("", "", 2.0, 0.1, 5.0, 0.1);
      slidersetting2.setName("Толщина линии");
      slidersetting2.setDescription("Толщина линий");
      this.thicknessLines = slidersetting2;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("Масштаб от дистанции");
      booleansetting2.setDescription("Утолщать линии c расстоянием");
      this.scaleOtDistancii = booleansetting2;
      ColorSetting colorsetting1 = new ColorSetting("", "", -1, true);
      colorsetting1.setName("Цвет линий");
      colorsetting1.setDescription("Цвет линий");
      this.colorLiniy = colorsetting1;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", false);
      booleansetting3.setName("Обводка");
      booleansetting3.setDescription("Тёмная обводка вокруг линий для контраста");
      this.outline = booleansetting3;
      ColorSetting colorsetting2 = new ColorSetting("", "", -16777216, true);
      colorsetting2.setName("Цвет обводки");
      colorsetting2.setDescription("Цвет обводки линий");
      this.colorObvodki = colorsetting2;
      BooleanSetting booleansetting4 = new BooleanSetting("", "", false);
      booleansetting4.setName("Заливка");
      booleansetting4.setDescription("Заливка внутри бокса");
      this.fill = booleansetting4;
      ColorSetting colorsetting3 = new ColorSetting("", "", 1291845631, true);
      colorsetting3.setName("Цвет заливки");
      colorsetting3.setDescription("Цвет заливки");
      this.colorZalivki = colorsetting3;
      this.list = new ArrayList<>();
      this.map = new HashMap<>();
      this.boxEspRenderer = new BoxEspRenderer();
      this.list2 = new ArrayList<>();
      Supplier<Boolean> supplier = this::check4;
      Supplier<Boolean> supplier1 = this::check3;
      this.size.setVisibleWhen(supplier::get);
      this.opacityPlashki.setVisibleWhen(supplier::get);
      this.podsvechivatTaliki.setVisibleWhen(supplier::get);
      this.colorTalikov.setVisibleWhen(() -> this.getBooleanBySupplier(supplier1));
      this.colorObvodki.setVisibleWhen(this.outline::isFlag3);
      this.colorZalivki.setVisibleWhen(this.fill::isFlag3);
      CompactGroupSetting compactgroupsetting2 = new CompactGroupSetting("", "", this.thicknessLines, this.scaleOtDistancii);
      compactgroupsetting2.setName("Форма");
      compactgroupsetting2.setDescription("Параметры геометрии бокса.");
      CompactGroupSetting compactgroupsetting = compactgroupsetting2;
      compactgroupsetting.setVisibleWhen(supplier1::get);
      compactgroupsetting2 = new CompactGroupSetting("", "", this.colorLiniy, this.outline, this.colorObvodki, this.fill, this.colorZalivki);
      compactgroupsetting2.setName("Цвета");
      compactgroupsetting2.setDescription("Цвета линий, обводки и заливки.");
      CompactGroupSetting compactgroupsetting1 = compactgroupsetting2;
      compactgroupsetting1.setVisibleWhen(supplier1::get);
      this.addSettings(
         new Setting[]{
            this.mode,
            this.size,
            this.opacityPlashki,
            this.onlyCennyeItems,
            this.podsvechivatTaliki,
            this.colorTalikov,
            compactgroupsetting,
            compactgroupsetting1
         }
      );
   }

   private void render8(WorldRenderContext worldRenderContext) {
      MatrixStack matrixstack = worldRenderContext.getMatrixStack();
      if (matrixstack != null) {
         Camera camera = worldRenderContext.getCamera();
         Vec3d vec3d = camera.getPos();
         Vec3d vec3d1 = this.player().getPos();
         float f = worldRenderContext.getRenderTickCounter().getTickDelta(true);
         float f1 = (float)((Integer)this.client().options.getFov().getValue()).intValue();
         boolean flag = this.onlyCennyeItems.isFlag3();
         float f2 = this.size.getValueAsFloat();
         float f3 = this.opacityPlashki.getValueAsFloat() / 100.0F;
         int i = getIntByIntFloat(Theme.background(), f3);
         int j = getIntByIntFloat(Theme.border(), f3);
         int k = Theme.foreground();
         int l = this.podsvechivatTaliki.isFlag3() ? this.colorTalikov.getInt3() : k;
         List listx = this.list;
         int i1 = 0;

         for (Entity entity : this.clientWorld().getEntities()) {
            if (entity instanceof ItemEntity itementity) {
               double d3 = 1.0;
               double d2 = 128.0;
               if (ItemPickupMath.isVec3dItemEntityDoubleBooleanDouble(vec3d1, itementity, d3, flag, d2)) {
                  Vec3d vec3d2 = itementity.getPos();
                  double d0 = vec3d2.y + itementity.getHeight() + 0.5;
                  double d5 = vec3d2.z;
                  double d4 = vec3d2.x;
                  if (ItemPickupMath.isCameraDoubleFloatDoubleDouble(camera, d0, f1, d5, d4)) {
                     Vec3d vec3d3 = DepthState.getVec3dByFloatEntityVec3d(f, itementity, vec3d);
                     double d1 = vec3d1.distanceTo(vec3d2);
                     ItemEspEntry itemespentry;
                     if (i1 < listx.size()) {
                        itemespentry = (ItemEspEntry)listx.get(i1);
                     } else {
                        itemespentry = new ItemEspEntry();
                        listx.add(itemespentry);
                     }

                     this.onItemEspEntryVec3dItemEntityDoubleFloat(itemespentry, vec3d3, itementity, d1, f2);
                     i1++;
                  }
               }
            }
         }

         if (i1 != 0) {
            RotationBuffer.setMinecraftClient2(this.client());
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            ShapeShader.update2();

            try {
               for (int k1 = 0; k1 < i1; k1++) {
                  ItemEspEntry itemespentry1 = (ItemEspEntry)listx.get(k1);
                  matrixstack.push();

                  try {
                     render(matrixstack, camera, itemespentry1);
                     Matrix4f matrix4f1 = matrixstack.peek().getPositionMatrix();
                     itemespentry1.matrix4f.set(matrix4f1);
                     this.onMatrix4fIntItemEspEntry2(matrix4f1, i, itemespentry1);
                  } finally {
                     matrixstack.pop();
                  }
               }
            } finally {
               ShapeShader.update();
            }

            ItemIconCache.setFloat(1.0F);
            TextShader.update3();

            try {
               for (int l1 = 0; l1 < i1; l1++) {
                  ItemEspEntry itemespentry2 = (ItemEspEntry)listx.get(l1);
                  int j1 = itemespentry2.flag2 ? l : k;
                  Matrix4f matrix4f = itemespentry2.matrix4f;
                  this.onMatrix4fIntItemEspEntry(matrix4f, j1, itemespentry2);
               }
            } finally {
               TextShader.update();
               ItemIconCache.update4();
               RenderSystem.depthMask(true);
               RenderSystem.enableDepthTest();
               RotationBuffer.setMinecraftClient(this.client());
            }

            for (int i2 = 0; i2 < i1; i2++) {
               ((ItemEspEntry)listx.get(i2)).update();
            }
         }
      }
   }

   private void onMatrix4fIntItemEspEntry(Matrix4f matrix4f, int count, ItemEspEntry itemEspEntry) {
      float f = -itemEspEntry.value5 / 2.0F;
      float f1 = -16.0F;
      float f2 = f1 + 8.0F;
      float f3 = f1 + 10.0F;
      float f4 = f1 + 9.0F;
      float f5 = f + 6.0F;
      float f7 = 1.0F;
      float f6 = 16.0F;
      ItemStack itemstack = itemEspEntry.itemStack;
      if (!ItemIcons.isFloatMatrix4fFloatFloatFloatItemStack(f5, matrix4f, f7, f6, f2, itemstack)) {
         float f8 = 16.0F;
         ItemStack itemstack1 = itemEspEntry.itemStack;
         ItemIconCache.onFloatFloatItemStackMatrix4fFloat(f5, f8, itemstack1, matrix4f, f2);
      }

      f5 += 16.0F;
      if (itemEspEntry.flag) {
         f5 += 4.0F;
         String s2 = itemEspEntry.text2;
         int j = Theme.mutedFg();
         float f10 = 1.0F;
         int i = j;
         float f9 = 12.0F;
         String s = s2;
         TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f3, f5, i, f9, f10, s, matrix4f);
         f5 += itemEspEntry.value4;
      }

      f5 += 6.0F;
      float f12 = 1.0F;
      float f11 = 14.0F;
      String s1 = itemEspEntry.text;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f4, f5, count, f11, f12, s1, matrix4f);
   }

   public boolean check3() {
      return this.mode.isString("Бокс") || this.mode.isString("Плашка + Бокс");
   }

   @Override
   public void onDisable() {
      this.list.clear();
      this.map.clear();
      this.list2.clear();
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!StreamBypass.check6()) {
         if (!this.notInGame()) {
            if (this.check4()) {
               try {
                  this.render8(worldRenderContext);
               } catch (Exception exception) {
               }
            }
         }
      }
   }

   private String getStringByItemStack(ItemStack itemStack) {
      Item item = itemStack.getItem();
      if (item == Items.TOTEM_OF_UNDYING) {
         TrackedItem trackeditem = SphereItems.getTrackedItemByItemStack(itemStack);
         if (trackeditem != null) {
            return trackeditem.text;
         }
      }

      if (item != Items.PLAYER_HEAD && item != Items.TOTEM_OF_UNDYING) {
         String s1 = this.map.get(item);
         if (s1 != null) {
            return s1;
         } else {
            String s = pattern.matcher(Text.translatable(item.getTranslationKey()).getString()).replaceAll("");
            this.map.put(item, s);
            return s;
         }
      } else {
         return pattern.matcher(itemStack.getName().getString()).replaceAll("");
      }
   }

   private void onItemEspEntryVec3dItemEntityDoubleFloat(ItemEspEntry itemEspEntry, Vec3d vec3d2, ItemEntity itemEntity, double value6, float value7) {
      itemEspEntry.vec3d = vec3d2;
      itemEspEntry.value = itemEntity.getHeight();
      itemEspEntry.value2 = DistanceScale.getFloatByDoubleFloat(value6, value7);
      itemEspEntry.itemStack = itemEntity.getStack();
      itemEspEntry.flag2 = SphereItems.getTrackedItemByItemStack(itemEspEntry.itemStack) != null;
      itemEspEntry.text = this.getStringByItemStack(itemEspEntry.itemStack);
      int i = itemEspEntry.itemStack.getCount();
      itemEspEntry.flag = i > 1;
      itemEspEntry.text2 = itemEspEntry.flag ? "x" + i : "";
      itemEspEntry.value3 = TextShader.getFloatByStringFloat(itemEspEntry.text, 14.0F);
      itemEspEntry.value4 = itemEspEntry.flag ? TextShader.getFloatByStringFloat(itemEspEntry.text2, 12.0F) : 0.0F;
      itemEspEntry.value5 = 22.0F + (itemEspEntry.flag ? 4.0F + itemEspEntry.value4 : 0.0F) + 6.0F + itemEspEntry.value3 + 6.0F;
   }

   private static int getIntByIntFloat(int count, float value) {
      float f = value < 0.0F ? 0.0F : Math.min(value, 1.0F);
      int i = count >> 24 & 0xFF;
      int j = Math.round(i * f);
      return j << 24 | count & 16777215;
   }

   private Boolean getBooleanBySupplier(Supplier<Boolean> supplier) {
      return (Boolean)supplier.get() && this.podsvechivatTaliki.isFlag3();
   }

   private void onMatrix4fIntItemEspEntry2(Matrix4f matrix4f, int count, ItemEspEntry itemEspEntry) {
      float f = -itemEspEntry.value5 / 2.0F;
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
      float f2 = itemEspEntry.value5;
      ShapeShader.onFloatIntIntFloatFloatFloatFloatFloatFloatMatrix4fFloatIntFloatFloatFloatFloatFloat(
         f2, b1, count, f, f1, f11, f4, f3, f10, matrix4f, f12, b0, f6, f8, f5, f7, f9
      );
   }

   private void render9(WorldRenderContext worldRenderContext) {
      Vec3d vec3d = this.player().getPos();
      boolean flag = this.onlyCennyeItems.isFlag3();
      this.list2.clear();

      for (Entity entity : this.clientWorld().getEntities()) {
         if (entity instanceof ItemEntity itementity) {
            double d1 = 1.0;
            double d0 = 128.0;
            if (ItemPickupMath.isVec3dItemEntityDoubleBooleanDouble(vec3d, itementity, d1, flag, d0)) {
               this.list2.add(itementity);
            }
         }
      }

      if (!this.list2.isEmpty()) {
         int i = this.colorLiniy.getInt3();
         int j = this.colorZalivki.getInt3();
         this.boxEspRenderer.setFlag(this.scaleOtDistancii.isFlag3());
         this.boxEspRenderer.onBooleanInt(this.outline.isFlag3(), this.colorObvodki.getInt3());
         BoxEspRenderer boxesprenderer = this.boxEspRenderer;
         ArrayList arraylist1 = this.list2;
         float f9 = MathUtil.getFloatByInt4(i);
         float f10 = MathUtil.getFloatByInt(i);
         float f11 = MathUtil.getFloatByInt2(i);
         float f12 = MathUtil.getFloatByInt3(i);
         float f13 = this.thicknessLines.getValueAsFloat();
         boolean flag2 = this.fill.isFlag3();
         float f14 = MathUtil.getFloatByInt4(j);
         float f15 = MathUtil.getFloatByInt(j);
         float f8 = MathUtil.getFloatByInt3(j);
         float f7 = MathUtil.getFloatByInt2(j);
         float f6 = f15;
         float f5 = f14;
         boolean flag1 = flag2;
         float f4 = f13;
         float f3 = f12;
         float f2 = f11;
         float f1 = f10;
         float f = f9;
         ArrayList arraylist = arraylist1;
         boxesprenderer.onFloatFloatListFloatFloatFloatFloatBooleanFloatFloatWorldRenderContextFloat(f5, f2, arraylist, f3, f1, f6, f8, flag1, f7, f, worldRenderContext, f4);
         this.list2.clear();
      }
   }

   public boolean check4() {
      return this.mode.isString("Плашка") || this.mode.isString("Плашка + Бокс");
   }

   private static void render(MatrixStack matrixStack, Camera camera, ItemEspEntry itemEspEntry) {
      matrixStack.translate(itemEspEntry.vec3d.x, itemEspEntry.vec3d.y + itemEspEntry.value + 0.5, itemEspEntry.vec3d.z);
      RotationBuffer.render(matrixStack);
      matrixStack.scale(-itemEspEntry.value2, -itemEspEntry.value2, -itemEspEntry.value2);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void render7(WorldRenderContext worldRenderContext) {
      if (!this.notInGame() && this.check3()) {
         try {
            this.render9(worldRenderContext);
         } catch (Exception exception) {
         }
      }
   }
}
