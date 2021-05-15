<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int checkCode = 0;
	if(request.getAttribute("checkCode") != null) {
		checkCode = (int)request.getAttribute("checkCode");
	}
	Member member = (Member)request.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GYMCARRY 짐캐리</title>
<link rel="stylesheet" type="text/css" href="/resources/css/defaultarea.css">
<link rel="stylesheet" type="text/css" href="/resources/css/resetPwd.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
</head>
<body>
	<script>
		function checkPwd() {
			var pwd = $("#pwd").val;
			var rePwd = $("#pwd-re").val;
			
			pwd.on("keyup", function () {
				if (!pwdreg.test(pwd.val())) {
					$("#checkPwd").text("패스워드는 대소문자,숫자를 포함한 5~12자리 입니다.");
					return false;
				} else {
					$("#checkPwd").text("사용가능한 패스워드 입니다.");
				}

			});
			$(pwdch).on("keyup", function () {
				if (pwd.val() != pwdch.val()) {
					$("#checkPwd").text("패스워드가 일치하지 않습니다. 다시 확인해주세요.");
					return false;
				} else {
					$("#checkPwd").text("패스워드가 일치합니다.");
				}

			});

		}
	</script>
	
		<!-- ********************************** 헤더 *********************************** -->
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
	
	<!-- ********************************** 메인 *********************************** -->
	<main>
		<section class="main-frame">
			
			<h1 class="index">비밀번호 재설정</h1><br>
			<section id="find-box">
				<form action="/member/resetPwd" method="post">
					<h2>아이디를 입력해주세요.</h2>
					<input type="text" id="user-id" name="user-id" placeholder="아이디 입력" required />
					<h2>변경할 새 비밀번호를 입력해주세요.</h2>
					<div>
					<input type="password" id="pwd" name="user-pwd" placeholder="비밀번호 입력" required />
					<input type="password" id="pwd-re" name="user-pwd-re" onkeyup="checkPwd()" placeholder="비밀번호 입력" required />
					</div>
					<div id="checkPwd"></div>
					<input type="submit" class="submitBtn" value="변경 완료" />
				</form>
			</section>
		</section>
	</main>
	
	<!-- ********************************** footer *********************************** -->
	<footer>
		<div class="frame">
			<a href="/main"><img src="/img/wLogo.png"></a>
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