import DataRecord from './DataRecord.js';

const browseBtn = document.getElementById('browseBtn');
const analyzeBtn = document.getElementById('analyzeBtn');
const zoomInBtn = document.getElementById('zoomInBtn');
const zoomOutBtn = document.getElementById('zoomOutBtn');
const fileInput = document.getElementById('fileInput');
const pathField = document.getElementById('pathField');
const dataTable = document.getElementById('dataTable').querySelector('tbody');
const averageLabel = document.getElementById('averageLabel');
const summaryLabel = document.getElementById('summaryLabel');
const searchField = document.getElementById('searchField');

let dataRecords = [];
let fontSize = 14;

// --- Browse CSV ---
browseBtn.addEventListener('click', () => {
    fileInput.value = ''; // reset previous selection
    fileInput.click();    // open file picker
});

fileInput.addEventListener('change', () => {
    if (fileInput.files.length > 0) {
        pathField.value = fileInput.files[0].name;
    }
});

// --- Analyze CSV ---
analyzeBtn.addEventListener('click', () => {
    if (!fileInput.files[0]) {
        alert("Please select a CSV file.");
        return;
    }
    const reader = new FileReader();
    reader.onload = (e) => processCSV(e.target.result);
    reader.readAsText(fileInput.files[0]);
});

// --- Zoom ---
zoomInBtn.addEventListener('click', () => zoomText(2));
zoomOutBtn.addEventListener('click', () => zoomText(-2));

function zoomText(change) {
    fontSize += change;
    if (fontSize < 10) fontSize = 10;
    if (fontSize > 30) fontSize = 30;
    dataTable.parentElement.style.fontSize = fontSize + "px";
}

// --- Search ---
searchField.addEventListener('input', () => {
    const query = searchField.value.toLowerCase();
    [...dataTable.rows].forEach(row => {
        const text = row.cells[0].innerText.toLowerCase();
        row.style.display = text.includes(query) ? '' : 'none';
    });
});

// --- CSV Parser ---
function parseCSVLine(line) {
    const result = [];
    let sb = '';
    let inQuotes = false;
    for (let c of line) {
        if (c === '"') inQuotes = !inQuotes;
        else if (c === ',' && !inQuotes) { result.push(sb.trim()); sb = ''; }
        else sb += c;
    }
    result.push(sb.trim());
    return result;
}

// --- Process CSV ---
function processCSV(csvText) {
    dataRecords = [];
    dataTable.innerHTML = '';
    const lines = csvText.split(/\r?\n/);
    if (lines.length <= 1) {
        alert("CSV is empty or has no data.");
        return;
    }

    const headers = parseCSVLine(lines[0]);
    const titleIndex = headers.findIndex(h => h.toLowerCase() === 'title');
    const salesIndex = headers.findIndex(h => h.toLowerCase() === 'total_sales');

    if (titleIndex === -1 || salesIndex === -1) {
        alert("CSV must have 'title' and 'total_sales' columns.");
        return;
    }

    let totalSales = 0;
    let totalRows = 0;

    for (let i = 1; i < lines.length; i++) {
        if (!lines[i].trim()) continue;
        const parts = parseCSVLine(lines[i]);
        if (parts.length <= salesIndex) continue;

        const title = parts[titleIndex].replace(/"/g, '');
        let sales = parseFloat(parts[salesIndex].replace(/,/g, '').replace(/"/g, ''));
        if (isNaN(sales)) continue;

        let record = dataRecords.find(r => r.title === title);
        if (record) record.addSales(sales);
        else {
            record = new DataRecord(title, sales);
            dataRecords.push(record);
        }
        totalRows++;
        totalSales += sales;
    }

    const average = totalSales / dataRecords.length;

    let lowCount = 0;
    const sortedRecords = [...dataRecords].sort((a,b) => a.totalSales - b.totalSales);
    sortedRecords.forEach(r => {
        const status = r.totalSales < average ? 'LOW' : 'OK';
        if (status === 'LOW') lowCount++;
        const row = dataTable.insertRow();
        row.insertCell().innerText = r.title;
        row.insertCell().innerText = r.totalSales.toFixed(2);
        row.insertCell().innerText = status;
        if (status === 'LOW') row.style.backgroundColor = 'rgb(255,220,220)';
    });

    averageLabel.innerText = `Average Sales: ${average.toFixed(2)} Million Units`;
    summaryLabel.innerText = `Total Rows: ${totalRows} | Low Performing: ${lowCount}`;
}