# 使用官方Java基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制构建好的jar文件
COPY target/*.jar app.jar

EXPOSE 8081

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]

