package com.cloud.plat.common.base.base;
import com.cloud.plat.common.base.util.ResultUtil;
import com.cloud.plat.common.base.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * @describe x
 */
public abstract class LessBaseController<E, ID extends Serializable> {

    /**
     * 获取service
     * @return
     */
    @Autowired
    public abstract BaseService<E, ID> getService();

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过id获取")

    public Result<E> get(@PathVariable ID id) {
        E entity = getService().get(id);
        return new ResultUtil<E>().setData(entity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新数据")

    public Result<E> update(E entity) {
        E e = getService().update(entity);
        return new ResultUtil<E>().setData(e);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存数据")
    public Result<E> save(E entity) {
        E e = getService().save(entity);
        return new ResultUtil<E>().setData(e);
    }

}
