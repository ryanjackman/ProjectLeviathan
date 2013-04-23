package com.ryanjackman.leviathan.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.graphics.EntityInfoBox;
import com.ryanjackman.leviathan.graphics.Images;

public class House extends Entity {

	private float lastTime = 0;
	private int updatePeriod = 1;

	public House(World world, int x, int y) {
		// money, energy, resource
		super(world, Images.oneXOne, x, y, 0, 50, 200);

		ID = 1;

		buildTime = 5;

		infoBox = new EntityInfoBox(this);
	}

	public void update(GameContainer gc, int delta) throws SlickException {

		super.update(gc, delta);

		if (completed) {
			if (world.gameTimer.getTime() - lastTime > updatePeriod) {
				world.player.addEnergy(10);
				lastTime = world.gameTimer.getTime();
			}
		}

	}
}