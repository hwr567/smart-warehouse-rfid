// Inventory.java (实体类)
package com.warehouse.entity;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Integer, Integer> stockMap = new HashMap<>();

    public Inventory() {
        // 初始化10个类别，库存为0
        for (int i = 1; i <= 10; i++) {
            stockMap.put(i, 0);
        }
    }

    public void addStock(int categoryId, int quantity) {
        stockMap.put(categoryId, stockMap.get(categoryId) + quantity);
    }

    public boolean removeStock(int categoryId, int quantity) {
        if (stockMap.get(categoryId) < quantity) {
            return false;
        }
        stockMap.put(categoryId, stockMap.get(categoryId) - quantity);
        return true;
    }

    public int getStock(int categoryId) {
        return stockMap.get(categoryId);
    }

    public Map<Integer, Integer> getAllStock() {
        return stockMap;
    }
}