package com.ryanjackman.leviathan.actions;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.Leviathan;
import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.entities.Entity;
import com.ryanjackman.leviathan.entities.House;
import com.ryanjackman.leviathan.entities.Shipyard;
import com.ryanjackman.leviathan.entities.Warehouse;

public class PlaceEntityAction extends Action {

	private Entity e;
	private int id;

	public PlaceEntityAction(World world, int id) {
		super(world);

		this.id = id;
		e = initEntity(world, id, 0, 0);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		int mx = input.getMouseX();
		int my = input.getMouseY();

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (my > 40 && my < Leviathan.HEIGHT - 60) {

				int ex = (mx - (int) world.camera.getX()) / world.tileSize;
				int ey = (my - (int) world.camera.getY()) / world.tileSize;

				e = initEntity(world, id, ex * world.tileSize, ey * world.tileSize);

				boolean empty = true;

				for (int i = 0; i < e.tileWidth; i++)
					for (int j = 0; j < e.tileHeight; j++)
						if (world.places[ex + i][ey + j] == 1)
							empty = false;

				boolean buildable = true;

				for (int i = 0; i < e.tileWidth; i++)
					for (int j = 0; j < e.tileHeight; j++)
						if (world.tilemap.getTileProperty(world.tilemap.getTileId(ex + i, ey + j, 0), "buildable", "false").equals("false"))
							buildable = false;

				if (world.player.haveFunds(e.getCostEnergy(), e.getCostMoney(), e.getCostResource())) {
					System.out.println("player has funds " + e.costEnergy + " " + e.costMoney + " " + e.costResource);
					if (empty && buildable && e.canPlace(ex, ey)) {
						world.entities.add(e);
						world.player.addEnergy(-e.getCostEnergy());
						world.player.addMoney(-e.getCostMoney());
						world.player.addResource(-e.getCostResource());

						for (int i = 0; i < e.tileWidth; i++)
							for (int j = 0; j < e.tileHeight; j++)
								world.places[ex + i][ey + j] = 1;

						completed = true;
					}
				}
			}
		}
	}

	private Entity initEntity(World world, int id, int x, int y) {
		switch (id) {
		case 1:
			return new House(world, x, y);
		case 2:
			return new Warehouse(world, x, y);
		case 3:
			return new Shipyard(world, x, y);
		default:
			return new House(world, x, y);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		Input input = gc.getInput();

		g.setColor(new Color(0, 0, 0, 150));
		g.drawImage(e.image, input.getMouseX() - world.tileSize / 2, input.getMouseY() - world.tileSize / 2);
	}

}
