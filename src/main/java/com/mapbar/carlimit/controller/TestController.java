package com.mapbar.carlimit.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.mapbar.carlimit.model.CarLimitModel;

@Controller
@RequestMapping(value = "/test")
public class TestController {

	@ControllerAdvice(basePackages = "com.mapbar.carlimit.controller")
	static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
		public JsonpAdvice() {
			super("callback", "jsonp");
		}
	}

	@RequestMapping(value = "/response/annotation", method = RequestMethod.GET)
	@ResponseBody
	public String responseBody() {
		return "The String ResponseBody 测试";
	}

	@RequestMapping(value = "/response/charset/accept", method = RequestMethod.GET)
	@ResponseBody
	public String responseAcceptHeaderCharset() {
		return "こんにちは世界！ (\"Hello world!\" in Japanese)";
	}

	@RequestMapping(value = "/response/charset/produce", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String responseProducesConditionCharset() {
		return "こんにちは世界！ (\"Hello world!\" in Japanese)";
	}

	@RequestMapping(value = "/response/entity/status", method = RequestMethod.GET)
	public ResponseEntity<String> responseEntityStatusCode() {
		return new ResponseEntity<String>("The String ResponseBody with custom status code (403 Forbidden - stephansun测试)", HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/response/entity/headers", method = RequestMethod.GET)
	public ResponseEntity<String> responseEntityCustomHeaders() {
		HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>("The String ResponseBody with custom header Content-Type=text/plain 测试", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<CarLimitModel> addUser(CarLimitModel u) {
		// implementation omitted
		List<CarLimitModel> list = new ArrayList<>();
		u.setCh("aa");
		u.setCt("bb");
		list.add(u);
		return list;
	}

	@RequestMapping(value = "/users1", method = RequestMethod.GET)
	@ResponseBody
	public List<CarLimitModel> addUser1(CarLimitModel u) {
		// implementation omitted
		List<CarLimitModel> list = new ArrayList<>();
		u.setCh("aa");
		list.add(u);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/user2", method = { RequestMethod.GET, RequestMethod.POST })
	public JSONPObject login(CarLimitModel u, HttpServletRequest request, String callback) {
		u.setCh("aa");
		// 只要放回Jsonp对象即可
		JSONPObject jsonpObject = new JSONPObject(callback);
		jsonpObject.addParameter(u);
		jsonpObject.addParameter(u);
		return jsonpObject;
	}

	@ResponseBody
	@RequestMapping(value = "/user3", method = { RequestMethod.GET, RequestMethod.POST })
	public String login1(CarLimitModel u, HttpServletRequest request, String callback) {
		u.setCh("aa中文");
		// 只要放回Jsonp对象即可
		JSONPObject jsonpObject = new JSONPObject(callback);
		jsonpObject.addParameter(u);
		String u1 = "bb";
		jsonpObject.addParameter(u1);
		return jsonpObject.toString();
	}

	@RequestMapping(value = "/users3", method = RequestMethod.GET)
	public List<CarLimitModel> addUser2(CarLimitModel u) {
		// implementation omitted
		List<CarLimitModel> list = new ArrayList<>();
		u.setCh("aa唯唯诺诺");
		String a = "cc";
		list.add(u);
		return list;
	}

	@RequestMapping(value = "/users4", method = RequestMethod.GET)
	public String addUser3(CarLimitModel u) {
		JSONObject j = new JSONObject();
		j.put("aa", "cc");
		j.put("bb", "dd");
		return j.toString();
	}
	
	@RequestMapping(value = "/users5", method = RequestMethod.GET)
	public JSONObject addUser5() {
		JSONObject j = new JSONObject();
		j.put("aa", "cc");
		j.put("bb", "dd");
		return j;
	}

	@RequestMapping(value = "/something")
	public ResponseEntity<CarLimitModel> handle() throws UnsupportedEncodingException {
		// String requestHeader = requestEntity.getHeaders().getFirst("MyRequestHeader");
		// byte[] requestBody = requestEntity.getBody();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<CarLimitModel>(new CarLimitModel(), responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/something1")
	public ResponseEntity<String> handle1(HttpEntity<String> requestEntity) throws UnsupportedEncodingException {
		byte[] bytes = requestEntity.getBody().getBytes();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<String>("Hello World 测试", responseHeaders, HttpStatus.CREATED);
	}
}
