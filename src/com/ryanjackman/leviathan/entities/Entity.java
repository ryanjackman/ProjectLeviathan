package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.actions.MouseAction;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;

public abstract class Entity {

	public int x, y;
	public int height, width;
	
	public static int ID;

	public int tileX, tileY;
	public int tileHeight, tileWidth;
	public World world;

	public Image image;

	EntityInfoBox infoBox;
	public boolean mouseDown;

	protected int costMoney;
	protected int costEnergy;
	protected int costResource;

	/**
	 * @param x = x position relative to world
	 * @param y = y position relative to world
	 * @param iD = ID of 
	 * @param world
	 * @param image
	 * @param costMoney
	 * @param costEnergy
	 * @param costResource
	 */
	Entity(World world, Image i, int x, int y, int m, int e, int r) {
		this.world = world;
		this.x = x;
		this.y = y;
		
		this.costMoney = m;
		this.costEnergy = e;
		this.costResource = r;

		tileX = x / world.tileSize;
		tileY = y / world.tileSize;

		image = i;
		
		height = image.getHeight();
		width = image.getWidth();
		
		tileHeight = height / world.tileSize;
		tileWidth = width / world.tileSize;
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input i = gc.getInput();

		mouseDown = false;
		if (i.getMouseX() - world.camera.getX() > x
				&& i.getMouseX() - world.camera.getX() < x + width) {
			if (i.getMouseY() - world.camera.getY() > y
					&& i.getMouseY() - world.camera.getY() < y + height) {
				mouseDown = true;
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		g.drawImage(image, x + world.camera.getX(), y + world.camera.getY());

		if (mouseDown) {
			if (world.action instanceof MouseAction)
				infoBox.render(gc, gc.getGraphics());
		}
	}

	public int getCostMoney() {
		return costMoney;
	}

	public int getCostEnergy() {
		return costEnergy;
	}

	public int getCostResource() {
		return costResource;
	}
}
