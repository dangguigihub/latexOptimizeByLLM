package com.demo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptimizeDTO {
    private String latex;
    private String modelId; // 字段名必须与前端JSON完全一致（包括大小写）

    @Override
    public String toString() {
        return "OptimizeDTO{" +
                "latex='" + latex + '\'' +
                ", modelID='" + modelId + '\'' +
                '}';
    }
}
