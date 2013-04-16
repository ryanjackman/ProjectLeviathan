package com.ryanjackman.leviathan.graphics;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.Leviathan;
import com.ryanjackman.leviathan.World;

public class HUD {
	
	private World world;
	
	private ArrayList<Button> buttons;
	
	Button houseButton;
	
	public HUD(World world){
		this.world = world;
		buttons = new ArrayList<Button>();
		
		try {
			houseButton = new Button(world, Leviathan.app, new Image("res/entity.png"), 20, Leviathan.HEIGHT - 50);
			buttons.add(houseButton);
		} catch (SlickException e) {
			e.printStackTrace();
		}
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
