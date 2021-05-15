<%@page import="message.model.vo.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Message message = (Message) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MESSAGE</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Anton&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
	<link rel="stylesheet" type="text/css"
	href="/resources/css/messageDetail.css">
<style>

</style>
</head>
<body>
	<script>
		function fn_popClose(){
			top.window.close();
		}
		function removeCheck() {
			if (confirm("정말 삭제하시겠습니까??") == true) { //확인
				document.removefrm.submit();
			} else { //취소
				return false;
			}
		}
	</script>
	<div class="dialog">
		<!-- <a href="/message/list"><span class="dialog__back">&#11013;</span></a> -->
		<button class="back-button" onclick="history.back()"><span class="dialog__back">&#11013;</span></button>
		<span class="dialog__close" onclick="fn_popClose();">&#x2715;</span>
		<h2 class="dialog__title">MESSAGE</h2>
		<br>
		<div id="msg-list" class="msg-list">
			<div class="top-box">
				<div class="msg-id msg-top" id="">
				<span class="to-from">TO.</span>
				<%=message.getNickName()%></div>
				<!-- <div id="msg-read" class="msg-top">읽음</div> -->
				<div class="msg-date msg-top"><%=message.getMessageDate()%></div>
			</div>
			<div class="msg-content">
				<p><%=message.getMessageContent()%></p>
			</div>
		</div>
					<div class="btn" id="re-btn">
				<!-- <form action="/message/delete" method="post"> -->
				<a href="/message/delete?messageNo=<%= message.getMessageNo() %>">
					<input type="submit" value="삭제" class="delete-btn"  onclick="return removeCheck()">
				</a>
				<!-- </form> -->
			</div>
	</div>
</body>
</html>