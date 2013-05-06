package com.ryanjackman.leviathan;

public class Player {

	private int energy;
	private int money;
	private int resource;

	Player() {
		energy = 100000;
		money = 0;
		resource = 100000;
	}
	
	public boolean haveFunds(int e, int m, int r){
		if( energy < e || money < m || resource < r)
			return false;
		return true;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean addEnergy(int energy) {
		if (this.energy + energy < 0)
			return false;
		this.energy += energy;
		return true;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean addMoney(int money) {
		if (this.money + money < 0)
			return false;
		this.money += money;
		return true;
	}

	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	public boolean addResource(int resource) {
		if (this.resource + resource < 0)
			return false;
		this.resource += resource;
		return true;
	}
}
