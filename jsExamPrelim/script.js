/*
 Programmer: YOUR FULL NAME - YOUR STUDENT ID
*/

// CSV DATA
const csvData = `
1,Juan Dela Cruz,90
2,Maria Santos,95
3,Pedro Reyes,88
`;

// PARSE CSV
let records = csvData.trim().split("\n").map(line => {
    const [id, name, grade] = line.split(",");
    return { id, name, grade };
});

// RENDER
function render() {
    const tbody = document.getElementById("tableBody");
    tbody.innerHTML = "";

    records.forEach((r, index) => {
        tbody.innerHTML += `
            <tr>
                <td>${r.id}</td>
                <td>${r.name}</td>
                <td>${r.grade}</td>
                <td>
                    <button onclick="deleteRecord(${index})">Delete</button>
                </td>
            </tr>
        `;
    });
}

// CREATE
function addRecord() {
    const id = document.getElementById("id").value;
    const name = document.getElementById("name").value;
    const grade = document.getElementById("grade").value;

    records.push({ id, name, grade });
    render();
}

// DELETE
function deleteRecord(index) {
    records.splice(index, 1);
    render();
}

render();
