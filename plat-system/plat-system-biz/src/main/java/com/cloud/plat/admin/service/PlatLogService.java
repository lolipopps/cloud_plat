package com.cloud.plat.admin.service;

import com.cloud.plat.admin.api.entity.PlatLog;
import com.cloud.plat.common.base.base.BaseService;
import com.cloud.plat.common.base.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 日志接口
 * @describe x
 */
public interface PlatLogService extends BaseService<PlatLog, Long> {

    /**
     * 分页搜索获取日志
     * @param type
     * @param key
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<PlatLog> findByConfition(Integer type, String key, SearchVo searchVo, Pageable pageable);

    /**
     * 删除所有
     */
    void deleteAll();

    /**
     * 搜索第10条查询记录
     * @param username
     * @param url
     * @return
     */
    PlatLog findSearchTenRecord(String username,String url);
}
