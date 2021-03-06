package sc.yhy.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * servlet 工具类
 * 
 * @author YHY
 *
 */
public abstract class BaseServlet extends HttpServlet {
	static final Logger logfile = Logger.getLogger(BaseServlet.class.getName());

	private static final long serialVersionUID = 9074877621851516177L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.genery(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.genery(request, response);
	}

	private void genery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		long start = 0l, end = 0l;
		start = System.currentTimeMillis();
		try {
			this.doServlet(request, response);
		} catch (Exception e) {
			logfile.info(e.getMessage());
		}
		end = System.currentTimeMillis();
		System.out.println("执行时间=" + (end - start) + " ms");
	}

	protected void sendRedirect(HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(url);
	}

	protected void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String url) throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	protected String getRequestParamValue(HttpServletRequest request, String key) {
		return request.getParameter(key);
	}

	protected String[] getRequestParamValues(HttpServletRequest request, String key) {
		return request.getParameterValues(key);
	}

	protected abstract void doServlet(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
