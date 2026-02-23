package com.org.dto;

public class BankDTO {
	private double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "BankDTO [amount=" + amount + "]";
	}
}
