package com.cloud.plat.admin.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Oauth2TokenInfoVo implements Serializable {

    private String clientId;

    private String username;
}
