package com.org.bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.org.ops.Operations;

public class Bank {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Scanner s = new Scanner(System.in);
	public double amt;
	public int passcode;
	public double totalAmt;
	public static int pin;
	public static String verifyPin;
	public static String response;

	public static Properties readPropertiesFile(String fileName) throws IOException {
		Properties prop = null;
		try {
			FileInputStream fis = new FileInputStream("E:/JavaCoding/BankingOperation/src/db.properties");
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return prop;
	}

	public void DepositOps() throws IOException {
		Properties prop = Bank.readPropertiesFile("E:/JavaCoding/BankingOperation/src/com/org/properties/db.properties");
		System.out.println("enter amount to deposit");
		try {
			amt = s.nextDouble();
		} catch (InputMismatchException ime) {
			System.out.println("Enter valid input: " + ime.getStackTrace());
		}

		System.out.println(new Operations().deposit(amt, Integer.toString(pin)));

		totalAmt = amt + Double.parseDouble(prop.getProperty(Integer.toString(pin)));
		prop.put(Integer.toString(pin), Double.toString(totalAmt));
		String path = "E:/JavaCoding/BankingOperation/src/com/org/properties/db.properties";
		FileOutputStream outputStrem = new FileOutputStream(path);
		prop.store(outputStrem, "This is a sample properties file");
		System.out.println("Properties file created......");
	}

	public void WithdrawOps() throws IOException {
		Properties prop = Bank.readPropertiesFile("E:/JavaCoding/BankingOperation/src/com/org/properties/db.properties");
		System.out.println("enter withdraw amount ");
		try {
			amt = s.nextDouble();
		} catch (InputMismatchException ime) {
			System.out.println("Enter valid input: " + ime.getStackTrace());
		}
		if (amt <=0) {
			LOGGER.log(Level.INFO, "Insufficient Balance...");
		} else {
			System.out.println(new Operations().withDraw(amt, Integer.toString(pin)));
		}
		double totalAmt = amt + Double.parseDouble(prop.getProperty(Integer.toString(pin)));
		prop.put(Integer.toString(pin), Double.toString(totalAmt));
		String path = "E:/JavaCoding/BankingOperation/src/com/org/properties/db.properties";
		FileOutputStream outputStrem = new FileOutputStream(path);
		prop.store(outputStrem, "This is a sample properties file");
	}

	public void init() {
		System.out.println("Enter pin: ");
		try {
			pin = s.nextInt();
		} catch (InputMismatchException ime) {
			System.out.println("Enter digit value: " + ime.getMessage());
		}

	}

	public static void main(String[] args) throws IOException {
		Bank bankObj = new Bank();
		bankObj.init();
		verifyPin = new Operations().verifyPin(pin);
		while (verifyPin == "repeat" || verifyPin == "correct") {
			if (verifyPin == "correct") {
				System.out.println("Select Operations");
				System.out.println("1.Deposit\n2.Withdraw");
				int input = s.nextInt();
				if (input == 1) {
					bankObj.DepositOps();
					System.out.println("Do you want to continue Operations? \n1.Yes\n2.No");
					response=s.next();
					if(response.equalsIgnoreCase("Yes")){
						continue;
					}
					else if(response.equalsIgnoreCase("No")){
						LOGGER.log(Level.INFO, "Thank You");
						break;	
					}

				} else {
					bankObj.WithdrawOps();
					System.out.println("Do you want to continue Operations? \n1.Yes\n2.No");
					response=s.next();
					if(response.equalsIgnoreCase("Yes")){
						continue;
					}
					else if(response.equalsIgnoreCase("No")){
						LOGGER.log(Level.INFO, "Thank You");
						break;	
					}
				}
			} else if (Operations.count == 3) {
				LOGGER.log(Level.INFO, "Try after some time");
				break;
			} else {
				bankObj.init();
				verifyPin = new Operations().verifyPin(pin);
			}
		}
	}
}
