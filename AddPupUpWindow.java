package main.controller.utils;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import main.StageLocal;

public class AddPupUpWindow {

    @FXML
    private MenuButton rowMenuButton, firstSeatMenuSelection, lastSeatMenuSelection;

    @FXML
    private TextField nameTextField;

    private int selectedRow, firstSelectedSeat, lastSelectedSeat;

    private StageLocal stageLocal;

    public AddPupUpWindow() {
    }

    @FXML
    public void initialize() {
        stageLocal = StageLocal.getCurrentStage();
        updateRowMenu();
    }

    @FXML
    public void handleSaveNameText() {
        String text = nameTextField.getText();
        System.out.println("Name: " + text);
    }

    private void updateRowMenu() {
        int numberOfRows = getNumberOfRowsFromStage();
        rowMenuButton.getItems().clear();
        for (int i = 0; i < numberOfRows; i++) {
            MenuItem item = new MenuItem("Row " + i);
            int finalI = i;
            item.setOnAction(e -> {
                selectedRow = finalI;
                System.out.println("Selected Row: " + selectedRow);
                updateFirstSeatRangeMenu();
            });
            rowMenuButton.getItems().add(item);
        }
    }

    private void updateFirstSeatRangeMenu() {
        firstSeatMenuSelection.getItems().clear();
        int[] seatRange = getSeatRangeForRow(selectedRow);
        for (int seat = seatRange[0]; seat <= seatRange[1]; seat++) {
            MenuItem item = new MenuItem("Seat " + seat);
            int finalSeat = seat;
            item.setOnAction(e -> {
                firstSelectedSeat = finalSeat;
                System.out.println("First Selected Seat: " + firstSelectedSeat);
                updateLastSeatRangeMenu();
            });
            firstSeatMenuSelection.getItems().add(item);
        }
    }

    private void updateLastSeatRangeMenu() {
        lastSeatMenuSelection.getItems().clear();
        int[] seatRange = getSeatRangeForRow(selectedRow);
        for (int seat = firstSelectedSeat; seat <= seatRange[1]; seat++) {
            MenuItem item = new MenuItem("Seat " + seat);
            int finalSeat = seat;
            item.setOnAction(e -> {
                lastSelectedSeat = finalSeat;
                System.out.println("Last Selected Seat: " + lastSelectedSeat);
                stageLocal.book(selectedRow, firstSelectedSeat, lastSelectedSeat);
            });
            lastSeatMenuSelection.getItems().add(item);
        }
    }

    private int getNumberOfRowsFromStage() {
        return stageLocal.getNumberOfRows();
    }

    private int[] getSeatRangeForRow(int row) {
        return new int[]{0, stageLocal.getNumberOfSeats()};
    }
}
