<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%
	String path = request.getContextPath();
//getContextPath()方法的作用：获取相对路径
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" +request.getServerPort() + path + "/";
//getScheme()、getServerName()、getServerPort()的作用分别是获取链接协议、获取服务器域名、获取端口号
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href = "<%= basePath %>">
<title>计算机考试报名</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link href="css/mystylesheet.css" rel="stylesheet" type="text/css" /> 
</head>
<body class="twoColHybLtHdr">
<div id="container">
	<div id="header">
		<%@ include file="txtfile/header.txt" %>
	</div>
	<div id="sidebar1">
		<%@ include file="txtfile/left.txt" %>
	</div>
	<div id="mainContent">
		<center>
			<p><font size=4 color=red>欢迎报名</font></p><br>
			<img src="image/earth.jpg" width=450 height=350></img>
		</center>		
	</div>
	<br class = "clearfloat" />
		<%@ include file="txtfile/footer.txt" %>
</div>
</body>
</html>
