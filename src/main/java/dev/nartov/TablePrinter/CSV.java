package dev.nartov.TablePrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    // Method to parse a CSV file into headers and data
    //newUpdated
    public static void parseCSVFile(String filePath, List<String> headers, List<List<String>> data) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder lineBuilder = new StringBuilder();
            int charInt;
            boolean insideQuote = false;
            boolean linebreakBefore = false;
            while ((charInt = br.read()) != -1) {
                char c = (char) charInt;
                if (c == '"') {
                    insideQuote = !insideQuote;
                    lineBuilder.append('"');
                } else if ((c == '\n' || c == '\r')&&!insideQuote) {
                    if (linebreakBefore) {
                        linebreakBefore = false;
                        continue;
                    }
                    linebreakBefore = true;
                    if (!insideQuote) {
                        String line = lineBuilder.toString();
                        lineBuilder = new StringBuilder();
                        if (headers.isEmpty()) {
                            headers.addAll(List.of(line.split(",")));
                        } else {
                            ArrayList<String> row = new ArrayList<>();
                            StringBuilder cell = new StringBuilder();
                            for (int i = 0; i < line.length(); i++) {
                                char currChar = line.charAt(i);
                                if (currChar == '"') {
                                    insideQuote = !insideQuote;
                                } else if (currChar == ',' && !insideQuote) {
                                    row.add(cell.toString());
                                    cell = new StringBuilder();
                                } else {
                                    cell.append(currChar);
                                }
                            }
                            row.add(cell.toString());
                            data.add(row);
                        }
                    } else {
                        lineBuilder.append(c);
                    }
                } else {
                    lineBuilder.append(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
