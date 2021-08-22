package com.cloud.plat.common.core.util;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 22:24
 * @contact 269016084@qq.com
 *
 **/
public class PasswordUtil {
    /**
     * 包含大小写字母及数字且在6-12位
     * 是否包含
     *
     * @param str
     * @return
     */
    public static final String PW_PATTERN =
            "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+`\\-={}:\";'<>,.\\/]).{8,16}";
    public static boolean isRightPass(String str) {
        return   str.matches(PW_PATTERN);
    }
    public static void main(String[] args){

        String pw13 = "ABCDEabcde!@#$%";
        String pw14 = "ABCDEabcde01234";
        String pw15 = "Aa0!";
        //符合要求密码
        String pw16="a123456.";
        //符合要求密码
        String pw17="aA123456.&8";
        System.out.println(isRightPass(pw17));
        System.out.println(isRightPass(pw16));
    }
}
