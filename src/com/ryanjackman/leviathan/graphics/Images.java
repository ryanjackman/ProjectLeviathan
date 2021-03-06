package com.ryanjackman.leviathan.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	
	public static Image oneXOne;
	public static Image twoXOne;
	public static Image shipyard;
	public static Image unit;
	
	public static void init(){
		try {
			oneXOne = new Image("res/32x32.png");
			twoXOne = new Image("res/64x32.png");
			shipyard = new Image("res/shipyard.png");
			unit = new Image("res/unit.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
