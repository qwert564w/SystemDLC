package client.audio;

import client.concurrent.AssetIndex;
import client.data.SoundBuffer;
import client.enums.SoundEvent;
import client.module.client.SoundsModule;
import client.network.ConfigApi;
import client.util.UnsafeAccess;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

public class SoundEngine {
   private static final SoundEngine INSTANCE = new SoundEngine();
   private final Map<SoundEvent, SoundSource> map = new EnumMap<>(SoundEvent.class);
   private final Map<SoundEvent, Boolean> map2 = new EnumMap<>(SoundEvent.class);
   private final UnsafeAccess<SoundsModule> unsafeAccess = new UnsafeAccess<>(SoundsModule.class);
   private final ExecutorService executorService = Executors.newSingleThreadExecutor(var0 -> {
      Thread thread = new Thread(var0, "b");
      thread.setDaemon(true);
      return thread;
   });
   private long time = 0L;
   private long time2 = 0L;
   private boolean flag = false;
   private float value = 1.0F;
   private boolean flag2 = true;
   private boolean flag3 = false;

   private SoundEngine() {
      for (SoundEvent soundevent : SoundEvent.values()) {
         this.map2.put(soundevent, true);
      }
   }

   private void update2() {
      try {
         long i = ALC10.alcOpenDevice((ByteBuffer)null);
         if (i == 0L) {
            return;
         }

         ALCCapabilities alccapabilities = ALC.createCapabilities(i);
         long j = ALC10.alcCreateContext(i, (int[])null);
         if (j == 0L) {
            ALC10.alcCloseDevice(i);
            return;
         }

         if (!ALC10.alcMakeContextCurrent(j)) {
            ALC10.alcDestroyContext(j);
            ALC10.alcCloseDevice(i);
            return;
         }

         AL.createCapabilities(alccapabilities);
         this.time = i;
         this.time2 = j;
         this.flag = true;
      } catch (Throwable throwable) {
      }
   }

   public void setFlag3(boolean flag) {
      this.flag3 = flag;
   }

   private void update3() {
      Map mapx = AssetIndex.getMap2();
      if (mapx == null) {
         mapx = ConfigApi.getMap();
      }

      if (mapx != null) {
         for (SoundEvent soundevent : SoundEvent.values()) {
            byte[] abyte = (byte[])mapx.get(soundevent.getText());
            if (abyte != null && abyte.length != 0) {
               this.executorService.execute(() -> {
                  if (this.flag) {
                     long i = this.getLong();

                     try {
                        SoundSource soundsource = this.getSoundSourceByByteArray(abyte);
                        if (soundsource != null) {
                           this.map.put(soundevent, soundsource);
                        }
                     } finally {
                        this.onLong(i);
                     }
                  }
               });
            }
         }
      }
   }

   public void update4() {
      this.executorService.execute(() -> {
         long i = this.getLong();

         try {
            for (SoundSource soundsource : this.map.values()) {
               soundsource.update();
            }

            this.map.clear();
            this.map2.clear();
            if (this.time2 != 0L) {
               ALC10.alcMakeContextCurrent(0L);
               ALC10.alcDestroyContext(this.time2);
               this.time2 = 0L;
            }

            if (this.time != 0L) {
               ALC10.alcCloseDevice(this.time);
               this.time = 0L;
            }

            this.flag = false;
         } catch (Throwable throwable) {
         } finally {
            if (i != 0L) {
               ALC10.alcMakeContextCurrent(i);
            }
         }
      });
      this.executorService.shutdown();
   }

   public boolean isFlag2() {
      return this.flag2;
   }

   public static SoundEngine getInstance() {
      return INSTANCE;
   }

   public void setFlag2(boolean flag) {
      this.flag2 = flag;
   }

   public void onSoundEventBoolean(SoundEvent soundEvent, boolean flag) {
      this.map2.put(soundEvent, flag);
   }

   private long getLong() {
      long i = this.time2;
      if (i == 0L) {
         return 0L;
      } else {
         long j = ALC10.alcGetCurrentContext();
         if (j != i) {
            ALC10.alcMakeContextCurrent(i);
         }

         return j;
      }
   }

   private void onSoundSourceFloat2(SoundSource soundSource, float value) {
      if (this.flag) {
         long i = this.getLong();

         try {
            int j = soundSource.getInt();
            if (j != 0) {
               AL10.alSourceRewind(j);
               AL10.alSourcef(j, 4106, Math.clamp(value, 0.0F, 1.0F));
               AL10.alSourcePlay(j);
               return;
            }
         } catch (Exception exception) {
            return;
         } finally {
            this.onLong(i);
         }
      }
   }

   public void onSoundEvent(SoundEvent soundEvent) {
      if (!this.flag3 && this.flag2) {
         if (this.unsafeAccess.getModule2() != null) {
            if (this.isSoundEvent(soundEvent)) {
               float f = this.value;
               if (!(f <= 0.0F)) {
                  SoundSource soundsource = this.map.get(soundEvent);
                  if (soundsource != null) {
                     try {
                        this.executorService.execute(() -> this.onSoundSourceFloat2(soundsource, f));
                     } catch (Exception exception) {
                     }
                  }
               }
            }
         }
      }
   }

   private static SoundBuffer getSoundBufferByByteArray(byte[] valueArray) {
      try {
         if (valueArray.length < 44) {
            return null;
         } else {
            ByteBuffer bytebuffer = ByteBuffer.wrap(valueArray).order(ByteOrder.LITTLE_ENDIAN);
            if (bytebuffer.getInt() != 1179011410) {
               return null;
            } else {
               bytebuffer.getInt();
               if (bytebuffer.getInt() != 1163280727) {
                  return null;
               } else {
                  int i = 0;
                  int j = 0;
                  int k = 0;
                  byte[] abyte = null;

                  while (true) {
                     int i1;
                     label115: {
                        if (bytebuffer.remaining() >= 8) {
                           int l = bytebuffer.getInt();
                           i1 = bytebuffer.getInt();
                           if (i1 < 0 || i1 > bytebuffer.remaining()) {
                              return null;
                           }

                           if (l == 544501094) {
                              int j1 = bytebuffer.position();
                              short short1 = bytebuffer.getShort();
                              i = bytebuffer.getShort() & '\uffff';
                              j = bytebuffer.getInt();
                              bytebuffer.getInt();
                              bytebuffer.getShort();
                              k = bytebuffer.getShort() & '\uffff';
                              if (short1 != 1) {
                                 return null;
                              }

                              bytebuffer.position(j1 + i1);
                              break label115;
                           }

                           if (l != 1635017060) {
                              bytebuffer.position(bytebuffer.position() + i1);
                              break label115;
                           }

                           abyte = new byte[i1];
                           bytebuffer.get(abyte);
                        }

                        if (abyte != null && j != 0 && i != 0 && k != 0) {
                           short short2;
                           if (k == 8 && i == 1) {
                              short2 = 4352;
                           } else if (k == 8 && i == 2) {
                              short2 = 4354;
                           } else if (k == 16 && i == 1) {
                              short2 = 4353;
                           } else {
                              if (k != 16 || i != 2) {
                                 return null;
                              }

                              short2 = 4355;
                           }

                           return new SoundBuffer(short2, j, abyte);
                        }

                        return null;
                     }

                     if ((i1 & 1) == 1 && bytebuffer.remaining() > 0) {
                        bytebuffer.position(bytebuffer.position() + 1);
                     }
                  }
               }
            }
         }
      } catch (Exception exception) {
         return null;
      }
   }

   private SoundSource getSoundSourceByByteArray(byte[] valueArray) {
      try {
         SoundBuffer soundbuffer = getSoundBufferByByteArray(valueArray);
         if (soundbuffer == null) {
            return null;
         } else {
            int i = AL10.alGenBuffers();
            if (AL10.alGetError() != 0) {
               return null;
            } else {
               ByteBuffer bytebuffer = ByteBuffer.allocateDirect(soundbuffer.byteArray.length).order(ByteOrder.nativeOrder());
               bytebuffer.put(soundbuffer.byteArray).flip();
               AL10.alBufferData(i, soundbuffer.value, bytebuffer, soundbuffer.value2);
               if (AL10.alGetError() != 0) {
                  AL10.alDeleteBuffers(i);
                  return null;
               } else {
                  int[] aint = new int[6];

                  for (int j = 0; j < 6; j++) {
                     int k = AL10.alGenSources();
                     if (AL10.alGetError() != 0) {
                        aint[j] = 0;
                     } else {
                        AL10.alSourcei(k, 4105, i);
                        AL10.alSourcef(k, 4106, 1.0F);
                        AL10.alSourcef(k, 4099, 1.0F);
                        AL10.alSource3f(k, 4100, 0.0F, 0.0F, 0.0F);
                        AL10.alSourcei(k, 514, 1);
                        aint[j] = k;
                     }
                  }

                  return new SoundSource(i, aint);
               }
            }
         }
      } catch (Exception exception) {
         return null;
      }
   }

   public boolean isSoundEvent(SoundEvent soundEvent) {
      return this.map2.getOrDefault(soundEvent, true);
   }

   public void update5() {
      this.executorService.execute(this::update2);
      new Thread(this::update3, "SL").start();
   }

   public void setFloat(float value2) {
      this.value = Math.clamp(value2, 0.0F, 1.0F);
   }

   private void onLong(long time) {
      if (time != 0L && time != this.time2) {
         ALC10.alcMakeContextCurrent(time);
      }
   }
}
