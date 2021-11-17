package life.gui;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryStack.stackPush;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

public class Window {

	private long lwjglWindowHandle;

	public Window(long lwjglWindowHandle) {
		this.lwjglWindowHandle = lwjglWindowHandle;
	}

	public void close() {
		glfwSetWindowShouldClose(lwjglWindowHandle, true); // We will detect this in the rendering loop
	}
	
	public void attachKeyCallback(KeyCallback callback) {
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(lwjglWindowHandle, (window, key, scancode, action, mods) -> {
			callback.execute(key, scancode, action, mods);
		});
	}

	public void center() {
		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(lwjglWindowHandle, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the lwjglWindowHandle
			glfwSetWindowPos(
				lwjglWindowHandle,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically
	}

	public void show() {
		// Make the OpenGL context current
		glfwMakeContextCurrent(lwjglWindowHandle);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the lwjglWindowHandle visible
		glfwShowWindow(lwjglWindowHandle);
	}

	public void destroy() {
		// Free the lwjglWindowHandle callbacks and destroy the lwjglWindowHandle
		glfwFreeCallbacks(lwjglWindowHandle);
		glfwDestroyWindow(lwjglWindowHandle);
	}

	public boolean closed() {
		return glfwWindowShouldClose(lwjglWindowHandle);
	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		glfwSwapBuffers(lwjglWindowHandle); // swap the color buffers

		// Poll for lwjglWindowHandle events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
	}
}
