import java.awt.*;
import java.io.*;
import javax.swing.*;

public class TestStage {

	private JFrame fileFrame;
	private JPanel filePanel;

	private JFrame setterFrame;
	private JPanel setterPanel;
	private JTextField nameField, standardPriceField, vipPriceField, premiumPriceField;
	private JSpinner rowSpinner, seatSpinner;
	private JRadioButton yesButton, noButton;
	private ButtonGroup buttonGroup;
	private JPanel premiumCheck;
	private JLabel nameLabel, rowLabel, seatLabel, standardLabel, vipLabel, premiumLabel, premiumPriceLabel;
	private JButton saveButton;

	private JFrame menuFrame;
	private JPanel menuPanel;
	private JTextArea stageDisplay;
	private JButton addButton, findButton, cancelButton, SaveAndExitButton;
	private JPanel addPanel, innerAddPanel;
	private JLabel rowNumberLabel, firstSeatLabel, lastSeatLabel;
	private JSpinner rowNumberSpinner, firstSeatSpinner, lastSeatSpinner;

	Stage stage = null;
	String name;
	String orgResponse;
	boolean addPremiumService;
	int numberOfRows, numberOfSeats;
	double standardPrice, VIPPrice, premiumServicePrice;
	int row, first, last;
	String src;
	boolean finished = false;

	public TestStage() {

		fileFrame = new JFrame("File");
		fileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		filePanel = new JPanel();
		filePanel.setLayout(new GridBagLayout());

		JButton oldFileButton = new JButton("Open Old File");
		oldFileButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(fileFrame);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				src = fileChooser.getSelectedFile().getAbsolutePath();
				JOptionPane.showMessageDialog(fileFrame, "Selected file: " + src);
				stage = readFile(src);
				fileFrame.dispose();
				showMenu();
			} else {
				JOptionPane.showMessageDialog(fileFrame, "File selection cancelled.");
			}
		});

		JButton newFileButton = new JButton("Create New File");
		newFileButton.addActionListener(e -> {
			File file = new File("default.dat");
			src = file.getAbsolutePath();
			JOptionPane.showMessageDialog(fileFrame, "Selected file: " + file.getAbsolutePath());
			fileFrame.dispose();
			showSetter();
		});

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(20, 17, 20, 17);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		filePanel.add(oldFileButton, gbc);
		gbc.gridx = 1;
		filePanel.add(newFileButton, gbc);
		fileFrame.setContentPane(filePanel);
		fileFrame.pack();
		fileFrame.setLocationRelativeTo(null);
		fileFrame.setResizable(false);
		fileFrame.setVisible(true);

// End of the First frame
// ------------------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------------------------------------

		// Collects initial setup information for the stage from the user

		setterFrame = new JFrame("Stage Setter");
		setterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setterPanel = new JPanel();
		setterPanel.setLayout(new GridBagLayout());
		gbc.insets = new Insets(10, 10, 10, 10);

		nameLabel = new JLabel("Please enter the name of the show: ");
		nameField = new JTextField(10);

		rowLabel = new JLabel("Please enter how many rows in your stage: ");
		rowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

		seatLabel = new JLabel("Please enter how many Seats in a row: ");
		seatSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

		standardLabel = new JLabel("Please enter the price of the Standard seats: ");
		standardPriceField = new JTextField();

		vipLabel = new JLabel("Please enter the price of the VIP seats: ");
		vipPriceField = new JTextField();

		premiumLabel = new JLabel("Would you be interested in offering a Premium service to your attendees?");
		yesButton = new JRadioButton("YES");
		noButton = new JRadioButton("NO");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(yesButton);
		buttonGroup.add(noButton);
		premiumCheck = new JPanel();
		premiumCheck.add(yesButton);
		premiumCheck.add(noButton);

		premiumPriceLabel = new JLabel("Please enter the price for the Premium service: ");
		premiumPriceLabel.setVisible(false);
		premiumPriceField = new JTextField();
		premiumPriceField.setVisible(false);

		yesButton.addActionListener(e -> {
			premiumPriceLabel.setVisible(true);
			premiumPriceField.setVisible(true);
		});

		noButton.addActionListener(e -> {
			premiumPriceField.setText("0");
			premiumPriceLabel.setVisible(false);
			premiumPriceField.setVisible(false);
		});

		saveButton = new JButton("Save");

		saveButton.addActionListener(e -> {
			name = nameField.getText();
			numberOfRows = (int) rowSpinner.getValue();
			numberOfSeats = (int) seatSpinner.getValue();

			try {
				standardPrice = Double.parseDouble(standardPriceField.getText());
				VIPPrice = Double.parseDouble(vipPriceField.getText());
				premiumServicePrice = Double.parseDouble(premiumPriceField.getText());

				if (standardPrice < 0 || VIPPrice < 0 || premiumServicePrice < 0)
					throw new NegativeNumberException("");

				JOptionPane.showMessageDialog(setterFrame, "Stage completed.");
				stage = new Stage(name, numberOfRows);
				stage.setRows(numberOfSeats, standardPrice, VIPPrice, premiumServicePrice);
				setterFrame.dispose();
				showMenu();

			} catch (NegativeNumberException ex) {
				JOptionPane.showMessageDialog(setterFrame, "Price cannot be negative. Please enter a valid price.",
						"Invalid Price", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(setterFrame, "Invalid price format. Please enter a valid number.",
						"Invalid Price", JOptionPane.ERROR_MESSAGE);
			}

		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		setterPanel.add(nameLabel, gbc);
		gbc.gridx = 1;
		setterPanel.add(nameField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		setterPanel.add(rowLabel, gbc);
		gbc.gridx = 1;
		setterPanel.add(rowSpinner, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		setterPanel.add(seatLabel, gbc);
		gbc.gridx = 1;
		setterPanel.add(seatSpinner, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		setterPanel.add(standardLabel, gbc);
		gbc.gridx = 1;
		setterPanel.add(standardPriceField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		setterPanel.add(vipLabel, gbc);
		gbc.gridx = 1;
		setterPanel.add(vipPriceField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		setterPanel.add(premiumLabel, gbc);
		gbc.gridx = 1;
		setterPanel.add(premiumCheck, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		setterPanel.add(premiumPriceLabel, gbc);
		gbc.gridx = 1;
		setterPanel.add(premiumPriceField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.NONE;
		setterPanel.add(saveButton, gbc);

		setterFrame.setContentPane(setterPanel);
		setterFrame.setSize(700, 400);
		;
		setterFrame.setLocationRelativeTo(null);
		setterFrame.setResizable(false);

// End of the Second frame
// ------------------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------------------------------------	
	}

	public void showMenu() {
		// Main loop for the reservation system's user interface.

		numberOfRows = stage.getNumberOfRows();
		numberOfSeats = stage.getNumberOfSeats();

		menuFrame = new JFrame("Menu");
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.CENTER;

		stageDisplay = new JTextArea(stage.toString());
		stageDisplay.setEditable(false);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 4;
		menuPanel.add(stageDisplay, gbc);

		addButton = new JButton("Add Reservation");
		addPanel = new JPanel();
		addPanel.setLayout(new GridBagLayout());
		GridBagConstraints innergbc = new GridBagConstraints();
		innergbc.insets = new Insets(5, 5, 5, 5);
		innergbc.fill = GridBagConstraints.HORIZONTAL;
		rowNumberLabel = new JLabel("Enter the row: ");
		firstSeatLabel = new JLabel("Enter the first seat: ");
		lastSeatLabel = new JLabel("Enter the last seat: ");

		rowNumberSpinner = new JSpinner(new SpinnerNumberModel(0, 0, numberOfRows - 1, 1));
		firstSeatSpinner = new JSpinner(new SpinnerNumberModel(0, 0, numberOfSeats - 1, 1));
		lastSeatSpinner = new JSpinner(new SpinnerNumberModel(0, 0, numberOfSeats - 1, 1));

		innergbc.gridx = 0;
		innergbc.gridy = 0;
		addPanel.add(rowNumberLabel, innergbc);
		innergbc.gridx = 1;
		addPanel.add(rowNumberSpinner, innergbc);
		innergbc.gridx = 0;
		innergbc.gridy = 1;
		addPanel.add(firstSeatLabel, innergbc);
		innergbc.gridx = 1;
		addPanel.add(firstSeatSpinner, innergbc);
		innergbc.gridx = 0;
		innergbc.gridy = 2;
		addPanel.add(lastSeatLabel, innergbc);
		innergbc.gridx = 1;
		addPanel.add(lastSeatSpinner, innergbc);

		addButton.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(menuFrame, addPanel, "Add Reservation",
					JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					row = (int) rowNumberSpinner.getValue();
					first = (int) firstSeatSpinner.getValue();
					last = (int) lastSeatSpinner.getValue();
					rowNumberSpinner.setValue(0);
					firstSeatSpinner.setValue(0);
					lastSeatSpinner.setValue(0);
					if (last < first)
						throw new IllegalArgumentException();
					stage.book(row, first, last, innerAddPanel);
					stageDisplay.setText(stage.toString());
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(menuFrame, "Last can't be less than First. Try again.",
							"Invalid Range", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		menuPanel.add(addButton, gbc);

		findButton = new JButton("Find Reservation");
		findButton.addActionListener(e -> {
			stage.findReservation(JOptionPane.showInputDialog("Please enter the name on the reservation: "));
		});
		gbc.gridy = 1;
		menuPanel.add(findButton, gbc);

		cancelButton = new JButton("Cancel Reseervation");
		cancelButton.addActionListener(e -> {
			stage.cancelReservation(JOptionPane.showInputDialog("Please enter the name on the reservation: "));
			stageDisplay.setText(stage.toString());
		});
		gbc.gridy = 2;
		menuPanel.add(cancelButton, gbc);

		SaveAndExitButton = new JButton("Save and Exit");
		SaveAndExitButton.addActionListener(e -> {
			try {
				SaveFile(src, stage);
				JOptionPane.showMessageDialog(null, "GoodBye.");
				System.exit(0);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		gbc.gridy = 3;
		menuPanel.add(SaveAndExitButton, gbc);

		menuFrame.setContentPane(menuPanel);
		menuFrame.pack();
		menuFrame.setLocationRelativeTo(null);

		menuFrame.setVisible(true);
	}

	public void showSetter() {
		setterFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TestStage();
			}
		});
	}

	public Stage readFile(String src) {

		Stage stage = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(src));
			stage = (Stage) ois.readObject();
			stage.setReservations(((Reservation[]) ois.readObject()));
			stage.setReservationsCount(ois.readInt());
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Stage class not found", "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return stage;

	}

	public void SaveFile(String src, Stage stage) throws IOException {

		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(src, false));
			oos.writeObject(stage);
			oos.writeObject(stage.getReservations());
			oos.writeInt(stage.getReservationsCount());
		} finally {
			if (oos != null)
				oos.close();
		}
	}
}
