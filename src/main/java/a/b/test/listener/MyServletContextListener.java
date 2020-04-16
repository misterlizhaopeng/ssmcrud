package a.b.test.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// sce.getSource() 与 sce.getServletContext()一样，只不过 sce.getServletContext()更具体了
		ServletContext servletContext1 = (ServletContext) sce.getSource();
		// ServletContext servletContext = sce.getServletContext();
		servletContext1.setAttribute("projectKey", servletContext1.getInitParameter("project"));
		System.out.println("context 被创建了 ,projectKey=" + servletContext1.getInitParameter("project"));

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("context 被销毁 了 ");
	}
}
