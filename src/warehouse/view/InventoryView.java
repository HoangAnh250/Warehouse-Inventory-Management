package warehouse.view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import warehouse.model.Product;
import warehouse.model.ProductBatch;

public class InventoryView {

    private final Scanner scanner;

    public InventoryView() {
        scanner = new Scanner(System.in);
    }

    public int displayMenuAndGetChoice() {
        System.out.println();
        System.out.println("===== WAREHOUSE INVENTORY MANAGEMENT =====");
        System.out.println("1. Add product");
        System.out.println("2. Search product by barcode");
        System.out.println("3. Import batch");
        System.out.println("4. Export product");
        System.out.println("5. Deactivate product");
        System.out.println("6. Check expired batches");
        System.out.println("7. Check near-expiry batches");
        System.out.println("8. Display all products");
        System.out.println("9. Display all batches");
        System.out.println("10. Get total quantity by barcode");
        System.out.println("0. Exit");

        return inputInt("Enter your choice: ");
    }

    public String inputString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public int inputInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                int value = Integer.parseInt(
                        scanner.nextLine().trim()
                );

                return value;
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Invalid number. Please enter again."
                );
            }
        }
    }

    public LocalDate inputDate(String message) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException exception) {
                System.out.println(
                        "Invalid date. Use format yyyy-MM-dd."
                );
            }
        }
    }

    public String inputExportStrategy() {
        while (true) {
            System.out.println("1. FIFO");
            System.out.println("2. LIFO");

            int choice = inputInt(
                    "Choose export strategy: "
            );

            if (choice == 1) {
                return "FIFO";
            }

            if (choice == 2) {
                return "LIFO";
            }

            System.out.println(
                    "Invalid strategy. Please choose 1 or 2."
            );
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayProduct(Product product) {
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("------------------------------");
        System.out.println(
                "Barcode: " + product.getBarcode()
        );
        System.out.println(
                "Name: " + product.getName()
        );
        System.out.println(
                "Category: " + product.getCategory()
        );
        System.out.println(
                "Active: " + product.isActive()
        );
    }

    public void displayProducts(Product[] products) {
        if (products == null || products.length == 0) {
            System.out.println("No products found.");
            return;
        }

        for (Product product : products) {
            displayProduct(product);
        }
    }

    public void displayBatch(ProductBatch batch) {
        if (batch == null) {
            System.out.println("Batch not found.");
            return;
        }

        System.out.println("------------------------------");
        System.out.println(
                "Batch ID: " + batch.getBatchId()
        );
        System.out.println(
                "Barcode: " + batch.getBarcode()
        );
        System.out.println(
                "Quantity: " + batch.getQuantity()
        );
        System.out.println(
                "Import date: " + batch.getImportDate()
        );
        System.out.println(
                "Expiry date: " + batch.getExpiryDate()
        );
    }

    public void displayBatches(ProductBatch[] batches) {
        if (batches == null || batches.length == 0) {
            System.out.println("No batches found.");
            return;
        }

        for (ProductBatch batch : batches) {
            displayBatch(batch);
        }
    }
}