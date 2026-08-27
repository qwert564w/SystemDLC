package client.module.combat;

import client.enums.AutoStartState;
import client.module.Category;
import client.module.Module;
import client.setting.BooleanSetting;
import client.setting.HotkeySetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.RandomUtil;
import client.util.RotationUtil;
import client.util.StringParts;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class AutoStart extends Module {
   private ListSetting mode;
   private HotkeySetting bindAktivacii;
   private SliderSetting tikiZaryadkiLuka;
   private SliderSetting deleyUstanovki;
   private SliderSetting speedNavodki;
   private BooleanSetting autoVykl;
   private AutoStartState autoStartState;
   private int value235;
   private BlockPos blockPos;
   private Direction direction;
   private Vec3d vec3d;
   private float value236;
   private float value237;
   private int value238;
   private int value239;
   private int value240;
   private int value241;
   private int value242;
   private int value243;
   private final RotationUtil rotationUtil;

   public AutoStart() {
      super("AutoStart", Category.COMBAT);
      ListSetting listsetting = new ListSetting(
         "",
         "",
         Arrays.asList(
            StringParts.join(new String[]{"С", "н", "а", "ч", "а", "л", "а", " ", "с", "т", "а", "в", "и", "т", "ь"}),
            StringParts.join(new String[]{"С", "н", "а", "ч", "а", "л", "а", " ", "с", "т", "р", "е", "л", "я", "т", "ь"})
         ),
         Collections.singletonList(StringParts.join(new String[]{"С", "н", "а", "ч", "а", "л", "а", " ", "с", "т", "а", "в", "и", "т", "ь"})),
         false
      );
      listsetting.setName("Режим");
      listsetting.setDescription("Порядок действий: сначала ставить ТНТ или сначала стрелять");
      this.mode = listsetting;
      HotkeySetting hotkeysetting = new HotkeySetting("", "", 86, this::update21);
      hotkeysetting.setName("Бинд активации");
      hotkeysetting.setDescription("Кнопка для активации AutoCart");
      this.bindAktivacii = hotkeysetting;
      SliderSetting slidersetting = new SliderSetting("", "", 7.0, 5.0, 20.0, 1.0);
      slidersetting.setName("Тики зарядки лука");
      slidersetting.setDescription("Сколько тиков заряжать лук");
      this.tikiZaryadkiLuka = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 2.0, 1.0, 10.0, 1.0);
      slidersetting1.setName("Делей установки");
      slidersetting1.setDescription("Задержка между действиями (тики)");
      this.deleyUstanovki = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 5.0, 1.0, 10.0, 0.5);
      slidersetting2.setName("Скорость наводки");
      slidersetting2.setDescription("Скорость поворота камеры");
      this.speedNavodki = slidersetting2;
      BooleanSetting booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Авто-выкл");
      booleansetting.setDescription("Выключить модуль после выстрела");
      this.autoVykl = booleansetting;
      this.autoStartState = AutoStartState.IDLE;
      this.value235 = 0;
      this.blockPos = null;
      this.direction = Direction.UP;
      this.vec3d = null;
      this.value236 = 0.0F;
      this.value237 = 0.0F;
      this.value238 = -1;
      this.value239 = -1;
      this.value240 = -1;
      this.value241 = -1;
      this.value242 = -1;
      this.value243 = 0;
      this.rotationUtil = new RotationUtil();
      this.addSettings(new Setting[]{this.mode, this.bindAktivacii, this.tikiZaryadkiLuka, this.deleyUstanovki, this.speedNavodki, this.autoVykl});
   }

   private void update11() {
      this.options().useKey.setPressed(false);
      this.networkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
      this.player().stopUsingItem();
      this.autoStartState = AutoStartState.RESTORE_SLOT;
      this.value235 = 0;
   }

   private float[] getFloatArrayByVec3d(Vec3d vec3d) {
      Vec3d vec3dx = this.player().getEyePos();
      double d0 = vec3d.x - vec3dx.x;
      double d1 = vec3d.y + 0.5 - vec3dx.y;
      double d2 = vec3d.z - vec3dx.z;
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      int i = (int)this.tikiZaryadkiLuka.getValue();
      float f = BowItem.getPullProgress(i);
      double d4 = f * 3.0;
      if (d4 < 0.1) {
         d4 = 0.1;
      }

      float f1 = this.getFloatByDoubleDoubleDouble(d4, d3, d1);
      float f2 = (float)(Math.atan2(d2, d0) * 180.0 / Math.PI) - 90.0F;
      return new float[]{f2, f1};
   }

   private void update12() {
      this.onInt(this.value241);
      this.autoStartState = AutoStartState.SF_ROTATE_TO_TARGET;
      this.value235 = 0;
   }

   private void update13() {
      if (this.check3() && this.value235 >= 2) {
         this.autoStartState = AutoStartState.SF_START_BOW_CHARGE;
         this.value235 = 0;
      }
   }

   private void update14() {
      this.value243++;
      if (this.value243 >= (int)this.tikiZaryadkiLuka.getValue()) {
         this.autoStartState = AutoStartState.SF_RELEASE_BOW;
         this.value235 = 0;
      }
   }

   private void update15() {
      if (this.check3() && this.value235 >= 2) {
         this.autoStartState = AutoStartState.SF_PLACE_RAIL;
         this.value235 = 0;
      }
   }

   private void update16() {
      this.onInt(this.value239);
      BlockHitResult blockhitresult = new BlockHitResult(
         Vec3d.ofCenter(this.blockPos).add(Vec3d.of(this.direction.getVector()).multiply(0.5)), this.direction, this.blockPos, false
      );
      this.interactionManager().interactBlock(this.clientPlayer(), this.mainHand(), blockhitresult);
      this.player().swingHand(this.mainHand());
      this.autoStartState = AutoStartState.SF_WAIT_RAIL;
      this.value235 = 0;
   }

   @Override
   public void onTick() {
      if (!this.notInGame() && this.autoStartState != AutoStartState.IDLE) {
         this.value235++;
         switch (this.autoStartState) {
            case ROTATE_TO_BLOCK:
               this.update20();
               break;
            case PLACE_RAIL:
               this.update22();
               break;
            case WAIT_RAIL:
               this.update26();
               break;
            case PLACE_CART:
               this.update23();
               break;
            case WAIT_CART:
               this.update25();
               break;
            case SWAP_TO_BOW:
               this.update18();
               break;
            case ROTATE_TO_CART:
               this.update27();
               break;
            case START_BOW_CHARGE:
               this.update24();
               break;
            case BOW_CHARGING:
               this.update29();
               break;
            case RELEASE_BOW:
               this.update11();
               break;
            case SF_SWAP_TO_BOW:
               this.update12();
               break;
            case SF_ROTATE_TO_TARGET:
               this.update13();
               break;
            case SF_START_BOW_CHARGE:
               this.update28();
               break;
            case SF_BOW_CHARGING:
               this.update14();
               break;
            case SF_RELEASE_BOW:
               this.update36();
               break;
            case SF_WAIT_AFTER_SHOT:
               this.update34();
               break;
            case SF_ROTATE_TO_BLOCK:
               this.update15();
               break;
            case SF_PLACE_RAIL:
               this.update16();
               break;
            case SF_WAIT_RAIL:
               this.update35();
               break;
            case SF_PLACE_CART:
               this.update32();
               break;
            case RESTORE_SLOT:
               this.update31();
               break;
            case DONE:
               this.update30();
         }

         if (this.value235 > 200) {
            this.update33();
         }
      }
   }

   private void update17() {
      float[] afloat = this.getFloatArrayByVec3d2(this.vec3d);
      this.value236 = afloat[0];
      this.value237 = afloat[1];
   }

   private void update18() {
      this.onInt(this.value241);
      this.autoStartState = AutoStartState.ROTATE_TO_CART;
      this.value235 = 0;
   }

   private void update19() {
      float[] afloat = this.getFloatArrayByVec3d(this.vec3d);
      this.value236 = afloat[0];
      this.value237 = afloat[1];
   }

   @Override
   public void onDisable() {
      this.update33();
   }

   private void update20() {
      if (this.check3() && this.value235 >= 2) {
         this.autoStartState = AutoStartState.PLACE_RAIL;
         this.value235 = 0;
      }
   }

   private void update21() {
      if (this.isEnabled() && !this.notInGame()) {
         if (this.autoStartState == AutoStartState.IDLE) {
            PlayerEntity playerentity = this.player();
            if (playerentity != null) {
               BlockHitResult blockhitresult = this.getBlockHitResult();
               if (blockhitresult != null && blockhitresult.getType() == Type.BLOCK) {
                  this.blockPos = blockhitresult.getBlockPos();
                  this.direction = blockhitresult.getSide();
                  BlockPos blockpos = this.blockPos.offset(this.direction);
                  this.vec3d = Vec3d.ofBottomCenter(blockpos);
                  this.value239 = this.getIntByItem(Items.RAIL);
                  if (this.value239 != -1) {
                     this.value240 = this.getIntByItem(Items.TNT_MINECART);
                     if (this.value240 != -1) {
                        this.value241 = this.getIntByItem(Items.BOW);
                        if (this.value241 != -1) {
                           this.value242 = this.getIntByItem(Items.ARROW);
                           if (this.value242 != -1) {
                              this.value238 = playerentity.getInventory().selectedSlot;
                              this.rotationUtil.update();
                              boolean flag = "Сначала стрелять".equals(this.mode.getString2());
                              if (flag) {
                                 this.update19();
                                 this.autoStartState = AutoStartState.SF_SWAP_TO_BOW;
                              } else {
                                 this.update17();
                                 this.autoStartState = AutoStartState.ROTATE_TO_BLOCK;
                              }

                              this.value235 = 0;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean check3() {
      float f = this.player().getYaw();
      float f1 = this.player().getPitch();
      double d0 = RandomUtil.getDoubleByDouble(this.value236 - f);
      double d1 = this.value237 - f1;
      return Math.sqrt(d0 * d0 + d1 * d1) < 1.5;
   }

   @Override
   public void update7() {
      if (!this.notInGame() && this.autoStartState != AutoStartState.IDLE && this.autoStartState != AutoStartState.DONE) {
         switch (this.autoStartState) {
            case ROTATE_TO_BLOCK:
            case PLACE_RAIL:
            case WAIT_RAIL:
            case PLACE_CART:
            case WAIT_CART:
               this.update17();
               break;
            case SWAP_TO_BOW:
            case ROTATE_TO_CART:
            case START_BOW_CHARGE:
            case BOW_CHARGING:
            case RELEASE_BOW:
               this.update19();
               break;
            case SF_SWAP_TO_BOW:
            case SF_ROTATE_TO_TARGET:
            case SF_START_BOW_CHARGE:
            case SF_BOW_CHARGING:
            case SF_RELEASE_BOW:
               this.update19();
            case SF_WAIT_AFTER_SHOT:
            default:
               break;
            case SF_ROTATE_TO_BLOCK:
            case SF_PLACE_RAIL:
            case SF_WAIT_RAIL:
            case SF_PLACE_CART:
               this.update17();
         }

         RotationUtil rotationutil = this.rotationUtil;
         float f2 = this.value236;
         float f3 = this.value237;
         double d0 = this.speedNavodki.getValue();
         float f1 = f3;
         float f = f2;
         rotationutil.onDoubleFloatFloat(d0, f1, f);
      }
   }

   private void update22() {
      this.onInt(this.value239);
      BlockHitResult blockhitresult = new BlockHitResult(
         Vec3d.ofCenter(this.blockPos).add(Vec3d.of(this.direction.getVector()).multiply(0.5)), this.direction, this.blockPos, false
      );
      ClientPlayerInteractionManager clientplayerinteractionmanager = this.interactionManager();
      if (clientplayerinteractionmanager != null) {
         clientplayerinteractionmanager.interactBlock(this.clientPlayer(), this.mainHand(), blockhitresult);
         this.player().swingHand(this.mainHand());
      }

      this.autoStartState = AutoStartState.WAIT_RAIL;
      this.value235 = 0;
   }

   @Override
   public void onEnable() {
      this.update33();
   }

   private void update23() {
      this.onInt(this.value240);
      BlockPos blockpos = this.blockPos.offset(this.direction);
      BlockHitResult blockhitresult = new BlockHitResult(Vec3d.ofBottomCenter(blockpos).add(0.0, 0.0625, 0.0), Direction.UP, blockpos, false);
      this.interactionManager().interactBlock(this.clientPlayer(), this.mainHand(), blockhitresult);
      this.player().swingHand(this.mainHand());
      this.autoStartState = AutoStartState.WAIT_CART;
      this.value235 = 0;
   }

   @Override
   public void update8() {
      if (!this.notInGame()) {
         if (this.autoStartState == AutoStartState.START_BOW_CHARGE
            || this.autoStartState == AutoStartState.BOW_CHARGING
            || this.autoStartState == AutoStartState.SF_START_BOW_CHARGE
            || this.autoStartState == AutoStartState.SF_BOW_CHARGING) {
            this.options().useKey.setPressed(true);
         } else if (this.autoStartState == AutoStartState.RELEASE_BOW || this.autoStartState == AutoStartState.SF_RELEASE_BOW) {
            this.options().useKey.setPressed(false);
         }
      }
   }

   private void update24() {
      this.interactionManager().interactItem(this.player(), this.mainHand());
      this.value243 = 0;
      this.autoStartState = AutoStartState.BOW_CHARGING;
      this.value235 = 0;
   }

   private void update25() {
      if (this.value235 >= (int)this.deleyUstanovki.getValue()) {
         this.autoStartState = AutoStartState.SWAP_TO_BOW;
         this.value235 = 0;
      }
   }

   private void update26() {
      if (this.value235 >= (int)this.deleyUstanovki.getValue()) {
         this.autoStartState = AutoStartState.PLACE_CART;
         this.value235 = 0;
      }
   }

   private void update27() {
      if (this.check3() && this.value235 >= 2) {
         this.autoStartState = AutoStartState.START_BOW_CHARGE;
         this.value235 = 0;
      }
   }

   private void update28() {
      this.interactionManager().interactItem(this.player(), this.mainHand());
      this.value243 = 0;
      this.autoStartState = AutoStartState.SF_BOW_CHARGING;
      this.value235 = 0;
   }

   private double getDoubleByDoubleFloatDoubleDouble(double value, float value2, double value3, double value4) {
      double d0 = Math.toRadians(value2);
      double d1 = Math.cos(d0);
      double d2 = -Math.sin(d0);
      double d3 = d1 * value;
      double d4 = d2 * value;
      double d5 = 0.0;
      double d6 = 0.0;

      for (int i = 0; i < 300; i++) {
         double d7 = d5;
         double d8 = d6;
         d5 += d3;
         d6 += d4;
         d3 *= 0.99;
         d4 = d4 * 0.99 - 0.05;
         if (d5 >= value4) {
            double d9 = (value4 - d7) / (d5 - d7);
            double d10 = d8 + (d6 - d8) * d9;
            return Math.abs(d10 - value3);
         }
      }

      return Math.abs(d6 - value3) + Math.abs(d5 - value4) * 10.0;
   }

   private BlockHitResult getBlockHitResult() {
      PlayerEntity playerentity = this.player();
      if (playerentity != null && this.world() != null) {
         Vec3d vec3dx = playerentity.getEyePos();
         Vec3d vec3d1 = playerentity.getRotationVec(1.0F);
         Vec3d vec3d2 = vec3dx.add(vec3d1.multiply(4.0));
         RaycastContext raycastcontext = new RaycastContext(vec3dx, vec3d2, ShapeType.OUTLINE, FluidHandling.NONE, playerentity);
         return this.world().raycast(raycastcontext);
      } else {
         return null;
      }
   }

   private float getFloatByDoubleDoubleDouble(double value, double value2, double value3) {
      float f = 0.0F;
      double d0 = Double.MAX_VALUE;

      for (float f1 = -90.0F; f1 <= 90.0F; f1 += 0.25F) {
         double d1 = this.getDoubleByDoubleFloatDoubleDouble(value, f1, value3, value2);
         if (d1 < d0) {
            d0 = d1;
            f = f1;
         }
      }

      for (float f2 = f - 0.5F; f2 <= f + 0.5F; f2 += 0.02F) {
         double d2 = this.getDoubleByDoubleFloatDoubleDouble(value, f2, value3, value2);
         if (d2 < d0) {
            d0 = d2;
            f = f2;
         }
      }

      return f;
   }

   private void update29() {
      this.value243++;
      if (this.value243 >= (int)this.tikiZaryadkiLuka.getValue()) {
         this.autoStartState = AutoStartState.RELEASE_BOW;
         this.value235 = 0;
      }
   }

   private float[] getFloatArrayByVec3d2(Vec3d vec3d) {
      return RotationUtil.getFloatArrayByVec3d(vec3d);
   }

   private int getIntByItem(Item item2) {
      PlayerInventory playerinventory = this.inventory();

      for (int i = 0; i < 9; i++) {
         if (playerinventory.getStack(i).isOf(item2)) {
            return i;
         }
      }

      for (int j = 9; j < 36; j++) {
         if (playerinventory.getStack(j).isOf(item2)) {
            return j;
         }
      }

      return -1;
   }

   private void onInt(int count) {
      if (count >= 0 && !this.notInGame()) {
         if (count < 9) {
            this.inventory().selectedSlot = count;
         } else {
            int i = Math.max(this.value238, 0);
            ClientPlayerInteractionManager clientplayerinteractionmanager = this.interactionManager();
            if (clientplayerinteractionmanager != null) {
               int j = this.player().currentScreenHandler.syncId;
               clientplayerinteractionmanager.clickSlot(j, count, i, SlotActionType.SWAP, this.player());
            }

            this.inventory().selectedSlot = i;
         }
      }
   }

   private void update30() {
      this.update33();
      if (this.autoVykl.isFlag3()) {
         this.setEnabled(false);
      }
   }

   private void update31() {
      if (this.value235 >= 1) {
         if (this.value238 >= 0) {
            this.inventory().selectedSlot = this.value238;
         }

         this.autoStartState = AutoStartState.DONE;
         this.value235 = 0;
      }
   }

   private void update32() {
      this.onInt(this.value240);
      BlockPos blockpos = this.blockPos.offset(this.direction);
      BlockHitResult blockhitresult = new BlockHitResult(Vec3d.ofBottomCenter(blockpos).add(0.0, 0.0625, 0.0), Direction.UP, blockpos, false);
      this.interactionManager().interactBlock(this.clientPlayer(), this.mainHand(), blockhitresult);
      this.player().swingHand(this.mainHand());
      this.autoStartState = AutoStartState.RESTORE_SLOT;
      this.value235 = 0;
   }

   private void update33() {
      this.options().useKey.setPressed(false);
      this.autoStartState = AutoStartState.IDLE;
      this.value235 = 0;
      this.blockPos = null;
      this.direction = Direction.UP;
      this.vec3d = null;
      this.value236 = 0.0F;
      this.value237 = 0.0F;
      this.value238 = -1;
      this.value239 = -1;
      this.value240 = -1;
      this.value241 = -1;
      this.value242 = -1;
      this.value243 = 0;
      this.rotationUtil.setTime();
   }

   private void update34() {
      if (this.value235 >= 1) {
         this.update17();
         this.autoStartState = AutoStartState.SF_ROTATE_TO_BLOCK;
         this.value235 = 0;
      }
   }

   private void update35() {
      if (this.value235 >= (int)this.deleyUstanovki.getValue()) {
         this.autoStartState = AutoStartState.SF_PLACE_CART;
         this.value235 = 0;
      }
   }

   private void update36() {
      this.options().useKey.setPressed(false);
      this.networkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
      this.player().stopUsingItem();
      this.autoStartState = AutoStartState.SF_WAIT_AFTER_SHOT;
      this.value235 = 0;
   }
}
