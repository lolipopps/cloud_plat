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

package com.cloud.plat.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.plat.admin.api.entity.SysOauthClient;
import com.cloud.plat.admin.dao.SysOauthClientDao;
import com.cloud.plat.admin.service.SysOauthClientService;
import com.cloud.plat.common.base.redis.RedisTemplateHelper;
import com.cloud.plat.common.base.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.concurrent.TimeUnit;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 20:17
 * @contact 269016084@qq.com
 **/
@Slf4j
@Service
@Transactional
@CacheConfig(cacheNames = "client")
public class SysOauthClientServiceImpl implements SysOauthClientService {

    @Autowired
    private SysOauthClientDao clientDao;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Override
    public SysOauthClientDao getRepository() {
        return clientDao;
    }

    @Override
    @Cacheable(key = "#id")
    public SysOauthClient get(Long id) {

        // 避免缓存穿透
        String result = redisTemplate.get("client::" + id);
        if ("null".equals(result)) {
            return null;
        }
        SysOauthClient client = clientDao.findById(id).orElse(null);
        if (client == null) {
            redisTemplate.set("client::" + id, "null", 5L, TimeUnit.MINUTES);
        }
        return client;
    }

    @Override
    @CacheEvict(key = "#client.id")
    public SysOauthClient update(SysOauthClient client) {

        return clientDao.saveAndFlush(client);
    }

    @Override
    @CacheEvict(key = "#client.id")
    public void delete(Long id) {

        clientDao.deleteById(id);
    }

    @Override
    public Page<SysOauthClient> findByCondition(SysOauthClient client, SearchVo searchVo, Pageable pageable) {

        return clientDao.findAll(new Specification<SysOauthClient>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<SysOauthClient> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> nameField = root.get("name");
                Path<String> clientIdField = root.get("clientId");
                Path<String> homeUriField = root.get("homeUri");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<>();

                //模糊搜素
                if (StrUtil.isNotBlank(client.getName())) {
                    list.add(cb.like(nameField, '%' + client.getName() + '%'));
                }
                if (StrUtil.isNotBlank(client.getHomeUri())) {
                    list.add(cb.like(homeUriField, '%' + client.getHomeUri() + '%'));
                }
                if (StrUtil.isNotBlank(client.getHomeUri())) {
                    list.add(cb.like(clientIdField, '%' + client.getClientId() + '%'));
                }

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public SysOauthClient findByCilentId(String clientId) {
        return clientDao.findByClientId(clientId);
    }

}
