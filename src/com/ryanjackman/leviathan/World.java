package com.ryanjackman.leviathan;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class World {

	Leviathan game;

	protected int height, width;

	protected int tileSize;

	private TiledMap tilemap;

	private Camera camera;
	private int scrollMargin = 50;

	World(Leviathan game) {
		this.game = game;

		camera = new Camera(this);

		try {
			tilemap = new TiledMap("res/tilemap.tmx");

			tileSize = tilemap.getTileWidth();
			height = tilemap.getHeight() * tileSize;
			width = tilemap.getWidth() * tileSize;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

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

		int mx = input.getMouseX();
		int my = input.getMouseY();

		if (mx < scrollMargin || my < scrollMargin
				|| mx > Leviathan.WIDTH - scrollMargin
				|| my > Leviathan.HEIGHT - scrollMargin) {
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
	}
}
