package dev.nartov.TablePrinter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PseudoTable {
    // Character symbols for table borders
    private static final char BORDER_TOP_LEFT = '\u2554';
    private static final char BORDER_TOP_RIGHT = '\u2557';
    private static final char BORDER_BOTTOM_LEFT = '\u255A';
    private static final char BORDER_BOTTOM_RIGHT = '\u255D';
    private static final char BORDER_VERTICAL = '\u2551';
    private static final char BORDER_HORIZONTAL = '\u2550';
    private static final char BORDER_CROSS = '\u2564';
    private static final char BORDER_CROSS_BOTTOM = '\u2567';
    private static final char BORDER_CROSS_LEFT = '\u255F';
    private static final char BORDER_CROSS_RIGHT = '\u2562';
    private static final char INNER_HORIZONTAL = '\u2500';
    private static final char SYMBOL_CROSS = '\u253C';
    private static final char SYMBOL_CROSS_HEADER = '\u256A';
    private static final char INNER_VERTICAL = '\u2502';

    private static final char HEADER_CROSS_LEFT = '\u2560';
    private static final char HEADER_CROSS_RIGHT = '\u2563';

    // ArrayList to store the headers and data of the table
    private List<String> headers;
    private List<List<String>> data;

    private int[] columnWidths;

    // Constructor to initialize headers and data
    public PseudoTable(List<String> headers, List<List<String>> data) {
        this.headers = headers;
        this.data = data;
        columnWidths = getColumnWidths(this.headers, this.data);
    }

    // Helper method to print the table header
    private String printTableHeaderLine() {
        int size = headers != null ? headers.size() : data.get(0).size();
        StringBuilder sb = new StringBuilder();
        sb.append(BORDER_TOP_LEFT);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < columnWidths[i] + 2; j++) {
                sb.append(BORDER_HORIZONTAL);
            }
            if (i < size - 1) {
                sb.append(BORDER_CROSS);
            }
        }
        sb.append(BORDER_TOP_RIGHT + "\n");
        return sb.toString();
    }

    private String printTableHeaderString() {
        StringBuilder sb = new StringBuilder();
        sb.append(printLineOfData(headers));
        if (data != null) sb.append(printHorizontalHeader());
        if (data != null) sb.append("\n");
        return sb.toString();
    }

    // Helper method to print the table data
    private String printTableData() {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < data.size(); k++) {
            List<String> row = data.get(k);
            sb.append(printLineOfData(row));
            if (k < data.size() - 1) {
                sb.append(printHorizontal());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private StringBuilder printLineOfData(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(BORDER_VERTICAL);
        List<String> nextLine = null;
        for (int i = 0; i < list.size(); i++) {
            String cell = list.get(i) != null ? list.get(i) : "";
            int index = lineBreakIndex(cell);
            if (index != -1) {
                if (nextLine == null) {
                    nextLine = new ArrayList<>(Collections.nCopies(list.size(), null));
                }
                nextLine.set(i, cell.substring(index));
                cell = cell.substring(0, index).replaceAll("\\r?\\n", "");
                sb.append(" ").append(cell);
            } else {
                sb.append(" ").append(cell);
            }
            for (int j = 0; j < columnWidths[i] - cell.length() + 1; j++) {
                sb.append(" ");
            }
            if (i < list.size() - 1) sb.append(INNER_VERTICAL);
            else sb.append(BORDER_VERTICAL);
        }
        sb.append("\n");
        if (nextLine != null) {
            sb.append(printLineOfData(nextLine));
        }
        return sb;
    }

    private static int lineBreakIndex(String input) {
        int index = input.indexOf("\r\n");
        if (index == -1) {
            index = input.indexOf("\n");
            if (index == -1) {
                index = input.indexOf("\r");
                if (index == -1)
                    return index;
                else index += 1;
            } else {
                index += 1;
            }
        } else {
            index += 2;
        }
        return index;
    }

    // Helper method to print the table footer
    private String printTableFooter() {
        int size = headers != null ? headers.size() : data.get(0).size();
        StringBuilder sb = new StringBuilder();
        sb.append(BORDER_BOTTOM_LEFT);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < columnWidths[i] + 2; j++) {
                sb.append(BORDER_HORIZONTAL);
            }
            if (i < size - 1) {
                sb.append(BORDER_CROSS_BOTTOM);
            }
        }
        sb.append(BORDER_BOTTOM_RIGHT);
        return sb.toString();
    }

    private StringBuilder printHorizontal() {
        StringBuilder sb = new StringBuilder();
        sb.append(BORDER_CROSS_LEFT);
        for (int i = 0; i < columnWidths.length; i++) {
            for (int j = 0; j < columnWidths[i] + 2; j++) {
                sb.append(INNER_HORIZONTAL);
            }
            if (i < columnWidths.length - 1) sb.append(SYMBOL_CROSS);
        }
        sb.append(BORDER_CROSS_RIGHT);
        return sb;
    }

    private StringBuilder printHorizontalHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(HEADER_CROSS_LEFT);
        for (int i = 0; i < columnWidths.length; i++) {
            for (int j = 0; j < columnWidths[i] + 2; j++) {
                sb.append(BORDER_HORIZONTAL);
            }
            if (i < columnWidths.length - 1) sb.append(SYMBOL_CROSS_HEADER);
        }
        sb.append(HEADER_CROSS_RIGHT);
        return sb;
    }

    public static int[] getColumnWidths(List<String> headers, List<List<String>> data) {
        int[] maxLengths = new int[]{};
        if (headers != null) {
            maxLengths = new int[headers.size()];
            for (int i = 0; i < headers.size(); i++) {
                maxLengths[i] = headers.get(i).length();
            }
        }
        if (data != null) {
            if (headers == null) maxLengths = new int[data.get(0).size()];
            for (List<String> row : data) {
                for (int i = 0; i < row.size(); i++) {
                    int length = getCellLength(row.get(i));
                    if (length > maxLengths[i]) {
                        maxLengths[i] = length;
                    }
                }
            }
        }
        return maxLengths;
    }


    private static int getCellLength(String s) {
        int length = -1;
        int index = lineBreakIndex(s);
        if (index != -1) {
            int lengthOuter = s.substring(0, index).length();
            int lengthInner = getCellLength(s.substring(index));
            length = Math.max(lengthInner, lengthOuter);
        } else {
            return s.length();
        }
        return length;
    }

    // Main method to print the table
    public String printTable() {
        String result = "";
        result += printTableHeaderLine();
        if (headers != null) result += printTableHeaderString();
        if (data != null) result += printTableData();
        result += printTableFooter();
        return result;
    }

    public static void main(String[] args) {

    }
}
