package com.mapbar.carlimit.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionInterceptor implements HandlerExceptionResolver {
	private static final Logger logger = Logger.getLogger(ExceptionInterceptor.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("msg", StringUtils.isEmpty(ex.getMessage()) ? "未知异常" : ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ModelAndView("error", model);
	}
}
