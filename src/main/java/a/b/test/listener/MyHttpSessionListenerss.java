package a.b.test.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListenerss implements HttpSessionListener {
	/**
	 * 创建session监视器
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// 在线人数的统计（当前在线人数放在 ServletContext 中的 onLineCount ）
		// 现在 servletContextListener 的自定义监视器中的初始化方法中定义 onLineCount 的数量，
		// 然后，在此session创建的监视中，对onLineCount自增1操作，onLineCount 表示当前在线人数

		// 通过session获取ServletContext上下文
		ServletContext context = se.getSession().getServletContext();
		int count = 1;
		if (context.getAttribute("onLineCount") == null) {
			context.setAttribute("onLineCount", 1);
		} else {
			count = (Integer) context.getAttribute("onLineCount");
			count++;
			context.setAttribute("onLineCount", count);
		}
		System.out.println("-------------> 一个新的session创建了，count=" + count);
	}

	/**
	 * session 失效监视器
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext context = se.getSession().getServletContext();
		int count = (Integer) context.getAttribute("onLineCount");
		count--;
		context.setAttribute("onLineCount", count);
	}
}