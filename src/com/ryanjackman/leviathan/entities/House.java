package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Images;

public class House extends Entity {
	
	public static int ID = 1;
	
	static int costMoney = 0;
	static int costEnergy = 50;
	static int costResource = 50;

	private float lastTime = 0;
	private int updatePeriod = 1;

	public House(World world, int x, int y) {
		// money, energy, resource
		super(world, Images.oneXOne, x, y);
		
		buildTime = 5;

		infoBox = new EntityInfoBox(this);
	}

	public void update(GameContainer gc, int delta) throws SlickException {

		super.update(gc, delta);

		if (completed) {
			if (world.gameTimer.getTime() - lastTime > updatePeriod) {
				world.player.addEnergy(10);
				lastTime = world.gameTimer.getTime();
			}
		}

	}
	
	public boolean canPlace(int x, int y){
		return world.tilemap.getTileProperty(world.tilemap.getTileId(x, y, 0), "resource", "false").equals("true");
	}
	
	public static String[] getInfo(){
		String[] info = new String[5];
		info[0] = "House";
		info[1] = "E: " + costEnergy;
		info[2] = "M: " + costMoney;
		info[3] = "R: " + costResource;
		info[4] = "Build Time: " + House.buildTime;
		
		return info;
	}
}