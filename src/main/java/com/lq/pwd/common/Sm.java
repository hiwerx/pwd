package com.lq.pwd.common;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

public class Sm {
    static String priKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgEOn8Vy6GabJEhhBV87Pho9YYfQvZALXHGicF7cyyqFKgCgYIKoEcz1UBgi2hRANCAATEHJ524eaFyiGXMOns7OMU88kF3F/2cKGCHj+EWFEbJIYmr9XhPRH204XiyvsBllzOfhKxuMObAtuA2Gj+7him";
    static String pubKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAExByeduHmhcohlzDp7OzjFPPJBdxf9nChgh4/hFhRGySGJq/V4T0R9tOF4sr7AZZczn4SsbjDmwLbgNho/u4Ypg==";
    private static SM2 sm2 = SmUtil.sm2(priKey,pubKey);
    public static String smEn(String text){


// 公钥加密，私钥解密
        return sm2.encryptBcd(text, KeyType.PublicKey);
    }
    public static String smDe(String encryptStr){
        return StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
    }

    public static void main(String[] args) {
        String text = "我是一段测试a拉近了距离发生了客户管理更";
        String en = smEn(text);
        Console.log(en);
        Console.log(smDe(en));

    }
}
