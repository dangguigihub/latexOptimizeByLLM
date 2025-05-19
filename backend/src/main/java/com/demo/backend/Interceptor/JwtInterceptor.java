package com.demo.backend.Interceptor;
import com.demo.backend.util.BaseContext;
import com.demo.backend.util.JwtUtil;
import com.demo.backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取JWT
        String token = request.getHeader("Authorization");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            log.info("OPTIONS请求，放行");
            return true;
        }
        if (token == null) {
            // 如果没有JWT或者格式不正确，返回错误
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token令牌不存在");
            return false;
        }
        try {
            // 验证JWT
            Result r= JwtUtil.checkToken(token);
            // JWT验证成功，继续处理请求
            if (r.getCode()==1) {
                // 将用户ID存储在ThreadLocal中
                BaseContext.setUserId(String.valueOf(r.getData()));
                return true;
            }else {
                // JWT验证失败，返回错误
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token验证失败");
                return false;
            }
        } catch (Exception e) {
            // JWT验证失败，返回错误
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token验证失败");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求处理完毕后清除用户ID
        BaseContext.clearUserId();
    }
}