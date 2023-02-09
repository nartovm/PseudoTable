╔═════════════╗
║ PseudoTable ║
╚═════════════╝
PseudoTable lib requires headers and data to print a table
```java
        List<String> headers = new ArrayList<>(List.of("Header", "And one more", "Third"));
        List<List<String>> data = new ArrayList<>(){{
                add(List.of("v1", "v2", "v3"));
                add(List.of("value 1", "multi-line\r\nvalue 2", "value 3"));
            }};
        System.out.println(new PseudoTable(headers, data).printTable());
```
```
╔═════════╤══════════════╤═════════╗
║ Header  │ And one more │ Third   ║
╟─────────┼──────────────┼─────────╢
║ v1      │ v2           │ v3      ║
╟─────────┼──────────────┼─────────╢
║ value 1 │ multi-line   │ value 3 ║
║         │ value 2      │         ║
╚═════════╧══════════════╧═════════╝
```
Or only one of them
```java
 System.out.println(new PseudoTable(null, data).printTable());
```
```
╔═════════╤══════════════╤═════════╗
║ v1      │ v2           │ v3      ║
╟─────────┼──────────────┼─────────╢
║ value 1 │ multi-line   │ value 3 ║
║         │ value 2      │         ║
╚═════════╧══════════════╧═════════╝
```
```java
 System.out.println(new PseudoTable(headers, null).printTable());
```
```
╔════════╤══════════════╤═══════╗
║ Header │ And one more │ Third ║
╚════════╧══════════════╧═══════╝
```
