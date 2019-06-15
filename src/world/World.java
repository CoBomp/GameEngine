package world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import fr.patapole.main.Window;
import render.Camera;
import render.Shader;

public class World {
	
	private final int view = 24;
	private byte[] tiles;
	private int width;
	private int height;
	private int scale;
	
	private Matrix4f world;
	
	public World() {
		width = 64;
		height = 64 ;
		scale = 16;
		
		tiles = new byte[width * height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale);
	}

	public void render(TileRenderer render, Shader shader, Camera camera, Window window) {
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				render.renderTile(tiles[j + i*width], j, -i, shader, world, camera);
//			}
//		}
		
		int posX = ((int)camera.getPosition().x + (window.getWidth()/2)) / (scale * 2);
		int posY = ((int)camera.getPosition().y - (window.getHeight()/2)) / (scale * 2);
		
		for (int i = 0; i < view; i++) {
			for (int j = 0; j < view; j++) {
				Tile t = getTile(i-posX, j+posY);
				if(t != null) {
					render.renderTile(t, i-posX, -j-posY, shader, world, camera);
				}
			}
		}
	}
	
	public void correctCamera(Camera cam, Window win) {
		Vector3f pos = cam.getPosition();
		
		int w = -width * scale * 2;
		int h = height * scale * 2;
		
		if(pos.x > -(win.getWidth()/2)+scale)
			pos.x = -(win.getWidth()/2)+scale;
		
		if(pos.x < w + (win.getWidth()/2) + scale)
			pos.x = w + (win.getWidth()/2) + scale;
		
		if(pos.y < (win.getHeight()/2)-scale)
			pos.y = (win.getHeight()/2)-scale;
		
		if(pos.y > h - (win.getHeight()/2)-scale)
			pos.y = h - (win.getHeight()/2)-scale;
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x + y*width] = tile.getId();
	}
	
	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		}catch(ArrayIndexOutOfBoundsException aiooe) {
			return null;
		}
	}
}
