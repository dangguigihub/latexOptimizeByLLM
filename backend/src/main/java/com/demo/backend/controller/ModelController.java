package com.demo.backend.controller;

import com.demo.backend.dto.ModelDTO;
import com.demo.backend.dto.OptimizeDTO;
import com.demo.backend.service.ModelService;
import com.demo.backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class ModelController {
    @Autowired
    private ModelService modelService;
    @GetMapping("/model/info")
    public Result getInfo(){
        return modelService.getModelInfo();
    }
    @PostMapping("/model/add")
    public Result addModel(@RequestBody ModelDTO modelDTO){
        return modelService.addModel(modelDTO);
    }
    @PostMapping("/model/optimize")
    public Result optimize(@RequestBody OptimizeDTO optimizeDTO) {
        return modelService.optimize(optimizeDTO);
    }
}
