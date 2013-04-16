package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;

public abstract class Entity {

	private int x, y;
	private Image image;
	public World world;
	
	public static int COST_MONEY = 0;
	public static int COST_ENERGY = 0;
	public static int COST_RESOURCE = 0;

	Entity(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
		
		try {
			image = new Image("res/entity.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(image, x + world.camera.getX(), y + world.camera.getY());
	}

}
