package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult loginOrReg(String passengerPhone) {
        System.out.println("user service被调用, 手机号" + passengerPhone);
        // 根据手机号查询用户信息

        // 判读用户信息是否存在
        
        // 如果不存在，输入用户信息

        return ResponseResult.success();
    }

}
