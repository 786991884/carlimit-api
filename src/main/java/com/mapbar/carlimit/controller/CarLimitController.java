package com.mapbar.carlimit.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.mapbar.carlimit.model.CarLimitModel;
import com.mapbar.carlimit.service.CarLimitService;
import com.mapbar.carlimit.util.LimitCityEnum;
import com.mapbar.carlimit.util.PropertiesUtil;
import com.mapbar.carlimit.util.StringUtil;

@Controller
@RequestMapping(value = "/search")
public class CarLimitController {
	public static final String TemplateID = "46_1";
	public static final String TemplateIDWeek = "46_3";
	@Resource
	private CarLimitService carLimitService;

	@RequestMapping(value = "/carLimit", method = { RequestMethod.GET, RequestMethod.POST })
	public void getCarLimit(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();

		try {
			String mapbarService = StringUtil.getNoNull(request.getParameter("MapbarService"));
			if (mapbarService.length() > 0) {
				response.setHeader("MapbarService", mapbarService);
			}
			response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
			String strEncode = StringUtil.getNoNull(request.getParameter("ch"));
			if (strEncode.length() < 1) {
				strEncode = "UTF-16LE";
			}
			String tp = StringUtil.getNoNull(request.getParameter("tp"));
			String ct = StringUtil.getNoNull(request.getParameter("ct"));
			if ("GET".equals(method)) {
				ct = StringUtil.encodeStr(ct, strEncode);
			}
			String sdate = StringUtil.getNoNull(request.getParameter("date"));
			StringBuffer strResult = new StringBuffer();
			CarLimitModel car = new CarLimitModel();
			car.setTp(tp);
			car.setCt(ct);
			car.setSdate(sdate);
			car.setCh(strEncode);
			if (TemplateIDWeek.equals(tp)) {
				int allstatus = 7;
				strResult.append("|$#|").append(allstatus).append("|").append(allstatus).append("|").append(TemplateIDWeek).append("|3|1,日期|1,星期|1,限行尾号|");
				List<String> list = carLimitService.getCarTrafficForWeek(car);
				for (String s : list) {
					String[] strarrays = s.split(",");
					strResult.append(strarrays[0]).append("|").append(strarrays[1]).append("|").append(strarrays[2]).append("|");
				}
				strResult.append("#$|");
			} else {
				int allstatus = 1;
				String str = null;
				str = carLimitService.getCarTraffic(car);
				String[] strarrays = str.split(",");
				strResult.append("|$#|").append(allstatus).append("|").append(allstatus).append("|").append(TemplateID).append("|2|1,星期|1,限行尾号|").append(strarrays[0]).append("|").append(strarrays[1]).append("|#$|");
			}
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.getWriter().write(strResult.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/carLimitJson", method = { RequestMethod.GET, RequestMethod.POST })
	public String getCarLimit(HttpServletRequest request, HttpServletResponse response, String callback) {
		String method = request.getMethod();
		try {
			String mapbarService = StringUtil.getNoNull(request.getParameter("MapbarService"));
			if (mapbarService.length() > 0) {
				response.setHeader("MapbarService", mapbarService);
			}
			response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
			String strEncode = StringUtil.getNoNull(request.getParameter("ch"));
			if (strEncode.length() < 1) {
				strEncode = "UTF-16LE";
			}
			String tp = StringUtil.getNoNull(request.getParameter("tp"));
			String ct = StringUtil.getNoNull(request.getParameter("ct"));
			if ("GET".equals(method)) {
				ct = StringUtil.encodeStr(ct, strEncode);
			}
			String sdate = StringUtil.getNoNull(request.getParameter("date"));
			CarLimitModel car = new CarLimitModel();
			car.setTp(tp);
			car.setCt(ct);
			car.setSdate(sdate);
			car.setCh(strEncode);
			if (TemplateIDWeek.equals(tp)) {
				int allstatus = 7;
				StringBuffer strResult = new StringBuffer();
				strResult.append("|$#|").append(allstatus).append("|").append(allstatus).append("|").append(TemplateIDWeek).append("|3|1,日期|1,星期|1,限行尾号|");
				List<String> list = carLimitService.getCarTrafficForWeek(car);
				JSONArray jarray = new JSONArray();
				JSONObject obj = null;
				String[] strarrays = null;
				for (String s : list) {
					strarrays = s.split(",");
					obj = new JSONObject();
					obj.put("date", strarrays[0]);
					obj.put("week", strarrays[1]);
					obj.put("desc", strarrays[2]);
					jarray.add(obj);
				}
				if (StringUtils.isNotBlank(callback)) {
					JSONPObject jsonpObject = new JSONPObject(callback);
					jsonpObject.addParameter(jarray);
					return jsonpObject.toString();
				}
				return jarray.toString();
			} else {
				String str = null;
				str = carLimitService.getCarTraffic(car);
				String[] strarrays = str.split(",");
				JSONObject obj = new JSONObject();
				obj.put("week", strarrays[0]);
				obj.put("desc", strarrays[1]);
				if (StringUtils.isNotBlank(callback)) {
					JSONPObject jsonpObject = new JSONPObject(callback);
					jsonpObject.addParameter(obj);
					return jsonpObject.toString();
				}
				return obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@ResponseBody
	@RequestMapping(value = "/carLimitWeekJson", method = { RequestMethod.POST, RequestMethod.GET })
	public String getCarLimitWeek(HttpServletRequest request, HttpServletResponse response, String callback) {
		String method = request.getMethod();
		try {
			String strEncode = StringUtil.getNoNull(request.getParameter("ch"));
			if (strEncode.length() < 1) {
				strEncode = "UTF-16LE";
			}
			String mapbarService = StringUtil.getNoNull(request.getParameter("MapbarService"));
			if (mapbarService.length() > 0) {
				response.setHeader("MapbarService", mapbarService);
			}
			response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
			String tp = StringUtil.getNoNull(request.getParameter("tp"));
			String ct = StringUtil.getNoNull(request.getParameter("ct"));
			if ("GET".equals(method)) {
				ct = StringUtil.encodeStr(ct, strEncode);
			}
			String sdate = StringUtil.getNoNull(request.getParameter("date"));
			int allstatus = 7;
			StringBuffer strResult = new StringBuffer();
			strResult.append("|$#|").append(allstatus).append("|").append(allstatus).append("|").append(TemplateIDWeek).append("|3|1,日期|1,星期|1,限行尾号|");
			CarLimitModel car = new CarLimitModel();
			car.setTp(tp);
			car.setCt(ct);
			car.setSdate(sdate);
			car.setCh(strEncode);
			List<String> list = carLimitService.getCarTrafficForWeek(car);
			JSONArray jarray = new JSONArray();
			JSONObject obj = null;
			String[] strarrays = null;
			for (String s : list) {
				strarrays = s.split(",");
				obj = new JSONObject();
				obj.put("date", strarrays[0]);
				obj.put("week", strarrays[1]);
				obj.put("desc", strarrays[2]);
				jarray.add(obj);
			}
			if (StringUtils.isNotBlank(callback)) {
				JSONPObject jsonpObject = new JSONPObject(callback);
				jsonpObject.addParameter(jarray);
				return jsonpObject.toString();
			}
			return jarray.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/carLimitWeek", method = { RequestMethod.POST, RequestMethod.GET })
	public void getCarLimitWeek(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		try {
			String strEncode = StringUtil.getNoNull(request.getParameter("ch"));
			if (strEncode.length() < 1) {
				strEncode = "UTF-16LE";
			}
			String mapbarService = StringUtil.getNoNull(request.getParameter("MapbarService"));
			if (mapbarService.length() > 0) {
				response.setHeader("MapbarService", mapbarService);
			}
			response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
			// String TemplateID = CommonParameter.getTemplateID(request.getRequestURI()); //模板ID
			String tp = StringUtil.getNoNull(request.getParameter("tp"));
			String ct = StringUtil.getNoNull(request.getParameter("ct"));
			if ("GET".equals(method)) {
				ct = StringUtil.encodeStr(ct, strEncode);
			}
			String sdate = StringUtil.getNoNull(request.getParameter("date"));
			int allstatus = 7;
			StringBuffer strResult = new StringBuffer();
			strResult.append("|$#|").append(allstatus).append("|").append(allstatus).append("|").append(TemplateIDWeek).append("|3|1,日期|1,星期|1,限行尾号|");
			CarLimitModel car = new CarLimitModel();
			car.setTp(tp);
			car.setCt(ct);
			car.setSdate(sdate);
			car.setCh(strEncode);
			List<String> list = carLimitService.getCarTrafficForWeek(car);

			for (String s : list) {
				String[] strarrays = s.split(",");
				strResult.append(strarrays[0]).append("|").append(strarrays[1]).append("|").append(strarrays[2]).append("|");
			}
			strResult.append("#$|");
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.getWriter().write(strResult.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/carLimit1")
	public ResponseEntity<String> getCarLimit1(CarLimitModel carLimitModel, HttpServletRequest request) {
		String strEncode = StringUtil.getNoNull(request.getParameter("ch"));
		if (carLimitModel.getCh().length() < 1) {
			carLimitModel.setCh("UTF-16LE");
		}
		int allstatus = 1;
		StringBuffer strResult = new StringBuffer();
		String str = null;
		carLimitModel.setCt(StringUtil.encodeStr(carLimitModel.getCt(), strEncode));
		carLimitModel.setSdate(carLimitModel.getDate());
		str = carLimitService.getCarTraffic(carLimitModel);
		String[] strarrays = str.split(",");
		strResult.append("|$#|").append(allstatus).append("|").append(allstatus).append("|").append(TemplateID).append("|2|1,星期|1,限行尾号|").append(strarrays[0]).append("|").append(strarrays[1]).append("|#$|");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setCacheControl("no-cache");
		responseHeaders.setContentType(MediaType.TEXT_PLAIN);
		ResponseEntity<String> responseEntity = null;
		str = strResult.toString();
		responseEntity = new ResponseEntity<String>(str, responseHeaders, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/refreshLimitDataByCity", method = RequestMethod.GET)
	public String refreshLimitData(@RequestParam(required = true, defaultValue = "BEIJING") String city) {
		PropertiesUtil.refreshPropertiesByCity(LimitCityEnum.adapt(city));
		return "redirect:refresh";
	}

}
