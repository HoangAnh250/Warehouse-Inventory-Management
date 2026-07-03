package warehouse.model;

public class Batchdeque {

    private class Node {

        ProductBatch data;
        Node next;
        Node prev;

        Node(ProductBatch data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Batchdeque() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addLast(ProductBatch batch) {

        Node newNode = new Node(batch);

        if (isEmpty()) {

            head = newNode;
            tail = newNode;

        } else {

            tail.next = newNode;
            newNode.prev = tail;

            tail = newNode;
        }

        size++;
    }

    public ProductBatch peekFirst() {

        if (isEmpty()) {
            return null;
        }

        return head.data;
    }

    public ProductBatch peekLast() {

        if (isEmpty()) {
            return null;
        }

        return tail.data;
    }

    public ProductBatch removeFirst() {

        if (isEmpty()) {
            return null;
        }

        ProductBatch removed = head.data;

        if (size == 1) {

            head = null;
            tail = null;

        } else {

            head = head.next;
            head.prev = null;
        }

        size--;

        return removed;
    }

    public ProductBatch removeLast() {

        if (isEmpty()) {
            return null;
        }

        ProductBatch removed = tail.data;

        if (size == 1) {

            head = null;
            tail = null;

        } else {

            tail = tail.prev;
            tail.next = null;
        }

        size--;

        return removed;
    }

}
