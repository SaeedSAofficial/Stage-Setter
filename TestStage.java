package main;

import java.io.*;
import java.util.*;

public class TestStage {

	// public static int setInt(String s) {
	// 	Scanner input = new Scanner(System.in);
	// 	int integer;
	// 	while (true) {
	// 		try {
	// 			System.out.print(s);
	// 			integer = input.nextInt();
	// 			return integer;
	// 		} catch (InputMismatchException e) {
	// 			System.err.println("Caught InputMismatchException: expected an integer.");
	// 			input.nextLine();
	// 		}
	// 	}
	// }

	// public static double setPrice(String s) {
	// 	Scanner input = new Scanner(System.in);
	// 	double price;
	// 	while (true) {
	// 		try {
	// 			System.out.print(s);
	// 			price = input.nextDouble();
	// 			if (price < 0)
	// 				throw new NegativeNumberException("Price cannot be negative");
	// 			return price;
	// 		} catch (NegativeNumberException e) {
	// 			System.err.println("Caught NegativeNumberException: " + e.getMessage());
	// 		} catch (InputMismatchException e) {
	// 			System.err.println("Caught InputMismatchException: expected a double.");
	// 			input.nextLine();
	// 		}
	// 	}
	// }

	public static  StageLocal readFile(String src) throws ClassNotFoundException , IOException{
		StageLocal stage = null;
		try {
			FileInputStream fileIn = new FileInputStream(src);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			stage = (StageLocal) in.readObject();
			stage.setReservations(((Reservation[])in.readObject()));
			stage.setReservationsCount(in.readInt());
			// stage.setRow((Row[]) in.readObject()); // Read and set the rows array
			if (stage != null) {
				stage.initializeScanner();
			}
			in.close();
			fileIn.close();
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		}

		return stage;

	}

	public static void SaveFile(String src, StageLocal stage) throws IOException {

		ObjectOutputStream oos = null;

		try {
			FileOutputStream fileOut = new FileOutputStream(src, false);
			oos = new ObjectOutputStream(fileOut);

			oos.writeObject(stage);
			oos.writeObject(stage.getReservations());
			oos.writeInt(stage.getReservationsCount());
		} finally {
			if (oos != null)
				oos.close();
		}
	}

	
}
