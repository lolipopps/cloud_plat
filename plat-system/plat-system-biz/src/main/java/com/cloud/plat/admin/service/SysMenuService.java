package com.cloud.plat.admin.service;

import com.cloud.plat.admin.api.entity.SysMenu;
import com.cloud.plat.common.base.base.BaseService;

import java.util.List;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 19:29
 * @contact 269016084@qq.com
 *
 **/
public interface SysMenuService extends BaseService<SysMenu, Long> {

    /**
     * 通过parendId查找
     * @param parentId
     * @return
     */
    List<SysMenu> findByParentIdOrderBySortOrder(Long parentId);

    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    List<SysMenu> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

    /**
     * 通过名称获取
     * @param title
     * @return
     */
    List<SysMenu> findByTitle(String title);

    /**
     * 模糊搜索
     * @param title
     * @return
     */
    List<SysMenu> findByTitleLikeOrderBySortOrder(String title);
}