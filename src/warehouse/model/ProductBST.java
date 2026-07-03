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
    private int traversalIndex;


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
            int cmpResult = product.getBarcode().compareToIgnoreCase(current.product.getBarcode());
            if (cmpResult == 0) {
                return false;
            } else if (cmpResult < 0) {
                current = current.left;
            } else {
                current = current.right;
            }

        }
        int cmp  = product.getBarcode().compareToIgnoreCase(parent.product.getBarcode());
        if (cmp < 0) {
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
            int cmp =  barcode.compareToIgnoreCase(current.product.getBarcode());
            if(cmp == 0){
                return current.product;
            } else if(cmp < 0){
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }
    private int countNodes(Node node){
        if(node == null){
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    public int totalNodes(){
        return countNodes(root);
    }
    
    private void inOrder(Node node, Product[] products){
        if(node == null){
            return;
        }
        //traverse left
        inOrder(node.left, products);
        //visit root
        products[traversalIndex] = node.product;
        traversalIndex++;
        //traverse right
        inOrder(node.right, products);
    }
    public Product[] getProductsInOrder(){
        int numberOfProducts = countNodes(root);
        Product[] products = new Product[numberOfProducts];
        traversalIndex = 0;
        inOrder(root, products);
        return products;
    }

}
