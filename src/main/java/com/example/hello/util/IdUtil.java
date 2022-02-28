package com.example.hello.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 常用工具类的封装，md5,uuid等
 */
public class IdUtil {


    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     * @return
     */
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().
                replaceAll("-","").substring(0,32);

        return uuid;
    }
    /**
     * 生成编号(由编号类型编码+编号创建平台编码+6位日期+时间戳后4位+4位随机数组成)
     * @param prefix 前缀（XYD-学员端）
     * @param numType 编号类型,1位(1-支付订单,2-退款订单)
     * @param platform 编号生成平台,1位(1-PC平台,2app平台,3移动web平台,4小程序端)
     * @return
     * @throws Exception
     */
    public synchronized static String createOrderNumber(String prefix,int numType,int platform){
        //格式化日期为"yymmdd"
        DateFormat format = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        StringBuffer buffer = new StringBuffer();
        buffer.append(prefix);
        buffer.append(numType);
        buffer.append(platform);
        buffer.append(format.format(date));
        buffer.append((date.getTime() + "").substring(9));
        buffer.append(getRandNum(4));
        return buffer.toString();
    }

    /**
     * 获取随机数
     * @param leng  随机数长度
     * @return
     */
    public static String getRandNum(int leng){
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < leng; i++) {
            result.append(random.nextInt(10));
        }
        if(result.length()>0){
            return result.toString();
        }
        return null;
    }


    /**
     * 生成两个数之间的随机数(没有四舍五入),参数为int型
     * @param min 开始
     * @param max 结束
     * @return
     */
    public static double getRandomNum(int min,int max){
        return (Math.random()*(max-min))+min;
    }


    /**
     * 生成两个数之间的随机数(没有四舍五入),参数为double型
     * @param min 开始
     * @param max 结束
     * @return
     */
    public static double getRandomNum(double min,double max){
        return (Math.random()*(max-min))+min;
    }


}
