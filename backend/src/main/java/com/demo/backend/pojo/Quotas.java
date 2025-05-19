package com.demo.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quotas {
    private Integer userId;//用户id
    private Integer modelId;
    private Integer totalQuota;
    private Integer usedQuota;
    private String modelName;
    private String provider;
}
