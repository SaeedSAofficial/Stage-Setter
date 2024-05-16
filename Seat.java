import java.io.Serializable;

public abstract class Seat implements Serializable {

	protected double price, totalPrice;
	protected char signature;
	protected boolean available;
	protected boolean addPremiumService;
	protected double premiumServicePrice;

	// Abstract method to return the service quality
	public abstract String getServiceQuality();

	// Abstract method to handle reservation cancellation
	public abstract void cancelReservation();

	// Books the seat if available, marking it not available and returns the price
	public double book() {
		if (available) {
			signature = 'X';
			available = false;
			return totalPrice;
		} else {
			return 0;
		}
	}

	// Sets the premium service state and adjusts price if added
	public void setServiceQuality(boolean addedPremiumService) {
		this.addPremiumService = addedPremiumService;
		if (addedPremiumService) {
			totalPrice = price + premiumServicePrice;
		}
		else
			totalPrice = price;
	}

	// Returns the additional cost of premium service
	public double getPremiumServicePrice() {
		return premiumServicePrice;
	}

}
