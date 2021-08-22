package com.cloud.plat.common.base.base;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/22 10:41
 * @contact 269016084@qq.com
 *
 **/
// 自定义接口 不会创建接口的实例 必须加此注解
@NoRepositoryBean
public interface BaseDao<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

}
