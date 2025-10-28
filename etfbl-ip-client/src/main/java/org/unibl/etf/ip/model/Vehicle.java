package org.unibl.etf.ip.model;

public class Vehicle {

	private int id;
	private String uid;
	private String model;
	private String manufacturerName;
	private double pricePerSecond;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String m) {
		this.model = m;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String name) {
		this.manufacturerName = name;
	}

	public double getPricePerSecond() {
		return pricePerSecond;
	}

	public void setPricePerSecond(double p) {
		this.pricePerSecond = p;
	}
}
