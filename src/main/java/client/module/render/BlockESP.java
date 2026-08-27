package client.module.render;

import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.network.PacketEvent;
import client.render.DepthState;
import client.render.WorldRenderContext;
import client.setting.BlocklistSetting;
import client.setting.BooleanSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.BlockPosCounter;
import client.util.StringParts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.UnloadChunkS2CPacket;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BlockESP extends Module {
   private ListSetting modeEsp;
   private SliderSetting sizeUglov;
   private BooleanSetting withoutLimitaDalnosti;
   private SliderSetting dalnost;
   private SliderSetting opacityZalivki;
   private BooleanSetting onlyPovernutye;
   private BooleanSetting treysery;
   private BooleanSetting oblomkiThroughTnt;
   private BlocklistSetting spisokBlokov;
   private final Set<BlockPos> set;
   private final Set<BlockPos> set2;
   private final List<BlockPosCounter> list;
   private long time;
   private static final int[] intArray = new int[]{4, 10, 20, 40};
   private final Map<ChunkPos, Map<BlockPos, Block>> map;
   private final Set<ChunkPos> set3;
   private ExecutorService executorService;
   private final Map<BlockPos, Block> map2;
   private final float[] floatArray;
   private BlockPos blockPos;
   private int value235;
   private int value236;
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

   public BlockESP() {
      super("BlockESP", Category.RENDER);
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
      listsetting.setDescription("Режим отображения ЕСП");
      this.modeEsp = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting.setName("Размер углов");
      slidersetting.setDescription("Размер углов ЕСП");
      this.sizeUglov = slidersetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Без лимита дальности");
      booleansetting.setDescription("Показывать все прогруженные блоки без ограничения по дальности");
      this.withoutLimitaDalnosti = booleansetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 32.0, 16.0, 918.0, 8.0);
      slidersetting1.setName("Дальность");
      slidersetting1.setDescription("Дальность поиска блоков");
      this.dalnost = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.3, 0.1, 1.0, 0.1);
      slidersetting2.setName("Прозрачность заливки");
      slidersetting2.setDescription("Прозрачность заливки блоков");
      this.opacityZalivki = slidersetting2;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", false);
      booleansetting1.setName("Только повёрнутые");
      booleansetting1.setDescription("Показывать только блоки поставленные игроком (базальт/логи c горизонтальной осью)");
      this.onlyPovernutye = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", false);
      booleansetting2.setName("Трейсеры");
      booleansetting2.setDescription("Рисовать линии от игрока к блокам");
      this.treysery = booleansetting2;
      BooleanSetting booleansetting3 = new BooleanSetting("", "", false);
      booleansetting3.setName("Обломки через TNT");
      booleansetting3.setDescription("Показывать только незеритовые обломки, вскрытые взрывом TNT");
      this.oblomkiThroughTnt = booleansetting3;
      BlocklistSetting blocklistsetting = new BlocklistSetting("", "");
      blocklistsetting.setName("Список блоков");
      blocklistsetting.setDescription("Управление блоками для ЕСП");
      this.spisokBlokov = blocklistsetting;
      this.set = ConcurrentHashMap.newKeySet();
      this.set2 = ConcurrentHashMap.newKeySet();
      this.list = new ArrayList<>();
      this.time = 0L;
      this.map = new ConcurrentHashMap<>();
      this.set3 = ConcurrentHashMap.newKeySet();
      this.map2 = new HashMap<>();
      this.floatArray = new float[3];
      this.blockPos = null;
      this.value235 = -1;
      this.value236 = 0;
      this.update14();
      this.addSettings(
         new Setting[]{
            this.modeEsp,
            this.sizeUglov,
            this.withoutLimitaDalnosti,
            this.dalnost,
            this.opacityZalivki,
            this.onlyPovernutye,
            this.treysery,
            this.oblomkiThroughTnt,
            this.spisokBlokov
         }
      );
      this.onlyPovernutye.setOnChange(this::update12);
      this.withoutLimitaDalnosti.setOnChange(this::update17);
      this.update11();
   }

   private Boolean getBoolean() {
      return this.modeEsp.isString("Углы");
   }

   @Override
   public void onTick() {
      if (this.world() != null && this.player() != null) {
         this.update18();
         if (this.oblomkiThroughTnt.isFlag3()) {
            Iterator iterator = this.list.iterator();

            while (iterator.hasNext()) {
               BlockPosCounter blockposcounter = (BlockPosCounter)iterator.next();
               if (--blockposcounter.value <= 0) {
                  byte b0 = 28;
                  BlockPos blockpos1 = blockposcounter.blockPos;
                  this.onIntBlockPos(b0, blockpos1);
                  iterator.remove();
               }
            }

            long i = System.currentTimeMillis();
            if (i - this.time > 50L) {
               for (BlockPos blockpos : this.set) {
                  if (this.set2.add(blockpos)) {
                     if (this.client().getNetworkHandler() != null) {
                        this.client().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, blockpos, Direction.UP));
                        this.client().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, blockpos, Direction.UP));
                     }

                     this.time = i;
                     break;
                  }
               }
            }

            this.set.removeIf(this::isBlockPos2);
         }
      }
   }

   private void onIntInt(int count, int count2) {
      if (this.world() != null) {
         WorldChunk worldchunk = this.world().getChunk(count2, count);
         if (worldchunk != null) {
            ChunkSection[] achunksection = worldchunk.getSectionArray();
            int i = count2 << 4;
            int j = count << 4;
            ConcurrentHashMap concurrenthashmap = new ConcurrentHashMap();

            for (int k = 0; k < achunksection.length; k++) {
               ChunkSection chunksection = achunksection[k];
               if (chunksection != null && !chunksection.isEmpty() && chunksection.hasAny(this::isBlockState)) {
                  int l = worldchunk.sectionIndexToCoord(k) << 4;

                  for (int i1 = 0; i1 < 16; i1++) {
                     for (int j1 = 0; j1 < 16; j1++) {
                        for (int k1 = 0; k1 < 16; k1++) {
                           BlockState blockstate = chunksection.getBlockState(i1, j1, k1);
                           if (this.isBlockState(blockstate)) {
                              BlockPos blockpos = new BlockPos(i + i1, l + j1, j + k1);
                              concurrenthashmap.put(blockpos, blockstate.getBlock());
                           }
                        }
                     }
                  }
               }
            }

            ChunkPos chunkpos = new ChunkPos(count2, count);
            if (concurrenthashmap.isEmpty()) {
               this.map.remove(chunkpos);
            } else {
               this.map.put(chunkpos, concurrenthashmap);
            }
         }
      }
   }

   private boolean isBlockPos(BlockPos blockPos) {
      int i = 0;

      for (int j = -1; j <= 1; j++) {
         for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
               Block block = this.world().getBlockState(blockPos.add(j, k, l)).getBlock();
               if (block == Blocks.NETHER_QUARTZ_ORE || block == Blocks.NETHER_GOLD_ORE) {
                  if (++i >= 4) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean isBlockPosInt(BlockPos blockPos, int count) {
      int i = 0;

      for (int j = -3; j <= 2; j++) {
         for (int k = -2; k <= 2; k++) {
            for (int l = -2; l <= 3; l++) {
               if (this.world().getBlockState(blockPos.add(j, k, l)).getBlock() == Blocks.ANCIENT_DEBRIS) {
                  if (++i > count) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private Boolean getBoolean2() {
      return !this.oblomkiThroughTnt.isFlag3();
   }

   private boolean isBlockState(BlockState blockState) {
      return this.isBlock2(blockState.getBlock()) && this.isBlockState2(blockState);
   }

   private boolean isBlock(Block block2) {
      return this.oblomkiThroughTnt.isFlag3() ? block2 == Blocks.ANCIENT_DEBRIS : this.spisokBlokov.isBlock(getBlockByBlock(block2));
   }

   private void update11() {
      this.spisokBlokov.onBlockBooleanInt(Blocks.DIAMOND_ORE, true, -16711681);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_DIAMOND_ORE, true, -16711681);
      this.spisokBlokov.onBlockBooleanInt(Blocks.GOLD_ORE, true, -10496);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_GOLD_ORE, true, -10496);
      this.spisokBlokov.onBlockBooleanInt(Blocks.NETHER_GOLD_ORE, true, -10496);
      this.spisokBlokov.onBlockBooleanInt(Blocks.RAW_GOLD_BLOCK, true, -10496);
      this.spisokBlokov.onBlockBooleanInt(Blocks.GOLD_BLOCK, true, -10496);
      this.spisokBlokov.onBlockBooleanInt(Blocks.ANCIENT_DEBRIS, true, -8388480);
      this.spisokBlokov.onBlockBooleanInt(Blocks.NETHERITE_BLOCK, true, -8388480);
      this.spisokBlokov.onBlockBooleanInt(Blocks.EMERALD_ORE, true, -16711936);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_EMERALD_ORE, true, -16711936);
      this.spisokBlokov.onBlockBooleanInt(Blocks.IRON_ORE, false, -3618616);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_IRON_ORE, false, -3618616);
      this.spisokBlokov.onBlockBooleanInt(Blocks.COAL_ORE, false, -13487566);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_COAL_ORE, false, -13487566);
      this.spisokBlokov.onBlockBooleanInt(Blocks.REDSTONE_ORE, false, -65536);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_REDSTONE_ORE, false, -65536);
      this.spisokBlokov.onBlockBooleanInt(Blocks.LAPIS_ORE, false, -16751361);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_LAPIS_ORE, false, -16751361);
      this.spisokBlokov.onBlockBooleanInt(Blocks.COPPER_ORE, false, -29696);
      this.spisokBlokov.onBlockBooleanInt(Blocks.DEEPSLATE_COPPER_ORE, false, -29696);
      this.spisokBlokov.onBlockBooleanInt(Blocks.CHEST, true, -7650029);
      this.spisokBlokov.onBlockBooleanInt(Blocks.TRAPPED_CHEST, true, -7650029);
      this.spisokBlokov.onBlockBooleanInt(Blocks.ENDER_CHEST, true, -8388480);
      this.spisokBlokov.onBlockBooleanInt(Blocks.SPAWNER, true, -39836);
      this.spisokBlokov.onBlockBooleanInt(Blocks.SHULKER_BOX, true, -5635926);
   }

   private Boolean getBoolean3() {
      return !this.withoutLimitaDalnosti.isFlag3();
   }

   private boolean isBlockPos2(BlockPos blockPos) {
      return this.world().getBlockState(blockPos).getBlock() != Blocks.ANCIENT_DEBRIS;
   }

   private void update12() {
      this.map.clear();
      this.set3.clear();
      this.map2.clear();
      this.blockPos = null;
      this.value235 = -1;
      this.update13();
   }

   private void onIntInt2(int count, int count2) {
      try {
         this.onIntInt(count2, count);
      } catch (Exception exception) {
      }
   }

   private boolean isBlockState2(BlockState blockState) {
      return !this.onlyPovernutye.isFlag3() || isBlockState3(blockState);
   }

   @Override
   public void onDisable() {
      this.map.clear();
      this.set3.clear();
      this.map2.clear();
      this.set.clear();
      this.set2.clear();
      this.list.clear();
      if (this.executorService != null) {
         this.executorService.shutdownNow();
         this.executorService = null;
      }
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!this.notInGame()) {
         try {
            this.update15();
            this.render8(worldRenderContext);
         } catch (Exception exception) {
         }
      }
   }

   private Boolean getBoolean4() {
      return this.modeEsp.isString("Заливка");
   }

   private void update13() {
      if (this.world() != null && this.player() != null) {
         int i = this.client().options.getClampedViewDistance() + 1;
         int j = this.player().getChunkPos().x;
         int k = this.player().getChunkPos().z;

         for (int l = -i; l <= i; l++) {
            for (int i1 = -i; i1 <= i; i1++) {
               this.set3.add(new ChunkPos(j + l, k + i1));
            }
         }
      }
   }

   private static Map getMapByChunkPos(ChunkPos chunkPos) {
      return new ConcurrentHashMap();
   }

   private void onListList(List list, List list2) {
      for (int i = 0; i < list.size(); i++) {
         BlockPos blockpos1 = (BlockPos)list.get(i);
         BlockState blockstate = (BlockState)list2.get(i);
         BlockPos blockpos = blockpos1;
         this.onBlockStateBlockPos(blockstate, blockpos);
      }
   }

   private static void onListListBlockPosBlockState(List list, List list2, BlockPos blockPos, BlockState blockState) {
      list.add(blockPos.toImmutable());
      list2.add(blockState);
   }

   private void onFloatBufferBuilderMatrixStackFloatArray(float value, BufferBuilder bufferBuilder, MatrixStack matrixStack, float[] valueArray) {
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      float f = this.sizeUglov.getValueAsFloat();

      for (int i = 0; i < floatArrayArray.length; i++) {
         float f1 = floatArrayArray[i][0];
         float f2 = floatArrayArray[i][1];
         float f3 = floatArrayArray[i][2];
         float f4 = floatArrayArray2[i][0] * f;
         float f5 = floatArrayArray2[i][1] * f;
         float f6 = floatArrayArray2[i][2] * f;
         float f22 = f1 + f4;
         float f23 = valueArray[0];
         float f24 = valueArray[1];
         float f25 = valueArray[2];
         float f11 = 1.0F;
         float f10 = f25;
         float f9 = f24;
         float f8 = f23;
         float f7 = f22;
         DepthState.onFloatFloatFloatMatrix4fFloatFloatFloatFloatFloatBufferBuilderFloat(f8, f7, f1, matrix4f, f2, f10, f3, f11, f9, bufferBuilder, value);
         f22 = f2 + f5;
         f23 = valueArray[0];
         f24 = valueArray[1];
         f25 = valueArray[2];
         float f16 = 1.0F;
         float f15 = f25;
         float f14 = f24;
         float f13 = f23;
         float f12 = f22;
         DepthState.onFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(f3, f14, matrix4f, f2, f1, f16, bufferBuilder, f15, value, f12, f13);
         f22 = f3 + f6;
         f23 = valueArray[0];
         f24 = valueArray[1];
         f25 = valueArray[2];
         float f21 = 1.0F;
         float f20 = f25;
         float f19 = f24;
         float f18 = f23;
         float f17 = f22;
         DepthState.onMatrix4fFloatFloatFloatFloatFloatFloatFloatFloatBufferBuilderFloat(matrix4f, f18, f2, f20, f3, value, f21, f1, f19, bufferBuilder, f17);
      }
   }

   private void onBlockFloatArray(Block block2, float[] valueArray) {
      int i = this.spisokBlokov.getIntByBlock(getBlockByBlock(block2));
      valueArray[0] = (i >> 16 & 0xFF) / 255.0F;
      valueArray[1] = (i >> 8 & 0xFF) / 255.0F;
      valueArray[2] = (i & 0xFF) / 255.0F;
   }

   private static Thread getThreadByRunnable(Runnable runnable) {
      Thread thread = new Thread(runnable, "z");
      thread.setDaemon(true);
      return thread;
   }

   private static boolean isIntIntIntChunkPos(int count, int count2, int count3, ChunkPos chunkPos) {
      return Math.abs(chunkPos.x - count) > count2 || Math.abs(chunkPos.z - count3) > count2;
   }

   private Boolean getBoolean5() {
      return !this.oblomkiThroughTnt.isFlag3();
   }

   private void addBlockPos(BlockPos blockPos) {
      for (int i : intArray) {
         this.list.add(new BlockPosCounter(blockPos, i));
      }
   }

   private void onBlockPosBlockState(BlockPos blockPos, BlockState blockState) {
      this.onBlockStateBlockPos(blockState, blockPos);
   }

   private static Block getBlockByBlock(Block block2) {
      return block2 instanceof ShulkerBoxBlock ? Blocks.SHULKER_BOX : block2;
   }

   private boolean isBlockPos3(BlockPos blockPos) {
      if (this.world() == null) {
         return false;
      } else if (this.world().getBlockState(blockPos).getBlock() != Blocks.ANCIENT_DEBRIS) {
         return false;
      } else {
         if (this.isBlockPosInt2(blockPos, 2) && !this.isBlockPos(blockPos)) {
            byte b0 = 4;
            if (this.isIntBlockPos(b0, blockPos) && !this.isBlockPosInt(blockPos, 6)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isBlockPosInt2(BlockPos blockPos, int count) {
      int i = 0;

      for (Direction direction : Direction.values()) {
         Block block = this.world().getBlockState(blockPos.offset(direction)).getBlock();
         if (block == Blocks.AIR || block == Blocks.LAVA || block == Blocks.CAVE_AIR) {
            if (++i >= count) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isIntBlockPos(int count, BlockPos blockPos) {
      int i = 0;

      for (int j = -1; j <= 1; j++) {
         for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
               Block block = this.world().getBlockState(blockPos.add(j, k, l)).getBlock();
               if (block == Blocks.AIR || block == Blocks.LAVA || block == Blocks.CAVE_AIR) {
                  if (++i >= count) {
                     return true;
                  }
               }
            }
         }
      }

      return i >= count;
   }

   private void onIntInt3(int count, int count2) {
      if (this.executorService != null) {
         this.executorService.submit(() -> this.onIntInt2(count, count));
      }
   }

   private void onIntBlockPos(int count, BlockPos blockPos) {
      Mutable mutable = new Mutable();

      for (int i = -count; i <= count; i++) {
         for (int j = -count; j <= count; j++) {
            for (int k = -count; k <= count; k++) {
               mutable.set(blockPos.getX() + i, blockPos.getY() + j, blockPos.getZ() + k);
               if (this.isBlockPos3(mutable)) {
                  this.set.add(mutable.toImmutable());
               }
            }
         }
      }
   }

   private void onBlockStateBlockPos(BlockState blockState, BlockPos blockPos) {
      Block block = blockState.getBlock();
      if (this.oblomkiThroughTnt.isFlag3()) {
         if (block == Blocks.ANCIENT_DEBRIS) {
            if (this.isBlockPos3(blockPos)) {
               this.set.add(blockPos.toImmutable());
            }
         } else {
            this.set.remove(blockPos);
            this.set2.remove(blockPos);
         }
      } else {
         ChunkPos chunkpos = new ChunkPos(blockPos);
         boolean flag = this.isBlock2(block) && this.isBlockState2(blockState);
         if (flag) {
            Map mapx = this.map.computeIfAbsent(chunkpos, BlockESP::getMapByChunkPos);
            mapx.put(blockPos, block);
         } else {
            Map map1 = this.map.get(chunkpos);
            if (map1 != null) {
               map1.remove(blockPos);
               if (map1.isEmpty()) {
                  this.map.remove(chunkpos);
               }
            }
         }
      }
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (packetEvent.getPacketDirection() == PacketDirection.RECEIVE) {
         if (this.isEnabled()) {
            Packet packet = packetEvent.getPacket();
            if (this.oblomkiThroughTnt.isFlag3() && packet instanceof ExplosionS2CPacket explosions2cpacket) {
               BlockPos blockpos = BlockPos.ofFloored(explosions2cpacket.center());
               this.client().execute(() -> this.addBlockPos(blockpos));
            } else {
               if (packet instanceof ChunkDataS2CPacket chunkdatas2cpacket) {
                  this.set3.add(new ChunkPos(chunkdatas2cpacket.getChunkX(), chunkdatas2cpacket.getChunkZ()));
               } else if (packet instanceof UnloadChunkS2CPacket unloadchunks2cpacket) {
                  UnloadChunkS2CPacket unloadchunks2cpacket1 = unloadchunks2cpacket;

                  ChunkPos chunkpos = unloadchunks2cpacket1.pos();
                  this.map.remove(chunkpos);
                  this.set3.remove(chunkpos);
               } else if (packet instanceof BlockUpdateS2CPacket blockupdates2cpacket) {
                  BlockPos blockpos1 = blockupdates2cpacket.getPos().toImmutable();
                  BlockState blockstate = blockupdates2cpacket.getState();
                  this.client().execute(() -> this.onBlockPosBlockState(blockpos1, blockstate));
               } else if (packet instanceof ChunkDeltaUpdateS2CPacket chunkdeltaupdates2cpacket) {
                  ArrayList<BlockPos> arraylist = new ArrayList<>();
                  ArrayList<BlockState> arraylist1 = new ArrayList<>();
                  chunkdeltaupdates2cpacket.visitUpdates((p0, p1) -> BlockESP.onListListBlockPosBlockState(arraylist, arraylist1, p0, p1));
                  this.client().execute(() -> this.onListList(arraylist, arraylist1));
               }
            }
         }
      }
   }

   private static boolean isBlockState3(BlockState blockState) {
      if (blockState.contains(Properties.AXIS)) {
         return blockState.get(Properties.AXIS) != Axis.Y;
      } else if (blockState.contains(Properties.HORIZONTAL_AXIS)) {
         return true;
      } else if (!blockState.contains(Properties.FACING)) {
         return false;
      } else {
         Direction direction = (Direction)blockState.get(Properties.FACING);
         return direction != Direction.UP && direction != Direction.DOWN;
      }
   }

   private void onFloatArrayBufferBuilderFloatMatrixStack(float[] valueArray, BufferBuilder bufferBuilder, float value, MatrixStack matrixStack) {
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      float f10 = valueArray[0];
      float f11 = valueArray[1];
      float f12 = valueArray[2];
      float f9 = 1.0F;
      float f8 = f12;
      float f7 = f11;
      float f6 = f10;
      float f5 = 1.0F;
      float f4 = 1.0F;
      float f3 = 1.0F;
      float f2 = 0.0F;
      float f1 = 0.0F;
      float f = 0.0F;
      DepthState.onFloatFloatFloatFloatMatrix4fFloatFloatFloatBufferBuilderFloatFloatFloatFloat(f3, f9, f7, f1, matrix4f, f, f2, f4, bufferBuilder, f8, value, f5, f6);
   }

   private void render8(WorldRenderContext worldRenderContext) {
      MatrixStack matrixstack = worldRenderContext.getMatrixStack();
      if (matrixstack != null) {
         boolean flag = this.oblomkiThroughTnt.isFlag3();
         if (flag) {
            if (this.set.isEmpty()) {
               return;
            }

            this.map2.clear();

            for (BlockPos blockpos : this.set) {
               this.map2.put(blockpos, Blocks.ANCIENT_DEBRIS);
            }
         } else if (this.map.isEmpty()) {
            return;
         }

         Vec3d vec3d = worldRenderContext.getCamera().getPos();
         BlockPos blockpos3 = this.player().getBlockPos();
         boolean flag1 = this.withoutLimitaDalnosti.isFlag3();
         int i = (int)this.dalnost.getValue();
         int j = i * i;
         boolean flag2 = this.modeEsp.isString("Ящик");
         boolean flag3 = this.modeEsp.isString("Углы");
         boolean flag4 = this.modeEsp.isString("Заливка");
         boolean flag5 = this.treysery.isFlag3();
         int k = flag1 ? Integer.MAX_VALUE : i;
         boolean flag6 = k != this.value235;
         boolean flag7 = this.blockPos == null
            || blockpos3.getX() >> 4 != this.blockPos.getX() >> 4
            || blockpos3.getZ() >> 4 != this.blockPos.getZ() >> 4
            || Math.abs(blockpos3.getY() - this.blockPos.getY()) > 8;
         this.value236++;
         boolean flag8 = this.value236 >= 4;
         if (!flag && (flag6 || flag7 || flag8)) {
            this.value236 = 0;
            this.blockPos = blockpos3;
            this.value235 = k;
            this.map2.clear();

            for (Map mapx : this.map.values()) {
               for (Entry entry : (Iterable<Entry>)(mapx.entrySet())) {
                  BlockPos blockpos1 = (BlockPos)entry.getKey();
                  if (flag1 || blockpos1.getSquaredDistance(blockpos3) <= j) {
                     this.map2.put(blockpos1, (Block)entry.getValue());
                  }
               }
            }
         }

         if (!this.map2.isEmpty()) {
            if (flag4) {
               Map map1 = this.map2;
               this.onMapMatrixStackVec3d(map1, matrixstack, vec3d);
            }

            if (flag2 || flag3) {
               DepthState.update2();
               Tessellator tessellator = Tessellator.getInstance();
               BufferBuilder bufferbuilder = tessellator.begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
               float f = 0.005F;

               for (Entry entry1 : this.map2.entrySet()) {
                  BlockPos blockpos2 = (BlockPos)entry1.getKey();
                  Block block = (Block)entry1.getValue();
                  if (this.isBlock(block)) {
                     matrixstack.push();
                     matrixstack.translate(blockpos2.getX() - vec3d.x, blockpos2.getY() - vec3d.y, blockpos2.getZ() - vec3d.z);
                     this.onBlockFloatArray(block, this.floatArray);
                     if (flag2) {
                        float[] afloat = this.floatArray;
                        this.onFloatArrayBufferBuilderFloatMatrixStack(afloat, bufferbuilder, f, matrixstack);
                     }

                     if (flag3) {
                        float[] afloat1 = this.floatArray;
                        this.onFloatBufferBuilderMatrixStackFloatArray(f, bufferbuilder, matrixstack, afloat1);
                     }

                     matrixstack.pop();
                  }
               }

               BufferRenderer.drawWithGlobalProgram(bufferbuilder.end());
               DepthState.update();
            }

            if (flag5) {
               this.render(matrixstack, vec3d, worldRenderContext);
            }
         }
      }
   }

   private void onMapMatrixStackVec3d(Map map, MatrixStack matrixStack, Vec3d vec3d) {
      DepthState.update2();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
      float f = this.opacityZalivki.getValueAsFloat();

      for (Entry entry : (Iterable<Entry>)(map.entrySet())) {
         BlockPos blockpos = (BlockPos)entry.getKey();
         Block block = (Block)entry.getValue();
         if (this.isBlock(block)) {
            matrixStack.push();
            matrixStack.translate(blockpos.getX() - vec3d.x, blockpos.getY() - vec3d.y, blockpos.getZ() - vec3d.z);
            this.onBlockFloatArray(block, this.floatArray);
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

   private void update14() {
      this.sizeUglov.setVisibleWhen(this::getBoolean);
      this.opacityZalivki.setVisibleWhen(this::getBoolean4);
      this.dalnost.setVisibleWhen(this::getBoolean3);
      this.spisokBlokov.setVisibleWhen(this::getBoolean2);
      this.onlyPovernutye.setVisibleWhen(this::getBoolean5);
      this.oblomkiThroughTnt.setOnChange(this::update16);
   }

   private boolean isBlock2(Block block2) {
      return this.spisokBlokov.isBlock(getBlockByBlock(block2));
   }

   private void render(MatrixStack matrixStack, Vec3d vec3d, WorldRenderContext worldRenderContext) {
      DepthState.update2();
      BufferBuilder bufferbuilder = DepthState.getBufferBuilderByFloat(1.0F);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      Vector3f vector3f = worldRenderContext.getCamera().getHorizontalPlane();
      double d0 = vector3f.x * 0.5;
      double d1 = vector3f.y * 0.5 - 0.1;
      double d2 = vector3f.z * 0.5;

      for (Entry entry : this.map2.entrySet()) {
         BlockPos blockpos = (BlockPos)entry.getKey();
         Block block = (Block)entry.getValue();
         if (this.spisokBlokov.isBlock(getBlockByBlock(block))) {
            this.onBlockFloatArray(block, this.floatArray);
            double d3 = blockpos.getX() + 0.5 - vec3d.x;
            double d4 = blockpos.getY() + 0.5 - vec3d.y;
            double d5 = blockpos.getZ() + 0.5 - vec3d.z;
            float f10 = (float)d0;
            float f11 = (float)d1;
            float f12 = (float)d2;
            float f13 = (float)d3;
            float f14 = (float)d4;
            float f15 = (float)d5;
            float f16 = this.floatArray[0];
            float f17 = this.floatArray[1];
            float f18 = this.floatArray[2];
            float f9 = 1.0F;
            float f8 = f18;
            float f7 = f17;
            float f6 = f16;
            float f5 = f15;
            float f4 = f14;
            float f3 = f13;
            float f2 = f12;
            float f1 = f11;
            float f = f10;
            DepthState.onBufferBuilderFloatFloatFloatFloatFloatFloatFloatFloatFloatMatrix4fFloat(bufferbuilder, f8, f4, f9, f7, f, f6, f5, f1, f2, matrix4f, f3);
         }
      }

      DepthState.onBufferBuilder(bufferbuilder);
      DepthState.update();
   }

   private void update15() {
      if (this.player() != null) {
         int i = this.client().options.getClampedViewDistance() + 2;
         int j = this.player().getChunkPos().x;
         int k = this.player().getChunkPos().z;
         Predicate<ChunkPos> predicate = p0 -> BlockESP.isIntIntIntChunkPos(k, k, k, p0);
         this.map.keySet().removeIf(predicate);
         this.set3.removeIf(predicate);
      }
   }

   @Override
   public void onEnable() {
      this.map.clear();
      this.set3.clear();
      this.map2.clear();
      this.set.clear();
      this.set2.clear();
      this.list.clear();
      this.blockPos = null;
      this.value235 = -1;
      this.value236 = 0;
      this.executorService = Executors.newSingleThreadExecutor(BlockESP::getThreadByRunnable);
      this.update13();
   }

   private void update16() {
      this.set.clear();
      this.set2.clear();
      this.list.clear();
      this.update12();
   }

   private void update17() {
      this.map2.clear();
      this.blockPos = null;
      this.value235 = -1;
   }

   private void update18() {
      if (!this.set3.isEmpty()) {
         Iterator iterator = this.set3.iterator();

         while (iterator.hasNext()) {
            ChunkPos chunkpos = (ChunkPos)iterator.next();
            if (this.world().getChunkManager().isChunkLoaded(chunkpos.x, chunkpos.z)) {
               iterator.remove();
               this.onIntInt3(chunkpos.x, chunkpos.z);
            }
         }
      }
   }
}
