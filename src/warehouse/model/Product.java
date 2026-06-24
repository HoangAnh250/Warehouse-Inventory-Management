package warehouse.model;

public class Product {
    private String barcode;
    private String name;
    private String category;
    private boolean active;
    public Product(String barcode, String name, String category, boolean active) {
        this.barcode = barcode;
        this.name = name;
        this.category = category;
        this.active = active;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
