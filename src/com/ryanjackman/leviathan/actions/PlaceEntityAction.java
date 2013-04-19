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

				e = initEntity(world, id, ex * world.tileSize, ey
						* world.tileSize);
				System.out.println(id);

				if (world.player.haveFunds(e.getCostEnergy(), e.getCostMoney(),
						e.getCostResource())) {
					if (world.places[ex][ey] == 0) {
						world.entities.add(e);
						world.player.addEnergy(-e.getCostEnergy());
						world.player.addMoney(-e.getCostMoney());
						world.player.addResource(-e.getCostResource());
						world.places[ex][ey] = 1;
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
		default:
			return new House(world, x, y);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		Input input = gc.getInput();

		g.setColor(new Color(0, 0, 0, 150));
		g.drawImage(e.image,
				input.getMouseX() - world.tileSize / 2,
				input.getMouseY() - world.tileSize / 2);
	}

}
