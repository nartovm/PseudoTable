package com.nartov.maksim.TablePrinter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.nartov.maksim.TablePrinter.CSV.parseCSVFile_newUpdated;
import static com.nartov.maksim.TablePrinter.CSV.parseCSVFile_newUpdated_Refactored;

class CSVTest {

    @Test
    void parseCSVFile_newUpdatedTest() {
        String filepath = "E:\\BW\\LS2\\ReportAutomation\\src\\main\\resources\\Slices\\3.csv";
        java.util.List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile_newUpdated(filepath, headers, data);


        java.util.List<String> headersRefactored = new ArrayList<>();
        List<List<String>> dataRefactored = new ArrayList<>();
        parseCSVFile_newUpdated_Refactored(filepath, headersRefactored, dataRefactored);

        Assertions.assertEquals(data,dataRefactored);
        Assertions.assertEquals(headers,headersRefactored);
    }
}