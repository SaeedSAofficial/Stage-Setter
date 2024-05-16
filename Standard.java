package main;

import java.io.Serializable;

public class Standard extends Seat implements Serializable {

	// Constructor: Initializes a Standard seat with specified prices
	public Standard(double price, double premiumServicePrice) {
		signature = 'S';
		available = true;
		this.price = price;
		this.premiumServicePrice = premiumServicePrice;
	}

	// Returns service quality as a string based on premium service addition
	public String getServiceQuality() {
		if (addPremiumService) {
			return "Premium service";
		}
		return "Standard service";
	}

	// Resets the seat to its default state (available and marked as Standard)
	public void cancelReservation() {
		signature = 'S';
		available = true;

	}

}
