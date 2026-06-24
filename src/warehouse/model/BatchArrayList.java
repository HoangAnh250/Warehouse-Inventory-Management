package warehouse.model;

public class BatchArrayList {

    private ProductBatch[] data;
    private int size;

    public BatchArrayList() {
        data = new ProductBatch[10];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {

        if (size < data.length) {
            return;
        }

        ProductBatch[] newData
                = new ProductBatch[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    public void add(ProductBatch batch) {

        ensureCapacity();

        data[size] = batch;
        size++;
    }

    public ProductBatch get(int index) {

        if (index < 0 || index >= size) {
            return null;
        }

        return data[index];
    }

    public void set(int index,
            ProductBatch batch) {

        if (index < 0 || index >= size) {
            return;
        }

        data[index] = batch;
    }

    public ProductBatch remove(int index) {

        if (index < 0 || index >= size) {
            return null;
        }

        ProductBatch removed = data[index];

        for (int i = index;
                i < size - 1;
                i++) {

            data[i] = data[i + 1];
        }

        data[size - 1] = null;

        size--;

        return removed;
    }

    public ProductBatch findByBatchId(
            String batchId) {

        for (int i = 0; i < size; i++) {

            if (data[i].getBatchId()
                    .equals(batchId)) {

                return data[i];
            }
        }

        return null;
    }

    public int getTotalQuantity(
            String barcode) {

        int total = 0;

        for (int i = 0; i < size; i++) {

            if (data[i].getBarcode()
                    .equals(barcode)) {

                total += data[i].getQuantity();
            }
        }

        return total;
    }

    public ProductBatch[] getBatchesByBarcode(
            String barcode) {

        int count = 0;

        for (int i = 0; i < size; i++) {

            if (data[i].getBarcode()
                    .equals(barcode)) {

                count++;
            }
        }

        ProductBatch[] result
                = new ProductBatch[count];

        int index = 0;

        for (int i = 0; i < size; i++) {

            if (data[i].getBarcode()
                    .equals(barcode)) {

                result[index++] = data[i];
            }
        }

        return result;
    }

    public ProductBatch[] toArray() {

        ProductBatch[] result
                = new ProductBatch[size];

        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }

        return result;
    }
}
