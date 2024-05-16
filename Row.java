import java.io.Serializable;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Row implements Serializable {

	private Seat[] seats;
	private int rowNumber;
	private char type;
	private double premiumServicePrice;
	
	
	// Constructor: Initializes a row of seats with a specific type and pricing
	public Row(int rowNumber, int size, char type, double price, double premiumServicePrice) {
		this.rowNumber = rowNumber;
		this.type = type;
		this.premiumServicePrice = premiumServicePrice;
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
	private boolean seatsAvailable(int first, int last) {
		for (int i = first; i <= last; i++) {
			if (!(seats[i].available)) {
				JOptionPane.showMessageDialog(null, "the seats are not available.", "Seats not Available", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}

	// Attempts to book a range of seats in the row and creates a reservation if successful
	public boolean book(int first, int last, JPanel innerAddPanel) {
		double total = 0;
		String response;
		boolean addPremiumService = false;
		if (seatsAvailable(first, last)) {
			if (seats[first].getPremiumServicePrice() > 0) {
				response = JOptionPane.showInputDialog("Would you like to add premium quality service for " + premiumServicePrice
						+ "SR per seat (yes/no): ");
				if (!(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no"))) {
					while (!(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no"))) {
						response = JOptionPane.showInputDialog("Please enter (yes) or (no): ");
					}
				}
				if (response.equalsIgnoreCase("yes"))
					addPremiumService = true;
				else
					addPremiumService = false;
			}
			for (int i = first; i <= last; i++) {
				seats[i].setServiceQuality(addPremiumService);
				total += seats[i].book();
			}
			String name = JOptionPane.showInputDialog("Please enter your name: ");
			Stage.addReservation(name, rowNumber, first, last, type, seats[first].getServiceQuality(), total);
			return true;
		}
		return false;
	}

	// Cancels the reservation for a range of seats in the row
	public void cancelReservation(int first, int last) {
		for (int i = first; i <= last; i++)
			seats[i].cancelReservation();
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
