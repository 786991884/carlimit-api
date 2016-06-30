package com.mapbar.carlimit.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;

import com.mapbar.carlimit.dao.CarLimitDao;
import com.mapbar.carlimit.util.LimitCityEnum;
import com.mapbar.carlimit.util.PropertiesUtil;

@Repository
public class CarLimitDaoImpl implements CarLimitDao {
	private static final String CACHE_PREFIX = "CARLIMIT_";
	@Autowired
	private RedisTemplate<String, Map<String, String>> redisTemplate;

	@Override
	public String getLimitDataByCity(LimitCityEnum city, String key) {
		return (String) redisTemplate.opsForHash().get(CACHE_PREFIX + city.getCode(), key);
	}

	@Override
	public void addLimitData(final LimitCityEnum city) {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			@Override
			public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();
				@SuppressWarnings("unchecked")
				K key = (K) (CACHE_PREFIX + city.getCode());
				Map<String, String> map = PropertiesUtil.getPropertiesByKey(city);
				operations.opsForHash().putAll(key, map);
				return operations.exec();
			}
		});
		/*
		 * for (Map.Entry<String, String> entry : map.entrySet()) { redisTemplate.opsForHash().put(city.getCode(), entry.getKey(), entry.getValue()); }
		 */
	}

	@Override
	public void refreshLimitDataByCity(final LimitCityEnum city, final long modifiedTime) {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			@Override
			public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
				@SuppressWarnings("unchecked")
				K key = (K) (CACHE_PREFIX + city.getCode());
				String keyTime = CACHE_PREFIX + (city.getCode() + "_MODTIME");
				Boolean hasKey = operations.opsForHash().hasKey(key, keyTime);
				if (!hasKey) {
					operations.multi();
					Map<String, String> map = PropertiesUtil.getPropertiesByKey(city);
					operations.opsForHash().putAll(key, map);
					operations.opsForHash().put(key, keyTime, String.valueOf(modifiedTime));
				} else {
					String str = (String) operations.opsForHash().get(key, keyTime);
					long time = Long.valueOf(str);
					operations.multi();
					if (time < modifiedTime) {
						operations.delete(key);
						Map<String, String> map = PropertiesUtil.getPropertiesByKey(city);
						operations.opsForHash().putAll(key, map);
						operations.opsForHash().put(key, keyTime, String.valueOf(modifiedTime));
					}
				}
				return operations.exec();
			}
		});
	}

	@Override
	public boolean limitDataHaskey(LimitCityEnum city, String key) {
		return redisTemplate.opsForHash().hasKey(CACHE_PREFIX + city.getCode(), key);
	}

}
