<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tweet</title>
</head>
<body>
	<p>
		<s:property value="user.username" />
		<s:property value="user.created_at" />
	</p>
	
	
	<p>Tweet</p>

	<s:form action="tweet">

		<s:textarea name="tweet.message" label="I'd like to say" rows="5" cols="40" />
		<s:submit value="Submit" />
	</s:form>
	
<jsp:include page="_footer.jsp"/>
</body>
</html>