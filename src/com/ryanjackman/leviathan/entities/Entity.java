package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.Images;

public abstract class Entity {

	public int x, y;
	
	public int tileX, tileY;
	public World world;
	
	public static int COST_MONEY = 0;
	public static int COST_ENERGY = 0;
	public static int COST_RESOURCE = 0;

	Entity(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
		
		tileX = x / world.tileSize;
		tileY = y / world.tileSize;

	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(Images.houseImage, x + world.camera.getX(), y + world.camera.getY());
	}

}
