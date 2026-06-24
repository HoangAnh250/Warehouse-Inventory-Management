package warehouse.model;

public class Batchdeque {

    private ProductBatch[] data;
    private int size;

    public Batchdeque() {
        data = new ProductBatch[10];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addLast(ProductBatch batch) {

        ensureCapacity();

        data[size] = batch;
        size++;
    }

    public ProductBatch peekFirst() {

        if (isEmpty()) {
            return null;
        }

        return data[0];
    }

    public ProductBatch peekLast() {

        if (isEmpty()) {
            return null;
        }

        return data[size - 1];
    }

    public ProductBatch removeFirst() {

        if (isEmpty()) {
            return null;
        }

        ProductBatch removed = data[0];

        for (int i = 0;
                i < size - 1;
                i++) {

            data[i] = data[i + 1];
        }

        data[size - 1] = null;

        size--;

        return removed;
    }

    public ProductBatch removeLast() {

        if (isEmpty()) {
            return null;
        }

        ProductBatch removed
                = data[size - 1];

        data[size - 1] = null;

        size--;

        return removed;
    }

    private void ensureCapacity() {

        if (size < data.length) {
            return;
        }

        ProductBatch[] newData= new ProductBatch[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }
}
