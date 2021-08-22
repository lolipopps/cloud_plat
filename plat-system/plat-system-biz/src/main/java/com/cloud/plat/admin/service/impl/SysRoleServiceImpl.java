package com.cloud.plat.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.service.SysRoleService;
import com.cloud.plat.admin.dao.SysRoleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色接口实现
 * @describe x
 */
@Slf4j
@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao roleDao;

    @Override
    public SysRoleDao getRepository() {
        return roleDao;
    }

    @Override
    public List<SysRole> findByDefaultRole(Boolean defaultRole) {
        return roleDao.findByDefaultRole(defaultRole);
    }

    @Override
    public List<SysRole> findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public Page<SysRole> findByCondition(String key, Pageable pageable) {

        return roleDao.findAll(new Specification<SysRole>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> nameField = root.get("name");
                Path<String> descriptionField = root.get("description");

                List<Predicate> list = new ArrayList<>();

                // 模糊搜素
                if (StrUtil.isNotBlank(key)) {
                    Predicate p1 = cb.like(nameField, '%' + key + '%');
                    Predicate p2 = cb.like(descriptionField, '%' + key + '%');
                    list.add(cb.or(p1, p2));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
}
