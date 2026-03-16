# Programming Assignment 1 — 3×3 Matrix Determinant Solver

## Student Information

| Field | Value |
|---|---|
| **Full Name** | Justin B. Laxa |
| **Section** | [0401 - AY225] |
| **Course** | Math 1100 – Linear Algebra |
| **School** | University of Perpetual Help System DALTA, Molino Campus |

---

## Assignment Title

**Assignment 01 — 3×3 Matrix Determinant Solver**

Implement a 3×3 matrix determinant solver in Java and JavaScript that outputs a complete, human-readable step-by-step solution using cofactor expansion along the first row.

---

## Assigned Matrix

```
┌               ┐
│   6    4    3  │
│   2    5    4  │
│   3    1    2  │
└               ┘
```

---

## How to Run

### Java — `DetSolve.java`

**Requirements:** JDK 8 or later

```bash
# Step 1 — Compile
javac DetSolve.java

# Step 2 — Run
java DetSolve
```

### JavaScript — `determinant_solver.js`

**Requirements:** Node.js v14 or later

```bash
node determinant_solver.js
```

---

## Sample Output

### Java

```
===================================================
  3x3 MATRIX DETERMINANT SOLVER
  Student: Justin B. Laxa
  Assigned Matrix:
===================================================
┌               ┐
│   6    4    3  │
│   2    5    4  │
│   3    1    2  │
└               ┘
===================================================

Expanding along Row 1 (cofactor expansion):

  Step 1 - Minor M11: det([5,4],[1,2]) = (5x2) - (4x1) = 10 - 4 = 6
  Step 2 - Minor M12: det([2,4],[3,2]) = (2x2) - (4x3) = 4 - 12 = -8
  Step 3 - Minor M13: det([2,5],[3,1]) = (2x1) - (5x3) = 2 - 15 = -13

  Cofactor C11 = (+1) x 6 x 6 = 36
  Cofactor C12 = (-1) x 4 x -8 = 32
  Cofactor C13 = (+1) x 3 x -13 = -39

  det(M) = 36 + 32 + (-39)

===================================================
  DETERMINANT = 29
===================================================
```

### JavaScript

```
===================================================
  3x3 MATRIX DETERMINANT SOLVER
  Student: Justin B. Laxa
  Assigned Matrix:
===================================================
┌               ┐
│   6    4    3  │
│   2    5    4  │
│   3    1    2  │
└               ┘
===================================================

Expanding along Row 1 (cofactor expansion):

  Step 1 - Minor M11: det([5,4],[1,2]) = (5x2) - (4x1) = 10 - 4 = 6
  Step 2 - Minor M12: det([2,4],[3,2]) = (2x2) - (4x3) = 4 - 12 = -8
  Step 3 - Minor M13: det([2,5],[3,1]) = (2x1) - (5x3) = 2 - 15 = -13

  Cofactor C11 = (+1) x 6 x 6 = 36
  Cofactor C12 = (-1) x 4 x -8 = 32
  Cofactor C13 = (+1) x 3 x -13 = -39

  det(M) = 36 + 32 + (-39)

===================================================
  DETERMINANT = 29
===================================================
```

---

## Final Determinant Value

> **det(M) = 29**

Both the Java and JavaScript programs produce the same result.

---

## File Structure

```
MIDTERM-Lab-2/
├── java/
│   └── DetSolve.java
├── javascript/
│   └── determinant_solver.js
└── README.md
```