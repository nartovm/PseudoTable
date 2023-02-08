package com.nartov.maksim.TablePrinter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.nartov.maksim.TablePrinter.CSV.parseCSVFile_newUpdated;

class PseudoTableTest {

    @Test
    void printTableTest1() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile_newUpdated("src/test/resources/1.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/1.txt")));
        String actual = new PseudoTable(headers, data).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printTableTest2() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile_newUpdated("src/test/resources/2.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/2.txt")));
        String actual = new PseudoTable(headers, data).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printTableTest3() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile_newUpdated("src/test/resources/3.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/3.txt")));
        String actual = new PseudoTable(headers, data).printTable();
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printNoHeadersTest() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile_newUpdated("src/test/resources/3.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/3_NoHeader.txt")));
        String actual = new PseudoTable(null, data).printTable();
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

}