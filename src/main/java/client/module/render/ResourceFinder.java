package client.module.render;

import client.data.ResourceEntry;
import client.data.ResourceItem;
import client.module.Category;
import client.module.Module;
import client.render.DepthState;
import client.render.HudRenderContext;
import client.render.WorldRenderContext;
import client.setting.ActionSetting;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.ResourceIndexSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.StringParts;
import client.util.UnsafeFields;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;

public class ResourceFinder extends Module {
   private static final long time = 5L * NANOS_PER_SECOND;
   private static final float[][] floatArrayArray = new float[][]{
      {0.0F, 0.0F, 0.0F},
      {1.0F, 0.0F, 0.0F},
      {0.0F, 0.0F, 1.0F},
      {1.0F, 0.0F, 1.0F},
      {0.0F, 1.0F, 0.0F},
      {1.0F, 1.0F, 0.0F},
      {0.0F, 1.0F, 1.0F},
      {1.0F, 1.0F, 1.0F}
   };
   private static final float[][] floatArrayArray2 = new float[][]{
      {1.0F, 1.0F, 1.0F},
      {-1.0F, 1.0F, 1.0F},
      {1.0F, 1.0F, -1.0F},
      {-1.0F, 1.0F, -1.0F},
      {1.0F, -1.0F, 1.0F},
      {-1.0F, -1.0F, 1.0F},
      {1.0F, -1.0F, -1.0F},
      {-1.0F, -1.0F, -1.0F}
   };
   private ListSetting modeEsp;
   private SliderSetting sizeUglov;
   private SliderSetting dalnost;
   private SliderSetting opacityZalivki;
   private ColorSetting colorPodsvetki;
   private ResourceIndexSetting indeksResursov;
   private ActionSetting ochistitIndeks;
   private BlockPos blockPos;
   private long time2;
   private HandledScreen<?> handledScreen;
   private int value235;
   private UnsafeFields<Integer> unsafeFields;
   private UnsafeFields<Integer> unsafeFields2;
   private UnsafeFields<Integer> unsafeFields3;
   private UnsafeFields<Integer> unsafeFields4;
   private boolean flag;
   private final float[] floatArray;

   public ResourceFinder() {
      super("ResourceFinder", Category.RENDER);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"Я", "щ", "и", "к"}),
            StringParts.join(new String[]{"У", "г", "л", "ы"}),
            StringParts.join(new String[]{"З", "a", "л", "и", "в", "к", "а"})
         ),
         List.of(StringParts.join(new String[]{"Я", "щ", "и", "к"})),
         false
      );
      listsetting.setName("Режим ЕСП");
      listsetting.setDescription("Как отрисовывать подсветку контейнеров");
      this.modeEsp = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting.setName("Размер углов");
      slidersetting.setDescription("Размер углов ЕСП");
      this.sizeUglov = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 64.0, 16.0, 256.0, 8.0);
      slidersetting1.setName("Дальность");
      slidersetting1.setDescription("Дальность подсветки");
      this.dalnost = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting2.setName("Прозрачность заливки");
      slidersetting2.setDescription("Прозрачность заливки контейнеров");
      this.opacityZalivki = slidersetting2;
      ColorSetting colorsetting = new ColorSetting("", "", -23296);
      colorsetting.setName("Цвет подсветки");
      colorsetting.setDescription("Цвет ЕСП контейнеров c искомым ресурсом");
      this.colorPodsvetki = colorsetting;
      ResourceIndexSetting resourceindexsetting = new ResourceIndexSetting("", "");
      resourceindexsetting.setName("Индекс ресурсов");
      resourceindexsetting.setDescription("Открой сундук/шалкер чтобы запомнить его содержимое");
      this.indeksResursov = resourceindexsetting;
      ActionSetting actionsetting = new ActionSetting("", "");
      actionsetting.setName("Очистить индекс");
      actionsetting.setDescription("Забыть все запомненные предметы");
      this.ochistitIndeks = actionsetting;
      this.floatArray = new float[3];
      this.sizeUglov.setVisibleWhen(this::getBoolean);
      this.opacityZalivki.setVisibleWhen(this::getBoolean2);
      this.addSettings(
         new Setting[]{this.modeEsp, this.sizeUglov, this.dalnost, this.opacityZalivki, this.colorPodsvetki, this.indeksResursov, this.ochistitIndeks}
      );
      this.ochistitIndeks.setRunnable(this.indeksResursov::update2);
   }

   @Override
   public void onTick() {
      if (!this.notInGame()) {
         Screen screen = this.currentScreen();
         if (screen instanceof HandledScreen handledscreen && !(screen instanceof InventoryScreen) && !(screen instanceof CreativeInventoryScreen)) {
            if (this.handledScreen != handledscreen) {
               this.handledScreen = handledscreen;
               this.value235 = 0;
            }

            int i = this.getIntByHandledScreen(handledscreen);
            if (i != 0 && i != this.value235) {
               this.value235 = i;
               List<ResourceEntry> list = this.getListByHandledScreen(handledscreen);
               if (!list.isEmpty()) {
                  BlockPos blockpos = this.getBlockPos();
                  if (blockpos != null) {
                     String s = this.world().getRegistryKey().getValue().toString();
                     List<BlockPos> list1 = this.getListByBlockPos(blockpos);
                     BlockPos blockpos1 = getBlockPosByList(list1);

                     for (BlockPos blockpos2 : list1) {
                        if (!blockpos2.equals(blockpos1)) {
                           this.indeksResursov.onStringBlockPos(s, blockpos2);
                        }
                     }

                     this.indeksResursov.onStringBlockPosList(s, blockpos1, list);
                  } else {
                     this.indeksResursov.onStringBlockPosList("virtual", getBlockPosByHandledScreen(handledscreen), list);
                  }
               }
            }
         } else {
            this.handledScreen = null;
         }
      }
   }

   private static void onStringBuilderString(StringBuilder stringBuilder, String text2) {
      if (text2 != null) {
         String s = Formatting.strip(text2);
         if (s != null) {
            stringBuilder.append(s);
         }
      }
   }

   private Boolean getBoolean() {
      return this.modeEsp.isString("Углы");
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame() && !this.indeksResursov.getSet().isEmpty()) {
         String s = this.world().getRegistryKey().getValue().toString();
         this.onString2(s);
         List<BlockPos> list = this.getListByList(this.indeksResursov.getListByString(s));
         if (!list.isEmpty()) {
            MatrixStack matrixstack = worldRenderContext.getMatrixStack();
            if (matrixstack != null) {
               Vec3d vec3d = worldRenderContext.getCamera().getPos();
               BlockPos blockpos = this.player().getBlockPos();
               int i = (int)this.dalnost.getValue();
               int j = i * i;
               int k = this.colorPodsvetki.getInt();
               this.floatArray[0] = (k >> 16 & 0xFF) / 255.0F;
               this.floatArray[1] = (k >> 8 & 0xFF) / 255.0F;
               this.floatArray[2] = (k & 0xFF) / 255.0F;
               if (this.modeEsp.isString("Заливка")) {
                  this.onListMatrixStackVec3dBlockPosInt(list, matrixstack, vec3d, blockpos, j);
               }

               if (this.modeEsp.isString("Ящик") || this.modeEsp.isString("Углы")) {
                  this.onVec3dIntBlockPosListMatrixStack(vec3d, j, blockpos, list, matrixstack);
               }
            }
         }
      }
   }

   private void onListMatrixStackVec3dBlockPosInt(List<BlockPos> list, MatrixStack matrixStack, Vec3d vec3d, BlockPos blockPos, int count) {
      DepthState.update2();
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
      float f = this.opacityZalivki.getValueAsFloat();

      for (BlockPos blockpos : list) {
         if (!(blockpos.getSquaredDistance(blockPos) > count)) {
            matrixStack.push();
            matrixStack.translate(blockpos.getX() - vec3d.x, blockpos.getY() - vec3d.y, blockpos.getZ() - vec3d.z);
            Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
            float f10 = this.floatArray[0];
            float f11 = this.floatArray[1];
            float f9 = this.floatArray[2];
            float f8 = f11;
            float f7 = f10;
            float f6 = 1.0F;
            float f5 = 1.0F;
            float f4 = 1.0F;
            float f3 = 0.0F;
            float f2 = 0.0F;
            float f1 = 0.0F;
            DepthState.onFloatFloatFloatFloatFloatMatrix4fFloatBufferBuilderFloatFloatFloatFloat(f8, f3, f2, f4, f9, matrix4f, f, bufferbuilder, f1, f6, f7, f5);
            matrixStack.pop();
         }
      }

      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      DepthState.update();
   }

   private void onBufferBuilderFloatMatrix4f(BufferBuilder bufferBuilder, float value, Matrix4f matrix4f) {
      for (int i = 0; i < floatArrayArray.length; i++) {
         float f = floatArrayArray[i][0];
         float f1 = floatArrayArray[i][1];
         float f2 = floatArrayArray[i][2];
         float f3 = floatArrayArray2[i][0] * value;
         float f4 = floatArrayArray2[i][1] * value;
         float f5 = floatArrayArray2[i][2] * value;
         float f24 = f + f3;
         float f25 = this.floatArray[0];
         float f26 = this.floatArray[1];
         float f27 = this.floatArray[2];
         float f11 = 1.0F;
         float f10 = f27;
         float f9 = f26;
         float f8 = f25;
         float f7 = 0.005F;
         float f6 = f24;
         DepthState.onFloatFloatFloatMatrix4fFloatFloatFloatFloatFloatBufferBuilderFloat(f8, f6, f, matrix4f, f1, f10, f2, f11, f9, bufferBuilder, f7);
         f24 = f1 + f4;
         f25 = this.floatArray[0];
         f26 = this.floatArray[1];
         f27 = this.floatArray[2];
         float f17 = 1.0F;
         float f16 = f27;
         float f15 = f26;
         float f14 = f25;
         float f13 = 0.005F;
         float f12 = f24;
         DepthState.onFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(f2, f15, matrix4f, f1, f, f17, bufferBuilder, f16, f13, f12, f14);
         f24 = f2 + f5;
         f25 = this.floatArray[0];
         f26 = this.floatArray[1];
         f27 = this.floatArray[2];
         float f23 = 1.0F;
         float f22 = f27;
         float f21 = f26;
         float f20 = f25;
         float f19 = 0.005F;
         float f18 = f24;
         DepthState.onMatrix4fFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloat(matrix4f, f20, f1, f22, f2, f19, f23, f, f21, bufferBuilder, f18);
      }
   }

   @Override
   public void onHudRenderContext(HudRenderContext hudRenderContext) {
      if (!this.notInGame() && !this.indeksResursov.getSet().isEmpty()) {
         if (this.currentScreen() instanceof HandledScreen handledscreen) {
            HashSet hashset = new HashSet();

            for (String s : this.indeksResursov.getSet()) {
               ResourceItem resourceitem = (ResourceItem)this.indeksResursov.getMap().get(s);
               if (resourceitem != null && resourceitem.text3 != null) {
                  hashset.add(resourceitem.text3.toLowerCase());
               }
            }

            if (!hashset.isEmpty()) {
               int[] aint = this.getIntArrayByHandledScreen(handledscreen);
               int i = this.colorPodsvetki.getInt3();
               DrawContext drawcontext = hudRenderContext.getDrawContext();

               for (Slot slot : handledscreen.getScreenHandler().slots) {
                  if (slot != null && slot.isEnabled() && slot.hasStack()) {
                     String s1 = getStringByString(slot.getStack().getName().getString()).toLowerCase();
                     if (!s1.isEmpty() && hashset.contains(s1)) {
                        drawcontext.fill(aint[0] + slot.x, aint[1] + slot.y, aint[0] + slot.x + 16, aint[1] + slot.y + 16, i);
                     }
                  }
               }
            }
         }
      }
   }

   private void onVec3dIntBlockPosListMatrixStack(Vec3d vec3d, int count, BlockPos blockPos, List<BlockPos> list, MatrixStack matrixStack) {
      boolean flagx = this.modeEsp.isString("Ящик");
      boolean flag1 = this.modeEsp.isString("Углы");
      DepthState.update2();
      BufferBuilder bufferbuilder = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

      for (BlockPos blockpos : list) {
         if (!(blockpos.getSquaredDistance(blockPos) > count)) {
            matrixStack.push();
            matrixStack.translate(blockpos.getX() - vec3d.x, blockpos.getY() - vec3d.y, blockpos.getZ() - vec3d.z);
            Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
            if (flagx) {
               float f12 = this.floatArray[0];
               float f13 = this.floatArray[1];
               float f14 = this.floatArray[2];
               float f10 = 1.0F;
               float f9 = f14;
               float f8 = f13;
               float f7 = f12;
               float f6 = 0.005F;
               float f5 = 1.0F;
               float f4 = 1.0F;
               float f3 = 1.0F;
               float f2 = 0.0F;
               float f1 = 0.0F;
               float f = 0.0F;
               DepthState.onFloatFloatFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(
                  f3, f10, f8, f1, matrix4f, f, f2, f4, bufferbuilder, f9, f6, f5, f7
               );
            }

            if (flag1) {
               float f11 = this.sizeUglov.getValueAsFloat();
               this.onBufferBuilderFloatMatrix4f(bufferbuilder, f11, matrix4f);
            }

            matrixStack.pop();
         }
      }

      BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
      DepthState.update();
   }

   private void onString2(String text2) {
      World world = this.world();
      ResourceIndexSetting resourceindexsetting = this.indeksResursov;
      Predicate<BlockPos> predicate = p0 -> ResourceFinder.isWorldBlockPos(world, p0);
      resourceindexsetting.onPredicateString(predicate, text2);
   }

   private List<BlockPos> getListByList(List<BlockPos> list) {
      if (list.isEmpty()) {
         return list;
      } else {
         HashSet hashset = new HashSet(list.size() * 2);

         for (BlockPos blockpos : list) {
            hashset.addAll(this.getListByBlockPos(blockpos));
         }

         return new ArrayList(hashset);
      }
   }

   private static int[] getIntArrayByString(String text2) {
      return new int[1];
   }

   private Boolean getBoolean2() {
      return this.modeEsp.isString("Заливка");
   }

   private void update11() {
      this.blockPos = null;
      this.handledScreen = null;
      this.value235 = 0;
   }

   private static void onStringBuilderString2(StringBuilder stringBuilder, String text2) {
      stringBuilder.append('\n');
      onStringBuilderString(stringBuilder, text2);
   }

   private static void onStringBuilderStatusEffectInstance(StringBuilder stringBuilder, StatusEffectInstance statusEffectInstance) {
      try {
         onStringBuilderString2(stringBuilder, ((StatusEffect)statusEffectInstance.getEffectType().value()).getName().getString());
      } catch (Throwable throwable) {
      }
   }

   private static boolean isWorldBlockPos(World world2, BlockPos blockPos) {
      return !world2.getChunkManager().isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4) ? true : isBlock(world2.getBlockState(blockPos).getBlock());
   }

   private int[] getIntArrayByHandledScreen(HandledScreen handledScreen) {
      if (!this.flag) {
         this.unsafeFields = new UnsafeFields<>(null, HandledScreen.class, 23);
         this.unsafeFields2 = new UnsafeFields<>(null, HandledScreen.class, 24);
         this.unsafeFields3 = new UnsafeFields<>(null, HandledScreen.class, 9);
         this.unsafeFields4 = new UnsafeFields<>(null, HandledScreen.class, 10);
         this.flag = true;
      }

      int i = this.unsafeFields.getIntByObject(handledScreen);
      int j = this.unsafeFields2.getIntByObject(handledScreen);
      if (i == 0 && j == 0) {
         int k = this.unsafeFields3.getIntByObject(handledScreen);
         int l = this.unsafeFields4.getIntByObject(handledScreen);
         if (k == 0) {
            k = 176;
         }

         if (l == 0) {
            l = 166;
         }

         return new int[]{(handledScreen.width - k) / 2, (handledScreen.height - l) / 2};
      } else {
         return new int[]{i, j};
      }
   }

   private int getIntByHandledScreen(HandledScreen handledScreen) {
      int i = 0;
      PlayerInventory playerinventory = this.inventory();

      for (Slot slot : handledScreen.getScreenHandler().slots) {
         if (slot.inventory != playerinventory) {
            ItemStack itemstack = slot.getStack();
            if (!itemstack.isEmpty()) {
               i = i * 31 + itemstack.getItem().hashCode();
               i = i * 31 + itemstack.getCount();
            }
         }
      }

      return i;
   }

   private static BlockPos getBlockPosByHandledScreen(HandledScreen handledScreen) {
      String s = handledScreen.getTitle() == null ? "" : handledScreen.getTitle().getString();
      return new BlockPos(s.hashCode(), handledScreen.getScreenHandler().slots.size(), 0);
   }

   private static boolean isBlock(Block block2) {
      return block2 instanceof ChestBlock || block2 instanceof EnderChestBlock || block2 instanceof ShulkerBoxBlock || block2 instanceof BarrelBlock;
   }

   private static BlockPos getBlockPosByList(List<BlockPos> list) {
      BlockPos blockpos = (BlockPos)list.getFirst();

      for (BlockPos blockpos1 : list) {
         if (blockpos1.asLong() < blockpos.asLong()) {
            blockpos = blockpos1;
         }
      }

      return blockpos;
   }

   private List getListByBlockPos(BlockPos blockPos) {
      BlockState blockstate = this.world().getBlockState(blockPos);
      if (!(blockstate.getBlock() instanceof ChestBlock)) {
         return List.of(blockPos);
      } else {
         ChestType chesttype = blockstate.getOrEmpty(Properties.CHEST_TYPE).orElse(ChestType.SINGLE);
         if (chesttype == ChestType.SINGLE) {
            return List.of(blockPos);
         } else {
            Direction direction = blockstate.getOrEmpty(Properties.HORIZONTAL_FACING).orElse(Direction.NORTH);
            Direction direction1 = chesttype == ChestType.LEFT ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
            return List.of(blockPos, blockPos.offset(direction1));
         }
      }
   }

   private BlockPos getBlockPos() {
      if (this.blockPos != null && System.nanoTime() - this.time2 <= time) {
         return this.blockPos;
      } else {
         if (this.client().crosshairTarget instanceof BlockHitResult blockhitresult && blockhitresult.getType() == Type.BLOCK) {
            BlockPos blockpos = blockhitresult.getBlockPos();
            if (isBlock(this.world().getBlockState(blockpos).getBlock())) {
               return blockpos.toImmutable();
            }
         }

         return null;
      }
   }

   @Override
   public ActionResult getActionResultByPlayerEntityWorldHandBlockHitResult(PlayerEntity playerEntity, World world2, Hand hand, BlockHitResult blockHitResult) {
      if (world2 != null && blockHitResult != null) {
         Block block = world2.getBlockState(blockHitResult.getBlockPos()).getBlock();
         if (isBlock(block)) {
            this.blockPos = blockHitResult.getBlockPos().toImmutable();
            this.time2 = System.nanoTime();
         }

         return ActionResult.PASS;
      } else {
         return ActionResult.PASS;
      }
   }

   private String getStringByPlayerEntityItemStack(PlayerEntity playerEntity, ItemStack itemStack) {
      StringBuilder stringbuilder = new StringBuilder();
      onStringBuilderString(stringbuilder, itemStack.getName().getString());
      Text text = (Text)itemStack.get(DataComponentTypes.CUSTOM_NAME);
      if (text != null) {
         onStringBuilderString2(stringbuilder, text.getString());
      }

      Text text1 = (Text)itemStack.get(DataComponentTypes.ITEM_NAME);
      if (text1 != null) {
         onStringBuilderString2(stringbuilder, text1.getString());
      }

      LoreComponent lorecomponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
      if (lorecomponent != null) {
         for (Text text2 : lorecomponent.lines()) {
            onStringBuilderString2(stringbuilder, text2.getString());
         }
      }

      try {
         for (Entry entry : EnchantmentHelper.getEnchantments(itemStack).getEnchantmentEntries()) {
            onStringBuilderString2(stringbuilder, ((Enchantment)((RegistryEntry)entry.getKey()).value()).description().getString());
         }
      } catch (Throwable throwable2) {
      }

      PotionContentsComponent potioncontentscomponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
      if (potioncontentscomponent != null) {
         try {
            onStringBuilderString2(stringbuilder, potioncontentscomponent.getName("").getString());
         } catch (Throwable throwable) {
         }

         potioncontentscomponent.forEachEffect(p0 -> ResourceFinder.onStringBuilderStatusEffectInstance(stringbuilder, p0));
      }

      try {
         List<Text> list = itemStack.getTooltip(TooltipContext.DEFAULT, playerEntity, TooltipType.ADVANCED);
         if (list != null) {
            for (Text text3 : list) {
               if (text3 != null) {
                  onStringBuilderString2(stringbuilder, text3.getString());
               }
            }
         }
      } catch (Throwable throwable1) {
      }

      Identifier identifier = Registries.ITEM.getId(itemStack.getItem());
      if (identifier != null) {
         stringbuilder.append('\n').append(identifier.getPath()).append(' ').append(identifier.getNamespace());
      }

      return stringbuilder.toString().toLowerCase();
   }

   private static String getStringByWrapperLookupItemStack(WrapperLookup wrapperLookup, ItemStack itemStack) {
      try {
         NbtElement nbtelement = itemStack.toNbtAllowEmpty(wrapperLookup);
         return nbtelement.toString();
      } catch (Throwable throwable) {
         return null;
      }
   }

   private static String getStringByString(String text2) {
      if (text2 == null) {
         return "";
      } else {
         String s = text2;

         while (s.startsWith("[")) {
            int i = s.indexOf(93);
            if (i < 0) {
               break;
            }

            s = s.substring(i + 1).stripLeading();
         }

         return s;
      }
   }

   private List<ResourceEntry> getListByHandledScreen(HandledScreen handledScreen) {
      HashMap<String, int[]> hashmap = new HashMap<>();
      HashMap<String, ItemStack> hashmap1 = new HashMap<>();
      HashMap<String, String> hashmap2 = new HashMap<>();
      PlayerInventory playerinventory = this.inventory();

      for (Slot slot : handledScreen.getScreenHandler().slots) {
         if (slot.inventory != playerinventory) {
            ItemStack itemstack = slot.getStack();
            if (!itemstack.isEmpty()) {
               Identifier identifier = Registries.ITEM.getId(itemstack.getItem());
               if (identifier != null) {
                  String s = identifier.toString();
                  String s1 = s + "\u0000" + itemstack.getName().getString();
                  hashmap.computeIfAbsent(s1, ResourceFinder::getIntArrayByString)[0] += itemstack.getCount();
                  hashmap1.put(s1, itemstack);
                  hashmap2.put(s1, s);
               }
            }
         }
      }

      PlayerEntity playerentity = this.player();
      DynamicRegistryManager dynamicregistrymanager = this.world().getRegistryManager();
      ArrayList<ResourceEntry> arraylist = new ArrayList<>(hashmap.size());

      for (java.util.Map.Entry<String, int[]> entry : hashmap.entrySet()) {
         String s2 = (String)entry.getKey();
         ItemStack itemstack1 = hashmap1.get(s2);
         arraylist.add(
            new ResourceEntry(
               s2,
               hashmap2.get(s2),
               getStringByString(itemstack1.getName().getString()),
               this.getStringByPlayerEntityItemStack(playerentity, itemstack1),
               getStringByWrapperLookupItemStack(dynamicregistrymanager, itemstack1),
               entry.getValue()[0]
            )
         );
      }

      return arraylist;
   }

   @Override
   public void onEnable() {
      this.update11();
   }
}
