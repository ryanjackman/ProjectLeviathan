package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Images;

public class Warehouse extends Entity {
	
	public static int ID = 2;
	
	static int costMoney = 0;
	static int costEnergy = 500;
	static int costResource = 0;

	private float lastTime = 0;
	private int updatePeriod = 1;

	public Warehouse(World world, int x, int y) {
		// money, energy, resource
		super(world, Images.twoXOne, x, y);
		
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
	
	public static String[] getInfo(){
		String[] info = new String[5];
		info[0] = "Warehouse";
		info[1] = "E: " + Warehouse.costEnergy;
		info[2] = "M: " + Warehouse.costMoney;
		info[3] = "R: " + Warehouse.costResource;
		info[4] = "Build Time: " + Warehouse.buildTime;
		
		return info;
	}

}
