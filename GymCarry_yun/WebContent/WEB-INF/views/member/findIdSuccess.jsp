<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Member member = (Member) session.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GYM CARRY</title>
<!-- CSS파일 분리 -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/defaultarea.css">
<link rel="stylesheet" type="text/css" href="/resources/css/findId.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
<style>
	#userId {
		color : #690000;
	}
	legend {
		margin : 100px auto 100px;
	}
	.goLogin {
		font-family: sans-serif;
		text-decoration: none;
		color : #690000;
		font-weight: bold;
	}
	a {
		decoration: none;
	}
</style>
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

			<div>
				<h2>
					고객님의 아이디는 <a id="userId"><%=member.getUserId()%> </a>입니다.
				</h2>
				<br> <a href="/member/login" class="goLogin">Login하기</a> <br>
			</div>
		</div>
		<footer>
			<div class="frame">
				<a href="/main"><img src="/resources/img/wLogo.png"></a>
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