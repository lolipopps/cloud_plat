/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.plat.admin.service;
import com.cloud.plat.admin.api.entity.SysUser;
import com.cloud.plat.common.base.base.BaseService;
import com.cloud.plat.common.base.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 19:04
 * @contact 269016084@qq.com
 *
 **/
public interface SysUserService extends BaseService<SysUser, Long> {

	/**
	 * 通过用户名获取用户
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);

	/**
	 * 通过手机获取用户
	 * @param mobile
	 * @return
	 */
	SysUser findByMobile(String mobile);

	/**
	 * 通过邮件和状态获取用户
	 * @param email
	 * @return
	 */
	SysUser findByEmail(String email);

	/**
	 * 多条件分页获取用户
	 * @param user
	 * @param searchVo
	 * @param pageable
	 * @return
	 */
	Page<SysUser> findByCondition(SysUser user, SearchVo searchVo, Pageable pageable);

	/**
	 * 通过部门id获取
	 * @param departmentId
	 * @return
	 */
	List<SysUser> findByDepartmentId(Long departmentId);

	/**
	 * 通过用户名模糊搜索
	 * @param username
	 * @param status
	 * @return
	 */
	List<SysUser> findByUsernameLikeAndStatus(String username, Integer status);

	/*** @param username
	 * @param ids
	 * @return
	 */
	List<SysUser> findByIds(Long[] ids);

	/**
	 * 更新部门名称
	 * @param deptId
	 * @param deptTitle
	 */
	void updateDeptTitle(Long deptId, String deptTitle);


}
