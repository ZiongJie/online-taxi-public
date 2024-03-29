package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.constant.TokenConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");
        // 解析token
        TokenResult tokenResult = JwtUtils.checkToken(token);

        if (tokenResult == null) {
            resultString = "token invalid";
            result = false;
        } else {
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();

            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
            // 从redis中取出token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            // 比较我们传入的token和redis中的token是否相等
            if (StringUtils.isBlank(tokenRedis) || !token.trim().equals(tokenRedis.trim())) {
                resultString = "token invalid";
                result = false;
            }
        }

        if (!result) {
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }
}
