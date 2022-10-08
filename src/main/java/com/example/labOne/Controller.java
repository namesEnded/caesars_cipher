package com.example.labOne;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class Controller {

    private ObservableList<letterTableItem> lettersDataRT = FXCollections.observableArrayList();
    private ObservableList<letterTableItem> lettersDataCT = FXCollections.observableArrayList();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ciphertextArea"
    private TextArea ciphertextArea; // Value injected by FXMLLoader

    @FXML // fx:id="ciphertextTable"
    private TableView<letterTableItem> ciphertextTable; // Value injected by FXMLLoader

    @FXML // fx:id="freqClmnCT"
    private TableColumn<letterTableItem, Integer> freqClmnCT; // Value injected by FXMLLoader

    @FXML // fx:id="freqClmnRT"
    private TableColumn<letterTableItem, Integer> freqClmnRT; // Value injected by FXMLLoader

    @FXML // fx:id="inputField"
    private TextField inputField; // Value injected by FXMLLoader

    @FXML // fx:id="keySpinner"
    private Spinner<Integer> keySpinner; // Value injected by FXMLLoader

    @FXML // fx:id="keySpinnerBreak"
    private Spinner<Integer> keySpinnerBreak; // Value injected by FXMLLoader

    @FXML // fx:id="letterClmnCT"
    private TableColumn<letterTableItem, Character> letterClmnCT; // Value injected by FXMLLoader

    @FXML // fx:id="letterClmnRT"
    private TableColumn<letterTableItem, Character> letterClmnRT; // Value injected by FXMLLoader

    @FXML // fx:id="outputField"
    private TextArea outputField; // Value injected by FXMLLoader

    @FXML // fx:id="referenceTextArea"
    private TextArea referenceTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="referenceTextTable"
    private TableView<letterTableItem> referenceTextTable; // Value injected by FXMLLoader

    @FXML // fx:id="resultOutputArea"
    private TextArea resultOutputArea; // Value injected by FXMLLoader

    @FXML // fx:id="runWithThisKeyBtn"
    private Button runWithThisKeyBtn; // Value injected by FXMLLoader

    @FXML // fx:id="saveToFileBtn"
    private Button saveToFileBtn; // Value injected by FXMLLoader

    @FXML // fx:id="startAnalysisBtn"
    private Button startAnalysisBtn; // Value injected by FXMLLoader

    @FXML // fx:id="startBtn"
    private Button startBtn; // Value injected by FXMLLoader

    @FXML
    private Button fromFileToCTBtn;

    @FXML
    private Button fromFileToRTBtn;

    @FXML // This method is called by the FXMLLoader when initialization is complete

    void initialize() {
        ScrollPane scrollPane = new ScrollPane(outputField);
        scrollPane.setPrefViewportHeight(150);
        scrollPane.setPrefViewportWidth(200);
        SpinnerValueFactory<Integer> keysValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-100,100);
        this.keySpinner.setValueFactory(keysValueFactory);
        this.keySpinnerBreak.setValueFactory(keysValueFactory);
        keysValueFactory.setValue(0);
        keySpinner.setEditable(true);
        keySpinnerBreak.setEditable(true);
        letterClmnCT.setCellValueFactory(new PropertyValueFactory<letterTableItem, Character>("letter"));
        letterClmnRT.setCellValueFactory(new PropertyValueFactory<letterTableItem, Character>("letter"));
        freqClmnCT.setCellValueFactory(new PropertyValueFactory<letterTableItem, Integer>("freq"));
        freqClmnRT.setCellValueFactory(new PropertyValueFactory<letterTableItem, Integer>("freq"));


        fromFileToCTBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(ciphertextArea.getScene().getWindow());
            if(file != null){
                ciphertextArea.setText(readFile(file));
            }
        });

        fromFileToRTBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(referenceTextArea.getScene().getWindow());
            if(file != null){
                referenceTextArea.setText(readFile(file));
            }
        });

        saveToFileBtn.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(resultOutputArea.getScene().getWindow());

            if(file != null){
                try {
                    writeFile(file, resultOutputArea.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        startBtn.setOnAction(event -> {
            Caesars_cipher.setKey(keySpinner.getValue());
            String buf = inputField.getText();
            if (!buf.isEmpty()) {
                buf = Caesars_cipher.doEncryption(buf);
                outputField.setText(buf);
            }
        });

        runWithThisKeyBtn.setOnAction(event -> {
        Caesars_cipher.setKey(keySpinnerBreak.getValue());
        String buf = ciphertextArea.getText();
        if (!buf.isEmpty()) {
            buf = Caesars_cipher.doEncryption(buf);
            resultOutputArea.setText(buf);
            }
        });

        startAnalysisBtn.setOnAction(event -> {
            Analysis.setCiphertext(ciphertextArea.getText());
            Analysis.setRefText(referenceTextArea.getText());
            Analysis.start();
            Caesars_cipher.setKey(keySpinner.getValue());
            Map<Character,Integer> sortedRefTextMap = Analysis.getSortedRefTextMap();
            Map<Character,Integer> sortedCipherTextMap = Analysis.getSortedCipherTextMap();
            for (var entry : sortedRefTextMap.entrySet()) newRecordRT(entry.getKey(), entry.getValue());
            for (var entry : sortedCipherTextMap.entrySet()) newRecordCT(entry.getKey(), entry.getValue());
            referenceTextTable.setItems(lettersDataRT);
            ciphertextTable.setItems(lettersDataCT);
            Map.Entry<Character, Integer> actualValueRT = sortedRefTextMap.entrySet()
                    .stream()
                    .findFirst()
                    .get();
            Map.Entry<Character, Integer> actualValueCT = sortedCipherTextMap.entrySet()
                    .stream()
                    .findFirst()
                    .get();
            System.out.println("actualValueRT\n");
            System.out.println(actualValueRT.getKey() + "/" + actualValueRT.getValue() + "\n");
            System.out.println("actualValueCT\n");
            System.out.println(actualValueCT.getKey() + "/" + actualValueCT.getValue() + "\n");
            int probableKey = 0;
            if (Caesars_cipher.checkBoundaries(Character.toLowerCase(actualValueRT.getKey()))==
                    Caesars_cipher.checkBoundaries(Character.toLowerCase(actualValueCT.getKey())))
            {
                //probableKey = Math.abs((Character.toLowerCase(actualValueCT.getKey()))-(Character.toLowerCase(actualValueRT.getKey())));
                probableKey = ((Character.toLowerCase(actualValueRT.getKey()))- (Character.toLowerCase(actualValueCT.getKey())));
            }
            keySpinnerBreak.getValueFactory().setValue(probableKey);
        });
    }

    public String getCiphertextArea() {
        return ciphertextArea.getText();
    }


    public void setOutputField(String outputField) {
        this.outputField.setText(outputField);
    }

    public String getReferenceTextArea() {
        return referenceTextArea.getText();
    }



    private void newRecordRT( char letter, int freq ) {
        lettersDataRT.add(new letterTableItem(lettersDataRT.size()+1, letter, freq));
    }
    private void newRecordCT( char letter, int freq ) {
        lettersDataCT.add(new letterTableItem(lettersDataCT.size()+1, letter, freq));
    }

    private String readFile(File file){
        String filePath = file.getPath();
        String content = null;
        try {
            content = Files.lines(Paths.get(filePath))
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  content;
    }

    public static void writeFile(File file, String text)
            throws IOException
    {
        FileWriter writer = new FileWriter(file);
        writer.write(text);
        writer.close();
    }
}
