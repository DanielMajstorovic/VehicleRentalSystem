package org.unibl.etf.ip.model;


public class Client {
    private int userId;
    private String idNumber;
    private String passportNumber;
    private String email;
    private String phone;
    private boolean hasAvatarImage;
    private double balance;

    private User user;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(int userId, String idNumber, String passportNumber, String email, String phone,
			boolean hasAvatarImage, double balance, User user) {
		super();
		this.userId = userId;
		this.idNumber = idNumber;
		this.passportNumber = passportNumber;
		this.email = email;
		this.phone = phone;
		this.hasAvatarImage = hasAvatarImage;
		this.balance = balance;
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isHasAvatarImage() {
		return hasAvatarImage;
	}

	public void setHasAvatarImage(boolean hasAvatarImage) {
		this.hasAvatarImage = hasAvatarImage;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

   
    
    
    
}