package com.ryanjackman.leviathan.units;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.ryanjackman.leviathan.Leviathan;
import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.Vector2D;
import com.ryanjackman.leviathan.graphics.Images;

public class Unit {

	float x, y;

	private int radius = 10;
	private float restitution = 0.1f;
	private float mass = 10;

	Vector2D position;
	Vector2D velocity;
	Vector2D newVelocity;

	public World world;

	private Image image;

	public Unit(World world, int x, int y) {
		this.world = world;
		position = new Vector2D();
		position.x = x;
		position.y = y;

		velocity = new Vector2D((float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1));
		velocity.normalize();

		image = Images.unit;

		//radius = 10;
	}

	public void update(GameContainer gc, int delta) {
		/*
		 * if (Math.random() * 100 < 1) { velocity = new Vector2D((float)
		 * (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1));
		 * velocity.normalize(); }
		 */
		
		//if(newVelocity != null) {
			//velocity = newVelocity;
			//this.velocity.setX(newVelocity.getX());
			//this.velocity.setY(newVelocity.getY());
			//velocity = new Vector2D(newVelocity.getX(), newVelocity.getY());
		//	newVelocity = null;
		//}

		this.position.setX(this.position.getX() + (this.velocity.getX()));
		this.position.setY(this.position.getY() + (this.velocity.getY()));

		this.x = position.x;
		this.y = position.y;

		// TODO - Where should I be checking for epsilon?
		if (Math.abs(this.velocity.getX()) < .008)
			this.velocity.setX(0);
		if (Math.abs(this.velocity.getY()) < .008)
			this.velocity.setY(0);

		if (this.position.getX() - this.radius < 0) {
			this.position.setX(this.radius);
			this.velocity.setX(-(this.velocity.getX() * restitution));
			this.velocity.setY(this.velocity.getY() * restitution);
		} else if (this.position.getX() + this.radius > Leviathan.WIDTH) {
			this.position.setX(Leviathan.WIDTH - this.radius);
			this.velocity.setX(-(this.velocity.getX() * restitution));
			this.velocity.setY((this.velocity.getY() * restitution));
		}
		if (this.position.getY() - this.radius < 0) {
			this.position.setY(this.radius);
			this.velocity.setY(-(this.velocity.getY() * restitution));
			this.velocity.setX((this.velocity.getX() * restitution));
		} else if (this.position.getY() + this.radius > Leviathan.HEIGHT) {
			this.position.setY(Leviathan.HEIGHT - this.radius);
			this.velocity.setY(-(this.velocity.getY() * restitution));
			this.velocity.setX((this.velocity.getX() * restitution));
		}
	}

	public void render(GameContainer gc, Graphics g) {
		// this.image.setRotation((float) velocity.getTheta() - 90);
		g.drawImage(image, x + world.camera.getX(), y + world.camera.getY());
	}

	public boolean colliding(Unit unit) {
		float xd = position.getX() - unit.position.getX();
		float yd = position.getY() - unit.position.getY();

		float sumRadius = radius + unit.radius;
		float sqrRadius = sumRadius * sumRadius;

		float distSqr = (xd * xd) + (yd * yd);

		if (distSqr <= sqrRadius) {
			return true;
		}

		return false;

	}

	public void resolveCollision(Unit unit) {

		// get the mtd
		Vector2D delta = (position.subtract(unit.position));
		float r = radius + unit.radius;
		float dist2 = delta.dot(delta);

		if (dist2 > r * r)
			return; // they aren't colliding

		float d = delta.getLength();

		Vector2D mtd;
		if (d != 0.0f) {
			mtd = delta.multiply(((radius + unit.radius) - d) / d);

		} else // Special case. Balls are exactly on top of each other. Don't
				// want to divide by zero.
		{
			d = unit.radius + radius - 1.0f;
			delta = new Vector2D(unit.radius + radius, 0.0f);

			mtd = delta.multiply(((radius + unit.radius) - d) / d);
		}

		// resolve intersection
		float im1 = 1 / mass; // inverse mass quantities
		float im2 = 1 / unit.mass;

		// push-pull them apart
		// position = position.add(mtd.multiply(im1 / (im1 + im2)));
		// unit.position = unit.position.subtract(mtd.multiply(im2 / (im1 +
		// im2)));

		// impact speed
		Vector2D v = (this.velocity.subtract(unit.velocity));
		float vn = v.dot(mtd.normalize());

		// sphere intersecting but moving away from each other already
		if (vn > 0.0f)
			return;

		// collision impulse
		float i = (-(1.0f + restitution) * vn) / (im1 + im2);
		Vector2D impulse = mtd.multiply(i);

		// change in momentum
		//newVelocity = this.velocity.add(impulse.multiply(im1));
		//System.out.println(impulse.multiply(im1).x);
		//this.velocity.x += impulse.multiply(im1).x;
		//this.velocity.y += impulse.multiply(im1).y;
		//unit.newVelocity = unit.velocity.subtract(impulse.multiply(im2));/**/
		this.velocity = this.velocity.add(impulse.multiply(im1));
		unit.velocity = unit.velocity.subtract(impulse.multiply(im2));

	}

}
