package com.mapbar.carlimit.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CharacterEncodingFilter extends org.springframework.web.filter.CharacterEncodingFilter {
	private String encoding;

	private boolean forceEncoding = false;

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		response.setHeader("Server", "MapbarServer");// 在回应信息的头中加入返回的标识信息
		String contentType = request.getContentType();// 得到请求的用户的contentType
		String ch = request.getParameter("ch");
		if (contentType != null && contentType.indexOf("charset=") > -1) {
			encoding = contentType.substring(contentType.indexOf("charset=") + 8);
		} else if (StringUtils.isNotEmpty(ch)) {
			encoding = ch;
		}
		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
			}
		}
		request.setAttribute("ch", encoding);
		filterChain.doFilter(request, response);
	}
}
