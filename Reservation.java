import java.io.Serializable;

public class Reservation implements Serializable {

	private String name;
	private int row, firstSeat, lastSeat;
	private String type;
	private String serviceQuality;
	private double total;

	// Constructor: Initializes a reservation with details
	public Reservation(String name, int row, int first, int last, char type, String serviceQuality, double total) {
		this.name = name;
		this.row = row;
		this.firstSeat = first;
		this.lastSeat = last;
		if (type == 's')
			this.type = "Standard";
		else if (type == 'v')
			this.type = "VIP";
		this.serviceQuality = serviceQuality;
		this.total = total;
	}

	// Returns the name associated with the reservation
	public String getName() {
		return name;
	}

	// Returns the row number of the reservation
	public int getRow() {
		return row;
	}

	// Returns the first seat number in the reservation
	public int getFirstSeat() {
		return firstSeat;
	}

	// Returns the last seat number in the reservation
	public int getLastSeat() {
		return lastSeat;
	}

	// Returns a formatted string summarizing the reservation details
	public String toString() {
		String s = name + ":\nRow(" + row + ") Seats{" + firstSeat;
		for (int i = firstSeat + 1; i <= lastSeat; i++) {
			s += ", " + i;
		}
		s += "}\nSeats type: " + type + ". Service Quality: " + serviceQuality + ". Total Price: " + total + "SR";
		return s;
	}

}
