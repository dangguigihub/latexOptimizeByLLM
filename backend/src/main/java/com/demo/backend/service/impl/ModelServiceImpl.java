package com.demo.backend.service.impl;

import com.demo.backend.dto.ModelDTO;
import com.demo.backend.dto.OptimizeDTO;
import com.demo.backend.mapper.ModelMapper;
import com.demo.backend.pojo.Model;
import com.demo.backend.service.ModelService;
import com.demo.backend.util.BaseContext;
import com.demo.backend.util.HuoShanUtil;
import com.demo.backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Result getModelInfo() {
        List<Model> list = modelMapper.getModelInfo();
        return Result.success(list);
    }

    @Override
    public Result addModel(ModelDTO modelDTO) {
        log.info(String.valueOf(modelDTO));
        //这里要向服务商查询总额度以及已经使用的额度

        //再将额度结果插入到user_quotas表中
        String ModelId=modelMapper.getModelIdByModelNameAndProvider(modelDTO.getModelName(),modelDTO.getProvider());
        modelMapper.addModel(BaseContext.getUserId(),ModelId,1000,0,modelDTO.getApiKey());
        return Result.success();
    }

    @Override
    public Result optimize(OptimizeDTO optimizeDTO) {
        String userId = BaseContext.getUserId();
        String modelId= optimizeDTO.getModelId();
        String key = modelMapper.getApiKey(userId,modelId);
        String modelName = modelMapper.getModelName(modelId);
        return HuoShanUtil.Chat(key,optimizeDTO.getLatex(),modelName);
    }


}
