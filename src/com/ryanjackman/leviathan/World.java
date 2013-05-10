package com.ryanjackman.leviathan;

import java.util.ArrayList;

import org.lwjgl.util.Timer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.ryanjackman.leviathan.actions.Action;
import com.ryanjackman.leviathan.actions.MouseAction;
import com.ryanjackman.leviathan.actions.PlaceEntityAction;
import com.ryanjackman.leviathan.entities.Entity;
import com.ryanjackman.leviathan.entities.House;
import com.ryanjackman.leviathan.entities.Shipyard;
import com.ryanjackman.leviathan.entities.Warehouse;
import com.ryanjackman.leviathan.graphics.HUD;
import com.ryanjackman.leviathan.graphics.Images;
import com.ryanjackman.leviathan.units.Unit;

public class World {

	public Leviathan game;
	public HUD hud;

	public Timer gameTimer;

	public Player player;
	public int[][] places;
	public ArrayList<Entity> entities;
	public ArrayList<Unit> units;
	
	public Vector2D target;

	public Action action;

	public TiledMap tilemap;
	public int mapHeight;
	public int mapWidth;
	public int tileSize;

	public Camera camera;

	World(Leviathan game) {
		this.game = game;
		gameTimer = new Timer();
		Images.init();
		player = new Player();
		hud = new HUD(this);

		camera = new Camera(this);

		action = new MouseAction(this);

		try {
			tilemap = new TiledMap("res/watermap.tmx");

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

		units = new ArrayList<Unit>();
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		
		hud.update(gc, delta);

		if (action != null) {
			action.update(gc, delta);
			if (action.completed)
				action = new MouseAction(this);
		}

		for (Entity e : entities)
			e.update(gc, delta);
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && action instanceof MouseAction) {
			target = new Vector2D(input.getMouseX() - camera.getX(), input.getMouseY() - camera.getY());
			System.out.println("Target set at " + target.x + ", " + target.y);
			for (Unit u : units)
				u.moving = true;
		}
		
		for (Unit u : units)
			u.update(gc, delta);

		for (int i = 0; i < units.size(); i++) {
			for (int j = i + 1; j < units.size(); j++) {
				if (units.get(i).colliding(units.get(j))) {
					units.get(i).resolveCollision(units.get(j));
				}
			}
		}
		
		int mx = input.getMouseX();
		int my = input.getMouseY();

		Vector2f vector = new Vector2f(0, 0);

		if (input.isKeyDown(Input.KEY_W)) {
			vector.y += 1;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			vector.y -= 1;
		}
		if (input.isKeyDown(Input.KEY_A)) {
			vector.x += 1;
		}
		if (input.isKeyDown(Input.KEY_D)) {
			vector.x -= 1;
		}

		camera.scroll(vector.normalise());

		if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			float a = (float) Math.toDegrees(Math.atan2(mx - Leviathan.WIDTH / 2, my - Leviathan.HEIGHT / 2));
			camera.scroll(new Vector2f(-a + 270));
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			if (action instanceof MouseAction)
				System.exit(1);
			else
				action = new MouseAction(this);
		}

		if (input.isKeyPressed(Input.KEY_1))
			action = new PlaceEntityAction(this, House.ID);
		if (input.isKeyPressed(Input.KEY_2))
			action = new PlaceEntityAction(this, Warehouse.ID);
		if (input.isKeyPressed(Input.KEY_3))
			action = new PlaceEntityAction(this, Shipyard.ID);
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
			tilemap.render(xRenderOffset, yRenderOffset, firstTileX, firstTileY, lastTileX, lastTileY);
		}

		for (Unit u : units)
			u.render(gc, g);

		for (Entity e : entities)
			e.render(gc, g);

		hud.render(gc, g);

		if (action != null)
			action.render(gc, g);
	}

	public Entity entityAtTile(int x, int y) {
		for (Entity e : entities) {
			if (e.tileX == x && e.tileY == y)
				return e;
		}
		return null;
	}
}
