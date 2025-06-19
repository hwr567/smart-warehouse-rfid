package com.warehouse;
import com.warehouse.entity.Category;
import com.warehouse.entity.Inventory;
import com.warehouse.service.RFIDService;

import java.io.File;
import java.util.Map;


public class UnitTest {
    // 测试文件路径（使用独立文件避免影响正式数据）
    private static final String TEST_INVENTORY_FILE = "test_inventory.json";
    private static final String TEST_LOG_FILE = "test_operation_log.csv";

    public static void main(String[] args) {
        System.out.println("===== RFID智能仓储系统单元测试 =====");

        // 清理测试文件
        cleanupTestFiles();

        try {
            // 1. 测试Category类
            testCategoryClass();

            // 2. 测试Inventory类
            testInventoryClass();

            // 3. 测试RFIDService类
            testRFIDService();



            System.out.println("\n所有测试完成！");
        } catch (Exception e) {
            System.err.println("测试过程中发生错误: " + e.getMessage());
        }
    }

    // 测试Category类
    private static void testCategoryClass() {
        System.out.println("\n--- 测试Category类 ---");
        Category category = new Category(1, "测试类别");

        System.out.print("验证构造函数和Getter: ");
        if (category.getId() == 1 && category.getName().equals("测试类别")) {
            System.out.println("通过");
        } else {
            System.out.println("失败");
        }

        System.out.print("验证toString方法: ");
        if (category.toString().equals("Category{id=1, name='测试类别'}")) {
            System.out.println("通过");
        } else {
            System.out.println("失败");
        }
    }

    // 测试Inventory类
    private static void testInventoryClass() {
        System.out.println("\n--- 测试Inventory类 ---");
        Inventory inventory = new Inventory();

        System.out.print("验证初始化库存: ");
        Map<Integer, Integer> stockMap = inventory.getAllStock();
        boolean initOk = stockMap.size() == 10;
        for (int i = 1; i <= 10; i++) {
            initOk = initOk && (stockMap.get(i) == 0);
        }
        System.out.println(initOk ? "通过" : "失败");

        System.out.print("验证入库功能: ");
        inventory.addStock(1, 5);
        boolean addOk = inventory.getStock(1) == 5;
        inventory.addStock(1, 3);
        addOk = addOk && (inventory.getStock(1) == 8);
        System.out.println(addOk ? "通过" : "失败");

        System.out.print("验证出库功能: ");
        inventory.addStock(2, 10);
        boolean removeOk = inventory.removeStock(2, 3) && (inventory.getStock(2) == 7);
        removeOk = removeOk && !inventory.removeStock(2, 10) && (inventory.getStock(2) == 7);
        System.out.println(removeOk ? "通过" : "失败");
    }

    // 测试RFIDService类
    private static void testRFIDService() {
        System.out.println("\n--- 测试RFIDService类 ---");
        RFIDService rfidService = new RFIDService();

        System.out.print("验证初始化标签数量: ");
        if (rfidService.tagDatabase.size() == 10) {
            System.out.println("通过");
        } else {
            System.out.println("失败");
        }

        System.out.print("验证有效标签读取: ");
        Category category = rfidService.readTag("TAG_1");
        boolean readOk = (category != null) && (category.getId() == 1) &&
                category.getName().equals("类别1");
        System.out.println(readOk ? "通过" : "失败");

        System.out.print("验证无效标签读取: ");
        if (rfidService.readTag("INVALID_TAG") == null) {
            System.out.println("通过");
        } else {
            System.out.println("失败");
        }
    }


    // 清理测试文件
    private static void cleanupTestFiles() {
        new File(TEST_INVENTORY_FILE).delete();
        new File(TEST_LOG_FILE).delete();
    }
}