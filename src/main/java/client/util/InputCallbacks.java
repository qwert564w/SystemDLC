package client.util;

import client.data.Rotation;
import client.data.SlotSelection;
import client.module.Feature;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class InputCallbacks {
   private boolean flag;
   private boolean flag2;
   private GLFWScrollCallback gLFWScrollCallback;
   private GLFWMouseButtonCallback gLFWMouseButtonCallback;

   public void setModuleDispatcher(ModuleDispatcher moduleDispatcher) {
      if (Feature.mc != null && Feature.mc.getWindow() != null) {
         long i = Feature.mc.getWindow().getHandle();
         if (i != 0L) {
            if (!this.flag) {
               this.gLFWScrollCallback = GLFW.glfwSetScrollCallback(i, null);
               GLFWScrollCallback glfwscrollcallback = GLFWScrollCallback.create((item, item2, item3) -> {
                  Rotation rotation = moduleDispatcher.getRotationByDoubleDouble(item2, item3);
                  if (!rotation.isFlag() && this.gLFWScrollCallback != null) {
                     this.gLFWScrollCallback.invoke(item, item2, item3);
                  }
               });
               GLFW.glfwSetScrollCallback(i, glfwscrollcallback);
               this.flag = true;
            }

            if (!this.flag2) {
               this.gLFWMouseButtonCallback = GLFW.glfwSetMouseButtonCallback(i, null);
               GLFWMouseButtonCallback glfwmousebuttoncallback = GLFWMouseButtonCallback.create((item, item2, item4, item3) -> {
                  SlotSelection slotselection = moduleDispatcher.getSlotSelectionByIntIntInt(item2, item4, item3);
                  if (!slotselection.isFlag() && this.gLFWMouseButtonCallback != null) {
                     this.gLFWMouseButtonCallback.invoke(item, item2, item4, item3);
                  }
               });
               GLFW.glfwSetMouseButtonCallback(i, glfwmousebuttoncallback);
               this.flag2 = true;
            }
         }
      }
   }
}
