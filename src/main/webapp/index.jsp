<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>

<html>
<head>
<title>Twitter with Drifters</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
hr {
	border: none;
	height: 1px;
	color: #333;
	background-color: #333;
}
</style>
</head>
<body style="margin: 0px 0px 0px 20px;">
	<h2>Twitter with Drifters</h2>
	<br>
	<form action="executeApi" method="POST">
		<input type="text" name="argumentStr" value=""> <select
			name="twitterAction">
			<option value="Favorite Tweets">Favorite Tweets</option>
			<option value="Follow User">Follow User</option>
			<option value="Followers List">Followers List</option>
			<option value="Get Tweets with HashTag">Get Tweets with HashTag</option>
			<option value="Show Timeline">Show Timeline</option>
			<option value="Supported Languages">Supported Languages</option>
			<option value="Trending From">Trending From</option>
			<option value="Tweet">Tweet</option>
		</select> <input type="submit" value="Submit" />
	</form>
	<br>
	<div class="panel panel-primary">
		<%
			try {
				if (session.getAttribute("twitterAction") != null) {
		%>
		<div class="panel-heading"><%=session.getAttribute("twitterAction")%></div>
		<%
			}

				if (session.getAttribute("twitterResponse") != null) {
					List<String> responses = (List<String>) session.getAttribute("twitterResponse");

					for (String resp : responses) {
		%>
		<div class="panel-body"><%=resp%></div>
		<hr>
		<%
			}
				}
			} catch (Exception e) {
			}
		%>
	</div>
</body>
</html>

