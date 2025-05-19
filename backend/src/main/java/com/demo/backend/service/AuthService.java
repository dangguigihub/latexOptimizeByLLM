package com.demo.backend.service;

import com.demo.backend.dto.LoginDTO;
import com.demo.backend.dto.RegisterDTO;
import com.demo.backend.util.Result;


public interface AuthService {
    public Result<?> register(RegisterDTO dto);
    public Result login(LoginDTO dto);

}
