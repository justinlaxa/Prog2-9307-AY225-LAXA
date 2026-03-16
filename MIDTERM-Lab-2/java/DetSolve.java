/**
 * ===================================================
 * Student   : Justin B. Laxa
 * Course    : [MATH 1100 - Linear Algebra]
 * Assignment: Assignment 01 — 3x3 Matrix Determinant Solver
 * School    : University of Perpetual Help System DALTA, Molino Campus
 * Date      : 2026-03-16
 * GitHub    : https://github.com/justinlaxa/Prog2-9307-AY225-LAXA.git
 * Runtime   : Java (compile: javac DetSolve.java | run: java DetSolve)
 *
 * Description:
 *   Computes the determinant of a hardcoded 3x3 matrix using
 *   cofactor expansion along the first row, and prints every
 *   step so the solution is fully human-readable.
 * ===================================================
 */
public class DetSolve {

    // ── SECTION 1: Matrix Declaration ───────────────────────────────────
    // The assigned 3x3 matrix is declared as a 2D Java array.
    // Outer array = rows, inner arrays = individual row values.
    static int[][] matrix = {
        {6, 4, 3},   // Row 1
        {2, 5, 4},   // Row 2
        {3, 1, 2}    // Row 3
    };

    // ── SECTION 2: Matrix Printer ────────────────────────────────────────
    // Accepts a 3x3 array and prints it in a formatted table-like style.
    // Uses box-drawing characters for a clean bordered look.
    public static void printMatrix(int[][] m) {
        System.out.println("\u250c               \u2510");
        for (int[] row : m) {
            System.out.printf("\u2502 %3d  %3d  %3d  \u2502%n", row[0], row[1], row[2]);
        }
        System.out.println("\u2514               \u2518");
    }

    // ── SECTION 3: 2x2 Determinant Helper ───────────────────────────────
    // Computes the determinant of a 2x2 matrix given four scalar values.
    // Called three times during the cofactor expansion step.
    // Parameters: a, b = first row; c, d = second row of the 2x2 sub-matrix.
    public static int computeMinor(int a, int b, int c, int d) {
        // 2x2 determinant formula: ad - bc
        return (a * d) - (b * c);
    }

    // ── SECTION 4: Cofactor Expansion + Step Printer ─────────────────────
    // Performs cofactor expansion along Row 1 of the 3x3 matrix.
    // Prints each minor, each signed cofactor term, and the final determinant.
    // Also flags the matrix as singular if the determinant is zero.
    public static void solveDeterminant(int[][] m) {
        String divider = "===================================================";

        // Print header and the assigned matrix
        System.out.println(divider);
        System.out.println("  3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("  Student: Justin B. Laxa");
        System.out.println("  Assigned Matrix:");
        System.out.println(divider);
        printMatrix(m);
        System.out.println(divider);

        // Compute each 2x2 minor by dropping the corresponding row and column
        int minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        int minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        int minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);

        // Print each minor calculation as a labelled step
        System.out.println("\nExpanding along Row 1 (cofactor expansion):\n");
        System.out.println("  Step 1 - Minor M11: det([" + m[1][1] + "," + m[1][2] + "],[" + m[2][1] + "," + m[2][2] + "])"
            + " = (" + m[1][1] + "x" + m[2][2] + ") - (" + m[1][2] + "x" + m[2][1] + ")"
            + " = " + (m[1][1]*m[2][2]) + " - " + (m[1][2]*m[2][1]) + " = " + minor11);
        System.out.println("  Step 2 - Minor M12: det([" + m[1][0] + "," + m[1][2] + "],[" + m[2][0] + "," + m[2][2] + "])"
            + " = (" + m[1][0] + "x" + m[2][2] + ") - (" + m[1][2] + "x" + m[2][0] + ")"
            + " = " + (m[1][0]*m[2][2]) + " - " + (m[1][2]*m[2][0]) + " = " + minor12);
        System.out.println("  Step 3 - Minor M13: det([" + m[1][0] + "," + m[1][1] + "],[" + m[2][0] + "," + m[2][1] + "])"
            + " = (" + m[1][0] + "x" + m[2][1] + ") - (" + m[1][1] + "x" + m[2][0] + ")"
            + " = " + (m[1][0]*m[2][1]) + " - " + (m[1][1]*m[2][0]) + " = " + minor13);

        // Apply checkerboard signs (+, -, +) to each cofactor term
        int cofactor11 =  m[0][0] * minor11;
        int cofactor12 = -m[0][1] * minor12;
        int cofactor13 =  m[0][2] * minor13;

        System.out.println();
        System.out.println("  Cofactor C11 = (+1) x " + m[0][0] + " x " + minor11 + " = " + cofactor11);
        System.out.println("  Cofactor C12 = (-1) x " + m[0][1] + " x " + minor12 + " = " + cofactor12);
        System.out.println("  Cofactor C13 = (+1) x " + m[0][2] + " x " + minor13 + " = " + cofactor13);

        // Sum the three cofactor terms to get the final determinant
        int det = cofactor11 + cofactor12 + cofactor13;

        String fmt12 = cofactor12 < 0 ? "(" + cofactor12 + ")" : String.valueOf(cofactor12);
        String fmt13 = cofactor13 < 0 ? "(" + cofactor13 + ")" : String.valueOf(cofactor13);
        System.out.println("\n  det(M) = " + cofactor11 + " + " + fmt12 + " + " + fmt13 + "\n");

        System.out.println(divider);
        System.out.println("  DETERMINANT = " + det);
        System.out.println(divider);

        // Explicitly handle the case where the matrix has no inverse
        if (det == 0) {
            System.out.println("  The matrix is SINGULAR - it has no inverse.");
            System.out.println(divider);
        }
    }

    // Entry point — pass the declared matrix into the solver
    public static void main(String[] args) {
        solveDeterminant(matrix);
    }
}