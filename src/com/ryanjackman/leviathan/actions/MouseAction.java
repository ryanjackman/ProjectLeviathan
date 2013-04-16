package com.ryanjackman.leviathan.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Fonts;

public class MouseAction extends Action {
	
	EntityInfoBox infoBox = null;

	public MouseAction(World world) {
		super(world);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(world.places[input.getMouseX() / world.tileSize][input.getMouseY() / world.tileSize] == 1){
			infoBox = new EntityInfoBox(world.entityAtTile(input.getMouseX() / world.tileSize, input.getMouseY() / world.tileSize));
		}else
			infoBox = null;
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		Input input = gc.getInput();
		Fonts.basicFont.drawString(input.getMouseX(), input.getMouseY(),
				"MOUSE");
		if(infoBox != null)
			infoBox.render(gc, gc.getGraphics());
	}

}
