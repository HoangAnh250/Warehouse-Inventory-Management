package warehouse.model;

public class ProductBatch {
    private String batchId;
    private String barcode;
    private int quantity;
    private int importDate;
    private int expiryDate;
    public ProductBatch(String batchId, String barcode, int quantity, int importDate, int expiryDate) {
        this.batchId = batchId;
        this.barcode = barcode;
        this.quantity = quantity;
        this.importDate = importDate;
        this.expiryDate = expiryDate;
    }
    public String getBatchId() {
        return batchId;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getImportDate() {
        return importDate;
    }
    public void setImportDate(int importDate) {
        this.importDate = importDate;
    }
    public int getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }

}
