package com.ryanjackman.leviathan.units;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.ryanjackman.leviathan.Vector2D;
import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.actions.MouseAction;

public class UnitGroup {

	public ArrayList<Unit> units;

	public Vector2D target;

	private World world;

	public boolean selected = false;

	public UnitGroup(World world) {
		this.world = world;
		units = new ArrayList<Unit>();

	}

	public void update(GameContainer gc, int delta) {

		Input input = gc.getInput();
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			boolean s = false;
			for (Unit u : units)
				if (u.containsPoint(input.getMouseX() - world.camera.getX(), input.getMouseY() - world.camera.getY()))
					s = true;

			if (s)
				selectAll();
			else
				deselectAll();
		}
		
		if (selected)
			if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && world.action instanceof MouseAction)
				target = new Vector2D(input.getMouseX() - world.camera.getX(), input.getMouseY() - world.camera.getY());

		for (Unit u : units)
			u.update(gc, delta);
	}

	public void render(GameContainer gc, Graphics g) {
		for (Unit u : units)
			u.render(gc, g);
	}

	public void selectAll() {
		for (Unit u : units)
			u.selected = true;
		selected = true;
	}

	public void deselectAll() {
		for (Unit u : units)
			u.selected = false;
		selected = false;
	}
}
