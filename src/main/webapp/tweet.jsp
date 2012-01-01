<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tweet</title>
</head>
<body>
	<h2>
		<s:property value="user.username" />
	</h2>
	<h2>
		<s:property value="user.created_at" />
	</h2>
	
	<p>Tweet</p>

	<s:form action="tweet">

		<s:textarea name="tweet.message" label="I'd like to say" rows="5" cols="40" />
		<s:submit value="Submit" />
	</s:form>
	
<a href="<s:url action="index" namespace="config-browser" />">Launch the configuration browser</a>

</body>
</html>