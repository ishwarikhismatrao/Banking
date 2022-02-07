package com.banking.auth.customresponse;

public class CustomRequestForResetPassword {
	
	private String email;
	private String password;
	private String newPassword;
	public CustomRequestForResetPassword() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomRequestForResetPassword(String email, String password, String newPassword) {
		super();
		this.email = email;
		this.password = password;
		this.newPassword = newPassword;
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "CustomRequestForResetPassword [email=" + email + ", password=" + password + ", newPassword="
				+ newPassword + "]";
	}
	
	

}
