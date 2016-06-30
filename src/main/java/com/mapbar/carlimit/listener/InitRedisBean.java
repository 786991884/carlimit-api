package com.mapbar.carlimit.listener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.mapbar.carlimit.service.CarLimitService;
import com.mapbar.carlimit.util.LimitCityEnum;

//@Component
public class InitRedisBean implements InitializingBean {
	@Autowired
	CarLimitService carLimitService;

	@Override
	public void afterPropertiesSet() throws Exception {
		LimitCityEnum[] values = LimitCityEnum.values();
		for (LimitCityEnum limitCity : values) {
			// carLimitService.refreshLimitDataByCity(limitCity);
		}
	}
}
