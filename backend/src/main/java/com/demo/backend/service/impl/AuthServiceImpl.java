package com.demo.backend.service.impl;

import com.demo.backend.dto.LoginDTO;
import com.demo.backend.dto.RegisterDTO;
import com.demo.backend.mapper.UserMapper;
import com.demo.backend.pojo.User;
import com.demo.backend.service.AuthService;
import com.demo.backend.util.JwtUtil;
import com.demo.backend.util.PasswordUtil;
import com.demo.backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserMapper userMapper;
    public Result<?> register(RegisterDTO dto) {
        if (userMapper.existsByUsername(dto.getUsername())) {
            return Result.fail("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(PasswordUtil.encode(dto.getPassword()));
        userMapper.insert(user);
        return Result.success();
    }

    public Result login(LoginDTO dto) {
        // 改用map查询避免直接抛异常
        Optional<User> userOpt = userMapper.selectByUsername(dto.getUsername());
        // 用户不存在时返回业务错误响应
        if (!userOpt.isPresent()) {
            return Result.fail("用户不存在");
        }
        User user = userOpt.get();
        if (!PasswordUtil.matches(dto.getPassword(), user.getPasswordHash())) {
            return Result.fail("密码错误");
        }
        String token= JwtUtil.createToken(String.valueOf(user.getId()),3600000000L);//生成token
        return Result.success(token);
    }
}
