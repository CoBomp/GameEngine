package collision;

import org.joml.Vector2f;

public class AABB {
	private Vector2f center, half_extent;
	
	public AABB(Vector2f center, Vector2f half_extent) {
		this.center = center;
		this.half_extent = half_extent;
	}
	
	public boolean isIntersecting(AABB box2) {
		Vector2f distance = box2.center.sub(center);
		distance.x = (float)Math.abs(distance.x);
		distance.y = (float)Math.abs(distance.y);
		
		distance.sub(half_extent.add(box2.half_extent));
		
		return (distance.x < 0 && distance.y < 0);
		
	}
	
	public Vector2f getCenter() {
		return center;
	}
	public Vector2f getHalfExtent() {
		return half_extent;
	}
	

}
