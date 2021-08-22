package com.cloud.plat.common.core.constant;

/**
 * @describe x
 */
public interface SecurityConstant {


    /**
     * loginType 登入类型
     */
    String PC_LOGIN = "1";

    String OTHER_LOGIN = "2";

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = "Freight";

    /**
     * token参数头
     */
    String HEADER = "accessToken";

    /**
     * appToken参数头
     */
    String APP_HEADER = "appToken";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * 用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";


    /**
     * 交互token前缀key
     */
    String TOKEN_PRE = "FREIGHT_TOKEN_PRE::";

    /**
     * 用户token前缀key 单点登录使用
     */
    String USER_TOKEN = "FREIGHT_USER_TOKEN::";

    /**
     * 会员交互token前缀key
     */
    String TOKEN_MEMBER_PRE = "FREIGHT_TOKEN_MEMBER_PRE::";

    /**
     * 会员token前缀key
     */
    String MEMBER_TOKEN = "FREIGHT_MEMBER_TOKEN::";
}

