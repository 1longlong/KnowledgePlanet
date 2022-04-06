package com.major.knowledgePlanet.util;


import java.text.SimpleDateFormat;
import java.util.Date;


public class EmailUtil {

    private  static final String sendFrom="知识星球官方";

    public static String getContents(String mailTo,String verificationCode){
        //实例化一个StringBuffer
        StringBuffer sb=new StringBuffer();
        sb.append("<h2>"+"亲爱的"+mailTo+"您好！</h2>").append("<p style='text-align: center; font-size: 24px; font-weight: bold'>您的注册验证码为:"+verificationCode+"</p>");
        sb.append("<p style='text-align:right'>"+sendFrom+"</p>");
        sb.append("<p style='text-align:right'>"+curDate("yyyy-MM-dd HH:mm:ss")+"</p>");
        return sb.toString();
    }

    private static String curDate(String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
    * 获取一个六位验证码
    * @return : java.lang.String
    * @author Covenant
    * @date 2022-04-06 21:35
    */
    public static String getVerificationCode(){
        Integer res=(int)((Math.random())*1000000);
        return res+"";
    }
}
