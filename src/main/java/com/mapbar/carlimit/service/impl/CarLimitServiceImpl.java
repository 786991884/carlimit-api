package com.mapbar.carlimit.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapbar.carlimit.dao.CarLimitDao;
import com.mapbar.carlimit.model.CarLimitModel;
import com.mapbar.carlimit.service.CarLimitService;
import com.mapbar.carlimit.util.CarLimitUtil;
import com.mapbar.carlimit.util.LimitCityEnum;

@Service
public class CarLimitServiceImpl implements CarLimitService {
	@Autowired
	private CarLimitDao carLimitDao;

	@Override
	public String getCarTraffic(CarLimitModel carLimitModel) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sysdate = null;
		String sdate = carLimitModel.getSdate();
		if (StringUtils.isNotEmpty(sdate)) {
			sysdate = sdate;
		} else {
			sysdate = sdf.format(new Date());
		}
		String city = carLimitModel.getCt();
		if (StringUtils.isEmpty(city)) {
			city = "北京市";
		}
		LimitCityEnum limtiCity = null;
		if (city.equals("北京市") || city.equals("北京")) {
			limtiCity = LimitCityEnum.BEIJING;
		} else if (city.equals("成都市") || city.equals("成都")) {
			limtiCity = LimitCityEnum.CHENGDU;
		} else if (city.equals("贵阳市") || city.equals("贵阳")) {
			limtiCity = LimitCityEnum.GUIYANG;
		} else if (city.equals("杭州市") || city.equals("杭州")) {
			limtiCity = LimitCityEnum.HANGZHOU;
		} else if (city.equals("南昌市") || city.equals("南昌")) {
			limtiCity = LimitCityEnum.NANCHANG;
		} else if (city.equals("长春市") || city.equals("长春")) {
			limtiCity = LimitCityEnum.CHANGCHUN;
		} else if (city.equals("兰州市") || city.equals("兰州")) {
			limtiCity = LimitCityEnum.LANZHOU;
		} else if (city.equals("天津市") || city.equals("天津")) {
			limtiCity = LimitCityEnum.TIANJIN;
		} else {// 无配置城市返回配置提示
			return CarLimitUtil.getToDate(sysdate) + ",非限行城市";
		}

		String todate = CarLimitUtil.getToDate(sysdate);
		String strs = "";
		try {
			strs = carLimitDao.getLimitDataByCity(limtiCity, sysdate);
			if (strs == null || "".equals(strs)) {
				return CarLimitUtil.getToDate(sysdate) + ",非限行城市";
			}
		} catch (Exception e) {
			return CarLimitUtil.getToDate(sysdate) + ",非限行城市";
		}
		String[] date = strs.split(",");
		String todates = CarLimitUtil.getToDate(sysdate);
		if (getSpecialDate(limtiCity, sysdate)) {
			str = todate + ",无停驶尾号";
			return str;
		}
		if (!strs.contains("和")) {
			str = todate + "," + strs.replaceAll(",", "和");
			return str;
		}
		if (((todate.equals("星期六")) || (todate.equals("星期日"))) && (!getChangeByWeekDate(limtiCity, sysdate))) {
			if (city.equals("长春市") || city.equals("长春")) {
				str = todate + "," + strs;
			} else {
				str = todate + ",无停驶尾号";
			}

		} else if (getSpecialDate(limtiCity, sysdate)) {
			str = todate + ",无停驶尾号";
		} else {
			String num = null;
			if (city.equals("长春市") || city.equals("兰州市") || city.equals("长春") || city.equals("兰州")) {
				num = strs;
			} else {
				num = getCarNumber(todates, date);
			}
			str = todate + "," + num;
			limtiCity = null;
		}
		return str;
	}

	@Override
	public List<String> getCarTrafficForWeek(CarLimitModel carLimitModel) {
		List<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sdate = carLimitModel.getSdate();
		String date = "";
		if (StringUtils.isEmpty(sdate)) {
			date = sdf.format(new Date());
		} else {
			date = sdate;
		}
		String city = carLimitModel.getCt();
		if (StringUtils.isEmpty(city)) {
			city = "北京市";
		}
		LimitCityEnum limtiCity = null;
		if (city.equals("北京市") || city.equals("北京")) {
			limtiCity = LimitCityEnum.BEIJING;
		} else if (city.equals("成都市") || city.equals("成都")) {
			limtiCity = LimitCityEnum.CHENGDU;
		} else if (city.equals("贵阳市") || city.equals("贵阳")) {
			limtiCity = LimitCityEnum.GUIYANG;
		} else if (city.equals("杭州市") || city.equals("杭州")) {
			limtiCity = LimitCityEnum.HANGZHOU;
		} else if (city.equals("南昌市") || city.equals("南昌")) {
			limtiCity = LimitCityEnum.NANCHANG;
		} else if (city.equals("长春市") || city.equals("长春")) {
			limtiCity = LimitCityEnum.CHANGCHUN;
		} else if (city.equals("兰州市") || city.equals("兰州")) {
			limtiCity = LimitCityEnum.LANZHOU;
		} else if (city.equals("天津市") || city.equals("天津")) {
			limtiCity = LimitCityEnum.TIANJIN;
		} else {// 无配置城市返回配置提示
			result.add(date + "," + CarLimitUtil.getToDate(date) + ",非限行城市");
			return result;
		}

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < 7; i++) {
			String str = "";
			if (i != 0) {
				cal.add(Calendar.DATE, 1);
				date = sdf.format(cal.getTime());
			}
			String todate = CarLimitUtil.getToDate(date);

			String strs = "";
			try {
				strs = carLimitDao.getLimitDataByCity(limtiCity, date);
				if (strs == null || "".equals(strs)) {
					result.add(date + "," + CarLimitUtil.getToDate(date) + ",非限行城市");
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.add(date + "," + CarLimitUtil.getToDate(date) + ",非限行城市");
				continue;
			}
			if (getSpecialDate(limtiCity, date)) {
				str = date + "," + todate + ",无停驶尾号";
				result.add(str);
				continue;
			}
			String[] data = strs.split(",");
			if (!strs.contains("和")) {
				str = date + "," + todate + "," + strs.replaceAll(",", "和");
				result.add(str);
				continue;
			}
			if (((todate.equals("星期六")) || (todate.equals("星期日"))) && (!getChangeByWeekDate(limtiCity, date))) {
				if (city.equals("长春市") || city.equals("长春")) {
					str = date + "," + todate + "," + strs;
				} else {
					str = date + "," + todate + ",无停驶尾号";
				}
			} else if (getSpecialDate(limtiCity, date)) {
				str = date + "," + todate + ",无停驶尾号";
			} else {
				String num = null;
				if (city.equals("长春市") || city.equals("兰州市") || city.equals("长春") || city.equals("兰州")) {
					num = strs;
				} else {
					num = getCarNumber(todate, data);
				}
				str = date + "," + todate + "," + num;

			}

			result.add(str);
		}

		return result;
	}

	private boolean getSpecialDate(LimitCityEnum limitCity, String sysdate) {
		boolean boo = false;
		if (!carLimitDao.limitDataHaskey(limitCity, "20100000")) {
			return boo;
		}
		String str = carLimitDao.getLimitDataByCity(limitCity, "20100000");

		String[] date = str.split(",");
		int i = 0;
		for (int j = date.length; i < j; i++) {
			String sst = date[i].toString();
			if (sysdate.equals(sst)) {
				boo = true;
				break;
			}
		}
		return boo;
	}

	private boolean getChangeByWeekDate(LimitCityEnum limitCity, String sysdate) {
		boolean boo = false;
		if (!carLimitDao.limitDataHaskey(limitCity, "20109999")) {
			return boo;
		}
		String str = carLimitDao.getLimitDataByCity(limitCity, "20109999");
		String[] date = str.split(",");
		int i = 0;
		for (int j = date.length; i < j; i++) {
			String sst = date[i].toString();
			if (sysdate.equals(sst)) {
				boo = true;
				break;
			}
		}
		return boo;
	}

	private String getCarNumber(String date, String[] str) {
		Map<String, String> map = new HashMap<String, String>();
		int i = 0;
		for (int j = str.length; i < j; i++) {
			if (i == 0) {
				map.put("星期一", str[i].toString());
			} else if (i == 1) {
				map.put("星期二", str[i].toString());
			} else if (i == 2) {
				map.put("星期三", str[i].toString());
			} else if (i == 3) {
				map.put("星期四", str[i].toString());
			} else if (i == 4) {
				map.put("星期五", str[i].toString());
			} else if (i == 5) {
				map.put("星期六", str[i].toString());
			} else if (i == 6) {
				map.put("星期日", str[i].toString());
			}
		}
		String number = (String) map.get(date);

		return number;
	}

	@Override
	public void refreshLimitDataByCity(LimitCityEnum city, long modifiedTime) {
		carLimitDao.refreshLimitDataByCity(city, modifiedTime);
	}
}
