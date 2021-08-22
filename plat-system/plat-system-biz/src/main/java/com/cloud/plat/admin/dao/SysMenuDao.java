package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.entity.SysMenu;
import com.cloud.plat.common.base.base.BaseDao;

import java.util.List;

/**
 * 权限数据处理层
 * @describe
 */
public interface SysMenuDao extends BaseDao<SysMenu, Long> {

    /**
     * 通过层级查找
     * 默认升序
     * @param level
     * @return
     */
    List<SysMenu> findByLevelOrderBySortOrder(Integer level);

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