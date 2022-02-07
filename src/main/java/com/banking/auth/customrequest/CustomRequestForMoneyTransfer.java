package com.banking.auth.customrequest;

public class CustomRequestForMoneyTransfer {
	
	private String accountNumber;
	private String userAccountNumber;
	private long amount;
	private String branchName;
	private String tranctionPin;
	private String ifsc;
	private String upadateAt;
	private String createAt;
	private String status;
	public CustomRequestForMoneyTransfer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomRequestForMoneyTransfer(String accountNumber, String userAccountNumber, long amount, String branchName,
			String tranctionPin, String ifsc, String upadateAt, String createAt, String status) {
		super();
		this.accountNumber = accountNumber;
		this.userAccountNumber = userAccountNumber;
		this.amount = amount;
		this.branchName = branchName;
		this.tranctionPin = tranctionPin;
		this.ifsc = ifsc;
		this.upadateAt = upadateAt;
		this.createAt = createAt;
		this.status = status;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getUserAccountNumber() {
		return userAccountNumber;
	}
	public void setUserAccountNumber(String userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getTranctionPin() {
		return tranctionPin;
	}
	public void setTranctionPin(String tranctionPin) {
		this.tranctionPin = tranctionPin;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getUpadateAt() {
		return upadateAt;
	}
	public void setUpadateAt(String upadateAt) {
		this.upadateAt = upadateAt;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CustomRequestForMoneyTransfer [accountNumber=" + accountNumber + ", userAccountNumber="
				+ userAccountNumber + ", amount=" + amount + ", branchName=" + branchName + ", tranctionPin="
				+ tranctionPin + ", ifsc=" + ifsc + ", upadateAt=" + upadateAt + ", createAt=" + createAt + ", status="
				+ status + "]";
	}
}
