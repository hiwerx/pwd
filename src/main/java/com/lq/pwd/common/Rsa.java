package com.lq.pwd.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;

public class Rsa {
    public static String priKeyQ = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOKwWJfcTP5RpGdYKPk65fLEL+5p8kIf/Iybk2X+S76qWmjkOX4e4xDnkpVkGgykOsR95it7Gj2sjcKA004lfaeHlBu4gJBntPvbb4IPtYDt1X5UWH1jha02nKzZDN6enKvJ4iedT+Kn4EvLUVQg0hIZKaPPvlMzq1pS0cVN3DMbAgMBAAECgYAfGXBeDSQm885pOygL4S+w2Yd13uUUe0zrgWB8aqG7m6VhpIXarqQaKprqkdwdBd7mHBuatX3JHkYofAIBQ5V0RzcY5do+YlbgZPPEJ821jrJAIduhUdgizhX4gr6bscQXfDXnlpLohnGmRHItIx5JlDL9Kt1IyBNFxUZR+BpbIQJBAPrn1aMc2gbG3eap5bk+qAiIsJ8nibV7WvrLtzx2me+nUcq/rqrY/qLiQsqqVflqcaY3qE7p5H9Ztcsyl2ytLbECQQDnSqMMjxHnB7jErTTSjZY6o4l0gex9vL4VL1SFHzXlNJUu5q9frCHaRyluNBaEss3K4cVaGAI9pGr4bczcP6SLAkAUncIPIgrELckD8AfhPcW+1cR1f2EwWU9zdhO7Ux948q2k1DU88pGRvrewQWjJErb96mrY+Rz+XdezN5f7pQsxAkBa9ui/dLlWwpuxhmmC8bqG33CdCMgo/4VGsbGtFYRN2+tcYc/RcdJylZA+zm+YyvXS+A51C4I+Gh8UC6MJcGJXAkEA+MQTZJiIJ8bnnayjisWwF+t9tdVXrpsgRncCYESoIlBbbkdLkqnDzd+L7/JDtA5DTOla26tGsMvpRf3TOIHFCA==";
    public static String pubKeyQ = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDisFiX3Ez+UaRnWCj5OuXyxC/uafJCH/yMm5Nl/ku+qlpo5Dl+HuMQ55KVZBoMpDrEfeYrexo9rI3CgNNOJX2nh5QbuICQZ7T722+CD7WA7dV+VFh9Y4WtNpys2QzenpyryeInnU/ip+BLy1FUINISGSmjz75TM6taUtHFTdwzGwIDAQAB";

    public static String priKeyH = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJvJqEQox8eSW6yAtn1fI6+SDZLaVbInKjWFwMX5IBjghZLkZf5srJyBrz/G+hGQG/DFN1iF9SevPBG4Jhk6SHlMV76totfenaP4HtQe3GoxicwIglUqa0duuDJb43LEj17sjvzM0TDeWSpi16Zz5zIdWc0qPzq27aDcHCjn3NzhAgMBAAECgYAJvJDHscTKtFsGbQT60PdqAbbXds3kVI5Oyg1CUk+vPlka1SuKu0AOiAxr8AOxFVCpu6m53qVz1X/rm5bF/N6KnUN8PbF9yBdx/BlBTwUWVxHpQKzuHNdA062ySwXIaJelGbhuk+GlDM/F8QIEwRjCd9NB84tqqZDZZZ2wKNoAAQJBAMsBRoVV+AlBHY+0OcXntsHZe6rjXQOhbjCy6g0PF77iNi6ViyUUnG8MWir8WcfYJFbEIRYEKGrG+ikXVmN6CQ0CQQDEdOAVcSg8COUvtsPUjl2tLEs0PAuKc/6PHOjDUyQfrE2oIOziuQWH/6QrW/B6NtS9Og+nZS78bndLk3YbwEYlAkB5UbPRb4UiErYV8YEtUsMXql+LywEFcG4n0GSrlT99pjb3NAvKBz1N6DXixpjpI7Tj3aZgP+/fkDZkZDwOixnpAkEAsKnBUMbfPY1qO8wIsj4L80xfnGtanXjNs3h1wCAl3e2eL9Db9M4ZMUEsKmmVCPIBwOBTb17IL+xOjsHedfojmQJAc9GCrwOoWJwYuKmwFR16EuGwFtKcBBoKtEbvEYFIpPGdoCjfT9LxZp/qLOb2Gh4Cv8ypilCJX9PpWtDvNGmooQ==";
    public static String pubKeyH = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbyahEKMfHklusgLZ9XyOvkg2S2lWyJyo1hcDF+SAY4IWS5GX+bKycga8/xvoRkBvwxTdYhfUnrzwRuCYZOkh5TFe+raLX3p2j+B7UHtxqMYnMCIJVKmtHbrgyW+NyxI9e7I78zNEw3lkqYtemc+cyHVnNKj86tu2g3Bwo59zc4QIDAQAB";

    public static void generateKey(){
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        Console.log("{}\r\n{}", Base64.encode(keyPair.getPrivate().getEncoded()),Base64.encode(keyPair.getPublic().getEncoded()));
        KeyPair keyPairSm = SecureUtil.generateKeyPair("SM2");
        Console.log("{}\r\n{}", Base64.encode(keyPairSm.getPrivate().getEncoded()),Base64.encode(keyPairSm.getPublic().getEncoded()));

    }
    public static String decodeRsa(String priKey,String encode) {

        RSA rsa = SecureUtil.rsa(priKey,null);

        String decd = rsa.decryptStr(encode, KeyType.PrivateKey);
      //  Console.log("encd:{}\r\ndecd:{}",encode,decd);
        return rsa.decryptStr(encode, KeyType.PrivateKey);
    }

    public static String encodeRsa(String pubKey,String content){
        RSA rsa = SecureUtil.rsa(null,pubKey);
        String encode = rsa.encryptBase64(content,KeyType.PublicKey);
        return encode;
    }

    // 公钥验签
    public static boolean verify(String pubKey, String content,String signature){
        RSA rsa = SecureUtil.rsa(null,pubKey);
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA,null,pubKey);
        return sign.verify(content.getBytes(StandardCharsets.UTF_8),Base64.decode(signature));
    }

    public static String sign(String priKey, String content){
        RSA rsa = SecureUtil.rsa(priKey,null);
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA,priKey,null);
        byte[] bytes = sign.sign(content);
        Console.log(sign.signHex(content));
        return Base64.encode(bytes);
    }

    public static void main(String[] args) {
        generateKey();
    }
}
