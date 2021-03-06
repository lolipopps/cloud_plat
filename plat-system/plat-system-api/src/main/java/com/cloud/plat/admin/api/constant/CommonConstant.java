package com.cloud.plat.admin.api.constant;

public interface CommonConstant {

    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "https://ooo.0o0.ooo/2019/04/28/5cc5a71a6e3b6.png";
    /**
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 0;
    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_LOCK = -1;
    /**
     * 普通用户
     */
    Integer USER_PRI_TYPE_NORMAL = 0;
    /**
     * 管理员
     */
    Integer USER_PRI_TYPE_ADMIN = 1;

    /**
     * 角色类型
     */
    String ROLE_KEFU = "ROLE_KEFU";

    String ROLE_ADMIN = "ROLE_ADMIN";

    String ROLE_ORDER = "ROLE_USER";

    String ROLE_USER = "ROLE_OPERA";

    /**
     * 全部数据权限
     */
    Integer DATA_TYPE_ALL = 0;

    /**
     * 自定义数据权限
     */
    Integer DATA_TYPE_CUSTOM = 1;

    /**
     * 本部门及以下
     */
    Integer DATA_TYPE_UNDER = 2;

    /**
     * 本部门
     */
    Integer DATA_TYPE_SAME = 3;


    /**
     * 部门负责人类型 主负责人
     */
    Integer HEADER_TYPE_MAIN = 0;

    /**
     * 部门负责人类型 副负责人
     */
    Integer HEADER_TYPE_VICE = 1;


    /**
     * 顶部菜单类型权限
     */
    Integer PERMISSION_NAV = -1;

    /**
     * 页面类型权限
     */
    Integer PERMISSION_PAGE = 0;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 1;

    /**
     * 短信验证码key前缀
     */
    String PRE_SMS = "FREIGHT_PRE_SMS_";

    /**
     * 邮件验证码key前缀
     */
    String PRE_EMAIL = "FREIGHT_PRE_EMAIL_";


    /**
     * 1级菜单父id
     */
    Long PARENT_ID = 0L;

    /**
     * 0级菜单
     */
    Integer LEVEL_ZERO = 0;

    /**
     * 1级菜单
     */
    Integer LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    Integer LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    Integer LEVEL_THREE = 3;

}
