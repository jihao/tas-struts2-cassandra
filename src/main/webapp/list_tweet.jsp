<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>List Tweets</title>
</head>
<body>
	<p>
		<s:property value="tweet.message" />
		<s:property value="tweet.created_at" />
	</p>
	
	<jsp:include page="_footer.jsp"/>
</body>
</html>