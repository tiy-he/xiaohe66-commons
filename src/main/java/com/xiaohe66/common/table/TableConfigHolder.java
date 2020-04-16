package com.xiaohe66.common.table;

import com.xiaohe66.common.table.entity.TableConfig;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohe
 * @time 2020.04.15 14:07
 */
public class TableConfigHolder {

    private static final Map<String, TableConfig> configMap = new ConcurrentHashMap<>();

    private TableConfigHolder() {
    }

    public static void add(String configName, TableConfig config) {
        Objects.requireNonNull(configName);
        Objects.requireNonNull(config);
        configMap.put(configName, config);
    }

    public static void remove(String configName) {
        configMap.remove(configName);
    }

    public static TableConfig get(String configName) {
        return configMap.get(configName);
    }

    public static boolean isExist(String configName) {
        return configMap.containsKey(configName);
    }

}
