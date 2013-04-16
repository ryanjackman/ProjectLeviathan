package com.ryanjackman.leviathan.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.Fonts;

public class Action {

	public World world;

	public boolean completed = false;

	public Action(World world) {
		this.world = world;
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		completed = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
			
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		Input input = gc.getInput();
		Fonts.basicFont.drawString(input.getMouseX(), input.getMouseY(),
				"ACTION");
	}
}
