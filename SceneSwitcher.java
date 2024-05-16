package main.controller.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

     public static void openPopup(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Object controller = loader.getController();
            // if(controller instanceof AddPupUpWindow){
            //     ((AddPupUpWindow) controller).initializer();
            // }


            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); 
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); 
            popupStage.setTitle("Popup Window"); 

            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.showAndWait(); 
        } catch (IOException e) {
            System.err.println("Error loading popup: " + e.getMessage());
        }
    }
}

