package com.ryanjackman.leviathan.units;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.ryanjackman.leviathan.Vector2D;
import com.ryanjackman.leviathan.World;
import com.ryanjackman.leviathan.actions.MouseAction;
import com.ryanjackman.leviathan.entities.Entity;
import com.ryanjackman.leviathan.graphics.Images;

public class Unit {

	private float radius = 8;
	private float restitution = 1f;
	private float mass = 10;

	Vector2D position;
	Vector2D velocity;
	Vector2D newVelocity;

	// Vector2D target;

	public World world;
	public UnitGroup group;

	private Image image;

	public boolean moving = false;

	public boolean selected = false;

	public Unit(World world, UnitGroup u, int x, int y) {
		this.world = world;
		this.group = u;
		position = new Vector2D();
		position.x = x;
		position.y = y;

		velocity = new Vector2D(0, 0);

		image = Images.unit;

		// radius = 10;
	}

	public void update(GameContainer gc, int delta) {
		Input input = gc.getInput();

		/*
		 * if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) if
		 * (input.getMouseX() - world.camera.getX() > position.x &&
		 * input.getMouseX() - world.camera.getX() < position.x + 2 * radius) if
		 * (input.getMouseX() - world.camera.getY() > position.y &&
		 * input.getMouseY() - world.camera.getY() < position.y + 2 * radius) if
		 * (group.selected) { group.deselectAll(); } else { group.selectAll(); }
		 * 
		 * 
		 * if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && world.action
		 * instanceof MouseAction) {
		 * 
		 * target = new Vector2D(input.getMouseX() - world.camera.getX(),
		 * input.getMouseY() - world.camera.getY()); moving = true;
		 * 
		 * System.out.println("Target set at " + (input.getMouseX() -
		 * world.camera.getX()) + ", " + (input.getMouseY() -
		 * world.camera.getY())); }
		 */

		if (group.target != null) {
			if (position.getDistance(group.target) > (Math.sqrt(group.units.size()) * radius)) {

				Vector2D targetVector = new Vector2D(group.target.x - position.x, group.target.y - position.y);
				targetVector.normalize();
				velocity = velocity.add(targetVector);
			} else {
				// moving = false;
				velocity = new Vector2D(0, 0);
			}
		} else
			velocity = new Vector2D(0, 0);

		velocity.normalize();

		this.position.setX(this.position.getX() + (this.velocity.getX()));
		this.position.setY(this.position.getY() + (this.velocity.getY()));

		// TODO - Where should I be checking for epsilon?
		if (Math.abs(this.velocity.getX()) < .008)
			this.velocity.setX(0);
		if (Math.abs(this.velocity.getY()) < .008)
			this.velocity.setY(0);

		if (this.position.getX() - this.radius < 0) {
			this.position.setX(this.radius);
			this.velocity.setX(-(this.velocity.getX() * restitution));
			this.velocity.setY(this.velocity.getY() * restitution);
		} else if (this.position.getX() + this.radius > world.mapWidth) {
			this.position.setX(world.mapWidth - this.radius);
			this.velocity.setX(-(this.velocity.getX() * restitution));
			this.velocity.setY((this.velocity.getY() * restitution));
		}
		if (this.position.getY() - this.radius < 0) {
			this.position.setY(this.radius);
			this.velocity.setY(-(this.velocity.getY() * restitution));
			this.velocity.setX((this.velocity.getX() * restitution));
		} else if (this.position.getY() + this.radius > world.mapHeight) {
			this.position.setY(world.mapHeight - this.radius);
			this.velocity.setY(-(this.velocity.getY() * restitution));
			this.velocity.setX((this.velocity.getX() * restitution));
		}
	}

	public void render(GameContainer gc, Graphics g) {
		this.image.setRotation((float) velocity.getTheta() - 90);
		g.drawImage(image, position.x + world.camera.getX(), position.y + world.camera.getY(), group.selected ? Color.blue : Color.white);
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

	/*
	 * public boolean colliding(Entity e) { float xd = position.getX() - e.x;
	 * float yd = position.getY() - e.y;
	 * 
	 * float sumRadius = radius + e.radius; float sqrRadius = sumRadius *
	 * sumRadius;
	 * 
	 * float distSqr = (xd * xd) + (yd * yd);
	 * 
	 * if (distSqr <= sqrRadius) { return true; }
	 * 
	 * return false;
	 * 
	 * }
	 */

	public boolean colliding(Entity e) {
		float circleDistancex = Math.abs(position.x - e.x);
		float circleDistancey = Math.abs(position.y - e.y);

		if (circleDistancex > (e.width / 2 + radius)) {
			return false;
		}
		if (circleDistancey > (e.height / 2 + radius)) {
			return false;
		}

		if (circleDistancex <= (e.width / 2)) {
			return true;
		}
		if (circleDistancey <= (e.height / 2)) {
			return true;
		}

		float cornerDistance_sq = (float) (Math.pow((circleDistancex - e.width / 2), 2) + Math.pow(circleDistancey - e.height / 2, 2));

		return (cornerDistance_sq <= (radius * radius));
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
		position = position.add(mtd.multiply(im1 / (im1 + im2)));
		unit.position = unit.position.subtract(mtd.multiply(im2 / (im1 + im2)));

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
		this.velocity = this.velocity.add(impulse.multiply(im1));
		unit.velocity = unit.velocity.subtract(impulse.multiply(im2));

	}

	public void resolveCollision(Entity e) {
		// get the mtd
		Vector2D delta = (position.subtract(new Vector2D(e.x + e.width/2, e.y + e.height/2)));
		float r = radius + e.radius;
		float dist2 = delta.dot(delta);

		if (dist2 > r * r)
			return; // they aren't colliding

		float d = delta.getLength();

		Vector2D mtd;
		if (d != 0.0f) {
			mtd = delta.multiply(((radius + e.radius) - d) / d);

		} else // Special case. Balls are exactly on top of each other. Don't
				// want to divide by zero.
		{
			d = e.radius + radius - 1.0f;
			delta = new Vector2D(e.radius + radius, 0.0f);

			mtd = delta.multiply(((radius + e.radius) - d) / d);
		}

		// resolve intersection
		float im1 = 1 / mass; // inverse mass quantities
		float im2 = 0;

		// push-pull them apart
		position = position.add(mtd.multiply(im1 / (im1 + im2)));

		// impact speed
		Vector2D v = (this.velocity.subtract(new Vector2D(0, 0)));
		float vn = v.dot(mtd.normalize());

		// sphere intersecting but moving away from each other already
		if (vn > 0.0f)
			return;

		// collision impulse
		float i = (-(1.0f + restitution) * vn) / (im1 + im2);
		Vector2D impulse = mtd.multiply(i);

		// change in momentum
		//this.velocity = this.velocity.add(impulse.multiply(im1));
	}

	public boolean containsPoint(float xp, float yp) {
		if (xp > position.x && xp < position.x + 2 * radius)
			if (yp > position.y && yp < position.y + 2 * radius)
				return true;
		return false;
	}
}
