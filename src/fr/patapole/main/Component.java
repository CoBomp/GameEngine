package fr.patapole.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.Tile;
import world.TileRenderer;


public class Component {
	
	public static int scale = 3;
	public static int width = 720 / scale;
	public static int height = 480 / scale;
	
	public long mainWindow;
	
	boolean running = false;
	public Component() {
		Window.setCallbacks();
		
		if(!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW !");
		}
		
		Window win = new Window();
		win.createWindow();
		
		GL.createCapabilities();
		
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tiles = new TileRenderer();		
//		float[] vertices = new float[] {
//				-0.5f, 0.5f, 0,
//				0.5f, 0.5f, 0,
//				0.5f, -0.5f, 0,
//				-0.5f, -0.5f, 0,
//		};
//		
//		float[] texture = new float[] {
//				0,0,
//				1,0,
//				1,1,
//				0,1,
//		};
//		
//		int[] indices = new int[] {
//				0, 1, 2, 
//				2, 3, 0
//		};
//		
//		Model model = new Model(vertices, texture, indices);
//		
//		Texture tex = new Texture("test.png");
		
		Shader shader = new Shader("shader");
		Matrix4f scale = new Matrix4f().translate(new Vector3f(0, 0, 0)).scale(16);
		
		Matrix4f target = new Matrix4f();
		
		camera.setPosition(new Vector3f(-100, 0, 0));
		
		double frame_cap = 1.0/120.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		while(!win.shouldClose()) {
			boolean can_render = false;
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed+=passed;
			frame_time+=passed;
			
			time = time_2;
			
			while(unprocessed >= frame_cap) {
				unprocessed-=frame_cap;
				can_render = true;
				
				target = scale;
				
				if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(win.getWindow(), true);
				}
				
				win.update();
				if(frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS : "+frames);
					frames = 0;
				}
			}	
			
			if(can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				
				for(int i = 0; i< 8; i++) {
					//tiles.renderTile(Tile.test, i, 0, shader, scale, camera);
					tiles.renderTile((byte)0, i, 0, shader, scale, camera);
				}
				
				win.swapBuffers();
				frames++;
			}
		}
		
		glfwTerminate();
		
	}

	

}
