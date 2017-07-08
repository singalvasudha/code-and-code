package com.xebia.command;

public class OrderCommand {
	private String custName;
	private long daysElapsed;
	private double milkReqd;
	private int skinsReqd;
	private double milkDelivered;
	private int skinsDelivered;
	private int status;//statusCode
	private String error;

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public long getDaysElapsed() {
		return daysElapsed;
	}

	public double getMilkReqd() {
		return milkReqd;
	}

	public void setMilkReqd(double milkReqd) {
		this.milkReqd = milkReqd;
	}

	public int getSkinsReqd() {
		return skinsReqd;
	}

	public void setSkinsReqd(int skinsReqd) {
		this.skinsReqd = skinsReqd;
	}

	public double getMilkDelivered() {
		return milkDelivered;
	}

	public void setMilkDelivered(double milkDelivered) {
		this.milkDelivered = milkDelivered;
	}

	public int getSkinsDelivered() {
		return skinsDelivered;
	}

	public void setSkinsDelivered(int skinsDelivered) {
		this.skinsDelivered = skinsDelivered;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setDaysElapsed(long daysElapsed) {
		this.daysElapsed = daysElapsed;
	}

}
