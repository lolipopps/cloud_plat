package com.cloud.plat.admin.dao;
import com.cloud.plat.admin.api.entity.PlatLog;
import com.cloud.plat.common.base.base.BaseDao;
import org.springframework.data.jpa.repository.Query;

/**
 * 日志数据处理层
 * @describe x
 */
public interface PlatLogDao extends BaseDao<PlatLog, Long> {

    @Query(value = "select * from sys_log u where u.username = ?1 or u.request_url = ?2 ORDER BY u.create_time desc limit 10,10",nativeQuery = true)
    PlatLog findSearchTenRecord(String username, String url);
}
