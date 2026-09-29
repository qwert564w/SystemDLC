package ru.pulse.cosmetic.model;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

public class CosmeticModel {
   private final String name;
   private final int id;
   private final int category;
   private String rawModelJson;
   private Identifier textureId;
   private ModelPosition position = ModelPosition.HEAD;
   private float scale = 1.0F;
   private float x;
   private float y;
   private float z;
   private float yaw;
   private float pitch;
   private float roll;
   private float height = 0.0F;
   private float previewScale = 1.0F;
   private float previewY = 0.0F;
   private JsonObject animationJson;

   public CosmeticModel(String var1, int var2, int var3) {
      this.name = var1;
      this.id = var2;
      this.category = var3;
   }

   public String getName() {
      return this.name;
   }

   public int getId() {
      return this.id;
   }

   public int getCategory() {
      return this.category;
   }

   public String getRawModelJson() {
      return this.rawModelJson;
   }

   public void setRawModelJson(String var1) {
      this.rawModelJson = var1;
   }

   public Identifier getTextureId() {
      return this.textureId;
   }

   public void setTextureId(Identifier var1) {
      this.textureId = var1;
   }

   public ModelPosition getPosition() {
      return this.position;
   }

   public void setPosition(ModelPosition var1) {
      this.position = var1;
   }

   public float getScale() {
      return this.scale;
   }

   public void setScale(float var1) {
      this.scale = var1;
   }

   public float getX() {
      return this.x;
   }

   public void setX(float var1) {
      this.x = var1;
   }

   public float getY() {
      return this.y;
   }

   public void setY(float var1) {
      this.y = var1;
   }

   public float getZ() {
      return this.z;
   }

   public void setZ(float var1) {
      this.z = var1;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public float getRoll() {
      return this.roll;
   }

   public void setRoll(float var1) {
      this.roll = var1;
   }

   public float getHeight() {
      return this.height;
   }

   public void setHeight(float var1) {
      this.height = var1;
   }

   public float getPreviewScale() {
      return this.previewScale;
   }

   public void setPreviewScale(float var1) {
      this.previewScale = var1;
   }

   public float getPreviewY() {
      return this.previewY;
   }

   public void setPreviewY(float var1) {
      this.previewY = var1;
   }

   public JsonObject getAnimationJson() {
      return this.animationJson;
   }

   public void setAnimationJson(JsonObject var1) {
      this.animationJson = var1;
   }
}
