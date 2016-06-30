package com.mapbar.carlimit.dao;

import com.mapbar.carlimit.util.LimitCityEnum;

public interface CarLimitDao {
	/**
	 * 通过城市获取限行数据
	 * 
	 * @param limitCity
	 * @return
	 */
	String getLimitDataByCity(LimitCityEnum city, String key);

	/**
	 * 添加城市限行数据
	 * 
	 * @param city
	 * @param fileName
	 */
	void addLimitData(LimitCityEnum city);

	/**
	 * 根据城市刷新限行数据
	 * 
	 * @param city
	 * @param fileName
	 */
	void refreshLimitDataByCity(LimitCityEnum city, long modifiedTime);

	/**
	 * 是否存在该key
	 * 
	 * @param city
	 * @param key
	 * @return
	 */
	boolean limitDataHaskey(LimitCityEnum city, String key);
}
