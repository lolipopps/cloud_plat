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

package com.cloud.plat;

import com.cloud.plat.common.feign.annotation.EnablePigFeignClients;
import com.cloud.plat.common.security.annotation.EnablePigResourceServer;
import com.cloud.plat.common.swagger.annotation.EnablePigSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/22 15:45
 * @contact 269016084@qq.com
 *
 **/
@EnablePigSwagger2
@EnablePigResourceServer
@EnablePigFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class PlatSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatSystemApplication.class, args);
	}

}
