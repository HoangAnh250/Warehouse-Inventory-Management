package warehouse.controller;

import java.time.LocalDate;
import warehouse.model.InventoryManagement;
import warehouse.model.Product;
import warehouse.model.ProductBatch;
import warehouse.view.InventoryView;

public class InventoryController {

    private final InventoryManagement management;
    private final InventoryView view;

    public InventoryController(
            InventoryManagement management,
            InventoryView view) {

        this.management = management;
        this.view = view;
    }

    public void run() {

        while (true) {

            int choice = view.displayMenuAndGetChoice();

            switch (choice) {

                case 1:
                    handleAddProduct();
                    break;

                case 2:
                    handleSearchProduct();
                    break;

                case 3:
                    handleImportBatch();
                    break;

                case 4:
                    handleExportProduct();
                    break;

                case 5:
                    handleDeactivateProduct();
                    break;

                case 6:
                    handleCheckExpired();
                    break;

                case 7:
                    handleCheckNearExpiry();
                    break;

                case 8:
                    handleDisplayAllProducts();
                    break;

                case 9:
                    handleDisplayAllBatches();
                    break;

                case 10:
                    handleGetTotalQuantity();
                    break;

                case 0:
                    view.displayMessage("Program ended.");
                    return;

                default:
                    view.displayMessage("Invalid choice.");
            }
        }
    }

    private void handleAddProduct() {

        String barcode = view.inputString(
                "Enter barcode: ");

        String name = view.inputString(
                "Enter product name: ");

        String category = view.inputString(
                "Enter category: ");

        boolean result = management.addProduct(
                barcode,
                name,
                category);

        if (result) {
            view.displayMessage(
                    "Product added successfully.");
        } else {
            view.displayMessage(
                    "Cannot add product.");
        }
    }

    private void handleSearchProduct() {

        String barcode = view.inputString(
                "Enter barcode: ");

        Product product = management.searchByBarcode(barcode);

        view.displayProduct(product);
    }

    private void handleImportBatch() {

        String batchId = view.inputString(
                "Enter batch ID: ");

        String barcode = view.inputString(
                "Enter product barcode: ");

        int quantity = view.inputInt(
                "Enter quantity: ");

        LocalDate importDate = view.inputDate(
                "Enter import date yyyy-MM-dd: ");

        LocalDate expiryDate = view.inputDate(
                "Enter expiry date yyyy-MM-dd: ");

        boolean result = management.importBatch(
                batchId,
                barcode,
                quantity,
                importDate,
                expiryDate);

        if (result) {
            view.displayMessage(
                    "Batch imported successfully.");
        } else {
            view.displayMessage(
                    "Cannot import batch.");
        }
    }

    private void handleExportProduct() {

        String barcode = view.inputString(
                "Enter product barcode: ");

        int quantity = view.inputInt(
                "Enter export quantity: ");

        String strategy = view.inputExportStrategy();

        boolean result = management.exportProduct(
                barcode,
                quantity,
                strategy);

        if (result) {
            view.displayMessage(
                    "Product exported successfully.");
        } else {
            view.displayMessage(
                    "Cannot export product.");
        }
    }

    private void handleDeactivateProduct() {

        String barcode = view.inputString(
                "Enter product barcode: ");

        boolean result = management.deactivateProduct(barcode);

        if (result) {
            view.displayMessage(
                    "Product deactivated successfully.");
        } else {
            view.displayMessage(
                    "Cannot deactivate product.");
        }
    }

    private void handleCheckExpired() {

        LocalDate currentDate = view.inputDate(
                "Enter current date yyyy-MM-dd: ");

        ProductBatch[] expiredBatches = management.checkExpired(currentDate);

        view.displayBatches(expiredBatches);
    }

    private void handleCheckNearExpiry() {

        LocalDate currentDate = view.inputDate(
                "Enter current date yyyy-MM-dd: ");

        int warningDays = view.inputInt(
                "Enter warning days: ");

        ProductBatch[] nearExpiryBatches = management.checkNearExpiry(
                currentDate,
                warningDays);

        view.displayBatches(nearExpiryBatches);
    }

    private void handleDisplayAllProducts() {

        Product[] products = management.getAllProducts();

        view.displayProducts(products);
    }

    private void handleDisplayAllBatches() {

        ProductBatch[] batches = management.getAllBatches();

        view.displayBatches(batches);
    }

    private void handleGetTotalQuantity() {

        String barcode = view.inputString(
                "Enter product barcode: ");

        int totalQuantity = management.getTotalQuantity(barcode);

        view.displayMessage(
                "Total quantity: " + totalQuantity);
    }
}