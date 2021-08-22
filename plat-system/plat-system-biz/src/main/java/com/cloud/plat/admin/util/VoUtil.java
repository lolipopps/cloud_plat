package com.cloud.plat.admin.util;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.plat.admin.api.entity.SysMenu;
import com.cloud.plat.admin.api.vo.MenuVo;


/**
 * @describe 
 */
public class VoUtil {

    public static MenuVo permissionToMenuVo(SysMenu p) {

        MenuVo menuVo = new MenuVo();
        BeanUtil.copyProperties(p, menuVo);
        return menuVo;
    }
}
