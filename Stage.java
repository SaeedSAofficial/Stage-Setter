import java.io.Serializable;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Stage implements Serializable {

	private String name;
	private Row[] rows;
	private static Reservation[] reservations;
	private static int reservationsCount = 0;
	private double standardPrice, VIPPrice;
	private int numberOfRows, numberOfSeats;

	// Constructor: Initializes the stage with a name and number of rows
	public Stage(String name, int size) {
		this.name = name;
		this.numberOfRows = size;
		rows = new Row[size];
	}
	
	// Sets up the rows of the stage with specified seat configuration and pricing
	public void setRows(int numberOfSeats, double standardPrice, double VIPPrice, double premiumServicePrice) {
		this.numberOfSeats = numberOfSeats;
		reservations = new Reservation[this.numberOfSeats * this.numberOfRows];
		this.standardPrice = standardPrice;
		this.VIPPrice = VIPPrice;
		for (int i = 0; i < rows.length; i++) {
			char type;
			type = JOptionPane.showInputDialog("Please enter the type of row " + (i + 1) + " ('s' for standard and 'v' for VIP): ").charAt(0);
			while (type != 's' && type != 'v') {
				type = JOptionPane.showInputDialog("Please enter ('s' for standard or 'v' for VIP): ").charAt(0);
			}
			if (type == 's') {
				rows[i] = new Row(i, numberOfSeats, type, standardPrice, premiumServicePrice);
			} else if (type == 'v') {
				rows[i] = new Row(i, numberOfSeats, type, VIPPrice, premiumServicePrice);
			}
		}
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	// Facilitates booking of seats in a specific row
	public void book(int row, int first, int last, JPanel innerAddPanel) {
		rows[row].book(first, last, innerAddPanel);
	}

	// Adds a new reservation to the stage
	public static void addReservation(String name, int row, int first, int last, char type, String serviceQuality, double total) {
		int i = 0;
		while (i < reservationsCount) {
			if (reservations[i].getName().equalsIgnoreCase(name)) {
				name = JOptionPane.showInputDialog("There is already a reservation under the name " + name + ", enter another name: ");
				i = -1;
			}
			i++;
		}
		reservations[reservationsCount++] = new Reservation(name, row, first, last, type, serviceQuality, total);
		JOptionPane.showMessageDialog(null, "Done.");
	}

	// Searches for and displays a reservation by name
	public boolean findReservation(String name) {
		for (int i = 0; i < reservationsCount; i++) {
			if (reservations[i].getName().equalsIgnoreCase(name)) {
				JOptionPane.showMessageDialog(null, reservations[i].toString());
				return true;
			}
		}
		JOptionPane.showMessageDialog(null, "There is no reservation under the name " + name);
		return false;
	}

	// Cancels a reservation by name and adjusts seat availability accordingly
	public boolean cancelReservation(String name) {
		for (int i = 0; i < reservationsCount; i++) {
			if (reservations[i].getName().equalsIgnoreCase(name)) {
				rows[reservations[i].getRow()].cancelReservation(reservations[i].getFirstSeat(), reservations[i].getLastSeat());
				JOptionPane.showMessageDialog(null, "Reservation canceled.");
				for (int j = i; j < reservationsCount - 1; j++) {
					reservations[j] = reservations[j + 1];
				}
				reservationsCount--;
				return true;
			}
		}
		JOptionPane.showMessageDialog(null, "There is no reservation under the name " + name);
		return false;
	}

	// Returns a string representation of the stage, including seat and pricing info
	public String toString() {
		String s = "Welcome to " + name + "\n    ";
		for (int i = 0; i < numberOfSeats; i++) {
			s += (i + "   ");
		}
		s += "\n";
		for (int i = 0; i < rows.length; i++) {
			s += i + "   " + rows[i].toString() + "\n";
		}
		s += "Standard(S) = " + standardPrice + "SR   VIP(V) = " + VIPPrice + "SR   not_available(X)";
		s += "\n-------------------------------------";
		return s;
	}

	public Reservation[] getReservations() {
		return reservations;
	}

	public void setReservations(Reservation[] reservations) {
		Stage.reservations = reservations;
	}

	public int getReservationsCount() {
		return reservationsCount;
	}

	public void setReservationsCount(int reservationsCount) {
		Stage.reservationsCount = reservationsCount;
	}

}
