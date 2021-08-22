package com.cloud.plat.common.core.exception;

import lombok.Data;

/**
 * @Description <类描述>
 * @author hyt
 * @create 2021/8/21 18:10
 * @contact 269016084@qq.com
 *
 **/
@Data
public class HytException extends RuntimeException {

    private String msg;

    public HytException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
