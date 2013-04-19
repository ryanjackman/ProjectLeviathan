package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.actions.MouseAction;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Images;

public abstract class Entity {

	public int x, y;
	public int height, width;
	
	public int tileX, tileY;
	public World world;
	
	public Image image;
	
	EntityInfoBox infoBox;
	public boolean mouseDown;
	
	public static int costMoney = 0;
	public static int costEnergy = 0;
	public static int costResource = 0;

	Entity(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
		
		tileX = x / world.tileSize;
		tileY = y / world.tileSize;
		
		image = Images.twoXOne;
		height = image.getHeight();
		width = image.getWidth();
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input i = gc.getInput();
		
		mouseDown = false;		
		if(i.getMouseX() - world.camera.getX() > x && i.getMouseX() - world.camera.getX() < x + width){
			if(i.getMouseY() - world.camera.getY() > y && i.getMouseY() - world.camera.getY() < y + height){
					mouseDown = true;
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		Input i = gc.getInput();
		
		g.drawImage(image, x + world.camera.getX(), y + world.camera.getY());
		
		if(mouseDown){
			if(world.action instanceof MouseAction)
				//infoBox = new EntityInfoBox(world.entityAtTile(i.getMouseX() / world.tileSize, i.getMouseY() / world.tileSize));
					
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
