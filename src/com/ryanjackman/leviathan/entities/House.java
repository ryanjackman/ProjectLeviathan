package com.ryanjackman.leviathan.entities;

import org.lwjgl.util.Timer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Images;

public class House extends Entity{
	
	Timer energyTimer;
	
	public static int ID = 1;
	
	public House(World world, int x, int y) {
		super(world, x, y);
		
		costMoney = 0;
		costEnergy = 10;
		costResource = 0;
		
		ID = 1;

		image = Images.oneXOne;
		
		energyTimer = new Timer();
		
		infoBox = new EntityInfoBox(this);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		
		super.update(gc, delta);
		
		if(energyTimer.getTime() > 1){
			world.player.addEnergy(10);
			energyTimer.reset();
		}
	}
}