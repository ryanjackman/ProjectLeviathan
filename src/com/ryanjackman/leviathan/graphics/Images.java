package com.ryanjackman.leviathan.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	
	public static Image houseImage;
	
	public static void init(){
		try {
			houseImage = new Image("res/entity.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
