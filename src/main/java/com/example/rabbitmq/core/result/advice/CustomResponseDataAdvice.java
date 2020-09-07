package com.example.rabbitmq.core.result.advice;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.rabbitmq.core.result.ResResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.File;

/**
 * controller层返回对象统一组装
 */
@ControllerAdvice
public class CustomResponseDataAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseDataAdvice.class);

    /**
     * 配置支持对结果进行增强处理
     * @param methodParameter
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    /**
     * 只有supports返回true时，才做增强处理
     * @param body
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String path = serverHttpRequest.getURI().getPath();
        if(body != null && (body instanceof File)){
            //文件流不进行增强处理
            return body;
        } else if (body != null && (body instanceof ResResult)){
            //如果body返回的是ResResult类型的对象，不进行增强处理
            return body;
        } else if (body != null && (body instanceof String)){
            //如果body返回的是String类型的对象，不进行增强处理
            return body;
        } else {
            ResResult resResult = new ResResult(path, body);
            log.debug("【response】标准输出结果:" + JSONObject.toJSONString(resResult, SerializerFeature.WriteMapNullValue));
            return resResult;
        }
    }
}
