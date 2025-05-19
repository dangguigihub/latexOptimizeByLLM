
# 构建阶段
FROM node:17 AS build-stage

# 设置工作目录
WORKDIR /app

# 先拷贝依赖文件（利用Docker缓存层）
COPY package*.json ./

# 安装依赖（使用root用户避免权限问题）
RUN npm install

# 拷贝项目文件
COPY . .

# 解决vue-cli-service权限问题
RUN chmod -R 755 node_modules/.bin

# 构建项目
RUN npm run build