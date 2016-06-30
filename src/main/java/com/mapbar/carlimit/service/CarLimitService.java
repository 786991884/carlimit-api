package com.mapbar.carlimit.service;

import java.util.List;

import com.mapbar.carlimit.model.CarLimitModel;
import com.mapbar.carlimit.util.LimitCityEnum;

public interface CarLimitService {

	String getCarTraffic(CarLimitModel carLimitModel);

	List<String> getCarTrafficForWeek(CarLimitModel carLimitModel);

	void refreshLimitDataByCity(LimitCityEnum city, long modifiedTime);
}
