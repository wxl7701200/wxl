package wxl.lt.framework.interceptors;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;




/**
 * Authority interceptor
 * 权限拦截器
 * 
 */
public class SecurityInterceptor implements HandlerInterceptor {

	private List<String> excludeUrls;//The resource don't need intercept 不需要拦截的资源

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * After finish the page render call
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

	}

	/**
	 * intercept after invoke controller's method
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * intercept before invoke controller's method
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		Object sessionInfo =  request.getSession().getAttribute("systemUser");
//		if(sessionInfo==null){
//			sessionInfo=  request.getSession().getAttribute("eshopUser");
//		}
//		if(sessionInfo==null){
//			sessionInfo=  request.getSession().getAttribute("emsuser");
//		}

		if ((url.indexOf("/index/login") > -1) || excludeUrls.contains(url)) {//// If the resource you call on needn't verify 如果要访问的资源是不需要验证的
			return true;
		}
		if (sessionInfo == null) {//if not login or login time out 如果没有登录或登录超时			
			request.getRequestDispatcher("/").forward(request, response);
			return false;
		}
		
		return true;
	}
}
