package com.cloud.plat.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.plat.admin.api.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/22 10:31
 * @contact 269016084@qq.com
 *
 **/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<SysMenu> findByUserId(@Param("userId") Long userId);
}
