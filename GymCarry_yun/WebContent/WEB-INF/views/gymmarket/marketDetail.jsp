<%@page import="member.model.vo.Member"%>
<%@page import="like.model.vo.Like"%>
<%@page import="market.model.vo.MarketPic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="market.model.vo.Market"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Market market = (Market) request.getAttribute("market");
	ArrayList<MarketPic> picList = (ArrayList<MarketPic>) request.getAttribute("picList");
	String likeCheck = (String) request.getAttribute("likeCheck"); // MarketDetailServlet에서 'like'나 'none'을 받아와서 저장해줌
	/* if (session.getAttribute("userId") != null && session.getAttribute("uniqId") != null) {
		userId = (String) session.getAttribute("userId");
		uniqId = (int) session.getAttribute("uniqId");
	}  */
	Member member = null;
	String userId = null;
	int uniqId = 0;
	if (session != null && session.getAttribute("member") != null) {
   		member = (Member) session.getAttribute("member");
    	uniqId = member.getUniqId();
    	userId = member.getUserId();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.3.1.js"
	integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
	crossorigin="anonymous"></script>
<title>양도권 게시글 작성</title>
<link rel="stylesheet" type="text/css"
	href="/resources/css/marketDetail.css">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Anton&family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet">
</head>
<body>
	<script>
        $(document).ready(function(){
        	$("#msg-list").on("click", function() {
				 var url = "/message/list";
		            var name = "MESSAGE LIST";
		            var option = "width=550,height=670,left=500,top=300,resizable=no, toolbars=no, menubar=no ,status=no";
				window.open(url, name, option);
			});
		
			$('.star').on("click", function(){
	    		if('<%= userId %>' == 'null' || <%= uniqId %> == 0) {
	    			alert("로그인 후 사용가능한 서비스입니다.");
	    			return false;
	    		} else {
	    			location.href = "/market/like?marketNo="+<%=market.getMarketNo()%>+"&uniqId="+ <%= uniqId %>;        			
	    		}
	    	});
			$("#sendmsgbtn").on("click", function() {
				window.open("/message/write?marketNo="+<%=market.getMarketNo()%>, "쪽지보내기",
						"width=500,height=600,menubar=no,tollbar=no");
			});
        });
        
		        			
		

    </script>
	<!-- <div id="wrapper"> -->
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
	<main>
		<div class="frame">
			<div id="marketdetail">
				<div class="titlewrapper">
					<span class="category"> <%=market.getMarketField()%></span><br>
					<span class="title"><%=market.getMarketTitle()%></span>
					<div class="zzim">
						<%
							if (!likeCheck.equals("like")) {
						%>
						<button type="button" class="star" name="likeCheck">
							<img class="img-btn" src="${path}/resources/img/linestar.png" />
						</button>
						<span id="textzzim">찜하기</span>
						 <%
							} else {
						%>
						<button type="button" class="star" name="likeCheck">
							<img class="img-btn" src="${path}/resources/img/colorstar.png" />
						</button>
						<span id="textzzim">찜해제</span>
						 <%
							}
						%> 
					</div>
					<br>
					<div class="info">
						<span class="name"> <%=market.getNickName()%></span><span
							class="date"><%=market.getMarketDate()%> </span>
					</div>
				</div>

				<div class="contents">
					 <%
						for (MarketPic fd : picList) {
					%> 
					<div class="product-title">
						<div class="img-box">
							<img class="main-pic"
								src="${path}/resources/MUpload/<%= fd.getFileName() %>">
						</div>
					</div>
					<br>
					<%
						}
					%> 

					<span class="price">희망 가격 : <%=market.getMarketPrice()%></span>
					<p class="content"> <%=market.getMarketContent()%> 
					</p>
				</div>
				<div style="text-align: center;">
					<%--  <form action="/message/write" method="get">
						<input type="hidden" name="marketNo"
							value="<%=market.getMarketNo()%>"> <input type="submit"
							class="sendmsgbtn" value="쪽지보내기">
					</form> --%>
					 <%
						if (member != null) {
					%> 
						<a href="#" role="button"><button id="sendmsgbtn">쪽지보내기</button></a>
					<%
						}
					%> 
				</div>
			</div>
		</div>
	</main>
	<footer>
		<div class="frame">
			<a href="/main"><img src="${path}/resources/img/wLogo.png"></a>
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