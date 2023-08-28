package com.neu.his.utils;

import java.security.MessageDigest;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: ShaUtil.java
 * @Description: SHA256加密
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:03:57
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public class ShaUtil {
    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     */
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(getSHA256("admin"));
        System.out.println(getSHA256("123456"));
    }
}