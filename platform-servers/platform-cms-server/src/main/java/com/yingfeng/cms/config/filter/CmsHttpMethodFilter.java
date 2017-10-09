package com.yingfeng.cms.config.filter;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CMS基本拦截器 <br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.config.filter
 * @since 2017/8/7
 */
@Slf4j
@WebFilter(filterName="cmsHttpMethodFilter", urlPatterns="/*")
public class CmsHttpMethodFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestUrl = request.getRequestURL().toString();
//      response.sendRedirect(request.getContextPath() + "/session/fail");

        filterChain.doFilter(servletRequest, servletResponse);

        log.info("URL>>"+requestUrl);

    }

    @Override
    public void destroy() {
        log.info("Destruction filter");
    }
}
