package warehouse.experiment;

import warehouse.model.ProductBatch;

public class BatchLinkedList {
    private static class Node {
        ProductBatch info;
        Node next;

        public Node(ProductBatch info) {
            this.info = info;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;

    public BatchLinkedList() {
        this.head = null;
        this.tail = null;

    }

    public boolean isEmpty() {
        return head == null;
    }

    public void addLast(ProductBatch batch) {
        if (batch == null) {
            return;
        }
        Node newNode = new Node(batch);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;

        }

    }

    public void addFirst(ProductBatch batch) {
        if (batch == null) {
            return;
        }
        Node newNode = new Node(batch);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    public ProductBatch removeFirst() {
        if (isEmpty()) {
            return null;
        }
        ProductBatch removed = head.info;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
        }
        return removed;
    }

    public ProductBatch removeLast() {
        if (isEmpty()) {
            return null;
        }
        ProductBatch removed = tail.info;
        if (head == tail) {
            head = tail = null;
        } else {
            Node current = head;
            while (current.next != tail) {
                current = current.next;
            }
            tail = current;
            tail.next = null;
        }
        return removed;
    }

    public int traverseAndSumQuantity() {
        int totalQuantity = 0;
        Node current = head;
        while (current != null) {
            totalQuantity += current.info.getQuantity();
            current = current.next;
        }
        return totalQuantity;
    }

    public ProductBatch removeAt(int index) {
        if (index < 0 || isEmpty()) {
            return null;
        }
        if (index == 0) {
            return removeFirst();
        }
        Node current = head;
        Node previous = null;
        int currentIndex = 0;
        while (current != null && currentIndex < index) {
            previous = current;
            current = current.next;
            currentIndex++;
        }
        if (current == null) {
            return null;
        }
        previous.next = current.next;
        if (current == tail) {
            tail = previous;
        }
        return current.info;
    }

}
