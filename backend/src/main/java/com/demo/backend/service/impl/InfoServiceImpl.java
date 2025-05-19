package com.demo.backend.service.impl;

import com.demo.backend.mapper.InfoMapper;
import com.demo.backend.pojo.Quotas;
import com.demo.backend.pojo.User;
import com.demo.backend.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoMapper infoMapper;
    @Override
    public User getInfo(String userId) {
        //在这里既要获得用户的基本信息
        User user = infoMapper.getBaseInfo(userId);
        user.setPasswordHash(null);
        //又要获取用户的配额信息
        List<Quotas> quotas = infoMapper.getQuotas(userId);
        user.setQuotas(quotas);
        return user;
    }

    @Override
    public List<Quotas> getUsed(String userId) {
        return infoMapper.getQuotas(userId);
    }


}
