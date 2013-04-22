package com.ryanjackman.leviathan;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.ryanjackman.leviathan.actions.Action;
import com.ryanjackman.leviathan.actions.MouseAction;
import com.ryanjackman.leviathan.entities.Entity;
import com.ryanjackman.leviathan.graphics.HUD;
import com.ryanjackman.leviathan.graphics.Images;

public class World {

	public Leviathan game;
	public HUD hud;

	public Player player;
	public int[][] places;
	public ArrayList<Entity> entities;

	public Action action;

	protected int mapHeight, mapWidth;

	private TiledMap tilemap;
	public int tileSize;

	public Camera camera;

	World(Leviathan game) {
		this.game = game;
		Images.init();
		player = new Player();
		hud = new HUD(this);

		camera = new Camera(this);
		
		action = new MouseAction(this);
		
		

		try {
			tilemap = new TiledMap("res/tilemap.tmx");

			tileSize = tilemap.getTileWidth();
			mapHeight = tilemap.getHeight() * tileSize;
			mapWidth = tilemap.getWidth() * tileSize;
		} catch (SlickException e) {
			e.printStackTrace();
		}

		entities = new ArrayList<Entity>();
		places = new int[mapHeight][mapWidth];
		for (int i = 0; i < places.length; i++) {
			for (int j = 0; j < places[i].length; j++) {
				places[i][j] = 0;
			}
		}
	}

	public void update(GameContainer gc, int delta) throws SlickException {

		hud.update(gc, delta);

		if (action != null) {
			action.update(gc, delta);
			if (action.completed)
				action = new MouseAction(this);
		}

		for (Entity e : entities)
			e.update(gc, delta);

		Input input = gc.getInput();

		int mx = input.getMouseX();
		int my = input.getMouseY();

		Integer angle = null;

		if (input.isKeyDown(Input.KEY_W)) {
			angle = 90;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			angle = 270;
		}
		if (input.isKeyDown(Input.KEY_A)) {
			angle = 0;
		}
		if (input.isKeyDown(Input.KEY_D)) {
			angle = 180;
		}

		if (angle != null)
			camera.scroll(new Vector2f(angle));

		if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			float a = (float) Math.toDegrees(Math.atan2(mx - Leviathan.WIDTH
					/ 2, my - Leviathan.HEIGHT / 2));
			camera.scroll(new Vector2f(-a + 270));
		}

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(1);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		// Code borrowed from http://gamedev.stackexchange.com/a/18040

		// Render positions as integers
		int renderX = (int) camera.getX();
		int renderY = (int) camera.getY();

		// Render offset for map
		int xRenderOffset = renderX % tileSize;
		int yRenderOffset = renderY % tileSize;

		// Get the first tile to be rendered
		int firstTileX = (int) -renderX / tileSize;
		int firstTileY = (int) -renderY / tileSize;

		// Render tiles only if their indices are greater than 0
		if (firstTileX < 0) {
			xRenderOffset += Math.abs(firstTileX) * tileSize;
			firstTileX = 0;
		}

		if (firstTileY < 0) {
			yRenderOffset += Math.abs(firstTileY) * tileSize;
			firstTileY = 0;
		}

		// Get the last tile to be rendered
		int lastTileX = firstTileX + (Leviathan.WIDTH / tileSize) + 1;
		int lastTileY = firstTileY + (Leviathan.HEIGHT / tileSize) + 2;

		int mapSizeX = tilemap.getWidth();
		int mapSizeY = tilemap.getHeight();

		// Verify that the last tile is valid
		if (lastTileX > mapSizeX - 1) {
			lastTileX = mapSizeX - 1;
		}

		if (lastTileY > mapSizeY - 1) {
			lastTileY = mapSizeY - 1;
		}

		// Check if the map will be visible on the screen
		if (lastTileX >= 0 && lastTileY >= 0) {
			tilemap.render(xRenderOffset, yRenderOffset, firstTileX,
					firstTileY, lastTileX, lastTileY);
		}

		for (Entity e : entities)
			e.render(gc, g);

		hud.render(gc, g);

		if (action != null)
			action.render(gc, g);
	}
	
	public Entity entityAtTile(int x, int y){
		for(Entity e : entities){
			if( e.tileX == x && e.tileY == y)
				return e;
		}
		return null;
	}
}
