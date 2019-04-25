package com.imooc.seller.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名服务
 */
@Service
public class SignService {
    static Map<String,String> PUBLIC_KEYS = new HashMap<>();
    static {
        PUBLIC_KEYS.put("1000","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkrOQw/s5ylyVcV87r2NK33Cju\n" +
                "d6g4wM7gcAljLEtQb0vMFo9G7f6QgZMIKyEhhM5bk+bh+NXmQfdlB7+SCQ0jfcFv\n" +
                "0sd89iwIOQERY7iJT7LgNZZRje/+UNrimeZJZWCgWhn7L4uXaflNa2gN8SexWf1M\n" +
                "WSqEdnCY1sPOxUuu1wIDAQAB");
    }

    /**
     * 根据授权编号获取公钥
     * @param authId
     * @return
     */
    public String getPublicKey(String authId){
        return PUBLIC_KEYS.get(authId);
    }
}
