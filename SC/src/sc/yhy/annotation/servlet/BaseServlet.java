package sc.yhy.annotation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 9074877621851516177L;
	protected HttpSession session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected PrintWriter printWriter;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.genery(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.genery(request, response);
	}

	private void genery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		this.session = request.getSession();
		this.request = request;
		this.response = response;
		this.printWriter = response.getWriter();
		try {
			this.before();
			this.doServlet();
			this.after();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendRedirect(String url) throws IOException {
		this.response.sendRedirect(url);
	}

	protected void dispatcherForward(String url) throws IOException, ServletException {
		this.request.getRequestDispatcher(url).forward(this.request, this.response);
	}

	protected String getRequestParamValue(String key) {
		return this.request.getParameter(key);
	}

	protected String[] getRequestParamValues(String key) {
		return this.request.getParameterValues(key);
	}

	protected abstract void before() throws Exception;

	protected abstract void doServlet() throws Exception;

	protected abstract void after() throws Exception;

}
