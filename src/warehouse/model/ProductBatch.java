package warehouse.model;

import java.time.LocalDate;

public class ProductBatch {

    private String batchId;
    private String barcode;
    private int quantity;
    private LocalDate importDate;
    private LocalDate expiryDate;

    public ProductBatch(
            String batchId,
            String barcode,
            int quantity,
            LocalDate importDate,
            LocalDate expiryDate) {

        this.batchId = batchId;
        this.barcode = barcode;
        this.quantity = quantity;
        this.importDate = importDate;
        this.expiryDate = expiryDate;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}