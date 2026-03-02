export default class DataRecord {
    constructor(title, sales) {
        this.title = title;
        this.totalSales = sales;
    }

    addSales(sales) {
        this.totalSales += sales;
    }
}