package com.ryanjackman.leviathan;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.ryanjackman.leviathan.entities.Entity;
import com.ryanjackman.leviathan.entities.House;

public class World {

	public Leviathan game;
	public HUD hud;

	public Player player;
	public int[][] places;
	public ArrayList<Entity> entities;

	protected int mapHeight, mapWidth;

	private TiledMap tilemap;
	protected int tileSize;

	public Camera camera;

	// private int scrollMargin = 50;

	World(Leviathan game) {
		this.game = game;

		player = new Player();
		hud = new HUD(this);

		camera = new Camera(this);

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
		for(int i = 0; i < places.length; i++){
			for(int j = 0; j < places[i].length; j++){
				places[i][j] = 0;
			}
		}
	}

	public void update(GameContainer gc, int delta) throws SlickException {

		hud.update(gc, delta);

		for(Entity e : entities)
			e.update(gc, delta);

		Input input = gc.getInput();

		int mx = input.getMouseX();
		int my = input.getMouseY();

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			int ex = (mx - (int) camera.getX()) / tileSize;
			int ey = (my - (int) camera.getY()) / tileSize;

			if (player.haveFunds(House.COST_ENERGY, House.COST_MONEY,
					House.COST_RESOURCE)) {
				// entities.add());
				if(places[ex][ey] == 0){
					entities.add(new House(this, ex * tileSize, ey * tileSize));
					player.addEnergy(-House.COST_ENERGY);
					player.addMoney(-House.COST_MONEY);
					player.addResource(-House.COST_RESOURCE);
					places[ex][ey] = 1;
				}
			}
		}

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

		/*
		 * if (mx < scrollMargin || my < scrollMargin || mx > Leviathan.WIDTH -
		 * scrollMargin || my > Leviathan.HEIGHT - scrollMargin) { float a =
		 * (float) Math.toDegrees(Math.atan2(mx - Leviathan.WIDTH / 2, my -
		 * Leviathan.HEIGHT / 2)); camera.scroll(new Vector2f(-a + 270)); }
		 */

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

		for(Entity e : entities)
			e.render(gc, g);

		hud.render(gc, g);
	}
}
