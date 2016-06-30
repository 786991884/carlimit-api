package com.mapbar.carlimit.converter;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;

public class FastJsonHttpMessageConverter extends com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter {
	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

		if (obj instanceof JSONPObject) {
			JSONPObject jsonp = (JSONPObject) obj;
			OutputStream out = outputMessage.getBody();
			String text = jsonp.getFunction() + "(" + JSON.toJSONString(jsonp.getParameters(), getFeatures()) + ")";
			byte[] bytes = text.getBytes(getCharset());
			out.write(bytes);
		} else {
			super.writeInternal(obj, outputMessage);
		}
	}
}
