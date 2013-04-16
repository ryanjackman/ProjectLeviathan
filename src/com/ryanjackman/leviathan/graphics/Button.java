package com.ryanjackman.leviathan.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.actions.PlaceEntityAction;

public class Button implements ComponentListener{
	
	MouseOverArea button;
	
	World world;
	
	public Button(World world, GameContainer gc, Image image, int x, int y){
		
		this.world = world;
		
		button = new MouseOverArea(gc, image, x, y, this);
		button.setMouseOverColor(Color.blue);
		button.setMouseDownColor(Color.red);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		world.action = new PlaceEntityAction(world);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		button.render(gc, g);
	}


}
