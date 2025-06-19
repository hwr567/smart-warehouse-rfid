// LogService.java (日志服务)
package com.warehouse.service;

import com.warehouse.util.FileUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogService {
    private static final String LOG_FILE = "operation_log.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LogService() {
        // 检查日志文件是否存在，不存在则创建并写入表头
        try {
            if (!FileUtil.exists(LOG_FILE)) {
                FileUtil.writeFile(LOG_FILE, "时间,操作类型,标签ID,类别ID,数量,操作人\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInbound(String tagId, int categoryId, int quantity, String operator) {
        logOperation("入库", tagId, categoryId, quantity, operator);
    }

    public void logOutbound(String tagId, int categoryId, int quantity, String operator) {
        logOperation("出库", tagId, categoryId, quantity, operator);
    }

    private void logOperation(String operationType, String tagId, int categoryId, int quantity, String operator) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logLine = String.format("%s,%s,%s,%d,%d,%s\n",
                timestamp, operationType, tagId, categoryId, quantity, operator);

        try {
            FileUtil.appendFile(LOG_FILE, logLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}