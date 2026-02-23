package com.org.tnx;

public interface Transaction {
	public double deposit(double amt, String digit);

	public double withDraw(double amt, String pin);

	
}
