package com.demo.backend.mapper;

import com.demo.backend.pojo.Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelMapper {
    List<Model> getModelInfo();

    String getApiKey(String userId, String modelId);

    String getModelName(String modelId);



    String getModelIdByModelNameAndProvider(String modelName, String provider);


    void addModel(String userId, String modelId, int total, int used, String apiKey);
}
