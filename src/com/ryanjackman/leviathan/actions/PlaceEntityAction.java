package com.ryanjackman.leviathan.actions;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.Leviathan;
import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.entities.House;
import com.ryanjackman.leviathan.graphics.Images;

public class PlaceEntityAction extends Action {

	public PlaceEntityAction(World world) {
		super(world);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		int mx = input.getMouseX();
		int my = input.getMouseY();

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (my > 40 && my < Leviathan.HEIGHT - 60) {

				int ex = (mx - (int) world.camera.getX()) / world.tileSize;
				int ey = (my - (int) world.camera.getY()) / world.tileSize;

				if (world.player.haveFunds(House.COST_ENERGY, House.COST_MONEY,
						House.COST_RESOURCE)) {
					if (world.places[ex][ey] == 0) {
						world.entities.add(new House(world,
								ex * world.tileSize, ey * world.tileSize));
						world.player.addEnergy(-House.COST_ENERGY);
						world.player.addMoney(-House.COST_MONEY);
						world.player.addResource(-House.COST_RESOURCE);
						world.places[ex][ey] = 1;
						completed = true;
					}
				}
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		Input input = gc.getInput();

		g.setColor(new Color(0, 0, 0, 150));
		g.drawImage(Images.houseImage, input.getMouseX() - Images.houseImage.getWidth() / 2, input.getMouseY() - Images.houseImage.getHeight() / 2);
	}

}
