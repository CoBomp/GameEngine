package entity;

import static org.lwjgl.glfw.GLFW.*;
import org.joml.Vector3f;

import fr.patapole.main.Component;
import fr.patapole.main.Window;
import render.Animation;
import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.Tile;
import world.World;

public class Player {
	
	private Model model;
	//private Texture texture;
	private Animation texture;
	private Transform transform;
	
	public Player() {
		
		float[] vertices = new float[]{
				-1f, 1f, 0, 
				1f, 1f, 0,
				1f, -1f, 0,
				-1f, -1f, 0,
		};
		
		float[] texture = new float[]{0, 0, 1, 0, 1, 1, 0, 1,};
		
		int[] indices = new int[]{0, 1, 2, 2, 3, 0};
		
		model = new Model(vertices, texture, indices);
	
		this.texture = new Animation(5, 16, "an");
		
		transform = new Transform();
		transform.scale = new Vector3f(16, 16, 1);
	
	}
	
	public void update(float delta, Window window, Camera camera, World world) {

		float newX = 0;
		float newY = 0;
		boolean canMove = true;
		
		if(window.getInput().isKeyDown(GLFW_KEY_Q)) {
			//transform.pos.add(new Vector3f(-10*delta, 0, 0));
			newX -= 10*delta;
		}
		if(window.getInput().isKeyDown(GLFW_KEY_D)) {
			//transform.pos.add(new Vector3f(10*delta, 0, 0));
			newX += 10*delta;
		}
		if(window.getInput().isKeyDown(GLFW_KEY_Z)) {
			//transform.pos.add(new Vector3f(0, 10*delta, 0));
			newY += 10*delta;
		}
		if(window.getInput().isKeyDown(GLFW_KEY_S)) {
			//transform.pos.add(new Vector3f(0, -10*delta, 0));
			newY -= 10*delta;
		}
		
		Double px = (double) (transform.pos.x + newX);
		px /= 2;
		Double py = (double) (transform.pos.y + newY);
		py /= 2;
		System.out.println(transform.pos.x+" : "+transform.pos.y);
		
		Tile tile = Component.world.getTile((int)Math.abs(px.intValue()), (int)Math.abs(py.intValue()));
		
		if(tile != null) {
			System.out.println(tile.getTexture());
			if(tile.getTexture().equalsIgnoreCase("checker")) {
				canMove = false;
			}
		}else System.out.println("null");
		
		if(canMove) {
			transform.pos.add(new Vector3f(newX, newY, 0));
			camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
		}
		
	}
	
	public void render(Shader shader, Camera camera) {
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(camera.getProjection()));
		texture.bind(0);
		model.render();
	}

}
