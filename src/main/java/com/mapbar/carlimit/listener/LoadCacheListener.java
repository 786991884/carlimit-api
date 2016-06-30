package com.mapbar.carlimit.listener;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mapbar.carlimit.service.CarLimitService;
import com.mapbar.carlimit.util.LimitCityEnum;

@Component
public class LoadCacheListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	CarLimitService carLimitService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		final ApplicationContext app = event.getApplicationContext();
		// 当存在父子容器时，此判断很有用
		if (null == app.getParent() && "Root WebApplicationContext".equals(app.getDisplayName())) {
			String city = "";
			LimitCityEnum[] values = LimitCityEnum.values();
			for (LimitCityEnum limitCity : values) {
				city = limitCity.getCode();
				String name = LimitCityEnum.adapt(city).getDesc();
				name = name.substring(name.lastIndexOf("/") + 1);
				String path = this.getClass().getResource("/").getPath() + "data";
				File dataFile = new File(path, name);
				long modifiedTime = dataFile.lastModified();
				carLimitService.refreshLimitDataByCity(limitCity, modifiedTime);
			}

		}
	}

}
