package world;

public class Tile {
	
	public static Tile tiles[] = new Tile[16];
	private static byte not = 0;
	
	public static Tile test = new Tile("grass");
	public static Tile checker = new Tile("checker");
	
	public int collision;
	private byte id;
	private String texture;
	
	public Tile(String texture) {
		this.id = not;
		not++;
		this.texture = texture;
		
		if(tiles[id] != null) {
			throw new IllegalStateException("Tiles at ["+id+"] is already being used!");
		}
		tiles[id] = this;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

}
