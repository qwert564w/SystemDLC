package client.module.client;

import client.concurrent.WaypointStore;
import client.data.EventStatus;
import client.data.Waypoint;
import client.enums.EventState;
import client.enums.PacketDirection;
import client.module.Category;
import client.module.Module;
import client.module.player.SafeLeave;
import client.network.PacketEvent;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.EventChatParser;
import client.util.EventInfo;
import client.util.EventSchedule;
import client.util.PvpStateParser;
import client.util.StringParts;
import client.util.WaypointMath;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class Waypoints extends Module {
   private static final String[] stringArray = new String[]{"аир-дроп", "аирдроп", "air drop", "airdrop", "алтарь нежити"};
   private BooleanSetting renderVMire;
   private BooleanSetting renderVHude;
   private SliderSetting maxRange;
   private SliderSetting sizePlashki;
   private BooleanSetting togglSkryvaetVMire;
   private BooleanSetting togglSkryvaetVHude;
   private BooleanSetting metkiEventov;
   private final WaypointMath waypointMath;
   private final List<String> list;
   private long time;
   private long time2;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private WeakReference<ClientWorld> weakReference;

   public Waypoints() {
      super("Waypoints", Category.CLIENT);
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Рендер в мире");
      booleansetting.setDescription("Отображать плашку вейпоинта в мире");
      this.renderVMire = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Рендер в худе");
      booleansetting.setDescription("Показывать вейпоинты в HUD-списке");
      this.renderVHude = booleansetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1000.0, 16.0, 50000.0, 1.0, StringParts.join(new String[]{"м"}), 0);
      slidersetting.setName("Макс. дистанция");
      slidersetting.setDescription("Максимальная дистанция отображения");
      this.maxRange = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 50.0, 10.0, 200.0, 5.0, "%", 0);
      slidersetting1.setName("Размер плашки");
      slidersetting1.setDescription("Размер плашки вейпоинта в мире");
      this.sizePlashki = slidersetting1;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Тоггл скрывает в мире");
      booleansetting.setDescription("Чекбокс на карточке прячет плашку в мире");
      this.togglSkryvaetVMire = booleansetting;
      booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Тоггл скрывает в худе");
      booleansetting.setDescription("Чекбокс на карточке прячет вейпоинт в HUD");
      this.togglSkryvaetVHude = booleansetting;
      booleansetting = new BooleanSetting("", "", false);
      booleansetting.setName("Метки эвентов");
      booleansetting.setDescription("После захода на анку запросить /events delay и отметить активные эвенты");
      this.metkiEventov = booleansetting;
      this.waypointMath = new WaypointMath();
      this.list = new CopyOnWriteArrayList<>();
      this.time = 0L;
      this.time2 = 0L;
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.weakReference = new WeakReference<>(null);
      this.addSettings(
         new Setting[]{
            this.renderVMire, this.renderVHude, this.maxRange, this.sizePlashki, this.togglSkryvaetVMire, this.togglSkryvaetVHude, this.metkiEventov
         }
      );
      this.setEnabledSilent(true);
   }

   @Override
   public void onTick() {
      if (this.metkiEventov.isFlag3()) {
         long i = System.currentTimeMillis();
         this.setLong(i);
         if (this.time != 0L && i >= this.time) {
            if (!this.notInGame() && this.networkHandler() != null) {
               if (!PvpStateParser.check2() && !SafeLeave.isFlag()) {
                  this.time = 0L;
                  this.flag3 = true;

                  try {
                     this.networkHandler().sendChatCommand("events delay");
                  } finally {
                     this.flag3 = false;
                  }

                  this.setBoolean(true);
               } else {
                  this.time = i + 5000L;
               }
            }
         } else {
            if (this.time2 != 0L && i >= this.time2) {
               this.time2 = 0L;
               this.setEventSchedule(EventChatParser.getEventScheduleByList(this.list));
               this.list.clear();
            }
         }
      } else {
         if (this.time2 != 0L || this.time != 0L) {
            this.update11();
         }
      }
   }

   public boolean check3() {
      return this.togglSkryvaetVHude.isFlag3();
   }

   public BooleanSetting getRenderVHude() {
      return this.renderVHude;
   }

   public SliderSetting getSizePlashki() {
      return this.sizePlashki;
   }

   public BooleanSetting getTogglSkryvaetVMire() {
      return this.togglSkryvaetVMire;
   }

   private void setBoolean(boolean flag3) {
      this.list.clear();
      this.flag = false;
      this.flag2 = flag3;
      this.time2 = System.currentTimeMillis() + 6000L;
   }

   @Override
   public void onDisable() {
      this.update11();
   }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (!StreamBypass.check6()) {
         if (!this.notInGame()) {
            if (this.renderVMire.isFlag3()) {
               if (WaypointStore.check()) {
                  String s = WaypointStore.getString2();
                  if (s != null) {
                     ArrayList<Waypoint> arraylist = new ArrayList<>();
                     double d0 = this.maxRange.getValue();
                     boolean flagx = this.togglSkryvaetVMire.isFlag3();

                     for (Waypoint waypoint : (Iterable<Waypoint>)(WaypointStore.getInstance().getListByString(s))) {
                        if (!waypoint.isFlag3() && (!flagx || waypoint.isFlag())) {
                           double d1 = this.player().getX() - waypoint.getValue();
                           double d2 = this.player().getY() - waypoint.getValue2();
                           double d3 = this.player().getZ() - waypoint.getValue3();
                           double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                           if (!(d4 > d0)) {
                              arraylist.add(waypoint);
                           }
                        }
                     }

                     if (!arraylist.isEmpty()) {
                        try {
                           WaypointMath waypointmath = this.waypointMath;
                           float f = this.getFloat();
                           waypointmath.render(worldRenderContext, f, arraylist);
                        } catch (Exception exception) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public BooleanSetting getTogglSkryvaetVHude() {
      return this.togglSkryvaetVHude;
   }

   public SliderSetting getMaxRange() {
      return this.maxRange;
   }

   @Override
   public void onPacketEvent(PacketEvent packetEvent) {
      if (this.metkiEventov.isFlag3()) {
         if (packetEvent.getPacketDirection() == PacketDirection.SEND) {
            if (!this.flag3 && packetEvent.getPacket() instanceof CommandExecutionC2SPacket commandexecutionc2spacket) {
               CommandExecutionC2SPacket commandexecutionc2spacket1 = commandexecutionc2spacket;

               String s1 = commandexecutionc2spacket1.command();
               if (s1.trim().toLowerCase().startsWith("events")) {
                  this.setBoolean(false);
               }
            }
         } else if (this.time2 != 0L) {
            if (packetEvent.getPacket() instanceof GameMessageS2CPacket gamemessages2cpacket) {
               GameMessageS2CPacket gamemessages2cpacket1 = gamemessages2cpacket;

               Text text = gamemessages2cpacket1.content();
               gamemessages2cpacket1 = gamemessages2cpacket;

               boolean flagx = gamemessages2cpacket1.overlay();
               if (true) {
                  if (flagx) {
                     return;
                  }

                  String s = text.getString();
                  this.list.add(s);
                  if (!this.flag && EventChatParser.isString2(s)) {
                     this.flag = true;
                     this.time2 = System.currentTimeMillis() + 1200L;
                  }

                  if (this.flag2 && EventChatParser.isString(s)) {
                     packetEvent.setFlag(true);
                  }

                  return;
               }
            }
         }
      }
   }

   public BooleanSetting getRenderVMire() {
      return this.renderVMire;
   }

   private void update11() {
      this.list.clear();
      this.time = 0L;
      this.time2 = 0L;
      this.flag = false;
      this.flag2 = false;
   }

   public boolean check4() {
      return this.isEnabled() && this.renderVHude.isFlag3();
   }

   private static boolean isString(String text2) {
      if (text2 == null) {
         return true;
      } else {
         String s = text2.toLowerCase();

         for (String s1 : stringArray) {
            if (s.contains(s1)) {
               return true;
            }
         }

         return false;
      }
   }

   private void setEventSchedule(EventSchedule eventSchedule) {
      WaypointStore waypointstore = WaypointStore.getInstance();
      waypointstore.update3();
      String s = WaypointStore.getString2();
      if (s != null) {
         ArrayList<EventInfo> arraylist = new ArrayList(eventSchedule.getActive());
         arraylist.addAll(eventSchedule.getUpcoming());
         long i = System.currentTimeMillis();
         long j = Long.MAX_VALUE;

         for (EventInfo eventinfo : arraylist) {
            if (!isString(eventinfo.getName())) {
               EventStatus eventstatus = eventinfo.getStatus();
               Integer integer = eventstatus == null ? null : eventstatus.getRemainingSec();
               boolean flagx = eventstatus != null && eventstatus.getState() == EventState.PENDING;
               BlockPos blockpos = eventinfo.getCoords();
               boolean flag1 = blockpos == null;
               if (!flag1 || flagx && integer != null) {
                  Waypoint waypoint = waypointstore.getWaypointByStringIntIntIntString(
                     eventinfo.getName(), flag1 ? 0 : blockpos.getX(), flag1 ? 0 : blockpos.getY(), flag1 ? 0 : blockpos.getZ(), s
                  );
                  if (waypoint != null) {
                     waypoint.setFlag3(flag1);
                     if (flagx && integer != null) {
                        long k = i + integer.intValue() * 1000L;
                        waypoint.setTime(k);
                        j = Math.min(j, k);
                     }
                  }
               }
            }
         }

         if (eventSchedule.getNextDelaySec() != null) {
            long l = i + eventSchedule.getNextDelaySec().intValue() * 1000L;
            Waypoint waypoint1 = waypointstore.getWaypointByStringIntIntIntString("Следующий ивент", 0, 0, 0, s);
            if (waypoint1 != null) {
               waypoint1.setFlag3(true);
               waypoint1.setTime(l);
            }

            j = Math.min(j, l);
         }

         if (this.flag) {
            long i1 = i + 300000L;
            if (j != Long.MAX_VALUE) {
               i1 = Math.min(i1, j + 3000L);
            }

            this.time = Math.max(i1, i + 30000L);
         }
      }
   }

   private void setLong(long time2) {
      ClientWorld clientworld = this.clientWorld();
      if (clientworld != null && clientworld != this.weakReference.get()) {
         this.weakReference = new WeakReference<>(clientworld);
         WaypointStore.getInstance().update3();
         this.time = time2 + 2000L;
      }
   }

   public float getFloat() {
      return this.sizePlashki.getValueAsFloat() / 100.0F;
   }

   public double getDouble() {
      return this.maxRange.getValue();
   }

   @Override
   public void update4() {
      this.update11();
      WaypointStore.getInstance().update3();
   }

   @Override
   public void onEnable() {
   }
}
