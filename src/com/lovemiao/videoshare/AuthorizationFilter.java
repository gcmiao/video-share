package com.lovemiao.videoshare;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/AuthorizationFilter")
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String password = request.getParameter("password");
		if ("scripts/.dll-tt7".equals(password)) {
			session.setAttribute(Setting.KEY_VERIFY, "TRUE");
			session.setMaxInactiveInterval(600);
		}

		String verify = (String) session.getAttribute(Setting.KEY_VERIFY);
		if (!"TRUE".equals(verify)) {
			request.setAttribute(Setting.KEY_ERROR_CODE,
					Setting.ERROR_NO_AUTHORIZATION);
			request.setAttribute(Setting.KEY_ERROR_REASON, "no authorizion");
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
