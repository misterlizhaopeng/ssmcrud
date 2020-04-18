package a.b.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyFirstInterceptor implements HandlerInterceptor {
	/**
	 * 调用目标方法之前被调用； 返回true，则继续调用后续的拦截器和方法； 返回false，则不能继续调用后续的拦截器和方法；
	 *
	 * @date 2018年5月20日
	 * @author misterLip
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("MyFirstInterceptor-preHandle");
		return true;
	}

	/**
	 * 调用目标方法之后，渲染视图之前调用
	 *
	 * @date 2018年5月20日
	 * @author misterLip
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("MyFirstInterceptor-postHandle");
	}

	/**
	 * 渲染视图之后调用
	 *
	 * @date 2018年5月20日
	 * @author misterLip
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("MyFirstInterceptor-afterCompletion");
	}
}