/**
 * ===================================================
 * Student   : Justin B. Laxa
 * Course    : [MATH 1100 - Linear Algebra]
 * Assignment: Assignment 01 — 3x3 Matrix Determinant Solver
 * School    : University of Perpetual Help System DALTA, Molino Campus
 * Date      : 2026-03-16
 * GitHub    : https://github.com/justinlaxa/Prog2-9307-AY225-LAXA.git
 * Runtime   : Node.js (run with: node determinant_solver.js)
 *
 * Description:
 *   Computes the determinant of a hardcoded 3x3 matrix using
 *   cofactor expansion along the first row, and prints every
 *   step so the solution is fully human-readable.
 * ===================================================
 */

// ── SECTION 1: Matrix Declaration ───────────────────────────────────
// The assigned 3x3 matrix is declared as a 2D JavaScript array.
// Outer array = rows, inner arrays = individual row values.
const matrix = [
    [6, 4, 3],   // Row 1
    [2, 5, 4],   // Row 2
    [3, 1, 2]    // Row 3
];

// ── SECTION 2: Matrix Printer ────────────────────────────────────────
// Accepts a 3x3 array and prints it in a formatted table-like style.
// Uses template literals for clean string interpolation.
function printMatrix(m) {
    console.log(`┌               ┐`);
    m.forEach(row => {
        const fmt = row.map(v => v.toString().padStart(3)).join("  ");
        console.log(`│ ${fmt}  │`);
    });
    console.log(`└               ┘`);
}

// ── SECTION 3: 2×2 Determinant Helper ───────────────────────────────
// Computes the determinant of a 2x2 matrix given four scalar values.
// Called three times during the cofactor expansion step.
// Parameters: a, b = first row; c, d = second row of the 2x2 sub-matrix.
function computeMinor(a, b, c, d) {
    // 2x2 determinant formula: ad - bc
    return (a * d) - (b * c);
}

// ── SECTION 4: Cofactor Expansion + Step Printer ─────────────────────
// Performs cofactor expansion along Row 1 of the 3x3 matrix.
// Prints each minor, each signed cofactor term, and the final determinant.
// Also flags the matrix as singular if the determinant is zero.
function solveDeterminant(m) {
    const divider = "===================================================";

    // Print header and the assigned matrix
    console.log(divider);
    console.log("  3x3 MATRIX DETERMINANT SOLVER");
    console.log("  Student: Justin B. Laxa");
    console.log("  Assigned Matrix:");
    console.log(divider);
    printMatrix(m);
    console.log(divider);

    // Compute each 2x2 minor by dropping the corresponding row and column
    const minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
    const minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
    const minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);

    // Print each minor calculation as a labelled step
    console.log("\nExpanding along Row 1 (cofactor expansion):\n");
    console.log(`  Step 1 - Minor M11: det([${m[1][1]},${m[1][2]}],[${m[2][1]},${m[2][2]}])`
        + ` = (${m[1][1]}x${m[2][2]}) - (${m[1][2]}x${m[2][1]})`
        + ` = ${m[1][1]*m[2][2]} - ${m[1][2]*m[2][1]} = ${minor11}`);
    console.log(`  Step 2 - Minor M12: det([${m[1][0]},${m[1][2]}],[${m[2][0]},${m[2][2]}])`
        + ` = (${m[1][0]}x${m[2][2]}) - (${m[1][2]}x${m[2][0]})`
        + ` = ${m[1][0]*m[2][2]} - ${m[1][2]*m[2][0]} = ${minor12}`);
    console.log(`  Step 3 - Minor M13: det([${m[1][0]},${m[1][1]}],[${m[2][0]},${m[2][1]}])`
        + ` = (${m[1][0]}x${m[2][1]}) - (${m[1][1]}x${m[2][0]})`
        + ` = ${m[1][0]*m[2][1]} - ${m[1][1]*m[2][0]} = ${minor13}`);

    // Apply checkerboard signs (+, -, +) to each cofactor term
    const cofactor11 =  m[0][0] * minor11;
    const cofactor12 = -m[0][1] * minor12;
    const cofactor13 =  m[0][2] * minor13;

    console.log();
    console.log(`  Cofactor C11 = (+1) x ${m[0][0]} x ${minor11} = ${cofactor11}`);
    console.log(`  Cofactor C12 = (-1) x ${m[0][1]} x ${minor12} = ${cofactor12}`);
    console.log(`  Cofactor C13 = (+1) x ${m[0][2]} x ${minor13} = ${cofactor13}`);

    // Sum the three cofactor terms to get the final determinant
    const det = cofactor11 + cofactor12 + cofactor13;

    const fmt12 = cofactor12 < 0 ? `(${cofactor12})` : `${cofactor12}`;
    const fmt13 = cofactor13 < 0 ? `(${cofactor13})` : `${cofactor13}`;
    console.log(`\n  det(M) = ${cofactor11} + ${fmt12} + ${fmt13}\n`);

    console.log(divider);
    console.log(`  DETERMINANT = ${det}`);
    console.log(divider);

    // Explicitly handle the case where the matrix has no inverse
    if (det === 0) {
        console.log("  The matrix is SINGULAR - it has no inverse.");
        console.log(divider);
    }
}

// Entry point — pass the declared matrix into the solver
solveDeterminant(matrix);