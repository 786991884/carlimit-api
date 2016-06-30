package com.mapbar.carlimit.util;

import java.util.HashMap;
import java.util.Map;

public enum LimitCityEnum {
	// 通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
	// 赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
	BEIJING("BEIJING", "./data/cartraffic.properties"), //
	CHANGCHUN("CHANGCHUN", "./data/cartraffic_changchun.properties"), //
	CHENGDU("CHENGDU", "./data/cartraffic_chengdu.properties"), //
	GUIYANG("GUIYANG", "./data/cartraffic_guiyang.properties"), //
	HAERBIN("HAERBIN", "./data/cartraffic_haerbin.properties"), //
	HANGZHOU("HANGZHOU", "./data/cartraffic_hangzhou.properties"), //
	LANZHOU("LANZHOU", "./data/cartraffic_lanzhou.properties"), //
	NANCHANG("NANCHANG", "./data/cartraffic_nanchang.properties"), //
	TIANJIN("TIANJIN", "./data/cartraffic_tianjin.properties"), //
	WUHAN("WUHAN", "./data/cartraffic_wuhan.properties");
	private String code;
	private String desc;

	private LimitCityEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static LimitCityEnum adapt(String code) {
		code = code.trim();
		LimitCityEnum[] allStatus = values();
		for (LimitCityEnum limitCityEnum : allStatus) {
			if (code.equals(limitCityEnum.getCode())) {
				return limitCityEnum;
			}
		}
		return LimitCityEnum.BEIJING;
	}

	public static Map<String, String> createKeyByCode() {
		Map<String, String> map = new HashMap<String, String>();
		for (LimitCityEnum limitCityEnum : LimitCityEnum.values()) {
			map.put(limitCityEnum.getCode(), limitCityEnum.getDesc());
		}
		return map;
	}
}
