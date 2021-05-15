<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String userId = (String) session.getAttribute("userId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GYM CARRY</title>
<!-- CSS파일 분리 -->
<link rel="stylesheet" type="text/css" href="/resources/css/defaultarea.css">
<link rel="stylesheet" type="text/css" href="/resources/css/login.css">
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
				<a href="/gymstory.jsp"><li class="nav-menu first-nav">GYM
						STORY</li></a>
				<a href="/partner/search"><li class="nav-menu second-nav">GYM POT</li></a>
				<a href="/market/list"><li class="nav-menu third-nav">GYM MARKET</li></a>
				<a href="/geundeal/list"><li class="nav-menu fourth-nav">GEUN DEAL</li></a>
			</ul>
			<!-- 로그인 하지 않았을 때 보여지는 화면 -->
			<%
			if (userId == null) {
			%>
			<ul class="login-box">
				<a href="/member/login" role="button"><li>LOGIN</li></a>
				<a href="/member/enroll" role="button"><li>JOIN</li></a>
			</ul>
			<%
			} else {
			%>
			<ul class="login-box">
				<a href="#" role="button"><li>MESSAGE</li></a>
				<a href="/member/mypage" role="button"><li>MY PAGE</li></a>
			</ul>
			<%
			}
			%>
		</div>
	</header>

	<div class="container">
		<div class="login-frame">
			<div class="login">
				<legend>LOGIN</legend>
			</div>
			<%
			if (userId == null) {
			%>
			<form name="loginInfo" action="/member/login" method="post">
				<a class="id">ID</a><input type="text" class="userId" name="userId"
					placeholder="아이디를 입력하세요" required><br> <a class="pw">PW</a><input
					type="password" class="userPwd" name="userPwd"
					placeholder="비밀번호를 입력하세요" required><br> <input type="submit"
					class="button" id="loginBtn" value="LOGIN"><br> 
			</form>
				<div class="bottomBtn">
					<span class="forget-msg" style="color='white';">Forgot your </span>
					<a href="/member/findId" class="findIdBtn" name="findIdBtn" id="findIdBtn">ID</a><span> or </span> 
					<a href="/member/findPwd" class="findPwdBtn" name="findPwdBtn" id="findPwdBtn">Password?</a><span>   </span>
					<a href="/member/enroll" class="joinBtn" name="joinBtn" id="joinBtn">Sign up</a>
				</div>
			<%
			}
			%>
			<% if(userId != null && userId != "") { %>
				<div class="congretMsg">
				<%= userId %> 님 환영합니다.
				
				<a href="/member/logout" class="logoutBtn">로그아웃</a>
				<a href="/main.jsp" class="goHome">홈으로 가기</a>
				</div>
			<% } %>
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