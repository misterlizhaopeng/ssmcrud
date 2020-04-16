package a.b.test.servlet;

import java.io.IOException;

import javax.jws.soap.InitParam;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class T1Servlet
 */
@WebServlet(value = "/T1Servlet", initParams = { 
		@WebInitParam(name = "a", value = "b"),
		@WebInitParam(name = "aa", value = "d") })
public class T1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public T1Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext sc = getServletContext();
		sc.setAttribute("parameter1", "tst");// 依靠对象ServletContext，在不同 servlet 之间传递参数；
		String initParameter2 = this.getInitParameter("aa");// 获取servlet的参数

		// Object contextConfigLocation = sc.getAttribute("contextConfigLocation");
		String initParameter = sc.getInitParameter("contextConfigLocation");
		response.getWriter().append("Served at:abcd---ggog ,-------->contextConfigLocation=" + initParameter
				+ ",servlet's parameter=" + initParameter2).append(request.getContextPath());

		for (int i = 0; i < 10; i++)
			request.getSession().setAttribute("a-session" + i, "value-session");
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
