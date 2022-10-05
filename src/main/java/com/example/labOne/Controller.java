package com.example.labOne;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="inputField"
    private TextField inputField;

    @FXML // fx:id="keySpinner"
    private Spinner<Integer> keySpinner; // Value injected by FXMLLoader


    @FXML // fx:id="outputField"
    private TextArea outputField;

    @FXML // fx:id="startBtn"
    private Button startBtn;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        SpinnerValueFactory<Integer> keysValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10);
        this.keySpinner.setValueFactory(keysValueFactory);
        keySpinner.setEditable(true);
        startBtn.setOnAction(event -> {
            Caesars_cipher.setKey(keySpinner.getValue());
            String buf = inputField.getText();
            boolean b = buf.isEmpty();
            if (!buf.isEmpty()) {
                buf = Caesars_cipher.doEncryption(buf);
                outputField.setText(buf);
            }
        });
    }

}
