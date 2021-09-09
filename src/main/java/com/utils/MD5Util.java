package com.utils;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * MD5加密工具类,主要用于用户密码的加密和验证操作
 * @author zzc
 * @version 1.1.0
 */
public class MD5Util {
    private static final String HEX_NUMS_STR = "0123456789ABCDEF" ;
    private static final Integer SALT_LENGTH = 12;

    /**
     * 将 16 进制字符串转换成字节数组
     * @param hex 十六进制字符串
     * @return  返回转化后的字节数组
     */
    private static byte[] hexStringToByte (String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
                    .indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将指定 byte 数组转换成 16 进制字符串
     * @param  b   待转换的字节数组
     * @return  返回转换后的十六进制字符串
     */
    private static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 获得加密后的 16 进制形式口令
     * @param password  待加密的密码
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     *  * @return  加密后的密码
     */
    public static String getEncryptedPwd(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 声明加密后的口令数组变量
        byte[] pwd = null;
        // 随机数生成器
        SecureRandom random = new SecureRandom();
        // 声明数组变量
        byte[] salt = new byte[SALT_LENGTH];
        // 将随机数放入盐变量中
        random.nextBytes(salt);
        // 声明消息摘要对象
        MessageDigest md = null;
        // 创建消息摘要
        md = MessageDigest.getInstance("MD5");
        // 将数据传入消息摘要对象
        md.update(salt);
        // 将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));
        // 获得消息摘要的字节数组
        byte[] digest = md.digest();
        // 因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + SALT_LENGTH];
        // 将字节拷贝到生成的加密口令字节数组的前 12 个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        // 将消息摘要拷贝到加密口令字节数组从第 13 个字节开始的字节
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        // 将字节数组格式加密后的口令转化为 16 进制字符串格式的口令
        return byteToHexString(pwd);
    }

    /**
     * 验证口令是否合法
     * @param password  待验证的密码
     * @param passwordInDb  实际存储密码的加密字符串
     * @return 成功返回true,失败返回false
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validPassword(String password, String passwordInDb){
        try {
            // 将 16 进制字符串格式口令转换成字节数组
            byte[] pwdInDb = hexStringToByte(passwordInDb);
            // 声明盐变量
            byte[] salt = new byte[SALT_LENGTH];
            // 将盐从数据库中保存的口令字节数组中提取出来
            System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
            // 创建消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将盐数据传入消息摘要对象
            md.update(salt);
            // 将口令的数据传给消息摘要对象
            md.update(password.getBytes("UTF-8"));
            // 生成输入口令的消息摘要
            byte[] digest = md.digest();
            // 声明一个保存数据库中口令消息摘要的变量
            byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
            // 取得数据库中口令的消息摘要
            System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0,digestInDb.length);
            // 比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
            if (Arrays.equals(digest, digestInDb)) {
                // 口令正确返回口令匹配消息
                return true;
            } else {
                // 口令不正确返回口令不匹配消息
                return false;
            }
        } catch (Exception e) {
            new Exception("数据库中保存的密码，没有进行MD5加密，数据非常不安全，请尽快加密！").printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        System.out.println(getEncryptedPwd("123456"));
        System.out.println(getEncryptedPwd("111111"));
        System.out.println(validPassword("123456","21166B5071425AED9C296696F55D7C86B22CAE9A9324F8F540320BBC"));
    }
}

