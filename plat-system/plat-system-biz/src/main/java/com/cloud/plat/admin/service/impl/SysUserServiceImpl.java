package com.cloud.plat.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.dto.SysRoleDto;
import com.cloud.plat.admin.api.dto.SysTitleMenuDto;
import com.cloud.plat.admin.api.entity.SysMenu;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.mapper.SysMenuMapper;
import com.cloud.plat.admin.mapper.SysUserRoleMapper;
import com.cloud.plat.admin.service.SysDeptService;
import com.cloud.plat.admin.service.SysUserService;
import com.cloud.plat.admin.dao.SysUserDao;
import com.cloud.plat.common.base.vo.SearchVo;
import com.cloud.plat.common.security.util.SecurityUtil;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 19:31
 * @contact 269016084@qq.com
 *
 **/
@Slf4j
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao userDao;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysMenuMapper permissionMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    SysDeptService sysDeptService;

    @Override
    public SysUserDao getRepository() {
        return userDao;
    }

    @Override
    public SysUser findByUsername(String username) {

        SysUser user = userDao.findByUsername(username);
        return userToDTO(user);
    }

    @Override
    public SysUser findByMobile(String mobile) {

        SysUser user = userDao.findByMobile(mobile);
        return userToDTO(user);
    }

    @Override
    public SysUser findByEmail(String email) {

        SysUser user = userDao.findByEmail(email);
        return userToDTO(user);
    }

    public SysUser userToDTO(SysUser user) {

        if (user == null) {
            return null;
        }
        // 关联角色
        List<SysRole> roleList = userRoleMapper.findByUserId(user.getId());
        List<SysRoleDto> roleDTOList = roleList.stream().map(e -> {
            return new SysRoleDto().setId(e.getId()).setName(e.getName());
        }).collect(Collectors.toList());
        user.setRoles(roleDTOList);
        // 关联权限菜单
        List<SysMenu> permissionList = permissionMapper.findByUserId(user.getId());
        List<SysTitleMenuDto> permissionDTOList = permissionList.stream()
                .filter(e -> CommonConstant.PERMISSION_OPERATION.equals(e.getType()))
                .map(e -> {
                    return new SysTitleMenuDto().setTitle(e.getTitle()).setPath(e.getPath());
                }).collect(Collectors.toList());
        user.setPermissions(permissionDTOList);
        return user;
    }

    @Override
    public Page<SysUser> findByCondition(SysUser user, SearchVo searchVo, Pageable pageable) {

        return userDao.findAll(new Specification<SysUser>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> idField = root.get("id");
                Path<String> usernameField = root.get("username");
                Path<String> nickNameField = root.get("nickName");
                Path<String> mobileField = root.get("mobile");
                Path<String> departmentIdField = root.get("departmentId");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<>();

                if (null != user.getId()) {
                    list.add(cb.equal(idField, user.getId()));
                }
                // 模糊搜素
                if (StrUtil.isNotBlank(user.getUsername())) {
                    list.add(cb.like(usernameField, '%' + user.getUsername() + '%'));
                }
                if (StrUtil.isNotBlank(user.getMobile())) {
                    list.add(cb.like(mobileField, '%' + user.getMobile() + '%'));
                }
                // 部门
                if (null != user.getDeptId()) {
                    list.add(cb.equal(departmentIdField, user.getDeptId()));
                }

                // 类型
                if (user.getType() != null) {
                    list.add(cb.equal(typeField, user.getType()));
                }
                // 状态
                if (user.getStatus() != null) {
                    list.add(cb.equal(statusField, user.getStatus()));
                }
                // 创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                // 数据权限
                List<Long> depIds = sysDeptService.getDeparmentIds();
                if (depIds != null && depIds.size() > 0) {
                    list.add(departmentIdField.in(depIds));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public List<SysUser> findByDepartmentId(Long deptId) {

        return userDao.findByDeptId(deptId);
    }

    @Override
    public List<SysUser> findByUsernameLikeAndStatus(String username, Integer status) {

        return userDao.findByUsernameLikeAndStatus(username, status);
    }

    @Override
    public List<SysUser> findByIds(Long[] ids) {
        return userDao.findByIdIn(ids);
    }

    @Override
    public void updateDeptTitle(Long departmentId, String departmentTitle) {
        userDao.updateDeptTitle(departmentId, departmentTitle);
    }



}
