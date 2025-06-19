// RFIDService.java (服务类)
package com.warehouse.service;

import com.warehouse.entity.Category;
import java.util.HashMap;
import java.util.Map;

public class RFIDService {
    public Map<String, Category> tagDatabase = new HashMap<>();

    public RFIDService() {
        // 初始化10个RFID标签与类别的映射
        for (int i = 1; i <= 10; i++) {
            tagDatabase.put("TAG_" + i, new Category(i, "类别" + i));
        }
    }

    public Category readTag(String tagId) {
        return tagDatabase.get(tagId);
    }
}