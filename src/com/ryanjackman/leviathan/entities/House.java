package com.ryanjackman.leviathan.entities;

import org.lwjgl.util.Timer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;

public class House extends Entity{
	
	Timer energyTimer;

	public House(World world, int x, int y) {
		super(world, x, y);
		
		COST_MONEY = 0;
		COST_ENERGY = 10;
		COST_RESOURCE = 0;
		
		energyTimer = new Timer();
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		if(energyTimer.getTime() > 1){
			world.player.addEnergy(100);
			energyTimer.reset();
		}
	}
}
