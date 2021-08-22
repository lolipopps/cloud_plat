package com.cloud.plat.admin.controller;

import com.cloud.plat.admin.api.entity.PlatLog;
import com.cloud.plat.admin.service.PlatLogService;
import com.cloud.plat.common.base.util.PageUtil;
import com.cloud.plat.common.base.util.ResultUtil;
import com.cloud.plat.common.base.vo.PageVo;
import com.cloud.plat.common.base.vo.Result;
import com.cloud.plat.common.base.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @describe x
 */
@Slf4j
@RestController
@Api(value = "sys_log", tags = "日志管理接口")
@RequestMapping("/common/log")
@Transactional
public class SysLogController {

    @Value("${hyt.logRecord.es:false}")
    private Boolean esRecord;


    @Autowired
    private PlatLogService logService;

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取全部")
    public Result<Object> getAllByPage(@RequestParam(required = false) Integer type,
                                       @RequestParam String key,
                                       SearchVo searchVo,
                                       PageVo pageVo) {


        Page<PlatLog> log = logService.findByConfition(type, key, searchVo, PageUtil.initPage(pageVo));
        return ResultUtil.data(log);

    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除")
    public Result<Object> delByIds(@RequestParam Long[] ids) {

        for (Long id : ids) {

            logService.delete(id);
        }

        return ResultUtil.success("删除成功");
    }

    @RequestMapping(value = "/delAll", method = RequestMethod.POST)
    @ApiOperation(value = "全部删除")
    public Result<Object> delAll() {

        if (esRecord) {

            logService.deleteAll();
        }
        return ResultUtil.success("删除成功");
    }
}
