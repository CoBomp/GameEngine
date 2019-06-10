package fr.patapole.main;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private long window;
	
	public int width = 720;
	public int height = 480;
	
	private boolean fullscreen = false;
	private Input input;
	
	private String title = "Window ";
	
	public static void setCallbacks() {
		glfwSetErrorCallback(new GLFWErrorCallback() {
			
			@Override
			public void invoke(int error, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}
		});
	}
	
	public Window() {
		setFullscreen(false);
	}
	
	public void createWindow() {
		window = glfwCreateWindow(
				width,
				height,
				title, 
				fullscreen ? glfwGetPrimaryMonitor() : 0, 
				0);
		
		if(window == 0)
			throw new IllegalStateException("Failed to create window !");
		if(!fullscreen) {
			GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (videoMode.width()-width)/2, (videoMode.height()-height)/2);
			glfwShowWindow(window);
		}

		glfwMakeContextCurrent(window);
		
		input = new Input(window);
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window) != false;
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void show() {
		if(window != 0) {
			glfwShowWindow(window);
		}
	}
	
	public void hide() {
		if(window != 0) {
			glfwHideWindow(window);
		}
	}
	
	public long getWindow() {
		return window;
	}
	
	public void setTitle(String title) {
		this.title = title;
		if(window != 0) {
			glfwSetWindowTitle(window, title);
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void resize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}
	
	public Input getInput() {
		return input;
	}
	
	public void update() {
		input.update();
		glfwPollEvents();
	}
}
