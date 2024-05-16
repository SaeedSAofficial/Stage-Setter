package main;

import java.io.Serializable;

public class VIP extends Seat implements Serializable {

	// Constructor: Initializes a VIP seat with specified prices.
	public VIP(double price, double premiumServicePrice) {
		signature = 'V';
		available = true;
		this.price = price;
		this.premiumServicePrice = premiumServicePrice;
	}

	// Returns service quality as a string, always premium if added, otherwise VIP.
	public String getServiceQuality() {
		if (addPremiumService) {
			return "Premium service";
		}
		return "VIP service";
	}

// Resets the seat to its default state (available and marked as VIP)
	public void cancelReservation() {
		signature = 'V';
		available = true;
	}
}
