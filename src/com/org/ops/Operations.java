package com.org.ops;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.org.bank.Bank;
import com.org.tnx.Transaction;

public class Operations implements Transaction {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Scanner s;
	public static int count = 0;
	public String passcode;
	public static FileInputStream fis;
	public double depositAmt;
	public double wDrawAmt;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[31m";
	public static String dbProperies = "E:/Git/Bank-Operation/src/com/org/properties/db.properties";
	public static Properties prop = null;

	@Override
	public double deposit(double amt, String pin) {
		try {
			prop = readPropertiesData(Bank.dbProperies);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pin = prop.getProperty(pin);
		depositAmt = Double.parseDouble(pin) + amt;
		return depositAmt;
	}

	@Override
	public double withDraw(double amt, String pin) {
		try {
			prop = readPropertiesData(Bank.dbProperies);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = prop.getProperty(pin);
		if (Double.parseDouble(value) <amt) {
			LOGGER.log(Level.INFO, "Insufficient Balance...");
			return Double.parseDouble(value);
		} else {
			wDrawAmt = Double.parseDouble(value) - amt;
			return wDrawAmt;
		}
	}

	public static Properties readPropertiesData(String fileName) throws IOException {
		try {
			fis = new FileInputStream(Bank.dbProperies);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return prop;
	}

	public String verifyPin(int pin) throws IOException {
		if (pin == 0 || pin < 0) {
			LOGGER.log(Level.INFO, "Invalid pin, Please enter value>0");
			return "invalid";
		} else {
			passcode = Integer.toString(pin);

			prop = readPropertiesData(Bank.dbProperies);
			passcode = prop.getProperty(passcode);
			if (passcode != null) {
				return "correct";
			} else {
				System.out.println(ANSI_RESET + " Invalid entered pin, Please enter correct pin " + ANSI_YELLOW);
				Operations.count++;
				return "repeat";
			}
		}
	}
}
