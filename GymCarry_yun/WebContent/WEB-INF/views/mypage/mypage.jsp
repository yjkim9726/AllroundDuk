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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<title>마이페이지</title>
<link rel="stylesheet" type="text/css" href="/resources/css/mypage.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
</head>
<body>
	<script>
         $(document).ready(function(){
        	 var btn = document.getElementById("msg-list");
 			btn.onclick = function() {
 				 var url = "/message/list";
 		            var name = "MESSAGE LIST";
 		            var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
 				window.open(url, name, option);
 			}
            $('.delete').on("click", function(){
	        	var del_check = confirm("회원 탈퇴 탈퇴하시겠습니까? 탈퇴하신 회원정보는 복구 할 수 없습니다.");
	        	if(del_check == true) {
	        		document.form.submit();
	        	} else {
	        		return false;
	        	}
            });
            var btn = document.getElementById("msg-list");
            btn.onclick = function() {
                var url = "/message/list";
                     var name = "MESSAGE LIST";
                     var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
               window.open(url, name, option);
            }

        }) 
    </script>
    <div id="wrapper">
         <header id="header">
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
           
        <!-- 메인 시작################# -->
        <main>
            <div class="welcome">
                <span class="text" ><%= member.getName() %> 님</span> <span class="
                text"> , 오늘도 캐리하세요!</span> 
            </div>
            <div class="info">
                <div class="circle">
                    <a class="cir myModify" href="/member/myinfo?userId=<%= member.getUserId() %>">
                        <img src="${path}/resources/img/myInfo.png">
                        <img src="${path}/resources/img/myInfo_hover.png">
                    </a>
                </div>
                <div class="circle">
                    <a class="cir myLike" href="/like/list">
                        <img src="${path}/resources/img/myLike.png">
                        <img src="${path}/resources/img/myLike_hover.png">
                    </a>
                </div>
                <div class="circle">
                    <a class="cir myReview" href="/review/mylist">
                        <img src="${path}/resources/img/myReview.png">
                        <img src="${path}/resources/img/myReview_hover.png">
                    </a>
                </div>
                <div class="circle lastc">
                    <a class="cir myWrite" href="/mypage/writeList">
                       <img src="${path}/resources/img/myWrite.png">
                       <img src="${path}/resources/img/myWrite_hover.png">
                    </a>
                </div>
                <div class="bye">
                    <a class="bye-text" href="/member/logout">로그아웃 / </a> 
                    <a class="bye-text delete" href="/member/delete">회원탈퇴</a>
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

    </div>
</body>
</html>