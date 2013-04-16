package com.ryanjackman.leviathan;

import org.lwjgl.util.Timer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Leviathan extends BasicGame {

	private static boolean showFPS = true;
	private static boolean fullscreen = false;
	
	public static AppGameContainer app;

	public static int WIDTH = 1024, HEIGHT = 768;

	private World world;

	public Leviathan() {
		super("Project Leviathan");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		world = new World(this);		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Timer.tick();
		world.update(gc, delta);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		world.render(gc, g);
	}

	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new Leviathan());

		if (fullscreen) {
			WIDTH = app.getScreenWidth();
			HEIGHT = app.getScreenHeight();
		}

		app.setDisplayMode(WIDTH, HEIGHT, fullscreen);

		app.setVSync(true);
		app.setTargetFrameRate(60);
		app.setShowFPS(showFPS);
		app.start();
	}
}