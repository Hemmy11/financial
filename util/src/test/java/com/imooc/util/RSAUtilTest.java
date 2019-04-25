package com.imooc.util;

import org.junit.Test;

/**
 * 测试加签验签
 */
public class RSAUtilTest {
    static final String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkrOQw/s5ylyVcV87r2NK33Cju\n" +
            "d6g4wM7gcAljLEtQb0vMFo9G7f6QgZMIKyEhhM5bk+bh+NXmQfdlB7+SCQ0jfcFv\n" +
            "0sd89iwIOQERY7iJT7LgNZZRje/+UNrimeZJZWCgWhn7L4uXaflNa2gN8SexWf1M\n" +
            "WSqEdnCY1sPOxUuu1wIDAQAB";
    static final String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOSs5DD+znKXJVxX\n" +
            "zuvY0rfcKO53qDjAzuBwCWMsS1BvS8wWj0bt/pCBkwgrISGEzluT5uH41eZB92UH\n" +
            "v5IJDSN9wW/Sx3z2LAg5ARFjuIlPsuA1llGN7/5Q2uKZ5kllYKBaGfsvi5dp+U1r\n" +
            "aA3xJ7FZ/UxZKoR2cJjWw87FS67XAgMBAAECgYBlczqNbg4DEoE1tZMMWRYrEZZr\n" +
            "LVTJUOi2852GjPkgxy+WbI/GBQIB9CpZ3mCFcVjnkfdFW1LyI2PxQ32++RsFRCsG\n" +
            "Gm0AdWMIuRU2/8286569tkSx4bNBZC18Mqy0SVIPbvHymk3Md9ceqKg+s5+UJvow\n" +
            "cE81Sl3OibCU6iU9+QJBAPT5BOVXOABHcE5KFYwMI+qqyJsZyr/nZwc2B5NHa8RP\n" +
            "zuNAiBeRUwovS+syyBOI+d+8Co+Sp0CXBj1FVmiJ1esCQQDu+BEkDcwerCmS6ej7\n" +
            "fmSyJ+1v5xuIQ1O7uqKn8XkNMc/hHILTXfCJtCFbzRCUgtn4TTgUhnplzNC41E2Q\n" +
            "hPPFAkBIHLXQPSiDDISGx1Aodbps7fE3lxQUAa5WdRUtyaxzkXq5ctmc9m6XZOzY\n" +
            "Q46aEYxczCHfua/6V3rCtpKFG24ZAkAFiA51hDA+YYPQ18NB1wojqZLwbSwQ+mX8\n" +
            "yXQsaQWBTa7fKhbJJ7qCpX3ELzn8Bg9flQIAT6CTrKsELD3jY4B1AkEAsF5x8e7c\n" +
            "GpyiRPhPU7mZGA4w3bqryxvINHfBVLuT4raLPRJVQ+n4tGA1Aa/loW/ZFnIUzCsE\n" +
            "qO0sPOh/4cBeyw==";

    @Test
    public void signTest(){
        String text = "{\"amount\":30,\"chanId\":\"100\",\"chanUserId\":\"123\",\"createAt\":1553763828757,\"outerOrderId\":\"10001\",\"productId\":\"T002\",\"updateAt\":1553763828757}";
        String sign = RSAUtil.sign(text,privateKey);
        System.out.println(sign);
        System.out.println(RSAUtil.verify(text,sign,publicKey));
    }
}
