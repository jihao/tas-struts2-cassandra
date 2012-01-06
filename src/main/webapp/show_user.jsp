<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Register</title>
</head>
<body>
	<h2>
		
	</h2>
	<p>
		 <s:property value="user.username" /> successfully registered, created at: <s:property value="user.created_at" />
		<br/>
		<a href='<s:url action="loginUI"/>'>Sign in</a> to tweet!
		
	</p>
	
	
	<jsp:include page="_footer.jsp"/>
</body>
</html>