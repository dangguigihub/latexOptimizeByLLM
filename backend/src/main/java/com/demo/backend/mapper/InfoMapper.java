package com.demo.backend.mapper;

import com.demo.backend.pojo.Quotas;
import com.demo.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InfoMapper {


    User getBaseInfo(String userId);

    List<Quotas> getQuotas(String userId);
}
