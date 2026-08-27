package client.gui.widget;

import client.api.ListEntry;
import client.api.Theme;
import client.data.AnimatedColor;
import client.data.AnimatedInt;
import client.data.Tween;
import client.module.CategoryType;
import client.render.ItemIconCache;
import client.render.ShapeShader;
import client.render.SvgShader;
import client.render.TextShader;
import client.setting.BlocklistSetting;
import client.setting.ColorSetting;
import client.util.EasingPresets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.joml.Matrix4f;

public class BlockEspPanel extends OverlayPanel<Block, BlockListEntry> {
   private static final int value277 = -1;
   private static final float value278 = 56.0F;
   private static final float value279 = 18.0F;
   private static final float value280 = 4.0F;
   private static final float value281 = 4.0F;
   private static final float value282 = 12.0F;
   private static final float value283 = 8.0F;
   private static final float value284 = 16.0F;
   private static final float value285 = 16.0F;
   private static final float value286 = 8.0F;
   private final BlocklistSetting blocklistSetting;
   private final List<Block> list = new ArrayList<>();
   private final List<Block> list2 = new ArrayList<>();
   private final ColorSetting blocklistEdit;
   private final ColorPicker colorPicker;
   private Block block;
   private boolean flag9;
   private final Map<Block, Tween> map;
   private final Map<Block, Tween> map2;

   public BlockEspPanel(BlocklistSetting blocklistSetting2) {
      ColorSetting colorsetting = new ColorSetting("", "", -1, true);
      colorsetting.setName("__blocklist_edit");
      this.blocklistEdit = colorsetting;
      this.colorPicker = new ColorPicker(this.blocklistEdit);
      this.map = new HashMap<>();
      this.map2 = new HashMap<>();
      this.blocklistSetting = blocklistSetting2;

      for (Block blockx : Registries.BLOCK) {
         if (blockx != Blocks.AIR
            && blockx != Blocks.CAVE_AIR
            && blockx != Blocks.VOID_AIR
            && (!(blockx instanceof ShulkerBoxBlock) || blockx == Blocks.SHULKER_BOX)
            && !new ItemStack(blockx).isEmpty()) {
            this.list2.add(blockx);
         }
      }

      this.list2.sort((item2, item3) -> this.getStringByBlock(item2).compareToIgnoreCase(this.getStringByBlock(item3)));
      this.blocklistEdit.setOnChange(() -> {
         if (this.block != null) {
            int i = this.blocklistEdit.getInt3();
            Block block1 = this.block;
            blocklistSetting2.onIntBlock(i, block1);
            BlockListEntry blocklistentry = this.getBlockListEntryByBlock(this.block);
            if (blocklistentry != null) {
               blocklistentry.animatedInt.setInt2(i);
            }
         }
      });
      this.colorPicker.setFlag5(false);
   }

   @Override
   protected String getString() {
      return "Выключить все";
   }

   @Override
   protected boolean isDoubleDoubleInt2(double value, double value2, int count) {
      if (!this.colorPicker.check()) {
         return false;
      } else {
         this.colorPicker.isDoubleDoubleInt(value, value2, count);
         return true;
      }
   }

   private BlockListEntry getBlockListEntryByBlock(Block block2) {
      for (BlockListEntry blocklistentry : (Iterable<BlockListEntry>)(this.scrollAnimator.getCollection())) {
         if (blocklistentry.block == block2) {
            return blocklistentry;
         }
      }

      return null;
   }

   @Override
   protected void update12() {
      this.flag9 = !this.flag9;
      this.update17();
   }

   @Override
   protected String getString2() {
      return "Настройте отображение блоков функции БлокЕсп.";
   }

   @Override
   protected boolean isDoubleDoubleDouble2(double value, double value2, double value3) {
      return this.colorPicker.check();
   }

   @Override
   protected boolean isIntChar2(int count, char symbol) {
      return !this.colorPicker.check() ? false : this.colorPicker.isIntChar(count, symbol);
   }

   @Override
   protected boolean isIntDoubleDouble3(int count, double value, double value2) {
      if (!this.colorPicker.check()) {
         return false;
      } else {
         float f4 = this.colorPicker.getValue235();
         float f5 = this.colorPicker.getValue236();
         float f3 = 383.0F;
         float f2 = 293.0F;
         float f1 = f5;
         float f = f4;
         if (isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value)) {
            this.colorPicker.isIntDoubleDouble(count, value, value2);
         } else {
            this.colorPicker.update4();
         }

         return true;
      }
   }

   @Override
   protected String getString3() {
      return "Введите название блока";
   }

   @Override
   protected void update13() {
      for (Block blockx : this.blocklistSetting.getMap().keySet()) {
         this.blocklistSetting.onBlockBoolean(blockx, false);
         BlockListEntry blocklistentry = this.getBlockListEntryByBlock(blockx);
         if (blocklistentry != null) {
            blocklistentry.tween.setFloat2(0.0F);
         }
      }

      if (this.flag9) {
         this.update17();
      }
   }

   @Override
   protected boolean isIntIntInt4(int count, int count2, int count3) {
      return !this.colorPicker.check() ? false : this.colorPicker.isIntIntInt2(count, count2, count3);
   }

   @Override
   protected String getString4() {
      return this.flag9 ? "Показать все" : "Только вкл.";
   }

   @Override
   protected boolean isDoubleDouble(double value, double value2) {
      if (super.isDoubleDouble(value, value2)) {
         return true;
      } else {
         if (this.colorPicker.check()) {
            float f4 = this.colorPicker.getValue235();
            float f5 = this.colorPicker.getValue236();
            float f3 = 383.0F;
            float f2 = 293.0F;
            float f1 = f5;
            float f = f4;
            if (isFloatFloatDoubleFloatFloatDouble(f, f1, value2, f3, f2, value)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   protected void update15() {
      this.update17();
   }

   @Override
   protected boolean isDoubleDoubleDoubleIntDouble(double value, double value2, double value3, int count, double value4) {
      if (!this.colorPicker.check()) {
         return false;
      } else {
         this.colorPicker.isDoubleDoubleIntDoubleDouble(value2, value, count, value4, value3);
         return true;
      }
   }

   @Override
   protected boolean isListEntryFloatFloatDoubleFloatDouble(ListEntry listEntry, float value, float value2, double value3, float value4, double value5) {
      BlockListEntry blocklistentry = (BlockListEntry)listEntry;
      return this.isDoubleFloatBlockListEntryFloatDoubleFloat(value3, value2, blocklistentry, value, value5, value4);
   }

   @Override
   protected void onMatrix4fFloatFloatFloatFloatFloatFloatListEntry(
      Matrix4f matrix4f, float value, float value2, float value3, float value4, float value5, float value6, ListEntry listEntry
   ) {
      BlockListEntry blocklistentry = (BlockListEntry)listEntry;
      this.onFloatFloatBlockListEntryMatrix4fFloatFloatFloatFloat(value2, value5, blocklistentry, matrix4f, value6, value3, value, value4);
   }

   @Override
   protected ListEntry getListEntryByObject2(Object value) {
      return this.getBlockListEntryByBlock2((Block)value);
   }

   @Override
   protected List<Block> getList() {
      return this.list;
   }

   protected BlockListEntry getBlockListEntryByBlock2(Block block2) {
      return new BlockListEntry(block2, this.blocklistSetting.isBlock(block2), this.getIntByBlock(block2));
   }

   private String getStringByBlock(Block block2) {
      return block2.getName().getString();
   }

   private int getIntByBlock(Block block2) {
      return this.blocklistSetting.getMap().containsKey(block2) ? this.blocklistSetting.getIntByBlock(block2) : -1;
   }

   private void update17() {
      String s = this.textField.getString() == null ? "" : this.textField.getString().trim().toLowerCase();
      this.list.clear();

      for (Block blockx : this.list2) {
         if ((!this.flag9 || this.blocklistSetting.isBlock(blockx)) && (s.isEmpty() || this.getStringByBlock(blockx).toLowerCase().contains(s))) {
            this.list.add(blockx);
         }
      }
   }

   @Override
   protected boolean check3() {
      return this.flag9;
   }

   @Override
   protected String getString5() {
      return "Отображаемые блоки";
   }

   protected boolean isDoubleFloatBlockListEntryFloatDoubleFloat(double value, float value2, BlockListEntry blockListEntry, float value3, double value4, float value5) {
      float f = value2 + value5 - 8.0F - 28.0F;
      float f1 = value3 + 5.0F;
      float f2 = f - 8.0F - 16.0F;
      float f3 = value3 + 5.0F;
      float f12 = f - 2.0F;
      float f13 = f1 - 3.0F;
      float f7 = 22.0F;
      float f6 = 32.0F;
      float f5 = f13;
      float f4 = f12;
      if (isFloatFloatDoubleFloatFloatDouble(f4, f5, value4, f7, f6, value)) {
         this.onBlock(blockListEntry.block);
      } else {
         f12 = f2 - 4.0F;
         f13 = f3 - 4.0F;
         float f11 = 24.0F;
         float f10 = 24.0F;
         float f9 = f13;
         float f8 = f12;
         if (isFloatFloatDoubleFloatFloatDouble(f8, f9, value4, f11, f10, value)) {
            this.onBlockFloat(blockListEntry.block, value3 + 13.0F);
         }
      }

      return true;
   }

   @Override
   protected void onFloatMatrix4fFloatMatrix4fFloatFloat(float value, Matrix4f matrix4f, float value2, Matrix4f matrix4f2, float value3, float value4) {
      if (this.colorPicker.check()) {
         this.colorPicker.onFloatFloatFloatMatrix4f2(value2, value, value3, matrix4f);
      }
   }

   private void onBlockFloat(Block block2, float value) {
      if (!this.blocklistSetting.getMap().containsKey(block2)) {
         BlocklistSetting blocklistsetting = this.blocklistSetting;
         boolean flag1 = this.blocklistSetting.isBlock(block2);
         byte b0 = -1;
         boolean flag = flag1;
         blocklistsetting.onBooleanBlockInt(flag, block2, b0);
      }

      this.block = block2;
      int i = this.getIntByBlock(block2);
      int k1 = i >> 16 & 0xFF;
      int j1 = i >> 8 & 0xFF;
      int l1 = i & 0xFF;
      int i1 = i >> 24 & 0xFF;
      int l = l1;
      int k = j1;
      int j = k1;
      this.blocklistEdit.onIntIntIntInt(j, k, i1, l);
      float f = this.value235 - 293.0F - 6.0F;
      if (f < 0.0F) {
         f = this.value235 + this.value237 + 6.0F;
      }

      float f1 = value - 191.5F;
      this.colorPicker.onFloatFloat2(f1, f);
      this.colorPicker.update3();
   }

   private void onBlock(Block block2) {
      boolean flag;
      if (this.blocklistSetting.getMap().containsKey(block2)) {
         flag = !this.blocklistSetting.isBlock(block2);
         this.blocklistSetting.onBlockBoolean(block2, flag);
      } else {
         byte b0 = -1;
         boolean flag1 = true;
         this.blocklistSetting.onBooleanBlockInt(flag1, block2, b0);
         flag = true;
      }

      BlockListEntry blocklistentry = this.getBlockListEntryByBlock(block2);
      if (blocklistentry != null) {
         blocklistentry.tween.setFloat2(flag ? 1.0F : 0.0F);
      }

      if (this.flag9) {
         this.update17();
      }
   }

   protected void onFloatFloatBlockListEntryMatrix4fFloatFloatFloatFloat(
      float value, float value2, BlockListEntry blockListEntry, Matrix4f matrix4f, float value3, float value4, float value5, float value6
   ) {
      Block blockx = blockListEntry.block;
      boolean flag = this.blocklistSetting.isBlock(blockx);
      blockListEntry.tween.setFloat2(flag ? 1.0F : 0.0F);
      float f = blockListEntry.tween.getFloat();
      float f1 = value * (0.4F + 0.6F * f);
      Tween tween = this.map.computeIfAbsent(blockx, var0 -> EasingPresets.getTween());
      double d4 = value6;
      double d5 = value2;
      float f15 = 26.0F;
      double d1 = d5;
      double d0 = d4;
      boolean flag1 = isFloatFloatDoubleFloatFloatDouble(value4, value3, d1, f15, value5, d0);
      float f17 = 6.0F;
      float f16 = 26.0F;
      PanelPainter.onFloatMatrix4fBooleanFloatFloatFloatTweenFloatFloat(value, matrix4f, flag1, f17, value3, f16, tween, value5, value4);
      float f2 = value4 + 6.0F;
      float f3 = value3 + 5.0F;
      ItemStack itemstack = new ItemStack(blockx);
      if (!itemstack.isEmpty()) {
         float f18 = 16.0F;
         ItemIconCache.onFloatFloatFloatItemStackMatrix4fFloat(f18, f1, f2, itemstack, matrix4f, f3);
      }

      int k2 = Theme.mutedFg();
      int j1 = Theme.foreground();
      int i1 = k2;
      int i = AnimatedInt.getIntByIntFloatInt(j1, f, i1);
      float f4 = value4 + value5 - 8.0F - 28.0F;
      float f5 = value3 + 5.0F;
      float f6 = f4 - 8.0F - 16.0F;
      float f7 = value3 + 5.0F;
      String s = String.format("#%06X", this.getIntByBlock(blockx) & 16777215);
      float f8 = TextShader.getFloatByStringFloat(s, 12.0F);
      float f9 = Math.max(56.0F, f8 + 8.0F);
      float f10 = f6 - 8.0F - f9;
      float f11 = value3 + 4.0F;
      int j = 0xFF000000 | this.getIntByBlock(blockx) & 16777215;
      int k = PanelPainter.getIntByInt(j);
      if (blockListEntry.animatedColor == null) {
         blockListEntry.animatedColor = new AnimatedColor(k, j, f9);
      }

      blockListEntry.animatedColor.onIntIntFloat(j, k, f9);
      float f12 = f2 + 16.0F + 6.0F;
      float f13 = value3 + 7.0F;
      float f14 = f10 - 6.0F - f12;
      float f19 = 26.0F;
      ScissorStack.onFloatFloatFloatFloat(f14, f19, value3, f12);
      TextShader.onMatrix4fStringFloatFloatFloatIntFloat3(matrix4f, this.getStringByBlock(blockx), f12, f13, 12.0F, i, f1);
      ScissorStack.update();
      float f34 = blockListEntry.animatedColor.getFloat();
      int k1 = blockListEntry.animatedColor.getInt2();
      float f22 = 4.0F;
      float f21 = 18.0F;
      float f20 = f34;
      ShapeShader.onFloatFloatIntMatrix4fFloatFloatFloatFloat(f22, f10, k1, matrix4f, f21, f20, f1, f11);
      float f33 = f10 + (blockListEntry.animatedColor.getFloat() - f8) / 2.0F;
      f34 = f11 + 3.0F;
      int l1 = blockListEntry.animatedColor.getInt();
      float f25 = 12.0F;
      float f24 = f34;
      float f23 = f33;
      TextShader.onFloatFloatIntFloatFloatStringMatrix4f(f24, f23, l1, f25, f1, s, matrix4f);
      Tween tween1 = this.map2.computeIfAbsent(blockx, var0 -> EasingPresets.getTween());
      d4 = value6;
      d5 = value2;
      f33 = f6 - 4.0F;
      f34 = f7 - 4.0F;
      float f29 = 24.0F;
      float f28 = 24.0F;
      float f27 = f34;
      float f26 = f33;
      double d3 = d5;
      double d2 = d4;
      boolean flag2 = isFloatFloatDoubleFloatFloatDouble(f26, f27, d3, f29, f28, d2);
      tween1.setFloat2(flag2 ? 1.0F : 0.0F);
      int l2 = Theme.mutedFg();
      int i3 = Theme.foreground();
      float f30 = tween1.getFloat();
      int j2 = i3;
      int i2 = l2;
      int l = AnimatedInt.getIntByIntFloatInt(j2, f30, i2);
      float f32 = 16.0F;
      float f31 = 16.0F;
      CategoryType categorytype = CategoryType.BRUSH;
      SvgShader.onFloatIntMatrix4fFloatCategoryTypeFloatFloatFloat(f1, l, matrix4f, f7, categorytype, f32, f6, f31);
      PanelPainter.onFloatFloatFloatMatrix4fFloat(f5, value, f4, matrix4f, f);
   }

   @Override
   protected void update16() {
      if (this.colorPicker.check()) {
         this.colorPicker.update4();
      }
   }
}
