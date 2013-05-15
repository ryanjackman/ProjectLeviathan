package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Images;
import com.ryanjackman.leviathan.units.Unit;
import com.ryanjackman.leviathan.units.UnitGroup;

public class Shipyard extends Entity {

	public static int ID = 3;

	static int costMoney = 0;
	static int costEnergy = 1000;
	static int costResource = 1000;

	private float unitTime = 1;
	private float lastUnitPlaced = 0;
	
	private UnitGroup group;

	public Shipyard(World world, int x, int y) {
		super(world, Images.shipyard, x, y);
		
		group = new UnitGroup(world);
		world.groups.add(group);

		buildTime = 2;

		infoBox = new EntityInfoBox(this);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		if (completed) {
			if (world.gameTimer.getTime() > lastUnitPlaced + unitTime) {
				if (group.units.size() < 20) {
					if (world.player.haveFunds(10, 0, 10)) {
						group.units.add(new Unit(world, group, x + 100 + (int) (Math.random() * 20), y + 100 + (int) (Math.random() * 20)));
						world.player.addEnergy(-10);
						world.player.addResource(-10);
						lastUnitPlaced = world.gameTimer.getTime();
					}
				}
			}
		}
	}

	public static String[] getInfo() {
		String[] info = new String[5];
		info[0] = "Shipyard";
		info[1] = "E: " + Shipyard.costEnergy;
		info[2] = "M: " + Shipyard.costMoney;
		info[3] = "R: " + Shipyard.costResource;
		info[4] = "Build Time: " + Shipyard.buildTime;

		return info;
	}

}
