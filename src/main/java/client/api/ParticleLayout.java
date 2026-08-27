package client.api;

import client.render.ParticleBuffer;

public interface ParticleLayout {
   public void frame(ParticleBuffer particleBuffer, float[] valueArray, float value, int count);

   public void place(int count, float[] valueArray, float[] valueArray2, int count2, ParticleBuffer particleBuffer);
}
