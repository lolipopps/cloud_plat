package com.cloud.plat.admin.service.impl;
import com.cloud.plat.admin.api.entity.SysMenu;
import com.cloud.plat.admin.dao.SysMenuDao;
import com.cloud.plat.admin.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 20:09
 * @contact 269016084@qq.com
 *
 **/
@Slf4j
@Service
@Transactional
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao permissionDao;

    @Override
    public SysMenuDao getRepository() {
        return permissionDao;
    }

    @Override
    public List<SysMenu> findByParentIdOrderBySortOrder(Long parentId) {

        return permissionDao.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public List<SysMenu> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {

        return permissionDao.findByTypeAndStatusOrderBySortOrder(type, status);
    }

    @Override
    public List<SysMenu> findByTitle(String title) {

        return permissionDao.findByTitle(title);
    }

    @Override
    public List<SysMenu> findByTitleLikeOrderBySortOrder(String title) {

        return permissionDao.findByTitleLikeOrderBySortOrder(title);
    }
}