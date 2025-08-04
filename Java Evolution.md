Java 7 features:
==================
String in Switch statement
Multiple exception catching
try with autocloseable resources
Underscores in numeric literals, binary literals
type inference with generic instance
Fork join pool
etc

Java 8:
==================
Lamda expressions
default and static methods in interface
stream api
JDBC-ODBC bridge has been removed
permGen space has been removed
Optional, CompletableFuture,
Arrays.parallelSort(numbers), Nashorn javascript engine
Type Annotations - List<@NonNull String> strings = new ArrayList<>();
-----------------------------------------------
Iterator<String> iterator = fruits.iterator();
iterator.forEachRemaining((fruit) -> System.out.println(fruit));
-----------------------------------------------
## 🔹 **Java 9** (Sep 2017)

### ✅ Key Features:

1. **JPMS (Java Platform Module System)** – Project Jigsaw

   * Introduced modularization with `module-info.java`.

2. **JShell (REPL)**

   * Interactive shell for running Java code snippets.

3. **Private Methods in Interfaces**

4. **Stream API Enhancements**

   * Methods like `takeWhile()`, `dropWhile()`, `ofNullable()`.

5. **Factory Methods for Collections**

   * e.g., `List.of()`, `Set.of()`, `Map.of()`.

6. **Improved `@Deprecated` annotation** - forremoval=true
---

## 🔹 **Java 10** (Mar 2018)

### ✅ Key Features:

1. **Local Variable Type Inference (`var`)**

   * Allows `var` for local variables.

     ```java
     var list = new ArrayList<String>();
     ```

2. **GC Improvements** (Garbage Collector Interface)

3. **Application Class-Data Sharing (AppCDS)**

---

## 🔹 **Java 11** (Sep 2018) – **LTS Release**

### ✅ Key Features:

1. **`var` in Lambda Parameters**

2. **New `String` Methods**

   * `isBlank()`, `lines()`, `strip()`, `repeat()`.

3. **`Files.readString()`, `writeString()`**

4. **`HttpClient` API (Standardized)**

   * Replaces old `HttpURLConnection`.

5. **Running Java files with `java` directly**

   ```bash
   java HelloWorld.java
   ```

6. **Removed Features**

   * Java EE and CORBA modules.

---

## 🔹 **Java 12** (Mar 2019)

### ✅ Key Features:

1. **Switch Expressions (Preview)**

   * `switch` as an expression, with arrow syntax.

     ```java
     int result = switch (day) {
         case MONDAY -> 1;
         case TUESDAY -> 2;
         default -> 0;
     };
     ```

2. **New String Method: `indent()`**

3. **Compact Number Formatting (Intl)**

---

## 🔹 **Java 13** (Sep 2019)

### ✅ Key Features:

1. **Text Blocks (Preview)**

   * Multi-line string literals using `"""`.

     ```java
     String html = """
                   <html>
                       <body>Hello</body>
                   </html>
                   """;
     ```

2. **Refined Switch Expressions (Preview)**

---

## 🔹 **Java 14** (Mar 2020)

### ✅ Key Features:

1. **Records (Preview)**

   * Concise syntax for immutable data classes.

     ```java
     record Person(String name, int age) {}
     ```

2. **Pattern Matching for `instanceof` (Preview)**

   ```java
   if (obj instanceof String s) {
       System.out.println(s.toLowerCase());
   }
   ```

3. **Helpful NullPointerExceptions**

---

## 🔹 **Java 15** (Sep 2020)

### ✅ Key Features:

1. **Sealed Classes (Preview)**

   * Restrict which classes can extend or implement a class/interface.

     ```java
     public sealed class Shape permits Circle, Square {}

     final class Circle extends Shape {}
     ```

2. **Text Blocks (Finalized)**

3. **Hidden Classes**

   * Useful for frameworks generating classes at runtime.

---

## 🔹 **Java 16** (Mar 2021)

### ✅ Key Features:

1. **Records (Finalized)**

2. **Pattern Matching for `instanceof` (Finalized)**

3. **Sealed Classes (2nd Preview)**

4. **JEP 394: Strong Encapsulation of JDK Internals**

---

## 🔹 **Java 17** (Sep 2021) – **LTS Release**

### ✅ Key Features:

1. **Sealed Classes (Finalized)**

2. **Pattern Matching Enhancements**

3. **New `switch` Preview Features**

4. **JEP 356: Enhanced Pseudo-Random Number Generators**

5. **JEP 382: New macOS Rendering Pipeline**

6. **JEP 409: Pattern Matching for `switch` (Preview)**

---

### ✅ Summary Table

| Version     | Key Features                                     |
| ----------- | ------------------------------------------------ |
| **Java 9**  | Modules (JPMS), JShell, `List.of()`              |
| **Java 10** | `var` keyword                                    |
| **Java 11** | New String methods, HttpClient, `var` in lambdas |
| **Java 12** | Switch Expressions (Preview)                     |
| **Java 13** | Text Blocks (Preview)                            |
| **Java 14** | Records (Preview), instanceof pattern matching   |
| **Java 15** | Sealed Classes (Preview), Text Blocks finalized  |
| **Java 16** | Records finalized, Strong Encapsulation          |
| **Java 17** | LTS, Sealed Classes finalized, Pattern Matching  |

---
