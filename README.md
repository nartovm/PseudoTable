```
╔═════════════╗
║ PseudoTable ║
╚═════════════╝
```
PseudoTable lib requires headers and data to print a table
```java
List<String> headers = new ArrayList<>(List.of("Header", "And one more", "Third"));
List<List<String>> data = new ArrayList<>() {{
    add(List.of("v1", "v2", "v3"));
    add(List.of("value 1", "value 2", "value3"));
}};
```
```
╔═════════╤══════════════╤════════╗
║ Header  │ And one more │ Third  ║
╟─────────┼──────────────┼────────╢
║ v1      │ v2           │ v3     ║
╟─────────┼──────────────┼────────╢
║ value 1 │ value 2      │ value3 ║
╚═════════╧══════════════╧════════╝
```
Or the data only
```java
 System.out.println(new PseudoTable(null, data).printTable());
```
```
╔═════════╤═════════╤════════╗
║ v1      │ v2      │ v3     ║
╟─────────┼─────────┼────────╢
║ value 1 │ value 2 │ value3 ║
╚═════════╧═════════╧════════╝
```
Or the header only
```java
 System.out.println(new PseudoTable(headers, null).printTable());
```
```
╔════════╤══════════════╤═══════╗
║ Header │ And one more │ Third ║
╚════════╧══════════════╧═══════╝
```
Multi-line strings are supported
```java
List<String> headers = new ArrayList<>(List.of("Header", "And one more", "Third"));
List<List<String>> data = new ArrayList<>() {{
    add(List.of("v1", "v2", "v3"));
    add(List.of("value 1", "multi-line\r\nvalue 2", "value3"));
}};
```
```
╔═════════╤══════════════╤════════╗
║ Header  │ And one more │ Third  ║
╟─────────┼──────────────┼────────╢
║ v1      │ v2           │ v3     ║
╟─────────┼──────────────┼────────╢
║ value 1 │ multi-line   │ value3 ║
║         │ value 2      │        ║
╚═════════╧══════════════╧════════╝
```
So nested tables are supported
```java
List<String> headers = new ArrayList<>(List.of("Header", "And one more", "Third"));
List<List<String>> nestedTable = new ArrayList<>() {{
    add(List.of("v1", "v2"));
    add(List.of("nested 1", "multi-line\r\nnested 2"));
}};
List<List<String>> data = new ArrayList<>() {{
    add(List.of("v1", "v2", "v3"));
    add(List.of("value 1", "multi-line\r\nvalue 2", new PseudoTable(null, nestedTable).printTable()));
}};
System.out.println(new PseudoTable(headers, data).printTable());
```
```
╔═════════╤══════════════╤══════════════════════════════╗
║ Header  │ And one more │ Third                        ║
╟─────────┼──────────────┼──────────────────────────────╢
║ v1      │ v2           │ v3                           ║
╟─────────┼──────────────┼──────────────────────────────╢
║ value 1 │ multi-line   │ ╔══════════╤══════════════╗  ║
║         │ value 2      │ ║ v1       │ v2           ║  ║
║         │              │ ╟──────────┼──────────────╢  ║
║         │              │ ║ nested 1 │ multi-line   ║  ║
║         │              │ ║          │ nested 2     ║  ║
║         │              │ ╚══════════╧══════════════╝  ║
╚═════════╧══════════════╧══════════════════════════════╝
```
