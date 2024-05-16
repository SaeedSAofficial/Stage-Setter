package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import main.controller.utils.*;

public class NewStageController {
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private AnchorPane dynamicContentArea;

    @FXML
    private Button HOME;

    @FXML
    private Button Save;

    @FXML
    private RadioButton yesButton;

    @FXML
    private RadioButton noButton;

    @FXML
    private Label premuimServicePrice;

    @FXML
    private TextField premuimServicePriceTextF, nameTextField, seatsTextField, standardPriceTextField , vipPriceTextField, rowsTextField;

    @FXML
    private TextField[]textFields;

    String name, src;
    int numberOfRows, numberOfSeats;
    double standardPrice, vipPrice, premiumServicePrice;

    
    @FXML
    public void handleReturnToHomeButtonAction(ActionEvent event){
       ReturnToHome.returnToHome(event);
    }

    @FXML
    public void handleSaveButtonAction(ActionEvent event) {
        // you need to sea if the user entered the type of seats and all inputs
        // handleSaveNameText();
        // handleSaveSeatsText();
        // handleSaveRowsText();
        // handleStandardPriceText();
        // handleVipPriceText();
        // handlePremuimPriceText();

         if(isAllInputsValid()){
            handleGenButtonAction();
            SceneSwitcher.switchScene("/resources/view/usersPage.fxml", event);
         }
    }

    @FXML
    public void handleSaveNameText(){
        name = nameTextField.getText();
    }

    @FXML
    public void handleSaveSeatsText(){
        try{
            numberOfSeats = Integer.parseInt(seatsTextField.getText());
            if(numberOfSeats <= 0) throw new IllegalArgumentException("Number of seats must be greater than 0.");
        } catch(IllegalArgumentException e){
            showAlert("Validation Error",  "Invalid number of seats: " + e.getMessage());
        }
    }

    @FXML
    public void handleSaveRowsText(){
       try{
        numberOfRows = Integer.parseInt(rowsTextField.getText());
        if(numberOfRows <= 0) throw new IllegalArgumentException("Number if rows must be greater than 0.");
        handleSettingTypes();
       }catch(IllegalArgumentException e){
        showAlert("Validation Error", "Invalid number of rows: " + e.getMessage());
       }
    }

    @FXML
    public void handleStandardPriceText(){
        try {
            standardPrice = validatePrice(standardPriceTextField, "s");
        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", "Invalid price for standard seat: " + e.getMessage());
        }
    }

    @FXML
    public void handleVipPriceText(){
        try {
            vipPrice = validatePrice(vipPriceTextField, "v");
        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", "Invalid price for vip seat: " + e.getMessage());
        }
    }

    @FXML
    public void handlePremuimPriceText(){
        try {
            premiumServicePrice = validatePrice(premuimServicePriceTextF, "p");
        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", "Invalid price for premuim service: " + e.getMessage());
        }
    }
    

    @FXML
    public void handleSelectionChange(){
        if(yesButton.isSelected()){
            premuimServicePrice.setVisible(true);
            premuimServicePriceTextF.setVisible(true);
        } else {
            premuimServicePrice.setVisible(false);
            premuimServicePriceTextF.setVisible(false);
        }
    }

    @FXML
    public void handleSettingTypes(){

        double intitialXForLabel = 30, intitialXForTextField= 370 ,intitialY = 10;
        int deltaY = 50;

        dynamicContentArea.getChildren().clear();
        textFields = new TextField[numberOfRows];
        int count = numberOfRows;
        for (int i = 0 ; i < count; i++){
            
            Label label = new Label("Please enter the type of row ("+ (i + 1) + "):");
            AnchorPane.setTopAnchor(label, intitialY + (i * deltaY));
            AnchorPane.setLeftAnchor(label, intitialXForLabel);
            label.setAlignment(Pos.TOP_LEFT);
            label.setPrefHeight(55);
            label.setPrefWidth(330);
            label.maxHeight(200);
            label.maxWidth(330);
            label.setFont(new Font("System", 18));

            TextField textField = new TextField();
            AnchorPane.setTopAnchor(textField,  intitialY + (i * deltaY) );
            AnchorPane.setLeftAnchor(textField, intitialXForTextField);
            textField.setPrefHeight(25.6);
            textField.setPrefWidth(180);
            textField.maxHeight(30);
            textField.maxWidth(180);
            textField.setPromptText("s for standard, v for vip");

            dynamicContentArea.getChildren().add(label);
            dynamicContentArea.getChildren().add(textField);

            textFields [i] = textField;
        }           
    }

    // store the types of rows whem submit is clicked 
    @FXML
    public void handleGenButtonAction(){

        if (textFields.length != numberOfRows) {
            showAlert("Error", "Number of text fields does not match number of rows.");
            return;
        }

        StageLocal stage = new StageLocal(name, numberOfRows);
        char[] rowTypes = new char[numberOfRows];

        int i = 0;
        for(TextField textField : textFields){
            String text = textField.getText().trim().toLowerCase();
            rowTypes[i++] = (text.equals("v") ? 'v' : 's' );
            }

        stage.setSrc("C:\\Users\\saeed\\Documents\\java\\GUIdomain\\CSC113pr\\default.dat");// i need to make a new file
        stage.setRows(numberOfSeats, standardPrice, vipPrice, premiumServicePrice, rowTypes);
        StageLocal.setCurrentStage(stage);
     }


    private double validatePrice(TextField field, String type) throws IllegalArgumentException {
       double price = Double.parseDouble(field.getText());
        if (price < 0){
            throw new IllegalArgumentException("Price for" + type + " cannot be negative.");
        }
        return price;
    }

    private boolean isAllInputsValid() {
        return name != null && !name.isEmpty()
            && numberOfSeats > 0
            && numberOfRows > 0
            && standardPrice >= 0
            && vipPrice >= 0
            && premiumServicePrice >= 0;
    }

    private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    }
}

