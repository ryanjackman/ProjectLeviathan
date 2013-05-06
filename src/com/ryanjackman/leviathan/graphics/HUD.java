package com.ryanjackman.leviathan.graphics;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.Leviathan;
import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.entities.House;
import com.ryanjackman.leviathan.entities.Shipyard;
import com.ryanjackman.leviathan.entities.Warehouse;

public class HUD {
	
	private World world;
	
	private ArrayList<Button> buttons;
	
	Button houseButton;
	Button warehouseButton;
	Button shipyardButton;
	
	public HUD(World world){
		this.world = world;
		buttons = new ArrayList<Button>();
		
		houseButton = new Button( world, Leviathan.app, Images.oneXOne, House.ID,  20, Leviathan.HEIGHT - 50);
		warehouseButton = new Button( world, Leviathan.app, Images.twoXOne, Warehouse.ID,  80, Leviathan.HEIGHT - 50);
		shipyardButton = new Button( world, Leviathan.app, Images.shipyard, Shipyard.ID,  180, Leviathan.HEIGHT - 70);
		buttons.add(houseButton);
		buttons.add(warehouseButton);
		buttons.add(shipyardButton);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		for(Button b : buttons)
			b.update(gc, delta);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, Leviathan.HEIGHT - 60, Leviathan.WIDTH, 60);
		g.fillRect(0, 0, Leviathan.WIDTH, 40);
		
		Fonts.basicFont.drawString(Leviathan.WIDTH - 150, 10, "ENERGY: " +world.player.getEnergy(), Color.white);
		Fonts.basicFont.drawString(Leviathan.WIDTH - 300, 10, "MONEY: " + world.player.getMoney(), Color.white);
		Fonts.basicFont.drawString(Leviathan.WIDTH - 450, 10, "RESOURCE: " + world.player.getResource(), Color.white);
		
		for(Button m : buttons)
			m.render(gc, g);
	}

}
