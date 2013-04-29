package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Images;

public class Warehouse extends Entity {
	
	public static int ID = 2;

	private float lastTime = 0;
	private int updatePeriod = 1;

	public Warehouse(World world, int x, int y) {
		// money, energy, resource
		super(world, Images.twoXOne, x, y, 0, 500, 0);
		
		buildTime = 10;
		
		infoBox = new EntityInfoBox(this);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		
		super.update(gc, delta);
		
		if (completed) {
			if (world.gameTimer.getTime() - lastTime > updatePeriod) {
				world.player.addResource(10);
				lastTime = world.gameTimer.getTime();
			}
		}
	}

}
