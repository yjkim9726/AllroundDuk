<%@page import="member.model.vo.Member"%>
<%@page import="market.model.vo.Market"%>
<%@page import="event.model.vo.Event"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ArrayList<Event> eList = (ArrayList<Event>)request.getAttribute("eList");
	ArrayList<Market> mList = (ArrayList<Market>)request.getAttribute("mList");
	String userId = null;
	if(session != null && (String)session.getAttribute("userId") != null) {
		userId = (String)session.getAttribute("userId");
		
	}
	Member member = null;
	if (session != null && session.getAttribute("member") != null) {
		member = (Member) session.getAttribute("member");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<title>GYM CARRY짐캐리</title>
<link rel="stylesheet" type="text/css" href="resources/css/main.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">

</head>
<body>
	<header>
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
		<div class="frame">
			<a href="/main"><img src="resources/img/logo.png"></a>
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
			<div class="banner">
				<img class="bannerimg" src="resources/img/mainbanner.png">
				<p>솔직한 리뷰, 믿을 수 있는 정보!</p>
				<img src="resources/img/main-imglogo.png"> <img
					src="resources/img/main_contlogo.png">
			</div>
			<div class="moving"></div>
			<div id="market">
				<h1>GYM MARKET</h1>
				<h5>실시간 거래되는 회원권을 한눈에 비교해보자.</h5>
				<table>
					<tr class="tableheader">
						<th>NO</th>
						<th>TITLE</th>
						<th>NAME</th>
						<th>DATE</th>
					</tr>
					<% for(Market market : mList) { %>
					<tr>
							<td><%= market.getNum() %></td>
							<td><a href="/market/detail?marketNo=<%= market.getMarketNo() %>"><%= market.getMarketTitle() %></a></td>
							<td><%= market.getNickName() %></td>
							<td><%= market.getMarketDate() %></td>
					</tr>
					<% } %>
				</table>
			</div>
			<div id="deal">
				<h1>GEUN DEAL</h1>
				<h5>짐캐리에서만 만날 수 있는 초특가 이벤트를 확인해보자.</h5>
				<div class="moving"></div>
				<ul>
					<% for(Event event : eList) { %>
                    <li class="contents"><a href="/geundeal/detail?eventNum=<%= event.getEventNo() %>">
                        <div class="img-box">
                            <img src="${path}/resources/Eupload/<%= event.getFileName() %>">
                        </div>
                        <div class="content-text">
                            <div class="title-area">
                                <span><%= event.getPartnerName() %></span>
                                <p><%= event.getEventAddress() %></p>
                            </div>
                            <div class="sale-time">
                                <p><%= event.getEventTitle() %></p>
                                <p><%= event.getStartDate() %> ~ <%= event.getEndDate() %></p>
                            </div>
                            <button>GO</button>
                        </div>
                    </a></li>
                	<% } %>
				</ul>
			</div>
			<div class="enddiv">
				<div id="story">
					<div class="titlebox">
						<h2>GYM STORY</h2>
						<h1>
							WHY DID WE<br>STARTED THIS PROJECT?
						</h1>
						<p>짐 캐리의 시작, 그 이유는 어디에서 출발했을까.</p>
						<a href="/gymstory.jsp"><button>GO</button></a>
					</div>
				</div>
				<div id="business">
					<h2>FOR</h2>
					<h1>BUSINESS</h1>
					<P>비즈니스 문의는 여기에서</P>
					<a href="/geundeal/write"><button>GO</button></a>
				</div>
			</div>
		</div>
	</main>
	<footer>
		<div class="frame">
			<a href="/main"><img src="resources/img/wLogo.png"></a>
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