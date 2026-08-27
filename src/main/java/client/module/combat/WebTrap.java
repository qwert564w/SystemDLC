package client.module.combat;

import client.enums.WebTrapState;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.RotationUtil;
import client.util.SphereItems;
import client.util.StringParts;
import client.util.TargetSelector;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class WebTrap extends Module {
   private HotkeySetting knopka;
   private ListSetting kolichestvo;
   private BooleanSetting rotation;
   private SliderSetting speedNavodki;
   private SliderSetting delay;
   private WebTrapState webTrapState;
   private int value235;
   private int value236;
   private int value237;
   private int value238;
   private BlockPos blockPos;
   private Vec3d vec3d;
   private final RotationUtil rotationUtil;
   private int value239;

   public WebTrap() {
      super("WebTrap", Category.COMBAT);
      HotkeySetting hotkeysetting = new HotkeySetting("", "", -1, this::update12);
      hotkeysetting.setName("Кнопка");
      hotkeysetting.setDescription("нажмите для размещения паутины");
      this.knopka = hotkeysetting;
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(StringParts.join(new String[]{"O", "д", "н", "а"}), StringParts.join(new String[]{"Д", "в", "e"})),
         List.of(StringParts.join(new String[]{"O", "д", "н", "а"})),
         false
      );
      listsetting.setName("Количество");
      listsetting.setDescription("сколько паутин ставить");
      this.kolichestvo = listsetting;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Ротация");
      booleansetting.setDescription("поворачивать камеру к блоку");
      this.rotation = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 5.0, 1.0, 10.0, 0.5);
      slidersetting.setName("Скорость наводки");
      slidersetting.setDescription("скорость поворота камеры");
      this.speedNavodki = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 1.0, 0.0, 5.0, 1.0);
      slidersetting1.setName("Задержка");
      slidersetting1.setDescription("задержка между стадиями (тики)");
      this.delay = slidersetting1;
      this.webTrapState = WebTrapState.IDLE;
      this.value235 = 0;
      this.value236 = -1;
      this.value237 = -1;
      this.value238 = -1;
      this.rotationUtil = new RotationUtil();
      this.value239 = 0;
      this.speedNavodki.setVisibleWhen(this.rotation::isFlag3);
      this.addSettings(new Setting[]{this.knopka, this.kolichestvo, this.rotation, this.speedNavodki, this.delay});
   }

   private int getInt() {
      return SphereItems.getIntByItem5(Items.COBWEB);
   }

   private void update11() {
      this.webTrapState = WebTrapState.IDLE;
      this.value235 = 0;
      this.value236 = -1;
      this.value237 = -1;
      this.value238 = -1;
      this.blockPos = null;
      this.vec3d = null;
      this.rotationUtil.setTime();
      this.value239 = 0;
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   private boolean isPlayerEntity(PlayerEntity playerEntity) {
      return playerEntity.isAlive() && playerEntity != this.player() && !this.isFriend(playerEntity);
   }

   private int getInt2() {
      return SphereItems.getIntByItem2(Items.COBWEB);
   }

   private BlockPos getBlockPos() {
      Vec3d vec3dx = this.player().getPos();
      Box box = new Box(vec3dx.x - 4.0, vec3dx.y - 4.0, vec3dx.z - 4.0, vec3dx.x + 4.0, vec3dx.y + 4.0, vec3dx.z + 4.0);
      List<PlayerEntity> list = this.world().getEntitiesByClass(PlayerEntity.class, box, this::isPlayerEntity);
      PlayerEntity playerentity = list.stream().min(Comparator.comparingDouble(this::getDoubleByPlayerEntity)).orElse(null);
      if (playerentity == null) {
         return null;
      } else {
         Vec3d vec3d1 = playerentity.getPos();
         boolean flag = false;
         boolean flag1 = false;

         for (double d0 = -0.31; d0 <= 0.31; d0 += 0.31) {
            for (double d1 = -0.31; d1 <= 0.31; d1 += 0.31) {
               if (this.world().getBlockState(BlockPos.ofFloored(vec3d1.x + d0, vec3d1.y, vec3d1.z + d1)).getBlock() == Blocks.COBWEB) {
                  flag = true;
               }

               if (this.world().getBlockState(BlockPos.ofFloored(vec3d1.x + d0, vec3d1.y + playerentity.getStandingEyeHeight(), vec3d1.z + d1)).getBlock()
                  == Blocks.COBWEB) {
                  flag1 = true;
               }
            }
         }

         BlockPos blockpos = playerentity.getBlockPos();
         if (!flag && this.world().getBlockState(blockpos).isAir()) {
            return blockpos;
         } else {
            return !flag1 && this.world().getBlockState(blockpos.up()).isAir() ? blockpos.up() : null;
         }
      }
   }

   private boolean check3() {
      return this.getInt2() != -1 || this.getInt() != -1;
   }

   private double getDoubleByPlayerEntity(PlayerEntity playerEntity) {
      return playerEntity.squaredDistanceTo(this.player());
   }

   private void update12() {
      if (this.webTrapState == WebTrapState.IDLE) {
         if (this.inGame()) {
            this.blockPos = this.getBlockPos();
            if (this.blockPos != null) {
               if (this.check3()) {
                  this.value239 = 0;
                  this.vec3d = Vec3d.ofCenter(this.blockPos).add(0.0, -0.5, 0.0);
                  this.value236 = this.player().getInventory().selectedSlot;
                  this.setWebTrapState(WebTrapState.SWAP_SLOT);
               }
            }
         }
      }
   }

   private boolean isDoubleVec3d(double value, Vec3d vec3d) {
      return TargetSelector.isDoubleVec3d(value, vec3d);
   }

   private void setWebTrapState(WebTrapState webTrapState2) {
      this.webTrapState = webTrapState2;
      this.value235 = this.delay.getInt2();
   }

   @Override
   public void update7() {
      if (this.webTrapState != WebTrapState.IDLE && this.vec3d != null && this.rotation.isFlag3()) {
         if (!this.notInGame()) {
            RotationUtil rotationutil = this.rotationUtil;
            Vec3d vec3d1 = this.vec3d;
            double d0 = this.speedNavodki.getValue();
            Vec3d vec3dx = vec3d1;
            rotationutil.onDoubleVec3d(d0, vec3dx);
         }
      }
   }

   @Override
   public void onEnable() {
      this.update11();
   }

   @Override
   public void update8() {
      if (!this.notInGame() && this.webTrapState != WebTrapState.IDLE) {
         if (this.value235 > 0) {
            this.value235--;
         } else {
            switch (this.webTrapState) {
               case SWAP_SLOT:
                  int i = this.getInt2();
                  if (i != -1) {
                     this.value237 = i;
                     this.inventory().selectedSlot = i;
                  } else {
                     this.value238 = this.getInt();
                     if (this.value238 == -1) {
                        this.update11();
                        return;
                     }

                     this.value237 = (this.value236 + 1) % 9;
                     if (this.value237 == this.value236) {
                        this.value237 = (this.value236 + 2) % 9;
                     }

                     this.interactionManager()
                        .clickSlot(this.player().currentScreenHandler.syncId, this.value238, this.value237, SlotActionType.SWAP, this.player());
                     this.inventory().selectedSlot = this.value237;
                  }

                  this.setWebTrapState(WebTrapState.PLACE);
                  break;
               case PLACE:
                  if (this.blockPos == null || !this.world().getBlockState(this.blockPos).isAir()) {
                     this.update11();
                     return;
                  }

                  if (this.rotation.isFlag3()) {
                     double d0 = 15.0;
                     Vec3d vec3dx = this.vec3d;
                     if (!this.isDoubleVec3d(d0, vec3dx)) {
                        this.value235 = 1;
                        return;
                     }
                  }

                  BlockHitResult blockhitresult = new BlockHitResult(Vec3d.ofBottomCenter(this.blockPos), Direction.UP, this.blockPos.down(), false);
                  this.interactionManager().interactBlock(this.clientPlayer(), this.mainHand(), blockhitresult);
                  this.player().swingHand(this.mainHand());
                  this.setWebTrapState(WebTrapState.SWAP_BACK);
                  break;
               case SWAP_BACK:
                  if (this.value238 != -1) {
                     this.interactionManager()
                        .clickSlot(this.player().currentScreenHandler.syncId, this.value238, this.value237, SlotActionType.SWAP, this.player());
                  }

                  this.inventory().selectedSlot = this.value236;
                  this.value239++;
                  boolean flag = "Двe".equals(this.kolichestvo.getString2());
                  if (flag && this.value239 < 2 && this.check3()) {
                     BlockPos blockpos = this.getBlockPos();
                     if (blockpos != null) {
                        this.blockPos = blockpos;
                        this.vec3d = Vec3d.ofCenter(this.blockPos).add(0.0, -0.5, 0.0);
                        this.value236 = this.inventory().selectedSlot;
                        this.value237 = -1;
                        this.value238 = -1;
                        this.setWebTrapState(WebTrapState.SWAP_SLOT);
                        return;
                     }
                  }

                  this.update11();
            }
         }
      }
   }
}
