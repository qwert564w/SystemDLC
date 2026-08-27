package client.module;

import client.api.UiMetrics;
import client.data.SystemFriend;
import client.util.ChatSpamGuard;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Feature implements UiMetrics {
   public static long NANOS_PER_SECOND = 1000000000L;
   public static MinecraftClient mc = MinecraftClient.getInstance();

   public boolean isFriend(PlayerEntity playerEntity) {
      return SystemFriend.getInstance().isPlayerEntity(playerEntity);
   }

   public PlayerEntity player() {
      return mc.player;
   }

   protected Hand offHand() {
      return Hand.OFF_HAND;
   }

   public void sendMessage(String text2) {
      if (this.inGame()) {
         mc.player.sendMessage(Text.of(text2), false);
      }
   }

   protected void clickSlot(int count) {
      ClientPlayerInteractionManager clientplayerinteractionmanager = mc.interactionManager;
      ClientPlayerEntity clientplayerentity = mc.player;
      if (clientplayerinteractionmanager != null && clientplayerentity != null) {
         clientplayerinteractionmanager.clickSlot(clientplayerentity.currentScreenHandler.syncId, count, 0, SlotActionType.PICKUP, clientplayerentity);
      }
   }

   public boolean isFriend(LivingEntity livingEntity) {
      return SystemFriend.getInstance().isLivingEntity(livingEntity);
   }

   protected void quickMoveSlot(int count) {
      this.clickSlot(count < 9 ? count + 36 : count);
      this.clickSlot(6);
      this.clickSlot(count < 9 ? count + 36 : count);
   }

   public boolean isFriend(String text2) {
      return SystemFriend.getInstance().isString(text2);
   }

   protected void swapSlots(int count, int count2) {
      this.clickSlot(count < 9 ? count + 36 : count);
      this.clickSlot(count2 < 9 ? count2 + 36 : count2);
      this.clickSlot(count < 9 ? count + 36 : count);
   }

   public void sendPrefixedMessage(String text2) {
      if (this.inGame()) {
         ChatSpamGuard.setFlag();
         mc.player.sendMessage(Text.of(text2), false);
      }
   }

   protected ServerWorld serverWorld() {
      return mc.getServer() != null ? mc.getServer().getWorld(Objects.requireNonNull(mc.world).getRegistryKey()) : null;
   }

   protected void interactItem(Hand hand) {
      ClientPlayerInteractionManager clientplayerinteractionmanager = mc.interactionManager;
      ClientPlayerEntity clientplayerentity = mc.player;
      if (clientplayerinteractionmanager != null && clientplayerentity != null) {
         clientplayerinteractionmanager.interactItem(clientplayerentity, hand);
      }
   }

   protected Hand mainHand() {
      return Hand.MAIN_HAND;
   }

   public World world() {
      return mc.world;
   }

   public MinecraftClient client() {
      return mc;
   }

   protected Screen currentScreen() {
      return mc.currentScreen;
   }

   protected ClientPlayNetworkHandler networkHandler() {
      return mc.getNetworkHandler();
   }

   protected PlayerInventory inventory() {
      ClientPlayerEntity clientplayerentity = mc.player;
      return clientplayerentity != null ? clientplayerentity.getInventory() : null;
   }

   public GameRenderer gameRenderer() {
      return mc.gameRenderer;
   }

   protected GameOptions options() {
      return mc.options;
   }

   protected ClientWorld clientWorld() {
      return mc.world;
   }

   protected ClientPlayerEntity clientPlayer() {
      return mc.player;
   }

   protected ClientPlayerInteractionManager interactionManager() {
      return mc.interactionManager;
   }

   protected boolean notInGame() {
      return !this.inGame();
   }

   protected boolean inGame() {
      return mc.player != null && mc.world != null;
   }
}
