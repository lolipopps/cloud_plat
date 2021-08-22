package com.cloud.plat.admin.service;

import com.cloud.plat.admin.api.entity.SysDept;
import com.cloud.plat.common.base.base.BaseService;

import java.util.List;

/**
 * 部门接口
 * @describe
 */
public interface SysDeptService extends BaseService<SysDept, Long> {

    /**
     * 通过父id获取 升序
     * @param parentId
     * @param openDataFilter 是否开启数据权限
     * @return
     */
    List<SysDept> findByParentIdOrderBySortOrder(Long parentId, Boolean openDataFilter);

    /**
     * 通过父id和状态获取
     * @param parentId
     * @param status
     * @return
     */
    List<SysDept> findByParentIdAndStatusOrderBySortOrder(Long parentId, Integer status);

    /**
     * 部门名模糊搜索 升序
     * @param title
     * @param openDataFilter 是否开启数据权限
     * @return
     */
    List<SysDept> findByTitleLikeOrderBySortOrder(String title, Boolean openDataFilter);

    List<Long> getDeparmentIds();
}