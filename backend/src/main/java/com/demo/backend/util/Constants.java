package com.demo.backend.util;


import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Data
public final class Constants {
    private Constants() {}
    private static final Map<String,String> HuoShanMap;

    static {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("DeepSeek-R1(7B)","deepseek-r1-distill-qwen-7b-250120");
        HuoShanMap = Collections.unmodifiableMap(tempMap);
    }
    public static String get(String key) {
        return HuoShanMap.get(key);
    }
}
