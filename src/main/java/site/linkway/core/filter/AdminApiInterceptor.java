package site.linkway.core.filter;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import site.linkway.core.dao.AdminDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminApiInterceptor  implements HandlerInterceptor {
    //在请求处理的方法之前执行
    //如果返回true执行下一个拦截器
    //如果返回false就不执行下一个拦截器
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //检测id与cookie值是否匹配,匹配则放行，不匹配则拒绝通过
        //test seesion_id
        HttpSession httpSession=httpServletRequest.getSession();
        String id=(String)httpSession.getAttribute("id");
        if(null==id||false==id.equals(AdminDao.admin)){
            httpServletResponse.sendRedirect("/loginPage");
            return false;
        }
        return true;
    }
    //在请求处理方法执行之后执行
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    //在dispatcherServlet处理后执行,做清理工作.
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
