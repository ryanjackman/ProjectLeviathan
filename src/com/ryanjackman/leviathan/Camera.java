package com.ryanjackman.leviathan;

import org.newdawn.slick.geom.Vector2f;

public class Camera {

	private int width, height;
	private float x, y;
	private float scale = 1;
	private float scrollSpeed = 10;
	
	private World world;

	public Camera(World world) {
		
		this.world = world;
		
		this.width = Leviathan.WIDTH;
		this.height = Leviathan.HEIGHT;
		this.x = 0;
		this.y = 0;
	}

	public void scroll(Vector2f vector) {
		if (vector != null) {
			x = ( x +  vector.x * scrollSpeed);
			y = ( y +  vector.y * scrollSpeed);
			if(x > 0) x = 0;
			if(y > 0) y = 0;
			if(-x > world.mapWidth - Leviathan.WIDTH) x = -(world.mapWidth - Leviathan.WIDTH);
			if(-y > world.mapHeight - Leviathan.HEIGHT) y = -(world.mapHeight - Leviathan.HEIGHT);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
