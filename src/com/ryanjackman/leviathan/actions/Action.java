package com.ryanjackman.leviathan.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;

public abstract class Action {

	public World world;

	public boolean completed = false;

	public Action(World world) {
		this.world = world;
	}

	public void update(GameContainer gc, int delta) throws SlickException {
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
	}
}
