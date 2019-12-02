package top.imlgw.rbac.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author imlgw.top
 * @date 2019/11/21 16:36
 */
@Slf4j
@Component
public class HttpInterceptor implements HandlerInterceptor {

    /**
     *
     * Controller方法处理之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURL = request.getRequestURL().toString();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("request start. url:{}, param:{}",requestURL,new HashMap<>(parameterMap));
        return true;
    }

    /**
     * preHandle返回true
     * Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURL = request.getRequestURL().toString();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("request finished. url:{}, param:{}",requestURL,new HashMap<>(parameterMap));
    }

    /**
     * Interceptor的preHandle方法的返回值为true, 或者Controller中抛了异常才会执行
     * DispatcherServlet进行视图的渲染之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURL = request.getRequestURL().toString();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("request complete. url:{}, param:{}",requestURL,new HashMap<>(parameterMap));
    }
}
