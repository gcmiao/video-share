<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" import="com.lovemiao.videoshare.*"%>
<%
	Integer error = (Integer) request
			.getAttribute(Setting.KEY_ERROR_CODE);
%>
<%
	if (error == null || Setting.NO_ERROR.equals(error)) {
%>
<title>Welcome</title>
<%
	} else {
%>
<title>There is something wrong!</title>
<%
	}
%>
</head>
<body>
	<%
		if (error != null && !error.equals(Setting.NO_ERROR)) {
			String error_reason = (String) request
					.getAttribute(Setting.KEY_ERROR_REASON);
	%>
	<%=error_reason%>
	<br>
	<%
		}
	%>
	<%
		String verify = (String) session.getAttribute(Setting.KEY_VERIFY);
		if (!"TRUE".equals(verify)) {
	%>
	<form action="home" method="post">
		password: <input type="password" name="password"> <input
			type="submit" value="Enter">
	</form>
	<%
		} else {
	%>
	<a href=<%=config.getServletContext().getContextPath() + "/home"%>>enter</a>
	<%
		}
	%>
</body>
</html>