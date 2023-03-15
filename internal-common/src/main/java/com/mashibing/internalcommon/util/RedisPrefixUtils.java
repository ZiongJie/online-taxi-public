package com.mashibing.internalcommon.util;

public class RedisPrefixUtils {

    // 乘客验证码的前缀
    public static String verificationcodePrefix = "passenger-verification-code-";

    // token存储的前缀
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号，生成key
     * @param passengerPhone
     * @return
     */
    public static String generatorKeyByPhone(String passengerPhone) {
        return verificationcodePrefix + passengerPhone;
    }

    /**
     * 根据手机号和身份表示生成key
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorTokenKey(String phone, String identity, String tokenType) {
        return tokenPrefix + phone + "-" + identity + tokenType;
    }
}
