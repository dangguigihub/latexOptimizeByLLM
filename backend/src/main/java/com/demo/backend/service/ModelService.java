package com.demo.backend.service;

import com.demo.backend.dto.ModelDTO;
import com.demo.backend.dto.OptimizeDTO;
import com.demo.backend.util.Result;

public interface ModelService {
    Result getModelInfo();

    Result addModel(ModelDTO modelDTO);

    Result optimize(OptimizeDTO optimizeDTO);
}
