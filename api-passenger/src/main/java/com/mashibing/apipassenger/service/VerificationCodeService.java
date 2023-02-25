package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    private String verificationcodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult generationCode(String passengerPhone) {

        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("remote nmber code:" + numberCode);

        String code = "1111111";

        // 存入redis
        System.out.println("存入redis");
        // key,value,过期时间
        String key = verificationcodePrefix + passengerPhone;
        // 存入redis
        stringRedisTemplate.opsForValue().set(key, numberCode+"", 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联


        // 返回值
        return ResponseResult.success();
    }
}
