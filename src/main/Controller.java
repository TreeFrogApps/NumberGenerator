package main;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.generator.BaseGenerator;
import main.generator.utils;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Controller implements Initializable, EventHandler<ActionEvent>, ChangeListener<String> {

    @FXML
    private TextArea textArea;
    @FXML
    private TextField startNumber;
    @FXML
    private TextField stopNumber;
    @FXML
    private CheckBox paddingCheckBox;
    @FXML
    private TextField suffix;
    @FXML
    private TextField prefix;
    @FXML
    private TextField columnTitle;
    @FXML
    private Button saveButton;
    @FXML
    private Button generateButton;
    @FXML
    private ToggleGroup radioGroup;
    @FXML
    private RadioButton radioNormal;
    @FXML
    private RadioButton radioReverse;
    @FXML
    private RadioButton radioRandom;
    @FXML
    private TextField incrementNumber;


    private LinkedList<String> mCurrentNumberList = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Run when the view is created - can be used to start listeners
         * - if binding to the fmxl onAction listener is not desired - no thanks!
         */
        System.out.println("Initializable Called Back");

        saveButton.setOnAction(this);
        generateButton.setOnAction(this);

        UnaryOperator<TextFormatter.Change> textFilter = change -> {
            for (int i = 0; i < change.getText().length(); i++) {
                if (!Character.isDigit(change.getText().charAt(i))) {
                    return null;
                }
            }
            return change;
        };

        startNumber.setTextFormatter(new TextFormatter<Integer>(textFilter));
        stopNumber.setTextFormatter(new TextFormatter<Integer>(textFilter));
        incrementNumber.setTextFormatter(new TextFormatter<Integer>(textFilter));

        textArea.setEditable(false);

        radioNormal.setUserData(1);
        radioReverse.setUserData(2);
        radioRandom.setUserData(3);
    }


    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == saveButton) {

            if (mCurrentNumberList.size() > 0) {

                String filename = (columnTitle.getText().equals("") ?
                        mCurrentNumberList.getFirst() : (mCurrentNumberList.getFirst() + "_" + mCurrentNumberList.get(1))) + "_" + mCurrentNumberList.getLast() + ".txt";
                try {
                    utils.saveFile(saveButton.getScene(), filename, textArea);
                } catch (IOException e) {
                    System.out.println(Controller.class.getSimpleName() + ": Error Saving file : " + e);
                }
            }

        } else if (event.getSource() == generateButton) {

            System.out.println("here");

            if (!startNumber.getText().equals("") && !stopNumber.getText().equals("")) {

                int start = Integer.parseInt(startNumber.getText().replaceFirst("^0+(?!$)", ""));
                int stop = Integer.parseInt(stopNumber.getText().replaceFirst("^0+(?!$)", ""));
                int increment = Integer.parseInt(incrementNumber.getText().replaceFirst("^0+(?!$)", ""));
                boolean padding = paddingCheckBox.isSelected();

                if (utils.checkNumbers(start, stop)) {

                    System.out.println("Values: Start: " + start + " , Stop : " + stop + ", Incre : " + increment + ", Padding : " + padding);

                    BaseGenerator generator = new BaseGenerator.Builder(start, stop, increment, padding)
                            .order((Integer) radioGroup.getSelectedToggle().getUserData())
                            .prefix(prefix.getText() == null ? "" : prefix.getText())
                            .suffix(suffix.getText() == null ? "" : suffix.getText())
                            .build();

                    mCurrentNumberList.clear();
                    mCurrentNumberList.addAll(generator.generateNumbers());

                    if (!columnTitle.getText().equals("")) {
                        mCurrentNumberList.addFirst(columnTitle.getText());
                    }
                    textArea.clear();
                    textArea.setText(utils.buildFormattedList(mCurrentNumberList));

                } else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Number Error");
                    alert.setContentText("Stop Number must be greater than Start Number!");
                    alert.showAndWait();
                }
            }
        }
    }


    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

    }


}
