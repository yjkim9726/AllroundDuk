<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userId = (String)session.getAttribute("userId");
	Member member = null;
	if (session != null && session.getAttribute("member") != null) {
	   member = (Member) session.getAttribute("member");
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">                                                                                                                     
	<title>짐캐리 소개</title>                                                                                                                      
	<link rel="stylesheet" type="text/css" href="resources/css/gymstory.css">                                                                                
	<link rel="preconnect" href="https://fonts.gstatic.com">                                                                                   
	<link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
</head>
<body>
	<script>
		window.onload = function() {
			var btn = document.getElementById("msg-list");
			btn.onclick = function() {
				 var url = "/message/list";
		            var name = "MESSAGE LIST";
		            var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
				window.open(url, name, option);
			}
		}
	</script>
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
        <main>
            <div class="frame">
                <img class="banner" src="resources/img/gymstory-banner.png">
                <p><span>GYM STORY</span><br>짐캐리의 시작</p>
                <div class="main-visual">
                    <h2 class="center-text">2021년 여섯명의 덕후들이 만든 짐캐리는<br>운동을 사랑하는 사람들의 사소한 고민에서 출발하였다.</h2>
                    <div class="moving"></div>
                    <div class="submoving"></div>
                    <img class="member" src="resources/img/member.png">
                    <span class="sobig">그래서 만들었다</span>
                    <img class="light" src="resources/img/light.png">
                    <h2 class="right-text">‘헬스’, ‘PT’종류만 보여주는 기존 사이트보다<br>더 다양한 니즈가 있는 ‘프로 운동러’들을 위해 다양한 <br>운동시설을 포함시켰다.</h2>
                    <img class="pc-img" src="resources/img/pc.png">
                    <h2 class="secnd-right-text">내 주변의 모든 운동시설의 가격정보를<br>한눈에 확인할 수 있다.</h2>
                    <img class="mockup" src="resources/img/gymmarket.png">
                    <h2 class="center-text">열심히 손품팔아 겨우 양도가 가능했던 회원권을<br>짐캐리를 통해 한 곳에 모아서 볼 수 있다. </h2>
                    <img class="roundlogo" src="resources/img/round-logo.png">
                    <h2 class="third-right-text">브랜드명의 ‘캐리’는 좋은 운동시설로 이끌어주는<br>우리의 목표를 의미한다.<br>회원들에게 더 나은 정보와 가격을 제공하고자 한다. </h2>
                </div>
            </div>
        </main>
        <footer>
        	<div class="frame">
                <a href="/main"><img src="resources/img/wLogo.png"></a>
                <div class="footer-contents">
                    <p>(주)올라운덕	<span class="stick"></span>	대표이사 올라운덕	<span class="stick"></span>	사업자등록번호 123-45-67890</p>
                    <p>서울특별시 종로구 종로대로 1000  	 <span class="stick"></span>	고객센터 080-1234-5678 (수신자요금부담)</p>
                </div>
                <p class="copyright"><span>©</span>ALL ROUNDUK. All rights reserved.</p>
            </div>
        </footer>
</body>
</html>