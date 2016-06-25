/**
 * Alibaba.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.dagger.dtest.enums;

/**
 * 用于控制运行的环境变量枚举类，只用于环境变量输入
 * 
 * 
 * @author 马洪良
 * @version $Id: EnvParamEnum.java, v 0.1 2014年3月13日 下午2:05:00 马洪良 Exp $
 */
public enum EnvParamEnum {

	DATA_PROVIDER_FILE("stest.provider.file", "数据驱动文件,只能使用绝对路径");

	private String envKey;
	private String description;

	private EnvParamEnum(String envKey, String desc) {
		this.envKey = envKey;
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public String getEnvKey() {
		return envKey;
	}
}
