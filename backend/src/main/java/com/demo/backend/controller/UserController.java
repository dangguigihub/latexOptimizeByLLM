package com.demo.backend.controller;


import com.demo.backend.dto.LoginDTO;
import com.demo.backend.dto.RegisterDTO;
import com.demo.backend.pojo.Quotas;
import com.demo.backend.pojo.User;
import com.demo.backend.service.AuthService;
import com.demo.backend.service.InfoService;
import com.demo.backend.util.BaseContext;
import com.demo.backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private InfoService infoService;

    @PostMapping("/auth/register")
    public Result<?> register(@RequestBody RegisterDTO dto) {
        log.info("dto"+dto);
        return authService.register(dto);
    }

    @PostMapping("/auth/login")
    public Result login(@RequestBody LoginDTO dto) {
        return authService.login(dto);
    }
    @GetMapping("/user/info")
    public Result get(){
        String userId = BaseContext.getUserId();
        User user = infoService.getInfo(userId);
        return Result.success(user);
    }
    @GetMapping("/user/used")
    public Result getUsed(){
        String userId = BaseContext.getUserId();
        List<Quotas> list =  infoService.getUsed(userId);
        return Result.success(list);
    }

}
