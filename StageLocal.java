package main;

import java.io.Serializable;
import java.util.Scanner;

public class StageLocal implements Serializable {

	transient Scanner input = new Scanner(System.in);
	private static StageLocal currentStage;
	private static String currentSrc;
	private String name, src;
	private static Row[] rows;
	private static Reservation[] reservations;
	private static int reservationsCount = 0;
	private double standardPrice, VIPPrice;
	public static int numberOfRows;
	public static int numberOfSeats;
	
	public void initializeScanner() {
	
		for (Row row: rows) {
			row.initializeScanner();
		}
	}

	public void setRow(Row [] row){
		rows = row;
	}
	// Constructor: Initializes the stage with a name and number of rows
	public StageLocal(String name, int size) {
		this.name = name;
		StageLocal.numberOfRows = size;
		rows = new Row[size];
	}

	public StageLocal(){
		
	}

	public  static void setCurrentStage(StageLocal stage) {
        currentStage = stage;
    }

    public static StageLocal getCurrentStage() {
        return currentStage;
    }
  
	public void setSrc(String src){
        this.src = src;
		currentSrc = src;
    }


	
	// Sets up the rows of the stage with specified seat configuration and pricing
	public void setRows(int numberOfSeats, double standardPrice, double VIPPrice, double premiumServicePrice, char[] rowTypes) {
		StageLocal.numberOfSeats = numberOfSeats;
		reservations = new Reservation[StageLocal.numberOfSeats * StageLocal.numberOfRows];
		this.standardPrice = standardPrice;
		this.VIPPrice = VIPPrice;
		for (int i = 0; i < rows.length; i++) {
			char type = rowTypes[i];
			// System.out.print("Please enter the type of row " + (i + 1) + " ('s' for standard and 'v' for VIP): ");
			// type = input.next().charAt(0);
			// while (type != 's' && type != 'v') {
			// 	System.out.print("Please enter ('s' for standard or 'v' for VIP): ");
			// 	type = input.next().charAt(0);
			// }
			if (type == 's') {
				rows[i] = new Row(i, numberOfSeats, type, standardPrice, premiumServicePrice);
			} else if (type == 'v') {
				rows[i] = new Row(i, numberOfSeats, type, VIPPrice, premiumServicePrice);
			}
		}
	}

	public  int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

    public static String getSrc(){
        return currentSrc;
    }

	// Facilitates booking of seats in a specific row
	public void book(int row, int first, int last) {
		Row.book(first, last);
	}

	// Adds a new reservation to the stage
	public static void addReservation(String name, int row, int first, int last, char type, String serviceQuality, double total) {
		Scanner kb = new Scanner(System.in);
		int i = 0;
		while (i < reservationsCount) {
			if (reservations[i].getName().equalsIgnoreCase(name)) {
				System.out.print("There is already a reservation under the name " + name + ", enter another name: ");
				name = kb.nextLine();
				i = -1;
			}
			i++;
		}
		reservations[reservationsCount++] = new Reservation(name, row, first, last, type, serviceQuality, total);
		System.out.println("Done.");
	}

	// Searches for and displays a reservation by name
	public static String findReservation(String name) {
		for (int i = 0; i < reservationsCount; i++) {
			if (reservations[i].getName().equalsIgnoreCase(name)) {
				return reservations[i].toString();
			}
		}
		return "There is no reservation under the name " + name;
		
	}

	// Cancels a reservation by name and adjusts seat availability accordingly
	public static boolean cancelReservation(String name) {
		for (int i = 0; i < reservationsCount; i++) {
			if (reservations[i].getName().equalsIgnoreCase(name)) {
				rows[reservations[i].getRow()].cancelReservation(reservations[i].getFirstSeat(), reservations[i].getLastSeat());
				System.out.println("Reservation canceled.");
				for (int j = i; j < reservationsCount - 1; j++) {
					reservations[j] = reservations[j + 1];
				}
				reservationsCount--;
				return true;
			}
		}
		System.out.println("There is no reservation under the name " + name);
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
		return s;
	}

	

	public Reservation[] getReservations() {
		return reservations;
	}

	public void setReservations(Reservation[] reservations) {
		StageLocal.reservations = reservations;
	}

	public int getReservationsCount() {
		return reservationsCount;
	}

	public void setReservationsCount(int reservationsCount) {
		StageLocal.reservationsCount = reservationsCount;
	}

}
