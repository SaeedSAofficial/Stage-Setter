package main.controller.utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.StageLocal;
public class CancelPupUpWindow {

  

    @FXML
    private TextField nameTextField;

    @FXML
    private Label resultLabel;

    private String reservation;

    private boolean nameCancelled = false;

    @FXML
    public void handleSaveNameTextField(){
        reservation = nameTextField.getText();
        System.out.println(reservation);
        cancelRes(reservation);
        if(nameCancelled == true){
            showResult("Reservation canceled.");
        }else{
            showResult("There is no reservation under the name " + reservation);
        }
    }

    public  void cancelRes(String res){
       if(StageLocal.cancelReservation(res) == true){
        nameCancelled = true;
       } else{
        nameCancelled = false;
       }
    }

    @FXML
    public void showResult(String res){
        // get text
        resultLabel.setText(res);
    }

}
