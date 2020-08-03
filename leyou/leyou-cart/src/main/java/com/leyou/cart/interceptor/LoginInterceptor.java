package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 要子康
 * @description LoginInterceptor
 * @since 2020/7/26 09:33
 */
public class LoginInterceptor  extends HandlerInterceptorAdapter {

    private JwtProperties jwtProperties;

    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public LoginInterceptor(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //查询token
        String token = CookieUtils.getCookieValue(request, "LY_TOKEN");
        if (StringUtils.isBlank(token)) {
            //未登陆返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //有token，查询用户信息
        try {
            //解析成功，证明已登陆
            UserInfo user = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            //放入线程域
            tl.set(user);
            return true;
        }catch (Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        tl.remove();
    }

    public static UserInfo getLoginUser(){
        return tl.get();
    }
}
