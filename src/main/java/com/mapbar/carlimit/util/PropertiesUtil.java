package com.mapbar.carlimit.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 获取properties文件内容的工具类 加载配置文件，同时返回指定的key, <br>
 * 如果指定的key不存在，就会重新加载配置文件（适合于动态的固定key的配置文件加载）
 */
public class PropertiesUtil {
	protected PropertiesUtil() {
		params.put(LimitCityEnum.BEIJING, new HashMap<String, String>());
		params.put(LimitCityEnum.CHANGCHUN, new HashMap<String, String>());
		params.put(LimitCityEnum.CHENGDU, new HashMap<String, String>());
		params.put(LimitCityEnum.GUIYANG, new HashMap<String, String>());
		params.put(LimitCityEnum.HAERBIN, new HashMap<String, String>());
		params.put(LimitCityEnum.HANGZHOU, new HashMap<String, String>());
		params.put(LimitCityEnum.LANZHOU, new HashMap<String, String>());
		params.put(LimitCityEnum.NANCHANG, new HashMap<String, String>());
		params.put(LimitCityEnum.TIANJIN, new HashMap<String, String>());
		params.put(LimitCityEnum.WUHAN, new HashMap<String, String>());
	}

	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	// 存放配置文件的所有的key-value
	private Map<LimitCityEnum, Map<String, String>> params = new HashMap<LimitCityEnum, Map<String, String>>();

	// 根据文件名称-key，返回相应key的值
	public static String getPropertiesByKey(LimitCityEnum city, String key) {
		PropertiesUtil p = new PropertiesUtil();
		try {
			Map<String, String> map = p.params.get(city);
			logger.info("开始初始化配置文件【" + city.getDesc() + "】");
			// map.clear();
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(city.getDesc());
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			// Reader reader = new InputStreamReader(in, "UTF-8");
			Properties prop = new Properties();
			// prop.load(in);
			prop.load(br);
			Set<Entry<Object, Object>> allKey = prop.entrySet();
			for (Entry<Object, Object> entry : allKey) {
				map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
			br.close();
			logger.info("成功初始化配置文件【" + city.getDesc() + "】");
			return map.get(key);
		} catch (Exception e) {
			logger.error("初始化配置文件【" + city.getDesc() + "】出错");
			e.printStackTrace();
		}
		return null;
	}

	// 根据文件名称-key，返回相应key的值
	public static Map<String, String> getPropertiesByKey(LimitCityEnum city) {
		PropertiesUtil p = new PropertiesUtil();
		try {
			Map<String, String> map = p.params.get(city);
			logger.info("开始初始化配置文件【" + city.getDesc() + "】");
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(city.getDesc());
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			// Reader reader = new InputStreamReader(in, "UTF-8");
			Properties prop = new Properties();
			// prop.load(in);
			prop.load(br);
			Set<Entry<Object, Object>> allKey = prop.entrySet();
			for (Entry<Object, Object> entry : allKey) {
				map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
			br.close();
			logger.info("成功初始化配置文件【" + city.getDesc() + "】");
			return map;
		} catch (Exception e) {
			logger.error("初始化配置文件【" + city.getDesc() + "】出错");
			e.printStackTrace();
		}
		return null;
	}

	// 根据文件名称-key，返回相应key的值
	public static void refreshPropertiesByCity(LimitCityEnum city) {
		PropertiesUtil p = new PropertiesUtil();
		try {
			Map<String, String> map = p.params.get(city);
			logger.info("开始刷新配置文件【" + city.getDesc() + "】");
			map.clear();
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(city.getDesc());
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			Properties prop = new Properties();
			prop.load(br);
			Set<Entry<Object, Object>> allKey = prop.entrySet();
			for (Entry<Object, Object> entry : allKey) {
				map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
			br.close();
			logger.info("成功刷新配置文件【" + city.getDesc() + "】");
		} catch (Exception e) {
			logger.error("刷新配置文件【" + city.getDesc() + "】出错");
			e.printStackTrace();
		}
	}

}
