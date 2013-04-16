package com.ryanjackman.leviathan;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class HUD {
	
	private World world;
	
	HUD(World world){
		this.world = world;
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		Fonts.basicFont.drawString(10, 60, "ENERGY: " +world.player.getEnergy(), Color.black);
		Fonts.basicFont.drawString(10, 80, "MONEY: " + world.player.getMoney(), Color.black);
		Fonts.basicFont.drawString(10, 100, "RESOURCE: " + world.player.getResource(), Color.black);
	}

}
