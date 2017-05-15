package com.bs.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangsz on 2016/4/14 15:12.
 * 身份证验证
 */
public class IDCardValidate {
    /**
     * 身份证15位编码规则：dddddd yymmdd xx p
     * dddddd：地区码
     * yymmdd: 出生年月日
     * xx: 顺序类编码，无法确定
     * p: 性别，奇数为男，偶数为女
     * <p />
     * 身份证18位编码规则：dddddd yyyymmdd xxx y
     * dddddd：地区码
     * yyyymmdd: 出生年月日
     * xxx:顺序类编码，无法确定，奇数为男，偶数为女
     * y: 校验码，该位数值可通过前17位计算获得
     * <p />
     * 18位号码加权因子为(从右到左) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]
     * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
     * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
     * i为身份证号码从右往左数的 2...18 位; Y_P为脚丫校验码所在校验码数组位置
     *
     */
    private static final int[] Wi = new int[]{ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};// 加权因子
    private static final int[] ValideCode = new int[]{ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };// 身份证验证位值.10代表X

    /**
     * 判断身份证号码是否正确
     * @param idCard 身份证号码
     * @return
     */
    public static boolean idCardValidate(String idCard) {
        idCard = idCard.trim();
        if (idCard.length() == 15) {
            return isValidityBrithBy15IdCard(idCard);
        } else if (idCard.length() == 18) {
            if(isValidityBrithBy18IdCard(idCard) && isTrueValidateCodeBy18IdCard(idCard)){
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断身份证号码为18位时最后的验证位是否正确
     * @param idCard18 18位书身份证字符串
     * @return
     */
    public static boolean isTrueValidateCodeBy18IdCard(String idCard18) {
        try {
            if (idCard18.length() != 18) return false;
            int sum = 0; // 声明加权求和变量
            String s = String.valueOf(idCard18.charAt(17));
            if (s.equals("x") || s.equals("X")) {
                s = "10";// 将最后位为x的验证码替换为10方便后续操作
            }
            for (int i = 0; i < 17; i++) {
                sum += Wi[i] * Integer.valueOf(String.valueOf(idCard18.charAt(i)));// 加权求和
            }
            int valCodePosition = sum % 11;// 得到验证码所位置
            if (Integer.valueOf(s) == ValideCode[valCodePosition]) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
//			e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过身份证判断是男是女
     * @param idCard 15/18位身份证号码
     * @return 'female'-女、'male'-男
     */
    public static String maleOrFemalByIdCard(String idCard){
        if (idCard.length() != 15 && idCard.length() != 18) return null;
        idCard = idCard.trim();// 对身份证号码做处理。包括字符间有空格。
        if(idCard.length() == 15){
            if(Integer.valueOf(idCard.substring(14, 15)) % 2 == 0){
                return "female";
            }else{
                return "male";
            }
        }else if(idCard.length() == 18){
            if(Integer.valueOf(idCard.substring(14, 17)) % 2 == 0){
                return "female";
            }else{
                return "male";
            }
        }else{
            return null;
        }
    }

    /**
     * 验证18位数身份证号码中的生日是否是有效生日
     * @param idCard18 18位书身份证字符串
     * @return
     */
    public static boolean isValidityBrithBy18IdCard(String idCard18){
        if (idCard18.length() != 18) return false;
        int year =  Integer.valueOf(idCard18.substring(6, 10));
        int month = Integer.valueOf(idCard18.substring(10, 12));
        int day = Integer.valueOf(idCard18.substring(12, 14));
        Date temp_date = new Date(year, month - 1, day);
        // 这里用getFullYear()获取年份，避免千年虫问题
        if(temp_date.getYear() != year
                || temp_date.getMonth() != month - 1
                || temp_date.getDate() != day){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 验证15位数身份证号码中的生日是否是有效生日
     * @param idCard15 15位书身份证字符串
     * @return
     */
    public static boolean isValidityBrithBy15IdCard(String idCard15){
        if (idCard15.length() != 15) return false;
        int year =  Integer.valueOf(idCard15.substring(6, 8));
        int month = Integer.valueOf(idCard15.substring(8, 10));
        int day = Integer.valueOf(idCard15.substring(10, 12));
        Date temp_date = new Date(year, month - 1, day);
        // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
        if(temp_date.getYear() != year
                || temp_date.getMonth() != month - 1
                || temp_date.getDate() != day){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 根据身份证获取年龄(不考虑身份证校验,15位不考虑)
     * @param idCard
     * @return
     */
    public static int getAgeFromIdcard(String idCard){
        if (idCard.length() == 18){
            //获取年龄
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");
            String now=simpleDateFormat.format(new Date());
            int year =  Integer.valueOf(idCard.substring(6, 10));
            return Integer.valueOf(now) - year;
        } else {
            return 0;
        }
    }
}
