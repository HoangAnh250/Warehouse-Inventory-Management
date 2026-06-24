package warehouse.model;

public class ProductBST {

    private class Node {
        Product product;
        Node left;
        Node right;

        Node(Product product) {
            this.product = product;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public ProductBST() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean insert(Product product) {
        if (product == null) {
            return false;
        }
        Node newNode = new Node(product);
        if (isEmpty()) {
            root = newNode;
            return true;
        }
        Node current = root;
        Node parent = null;
        while (current != null) {
            parent = current;
            int comparision = product.getBarcode().compareToIgnoreCase(current.product.getBarcode());
            if (comparision == 0) {
                return false;
            } else if (comparision < 0) {
                current = current.left;
            } else {
                current = current.right;
            }

        }
        int comparision = product.getBarcode().compareToIgnoreCase(parent.product.getBarcode());
        if (comparision < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        return true;
    }
    public Product searchByBarcode(String barcode){
        if(barcode == null){
            return null;
        }
        Node current = root;
        while(current != null){
            int comparision =  barcode.compareToIgnoreCase(current.product.getBarcode());
            if(comparision == 0){
                return current.product;
            } else if(comparision < 0){
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

}