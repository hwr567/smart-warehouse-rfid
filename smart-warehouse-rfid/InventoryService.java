// InventoryService.java (核心服务类)
package com.warehouse.service;

import com.warehouse.entity.Category;
import com.warehouse.entity.Inventory;
import com.warehouse.util.FileUtil;
import com.warehouse.util.JsonUtil;
import java.io.IOException;

public class InventoryService {
    private static final String INVENTORY_FILE = "inventory.json";
    private Inventory inventory;
    private RFIDService rfidService;
    private LogService logService;

    public InventoryService() {
        this.rfidService = new RFIDService();
        this.logService = new LogService();
        loadInventory();
    }

    private void loadInventory() {
        try {
            String json = FileUtil.readFile(INVENTORY_FILE);
            if (json != null && !json.isEmpty()) {
                inventory = JsonUtil.fromJson(json, Inventory.class);
            } else {
                inventory = new Inventory();
                saveInventory();
            }
        } catch (IOException e) {
            inventory = new Inventory();
            saveInventory();
        }
    }

    private void saveInventory() {
        try {
            String json = JsonUtil.toJson(inventory);
            FileUtil.writeFile(INVENTORY_FILE, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean inbound(String tagId, int quantity, String operator) {
        Category category = rfidService.readTag(tagId);
        if (category == null) {
            return false;
        }

        inventory.addStock(category.getId(), quantity);
        saveInventory();
        logService.logInbound(tagId, category.getId(), quantity, operator);
        return true;
    }

    public boolean outbound(String tagId, int quantity, String operator) {
        Category category = rfidService.readTag(tagId);
        if (category == null) {
            return false;
        }

        if (!inventory.removeStock(category.getId(), quantity)) {
            return false;
        }

        saveInventory();
        logService.logOutbound(tagId, category.getId(), quantity, operator);
        return true;
    }

    public int getStock(int categoryId) {
        return inventory.getStock(categoryId);
    }
}