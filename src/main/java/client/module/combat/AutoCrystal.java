package client.module.combat;

import client.enums.AutoCrystalState;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.BlockChecks;
import client.util.PlaceCooldown;
import client.util.RotationUtil;
import client.util.SphereItems;
import client.util.StringParts;
import client.util.TargetSelector;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AutoCrystal extends Module {
   private ListSetting typeZaderzhki;
   private SliderSetting deleyStavki;
   private SliderSetting deleyStavkiMs;
   private SliderSetting maxOzhidanieKristalla;
   private SliderSetting speedNavodki;
   private BooleanSetting autoOnStavke;
   private BooleanSetting svapatObratno;
   private BooleanSetting rotation;
   private HotkeySetting keyObsidiana;
   private final PlaceCooldown placeCooldown;
   private AutoCrystalState autoCrystalState;
   private int value235;
   private int value236;
   private int value237;
   private BlockPos blockPos;
   private int value238;
   private int value239;
   private boolean flag;
   private Vec3d vec3d;
   private final RotationUtil rotationUtil;
   private Vec3d vec3d2;
   private BlockPos blockPos2;
   private Direction direction;

   public AutoCrystal() {
      super("AutoCrystal", Category.COMBAT);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         List.of(StringParts.join(new String[]{"T", "и", "к", "о", "в", "а", "я"}), StringParts.join(new String[]{"O", "б", "ы", "ч", "н", "а", "я"})),
         List.of(StringParts.join(new String[]{"T", "и", "к", "о", "в", "а", "я"})),
         false
      );
      listsetting.setName("Тип задержки");
      listsetting.setDescription("Тип задержки между действиями");
      this.typeZaderzhki = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1.0, 0.0, 10.0, 1.0);
      slidersetting.setName("Делей ставки");
      slidersetting.setDescription("Задержка после постановки обсидиана");
      this.deleyStavki = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 1.0, 0.0, 50.0, 1.0);
      slidersetting1.setName("Делей ставки (мс)");
      slidersetting1.setDescription("Задержка после постановки обсидиана (мс)");
      this.deleyStavkiMs = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 5.0, 3.0, 30.0, 1.0);
      slidersetting2.setName("Макс. ожидание кристалла");
      slidersetting2.setDescription("Сколько тиков ждать появления кристалла");
      this.maxOzhidanieKristalla = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 5.0, 1.0, 10.0, 0.5);
      slidersetting3.setName("Скорость наводки");
      slidersetting3.setDescription("Скорость поворота камеры");
      this.speedNavodki = slidersetting3;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Авто при ставке");
      booleansetting.setDescription("Автоматически ставить и взрывать кристалл при ручной ставке обсидиана");
      this.autoOnStavke = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Свапать обратно");
      booleansetting1.setDescription("Вернуть предмет в руку после взрыва");
      this.svapatObratno = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("Ротация");
      booleansetting2.setDescription("Поворачивать камеру к блоку/кристаллу");
      this.rotation = booleansetting2;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", -1, this::update11);
      hotkeysetting.setName("Клавиша обсидиана");
      hotkeysetting.setDescription("Кнопка для начала цепочки");
      this.keyObsidiana = hotkeysetting;
      this.placeCooldown = new PlaceCooldown();
      this.autoCrystalState = AutoCrystalState.IDLE;
      this.value235 = -1;
      this.value236 = -1;
      this.value237 = -1;
      this.blockPos = null;
      this.value238 = 0;
      this.value239 = 0;
      this.flag = false;
      this.vec3d = null;
      this.rotationUtil = new RotationUtil();
      this.vec3d2 = null;
      this.blockPos2 = null;
      this.direction = null;
      this.deleyStavki.setVisibleWhen(this::check3);
      this.deleyStavkiMs.setVisibleWhen(this::getBoolean);
      this.speedNavodki.setVisibleWhen(this.rotation::isFlag3);
      this.addSettings(
         new Setting[]{
            this.autoOnStavke,
            this.typeZaderzhki,
            this.deleyStavki,
            this.deleyStavkiMs,
            this.maxOzhidanieKristalla,
            this.speedNavodki,
            this.svapatObratno,
            this.rotation,
            this.keyObsidiana
         }
      );
   }

   private void update11() {
      if (this.autoCrystalState == AutoCrystalState.IDLE
         && !this.notInGame()
         && this.client().crosshairTarget instanceof BlockHitResult blockhitresult
         && blockhitresult.getType() == Type.BLOCK) {
         BlockPos blockpos1 = blockhitresult.getBlockPos();
         if (!this.world().getBlockState(blockpos1).isAir() && !this.world().getBlockState(blockpos1).isLiquid()) {
            if (!(this.player().getEyePos().distanceTo(Vec3d.ofCenter(blockpos1)) > 4.5)) {
               this.value237 = this.getIntByItem(Items.END_CRYSTAL);
               if (this.value237 != -1) {
                  Direction directionx = blockhitresult.getSide();
                  BlockPos blockpos = blockpos1.offset(directionx);
                  if (this.isBlockPos(blockpos)) {
                     this.value235 = this.inventory().selectedSlot;
                     this.blockPos = blockpos;
                     this.vec3d2 = blockhitresult.getPos();
                     this.blockPos2 = blockhitresult.getBlockPos();
                     this.direction = directionx;
                     this.value238 = 0;
                     this.flag = false;
                     this.vec3d = this.vec3d2;
                     boolean flagx = this.player().getMainHandStack().getItem() == Items.OBSIDIAN;
                     if (flagx) {
                        this.autoCrystalState = AutoCrystalState.PLACE_OBSIDIAN;
                     } else {
                        this.value236 = this.getIntByItem(Items.OBSIDIAN);
                        if (this.value236 == -1) {
                           this.update15();
                           return;
                        }

                        this.autoCrystalState = AutoCrystalState.SWAP_TO_OBSIDIAN;
                     }
                  }
               }
            }
         }
      }
   }

   private void update12() {
      if (this.blockPos == null) {
         this.update15();
      } else {
         EndCrystalEntity endcrystalentity = this.getEndCrystalEntityByBlockPos(this.blockPos);
         if (endcrystalentity == null) {
            this.update15();
         } else {
            this.vec3d = endcrystalentity.getPos();
            if (this.rotation.isFlag3()) {
               Vec3d vec3d1 = endcrystalentity.getPos();
               double d0 = 15.0;
               Vec3d vec3dx = vec3d1;
               if (!this.isDoubleVec3d(d0, vec3dx)) {
                  byte b0 = 1;
                  AutoCrystalState autocrystalstate = AutoCrystalState.ATTACK_CRYSTAL;
                  this.onIntAutoCrystalState(b0, autocrystalstate);
                  return;
               }
            }

            if (this.interactionManager().isBreakingBlock()) {
               byte b1 = 1;
               AutoCrystalState autocrystalstate1 = AutoCrystalState.ATTACK_CRYSTAL;
               this.onIntAutoCrystalState(b1, autocrystalstate1);
            } else {
               this.interactionManager().attackEntity(this.player(), endcrystalentity);
               this.clientPlayer().swingHand(this.mainHand());
               if (this.svapatObratno.isFlag3()) {
                  byte b2 = 1;
                  AutoCrystalState autocrystalstate2 = AutoCrystalState.SWAP_BACK;
                  this.onIntAutoCrystalState(b2, autocrystalstate2);
               } else {
                  byte b3 = 1;
                  AutoCrystalState autocrystalstate3 = AutoCrystalState.DONE;
                  this.onIntAutoCrystalState(b3, autocrystalstate3);
               }
            }
         }
      }
   }

   private void update13() {
      if (this.value236 < 9) {
         this.inventory().selectedSlot = this.value236;
      } else {
         int i = this.player().currentScreenHandler.syncId;
         this.interactionManager().clickSlot(i, this.value236, this.value235, SlotActionType.SWAP, this.player());
      }

      byte b0 = 1;
      AutoCrystalState autocrystalstate = AutoCrystalState.PLACE_OBSIDIAN;
      this.onIntAutoCrystalState(b0, autocrystalstate);
   }

   @Override
   public void onDisable() {
      this.update15();
   }

   private void update14() {
      if (this.vec3d2 != null && this.blockPos2 != null && this.direction != null && this.blockPos != null) {
         if (this.player().getMainHandStack().getItem() != Items.OBSIDIAN) {
            this.update15();
         } else if (this.world().getBlockState(this.blockPos2).isAir() || this.world().getBlockState(this.blockPos2).isLiquid()) {
            this.update15();
         } else if (!this.isBlockPos(this.blockPos)) {
            this.update15();
         } else if (this.player().getEyePos().distanceTo(Vec3d.ofCenter(this.blockPos2)) > 4.5) {
            this.update15();
         } else {
            this.vec3d = this.vec3d2;
            BlockHitResult blockhitresult = new BlockHitResult(this.vec3d2, this.direction, this.blockPos2, false);
            this.interactionManager().interactBlock(this.clientPlayer(), this.mainHand(), blockhitresult);
            this.clientPlayer().swingHand(this.mainHand());
            this.vec3d2 = null;
            this.blockPos2 = null;
            this.direction = null;
            this.value239 = 0;
            SliderSetting slidersetting1 = this.deleyStavkiMs;
            SliderSetting slidersetting = this.deleyStavki;
            this.onSliderSettingSliderSetting(slidersetting1, slidersetting);
         }
      } else {
         this.update15();
      }
   }

   private boolean isDoubleVec3d(double value, Vec3d vec3d) {
      return TargetSelector.isDoubleVec3d(value, vec3d);
   }

   private boolean check3() {
      return "Tиковая".equals(this.typeZaderzhki.getString2());
   }

   private Boolean getBoolean() {
      return !this.check3();
   }

   private boolean isBlockPos(BlockPos blockPos) {
      return BlockChecks.isBlockPos(blockPos);
   }

   private BlockPos getBlockPosByBlockPos(BlockPos blockPos) {
      if (this.world().getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) {
         return blockPos;
      } else {
         return this.world().getBlockState(blockPos.down()).getBlock() == Blocks.OBSIDIAN ? blockPos.down() : null;
      }
   }

   private EndCrystalEntity getEndCrystalEntityByBlockPos(BlockPos blockPos) {
      for (Entity entity : this.clientWorld().getEntities()) {
         if (entity instanceof EndCrystalEntity endcrystalentity && !endcrystalentity.isRemoved() && endcrystalentity.isAlive()) {
            BlockPos blockpos = endcrystalentity.getBlockPos();
            if (blockpos.equals(blockPos.up()) || blockpos.down().equals(blockPos)) {
               return endcrystalentity;
            }
         }
      }

      return null;
   }

   private int getIntByItem(Item item2) {
      return SphereItems.getIntByItem(item2);
   }

   @Override
   public ActionResult getActionResultByPlayerEntityWorldHandBlockHitResult(PlayerEntity playerEntity, World world2, Hand hand, BlockHitResult blockHitResult) {
      if (!this.autoOnStavke.isFlag3()) {
         return ActionResult.PASS;
      } else if (!this.notInGame() && this.autoCrystalState == AutoCrystalState.IDLE) {
         if (playerEntity.getMainHandStack().getItem() != Items.OBSIDIAN) {
            return ActionResult.PASS;
         } else {
            BlockPos blockpos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            if (!this.isBlockPos(blockpos)) {
               return ActionResult.PASS;
            } else {
               this.value237 = this.getIntByItem(Items.END_CRYSTAL);
               if (this.value237 == -1) {
                  return ActionResult.PASS;
               } else {
                  this.value235 = this.inventory().selectedSlot;
                  this.value236 = -1;
                  this.blockPos = blockpos;
                  this.vec3d2 = null;
                  this.blockPos2 = null;
                  this.direction = null;
                  this.value238 = 0;
                  this.flag = false;
                  this.vec3d = blockpos.toCenterPos();
                  this.value239 = 0;
                  SliderSetting slidersetting1 = this.deleyStavkiMs;
                  SliderSetting slidersetting = this.deleyStavki;
                  this.onSliderSettingSliderSetting(slidersetting1, slidersetting);
                  return ActionResult.PASS;
               }
            }
         }
      } else {
         return ActionResult.PASS;
      }
   }

   private void update15() {
      this.autoCrystalState = AutoCrystalState.IDLE;
      this.placeCooldown.update();
      this.value235 = -1;
      this.value236 = -1;
      this.value237 = -1;
      this.blockPos = null;
      this.value238 = 0;
      this.value239 = 0;
      this.flag = false;
      this.vec3d = null;
      this.rotationUtil.setTime();
      this.vec3d2 = null;
      this.blockPos2 = null;
      this.direction = null;
   }

   private void onIntAutoCrystalState(int count, AutoCrystalState autoCrystalState2) {
      this.autoCrystalState = autoCrystalState2;
      if (count <= 0) {
         this.flag = false;
         this.placeCooldown.update();
      } else {
         this.flag = true;
         if (this.check3()) {
            this.placeCooldown.setFlag(false);
            this.placeCooldown.setInt(count);
         } else {
            this.placeCooldown.setFlag(true);
            this.placeCooldown.setLong(count * 50L);
         }
      }
   }

   private void onSliderSettingSliderSetting(SliderSetting sliderSetting, SliderSetting sliderSetting2) {
      this.autoCrystalState = AutoCrystalState.VERIFY_OBSIDIAN;
      if (this.check3()) {
         int i = sliderSetting2.getInt2();
         if (i <= 0) {
            this.flag = false;
            this.placeCooldown.update();
         } else {
            this.flag = true;
            this.placeCooldown.setFlag(false);
            this.placeCooldown.setInt(i);
         }
      } else {
         int j = sliderSetting.getInt2();
         if (j <= 0) {
            this.flag = false;
            this.placeCooldown.update();
         } else {
            this.flag = true;
            this.placeCooldown.setFlag(true);
            this.placeCooldown.setLong(j);
         }
      }
   }

   @Override
   public void update7() {
      if (this.inGame() && this.autoCrystalState != AutoCrystalState.IDLE && this.vec3d != null && this.rotation.isFlag3()) {
         RotationUtil rotationutil = this.rotationUtil;
         Vec3d vec3d1 = this.vec3d;
         double d0 = this.speedNavodki.getValue();
         Vec3d vec3dx = vec3d1;
         rotationutil.onDoubleVec3d(d0, vec3dx);
      }
   }

   private void update16() {
      if (this.blockPos == null) {
         this.update15();
      } else if (this.world().getBlockState(this.blockPos).getBlock() == Blocks.OBSIDIAN) {
         byte b0 = 0;
         AutoCrystalState autocrystalstate = AutoCrystalState.SWAP_TO_CRYSTAL;
         this.onIntAutoCrystalState(b0, autocrystalstate);
      } else {
         this.value239++;
         if (this.value239 >= 5) {
            this.update15();
         } else {
            byte b1 = 1;
            AutoCrystalState autocrystalstate1 = AutoCrystalState.VERIFY_OBSIDIAN;
            this.onIntAutoCrystalState(b1, autocrystalstate1);
         }
      }
   }

   @Override
   public void onEnable() {
      this.update15();
   }

   private void update17() {
      if (this.blockPos == null) {
         this.update15();
      } else {
         BlockPos blockpos = this.getBlockPosByBlockPos(this.blockPos);
         if (blockpos == null) {
            this.update15();
         } else if (this.player().getMainHandStack().getItem() != Items.END_CRYSTAL) {
            this.update15();
         } else {
            BlockPos blockpos1 = blockpos.up();
            if (this.world().getBlockState(blockpos1).isReplaceable() && this.world().getBlockState(blockpos1.up()).isReplaceable()) {
               Vec3d vec3dx = blockpos.toCenterPos().add(0.0, 0.5, 0.0);
               this.vec3d = vec3dx;
               if (this.rotation.isFlag3()) {
                  double d0 = 15.0;
                  if (!this.isDoubleVec3d(d0, vec3dx)) {
                     byte b0 = 1;
                     AutoCrystalState autocrystalstate = AutoCrystalState.PLACE_CRYSTAL;
                     this.onIntAutoCrystalState(b0, autocrystalstate);
                     return;
                  }
               }

               BlockHitResult blockhitresult = new BlockHitResult(vec3dx, Direction.UP, blockpos, false);
               this.interactionManager().interactBlock(this.clientPlayer(), this.mainHand(), blockhitresult);
               this.clientPlayer().swingHand(this.mainHand());
               this.value238 = 0;
               byte b1 = 1;
               AutoCrystalState autocrystalstate1 = AutoCrystalState.WAIT_CRYSTAL;
               this.onIntAutoCrystalState(b1, autocrystalstate1);
            } else {
               this.update15();
            }
         }
      }
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         while (this.autoCrystalState != AutoCrystalState.IDLE) {
            if (this.flag && !this.placeCooldown.check()) {
               this.placeCooldown.update2();
               return;
            }

            AutoCrystalState autocrystalstate = this.autoCrystalState;
            switch (this.autoCrystalState) {
               case SWAP_TO_OBSIDIAN:
                  this.update13();
                  break;
               case PLACE_OBSIDIAN:
                  this.update14();
                  break;
               case VERIFY_OBSIDIAN:
                  this.update16();
                  break;
               case SWAP_TO_CRYSTAL:
                  this.update19();
                  break;
               case PLACE_CRYSTAL:
                  this.update17();
                  break;
               case WAIT_CRYSTAL:
                  this.update18();
                  break;
               case ATTACK_CRYSTAL:
                  this.update12();
                  break;
               case SWAP_BACK:
                  this.update20();
                  break;
               case DONE:
                  this.update15();
            }

            if (this.autoCrystalState == AutoCrystalState.IDLE || this.flag && !this.placeCooldown.check() || this.autoCrystalState == autocrystalstate) {
               break;
            }
         }
      }
   }

   private void update18() {
      if (this.blockPos == null) {
         this.update15();
      } else {
         EndCrystalEntity endcrystalentity = this.getEndCrystalEntityByBlockPos(this.blockPos);
         if (endcrystalentity != null) {
            this.vec3d = endcrystalentity.getPos();
            byte b0 = 0;
            AutoCrystalState autocrystalstate = AutoCrystalState.ATTACK_CRYSTAL;
            this.onIntAutoCrystalState(b0, autocrystalstate);
         } else {
            this.value238++;
            if (this.value238 >= this.maxOzhidanieKristalla.getInt2()) {
               this.update15();
            } else {
               byte b1 = 1;
               AutoCrystalState autocrystalstate1 = AutoCrystalState.WAIT_CRYSTAL;
               this.onIntAutoCrystalState(b1, autocrystalstate1);
            }
         }
      }
   }

   private void update19() {
      this.value237 = this.getIntByItem(Items.END_CRYSTAL);
      if (this.value237 == -1) {
         this.update15();
      } else {
         if (this.value237 < 9) {
            this.inventory().selectedSlot = this.value237;
         } else {
            int i = this.inventory().selectedSlot;
            int j = this.player().currentScreenHandler.syncId;
            this.interactionManager().clickSlot(j, this.value237, i, SlotActionType.SWAP, this.player());
         }

         byte b0 = 1;
         AutoCrystalState autocrystalstate = AutoCrystalState.PLACE_CRYSTAL;
         this.onIntAutoCrystalState(b0, autocrystalstate);
      }
   }

   private void update20() {
      if (this.value236 != -1 && this.value236 >= 9) {
         int i = this.inventory().selectedSlot;
         int j = this.player().currentScreenHandler.syncId;
         this.interactionManager().clickSlot(j, this.value236, i, SlotActionType.SWAP, this.player());
      }

      this.inventory().selectedSlot = this.value235;
      this.vec3d = null;
      byte b0 = 0;
      AutoCrystalState autocrystalstate = AutoCrystalState.DONE;
      this.onIntAutoCrystalState(b0, autocrystalstate);
   }
}
