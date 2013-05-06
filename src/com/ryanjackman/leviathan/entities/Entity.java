package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.Color;
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

	public int tileX, tileY;
	public int tileHeight, tileWidth;
	public World world;

	public Image image;

	EntityInfoBox infoBox;
	public boolean mouseDown;

	public static int costMoney;
	public static int costEnergy;
	public static int costResource;

	protected float startTime;
	static int buildTime;
	public boolean completed = false;

	/**
	 * @param x
	 * @param y
	 * @param world
	 * @param image
	 */
	Entity(World world, Image i, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;

		tileX = x / world.tileSize;
		tileY = y / world.tileSize;

		image = i;

		height = image.getHeight();
		width = image.getWidth();

		tileHeight = height / world.tileSize;
		tileWidth = width / world.tileSize;

		startTime = world.gameTimer.getTime();
		buildTime = 5;
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input i = gc.getInput();

		if (!completed)
			if (world.gameTimer.getTime() - startTime > buildTime)
				completed = true;

		mouseDown = false;
		if (i.getMouseX() - world.camera.getX() > x && i.getMouseX() - world.camera.getX() < x + width) {
			if (i.getMouseY() - world.camera.getY() > y && i.getMouseY() - world.camera.getY() < y + height) {
				mouseDown = true;
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		g.drawImage(image, x + world.camera.getX(), y + world.camera.getY());
		if (!completed) {
			g.setColor(Color.black);
			g.fillRect(x + world.camera.getX(), y + world.camera.getY() + height - 5, width, 5);
			g.setColor(Color.red);
			g.fillRect(x + world.camera.getX(), y + world.camera.getY() + height - 5, (world.gameTimer.getTime() - startTime) / buildTime * width, 5);
		}

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

	public boolean canPlace(int ex, int ey) {
		return true;
	}
}
