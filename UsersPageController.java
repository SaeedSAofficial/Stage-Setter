package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.controller.utils.*;

public class UsersPageController implements Initializable {

    @FXML
    private Button HOME;
    @FXML
    private Button addButton;
    @FXML
    private Button findButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveExitButton;
    @FXML
    private Label stageLabel;

    private StageLocal stage;
    private String src;


    @FXML
    public void seatingTheTheater() {
        String representation = StageLocal.getCurrentStage().toString();
        stageLabel.setText(representation);
    }

    @FXML
    private void addReservation(ActionEvent event) {
        // Logic to add a reservation
        // This would likely involve opening a new popup window to select seats
        SceneSwitcher.openPopup("/resources/view/addPupUpWindow.fxml", event);
        System.out.println("Add Reservation Clicked");
    }

    @FXML
    private void showReservation(ActionEvent event) {
        // Logic to show a reservation
        // Similarly, use a popup to display reservation details
        SceneSwitcher.openPopup("/resources/view/findPupUpWindow.fxml", event);
        System.out.println("Find Reservation Clicked");
    }

    @FXML
    private void removeReservation(ActionEvent event) {
        // Logic to cancel a reservation
        SceneSwitcher.openPopup("/resources/view/cancelPupUpWindow.fxml", event);
        System.out.println("Cancel Reservation Clicked");
    }

    @FXML
    private void saveAndExit() {
        try {
            TestStage.SaveFile(StageLocal.getSrc(), StageLocal.getCurrentStage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seatingTheTheater();
    }
}
