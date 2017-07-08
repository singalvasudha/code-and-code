package com.xebia.beans;

public class Order {
	
	private double milk;
	private int skins;
	
	public Order(double milk, int skins){
		this.milk = milk;
		this.skins = skins;
	}
	public double getMilk() {
		return milk;
	}
	public void setMilk(double milk) {
		this.milk = milk;
	}
	public int getSkins() {
		return skins;
	}
	public void setSkins(int skins) {
		this.skins = skins;
	}

}
