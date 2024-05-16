package main.controller.utils;

import javafx.event.ActionEvent;

public class ReturnToHome {
    
    public static void returnToHome(ActionEvent event) {
        SceneSwitcher.switchScene("/resources/view/MainPage.fxml", event);
    }

}
