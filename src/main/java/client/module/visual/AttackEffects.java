package client.module.visual;

import client.api.Theme;
import client.module.Category;
import client.module.Module;
import client.render.WorldRenderContext;
import client.setting.BooleanSetting;
import client.setting.ColorSetting;
import client.setting.ListSetting;
import client.setting.Setting;
import client.setting.SliderSetting;
import client.util.AttackEffectEntry;
import client.util.MathUtil;
import client.util.StringParts;
import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class AttackEffects extends Module {
   private static final List<String> list = List.of("Плазма", "Искры", "Волна", "Слэш");
   private ListSetting effect;
   private SliderSetting radius;
   private SliderSetting temperatura;
   private SliderSetting yarkost;
   private SliderSetting nasyschennost;
   private SliderSetting dlitelnost;
   private BooleanSetting colorTemy;
   private ColorSetting color;
   private final List<AttackEffectEntry> list2;
   private long time;
   private int value235;
   private int value236;
   private final Vector3f vector3f;
   private final Vector3f vector3f2;

   public AttackEffects() {
      super("AttackEffects", Category.VISUAL);
      ListSetting listsetting = new ListSetting("", "", list, List.of(list.getFirst()), false);
      listsetting.setName("Эффект");
      listsetting.setDescription("Что вспыхивает в точке удара");
      this.effect = listsetting;
      SliderSetting slidersetting = new SliderSetting("", "", 1.4, 0.4, 5.0, 0.05);
      slidersetting.setName("Радиус");
      slidersetting.setDescription("Размер эффекта в блоках");
      this.radius = slidersetting;
      SliderSetting slidersetting1 = new SliderSetting("", "", 1.0, 0.5, 2.0, 0.05);
      slidersetting1.setName("Температура");
      slidersetting1.setDescription("Плотность эффекта: ядро, толщина лучей и волн");
      this.temperatura = slidersetting1;
      SliderSetting slidersetting2 = new SliderSetting("", "", 0.8, 0.3, 3.0, 0.02);
      slidersetting2.setName("Яркость");
      slidersetting2.setDescription("Интенсивность свечения");
      this.yarkost = slidersetting2;
      SliderSetting slidersetting3 = new SliderSetting("", "", 1.2, 0.0, 2.0, 0.05, "", 2);
      slidersetting3.setName("Насыщенность");
      slidersetting3.setDescription("1 — цвет как есть, выше — чище и сочнее");
      this.nasyschennost = slidersetting3;
      SliderSetting slidersetting4 = new SliderSetting("", "", 480.0, 220.0, 900.0, 10.0, StringParts.join(new String[]{" ", "м", "c"}), 0);
      slidersetting4.setName("Длительность");
      slidersetting4.setDescription("Время жизни эффекта");
      this.dlitelnost = slidersetting4;
      BooleanSetting booleansetting = new BooleanSetting("", "", true);
      booleansetting.setName("Цвет темы");
      booleansetting.setDescription("Красить вспышку в акцентный цвет темы клиента");
      this.colorTemy = booleansetting;
      ColorSetting colorsetting = new ColorSetting("", "", -10029228);
      colorsetting.setName("Цвет");
      colorsetting.setDescription("Свой цвет вспышки");
      this.color = colorsetting;
      this.list2 = new ArrayList<>();
      this.value235 = Integer.MIN_VALUE;
      this.vector3f = new Vector3f();
      this.vector3f2 = new Vector3f();
      this.color.setVisibleWhen(this::getBoolean);
      this.addSettings(new Setting[]{this.effect, this.radius, this.temperatura, this.yarkost, this.nasyschennost, this.dlitelnost, this.colorTemy, this.color});
   }

   @Override
   public void onDisable() {
      this.list2.clear();
   }

   @Override
   public void onEnable() {
      this.list2.clear();
      this.value235 = Integer.MIN_VALUE;
      this.time = 0L;
   }

   private float easeOutCubic(float x) { return 1.0F - (float)Math.pow(1.0F - x, 3); }
   private float easeOutQuint(float x) { return 1.0F - (float)Math.pow(1.0F - x, 5); }

   @Override
   public void render5(WorldRenderContext worldRenderContext) {
      if (this.notInGame()) return;
      long i = System.currentTimeMillis();
      this.list2.removeIf(e -> e.isLong(i));
      if (this.list2.isEmpty()) return;

      Camera camera = worldRenderContext.getCamera();
      Vec3d camPos = camera.getPos();
      this.vector3f.set(1.0F, 0.0F, 0.0F).rotate(camera.getRotation());
      this.vector3f2.set(0.0F, 1.0F, 0.0F).rotate(camera.getRotation());
      Matrix4f matrix4f = worldRenderContext.getMatrixStack().peek().getPositionMatrix();
      float t = (float)(i % 100000L) / 1000.0F;

      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
      RenderSystem.depthMask(false);
      RenderSystem.disableCull();
      RenderSystem.setShader(GameRenderer::getPositionColorProgram);

      try {
         for (AttackEffectEntry entry : this.list2) {
            float progress = Math.min(1.0F, (float)(i - entry.getStartMs()) / (float)entry.getDurationMs());
            float alpha = (1.0F - progress) * Math.min(1.0F, entry.getBrightness());
            if (alpha <= 0.01F) continue;
            
            Random rnd = new Random(entry.getSeed());
            int baseColor = entry.getColor();
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >> 8) & 0xFF;
            int b = baseColor & 0xFF;
            int a = (int)(alpha * 255);
            
            int effectType = entry.getEffect();
            float rad = entry.getRadius();
            float temp = entry.getTemperature();
            float px = (float)(entry.getPos().x - camPos.x);
            float py = (float)(entry.getPos().y - camPos.y);
            float pz = (float)(entry.getPos().z - camPos.z);

            switch (effectType) {
               case 0: drawPlasma(matrix4f, px, py, pz, rad, temp, t, progress, r, g, b, a, rnd); break;
               case 1: drawSparks(matrix4f, px, py, pz, temp, progress, r, g, b, a, rnd); break;
               case 2: drawWave(matrix4f, px, py, pz, rad, progress, r, g, b, a); break;
               case 3: drawSlash(matrix4f, px, py, pz, rad, progress, r, g, b, a); break;
            }
         }
      } finally {
         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private void drawPlasma(Matrix4f matrix4f, float px, float py, float pz, float rad, float temp, float t, float p, int r, int g, int b, int a, Random rnd) {
      float ease = easeOutCubic(p);
      float radius = rad * (0.35F + 0.85F * ease);
      int segments = 40;
      BufferBuilder bb = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      for (int i = 0; i < segments; i++) {
         float angle1 = (i / (float)segments) * 2.0F * (float)Math.PI;
         float angle2 = ((i + 1) / (float)segments) * 2.0F * (float)Math.PI;
         float wobble = 1.0F + 0.14F * temp * (float)Math.sin(angle1 * 5.0F + (float)rnd.nextInt(1000) * 0.01F + t * 7.0F);
         float wobble2 = 1.0F + 0.14F * temp * (float)Math.sin(angle2 * 5.0F + (float)rnd.nextInt(1000) * 0.01F + t * 7.0F);
         float r1 = radius * wobble;
         float r2 = radius * wobble2;
         float x1 = (float)Math.cos(angle1) * r1;
         float y1 = (float)Math.sin(angle1) * r1;
         float x2 = (float)Math.cos(angle2) * r2;
         float y2 = (float)Math.sin(angle2) * r2;
         float thickness = 0.05F + 0.05F * temp;
         Vector3f dir1 = new Vector3f(x1, y1, 0).normalize();
         Vector3f dir2 = new Vector3f(x2, y2, 0).normalize();
         Vector3f p1Inner = new Vector3f(x1 - dir1.x() * thickness, y1 - dir1.y() * thickness, 0);
         Vector3f p1Outer = new Vector3f(x1 + dir1.x() * thickness, y1 + dir1.y() * thickness, 0);
         Vector3f p2Inner = new Vector3f(x2 - dir2.x() * thickness, y2 - dir2.y() * thickness, 0);
         Vector3f p2Outer = new Vector3f(x2 + dir2.x() * thickness, y2 + dir2.y() * thickness, 0);
         Vector3f v1 = addBillboard(px, py, pz, p1Inner);
         Vector3f v2 = addBillboard(px, py, pz, p1Outer);
         Vector3f v3 = addBillboard(px, py, pz, p2Outer);
         Vector3f v4 = addBillboard(px, py, pz, p2Inner);
         bb.vertex(matrix4f, v1.x, v1.y, v1.z).color(r, g, b, a);
         bb.vertex(matrix4f, v2.x, v2.y, v2.z).color(r, g, b, a);
         bb.vertex(matrix4f, v3.x, v3.y, v3.z).color(r, g, b, a);
         bb.vertex(matrix4f, v4.x, v4.y, v4.z).color(r, g, b, a);
      }
      BufferRenderer.drawWithGlobalProgram(bb.end());
      
      float coreSize = 0.1F + 0.1F * temp;
      int cr = Math.min(255, r + 90);
      int cg = Math.min(255, g + 90);
      int cb = Math.min(255, b + 90);
      BufferBuilder bb2 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      Vector3f c1 = addBillboard(px, py, pz, new Vector3f(-coreSize, -coreSize, 0));
      Vector3f c2 = addBillboard(px, py, pz, new Vector3f( coreSize, -coreSize, 0));
      Vector3f c3 = addBillboard(px, py, pz, new Vector3f( coreSize,  coreSize, 0));
      Vector3f c4 = addBillboard(px, py, pz, new Vector3f(-coreSize,  coreSize, 0));
      bb2.vertex(matrix4f, c1.x, c1.y, c1.z).color(cr, cg, cb, a);
      bb2.vertex(matrix4f, c2.x, c2.y, c2.z).color(cr, cg, cb, a);
      bb2.vertex(matrix4f, c3.x, c3.y, c3.z).color(cr, cg, cb, a);
      bb2.vertex(matrix4f, c4.x, c4.y, c4.z).color(cr, cg, cb, a);
      BufferRenderer.drawWithGlobalProgram(bb2.end());
   }

   private void drawSparks(Matrix4f matrix4f, float px, float py, float pz, float temp, float p, int r, int g, int b, int a, Random rnd) {
      int count = 10 + (int)(temp * 8);
      BufferBuilder bb = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      for (int i = 0; i < count; i++) {
         double theta = rnd.nextDouble() * Math.PI * 2;
         double phi = rnd.nextDouble() * Math.PI;
         float len = 0.22F + rnd.nextFloat() * 0.25F;
         float dx = (float)(Math.sin(phi) * Math.cos(theta) * len);
         float dy = (float)(Math.cos(phi) * len);
         float dz = (float)(Math.sin(phi) * Math.sin(theta) * len);
         dy -= 0.55F * p * p;
         Vector3f start = new Vector3f(px, py, pz);
         Vector3f end = new Vector3f(px + dx * p, py + dy * p, pz + dz * p);
         Vector3f right = new Vector3f(vector3f).mul(0.035F);
         Vector3f v1 = new Vector3f(start).add(right);
         Vector3f v2 = new Vector3f(start).sub(right);
         Vector3f v3 = new Vector3f(end).sub(right);
         Vector3f v4 = new Vector3f(end).add(right);
         bb.vertex(matrix4f, v1.x, v1.y, v1.z).color(r, g, b, a);
         bb.vertex(matrix4f, v2.x, v2.y, v2.z).color(r, g, b, a);
         bb.vertex(matrix4f, v3.x, v3.y, v3.z).color(r, g, b, a);
         bb.vertex(matrix4f, v4.x, v4.y, v4.z).color(r, g, b, a);
      }
      BufferRenderer.drawWithGlobalProgram(bb.end());
   }

   private void drawWave(Matrix4f matrix4f, float px, float py, float pz, float rad, float p, int r, int g, int b, int a) {
      float ease = easeOutQuint(p);
      float radius = rad * 1.8F * ease;
      float height = 0.35F * (1.0F - p) + 0.06F;
      float y0 = py - 0.45F;
      int segments = 40;
      for (int ring = 0; ring < 2; ring++) {
         float rr = radius * (ring == 0 ? 1.0F : 0.7F);
         BufferBuilder bb = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
         for (int i = 0; i < segments; i++) {
            float angle1 = (i / (float)segments) * 2.0F * (float)Math.PI;
            float angle2 = ((i + 1) / (float)segments) * 2.0F * (float)Math.PI;
            float x1 = (float)Math.cos(angle1) * rr;
            float z1 = (float)Math.sin(angle1) * rr;
            float x2 = (float)Math.cos(angle2) * rr;
            float z2 = (float)Math.sin(angle2) * rr;
            bb.vertex(matrix4f, px + x1, y0, pz + z1).color(r, g, b, a);
            bb.vertex(matrix4f, px + x1, y0 + height, pz + z1).color(r, g, b, a);
            bb.vertex(matrix4f, px + x2, y0 + height, pz + z2).color(r, g, b, a);
            bb.vertex(matrix4f, px + x2, y0, pz + z2).color(r, g, b, a);
         }
         BufferRenderer.drawWithGlobalProgram(bb.end());
      }
   }

   private void drawSlash(Matrix4f matrix4f, float px, float py, float pz, float rad, float p, int r, int g, int b, int a) {
      float ease = easeOutCubic(p);
      float inner = rad * 0.45F;
      float outer = rad * 1.15F * (0.6F + 0.4F * ease);
      int alpha = (int)(a * Math.sin(p * Math.PI));
      if (alpha <= 0) return;
      int segments = 26;
      float startAngle = -137.0F / 2.0F;
      float endAngle = 137.0F / 2.0F;
      BufferBuilder bb = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      for (int i = 0; i < segments; i++) {
         float a1 = (float)Math.toRadians(startAngle + (endAngle - startAngle) * (i / (float)segments));
         float a2 = (float)Math.toRadians(startAngle + (endAngle - startAngle) * ((i + 1) / (float)segments));
         float x1 = (float)Math.cos(a1);
         float y1 = (float)Math.sin(a1);
         float x2 = (float)Math.cos(a2);
         float y2 = (float)Math.sin(a2);
         Vector3f p1In = new Vector3f(x1 * inner, y1 * inner, 0);
         Vector3f p1Out = new Vector3f(x1 * outer, y1 * outer, 0);
         Vector3f p2In = new Vector3f(x2 * inner, y2 * inner, 0);
         Vector3f p2Out = new Vector3f(x2 * outer, y2 * outer, 0);
         Vector3f v1 = addBillboard(px, py, pz, p1In);
         Vector3f v2 = addBillboard(px, py, pz, p1Out);
         Vector3f v3 = addBillboard(px, py, pz, p2Out);
         Vector3f v4 = addBillboard(px, py, pz, p2In);
         bb.vertex(matrix4f, v1.x, v1.y, v1.z).color(r, g, b, alpha);
         bb.vertex(matrix4f, v2.x, v2.y, v2.z).color(r, g, b, alpha);
         bb.vertex(matrix4f, v3.x, v3.y, v3.z).color(r, g, b, alpha);
         bb.vertex(matrix4f, v4.x, v4.y, v4.z).color(r, g, b, alpha);
      }
      BufferRenderer.drawWithGlobalProgram(bb.end());
   }

   private Vector3f addBillboard(float px, float py, float pz, Vector3f local) {
      return new Vector3f(
         px + this.vector3f.x * local.x + this.vector3f2.x * local.y,
         py + this.vector3f.y * local.x + this.vector3f2.y * local.y,
         pz + this.vector3f.z * local.x + this.vector3f2.z * local.y
      );
   }

   private Boolean getBoolean() {
      return !this.colorTemy.isFlag3();
   }

   @Override
   public void onPlayerEntityWorldHandEntityEntityHitResult(PlayerEntity playerEntity, World world2, Hand hand, Entity entity2, EntityHitResult entityHitResult) {
      if (!this.notInGame() && entity2 != null && entity2 != this.player()) {
         long i = System.currentTimeMillis();
         if (entity2.getId() != this.value235 || i - this.time >= 30L) {
            this.value235 = entity2.getId();
            this.time = i;
            Vec3d vec3d = this.player().getRotationVec(1.0F).normalize();
            this.value236 = this.value236 + 1 & 1023;
            float f = this.value236 * 7.31F % 41.0F;
            this.list2
               .add(
                  new AttackEffectEntry(
                     this.getVec3dByVec3dEntity(vec3d, entity2),
                     i,
                     (long)this.dlitelnost.getValue(),
                     this.radius.getValueAsFloat(),
                     this.temperatura.getValueAsFloat(),
                     this.yarkost.getValueAsFloat(),
                     f,
                     this.getInt(),
                     Math.max(0, list.indexOf(this.effect.getString2()))
                  )
               );

            while (this.list2.size() > 12) {
               this.list2.remove(0);
            }
         }
      }
   }

   private Vec3d getVec3dByVec3dEntity(Vec3d vec3d2, Entity entity2) {
      if (this.client().crosshairTarget instanceof EntityHitResult entityhitresult && entityhitresult.getEntity() == entity2) {
         return entityhitresult.getPos();
      } else {
         Box box = entity2.getBoundingBox();
         Vec3d vec3d = this.player().getEyePos();
         return box.raycast(vec3d, vec3d.add(vec3d2.multiply(9.0))).orElse(box.getCenter());
      }
   }

   private int getInt() {
      int i;
      if (this.colorTemy.isFlag3()) {
         try {
            i = Theme.primary();
         } catch (Throwable throwable) {
            i = 6747988;
         }
      } else {
         i = this.color.getInt3();
      }

      float f = this.nasyschennost.getValueAsFloat();
      return MathUtil.getIntByFloatInt(f, i) & 16777215;
   }
}
