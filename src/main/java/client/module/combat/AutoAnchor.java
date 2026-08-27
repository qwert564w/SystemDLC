package client.module.combat;

import client.enums.AutoAnchorState;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.BlockChecks;
import client.util.KeyBindings;
import client.util.PlaceCooldown;
import client.util.RotationUtil;
import client.util.SphereItems;
import client.util.StringParts;
import client.util.TargetSelector;
import java.util.List;
import net.minecraft.block.Blocks;
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

public class AutoAnchor extends Module {
   private ListSetting typeZaderzhki;
   private SliderSetting deleyStavki;
   private SliderSetting deleyStavkiMs;
   private SliderSetting speedNavodki;
   private BooleanSetting autoOnStavke;
   private BooleanSetting svapatObratno;
   private BooleanSetting rotation;
   private HotkeySetting keyYakorya;
   private final PlaceCooldown placeCooldown;
   private AutoAnchorState autoAnchorState;
   private int value235;
   private int value236;
   private int value237;
   private BlockPos blockPos;
   private int value238;
   private boolean flag;
   private Vec3d vec3d;
   private final RotationUtil rotationUtil;
   private Vec3d vec3d2;
   private BlockPos blockPos2;
   private Direction direction;

   public AutoAnchor() {
      super("AutoAnchor", Category.COMBAT);
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
      slidersetting.setDescription("Задержка после постановки якоря");
      this.deleyStavki = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 1.0, 0.0, 50.0, 1.0);
      slidersetting1.setName("Делей ставки (мс)");
      slidersetting1.setDescription("Задержка после постановки якоря (мс)");
      this.deleyStavkiMs = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 5.0, 1.0, 10.0, 0.5);
      slidersetting2.setName("Скорость наводки");
      slidersetting2.setDescription("Скорость поворота камеры");
      this.speedNavodki = slidersetting2;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Авто при ставке");
      booleansetting.setDescription("Автоматически заряжать и взрывать при ручной ставке якоря");
      this.autoOnStavke = booleansetting;
      BooleanSetting booleansetting1 = new BooleanSetting("", "", true);
      booleansetting1.setName("Свапать обратно");
      booleansetting1.setDescription("Вернуть предмет в руку после взрыва");
      this.svapatObratno = booleansetting1;
      BooleanSetting booleansetting2 = new BooleanSetting("", "", true);
      booleansetting2.setName("Ротация");
      booleansetting2.setDescription("Поворачивать камеру к якорю");
      this.rotation = booleansetting2;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", -1, this::update11);
      hotkeysetting.setName("Клавиша якоря");
      hotkeysetting.setDescription("Кнопка для начала цепочки");
      this.keyYakorya = hotkeysetting;
      this.placeCooldown = new PlaceCooldown();
      this.autoAnchorState = AutoAnchorState.IDLE;
      this.value235 = -1;
      this.value236 = -1;
      this.value237 = -1;
      this.blockPos = null;
      this.value238 = 0;
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
            this.autoOnStavke, this.typeZaderzhki, this.deleyStavki, this.deleyStavkiMs, this.speedNavodki, this.svapatObratno, this.rotation, this.keyYakorya
         }
      );
   }

   private void update11() {
      if (this.autoAnchorState == AutoAnchorState.IDLE
         && !this.notInGame()
         && this.client().crosshairTarget instanceof BlockHitResult blockhitresult
         && blockhitresult.getType() == Type.BLOCK) {
         BlockPos blockpos1 = blockhitresult.getBlockPos();
         if (!this.world().getBlockState(blockpos1).isAir() && !this.world().getBlockState(blockpos1).isLiquid()) {
            if (!(this.player().getEyePos().distanceTo(Vec3d.ofCenter(blockpos1)) > 4.5)) {
               this.value237 = this.getIntByItem(Items.GLOWSTONE);
               if (this.value237 != -1) {
                  Direction directionx = blockhitresult.getSide();
                  BlockPos blockpos = blockpos1.offset(directionx);
                  if (this.isBlockPos2(blockpos)) {
                     this.value235 = this.inventory().selectedSlot;
                     this.blockPos = blockpos;
                     this.vec3d2 = blockhitresult.getPos();
                     this.blockPos2 = blockhitresult.getBlockPos();
                     this.direction = directionx;
                     this.value238 = 0;
                     this.flag = false;
                     this.vec3d = this.vec3d2;
                     boolean flagx = this.player().getMainHandStack().getItem() == Items.RESPAWN_ANCHOR;
                     if (flagx) {
                        this.autoAnchorState = AutoAnchorState.PLACE_ANCHOR;
                     } else {
                        this.value236 = this.getIntByItem(Items.RESPAWN_ANCHOR);
                        if (this.value236 == -1) {
                           this.update15();
                           return;
                        }

                        this.autoAnchorState = AutoAnchorState.SWAP_TO_ANCHOR;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean isBlockPos(BlockPos blockPos) {
      return BlockChecks.isBlockPos2(blockPos);
   }

   private void update12() {
      if (this.blockPos == null) {
         this.update15();
      } else if (this.world().getBlockState(this.blockPos).getBlock() != Blocks.RESPAWN_ANCHOR) {
         this.update15();
      } else if (!this.isBlockPos(this.blockPos)) {
         this.update15();
      } else {
         Vec3d vec3dx = this.blockPos.toCenterPos();
         this.vec3d = vec3dx;
         if (this.rotation.isFlag3()) {
            double d0 = 15.0;
            if (!this.isDoubleVec3d(d0, vec3dx)) {
               this.onAutoAnchorStateInt(AutoAnchorState.EXPLODE_ANCHOR, 1);
               return;
            }
         }

         KeyBindings.update3();
         if (this.svapatObratno.isFlag3()) {
            this.onAutoAnchorStateInt(AutoAnchorState.SWAP_BACK, 1);
         } else {
            this.onAutoAnchorStateInt(AutoAnchorState.DONE, 1);
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

      this.onAutoAnchorStateInt(AutoAnchorState.PLACE_ANCHOR, 1);
   }

   @Override
   public void onDisable() {
      this.update15();
   }

   private void update14() {
      if (this.vec3d2 != null && this.blockPos2 != null && this.direction != null && this.blockPos != null) {
         if (this.player().getMainHandStack().getItem() != Items.RESPAWN_ANCHOR) {
            this.update15();
         } else if (this.world().getBlockState(this.blockPos2).isAir() || this.world().getBlockState(this.blockPos2).isLiquid()) {
            this.update15();
         } else if (!this.isBlockPos2(this.blockPos)) {
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
            this.value238 = 0;
            this.onSliderSettingSliderSetting(this.deleyStavki, this.deleyStavkiMs);
         }
      } else {
         this.update15();
      }
   }

   private Boolean getBoolean() {
      return !this.check3();
   }

   private boolean isDoubleVec3d(double value, Vec3d vec3d) {
      return TargetSelector.isDoubleVec3d(value, vec3d);
   }

   private boolean isBlockPos2(BlockPos blockPos) {
      return BlockChecks.isBlockPos(blockPos);
   }

   private boolean check3() {
      return "Tиковая".equals(this.typeZaderzhki.getString2());
   }

   private int getInt() {
      int i = this.inventory().selectedSlot;
      if (this.inventory().getStack(i).getItem() != Items.GLOWSTONE) {
         return i;
      } else {
         for (int j = 0; j < 9; j++) {
            if (this.inventory().getStack(j).getItem() != Items.GLOWSTONE) {
               return j;
            }
         }

         return -1;
      }
   }

   private int getIntByItem(Item item2) {
      return SphereItems.getIntByItem(item2);
   }

   private void update15() {
      this.autoAnchorState = AutoAnchorState.IDLE;
      this.placeCooldown.update();
      this.value235 = -1;
      this.value236 = -1;
      this.value237 = -1;
      this.blockPos = null;
      this.value238 = 0;
      this.flag = false;
      this.vec3d = null;
      this.rotationUtil.setTime();
      this.vec3d2 = null;
      this.blockPos2 = null;
      this.direction = null;
   }

   @Override
   public ActionResult getActionResultByPlayerEntityWorldHandBlockHitResult(PlayerEntity playerEntity, World world2, Hand hand, BlockHitResult blockHitResult) {
      if (!this.autoOnStavke.isFlag3()) {
         return ActionResult.PASS;
      } else if (!this.notInGame() && this.autoAnchorState == AutoAnchorState.IDLE) {
         if (playerEntity.getMainHandStack().getItem() != Items.RESPAWN_ANCHOR) {
            return ActionResult.PASS;
         } else {
            BlockPos blockpos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            if (!this.isBlockPos2(blockpos)) {
               return ActionResult.PASS;
            } else {
               this.value237 = this.getIntByItem(Items.GLOWSTONE);
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
                  this.onSliderSettingSliderSetting(this.deleyStavki, this.deleyStavkiMs);
                  return ActionResult.PASS;
               }
            }
         }
      } else {
         return ActionResult.PASS;
      }
   }

   private void onAutoAnchorStateInt(AutoAnchorState autoAnchorState2, int count) {
      this.autoAnchorState = autoAnchorState2;
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
      this.autoAnchorState = AutoAnchorState.VERIFY_ANCHOR;
      if (this.check3()) {
         int i = sliderSetting.getInt2();
         if (i <= 0) {
            this.flag = false;
            this.placeCooldown.update();
         } else {
            this.flag = true;
            this.placeCooldown.setFlag(false);
            this.placeCooldown.setInt(i);
         }
      } else {
         int j = sliderSetting2.getInt2();
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
      if (this.inGame() && this.autoAnchorState != AutoAnchorState.IDLE && this.vec3d != null) {
         if (this.rotation.isFlag3()) {
            RotationUtil rotationutil = this.rotationUtil;
            Vec3d vec3d1 = this.vec3d;
            double d0 = this.speedNavodki.getValue();
            Vec3d vec3dx = vec3d1;
            rotationutil.onDoubleVec3d(d0, vec3dx);
         }
      }
   }

   private void update16() {
      if (this.blockPos == null) {
         this.update15();
      } else if (this.world().getBlockState(this.blockPos).getBlock() == Blocks.RESPAWN_ANCHOR) {
         this.onAutoAnchorStateInt(AutoAnchorState.SWAP_TO_GLOWSTONE, 0);
      } else {
         this.value238++;
         if (this.value238 >= 5) {
            this.update15();
         } else {
            this.onAutoAnchorStateInt(AutoAnchorState.VERIFY_ANCHOR, 1);
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
      } else if (this.world().getBlockState(this.blockPos).getBlock() != Blocks.RESPAWN_ANCHOR) {
         this.update15();
      } else {
         Vec3d vec3dx = this.blockPos.toCenterPos();
         this.vec3d = vec3dx;
         if (this.rotation.isFlag3()) {
            double d0 = 15.0;
            if (!this.isDoubleVec3d(d0, vec3dx)) {
               this.onAutoAnchorStateInt(AutoAnchorState.CHARGE_ANCHOR, 1);
               return;
            }
         }

         KeyBindings.update3();
         this.onAutoAnchorStateInt(AutoAnchorState.SWAP_BACK_FROM_GLOW, 1);
      }
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         while (this.autoAnchorState != AutoAnchorState.IDLE) {
            if (this.flag && !this.placeCooldown.check()) {
               this.placeCooldown.update2();
               return;
            }

            AutoAnchorState autoanchorstate = this.autoAnchorState;
            switch (this.autoAnchorState) {
               case SWAP_TO_ANCHOR:
                  this.update13();
                  break;
               case PLACE_ANCHOR:
                  this.update14();
                  break;
               case VERIFY_ANCHOR:
                  this.update16();
                  break;
               case SWAP_TO_GLOWSTONE:
                  this.update19();
                  break;
               case CHARGE_ANCHOR:
                  this.update17();
                  break;
               case SWAP_BACK_FROM_GLOW:
                  this.update18();
                  break;
               case EXPLODE_ANCHOR:
                  this.update12();
                  break;
               case SWAP_BACK:
                  this.update20();
                  break;
               case DONE:
                  this.update15();
            }

            if (this.autoAnchorState == AutoAnchorState.IDLE || this.flag && !this.placeCooldown.check() || this.autoAnchorState == autoanchorstate) {
               break;
            }
         }
      }
   }

   private void update18() {
      int i = this.getInt();
      if (i != -1) {
         this.inventory().selectedSlot = i;
      } else {
         this.inventory().selectedSlot = this.value235;
      }

      this.onAutoAnchorStateInt(AutoAnchorState.EXPLODE_ANCHOR, 1);
   }

   private void update19() {
      this.value237 = this.getIntByItem(Items.GLOWSTONE);
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

         this.onAutoAnchorStateInt(AutoAnchorState.CHARGE_ANCHOR, 1);
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
      this.onAutoAnchorStateInt(AutoAnchorState.DONE, 0);
   }
}
