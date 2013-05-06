package com.ryanjackman.leviathan.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

import com.ryanjackman.leviathan.Leviathan;
import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.actions.PlaceEntityAction;
import com.ryanjackman.leviathan.entities.House;
import com.ryanjackman.leviathan.entities.Shipyard;
import com.ryanjackman.leviathan.entities.Warehouse;

public class Button implements ComponentListener {

	MouseOverArea button;

	World world;

	private int id;
	private String[] info;

	public Button(World world, GameContainer gc, Image image, int id, int x, int y) {

		this.world = world;

		this.id = id;
		setInfo();

		button = new MouseOverArea(gc, image, x, y, this);
		button.setMouseOverColor(Color.blue);
		button.setMouseDownColor(Color.red);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		world.action = new PlaceEntityAction(world, id);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		setInfo();
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		button.render(gc, g);

		if (button.isMouseOver()) {
			
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRoundRect(button.getX(), Leviathan.HEIGHT - 200, 150, 100, 3);
			g.setColor(Color.white);
			g.drawString(info[0], button.getX() + 5, Leviathan.HEIGHT - 200);
			g.drawString(info[1], button.getX() + 5, Leviathan.HEIGHT - 180);
			g.drawString(info[2], button.getX() + 5, Leviathan.HEIGHT - 160);
			g.drawString(info[3], button.getX() + 5, Leviathan.HEIGHT - 140);
			g.drawString(info[4], button.getX() + 5, Leviathan.HEIGHT - 120);

		}
	}
	
	private void setInfo(){
		switch (id) {
		case 1:
			info = House.getInfo();
			break;
		case 2:
			info = Warehouse.getInfo();
			break;
		case 3:
			info = Shipyard.getInfo();
			break;
		}
	}

}
