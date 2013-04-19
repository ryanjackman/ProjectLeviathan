package com.ryanjackman.leviathan.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.entities.Entity;

public class EntityInfoBox {
	
	int width = 75, height = 100;
	
	Entity e;
	
	public EntityInfoBox(Entity e){
		this.e = e;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(new Color(0,0,0,150));
		g.fillRoundRect(e.x - width, e.y - height, (float)width, (float)height, 3);
		Fonts.basicFont.drawString(e.x - width + 10, e.y - height + 10, e.getClass().getName() );
		Fonts.basicFont.drawString(e.x - width + 10, e.y - height + 30, e.tileX + " " + e.tileY );
	}
}
