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
<link rel="stylesheet" type="text/css" href="/resources/css/findPwd.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
</head>
<body>
	<script>
		function checkCode(){
			var v1 = certification.code_check.value;
			var v2 = certification.code.value;
			if(v1 != v2) {
				document.getElementById("checkcode").style.color = "red";
				document.getElementById("checkCode").innerHTML = "잘못된 인증번호";
				makeNull();
			} else {
				document.getElementById("checkCode").style.color = "#600000";
				document.getElementById("checkCode").innerHTML = "인증완료";
				makeReal();
			}
		}
		function makeNull() {
			var hi = document.getElementById("hi");
			hi.type = "hidden";
		}
		function makeReal() {
			var hi = document.getElementById("hi");
			hi.type = "submit";
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
	<main class="main">
		<section class="main-frame" id="wrapper">
			<h1 class="index">비밀번호 찾기</h1>
			
			<section id="find-box">
				<!-- if 인증번호 값이 없으면(get)으로 찾기 -->
				<%-- <% if(checkCode == 0) { %> --%>
				<form action="/member/sendFindPwd">
					<h2>아이디를 입력해주세요.</h2>
					<input type="text" name="userId">
					<h2>회원가입 시 등록한 이메일을 입력해주세요.</h2>
					<input type="text" name="userEmail"></br>
					<input type="submit" class="submitBtn" value="인증번호 발송">
				</form>
				<br><br>
				<%-- <% } %> --%>
				
				
				<!-- if 인증번호 값이 있으면(get) 아이디 표시 -->
				<%-- <% if(checkCode != 0) {%> --%>
				<form id="certification" action="/member/resetPwd" method="get">
					<!-- 인증번호 일치하면 비밀번호 재설정으로 넘어감 -->
					<fieldset>
						<legend><h2>인증번호 입력</h2></legend>
						<h4>인증번호를 입력해주세요.</h4>
						<input type="text" name="code" id="code" onkeyup="checkCode()" maxlength="5"><br>
						<div id="checkCode"></div>
						<input type="hidden" readonly="readonly" name="code_check" id="code_check" value="<%= checkCode %>" />
						<input id="confirmBtn" type="hidden" value="인증하기" />
					</fieldset>
				</form>
				<%-- <% } %> --%>
				
			</section>
		
		</section>
	</main>
	
	
	<!-- ********************************** footer *********************************** -->
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