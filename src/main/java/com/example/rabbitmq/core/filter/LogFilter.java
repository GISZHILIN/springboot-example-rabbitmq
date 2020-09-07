package com.example.rabbitmq.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String serviceName = request.getServerName();
//        log.info("日志过滤器，请求路径：" + requestUri);
        chain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
