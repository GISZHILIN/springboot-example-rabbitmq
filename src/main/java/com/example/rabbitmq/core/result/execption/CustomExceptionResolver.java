package com.example.rabbitmq.core.result.execption;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.rabbitmq.core.execption.CommonExecption;
import com.example.rabbitmq.core.result.ResResult;
import com.example.rabbitmq.core.result.ResResultEnum;
import com.example.rabbitmq.core.utils.BeanToMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        log.error("【统一异常拦截】请求出现异常，内容如下：",e);
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        String uri = request.getRequestURI();
        if(e instanceof CommonExecption){
            //CommonExecption为自定义异常类
            printWrite(ResResultEnum.CUSTOM_ERROR_MESSAGE,((CommonExecption) e).getMsg(),((CommonExecption) e).getData(), uri, mv);
        } else {
            printWrite(ResResultEnum.DEFAULT_ERROR_MESSAGE,e.getMessage(),null, uri, mv);
        }
        return mv;
    }

    /**
     * 封装相应结果
     * @param object
     */
    private void printWrite(ResResultEnum errorEnum, String msg, Object object, String uri, ModelAndView mv){
        ResResult resResult = new ResResult(uri, errorEnum, object);
        if(msg != null){resResult.setMsg(msg);}
        if(log.isDebugEnabled()){
            log.debug("【response】异常输出结果:" + JSONObject.toJSONString(resResult, SerializerFeature.WriteMapNullValue));
        }
        Map resultMap = BeanToMapUtil.beanToMap(resResult);
        mv.addAllObjects(resultMap);
    }
}
