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
		UserInfo
	</h2>
	<h2>
		<s:property value="user.username" />
	</h2>
	<h2>
		<s:property value="user.created_at" />
	</h2>
	<h2>
		<s:property value="user.tweets.size" />
	</h2>
	
	
</body>
</html>