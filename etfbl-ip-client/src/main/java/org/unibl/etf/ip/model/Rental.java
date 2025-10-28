package org.unibl.etf.ip.model;

import java.sql.Timestamp;

public class Rental {
	private int id;
	private Timestamp startTime;
	private String vehicleUid;
	private String vehicleModel;
	private double pricePerSecond;
	private Timestamp endTime;
	private double totalAmount;

	private double startX, startY;
	private String driversLicense, paymentCard;
	private int vehicleId, clientId;

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public String getPaymentCard() {
		return paymentCard;
	}

	public void setPaymentCard(String paymentCard) {
		this.paymentCard = paymentCard;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp s) {
		this.startTime = s;
	}

	public String getVehicleUid() {
		return vehicleUid;
	}

	public void setVehicleUid(String u) {
		this.vehicleUid = u;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String m) {
		this.vehicleModel = m;
	}

	public double getPricePerSecond() {
		return pricePerSecond;
	}

	public void setPricePerSecond(double p) {
		this.pricePerSecond = p;
	}
}
