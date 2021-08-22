package com.cloud.plat.admin.service.impl;

import com.cloud.plat.admin.api.dto.SysDeptHeader;
import com.cloud.plat.admin.dao.SysDeptHeaderDao;
import com.cloud.plat.admin.service.SysDeptHeaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门负责人接口实现
 * @describe
 */
@Slf4j
@Service
@Transactional
public class SysDeptHeaderServiceImpl implements SysDeptHeaderService {

    @Autowired
    private com.cloud.plat.admin.dao.SysDeptHeaderDao SysDeptHeaderDao;

    @Override
    public SysDeptHeaderDao getRepository() {
        return SysDeptHeaderDao;
    }


    @Override
    public List<Long> findHeaderByDeptId(Long departmentId, Integer type) {

        List<Long> list = new ArrayList<>();
        List<SysDeptHeader> headers = SysDeptHeaderDao.findByDeptIdAndType(departmentId, type);
        headers.forEach(e -> {
            list.add(e.getUserId());
        });
        return list;
    }

    @Override
    public List<SysDeptHeader> findByDeptIdIn(List<Long> departmentIds) {

        return SysDeptHeaderDao.findByDeptIdIn(departmentIds);
    }

    @Override
    public void deleteByDeptId(Long departmentId) {

        SysDeptHeaderDao.deleteByDepartmentId(departmentId);
    }

    @Override
    public void deleteByUserId(Long userId) {

        SysDeptHeaderDao.deleteByUserId(userId);
    }
}