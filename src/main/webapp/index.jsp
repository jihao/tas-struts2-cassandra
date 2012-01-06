<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<body>
<h2>a simple twitter alike system (tas) build upon struts2 and cassandra</h2>

<s:if test="#session['SESSION_CURRENT_USER']">
<a href="<s:url action="logout"/>">Sign out</a>
</s:if>
<s:else>
<a href="<s:url action="registerUI"/>">Register</a> | <a href="<s:url action="loginUI"/>">Sign in</a>
</s:else>
<p><a href="<s:url action='tweetUI'/>">Tweet</a></p>

<jsp:include page="_footer.jsp"/>
</body>
</html>
