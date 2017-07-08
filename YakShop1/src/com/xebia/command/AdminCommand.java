package com.xebia.command;

import com.xebia.beans.Herd;

public class AdminCommand {
	private String path;
	private long daysElapsed;
	private double milk;
	private int skins;
	private Herd herd;
	private String error;
	private String jsonHerd;
	private String jsonStock;

	public String getJsonHerd() {
		return jsonHerd;
	}

	public void setJsonHerd(String jsonHerd) {
		this.jsonHerd = jsonHerd;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Herd getHerd() {
		return herd;
	}

	public void setHerd(Herd herd) {
		this.herd = herd;
	}

	public int getSkins() {
		return skins;
	}

	public void setSkins(int skins) {
		this.skins = skins;
	}

	public double getMilk() {
		return milk;
	}

	public void setMilk(double milk) {
		this.milk = milk;
	}

	public long getDaysElapsed() {
		return daysElapsed;
	}

	public void setDaysElapsed(long daysElapsed) {
		this.daysElapsed = daysElapsed;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getJsonStock() {
		return jsonStock;
	}

	public void setJsonStock(String jsonStock) {
		this.jsonStock = jsonStock;
	}

}
