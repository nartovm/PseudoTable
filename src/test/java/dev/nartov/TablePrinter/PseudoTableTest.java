package dev.nartov.TablePrinter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static dev.nartov.TablePrinter.CSV.parseCSVFile;

class PseudoTableTest {

    @Test
    void printParsedTableTest1() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile("src/test/resources/1.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/1.txt")));
        String actual = new PseudoTable(headers, data).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printParsedTableTest2() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile("src/test/resources/2.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/2.txt")));
        String actual = new PseudoTable(headers, data).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printParsedTableTest3() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile("src/test/resources/3.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/3.txt")));
        String actual = new PseudoTable(headers, data).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printParsedTableNoHeadersTest() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile("src/test/resources/3.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/3_NoHeader.txt")));
        String actual = new PseudoTable(null, data).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void printParsedTableNoDataTest() throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        parseCSVFile("src/test/resources/3.csv", headers, data);
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/3_NoData.txt")));
        String actual = new PseudoTable(headers, null).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void simpleNestedPrintTest() throws IOException {
        List<String> nestedHeader = new ArrayList<>(List.of("Nested header", "Header2"));
        List<List<String>> nestedData = new ArrayList<>() {{
            add(List.of("v1", "v2"));
            add(List.of("nested 1", "multi-line\r\nnested 2"));
        }};
        List<String> headers = new ArrayList<>(List.of("Header", "And one more", "Third"));
        List<List<String>> data = new ArrayList<>() {{
            add(List.of("v1", "v2", "v3"));
            add(List.of("value 1", "multi-line\r\nvalue 2", new PseudoTable(nestedHeader, nestedData).printTable()));
        }};
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/SimpleNested.txt")));
        String actual = new PseudoTable(headers, data).printTable();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void simpleTest() throws IOException {
        List<String> headers = new ArrayList<>(List.of("Header", "And one more", "Third"));
        List<List<String>> data = new ArrayList<>() {{
            add(List.of("v1", "v2", "v3"));
            add(List.of("value 1", "multi-line\r\nvalue 2", ""));
        }};
        String actual = new PseudoTable(headers, data).printTable();
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/SimpleTest.txt")));
        Assertions.assertEquals(expected, actual);
    }

}