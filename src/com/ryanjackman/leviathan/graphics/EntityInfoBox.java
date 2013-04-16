package com.ryanjackman.leviathan.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.entities.Entity;

public class EntityInfoBox {
	
	int width = 75, height = 100;
	
	Entity e;
	
	public EntityInfoBox(Entity e){
		this.e = e;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		Input input = gc.getInput();
		
		g.setColor(new Color(0,0,0,150));
		g.fillRoundRect(input.getMouseX() - width, input.getMouseY() - height, (float)width, (float)height, 5);
		Fonts.basicFont.drawString(input.getMouseX() - width + 10, input.getMouseY() - height + 10, e.tileX + " " + e.tileY );
	}
}
