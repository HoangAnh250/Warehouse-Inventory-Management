package warehouse.model;

import java.time.LocalDate;

public class InventoryManagement {
    private ProductBST productBST;
    private BatchArrayList batchList;

    public InventoryManagement() {
        productBST = new ProductBST();
        batchList = new BatchArrayList();
    }

    public ProductBST getProductBST() {
        return productBST;
    }

    public boolean addProduct(String barcode, String name, String category) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (category == null || category.trim().isEmpty()) {
            return false;
        }
        barcode = barcode.trim();
        name = name.trim();
        category = category.trim();
        Product existingProduct = productBST.searchByBarcode(barcode);
        if (existingProduct != null) {
            return false;
        }
        Product newProduct = new Product(barcode, name, category, true);
        return productBST.insert(newProduct);
    }

    public Product searchByBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return null;
        }
        barcode = barcode.trim();
        return productBST.searchByBarcode(barcode);
    }

    public boolean deactivateProduct(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return false;
        }
        barcode = barcode.trim();
        Product product = productBST.searchByBarcode(barcode);

        if (product == null) {
            return false;
        }

        if (!product.isActive()) {
            return false;
        }
        product.setActive(false);
        return true;
    }

    public Product[] getAllProducts() {
        return productBST.getProductsInOrder();
    }

    public boolean importBatch(String batchId, String barcode, int quantity, LocalDate importDate,
            LocalDate expiryDate) {
        if (batchId == null || batchId.trim().isEmpty()) {
            return false;
        }
        if (barcode == null || barcode.trim().isEmpty()) {
            return false;
        }
        if (quantity <= 0) {
            return false;
        }
        if (importDate == null || expiryDate == null || importDate.isAfter(expiryDate)) {
            return false;
        }
        batchId = batchId.trim();
        barcode = barcode.trim();
        Product product = productBST.searchByBarcode(barcode);
        if (product == null) {
            return false;
        }
        if (!product.isActive()) {
            return false;
        }
        if (batchList.findByBatchId(batchId) != null) {
            return false;
        }
        ProductBatch newBatch = new ProductBatch(batchId, barcode, quantity, importDate, expiryDate);
        batchList.add(newBatch);
        return true;

    }

    public boolean exportProduct(String barcode,int exportQuantity,String strategy) {

        if (barcode == null || barcode.trim().isEmpty()) {
            return false;
        }

        if (exportQuantity <= 0) {
            return false;
        }

        if (strategy == null || strategy.trim().isEmpty()) {
            return false;
        }

        barcode = barcode.trim();
        strategy = strategy.trim().toUpperCase();

        if (!strategy.equals("FIFO")
                && !strategy.equals("LIFO")) {

            return false;
        }

        Product product = productBST.searchByBarcode(barcode);

        if (product == null) {
            return false;
        }

        int totalStock = batchList.getTotalQuantity(barcode);

        if (totalStock < exportQuantity) {
            return false;
        }

        ProductBatch[] matchingBatches = batchList.getBatchesByBarcode(barcode);

        Batchdeque selectedBatches = new Batchdeque();

        for (int i = 0; i < matchingBatches.length; i++) {

            selectedBatches.addLast(
                    matchingBatches[i]);
        }

        int remainingQuantity = exportQuantity;

        while (remainingQuantity > 0
                && !selectedBatches.isEmpty()) {

            ProductBatch selectedBatch;

            if (strategy.equals("FIFO")) {
                selectedBatch = selectedBatches.removeFirst();
            } else {
                selectedBatch = selectedBatches.removeLast();
            }

            if (selectedBatch == null) {
                return false;
            }

            int batchQuantity = selectedBatch.getQuantity();

            if (batchQuantity <= remainingQuantity) {

                remainingQuantity -= batchQuantity;

                selectedBatch.setQuantity(0);

            } else {

                selectedBatch.setQuantity(
                        batchQuantity - remainingQuantity);

                remainingQuantity = 0;
            }
        }

        removeEmptyBatches();

        return remainingQuantity == 0;
    }

    private void removeEmptyBatches() {

        int index = 0;

        while (index < batchList.size()) {

            ProductBatch batch = batchList.get(index);

            if (batch.getQuantity() == 0) {
                batchList.remove(index);
            } else {
                index++;
            }
        }
    }

    public ProductBatch[] checkExpired(LocalDate currentDate) {

        if (currentDate == null) {
            return new ProductBatch[0];
        }

        int count = 0;

        for (int i = 0; i < batchList.size(); i++) {

            ProductBatch batch = batchList.get(i);

            if (batch.getExpiryDate()
                    .isBefore(currentDate)) {

                count++;
            }
        }

        ProductBatch[] expiredBatches = new ProductBatch[count];

        int resultIndex = 0;

        for (int i = 0; i < batchList.size(); i++) {

            ProductBatch batch = batchList.get(i);

            if (batch.getExpiryDate()
                    .isBefore(currentDate)) {

                expiredBatches[resultIndex] = batch;

                resultIndex++;
            }
        }

        return expiredBatches;
    }

    public ProductBatch[] checkNearExpiry(LocalDate currentDate,int warningDays) {

        if (currentDate == null
                || warningDays < 0) {

            return new ProductBatch[0];
        }

        LocalDate warningDate = currentDate.plusDays(warningDays);

        int count = 0;

        for (int i = 0; i < batchList.size(); i++) {

            ProductBatch batch = batchList.get(i);

            LocalDate expiryDate = batch.getExpiryDate();

            boolean notExpired = !expiryDate.isBefore(currentDate);

            boolean withinWarningPeriod = !expiryDate.isAfter(warningDate);

            if (notExpired
                    && withinWarningPeriod) {

                count++;
            }
        }

        ProductBatch[] nearExpiryBatches = new ProductBatch[count];

        int resultIndex = 0;

        for (int i = 0; i < batchList.size(); i++) {

            ProductBatch batch = batchList.get(i);

            LocalDate expiryDate = batch.getExpiryDate();

            boolean notExpired = !expiryDate.isBefore(currentDate);

            boolean withinWarningPeriod = !expiryDate.isAfter(warningDate);

            if (notExpired
                    && withinWarningPeriod) {

                nearExpiryBatches[resultIndex] = batch;

                resultIndex++;
            }
        }

        return nearExpiryBatches;
    }

    public int getTotalQuantity(
            String barcode) {

        if (barcode == null
                || barcode.trim().isEmpty()) {

            return 0;
        }

        return batchList.getTotalQuantity(
                barcode.trim());
    }

    public ProductBatch[] getAllBatches() {
        return batchList.toArray();
    }

}
