package com.nartov.maksim.TablePrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {

    // Method to parse a CSV file into headers and data
    public static PseudoTable parseCSV(String filePath, List<String> headers, List<List<String>> data) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) {
                    headers.addAll(parseLine(line));
                } else {
                    data.add(parseLine(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PseudoTable(headers, data);
    }

    // Helper method to parse a line of CSV into an ArrayList
    private static ArrayList<String> parseLine(String line) {
        List<String> values = Arrays.asList(line.split(","));
        ArrayList<String> result = new ArrayList<>();

        for (String value : values) {
            value = value.trim();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            result.add(value);
        }
        return result;
    }

    public static void parseCSVFile_old(String filePath, ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                headers.addAll(List.of(line.split(",")));
                while ((line = br.readLine()) != null) {
                    ArrayList<String> row = new ArrayList<>(List.of(line.split(",")));
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseCSVFile(String filePath, ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                headers.addAll(List.of(line.split(",")));
                while ((line = br.readLine()) != null) {
                    ArrayList<String> row = new ArrayList<>();
                    StringBuilder cell = new StringBuilder();
                    boolean insideQuote = false;
                    for (int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);
                        if (c == '"') {
                            insideQuote = !insideQuote;
                        } else if (c == ',' && !insideQuote) {
                            row.add(cell.toString());
                            cell = new StringBuilder();
                        } else {
                            cell.append(c);
                        }
                    }
                    row.add(cell.toString());
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void parseCSVFile_new(String filePath, ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder lineBuilder = new StringBuilder();
            int charInt;
            boolean insideQuote = false;
            while ((charInt = br.read()) != -1) {
                char c = (char) charInt;
                if (c == '"') {
                    insideQuote = !insideQuote;
                } else if ((c == '\n') && (!insideQuote)) {
                    String line = lineBuilder.toString();
                    lineBuilder = new StringBuilder();
                    if (headers.isEmpty()) {
                        headers.addAll(List.of(line.split(",")));
                    } else {
                        ArrayList<String> row = new ArrayList<>();
                        StringBuilder cell = new StringBuilder();
                        for (int i = 0; i < line.length(); i++) {
                            char currChar = line.charAt(i);
                            if (currChar == ',') {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseCSVFile_newUpdated(String filePath, List<String> headers, List<List<String>> data) {
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

    public static void parseCSVFile_newUpdated_Refactored(String filePath, List<String> headers, List<List<String>> data) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean insideQuote = false;
            while ((line = reader.readLine()) != null) {
                List<String> row = new ArrayList<>();
                StringBuilder cell = new StringBuilder();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '"') {
                        insideQuote = !insideQuote;
                    } else if (c == ',' && !insideQuote) {
                        row.add(cell.toString());
                        cell = new StringBuilder();
                    } else {
                        cell.append(c);
                    }
                }
                row.add(cell.toString());
                if (headers.isEmpty()) {
                    headers.addAll(row);
                } else {
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
