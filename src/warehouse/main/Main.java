package warehouse.main;

import warehouse.controller.InventoryController;
import warehouse.model.InventoryManagement;
import warehouse.view.InventoryView;

public class Main {

    public static void main(String[] args) {

        InventoryManagement manager = new InventoryManagement();

        InventoryView view = new InventoryView();

        InventoryController controller = new InventoryController(
                manager,
                view);

        controller.run();
    }
}