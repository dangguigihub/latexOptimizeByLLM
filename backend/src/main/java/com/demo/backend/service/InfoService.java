package com.demo.backend.service;

import com.demo.backend.pojo.Quotas;
import com.demo.backend.pojo.User;

import java.util.List;

public interface InfoService {

    User getInfo(String userId);


    List<Quotas> getUsed(String userId);
}
