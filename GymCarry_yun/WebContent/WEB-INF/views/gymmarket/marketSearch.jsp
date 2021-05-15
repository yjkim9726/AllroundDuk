<%@page import="market.model.vo.Market"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% 
    	ArrayList<Market> list = (ArrayList<Market>)request.getAttribute("mList");
    	String pageNavi = (String)request.getAttribute("pageNavi");
    	String userId = (String)session.getAttribute("userId");
    	String searchkeyword = (String)request.getAttribute("searchKeyword");
    	String selectOption = (String)request.getAttribute("selectOption");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<title>짐마켓</title>
<link rel="stylesheet" type="text/css" href="/resources/css/gymmarket.css">
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
            $('#writebtn').on("click", function(){
	        	var userId = '<%=userId%>';
         		if(<%=userId%> == null) {
	                alert("로그인 후 사용가능합니다.");
	                return false;
	            }
            });
        }) 
    </script>
    
    <div id="wrapper">
         <header id="header">
            <!-- 네이게이션 바 -->
            <div class="frame">
                <a href="/index.jsp"><img src="${path}/resources/img/gym-carry.png"></a>
                <ul class="navbar">
                    <a href="/gymstory.jsp"><li class="nav-menu first-nav">GYM STORY</li></a>
                    <a href="#"><li class="nav-menu second-nav">GYM POT</li></a>
                    <a href="/market/list"><li class="nav-menu third-nav">GYM MARKET</li></a>
                    <a href="/geundeal/list"><li class="nav-menu fourth-nav">GEUN DEAL</li></a>
                </ul>
               <!-- 로그인 하지 않았을 때 보여지는 nav버튼 -->
			<%
				if (userId == null) {
			%>
			<ul class="login-box">
				<a href="/member/login" role="button"><li>LOGIN</li></a>
				<a href="/member/enroll" role="button"><li>JOIN</li></a>
			</ul>
			<!-- 로그인 했을 때 보여지는 nav버튼 -->
			<%
				} else {
			%>
			<ul class="login-box">
				<a href="#" role="button" id="msg-list"><li>MESSAGE</li></a>
				<a href="/member/mypage" role="button"><li>MY PAGE</li></a>
			</ul>
			<%
				}
			%>
            </div>
        </header>
            <!-- 상단 이미지 바  -->
            <div class="lb-wrap">
                <div class="lb-image">
                    <img src="${path}/resources/img/marketbanner.png">
                </div>
                <div class="lb-text">
                    <span id="bold-T">GYM MARKET</span> <br>
                    <span id="bottom-T">실시간 거래되는 회원권</span>
                </div>
            </div>

        <!-- 메인 시작################# -->
        <main>
            <div class="inner-frame">
                <div class=searcharea>
                	<form action="/market/search" method="get">
	                    <div id="searchBox">
	                    	<% if (selectOption.equals("title")) { %>
		                    <select name="searchOption" class="selectbox">
		                            <option value="title" selected>제목</option>
		                            <option value="writer">작성자</option>
		                            <option value="subject">내용</option>
		                    </select>
		                    <% } else if (selectOption.equals("writer")) { %>
		                    <select name="searchOption" class="selectbox">
		                            <option value="title">제목</option>
		                            <option value="writer" selected>작성자</option>
		                            <option value="subject">내용</option>
		                    </select>
		                    <% } else { %>
		                    <select name="searchOption" class="selectbox">
		                            <option value="title">제목</option>
		                            <option value="writer">작성자</option>
		                            <option value="subject" selected>내용</option>
		                    </select>
		                    <% } %>
		                    <input type="text" name="searchKeyword" class="searchbox" placeholder="검색어를 입력해주세요." value="<%=searchkeyword %> ">
		                    <button type="submit" class="searchbtn" value="검색">검색</button>
		               </div>
                    </from>
                </div>
                <table frame=void>
                    <tr class="list-header">
                        <th class="header-no">NO</th>
                        <th class="header-title">TITLE</th>
                        <th class="header-name">NAME</th>
                        <th class="header-date">DATE</th>
                    </tr>
                    <% for(Market mOne : list) { %>
                    <tr class="list-contain">
                        <th><%= mOne.getNum() %></th>
                        <th class="contain-title"><a href="/market/detail?marketNo=<%= mOne.getMarketNo()%>"><%= mOne.getMarketTitle() %></a></th>
                        <th><%= mOne.getNickName() %></th>
                        <th><%= mOne.getMarketDate() %></th>
                    </tr>
                    <% } %>
                </table>
                <div class="writebtn">
                	<input type="button" onclick="location.href='/market/write'" name="write" id="writebtn" value="글쓰기">
                </div>
                <div class="paging">
               		<%-- <a href="#"><img src="${path}/img/prev.png"></a>  --%>
               		  	<%= pageNavi %>
                    <%-- <a href="#"><img src="${path}/img/next.png"></a>  --%>
                </div>
          </div>
        </main>
        
        <footer>
            <div class="frame">
                <a href="#"><img src="${path}/resources/img/wLogo.png"></a>
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