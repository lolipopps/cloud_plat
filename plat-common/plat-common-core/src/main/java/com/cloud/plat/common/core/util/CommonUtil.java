package com.cloud.plat.common.core.util;

import cn.hutool.core.util.IdUtil;

import java.security.SecureRandom;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/22 11:32
 * @contact 269016084@qq.com
 *
 **/
public class CommonUtil {

    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static SecureRandom random = new SecureRandom();

    /**
     * 以UUID重命名
     * @param fileName
     * @return
     */
    public static String renamePic(String fileName) {
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return IdUtil.simpleUUID() + extName;
    }


    /**
     * 随机6位数生成
     */
    public static String getRandomNum() {
        int num = random.nextInt(999999);
        // 不足六位前面补0
        String str = String.format("%06d", num);
        return str;
    }

    /**
     * 批量递归删除时 判断target是否在ids中 避免重复删除
     * @param target
     * @param ids
     * @return
     */
    public static Boolean judgeIds(String target, Long[] ids) {

        Boolean flag = false;
        for (Long id : ids) {
            if (id.equals(target)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * 批量递归删除时 判断target是否在ids中 避免重复删除
     * @param target
     * @param ids
     * @return
     */
    public static Boolean judgeIds(Long target, Long[] ids) {

        Boolean flag = false;
        for (Long id : ids) {
            if (id.equals(target)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
