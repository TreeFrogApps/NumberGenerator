package main.generator;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Utils class to do the generating.
 */
public class utils {

    static List<String> generateListNormalOrder(int start, int stop, String prefix, String suffix, int increment, boolean padding) {

        List<String> numberList = new LinkedList<>();

        final int length = String.valueOf(stop).length();
        String format = "";

        if (padding) {
            for (int i = 0; i < length; i++) {
                format += "0";
            }
        }

        for (int j = start; j <= stop; j += increment) {

            String number = String.valueOf(j);
            String line;

            if(padding){
                line = prefix + (format + number).substring(number.length()) + suffix;
            } else {
                line = prefix + number + suffix;
            }
            numberList.add(line);
        }

        return numberList;
    }

    static List<String> generateListReverseOrder(int start, int stop, String prefix, String suffix, int increment, boolean padding) {

        List<String> numberList = new LinkedList<>();

        final int length = String.valueOf(stop).length();
        String format = "";

        if (padding) {
            for (int i = 0; i < length; i++) {
                format += "0";
            }
        }

        for (int j = stop; j >= start; j -= increment) {

            String number = String.valueOf(j);

            String line;

            if(padding){
                line = prefix + (format + number).substring(number.length()) + suffix;
            } else {
                line = prefix + number + suffix;
            }
            numberList.add(line);
        }

        return numberList;
    }

    static List<String> generateListRandomOrder(int start, int stop, String prefix, String suffix, int increment, boolean padding) {

        List<String> numberList = new LinkedList<>();

        final int length = String.valueOf(stop).length();
        String format = "";

        if (padding) {
            for (int i = 0; i < length; i++) {
                format += "0";
            }
        }

        HashSet<Integer> values = new HashSet<>();

        for (int j = start; j <= stop; j += increment) {

            int intNumber = (int) ((Math.random() * ((stop + 1) - start)) + start);

            while (!values.add(intNumber)) {
                intNumber = (int) ((Math.random() * ((stop + 1) - start)) + start);
            }

            String number = String.valueOf(intNumber);

            String line;

            if(padding){
                line = prefix + (format + number).substring(number.length()) + suffix;
            } else {
                line = prefix + number + suffix;
            }
            numberList.add(line);
        }

        return numberList;
    }


    public static String buildFormattedList(List<String> list) {

        StringBuilder builder = new StringBuilder();

        for (String line : list) {

            builder.append(line).append("\n");
        }

        return builder.toString();
    }

    public static boolean checkNumbers(int start, int stop) {
        return start < stop;
    }

    public static void saveFile(Scene scene, String filename, TextArea textArea) throws IOException {

        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setInitialFileName(filename);
        chooser.setTitle("Save as txt format");
        File saveFile = chooser.showSaveDialog(scene.getWindow());

        if(saveFile != null){

            FileWriter writer = new FileWriter(saveFile);
            writer.write(textArea.getText());
            writer.close();
        }

    }
}
