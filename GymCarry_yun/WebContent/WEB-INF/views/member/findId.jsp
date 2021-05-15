<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String userId = (String) session.getAttribute("userId");
Member member = (Member) session.getAttribute("memberInfo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GYM CARRY</title>
<!-- CSS파일 분리 -->
<link rel="stylesheet" type="text/css" href="/resources/css/defaultarea.css">
<link rel="stylesheet" type="text/css" href="/resources/css/findId.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

</head>
<body>
	<header>
		<div class="frame">
         <a href="/main"><img src="/resources/img/logo.png"></a>
         <ul class="navbar">
            <a href="/gymstory.jsp"><li class="nav-menu first-nav">GYM STORY</li></a>
            <a href="/partner/search"><li class="nav-menu second-nav">GYM POT</li></a>
            <a href="/market/list"><li class="nav-menu third-nav">GYM MARKET</li></a>
            <a href="/geundeal/list"><li class="nav-menu fourth-nav">GEUN DEAL</li></a>
         </ul>
         <!-- 로그인 하지 않았을 때 보여지는 nav버튼 -->
         <%
         if (member == null) {
         %>
         <ul class="login-box">
            <a href="/member/login" role="button"><li>LOGIN</li></a>
            <a href="/member/enroll" role="button"><li>JOIN</li></a>
         </ul>
         <!-- 로그인 했을 때 보여지는 nav버튼(admin) -->
         <%
         } else if (member != null && member.getUserId().equals("admin")) {
         %>
         <ul class="login-box">
            <a href="/admin/memberlist" role="button"><li>MANAGE</li></a>
            <a href="/admin/eventlist" role="button"><li>CFM DEAL</li></a>
         </ul>
         <!-- 로그인 했을 때 보여지는 nav버튼(user) -->
         <%
         } else {
         %>
         <ul class="login-box">
            <a href="/member/mypage" role="button"><li>MY PAGE</li></a>
            <a href="#" role="button" id="msg-list"><li>MESSAGE</li></a>
         </ul>
         <%
         }
         %>
      </div>
	</header>

	<div class="container">
		<div class="login-frame">
			<div class="subject">
				<legend>아이디 찾기</legend>
			</div>
			<%
			if (userId == null) {
			%>
			<form name="loginInfo" action="/member/findId" method="post">
				<a class="name">NAME</a>
				<input type="text" class="userName" name="userName" placeholder="가입 시 입력한 이름을 입력하세요" required><br> 
				<a class="phone">PHONE</a>
				<input type="text" class="userPhone" name="userPhone" placeholder="연락처를 입력하세요 (- 제외)" required><br> 
				<input type="submit" class="submitBtn" id="submitBtn" value="아이디 찾기"><br> 
			</form>
			<%
			}
			%>
		</div>
		<% if(userId != null && userId != "") { %>
		<div>
		<h2>고객님의 아이디는 <%=userId%> 입니다.</h2> <br>
		<a href="/main.jsp" class="goHome">홈으로</a> <br>
		</div>
		<% } %>
	</div>
	<footer>
		<div class="frame">
			<a href="/"><img src="/resources/img/wLogo.png"></a>
			<div class="footer-contents">
				<p>
					(주)올라운덕 <span class="stick"></span> 대표이사 올라운덕 <span class="stick"></span>
					사업자등록번호 123-45-67890
				</p>
				<p>
					서울특별시 종로구 종로대로 1000 <span class="stick"></span> 고객센터 080-1234-5678
					(수신자요금부담)
				</p>
			</div>
			<p class="copyright">
				<span>©</span>ALL ROUNDUK. All rights reserved.
			</p>
		</div>
	</footer>
</body>
</html>