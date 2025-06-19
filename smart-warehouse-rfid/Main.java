// Main.java (程序入口)
package com.warehouse;

import com.warehouse.service.InventoryService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryService service = new InventoryService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== RFID智能仓储系统 =====");
            System.out.println("1. 入库操作");
            System.out.println("2. 出库操作");
            System.out.println("3. 查询库存");
            System.out.println("4. 退出");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    handleInbound(service, scanner);
                    break;
                case 2:
                    handleOutbound(service, scanner);
                    break;
                case 3:
                    showInventory(service);
                    break;
                case 4:
                    System.out.println("退出系统...");
                    return;
                default:
                    System.out.println("无效选择，请重新输入");
            }
        }
    }

    private static void handleInbound(InventoryService service, Scanner scanner) {
        System.out.print("请输入RFID标签ID (格式: TAG_1~TAG_10): ");
        String tagId = scanner.nextLine();
        System.out.print("请输入数量: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("请输入操作人: ");
        String operator = scanner.nextLine();

        boolean result = service.inbound(tagId, quantity, operator);
        System.out.println(result ? "入库成功" : "入库失败：无效标签");
    }

    private static void handleOutbound(InventoryService service, Scanner scanner) {
        System.out.print("请输入RFID标签ID (格式: TAG_1~TAG_10): ");
        String tagId = scanner.nextLine();
        System.out.print("请输入数量: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("请输入操作人: ");
        String operator = scanner.nextLine();

        boolean result = service.outbound(tagId, quantity, operator);
        System.out.println(result ? "出库成功" : "出库失败：无效标签或库存不足");
    }

    private static void showInventory(InventoryService service) {
        System.out.println("\n===== 当前库存 =====");
        for (int i = 1; i <= 10; i++) {
            System.out.printf("类别%d: %d\n", i, service.getStock(i));
        }
    }
}