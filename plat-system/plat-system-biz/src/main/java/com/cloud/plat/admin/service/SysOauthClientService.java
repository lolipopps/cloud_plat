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

import com.cloud.plat.admin.api.entity.SysOauthClient;
import com.cloud.plat.common.base.base.BaseService;
import com.cloud.plat.common.base.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 20:17
 * @contact 269016084@qq.com
 *
 **/
public interface SysOauthClientService extends BaseService<SysOauthClient, Long>  {
	/**
	 * 多条件分页获取
	 * @param client
	 * @param searchVo
	 * @param pageable
	 * @return
	 */
	Page<SysOauthClient> findByCondition(SysOauthClient client, SearchVo searchVo, Pageable pageable);


	/** 
	 * 
	 * @description   
	 * @author hyt
	 * @date 2021/8/21 23:49
	 * @param 
	 * @return 
	 *
	 **/
	SysOauthClient findByCilentId(String clientId);

}
