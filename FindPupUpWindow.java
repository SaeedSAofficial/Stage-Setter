package main.controller.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.StageLocal;

public class FindPupUpWindow {


    @FXML
    private TextField nameTextField;

    @FXML
    private Label resultLabel;

    private String reservation;

    @FXML
    public void handleSaveNameTextField(){
        reservation = nameTextField.getText();
        System.out.println(reservation);
       resultLabel.setText(StageLocal.findReservation(reservation));
    }

}