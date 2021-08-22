package com.cloud.plat.admin.dao;

import com.cloud.plat.admin.api.entity.SysOauthClient;
import com.cloud.plat.common.base.base.BaseDao;

/**
 * 部门数据处理层
 * @describe 
 */
public interface SysOauthClientDao extends BaseDao<SysOauthClient, Long> {

    boolean deleteByClientId(String clientId);

    /** 
     * 
     * @description   
     * @author hyt
     * @date 2021/8/21 23:28
     * @param 
     * @return 
     *
     **/
    SysOauthClient findByClientId(String clientId);
    
}