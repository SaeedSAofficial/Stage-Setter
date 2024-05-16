package main;

import main.controller.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class MainPageController {

    @FXML
    private Button newStageButton;

    @FXML
    private Button oldStageButton;

    @FXML
    private Button HOME;

    private String src;
    
    @FXML
    public void handleReturnToHomeButtonAction(ActionEvent event){
        ReturnToHome.returnToHome(event);
    }

    @FXML
    private void handleNewStageButtonAction(ActionEvent event) {
        SceneSwitcher.switchScene("/resources/view/newStage.fxml", event);
    }
    
    @FXML
    private void handleOldStageButtonAction(ActionEvent event) {
        FileChooser fc = new FileChooser();

        fc.setTitle("Open Dat File");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dat Files", "*.dat"));
        File file = fc.showOpenDialog(null);
        
        if(file != null){
            try{
                StageLocal stage = TestStage.readFile(file.getPath());
                stage.setSrc(file.getPath());
            // go to user page 
            SceneSwitcher.switchScene("/resources/view/usersPage.fxml", event);
            }catch (IOException | ClassNotFoundException e){
                showAlert("Error", "Failed to open file: " + e.getMessage());
            }
           
        }System.out.println("ERROR");
       
    }

    private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
   
}
