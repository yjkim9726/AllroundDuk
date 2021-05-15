<%@page import="member.model.vo.Member"%>
<%@page import="java.util.ArrayList"%>
<%@page import="event.model.vo.EventPic"%>
<%@page import="event.model.vo.Event"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Event event = (Event)request.getAttribute("event");
	ArrayList<EventPic> epList = (ArrayList<EventPic>)request.getAttribute("epList");
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
    <title>GYM CARRY 근딜 상세보기</title>
    <link rel="stylesheet" type="text/css" href="${path}/resources/css/geundealDetail.css">
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
	        <div class="inner-frame">
	            <div class="title">
	                <div class="sub-title">
	                    <p><%= event.getPartnerName() %></p>
	                    <!-- <button src="">센터페이지 바로가기</button> -->
	                </div>
	                <span><%= event.getEventTitle() %></span>
	            </div>
	            <div class="contents">
	                <div class="con-title">
	                    <span>이벤트 기간</span>
	                    <P><%= event.getStartDate() %> ~ <%= event.getEndDate() %></P>
	                </div>
	                <div class="con-title">
	                    <span>센터 위치</span>
	                    <p><%= event.getEventAddress() %></p>
	                </div>
	                <% for(EventPic eventPic : epList) { %>
	                <div class="img-title">
	                    <div class="img-box">
	                        <img src="${path}/resources/Eupload/<%= eventPic.getFileName() %>">
	                    </div>
	                </div>
	                <% } %>
	                <div class="text-box">
	                    <p><%= event.getEventContent() %><br>
	                    </p>
	                </div>
	                <button type="button" onclick="history.back(-1);">목록보기</button>
	            </div>
	        </div>
	    </div>
	</main>
	<footer>
	    <div class="frame">
	        <a href="/main"><img src="${path}/resources/img/wLogo.png"></a>
	        <div class="footer-contents">
	            <p>(주)올라운덕	<span class="stick"></span>	대표이사 올라운덕	<span class="stick"></span>	사업자등록번호 123-45-67890</p>
	            <p>서울특별시 종로구 종로대로 1000  	 <span class="stick"></span>	고객센터 080-1234-5678 (수신자요금부담)</p>
	        </div>
	        <p class="copyright"><span>©</span>ALL ROUNDUK. All rights reserved.</p>
	    </div>
	</footer>
</body>
</html>