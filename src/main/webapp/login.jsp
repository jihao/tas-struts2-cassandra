<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Sign In</title>
</head>
<body>
	<p>Sign In</p>
	
	<s:actionerror />
	<s:form action="login">

		<s:textfield name="user.username" label="Name" />
		<s:password name="user.password" label="Password" />
		
		<s:submit value="Sign In" />

	</s:form>
	
<a href="<s:url action="index" namespace="config-browser" />">Launch the configuration browser</a>
<jsp:include page="_footer.jsp"/>
</body>
</html>