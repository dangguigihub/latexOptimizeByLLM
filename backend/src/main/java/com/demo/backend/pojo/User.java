package com.demo.backend.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {
    private Integer id;
    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;
    private List<Quotas> quotas;
}

