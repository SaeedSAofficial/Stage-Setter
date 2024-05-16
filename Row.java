package main;

import java.io.Serializable;
import java.util.Scanner;


public class Row implements Serializable {

	transient static Scanner input = new Scanner(System.in);
	private static Seat[] seats;
	private static int rowNumber;
	private static char type;
	private static double premiumServicePrice;

	public void initializeScanner() {
		Row.input = new Scanner(System.in);
	}
	
	// Constructor: Initializes a row of seats with a specific type and pricing
	public Row(int rowNumber, int size, char type, double price, double premiumServicePrice) {
		Row.rowNumber = rowNumber;
		Row.type = type;
		Row.premiumServicePrice = premiumServicePrice;
		seats = new Seat[size];
		if (type == 's')
			for (int i = 0; i < size; i++) {
				seats[i] = new Standard(price, premiumServicePrice);
			}
		else if (type == 'v')
			for (int i = 0; i < size; i++) {
				seats[i] = new VIP(price, premiumServicePrice);
			}
	}
	

	// Checks if a range of seats in the row is available
	private  static boolean seatsAvailable(int first, int last) {
		for (int i = first; i <= last; i++) {
			if (!(seats[i].available)) {
				// System.out.println("the seats are not available.");
				return false;
			}
		}
		return true;
	}

	// Attempts to book a range of seats in the row and creates a reservation if successful
	public static boolean book(int first, int last) {
		double total = 0;
		String response;
		boolean addPremiumService = false;
		if (seatsAvailable(first, last)) {
			if (seats[first].getPremiumServicePrice() > 0) {
				// System.out.print("Would you like to add premium quality service for " + premiumServicePrice
				// 		+ "SR per seat (yes/no): ");
				response = input.next();
				if (!(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no"))) {
					while (!(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no"))) {
						System.out.print("Please enter (yes) or (no): ");
						response = input.next();
					}
				}
				if (response.equalsIgnoreCase("yes"))
					addPremiumService = true;
				else
					addPremiumService = false;
				input.nextLine();
			}
			for (int i = first; i <= last; i++) {
				seats[i].setServiceQuality(addPremiumService);
				total += seats[i].book();
			}
			// System.out.print("Please enter your name: ");
			String name = input.nextLine();
			StageLocal.addReservation(name, rowNumber, first, last, type, seats[first].getServiceQuality(), total);
			return true;
		}
		return false;
	}

	 

	// Cancels the reservation for a range of seats in the row
	public void cancelReservation(int first, int last) {
		for (int i = first; i <= last; i++)
			seats[i].cancelReservation();
	}

	public char getType(){
		return Row.type;
	}

	public int getSize(){
		return seats.length;
	}

	// Returns a string representation of the row's seat statuses
	public String toString() {
		String s = "";
		for (int i = 0; i < seats.length; i++) {
			s += (seats[i].signature + "   ");
		}
		return s;
	}
}
