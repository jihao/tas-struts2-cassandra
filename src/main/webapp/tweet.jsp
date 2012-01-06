<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Tweet</title>
</head>
<body>
	<p>
		Hi <s:property value="#session['SESSION_CURRENT_USER'].username" />, your account was created at: 
		<s:date name="#session['SESSION_CURRENT_USER'].created_at" format="yyyy-MM-dd HH:mm:ss" />
	</p>
	<p>Tweet</p>
	<s:actionerror />
	<s:form action="tweet">
		<s:textarea name="message" label="I'd like to say" rows="5" cols="40" />
		<s:submit value="Tweet" />
	</s:form>
	<div id="tweetList">
		<s:iterator value="#request.tweetList" status="status" var="tweet">
			<div id='<s:property value="#status.index"/>'>
				<div>
					<s:hidden value="#tweet.id_key"></s:hidden>
					<s:property value="#tweet.message"></s:property>
				</div>
				<s:property value="#tweet.user.username"/>
				
			</div>
		</s:iterator>
	</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>