package sc.yhy.servlet;

import java.io.IOException;

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
		try {
			this.before(request, response);
			this.doServlet(request, response);
			this.after(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.isCommitted(response);
		}
	}

	private void isCommitted(HttpServletResponse response) {
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (response.isCommitted()) {
				System.out.println("isCommitted=true");
				return;
			} else {
				System.out.println("isCommitted=false");
				return;
			}

		}

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

	protected abstract void before(HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected abstract void doServlet(HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected abstract void after(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
