package com.cloud.plat.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.cloud.plat.admin.api.constant.CommonConstant;
import com.cloud.plat.admin.api.dto.SysRoleDto;
import com.cloud.plat.admin.api.dto.UserDto;
import com.cloud.plat.admin.api.dto.UserMobileDto;
import com.cloud.plat.admin.api.entity.SysDept;
import com.cloud.plat.admin.api.entity.SysRole;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.admin.api.entity.SysUserRole;
import com.cloud.plat.admin.service.*;
import com.cloud.plat.common.core.constant.CommonConstants;
import com.cloud.plat.common.core.constant.OAuthConstant;
import com.cloud.plat.common.core.constant.SecurityConstant;
import com.cloud.plat.common.core.exception.HytException;
import com.cloud.plat.common.core.exception.LoginFailLimitException;
import com.cloud.plat.common.core.util.ObjectUtil;
import com.cloud.plat.common.core.util.PasswordUtil;
import com.cloud.plat.common.log.annotation.PlatLogAo;
import com.cloud.plat.common.log.enums.LogTypeEnum;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import com.cloud.plat.common.base.util.PageUtil;
import com.cloud.plat.common.base.util.ResultUtil;
import com.cloud.plat.common.base.vo.PageVo;
import com.cloud.plat.common.base.vo.Result;
import com.cloud.plat.common.base.vo.SearchVo;
import com.cloud.plat.common.security.annotation.Inner;
import com.cloud.plat.common.security.service.SecurityUserDetails;
import com.cloud.plat.common.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @describe x
 */
@Slf4j
@RestController
@Api(value = "sys_user", tags = "用户接口")
@RequestMapping("/sys/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class SysUserController {

    public static final String USER = "user::";

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysDeptService departmentService;

    @Autowired
    private SysDeptHeaderService departmentHeaderService;

    @Autowired
    private ISysUserRoleService iUserRoleService;

    @Autowired
    private SysUserRoleService userRoleService;

//    @Autowired
//    private AddMessage addMessage;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @PersistenceContext
    private EntityManager entityManager;


//    @RequestMapping(value = "/getInfoByMobile")
//    @PlatLogAo(description = "短信登录", type = LogTypeEnum.LOGIN)
//    @ApiOperation(value = "短信登录接口")
//    public Result<Object> smsLogin(@RequestParam String mobile, @RequestParam String code,
//                                   @RequestParam(required = false) Boolean saveLogin) {
//
//
//    }


    @RequestMapping(value = "/smsLogin")
    @PlatLogAo(description = "短信登录", type = LogTypeEnum.LOGIN)
    @ApiOperation(value = "短信登录接口")
    public Result<Object> smsLogin(@RequestParam String mobile, @RequestParam String code,
                                   @RequestParam(required = false) Boolean saveLogin) {
        String redisCode = redisTemplate.get(CommonConstant.PRE_SMS + mobile);
        if (!code.equals(redisCode)) {
            throw new HytException("验证码错误");
        }
        SysUser u = userService.findByMobile(mobile);
        if (u == null) {
            throw new HytException("手机号不存在");
        }
        if (!u.getStatus().equals(CommonConstant.USER_STATUS_NORMAL)) {
            throw new LoginFailLimitException("该用户被禁用 或存在异常，请联系管理员");
        }
        String accessToken = securityUtil.getToken(u.getUsername(), saveLogin);
        // 记录日志使用
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(new SecurityUserDetails(u), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResultUtil.data(accessToken);
    }

    @RequestMapping(value = "/resetByMobile", method = RequestMethod.POST)
    @ApiOperation(value = "通过短信重置密码")
    @PlatLogAo(description = "通过短信重置密码", type = LogTypeEnum.NORMAL)
    public Result<Object> resetByMobile(@RequestParam String mobile,
                                        @RequestParam String password,
                                        @RequestParam String passStrength) {

        SysUser u = userService.findByMobile(mobile);
        String encryptPass = new BCryptPasswordEncoder().encode(password);
        u.setPassword(encryptPass);
        userService.update(u);
        // 删除缓存
        redisTemplate.delete(USER + u.getUsername());
        return ResultUtil.success("重置密码成功");
    }


    @RequestMapping(value = "/registMobile", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    @PlatLogAo(description = "注册用户", type = LogTypeEnum.NORMAL)
    public Result<Object> registMobile(@Valid UserMobileDto u) {
        // 校验是否已存在
        String redisCode = redisTemplate.get(CommonConstant.PRE_SMS + u.getMobile());
        if (!u.getCode().equals(redisCode)) {
            throw new HytException("验证码错误");
        }
        checkUserInfo(u.getUsername(), u.getMobile());
        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        SysUser user = new SysUser();
        user.setType(CommonConstant.USER_PRI_TYPE_NORMAL);
        ObjectUtil.autoFillEqFieldsWithArgs(u, user);
        user = userService.save(user);
        user.setUsername(u.getMobile());
        // 默认角色
        List<SysRole> roleList = roleService.findByDefaultRole(true);
        if (roleList != null && roleList.size() > 0) {
            for (SysRole role : roleList) {
                SysUserRole ur = new SysUserRole().setUserId(user.getId()).setRoleId(role.getId());
                userRoleService.save(ur);
            }
        }
        // 异步发送创建账号消息
        // addMessage.addSendMessage(user.getId());
        return ResultUtil.data(user);
    }


    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    @PlatLogAo(description = "注册用户", type = LogTypeEnum.NORMAL)
    public Result<Object> regist(@Valid UserDto userDto) {

        // 校验是否已存在
        checkUserInfo(userDto.getUsername(), userDto.getMobile());
        String encryptPass = new BCryptPasswordEncoder().encode(userDto.getPassword());
        SysUser u = new SysUser();
        ObjectUtil.copyPropertiesIgnoreNull(userDto, u);
        u.setPassword(encryptPass).setType(CommonConstant.USER_PRI_TYPE_NORMAL);
        SysUser user = userService.save(u);

        // 默认角色
        List<SysRole> roleList = roleService.findByDefaultRole(true);
        if (roleList != null && roleList.size() > 0) {
            for (SysRole role : roleList) {
                SysUserRole ur = new SysUserRole().setUserId(user.getId()).setRoleId(role.getId());
                userRoleService.save(ur);
            }
        }
        // 异步发送创建账号消息
        // addMessage.addSendMessage(user.getId());

        return ResultUtil.data(user);
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<SysUser> getUserInfo() {
        SysUser u = securityUtil.getCurrUser();
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        u.setPassword(null);
        return new ResultUtil<SysUser>().setData(u);
    }


    @Inner
    @RequestMapping(value = "/info/{username}", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<SysUser> getUserInfoByuserame(@PathVariable("username") String username) {
        SysUser u = userService.findByUsername(username);
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        u.setPassword(null);
        return new ResultUtil<SysUser>().setData(u);
    }


    @RequestMapping(value = "/changeMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改绑定手机")
    @PlatLogAo(description = "修改绑定手机", type = LogTypeEnum.NORMAL)
    public Result<Object> changeMobile(String mobile, String code) {
        String redisCode = redisTemplate.get(CommonConstant.PRE_SMS + mobile);
        if (!code.equals(redisCode)) {
            throw new HytException("验证码错误");
        } else {
            SysUser u = securityUtil.getCurrUser();
            u.setUsername(mobile);
            u.setMobile(mobile);
            userService.update(u);
            // 删除缓存
            redisTemplate.delete(USER + u.getUsername());
        }
        return ResultUtil.success("修改手机号成功");
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public Result<Object> unLock(@RequestParam String password) {

        SysUser u = securityUtil.getCurrUser();
        if (!new BCryptPasswordEncoder().matches(password, u.getPassword())) {
            return ResultUtil.error("密码不正确");
        }
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/resetPass", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码")
    @PlatLogAo(description = "重置密码", type = LogTypeEnum.NORMAL)
    public Result<Object> resetPass(@RequestParam Long[] ids) {

        for (Long id : ids) {
            SysUser u = userService.get(id);
            // 在线DEMO所需
            if ("test".equals(u.getUsername()) || "test2".equals(u.getUsername()) || "admin".equals(u.getUsername())) {
                throw new HytException("测试账号及管理员账号不得重置");
            }
            u.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userService.update(u);
            redisTemplate.delete(USER + u.getUsername());
        }
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户自己资料", notes = "用户名密码等不会修改 需要username更新缓存")
    @PlatLogAo(description = "修改用户自己资料", type = LogTypeEnum.NORMAL)
    @CacheEvict(key = "#u.username")
    public Result<Object> editOwn(SysUser u) {

        SysUser old = securityUtil.getCurrUser();
        // 不能修改的字段
        u.setUsername(old.getUsername()).setPassword(old.getPassword()).setType(old.getType()).setStatus(old.getStatus());
        userService.update(u);
        return ResultUtil.success("修改成功");
    }

    /**
     * 线上demo不允许测试账号改密码
     *
     * @param password
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    @PlatLogAo(description = "修改密码", type = LogTypeEnum.NORMAL)
    public Result<Object> modifyPass(@ApiParam("旧密码") @RequestParam String password,
                                     @ApiParam("新密码") @RequestParam String newPass,
                                     @ApiParam("密码强度") @RequestParam String passStrength) {
        SysUser user = securityUtil.getCurrUser();
        // 在线DEMO所需
        if ("test".equals(user.getUsername()) || "test2".equals(user.getUsername())) {
            return ResultUtil.error("演示账号不支持修改密码");
        }

        if (!PasswordUtil.isRightPass(newPass)) {
            throw new HytException("密码长度请确保 8 位数以上,且包含 数字字母和符号");
        }

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return ResultUtil.error("旧密码不正确");
        }
        String newEncryptPass = new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        userService.update(user);
        // 手动更新缓存
        redisTemplate.delete(USER + user.getUsername());

        return ResultUtil.success("修改密码成功");
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<Page<SysUser>> getByCondition(SysUser user,
                                                SearchVo searchVo,
                                                PageVo pageVo) {
        Page<SysUser> page = userService.findByCondition(user, searchVo, PageUtil.initPage(pageVo));
        for (SysUser u : page.getContent()) {
            // 关联角色
            List<SysRole> list = iUserRoleService.findByUserId(u.getId());
            List<SysRoleDto> roleDTOList = list.stream().map(e -> {
                return new SysRoleDto().setId(e.getId()).setName(e.getName()).setDescription(e.getDescription());
            }).collect(Collectors.toList());
            u.setRoles(roleDTOList);
            // 游离态 避免后面语句导致持久化
            entityManager.detach(u);
            u.setPassword(null);
        }
        return new ResultUtil<Page<SysUser>>().setData(page);
    }

    @RequestMapping(value = "/getByDepartmentId/{departmentId}", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<List<SysUser>> getByCondition(@PathVariable Long departmentId) {

        List<SysUser> list = userService.findByDepartmentId(departmentId);
        entityManager.clear();
        list.forEach(u -> {
            u.setPassword(null);
        });
        return new ResultUtil<List<SysUser>>().setData(list);
    }

    @RequestMapping(value = "/searchByName/{username}", method = RequestMethod.GET)
    @ApiOperation(value = "通过用户名搜索用户")
    public Result<List<SysUser>> searchByName(@PathVariable String username) throws UnsupportedEncodingException {

        List<SysUser> list = userService.findByUsernameLikeAndStatus(URLDecoder.decode(username, "utf-8"), CommonConstants.STATUS_NORMAL);
        entityManager.clear();
        list.forEach(u -> {
            u.setPassword(null);
        });
        return new ResultUtil<List<SysUser>>().setData(list);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部用户数据")
    public Result<List<SysUser>> getAll() {

        List<SysUser> list = userService.getAll();
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        for (SysUser u : list) {
            u.setPassword(null);
        }
        return new ResultUtil<List<SysUser>>().setData(list);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    @PlatLogAo(description = "添加用户", type = LogTypeEnum.NORMAL)
    public Result<Object> add(@Valid SysUser u,
                              @RequestParam(required = false) Long[] roleIds) {
        // 校验是否已存在
        checkUserInfo(u.getUsername(), u.getMobile());

        if (!PasswordUtil.isRightPass(u.getPassword())) {
            throw new HytException("密码长度请确保 8 位数以上,且包含 数字字母和符号");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        if (null != u.getDeptId()) {
            SysDept d = departmentService.get(u.getDeptId());
            if (d != null) {
                u.setDeptTitle(d.getTitle());
            }
        } else {
            u.setDeptId(null);
            u.setDeptTitle("");
        }
        SysUser user = userService.save(u);
        if (roleIds != null) {
            // 添加角色
            List<SysUserRole> userRoles = Arrays.asList(roleIds).stream().map(e -> {
                return new SysUserRole().setUserId(u.getId()).setRoleId(e);
            }).collect(Collectors.toList());
            userRoleService.saveOrUpdateAll(userRoles);
        }
        // 发送创建账号消息
        // addMessage.addSendMessage(user.getId());
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理员修改资料", notes = "需要通过id获取原用户信息 需要username更新缓存")
    @PlatLogAo(description = "管理员修改资料", type = LogTypeEnum.NORMAL)
    @CacheEvict(key = "#u.username")
    public Result<Object> edit(SysUser u,
                               @RequestParam(required = false) Long[] roleIds) {

        SysUser old = userService.get(u.getId());
        u.setUsername(old.getUsername());
        // 若修改了手机和邮箱判断是否唯一
        if (old.getMobile() != null && !old.getMobile().equals(u.getMobile())
                && userService.findByMobile(u.getMobile()) != null) {
            return ResultUtil.error("该手机号已绑定其他账户");
        }
        if (null != u.getDeptId()) {
            SysDept d = departmentService.get(u.getDeptId());
            if (d != null) {
                u.setDeptTitle(d.getTitle());
            }
        } else {
            u.setDeptId(null);
            u.setDeptTitle("");
        }
        u.setPassword(old.getPassword());
        userService.update(u);
        // 删除该用户角色
        userRoleService.deleteByUserId(u.getId());
        if (roleIds != null) {
            // 新角色
            List<SysUserRole> userRoles = Arrays.asList(roleIds).stream().map(e -> {
                return new SysUserRole().setRoleId(e).setUserId(u.getId());
            }).collect(Collectors.toList());
            userRoleService.saveOrUpdateAll(userRoles);
        }
        // 手动删除缓存
        redisTemplate.delete("userRole::" + u.getId());
        redisTemplate.delete("userRole::depIds::" + u.getId());
        redisTemplate.delete("permission::userMenuList::" + u.getId());
        return ResultUtil.success("修改成功");
    }

    @RequestMapping(value = "/admin/disable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    @PlatLogAo(description = "后台禁用用户", type = LogTypeEnum.NORMAL)
    public Result<Object> disable(@ApiParam("用户唯一id标识") @PathVariable Long userId) {

        SysUser user = userService.get(userId);
        user.setStatus(CommonConstant.USER_STATUS_LOCK);
        userService.update(user);
        // 手动更新缓存

        // 删除当前用户登录accessToken
        String token = redisTemplate.get(SecurityConstant.USER_TOKEN + user.getUsername());
        redisTemplate.delete(token);
        redisTemplate.delete(SecurityConstant.USER_TOKEN + user.getUsername());
        // 删除当前用户授权第三方应用的access_token
        redisTemplate.deleteByPattern(OAuthConstant.OAUTH_TOKEN_PRE + user.getUsername() + "::*");
        redisTemplate.deleteByPattern(OAuthConstant.OAUTH_REFRESH_TOKEN_PRE + user.getUsername() + "::*");

        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/admin/enable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public Result<Object> enable(@ApiParam("用户唯一id标识") @PathVariable Long userId) {

        SysUser user = userService.get(userId);
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userService.update(user);
        // 手动更新缓存
        redisTemplate.delete(USER + user.getUsername());
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delAllByIds(@RequestParam Long[] ids) {
        for (Long id : ids) {
            SysUser u = userService.get(id);
            // 删除相关缓存
            redisTemplate.delete(USER + u.getUsername());
            redisTemplate.delete("userRole_" + u.getId());
            redisTemplate.delete("userRole::depIds:" + u.getId());
            redisTemplate.delete("permission::userMenuList:" + u.getId());
            redisTemplate.deleteByPattern("department::*");
            userService.delete(id);
            // 删除关联角色
            userRoleService.deleteByUserId(id);
            // 删除关联部门负责人
            departmentHeaderService.deleteByUserId(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @ApiOperation(value = "导入用户数据")
    public Result<Object> importData(@RequestBody List<SysUser> users) {

        List<Integer> errors = new ArrayList<>();
        List<String> reasons = new ArrayList<>();
        int count = 0;
        for (SysUser u : users) {
            count++;
            // 验证用户名密码不为空
            if (StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())) {
                errors.add(count);
                reasons.add("用户名或密码为空");
                continue;
            }
            // 验证用户名唯一
            if (userService.findByUsername(u.getUsername()) != null) {
                errors.add(count);
                reasons.add("用户名已存在");
                continue;
            }
            // 加密密码
            u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
            // 验证部门id正确性
            if (null != u.getDeptId()) {
                try {
                    SysDept d = departmentService.get(u.getDeptId());
                    log.info(d.toString());
                } catch (Exception e) {
                    errors.add(count);
                    reasons.add("部门id不存在");
                    continue;
                }
            }
            if (u.getStatus() == null) {
                u.setStatus(CommonConstant.USER_STATUS_NORMAL);
            }
            userService.save(u);
            // 分配默认角色
            if (u.getDefaultRole() != null && u.getDefaultRole() == 1) {
                List<SysRole> roleList = roleService.findByDefaultRole(true);
                if (roleList != null && roleList.size() > 0) {
                    for (SysRole role : roleList) {
                        SysUserRole ur = new SysUserRole().setUserId(u.getId()).setRoleId(role.getId());
                        userRoleService.save(ur);
                    }
                }
            }
        }
        // 批量保存数据
        int successCount = users.size() - errors.size();
        String successMessage = "全部导入成功，共计 " + successCount + " 条数据";
        String failMessage = "导入成功 " + successCount + " 条，失败 " + errors.size() + " 条数据。<br>" +
                "第 " + errors.toString() + " 行数据导入出错，错误原因分别为：<br>" + reasons.toString();
        String message = "";
        if (errors.isEmpty()) {
            message = successMessage;
        } else {
            message = failMessage;
        }
        return ResultUtil.success(message);
    }

    /**
     * 校验
     *
     * @param username 用户名 不校验传空字符或null 下同
     * @param mobile   手机号
     */
    public void checkUserInfo(String username, String mobile) {

        // 禁用词
        // StopWordsUtil.matchWord(username);

        if (StrUtil.isNotBlank(username) && userService.findByUsername(username) != null) {
            throw new HytException("该登录账号已被注册");
        }

        if (StrUtil.isNotBlank(mobile) && userService.findByMobile(mobile) != null) {
            throw new HytException("该手机号已被注册");
        }
    }

    /**
     * 校验
     *
     * @param mobile 手机号
     */
    public void checkUserInfo(String mobile) {

        if (StrUtil.isNotBlank(mobile) && userService.findByMobile(mobile) != null) {
            throw new HytException("该手机号已被注册");
        }
    }

}
