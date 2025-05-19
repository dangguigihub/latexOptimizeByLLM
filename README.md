# Docker 全栈项目部署指南

本项目基于 **Vue2 + Spring Boot + MySQL** 构建，通过 Docker Compose 实现容器化部署，Nginx 提供反向代理和跨域解决方案。

---

## 🚀 快速开始

### 环境要求
- Docker ≥ 20.10.7
- Docker Compose ≥ 2.35.1

### 部署步骤
```bash
# 克隆项目
git clone https://github.com/your-username/your-repo.git
cd your-repo

# 构建镜像（首次运行或代码更新时执行）
docker compose build

# 启动所有服务（后台模式）
docker compose up -d

.
├── backend/
│   ├── Dockerfile       # Spring Boot 容器配置（含 Maven 构建）
│   ├── pom.xml          # Maven 依赖
│   └── src/             # 后端源码
├── frontend/
│   ├── Dockerfile       # Vue 静态资源构建配置
│   └── src/             # 前端源码
├── mysql/
│   └── demo.sql         # 数据库初始化脚本
├── nginx.conf           # Nginx 反向代理配置
└── docker-compose.yml   # 服务编排定义
